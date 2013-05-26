package com.tyoku.bukkit.plugin.csb;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import com.tyoku.bukkit.plugin.csb.command.CreateSensorCommand;
import com.tyoku.bukkit.plugin.csb.command.SensorCommandClear;
import com.tyoku.bukkit.plugin.csb.command.SensorCommandDistance;
import com.tyoku.bukkit.plugin.csb.command.SensorCommandView;
import com.tyoku.bukkit.plugin.csb.listener.BlockListener;

public class CommandSensorBlock extends JavaPlugin {
	protected static CommandSensorBlock instance;
	public static Logger logger;
	public static TyokuchanBlockManager tbManager;
	public static boolean commandable = true;

	@Override
	public void onEnable() {
		instance = this;
		logger = getLogger();

		//コマンド読み込み
		getCommand("csb").setExecutor(new CreateSensorCommand());
		getCommand("csbclear").setExecutor(new SensorCommandClear());
		getCommand("csblist").setExecutor(new SensorCommandView());
		getCommand("csbdistance").setExecutor(new SensorCommandDistance());

		//リスナー読み込み
		getServer().getPluginManager().registerEvents(new BlockListener(), this);

		//管理呼び出し
		tbManager = new TyokuchanBlockManager();
	}

	public static CommandSensorBlock getInstance(){
		return instance;
	}

}
