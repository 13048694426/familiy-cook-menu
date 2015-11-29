package com.example.familiycookmenu.activity;

import java.util.List;

import com.example.familiycookmenu.R;
import com.example.familiycookmenu.base.BaseDetailsPage;
import com.example.familiycookmenu.base.BaseInstructionPage;
import com.example.familiycookmenu.base.BasePage;
import com.example.familiycookmenu.page.implBasePage.FoodDetailPage;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.widget.FrameLayout;

public class FoodInstructionActivity extends Activity {
	
	public BaseInstructionPage page;//食物的具体做法
	
	public FrameLayout flRoot;
	
	public FoodDetailPage detailPage; //所有食物的详情
		
	public List<BaseDetailsPage> detailsPageList;    //每一种食物的详情的集合
	
	public ViewPager vpFood;
	
	private List<BasePage> pageList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		initView();
		initData();
	}

	private void initView() {
		setContentView(R.layout.activity_food_instruction);
		flRoot = (FrameLayout) findViewById(R.id.fl_root);
	}
	
	public void initData() {
		pageList = MainActivity.mPageList;
		System.out.println(pageList.size() + "dota2  和 lol ");
		detailPage = (FoodDetailPage) pageList.get(1);//通过广播得到如下数据
		vpFood = detailPage.vpFood;
		detailsPageList = detailPage.mPageList;
		int position = getIntent().getIntExtra("position", 0);//通过intent将listView点击的位置传递进来，从而通过构造方法传递下去
		int currentItem = vpFood.getCurrentItem();
		page = new BaseInstructionPage(this, detailsPageList.get(currentItem), position - 1);
		flRoot.removeAllViews();
		flRoot.addView(page.mRoot);
	}
	
	
	

}
