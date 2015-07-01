package com.ZYKJ.buerhaitao.UI;

import java.util.HashMap;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.Header;

import com.ZYKJ.buerhaitao.R;
import com.ZYKJ.buerhaitao.adapter.B2_GoodsAdapter;
import com.ZYKJ.buerhaitao.adapter.B2_ShopsAdapter;
import com.ZYKJ.buerhaitao.base.BaseActivity;
import com.ZYKJ.buerhaitao.data.Goods;
import com.ZYKJ.buerhaitao.data.Shop;
import com.ZYKJ.buerhaitao.popupwindow.AddPopWindow;
import com.ZYKJ.buerhaitao.utils.HttpUtils;
import com.ZYKJ.buerhaitao.view.MyListView;
import com.ZYKJ.buerhaitao.view.RequestDailog;
import com.ZYKJ.buerhaitao.view.ToastView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.external.maxwin.view.XListView.IXListViewListener;
import com.loopj.android.http.AsyncHttpResponseHandler;

/**
 * @author lss 2015年6月18日 搜索页
 *
 */
public class B1_a4_SearchActivity extends BaseActivity implements IXListViewListener,OnItemClickListener{
	//返回
	ImageButton a4_back;
	//宝贝/店铺
	TextView tv_a4_baobei;
	//默认，销量，价格，好评
	TextView tv_a4_moren,tv_a4_xiaoliang,tv_a4_jiage,tv_a4_haoping;
	LinearLayout ly_a4_xiaoliang,ly_a4_jiage;
	//销量，价格箭头
	ImageView im_a4_xiaoliangstate,im_a4_jiagestate;
	//产品列表
	MyListView a4_seachlist;
	
	public static int CHANNEL = 0;
	private int mrstate = 0;// 按默认排序的状态，0为第一次点击，1为多次点击
	private int hpstate = 0;// 按好评排序的状态
	private int xlstate = 0;// 按销量排序的状态，0为从高到低，1为从低到高
	private int jgstate = 0;// 按价格排序的状态，0为从低到高，1为从高到低
	
	private Integer key,order=1,page=2,curpage=1;
	private String city_id="88",keyword,id;
	private String lng="1",lat="1";
	private List<Goods> goods;
	private List<Shop> shops;
	private B2_GoodsAdapter goodsAdapter;
	private B2_ShopsAdapter shopsAdapter;

	/**
	 * 初始化Activity
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_a4_search);
		initView();
		requestData();
	}
	
	/**
	 * 加载页面
	 */
	private void initView(){
		a4_back = (ImageButton)findViewById(R.id.a4_back);
		tv_a4_baobei = (TextView)findViewById(R.id.tv_a4_baobei);
		tv_a4_moren = (TextView)findViewById(R.id.tv_a4_moren);
		tv_a4_xiaoliang = (TextView)findViewById(R.id.tv_a4_xiaoliang);
		tv_a4_jiage = (TextView)findViewById(R.id.tv_a4_jiage);
		tv_a4_haoping = (TextView)findViewById(R.id.tv_a4_haoping);
		im_a4_xiaoliangstate = (ImageView)findViewById(R.id.im_a4_xiaoliangstate);
		im_a4_jiagestate = (ImageView)findViewById(R.id.im_a4_jiagestate);
		ly_a4_xiaoliang = (LinearLayout)findViewById(R.id.ly_a4_xiaoliang);
		ly_a4_jiage = (LinearLayout)findViewById(R.id.ly_a4_jiage);
		a4_seachlist = (MyListView)findViewById(R.id.a4_seachlist);

		a4_seachlist.setPullLoadEnable(true);
		a4_seachlist.setPullRefreshEnable(true);
		a4_seachlist.setXListViewListener(this, 0);
		a4_seachlist.setOnItemClickListener(this);
		a4_seachlist.setRefreshTime();
		setListener(a4_back,tv_a4_baobei,tv_a4_moren,tv_a4_haoping,ly_a4_xiaoliang,ly_a4_jiage);
	}
	
