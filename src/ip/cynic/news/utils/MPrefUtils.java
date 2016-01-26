package ip.cynic.news.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class MPrefUtils {

	private static final String CONFIG = "config";

	public static void setBoolean(Context ctx, String key, boolean value) {

		SharedPreferences mPrefs = ctx.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
		mPrefs.edit().putBoolean(key, value).commit();

	}

	public static void setMoreBoolean(Context ctx, String key[], boolean value[]) throws Exception {
		
		if(key.length!=value.length){
			throw new Exception("key值与value值数组长度不相等");
		}
		
		if(key.length==0||value.length==0){
			throw new Exception("长度不能为空");
		}
		
		SharedPreferences mPrefs = ctx.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
		Editor edit = mPrefs.edit();
		for (int i = 0; i < value.length; i++) {
			edit.putBoolean(key[i], value[i]);
		}
		edit.commit();

	}

	public static boolean getBoolean(Context ctx, String key, boolean defualtValue) {

		SharedPreferences mPrefs = ctx.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
		return mPrefs.getBoolean(key, defualtValue);

	}

}
