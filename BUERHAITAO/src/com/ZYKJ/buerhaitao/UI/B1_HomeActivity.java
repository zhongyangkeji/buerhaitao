package com.ZYKJ.buerhaitao.UI;

/**
 * 首页界面
 */

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

import com.ZYKJ.buerhaitao.adapter.IndexAdapter;
import com.ZYKJ.buerhaitao.adapter.IndexPageAdapter;
import com.ZYKJ.buerhaitao.base.BaseActivity;
import com.ZYKJ.buerhaitao.data.Carousels;
import com.ZYKJ.buerhaitao.data.Goods;
import com.ZYKJ.buerhaitao.data.HttpAction;
import com.ZYKJ.buerhaitao.data.RequestBean;
import com.ZYKJ.buerhaitao.data.ResultBean;
import com.ZYKJ.buerhaitao.socket.SocketListener;
import com.ZYKJ.buerhaitao.socket.VolleySocket;
import com.ZYKJ.buerhaitao.utils.HttpUtils;
import com.ZYKJ.buerhaitao.utils.Tools;
import com.ZYKJ.buerhaitao.view.ListViewForScroll;
import com.ZYKJ.buerhaitao.view.RequestDailog;
import com.ZYKJ.buerhaitao.view.ToastView;
import com.ZYKJ.buerhaitao.view.TypeDialog;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

public class B1_HomeActivity extends BaseActivity {
	private AutoScrollViewPager viewPager;
	private List<Carousels> imageIdList;

	/** 推荐商品列表 */
	private ListViewForScroll m_list_recommand;
	/** 商品列表adapter */
	private IndexAdapter m_list_adapter;
	/** 推荐商品数据 */
	private List<Goods> m_recommand_data;
	/** 换一批按钮 */
	private TextView m_text_change;
	/** 滚动层 */
	private ScrollView m_scroll;
	/** 单选类型 */
	private RadioGroup m_rgroup_type;
	/** 当前的位置 */
	private int now_pos = 0;
	/** 当前错误界面的显示 */
	private RelativeLayout m_error_layout;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_index);
		initView();
//		RequestDailog.showDialog(this, "");
		// 获取当前界面的数据
		//进行A-http请求
		
	}

	//A-http请求之后的回调 res函数