	/**
	 * 请求数据
	 */
	private void requestData(){
		id = getIntent().getStringExtra(CHANNEL == 0?"gc_id":"sc_id");//店铺、宝贝编号
		HashMap<String,String> params = new HashMap<String,String>();
		params.put("key", String.valueOf(key));//排序方式 1-销量 2-评价 3-价格 空-按距离排序
		params.put("order", String.valueOf(order));//1-升序 2-降序
		params.put("page", String.valueOf(page));//每页数量
		params.put("curpage", String.valueOf(curpage));//当前页码
		params.put(CHANNEL == 0?"gc_id":"sc_id", id);//店铺、宝贝编号
		params.put("city_id", city_id);//城市id
		params.put("lng", lng);//经度
		params.put("lat", lat);//纬度
		params.put("keyword", keyword);//搜索关键字
		RequestDailog.showDialog(this, "正在加载数据，请稍后");
		if(CHANNEL == 0){
			HttpUtils.getGoodsList(curpage == 1?res_getGoodsList:res_getMoreGoodsList, HttpUtils.iterateParams(params));
		}else{
			HttpUtils.getStoreList(curpage == 1?res_getStoreList:res_getMoreStoreList, HttpUtils.iterateParams(params));
		}
	}

	@Override
	public void onRefresh(int id) {
		/*下拉加载*/
		curpage = 1;
		requestData();
	}

	@Override
	public void onLoadMore(int id) {
		/*上拉刷新*/
		curpage += 1;
		requestData();
	}
	
	AsyncHttpResponseHandler res_getGoodsList = new EntityHttpResponseHandler<Goods>(Goods.class){
		@Override
		public void onReadSuccess(List<Goods> list) {
			goods = list;
			goodsAdapter = new B2_GoodsAdapter(B1_a4_SearchActivity.this, goods);
			a4_seachlist.setAdapter(goodsAdapter);
		}
	};
	
	AsyncHttpResponseHandler res_getMoreGoodsList = new EntityHttpResponseHandler<Goods>(Goods.class){
		@Override
		public void onReadSuccess(List<Goods> list) {
			goods.addAll(list);
			goodsAdapter.notifyDataSetChanged();
		}
	};
	
	AsyncHttpResponseHandler res_getStoreList = new EntityHttpResponseHandler<Shop>(Shop.class){
		@Override
		public void onReadSuccess(List<Shop> list) {
			shops = list;
			shopsAdapter = new B2_ShopsAdapter(B1_a4_SearchActivity.this, shops);
			a4_seachlist.setAdapter(shopsAdapter);
		}
	};
	
	AsyncHttpResponseHandler res_getMoreStoreList = new EntityHttpResponseHandler<Shop>(Shop.class){
		@Override
		public void onReadSuccess(List<Shop> list) {
			shops.addAll(list);
			shopsAdapter.notifyDataSetChanged();
		}
	};
	
	abstract class EntityHttpResponseHandler<T> extends AsyncHttpResponseHandler{
		
		private Class<T> clzz;
		
		public EntityHttpResponseHandler(Class<T> clzz){this.clzz = clzz;}

		@Override
		public void onSuccess(int statusCode, Header[] headers, byte[] responseString) {
			RequestDailog.closeDialog();
			JSONObject response = (JSONObject)JSON.parse(responseString);
			JSONObject datas = response.getJSONObject("datas");
			JSONArray jsonArray = datas.getJSONArray("goods_list");
			List<T> list = JSONArray.parseArray(jsonArray.toString(), clzz);
			onReadSuccess(list);
		}

		@Override
		public void onFailure(int statusCode, Header[] headers, byte[] responseString, Throwable throwable) {
			RequestDailog.closeDialog();
			new ToastView(B1_a4_SearchActivity.this, "加载失败");
		}
		
	    public abstract void  onReadSuccess(List<T> list);
	}

