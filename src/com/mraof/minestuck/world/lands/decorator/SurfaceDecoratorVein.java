package com.mraof.minestuck.world.lands.decorator;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;

import com.mraof.minestuck.world.gen.OreHandler;
import com.mraof.minestuck.world.lands.gen.ChunkProviderLands;

public class SurfaceDecoratorVein implements ILandDecorator
{
	
	public int amount;
	public IBlockState block;
	public int size;
	public float priority;
	
	public SurfaceDecoratorVein(IBlockState block, int amount, int size)
	{
		this(block, amount, size, 0.3F);
	}
	
	public SurfaceDecoratorVein(IBlockState block, int amount, int size, float priority)
	{
		this.amount = amount;
		this.block = block;
		this.size = size;
		this.priority = priority;
	}
	
	@Override
	public BlockPos generate(World world, Random random, int chunkX, int chunkZ, ChunkProviderLands provider)
	{
		int minY = 0;
		int maxY = 255;
		int diffBtwnMinMaxY = maxY - minY;
		for(int x = 0; x < amount; x++)
		{
			int posX = chunkX * 16 + random.nextInt(16);
			int posY = minY + random.nextInt(diffBtwnMinMaxY);
			int posZ = chunkZ * 16 + random.nextInt(16);
			(new WorldGenMinable(block, size, new OreHandler.BlockStatePredicate(provider.surfaceBlock, provider.upperBlock))).generate(world, random, new BlockPos(posX, posY, posZ));
		}
		return null;
	}
	
	@Override
	public float getPriority()
	{
		return priority;
	}
	
}
