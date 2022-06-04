package com.savchenko.snake.control;

import com.savchenko.snake.canvases.Snakes;
import com.savchenko.snake.canvases.Wall;
import com.savchenko.snake.enums.Direction;
import com.savchenko.snake.gamecore.AllLets;
import com.savchenko.snake.gamecore.Point;
import com.savchenko.snake.interfaces.Let;
import java.util.Random;

public class SnakeBotController {
    private Snakes.Snake snake;
    private Random random = new Random();
    private AllLets allLets;

    public SnakeBotController(Snakes.Snake snake, AllLets allLets){
        this.snake = snake;
        this.allLets = allLets;
    }

    public void guess() {
        for(Let let: allLets.getLets()){
            if(complicatedCondition(let, snake.getCurrentDirection())) {
                snake.setDirection(newDirection(snake.getCurrentDirection(), let));
                break;
            }
        }
    }

    private boolean complicatedCondition(Let let, Direction direction){
        return let.getClass().equals(Wall.class) && ((Wall)let).hasWall(getPoint(direction, snake.getHead())) || let == snake && ((Snakes.Snake)let).containsNode(getPoint(direction,snake.getHead()));
    }

    private Direction newDirection(Direction direction, Let let){
        switch (direction){
            case LEFT, RIGHT -> {
                if(random.nextBoolean()) {
                    if (complicatedCondition(let, Direction.TOP)) return Direction.BOTTOM;
                    else return Direction.TOP;
                }
                if (complicatedCondition(let, Direction.BOTTOM)) return Direction.TOP;
                else return Direction.BOTTOM;
            }
            default -> {
                if(random.nextBoolean()) {
                    if (complicatedCondition(let, Direction.LEFT)) return Direction.RIGHT;
                    else return Direction.LEFT;
                }
                if (complicatedCondition(let, Direction.RIGHT)) return Direction.LEFT;
                else return Direction.RIGHT;
            }
        }
    }

    public boolean botAlive(){
        return snake.isAlive();
    }

    private Point getPoint(Direction current, Point head){
        switch (current){
            case LEFT ->   { return new Point(head.x() - 1,head.y()); }
            case TOP ->    { return new Point(head.x(),head.y() - 1); }
            case RIGHT ->  { return new Point(head.x() + 1,head.y()); }
            case BOTTOM -> { return new Point(head.x(),head.y() + 1); }
            default ->     { return null; }
        }
    }
}
