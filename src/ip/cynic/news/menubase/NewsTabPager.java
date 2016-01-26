package ip.cynic.news.menubase;

import ip.cynic.news.domain.NewsData.NewsTabData;
import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public class NewsTabPager extends BaseMenuDetailPager{

	public NewsTabData newsTabData;
	private TextView view;
	
	public NewsTabPager(Activity activity, NewsTabData newsTabData) {
		super(activity);
		this.newsTabData = newsTabData;
	}

	@Override
	public View initView() {
		view = new TextView(mActivity);
		view.setTextColor(Color.RED);
		view.setGravity(Gravity.CENTER);
		return view;
	}
	
	@Override
	public void initData() {
		view.setText(newsTabData.title);
	}

}
