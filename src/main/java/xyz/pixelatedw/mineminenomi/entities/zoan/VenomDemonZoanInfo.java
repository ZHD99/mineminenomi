package xyz.pixelatedw.mineminenomi.entities.zoan;

import xyz.pixelatedw.mineminenomi.abilities.doku.VenomDemonAbility;
import xyz.pixelatedw.mineminenomi.api.ZoanInfo;
import xyz.pixelatedw.mineminenomi.models.entities.zoans.VenomDemonModel;
import xyz.pixelatedw.mineminenomi.renderers.entities.ZoanMorphRenderer;
import xyz.pixelatedw.mineminenomi.renderers.entities.ZoanMorphRenderer.Factory;
import xyz.pixelatedw.wypi.abilities.Ability;

public class VenomDemonZoanInfo extends ZoanInfo
{
	public static final String FORM = "venom_demon";

	@Override
	public String getDevilFruit()
	{
		return "doku_doku";
	}

	@Override
	public String getForm()
	{
		return FORM;
	}

	@Override
	public Factory getFactory()
	{
		return new ZoanMorphRenderer.Factory(new VenomDemonModel(), "venomdemon", 1.1, new float[] { 0, 0.3f, 0 });
	}

	@Override
	public Ability getAbility()
	{
		return VenomDemonAbility.INSTANCE;
	}

	@Override
	public double getWidth()
	{
		return 1.5;
	}

	@Override
	public double getHeight()
	{
		return 3.0;
	}
	
	@Override
	public double[] getHeldItemOffset()
	{
		return new double[] {0, 0, 0};
	}

	@Override
	public double getHeldItemRotation()
	{
		return 0;
	}
}
