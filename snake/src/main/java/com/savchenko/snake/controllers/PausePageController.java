package com.savchenko.snake.controllers;

import com.savchenko.snake.config.SpringConfig;
import com.savchenko.snake.enums.SceneName;
import com.savchenko.snake.models.Game;
import javafx.fxml.FXML;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.ImageView;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class PausePageController {

    @FXML
    private ImageView continueButton;

    @FXML
    private ImageView exitButton;

    @FXML
    private ImageView restartButton;

    @FXML
    void continueButtonMouseClicked() {
        SpringConfig.switchScene(SceneName.PLAY);
        SpringConfig.currentGame.play();
    }

    @FXML
    void continueButtonMouseEntered() {
        continueButton.setBlendMode(BlendMode.RED);
    }

    @FXML
    void continueButtonMouseExited() {
        continueButton.setBlendMode(BlendMode.DARKEN);
    }

    @FXML
    void exitButtonMouseClicked() {
        SpringConfig.switchScene(SceneName.FIRST);
    }

    @FXML
    void exitButtonMouseEntered() {
        exitButton.setBlendMode(BlendMode.RED);
    }

    @FXML
    void exitButtonMouseExited() {
        exitButton.setBlendMode(BlendMode.DARKEN);
    }

    @FXML
    void restartButtonMouseEntered() {
        restartButton.setBlendMode(BlendMode.RED);
    }

    @FXML
    void restartButtonMouseClicked() {
        Game game = new AnnotationConfigApplicationContext(SpringConfig.class).getBean(Game.class);
        game.play();
        SpringConfig.switchScene(SceneName.PLAY);
        game.rule();
    }

    @FXML
    void restartButtonMouseExited() {
        restartButton.setBlendMode(BlendMode.DARKEN);
    }
}
