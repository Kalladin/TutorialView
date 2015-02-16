package com.example.testtutorial;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

public class TutorialGalleryView extends HorizontalScrollView {
	private static final int SWIPE_MIN_DISTANCE = 5;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	private static final String LOG_TAG = "TutorialGalleryView";

	private ArrayList<ImageView> mItems = null;
	private ArrayList<Integer> pointList = null;

	private GestureDetector mGestureDetector;

	private int mActiveFeature = 0;
	private int o_mActiveFeature = 0;

	private int parentWidth = 0;
	private int parentHeight = 0;
	private Context mContext;
	private View tutorialView;
	private LinearLayout tutorialLinear;
	private TutorialGalleryView myself;
	
	public static View createTutorialContainerView(Context context, int [] drawableRes, ScaleType type,
						int mScreenWidth, int mScreenHeight, OnClickListener okBtnHandle) {
		
		LayoutInflater mInflater = LayoutInflater.from(context);
		View inflateView = mInflater.inflate(R.layout.custom_horizontal_scroll_view, null);
		LinearLayout featureContainer = (LinearLayout) inflateView.findViewById(R.id.featureContainer);
		LinearLayout pageIndexLinear = (LinearLayout) inflateView.findViewById(R.id.pageIndexLinear);
		TutorialGalleryView scrollView = new TutorialGalleryView(context, pageIndexLinear, drawableRes, type ,mScreenWidth, mScreenHeight);

		Button okBtn = (Button) inflateView.findViewById(R.id.tutorialOkBtn);
		
		for(int i = 0 ; i < drawableRes.length ; i++) {
			
            ImageView indexBubble = new ImageView(context);
            if(i == 0){
            	indexBubble.setImageResource(R.drawable.circle_blue_16x16);
            	}
            else
            	indexBubble.setImageResource(R.drawable.circle_grey_16x16);
            //indexBubble.setScaleX(0.2f);
            //indexBubble.setScaleY(0.2f);
            indexBubble.setScaleType(ScaleType.FIT_CENTER);
            indexBubble.setBackgroundColor(Color.TRANSPARENT);
            indexBubble.setPadding(5, 0, 5, 0);
            pageIndexLinear.addView(indexBubble);
		}
		pageIndexLinear.setBackgroundColor(Color.TRANSPARENT);
		//tutorialLinear.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
		featureContainer.addView(scrollView);

		okBtn.setOnClickListener(okBtnHandle);
		return inflateView;
	}
	
	public int getSelectedIndex() {
		return mActiveFeature;
	}
	
	public int getCount() {
		return mItems.size();
	}
	
	public void setScaleType(ScaleType type, View inflateView) {
		LinearLayout featureContainer = (LinearLayout) inflateView.findViewById(R.id.featureContainer);

		for(int i = 0 ; i < featureContainer.getChildCount() ; i++) {
			ImageView view = (ImageView) tutorialLinear.getChildAt(i);
			view.setScaleType(type);
		};
	}
	
	private void scrollToIndex(int selected, LinearLayout pageIndexLinear) {
        if( o_mActiveFeature == selected) {
            Log.d(LOG_TAG, "Stay at the page:" + selected);
            return;
        }
        Log.d(LOG_TAG, "page:" + o_mActiveFeature + " --> page:" + selected);

        ImageView o_selectedView = (ImageView) pageIndexLinear.getChildAt(o_mActiveFeature);
		ImageView selectedView = (ImageView) pageIndexLinear.getChildAt(selected);
		selectedView.setImageResource(R.drawable.circle_blue_16x16);
		o_selectedView.setImageResource(R.drawable.circle_grey_16x16);
		o_mActiveFeature = selected;
	}
	
	private TutorialGalleryView(Context context, View pageIndexLinear,
						int [] drawableRes, ScaleType type, int parentWidth, int parentHeight) {
		super(context);
		this.mContext = context;
		this.mItems = new ArrayList<ImageView>();
		this.pointList = new ArrayList<Integer>();
		this.parentWidth = parentWidth;
		this.parentHeight = parentHeight;
		for(int i = 0 ; i < drawableRes.length ; i++){
			pointList.add(i*parentWidth);
		}
		setFeatureItem(drawableRes, type, (LinearLayout) pageIndexLinear);
	}
	
	private void setFeatureItem(int [] drawableRes, ScaleType scaleType, final LinearLayout pageIndexLinear) {

		LayoutParams params = new LayoutParams(parentWidth, LayoutParams.MATCH_PARENT);

		tutorialLinear = new LinearLayout(getContext());
		tutorialLinear.setLayoutParams(params);
		tutorialLinear.setOrientation(LinearLayout.HORIZONTAL);

 		mGestureDetector = new GestureDetector(new MyGestureDetector(pageIndexLinear));

		for(int i = 0 ; i < drawableRes.length; i++) {
			ImageView img = new ImageView(mContext);
            img.setImageResource(drawableRes[i]);
            img.setBackgroundColor(Color.WHITE);
            img.setLayoutParams(params);
            if(scaleType == null)
            	img.setScaleType(ScaleType.CENTER_INSIDE);
            else 
            	img.setScaleType(scaleType);

            tutorialLinear.addView(img);
            mItems.add(img);
        }

		addView(tutorialLinear);
		setHorizontalFadingEdgeEnabled(false);
		setVerticalScrollBarEnabled(false);
		setHorizontalScrollBarEnabled(false);
        setOnTouchListener(new OnTouchListener() {
 			@Override
 			public boolean onTouch(View v, MotionEvent event) {
 				//If the user swipes
 				if (mGestureDetector.onTouchEvent(event)) {
 					return true;
 				}
 				else if(event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL ){
 					
 					//pageScroll(HorizontalScrollView.FOCUS_RIGHT);
		   			Log.d(LOG_TAG, "onTouch page:" + mActiveFeature + " width:" + parentWidth);
		   			int width = v.getMeasuredWidth();
 					mActiveFeature = ((getScrollX() + (width/2))/width);
 					scrollToIndex(mActiveFeature, pageIndexLinear);
 					smoothScrollTo(pointList.get(mActiveFeature), 0);
 					return true;
 				}
 				else{
 					return false;
 				}
 			}
 		});
	}
	
	class MyGestureDetector extends SimpleOnGestureListener {
 		
		LinearLayout pageIndexLinear;
		public MyGestureDetector(View pageIndexLinear) {
			this.pageIndexLinear = (LinearLayout) pageIndexLinear;
		}
		
		@Override
 		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
 			try {
 				//right to left
  				if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					mActiveFeature = (mActiveFeature < (mItems.size() - 1))? mActiveFeature + 1:mItems.size() -1;
					Log.d(LOG_TAG,"onFling left page:" + mActiveFeature + " to:" + pointList.get(mActiveFeature));
					smoothScrollTo(pointList.get(mActiveFeature), 0);
 			        scrollToIndex(mActiveFeature, pageIndexLinear);
 			        return true;
 				}
   				//left to right
 				else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					mActiveFeature = (mActiveFeature > 0)? mActiveFeature - 1:0;
					Log.d(LOG_TAG,"onFling right page:" + mActiveFeature + " to:" + pointList.get(mActiveFeature));
					smoothScrollTo(pointList.get(mActiveFeature), 0);
 			        scrollToIndex(mActiveFeature, pageIndexLinear);
 			        return true;
				}
			} catch (Exception e) {
			        Log.e("Fling", "There was an error processing the Fling event:" + e.getMessage());
			}
			return false;
		}
	}

}
