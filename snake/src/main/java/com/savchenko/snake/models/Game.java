package com.savchenko.snake.models;

import com.savchenko.snake.canvases.*;
import com.savchenko.snake.config.SpringConfig;
import com.savchenko.snake.control.ScoreBoard;
import com.savchenko.snake.control.SnakeController;
import com.savchenko.snake.gamecore.GameData;
import com.savchenko.snake.enums.Direction;
import com.savchenko.snake.enums.SceneName;
import com.savchenko.snake.gamecore.AllLets;
import com.savchenko.snake.gamecore.Field;
import com.savchenko.snake.gamecore.PointsOfLets;
import com.savchenko.snake.interfaces.Changeable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private static Timeline timeline;
    private List<Canvas> canvas;
    private Snakes snakes;
    private GameData gameData;
    @Getter
    private Label scoreBoard;

    public Game(GameData gameData, PointsOfLets pointsOfLets, AllLets allLets){
        this.gameData = gameData;
        Field field = gameData.getField();
        BackGround backGround = new BackGround(field);

        Wall wall = new Wall(field, gameData.getComplicity(), pointsOfLets, allLets);
        Snakes snakes  = new Snakes(field, pointsOfLets, allLets);
        Food food = new Food(field, gameData.getCntOfFood(), pointsOfLets, allLets);
        ScoreBoard scoreBoard = new ScoreBoard(snakes);
        this.snakes = snakes;

        List<Changeable> changeables = new ArrayList<>();
        changeables.add(food);
        changeables.add(snakes);
        changeables.add(new AllLets());
        changeables.add(scoreBoard);
        this.scoreBoard = scoreBoard;

        List<Canvas> canvas = new ArrayList<>();
        canvas.add(backGround);
        canvas.add(wall);
        canvas.add(food);
        canvas.add(snakes);

        if(gameData.getField().getColumns() == 10) gameData.setCntOfBoots(0);
        snakes.addBots(gameData.getCntOfBoots());

        changeables.forEach(Changeable::change);


        timeline = new Timeline(new KeyFrame(Duration.millis(501 - gameData.getVelocity()), event -> {
            snakes.move();
            if(snakes.cntOfAlivePlayers() == 0){
                stopPlaying();
                SpringConfig.res(snakes.currentResult());
            }
            changeables.forEach(Changeable::change);
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);

        this.canvas = canvas;
    }

    public void play(){
        timeline.play();
    }

    public List<Canvas> layers(){
        return canvas;
    }

    public  void rule(){
        if(gameData.isSecond()){
            SnakeController snakeController = snakes.addPlayer(gameData.getFirstPlayer());
            SnakeController snakeController1 = snakes.addPlayer(gameData.getSecondPlayer());

            SpringConfig.GetScene(SceneName.PLAY).setOnKeyPressed(event -> {
                KeyCode keyCode = event.getCode();
                if (keyCode == KeyCode.D) snakeController1.setDirection(Direction.RIGHT);
                if (keyCode == KeyCode.W) snakeController1.setDirection(Direction.TOP);
                if (keyCode == KeyCode.A) snakeController1.setDirection(Direction.LEFT);
                if (keyCode == KeyCode.S) snakeController1.setDirection(Direction.BOTTOM);
                if (keyCode == KeyCode.RIGHT) snakeController.setDirection(Direction.RIGHT);
                if (keyCode == KeyCode.UP) snakeController.setDirection(Direction.TOP);
                if (keyCode == KeyCode.LEFT) snakeController.setDirection(Direction.LEFT);
                if (keyCode == KeyCode.DOWN) snakeController.setDirection(Direction.BOTTOM);
            });
        }
        else {
            SnakeController snakeController = snakes.addPlayer(gameData.getFirstPlayer());
            SpringConfig.GetScene(SceneName.PLAY).setOnKeyPressed(event -> {
                KeyCode keyCode = event.getCode();
                if (keyCode == KeyCode.RIGHT) snakeController.setDirection(Direction.RIGHT);
                if (keyCode == KeyCode.UP) snakeController.setDirection(Direction.TOP);
                if (keyCode == KeyCode.LEFT) snakeController.setDirection(Direction.LEFT);
                if (keyCode == KeyCode.DOWN) snakeController.setDirection(Direction.BOTTOM);
            });
        }
    }

    public void stopPlaying(){
        timeline.stop();
    }
}
