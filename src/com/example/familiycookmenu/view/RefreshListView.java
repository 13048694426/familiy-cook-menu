package com.example.familiycookmenu.view;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.example.familiycookmenu.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class RefreshListView extends ListView {
	
	private int startY= -1;
	
	private int viewHeight;
	
	private View headView;
	
	private int currentState = REFRESH_PULL;
	
	private int padding; //滑动的距离代表着滑动的方向
	
	private static final int REFRESH_PULL = 0;
	
	private static final int REFRESH_RELEASE = 1;
	
	private static final int REFRESHING = 2;
	
	private TextView tvState;
	
	private TextView tvTime;
	
	private ImageView ivArrow;
	
//	@ViewInject(R.id.pb_refresh)
	private ProgressBar pbRefresh;
	

	public RefreshListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initHeadView();
	}

	public RefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initHeadView();
	}

	public RefreshListView(Context context) {
		super(context);
		initHeadView();
	}
//初始化头布局
	private void initHeadView() {
	    headView = View.inflate(getContext(), R.layout.view_listview_refresh, null);
	    tvState = (TextView) headView.findViewById(R.id.tv_refresh);
	    tvTime = (TextView) headView.findViewById(R.id.tv_time);
	    ivArrow = (ImageView) headView.findViewById(R.id.iv_arrow);
	    pbRefresh = (ProgressBar) headView.findViewById(R.id.pb_refresh);
		this.addHeaderView(headView);
		headView.measure(0, 0);//先测量
	    viewHeight = headView.getMeasuredHeight();
		headView.setPadding(0, -viewHeight, 0, 0);
		initAnimation();
		pbRefresh.setVisibility(View.INVISIBLE);
		ivArrow.setVisibility(View.VISIBLE);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startY = (int) ev.getRawY();
			break;
			
		case MotionEvent.ACTION_MOVE:
			int endY = (int) ev.getRawY();
			if (startY == -1) { //防止没触发按下事件
				startY = (int) ev.getRawY();
			}
			if (currentState == REFRESHING) {
				break;
			}
		    int dy = endY - startY; 
			if (dy > 0 || this.getFirstVisiblePosition() == 0) {
				currentState = REFRESH_PULL;
			    padding = dy - viewHeight;
				headView.setPadding(0, dy-viewHeight, 0, 0);
				if (padding < 0 && currentState != REFRESH_PULL) {
					currentState = REFRESH_PULL;
					refreshState();
				} else if (padding > 0 && currentState != REFRESH_RELEASE) {
					currentState = REFRESH_RELEASE;
					refreshState();
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			startY = -1;
			if (padding > 0 && currentState != REFRESHING) {
				currentState = REFRESHING;
				refreshState();
			}
			break;
		
		}
		
		return super.onTouchEvent(ev) ;
	}

	private void refreshState() {
		switch(currentState) {
		case REFRESH_PULL:
			tvState.setText("下拉刷新");
			pbRefresh.setVisibility(View.INVISIBLE);
			ivArrow.setVisibility(View.VISIBLE);
			tvTime.setText("最后更新时间：" + setCurrentTime());
			ivArrow.startAnimation(animaDown);
			break;
		case REFRESH_RELEASE:
			tvState.setText("松开刷新");
			pbRefresh.setVisibility(View.INVISIBLE);
			ivArrow.setVisibility(View.VISIBLE);
			tvTime.setText("最后更新时间：" + setCurrentTime());
			ivArrow.startAnimation(animaUp);
			break;
		case REFRESHING:
			ivArrow.clearAnimation();
			tvState.setText("正在刷新");
			headView.setPadding(0, 0, 0, 0);
			pbRefresh.setVisibility(View.VISIBLE);
			ivArrow.setVisibility(View.INVISIBLE);
			tvTime.setText("最后更新时间：" + setCurrentTime());
			if (mListener != null) {
				mListener.onRefresh();
			}
			break;
		}
	}

	private String setCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TimeZone timeZone = TimeZone.getTimeZone("Asia/Shanghai");
		sdf.setTimeZone(timeZone);
		Date date = new Date();
		String desDate = sdf.format(date);
		return desDate;
	}
	
	private RefreshListener mListener;
	
//	 下拉刷新监听接口
    public interface RefreshListener {
		public void onRefresh();
	}
    
    public void setRefreshListener(RefreshListener listener) {
    	mListener = listener;
    }
	
    public void onRefreshComplete() {
    	tvState.setText("下拉刷新");
		pbRefresh.setVisibility(View.INVISIBLE);
		ivArrow.setVisibility(View.VISIBLE);
		tvTime.setText("最后更新时间：" + setCurrentTime());
    	headView.setPadding(0, -viewHeight, 0, 0);
    }
    
    private RotateAnimation animaDown;
    private RotateAnimation animaUp;
    public void initAnimation() {
     animaDown = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF,
    			0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
     animaDown.setFillAfter(true);
     animaDown.setDuration(200);
    	
    	
     animaUp = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF,
    			0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
     animaUp.setFillAfter(true);
     animaUp.setDuration(200);
    }
    
}
