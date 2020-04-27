package xyz.pixelatedw.wypi;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryManager;
import xyz.pixelatedw.wypi.abilities.Ability;
import xyz.pixelatedw.wypi.quests.Quest;

@Mod.EventBusSubscriber(modid = APIConfig.PROJECT_ID)
public class APIRegistries
{
	static
	{
		make(new ResourceLocation(APIConfig.PROJECT_ID, "abilities"), Ability.class);
		make(new ResourceLocation(APIConfig.PROJECT_ID, "quests"), Quest.class);
	}	
	
	public static final IForgeRegistry<Ability> ABILITIES = RegistryManager.ACTIVE.getRegistry(Ability.class);
	public static final IForgeRegistry<Quest> QUESTS = RegistryManager.ACTIVE.getRegistry(Quest.class);

	public static <T extends IForgeRegistryEntry<T>> void make(ResourceLocation name, Class<T> type)
	{
		new RegistryBuilder<T>().setName(name).setType(type).setMaxID(Integer.MAX_VALUE - 1).create();
	}
}
