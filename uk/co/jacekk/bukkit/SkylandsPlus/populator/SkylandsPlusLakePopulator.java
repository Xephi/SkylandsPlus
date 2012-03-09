package uk.co.jacekk.bukkit.SkylandsPlus.populator;

import java.util.Random;

import net.minecraft.server.WorldGenLakes;
import net.minecraft.server.WorldGenReed;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.generator.BlockPopulator;

public class SkylandsPlusLakePopulator extends BlockPopulator {
	
	private Random random;
	
	public SkylandsPlusLakePopulator(World world) {
		this.random = new Random(world.getSeed());
	}
	
	public void populate(World world, Random random, Chunk chunk){
		net.minecraft.server.World mcWorld = ((CraftWorld) world).getHandle();
		
		int x, y, z;
		
		int worldChunkX = chunk.getX() * 16;
		int worldChunkZ = chunk.getZ() * 16;
		
		if (this.random.nextInt(4) == 0){
			x = worldChunkX + this.random.nextInt(16) + 8;
			z = worldChunkZ + this.random.nextInt(16) + 8;
			
			y = world.getHighestBlockYAt(x, z) + 2;
			
			if (this.random.nextInt(100) < 85){
				(new WorldGenLakes(Material.STATIONARY_WATER.getId())).a(mcWorld, this.random, x, y, z);
				(new WorldGenReed()).a(mcWorld, this.random, x, y, z);
			}else{
				(new WorldGenLakes(Material.STATIONARY_LAVA.getId())).a(mcWorld, this.random, x, y, z);
			}
		}
	}

}
