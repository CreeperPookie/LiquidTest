package creeperpookie.liquidtest.liquid_test.mixin;

import net.minecraft.client.renderer.texture.*;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(TextureAtlas.class)
public abstract class TextureAtlasMixin extends AbstractTexture implements Dumpable, Tickable
{
	@Shadow private Map<ResourceLocation, TextureAtlasSprite> texturesByName;

	@Inject(method = "getSprite", at = @At("HEAD"), cancellable = true)
	public void getSprite(ResourceLocation p_118317_, CallbackInfoReturnable<TextureAtlasSprite> cir)
	{
		if (p_118317_ == null)
		{
			cir.cancel();
			cir.setReturnValue(this.texturesByName.get(MissingTextureAtlasSprite.getLocation()));
		}
	}
}
