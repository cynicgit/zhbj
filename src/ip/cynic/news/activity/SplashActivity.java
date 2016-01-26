package ip.cynic.news.activity;

import ip.cynic.news.R;
import ip.cynic.news.utils.MPrefUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

public class SplashActivity extends Activity {

	private RelativeLayout ilSplash;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		ilSplash = (RelativeLayout) findViewById(R.id.iv_splash);
		initAnimation();
	}

	public void initAnimation() {

		AnimationSet animationSet = new AnimationSet(false);

		// 旋转动画
		RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);

		rotate.setDuration(1500);
		rotate.setFillAfter(true);// 保持动画结束后状态

		ScaleAnimation scale = new ScaleAnimation(0, 1f, 0, 1f, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);

		scale.setDuration(1500);
		scale.setFillAfter(true);// 保持动画结束后状态

		AlphaAnimation alpha = new AlphaAnimation(0, 1f);

		alpha.setDuration(2000);
		alpha.setFillAfter(true);// 保持动画结束后状态

		animationSet.addAnimation(rotate);
		animationSet.addAnimation(scale);
		animationSet.addAnimation(alpha);

		animationSet.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				jump();
			}
		});

		ilSplash.startAnimation(animationSet);
	}

	/**
	 * 跳转页面
	 */
	private void jump() {
		boolean guideShow = MPrefUtils.getBoolean(this, "guide_show", false);
		if (!guideShow) {
			startActivity(new Intent(SplashActivity.this, GuideActivity.class));
		} else {
			startActivity(new Intent(SplashActivity.this, MainActivity.class));
		}
		finish();
	}

}
