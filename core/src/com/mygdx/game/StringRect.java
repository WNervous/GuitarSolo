package play.to.how.sketch.lessons.guitar.meevii.com.easyguitarnew.solo;

import java.util.HashMap;


/**
 * Created by JiaWeichen on 2018/6/8.
 */

public class StringRect {
    private int string;
    private GuitarRect guitarRect;
    private HashMap<Integer, Boolean> pointerHolder = new HashMap<Integer, Boolean>();

    public boolean isMoveBefore(int pointIndex) {
        if (pointerHolder.containsKey(pointIndex)) {
            return pointerHolder.get(pointIndex);
        }
        return false;
    }

    public void setMoveBefore(boolean moveBefore, int pointerIndex) {
        pointerHolder.put(pointerIndex, moveBefore);
    }

    public GuitarRect getGuitarRect() {
        return guitarRect;
    }

    public void setGuitarRect(GuitarRect guitarRect) {
        this.guitarRect = guitarRect;
    }

    public int getString() {
        return string;
    }

    public void setString(int string) {
        this.string = string;
    }


    public static class GuitarRect {
        public float left;
        public float right;
        public float top;
        public float bottom;

        public GuitarRect() {
        }

        public GuitarRect(float left, float right, float top, float bottom) {
            this.left = left;
            this.right = right;
            this.top = top;
            this.bottom = bottom;
        }

        public boolean isContainer(float x, float y) {
            return x > left && x < right && y > bottom && y < top;
        }
    }
}
