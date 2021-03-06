package xyz.pixelatedw.mineminenomi.models.entities.projectiles;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HeartModel extends EntityModel
{
	public RendererModel heart1;
	public RendererModel heart2;
	public RendererModel heart3;
	public RendererModel heart4;
	public RendererModel heart5;
	public RendererModel heart6;
	public RendererModel heart7;
	public RendererModel heart8;
	public RendererModel heart9;
	public RendererModel heart10;
	public RendererModel heart11;
	public RendererModel heart12;
	public RendererModel heart13;
	public RendererModel heart14;
	public RendererModel heart15;
	public RendererModel heart16;
	public RendererModel heart17;
	public RendererModel heart18;
	public RendererModel pellicle;

	public HeartModel()
	{
		this.textureWidth = 64;
		this.textureHeight = 32;
		this.heart4 = new RendererModel(this, 0, 0);
		this.heart4.setRotationPoint(3.2F, 8.7F, 0.0F);
		this.heart4.addBox(-0.5F, 0.0F, -0.5F, 1, 5, 1, 0.0F);
		this.setRotateAngle(heart4, 0.0F, -0.0F, -2.495820830351891F);
		this.heart15 = new RendererModel(this, 10, 0);
		this.heart15.setRotationPoint(-4.4F, -8.0F, 0.0F);
		this.heart15.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(heart15, 0.0F, -0.0F, -1.0122909661567112F);
		this.heart9 = new RendererModel(this, 5, 0);
		this.heart9.setRotationPoint(-9.8F, -2.9F, 0.0F);
		this.heart9.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
		this.setRotateAngle(heart9, 0.0F, -0.0F, -2.897246558310587F);
		this.heart11 = new RendererModel(this, 10, 0);
		this.heart11.setRotationPoint(-8.9F, -6.5F, 0.0F);
		this.heart11.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(heart11, 0.0F, -0.0F, -2.321287905152458F);
		this.heart16 = new RendererModel(this, 10, 0);
		this.heart16.setRotationPoint(4.4F, -8.0F, 0.0F);
		this.heart16.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(heart16, 0.0F, -0.0F, 1.0122909661567112F);
		this.heart2 = new RendererModel(this, 0, 0);
		this.heart2.setRotationPoint(-0.3F, 12.0F, 0.0F);
		this.heart2.addBox(-0.5F, 0.0F, -0.5F, 1, 5, 1, 0.0F);
		this.setRotateAngle(heart2, 0.0F, -0.0F, -2.3387411976724017F);
		this.heart6 = new RendererModel(this, 0, 0);
		this.heart6.setRotationPoint(6.2F, 4.9F, 0.0F);
		this.heart6.addBox(-0.5F, 0.0F, -0.5F, 1, 5, 1, 0.0F);
		this.setRotateAngle(heart6, 0.0F, -0.0F, -2.652900463031381F);
		this.heart12 = new RendererModel(this, 10, 0);
		this.heart12.setRotationPoint(8.9F, -6.5F, 0.0F);
		this.heart12.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(heart12, 0.0F, -0.0F, 2.321287905152458F);
		this.heart7 = new RendererModel(this, 0, 0);
		this.heart7.setRotationPoint(-8.6F, 0.7F, 0.0F);
		this.heart7.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
		this.setRotateAngle(heart7, 0.0F, -0.0F, 2.8623399732707004F);
		this.heart5 = new RendererModel(this, 0, 0);
		this.heart5.setRotationPoint(-6.2F, 4.9F, 0.0F);
		this.heart5.addBox(-0.5F, 0.0F, -0.5F, 1, 5, 1, 0.0F);
		this.setRotateAngle(heart5, 0.0F, -0.0F, 2.652900463031381F);
		this.pellicle = new RendererModel(this, 15, 0);
		this.pellicle.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.pellicle.addBox(-9.5F, -9.0F, 0.0F, 19, 21, 0, 0.0F);
		this.heart13 = new RendererModel(this, 10, 0);
		this.heart13.mirror = true;
		this.heart13.setRotationPoint(-7.1F, -8.4F, 0.0F);
		this.heart13.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(heart13, 0.0F, -0.0F, -1.413716694115407F);
		this.heart18 = new RendererModel(this, 10, 0);
		this.heart18.setRotationPoint(2.0F, -6.5F, 0.0F);
		this.heart18.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(heart18, 0.0F, -0.0F, 0.8726646259971648F);
		this.heart8 = new RendererModel(this, 0, 0);
		this.heart8.setRotationPoint(8.6F, 0.7F, 0.0F);
		this.heart8.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
		this.setRotateAngle(heart8, 0.0F, -0.0F, -2.8623399732707004F);
		this.heart14 = new RendererModel(this, 10, 0);
		this.heart14.setRotationPoint(7.1F, -8.4F, 0.0F);
		this.heart14.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(heart14, 0.0F, -0.0F, 1.413716694115407F);
		this.heart1 = new RendererModel(this, 0, 0);
		this.heart1.mirror = true;
		this.heart1.setRotationPoint(0.3F, 12.0F, 0.0F);
		this.heart1.addBox(-0.5F, 0.0F, -0.5F, 1, 5, 1, 0.0F);
		this.setRotateAngle(heart1, 0.0F, -0.0F, 2.3387411976724017F);
		this.heart17 = new RendererModel(this, 10, 0);
		this.heart17.setRotationPoint(-2.0F, -6.5F, 0.0F);
		this.heart17.addBox(-0.5F, 0.0F, -0.5F, 1, 3, 1, 0.0F);
		this.setRotateAngle(heart17, 0.0F, -0.0F, -0.8726646259971648F);
		this.heart3 = new RendererModel(this, 0, 0);
		this.heart3.setRotationPoint(-3.2F, 8.7F, 0.0F);
		this.heart3.addBox(-0.5F, 0.0F, -0.5F, 1, 5, 1, 0.0F);
		this.setRotateAngle(heart3, 0.0F, -0.0F, 2.495820830351891F);
		this.heart10 = new RendererModel(this, 5, 0);
		this.heart10.setRotationPoint(9.8F, -2.9F, 0.0F);
		this.heart10.addBox(-0.5F, 0.0F, -0.5F, 1, 4, 1, 0.0F);
		this.setRotateAngle(heart10, 0.0F, -0.0F, 2.897246558310587F);
		this.pellicle.addChild(this.heart4);
		this.pellicle.addChild(this.heart15);
		this.pellicle.addChild(this.heart9);
		this.pellicle.addChild(this.heart11);
		this.pellicle.addChild(this.heart16);
		this.pellicle.addChild(this.heart2);
		this.pellicle.addChild(this.heart6);
		this.pellicle.addChild(this.heart12);
		this.pellicle.addChild(this.heart7);
		this.pellicle.addChild(this.heart5);
		this.pellicle.addChild(this.heart13);
		this.pellicle.addChild(this.heart18);
		this.pellicle.addChild(this.heart8);
		this.pellicle.addChild(this.heart14);
		this.pellicle.addChild(this.heart1);
		this.pellicle.addChild(this.heart17);
		this.pellicle.addChild(this.heart3);
		this.pellicle.addChild(this.heart10);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		GlStateManager.pushMatrix();
		GlStateManager.translated(this.pellicle.offsetX, this.pellicle.offsetY - 0.5, this.pellicle.offsetZ);
		GlStateManager.translated(this.pellicle.rotationPointX * f5, this.pellicle.rotationPointY * f5, this.pellicle.rotationPointZ * f5);
		GlStateManager.scaled(1.0D, 1.0D, 1.1D);
		GlStateManager.translated(-this.pellicle.offsetX, -this.pellicle.offsetY, -this.pellicle.offsetZ);
		GlStateManager.translated(-this.pellicle.rotationPointX * f5, -this.pellicle.rotationPointY * f5, -this.pellicle.rotationPointZ * f5);
		this.pellicle.render(f5);
		GlStateManager.popMatrix();
	}

	/**
	 * This is a helper function from Tabula to set the rotation of model parts
	 */
	public void setRotateAngle(RendererModel RendererModel, float x, float y, float z)
	{
		RendererModel.rotateAngleX = x;
		RendererModel.rotateAngleY = y;
		RendererModel.rotateAngleZ = z;
	}
}
