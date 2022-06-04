package com.savchenko.snake.controllers;

import com.savchenko.snake.config.SpringConfig;
import com.savchenko.snake.enums.SceneName;
import com.savchenko.snake.models.Game;
import javafx.fxml.FXML;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.ImageView;
import javafx.scene.shape.QuadCurve;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class FirstPageController {

    @FXML
    private ImageView buttonPlay;

    @FXML
    private ImageView buttonQuestion;

    @FXML
    private ImageView buttonSettings;


    @FXML
    void playButtonMouseClicked() {
        Game game = new AnnotationConfigApplicationContext(SpringConfig.class).getBean(Game.class);
        game.play();
        SpringConfig.switchScene(SceneName.PLAY);
        game.rule();
    }
    @FXML
    void playButtonMouseEntered() {
        buttonPlay.setBlendMode(BlendMode.RED);
    }
    @FXML
    void playButtonMouseExited() {
        buttonPlay.setBlendMode(BlendMode.DARKEN);
    }

    @FXML
    void questionButtonMouseClicked() {
        SpringConfig.switchScene(SceneName.QUESTION);
    }
    @FXML
    void questionButtonMouseEntered() {
        buttonQuestion.setBlendMode(BlendMode.RED);
    }
    @FXML
    void questionButtonMouseExited() {
        buttonQuestion.setBlendMode(BlendMode.DARKEN);
    }

    @FXML
    void settingsButtonMouseClicked() {
        SpringConfig.switchScene(SceneName.SETTINGS);
    }
    @FXML
    void settingsButtonMouseEntered() {
        buttonSettings.setBlendMode(BlendMode.RED);
    }
    @FXML
    void settingsButtonMouseExited() {
        buttonSettings.setBlendMode(BlendMode.DARKEN);
    }
}