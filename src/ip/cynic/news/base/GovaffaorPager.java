package ip.cynic.news.base;

import android.app.Activity;
import android.view.View;

public class GovaffaorPager extends BasePager{

	public GovaffaorPager(Activity activity) {
		super(activity);
	}
	
	public void initData(){
		mTvTitle.setText("政务");
		mImgButton.setVisibility(View.VISIBLE);
		setSlidingMenuEnable(true);
	}

}
