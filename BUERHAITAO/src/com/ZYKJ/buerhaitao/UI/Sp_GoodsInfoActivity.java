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
	String chanpinprice,kucun;
	JSONObject leixing1,xuanxiang;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_shangpinxiangqing);
		initView();
		HttpUtils.getGoodsInfo(res_goodsinfo, "100001");
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
//		key = getSharedPreferenceValue("key");
		tv_shichangjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		setListener(im_sp_back,im_sp_gouwuche,ll_ckgdpj,ll_sp_addincar,rl_chooseleixing,ll_goods_fenxiang);
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
			Intent itaddcar = new Intent(Sp_GoodsInfoActivity.this,Sp_a2_XingHao.class);
			itaddcar.putExtra("chanpinprice",chanpinprice);
			itaddcar.putExtra("kucun",kucun);
			if(leixing1.length()==1){
				itaddcar.putExtra("tiaomu","1");
				try {
					itaddcar.putExtra("fenlei1",leixing1.getString("1"));
					
					JSONArray jsonarray = xuanxiang.getJSONArray("1");
					
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
					itaddcar.putExtra("arry1",stringArray);
					
					
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(leixing1.length()==2){
				itaddcar.putExtra("tiaomu","2");
				try {
//					Toast.makeText(Sp_GoodsInfoActivity.this, leixing1.getString("1").toString(), Toast.LENGTH_LONG).show();
					itaddcar.putExtra("fenlei1",leixing1.getString("1").toString());
					itaddcar.putExtra("fenlei2",leixing1.getString("2").toString());
					
					JSONArray jsonarray1 = xuanxiang.getJSONArray("1");
					
					itaddcar.putExtra("arry1",jsonarray1.toString());
					

					JSONArray jsonarray2 = xuanxiang.getJSONArray("2");
					itaddcar.putExtra("arry2",jsonarray2.toString());
					 
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
						
			Sp_GoodsInfoActivity.this.startActivity(itaddcar);  
            
			Sp_GoodsInfoActivity.this.overridePendingTransition(R.anim.activity_open,0); 
			break;
		case R.id.rl_chooseleixing:
			Intent itaddcar1 = new Intent(Sp_GoodsInfoActivity.this,Sp_a2_XingHao.class);
			itaddcar1.putExtra("chanpinprice",chanpinprice);
			itaddcar1.putExtra("kucun",kucun);
			if(leixing1.length()==1){
				itaddcar1.putExtra("tiaomu","1");
				try {
					itaddcar1.putExtra("fenlei1",leixing1.getString("1"));
					
					JSONArray jsonarray = xuanxiang.getJSONArray("1");
					
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
			if(leixing1.length()==2){
				itaddcar1.putExtra("tiaomu","2");
				try {
//					Toast.makeText(Sp_GoodsInfoActivity.this, leixing1.getString("1").toString(), Toast.LENGTH_LONG).show();
					itaddcar1.putExtra("fenlei1",leixing1.getString("1").toString());
					itaddcar1.putExtra("fenlei2",leixing1.getString("2").toString());
					
					JSONArray jsonarray1 = xuanxiang.getJSONArray("1");
					
					itaddcar1.putExtra("arry1",jsonarray1.toString());
					

					JSONArray jsonarray2 = xuanxiang.getJSONArray("2");
					itaddcar1.putExtra("arry2",jsonarray2.toString());
					 
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
						
			Sp_GoodsInfoActivity.this.startActivity(itaddcar1);  
            
			Sp_GoodsInfoActivity.this.overridePendingTransition(R.anim.activity_open,0); 
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

						JSONObject jsonitemz = datas.getJSONObject("evaluate");
//							JSONObject jsonitemz = arrayz.getJSONObject(0);
						//商品名称
						tv_sp_spname.setText(jsonItem.getString("goods_name"));
						//销售价 
						chanpinprice = "￥"+jsonItem.getString("goods_price");
						tv_xiaoshoujia.setText(chanpinprice);
						//库存
						kucun = "库存："+jsonItem.getString("goods_storage")+"件";
						//市场价
//						tv_shichangjia
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
						//用户头像
				   		ImageLoader.getInstance().displayImage((String)jsonitemz.getString("geval_avatar"), im_userimage);
						//用户名称
						tv_username.setText(jsonitemz.getString("geval_frommembername")); 
						//评星
						xiangqing_rating_bar.setRating(Float.parseFloat(jsonitemz.getString("geval_scores")));
						//商品评价 
						tv_sp_pingjia.setText(jsonitemz.getString("geval_content"));
						//产品参数
						tv_chanpincanshu.setText(jsonitemz.getString("geval_spec"));
						//查看更多评价
						leixing1 = jsonItem.getJSONObject("spec_name");
						xuanxiang = jsonItem.getJSONObject("spec_value");
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

}