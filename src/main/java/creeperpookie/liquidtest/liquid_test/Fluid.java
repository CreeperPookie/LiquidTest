package creeperpookie.liquidtest.liquid_test;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;

public abstract class Fluid extends FlowingFluid
{
	public Fluid()
	{
		LiquidTest.getLogger().info("Instantiated new Fluid instance");
	}

	@Override
	protected void createFluidStateDefinition(@NotNull StateDefinition.Builder<net.minecraft.world.level.material.Fluid, FluidState> fluidStateBuilder)
	{
		super.createFluidStateDefinition(fluidStateBuilder);
		//fluidStateBuilder.add(LEVEL);
	}

	@NotNull
	@Override
	public FluidType getFluidType()
	{
		return new FluidType(FluidType.Properties.create().canConvertToSource(false).canPushEntity(true).motionScale(0.5).canDrown(true));
	}

	@Override
	public boolean isSame(@NotNull net.minecraft.world.level.material.Fluid fluid)
	{
		return fluid == getFlowing() || fluid == getSource();
	}

	@Override
	@NotNull
	public net.minecraft.world.level.material.Fluid getFlowing()
	{
		return Fluids.FLOWING_FLUID.get();
	}

	@Override
	@NotNull
	public net.minecraft.world.level.material.Fluid getSource()
	{
		return Fluids.FLUID.get();
	}

	@Override
	@NotNull
	public Item getBucket()
	{
		return null;
	}

	@Override
	protected boolean canConvertToSource(Level level)
	{
		return level.getGameRules().getBoolean(GameRules.RULE_WATER_SOURCE_CONVERSION);
	}

	@Override
	public boolean canConvertToSource(@NotNull FluidState state, @NotNull Level level, @NotNull BlockPos pos)
	{
		return canConvertToSource(level);
	}

	protected void beforeDestroyingBlock(@NotNull LevelAccessor accessor, @NotNull BlockPos pos, BlockState state)
	{
		BlockEntity blockentity = state.hasBlockEntity() ? accessor.getBlockEntity(pos) : null;
		Block.dropResources(state, accessor, pos, blockentity);
	}

	public int getSlopeFindDistance(@NotNull LevelReader levelReader)
	{
		return 4;
	}

	public int getDropOff(@NotNull LevelReader levelReader)
	{
		return 1;
	}

	public int getTickDelay(@NotNull LevelReader levelReader)
	{
		return 10;
	}

	public boolean canBeReplacedWith(@NotNull FluidState state, @NotNull BlockGetter blockGetter, @NotNull BlockPos pos, @NotNull net.minecraft.world.level.material.Fluid fluid, @NotNull Direction direction)
	{
		return direction == Direction.DOWN && isSame(fluid);
	}

	@Override
	protected float getExplosionResistance()
	{
		return 150f; // 1.5x as much as water and lava, to make it feel more viscous
	}

	@NotNull
	public BlockState createLegacyBlock(@NotNull FluidState state)
	{
		return Fluids.FLUID.get().defaultFluidState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state)).createLegacyBlock();
	}

	public static class Flowing extends Fluid
	{
		protected void createFluidStateDefinition(@NotNull StateDefinition.Builder<net.minecraft.world.level.material.Fluid, FluidState> fluidStateBuilder)
		{
			super.createFluidStateDefinition(fluidStateBuilder);
			fluidStateBuilder.add(LEVEL);
		}

		@Override
		public int getAmount(FluidState fluidState)
		{
			return fluidState.getValue(LEVEL);
		}

		@Override
		public boolean isSource(@NotNull FluidState fluidState)
		{
			return false;
		}
	}

	public static class Source extends Fluid
	{
		protected void createFluidStateDefinition(@NotNull StateDefinition.Builder<net.minecraft.world.level.material.Fluid, FluidState> fluidStateBuilder)
		{
			super.createFluidStateDefinition(fluidStateBuilder);
			fluidStateBuilder.add(LEVEL);
		}

		@Override
		public int getAmount(@NotNull FluidState fluidState)
		{
			return 8;
		}

		@Override
		public boolean isSource(@NotNull FluidState fluidState)
		{
			return true;
		}
	}
}
