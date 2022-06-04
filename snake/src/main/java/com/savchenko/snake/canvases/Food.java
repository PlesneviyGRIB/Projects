package com.savchenko.snake.canvases;

import com.savchenko.snake.gamecore.AllLets;
import com.savchenko.snake.gamecore.Point;
import com.savchenko.snake.gamecore.PointsOfLets;
import com.savchenko.snake.interfaces.Changeable;
import com.savchenko.snake.interfaces.Let;
import com.savchenko.snake.gamecore.Field;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import java.io.File;
import java.util.*;

public class Food extends Canvas implements Let, Changeable {
    private final Field field;
    private final Map<Point,Image> foodLocation = new HashMap<>();
    private final Random random = new Random();
    private PointsOfLets pointsOfLets;

    private static final String PATH = "/home/egor/IdeaProjects/snake/src/main/resources/com/savchenko/snake/data/FoodImages";
    private static Image[] images;
    static{
        File[] imageFiles = new File(PATH).listFiles((dir, name) -> name.matches("\\w+.png"));
        images = new Image[imageFiles.length];

        for (int i = 0; i< images.length;i++)
            images[i] = new Image(imageFiles[i].toURI().toString());
    }

    public Food(Field field, int cntOfFood, PointsOfLets     pointsOfLets, AllLets allLets) {
        super(field.getWidth(),field.getHeight());
        this.field = field;
        this.pointsOfLets = pointsOfLets;
        for(int i = 0; i<calculateFood(cntOfFood); ++i) addFood();
        allLets.addLet(this);
        change();
    }

    private int calculateFood(int cnt){
        if(cnt == 1) return 1;
        return (int)(((double)field.getRows() * field.getColumns()) / 100 * cnt);
    }

    private void addFood(){
        if(field.getRows() * field.getColumns() - (pointsOfLets.count() - Wall.getCntOfWalls()) > 10) {
            Point point;
            do {
                point = new Point(Math.abs(random.nextInt()) % field.getColumns(), Math.abs(random.nextInt()) % field.getRows());
            } while (foodLocation.containsKey(point) || pointsOfLets.contains(point));

            pointsOfLets.addLetPoint(point);
            foodLocation.put(point, images[Math.abs(random.nextInt()) % images.length]);
        }
    }

    private void rmFood(Point point){
        foodLocation.remove(point);
        addFood();
        pointsOfLets.rmLetPoint(point);
    }

    @Override
    public void change(){
        getGraphicsContext2D().clearRect(0,0, field.getWidth(), field.getHeight());
        for (Map.Entry<Point,Image> entry: foodLocation.entrySet()){
            getGraphicsContext2D().drawImage(entry.getValue(), entry.getKey().x() * field.getSquare(), entry.getKey().y() * field.getSquare(), field.getSquare(), field.getSquare());
        }
    }

    @Override
    public void actionWithSnake(Snakes.Snake snake) {
        if(foodLocation.containsKey(snake.getHead())){
            rmFood(snake.getHead());
            snake.addNode();
        }
    }
}