package model;

import javafx.scene.paint.Color;

public enum SpotType {
    REGULAR(Color.WHITE),
    IDOSO(Color.YELLOW),
    PCD(Color.BLUE),
    VIP(Color.MAGENTA);

    private final Color color;

    SpotType(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}