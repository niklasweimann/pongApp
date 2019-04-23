package pong.hapra.pong03;

import android.graphics.Bitmap;

/**
 * Abstrakte Oberklasse aller Spielelemente
 * @author Jan Bauerdick
 *
 */
public abstract class GameObject {

    private Bitmap bitmap;
    private Coordinates coordinates;
    private Speed speed;

    public GameObject(Bitmap bitmap) {
        this.bitmap = bitmap;
        this.coordinates = new Coordinates(bitmap);
        this.speed = new Speed();
    }

    public Bitmap getGraphic() {
        return bitmap;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Speed getSpeed() {
        return speed;
    }

}

