package com.ZYKJ.buerhaitao.utils;

import java.io.File;
import java.io.FileNotFoundException;

import android.content.Context;
import android.util.Log;

import com.ZYKJ.buerhaitao.data.AppValue;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
/**
 * @author 作者 zhang
 * @version 创建时间：2015年1月6日 上午10:33:29 类说明 AsyncHttp 异步联网第三方库
 */
public class HttpUtils {
	public static final String base_url = "http://115.28.21.137/mobile/";
	private static AsyncHttpClient client = new AsyncHttpClient(); // 实例话对象
	static {
		client.setTimeout(5000); // 设置链接超时，如果不设置，默认为15s
		client.setMaxRetriesAndTimeout(3, 5000);
		// client.setEnableRedirects(true);

	}

	public static void initClient(Context c) {
		PersistentCookieStore myCookieStore = new PersistentCookieStore(c);
		client.setCookieStore(myCookieStore);
	}

	public static AsyncHttpClient getClient() {

		return client;
	}

	/**
	 * 1登录
	 * 
	 * @param res
	 * @param loginname
	 * @param pwd
	 */

	public static void login(AsyncHttpResponseHandler res, String loginname,
			String pwd) {
		String url = null;
		url = base_url + "index.php?act=login";
		RequestParams requestParams  = new RequestParams();
		requestParams.put("mobile", loginname);
		requestParams.put("password", pwd);
		requestParams.put("client", AppValue.CLIENT);
		client.post(url,requestParams,res);

	}

	/**
	 * 2注册
	 * 
	 * @param res
	 * @param mobile
	 *            必选 用户名
	 * @param password
	 *            必选 密码
	 * @param verify_code
	 *            必选 验证码
	 * @param client
	 *            必选 客户端类型：android
	 */
	public static void regist(AsyncHttpResponseHandler res, String mobile ,
			String password , String verify_code) {
		String url = null;
		url = base_url + "index.php?act=login&op=register";
		RequestParams requestParams  = new RequestParams();
		requestParams.put("mobile", mobile);
		requestParams.put("password", password);
		requestParams.put("verify_code", verify_code);
		requestParams.put("client", AppValue.CLIENT);
		client.post(url,requestParams,res);
	}
	/**
	 * 注销
	 * @param res
	 * @param mobile
	 *            必选 手机号
	 * @param key 
	 *            必选 当前登录令牌
	 * @param client
	 *            必选 客户端类型：android
	 */
	public static void logout(AsyncHttpResponseHandler res, String mobile ,
			String key) {
		String url = null;
		url = base_url + "index.php?act=logout";
		RequestParams requestParams  = new RequestParams();
		requestParams.put("mobile", mobile);
		requestParams.put("key", key);
		requestParams.put("client", AppValue.CLIENT);
		client.post(url,requestParams,res);
	}

	/**
	 * 3获取首页信息
	 * 
	 * @param res
	 */
	public static void getFirstList(AsyncHttpResponseHandler res,String city_id ,String lng ,String lat) {
//		String url = base_url + "getSpecialList";
//		client.get(url, res);
		String url = null;
		url = base_url + "index.php?act=index"+"&city_id="+city_id+"&lng="+lng+"&lat="+lat;
		RequestParams requestParams  = new RequestParams();
		requestParams.put("city_id", city_id);
		requestParams.put("lng", lng);
		requestParams.put("lat", lat);
		client.post(url,requestParams,res);
	}

	/**
	 * 4首页商品列表
	 * 
	 * @param res
	 */
	public static void getHomeGoods(AsyncHttpResponseHandler res) // 用一个完整url获取一个string对象
	{

		String url = base_url + "getHomeGoods";
		client.get(url, res);
		Log.i("landousurl", url);
	}

	/**
	 * 5获取商品分类 gc_id默认为0
	 * 
	 * @param res
	 * @param gc_id
	 */
	public static void getGoodsClass(AsyncHttpResponseHandler res,String gc_id) {
		String url = base_url + "index.php?act=goods_class"+(gc_id != null?"&gc_id="+gc_id:"");
		client.get(url, res);
	}

