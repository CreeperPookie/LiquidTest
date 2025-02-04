package creeperpookie.liquidtest.liquid_test;

import net.minecraft.world.level.material.FlowingFluid;
import net.minecraftforge.registries.RegistryObject;

public class Fluids
{
	public static final RegistryObject<FlowingFluid> FLOWING_FLUID;
	public static final RegistryObject<FlowingFluid> FLUID;

	static
	{
		FLOWING_FLUID = LiquidTest.getFluidRegister().register("flowing_fluid", Fluid.Flowing::new);
		FLUID = LiquidTest.getFluidRegister().register("fluid", Fluid.Source::new);
		LiquidTest.getLogger().info("registered fluid as {}", !FLUID.isPresent() ? "null" : FLUID.get().getClass().getName());
	}

	public static void register() {} // used to initiate static initialization
}
