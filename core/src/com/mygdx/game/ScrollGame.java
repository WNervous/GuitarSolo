package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ScrollGame extends ApplicationAdapter {
    Texture img;
    Stage stage;
    ScrollPane scrollPane;
    Group group;

    @Override
    public void create() {
        img = new Texture("badlogic.jpg");
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        final float w = Gdx.graphics.getWidth();
        final float h = Gdx.graphics.getHeight();

        group = new Group();
        group.setSize(2 * w, h);
        scrollPane = new ScrollPane(group);
        scrollPane.setSize(w, h);
        scrollPane.setFlickScroll(false);

        stage.addActor(scrollPane);
        Image image1 = new Image(img);
        image1.setPosition(w * .5f, h / 2f);
        image1.setScale(.5f);
        group.addActor(image1);

        Image image2 = new Image(img);
        image2.setPosition(w * .75f, h / 3f);
        image2.setScale(.5f);
        group.addActor(image2);

        stage.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {

                float offsetPositionX = w * .5f;
                float offsetPositionY = 0;

                scrollPane.scrollTo(offsetPositionX, offsetPositionY, w, h);

                super.clicked(event, x, y);
            }
        });
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        img.dispose();
        stage.dispose();
    }
}

