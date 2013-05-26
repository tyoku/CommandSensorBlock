package com.tyoku.bukkit.plugin.csb.command;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.tyoku.bukkit.plugin.csb.CommandSensorBlock;
import com.tyoku.bukkit.plugin.csb.TyokuchanCommandBlock;

public class SensorCommandDistance implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender paramCommandSender, Command paramCommand, String paramString,
			String[] paramArrayOfString) {
		if(!paramCommandSender.isOp()){
			paramCommandSender.sendMessage("OP用");
			return true;
		}
		if(paramArrayOfString.length != 1){
			return false;
		}
		int distance = 0;
		try {
			distance = Integer.parseInt(paramArrayOfString[0]);
		} catch (Exception e) {
			return false;
		}

		if(paramCommandSender instanceof Player){
			Player player = (Player)paramCommandSender;
			Location loc = player.getTargetBlock(null, 3).getLocation();
			TyokuchanCommandBlock tcb = CommandSensorBlock.tbManager.getTyokuchanBlock(loc);
			if(tcb != null){
				tcb.setDistance(distance);
				CommandSensorBlock.tbManager.setDistance(tcb);
				player.sendMessage("コマンド有効範囲を設定しました。:"+distance);
				player.sendMessage(tcb.toString());
			}else{
				player.sendMessage("コマンドは未設定");
			}
			return true;
		}else{
			paramCommandSender.sendMessage("プレイヤー用");
			return true;
		}
	}

}
