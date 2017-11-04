package com.heshan.springboot.web.platform.common.utils;

import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.input.BOMInputStream;

/**
 * @ClassName  Tools
 * @Package  com.heshan.springboot.web.platform.common.utils
 * @Author Frank
 * @Description: 工具类
 * @Date 13:56 2017/8/28
 */
public class Tools {

	/**
	 * 随机生成六位数验证码
	 * 
	 * @return
	 */
	public static int getRandomNum() {
		Random r = new Random();
		return r.nextInt(900000) + 100000;// (Math.random()*(999999-100000)+100000)
	}

	/**
	 * 检测字符串是否不为空(null,"","null")
	 * 
	 * @param s
	 * @return 不为空则返回true，否则返回false
	 */
	public static boolean notEmpty(String s) {
		return s != null && !"".equals(s) && !"null".equals(s);
	}

	/**
	 * 检测字符串是否为空(null,"","null")
	 * 
	 * @param s
	 * @return 为空则返回true，不否则返回false
	 */
	public static boolean isEmpty(String s) {
		return s == null || "".equals(s) || "null".equals(s);
	}

	/**
	 * 字符串转换为字符串数组
	 * 
	 * @param str
	 *            字符串
	 * @param splitRegex
	 *            分隔符
	 * @return
	 */
	public static String[] str2StrArray(String str, String splitRegex) {
		if (isEmpty(str)) {
			return null;
		}
		return str.split(splitRegex);
	}

