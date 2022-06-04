package com.savchenko.snake.canvases;

import com.savchenko.snake.enums.Complicity;
import com.savchenko.snake.gamecore.AllLets;
import com.savchenko.snake.gamecore.Field;
import com.savchenko.snake.gamecore.Point;
import com.savchenko.snake.gamecore.PointsOfLets;
import com.savchenko.snake.interfaces.Let;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import lombok.Getter;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Wall extends Canvas implements Let {
    private Set<Point> points = new HashSet<>();
    private Field field;
    private Random random = new Random();
    private PointsOfLets pointsOfLets;
    @Getter
    private static int cntOfWalls;

    public Wall(Field field, Complicity complicity, PointsOfLets pointsOfLets, AllLets allLets){
        super(field.getWidth(), field.getHeight());
        this.pointsOfLets = pointsOfLets;
        this.field = field;
        allLets.addLet(this);
        generate(complicity);
        cntOfWalls = 0;
        draw();
        frame();
    }

    private void generate(Complicity complicity){
        int cntOfBars = (field.getHeight() / field.getSquare()) * (field.getWidth() / field.getSquare());

        switch (complicity){
            case NOOB -> cntOfBars = 0;
            case EASY -> cntOfBars = (int)(cntOfBars * 0.04);
            case MEDIUM -> cntOfBars = (int)(cntOfBars * 0.10);
            case HARD -> cntOfBars = (int)(cntOfBars * 0.15);
        }

        for (int i = 0; i< cntOfBars; i++) generateWalls();
    }

    private void generateWalls(){
        Point point;
        do {
            point = new Point(Math.abs(random.nextInt()) % field.getColumns(), Math.abs(random.nextInt()) % field.getRows());
        } while (points.contains(point) || pointsOfLets.contains(point));
        points.add(point);
        pointsOfLets.addWallPoint(point);
        cntOfWalls++;
    }

    private void frame(){
        Point point;
        for(int i = -1; i < field.getRows()+1;i++) {
            point=new Point(-1, i);
            points.add(point);
            pointsOfLets.addWallPoint(point);
            point=new Point(field.getColumns(), i);
            points.add(point);
            pointsOfLets.addWallPoint(point);
            cntOfWalls += 2;
        }
        for(int i = -1; i < field.getColumns()+1;i++) {
            point=new Point(i,-1);
            points.add(point);
            pointsOfLets.addWallPoint(point);
            point=new Point(i, field.getRows());
            points.add(point);
            pointsOfLets.addWallPoint(point);
            cntOfWalls += 2;
        }
    }

    public boolean hasWall(Point point){
        return points.contains(point);
    }

    private void draw(){
        getGraphicsContext2D().setFill(Color.rgb(110,110,110));
        for(Point point: points)
            getGraphicsContext2D().fillRoundRect(point.x()* field.getSquare(), point.y() * field.getSquare(), field.getSquare(), field.getSquare(), field.getSquare() / 2, field.getSquare() / 2);
    }

    @Override
    public void actionWithSnake(Snakes.Snake snake) {
        if(points.contains(snake.getHead())) {
            snake.die();
            pointsOfLets.addLetPoint(snake.getHead());
        }
    }
}