	/**
	 * 6获取商品列表
	 * 
	 * @param res_getGoodsList
	 * 
	 * @param gc_id
	 *            类别排序
	 * @param store_id
	 *            店铺
	 * @param search_text
	 *            搜索关键字
	 */
	public static void getGoodsList(AsyncHttpResponseHandler res_getGoodsList,
			String g_type) {
		String url = base_url + "getGoodsList" + g_type;
		Log.i("landousurl", url);
		client.get(url, res_getGoodsList);
	}
	/**
	 * 修改昵称
	 * @param res_getGoodsList
	 * @param user_name
	 * @param key
	 */
	public static void editname(AsyncHttpResponseHandler res,String user_name,String key) {
		String url = base_url + "index.php?act=member_index&op=editname";
		RequestParams params = new RequestParams();
		params.put("user_name", user_name);
		params.put("key", key);
		client.post(url, params, res);
	}
	

	/**
	 * 7获取商品详情
	 * 
	 * @param goods_id
	 * @param getGoodsDetail
	 */
	public static void getGoodsDetail(String goods_id,
			AsyncHttpResponseHandler getGoodsDetail) {
		String url = base_url + "getGoodsDetail&goods_id=" + goods_id;
		client.get(url, getGoodsDetail);
	}

	/**
	 * 8获取商品评价
	 * 
	 * @param res
	 * @param goods_id
	 */
	public static void getGoodsComments(AsyncHttpResponseHandler res,
			String goods_id) {
		String url = base_url + "getGoodsComments&goods_id=" + goods_id;
		client.get(url, res);
	}

	/**
	 * 9获取商店列表
	 * 
	 * @param res
	 */
	public static void getStoreList(AsyncHttpResponseHandler res,
			String search_text) {
		String url = null;
		if (search_text == null) {
			url = base_url + "getStoreList";
		} else {
			url = base_url + "getStoreList&search_text=" + search_text;
		}
		client.get(url, res);
	}

	/**
	 * 10获取店铺分类商品列表。。
	 * 
	 * @param res
	 * @param store_id
	 */
	public static void getStoreGoodsClass(AsyncHttpResponseHandler res,
			String store_id) {
		String url = base_url + "getStoreGoodsClass&store_id=" + store_id;
		client.get(url, res);

	}

	/**
	 * 11添加商品收藏
	 * 
	 * @param res
	 * @param goods_id
	 */
	public static void addFavoriteGoods(AsyncHttpResponseHandler res,
			String goods_id) {
		String url = base_url + "addFavoriteGoods&goods_id=" + goods_id;
		client.get(url, res);
	}

	/**
	 * 12取消商品收藏
	 * 
	 * @param res
	 * @param goods_id
	 */
	public static void delFavoriteGoods(AsyncHttpResponseHandler res,
			String goods_id) {
		String url = base_url + "delFavoriteGoods&goods_id=" + goods_id;
		client.get(url, res);
	}

	/**
	 * 13查询商品收藏
	 * 
	 * @param res
	 * @param page
	 * @param per_page
	 */
	public static void getFavoriteGoods(AsyncHttpResponseHandler res,
			String page, String per_page) {
		String url = base_url + "getFavoriteGoods&page=" + page + "&per_page="
				+ per_page;
		client.get(url, res);
	}

	/**
	 * 14添加商店收藏
	 * 
	 * @param res
	 * @param store_id
	 */
	public static void addFavoriteStore(AsyncHttpResponseHandler res,
			String store_id) {
		String url = base_url + "addFavoriteStore&store_id=" + store_id;
		client.get(url, res);
	}

	/**
	 * 15取消商店收藏
	 * 
	 * @param res
	 * @param store_id
	 */
	public static void delFavoriteStore(AsyncHttpResponseHandler res,
			String store_id) {
		String url = base_url + "delFavoriteStore&store_id=" + store_id;
		client.get(url, res);
	}

	/**
	 * 16查询商店收藏
	 * 
	 * @param res
	 * @param page
	 * @param per_page
	 */
	public static void getFavoriteStore(AsyncHttpResponseHandler res,
			String page, String per_page) {
		String url = base_url + "getFavoriteStore&page=" + page + "&per_page="
				+ per_page;
		client.get(url, res);
	}

