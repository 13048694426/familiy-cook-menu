package com.example.familiycookmenu.base;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.familiycookmenu.R;
import com.example.familiycookmenu.activity.FoodInstructionActivity;
import com.example.familiycookmenu.domain.FoodDetails;
import com.example.familiycookmenu.domain.FoodDetails.cbListDetail;
import com.example.familiycookmenu.domain.FoodDetails.imglistDetail;
import com.example.familiycookmenu.domain.FoodMenuDetail;
import com.example.familiycookmenu.domain.FoodMenuDetail.DataDetails;
import com.example.familiycookmenu.page.implBasePage.FoodDetailPage.HandleResponse;
import com.example.familiycookmenu.view.RefreshListView;
import com.example.familiycookmenu.view.RefreshListView.RefreshListener;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BaseDetailsPage {

	public View mRoot;

	public Activity activity;
	
	public static int num = 0;

	public RefreshListView lvFood;
	
	public String[] urlName;
	
	public BitmapUtils bitmapUtils;
	
	public lvFoodAdapter adapter;
	
	private int i;
	
	public static int[] imgResource = new int[] {};
	
	
	
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			lvFood.onRefreshComplete();
			super.handleMessage(msg);
		}
		
	};
	private ExecutorService singThreadExcutor = Executors
			.newSingleThreadExecutor();
	
	public List<cbListDetail> dataDetails = new ArrayList<cbListDetail>();

	public BaseDetailsPage(Activity activity, FoodDetails menuDetails, int i) {
		this.activity = activity;
		mRoot = initView(); 
		dataDetails = menuDetails.showapi_res_body.cbList; 
		this.i = i;
	}

	public View initView() {
		View view = View.inflate(activity, R.layout.page_details_base, null);
		lvFood = (RefreshListView) view.findViewById(R.id.lv_food_menu);
		imgResource = new int[] {R.drawable.default1,R.drawable.default2,R.drawable.default3,R.drawable.default4,R.drawable.default5,
				R.drawable.default6,R.drawable.default7,R.drawable.default8,R.drawable.default9,
				R.drawable.default10,R.drawable.default11};
		urlName = new String[] { "肉", "鱼", "蔬菜", "水果", "奶酪", "豆", "口味" };
		lvFood.setRefreshListener(new RefreshListener() {
			
			@Override
			public void onRefresh() {
				
				handler.sendEmptyMessageDelayed(0, 2000);
				
			 }
		});
		
		return view;
	}

	public void initData() {
		System.out.println("数据呢  前");
		if (adapter == null) {
			adapter = new lvFoodAdapter();
		    lvFood.setAdapter(adapter);
		} else {
			adapter.notifyDataSetChanged();
		}
		System.out.println("数据呢  后");
		lvFood.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(activity,FoodInstructionActivity.class);
				intent.putExtra("position", position);
				activity.startActivity(intent);
			}
		});
	}

	class lvFoodAdapter extends BaseAdapter {
		
		public lvFoodAdapter() {
			bitmapUtils = new BitmapUtils(activity);
			bitmapUtils.configDefaultLoadingImage(R.drawable.default_loading);
		}

		@Override
		public int getCount() {
			return dataDetails.size();
		}

		@Override
		public Object getItem(int position) {
			return dataDetails.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = new ViewHolder();
			if (convertView == null) {
				convertView = View.inflate(activity, R.layout.item_food_list,null);
				holder.ivFood = (ImageView) convertView.findViewById(R.id.iv_food);
				holder.tvTags = (TextView) convertView.findViewById(R.id.tv_tag);
				holder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
				convertView.setTag(holder);	
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			cbListDetail details = dataDetails.get(position);
			if (details.imgList != null && !details.imgList.isEmpty()) {
				String url = details.imgList.get(0).imgUrl;
				holder.tvTitle.setText(details.name);
				holder.tvTags.setText(details.cl);
				bitmapUtils.display(holder.ivFood, url);
			} else {
				holder.tvTitle.setText(details.name);
				holder.tvTags.setText(details.cl);
				holder.ivFood.setImageResource(imgResource[num]);
				if (num < 10) {
				 num++;
				}
			}
			
			return convertView;
		}

	}

	static class ViewHolder {
		TextView tvTitle;
		ImageView ivFood;
		TextView tvTags;
	}
	
	public void getFoodType(final String name, final HandleResponse handle) {
		//好他妈高兴，2天的bug终于解决了
				singThreadExcutor.execute(new Runnable() {
					@Override
					public void run() {
						String result = new com.example.familiycookmenu.utils.ShowApiRequest(
								"http://route.showapi.com/95-106", "12562",
								"086548ffe157467c9fccb43f4a3edab5").addTextPara("name",
								name).post();
						System.out.println(result.toString());
						Gson gson = new Gson();
						FoodDetails details = gson.fromJson(result, FoodDetails.class);
						Message message = Message.obtain();
						if (details.showapi_res_code == 0) {
							handle.success(details);
						} else {
							handle.failure();
						}
					}

				});
			}

			public interface HandleResponse {

				public abstract void success(FoodDetails response);

				public abstract void failure();
			}
}
