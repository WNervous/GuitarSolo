package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

import java.util.ArrayList;
import java.util.Iterator;

public class CircleAnimationCreate {

    private Texture texture;
    private String s;
    private ArrayList<CircleGroup> circleGroups = new ArrayList<>();
    private Stage stage;

    public CircleAnimationCreate(Stage stage) {
        this.stage = stage;
    }

    private final Pool<CircleGroup> circleGroupPool = new Pool<CircleGroup>() {
        @Override
        protected CircleGroup newObject() {
            return new CircleGroup(texture, s);
        }
    };

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public void setData(Texture texture, String s) {
        this.texture = texture;
        this.s = s;
    }

    public CircleGroup obtain() {
        final CircleGroup obtain = circleGroupPool.obtain();
        obtain.setListener(new CircleGroup.AnimalEndListener() {
            @Override
            public void animalEnd() {
                circleGroupPool.free(obtain);
                Array<Actor> actors = stage.getActors();
                for (Actor actor : actors) {
                    if (actor instanceof CircleGroup && ((CircleGroup) actor).isFinish()) {
                        actor.addAction(Actions.removeActor());
                    }
                }
            }
        });
        circleGroups.add(obtain);
        return obtain;
    }

    public void reset() {
        Iterator<CircleGroup> iterator = circleGroups.iterator();
        while (iterator.hasNext()) {
            CircleGroup circleGroup = iterator.next();
            if (circleGroup.isFinish()) {
                circleGroup.setFinish(false);
                circleGroupPool.free(circleGroup);
                iterator.remove();
            }
        }
    }
}
