package com.example.familiycookmenu.base;

import java.util.ArrayList;
import java.util.List;

import com.example.familiycookmenu.R;
import com.example.familiycookmenu.domain.FoodDetails;
import com.example.familiycookmenu.domain.FoodDetails.cbListDetail;
import com.lidroid.xutils.BitmapUtils;

import android.app.Activity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class BaseInstructionPage {
	
	public Activity mActivity;
	
	public View mRoot;
	
	private int position;
	
	private TextView tvTitle;
	
	private TextView tvContent;
	
	public static int num = 0;
	
	public static int[] imgResource = new int[] {};
	
	
	private ImageView ivFood;
	
	private BitmapUtils bitmapUtils;
	
	public List<cbListDetail> dataDetails = new ArrayList<cbListDetail>();//食物具体信息的集合类
	
	public BaseInstructionPage (Activity activity , BaseDetailsPage basePage ,int position) {
		mActivity = activity;
		mRoot = initView();
		this.position = position;
		dataDetails = basePage.dataDetails;
		initData();
	}
	
	public View initView() {
		View view = View.inflate(mActivity, R.layout.page_food_instruction, null);
		tvTitle = (TextView) view.findViewById(R.id.tv_title);
		tvContent = (TextView) view.findViewById(R.id.tv_content);
		tvContent.setMovementMethod(ScrollingMovementMethod.getInstance());
		ivFood = (ImageView) view.findViewById(R.id.iv_food);
		imgResource = new int[] {R.drawable.default1,R.drawable.default2,R.drawable.default3,R.drawable.default4,R.drawable.default5,
				R.drawable.default6,R.drawable.default7,R.drawable.default8,R.drawable.default9,
				R.drawable.default10,R.drawable.default11};
		return view;
	}
	
	public void initData() {
		bitmapUtils = new BitmapUtils(mActivity);
		cbListDetail details = dataDetails.get(position);
		if (details.imgList != null && !details.imgList.isEmpty()) {
			String url = details.imgList.get(0).imgUrl;
			tvTitle.setText(details.name);
			String result = details.zf;
			tvContent.setText(FormatText(result));
			bitmapUtils.display(ivFood, url);
		} else {
			tvTitle.setText(details.name);
			tvContent.setText(details.zf);
			ivFood.setImageResource(imgResource[num]);
			if (num < 10) {
				 num++;
				}
		}
		
	}

	private String FormatText(String result) {
		StringBuilder builder = new StringBuilder(result);
		for (int i=0 ; i < result.length(); i++) {
			char ch = result.charAt(i);
			if (ch >='2' && ch<='9') {
				builder.insert(i+1, "  ");
			}
		}
		return builder.toString();
	}

}
