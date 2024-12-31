package com.dotnomi.stranded.client.input;

import com.dotnomi.stranded.Stranded;
import com.dotnomi.stranded.client.input.handler.DebugKeyInputHandler;
import com.dotnomi.stranded.client.input.handler.SkipVoiceoverKeyInputHandler;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class ModKeyBindings {
	private static final String KEY_CATEGORY_STRANDED = "key.category.stranded";
	private static final String KEY_TRANSLATION_PREFIX = "key.stranded.";
	private static final List<KeyInputHandler> KEY_INPUT_HANDLERS = new ArrayList<>();

	public static final KeyBinding DEBUG_KEY = new KeyBinding(
		getTranslationKey("debug"),
		InputUtil.Type.KEYSYM,
		GLFW.GLFW_KEY_O,
		KEY_CATEGORY_STRANDED
	);

	public static final KeyBinding SKIP_VOICEOVER_KEY = new KeyBinding(
		getTranslationKey("skip-voiceover"),
		InputUtil.Type.KEYSYM,
		GLFW.GLFW_KEY_ENTER,
		KEY_CATEGORY_STRANDED
	);

	public static void register() {
		KEY_INPUT_HANDLERS.add(new DebugKeyInputHandler());
		KEY_INPUT_HANDLERS.add(new SkipVoiceoverKeyInputHandler());

		for (KeyInputHandler keyInputHandler : KEY_INPUT_HANDLERS) {
			KeyBindingHelper.registerKeyBinding(keyInputHandler.getKeyBinding());
			Stranded.LOGGER.debug("Registered key binding: {}", keyInputHandler.getKeyBinding().getTranslationKey());
		}
	}

	private static String getTranslationKey(String name) {
		return KEY_TRANSLATION_PREFIX + name;
	}
}
