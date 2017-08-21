package com.eposp.android.util;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

/**
 * @author xuqingfeng77
 * @date 2013-11-14
 * @function 与金钱相关的控制类
 */
public class MoneyTools {
	/**
	 * 金额转为double
	 * 
	 * @param money
	 * @return
	 */
	public static double string2Double(String money) {
		if (isAmount(money)) {
			return Double.parseDouble(money);
		}
		return 0.00;
	}

	/**
	 * 计算交易笔数
	 * 
	 * @param num
	 * @return
	 */
	public static int string2Int(String num) {
		if (isAmount(num)) {
			return Integer.parseInt(num);
		}
		return 0;
	}

	/**
	 * 
	 * 功能：判断金额
	 * 
	 * @param amount
	 * @return
	 */
	public static boolean isAmount(String amount) {
		boolean isMoney = false;
		String checkExpressions = "^([1-9]\\d*|[0])\\.\\d{1,2}$|^[1-9]\\d*$|^0$";
		if (amount == null) {
			isMoney = false;
		} else {
			isMoney = Pattern.matches(checkExpressions, amount);
		}
		return isMoney;
	}

	/** 金额为分的格式 */
	public static final String CURRENCY_FEN_REGEX = "\\-?[0-9]+";

	/**
	 * 将分为单位的转换为元并返回金额格式的字符串 （除100）
	 * 
	 * @param amount
	 * @return
	 * @throws Exception
	 */
	public static String changeF2Y(Long amount) throws Exception {
		if (!amount.toString().matches(CURRENCY_FEN_REGEX)) {
			throw new Exception("金额格式有误");
		}

		int flag = 0;
		String amString = amount.toString();
		if (amString.charAt(0) == '-') {
			flag = 1;
			amString = amString.substring(1);
		}
		StringBuffer result = new StringBuffer();
		if (amString.length() == 1) {
			result.append("0.0").append(amString);
		} else if (amString.length() == 2) {
			result.append("0.").append(amString);
		} else {
			String intString = amString.substring(0, amString.length() - 2);
			for (int i = 1; i <= intString.length(); i++) {
				if ((i - 1) % 3 == 0 && i != 1) {
					result.append(",");
				}
				result.append(intString.substring(intString.length() - i, intString.length() - i + 1));
			}
			result.reverse().append(".").append(amString.substring(amString.length() - 2));
		}
		if (flag == 1) {
			return "-" + result.toString();
		} else {
			return result.toString();
		}
	}

	/**
	 * 将分为单位的转换为元 （除100） 传过来的string 如果含有11000.0 ，点零后面的回被切割掉。格式111,111,22.92 ，含逗号
	 * 
	 * @param amount
	 * @return
	 * @throws Exception
	 */
	public static String changeF2Y(String amount) {

		try {
			if (TextUtils.isEmpty(amount)) {
				return "0.00";
			}
			// 有时分为单位double去比较大小之后会有.0之类的
			if (amount.contains(".")) {
				int index = amount.lastIndexOf(".");
				amount = amount.substring(0, index);
			}
			if (!amount.matches(CURRENCY_FEN_REGEX)) {
				return "0.00";
			}
		} catch (Exception e) {
			new Exception("金额格式有误");
		}
		String temp = BigDecimal.valueOf(Long.valueOf(amount)).divide(new BigDecimal(100)).toString();
		Double dou = Double.parseDouble(temp);
		DecimalFormat bf = new DecimalFormat("###,##0.00");

		return bf.format(dou);
	}

	/**
	 * 将分为单位的转换为元 （除100） 传过来的string 如果含有11000.0，点零后面的回被切割掉。 格式1111112292转为
	 * 11111122.92,不含逗号
	 * 
	 * @param amount
	 * @return
	 * @throws Exception
	 */
	public static String changeF2YTwo(String amount) {

		try {
			if (TextUtils.isEmpty(amount)) {
				return "0.00";
			}
			// 有时分为单位double去比较大小之后会有.0之类的
			if (amount.contains(".")) {
				int index = amount.lastIndexOf(".");
				amount = amount.substring(0, index);
			}
			if (!amount.matches(CURRENCY_FEN_REGEX)) {
				return "0.00";
			}
		} catch (Exception e) {
			new Exception("金额格式有误");
		}
		String temp = BigDecimal.valueOf(Long.valueOf(amount)).divide(new BigDecimal(100)).toString();
		Double dou = Double.parseDouble(temp);
		DecimalFormat bf = new DecimalFormat("#####0.00");

		return bf.format(dou);
	}

