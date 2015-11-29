package com.example.familiycookmenu.page.implBasePage;

import java.io.Serializable;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.familiycookmenu.R;
import com.example.familiycookmenu.base.BasePage;

public class FoodSettingPage extends BasePage  {
	
	private TextView tvCache;

	private TextView tvVersion;

	private TextView tvAdvice;

	private TextView tvAbout;

	public FoodSettingPage(Activity activity) {
		super(activity);
	}

	@Override
	public void initData() {
	}
	
	@Override
	public View initView() {
		View view = View.inflate(activity, R.layout.page_food_setting, null);
		tvCache = (TextView) view.findViewById(R.id.tv_cache);
		tvVersion = (TextView) view.findViewById(R.id.tv_version);
		tvAdvice = (TextView) view.findViewById(R.id.tv_advise);
		tvAbout = (TextView) view.findViewById(R.id.tv_about);
		System.out.println("lol");
		return view;
	}
	

//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.tv_cache:
//			Toast.makeText(activity, "缓存已清理", Toast.LENGTH_LONG).show();
//			break;
//		case R.id.tv_version:
//			Toast.makeText(activity, "当前版本是最新版本了", Toast.LENGTH_LONG).show();
//			break;
//		}
//		
//	}
	
	

}
