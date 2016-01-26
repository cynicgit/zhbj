package ip.cynic.news.base;

import android.app.Activity;
import android.view.View;

public class SmartServicePager extends BasePager{

	public SmartServicePager(Activity activity) {
		super(activity);
	}

	public void initData(){
		mTvTitle.setText("智慧服务");
		mImgButton.setVisibility(View.VISIBLE);
		setSlidingMenuEnable(true);
	}
}
