package com.example.familiycookmenu.activity;

import com.example.familiycookmenu.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

public class SplashActvity extends Activity {
	
	private RelativeLayout rlSplash;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		rlSplash = (RelativeLayout) findViewById(R.id.rl_splash);
		initAnimaion();
	}

	private void initAnimaion() {
		AnimationSet set = new AnimationSet(false);
		ScaleAnimation scaleAnimation = new ScaleAnimation
				(0, 1, 0,1 , Animation.RELATIVE_TO_SELF, 0.5f,  Animation.RELATIVE_TO_SELF, 0.5f);//缩放动画，相对于其中心点缩放
		scaleAnimation.setDuration(2000);
		scaleAnimation.setFillAfter(true); //动画结束后保持其状态
		RotateAnimation rotateAnimation = new RotateAnimation
				(0, 360, Animation.RELATIVE_TO_SELF, 0.5f,  Animation.RELATIVE_TO_SELF, 0.5f);//旋转动画，相对于其中心点旋转360度
		rotateAnimation.setDuration(2000);
		rotateAnimation.setFillAfter(true); //动画结束后保持其状态
		
		AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);//渐变动画
		alphaAnimation.setDuration(3000);
		alphaAnimation.setFillAfter(true); //动画结束后保持其状态
		set.addAnimation(alphaAnimation);
		set.addAnimation(rotateAnimation);
		set.addAnimation(scaleAnimation);
		rlSplash.startAnimation(set);
		set.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				Intent intent = new Intent(SplashActvity.this, MainActivity.class);
				startActivity(intent);
			}
		});
	}
}
