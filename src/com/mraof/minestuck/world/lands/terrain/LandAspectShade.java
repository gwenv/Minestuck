package com.mraof.minestuck.world.lands.terrain;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.Vec3d;

import com.mraof.minestuck.block.BlockColoredDirt;
import com.mraof.minestuck.block.MinestuckBlocks;
import com.mraof.minestuck.world.lands.decorator.ILandDecorator;
import com.mraof.minestuck.world.lands.decorator.SurfaceMushroomGenerator;
import com.mraof.minestuck.world.lands.decorator.UndergroundDecoratorVein;

public class LandAspectShade extends TerrainLandAspect 
{
	
	static Vec3d skyColor = new Vec3d(0.16D, 0.38D, 0.54D);
	
	public LandAspectShade()
	{
		/*List<WeightedRandomChestContent> list = new ArrayList<WeightedRandomChestContent>();
		list.add(new WeightedRandomChestContent(new ItemStack(Blocks.red_mushroom, 1, 0), 1, 7, 6));
		list.add(new WeightedRandomChestContent(new ItemStack(Blocks.red_mushroom, 1, 0), 1, 4, 3));
		list.add(new WeightedRandomChestContent(new ItemStack(Blocks.brown_mushroom, 1, 0), 1, 4, 3));
		list.add(new WeightedRandomChestContent(new ItemStack(MinestuckBlocks.coloredDirt, 1, 0), 4, 15, 5));
		list.add(new WeightedRandomChestContent(new ItemStack(Items.mushroom_stew, 1, 0), 1, 1, 4));
		list.add(new WeightedRandomChestContent(new ItemStack(MinestuckItems.minestuckBucket, 1, 0), 1, 1, 2));
		
		lootMap.put(AlchemyRecipeHandler.BASIC_MEDIUM_CHEST, list);*/
	}
	
	@Override
	public IBlockState getUpperBlock()
	{
		return MinestuckBlocks.coloredDirt.getDefaultState().withProperty(BlockColoredDirt.BLOCK_TYPE, BlockColoredDirt.BlockType.BLUE);
	}
	
	@Override
	public IBlockState[] getStructureBlocks()
	{
		return new IBlockState[] {Blocks.stone.getDefaultState(), Blocks.stonebrick.getDefaultState()};
	}
	
	@Override
	public IBlockState getDecorativeBlockFor(IBlockState state)
	{
		if(state.getBlock() == Blocks.stonebrick)
			return Blocks.stonebrick.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED);
		else return state;
	}
	
	@Override
	public IBlockState getOceanBlock() 
	{
		return MinestuckBlocks.blockOil.getDefaultState();
	}
	
	@Override
	public String getPrimaryName()
	{
		return "shade";
	}

	@Override
	public String[] getNames() {
		return new String[] {"shade"};
	}
	
	@Override
	public List<ILandDecorator> getDecorators()
	{
		ArrayList<ILandDecorator> list = new ArrayList<ILandDecorator>();
		list.add(new SurfaceMushroomGenerator());
		
		list.add(new UndergroundDecoratorVein(Blocks.gravel.getDefaultState(), 8, 33, 256));
		list.add(new UndergroundDecoratorVein(Blocks.iron_ore.getDefaultState(), 24, 9, 64));
		list.add(new UndergroundDecoratorVein(Blocks.lapis_ore.getDefaultState(), 6, 7, 35));
		
		return list;
	}

	@Override
	public int getDayCycleMode() {
		return 2;
	}

	@Override
	public Vec3d getFogColor() 
	{
		return skyColor;
	}
	
	@Override
	public int getWeatherType()
	{
		return 0;
	}
	
}
