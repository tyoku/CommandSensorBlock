package com.tyoku.bukkit.plugin.csb.command;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.tyoku.bukkit.plugin.csb.CommandSensorBlock;
import com.tyoku.bukkit.plugin.csb.TyokuchanCommandBlock;

public class SensorCommandClear implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender paramCommandSender, Command paramCommand, String paramString,
			String[] paramArrayOfString) {
		if(!paramCommandSender.isOp()){
			paramCommandSender.sendMessage("OP用");
			return true;
		}

		if(paramCommandSender instanceof Player){
			Location loc = ((Player)paramCommandSender).getTargetBlock(null, 3).getLocation();
			TyokuchanCommandBlock tcb = CommandSensorBlock.tbManager.getTyokuchanBlock(loc);
			CommandSensorBlock.tbManager.clearCommand(tcb);
			paramCommandSender.sendMessage("コマンド削除");
			return true;
		}else{
			paramCommandSender.sendMessage("プレイヤー用");
			return true;
		}
	}

}
