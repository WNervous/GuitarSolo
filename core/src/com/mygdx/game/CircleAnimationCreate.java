package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Pool;

import java.util.ArrayList;
import java.util.Iterator;

public class CircleAnimationCreate {

    private Texture texture;
    private String s;
    private ArrayList<CircleGroup> circleGroups = new ArrayList<>();

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
        CircleGroup obtain = circleGroupPool.obtain();
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