//	public void getData(final int flag) {
//		super.getData(flag);
//		RequestDailog.showDialog(this, "");
//
//		/** 界面中请求的回调 */
//		if (m_listener == null) {
//			m_listener = new SocketListener() {
//				public void response(ResultBean result) {
//					RequestDailog.closeDialog();
//					if (result.isSucceed()) {// 如果成功返回
//						switch (result.getFlag()) {
//						case 0:// 进入首页请求的数据
//								// 设置弹出的显示
//							if (m_scroll.getVisibility() != View.VISIBLE) {
//								m_scroll.setVisibility(View.VISIBLE);
//							}
//
//							if (m_error_layout.getVisibility() != View.VISIBLE) {
//								m_error_layout.setVisibility(View.GONE);
//							}
//
//							try {
//								JSONObject data_object = new JSONObject(result.getStr_result());
//								imageIdList = JSON.parseObject(data_object.getString("carouselses"),
//												new TypeReference<ArrayList<Carousels>>() {
//												});
//								// 设置轮播
//								viewPager.setAdapter(new IndexPageAdapter(
//										HomeActivity.this, imageIdList));
//								// 设置选中的标识
//								LinearLayout pointLinear = (LinearLayout) findViewById(R.id.gallery_point_linear);
//								for (int i = 0; i < imageIdList.size(); i++) {
//									ImageView pointView = new ImageView(
//											HomeActivity.this);
//									if (i == 0) {
//										pointView
//												.setBackgroundResource(R.drawable.feature_point_cur);
//									} else
//										pointView
//												.setBackgroundResource(R.drawable.feature_point);
//									pointLinear.addView(pointView);
//								}
//								// 获取商品数据
//								m_recommand_data = JSON.parseObject(
//										data_object.getString("list"),
//										new TypeReference<ArrayList<Goods>>() {
//										});
//								m_list_adapter = new IndexAdapter(
//										HomeActivity.this, m_recommand_data);
//								m_list_recommand.setAdapter(m_list_adapter);
//								m_list_recommand
//										.setOnItemClickListener(m_list_adapter);
//
//								// 滚动scrollView到头部
//								m_scroll.smoothScrollTo(0, 0);
//							} catch (Exception e) {
//								e.printStackTrace();
//							}
//							break;
//						}
//					} else {// 失败的操作
//						switch (result.getFlag()) {
//						case 0:// 进入首页请求失败的操作
//								// 界面不显示，显示错误界面
//							m_error_layout.setVisibility(View.VISIBLE);
//							m_scroll.setVisibility(View.GONE);
//							break;
//						}
//					}
//				}
//			};
//		}
//
//		// 构建请求参数需要的bean
//		RequestBean m_request = new RequestBean();
//		m_request.setContext(this);
//		m_request.setRequest_flag(flag);
//		m_request.setListener(m_listener);
//
//		switch (flag) {
//		case 0:// 进入界面时请求的flag
//			m_request.setStr_parmas(null);
//			m_request.setUrl(HttpAction.getCommand(6));
//			VolleySocket.getStringRequest(m_request);
//			break;
//		case 1://
//			break;
//		}
//	}

	Handler uihandler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:// 滚动的回调
				changePointView((Integer) msg.obj);
				break;
			}

		}

	};

	public void initView() {

		m_scroll = (ScrollView) findViewById(R.id.index_scroll);
		m_scroll.setVisibility(View.GONE);
		viewPager = (AutoScrollViewPager) findViewById(R.id.index_viewpage);
		m_text_change = (TextView) findViewById(R.id.index_change);

		LayoutParams pageParms = viewPager.getLayoutParams();
		pageParms.width = Tools.M_SCREEN_WIDTH;
		pageParms.height = Tools.M_SCREEN_WIDTH / 2;

		viewPager.setInterval(2000);
		viewPager.startAutoScroll();

		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			public void onPageSelected(int arg0) {
				// 回调view
				uihandler.obtainMessage(0, arg0).sendToTarget();
			}

			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			public void onPageScrollStateChanged(int arg0) {
			}
		});

		// 推荐商品列表
		m_list_recommand = (ListViewForScroll) findViewById(R.id.index_listView);
		// 单选组选中
		m_rgroup_type = (RadioGroup) findViewById(R.id.index_radio_group);
		m_rgroup_type.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				RadioButton rbtn = (RadioButton) findViewById(checkedId);
				rbtn.setChecked(false);

				switch (checkedId) {
				case R.id.index_type1:
					selectType(11);
					break;
				case R.id.index_type2:
					selectType(14);
					break;
				case R.id.index_type3:
					selectType(9);
					break;
				case R.id.index_type4:
					selectType(4);
					break;
				case R.id.index_type5:
					selectType(3);
					break;
				case R.id.index_type6:// 更多类别
					Tools.Log("更多类别");
					TypeDialog.showDialog(B1_HomeActivity.this,
							new OnItemClickListener() {
								public void onItemClick(AdapterView<?> parent,
										View view, int position, long id) {
									TypeDialog.closeDialog();
									int type = position + 1;
									selectType(type);
								}
							});
					break;
				}

			}
		});

		// 初始化加载失败的显示
		m_error_layout = (RelativeLayout) findViewById(R.id.error_layout);
		m_error_layout.setOnClickListener(this);

		// 按钮监听
		setListener(m_text_change);

	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (viewPager != null) {
			viewPager.startAutoScroll();
		}
		// 重新设置scrollView的位置
		if (m_scroll != null && m_scroll.getVisibility() == View.VISIBLE) {
			m_scroll.smoothScrollTo(0, 0);
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (viewPager != null) {
			viewPager.stopAutoScroll();
		}

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

	public void changePointView(int cur) {
		LinearLayout pointLinear = (LinearLayout) findViewById(R.id.gallery_point_linear);
		View view = pointLinear.getChildAt(now_pos);
		View curView = pointLinear.getChildAt(cur);
		if (view != null && curView != null) {
			ImageView pointView = (ImageView) view;
			ImageView curPointView = (ImageView) curView;
			pointView.setBackgroundResource(R.drawable.feature_point);
			curPointView.setBackgroundResource(R.drawable.feature_point_cur);
			now_pos = cur;
		}
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.index_change:// 换一批
			break;
		case R.id.error_layout:// 错误页面的点击
			//htttp请求
//			getData(0);
			break;
		}

	}

}
