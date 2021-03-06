package com.mraof.minestuck.world.lands.decorator;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenTrees;

public class BasicTreeDecorator extends TreeDecoratorBase
{
	WorldGenTrees[] treeTypes;
	
	public BasicTreeDecorator(IBlockState[] trees, IBlockState[] leaves)
	{
		this.treeTypes = new WorldGenTrees[trees.length];
		for(int i = 0; i < trees.length; i++)
			treeTypes[i] = new WorldGenTrees(false, 5, trees[i], leaves[i], false);
	}
	
	public BasicTreeDecorator(IBlockState treeType, IBlockState leafType)
	{
		this(new IBlockState[] {treeType}, new IBlockState[] {leafType});
	}
	
	public BasicTreeDecorator()
	{
		this(Blocks.log.getDefaultState(), Blocks.leaves.getDefaultState());
	}
	
	@Override
	protected int getTreesPerChunk(Random rand)
	{
		return rand.nextInt(5) + 5;
	}
	
	@Override
	protected WorldGenAbstractTree getTreeToGenerate(World world, BlockPos pos, Random rand)
	{
		return this.treeTypes[rand.nextInt(treeTypes.length)];
	}
}