	/**
	 * 获取地区列表
	 * 
	 * @param res
	 * @param parent_id
	 */
	public static void getArea(AsyncHttpResponseHandler res, String key,String area_id) {
		String url = base_url + "index.php?act=member_address&op=area_list"; 
		RequestParams params  = new RequestParams();
		params.put("key",key);
		params.put("area_id",area_id);
		client.post(url, params, res);
	}

	/**
	 * 地址添加
	 * 
	    key 当前登录令牌
	    true_name 姓名
	    city_id 城市编号(地址联动的第二级)
	    area_id 地区编号(地址联动的第三级)
	    area_info 地区信息，例：天津 天津市 红桥区
	    street 街道 例如:南大街
	    address 地址信息，例：水游城8层
	    zip 邮编
	    mob_phone 手机
	 */
	public static void addAddress(AsyncHttpResponseHandler res,
			String key, String true_name, String city_id, String area_id,String area_info,String street,String address,String zip,String mob_phone) {
		String url = base_url + "index.php?act=member_address&op=address_add";
		RequestParams params =new RequestParams();
		params.put("key", key);
		params.put("true_name", true_name);
		params.put("city_id", city_id);
		params.put("area_id", area_id);
		params.put("area_info", area_info);
		params.put("street", street);
		params.put("address", address);
		params.put("zip", zip);
		params.put("mob_phone", mob_phone);
		client.post(url, params, res);
	}

	/**
	 * 19修改地址
	 * 


    key 当前登录令牌
    address_id 地址编号
    true_name 姓名
    area_id 地区编号
    city_id 城市编号
    area_info 地区信息，例：天津 天津市 红桥区
    street 街道 例:南大街
    address 地址信息，例：水游城8层
    zip 邮编
    mob_phone 手机


	 */
	public static void changeAddress(AsyncHttpResponseHandler res,String key,String address_id,
			String true_name, String area_id,String city_id, String address, String area_info,
			String street,String zip,String mob_phone) {
		String url = base_url + "index.php?act=member_address&op=address_edit";
		RequestParams params =new RequestParams();
		params.put("key", key);
		params.put("address_id", address_id);
		params.put("true_name", true_name);
		params.put("area_id", area_id);
		params.put("city_id", city_id);
		params.put("area_info", area_info);
		params.put("street", street);
		params.put("address", address);
		params.put("zip", zip);
		params.put("mob_phone", mob_phone);
		client.post(url, params, res);
	}

	/**
	 * 删除地址
	 * 
	 * @param res
	 * @param address_id
	 *            地址id
	 */
	public static void delAddress(AsyncHttpResponseHandler res,String key,String address_id) {
		String url = base_url + "index.php?act=member_address&op=address_del";
		RequestParams pareParams =new RequestParams();
		pareParams.put("key", key);
		pareParams.put("address_id", address_id);
		client.post(url, pareParams, res);
	}

	/**
	 * 21收货地址
	 * 
	 * @param res
	 */
	public static void getAddress(AsyncHttpResponseHandler res,String key) {
		String url = base_url + "index.php?act=member_address&op=address_list"+"&key="+key;
		client.get(url, res);
	}

	/**
	 * 设置默认地址
	 * 
	 * @param res
	 * @param key
	 * @param address_id
	 */
	public static void setDefaultAddress(AsyncHttpResponseHandler res,String key,String address_id) {
		String url = base_url + "index.php?act=member_address&op=address_default";
		RequestParams params = new RequestParams();
		params.put("key", key);
		params.put("address_id", address_id);
		client.post(url, params, res);
	}

	/**
	 * 23添加购物车
	 * 
	 * @param res
	 * @param goods_id
	 *            商品id，必选
	 * @param count
	 *            商品数量，默认为1
	 */
	public static void addCart(AsyncHttpResponseHandler res, String goods_id,
			String count) {
		count = count == null ? "1" : count;
		String url = base_url + "addCart&goods_id=" + goods_id + "&count="
				+ count;
		client.get(url, res);

	}

	/**
	 * 24修改购物车
	 * 
	 * @param res
	 * @param cart_id
	 *            购物车id，必选
	 * @param count
	 *            商品数量，必选
	 */
	public static void updateCart(AsyncHttpResponseHandler res, String cart_id,
			int count) {
		String url = base_url + "updateCart&cart_id=" + cart_id + "&count="
				+ count;
		client.get(url, res);

	}

