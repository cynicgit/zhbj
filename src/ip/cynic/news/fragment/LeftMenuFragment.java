package ip.cynic.news.fragment;

import ip.cynic.news.R;
import ip.cynic.news.activity.MainActivity;
import ip.cynic.news.base.NewsPager;
import ip.cynic.news.domain.NewsData.NewsMenuData;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class LeftMenuFragment extends BaseFragment {

	@ViewInject(R.id.lv_menu_news)
	private ListView lvMenuNews;

	private List<NewsMenuData> mMenuData;
	
	private MenuAdapter mMenuAdapter;

	private int mCurrentPos;

	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.fragment_left, null);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void initData() {
		lvMenuNews.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				mCurrentPos = position;
				mMenuAdapter.notifyDataSetChanged();
				setCurrentDetailPager(position);
				toggleSlidingMenu();
			}
		});
		
	}
	
	public void toggleSlidingMenu(){
		MainActivity mainUI = (MainActivity) mActivity;
		SlidingMenu slidingMenu = mainUI.getSlidingMenu();
		slidingMenu.toggle();
	}
	
	public void setCurrentDetailPager(int position){
		MainActivity mainUI = (MainActivity) mActivity;
		MainFragment mainFragment = mainUI.getMainFragment();
		NewsPager newsPager = mainFragment.getNewsPager();
		newsPager.setCurrentDetailPager(position);
	}

	public void setMenuData(List<NewsMenuData> data) {
		mMenuData = data;
		mMenuAdapter = new MenuAdapter();
		lvMenuNews.setAdapter(mMenuAdapter);
	}

	class MenuAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mMenuData.size();
		}

		@Override
		public NewsMenuData getItem(int position) {
			return mMenuData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(mActivity, R.layout.item_left_news_menu, null);
			TextView textView = (TextView) view.findViewById(R.id.tv_menu_title);
			NewsMenuData item = getItem(position);
			textView.setText(item.title);

			if (mCurrentPos == position) {
				textView.setEnabled(true);
			} else {
				textView.setEnabled(false);
			}

			return view;
		}

	}

}
