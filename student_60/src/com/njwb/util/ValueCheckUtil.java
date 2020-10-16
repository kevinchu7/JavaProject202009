package com.njwb.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

/**值校验
 * @author  朱凯
 * No.60
 */
public class ValueCheckUtil {
	/**
	 * 用户名值校验
	 * (6-16位字母、数字、下划线，不可以数字打头）
	 * @param userName
	 * @return
	 */
	public static boolean matchUserName(String userName){
		return userName.matches("^[^0-9][\\w_]{5,15}$");
		
	}
	
	
	
//	public static boolean matchUserName(String userName){
//		if(userName.length()<6||userName.length()>16){
//			return false;
//		}
//		return true;
//		
//	}

	
	/**
	 * 
	 * 密码值校验
	 * （6-16位字母、数字、下划线）
	 * @param password
	 * @return
	 */
	public static boolean matchPassword(String password){
		return password.matches("^[\\w_]{6,16}$");
	}
	
	
//	/**
//	 * 密码值校验（6-16位数字和字母的组合）
//	 * @param password
//	 * @return
//	 */
//	public static boolean matchPassword(String password){
//		return password.matches("^(?![0-9]+$)(?![a-zA-Z]+$)" +
//				"[0-9A-Za-z]{6,16}$");
//	}
	
	/**
	 * 手机号值校验
	 * @param phone
	 * @return
	 */
	public static boolean matchPhoneNumber(String phone){
		return phone.matches("1[358][0-9]{9,9}");
	}
	
	/**
	 * 身份证号值校验
	 * @param idCardNo
	 * @return
	 */
	public static boolean matchIdCardNo(String idCardNo){
		
		return idCardNo.matches("(^[1-9]\\d{5}(18|19|20)\\d{2}" +
				"((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)" +
				"\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|" +
				"(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)");
	}
	
	/**
	 * 姓名值校验
	 * 
	 * 	1.可以是中文
		2.可以是英文，允许输入点（英文名字中的那种点）， 允许输入空格
		3.中文和英文不能同时出现
		4.长度在20个字符以内
	 * 
	 * @param realName
	 * @return
	 */
	public static boolean matchRealName(String realName){
		return realName.matches("^([\\u4e00-\\u9fa5]{1,20}|" +
				"[a-zA-Z\\.\\s]{1,20})$");
	}
	
	
	/**
	 * 时间字符串校验
	 * （yyyy-MM-dd HH:mm:ss）
	 * @param timeStr
	 * @return
	 */
	public static boolean matchTimeStr(String timeStr){
		String regex="\\d{4}(\\-|\\/|.)\\d{1,2}" +
				"\\1\\d{1,2} ([0-1]?[0-9]|2[0-3])" +
				":([0-5][0-9]):([0-5][0-9])$";
		boolean matches = Pattern.matches(regex,timeStr);
		if(!matches) {
			return matches;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 //设置日期格式转的严谨性
        sdf.setLenient(false);
        try {
            sdf.parse(timeStr);
        } catch (ParseException e) {
//            e.printStackTrace();
            return false;
        }
        return matches;
		
	}
	
	
	
	/**
	 * 日期字符串校验
	 * @param dateStr
	 * @return
	 */
	public static boolean matchDateStr(String dateStr) {
		String regex="[0-9]{4}-[0-9]{2}-[0-9]{2}";
		boolean matches =  Pattern.matches(regex, dateStr);
        if(!matches){
            return matches;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //设置日期格式转的严谨性
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
        } catch (ParseException e) {
//            e.printStackTrace();
            return false;
        }
        return matches;
	}
	
	/**
	 * 价格字符串校验
	 * （正整数或2位小数以内）
	 * @param priceStr
	 * @return
	 */
	public static boolean matchPriceStr(String priceStr){
		return priceStr.matches("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");
		
	}
	
	/**
	 * 航班号值校验
	 * （前两位代码由大写字母组成，后跟3-4位数字）
	 * @param flightId
	 * @return
	 */
	public static boolean matchFlightId(String flightId){
		return flightId.matches("^[A-Z]{2}\\d{3,4}$");
	}
}
