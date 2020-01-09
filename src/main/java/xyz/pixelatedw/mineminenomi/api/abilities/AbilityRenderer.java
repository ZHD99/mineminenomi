package xyz.pixelatedw.mineminenomi.api.abilities;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import xyz.pixelatedw.mineminenomi.Env;

@OnlyIn(Dist.CLIENT)
public class AbilityRenderer extends EntityRenderer<AbilityProjectile>
{
	private double scale = 1, red, blue, green, alpha;
	private EntityModel model;
	private ResourceLocation texture;

	public AbilityRenderer(EntityRendererManager renderManager, EntityModel model)
	{
		super(renderManager);
		this.model = model;
	}
	
	public void setTexture(ResourceLocation res)
	{
		this.texture = res;
	}
	
	public void setColor(double red, double green, double blue, double alpha)
	{
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}

	public void setScale(double scale)
	{
		this.scale = scale;
	}

	@Override
	public void doRender(AbilityProjectile entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
		GlStateManager.pushMatrix();
		{
			System.out.println(this.red);
			GlStateManager.color4f(1, 1, 1, 1);
			GlStateManager.translated(x, y + 0.25, z);
			if (this.texture == null)
				GlStateManager.disableTexture();
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);

			GlStateManager.rotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 180.0F, 0.0F, 1.0F, 0.0F);
			GlStateManager.rotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 1.0F, 0.0F, 0.0F);

			GlStateManager.rotatef(180, 0, 0, 1);

			GlStateManager.color4f((float) this.red, (float) this.green, (float) this.blue, (float) this.alpha);
			GlStateManager.scaled(this.scale, this.scale, this.scale);

			if (this.texture != null)
				Minecraft.getInstance().textureManager.bindTexture(this.getEntityTexture(entity));

			if (this.model != null)
				this.model.render(entity, (float) x, (float) y, (float) z, 0.0F, 0.0F, 0.0625F);

			GlStateManager.disableBlend();
			if (this.texture == null)
				GlStateManager.enableTexture();
		}	
		GlStateManager.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(AbilityProjectile entity)
	{
		return this.texture;
	}

	public static class Factory implements IRenderFactory<AbilityProjectile>
	{
		private EntityModel model = new CubeModel();
		private double scale = 1, red = 1, green = 1, blue = 1, alpha = 1;
		private ResourceLocation texture;

		public Factory(EntityModel model)
		{
			this.model = model;
		}

		public Factory setTexture(String textureName)
		{
			this.texture = new ResourceLocation(Env.PROJECT_ID, "textures/models/projectiles/" + textureName + ".png");
			return this;
		}

		public Factory setColor(double red, double green, double blue, double alpha)
		{
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.alpha = alpha;
			return this;
		}

		public Factory setScale(double scale)
		{
			this.scale = scale;
			return this;
		}

		@Override
		public EntityRenderer<? super AbilityProjectile> createRenderFor(EntityRendererManager manager)
		{
			AbilityRenderer renderer = new AbilityRenderer(manager, this.model);
			renderer.setTexture(this.texture);
			renderer.setScale(this.scale);
			renderer.setColor(this.red, this.green, this.blue, this.alpha);
			return renderer;
		}
	}

}
