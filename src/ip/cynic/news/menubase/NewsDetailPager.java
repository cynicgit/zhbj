package ip.cynic.news.menubase;

import ip.cynic.news.R;
import ip.cynic.news.activity.MainActivity;
import ip.cynic.news.domain.NewsData.NewsTabData;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.TabPageIndicator;

public class NewsDetailPager extends BaseMenuDetailPager implements OnPageChangeListener{

	private ViewPager mVpNewsDetail;
	private TabPageIndicator mTabPageIndicator;
	private ImageButton mImgNext;
	
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
		mImgNext = (ImageButton) view.findViewById(R.id.ib_next);
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
		mTabPageIndicator.setOnPageChangeListener(this);
		mImgNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int currentItem = mVpNewsDetail.getCurrentItem();
				mVpNewsDetail.setCurrentItem(++currentItem);
			}
		});
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
			container.addView(pager.mRootView);
			pager.initData();
			return pager.mRootView;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View)object);
		}
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
	}
  
	@Override
	public void onPageSelected(int position) {
		System.out.println("position:"+position);
		MainActivity mainUI = (MainActivity) mActivity;
		SlidingMenu slidingMenu = mainUI.getSlidingMenu();
		if(position==0){//开启侧滑菜单
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
			System.out.println("position:开启");
		}else{
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
			System.out.println("position:关闭");
		}
	}

	@Override
	public void onPageScrollStateChanged(int state) {
	}
	
}
