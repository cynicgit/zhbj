package ip.cynic.news.menubase;

import java.util.List;

import ip.cynic.news.R;
import ip.cynic.news.domain.NewsData.NewsTabData;
import ip.cynic.news.domain.NewsDetailData;
import ip.cynic.news.domain.NewsDetailData.News;
import ip.cynic.news.domain.NewsDetailData.TopNews;
import ip.cynic.news.global.URL;
import ip.cynic.news.view.NewsListView;
import ip.cynic.news.view.NewsListView.RefreshListener;
import ip.cynic.xmpp.utils.ToastUtils;
import android.app.Activity;
import android.location.GpsStatus.NmeaListener;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.viewpagerindicator.CirclePageIndicator;

public class NewsTabPager extends BaseMenuDetailPager implements OnPageChangeListener {

	public NewsTabData newsTabData;
	private NewsDetailData mNewsDetailData;
	private ViewPager mVpTopNews;

	private CirclePageIndicator indicator;
	private TextView mTopNewsTitle;
	private List<TopNews> mTopnews;
	private List<News> mNews;
	private NewsListView mLvNews;
	private String mMoreUrl;

	public NewsTabPager(Activity activity, NewsTabData newsTabData) {
		super(activity);
		this.newsTabData = newsTabData;
	}

	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.activity_news_tab_content, null);
		mLvNews = (NewsListView) view.findViewById(R.id.lv_news_detail);
		
		View headerView = View.inflate(mActivity, R.layout.activity_top_news, null);
		
		mVpTopNews = (ViewPager) headerView.findViewById(R.id.vp_center);
		indicator = (CirclePageIndicator) headerView.findViewById(R.id.indicator);
		mTopNewsTitle = (TextView) headerView.findViewById(R.id.tv_topnews_title);
		mLvNews.addHeaderView(headerView);
		
		mLvNews.setOnRefreshListener(new RefreshListener() {
			@Override
			public void onRefresh() {
				getNewsDataByServer();
			}

			@Override
			public void onLoadMore() {
				if(mMoreUrl!=null){
					getNewsMoreDataByServer();
				}else{
					ToastUtils.showToastSafe(mActivity, "最后一页");
					mLvNews.refreshCompleted(false);
				}
			}
		});
		return view;
	}

	@Override
	public void initData() {
		getNewsDataByServer();
	}

	public void getNewsDataByServer() {
		HttpUtils httpUtils = new HttpUtils(15000);
		httpUtils.send(HttpMethod.GET, URL.BASE_URL + newsTabData.url, new RequestCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String result = responseInfo.result;
				System.out.println("json:" + result);
				parseData(result);
				mLvNews.refreshCompleted(true);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Log.e("HttpUtils", msg);
				error.printStackTrace();
				mLvNews.refreshCompleted(false);
			}

		});

	}
	
	public void getNewsMoreDataByServer() {
		HttpUtils httpUtils = new HttpUtils(15000);
		httpUtils.send(HttpMethod.GET, URL.BASE_URL + newsTabData.url, new RequestCallBack<String>() {
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String result = responseInfo.result;
				System.out.println("json:" + result);
				parseData(result);
				mLvNews.refreshCompleted(true);
			}
			
			@Override
			public void onFailure(HttpException error, String msg) {
				Log.e("HttpUtils", msg);
				error.printStackTrace();
				mLvNews.refreshCompleted(false);
			}
			
		});
		
	}

	protected void parseData(String result) {
		Gson gson = new Gson();
		mNewsDetailData = gson.fromJson(result, NewsDetailData.class);
		mTopnews = mNewsDetailData.data.topnews;
		String url = mNewsDetailData.data.more;
		if(!TextUtils.isEmpty(url)){
			mMoreUrl = URL.BASE_URL + url;
		}else{
			mMoreUrl = null;
		}

		mNews = mNewsDetailData.data.news;
		mVpTopNews.setAdapter(new MyAdapter());
		indicator.setViewPager(mVpTopNews);
		indicator.setSnap(true);
		indicator.onPageSelected(0);
		indicator.setOnPageChangeListener(this);

		mTopNewsTitle.setText(mTopnews.get(0).title);
		
		mLvNews.setAdapter(new MyListAdapter());
	}

	class MyAdapter extends PagerAdapter {

		private BitmapUtils bitmapUtils;

		public MyAdapter() {
			bitmapUtils = new BitmapUtils(mActivity);
		}

		@Override
		public int getCount() {
			return mTopnews.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView imgView = new ImageView(mActivity);
			imgView.setScaleType(ScaleType.FIT_XY);
			TopNews news = mTopnews.get(position);
			System.out.println(news.topimage);
			bitmapUtils.display(imgView, news.topimage);
			container.addView(imgView);
			return imgView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

	}

	class MyListAdapter extends BaseAdapter {

		private BitmapUtils bitmapUtils;

		public MyListAdapter(){
			bitmapUtils = new BitmapUtils(mActivity);
			bitmapUtils.configDefaultLoadFailedImage(R.drawable.pic_item_list_default);
		}
		
		@Override
		public int getCount() {
			return mNews.size();
		}

		@Override
		public Object getItem(int position) {
			return mNews.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View view = null;
			ViewHodler hodler = null;
			if (convertView == null) {
				view = View.inflate(mActivity, R.layout.item_news_list, null);
				hodler = new ViewHodler();
				hodler.ivNews = (ImageView) view.findViewById(R.id.iv_news);
				hodler.tvTitle = (TextView) view.findViewById(R.id.tv_title);
				hodler.tvTime = (TextView) view.findViewById(R.id.tv_time);
				view.setTag(hodler);
			} else {
				view = convertView;
				hodler = (ViewHodler) view.getTag();
			}
			
			News news = mNews.get(position);
			
			bitmapUtils.display(hodler.ivNews, news.listimage);
			hodler.tvTitle.setText(news.title);
			hodler.tvTime.setText(news.pubdate);
			
			return view;
		}

	}

	static class ViewHodler {
		ImageView ivNews;
		TextView tvTitle;
		TextView tvTime;
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
	}

	@Override
	public void onPageSelected(int position) {
		String title = mTopnews.get(position).title;
		mTopNewsTitle.setText(title);
	}

	@Override
	public void onPageScrollStateChanged(int state) {
	}

}
