
# **Android Tutorial View**
User can easily add tutorial view to your android application with autoly expand bubble.
You can assign your own onClick handler to tutorial end button. 

## **Demo**

Three pictures with diffent size. 

<img src="http://i.imgur.com/3ag9BNi.png" alt="Drawing" width="200"/>
<img src="http://i.imgur.com/cxh9ts9.png" alt="Drawing" width="200"/>
<img src="http://i.imgur.com/g5DEO9D.png" alt="Drawing" width="200"/>

## **Usage**

**1. Create Tutorial View by call** 
```
createTutorialContainerView(Context context, int [] imageResArray, ScaleType type, 
		int screenWidth, int screenHeight, OnClickListener listener)
```

Example:
```
int[] myImageId = { 
	         R.drawable.tutorial,
	         R.drawable.tutorial2,
	         R.drawable.tutorial3
	         }
DisplayMetrics dm = new DisplayMetrics();
getWindowManager().getDefaultDisplay().getMetrics(dm);

View tutorial = TutorialGalleryView.createTutorialContainerView(getApplicationContext(), myImageId, ScaleType.CENTER_INSIDE ,dm.widthPixels, dm.heightPixels, new OnClickListener() { 
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
			case R.id.tutorialOkBtn:
				finish();
				break;
		}
	}});

```

**2. Add Tutorial View to your container**

Example:
```
LinearLayout container =  (LinearLayout) findViewById(R.id.tutorialContainer);
container.addView(tutorial);
```
