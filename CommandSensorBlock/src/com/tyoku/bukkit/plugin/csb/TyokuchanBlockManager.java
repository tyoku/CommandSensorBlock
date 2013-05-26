package com.tyoku.bukkit.plugin.csb;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

public class TyokuchanBlockManager {
	private TyokuchanBlockConfig tbConf;
	private ArrayList<TyokuchanCommandBlock> blocks;

	/**
	 * コンストラクタ
	 */
	public TyokuchanBlockManager() {
		tbConf = new TyokuchanBlockConfig();
		blocks = new ArrayList<TyokuchanCommandBlock>();
		loadBlocks();
	}

	/**
	 * コマンドをブロックに設定して設定ファイルにも反映
	 * @param location
	 * @param command
	 */
	public TyokuchanCommandBlock setCommand(Location location, String command){
		TyokuchanCommandBlock temp = new TyokuchanCommandBlock(location, command);

		for(TyokuchanCommandBlock tcBlock : blocks){
			if(tcBlock.isSameLocation(temp)){
				tcBlock.addCommand(command);
				saveCommand(tcBlock);
				return tcBlock;
			}
		}
		blocks.add(temp);
		saveCommand(temp);
		return temp;
	}

	public void setDistance(TyokuchanCommandBlock tcb){
		tbConf.getConfig().set(tcb.getLocateString()+".distance", tcb.getDistance());
		tbConf.save();
	}

	public void clearCommand(TyokuchanCommandBlock tcb){
		if(tcb == null){
			return;
		}
		clearCommand(tcb.getLocation());
	}

	public void clearCommand(Location location){
		if(location == null){
			return;
		}
		TyokuchanCommandBlock tmp = new TyokuchanCommandBlock(location, null);
		for(TyokuchanCommandBlock b : blocks){
			if(b.isSameLocation(location)){
				blocks.remove(b);
				break;
			}
		}
		tbConf.getConfig().set(tmp.getLocateString(), null);
		tbConf.save();
	}


	/**
	 * コマンドを設定ファイルに保管
	 * @param tbc
	 */
	private void saveCommand(TyokuchanCommandBlock tbc){
		tbConf.getConfig().set(tbc.getLocateString(), tbc.getConfCommands());
		tbConf.getConfig().set(tbc.getLocateString()+".commands", tbc.getConfCommands());
		tbConf.save();
	}

	/**
	 * 設定ファイルを読み込む
	 */
	public void loadBlocks(){

		for(String key : tbConf.getConfig().getKeys(false)){
			String[] zahyo = key.split("_");
			if(zahyo.length != 3){
				continue;
			}
			int x = 0;
			int y = 0;
			int z = 0;
			try {
				x = Integer.parseInt(zahyo[0]);
				y = Integer.parseInt(zahyo[1]);
				z = Integer.parseInt(zahyo[2]);
			} catch (Exception e) {
				continue;
			}
			TyokuchanCommandBlock tmp = new TyokuchanCommandBlock(x, y, z);
			tmp.setDistance(tbConf.getConfig().getInt(key+".distance"));
			List<String> cmds = tbConf.getConfig().getStringList(key+".commands");
			if(cmds == null || cmds.size() < 1){
				continue;
			}
			for(String cmd : cmds){
				tmp.addCommand(cmd);
			}
			blocks.add(tmp);
		}
	}

	/**
	 * 指定の座標に設定しているブロックを取得する。
	 * @param location
	 * @return
	 */
	public TyokuchanCommandBlock getTyokuchanBlock(Location location) {
		for(TyokuchanCommandBlock tcb : blocks){
			if(tcb.isSameLocation(location)){
				return tcb;
			}
		}
		return null;
	}

	public String[] getLocationList(){
		String[] locations = new String[blocks.size()];
		int i = 0;
		for(TyokuchanCommandBlock tcb : blocks){
			locations[i++] = tcb.getLocateString();
		}
		return locations;
	}

}
