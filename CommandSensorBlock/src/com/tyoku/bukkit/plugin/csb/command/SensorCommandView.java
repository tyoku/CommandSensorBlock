package com.tyoku.bukkit.plugin.csb.command;

import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.tyoku.bukkit.plugin.csb.CommandSensorBlock;
import com.tyoku.bukkit.plugin.csb.TyokuchanCommandBlock;

public class SensorCommandView implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender paramCommandSender, Command paramCommand, String paramString,
			String[] paramArrayOfString) {
		if(!paramCommandSender.isOp()){
			paramCommandSender.sendMessage("OP用");
			return true;
		}

		if(paramCommandSender instanceof Player){
			Player player = (Player)paramCommandSender;
			Block block = player.getTargetBlock(null, 3);
			TyokuchanCommandBlock tcb = CommandSensorBlock.tbManager.getTyokuchanBlock(block.getLocation());
			if(tcb != null){
				player.sendMessage("設定内容参照：" + block.getType().toString());
				player.sendMessage(tcb.toString());
			}else{
				player.sendMessage("コマンドは未設定："+ block.getType().toString());
			}
			return true;
		}else{
			paramCommandSender.sendMessage("プレイヤー用");
			return true;
		}
	}

}
