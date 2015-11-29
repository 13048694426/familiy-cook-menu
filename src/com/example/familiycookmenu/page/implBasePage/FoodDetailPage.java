package com.example.familiycookmenu.page.implBasePage;

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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.GetChars;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.example.familiycookmenu.R;
import com.example.familiycookmenu.activity.MainActivity;
import com.example.familiycookmenu.base.BasePage;
import com.example.familiycookmenu.base.BaseDetailsPage;
import com.example.familiycookmenu.domain.FoodDetails;
import com.example.familiycookmenu.view.RefreshListView;
import com.example.familiycookmenu.view.RefreshListView.RefreshListener;
import com.google.gson.Gson;
import com.viewpagerindicator.TabPageIndicator;

public class FoodDetailPage extends BasePage {

	public ViewPager vpFood;

	private TabPageIndicator indicator;

	private FrameLayout flLoad;

	public static List<BaseDetailsPage> mPageList;

	private VpFoodAdapter adapter;

	private ProgressBar pbLoad;

	private String[] pageTitle;

	private FoodDetails details;

	private RefreshListView listView;

	private String[] urlName;

	private ExecutorService singThreadExcutor = Executors
			.newSingleThreadExecutor();

	public Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			FoodDetails detail = (FoodDetails) msg.obj;
			int page = msg.what;
			int i = 0;
			mPageList.add(new BaseDetailsPage(activity, detail, i));
			i = i + 1;
			if (mPageList.size() == pageTitle.length) {
				initPageData();
				flLoad.setVisibility(View.GONE);
			}
		}

	};

	public FoodDetailPage(Activity activity) {
		super(activity);
		System.out.println("dota2 vs lol");
	}

	@Override
	public void initData() {
		pageTitle = new String[] { "肉类", "鱼类", "蔬菜", "水果", "奶酪", "豆制品", "特色口味" };
		urlName = new String[] { "肉", "鱼", "蔬菜", "水果", "奶酪", "豆", "口味" };
		mPageList = new ArrayList<BaseDetailsPage>();
		for (int i = 0; i < pageTitle.length; i++) {
			final int index = i;
			System.out.println(i + "= i");
			System.out.println(Thread.currentThread().getId() + "for循坏线程id");
			System.out.println(index + "= lol");
			String response = readCacheData(urlName[i]);
			if (!TextUtils.isEmpty(response)) {
				Gson gson = new Gson();
				FoodDetails details = gson
						.fromJson(response, FoodDetails.class);
				if (details != null) {
					Message msg = new Message();
					msg.obj = details;
					handler.sendMessage(msg);
				}
			}	else {
				getFoodType(urlName[i], new HandleResponse() {

					@Override
					public void success(FoodDetails response) {
						System.out.println(Thread.currentThread().getId()
								+ "线程池线程id");
						Message msg = new Message();
						msg.obj = response;
						handler.sendMessage(msg);

					}

					@Override
					public void failure() {
						System.out.println("没有数据");
					}

				});
			}
		}

	}

	protected void initPageData() {

		if (adapter == null) {
			adapter = new VpFoodAdapter();
			vpFood.setAdapter(new VpFoodAdapter());
		} else {
			adapter.notifyDataSetChanged();
		}
		System.out.println("dota2 vs lol");
		System.out.println(vpFood.toString());
		indicator.setVisibility(View.VISIBLE);
		// indicator.setViewPager(vpFood);
		indicator.setViewPager(vpFood);
		// indicator.onPageSelected(0);
		indicator.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// indicator.onPageSelected(arg0);
				mPageList.get(arg0).initData();
				System.out.println(arg0 + "==" + vpFood.getCurrentItem());
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		mPageList.get(0).initData();
		// initViewPageData();
	}

	private void initViewPageData() {
		int position = vpFood.getCurrentItem();
		System.out.println(position + "==" + "dota2");
		mPageList.get(position).initData();
	}

	@Override
	public View initView() {
		View view = View.inflate(activity, R.layout.page_food_details, null);
		vpFood = (ViewPager) view.findViewById(R.id.vp_food);
		indicator = (TabPageIndicator) view.findViewById(R.id.tpi_food);
		pbLoad = (ProgressBar) view.findViewById(R.id.pb_load);
		flLoad = (FrameLayout) view.findViewById(R.id.fl_load);
		System.out.println(vpFood.toString());

		return view;
	}

	public class VpFoodAdapter extends PagerAdapter {

		@Override
		public CharSequence getPageTitle(int position) {
			return pageTitle[position];
		}

		public int getCount() {
			// TODO Auto-generated method stub
			return mPageList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
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

	// 从服务器获取关于菜单种类的信息
	public void getFoodType(final String name, final HandleResponse handle) {
		// 好他妈高兴，2天的bug终于解决了，我操，我操
		singThreadExcutor.execute(new Runnable() {
			// new Thread(new Runnable() {
			@Override
			public void run() {
				// String response = ReadFromFile(name);
				// if (!TextUtils.isEmpty(response)) {
				// Gson gson = new Gson();
				// FoodDetails details = gson.fromJson(response,
				// FoodDetails.class);
				// if (details.showapi_res_code == 0) {
				// handle.success(details);
				// } else {
				// handle.failure();
				// }
				// }
				String response = new com.example.familiycookmenu.utils.ShowApiRequest(
						"http://route.showapi.com/95-106", "12562",
						"086548ffe157467c9fccb43f4a3edab5").addTextPara("name",
						name).post();
				System.out.println(response.toString() + "DOTA2");

				if (response != null) {
					Gson gson = new Gson();
					FoodDetails details = gson.fromJson(response,
							FoodDetails.class);
					if (details != null) {
						handle.success(details);
						setCacheData(name, response.toString());
					} else {
						handle.failure();
					}
				}
			}

		});
	}

	public interface HandleResponse {

		public abstract void success(FoodDetails response);

		public abstract void failure();
	}

	public void setCacheData(String key, String value) {
		SharedPreferences spf = activity.getSharedPreferences("cacheData",
				Context.MODE_PRIVATE);
		Editor editor = spf.edit();
		editor.putString(key, value).commit();
	}

	public String readCacheData(String key) {
		SharedPreferences spf = activity.getSharedPreferences("cacheData",
				Context.MODE_PRIVATE);
		String value = spf.getString(key, "");
		return value;
	}
	
	public static boolean isNetworkIsAvailable (Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		return cm.getActiveNetworkInfo().isAvailable();
	}

}
