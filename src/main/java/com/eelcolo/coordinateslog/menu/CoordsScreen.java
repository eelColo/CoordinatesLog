package com.eelcolo.coordinateslog.menu;

import com.eelcolo.coordinateslog.CoordinatesLog;
import com.eelcolo.coordinateslog.util.Coordinates; // Importa tu nueva clase
import com.eelcolo.coordinateslog.util.CoordinatesStorage;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CoordsScreen extends Screen {

    //Components for the coords screen
    private static final Component TITLE = Component.translatable("CoordsLog");
    private static final Component EDIT_BUTTON_TEXT = Component.literal("E");
    private static final Component DELETE_BUTTON_TEXT = Component.literal("X");
    private static final Component SUBMIT_BUTTON = Component.literal("Submit");

    //Reference to the edit widgets
    private EditBox editName;
    private EditBox editX;
    private EditBox editY;
    private EditBox editZ;

    //Mirror reference to a concrete coord to edit
    private Coordinates coordBeingEdited;

    private final List<Coordinates> coordinatesList;

    public CoordsScreen() {
        super(TITLE);
            this.coordinatesList = CoordinatesStorage.load();
    }


    @Override
    protected void init() {
        super.init();

        int index = 0;

        for (Coordinates coord : this.coordinatesList) {

            int yPosition = 30 + index * 75;

            //Text that show de coordinate in cuestion
            MultiLineTextWidget textWidget = new MultiLineTextWidget(Component.literal(coord.toString()), this.font);
            textWidget.setX(this.width / 2 - 100);
            textWidget.setY(yPosition);

            this.addRenderableWidget(textWidget);

            // EditButton
            // Component content for the button
            // The action when pressed
            // Bounds and position

            this.addRenderableWidget(
                    Button.builder(
                            EDIT_BUTTON_TEXT,
                                    button -> handleEdit(coord))
                    .bounds(this.width / 2 + 50, yPosition, 20, 20)
                    .build());

            // Delete
            this.addRenderableWidget(Button.builder(DELETE_BUTTON_TEXT, button -> handleDelete(coord))
                    .bounds(this.width / 2 + 75, yPosition, 20, 20)
                    .build());

            index++;
        }
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        // render all the widgets in init()
        super.render(graphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }


    private void handleEdit(Coordinates coordToEdit) {
        this.coordBeingEdited = coordToEdit;

        //Font, bounds (width, height), size and the component name
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
    }

    private void submitEdit(){
        try {
            String name = editName.getValue();
            float x = Float.parseFloat(editX.getValue());
            float y = Float.parseFloat(editY.getValue());
            float z = Float.parseFloat(editZ.getValue());

            Coordinates updated = new Coordinates(name, x, y, z, coordBeingEdited.getDimension());

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
        this.coordinatesList.remove(coordToDelete);
        CoordinatesStorage.save(this.coordinatesList);

        this.minecraft.setScreen(new CoordsScreen());
    }
}