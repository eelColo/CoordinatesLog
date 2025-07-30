package com.eelcolo.coordinateslog.events;

import com.eelcolo.coordinateslog.CoordinatesLog;
import com.eelcolo.coordinateslog.menu.CoordsScreen;
import com.eelcolo.coordinateslog.util.Coordinates;
import com.eelcolo.coordinateslog.util.CoordinatesStorage;
import com.eelcolo.coordinateslog.util.KeyBinding;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;


public class ClientEvents {

    @Mod.EventBusSubscriber(modid = CoordinatesLog.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvent {


        static List<Coordinates> playerSaves = new ArrayList<>();

        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.SAVE_COORD);
            event.register(KeyBinding.SEE_COORDS);
        }

        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            Minecraft mc = Minecraft.getInstance();
            Player player = mc.player;
            if (player == null) {
                return;
            }

            //Guardar las coordenadas
            if (KeyBinding.SAVE_COORD.consumeClick()) {
                // Generamos un nombre por defecto. Más adelante podrías hacer una pantalla para que el usuario ponga el nombre.
                String name = "Coord " + (playerSaves.size() + 1);

                float x = player.getBlockX();
                float y = player.getBlockY();
                float z = player.getBlockZ();

                String dimension = player.level().dimension().location().toString();

                Coordinates newCoord = new Coordinates(name, x, y, z, dimension);

                playerSaves.add(newCoord);
                CoordinatesStorage.save(playerSaves);

                player.displayClientMessage(Component.literal("Coordenadas guardadas"), true);
            }

            if (KeyBinding.SEE_COORDS.consumeClick()) {

                mc.setScreen(new CoordsScreen());
            }
        }
    }
}