package symlab.ust.hk.imagetagged;



import java.util.logging.Logger;

import symlab.ust.hk.imagetagged.R;

import android.os.Bundle;

import android.app.Activity;

import android.content.Intent;
import android.support.v4.view.GestureDetectorCompat;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.view.GestureDetector;

/*
 * author Huber Flores
 */

public class ImageTagged extends Activity implements android.view.View.OnClickListener, GestureDetector.OnGestureListener,
GestureDetector.OnDoubleTapListener{
	
	private Logger Log = Logger.getLogger(ImageTagged.class.getName());

	private double press = 0f;
	private double release = 0f;
	
    private GestureDetectorCompat mDetector;
   
    
    private Button btn_start;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_tagged);
        
        if (!isTaskRoot()) {
            final Intent intent = getIntent();
            final String intentAction = intent.getAction(); 
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && intentAction != null && intentAction.equals(Intent.ACTION_MAIN)) {
                Log.info("Main Activity is not the root.  Finishing Main Activity instead of launching.");
                finish();
                return;       
            }
        }
        
        mDetector = new GestureDetectorCompat(this,this);
        mDetector.setOnDoubleTapListener(this);
        
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this);
        
        btn_start.setOnTouchListener(btnTouch); 
        
        
    
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.image_tagged, menu);
        return true;
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
			case R.id.btn_start: 
				Toast.makeText(getApplicationContext(), "Do not confuse with OnClickListener",Toast.LENGTH_SHORT).show();
				        
		        
				break;	
				
		
		}		
		
	}
	
		      
	private View.OnTouchListener btnTouch = new View.OnTouchListener() {
	    @Override
	    public boolean onTouch(View v, MotionEvent event) {
	         int action = event.getAction();
	         if (action == MotionEvent.ACTION_DOWN)
	       	   press = System.currentTimeMillis();
	         else if (action == MotionEvent.ACTION_UP)
	           release = System.currentTimeMillis();
	         return false;   
	      }
	 };
	   
	   //Gesture events
	@Override 
	public boolean onTouchEvent(MotionEvent event){ 
	     this.mDetector.onTouchEvent(event);
	     // Be sure to call the superclass implementation
	   return super.onTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent event) {
		//Log.info("onDown: " + event.toString());
		
		
	  return true;
	}

	@Override
	public boolean onFling(MotionEvent event1, MotionEvent event2, 
	    float velocityX, float velocityY) {
	  	//Log.info("onFling: " + event1.toString()+event2.toString());
	  	
	  return true;
	}

	@Override
	public void onLongPress(MotionEvent event) {
	  	//Log.info("onLongPress: " + event.toString());
	  	
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
	            float distanceY) {
	   	//Log.info("onScroll: " + e1.toString()+e2.toString());
	   	     
	 return true;
	}

	@Override
	public void onShowPress(MotionEvent event) {
	  	//Log.info("onShowPress: " + event.toString());
	  	    
	}

	@Override
	public boolean onSingleTapUp(MotionEvent event) {
	   	//Log.info("onSingleTapUp: " + event.toString());
	   	    
	 return true;
	}

	@Override
	public boolean onDoubleTap(MotionEvent event) {
	  	//Log.info("onDoubleTap: " + event.toString());
	
		Toast.makeText(getApplicationContext(), "onDoubleTap: " + event.toString(),Toast.LENGTH_SHORT).show();
	 return true;
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent event) {
	   	//Log.info("onDoubleTapEvent: " + event.toString());
	     
	 return true;
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent event) {
	   	//Log.info("onSingleTapConfirmed: " + event.toString());
	   	
	 return true;
	}
	
	
	//Lifecycle activity management
	@Override
	public void onStart(){
		super.onStart();
		
	}
			
	@Override
	public void onRestart(){
		super.onRestart();
		 
	}
		
	
}
