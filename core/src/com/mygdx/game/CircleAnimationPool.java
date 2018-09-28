package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class CircleAnimationPool {

    private Texture texture;
    private String s;
    private Stage stage;

    public CircleAnimationPool(Stage stage) {
        this.stage = stage;
    }

//    private final Pool<CircleGroup> circleGroupPoolA = new Pool<CircleGroup>() {
//        @Override
//        protected CircleGroup newObject() {
//            return new CircleGroup(texture, "A");
//        }
//    };
//    private final Pool<CircleGroup> circleGroupPoolB = new Pool<CircleGroup>() {
//        @Override
//        protected CircleGroup newObject() {
//            return new CircleGroup(texture, "B");
//        }
//    };
//    private final Pool<CircleGroup> circleGroupPoolC = new Pool<CircleGroup>() {
//        @Override
//        protected CircleGroup newObject() {
//            return new CircleGroup(texture, "C");
//        }
//    };
//    private final Pool<CircleGroup> circleGroupPoolD = new Pool<CircleGroup>() {
//        @Override
//        protected CircleGroup newObject() {
//            return new CircleGroup(texture, "D");
//        }
//    };
//    private final Pool<CircleGroup> circleGroupPoolE = new Pool<CircleGroup>() {
//        @Override
//        protected CircleGroup newObject() {
//            return new CircleGroup(texture, "E");
//        }
//    };
//    private final Pool<CircleGroup> circleGroupPoolF = new Pool<CircleGroup>() {
//        @Override
//        protected CircleGroup newObject() {
//            return new CircleGroup(texture, "F");
//        }
//    };
//    private final Pool<CircleGroup> circleGroupPoolG = new Pool<CircleGroup>() {
//        @Override
//        protected CircleGroup newObject() {
//            return new CircleGroup(texture, "G");
//        }
//    };
//    private final Pool<CircleGroup> circleGroupPoolAPlus = new Pool<CircleGroup>() {
//        @Override
//        protected CircleGroup newObject() {
//            return new CircleGroup(texture, "#A");
//        }
//    };
//    private final Pool<CircleGroup> circleGroupPoolCPlus = new Pool<CircleGroup>() {
//        @Override
//        protected CircleGroup newObject() {
//            return new CircleGroup(texture, "#C");
//        }
//    };
//    private final Pool<CircleGroup> circleGroupPoolDPlus = new Pool<CircleGroup>() {
//        @Override
//        protected CircleGroup newObject() {
//            return new CircleGroup(texture, "#D");
//        }
//    };
//    private final Pool<CircleGroup> circleGroupPoolFPlus = new Pool<CircleGroup>() {
//        @Override
//        protected CircleGroup newObject() {
//            return new CircleGroup(texture, "#F");
//        }
//    };
//    private final Pool<CircleGroup> circleGroupPoolGPlus = new Pool<CircleGroup>() {
//        @Override
//        protected CircleGroup newObject() {
//            return new CircleGroup(texture, "#G");
//        }
//    };

    private final Pool<CircleGroup> circleGroupPool = new Pool<CircleGroup>() {
        @Override
        protected CircleGroup newObject() {
            return new CircleGroup();
        }

        @Override
        protected void reset(CircleGroup object) {
            object.clearData();
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
