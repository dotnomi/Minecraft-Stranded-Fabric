package com.dotnomi.stranded.client.input.handler;

import com.dotnomi.stranded.Stranded;
import com.dotnomi.stranded.client.input.AbstractKeyInputHandler;
import com.dotnomi.stranded.client.input.ModKeyBindings;
import net.minecraft.client.option.KeyBinding;

public class DebugKeyInputHandler extends AbstractKeyInputHandler {

	public DebugKeyInputHandler() {
		super(ModKeyBindings.DEBUG_KEY);
	}

	@Override
	public void onKeyPress(KeyBinding keyBinding) {

	}

	@Override
	public void onKeyUp(KeyBinding keyBinding) {

	}

	@Override
	public void onKeyDown(KeyBinding keyBinding) {
		Stranded.VOICEOVER_MANAGER.get().playVoiceover("landing_intro");
	}
}
