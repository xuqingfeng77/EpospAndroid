package com.eposp.android.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


/**
 * ImageLoader 工具类
 *
 * 使用方式： ImageLoaderUtils.loadImage(url, imageView,
 *							R.mipmap.ic_launcher, R.mipmap.ic_launcher));
 */
public class ImageLoaderUtils {
	
	private static ImageLoader imageLoader = null;

	/**
	 * 初始化ImageLoader图片加载器
	 * @param context 上下文
	 * @return
	 */
	public static ImageLoader initImageLoader(Context context) {

		if(imageLoader == null){
			//初始化ImageLoader和参数
			imageLoader = ImageLoader.getInstance();
			ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(context);
			imageLoader.init(configuration);

		}
		return imageLoader;
	}

	/**
	 * 加载图片
	 * @param url
	 * @param imageView
	 * @param loadResId 正在加载显示的图片
	 * @param failResId 加载失败显示的图片
	 */
	public static void loadBitmap(String url, ImageView imageView, int loadResId, int failResId){
		imageLoader.displayImage(url, imageView, initDisplayOptions(loadResId, failResId));
	}

	public static void loadBitmap(String url, ImageView imageView){
		imageLoader.displayImage(url, imageView, initDisplayOptions(0, 0));
	}

	public static DisplayImageOptions initDisplayOptions(int loadResId, int failResId) {
		DisplayImageOptions options;
		DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder()
				.showImageOnLoading(loadResId) //正在加载显示的图片
				.showImageOnFail(failResId) //加载失败显示的图片
				.cacheInMemory(true) //开启内存缓存
				.cacheOnDisk(true)   //开启磁盘缓存
						// 实际加载的时候，先去内存中找，找不到再去磁盘中找，最后再去网上下载
				.bitmapConfig(Bitmap.Config.ARGB_8888);
		options = builder.build();
		return options;
	}
	

}
