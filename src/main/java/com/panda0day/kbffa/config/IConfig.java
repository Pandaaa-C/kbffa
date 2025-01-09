package com.panda0day.kbffa.config;

import org.bukkit.configuration.file.FileConfiguration;

public interface IConfig {
    void createFile();
    void createDefaults();
    void save();
    void reload();
    FileConfiguration getFileConfiguration();
}
