package ip.cynic.news.activity;

import ip.cynic.news.R;
import ip.cynic.news.utils.MPrefUtils;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

public class GuideActivity extends Activity {

	private ViewPager vpGuide;
	private LinearLayout llPoints;
	private View redPoint;
	private Button btnGo;

	private int[] viewPagerIDS = new int[] { R.drawable.guide_1,
			R.drawable.guide_2, R.drawable.guide_3 };

	private List<ImageView> imageLists;
	private int pointWidth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		initUI();
		initData();
		listener();
	}

	private void initUI() {
		setContentView(R.layout.activity_guide);
		llPoints = (LinearLayout) findViewById(R.id.ll_points);
		redPoint = findViewById(R.id.red_point);
		vpGuide = (ViewPager) findViewById(R.id.vp_guide);
		btnGo = (Button) findViewById(R.id.btn_go);
	}

	private void initData() {
		imageLists = new ArrayList<ImageView>();
		for (int i = 0; i < viewPagerIDS.length; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setImageResource(viewPagerIDS[i]);
			imageLists.add(imageView);
		}

		for (int i = 0; i < viewPagerIDS.length; i++) {
			View view = new View(this);
			view.setBackgroundResource(R.drawable.guide_gray_shape);
			LayoutParams params = new LayoutParams(10, 10);
			if (i > 0) {
				params.leftMargin = 10;
			}
			view.setLayoutParams(params);
			llPoints.addView(view);
		}

		vpGuide.setAdapter(new GuideAdpater());

	}
	
	private void listener(){
		//获取视图树 设置layout绘制完的监听事件 来获取圆点之间的距离
		redPoint.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			@Override
			public void onGlobalLayout() {
				//解除监听事件 防止重复调用
				redPoint.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				pointWidth = llPoints.getChildAt(1).getLeft() - llPoints.getChildAt(0).getLeft();
				System.out.println(pointWidth);
			}
		});
		
		//设置viewpager的页面移动事件  使小红点移动
		vpGuide.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				//设置按钮显示隐藏
				if(position==imageLists.size()-1){
					btnGo.setVisibility(View.VISIBLE);
				}else{
					btnGo.setVisibility(View.INVISIBLE);
				}
			}
			
			/**
			 * @param position 当前页数
			 * @param positionOffset 移动百分比
			 * @param positionOffsetPixels 移动的像素
			 */
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				int delay = (int) (pointWidth * (positionOffset + position));
				RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) redPoint.getLayoutParams();
				System.out.println(params.leftMargin);
				params.leftMargin = delay; //相对于父布局
				
				redPoint.setLayoutParams(params);
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
			}
		});
		
		btnGo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MPrefUtils.setBoolean(GuideActivity.this, "guide_show", true);
				startActivity(new Intent(GuideActivity.this,MainActivity.class));
				finish();
			}
		});
	}

	class GuideAdpater extends PagerAdapter {

		@Override
		public int getCount() {
			return imageLists.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			vpGuide.removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			vpGuide.addView(imageLists.get(position));
			return imageLists.get(position);
		}

	}

}
