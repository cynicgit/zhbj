package ip.cynic.news.menubase;

import ip.cynic.news.R;
import ip.cynic.news.domain.NewsData.NewsTabData;

import java.util.ArrayList;
import java.util.List;

import com.viewpagerindicator.TabPageIndicator;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

public class NewsDetailPager extends BaseMenuDetailPager{

	private ViewPager mVpNewsDetail;
	private TabPageIndicator mTabPageIndicator;
	
	
	private List<NewsTabData>  mTabDatas;
	private List<NewsTabPager> mTabPager;
	public NewsDetailPager(Activity activity) {
		super(activity);
	}

	public NewsDetailPager(Activity activity, List<NewsTabData> children) {
		super(activity);
		mTabDatas = children;
	}

	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.activity_news_detail, null);
		mVpNewsDetail = (ViewPager) view.findViewById(R.id.vp_center);
		mTabPageIndicator = (TabPageIndicator) view.findViewById(R.id.tab_indicator);
		return view;
	}

	
	@Override
	public void initData() {
		mTabPager = new ArrayList<NewsTabPager>();
		for (int i = 0; i < mTabDatas.size(); i++) {
			NewsTabData newsTabData = mTabDatas.get(i);
			mTabPager.add(new NewsTabPager(mActivity, newsTabData));
		}
		
		mVpNewsDetail.setAdapter(new MyAdapter());
		mTabPageIndicator.setViewPager(mVpNewsDetail);
	}
	
	class MyAdapter extends PagerAdapter{

		
		@Override
		public CharSequence getPageTitle(int position) {
			return mTabDatas.get(position).title;
		}
		
		@Override
		public int getCount() {
			return mTabPager.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			NewsTabPager pager = mTabPager.get(position);
			mVpNewsDetail.addView(pager.mRootView);
			pager.initData();
			return pager.mRootView;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			mVpNewsDetail.removeView((View)object);
		}
	}
	
}
