package com.savchenko.snake.controllers;

import com.savchenko.snake.config.SpringConfig;
import com.savchenko.snake.enums.SceneName;
import com.savchenko.snake.models.Game;
import javafx.fxml.FXML;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ResultsPageController {

        @FXML
        private ImageView restartButton;

        @FXML
        private ImageView xitButton;

        @FXML
        void exitButtonClicked() {
            SpringConfig.switchScene(SceneName.FIRST);
        }

        @FXML
        void exitButtonEntered() {
            xitButton.setBlendMode(BlendMode.RED);
        }

        @FXML
        void exitButtonExited() {
            xitButton.setBlendMode(BlendMode.DARKEN);
        }


        @FXML
        void restartButtonClicked(MouseEvent event) {
                Game game = new AnnotationConfigApplicationContext(SpringConfig.class).getBean(Game.class);
                game.play();
                SpringConfig.switchScene(SceneName.PLAY);
                game.rule();
        }

        @FXML
        void restartButtonEntered(MouseEvent event) {
                restartButton.setBlendMode(BlendMode.RED);
        }

        @FXML
        void restartButtonExited(MouseEvent event) {
                restartButton.setBlendMode(BlendMode.DARKEN);
        }
}
