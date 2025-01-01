package com.dotnomi.stranded.client.input.handler;

import com.dotnomi.stranded.Stranded;
import com.dotnomi.stranded.client.input.AbstractKeyInputHandler;
import com.dotnomi.stranded.client.input.ModKeyBindings;
import net.minecraft.client.option.KeyBinding;

public class SkipVoiceoverKeyInputHandler extends AbstractKeyInputHandler {
	public SkipVoiceoverKeyInputHandler() {
		super(ModKeyBindings.SKIP_VOICEOVER_KEY);
	}

	@Override
	public void onKeyPress(KeyBinding keyBinding) {

	}

	@Override
	public void onKeyUp(KeyBinding keyMapping) {

	}

	@Override
	public void onKeyDown(KeyBinding keyMapping) {
		Stranded.VOICEOVER_MANAGER.get().skipCurrentVoiceover();
	}
}
