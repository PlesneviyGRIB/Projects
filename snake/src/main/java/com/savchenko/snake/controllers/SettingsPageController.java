package com.savchenko.snake.controllers;

import com.savchenko.snake.config.SpringConfig;
import com.savchenko.snake.enums.SceneName;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import java.io.IOException;

public class SettingsPageController {

    @FXML
    private ChoiceBox<String> complicityChoiceBox;

    @FXML
    private ChoiceBox<Integer> fieldSizeChoiceBox;

    public void initialize(){
        complicityChoiceBox.getItems().add("NOOB");
        complicityChoiceBox.getItems().add("EASY");
        complicityChoiceBox.getItems().add("MEDIUM");
        complicityChoiceBox.getItems().add("HARD");

        fieldSizeChoiceBox.getItems().add(10);
        fieldSizeChoiceBox.getItems().add(20);
        fieldSizeChoiceBox.getItems().add(40);
        fieldSizeChoiceBox.getItems().add(50);
        fieldSizeChoiceBox.getItems().add(100);
    }

    @FXML
    void saveButtonMouseClicked() throws IOException {
        SpringConfig.switchScene(SceneName.FIRST);
    }
}
