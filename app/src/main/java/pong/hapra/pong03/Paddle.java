package pong.hapra.pong03;

import android.graphics.Bitmap;

/**
 * Zeichnet einen Schlaeger auf dem Display
 * @author Jan Bauerdick
 *
 */
public class Paddle extends GameObject {

    public Paddle(Bitmap bitmap, int width, int height, boolean cpu) {
        super(bitmap);
        getCoordinates().setX(width / 2 - bitmap.getWidth() / 2);
        getCoordinates().setY(cpu ? height - 10 - bitmap.getHeight() / 2 : 10);
    }

}