package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class SliderTest extends ApplicationAdapter {
    Stage stage;
    Slider slider;

    @Override
    public void create() {
        stage = new Stage();
        Texture slider_background = new Texture(Gdx.files.internal("bgbar.png"));
        Texture slider_knob = new Texture(Gdx.files.internal("bar.png"));
        Slider.SliderStyle sliderStyle = new Slider.SliderStyle();
        sliderStyle.background = new TextureRegionDrawable(new TextureRegion(slider_background));
        sliderStyle.knob = new TextureRegionDrawable(new TextureRegion(slider_knob));
        slider = new Slider(0, slider_background.getWidth(), 1, false, sliderStyle);
        slider.setWidth(slider_background.getWidth());
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log("TAG", "slider changed to: " + slider.getValue());
            }
        });
        stage.addActor(slider);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.39f, 0.58f, 0.92f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
