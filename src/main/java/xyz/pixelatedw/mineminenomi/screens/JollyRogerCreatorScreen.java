package xyz.pixelatedw.mineminenomi.screens;

import java.awt.Color;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.config.GuiUtils;
import xyz.pixelatedw.mineminenomi.api.jollyroger.JollyRogerElement;
import xyz.pixelatedw.mineminenomi.api.jollyroger.JollyRogerElement.LayerType;
import xyz.pixelatedw.mineminenomi.data.entity.jollyroger.IJollyRoger;
import xyz.pixelatedw.mineminenomi.data.entity.jollyroger.JollyRogerCapability;
import xyz.pixelatedw.mineminenomi.init.ModJollyRogers;
import xyz.pixelatedw.mineminenomi.init.ModResources;
import xyz.pixelatedw.mineminenomi.packets.client.CJollyRogerSyncPacket;
import xyz.pixelatedw.mineminenomi.screens.extra.NoTextureButton;
import xyz.pixelatedw.mineminenomi.screens.extra.TexturedIconButton;
import xyz.pixelatedw.wypi.WyHelper;
import xyz.pixelatedw.wypi.network.WyNetwork;

@OnlyIn(Dist.CLIENT)
public class JollyRogerCreatorScreen extends Screen
{
	private PlayerEntity player;
	private Widget selectedButton;
	//private JollyRogerElement selectedElement;
	private LayerType layerType = LayerType.BASE;
	private IJollyRoger props;

	private int layerIndex;
	private int trueIndex;
	private Collection<RegistryObject<JollyRogerElement>> allElements;
	private List<RegistryObject<JollyRogerElement>> allBases;
	private List<RegistryObject<JollyRogerElement>> allBackgrounds;
	private List<RegistryObject<JollyRogerElement>> allDetails;

	public JollyRogerCreatorScreen()
	{
		super(new StringTextComponent(""));
		this.player = Minecraft.getInstance().player;
		this.props = JollyRogerCapability.get(this.player);

		this.allElements = ModJollyRogers.JOLLY_ROGER_ELEMENTS.getEntries();
		this.allBases = this.getTotalElementsForType(this.player, LayerType.BASE);
		this.allBackgrounds = this.getTotalElementsForType(this.player, LayerType.BACKGROUND);
		this.allDetails = this.getTotalElementsForType(this.player, LayerType.DETAIL);	
	}

	@Override
	public void render(int x, int y, float f)
	{
		this.renderBackground();

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		int posX = this.width / 2;
		int posY = this.height / 2;

		GlStateManager.enableBlend();
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);

		GL11.glPushMatrix();
		{
			double scale = 0.5;
			GL11.glTranslated(posX - 100, posY - 130, 1);
			GL11.glTranslated(128, 128, 0);
			GL11.glScaled(scale, scale, scale);
			GL11.glTranslated(-128, -128, 0);

			// Draw the black flag background
			//this.fillGradient(-10, 0, 0 + 270, 0 + 260, WyHelper.hexToRGB("#111115").getRGB(), WyHelper.hexToRGB("#202025").getRGB());		
			
			// Jolly Roger draw with all the backgrounds and layers
			// Drawing the base
			if (this.props.getBase() != null && this.props.getBase().getTexture() != null)
			{
				if (this.props.getBase().canBeColored())
				{
					Color color = WyHelper.hexToRGB(this.props.getBase().getColor());
					GlStateManager.color3f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F);
				}
				else
				{
					GlStateManager.color3f(1.0F, 1.0F, 1.0F);
				}
				Minecraft.getInstance().getTextureManager().bindTexture(this.props.getBase().getTexture());
				GuiUtils.drawTexturedModalRect(0, 0, 0, 0, 256, 256, 2);
			}