	/**
	 * 25删除购物车
	 * 
	 * @param res
	 * @param cart_id
	 *            购物车id，必选，可以多个，例如：1,2,3
	 */
	public static void delCart(AsyncHttpResponseHandler res, String cart_id) {
		String url = base_url + "delCart&cart_id=" + cart_id;
		client.get(url, res);
	}

	/**
	 * 26查询购物车
	 * 
	 * @param res
	 */
	public static void getCartList(AsyncHttpResponseHandler res, String cart_id) {
		String url = base_url + "getCartList&cart_id=" + cart_id;
		client.get(url, res);
		Log.i("landouurl", url);
	}

	/**
	 * 27订单确认
	 * 
	 * @param res
	 * @param cart_id
	 */
	public static void getOrderConfirm(AsyncHttpResponseHandler res,
			String cart_id) {
		String url = base_url + "getOrderConfirm&cart_id=" + cart_id;
		client.get(url, res);
		Log.i("landousjsonurl", url);
	}

	/**
	 * 27立即购买
	 * 
	 * @param res
	 * @param goods_id
	 */
	public static void BuyNow(AsyncHttpResponseHandler res, String goods_id,
			int count) {
		String url = base_url + "getOrderConfirm&goods_id=" + goods_id
				+ "&count=" + count;
		client.get(url, res);
		Log.i("landousjsonurl", url);
	}

	/**
	 * 28 提交订单 所有都是必选
	 * 
	 * @param res
	 * @param ship_method
	 * @param pay_method
	 * @param address_id
	 * @param cart_id
	 */
	public static void addOrder(AsyncHttpResponseHandler res,
			String ship_method, String pay_method, String address_id,
			String cart_id) {
		String url = base_url + "addOrder&ship_method=" + ship_method
				+ "&pay_method=" + pay_method + "&address_id=" + address_id
				+ "&cart_id=" + cart_id;
		client.get(url, res);
		Log.i("landousjsonurl", url);
	}

	/**
	 * 28 立即付款提交订单
	 * 
	 * @param res
	 * @param ship_method
	 * @param pay_method
	 * @param address_id
	 * @param goods_id
	 */
	public static void addOrderNow(AsyncHttpResponseHandler res,
			String ship_method, String pay_method, String address_id,
			String goods_id, String count) {
		String url = base_url + "addOrder&ship_method=" + ship_method
				+ "&pay_method=" + pay_method + "&address_id=" + address_id
				+ "&goods_id=" + goods_id + "&count=" + count;
		client.get(url, res);
	}

	/**
	 * 29查询订单列表
	 * 
	 * @param res
	 * @param order_state
	 *            订单状态：0(已取消)10(默认):未付款;20:已付款;30:已发货;40:已收货;
	 */
	public static void getOrderList(AsyncHttpResponseHandler res,
			String order_state) {
		String url = base_url + "getOrderList&order_state=" + order_state;
		client.get(url, res);

	}

	/**
	 * 30 更新移动支付减免
	 * 
	 * @param res
	 * @param pay_sn
	 * @param pay_method
	 * @param discount
	 */
	public static void updateMobileDiscount(AsyncHttpResponseHandler res,
			String pay_sn, String pay_method, String discount) {
		String url = base_url + "updateMobileDiscount&pay_sn=" + pay_sn
				+ "&pay_method=" + pay_method + "&discount=" + discount;
		client.get(url, res);
		Log.i("landousjsonurl", url);
	}

	/**
	 * 31取消订单
	 * 
	 * @param res
	 * @param order_id
	 * @param extend_msg
	 */
	public static void cancelOrder(AsyncHttpResponseHandler res,
			String order_id, String extend_msg) {
		String url = base_url + "cancelOrder&order_id=" + order_id
				+ "&extend_msg=" + extend_msg;
		client.get(url, res);
		Log.i("landousurl", url);
	}

	/**
	 * 32确认收货
	 * 
	 * @param res
	 * @param order_id
	 */
	public static void receiveGoods(AsyncHttpResponseHandler res,
			String order_id) {
		String url = base_url + "receiveGoods&order_id=" + order_id;
		client.get(url, res);
	}