	/**
	 * 将元为单位的转换为分 （乘100）
	 * 
	 * @param amount
	 * @return
	 */
	public static String changeY2F(Long amount) {
		return BigDecimal.valueOf(amount).multiply(new BigDecimal(100)).toString();
	}

	/**
	 * 将元为单位的转换为分 替换小数点，支持以逗号区分的金额
	 * 
	 * @param amount
	 * @return
	 */
	public static String changeY2F(String amount) {
		String currency = amount.replaceAll("\\$|\\￥|\\,", ""); // 处理包含, ￥
																// 或者$的金额
		int index = currency.indexOf(".");
		int length = currency.length();
		Long amLong = 0l;
		if (index == -1) {
			amLong = Long.valueOf(currency + "00");
		} else if (length - index >= 3) {
			amLong = Long.valueOf((currency.substring(0, index + 3)).replace(".", ""));
		} else if (length - index == 2) {
			amLong = Long.valueOf((currency.substring(0, index + 2)).replace(".", "") + 0);
		} else {
			amLong = Long.valueOf((currency.substring(0, index + 1)).replace(".", "") + "00");
		}
		return amLong.toString();
	}

	/**
	 * 判断数据字符是否是数字（能转成数字的）
	 * 
	 * @param strNumber
	 *            数字字符
	 * @return 是数字字符返回true,不是返回false
	 */
	public static boolean isNumber(String strNumber) {
		boolean bolResult = false;
		BigDecimal temp;
		try {
			temp = new BigDecimal(strNumber);
			bolResult = true;
		} catch (NumberFormatException e) {
			e.getStackTrace();
		}
		return bolResult;
	}

	/**
	 * 数学相加
	 * 
	 * @param augend
	 *            加数
	 * @param augend2
	 *            被加数
	 * @return strResult
	 */
	public static String addStr(String augend, String augend2) {
		String strResult = "0.00";
		if (isNumber(augend) && isNumber(augend2)) {
			BigDecimal bdAugend = new BigDecimal(augend);
			BigDecimal bdAugend2 = new BigDecimal(augend2);
			bdAugend = bdAugend.add(bdAugend2);
			strResult = bdAugend.toString();
		}
		return strResult;
	}

	/**
	 * 数据相减
	 * 
	 * @param augend
	 *            减数
	 * @param augend2
	 *            被减数
	 * @return augend-augend2
	 */
	public static String reduceStr(String augend, String augend2) {
		String strResult = "0.00";
		if (isNumber(augend) && isNumber(augend2)) {
			BigDecimal bdAugend = new BigDecimal(augend);
			BigDecimal bdAugend2 = new BigDecimal(augend2);
			bdAugend = bdAugend.subtract(bdAugend2);
			strResult = bdAugend.toString();
		}
		return strResult;
	}

	/**
	 * String数字相乘
	 * 
	 * @param multiplicand1
	 *            乘数1
	 * @param multiplicand2
	 *            乘数2
	 * @param scale
	 *            小数保留位数
	 * @return 运算结果
	 */
	public static String multiplyStr(String multiplicand1, String multiplicand2, int scale) {
		String strResult = "0.00";
		if (isNumber(multiplicand1) && isNumber(multiplicand2)) {
			BigDecimal bdCand1 = new BigDecimal(multiplicand1);
			BigDecimal bdCand2 = new BigDecimal(multiplicand2);
			bdCand1 = bdCand1.multiply(bdCand2);
			strResult = bdCand1.setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
		}
		return strResult;
	}

	/**
	 * String数字相除
	 * 
	 * @param divisor
	 *            除数
	 * @param dividend
	 *            被除数
	 * @param scale
	 *            小数保留位数
	 * @return 运算结果
	 */
	public static String divideStr(String divisor, String dividend, int scale) {
		String strValue = "0.00";
		if (isNumber(divisor) && isNumber(dividend)) {
			if (isNumberAndNotZero(dividend)) {
				BigDecimal bdCand1 = new BigDecimal(divisor);
				BigDecimal bdCand2 = new BigDecimal(dividend);
				bdCand1 = bdCand1.divide(bdCand2, scale, BigDecimal.ROUND_HALF_UP);
				strValue = bdCand1.toString();
			} else {
				strValue = "0.00";
			}
		}
		return strValue;
	}

