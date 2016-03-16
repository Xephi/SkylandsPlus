package uk.co.jacekk.bukkit.skylandsplus.generation;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.craftbukkit.v1_9_R1.CraftWorld;
import org.bukkit.generator.BlockPopulator;

import net.minecraft.server.v1_9_R1.BiomeBase;
import net.minecraft.server.v1_9_R1.BlockPosition;
import uk.co.jacekk.bukkit.baseplugin.util.ReflectionUtils;

public class BiomePopulator extends BlockPopulator {
	
	@Override
	public void populate(World world, Random random, Chunk chunk) {
		Biome biome = world.getBiome(chunk.getX() * 16, chunk.getZ() * 16);
		BlockPosition pos = new BlockPosition(chunk.getX() * 16, 0, chunk.getZ() * 16);
		
		//TODO: Some biomes are not being decorated.
		
		try{
			ReflectionUtils.getFieldValue(BiomeBase.class, biome.name(), BiomeBase.class, null).a(((CraftWorld) world).getHandle(), random, pos);
		}catch (NoSuchFieldException e){
			try{
				ReflectionUtils.getFieldValue(BiomeBase.class, Biome.FOREST.name(), BiomeBase.class, null).a(((CraftWorld) world).getHandle(), random, pos);
			}catch (IllegalArgumentException le){
				System.err.println(le.getMessage());
			}catch (RuntimeException le){
				// Decorator was already called on this chunk :/
			}catch (NoSuchFieldException le){
				// This won't happen.
			}
		}catch (IllegalArgumentException e){
			System.err.println(e.getMessage());
		}catch (RuntimeException e){
			// Decorator was already called on this chunk :/
		}
	}
	
}