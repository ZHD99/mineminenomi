package xyz.pixelatedw.mineminenomi.models.entities.zoans;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;
import xyz.pixelatedw.mineminenomi.api.ZoanMorphModel;

public class BisonWalkModel<T extends LivingEntity> extends ZoanMorphModel<T>
{
	public RendererModel head1;
	public RendererModel head2;
	public RendererModel body1;
	public RendererModel body2;
	public RendererModel body3;
	public RendererModel rightleg2;
	public RendererModel leftleg2;
	public RendererModel rearleftleg1;
	public RendererModel rearrightleg1;
	public RendererModel righthorn1;
	public RendererModel righthorn2;
	public RendererModel lefthorn1;
	public RendererModel lefthorn2;
	public RendererModel tail;
	public RendererModel rightleg1;
	public RendererModel righthull2;
	public RendererModel righthull1;
	public RendererModel leftleg1;
	public RendererModel lefthull1;
	public RendererModel lefthull2;
	public RendererModel rearlefthull1;
	public RendererModel rearlefthull2;
	public RendererModel rearrighthull1;
	public RendererModel rearrighthull2;

	public BisonWalkModel()
	{
		this.textureWidth = 128;
		this.textureHeight = 64;
		this.rearrighthull2 = new RendererModel(this, 0, 52);
		this.rearrighthull2.setRotationPoint(0.0F, 9.1F, -0.5F);
		this.rearrighthull2.addBox(-0.5F, 0.0F, -1.0F, 1, 1, 1, 0.0F);
		this.setRotateAngle(rearrighthull2, -0.12112585008840647F, -0.4883431247080134F, -0.03944444109507184F);
		this.righthull1 = new RendererModel(this, 0, 55);
		this.righthull1.setRotationPoint(0.0F, 3.1F, -0.5F);
		this.righthull1.addBox(-0.5F, 0.0F, -1.0F, 1, 1, 1, 0.0F);
		this.setRotateAngle(righthull1, -0.12112585008840647F, 0.4883431247080134F, 0.03944444109507184F);
		this.lefthorn2 = new RendererModel(this, 122, 0);
		this.lefthorn2.setRotationPoint(2.0999999046325684F, 6.099999904632568F, -5.0F);
		this.lefthorn2.addBox(-2.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
		this.setRotateAngle(lefthorn2, 0.0F, -0.0F, -1.9024088382720947F);
		this.head1 = new RendererModel(this, 13, 29);
		this.head1.setRotationPoint(-2.5F, 8.0F, -8.5F);
		this.head1.addBox(0.0F, 0.0F, 0.0F, 5, 5, 5, 0.0F);
		this.setRotateAngle(head1, 0.08726646006107329F, -0.0F, 0.0F);
		this.righthull2 = new RendererModel(this, 0, 55);
		this.righthull2.setRotationPoint(0.0F, 3.1F, -0.5F);
		this.righthull2.addBox(-0.5F, 0.0F, -1.0F, 1, 1, 1, 0.0F);
		this.setRotateAngle(righthull2, -0.12112585008840647F, -0.4883431247080134F, -0.03944444109507184F);
		this.body3 = new RendererModel(this, 76, 0);
		this.body3.setRotationPoint(-4.0F, 7.0F, 7.0F);
		this.body3.addBox(0.0F, 0.0F, 0.0F, 8, 7, 8, 0.0F);
		this.leftleg1 = new RendererModel(this, 0, 22);
		this.leftleg1.setRotationPoint(0.0F, 6.0F, 0.0F);
		this.leftleg1.addBox(-1.0F, 0.0F, -1.0F, 2, 4, 2, 0.0F);
		this.head2 = new RendererModel(this, 13, 40);
		this.head2.setRotationPoint(-2.0F, 10.0F, -11.0F);
		this.head2.addBox(0.0F, 0.0F, 0.0F, 4, 3, 3, 0.0F);
		this.setRotateAngle(head2, 0.08726646006107329F, -0.0F, 0.0F);
		this.righthorn2 = new RendererModel(this, 122, 0);
		this.righthorn2.setRotationPoint(-2.0999999046325684F, 6.099999904632568F, -5.0F);
		this.righthorn2.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
		this.setRotateAngle(righthorn2, 0.0F, -0.0F, 1.9024088382720947F);
		this.tail = new RendererModel(this, 110, 0);
		this.tail.setRotationPoint(0.0F, 10.0F, 14.5F);
		this.tail.addBox(-0.5F, 0.0F, 0.0F, 1, 4, 1, 0.0F);
		this.setRotateAngle(tail, 0.3490658503988659F, -0.0F, 0.0F);
		this.lefthull2 = new RendererModel(this, 0, 55);
		this.lefthull2.setRotationPoint(0.0F, 3.1F, -0.5F);
		this.lefthull2.addBox(-0.5F, 0.0F, -1.0F, 1, 1, 1, 0.0F);
		this.setRotateAngle(lefthull2, -0.12112585008840647F, -0.4883431247080134F, -0.03944444109507184F);
		this.righthorn1 = new RendererModel(this, 115, 0);
		this.righthorn1.setRotationPoint(-3.0F, 7.0F, -5.0F);
		this.righthorn1.addBox(0.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
		this.setRotateAngle(righthorn1, 0.0F, -0.0F, 0.7330383062362671F);
		this.rearleftleg1 = new RendererModel(this, 0, 39);
		this.rearleftleg1.setRotationPoint(3.0F, 14.0F, 14.0F);
		this.rearleftleg1.addBox(-1.0F, 0.0F, -1.0F, 2, 10, 2, 0.0F);
		this.rearrighthull1 = new RendererModel(this, 0, 52);
		this.rearrighthull1.setRotationPoint(0.0F, 9.1F, -0.5F);
		this.rearrighthull1.addBox(-0.5F, 0.0F, -1.0F, 1, 1, 1, 0.0F);
		this.setRotateAngle(rearrighthull1, -0.12112585008840647F, 0.4883431247080134F, 0.03944444109507184F);
		this.rearrightleg1 = new RendererModel(this, 0, 39);
		this.rearrightleg1.setRotationPoint(-3.0F, 14.0F, 14.0F);
		this.rearrightleg1.addBox(-1.0F, 0.0F, -1.0F, 2, 10, 2, 0.0F);
		this.rearlefthull2 = new RendererModel(this, 0, 52);
		this.rearlefthull2.setRotationPoint(0.0F, 9.1F, -0.5F);
		this.rearlefthull2.addBox(-0.5F, 0.0F, -1.0F, 1, 1, 1, 0.0F);
		this.setRotateAngle(rearlefthull2, -0.12112585008840647F, 0.4883431247080134F, 0.03944444109507184F);
		this.body1 = new RendererModel(this, 0, 0);
		this.body1.setRotationPoint(-4.5F, 7.0F, -3.5F);
		this.body1.addBox(0.0F, 0.0F, 0.0F, 9, 7, 11, 0.0F);
		this.body2 = new RendererModel(this, 41, 0);
		this.body2.setRotationPoint(-4.0F, 2.0F, 0.0F);
		this.body2.addBox(0.0F, 0.0F, 0.0F, 8, 6, 9, 0.0F);
		this.setRotateAngle(body2, -0.593411922454834F, -0.0F, 0.0F);
		this.lefthull1 = new RendererModel(this, 0, 55);
		this.lefthull1.setRotationPoint(0.0F, 3.1F, -0.5F);
		this.lefthull1.addBox(-0.5F, 0.0F, -1.0F, 1, 1, 1, 0.0F);
		this.setRotateAngle(lefthull1, -0.12112585008840647F, 0.4883431247080134F, 0.03944444109507184F);
		this.rearlefthull1 = new RendererModel(this, 0, 52);
		this.rearlefthull1.setRotationPoint(0.0F, 9.1F, -0.5F);
		this.rearlefthull1.addBox(-0.5F, 0.0F, -1.0F, 1, 1, 1, 0.0F);
		this.setRotateAngle(rearlefthull1, -0.12112585008840647F, -0.4883431247080134F, -0.03944444109507184F);
		this.leftleg2 = new RendererModel(this, 0, 29);
		this.leftleg2.setRotationPoint(3.0F, 14.0F, 0.0F);
		this.leftleg2.addBox(-1.5F, 0.0F, -2.0F, 3, 6, 3, 0.0F);
		this.rightleg1 = new RendererModel(this, 0, 22);
		this.rightleg1.setRotationPoint(0.0F, 6.0F, 0.0F);
		this.rightleg1.addBox(-1.0F, 0.0F, -1.0F, 2, 4, 2, 0.0F);
		this.rightleg2 = new RendererModel(this, 0, 29);
		this.rightleg2.setRotationPoint(-3.0F, 14.0F, 0.0F);
		this.rightleg2.addBox(-1.5F, 0.0F, -2.0F, 3, 6, 3, 0.0F);
		this.lefthorn1 = new RendererModel(this, 115, 0);
		this.lefthorn1.setRotationPoint(3.0F, 7.0F, -5.0F);
		this.lefthorn1.addBox(-2.0F, 0.0F, 0.0F, 2, 1, 1, 0.0F);
		this.setRotateAngle(lefthorn1, 0.0F, -0.0F, -0.7330383062362671F);
		this.rearrightleg1.addChild(this.rearrighthull2);
		this.rightleg1.addChild(this.righthull1);
		this.rightleg1.addChild(this.righthull2);
		this.leftleg2.addChild(this.leftleg1);
		this.leftleg1.addChild(this.lefthull2);
		this.rearrightleg1.addChild(this.rearrighthull1);
		this.rearleftleg1.addChild(this.rearlefthull2);
		this.leftleg1.addChild(this.lefthull1);
		this.rearleftleg1.addChild(this.rearlefthull1);
		this.rightleg2.addChild(this.rightleg1);
	}

	@Override
	public void render(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		this.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

		GlStateManager.translatef(0.0F, 0.0F, -0.3F);

		this.righthorn2.render(scale);
		this.rearrightleg1.render(scale);
		this.lefthorn1.render(scale);
		this.head1.render(scale);
		this.lefthorn2.render(scale);
		this.body2.render(scale);
		this.rightleg2.render(scale);
		this.righthorn1.render(scale);
		this.head2.render(scale);
		this.body1.render(scale);
		this.tail.render(scale);
		this.leftleg2.render(scale);
		this.rearleftleg1.render(scale);
		this.body3.render(scale);
	}

	@Override
	public void setRotationAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	{
		super.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
		
		// Hanldes the legs and tail movement
		this.leftleg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 0.3F * limbSwingAmount;
		this.rightleg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 0.3F * limbSwingAmount;
		this.rearrightleg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 0.4F * limbSwingAmount;
		this.rearleftleg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 0.4F * limbSwingAmount;
		if(entity.isSprinting())
			this.tail.rotateAngleX = 1.2F + MathHelper.cos(limbSwing * 0.6662F) * 0.2F * limbSwingAmount;
	}

	public void setRotateAngle(RendererModel RendererModel, float x, float y, float z)
	{
		RendererModel.rotateAngleX = x;
		RendererModel.rotateAngleY = y;
		RendererModel.rotateAngleZ = z;
	}

	@Override
	public RendererModel getHandRenderer()
	{
		return null;
	}

	@Override
	public RendererModel getArmRenderer()
	{
		return null;
	}
}