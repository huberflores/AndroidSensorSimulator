package monitor.movement;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
 

public class Accelerometer extends Activity 
        implements AccelerometerListener {
 
    private static Context CONTEXT;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        CONTEXT = this;
    }
 
    protected void onResume() {
        super.onResume();
        if (AccelerometerManager.isSupported()) {
            AccelerometerManager.startListening(this);
        }
    }
 
    protected void onDestroy() {
        super.onDestroy();
        if (AccelerometerManager.isListening()) {
            AccelerometerManager.stopListening();
        }
 
    }
 
    public static Context getContext() {
        return CONTEXT;
    }
 
    /**
     * onShake callback
     */
    public void onShake(float force) {
        Toast.makeText(this, "Phone shaked : " + force, 1000).show();
    }
 
    /**
     * onAccelerationChanged callback
     */
    public void onAccelerationChanged(float x, float y, float z) {
        ((TextView) findViewById(R.id.x)).setText(String.valueOf(x));
        ((TextView) findViewById(R.id.y)).setText(String.valueOf(y));
        ((TextView) findViewById(R.id.z)).setText(String.valueOf(z));
        
        if (Math.abs(x)<1 && Math.abs(y)<1 && Math.abs(z)<1){ //take in consideration values like 0.9999 as 0
        	//Here trigger the procedure when the phone is falling
        	((TextView) findViewById(R.id.status)).setText("Falling...");
        }
        
    }
 
}