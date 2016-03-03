package uk.co.jacekk.bukkit.skylandsplus.generation;

import java.util.Random;

/***
 * This is a temporary work around to solve a problem with how Biome decoration works
 * in newer versions of Minecraft.  This is not pretty and there may be a better approach.
 * Still this allows SkylandsPlus to work on newer versions of Minecraft.
 * <p/>
 * Issue:
 * When populating a SkylandsPlus chunk the Minecraft built in Biome generation classes will throw
 * IllegalArgumentException "n must be positive"
 * followed by
 * RuntimeException "Already decorating"
 * for all subsequent decoration calls.
 * <p/>
 * Details:
 * The problem was introduced when BiomeDecorator and related classes made use of the
 * highest block position as part of generating the next random number.
 * <p/>
 * Some offending code:
 * this.b.nextInt(this.a.getHighestBlockYAt(this.c.a(j, 0, k)).getY() * 2);
 * Note: b is the Random Class instance, a is the World class instance
 * <p/>
 * With SkylandsPlus it is possible for any given X,Z position to have no blocks along the Y axis.
 * World.getHighestBlockYAt(x, z).getY() will return a 0
 * The Random.nextInt(i) class must be passed a number > 0 or you get an exception.
 * <p/>
 * The first time the build in Biome decorator hits a SkylandsPlus Chunk
 * with an entire Y column of AIR, it breaks.  Then it stays broken.
 * <p/>
 * The following class overrides the standard random base class.
 * It intercepts any calls to nextInt with 0 or less and forces nextInt(1) to make things work.
 * <p/>
 * The SkylandsPlus BiomePopluator only needs to wrapper the Random object
 * parameter with this class, before calling the built in Biome methods.
 ***/

public class BiomeFixRandom extends Random
{

    // To keep our seed position consistent we want to wrapper an existing Random class instance.
    private Random r = null;

    public BiomeFixRandom(Random o)
    {
        r = o;
    }

    // Now intercept all the Random methods and direct them to the wrapper class.
    @Override
    public boolean nextBoolean()
    {
        return r.nextBoolean();
    }

    @Override
    public void nextBytes(byte[] bytes)
    {
        r.nextBytes(bytes);
        return;
    }

    @Override
    public double nextDouble()
    {
        return r.nextDouble();
    }

    @Override
    public float nextFloat()
    {
        return r.nextFloat();
    }

    @Override
    public double nextGaussian()
    {
        return r.nextGaussian();
    }

    @Override
    public int nextInt()
    {
        return r.nextInt();
    }

    @Override
    public int nextInt(int i)
    {
        // Here is the temporary fix / hack to allow us to use the built in Biome generation.
        // The following forces a positive value bypassing the exception.
        // This allows the Biome decoration logic to continue on a SkylandsPlus chunk.
        if (i > 0) return r.nextInt(i);
        return r.nextInt(1);
    }

    @Override
    public long nextLong()
    {
        return r.nextLong();
    }

    @Override
    public void setSeed(long seed)
    {
        if (r == null) return; //Ignore the base class initialization
        r.setSeed(seed);
        return;
    }
}
