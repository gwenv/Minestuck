package com.mraof.minestuck.block;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.mraof.minestuck.Minestuck;
import com.mraof.minestuck.client.gui.GuiHandler;
import com.mraof.minestuck.tileentity.TileEntityMachine;
import com.mraof.minestuck.tileentity.TileEntitySburbMachine;
import com.mraof.minestuck.util.IdentifierHandler;

public class BlockSburbMachine extends BlockContainer
{
	protected static final AxisAlignedBB CRUXTRUDER_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 15/16D, 1.0D);
	protected static final AxisAlignedBB[] PUNCH_DESIGNIX_AABB = {new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 5/8D), new AxisAlignedBB(3/8D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 3/8D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 5/8D, 1.0D, 1.0D)};
	protected static final AxisAlignedBB[] TOTEM_LATHE_AABB = {new AxisAlignedBB(0.0D, 0.0D, 5/16D, 1.0D, 1.0D, 11/16D), new AxisAlignedBB(5/16D, 0.0D, 0.0D, 11/16D, 1.0D, 1.0D)};
	protected static final AxisAlignedBB ALCHMITER_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1/2D, 1.0D);
	protected static final AxisAlignedBB[] ALCHEMITER_POLE_AABB = {new AxisAlignedBB(0.0D, 2/16D, 0.0D, 4.5/16D, 1.0D, 1/8D), new AxisAlignedBB(7/8D, 2/16D, 0.0D, 1.0D, 1.0D, 4.5/16D), new AxisAlignedBB(11.5/16D, 2/16D, 7/8D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 2/16D, 11.5/16D, 1/8D, 1.0D, 1.0D)};
	
	public static enum MachineType implements IStringSerializable
	{
		CRUXTRUDER("cruxtruder"),
		PUNCH_DESIGNIX("punchDesignix"),
		TOTEM_LATHE("totemLathe"),
		ALCHEMITER("alchemiter");
		
		private final String unlocalizedName;
		private MachineType(String name)
		{
			unlocalizedName = name;
		}
		
		public String getUnlocalizedName()
		{
			return unlocalizedName;
		}
		
		public String getName()
		{
			return name().toLowerCase();
		}
	}
	
	public static final PropertyEnum<MachineType> MACHINE_TYPE = PropertyEnum.create("machine_type", MachineType.class);
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	
	public BlockSburbMachine()
	{
		super(Material.rock);
		
		setUnlocalizedName("sburbMachine");
		setHardness(3.0F);
		setHarvestLevel("pickaxe", 0);
		setDefaultState(getDefaultState().withProperty(FACING, EnumFacing.SOUTH));
		this.setCreativeTab(Minestuck.tabMinestuck);

	}
	
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, MACHINE_TYPE, FACING);
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(MACHINE_TYPE).ordinal() + state.getValue(FACING).getHorizontalIndex()*4;
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(MACHINE_TYPE, MachineType.values()[meta%4]).withProperty(FACING, EnumFacing.getHorizontal(meta/4));
	}
	
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		return new ArrayList<ItemStack>();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List<ItemStack> subItems) 
	{
		for(int i = 0; i < 4; i++)
			subItems.add(new ItemStack(this, 1, i));
	}
	
	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if (!(tileEntity instanceof TileEntitySburbMachine) || playerIn.isSneaking())
			return false;
		
		if(!worldIn.isRemote)
		{
			if(state.getValue(MACHINE_TYPE) == MachineType.ALCHEMITER)
				((TileEntitySburbMachine) tileEntity).owner = IdentifierHandler.encode(playerIn);
			playerIn.openGui(Minestuck.instance, GuiHandler.GuiId.MACHINE.ordinal(), worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		TileEntityMachine te = (TileEntityMachine) worldIn.getTileEntity(pos);
		InventoryHelper.dropInventoryItems(worldIn, pos, te);
		
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
	{
		TileEntitySburbMachine te = (TileEntitySburbMachine) world.getTileEntity(pos);
		
		boolean b = super.removedByPlayer(state, world, pos, player, willHarvest);
		
		if(!world.isRemote && willHarvest && te != null)
		{
			ItemStack stack = new ItemStack(Item.getItemFromBlock(this), 1, state.getValue(MACHINE_TYPE).ordinal());
			if(state.getValue(MACHINE_TYPE) == MachineType.CRUXTRUDER && te.color != -1)
			{	//Moved this here because it's unnecessarily hard to check the tile entity in block.getDrops(), since it has been removed by then
				stack.setTagCompound(new NBTTagCompound());
				stack.getTagCompound().setInteger("color", te.color);
			}
			spawnAsEntity(world, pos, stack);
		}
		
		return b;
	}
	
	@Override
	public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return this.getStateFromMeta(meta).withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int metadata)
	{
		return new TileEntitySburbMachine();
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		EnumFacing rotation = (EnumFacing) state.getValue(FACING);
		MachineType type = (MachineType) state.getValue(MACHINE_TYPE);
		
		switch(type)
		{
		case CRUXTRUDER: return CRUXTRUDER_AABB;
		case PUNCH_DESIGNIX: return PUNCH_DESIGNIX_AABB[rotation.getHorizontalIndex()];
		case TOTEM_LATHE: return TOTEM_LATHE_AABB[rotation.getHorizontalIndex()%2];
		case ALCHEMITER: return ALCHMITER_AABB;
		default: return super.getBoundingBox(state, source, pos);
		}
	}
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB boundingBox, List<AxisAlignedBB> list, Entity entity)
	{
		super.addCollisionBoxToList(state, worldIn, pos, boundingBox, list, entity);
		if(state.getValue(MACHINE_TYPE).equals(MachineType.ALCHEMITER))
		{
			EnumFacing roation = (EnumFacing) getActualState(state, worldIn, pos).getValue(FACING);
			AxisAlignedBB bb = ALCHEMITER_POLE_AABB[roation.getHorizontalIndex()].offset(pos);
			if(boundingBox.intersectsWith(bb))
				list.add(bb);
		}
	}
	
}