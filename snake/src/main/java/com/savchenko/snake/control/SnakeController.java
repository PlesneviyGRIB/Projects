package com.savchenko.snake.control;

import com.savchenko.snake.canvases.Snakes;
import com.savchenko.snake.enums.Direction;

public class SnakeController {
    private Snakes.Snake snake;

    public SnakeController(Snakes.Snake snake){
        this.snake = snake;
    }

    public void setDirection(Direction direction){
        snake.setDirection(direction);
    }
}
