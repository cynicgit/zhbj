package ip.cynic.news.activity;

import ip.cynic.news.R;
import ip.cynic.news.fragment.LeftMenuFragment;
import ip.cynic.news.fragment.MainFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;



public class MainActivity extends SlidingFragmentActivity{

	private static final String LEFT_MENU = "left_menu";
	private static final String MAIN_CONTENT = "main_content";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main); //设置主界面
		
		setBehindContentView(R.layout.activity_left_menu);//左侧菜单
		
		SlidingMenu slidingMenu = getSlidingMenu();//获取侧边栏对象
		slidingMenu.setMode(SlidingMenu.LEFT); //设置模式 菜单在左边
		// 设置触发方式 边缘滑动出现 或者屏幕中滑动出现
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		slidingMenu.setBehindOffset(300);//设置主界面预留宽度
		
		initFragment();
	}

	private void initFragment() {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		
		transaction.replace(R.id.fl_left_menu, new LeftMenuFragment(), LEFT_MENU);
		transaction.replace(R.id.fl_main, new MainFragment(), MAIN_CONTENT);
		
		transaction.commit();
		
	}
	
	
	public LeftMenuFragment getLeftFragment(){
		FragmentManager fm = getSupportFragmentManager();
		return (LeftMenuFragment) fm.findFragmentByTag(LEFT_MENU);
	}
	
	public MainFragment getMainFragment(){
		FragmentManager fm = getSupportFragmentManager();
		return (MainFragment) fm.findFragmentByTag(MAIN_CONTENT);
	}
	

}
