package ip.cynic.news.base;

import java.util.ArrayList;
import java.util.List;

import ip.cynic.news.activity.MainActivity;
import ip.cynic.news.domain.NewsData;
import ip.cynic.news.domain.NewsData.NewsMenuData;
import ip.cynic.news.fragment.LeftMenuFragment;
import ip.cynic.news.global.URL;
import ip.cynic.news.menubase.BaseMenuDetailPager;
import ip.cynic.news.menubase.InteractDetailPager;
import ip.cynic.news.menubase.NewsDetailPager;
import ip.cynic.news.menubase.PhotoDetailPager;
import ip.cynic.news.menubase.TopicDetailPager;
import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class NewsPager extends BasePager {

	private NewsData newsData;
	private List<BaseMenuDetailPager> detailPagers = new ArrayList<BaseMenuDetailPager>();
	
	
	public NewsPager(Activity activity) {
		super(activity);
	}

	public void initData() {
		mImgButton.setVisibility(View.VISIBLE);
		setSlidingMenuEnable(true);
		
		getNewsDataByServer();

		
	}

	public void getNewsDataByServer() {
		
		HttpUtils httpUtils = new HttpUtils(5000);
		httpUtils.send(HttpMethod.GET, URL.CATEGORIES, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				
				String result = responseInfo.result;
				System.out.println("json:"+result);
				parseData(result);
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Log.e("HttpUtils", msg);
				error.printStackTrace();
			}
			
		});
		
	}
	
	public void parseData(String json){
		Gson gson = new Gson();
	    newsData = gson.fromJson(json, NewsData.class);
	    MainActivity mainActivity = (MainActivity) mActivity;
	    LeftMenuFragment leftFragment = mainActivity.getLeftFragment();
	    leftFragment.setMenuData(newsData.data);
	    
	    detailPagers.add(new NewsDetailPager(mActivity,newsData.data.get(0).children));
		detailPagers.add(new TopicDetailPager(mActivity));
		detailPagers.add(new PhotoDetailPager(mActivity));
		detailPagers.add(new InteractDetailPager(mActivity));
	    setCurrentDetailPager(0);
	}
	
	public void setCurrentDetailPager(int position){
		BaseMenuDetailPager pager = detailPagers.get(position);
		mFlContent.removeAllViews();
		mFlContent.addView(pager.mRootView);
		pager.initData();
		//设置标题
		NewsMenuData menuData = newsData.data.get(position);
		mTvTitle.setText(menuData.title);
	}
	

}
