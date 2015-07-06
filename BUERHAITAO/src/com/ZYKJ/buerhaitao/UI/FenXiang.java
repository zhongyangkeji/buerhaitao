package com.ZYKJ.buerhaitao.UI;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import android.app.Activity;
import android.content.Context;

public class FenXiang extends Activity{

	// 首先在您的Activity中添加如下成员变量
	final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
	public FenXiang(Context co,Activity ac,String fxnr,String fxtp){

		mController.setShareContent(fxnr);mController.getConfig().removePlatform(SHARE_MEDIA.RENREN, SHARE_MEDIA.DOUBAN,SHARE_MEDIA.TENCENT);
		// 设置分享图片, 参数2为图片的url地址
		mController.setShareMedia(new UMImage(co,fxtp));
		String appID = "wxb54187fbe1e447bd";
		String appSecret = "b0a3885263e3842f24a64e09717f2597";
		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(co,appID,appSecret);
		wxHandler.addToSocialSDK();
		// 添加微信朋友圈
		UMWXHandler wxCircleHandler = new UMWXHandler(co,appID,appSecret);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();
		
		//参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(ac, "1104753536",
		                "gfNQrmLumGooHdCf");
		qqSsoHandler.addToSocialSDK(); 
		//参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(ac, "1104753536",
		                "gfNQrmLumGooHdCf");
		qZoneSsoHandler.addToSocialSDK();
		//设置新浪SSO handler
		mController.getConfig().setSsoHandler(new SinaSsoHandler());
		
		mController.openShare(ac, false);
	}
}
