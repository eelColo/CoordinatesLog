package com.eelcolo.coordinateslog.menu;

import com.eelcolo.coordinateslog.CoordinatesLog;
import com.eelcolo.coordinateslog.util.Coordinates; // Importa tu nueva clase
import com.eelcolo.coordinateslog.util.CoordinatesStorage;
import com.eelcolo.coordinateslog.util.EditableTextBoxCoords;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.dialog.input.TextInput;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CoordsScreen extends Screen {

    private static final Component TITLE = Component.translatable("CoordsLog");
    private static final Component ADD_NEW_BUTTON_TEXT = Component.translatable("gui." + CoordinatesLog.MOD_ID + ".coords_log.button.add_new");
    private static final Component EDIT_BUTTON_TEXT = Component.literal("E");
    private static final Component DELETE_BUTTON_TEXT = Component.literal("X");
    private static final Component SUBMIT_BUTTON = Component.literal("Submit");


    private EditBox editName;
    private EditBox editX;
    private EditBox editY;
    private EditBox editZ;
    private Coordinates coordBeingEdited;

    private final List<EditableTextBoxCoords> editableCoords = new ArrayList<>();
    private final List<Coordinates> coordinatesList;

    public CoordsScreen() {
        super(TITLE);
            this.coordinatesList = CoordinatesStorage.load();
    }


    @Override
    protected void init() {
        super.init();

        //fuente, largo y ancho
        int index = 0;

        // --- Bucle para crear los widgets de cada coordenada ---
        for (Coordinates coord : this.coordinatesList) {

            int yOffset = 30 + index * 75;

            //Esto muestra las coordenadas
            MultiLineTextWidget textWidget = new MultiLineTextWidget(Component.literal(coord.toString()), this.font);
            textWidget.setX(this.width / 2 - 100);
            textWidget.setY(yOffset);

            this.addRenderableWidget(textWidget);

            // Botón de Editar
            this.addRenderableWidget(Button.builder(EDIT_BUTTON_TEXT, button -> handleEdit(coord))
                    .bounds(this.width / 2 + 50, yOffset, 20, 20)
                    .build());

            // Botón de Borrar
            this.addRenderableWidget(Button.builder(DELETE_BUTTON_TEXT, button -> handleDelete(coord))
                    .bounds(this.width / 2 + 75, yOffset, 20, 20)
                    .build());

            index++;



        }
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {

        int centerX = this.width / 2;

        graphics.drawString(this.font, "Texto arriba", centerX - 50, 10, 0xFFFFFF);
        // Renderiza todos los widgets (botones, etc.) que agregamos en init()
        super.render(graphics, mouseX, mouseY, partialTicks);


    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    // --- Métodos para manejar los clics de los botones ---

    private void handleEdit(Coordinates coordToEdit) {
        // Aquí abrirías una pantalla de edición, pasándole la coordenada a editar

        this.coordBeingEdited = coordToEdit;

        editName = new EditBox(this.font, this.width / 2 - 50, this.height - 120, 100, 20, Component.literal("Nombre"));
        editName.setValue(coordToEdit.getName());

        editX = new EditBox(this.font, this.width / 2 - 50, this.height - 90, 100, 20, Component.literal("X"));
        editX.setValue(String.valueOf(coordToEdit.getX()));

        editY = new EditBox(this.font, this.width / 2 - 50, this.height - 60, 100, 20, Component.literal("Y"));
        editY.setValue(String.valueOf(coordToEdit.getY()));

        editZ = new EditBox(this.font, this.width / 2 - 50, this.height - 30, 100, 20, Component.literal("Z"));
        editZ.setValue(String.valueOf(coordToEdit.getZ()));

        this.addRenderableWidget(editName);
        this.addRenderableWidget(editX);
        this.addRenderableWidget(editY);
        this.addRenderableWidget(editZ);

        this.addRenderableWidget(Button.builder(SUBMIT_BUTTON, button -> submitEdit())
                .bounds(this.width / 2 + 60, this.height - 30, 60, 20)
                .build());
        /*Coordinates nCoord = new Coordinates("Coordenada editada", 1, 2, 3, "nueva dimension");
        coordinatesList.remove(coordToEdit);
        coordinatesList.add(nCoord);
        */

        System.out.println("Intentando editar: " + coordToEdit.getName());
        // Ejemplo: this.minecraft.setScreen(new EditCoordScreen(coordToEdit));
    }
    private void submitEdit(){
        try {
            String name = editName.getValue();
            float x = Float.parseFloat(editX.getValue());
            float y = Float.parseFloat(editY.getValue());
            float z = Float.parseFloat(editZ.getValue());

            Coordinates updated = new Coordinates(name, x, y, z, coordBeingEdited.getDimension());

            // Reemplazá el viejo


            coordinatesList.remove(coordBeingEdited);
            coordinatesList.add(updated);

            // Volvé a abrir la pantalla
            CoordinatesStorage.save(coordinatesList);
            this.minecraft.setScreen(new CoordsScreen());

        } catch (NumberFormatException e) {
            System.out.println("Error: coordenadas inválidas");
        }
    }

    private void handleDelete(Coordinates coordToDelete) {
        // Aquí implementarías la lógica para borrar la coordenada de tu lista/archivo
        System.out.println("Intentando borrar: " + coordToDelete.getName());
        this.coordinatesList.remove(coordToDelete);
        CoordinatesStorage.save(this.coordinatesList);
        // Recargamos la pantalla para que los cambios se reflejen al instante
        this.minecraft.setScreen(new CoordsScreen());
    }
}