package com.example.petmod;

import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;

public class PetModClient implements ClientModInitializer {
	public static final String MOD_ID = "petmod";

	// Kategoria w Opcje > Sterowanie.
	private static final KeyMapping.Category CATEGORY =
		KeyMapping.Category.register(Identifier.fromNamespaceAndPath(MOD_ID, "main"));

	private static KeyMapping openGuiKey;

	@Override
	public void onInitializeClient() {
		// Domyslnie klawisz [ (mozna zmienic w ustawieniach sterowania).
		openGuiKey = KeyMappingHelper.registerKeyMapping(
			new KeyMapping(
				"key.petmod.open_gui",
				InputConstants.Type.KEYSYM,
				InputConstants.KEY_LBRACKET,
				CATEGORY
			));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (openGuiKey.consumeClick()) {
				// Otwieramy tylko, gdy gracz jest w grze i zadne inne GUI nie jest otwarte.
				if (client.player != null && client.screen == null) {
					client.setScreen(new PetScreen(Component.translatable("screen.petmod.title")));
				}
			}
		});
	}
}