			// Drawing the backgrounds
			for (JollyRogerElement element : this.props.getBackgrounds())
			{
				int i = 0;
				if (element != null && element.getTexture() != null)
				{
					if (element.canBeColored())
					{
						Color color = WyHelper.hexToRGB(element.getColor());
						GlStateManager.color3f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F);
					}
					else
					{
						GlStateManager.color3f(1.0F, 1.0F, 1.0F);
					}
					Minecraft.getInstance().getTextureManager().bindTexture(element.getTexture());
					GuiUtils.drawTexturedModalRect(0, 0, 0, 0, 256, 256, i);
				}
				i++;
			}
			
			// Drawing the details
			for (JollyRogerElement element : this.props.getDetails())
			{
				int i = 8;
				if (element != null && element.getTexture() != null)
				{
					if (element.canBeColored())
					{
						Color color = WyHelper.hexToRGB(element.getColor());
						GlStateManager.color3f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F);
					}
					else
					{
						GlStateManager.color3f(1.0F, 1.0F, 1.0F);
					}
					Minecraft.getInstance().getTextureManager().bindTexture(element.getTexture());
					GuiUtils.drawTexturedModalRect(0, 0, 0, 0, 256, 256, i);
				}
				i--;
			}

		}
		GL11.glPopMatrix();

		String text = this.trueIndex >= 0 ? (this.trueIndex + 1) + " / " + this.allBases.size() : "Empty";
		if (this.layerType == LayerType.BACKGROUND)
			text = this.trueIndex >= 0 ? (this.trueIndex + 1) + " / " + this.allBackgrounds.size() : "Empty";
		else if (this.layerType == LayerType.DETAIL)
			text = this.trueIndex >= 0 ? (this.trueIndex + 1) + " / " + this.allDetails.size() : "Empty";
		WyHelper.drawStringWithBorder(this.font, text, posX - font.getStringWidth(text) / 2 + 28, posY + 80, WyHelper.hexToRGB("#FFFFFF").getRGB());

		GlStateManager.disableBlend();

		super.render(x, y, f);
	}

	@Override
	public void init(Minecraft mc, int width, int height)
	{
		super.init(mc, width, height);

		int posX = 0;
		int posY = this.height / 2;

		int listPosY = posY - 85;

		NoTextureButton baseButton = new NoTextureButton(posX + 5, listPosY, 128, 16, "Base", this::selectButton);
		this.addButton(baseButton);

		for (int i = 0; i < this.props.getBackgrounds().length; i++)
		{
			NoTextureButton bgButton = new NoTextureButton(posX + 5, (listPosY + 20 + (i * 20)), 128, 16, "Background " + (i + 1), this::selectButton);
			this.addButton(bgButton);
		}

		for (int i = 0; i < this.props.getDetails().length; i++)
		{
			NoTextureButton detailButton = new NoTextureButton(posX + 5, (listPosY + 60 + (i * 20)), 128, 16, "Detail " + (i + 1), this::selectButton);
			this.addButton(detailButton);
		}

		posX = this.width / 2;

		TexturedIconButton nextBaseTexture = new TexturedIconButton(ModResources.BIG_WOOD_BUTTON_RIGHT, posX + 100, posY - 65, 32, 110, "", (btn) -> this.moveIndex(btn, true));
		nextBaseTexture = nextBaseTexture.setTextureInfo(posX + 100, posY - 75, 32, 128);
		this.addButton(nextBaseTexture);

		TexturedIconButton prevBaseTexture = new TexturedIconButton(ModResources.BIG_WOOD_BUTTON_LEFT, posX - 75, posY - 65, 32, 110, "", (btn) -> this.moveIndex(btn, false));
		prevBaseTexture = prevBaseTexture.setTextureInfo(posX - 75, posY - 75, 32, 128);
		this.addButton(prevBaseTexture);
	}

	public void moveIndex(Button btn, boolean toRight)
	{
		try
		{
			if(toRight)
				this.trueIndex += 1;
			else
				this.trueIndex -= 1;
			
			if (this.layerType == LayerType.BASE)
			{
				if(this.trueIndex >= this.allBases.size())
					this.trueIndex = -1;
				if(this.trueIndex < 0 && this.props.getBase() == null)
					this.trueIndex = this.allBases.size() - 1;

				if(this.trueIndex >= 0 && this.trueIndex <= this.allBases.size())
					this.props.setBase(this.allBases.get(this.trueIndex).get());
				else if(this.trueIndex <= 0 && this.props.getBase().getTexture() != null)
					this.props.setBase(null);
				
				for(int i = 0; i < this.props.getBackgrounds().length; i++)
				{
					JollyRogerElement element = this.props.getBackgrounds()[i];
					boolean hasElement = this.allBackgrounds.stream().anyMatch(elem -> elem != null && elem.get() != null && elem.get().equals(element) && !elem.get().canUse(this.player));
					if(hasElement)
						this.props.getBackgrounds()[i].setTexture(null);
				}
				
				for(int i = 0; i < this.props.getDetails().length; i++)
				{
					JollyRogerElement element = this.props.getDetails()[i];
					boolean hasElement = this.allDetails.stream().anyMatch(elem -> elem != null && elem.get() != null && elem.get().equals(element) && !elem.get().canUse(this.player));
					if(hasElement)
						this.props.getDetails()[i].setTexture(null);
				}
			}
			else if (this.layerType == LayerType.BACKGROUND)
			{
				if(this.trueIndex >= this.allBackgrounds.size())
					this.trueIndex = -1;
				if(this.trueIndex < 0 && this.props.getBackgrounds()[this.layerIndex] == null)
					this.trueIndex = this.allBackgrounds.size() - 1;

				if(this.trueIndex >= 0 && this.trueIndex <= this.allBackgrounds.size())
					this.props.getBackgrounds()[this.layerIndex] = this.allBackgrounds.get(this.trueIndex).get();
				else if(this.trueIndex <= 0 && this.props.getBackgrounds()[this.layerIndex].getTexture() != null)
					this.props.getBackgrounds()[this.layerIndex] = null;
			}
			else if (this.layerType == LayerType.DETAIL)
			{
				if(this.trueIndex >= this.allDetails.size())
					this.trueIndex = -1;
				if(this.trueIndex < 0 && this.props.getDetails()[this.layerIndex] == null)
					this.trueIndex = this.allDetails.size() - 1;

				if(this.trueIndex >= 0 && this.trueIndex <= this.allDetails.size())
					this.props.getDetails()[this.layerIndex] = this.allDetails.get(this.trueIndex).get();
				else if(this.trueIndex <= 0 && this.props.getDetails()[this.layerIndex].getTexture() != null)
					this.props.getDetails()[this.layerIndex] = null;
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void selectButton(Button btn)
	{
		if (!(btn instanceof NoTextureButton))
			return;

		if (this.selectedButton != null)
			((NoTextureButton) this.selectedButton).select();
		this.selectedButton = btn;
		((NoTextureButton) btn).select();

		if (this.buttons.get(0) == btn)
		{
			this.trueIndex = this.findIndex(this.getListFromType(LayerType.BASE), this.props.getBase(), this.player);
			this.layerType = LayerType.BASE;
			return;
		}

		int j = 0;
		for (int i = 1; i < this.props.getBackgrounds().length + 1; i++)
		{
			if (this.buttons.get(i) == btn)
			{
				this.trueIndex = this.findIndex(this.getListFromType(LayerType.BACKGROUND), this.props.getBackgrounds()[j], this.player);
				this.layerType = LayerType.BACKGROUND;
				this.allBackgrounds = this.getTotalElementsForType(this.player, LayerType.BACKGROUND);
				this.layerIndex = j;
				return;
			}
			j++;
		}

		j = 0;
		for (int i = this.props.getBackgrounds().length + 1; i < this.props.getDetails().length + this.props.getBackgrounds().length + 1; i++)
		{
			if (this.buttons.get(i) == btn)
			{
				this.trueIndex = this.findIndex(this.getListFromType(LayerType.DETAIL), this.props.getDetails()[j], this.player);
				this.layerType = LayerType.DETAIL;
				this.allDetails = this.getTotalElementsForType(this.player, LayerType.DETAIL);	
				this.layerIndex = j;
				return;
			}
			j++;
		}
	}

	@Override
	public void onClose()
	{
		//this.props.setBase(this.props.getBase());
		//this.props.setBackgrounds(this.props.getBackgrounds());
		//this.props.setDetails(this.props.getDetails());
		WyNetwork.sendToServer(new CJollyRogerSyncPacket(this.props));
		super.onClose();
	}

	public boolean doesGuiPauseGame()
	{
		return true;
	}

	private JollyRogerElement findElement(List<RegistryObject<JollyRogerElement>> elements, int index)
	{
		return elements.get(0).get();
	}
	
	private int findIndex(List<RegistryObject<JollyRogerElement>> elements, JollyRogerElement element, PlayerEntity player)
	{
		for (int i = 0; i < elements.size(); i++)
		{
			if (elements.get(i).get().equals(element))
			{
				return i;
			}
		}

		return -1;
	}

	public List<RegistryObject<JollyRogerElement>> getListFromType(LayerType type)
	{
		if (type == LayerType.BASE)
			return this.allBases;
		else if (type == LayerType.BACKGROUND)
			return this.allBackgrounds;
		else if (type == LayerType.DETAIL)
			return this.allDetails;

		return this.allBases;
	}

	public List<RegistryObject<JollyRogerElement>> getTotalElementsForType(PlayerEntity player, LayerType type)
	{
		return this.allElements.stream().filter(reg -> reg.get().getLayerType() == type && reg.get().canUse(player)).collect(Collectors.toList());
	}

	public static void open()
	{
		Minecraft.getInstance().displayGuiScreen(new JollyRogerCreatorScreen());
	}
}