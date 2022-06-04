package com.savchenko.snake.gamecore;

import com.savchenko.snake.enums.Complicity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import com.savchenko.snake.gamecore.Field;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class GameData {
    private Field field;
    private Complicity complicity;
    @Setter
    private int cntOfBoots;
    private int cntOfFood;
    private int velocity;
    private boolean second;
    private String firstPlayer;
    private String secondPlayer;
}
