package com.mraof.minestuck.item;

import net.minecraft.item.ItemAxe;

public class ItemMinestuckAxe extends ItemAxe
{
	public ItemMinestuckAxe(ToolMaterial material, float damage, float speed)
	{
		super(ToolMaterial.IRON);
		this.toolMaterial = material;
		this.damageVsEntity = damage;
		this.attackSpeed = speed;
		this.setMaxDamage(material.getMaxUses());
		this.efficiencyOnProperMaterial = material.getEfficiencyOnProperMaterial();
	}
}