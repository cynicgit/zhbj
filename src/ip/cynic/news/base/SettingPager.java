package ip.cynic.news.base;

import android.app.Activity;
import android.view.View;

public class SettingPager extends BasePager{

	public SettingPager(Activity activity) {
		super(activity);
	}

	public void initData(){
		mTvTitle.setText("设置");
		mImgButton.setVisibility(View.GONE);
		setSlidingMenuEnable(false);
	}
}