	/**
	 * 判断数字字符是否是数字且不等于0
	 * 
	 * @param strNumb
	 * @return
	 */
	public static boolean isNumberAndNotZero(String strNumb) {
		boolean bolResult = false;
		int intResult = 0;
		if (!TextUtils.isEmpty(strNumb)) {
			if (isNumber(strNumb)) {
				BigDecimal objBigDecimal = new BigDecimal(strNumb);
				BigDecimal objBigDecimal2 = new BigDecimal("0.00");
				intResult = objBigDecimal.compareTo(objBigDecimal2);
			}
		}
		if (intResult != 0) {
			bolResult = true;
		}
		return bolResult;
	}

	/**
	 * 判断数字字符是否是数字且等于0
	 * 
	 * @param strNumb
	 * @return
	 */
	public static boolean isNumberAndZero(String strNumb) {
		boolean bolResult = false;
		int intResult = 0;
		if (!TextUtils.isEmpty(strNumb)) {
			if (isNumber(strNumb)) {
				BigDecimal objBigDecimal = new BigDecimal(strNumb);
				BigDecimal objBigDecimal2 = new BigDecimal("0.00");
				intResult = objBigDecimal.compareTo(objBigDecimal2);
			}
		}
		if (intResult == 0) {
			bolResult = true;
		}
		return bolResult;
	}

	/**
	 * 控制输入金额监听 控制输入形式为形式12,12.0,12.00 配合EditText android:numeric="decimal"使用
	 * 
	 * @param mEdTxtPayMoney
	 */
	public static void setEditListener(final EditText mEdTxtPayMoney) {

		mEdTxtPayMoney.addTextChangedListener(new TextWatcher() {
			String temp2;
			boolean setTextFlag = true;

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				Log.i("TAG", "onTextChanged >>>>s=" + s + "start=" + start + "before=" + before + "count=" + count);
				// s.subSequence(count-1, count);

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

				Log.i("TAG", "beforeTextChanged >>>>s=" + s + "start=" + start + "after=" + after + "count=" + count);
				// if((s.length()+after)>9)//粘贴的时候超过限制长度，则不变化
				// {
				// mEdTxtPayMoney.setText(s);
				// setTextFlag=false;//
				// }

			}

			@Override
			public void afterTextChanged(Editable s) {
				Log.i("TAG", "afterTextChanged" + s);
				if (setTextFlag) {
					String str = mEdTxtPayMoney.getText().toString();
					if (str.length() == 1)// 不能以小数点开头
					{
						if (str.substring(0).equals(".")) {
							mEdTxtPayMoney.setText("");
						}
					} else// 判断只有一个小数点，并且小数的后最多有两位数字
					{
						int length = str.length();
						int pointNumber = 0;// 小数点
						int number = 0;// 小数点后的位数
						boolean startRecordNumber = false;
						for (int i = 0; i < length; i++) {

							String temp3 = str.substring(i, i + 1);
							if (temp3.equals(".")) {
								if (i == 0) {
									String temp4 = str.substring(1);
									mEdTxtPayMoney.setText(temp4);
									mEdTxtPayMoney.setSelection(temp4.length());
									return;
								}
								pointNumber++;
								startRecordNumber = true;
							}
							if (startRecordNumber) {
								number++;
							}
						}
						if (pointNumber >= 2) {
							mEdTxtPayMoney.setText(temp2);
							mEdTxtPayMoney.setSelection(temp2.length());
							return;
						}
						if (number > 3) {
							mEdTxtPayMoney.setText(temp2);
							mEdTxtPayMoney.setSelection(temp2.length());
							return;
						}

					}
					temp2 = str;
				}

			}
		});

	}

