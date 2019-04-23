package pong.hapra.pong03;

import android.graphics.Bitmap;

/**
 * Die Koordinaten eines Objekts auf dem Display
 * @author Jan Bauerdick
 *
 */
public class Coordinates {

    private Bitmap bitmap;
    private int x;
    private int y;

    public Coordinates(Bitmap bitmap) {
        this.bitmap = bitmap;
        x = 0;
        y = 0;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap b) {
        bitmap = b;
    }

    public int getX() {
        return x + bitmap.getWidth();
    }

    public int getY() {
        return y + bitmap.getHeight();
    }

    public void setX(int value) {
        x = value - bitmap.getWidth();
    }

    public void setY(int value) {
        y = value - bitmap.getHeight();
    }

    public String toString() {
        return "Coordinates: (" + x + ", " + y + ")";
    }

}
