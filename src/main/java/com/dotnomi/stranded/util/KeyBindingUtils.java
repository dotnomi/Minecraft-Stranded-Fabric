package com.dotnomi.stranded.util;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.text.Text;

public class KeyBindingUtils {
	public static String getKeyTranslation(KeyBinding keyBinding) {
		return Text.translatable(keyBinding.getBoundKeyTranslationKey()).getString();
	}
}
