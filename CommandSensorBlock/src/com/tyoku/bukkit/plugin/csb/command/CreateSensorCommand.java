package com.tyoku.bukkit.plugin.csb.command;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.tyoku.bukkit.plugin.csb.CommandSensorBlock;
import com.tyoku.bukkit.plugin.csb.TyokuchanCommandBlock;

public class CreateSensorCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		if(!arg0.isOp()){
			arg0.sendMessage("OP用");
			return true;
		}

		if(arg0 instanceof Player){
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < arg3.length; i++) {
				sb.append(arg3[i]);
				if (i != arg3.length - 1) {
					sb.append(" ");
				}
			}
			Player player = (Player)arg0;
			Location loc = player.getTargetBlock(null, 3).getLocation();
			TyokuchanCommandBlock tcb = CommandSensorBlock.tbManager.setCommand(loc, sb.toString());
			player.sendMessage("設定したかも。");
			player.sendMessage(tcb.toString());
			return true;
		}else{
			arg0.sendMessage("プレイヤー用");
			return true;
		}
	}

}
