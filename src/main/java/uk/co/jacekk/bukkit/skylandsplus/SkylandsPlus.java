package uk.co.jacekk.bukkit.skylandsplus;

import java.io.File;

import org.bukkit.generator.ChunkGenerator;

import uk.co.jacekk.bukkit.baseplugin.BasePlugin;
import uk.co.jacekk.bukkit.baseplugin.config.PluginConfig;
import uk.co.jacekk.bukkit.skylandsplus.listeners.MobSpawnListener;
import uk.co.jacekk.bukkit.skylandsplus.listeners.PhysicsListener;

public class SkylandsPlus extends BasePlugin {
	
	public void onEnable() {
		super.onEnable(true);
		
		this.config = new PluginConfig(new File(this.baseDirPath + File.separator + "config.yml"), Config.class, this.log);
		
		if (this.config.getBoolean(Config.PREVENT_SAND_FALLING)) {
			this.getServer().getPluginManager().registerEvents(new PhysicsListener(this), this);
		}
		
		if (this.config.getBoolean(Config.RESTRICT_MOB_SPAWNING)) {
		    this.getServer().getPluginManager().registerEvents(new MobSpawnListener(this), this);
		}
	}
	
	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		if(id == null || id.isEmpty()){
			id = "offset=0";
		}
		
		return new uk.co.jacekk.bukkit.skylandsplus.generation.ChunkGenerator(id);
	}
	
}