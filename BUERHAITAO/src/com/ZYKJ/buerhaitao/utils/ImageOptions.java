package com.ZYKJ.buerhaitao.utils;

import com.ZYKJ.buerhaitao.UI.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/** 异步加载图片属性的设置 */
public class ImageOptions {

	public static DisplayImageOptions getLogoOptions(boolean round) {
		DisplayImageOptions.Builder m_options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.logo_default)
				.showImageForEmptyUri(R.drawable.logo_default)
				.showImageOnFail(R.drawable.logo_default).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true);
		if (round) {// 如果是圆角
			m_options.displayer(new RoundedBitmapDisplayer(5));
		}
		return m_options.build();
	}

	/**
	 * 
	 * @param round
	 *            true是圆角
	 * @return
	 */
	public static DisplayImageOptions getGoodsOptions(boolean round) {
		DisplayImageOptions.Builder m_options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.pic_default)
				.showImageForEmptyUri(R.drawable.pic_default)
				.showImageOnFail(R.drawable.pic_default).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true);
		if (round) {// 如果是圆角
			m_options.displayer(new RoundedBitmapDisplayer(5));
		}
		return m_options.build();
	}
}
