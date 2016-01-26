package ip.cynic.news.base;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import ip.cynic.news.R;
import ip.cynic.news.activity.MainActivity;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

public class BasePager {

	public Activity mActivity;
	public View mView;
	public TextView mTvTitle;
	public FrameLayout mFlContent;
	public ImageButton mImgButton;
	
	public BasePager(Activity activity){
		mActivity = activity;
		initViews();
	}

	private void initViews() {
		mView = View.inflate(mActivity, R.layout.activity_basepager, null);
		mTvTitle = (TextView) mView.findViewById(R.id.tv_title);
		mFlContent = (FrameLayout) mView.findViewById(R.id.fl_content);
		mImgButton = (ImageButton)mView.findViewById(R.id.img_menu);
		mImgButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				toggleSlidingMenu();
			}
		});
	}
	
	/**
	 * 打开或关闭侧边栏
	 */
	public void toggleSlidingMenu(){
		MainActivity mainUI = (MainActivity) mActivity;
		SlidingMenu slidingMenu = mainUI.getSlidingMenu();
		slidingMenu.toggle();
	}
	
	public void initData(){
		
	}
	
	public void setSlidingMenuEnable(boolean enable){
		
		MainActivity activity = (MainActivity) mActivity;
		SlidingMenu slidingMenu = activity.getSlidingMenu();
		
		if(enable){
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		}else{
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
		
	}
	
}
