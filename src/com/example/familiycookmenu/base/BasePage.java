package com.example.familiycookmenu.base;

import java.io.Serializable;

import com.example.familiycookmenu.R;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class BasePage implements Serializable{
	
	public View mRoot;
	
	public Activity activity;
	
	public TextView tvTitle;
	
	public FrameLayout flContent;
	
	public String result;
	
	
	public BasePage (Activity activity) {
		this.activity = activity;
		mRoot = initView();
	}

	public View initView() {
		
		View view = View.inflate(activity, R.layout.page_base, null);
		tvTitle = (TextView) view.findViewById(R.id.tv_title);
		flContent = (FrameLayout) view.findViewById(R.id.fl_content);
		
		return view;
		
	}
	
	
	public void initData() {
		TestData(result);
	}

	public void TestData(String data) {
		
		tvTitle.setText(data);
		TextView textView = new TextView(activity);
		textView.setText(data);
		textView.setTextColor(Color.BLUE);
		textView.setTextSize(30);
		textView.setGravity(Gravity.CENTER);
		flContent.addView(textView);
		
	}

}
