package uk.co.jacekk.bukkit.skylandsplus;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;
import uk.co.jacekk.bukkit.skylandsplus.listeners.MobSpawnListener;
import uk.co.jacekk.bukkit.skylandsplus.listeners.PhysicsListener;

public class SkylandsPlus extends JavaPlugin
{

    public void onEnable()
    {
        if(this.getConfig().getBoolean("prevent-sand-falling", true))
		    this.getServer().getPluginManager().registerEvents(new PhysicsListener(), this);

        if(this.getConfig().getBoolean("restrict-mob-spawning"))
            this.getServer().getPluginManager().registerEvents(new MobSpawnListener(), this);
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id)
    {
        if (id == null || id.isEmpty())
        {
            id = "offset=0";
        }

        return new uk.co.jacekk.bukkit.skylandsplus.generation.ChunkGenerator(id);
    }

}
