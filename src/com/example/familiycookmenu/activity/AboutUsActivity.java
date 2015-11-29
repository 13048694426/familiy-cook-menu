package com.example.familiycookmenu.activity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.example.familiycookmenu.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class AboutUsActivity extends Activity {
	
	private TextView tvContent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.actvity_about_us);
		tvContent = (TextView) findViewById(R.id.tv_content);
		tvContent.setText("这是一款主要用来学习和交流的软件\n数据全部来自网络\n提拱了一些常用的食谱\n欢迎大家积极使用,并指出软件的不足\n交流"
				+ "QQ 396916884\n author by yansong" );
	}
	
	
}