	/**
	 * 33 查看物流
	 * 
	 * @param res
	 * @param order_id
	 */
	public static void getExpress(AsyncHttpResponseHandler res, String order_id) {
		String url = base_url + "getExpress&order_id=" + order_id;
		client.get(url, res);
	}

	/**
	 * 34 退款
	 * 
	 * @param res
	 * @param order_id
	 * @param rec_id
	 * @param refund_type
	 * @param refund_amount
	 * @param goods_num
	 * @param extend_msg
	 */
	public static void refund(AsyncHttpResponseHandler res, String order_id,
			String rec_id, String refund_type, String refund_amount,
			String goods_num, String extend_msg) {
		String url = base_url + "refund&order_id=" + order_id + "&rec_id="
				+ rec_id + "&refund_type=" + refund_type + "&refund_amount="
				+ refund_amount + "&goods_num=" + goods_num + "&extend_msg="
				+ extend_msg;
		client.get(url, res);
		Log.i("landousurl", url);
	}

	/**
	 * 35 订单评价
	 * 
	 * @param res
	 * @param anony
	 */
	public static void orderEvaluation(AsyncHttpResponseHandler res,
			String order_id, String anony, String other) {
		String url = base_url + "orderEvaluation&anony=" + anony + "&order_id="
				+ order_id + other;
		client.get(url, res);
		Log.i("landousurl", url);
	}

	/**
	 * 36 获取专题
	 * 
	 * @param res
	 * @param special_id
	 */
	public static void getSpecial(AsyncHttpResponseHandler res,
			String special_id) {
		String url = base_url + "getSpecial&special_id=" + special_id;
		Log.i("get-special_url", url);
		client.get(url, res);
	}

	/**
	 * 积分商城
	 * 
	 * @param res
	 * @param key
	 * 
	 */
	public static void pointsMall(AsyncHttpResponseHandler res, String key) {
		String url = base_url + "index.php?act=member_points&op=goods_list"+"&key="+key;
		client.get(url, res);
	}

	/**
	 * 积分兑换
	 * 
	 * @param res
	 * @param key           登录令牌
	 * @param pgoods_id     商品id
	 * @param address_id    收货地址id
	 * @param pcart_message 留言
	 * 
	 */
	public static void addPointsOrder(AsyncHttpResponseHandler res,
			String key, String pgoods_id, String pcart_message,
			String address_id) {

		String url = null;
		url = base_url + "index.php?act=member_points&op=exchange";
		RequestParams requestParams  = new RequestParams();
		requestParams.put("key", key);
		requestParams.put("pgoods_id", pgoods_id);
		requestParams.put("pcart_message", pcart_message);
		requestParams.put("address_id", address_id);
		client.post(url,requestParams,res);
	}
	/**
	 * 积分兑换记录
	 * @param res
	 * @param key
	 */
	public static void exchangerecord(AsyncHttpResponseHandler res,String key) {
		
		String url = null;
		url = base_url + "index.php?act=member_points&op=record"+"&key="+key;
		client.get(url, res);
	}

	/**
	 * 39积分订单付款完成
	 * 
	 * @param res
	 * @param point_orderid
	 */
	public static void payPointsOrder(AsyncHttpResponseHandler res,
			String point_orderid) {
		String url = base_url + "payPointsOrder&point_orderid=" + point_orderid;
		client.get(url, res);

	}

	/**
	 * 40 查询积分订单列表
	 * 
	 * @param res
	 * @param page
	 * @param per_page
	 */
	public static void getPointsOrder(AsyncHttpResponseHandler res,
			String page, String per_page) {
		String url = base_url + "getPointsOrder&page=" + page + "&per_page="
				+ per_page;
		client.get(url, res);
	}

	/**
	 * 41 查询积分变更列表
	 * @param res
	 * @param page
	 * @param per_page
	 */
	public static void getPointsLog(AsyncHttpResponseHandler res,String key) {
		String url = base_url + "index.php?act=member_points&op=points_log"+"&key="+key;
		client.get(url, res);
//		String url = base_url + "index.php?act=member_points&op=points_log";
//		RequestParams requestParams =new RequestParams();
//		requestParams.put("key", key);
//		client.post(url, requestParams, res);
		
	}

