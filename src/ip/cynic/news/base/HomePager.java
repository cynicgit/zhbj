package ip.cynic.news.base;

import android.app.Activity;
import android.view.View;

public class HomePager extends BasePager{

	public HomePager(Activity activity) {
		super(activity);
	}

	public void initData(){
		mTvTitle.setText("智慧北京");
		mImgButton.setVisibility(View.GONE);
		setSlidingMenuEnable(false);//关闭菜单
	}
	
}
