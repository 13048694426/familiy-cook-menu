package com.example.familiycookmenu.utils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TimeZone;


/**
 * 基于REST的客户端。
 */
public class ShowApiRequest extends NormalRequest  {
	private String appSecret;
	 
	public ShowApiRequest(String url,String appid,String appSecret    ) {
		super(url);
		this.appSecret = appSecret;
		this.textMap.put("showapi_appid",appid);
	}
	

	public String getAppSecret() {
		return appSecret;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	
	private String addSign() throws IOException{
		boolean ismd5=true;
		if(textMap.get(Constants.SHOWAPI_APPID)==null)return errorMsg(Constants.SHOWAPI_APPID+"不得为空!");
		String signmethod=textMap.get(Constants.SHOWAPI_SIGN_METHOD);
		if(signmethod!=null&&!signmethod.equals("md5"))ismd5=false;
		if(signmethod!=null&&!signmethod.equals("md5")&&!signmethod.equals("hmac"))return errorMsg("showapi_sign_method参数只能是md5或hmac");
				
		if(textMap.get(Constants.SHOWAPI_TIMESTAMP)==null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		TimeZone timeZone = TimeZone.getTimeZone("Asia/Shanghai");
		sdf.setTimeZone(timeZone);
		Date date = new Date();
		String desDate = sdf.format(date);
		textMap.put(Constants.SHOWAPI_TIMESTAMP,desDate);
			
		}
		if (ismd5) {
			textMap.put(Constants.SHOWAPI_SIGN, com.example.familiycookmenu.utils.util.ShowApiUtils.signRequest(textMap, appSecret, false));
		} else {
			textMap.put(Constants.SHOWAPI_SIGN, com.example.familiycookmenu.utils.util.ShowApiUtils.signRequest(textMap, appSecret, true));
		}
		return null;
	}
	
	public String post()   {
		String res="";
		try {
			String signResult=addSign();
			if(signResult!=null)return signResult;
			res= com.example.familiycookmenu.utils.util.WebUtils.doPost(this);
		} catch (Exception e) {
			e.printStackTrace();
			res="{showapi_res_code:-1,showapi_res_error:"+e.toString()+"}";
		}
		return res;
	}
	
	public byte[] postAsByte()   {
		byte res[]=null;
		try {
			String signResult=addSign();
			if(signResult!=null)return signResult.getBytes("utf-8");
			res= com.example.familiycookmenu.utils.util.WebUtils.doPostAsByte(this);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				res=("{showapi_res_code:-1,showapi_res_error:"+e.toString()+"}").getBytes("utf-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
		}
		return res;
	}
	
	
	public String get()   {
		String res="";
		try {
			String signResult=addSign();
			if(signResult!=null)return signResult;
			res= com.example.familiycookmenu.utils.util.WebUtils.doGet(this);
		} catch (Exception e) {
			e.printStackTrace();
			res="{showapi_res_code:-1,showapi_res_error:"+e.toString()+"}";
		}
		return res;
	}
	
	public byte[] getAsByte()   {
		byte[]  res=null;
		try {
			String signResult=addSign();
			if(signResult!=null)return signResult.getBytes("utf-8");
			res=com.example.familiycookmenu.utils.util.WebUtils.doGetAsByte(this);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				res=("{showapi_res_code:-1,showapi_res_error:"+e.toString()+"}").getBytes("utf-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
		}
		return res;
	}
	
	private   String errorMsg(String msg){
		String str="{"+Constants.SHOWAPI_RES_CODE+":-1,"+Constants.SHOWAPI_RES_ERROR+":"+msg+","+Constants.SHOWAPI_RES_BODY+":{}}";
		return str;
	}
	
 
 
	
	public static void main(String adfas[]) throws  Exception{ 
	}
	
	
}

