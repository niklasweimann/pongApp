package pong.hapra.pong03;

/**
 * Verwaltet die Geschwindigkeit und Richtung der Spielelemente
 * @author Jan Bauerdick
 *
 */
public class Speed {

    public static final int X_DIRECTION_RIGHT = 1;
    public static final int X_DIRECTION_LEFT = -1;
    public static final int Y_DIRECTION_DOWN = 1;
    public static final int Y_DIRECTION_UP = -1;

    private int _x = 0;
    private int _y = 0;

    private int _xDirection = X_DIRECTION_RIGHT;
    private int _yDirection = Y_DIRECTION_DOWN;

    public int getXDirection() {
        return _xDirection;
    }

    public void setXDirection(int direction) {
        _xDirection = direction;
    }

    public void toggleXDirection() {
        if (_xDirection == X_DIRECTION_RIGHT) {
            _xDirection = X_DIRECTION_LEFT;
        } else {
            _xDirection = X_DIRECTION_RIGHT;
        }
    }

    public int getYDirection() {
        return _yDirection;
    }

    public void setYDirection(int direction) {
        _yDirection = direction;
    }

    public void toggleYDirection() {
        if (_yDirection == Y_DIRECTION_DOWN) {
            _yDirection = Y_DIRECTION_UP;
        } else {
            _yDirection = Y_DIRECTION_DOWN;
        }
    }

    public int getX() {
        return _x;
    }

    public void setX(int speed) {
        _x = speed;
    }

    public int getY() {
        return _y;
    }

    public void setY(int speed) {
        _y = speed;
    }

}
