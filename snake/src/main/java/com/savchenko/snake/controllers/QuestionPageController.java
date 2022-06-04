package com.savchenko.snake.controllers;

import com.savchenko.snake.config.SpringConfig;
import com.savchenko.snake.enums.SceneName;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.MouseEvent;

public class QuestionPageController {

    @FXML
    private Label back;

    @FXML
    void backClicked(MouseEvent event) {
        SpringConfig.switchScene(SceneName.FIRST);
    }

    @FXML
    void backEntered(MouseEvent event) {
        back.setBlendMode(BlendMode.RED);
    }

    @FXML
    void backExited(MouseEvent event) {
        back.setBlendMode(BlendMode.DARKEN);
    }
}
