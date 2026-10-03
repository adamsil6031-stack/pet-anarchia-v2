package com.example.petmod;

import java.util.Map;
import java.util.WeakHashMap;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public class PetScreen extends Screen {
	// Model peta: assets/petmod/items/pet.json
	public static final Identifier PET_MODEL = Identifier.fromNamespaceAndPath(PetModClient.MOD_ID, "pet");

	// Zapamietuje oryginalny model, zeby mozna go bylo przywrocic.
	private static final Map<ItemStack, Identifier> ORIGINALS = new WeakHashMap<>();

	public PetScreen(Component title) {
		super(title);
	}

	@Override
	protected void init() {
		int cx = this.width / 2;
		int cy = this.height / 2;

		this.addRenderableWidget(Button.builder(Component.translatable("screen.petmod.to_pet"), btn -> swap(true))
			.bounds(cx - 100, cy - 10, 200, 20).build());

		this.addRenderableWidget(Button.builder(Component.translatable("screen.petmod.restore"), btn -> swap(false))
			.bounds(cx - 100, cy + 15, 200, 20).build());

		this.addRenderableWidget(Button.builder(Component.translatable("gui.done"), btn -> this.onClose())
			.bounds(cx - 100, cy + 40, 200, 20).build());
	}

	private void swap(boolean toPet) {
		LocalPlayer player = this.minecraft.player;
		if (player == null) {
			return;
		}

		ItemStack stack = player.getMainHandItem();
		if (stack.isEmpty()) {
			player.sendSystemMessage(Component.translatable("screen.petmod.empty_hand"));
			return;
		}

		if (toPet) {
			// Zapisujemy oryginalny model (tylko raz), potem podmieniamy na peta.
			if (!ORIGINALS.containsKey(stack)) {
				Identifier current = stack.get(DataComponents.ITEM_MODEL);
				ORIGINALS.put(stack, current != null ? current : BuiltInRegistries.ITEM.getKey(stack.getItem()));
			}
			stack.set(DataComponents.ITEM_MODEL, PET_MODEL);
			player.sendSystemMessage(Component.translatable("screen.petmod.swapped"));
		} else {
			Identifier original = ORIGINALS.remove(stack);
			if (original == null) {
				original = BuiltInRegistries.ITEM.getKey(stack.getItem());
			}
			stack.set(DataComponents.ITEM_MODEL, original);
			player.sendSystemMessage(Component.translatable("screen.petmod.restored"));
		}
	}

	@Override
	public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
		super.extractRenderState(graphics, mouseX, mouseY, delta);

		ItemStack held = this.minecraft.player != null ? this.minecraft.player.getMainHandItem() : ItemStack.EMPTY;
		String line = held.isEmpty()
			? Component.translatable("screen.petmod.empty_hand").getString()
			: Component.translatable("screen.petmod.holding", held.getHoverName().getString()).getString();

		graphics.text(this.font, this.title.getString(), this.width / 2 - 100, this.height / 2 - 50, 0xFFFFFFFF, true);
		graphics.text(this.font, line, this.width / 2 - 100, this.height / 2 - 32, 0xFFAAAAAA, true);
	}
}
