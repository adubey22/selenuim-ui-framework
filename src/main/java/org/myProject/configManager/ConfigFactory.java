package org.myProject.configManager;

import org.aeonbits.owner.ConfigCache;

public class ConfigFactory {
    private ConfigFactory(){
        // Prevent instantiation
    }
    public static FrameworkConfig getConfig(){
        return ConfigCache.getOrCreate(FrameworkConfig.class);
    }
}
