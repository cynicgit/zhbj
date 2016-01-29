package ip.cynic.news.menubase;

import java.util.List;

import ip.cynic.news.R;
import ip.cynic.news.domain.NewsData.NewsTabData;
import ip.cynic.news.domain.NewsDetailData;
import ip.cynic.news.domain.NewsDetailData.News;
import ip.cynic.news.domain.NewsDetailData.TopNews;
import ip.cynic.news.global.URL;
import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.viewpagerindicator.CirclePageIndicator;

public class NewsTabPager extends BaseMenuDetailPager implements OnPageChangeListener{

	public NewsTabData newsTabData;
	private NewsDetailData mNewsDetailData;
	private ViewPager mVpTopNews;

	private CirclePageIndicator indicator;
	private TextView mTopNewsTitle;
	private List<TopNews> topnews;
	private List<News> news;

	public NewsTabPager(Activity activity, NewsTabData newsTabData) {
		super(activity);
		this.newsTabData = newsTabData;
	}

	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.activity_news_tab_content, null);
		mVpTopNews = (ViewPager) view.findViewById(R.id.vp_center);
		indicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
		mTopNewsTitle = (TextView) view.findViewById(R.id.tv_topnews_title);
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
				// System.out.println("json:" + result);
				parseData(result);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Log.e("HttpUtils", msg);
				error.printStackTrace();
			}

		});

	}

	protected void parseData(String result) {
		Gson gson = new Gson();
		mNewsDetailData = gson.fromJson(result, NewsDetailData.class);
		topnews = mNewsDetailData.data.topnews;
		news = mNewsDetailData.data.news;
		
		mVpTopNews.setAdapter(new MyAdapter());
		indicator.setViewPager(mVpTopNews);
		indicator.setSnap(true);
		indicator.onPageSelected(0);
		indicator.setOnPageChangeListener(this);
		
		mTopNewsTitle.setText(topnews.get(0).title);
	}

	class MyAdapter extends PagerAdapter {

		private BitmapUtils bitmapUtils;

		public MyAdapter() {
			bitmapUtils = new BitmapUtils(mActivity);
		}

		@Override
		public int getCount() {
			return topnews.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView imgView = new ImageView(mActivity);
			imgView.setScaleType(ScaleType.FIT_XY);
			TopNews news = topnews.get(position);
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

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
	}

	@Override
	public void onPageSelected(int position) {
		String title = topnews.get(position).title;
		mTopNewsTitle.setText(title);
	}

	@Override
	public void onPageScrollStateChanged(int state) {
	}

}
