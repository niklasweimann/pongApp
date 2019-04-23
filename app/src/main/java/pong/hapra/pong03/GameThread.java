package pong.hapra.pong03;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Der Thread, der das Spiel ausfuehrt
 * @author Jan Bauerdick
 *
 */
public class GameThread extends Thread {

    private SurfaceHolder surfaceHolder;
    private Pong gameElement;
    private boolean running = false;

    public GameThread(SurfaceHolder surfaceHolder, Pong gameElement) {
        this.surfaceHolder = surfaceHolder;
        this.gameElement = gameElement;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @SuppressLint("WrongCall")
    @Override
    public void run() {
        Canvas c;
        while (running) {
            c = null;
            try {
                c = surfaceHolder.lockCanvas(null);
                synchronized (c) {
                    gameElement.onDraw(c);
                    gameElement.updatePhysics();
                }
            } finally {
                if (c != null) {
                    surfaceHolder.unlockCanvasAndPost(c);
                }
            }
        }
    }

}