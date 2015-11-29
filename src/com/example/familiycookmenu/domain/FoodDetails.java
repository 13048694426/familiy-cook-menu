package com.example.familiycookmenu.domain;

import java.util.ArrayList;

public class FoodDetails {
	

	public int showapi_res_code;
	
	public bodyDetail showapi_res_body;
	
	public String showapi_res_error;
	
	public class bodyDetail {
		
		public ArrayList<cbListDetail> cbList;

	}
	
	public class cbListDetail {
		
		public int cbId;
		
		public String cl;
		
		public String mainType;
		
		public String type;
		
		public String name;
		
		public String zf;
		
		public ArrayList<imglistDetail> imgList;

	}
	
	public class imglistDetail {
		public String imgUrl;
	}

}
