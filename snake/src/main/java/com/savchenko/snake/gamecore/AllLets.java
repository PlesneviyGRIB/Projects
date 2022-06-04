package com.savchenko.snake.gamecore;

import com.savchenko.snake.canvases.Snakes;
import com.savchenko.snake.interfaces.Changeable;
import com.savchenko.snake.interfaces.Let;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Component
public class AllLets implements Changeable {
    private List<Let> lets = new ArrayList<>();

    public void addLet(Let let){
        lets.add(let);
    }

    public void rmLet(Let let){
        lets.remove(let);
    }

    @Override
    public void change() {
        ArrayList<Let> newLets = new ArrayList<>(lets);

        for(Let let: lets)
            if((let.getClass().equals(Snakes.Snake.class) && !((Snakes.Snake)let).isAlive())) newLets.remove(let);

        lets = newLets;
    }
}
