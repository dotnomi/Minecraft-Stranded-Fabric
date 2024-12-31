package com.dotnomi.stranded.client.input;

import net.minecraft.client.option.KeyBinding;

public interface KeyInputHandler {
	KeyBinding getKeyBinding();
	void onKeyPress(KeyBinding keyMapping);
	void onKeyUp(KeyBinding keyMapping);
	void onKeyDown(KeyBinding keyMapping);
}
