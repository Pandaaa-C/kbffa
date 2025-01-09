package com.panda0day.kbffa.managers;

import com.panda0day.kbffa.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class LocationManager {
    public static Map<String, Location> getLocations() {
        Map<String, Location> locations = new HashMap<>();

        try {
            ResultSet resultSet = Main.getDatabase().executeQuery("SELECT * FROM locations");
            while (resultSet.next()) {
                World world = Bukkit.getWorld(resultSet.getString("world"));
                String name = resultSet.getString("name");
                double x = resultSet.getDouble("x");
                double y = resultSet.getDouble("y");
                double z = resultSet.getDouble("z");
                float yaw = resultSet.getFloat("yaw");
                float pitch = resultSet.getFloat("pitch");

                locations.put(name, new Location(world, x, y, z, yaw, pitch));
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }

        return locations;
    }

    public static Location getLocation(String name) {
        try {
            ResultSet resultSet = Main.getDatabase().executeQuery("SELECT * FROM locations WHERE name=?", name);
            while (resultSet.next()) {
                World world = Bukkit.getWorld(resultSet.getString("world"));
                double x = resultSet.getDouble("x");
                double y = resultSet.getDouble("y");
                double z = resultSet.getDouble("z");
                float yaw = resultSet.getFloat("yaw");
                float pitch = resultSet.getFloat("pitch");

                return new Location(world, x, y, z, yaw, pitch);
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }

        return null;
    }

    public static boolean doesLocationExist(String name) {
        ResultSet resultSet = Main.getDatabase().executeQuery("SELECT * FROM locations WHERE name=?", name);

        try {
            if (resultSet != null) {
                return resultSet.next();
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException exception) {
                System.out.println(exception.getMessage());
            }
        }

        return false;
    }

    public static boolean addLocation(String name, String world, double x, double y, double z, float yaw, float pitch) {
        ResultSet resultSet = Main.getDatabase().executeQuery("INSERT INTO locations (name, world, x, y, z, yaw, pitch) VALUES (?, ?, ?, ?, ?, ?, ?)",
                name,
                world,
                x,
                y,
                z,
                yaw,
                pitch
        );

        return resultSet == null;
    }

    public static boolean deleteLocation(String name) {
        ResultSet resultSet = Main.getDatabase().executeQuery("DELETE FROM locations WHERE name=?", name);
        return resultSet == null;
    }

    public static void createDefaultTable() {
        Main.getDatabase().createTable("""
                CREATE TABLE IF NOT EXISTS locations (
                     id INT AUTO_INCREMENT PRIMARY KEY,
                     name VARCHAR(255) NOT NULL UNIQUE,
                     world VARCHAR(255) NOT NULL,
                     x DOUBLE NOT NULL,
                     y DOUBLE NOT NULL,
                     z DOUBLE NOT NULL,
                     yaw FLOAT NOT NULL,
                     pitch FLOAT NOT NULL
                 )
                """);
    }
}
