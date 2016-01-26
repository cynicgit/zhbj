package ip.cynic.news.menubase;

import android.app.Activity;
import android.view.View;

public abstract class BaseMenuDetailPager {

	public Activity mActivity;
	
	public View mRootView;
	
	public BaseMenuDetailPager(Activity activity){
		mActivity = activity;
		mRootView = initView();
	}
	
	public abstract View initView();
	
	public void initData(){
		
	}
}
