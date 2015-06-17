package com.ZYKJ.buerhaitao.UI;

/**
 * 首页界面
 */

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.ZYKJ.buerhaitao.base.BaseActivity;
import com.ZYKJ.buerhaitao.view.ToastView;

public class B1_HomeActivity extends BaseActivity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_index);
		
	}
	
	// 退出操作
	private boolean isExit = false;
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isExit == false) {
				isExit = true;
				ToastView toast = new ToastView(getApplicationContext(),
						"再按一次退出程序");
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				Handler mHandler = new Handler() {
					@Override
					public void handleMessage(Message msg) {
						super.handleMessage(msg);
						isExit = false;
					}
				};
				mHandler.sendEmptyMessageDelayed(0, 3000);
				return true;
			} else {
				android.os.Process.killProcess(android.os.Process.myPid());
				return false;
			}
		}
		return true;
	}

	/** 类型选择 */
	public void selectType(int type) {
//		Intent intent = new Intent(this, ShopList.class);
//		intent.putExtra("state", 1);
//		intent.putExtra("type", type);
//		startActivity(intent);
		Toast.makeText(this, type, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.error_layout:// 错误页面的点击
			//htttp请求
			break;
		}

	}

}
