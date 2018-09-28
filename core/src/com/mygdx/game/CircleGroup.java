package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

/**
 * @author airsaid
 */
public class CircleGroup extends Group {

    private Array<Image> mImages = new Array<>();
    private Texture circleTexture;
    private Label label;
    private String mString;
    private boolean isFinish;

    CircleGroup() {
        setSize(132, 132);
    }

    public void setData(Texture circleTexture, String s) {
        this.circleTexture = circleTexture;
        this.mString = s;
        startAnimator();
        setSize(132, 132);
    }

    private void initActors() {
        clearActor();
        BitmapFont font = new BitmapFont(Gdx.files.internal("san_caption.fnt"), new TextureRegion(new Texture(Gdx.files.internal("san_caption.png"))));
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
        label = new Label(mString, labelStyle);
        label.setSize(132, 132);
        label.setAlignment(Align.center);
        label.setOrigin(Align.center);
        for (int i = 0; i < 4; i++) {
            Image image = new Image(circleTexture);
            image.setOrigin(Align.center);
            image.setScale(0f);
            addActor(image);
            mImages.add(image);
        }
        addActor(label);
    }

    private void startAnimator() {
        initActors();
        isFinish = false;
        DelayAction alpha = Actions.delay(0.4f, Actions.alpha(0, 0.3f));
        label.addAction(alpha);
        for (int i = 0; i < mImages.size; i++) {
            Image image = mImages.get(i);
            if (!image.hasActions()) {
                Action action = getScaleActionForIndex(i);
                if (action != null) {
                    image.addAction(action);
                }
            }
        }
    }


    private void clearActor() {
        for (int i = 0; i < mImages.size; i++) {
            Image image = mImages.get(i);
            image.remove();
        }
        mImages.clear();
        if (label != null) {
            label.remove();
            label.clear();
        }
    }

    public void clearData() {
        clearActor();
        circleTexture = null;
        mString = null;
    }

    private Action getScaleActionForIndex(int index) {
        float duration = 0.5f;
        if (index == 0) {
            // 第一个球先开始扩大
            return Actions.sequence(Actions.scaleTo(1.0f, 1.0f, duration / 2), Actions.parallel(Actions.alpha(0f, duration / 6), Actions.scaleTo(0.0f, 0.0f, duration / 2)));
        } else if (index == 1) {
            // 第二个球等待一小会后也开始扩大
            return Actions.sequence(Actions.delay(duration / 10, Actions.scaleTo(1.0f, 1.0f, duration / 2)), Actions.parallel(Actions.alpha(0f, duration / 6), Actions.scaleTo(0.0f, 0.0f, duration / 2)));
        } else if (index == 2) {
            // 第三个球和第四个球也开始相继开始扩大
            return Actions.delay(duration / 3, Actions.sequence(Actions.scaleTo(1.0f, 1.0f, duration / 2), Actions.scaleTo(0.0f, 0.0f, duration / 2)));
        } else if (index == 3) {
            return Actions.delay(duration / 3, Actions.sequence(Actions.delay(duration / 10, Actions.scaleTo(1.0f, 1.0f, duration / 2)), Actions.scaleTo(0.0f, 0.0f, duration / 2), Actions.run(new Runnable() {
                @Override
                public void run() {
                    isFinish = true;
                    if (listener != null) {
                        listener.animalEnd();
                    }
                    Gdx.app.log("CircleGroup", "Finish");
                }
            })));
        }
        return null;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }

    public AnimalEndListener listener;

    public void setListener(AnimalEndListener listener) {
        this.listener = listener;
    }

    public interface AnimalEndListener {
        void animalEnd();
    }
}
