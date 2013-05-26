package com.tyoku.bukkit.plugin.csb;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TyokuchanCommandBlock {
	private String lastUser;
	private int locX;
	private int locY;
	private int locZ;
	private int distance = 0;
	public int getDistance() {
		return distance;
	}

	public void setDistance(int range) {
		this.distance = range;
	}

	private ArrayList<String> commandList;

	public TyokuchanCommandBlock(int x, int y, int z) {
		locX = x;
		locY = y;
		locZ = z;
		commandList = new ArrayList<String>();
	}

	public TyokuchanCommandBlock(int x, int y, int z, String command) {
		locX = x;
		locY = y;
		locZ = z;
		commandList = new ArrayList<String>();
		if(command != null){
			commandList.add(command);
		}
	}

	public TyokuchanCommandBlock(Location loc, String command) {
		locX = loc.getBlockX();
		locY = loc.getBlockY();
		locZ = loc.getBlockZ();
		commandList = new ArrayList<String>();
		if(command != null){
			commandList.add(command);
		}
	}

	public boolean isSameLocation(Location otherLocation){
		return (locX == otherLocation.getBlockX()
				&& locY == otherLocation.getBlockY()
				&& locZ == otherLocation.getBlockZ());
	}
	public boolean isSameLocation(TyokuchanCommandBlock otherBlock){
		return (locX == otherBlock.locX
				&& locY == otherBlock.locY
				&& locZ == otherBlock.locZ);
	}

	public void doCommand(){
		if(!CommandSensorBlock.commandable){
			return;
		}
		doCommand(Bukkit.getConsoleSender());
	}

	@SuppressWarnings("deprecation")
	public void doCommand(CommandSender cs){
		if(!CommandSensorBlock.commandable){
			return;
		}
		lastUser = cs.getName();
		if(commandList == null) return;


		if(hasDistance()){
			for(Player p: Bukkit.getOnlinePlayers()){
				if(!isActiveZone(p)){
					continue;
				}
				for (String cmd : commandList) {
					//CommandSensorBlock.logger.info(String.format("[EXEC(%s)]%s",lastUser,cmd));
					Bukkit.getServer().dispatchCommand(cs, cmd.replaceAll("@p", p.getName()));
					p.updateInventory();
				}
			}
		}else{
			for (String cmd : commandList) {
				//CommandSensorBlock.logger.info(String.format("[EXEC(%s)]%s",lastUser,cmd));
				Bukkit.getServer().dispatchCommand(cs, cmd);
			}
		}
	}

	public void addCommand(String command){
		commandList.add(command);
	}

	public String getLastUser() {
		return lastUser;
	}

	public String getLocateString(){
		return String.format("%d_%d_%d", locX, locY, locZ);
	}
	public String[] getConfCommands(){
		String[] commands = new String[commandList.size()];
		int i = 0;
		for(String cmd : commandList){
			commands[i++] = cmd;
		}
		return commands;
	}

	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append(String.format("----------[%d, %d, %d]-----------\n", locX,locY,locZ));
		if(hasDistance()){
			sb.append("有効範囲："+distance+"\n");
		}
		for(String cmd : commandList){
			sb.append(cmd+"\n");
		}
		sb.append("---------------------------------");
		return sb.toString();
	}

	public Location getLocation(){
		return new Location(Bukkit.getServer().getWorlds().get(0), locX, locY, locZ);
	}

	public boolean isActiveZone(Player player){
		if(distance == 0){
			return false;
		}else if(distance < 0){
			return true;
		}
		int toLocX = player.getLocation().getBlockX();
		int toLocY = player.getLocation().getBlockY();
		int toLocZ = player.getLocation().getBlockZ();
		int targetDistance = (int)Math.floor(Math.sqrt( Math.pow(locX-toLocX, 2) + Math.pow(locY-toLocY, 2) + Math.pow(locZ-toLocZ, 2) ));
		CommandSensorBlock.logger.info("距離("+player.getName()+"):"+targetDistance);
		return distance >= targetDistance;
	}

	public boolean hasDistance(){
		CommandSensorBlock.logger.info("設定距離(:"+distance);
		return distance != 0;
	}
}
