package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
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

import java.util.ArrayList;
import java.util.HashMap;

public class MyGdxGame extends ApplicationAdapter {

    private String TAG = "MyGdxGame";

    public static final int SW = 1136;
    public static final int SH = 640;

    private Stage stage;

    private Slider slider;
    private float sliderValue;
    private int scrollWidth;

    private Image leftImg;
    private int singleHeight = SH / 7;

    private boolean isLeft;

    private ArrayList<StringRect> stringRects = new ArrayList<>();
    private HashMap<Integer, Integer> stringMap = new HashMap<>();

    private float[] position = {82.2f, 176.4f, 270, 364, 455.4f, 550};

    private String[] diao1 = {"E", "F", "#F", "G", "#G", "A", "#A", "B", "C", "#C", "D", "#D", "E"};
    private String[] diao2 = {"A", "#A", "B", "C", "#C", "D", "#D", "E", "F", "#F", "G", "#G", "A"};
    private String[] diao3 = {"D", "#D", "E", "F", "#F", "G", "#G", "A", "#A", "B", "C", "#C", "D"};
    private String[] diao4 = {"G", "#G", "A", "#A", "B", "C", "#C", "D", "#D", "E", "F", "#F", "G"};
    private String[] diao5 = {"B", "C", "#C", "D", "#D", "E", "F", "#F", "G", "#G", "A", "#A", "B"};
    private String[] diao6 = {"E", "F", "#F", "G", "#G", "A", "#A", "B", "C", "#C", "D", "#D", "E"};
    private String[][] diaoSet = {diao1, diao2, diao3, diao4, diao5, diao6};

    private Texture diaoA;
    private Texture diaoB;
    private Texture diaoC;
    private Texture diaoD;
    private Texture diaoE;
    private Texture diaoF;
    private Texture diaoG;

    private CirclePoolManager circlePoolManager;

    @Override
    public void create() {
        stage = new Stage(new FitViewport(SW, SH));
        circlePoolManager = new CirclePoolManager(stage);
        Texture solobg = new Texture(Gdx.files.internal("solo/bg.jpg"));
        Texture lad = new Texture(Gdx.files.internal("solo/lab.png"));
        Texture pattern = new Texture(Gdx.files.internal("solo/pattern.png"));
        Texture right = new Texture(Gdx.files.internal("solo/right.png"));
        final Texture left = new Texture(Gdx.files.internal("solo/left.png"));
        Texture background = new Texture(Gdx.files.internal("solo/slider_bg.png"));
        Texture knob = new Texture(Gdx.files.internal("solo/slider_knob.png"));


        diaoA = new Texture(Gdx.files.internal("solo/circle/ic_a.png"));
        diaoB = new Texture(Gdx.files.internal("solo/circle/ic_b.png"));
        diaoC = new Texture(Gdx.files.internal("solo/circle/ic_c.png"));
        diaoD = new Texture(Gdx.files.internal("solo/circle/ic_d.png"));
        diaoE = new Texture(Gdx.files.internal("solo/circle/ic_e.png"));
        diaoF = new Texture(Gdx.files.internal("solo/circle/ic_f.png"));
        diaoG = new Texture(Gdx.files.internal("solo/circle/ic_g.png"));

        Image bgimg = new Image(solobg);
        leftImg = new Image(left);
        stage.addActor(bgimg);
        Table container = new Table();
        stage.addActor(container);
        container.setFillParent(true);
        container.setDebug(true);
        container.add(leftImg).width(leftImg.getWidth());
        Gdx.input.setInputProcessor(stage);

        final Table table = new Table();
        table.setDebug(true);
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
            scrollWidth += 130;
            table.add(image);
        }
        container.add(scroll).fill().expand();

        //slider  设置
        final Slider.SliderStyle sliderStyle = new Slider.SliderStyle();
        sliderStyle.background = new TextureRegionDrawable(new TextureRegion(background));
        sliderStyle.knob = new TextureRegionDrawable(new TextureRegion(knob));
        final int max = scrollWidth - SW + left.getWidth();
        scroll.setScrollX(max);
        slider = new Slider(0, max, 1, false, sliderStyle);
        slider.setWidth(background.getWidth());
        slider.setPosition(SW / 2 - background.getWidth() / 2, 0);
        slider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                sliderValue = slider.getValue();
                scroll.setScrollX(slider.getValue());
            }
        });
