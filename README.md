
#### **Android Tutorial View**

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