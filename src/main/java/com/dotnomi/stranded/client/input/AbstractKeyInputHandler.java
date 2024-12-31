package com.dotnomi.stranded.client.input;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.option.KeyBinding;

public abstract class AbstractKeyInputHandler implements KeyInputHandler {
	private final KeyBinding keyBinding;
	private boolean wasPressed = false;

	public AbstractKeyInputHandler(KeyBinding keyBinding) {
		this.keyBinding = keyBinding;
		ClientTickEvents.END_CLIENT_TICK.register(client -> handleClientTick());
	}

	private void handleClientTick() {
		if (keyBinding.isPressed()) {
			if (!wasPressed) {
				wasPressed = true;
				onKeyDown(keyBinding);
			}

			onKeyPress(keyBinding);
		} else if (wasPressed) {
			onKeyUp(keyBinding);
			wasPressed = false;
		}
	}

	@Override
	public KeyBinding getKeyBinding() {
		return keyBinding;
	}

	@Override
	public abstract void onKeyPress(KeyBinding keyMapping);

	@Override
	public abstract void onKeyUp(KeyBinding keyMapping);

	@Override
	public abstract void onKeyDown(KeyBinding keyMapping);
}
