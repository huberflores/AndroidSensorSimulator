package monitor.movement;


import java.util.List;
 
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

 

public class AccelerometerManager {
 
    private static float threshold     = 0.2f;
    private static int interval     = 1000;
 
    private static Sensor sensor;
    private static SensorManager sensorManager;
 
    private static AccelerometerListener listener;
 
 
    private static Boolean supported;
    private static boolean running = false;
 
    
    public static boolean isListening() {
        return running;
    }
 
    
    public static void stopListening() {
        running = false;
        try {
            if (sensorManager != null && sensorEventListener != null) {
                sensorManager.unregisterListener(sensorEventListener);
            }
        } catch (Exception e) {}
    }
 
    
    public static boolean isSupported() {
        if (supported == null) {
            if (Accelerometer.getContext() != null) {
            	       	sensorManager = (SensorManager) Accelerometer.getContext().
                        getSystemService(Context.SENSOR_SERVICE);
                       	
                List<Sensor> sensors = sensorManager.getSensorList(
                        Sensor.TYPE_ACCELEROMETER);
                supported = new Boolean(sensors.size() > 0);
            } else {
                supported = Boolean.FALSE;
            }
        }
        return supported;
    }
 
    
    public static void configure(int threshold, int interval) {
        AccelerometerManager.threshold = threshold;
        AccelerometerManager.interval = interval;
    }
 
   
    public static void startListening(
            AccelerometerListener accelerometerListener) {
        sensorManager = (SensorManager) Accelerometer.getContext().
                getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(
                Sensor.TYPE_ACCELEROMETER);
        if (sensors.size() > 0) {
            sensor = sensors.get(0);
            running = sensorManager.registerListener(
                    sensorEventListener, sensor, 
                    SensorManager.SENSOR_DELAY_GAME);
            listener = accelerometerListener;
        }
    }
 
   
    public static void startListening(
            AccelerometerListener accelerometerListener, 
            int threshold, int interval) {
        configure(threshold, interval);
        startListening(accelerometerListener);
    }
 
   
    private static SensorEventListener sensorEventListener = 
        new SensorEventListener() {
 
        private long now = 0;
        private long timeDiff = 0;
        private long lastUpdate = 0;
        private long lastShake = 0;
 
        private float x = 0;
        private float y = 0;
        private float z = 0;
        private float lastX = 0;
        private float lastY = 0;
        private float lastZ = 0;
        private float force = 0;
 
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}
 
        public void onSensorChanged(SensorEvent event) {

            now = event.timestamp;
 
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];
 
           if (lastUpdate == 0) {
                lastUpdate = now;
                lastShake = now;
                lastX = x;
                lastY = y;
                lastZ = z;
            } else {
                timeDiff = now - lastUpdate;
                if (timeDiff > 0) {
                    force = Math.abs(x + y + z - lastX - lastY - lastZ) 
                                / timeDiff;
                    if (force > threshold) {
                        if (now - lastShake >= interval) {
                           
                            listener.onShake(force);
                        }
                        lastShake = now;
                    }
                    lastX = x;
                    lastY = y;
                    lastZ = z;
                    lastUpdate = now;
                }
            }
         
            listener.onAccelerationChanged(x, y, z);
        }
 
    };
 
}