	/**
	 * 上传头像保存
	 * 
	 * @param res
	 * @param avatar
	 * @param key
	 */
	public static void saveAvatar(AsyncHttpResponseHandler res,String avatar,String key ) {
		String url = base_url + "index.php?act=member_index&op=avatar_save";
		RequestParams params =new RequestParams();
		params.put("avatar", avatar);
		params.put("key", key);
		client.post(url, params, res);
	}

	/**
	 * 43 分享获得积分
	 * 
	 * @param res
	 */
	public static void addSharePoints(AsyncHttpResponseHandler res) {
		String url = base_url + "addSharePoints";
		client.get(url, res);
	}

	/**
	 * 44 签到
	 * 
	 * @param res
	 */
	public static void chackIn(AsyncHttpResponseHandler res,String key) {
		String url = base_url + "index.php?act=member_index&op=signin";
		RequestParams requestParams = new RequestParams();
		requestParams.put("key", key);
		client.post(url, requestParams, res);
	}
	/**
	 * 减钻石
	 * 
	 * @param res
	 */
	public static void subtractCheckPoints(AsyncHttpResponseHandler res, double goods_price) {
		String url = base_url + "subtractCheckPoints&goods_price="+goods_price;
		client.get(url, res);
	}
	/**
	 * 充值接口
	 * 
	 * @param res
	 */
	public static void recharge(AsyncHttpResponseHandler res, String key, String pdr_amount,String channel) {
		String url = base_url + "index.php?act=member_predeposit&op=recharge_add";
		RequestParams requestParams = new RequestParams();
		requestParams.put("key", key);
		requestParams.put("pdr_amount", pdr_amount);
		requestParams.put("channel ", channel );
		client.post(url, res);
	}
	/**
	 * 45 积分订单确认收货
	 * 
	 * @param res
	 * @param point_orderid
	 */
	public static void receivePointsOrder(AsyncHttpResponseHandler res,
			String point_orderid) {
		String url = base_url + "receivePointsOrder&point_orderid="+ point_orderid;
		client.get(url, res);
	}
	/**
	 *  获取支付宝秘钥
	 * 
	 * @param res
	 */
	public static void getAlipay(AsyncHttpResponseHandler res) {
		String url = base_url + "getAlipay";
		client.get(url, res);

	}

	/**
	 * 47.获取版本
	 * 
	 * @param res
	 */
	public static void getAppVersion(AsyncHttpResponseHandler res) {
		String url = base_url + "getAppVersion&platform=" + "android";
		client.get(url, res);
	}

	/**
	 * 48 忘记密码
	 * 
	 * @param res
	 * @param mobile 手机号
	 * @param verify_code 验证码
	 * @param client 客户端类型(android ios )
	 * 
	 */
	public static void resetPassword(AsyncHttpResponseHandler res,String mobile, String password,String verify_code) {
		String url = base_url + "index.php?act=login&op=restpasswd";
		RequestParams requestParams  = new RequestParams();
		requestParams.put("mobile", mobile);
		requestParams.put("password", password);
		requestParams.put("verify_code", verify_code);
		requestParams.put("client", AppValue.CLIENT);
		client.post(url, requestParams, res);
	}

	/**
	 * 49. 获取订单详情
	 * 
	 * @param res
	 * @param order_id
	 */
	public static void getOrderDetail(AsyncHttpResponseHandler res,
			String order_id) {
		String url = base_url + "getOrderDetail&order_id=" + order_id;
		client.get(url, res);

	}

	/**
	 * 50 获取手机减免额度
	 * 
	 * @param res
	 */
	public static void getDiscountSetting(AsyncHttpResponseHandler res) {
		String url = base_url + "getDiscountSetting";
		client.get(url, res);
	}

	/**
	 * 51.取消积分订单
	 * 
	 * @param res
	 * @param point_orderid
	 */
	public static void cancelPointsOrder(AsyncHttpResponseHandler res,
			String point_orderid) {
		String url = base_url + "cancelPointsOrder&point_orderid="
				+ point_orderid;
		client.get(url, res);
	}

