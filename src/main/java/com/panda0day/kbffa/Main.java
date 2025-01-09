package com.panda0day.kbffa;

import com.panda0day.kbffa.config.DatabaseConfig;
import com.panda0day.kbffa.config.MainConfig;
import com.panda0day.kbffa.database.Database;
import com.panda0day.kbffa.managers.EntitySpawner;
import com.panda0day.kbffa.managers.LocationManager;
import com.panda0day.kbffa.managers.PlayerBoardManager;
import com.panda0day.kbffa.managers.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.Set;

public class Main extends JavaPlugin {
    private static Main instance;
    private static Database database;

    // Configs
    private static DatabaseConfig databaseConfig;
    private static MainConfig mainConfig;

    // Tasks
    private BukkitTask boardTask;

    @Override
    public void onEnable() {
        instance = this;

        databaseConfig = new DatabaseConfig("mysql.yml");
        mainConfig = new MainConfig("config.yml");

        try {
            this.registerEventListenerClasses();
            this.registerCommandClasses();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }

        this.connectDatabase();

        WorldManager.loadWorld(Main.getMainConfig().getWorld());

        boardTask = getServer().getScheduler().runTaskTimer(this, PlayerBoardManager.getInstance(), 0, 1);

        Location location = LocationManager.getLocation("ticket_shop");
        if (location == null) {
            location = new Location(Bukkit.getWorld("world"),0 ,0, 0);
        }
        new EntitySpawner(EntityType.VILLAGER, location, "Ticket Shop");
    }

    @Override
    public void onDisable() {
        database.disconnect();

        Bukkit.getOnlinePlayers().forEach(player -> {
            player.kickPlayer("Server is being restarted..");
        });

        Bukkit.getWorlds().forEach(world -> {
            world.getEntities().forEach(Entity::remove);
        });
    }

    private void registerEventListenerClasses() throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Reflections reflections = new Reflections("com.panda0day.kbffa.listeners");
        Set<Class<? extends Listener>> listenerClasses = reflections.getSubTypesOf(Listener.class);
        getLogger().info("Loading " + listenerClasses.size() + " listeners");

        for (Class<? extends Listener> listenerClass : listenerClasses) {
            Listener listener = listenerClass.getDeclaredConstructor().newInstance();
            getServer().getPluginManager().registerEvents(listener, this);

            getLogger().info("Registered " + listenerClass.getSimpleName());
        }
    }

    private void registerCommandClasses() throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Reflections reflections = new Reflections("com.panda0day.kbffa.commands");
        Set<Class<? extends CommandExecutor>> commandClasses = reflections.getSubTypesOf(CommandExecutor.class);
        getLogger().info("Loading " + commandClasses.size() + " commands");

        for (Class<? extends CommandExecutor> commandClass : commandClasses) {
            CommandExecutor command = commandClass.getDeclaredConstructor().newInstance();
            String commandName = command.getClass().getSimpleName().replace("Command", "");

            Objects.requireNonNull(getCommand(commandName)).setExecutor(command);

            getLogger().info("Registered " + commandClass.getSimpleName());
        }
    }

    private void connectDatabase() {
        String host = databaseConfig.getFileConfiguration().getString("host");
        String username = databaseConfig.getFileConfiguration().getString("name");
        String password = databaseConfig.getFileConfiguration().getString("password");
        int port = databaseConfig.getFileConfiguration().getInt("port");
        String database = databaseConfig.getFileConfiguration().getString("database");

        setDatabase(new Database(host, username, password, port, database));
        getDatabase().connect();
    }

    public static Main getInstance() {
        return instance;
    }

    public static DatabaseConfig getDatabaseConfig() {
        return databaseConfig;
    }

    public static MainConfig getMainConfig() {
        return mainConfig;
    }

    public static Database getDatabase() {
        return database;
    }

    public static void setDatabase(Database database) {
        Main.database = database;
    }
}