//        slider.setValue(max);
        //添加slider
        stage.addActor(slider);
        stage.addListener(new InputListener() {

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                judgePinAndXian(x, y, false, pointer);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                judgePinAndXian(x, y, false, pointer);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                judgePinAndXian(x, y, true, pointer);
            }
        });
        initStringRect();
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
        circlePoolManager.dispose();
        stage.dispose();
        scrollWidth = 0;
        isLeft = false;
    }

    private void initStringRect() {
        for (int pin = 0; pin < 13; pin++) {
            for (int string = 1; string < 7; string++) {
                StringRect stringRect = new StringRect();
                if (pin == 0) {
                    stringRect.setPin(pin);
                    stringRect.setString(string);
                    StringRect.GuitarRect guitarRect = stringRect.getGuitarRect();
                    guitarRect.left = 0;
                    guitarRect.right = 225;
                    guitarRect.top = singleHeight * string + singleHeight / 2;
                    guitarRect.bottom = singleHeight * string - singleHeight / 2;
                } else {
                    stringRect.setPin(13 - pin);
                    stringRect.setString(string);
                    StringRect.GuitarRect guitarRect = stringRect.getGuitarRect();
                    guitarRect.left = (pin - 1) * 130 + 225;
                    guitarRect.right = guitarRect.left + 130;
                    guitarRect.top = singleHeight * string + singleHeight / 2;
                    guitarRect.bottom = singleHeight * string - singleHeight / 2;
                }
                stringRects.add(stringRect);
            }
        }
    }

    private void judgePinAndXian(float x, float y, boolean touchUp, int pointer) {
        if (x > 225) {
            x = x + sliderValue;
        }
        for (StringRect actor : stringRects) {
            if (!touchUp && actor.getGuitarRect().isContainer(x, y)) {
                if (!actor.isMoveBefore(pointer)) {
                    actor.setMoveBefore(true, pointer);
                    int string = actor.getString();
                    int pin = actor.getPin();
                    if (pin == 0) {
                        for (Integer string1 : stringMap.keySet()) {
                            if (string == string1) {
                                Integer pin1 = stringMap.get(string1);
                                if (pin1 > 0) {
                                    pin = pin1;
                                    isLeft = true;
                                    break;
                                } else {
                                    isLeft = false;
                                }
                            } else {
                                isLeft = false;
                            }
                        }
                    } else {
                        if (stringMap.containsKey(string)) {
                            stringMap.put(string, Math.max(pin, stringMap.get(string)));
                        } else {
                            stringMap.put(string, pin);
                        }
                    }
                    positionAnimal(string, pin, isLeft);
                }
            } else if (touchUp && actor.getGuitarRect().isContainer(x, y)) {
                actor.setMoveBefore(false, pointer);
                if (actor.getPin() == 0) {
                    return;
                }
                if (stringMap.containsKey(actor.getString())) {
                    stringMap.remove(actor.getString());
                }
            } else if (touchUp && !actor.getGuitarRect().isContainer(x, y)) {
                actor.setMoveBefore(false, pointer);
                if (stringMap.containsKey(actor.getString())) {
                    stringMap.remove(actor.getString());
                }
            }
        }
    }

    private void positionAnimal(int string, int pin, boolean isLeft) {
        String s = diaoSet[string - 1][pin];
        Texture texture;
        //先创建 对应类型的动画 ：
        switch (s) {
            case "A":
            case "#A":
                texture = diaoA;
                break;
            case "B":
                texture = diaoB;
                break;
            case "C":
            case "#C":
                texture = diaoC;
                break;
            case "D":
            case "#D":
                texture = diaoD;
                break;
            case "E":
                texture = diaoE;
                break;
            case "F":
            case "#F":
                texture = diaoF;
                break;
            case "G":
            case "#G":
                texture = diaoG;
                break;
            default:
                texture = diaoD;
        }

        circlePoolManager.setData(texture, s);
        //获取CircleGroup
        CircleGroup circleGroup = circlePoolManager.obtain();
        // 确定位置
        if (isLeft || pin == 0) {
            circleGroup.setPosition(225 / 2 - 132 / 2, position[string - 1] - 132 / 2);
        } else {
            circleGroup.setPosition(225 + (12 - pin) * 130 - sliderValue + 130 / 2 - 132 / 2, position[string - 1] - 132 / 2);
        }
        stage.addActor(circleGroup);
    }
}
