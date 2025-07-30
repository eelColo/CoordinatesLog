package com.eelcolo.coordinateslog.util;

import net.minecraft.network.chat.Component;

public class Coordinates {

    private final String name;
    private final float x;
    private final float y;
    private final float z;
    private final String dimension;


    public Coordinates(String name, float x, float y, float z, String dimension) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.dimension = dimension;
    }

    public String getName() {
        return name;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public String getDimension() {
        if (dimension != null && dimension.contains(":")) {
            return dimension.split(":")[1]; // devuelve solo "overworld"
        } else {
            return dimension; // devuelve lo que haya o null
        }
    }

    @Override
    public String toString() {
        return name +
                '\n' +
                '\n' +
                " X= " + x + '\n' +
                " Y= " + y + '\n' +
                " Z= " + z + '\n' +
                '\n' +
                this.getDimension() ;
    }

    // Método útil para mostrar la información como un Component
    public Component toComponent() {
        return Component.literal(String.format("%s: [%d, %d, %d] (%s)", name, x, y, z, dimension));
    }
}