package com.panda0day.kbffa.config;

import com.panda0day.kbffa.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class MainConfig implements IConfig {
    private final File file;
    private FileConfiguration fileConfiguration;

    public MainConfig(String fileName) {
        this.file = new File(Main.getInstance().getDataFolder(), fileName);

        this.createFile();
        this.createDefaults();
    }

    public void createFile() {
        if (!file.exists()) {
            try {
                file.createNewFile();
            }catch(IOException exception) {
                System.out.println(exception.getMessage());
            }
        }

        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public void createDefaults() {
        fileConfiguration.addDefault("prefix", "&6KnockbackFFA &8» &7");
        fileConfiguration.addDefault("world", "kbffa1");

        fileConfiguration.options().copyDefaults(true);
        save();
    }

    public void save() {
        try {
            fileConfiguration.save(file);
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void reload() {
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }

    public String getPrefix() {
        String prefix = fileConfiguration.getString("prefix");
        if (prefix == null) prefix = "&6KnockbackFFA &8» &7";
        return ChatColor.translateAlternateColorCodes('&', prefix);
    }

    public String getWorld() {
        String world = fileConfiguration.getString("world");
        if (world == null) world = "kbffa1";

        return world;
    }
}
