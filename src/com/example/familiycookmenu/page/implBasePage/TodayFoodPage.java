package com.example.familiycookmenu.page.implBasePage;

import java.io.Serializable;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.familiycookmenu.R;
import com.example.familiycookmenu.base.BasePage;

public class TodayFoodPage extends BasePage {


	public TodayFoodPage(Activity activity) {
		super(activity);
	}

	@Override
	public void initData() {

	}

	@Override
	public View initView() {
		View view = View.inflate(activity, R.layout.page_today_food, null);
		return view;
	}


}
