package com.ZYKJ.buerhaitao.UI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ZYKJ.buerhaitao.R;
import com.ZYKJ.buerhaitao.base.BaseActivity;
import com.ZYKJ.buerhaitao.utils.HttpUtils;
import com.ZYKJ.buerhaitao.utils.Tools;
import com.ZYKJ.buerhaitao.view.RequestDailog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * @author lss 2015年7月1日 商品详情
 *
 */
public class Sp_GoodsInfoActivity extends BaseActivity{
	//标题
 	private TextView tv_sp_title;
 	//返回
	private ImageView im_sp_back;
	//购物车
	private ImageView im_sp_gouwuche;
//	List<Map<String, String>> data = new ArrayList<Map<String, String>>();
	String key,goods_id;
	String tv_qxzlx = "",tv_qxzlx1 = "",tv_qxzlx2 = "";
	//商品名称
	private TextView tv_sp_spname;
	//销售价
	private TextView tv_xiaoshoujia;
	//市场价
	private TextView tv_shichangjia;
	//所属二级分类
	private TextView tv_sp_ssejfl;
	//已售件数
	private TextView tv_yishoujian;
	//浏览量
	private TextView tv_liulancount;
	//选择规格类型
	private RelativeLayout rl_chooseleixing;
	//宝贝评价(1731)
	private TextView tv_sp_babypingjia;
	//总评分：
	private TextView tv_zongpinfen;
	//用户头像
	private ImageView im_userimage;
	//用户名称
	private TextView tv_username;
	//评星
	private RatingBar xiangqing_rating_bar;
	//商品评价
	private TextView tv_sp_pingjia;
	//产品参数
	private TextView tv_chanpincanshu;
	//查看更多评价
	private LinearLayout ll_ckgdpj;
	//继续拖动，查看图文详情
	private LinearLayout ll_chakantuwen;
	//评价数
	private String pingjia;
	//加入购物车
	private LinearLayout ll_sp_addincar;
	//商品分享
	private LinearLayout ll_goods_fenxiang;
	private String chanpinprice,kucun,xsprice;
	private JSONArray leixing1;
	private int pj=0;
	private JSONObject jsonitemz,xuanxiang;
	private String choosejiage,choosexinghao;
	//选择规格类型
	private TextView tv_xzgglx;
	//立即购买
	private LinearLayout ll_ljgm;
	private String goodsid;
	private String xzhdgg=null;//选择后的goodsid
	private String tv_qxzlx_yincang=null;//隐藏的控件存了选择了的型号的id
	private String price;
	private Intent itaddcar1;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_shangpinxiangqing);
		initView();
		HttpUtils.getGoodsInfo(res_goodsinfo, goods_id);
	}
	
	private void initView(){
		tv_sp_title = (TextView)findViewById(R.id.tv_sp_title);
		im_sp_back= (ImageView)findViewById(R.id.im_sp_back);
		im_sp_gouwuche = (ImageView)findViewById(R.id.im_sp_gouwuche);
		goods_id = getIntent().getStringExtra("goods_id");
		tv_sp_spname = (TextView)findViewById(R.id.tv_sp_spname);
		tv_xiaoshoujia = (TextView)findViewById(R.id.tv_xiaoshoujia);
		tv_shichangjia = (TextView)findViewById(R.id.tv_shichangjia);
		tv_sp_ssejfl = (TextView)findViewById(R.id.tv_sp_ssejfl);
		tv_yishoujian = (TextView)findViewById(R.id.tv_yishoujian);
		tv_liulancount = (TextView)findViewById(R.id.tv_liulancount);
		rl_chooseleixing = (RelativeLayout)findViewById(R.id.rl_chooseleixing);
		tv_sp_babypingjia = (TextView)findViewById(R.id.tv_sp_babypingjia);
		tv_zongpinfen = (TextView)findViewById(R.id.tv_zongpinfen);
		im_userimage = (ImageView)findViewById(R.id.im_userimage);
		tv_username = (TextView)findViewById(R.id.tv_username);
		xiangqing_rating_bar = (RatingBar)findViewById(R.id.xiangqing_rating_bar);
		tv_sp_pingjia = (TextView)findViewById(R.id.tv_sp_pingjia);
		tv_chanpincanshu = (TextView)findViewById(R.id.tv_chanpincanshu);
		ll_ckgdpj = (LinearLayout)findViewById(R.id.ll_ckgdpj);
		ll_chakantuwen = (LinearLayout)findViewById(R.id.ll_chakantuwen);
		ll_sp_addincar = (LinearLayout)findViewById(R.id.ll_sp_addincar);
		ll_goods_fenxiang = (LinearLayout)findViewById(R.id.ll_goods_fenxiang);
		ll_ljgm = (LinearLayout)findViewById(R.id.ll_ljgm);
//		key = getSharedPreferenceValue("key");
		tv_xzgglx = (TextView)findViewById(R.id.tv_xzgglx);
		tv_shichangjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		setListener(im_sp_back,im_sp_gouwuche,ll_ckgdpj,ll_sp_addincar,rl_chooseleixing,ll_goods_fenxiang,ll_ljgm);
	}
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.im_sp_back:
			Sp_GoodsInfoActivity.this.finish();
			break;
		case R.id.im_sp_gouwuche:
			Intent itgwc = new Intent(Sp_GoodsInfoActivity.this,B3_ShoppingCartActivity.class);
			startActivity(itgwc);
			break;
		case R.id.ll_ckgdpj:
			Intent iteckpj = new Intent(Sp_GoodsInfoActivity.this,Sp_a1_GoodsEvaluation.class);
			iteckpj.putExtra("goods_id",goods_id);
			iteckpj.putExtra("sp_a1_title",pingjia);
			startActivity(iteckpj);
			break;
		case R.id.ll_sp_addincar:
			XuanZeLeiXing();
			break;
		case R.id.ll_ljgm://立即购买
			if (tv_xzgglx.getText().equals("选择规格类型")||tv_xzgglx.getText().equals("")) {
				XuanZeLeiXing();
			}else {
				if(leixing1.length()==1){
					try {
						String a = tv_qxzlx_yincang.substring(0,tv_qxzlx_yincang.length()-1);
						JSONObject jso=new JSONObject(choosexinghao);
						goodsid = jso.getString(a);
						Intent itmrhd = new Intent();
						itmrhd.setClass(Sp_GoodsInfoActivity.this, JieSuan1Activity.class);
						itmrhd.putExtra("allpri", price.substring(1, price.length()-1));
						itmrhd.putExtra("xzhdgg", xzhdgg);
						startActivity(itmrhd);
					} catch (Exception e) {
					}
				}
				if(leixing1.length()==2){
					if (tv_qxzlx1==null||tv_qxzlx1==""||tv_qxzlx2==null||tv_qxzlx2=="") {
						XuanZeLeiXing();
					}else {
//						立即购买
						try {
							String a = tv_qxzlx_yincang;
							JSONObject jso=new JSONObject(choosexinghao);
							goodsid = jso.getString(a);
							Intent itmrhd = new Intent();
							itmrhd.setClass(Sp_GoodsInfoActivity.this, JieSuan1Activity.class);
							itmrhd.putExtra("allpri", price.substring(1, price.length()-1));
							itmrhd.putExtra("xzhdgg", goodsid);
							startActivity(itmrhd);
							
						} catch (org.json.JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			break;
		case R.id.rl_chooseleixing:
			XuanZeLeiXing();
			break;
		case R.id.ll_goods_fenxiang:
			// 设置分享内容
						String fxnr = "友盟社会化组件（SDK）让移动应用快速整合社交分享功能，http://www.umeng.com/social";
						String fxtp = "http://www.umeng.com/images/pic/banner_module_social.png";
						FenXiang fx = new FenXiang(getApplicationContext(),Sp_GoodsInfoActivity.this,fxnr,fxtp);
			break;
		default:
			
			break;
		}
	}
	
	public void XuanZeLeiXing(){
		itaddcar1 = new Intent(Sp_GoodsInfoActivity.this,Sp_a2_XingHao.class);
		if(leixing1.length()==1){
			LeiXing1();
		}
		if(leixing1.length()==2){
			LeiXing2();
		}

		itaddcar1.putExtra("chanpinprice",chanpinprice);
		itaddcar1.putExtra("kucun",kucun);
		itaddcar1.putExtra("goodsid", goodsid);
		itaddcar1.putExtra("choosejiage", choosejiage);
		itaddcar1.putExtra("choosexinghao", choosexinghao);
		Sp_GoodsInfoActivity.this.startActivityForResult(itaddcar1,0);
		Sp_GoodsInfoActivity.this.overridePendingTransition(R.anim.activity_open,0); 
	}
	
	public void LeiXing1(){
		itaddcar1.putExtra("tiaomu","1");//arr.getJSONObject(0)
		try {
			itaddcar1.putExtra("fenlei1",leixing1.getJSONObject(0).getString("name").toString());
			
//			JSONArray jsonarray = xuanxiang.getJSONArray("1");
			JSONArray jsonarray = xuanxiang.getJSONArray(leixing1.getJSONObject(0).getString("id").toString());
			List<String> list = new ArrayList<String>();
			for (int i=0; i<jsonarray.length(); i++) {
			    try {
					list.add( jsonarray.getString(i) );
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			String[] stringArray = list.toArray(new String[list.size()]);
			itaddcar1.putExtra("arry1",stringArray);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void LeiXing2(){
		itaddcar1.putExtra("tiaomu","2");
		try {
//			Toast.makeText(Sp_GoodsInfoActivity.this, leixing1.getString("1").toString(), Toast.LENGTH_LONG).show();
			itaddcar1.putExtra("fenlei1",leixing1.getJSONObject(0).getString("name").toString());
			itaddcar1.putExtra("fenlei2",leixing1.getJSONObject(1).getString("name").toString());
			
			JSONArray jsonarray1 = xuanxiang.getJSONArray(leixing1.getJSONObject(0).getString("id").toString());
			
			itaddcar1.putExtra("arry1",jsonarray1.toString());
			

			JSONArray jsonarray2 = xuanxiang.getJSONArray(leixing1.getJSONObject(1).getString("id").toString());
			itaddcar1.putExtra("arry2",jsonarray2.toString());
			 
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	JsonHttpResponseHandler res_goodsinfo = new JsonHttpResponseHandler()
	{
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			// TODO Auto-generated method stub
			super.onSuccess(statusCode, headers, response);
			RequestDailog.closeDialog();
			Tools.Log("res_getAddress="+response);
			String error=null;
			JSONObject datas=null;
			try {
				 datas = response.getJSONObject("datas");
				 error = response.getString("error");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			if (error==null)//成功
			{
				try {
					//每日好店
						JSONObject jsonItem = datas.getJSONObject("goods_info");
//						JSONObject jsonItem = array.getJSONObject(0);
						try {
							jsonitemz = datas.getJSONObject("evaluate");
						} catch (Exception e) {
							pj=1;
						}
						
//							JSONObject jsonitemz = arrayz.getJSONObject(0);
						//商品名称
						tv_sp_spname.setText(jsonItem.getString("goods_name"));
						//销售价 
						xsprice = "￥"+jsonItem.getString("goods_promotion_price");
						tv_xiaoshoujia.setText(xsprice);
						//库存
						kucun = "库存："+jsonItem.getString("goods_storage")+"件";
						//市场价tv_xiaoshoujia
						chanpinprice = "￥"+jsonItem.getString("goods_price");
						tv_shichangjia.setText(chanpinprice);
						//所属二级分类
						tv_sp_ssejfl.setText(jsonItem.getString("gc_name"));
						//已售件数
						tv_yishoujian.setText("已售"+jsonItem.getString("goods_salenum")+"件");
						//浏览量
						tv_liulancount.setText("浏览 "+jsonItem.getString("goods_click"));
						//选择规格类型
//						rl_chooseleixing
						//宝贝评价(1731)
						pingjia = "评价("+ jsonItem.getString("evaluation_count")+")";
						tv_sp_babypingjia.setText("宝贝评价：（"+jsonItem.getString("evaluation_count")+"）");
						//总评分：
						tv_zongpinfen.setText("总评分："+jsonItem.getString("evaluation_good_star"));
						if (pj==1) {
							
						}else {
							//用户头像
					   		ImageLoader.getInstance().displayImage((String)jsonitemz.getString("geval_avatar"), im_userimage);
							//用户名称
							tv_username.setText(jsonitemz.getString("geval_frommembername")); 
							//评星
							xiangqing_rating_bar.setRating(Float.parseFloat(jsonitemz.getString("evaluation_good_star")));
							//商品评价 
							tv_sp_pingjia.setText(jsonitemz.getString("geval_content"));
							//产品参数
							tv_chanpincanshu.setText(jsonitemz.getString("geval_spec"));
						}
						//查看更多评价
						leixing1 = jsonItem.getJSONArray("spec_name");
//						leixing1 = jsonItem.getJSONObject("spec_name");
						xuanxiang = jsonItem.getJSONObject("spec_value");
						choosexinghao = datas.getJSONObject("spec_list").toString();
						choosejiage = datas.getJSONObject("spec_list_goods").toString();
						
//						ll_ckgdpj
						//继续拖动，查看图文详情
//						ll_chankantuwen
				} 
				catch (org.json.JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}/*circle*/
			else//失败 
			{
				Tools.Log("res_Points_error="+error+"");
//				Tools.Notic(B5_MyActivity.this, error+"", null);
			}
			
		}
	};


	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) { // resultCode为回传的标记，我在B中回传的是RESULT_OK
		case RESULT_OK:
			tv_qxzlx1 = data.getStringExtra("tv_qxzlx1");
			tv_qxzlx2 = data.getStringExtra("tv_qxzlx2");
			tv_qxzlx = tv_qxzlx1+tv_qxzlx2;
			tv_xzgglx.setText(tv_qxzlx);
			xzhdgg = data.getStringExtra("xzhdgg");
			tv_qxzlx_yincang = data.getStringExtra("tv_qxzlx_yincang");
			price = data.getStringExtra("price");
			break;
		default:
			break;
		}
	}
	
}