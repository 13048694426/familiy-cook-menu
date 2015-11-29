package com.example.familiycookmenu.activity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.familiycookmenu.R;
import com.example.familiycookmenu.base.BasePage;
import com.example.familiycookmenu.page.implBasePage.FoodDetailPage;
import com.example.familiycookmenu.page.implBasePage.FoodSettingPage;
import com.example.familiycookmenu.page.implBasePage.TodayFoodPage;
import com.example.familiycookmenu.utils.DeleteCache;

public class MainActivity extends Activity {

	private ViewPager vpContent;

	private RadioGroup  rgTab;

	static List<BasePage> mPageList;

	private Button btnSend;

	private Button btnCancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		initUi();
		initData();
	}

	private void initData() {
		rgTab.check(R.id.btn_today_food);// 默认选择第一个Button
		mPageList = new ArrayList<BasePage>(); // 数据源
		mPageList.add(new TodayFoodPage(this));
		mPageList.add(new FoodDetailPage(this));
		// mPageList.add(new FoodSearchPage(this));
		mPageList.add(new FoodSettingPage(this));
		vpContent.setAdapter(new VpContentAdapter());
		mPageList.get(0).initData();
		// 设置RadioGroup的监听事件
		rgTab.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				switch (arg1) {
				case R.id.btn_today_food:
					vpContent.setCurrentItem(0, false);
					break;
				case R.id.btn_food_list:
					vpContent.setCurrentItem(1, false);
					break;
				case R.id.btn_setting:
					vpContent.setCurrentItem(2, false);
					break;
				default:
					break;
				}

			}
		});
		// 设置viewpager的滑动监听事件
		vpContent.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				mPageList.get(arg0).initData();
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		// mPageList.get(0).initData();

	}

	private void initUi() {
		setContentView(R.layout.activity_main);
		vpContent = (ViewPager) findViewById(R.id.vp_content);
		rgTab = (RadioGroup) findViewById(R.id.rg_tab);
	}

	public class VpContentAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return mPageList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(mPageList.get(position).mRoot);
			return mPageList.get(position).mRoot;
		}

	}

	public void deleteCache(View view) {
		System.out.println("dota2");
		String cacheSize = DeleteCache.getBitmapCache(this);
		Toast.makeText(this, "缓存已清理" + cacheSize, Toast.LENGTH_SHORT).show();
	}

	public void checkVersion(View view) {
		Toast.makeText(this, "当前版本是最新版本了", Toast.LENGTH_SHORT).show();
	}

	public void adviceForUs(View view) {
		AlertDialog.Builder builder = new Builder(this);
		final AlertDialog dialog = builder.create();
		View dialogView = View.inflate(this, R.layout.view_dialog_advice, null);
		dialog.setView(dialogView,0,0,0,0);
		dialog.show();
		btnSend = (Button) dialogView.findViewById(R.id.btn_send);
		btnCancel = (Button) dialogView.findViewById(R.id.btn_cancel);
		btnSend.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(MainActivity.this, "建议已反馈", Toast.LENGTH_SHORT)
						.show();
				dialog.dismiss();
			}
		});

		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

	}

	public void aboutUs(View view) {
		Intent intent = new Intent(this, AboutUsActivity.class);
		startActivity(intent);
	}
	
    @Override
    protected void onDestroy() {
    	Toast.makeText(this, "单机返回键两次退出应用", Toast.LENGTH_SHORT).show();
    	super.onDestroy();
    }
	

}

