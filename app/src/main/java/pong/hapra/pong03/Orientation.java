package pong.hapra.pong03;

/**
 * Zugriff auf die Sensoren
 * @author Jan Bauerdick
 *
 */

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
public class Orientation {
    private SensorManager sm;
    private Orientation.Callback cb;

    public interface Callback {
        void orientationChanged(float x, float y, float z);
    }

    private SensorEventListener listener = new SensorEventListener() {
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
        public void onSensorChanged(SensorEvent event){
            cb.orientationChanged(event.values[0], event.values[1], event.values[2]);
        }
    };

    Orientation(Context pong, Orientation.Callback cb){
        this.cb = cb;
        sm = (SensorManager) pong.getSystemService(Context.SENSOR_SERVICE);
        sm.registerListener(listener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void close() {
        sm.unregisterListener(listener);
    }


}