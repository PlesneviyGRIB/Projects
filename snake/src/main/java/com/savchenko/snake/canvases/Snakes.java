package com.savchenko.snake.canvases;

import com.savchenko.snake.control.SnakeBotController;
import com.savchenko.snake.control.SnakeController;
import com.savchenko.snake.enums.Direction;
import com.savchenko.snake.enums.Type;
import com.savchenko.snake.gamecore.AllLets;
import com.savchenko.snake.gamecore.Field;
import com.savchenko.snake.gamecore.Point;
import com.savchenko.snake.gamecore.PointsOfLets;
import com.savchenko.snake.interfaces.Changeable;
import com.savchenko.snake.interfaces.Let;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

public class Snakes extends Canvas implements Changeable {
    private PointsOfLets pointsOfLets;

    public class Snake implements Let {
        private long smoothStart = 5;

        private Point head;
        private Queue<Point> body = new LinkedList<>();
        private Direction currentDirection;
        private boolean isAlive = true;
        private boolean grow;
        private boolean canChange;
        private final Type type;
        private AllLets allLets;
        @Getter
        private String name;

        public Snake(Point head, Direction direction, Type type, AllLets allLets, String name){
            this.name = name;
            this.head = head;
            this.allLets = allLets;
            this.currentDirection = direction;
            this.type = type;
            snakes.add(this);
            allLets.addLet(this);
        }

        public void move(){
            if(isAlive) {
                canChange = true;
                body.add(head);
                head = newHead(currentDirection, head);
                if(boundCheck(head) && smoothStart <= 0) {
                    this.die();
                    allLets.rmLet(this);
                    pointsOfLets.rmLetPoint(new ArrayList<>(body));
                }
                pointsOfLets.addLetPoint(head);
                if (!grow) pointsOfLets.rmLetPoint(body.remove());
                grow = false;
                checkLets();
            }
        }

        private boolean boundCheck(Point head){
            if(head.x() < 0 || head.x() > field.getColumns() -1) return true;
            if(head.y() < 0 || head.y() > field.getRows() -1) return true;
            return false;
        }

        public void addNode(){
            grow = true;
            pointsOfLets.addLetPoint(head);
        }

        public void setDirection(Direction direction){
            if(canChange){
                if(direction.ordinal() % 2 != currentDirection.ordinal() % 2)
                    currentDirection = direction;
                canChange = false;
            }
        }

        public void die(){
            pointsOfLets.rmLetPoint(head);
            pointsOfLets.rmLetPoint(new ArrayList<>(body));
            isAlive = false;
        }

        public int getLength(){
            return body.size() + 1;
        }

        public boolean containsNode(Point point){
            return body.contains(point);
        }

        public boolean isAlive(){
            return isAlive;
        }

        public Point getHead(){
            return head;
        }

        public List<Point> getBody(){
            return body.stream().toList();
        }

        private Point newHead(Direction current, Point head){
            switch (current){
                case LEFT ->   { return new Point(head.x() - 1,head.y()); }
                case TOP ->    { return new Point(head.x(),head.y() - 1); }
                case RIGHT ->  { return new Point(head.x() + 1,head.y()); }
                case BOTTOM -> { return new Point(head.x(),head.y() + 1); }
                default ->     { return head; }
            }
        }

        private void checkLets(){
            if(type == Type.PLAYER && smoothStart-- > 0) return;
            for(Let let: allLets.getLets()) {
                let.actionWithSnake(this);
            }
        }

        public void cutBody(Point point){
            Point tmp;
            if(body.contains(point)){
                do{
                    tmp = body.poll();
                    pointsOfLets.rmLetPoint(tmp);
                }
                while(!tmp.equals(point));
            }
        }

        public Direction getCurrentDirection(){
            return currentDirection;
        }

        @Override
        public void actionWithSnake(Snake snake) {
            if(!this.isAlive || !snake.isAlive) return;
            if(snake == this && body.contains(snake.getHead())) {
                die();
                return;
            }
            if(snake != this && head.equals(snake.head)){
                die();
                snake.die();
                return;
            }
            if(body.contains(snake.getHead())){
                cutBody(snake.getHead());
                snake.addNode();
            }
        }
    }

    private class InitSnake{
        private final Random random = new Random();
        private final Field field;
        private final AllLets allLets;
        private String[] names = new String[]{"Sasha", "Ara", "Nebula", "Igor", "Bot", "Blob", "Pro", "Noob", "Champion", "Darkness", "Glory", "Brother", "Snake"};

