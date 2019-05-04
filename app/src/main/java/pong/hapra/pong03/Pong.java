package pong.hapra.pong03;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.StrictMode;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Spiellogik
 *
 * @author Jan Bauerdick
 */
public class Pong extends SurfaceView implements SurfaceHolder.Callback, Orientation.Callback {

    private final ArrayList<GameObject> elements = new ArrayList<>();
    private GameThread thread;
    private Context parentContext;
    private boolean gameActive;
    private int acceleration;
    Orientation orientation;
    private float ori_X;

    public Pong(Context context) {
        super(context);
        getHolder().addCallback(this);
        gameActive = false;
        acceleration = 0;
        parentContext = context;
        thread = new GameThread(getHolder(), this);
    }

    public boolean isActive() {
        return gameActive;
    }

    public void setActive(boolean active) {
        gameActive = active;
    }

    public void initGame() {
        synchronized (elements) {
            Paddle paddle1 = new Paddle(BitmapFactory.decodeResource(
                    getResources(), R.drawable.paddle), getWidth(),
                    getHeight(), false);
            elements.add(paddle1);
            Paddle paddle2 = new Paddle(BitmapFactory.decodeResource(
                    getResources(), R.drawable.paddle), getWidth(),
                    getHeight(), true);
            elements.add(paddle2);
        }
        orientation = new Orientation(parentContext, this);

    }

    @Override
    public void onDraw(Canvas canvas) {
        synchronized (elements) {
            canvas.drawColor(Color.BLACK);
            Bitmap bitmap;
            Coordinates coords;
            for (GameObject element : elements) {
                bitmap = element.getGraphic();
                coords = element.getCoordinates();
                canvas.drawBitmap(bitmap, coords.getX(), coords.getY(), null);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        synchronized (elements) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (!isActive()) {
                    acceleration = 0;
                    Random rnd = new Random(new Date().getTime());
                    int x = rnd.nextInt(2) + 1;
                    int y = rnd.nextInt(2) + 1;
                    boolean right = rnd.nextBoolean();
                    boolean down = rnd.nextBoolean();
                    Ball b = new Ball(BitmapFactory.decodeResource(
                            getResources(), R.drawable.whiteball));
                    b.getCoordinates().setX(
                            getWidth() / 2 - b.getGraphic().getWidth() / 2);
                    b.getCoordinates().setY(
                            getHeight() / 2 - b.getGraphic().getHeight() / 2);
                    b.getSpeed().setX(x);
                    b.getSpeed().setY(y);
                    b.getSpeed().setXDirection(
                            right ? Speed.X_DIRECTION_RIGHT
                                    : Speed.X_DIRECTION_LEFT);
                    b.getSpeed().setYDirection(
                            down ? Speed.Y_DIRECTION_DOWN
                                    : Speed.Y_DIRECTION_UP);
                    b.getSpeed().setYDirection(Speed.Y_DIRECTION_UP);
                    elements.add(b);
                    setActive(true);
                }
            }
            return true;
        }

    }

