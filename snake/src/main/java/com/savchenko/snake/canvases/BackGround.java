package com.savchenko.snake.canvases;

import com.savchenko.snake.gamecore.Field;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BackGround extends Canvas {
    private final Field field;

    public BackGround(Field field){
        super(field.getWidth(), field.getHeight());
        this.field = field;
        draw(getGraphicsContext2D());
    }

    private void draw(GraphicsContext graphicsContext){
        for(int i = 0 ; i<field.getColumns(); i++)
            for (int j = 0; j<field.getRows(); j++){
                graphicsContext.setFill(Color.web((i+j) % 2 == 0? "AAD751":"A2D149"));
                graphicsContext.fillRect(i * field.getSquare(), j * field.getSquare(), field.getSquare(), field.getSquare());
            }
    }
}
