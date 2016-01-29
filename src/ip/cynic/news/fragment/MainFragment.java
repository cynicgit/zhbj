package ip.cynic.news.fragment;

import ip.cynic.news.R;
import ip.cynic.news.base.BasePager;
import ip.cynic.news.base.GovaffaorPager;
import ip.cynic.news.base.HomePager;
import ip.cynic.news.base.NewsPager;
import ip.cynic.news.base.SettingPager;
import ip.cynic.news.base.SmartServicePager;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

@SuppressLint("NewApi")
public class MainFragment extends BaseFragment {

	private ViewPager mViewPager;

	private List<BasePager> lists = new ArrayList<BasePager>();

	private RadioGroup mRgBottom;

	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.fragment_main, null);
		mViewPager = (ViewPager) view.findViewById(R.id.vp_center);
		mRgBottom = (RadioGroup) view.findViewById(R.id.rg_bottom);
		return view;
	}

	@Override
	public void initData() {
		lists.add(new HomePager(mActivity));
		lists.add(new NewsPager(mActivity));
		lists.add(new SmartServicePager(mActivity));
		lists.add(new GovaffaorPager(mActivity));
		lists.add(new SettingPager(mActivity));

		mViewPager.setAdapter(new MyViewPagerAdapter());

		mRgBottom.check(R.id.rb_home);
		listener();
	}

	public void listener() {

		// 禁止viewpager左右滑动
		mViewPager.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return true;
			}
		});

		// 监听radiogroup 某个选中后切换相应的页面
		// mRgBottom.in
		mRgBottom.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// 获取radiogroup 第几个被选中
				int indexOfChild = mRgBottom.indexOfChild(mRgBottom.findViewById(checkedId));
				mViewPager.setCurrentItem(indexOfChild, false);// false 没有切换动画
				lists.get(indexOfChild).initData();// 初始化数据
			}
		});

		lists.get(0).initData();// 默认初始化首页

	}

	public NewsPager getNewsPager() {
		return (NewsPager) lists.get(1);
	}

	class MyViewPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return lists.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view = lists.get(position).mView;
			container.addView(view);
			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

	}

}