    public void updatePhysics() {
        if (isActive()) {
            synchronized (elements) {
                int p1width = elements.get(0).getGraphic()
                        .getWidth();
                int p1height = elements.get(0).getGraphic()
                        .getHeight();
                int p2width = elements.get(1).getGraphic()
                        .getWidth();
                int p1xpos = elements.get(0).getCoordinates().getX();
                int p1ypos = elements.get(0).getCoordinates().getY();
                int p2xpos = elements.get(1).getCoordinates().getX();
                int p2ypos = elements.get(1).getCoordinates().getY();
                Coordinates coord;
                Speed speed;

                Vibrator vibrator = (Vibrator) this.parentContext.getSystemService(Context.VIBRATOR_SERVICE);

                //----------------------Paddle1: controlled by user----------------------
                Paddle p1 = (Paddle) elements.get(0);
                coord = p1.getCoordinates();
                speed = p1.getSpeed();
                speed.setX((int) ori_X);
                speed.setXDirection((int) ori_X);
                if (speed.getXDirection() == Speed.X_DIRECTION_RIGHT) {
                    coord.setX(coord.getX() + speed.getX());
                } else {
                    coord.setX(coord.getX() - speed.getX());
                }
                // Toggle x-direction on collision with borders
                if (coord.getX() < 0) {
                    coord.setX(-coord.getX());
                    speed.setXDirection(-1);
                    speed.setX(0);
                } else if (coord.getX() + p1.getGraphic().getWidth() > getWidth()) {
                    coord.setX(coord.getX() + getWidth()
                            - (coord.getX() + p1.getGraphic().getWidth()));
                    speed.setXDirection(1);
                    speed.setX(0);
                }

                //----------------------Paddle2: controlled by AI------------------------
                Paddle p2 = (Paddle) elements.get(1);
                coord = p2.getCoordinates();
                speed = p2.getSpeed();
                speed.setX(5);
                if (speed.getXDirection() == Speed.X_DIRECTION_RIGHT) {
                    coord.setX(coord.getX() + speed.getX());
                } else {
                    coord.setX(coord.getX() - speed.getX());
                }
                // Toggle x-direction on collision with borders
                if (coord.getX() < 0) {
                    speed.toggleXDirection();
                    coord.setX(-coord.getX());
                } else if (coord.getX() + p2.getGraphic().getWidth() > getWidth()) {
                    speed.toggleXDirection();
                    coord.setX(coord.getX() + getWidth()
                            - (coord.getX() + p2.getGraphic().getWidth()));
                }

                //---------------------------Ball---------------------------------------
                Ball ball = (Ball) elements.get(2);
                coord = ball.getCoordinates();
                speed = ball.getSpeed();

                // Speed to left orientation right
                if (speed.getXDirection() == Speed.X_DIRECTION_RIGHT) {
                    coord.setX(coord.getX() + speed.getX());
                } else {
                    coord.setX(coord.getX() - speed.getX());
                }

                // Speed to up orientation down
                if (speed.getYDirection() == Speed.Y_DIRECTION_DOWN) {
                    coord.setY(coord.getY() + speed.getY());
                } else {
                    coord.setY(coord.getY() - speed.getY());
                }

                // Toggle x-direction on collision with borders
                if (coord.getX() < 0) {
                    speed.toggleXDirection();
                    coord.setX(-coord.getX());

                    if (vibrator != null) {
                        vibrator.vibrate(50);
                    }
                } else if (coord.getX() + ball.getGraphic().getWidth() > getWidth()) {
                    speed.toggleXDirection();
                    coord.setX(coord.getX() + getWidth()
                            - (coord.getX() + ball.getGraphic().getWidth()));
                    if (vibrator != null) {
                        vibrator.vibrate(50);
                    }
                }

                // Toggle y-direction on collision with paddles
                if (coord.getY() <= p1ypos + p1height) {
                    if (coord.getX() > p1xpos
                            && coord.getX() < (p1xpos + p1width)) {
                        speed.toggleYDirection();
                        if (vibrator != null) {
                            vibrator.vibrate(50);
                        }
                        if (acceleration < 3) {
                            acceleration++;
                            ball.getSpeed().setY(
                                    ball.getSpeed().getY() + acceleration);
                        }
                    }
                } else if (coord.getY() + ball.getGraphic().getHeight() >= p2ypos) {
                    if (coord.getX() > p2xpos
                            && coord.getX() < (p2xpos + p2width)) {
                        if (acceleration < 3) {
                            acceleration++;
                            ball.getSpeed().setY(
                                    ball.getSpeed().getY() + acceleration);
                        }
                        speed.toggleYDirection();
                        if (vibrator != null) {
                            vibrator.vibrate(50);
                        }
                    }
                }

                // Check goal
                //goal for AI
                if (coord.getY() <= 0) {
                    elements.remove(ball);
                    setActive(false);
                    if (vibrator != null) {
                        vibrator.vibrate(250);
                    }
                }
                //goal for user
                else if (coord.getY() + ball.getGraphic().getHeight() >= getHeight()) {
                    elements.remove(ball);
                    setActive(false);
                    if (vibrator != null) {
                        vibrator.vibrate(250);
                    }
                }
            }

        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        initGame();
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    @Override
    public void orientationChanged(float x, float y, float z) {
        ori_X = x;
    }
}