//	/**
//	 * 根据服务器下发费率，计算扣除手续费后金额。
//	 *
//	 * @param amount
//	 *            原始金额
//	 * @param posMerchantFee
//	 *            费率字符串
//	 *
//	 * @return 扣除刷卡手续费后金额。
//	 */
//	public static double getSwiperAmount(double amount) {
//		UserData mUserData = UserData.getInstance();
//		if (mUserData == null) {
//			return amount;
//		}
//		double mAmount = amount;
//		try {
//			String feeType = UserData.getInstance().getFeeType();
//			if ("RATIO".equals(feeType)) {
//				String feeRate = mUserData.getFeeRate();
//				float rate = Float.parseFloat(feeRate);
//				mAmount = amount * (1f - rate);
//			} else if ("CAPPING".equals(feeType)) {
//				String feeCapAmount = mUserData.getFeeCapAmount();
//				String feeMaxAmount = mUserData.getFeeMaxAmount();
//				String feeRate = mUserData.getFeeRate();
//				float rate = Float.parseFloat(feeRate);
//				double capAmount = Double.parseDouble(feeCapAmount);
//				double maxAmount = Double.parseDouble(feeMaxAmount);
//				if (amount >= capAmount) {
//					mAmount = amount - maxAmount;
//				} else {
//					mAmount = amount * (1f - rate);
//				}
//			} else if ("LADDER".equals(feeType)) {
//				String ladderFee = mUserData.getLadderFee();
//				String minFee = ladderFee.substring(0, ladderFee.indexOf("<"));
//				float minRate = Float.parseFloat(minFee);
//				String maxFee = ladderFee.substring(ladderFee.lastIndexOf("<") + 1);
//				float maxRate = Float.parseFloat(maxFee);
//				String feeCapAmount = ladderFee.substring(ladderFee.indexOf("<") + 1, ladderFee.lastIndexOf("<"));
//				double capAmount = Double.parseDouble(feeCapAmount);
//				if (amount > capAmount) {
//					mAmount = amount * (1f - maxRate);
//				} else {
//					mAmount = amount * (1f - minRate);
//				}
//
//			}
//		} catch (Exception e) {
//
//		}
//
//		mAmount = string2BigDecimal(mAmount + "");
//		return mAmount;
//
//	}

//	/**
//	 * 计算扣除刷卡手续费后的提现手续费； 提现费率 固定手续费 提现最低手续费
//	 *
//	 * @param amount
//	 * @return
//	 */
//	public static double getTixianFee(double amount) {
//		UserData mUserData = UserData.getInstance();
//		if (mUserData == null) {
//			return amount;
//		}
//		String extractionRate = mUserData.getExtractionRate();// 提现费率
//		String fixedFee = mUserData.getFixedFee();// 固定手续费
//		String extractionMinFee = mUserData.getExtractionMinFee();// 最低手续费
//		double dbleExtractionRate = Double.parseDouble(TextUtils.isEmpty(extractionRate) ? "0" : extractionRate);
//		double dbleFixedFee = Double.parseDouble(TextUtils.isEmpty(fixedFee) ? "0" : fixedFee);
//		double dbleExtractionMinFee = Double.parseDouble(TextUtils.isEmpty(extractionMinFee) ? "0" : extractionMinFee);
//		double fee = string2BigDecimal(dbleExtractionRate * amount / 100 + "");
//		if (fee <= dbleExtractionMinFee) {
//			fee = dbleFixedFee + dbleExtractionMinFee;
//		} else {
//			fee = dbleFixedFee + fee;
//		}
//		return string2BigDecimal(fee + "");
//	}

//	/**
//	 *
//	 * @param n
//	 *            刷卡费率
//	 * @param x
//	 *            固定手续费
//	 * @param y
//	 *            提现最低手续费
//	 * @param z
//	 *            提现费率
//	 * @return获取最低刷卡金额
//	 */
//	public static String getMinSwiper() throws Exception {
//
//		try {
//			UserData mUserData = UserData.getInstance();
//			if (mUserData == null) {
//				return "0";
//			}
//			float n = Float.parseFloat(mUserData.getFeeRate());
//			float x = Float.parseFloat(mUserData.getFixedFee());
//			float y = Float.parseFloat(mUserData.getExtractionMinFee());
//			float z = Float.parseFloat(mUserData.getExtractionRate()) / 100;
//			float m1 = x / (1 - n - z + z * n);
//			float m2 = (x + y) / (1 - n);
//			float m3 = y / (z - z * n);// 条件
//			if (m1 > m3) {
//				return MathUtil.twoNumber(m1 + 0.01f);
//			} else {
//				return MathUtil.twoNumber(m2 + 0.01f);
//			}
//		} catch (Exception e) {
//			throw new Exception(e.toString());
//		}
//
//	}

	/**
	 * "100.0128"; 变成：100.01
	 * 
	 * @param money
	 * @return
	 */
	public static double string2BigDecimal(String money) {
		if (TextUtils.isEmpty(money)) {
			return 0.00;
		} else {
			BigDecimal resultMoney = new BigDecimal(money);
			resultMoney = resultMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
			return resultMoney.doubleValue();
		}

	}
	/**
	 * 提供精确的减法运算。
	 * 
	 * @param v1
	 *            被减数
	 * @param v2
	 *            减数
	 * @return 两个参数的差
	 */
	public static double sub(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}
}
