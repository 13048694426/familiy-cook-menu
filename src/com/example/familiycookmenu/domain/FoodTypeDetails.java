package com.example.familiycookmenu.domain;

import java.util.ArrayList;

import com.example.familiycookmenu.domain.FoodMenuDetail.ResultDetails;

public class FoodTypeDetails {

	public int error_code;

	public String success;

	public int resultcode;

	public ArrayList<ResultDetails> result;
	
	public class ResultDetails {
		
		public ArrayList<ListDetails> list;
		
		public String name;
	}
	
	public class ListDetails {
		public int id;
		
		public String name;
	}

}
