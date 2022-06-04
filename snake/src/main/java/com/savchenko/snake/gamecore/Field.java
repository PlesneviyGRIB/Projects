package com.savchenko.snake.gamecore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Field {
    private int width;
    private int height;
    private int rows;
    private int columns;
    private int square;
}