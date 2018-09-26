package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ProgressBarTest extends ApplicationAdapter {

    private Stage stage;
    private ProgressBar progressBar;
    private Texture background;
    private Texture knob;
    private float stateTime;
    private int count;
    private boolean isCount;

    @Override
    public void create() {
        stage = new Stage();
        background = new Texture(Gdx.files.internal("bgbar.png"));
        knob = new Texture(Gdx.files.internal("bar.png"));
        ProgressBar.ProgressBarStyle progressBarStyle = new ProgressBar.ProgressBarStyle();
        progressBarStyle.background = new TextureRegionDrawable(new TextureRegion(background));
        progressBarStyle.knob = new TextureRegionDrawable(new TextureRegion(knob));
        progressBar = new ProgressBar(1, 100, 1, false, progressBarStyle);
        progressBar.setWidth(background.getWidth());
        stage.addActor(progressBar);
        progressBar.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log("TAG", "slider changed to: " + progressBar.getValue());
            }
        });
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stateTime += Gdx.graphics.getDeltaTime();
        // knob 每1秒钟移动一步, 移动到最大值knob自己停止，但是为了不再计数，加一个判断条件
        if (stateTime > 1 && count <= 100) {
            count++;
            stateTime = 0;
            progressBar.setValue(count);
        }
        // 监听bar Value的值是否达到最大
        if (isCount && progressBar.getValue() == 100) {
            isCount = false;
            System.out.println("get max value");
        }

        stage.act();
        stage.draw();
    }
}
