package com.eposp.android.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
/**
 *@author : xqf
 *@date   :2017/8/16 下午5:52
 *@desc   :处理bitmap
 *@update :
 */
public class BimpUtil {
//	public static int max = 0;
//
//	/**
//	 * 可以拍照矫正方向
//	 * 
//	 * @param path
//	 * @param picsize
//	 * @return
//	 * @throws IOException
//	 */
//	public static byte[] getPicContent(String path, int picsize) throws IOException {
//		int degree = Bimp.readPictureDegree(path);
//		Bitmap bitmap = decodeFile(path, picsize);
//		bitmap = Bimp.rotaingImageView(degree, bitmap);// 矫正位置
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//		byte[] byteArray = baos.toByteArray();
//		return byteArray;
//	}
//
//
	/**
	 * 获取小图片，减少内存消耗
	 *
	 * @param path
	 *            图片路径
	 * @param picsize
	 *            宽的大小 bitmap大小;审核商户资料一般设置200左右就可以；如果是缩量图只要80就可以
	 * @return
	 */
	public static Bitmap decodeFile(String path, int picsize) {
		if (TextUtils.isEmpty(path)) {

			return null;
		}
		try {
			// decode image size
			File f = new File(path);
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);

			// Find the correct scale value. It should be the power of 2.
			final int REQUIRED_SIZE = picsize;// 如果是 传给服务器的，就大一点
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp > height_tmp) {
					width_tmp = height_tmp;
				}
				if (width_tmp / 2 < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}
			// decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inJustDecodeBounds = false;
			o2.inSampleSize = scale;
			o2.inInputShareable = true;
			o2.inPurgeable = true;
			Bitmap bm = BitmapFactory.decodeStream(new FileInputStream(f),
					null, o2);

			return bm;
		} catch (FileNotFoundException e) {
		}
		return null;
	}

    /**
     * caculate the bitmap sampleSize
     * @return
     */
    public final static int caculateInSampleSize(BitmapFactory.Options options, int rqsW, int rqsH) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (rqsW == 0 || rqsH == 0) return 1;
        if (height > rqsH || width > rqsW) {
            final int heightRatio = Math.round((float) height/ (float) rqsH);
            final int widthRatio = Math.round((float) width / (float) rqsW);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }
     
    /**
     * 压缩指定路径的图片，并得到图片对象
     * @param path bitmap source path
     * @return Bitmap {@link Bitmap}
     */
    public final static Bitmap compressBitmap(String path, int rqsW, int rqsH) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
		options.inPreferredConfig = Bitmap.Config.ARGB_4444;
        options.inSampleSize = caculateInSampleSize(options, rqsW, rqsH);
		//记得设置false，要不然bitmap会为空
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }
     
    /**
     * 基于质量的压缩算法， 此方法未 解决压缩后图像失真问题
     * <br> 可先调用比例压缩适当压缩图片后，再调用此方法可解决上述问题
     * @param bitmap
     * @param maxBytes 压缩后的图像最大大小 单位为byte
     * @return
     */
    public final static Bitmap compressBitmap(Bitmap bitmap, long maxBytes) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(CompressFormat.PNG, 100, baos);
            int options = 90;
            while (baos.toByteArray().length > maxBytes) {
                baos.reset();
                bitmap.compress(CompressFormat.PNG, options, baos);
                options -= 10;
            }
            byte[] bts = baos.toByteArray();
            Bitmap bmp = BitmapFactory.decodeByteArray(bts, 0, bts.length);
            baos.close();
            return bmp;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