	/**
	 * 52 订单完成支付
	 * 
	 * @param res
	 * @param pay_sn
	 */
	public static void payOrder(AsyncHttpResponseHandler res, String pay_sn) {
		String url = base_url + "payOrder&pay_sn=" + pay_sn;
		client.get(url, res);
	}
	/**
	 * 上传头像
	 * @param res
	 * @param key
	 * @param name
	 */

	public static void update(AsyncHttpResponseHandler res, String key,String name,File file ) {
		RequestParams params = new RequestParams();
		try {
			params.put("key", key);
			params.put("name", name);
			params.put("avatar",file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Upload a File
		String url = base_url + "index.php?act=member_index&op=avatar_upload";
//		String url = base_url + "index.php?act=member_circle&op=image_upload";
		client.post(url, params, res);
	}
	
	/**
	 * 54 猜你喜欢
	 * @param lng 经度
	 * @param lat 纬度
	 * @param city_id 城市id
	 * @param curpage 当前页码
	 */
	public static void getCaiNiLike(AsyncHttpResponseHandler res,String lng,String lat,String city_id,String curpage) {
		String url = base_url + "index.php?act=goods&op=goods_like_list"+"&lng="+lng+"&lat="+lat+"&city_id="+city_id+"&curpage="+curpage;
		client.get(url, res);
//		String url = base_url + "index.php?act=member_points&op=points_log";
//		RequestParams requestParams =new RequestParams();
//		requestParams.put("key", key);
//		client.post(url, requestParams, res);
		
	}
	/**
	 * 55 每日好店
	 * @param page 每页数量
	 * @param curpage 当前页码
	 * @param city_id 城市id
	 * @param lng 经度
	 * @param lat 纬度
	 */
	public static void getGoodStore(AsyncHttpResponseHandler res,String page,String curpage,String city_id,String lng,String lat) {
		String url = base_url + "index.php?act=store&op=good_store"+"&page="+page+"&curpage="+curpage+"&city_id="+city_id+"&lng="+lng+"&lat="+lat;
		client.get(url, res);
		
	}
	
	/**
	 * 上传晒单圈图片
	 * @param res
	 * @param key
	 * @param name
	 */
	
	public static void uploadshaidanquan(AsyncHttpResponseHandler res, String key,String name,File file ) {
		RequestParams params = new RequestParams();
		try {
			params.put("key", key);
			params.put("name", name);
			params.put("avatar",file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Upload a File
		String url = base_url + "index.php?act=member_circle&op=image_upload";
//		String url = base_url + "index.php?act=member_index&op=avatar_upload";
		client.post(url, params, res);
	}
/**
 * 晒单圈-发布
 * @param res
 * @param key
 * @param description
 * @param image
 */
	
	public static void shaidanquanfabu(AsyncHttpResponseHandler res, String key,String description,String image) {
		RequestParams params = new RequestParams();
		params.put("key", key);
		params.put("description", description);
		params.put("image",image);
		String url = base_url + "index.php?act=member_circle&op=publish";
		client.post(url, params, res);
	}
	/**
	 * 晒单圈-我发布的
	 * @param res
	 * @param key
	 */
	public static void shaidanquan_mypublish(AsyncHttpResponseHandler res, String key) {
		String url = base_url + "index.php?act=member_circle&op=my_publish"+"&key="+key;
		client.get(url, res);
	}
	/**
	 * 上传身份证
	 * @param res
	 * @param key
	 * @param name
	 */
	public static void uploadIDcard(AsyncHttpResponseHandler res, String key,String name,File file ) {
		RequestParams params = new RequestParams();
		try {
			params.put("key", key);
			params.put("name", name);
			params.put("avatar",file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Upload a File
		String url = base_url + "index.php?act=member_index&op=idcard_upload";
		client.post(url, params, res);
	}
	/**
	 * 买家实名认证
	 * @param res
	 * @param key
	 * @param truename
	 * @param birthday
	 * @param address
	 * @param idcard
	 */
	public static void certificate(AsyncHttpResponseHandler res, String key,String truename ,String birthday,String address,String idcard) {
		String url = base_url + "index.php?act=member_index&op=identification";
		RequestParams params = new RequestParams();
		params.put("key",key);
		params.put("truename",truename);
		params.put("birthday",birthday);
		params.put("address",address);
		params.put("idcard",idcard);
		client.post(url, params,res);
	}
	
}
