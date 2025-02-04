package creeperpookie.liquidtest.liquid_test;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.RegistryObject;

public class Blocks
{
	public static final RegistryObject<Block> FLUID_BLOCK;

	static
	{
		FLUID_BLOCK = LiquidTest.getBlocksRegister().register("fluid", () -> new LiquidBlock(Fluids.FLUID, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).replaceable().noCollission().randomTicks().strength(150f).pushReaction(PushReaction.DESTROY).noLootTable().liquid().sound(SoundType.EMPTY)));
	}

	public static void register() {}
}
