    package com.savchenko.snake.controllers;

import com.savchenko.snake.config.SpringConfig;
import com.savchenko.snake.enums.SceneName;
import javafx.fxml.FXML;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.ImageView;

public class PlayPageController {

    @FXML
    private ImageView pauseButton;

    @FXML
    void pauseButtonMouseClicked() {
        SpringConfig.switchScene(SceneName.PAUSE);
        SpringConfig.currentGame.stopPlaying();
    }

    @FXML
    void pauseButtonMouseEntered() {
        pauseButton.setBlendMode(BlendMode.RED);
    }

    @FXML
    void pauseButtonMouseExited() {
        pauseButton.setBlendMode(BlendMode.DARKEN);
    }
}