	/**
	 * 用默认的分隔符(,)将字符串转换为字符串数组
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static String[] str2StrArray(String str) {
		return str2StrArray(str, ",\\s*");
	}

	/**
	 * 按照yyyy-MM-dd HH:mm:ss的格式，日期转字符串
	 * 
	 * @param date
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String date2Str(Date date) {
		return date2Str(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 按照yyyy-MM-dd HH:mm:ss的格式，字符串转日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date str2Date(String date) {
		if (notEmpty(date)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				return sdf.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return new Date();
		} else {
			return null;
		}
	}

	/**
	 * 按照参数format的格式，日期转字符串
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String date2Str(Date date, String format) {
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		} else {
			return "";
		}
	}

	/**
	 * 把时间根据时、分、秒转换为时间段
	 * 
	 * @param StrDate
	 */
	public static String getTimes(String StrDate) {
		String resultTimes = "";

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date now;

		try {
			now = new Date();
			Date date = df.parse(StrDate);
			long times = now.getTime() - date.getTime();
			long day = times / (24 * 60 * 60 * 1000);
			long hour = (times / (60 * 60 * 1000) - day * 24);
			long min = ((times / (60 * 1000)) - day * 24 * 60 - hour * 60);
			long sec = (times / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);

			StringBuffer sb = new StringBuffer();
			// sb.append("发表于：");
			if (hour > 0) {
				sb.append(hour + "小时前");
			} else if (min > 0) {
				sb.append(min + "分钟前");
			} else {
				sb.append(sec + "秒前");
			}

			resultTimes = sb.toString();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return resultTimes;
	}

	/**
	 * 写txt里的单行内容
	 * 
	 * @param filePath
	 *            文件路径
	 * @param content
	 *            写入的内容
	 */
	public static void writeFile(String fileP, String content) {
		String filePath = String.valueOf(Thread.currentThread().getContextClassLoader().getResource("")) + "../../"; // 项目路径
		filePath = (filePath.trim() + fileP.trim()).substring(6).trim();
		if (filePath.indexOf(":") != 1) {
			filePath = File.separator + filePath;
		}
		try {
			OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(filePath), "utf-8");
			BufferedWriter writer = new BufferedWriter(write);
			writer.write(content);
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 验证邮箱
	 * 
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(String email) {
		boolean flag = false;
		try {
			String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 验证手机号码
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean checkMobileNumber(String mobileNumber) {
		boolean flag = false;
		try {
			Pattern regex = Pattern
					.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
			Matcher matcher = regex.matcher(mobileNumber);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 读取txt里的单行内容
	 * 
	 * @param filePath
	 *            文件路径
	 */
	public static String readTxtFile(String fileP) {
		try {

			String filePath = String.valueOf(Thread.currentThread().getContextClassLoader().getResource("")) + "../../"; // 项目路径
			filePath = filePath.replaceAll("file:/", "");
			filePath = filePath.replaceAll("%20", " ");
			filePath = filePath.trim() + fileP.trim();
			if (filePath.indexOf(":") != 1) {
				filePath = File.separator + filePath;
			}
			String encoding = "utf-8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding); // 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					return lineTxt;
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件,查看此路径是否正确:" + filePath);
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
		}
		return "";
	}
	
	public static BigDecimal calcDivide(int a, int b) {
		if (b == 0) return null;
		BigDecimal v1 = new BigDecimal(a);
		BigDecimal v2 = new BigDecimal(b);
		return v1.divide(v2, 2, BigDecimal.ROUND_HALF_DOWN).multiply(new BigDecimal(100));
	}
	
	public static String formatPercent(BigDecimal big) {
		if(big==null) return "";
		return big + "%";
	}
	
	public static String formatMoney(String money) {
		if(isNull(money)) return "";
		if(money.length()>=9){
			return divide(new BigDecimal(money),new BigDecimal(100000000))+"亿";
		}else if(money.length()>=8){
			return divide(new BigDecimal(money),new BigDecimal(10000000))+"千万";
		}else if(money.length()>=5){
			return divide(new BigDecimal(money),new BigDecimal(10000))+"万";
		}
		return money;
	}
	
	public static BigDecimal divide(BigDecimal v1, BigDecimal v2) {
		return v1.divide(v2, 2, BigDecimal.ROUND_HALF_DOWN);
	}
	
	public static boolean isNull(String str){
		if(str==null || "".equals(str.trim())){
			return true;
		}
		return false;
	}
	
	public static boolean isNullList(List<? super Object> list){
		if(list==null || list.isEmpty() ) return true;
		return false;
	}
	
	public static boolean isNullArray(String[] str){
		int count = 0;
		for(String sa : str){
			if(isNull(sa)){
				count++;
			}
		}
		return str.length == count;
	}
	
	public static String[] list2Array(List<String> list){
		if(list == null || list.isEmpty()){
			return new String[]{""};
		}
		return list.toArray(new String[list.size()]);
	}
	
	public static long[] list2ArrayLong(List<Long> list){
		if(list == null || list.isEmpty()){
			return new long[]{};
		}
		Long[] longs = list.toArray(new Long[list.size()]);
		long[] lons = new long[longs.length];
		for(int i = 0; i <longs.length; i++){
			lons[i] = longs[i].longValue();
		}
		return lons;
	}
	
	public static List<JSONObject> analyzePic(String pic){
		List<JSONObject> array = new ArrayList<JSONObject>();
		JSONObject obj = null;
		if(isNull(pic)) return array;
		String[] picList  = pic.split("\\,");
		String[] file = null;
		for(int i = 0; i < picList.length; i++){
			obj = new JSONObject();
			try {
				file = picList[i].split("\\|");
				obj.put("file_name", file[0]);
				obj.put("file_path", file[1]);
				array.add(obj);
			} catch (Exception e) {
				continue;
			}
		}
		return array;
	}
	
	public static String verifyRequired(String fieldName, String field){
		if(isNull(field)) return fieldName+"不能为空！";
		return null;
	}
	
	public static String verifyLength(String fieldName, String field, int length, boolean required){
		if(isNull(field) && required) return fieldName+"不能为空！";
		if(!isNull(field) && field.length()>length) return fieldName+"长度不能超过"+length+"!";
		return null;
	}

	
	public static String decimalFormat(String text){
		if(isNull(text))return null;
		String ret = null;
		DecimalFormat format = new DecimalFormat("0.0000");
		try {
			ret = format.format(Double.parseDouble(text));
		} catch (Exception e) {
		}
		return ret;
	}
	/**
	 * @Author Frank
	 * @Description: InputStream 转字节流
	 * @param
	 * @Date 11:33 2017/8/30
	 */
	public static byte[] toByteArray(InputStream input) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		InputStream inputStream=new BOMInputStream(input);
		byte[] bytes=null;
		try {
			byte[] buffer = new byte[4096];
			int n = 0;
			while (-1 != (n = inputStream.read(buffer))) {
				output.write(buffer, 0, n);
			}
			bytes=output.toByteArray();
		}finally {
			inputStream.close();
			input.close();
			output.close();
		}
		return  bytes;
	}
	
	public static void main(String[] args) {
		System.out.println(decimalFormat(""));
	}
	
}
