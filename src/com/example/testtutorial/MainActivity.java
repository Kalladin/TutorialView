package com.example.testtutorial;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ImageView.ScaleType;

public class MainActivity extends Activity implements OnClickListener{
	 int[] myImageId = { 
	         R.drawable.tutorial,
	         R.drawable.tutorial2,
	         R.drawable.tutorial3,
	         R.drawable.tutorial,
	         R.drawable.tutorial2,
	         R.drawable.tutorial3,
	         R.drawable.tutorial,
	         R.drawable.tutorial2,
	         R.drawable.tutorial3,	         
	         R.drawable.tutorial,
	         R.drawable.tutorial2,
	         R.drawable.tutorial3
	     };
         
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		LinearLayout tutorialContainer = (LinearLayout) findViewById(R.id.tutorialContainer);
		tutorialContainer.addView(TutorialGalleryView.createTutorialContainerView(getApplicationContext(), myImageId, ScaleType.CENTER_INSIDE ,getWinWidth(), getWinHeight(), this));
	}
    private int getWinWidth(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }
    private int getWinHeight(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
			case R.id.tutorialOkBtn:
				finish();
				break;
		}
	}
}
