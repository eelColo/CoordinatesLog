package com.eelcolo.coordinateslog.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {

    public static final String KEY_CATEGORY = "key.category.coordinateslog";
    public static final String KEY_SAVE_COORDINATES = "key.coordinateslog.save";
    public static final String KEY_SEE_AND_MOD = "key.coordinateslog.see";

    //Save your current Coords
    public static final KeyMapping SAVE_COORD = new KeyMapping(KEY_SAVE_COORDINATES, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_O, KEY_CATEGORY);
    //List all the coords and allow to modify
    public static final KeyMapping SEE_COORDS = new KeyMapping(KEY_SEE_AND_MOD, KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_U, KEY_CATEGORY);
}
