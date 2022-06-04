package com.savchenko.snake.control;

import com.savchenko.snake.canvases.Snakes;
import com.savchenko.snake.interfaces.Changeable;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.Map;

public class ScoreBoard extends Label implements Changeable {

    private Snakes snakes;

    public ScoreBoard(Snakes snakes){
        this.snakes = snakes;
        setLayoutX(5);
        setLayoutY(10.0);
        prefHeight(134.0);
        prefWidth(100.0);
        setFont(new Font("Arial", 15));
        setTextFill(Color.rgb(255,255,255));
        setAlignment(Pos.TOP_LEFT);
        setTextAlignment(TextAlignment.LEFT);
    }

    @Override
    public void change() {
        setText("");
        StringBuilder stringBuilder = new StringBuilder();
        Map.Entry<String,Integer> entry;

        for(int i = 0; i < 5 && i<snakes.currentResult().size(); i++) {
            entry = snakes.currentResult().get(i);
            stringBuilder.append(entry.getKey()).append("   ").append(entry.getValue()).append("\n");
        }

        setText(stringBuilder.toString());
    }
}
