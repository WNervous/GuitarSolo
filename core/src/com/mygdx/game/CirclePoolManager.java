package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class CirclePoolManager {

    private Texture texture;
    private String s;
    private Stage stage;

    CirclePoolManager(Stage stage) {
        this.stage = stage;
    }

    private final Pool<CircleGroup> circleGroupPool = new Pool<CircleGroup>() {
        @Override
        protected CircleGroup newObject() {
            return new CircleGroup();
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
        int free = circleGroupPool.getFree();
        LogUtil.log("circleGroupPool", "" + free);
        final CircleGroup obtain = circleGroupPool.obtain();
        obtain.setData(texture, s);
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
        return obtain;
    }

    public void dispose() {
        stage = null;
    }
}
