package pong.hapra.pong03;

/**
 * Zugriff auf die Sensoren
 * @author Jan Bauerdick
 *
 */

import android.content.Context;
import android.hardware.SensorManager;

import org.openintents.sensorsimulator.hardware.Sensor;
import org.openintents.sensorsimulator.hardware.SensorEvent;
import org.openintents.sensorsimulator.hardware.SensorEventListener;
import org.openintents.sensorsimulator.hardware.SensorManagerSimulator;
public class Orientation {
    private SensorManagerSimulator sensorManagerSimulator;
    private Callback callback;

    private SensorEventListener listener = new SensorEventListener() {
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
        public void onSensorChanged(SensorEvent event){
            callback.orientationChanged(event.values[0], event.values[1], event.values[2]);
        }
    };

    Orientation(Context context, Callback callback){
        this.callback = callback;
        sensorManagerSimulator = SensorManagerSimulator.getSystemService(context, Context.SENSOR_SERVICE);
        sensorManagerSimulator.connectSimulator();
        sensorManagerSimulator.registerListener(listener, sensorManagerSimulator.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void close() {
        sensorManagerSimulator.unregisterListener(listener);
    }

    public interface Callback {
        void orientationChanged(float x, float y, float z);
    }


}

