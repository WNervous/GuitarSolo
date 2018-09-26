package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MyGdxGame extends ApplicationAdapter {
    private String TAG = "MyGdxGame";
    private Stage stage;
    private Slider slider;
    private int scrollWidth;

    @Override
    public void create() {
        stage = new Stage(new FitViewport(1136, 640));

        Texture solobg = new Texture(Gdx.files.internal("solo/bg.jpg"));
        Texture lad = new Texture(Gdx.files.internal("solo/lab.jpg"));
        Texture pattern = new Texture(Gdx.files.internal("solo/pattern.jpg"));
        Texture right = new Texture(Gdx.files.internal("solo/right.png"));
        final Texture left = new Texture(Gdx.files.internal("solo/left.png"));

        Image bgimg = new Image(solobg);
        final Image leftImg = new Image(left);
        stage.addActor(bgimg);
        stage.addActor(leftImg);

        Table container = new Table();
        stage.addActor(container);
        container.setFillParent(true);
        container.setPosition(left.getWidth(), 0);
        Gdx.input.setInputProcessor(stage);

        final Table table = new Table();
        final ScrollPane scroll = new ScrollPane(table);
        scroll.setFlickScroll(false);
        scroll.setScrollingDisabled(false, true);
        table.defaults().expand();
        for (int i = 12; i > 0; i--) {
            final Image image;
            switch (i) {
                case 12:
                case 9:
                case 7:
                case 5:
                case 3:
                    image = new Image(lad);
                    break;
                case 2:
                case 4:
                case 6:
                case 8:
                case 10:
                case 11:
                    image = new Image(pattern);
                    break;
                case 1:
                    image = new Image(right);
                    break;
                default:
                    image = new Image(pattern);
            }
            scrollWidth += image.getWidth();
            Gdx.app.log(TAG, "EACH IMG" + image.getWidth());
            table.add(image);
        }
        Gdx.app.log(TAG, "TOTAL WIDTH;" + scrollWidth);
        scroll.addListener(new InputListener() {
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                Gdx.app.log("scrollpane", "touch: " + x + "___" + y);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return super.touchDown(event, x, y, pointer, button);
            }
        });
        new Group().addActor(scroll);
        container.add(scroll).fill().expand();

        //slider  设置
        Slider.SliderStyle sliderStyle = new Slider.SliderStyle();
        Texture background = new Texture(Gdx.files.internal("solo/slider_bg.png"));
        Texture knob = new Texture(Gdx.files.internal("solo/slider_knob.png"));
        sliderStyle.background = new TextureRegionDrawable(new TextureRegion(background));
        sliderStyle.knob = new TextureRegionDrawable(new TextureRegion(knob));
        slider = new Slider(0, scrollWidth - 1136 + left.getWidth(), 1, false, sliderStyle);
        scroll.getMaxX();
        slider.setWidth(background.getWidth());
        slider.setPosition(1136 / 2 - background.getWidth() / 2, 0);
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log(TAG, "slider:" + slider.getValue());
                scroll.scrollTo(slider.getValue(),0,1136,640);
            }
        });
        //添加slider
        stage.addActor(slider);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        scrollWidth = 0;
    }
}