        public InitSnake(Field field, AllLets allLets) {
            this.allLets = allLets;
            this.field = field;
        }

        public Snake nextBot(){
            Point head;
            Direction direction = Direction.values()[Math.abs(random.nextInt()) % Direction.values().length];

            do {
                head = new Point(Math.abs(random.nextInt(field.getColumns())), Math.abs(random.nextInt(field.getRows())));
            } while(pointsOfLets.contains(head));

            pointsOfLets.addLetPoint(head);

            return new Snake(head, direction, Type.BOT, allLets, names[random.nextInt(names.length)]);
        }

        public Snake nextPlayer(String name){
            Point head;
            Direction direction = Direction.values()[Math.abs(random.nextInt()) % Direction.values().length];

            do {
                head = new Point(Math.abs(random.nextInt(field.getColumns())), Math.abs(random.nextInt(field.getRows())));
            } while(pointsOfLets.contains(head));

            pointsOfLets.addLetPoint(head);

            return new Snake(head, direction, Type.PLAYER, allLets, name);
        }
    }

    private List<Snake> snakes = new ArrayList<>();
    private final Field field;
    private final InitSnake initSnake;
    private List<SnakeBotController> snakeBotControllers = new ArrayList<>();
    private AllLets allLets;

    public int cntOfAlivePlayers(){
        return (int) snakes.stream().filter(snake -> snake.type == Type.PLAYER && snake.isAlive).count();
    }

    public Snakes(Field field, PointsOfLets pointsOfLets, AllLets allLets){
        super(field.getWidth(), field.getHeight());
        this.allLets = allLets;
        this.pointsOfLets = pointsOfLets;
        this.field = field;
        initSnake = new InitSnake(field, allLets);
    }


    public void addBots(int cntOfSnakeBots){
        for (int i = 0; i <  cntOfSnakeBots; i++) {
            snakeBotControllers.add(new SnakeBotController(initSnake.nextBot(),allLets));
        }
    }
    
    public SnakeController addPlayer(String name){
        return new SnakeController(initSnake.nextPlayer(name));
    }

    public void move(){
        ruleBots();
        for(Snake snake: snakes) snake.move();
    }

    private void ruleBots(){
        int i = 0;
        for(SnakeBotController snakeBotController: snakeBotControllers) if(!snakeBotController.botAlive()) i++;
        addBots(i);
        snakeBotControllers = snakeBotControllers.stream().filter(SnakeBotController::botAlive).collect(Collectors.toList());
        snakeBotControllers.forEach(SnakeBotController::guess);
    }

    @Override
    public void change() {
        getGraphicsContext2D().clearRect(0,0, field.getWidth(), field.getHeight());
        snakes.forEach(snake -> {
            switch (snake.type){
                case BOT -> {drawSnake(snake, Color.rgb(255, 0,0), Color.rgb(255,130,205)); return;}
                case PLAYER -> drawSnake(snake, Color.rgb(0,0,255), Color.rgb(0,230,255));
                default -> drawSnake(snake, Color.rgb(0,0,0), Color.rgb(100,100,100));
            }
        });
    }

    public List<Map.Entry<String, Integer>> currentResult(){
        List<Map.Entry<String,Integer>> list = new ArrayList<>();

        snakes.forEach(snake -> list.add(new Map.Entry<>() {
            @Override
            public String getKey() {
                return snake.name;
            }

            @Override
            public Integer getValue() {
                return snake.getLength();
            }

            @Override
            public Integer setValue(Integer value) {
                return null;
            }
        }));
        return list.stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed()).toList();
    }

    private void drawSnake(Snake snake, Color head, Color body){
        if(!snake.isAlive()) return;

        getGraphicsContext2D().setFill(body);
        for (Point point: snake.getBody())
            getGraphicsContext2D().fillRoundRect(point.x() * field.getSquare(), point.y() * field.getSquare(), field.getSquare(), field.getSquare(), field.getSquare() - 8, field.getSquare() - 8);

        getGraphicsContext2D().setFill(head);
        getGraphicsContext2D().fillRoundRect(snake.getHead().x() * field.getSquare(), snake.getHead().y() * field.getSquare(), field.getSquare(), field.getSquare(), field.getSquare(), field.getSquare());
    }
}
