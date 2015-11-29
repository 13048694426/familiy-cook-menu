package com.example.familiycookmenu.base;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class TestTextView {
	
	public View mRoot;
	
	public Activity activity;
	
	public TestTextView (Activity activity,String result) {
		this.activity = activity;
		mRoot = TestData(result);
	}

	public View TestData(String data) {

		TextView textView = new TextView(activity);
		textView.setText(data);
		textView.setTextColor(Color.BLUE);
		textView.setTextSize(30);
		textView.setGravity(Gravity.CENTER);
		return textView;
	}

}
