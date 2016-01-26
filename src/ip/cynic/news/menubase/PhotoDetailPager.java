package ip.cynic.news.menubase;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class PhotoDetailPager extends BaseMenuDetailPager{

	public PhotoDetailPager(Activity activity) {
		super(activity);
	}

	@Override
	public View initView() {
		TextView view = new TextView(mActivity);
		view.setText("组图详情");
		view.setTextColor(Color.RED);
		view.setGravity(Gravity.CENTER);
		return view;
	}

}