	@Override
	public void onItemClick(AdapterView<?> groupView, View currentView, int position, long id) {
		/*店铺、宝贝详情*/
		Goods good = goods.get(position-1);
		Toast.makeText(B1_a4_SearchActivity.this, good.getGoods_id(), Toast.LENGTH_LONG).show();
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.a4_back:
			B1_a4_SearchActivity.this.finish();
			break;
		case R.id.tv_a4_baobei:
			AddPopWindow addPopWindow = new AddPopWindow(B1_a4_SearchActivity.this,tv_a4_baobei);
			addPopWindow.showAtLocation(tv_a4_baobei, Gravity.LEFT | Gravity.TOP, 110,65 );
//			addPopWindow.showPopupWindow(tv_baobei);
			break;
		case R.id.tv_a4_moren:
			if (mrstate == 0) {
				tv_a4_moren.setTextColor(Color.parseColor("#73498b"));
				tv_a4_xiaoliang.setTextColor(Color.parseColor("#808080"));
				tv_a4_jiage.setTextColor(Color.parseColor("#808080"));
				tv_a4_haoping.setTextColor(Color.parseColor("#808080"));
				im_a4_xiaoliangstate.setImageDrawable(getResources().getDrawable(R.drawable.a4_searchmoren));
				im_a4_jiagestate.setImageDrawable(getResources().getDrawable(R.drawable.a4_searchmoren));
				
				mrstate = 1;
				hpstate = 0;
				xlstate = 0;
				jgstate = 0;
			} else {
				mrstate = 1;
				hpstate = 0;
				xlstate = 0;
				jgstate = 0;
			}
			break;
		case R.id.ly_a4_xiaoliang:
			tv_a4_moren.setTextColor(Color.parseColor("#808080"));
			tv_a4_xiaoliang.setTextColor(Color.parseColor("#73498b"));
			tv_a4_jiage.setTextColor(Color.parseColor("#808080"));
			tv_a4_haoping.setTextColor(Color.parseColor("#808080"));
			im_a4_jiagestate.setImageDrawable(getResources().getDrawable(R.drawable.a4_searchmoren));
			if (xlstate==0) {//销量最多
				im_a4_xiaoliangstate.setImageDrawable(getResources().getDrawable(R.drawable.a4_searcjshang));
				xlstate = 1;
			}else{//销量最少
				im_a4_xiaoliangstate.setImageDrawable(getResources().getDrawable(R.drawable.a4_searchxia));
				xlstate = 0;
			}
			mrstate = 0;
			hpstate = 0;
			jgstate = 0;
			break;
		case R.id.ly_a4_jiage:
			tv_a4_moren.setTextColor(Color.parseColor("#808080"));
			tv_a4_xiaoliang.setTextColor(Color.parseColor("#808080"));
			tv_a4_jiage.setTextColor(Color.parseColor("#73498b"));
			tv_a4_haoping.setTextColor(Color.parseColor("#808080"));
			im_a4_xiaoliangstate.setImageDrawable(getResources().getDrawable(R.drawable.a4_searchmoren));
			if (jgstate==0) {//销量最多
				im_a4_jiagestate.setImageDrawable(getResources().getDrawable(R.drawable.a4_searcjshang));
				jgstate = 1;
			}else{//销量最少
				im_a4_jiagestate.setImageDrawable(getResources().getDrawable(R.drawable.a4_searchxia));
				jgstate = 0;
			}
			mrstate = 0;
			hpstate = 0;
			xlstate = 0;
			break;
		case R.id.tv_a4_haoping:
			if (hpstate == 0) {
				tv_a4_moren.setTextColor(Color.parseColor("#808080"));
				tv_a4_xiaoliang.setTextColor(Color.parseColor("#808080"));
				tv_a4_jiage.setTextColor(Color.parseColor("#808080"));
				tv_a4_haoping.setTextColor(Color.parseColor("#73498b"));
				im_a4_xiaoliangstate.setImageDrawable(getResources().getDrawable(R.drawable.a4_searchmoren));
				im_a4_jiagestate.setImageDrawable(getResources().getDrawable(R.drawable.a4_searchmoren));
				
				mrstate = 0;
				hpstate = 1;
				xlstate = 0;
				jgstate = 0;
			} else {
				mrstate = 0;
				hpstate = 1;
				xlstate = 0;
				jgstate = 0;
			}
			break;

		default:
			break;
		}

	}
}