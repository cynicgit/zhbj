package ip.cynic.news.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NoScollerPagerView extends ViewPager {

	public NoScollerPagerView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NoScollerPagerView(Context context) {
		super(context);
	}
	
	/**
	 * 禁止左右滑动
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		return true;
	}
	
	/**
	 * 事件向下传递
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return false;
	}

}
