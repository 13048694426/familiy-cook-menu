package com.example.familiycookmenu.domain;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;

public class FoodMenuDetail {
	
	public int error_code;
	
	public String success;
	
	public int resultcode;
	
	public ResultDetails result;
	
	public class ResultDetails {
		
		public int pn;
		
		public int rn;
		
		public int totalNum;
		
		public ArrayList<DataDetails> data;
	}
	
	public class DataDetails {
		public String burden;
		
		public String imtro;
		
		public String ingredients;
		
		public String tags;
		
		public String title;
		
		public ArrayList<String> albums;
		
		public ArrayList<StepsDetail> steps;
		
	}
	
	public class StepsDetail {
		public String img;
		
		public String step;
	}
	
}
