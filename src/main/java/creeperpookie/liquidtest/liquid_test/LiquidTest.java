package creeperpookie.liquidtest.liquid_test;

import com.mojang.logging.LogUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(LiquidTest.MODID)
public class LiquidTest
{

	// Define mod id in a common place for everything to reference
	public static final String MODID = "liquid_test";
	// Directly reference a slf4j logger
	private static final Logger LOGGER = LogUtils.getLogger();
	// Create a Deferred Register to hold Blocks which will all be registered under the "liquid_test" namespace
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
	// Create a Deferred Register to hold Items which will all be registered under the "liquid_test" namespace
	// public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
	public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, MODID);

	public LiquidTest()
	{
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		// Register the commonSetup method for modloading
		modEventBus.addListener(this::commonSetup);
		Fluids.register();
		Blocks.register();

		// Register the Deferred Register to the mod event bus so blocks get registered
		BLOCKS.register(modEventBus);
		FLUIDS.register(modEventBus);

		// Register ourselves for server and other game events we are interested in
		MinecraftForge.EVENT_BUS.register(this);
	}

	public static DeferredRegister<Fluid> getFluidRegister()
	{
		return FLUIDS;
	}

	public static Logger getLogger()
	{
		return LOGGER;
	}

	public static DeferredRegister<Block> getBlocksRegister()
	{
		return BLOCKS;
	}

	private void commonSetup(final FMLCommonSetupEvent event)
	{
	}

	// You can use SubscribeEvent and let the Event Bus discover methods to call
	@SubscribeEvent
	public void onServerStarting(ServerStartingEvent event)
	{
	}
}
