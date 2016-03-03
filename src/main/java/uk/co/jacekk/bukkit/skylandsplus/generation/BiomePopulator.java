package uk.co.jacekk.bukkit.skylandsplus.generation;


import net.minecraft.server.v1_9_R1.BiomeBase;
import net.minecraft.server.v1_9_R1.BlockPosition;
import net.minecraft.server.v1_9_R1.WorldServer;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_9_R1.CraftWorld;
import org.bukkit.generator.BlockPopulator;

import java.util.Random;

public class BiomePopulator extends BlockPopulator
{

    @Override
    public void populate(World world, Random random, Chunk chunk)
    {
        //Biome biome = world.getBiome(chunk.getX() * 16, chunk.getZ() * 16);

        //TODO: Some biomes are not being decorated.
        // Possibly solved by the approch below

        BiomeFixRandom biomefixrandom = new BiomeFixRandom(random);

        try
        {

            //ReflectionUtils.getFieldValue(BiomeBase.class, biome.name(), BiomeBase.class, null).a(((CraftWorld) world).getHandle(), biomefixrandom, new BlockPosition( chunk.getX() * 16, 0, chunk.getZ() * 16));

            // Go directly to the chunk's BiomeBase class
            // instead of converting it to a CraftBukkit enum and back
            WorldServer worldserver = ((CraftWorld) world).getHandle();
            BlockPosition p = new BlockPosition(chunk.getX() * 16, 0, chunk.getZ() * 16);
            BiomeBase biomebase = worldserver.getBiome(p);
            biomebase.a(worldserver, biomefixrandom, p);

            //}catch (NoSuchFieldException e){
            //	try{
            //		ReflectionUtils.getFieldValue(BiomeBase.class, Biome.FOREST.name(), BiomeBase.class, null).a(((CraftWorld) world).getHandle(), biomefixrandom, new BlockPosition( chunk.getX() * 16, 0, chunk.getZ() * 16));
            //	}catch (IllegalArgumentException le){
            //		System.err.println(le.getMessage());
            //	}catch (RuntimeException le){
            //		// Decorator was already called on this chunk :/
            //	}catch (NoSuchFieldException le){
            //		// This won't happen.
            //	}
        }
        catch (IllegalArgumentException e)
        {
            System.err.println(e.getMessage());
        }
        catch (RuntimeException e)
        {
            // Decorator was already called on this chunk :/
        }
    }

}
