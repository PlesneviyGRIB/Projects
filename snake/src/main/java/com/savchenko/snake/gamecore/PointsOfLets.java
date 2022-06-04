package com.savchenko.snake.gamecore;

import lombok.NoArgsConstructor;
import java.util.*;

@NoArgsConstructor
public class PointsOfLets {

    private Set<Point> points = new HashSet<>();
    private Set<Point> walls = new HashSet<>();

    public void addLetPoint(Point point){
        points.add(point);
    }

    public void addWallPoint(Point point){
        points.add(point);
        walls.add(point);
    }

    public void addLetPoint(List<Point> points){
        points.forEach(this::addLetPoint);
    }

    public boolean contains(Point point){
        return points.contains(point);
    }

    public boolean contains(List<Point> points){
        return points.stream().allMatch(this::contains);
    }

    public void rmLetPoint(Point point){
        if(!walls.contains(point)) points.remove(point);
    }

    public void rmLetPoint(List<Point> points){
        points.forEach(this::rmLetPoint);
    }

    public int  count(){return points.size();}
}
