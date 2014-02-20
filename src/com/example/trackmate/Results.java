package com.example.trackmate;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Results extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);
		// Show the Up button in the action bar.
//		getActionBar().setDisplayHomeAsUpEnabled(true);

		Bundle extras = getIntent().getExtras();
		double speed = extras.getDouble("speed");
		double miletime = 0;
		miletime = extras.getDouble("miletime");
		int calories = extras.getInt("calories");
		
		double speedR = Math.round(speed * 100.0 ) / 100.0;
		int miletimeM = (int)Math.floor(miletime);
		double miletimeRemainder = miletime - miletimeM;
		int miletimeS = (int)(miletimeRemainder * 60);
		miletimeS = Math.round(miletimeS);
		
		String spd = Double.toString(speedR);
		String mltmM = Integer.toString(miletimeM);	//miletime in minutes
		String mltmS = Integer.toString(miletimeS);	//decimal remainder of above, in seconds
		String cals = Integer.toString(calories);

		

		
		
		
	    // Create the text view
	    TextView textView = (TextView) findViewById(R.id.TextView01);
	    textView.setText("Calories Burned: " + cals);
	    
	    if (speed > 0)
	    {
		    TextView textView2 = (TextView) findViewById(R.id.TextView02);
		    textView2.setText("Average speed: " + spd + " mph" );
	    }
	    
	    else
	    {
		    TextView textView2 = (TextView) findViewById(R.id.TextView02);
		    textView2.setText("Average speed: N/A" );
	    }
	    
	    
	    if (miletime > 0)
	    {
		    TextView textView3 = (TextView) findViewById(R.id.TextView03);
		    textView3.setText("Miletime: " + mltmM +" minutes and " + mltmS + " seconds");
	    }
	    
	    else
	    {
		    TextView textView2 = (TextView) findViewById(R.id.TextView03);
		    textView2.setText("Average speed: N/A" );
	    }
	    // Set the text view as the activity layout
	//    setContentView(textView);

		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_results, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	

}
