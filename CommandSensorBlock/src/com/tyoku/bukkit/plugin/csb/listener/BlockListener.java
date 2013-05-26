package com.tyoku.bukkit.plugin.csb.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;

import com.tyoku.bukkit.plugin.csb.CommandSensorBlock;
import com.tyoku.bukkit.plugin.csb.TyokuchanCommandBlock;

public class BlockListener implements Listener {

	@EventHandler
	public void onBlockRedstone(BlockRedstoneEvent event){
		if(event.getNewCurrent() == 15){
			
			TyokuchanCommandBlock tcb = CommandSensorBlock.tbManager.getTyokuchanBlock(event.getBlock().getLocation());
			if(tcb != null){
				tcb.doCommand();
			}
		}
	}
}
