package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;

/**
 * @author airsaid
 */
public class CircleGroup extends Group {

    private TextureAtlas textureAtlas;
    private Array<Image> mImages = new Array<>();
    private Texture circleTexture;

    public CircleGroup() {
        textureAtlas = new TextureAtlas("atlas.pack");
        startAnimator();
    }

    public CircleGroup(Texture texture) {
        textureAtlas = new TextureAtlas("atlas.pack");
        circleTexture = texture;
        startAnimator();
    }

    public void startAnimator() {
        initActors();
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

    private void initActors() {
        clearActor();
        for (int i = 0; i < 4; i++) {
            Image image;
            if (circleTexture != null) {
                image = new Image(circleTexture);
            } else {
                image = new Image(textureAtlas.findRegion("c"));
            }
            image.setPosition(Gdx.graphics.getWidth() / 2 - image.getWidth() / 2, Gdx.graphics.getHeight() / 2 - image.getHeight() / 2);
            image.setOrigin(Align.center);
            image.setScale(0f);
            addActor(image);
            mImages.add(image);
        }
    }

    private void clearActor() {
        for (int i = 0; i < mImages.size; i++) {
            Image image = mImages.get(i);
            image.remove();
        }
        mImages.clear();
    }

    private Action getScaleActionForIndex(int index) {
        float duration = 0.6f;
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
            return Actions.delay(duration / 3, Actions.sequence(Actions.delay(duration / 10, Actions.scaleTo(1.0f, 1.0f, duration / 2)), Actions.scaleTo(0.0f, 0.0f, duration / 2)));
        }
        return null;
    }
}
