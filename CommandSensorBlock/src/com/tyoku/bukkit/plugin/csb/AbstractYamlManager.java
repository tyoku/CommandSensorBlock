package com.tyoku.bukkit.plugin.csb;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

public abstract class AbstractYamlManager{

    private File file;
    YamlConfiguration config;

    /**
     * コンストラクタ
     */
    public AbstractYamlManager() {

        file = new File(
        		CommandSensorBlock.getInstance().getDataFolder() +
                File.separator + getConfigFileName());

        if ( !file.exists() ) {
            YamlConfiguration conf = new YamlConfiguration();
            try {
                conf.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

	public abstract String getConfigFileName();


	public void save(){
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public YamlConfiguration getConfig(){
		return config;
	}

}
