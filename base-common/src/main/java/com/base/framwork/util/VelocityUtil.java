/**
 * 软件著作权：厦门益乐付信息技术有限公司
 * 
 * 系统名称：益乐付项目
 * 
 */
package com.base.framwork.util;

import com.alibaba.fastjson.JSON;
import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.config.DefaultKey;
import org.apache.velocity.tools.config.ValidScope;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Velocity工具类
 * 
 * @author yangfan
 */
@DefaultKey("util")
@ValidScope(Scope.APPLICATION)
public class VelocityUtil {

	/**
	 * float类型转换为int
	 * 
	 * @param f
	 * @return
	 */
	public int parseInt(float f) {
		return (int) f;
	}

	/**
	 * string类型转换为int
	 * 
	 * @param f
	 * @return
	 */
	public int parseInt(String f) {
		if (f == null || "".equals(f)) {
			return 0;
		}
		return Integer.valueOf(f);
	}

	/**
	 * 四舍五入保留n位小数
	 * 
	 * @param money
	 * @param n
	 * @return
	 */
	public String roundTo(BigDecimal money, int n) {
		if (money == null) {
			return "";
		}

		double d = money.doubleValue();
		return this.roundTo(d, n);
	}

	/**
	 * 四舍五入保留n位小数
	 * 
	 * @param money
	 * @param n
	 * @return
	 */
	public String roundTo(String money, int n) {
		if (money == null || "".equals(money)) {
			return "";
		}

		double d = Double.valueOf(money);
		return this.roundTo(d, n);
	}

	/**
	 * 四舍五入保留n位小数
	 * 
	 * @param money
	 * @param n
	 * @return
	 */
	public String roundTo(double money, int n) {
		double d = money;
		long power = (long) Math.pow(10, n);
		d = Math.round(d * power) / (power * 1.00);
		String s = String.valueOf(d);
		if (s.indexOf(".") == -1) {
			s = s + ".00";
		} else {
			String[] arr = s.split("\\.");
			if (arr[1].length() == 1) {
				s = s + "0";
			}
		}
		return s;
	}

	/**
	 * 金额格式化
	 * 
	 * @param money
	 * @return
	 */
	public String formatMoney(String money) {
		if (StringUtils.isEmpty(money))
			return "0";
		return this.formatMoney(Double.parseDouble(money));
	}

	/**
	 * 金额格式化
	 * 
	 * @param money
	 * @return
	 */
	public String formatMoney(double money) {
		DecimalFormat df = new DecimalFormat("##,###,###,###,##0.00");
		return df.format(money);
	}
	
	/**
	 * 金额格式化
	 * 
	 * @param money
	 * @return
	 */
	public String formatMoney(BigDecimal money) {
		return this.formatMoney(money.doubleValue());
	}

	/**
	 * 金额格式化(取整)
	 * 
	 * @param money
	 * @return
	 */
	public static String formatIntegerMoney(BigDecimal money) {
		if (money == null) {
			return "-";
		}
		return formatIntegerMoney(money.doubleValue());
	}
	
	/**
	 * 金额格式化(取整)
	 * 
	 * @param money
	 * @return
	 */
	public static String formatIntegerMoney(double money) {
		DecimalFormat df = new DecimalFormat("##,###,###,###,###0.00");
		return df.format(money).substring(0,df.format(money).length() - 3);
	}
	
	/**
	 * 金额格式化(取整)
	 * 
	 * @param money
	 * @return
	 */
	public static String formatDecimalMoney(BigDecimal money) {
		if (money == null) {
			return "--";
		}
		return formatDecimalMoney(money.doubleValue());
	}
	
	/**
	 * 金额格式化(取整)
	 * 
	 * @param money
	 * @return
	 */
	public static String formatDecimalMoney(double money) {
		DecimalFormat df = new DecimalFormat("##,###,###,###,##0.00");
		return df.format(money).substring(df.format(money).length() - 2);
	}

	/**
	 * 日期转化 $util.formatDate("20131223 10:32:49", "yyyyMMdd HH:mm:ss",
	 * "yyyy-MM-dd HH:mm:ss")
	 * 
	 * @param sdate
	 *            日期字符串
	 * @param oformater
	 *            原始格式化
	 * @param nformater
	 *            新格式化
	 * @return
	 */
	public String formatDate(String sdate, String oformater, String nformater) {
		if (sdate == null || "".equals(sdate)) {
			return "";
		}

		try {
			SimpleDateFormat osdf = new SimpleDateFormat(oformater);
			SimpleDateFormat nsdf = new SimpleDateFormat(nformater);
			Date odate = osdf.parse(sdate);
			return nsdf.format(odate);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "格式化错误";
	}
	/**
	 * 获取当前时间
	 * @date 2015年6月15日
	 * @author YANGD
	 * @param formatStr
	 * @return
	 */
	public String getCurrentDate(String formatStr){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
			return sdf.format(new Date());
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	/**
	 * 增加月份
	 * @date 2015年5月12日
	 * @author sunzhiwei
	 * @param sdate
	 * @param oformater
	 * @param nformater
	 * @param renewalsdata
	 * @return
	 */
	public String addMonth(String sdate, String oformater, String nformater, String renewalsdata){
		if (sdate == null || "".equals(sdate)) {
			return "";
		}
		if (renewalsdata == null || "".equals(renewalsdata)) {
			renewalsdata = "0";
		}
		SimpleDateFormat osdf = new SimpleDateFormat(oformater);
		SimpleDateFormat nsdf = new SimpleDateFormat(nformater);
		Calendar calendar = Calendar.getInstance();
		Date startDate = null;
		try {
			startDate = osdf.parse(sdate);
			System.out.println(startDate);
			calendar.setTime(startDate);
			System.out.println(osdf.format(calendar.getTime()));
			System.out.println(Integer.parseInt(renewalsdata));
			calendar.add(Calendar.MONTH, Integer.parseInt(renewalsdata));
			System.out.println(nsdf.format(calendar.getTime()));
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return nsdf.format(calendar.getTime());		
	}

	/**
	 * 
	 * @param userName
	 * @return
	 */
	public String hideUserName(String userName) {
		if (userName != null && !"".equals(userName)) {
			if (userName.length() > 3) {
				return "***" + userName.substring(3, userName.length());
			} else {
				return "***";
			}
		} else {
			return "用户名为空";
		}
	}

	/**
	 * 特殊字符转html
	 * 
	 * @param userName
	 * @return
	 */
	public String textToHtml(String s) {
		return s.replace("&", "&amp;").replace(" ", "&nbsp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;").replace("\n", "<BR>");
	}

	/**
	 * 获取银行卡号后四位
	 */
	public String bankNoSubstr(String bankNo) {
		if (!StringUtils.isEmpty(bankNo)) {
			return bankNo.substring(bankNo.length() - 4, bankNo.length());
		}
		return "";
	}

	/**
	 * 返回银行卡号如**** 3333 简要描述
	 * 
	 * @date 2014年3月10日
	 * @param bankNo
	 * @return
	 */
	public String formatBankNo8(String bankNo) {
		if (!StringUtils.isEmpty(bankNo)) {
			return "**** " + bankNoSubstr(bankNo);
		}
		return "";
	}

	/**
	 * 返回银行卡号如**** **** **** 3333 简要描述
	 * 
	 * @date 2014年3月10日
	 * @param bankNo
	 * @return
	 */
	public String formatBankNo16(String bankNo) {
		if (!StringUtils.isEmpty(bankNo)) {
			return "**** **** **** " + bankNoSubstr(bankNo);
		}
		return "";
	}

	/**
	 * 金额转换 10000 转换为万
	 */
	public String formatMoneyToW(BigDecimal money) {
		if (money == null) {
			return "0.00";
		} else {
			return money.doubleValue() / 10000 + "0";
		}
	}

	/**
	 * 返回手机号如180****3333 简要描述
	 * 
	 * @date 2014年3月10日
	 * @param bankNo
	 * @return
	 */
	public String formatMobile(String mobile) {
		if (!StringUtils.isEmpty(mobile)) {
			return mobile.substring(0, 3) + "****" + mobile.substring(mobile.length() - 4);
		}
		return "";
	}

	/**
	 * 返回身份证 如 361************970 简要描述
	 * 
	 * @date 2014年3月10日
	 * @param bankNo
	 * @return
	 */
	public String formatIdentNo(String identNo) {
		if (!StringUtils.isEmpty(identNo)) {
			return identNo.substring(0, 3) + "************" + identNo.substring(identNo.length() - 3);
		}
		return "";
	}

	public boolean isNull(Object obj) {
		return obj == null || "".equals(obj);
	}

	public boolean isNotNull(Object obj) {
		return !isNull(obj);
	}

	public boolean checkEqual(String str, String str1) {
		if (StringUtils.isEmpty(str)) {
			return false;
		}

		if (StringUtils.isEmpty(str1)) {
			return false;
		}
		if (str.equals(str1)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 字符串位置
	 * 
	 * @author 温亦汝
	 * @date 2014年3月20日
	 * @param str
	 * @param subStr
	 * @return
	 */
	public int strIndexOf(String str, String subStr) {
		if (StringUtils.isEmpty(str) || StringUtils.isEmpty(subStr)) {
			return -1;
		}
		return str.indexOf(subStr);
	}

	public static void main(String[] args) {
//		addMonth("20140201", "yyyyMMdd", "yyyy-MM-dd", "4");
		BigDecimal money = new BigDecimal(123.34);
		formatDecimalMoney(money);
		System.out.println(formatDecimalMoney(money));
	}

	/**
	 * 姓名隐掉第一个字符
	 */
	public String hideFirstChar(String custName) {
		if (!StringUtils.isEmpty(custName)) {
			return "*" + custName.substring(1, custName.length());
		}
		return "";
	}

	/**
	 * 截取字符串
	 */
	public String cutString(String str, int num) {
		if (!StringUtils.isEmpty(str)) {
			String returnStr = "";
			returnStr = str.length() > num ? str.substring(0, num) + "..." : str;
			return returnStr;
		}
		return "";
	}

	/**
	 * 两个金额相减
	 */
	public String subAmt(BigDecimal b1, BigDecimal b2) {
		if (b1 == null) {
			b1 = new BigDecimal(0);
		}
		if (b2 == null) {
			b2 = new BigDecimal(0);
		}
		BigDecimal b3 = b1.subtract(b2);
		return String.valueOf(b3);
	}
	
	/**
	 * String[] 金额数相加   ， type = "0"  全部相加,  "1"  只对正的相加  ,  "-1"  只对负的相加 ，默认全部相加 
	 */
	public String sumArrayAmt(String[] arrayAmt,String type){
		if(arrayAmt==null||arrayAmt.length==0){
			return "0.00";
		}
		try {
			BigDecimal bd_total = BigDecimal.ZERO;
			for (String amt : arrayAmt) {
				BigDecimal bd_amt = BigDecimal.ZERO;
				try {
					if(!StringUtils.isEmpty(amt)){
						bd_amt = new BigDecimal(amt);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				if("1".equals(type)){
					if(bd_amt.compareTo(BigDecimal.ZERO)>0){
						bd_total = bd_total.add(bd_amt);
					}
				}else if("-1".equals(type)){
					if(bd_amt.compareTo(BigDecimal.ZERO)<0){
						bd_total = bd_total.add(bd_amt);
					}
				}else{
					bd_total = bd_total.add(bd_amt);
				}
			}
			bd_total = bd_total.setScale(2, RoundingMode.HALF_UP);
			return bd_total.toString();		
		} catch (Exception e) {
			e.printStackTrace();
			return "0.00";
		}
	}
	

	/**
	 * 
	 */
	public String subStrLen(String str, int len) {
		if (StringUtils.isEmpty(str)) {
			return "";
		}
		if (str.length() > len) {
			return str.substring(0, len) + "...";
		}

		return str;
	}
	
	/**
	 * 截取字符串,$util.subStr("201501",4,6) return 01
	 */
	public String subStr(String str, int begin, int end) {
		if(StringUtils.isEmpty(str))
			return str;
		return str.substring(begin, end);
	}

	public String formatEmail(String email) {
		if (StringUtils.isEmpty(email)) {
			return "";
		}
		String[] emailArr = email.split("@");
		String emailName = emailArr[0];
		int mailNameHalfLen = emailName.length() / 2;
		return emailArr[0].substring(0, mailNameHalfLen) + "****@" + emailArr[1];
	}

	/**
	 * 金额格式化(整数)
	 * 
	 * @param money
	 * @return
	 */
	public String formatMoneyIntPart(String money) {
		DecimalFormat df = new DecimalFormat("##,###,###,###,##0");
		return df.format(Double.parseDouble(money));
	}

	/**
	 * 百分比格式化
	 * 
	 * @date 2015年5月6日
	 * @author sunzhiwei
	 * @param prop
	 * @param fractionDigits
	 *            保留小数点位数
	 * @return
	 */
	public String formatProp(BigDecimal prop, int fractionDigits) {
		if (null == prop) {
			return null;
		}
		NumberFormat percent = NumberFormat.getPercentInstance();
		percent.setMaximumFractionDigits(fractionDigits);
		return percent.format(prop.doubleValue());
	}

	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param v1
	 *            被乘数
	 * @param v2
	 *            乘数
	 * @return 两个参数的积
	 */
	public String mul(Object b1, Object b2) {
		if (b1 == null || b2 == null) {
			return "";
		}
		DecimalFormat    df   = new DecimalFormat("######0.00");
		BigDecimal v1 = new BigDecimal(b1.toString());   
		BigDecimal v2 = new BigDecimal(b2.toString());
		return df.format(v1.multiply(v2).doubleValue());
	}
	
	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param v1
	 *            数值1
	 * @param v2
	 *            数值2
	 * @return 两个参数的和
	 */
	public String addStr(Object b1, Object b2) {
		if (b1 == null || b2 == null) {
			return "0";
		}
		DecimalFormat    df   = new DecimalFormat("#######");
		BigDecimal v1 = new BigDecimal(b1.toString());   
		BigDecimal v2 = new BigDecimal(b2.toString());
		return df.format(v1.add(v2).doubleValue());
	}
	
	/**
	 * 性别字典
	 * @date 2015年5月6日
	 * @author sunzhiwei
	 * @param inValue
	 * @return
	 */
	public String sex(String inValue) {
		String returnValue="";
		if ("0".equals(inValue)) {
			returnValue = "男";
		}
		else if ("1".equals(inValue)) {
			returnValue = "女";
		}
		return returnValue;
	}
	
	/**
	 * 户口性质字典
	 * @date 2015年5月6日
	 * @author sunzhiwei
	 * @param inValue 00外籍 01本地城镇 02 本地农村 03 外地城镇 04 外地农村
	 * @return
	 */
	public String accountType(String inValue) {
		String returnValue="";
		if ("00".equals(inValue)) {
			returnValue = "外籍";
		}
		else if ("01".equals(inValue)) {
			returnValue = "本地城镇";
		}
		else if ("02".equals(inValue)) {
			returnValue = "本地农村 ";
		}
		else if ("03".equals(inValue)) {
			returnValue = "外地城镇";
		}
		else if ("04".equals(inValue)) {
			returnValue = "外地农村";
		}
		return returnValue;
	}
	
	/**
	 * 性别字典
	 * @date 2015年5月6日
	 * @author sunzhiwei
	 * @param inValue 01中共党员 02 中共预备役党员 03共青团员 04民革党员 05民盟盟员 06民建会员 07民进会员 08农工党党员 09致公党党员 10九三学社社员 11台盟盟员 12无党派人士 13群众 14港澳台同胞 15其他
	 * @return
	 */
	public String politicalStatus(String inValue) {
		String returnValue="";
		if ("01".equals(inValue)) {
			returnValue = "中共党员";
		}
		else if ("02".equals(inValue)) {
			returnValue = "中共预备役党员";
		}
		else if ("03".equals(inValue)) {
			returnValue = "共青团员";
		}
		else if ("04".equals(inValue)) {
			returnValue = "民革党员";
		}
		else if ("05".equals(inValue)) {
			returnValue = "民盟盟员";
		}
		else if ("06".equals(inValue)) {
			returnValue = "民建会员";
		}
		else if ("07".equals(inValue)) {
			returnValue = "民进会员";
		}
		else if ("08".equals(inValue)) {
			returnValue = "农工党党员 ";
		}
		else if ("09".equals(inValue)) {
			returnValue = "致公党党员 ";
		}
		else if ("10".equals(inValue)) {
			returnValue = "九三学社社员";
		}
		else if ("11".equals(inValue)) {
			returnValue = "台盟盟员";
		}
		else if ("12".equals(inValue)) {
			returnValue = "无党派人士 ";
		}
		else if ("13".equals(inValue)) {
			returnValue = "群众";
		}
		else if ("14".equals(inValue)) {
			returnValue = "港澳台同胞 ";
		}
		else if ("15".equals(inValue)) {
			returnValue = "其他";
		}
		return returnValue;
	}
	
	/**
	 * 证件类型字典
	 * @date 2015年5月6日
	 * @author sunzhiwei
	 * @param inValue 0身份证 1护照
	 * @return
	 */
	public String identityType(String inValue) {
		String returnValue="";
		if ("0".equals(inValue)) {
			returnValue = "身份证";
		}
		else if ("2".equals(inValue)) {
			returnValue = "护照";
		}
		return returnValue;
	}
	
	/**
	 * 员工类型字典
	 * @date 2015年5月6日
	 * @author sunzhiwei
	 * @param inValue 0、正式；1、试用 ；2、临时
	 * @return
	 */
	public String empType(String inValue) {
		String returnValue="";
		if ("0".equals(inValue)) {
			returnValue = "正式";
		}
		else if ("1".equals(inValue)) {
			returnValue = "试用";
		}
		else if ("2".equals(inValue)) {
			returnValue = "临时";
		}
		return returnValue;
	}
	
	/**
	 * 员工状态字典
	 * @date 2015年5月6日
	 * @author sunzhiwei
	 * @param inValue 1在职 2试用期 3已离职 4未入职
	 * @return
	 */
	public String empStatus(String inValue) {
		String returnValue="";
		if ("1".equals(inValue)) {
			returnValue = "在职";
		}
		else if ("2".equals(inValue)) {
			returnValue = "试用期";
		}
		else if ("3".equals(inValue)) {
			returnValue = "已离职";
		}
		else if ("4".equals(inValue)) {
			returnValue = "未入职";
		}
		return returnValue;
	}
	
	/**
	 * 福利类型
	 * @date 2015年5月11日
	 * @author sunzhiwei
	 * @param inValue 01、保险产品 02、福利券
	 * @return
	 */
	public String welfareType(String inValue) {
		String returnValue="";
		if ("01".equals(inValue)) {
			returnValue = "保险产品";
		}
		else if ("02".equals(inValue)) {
			returnValue = "福利券";
		}
		return returnValue;
	}
	
	/**
	 * 减员类型
	 * @date 2015年5月11日
	 * @author sunzhiwei
	 * @param inValue 0、离职 1、停保
	 * @return
	 */
	public String stopSclSecReason(String inValue) {
		String returnValue=inValue;
		if ("0".equals(inValue)) {
			returnValue = "离职";
		}
		else if ("1".equals(inValue)) {
			returnValue = "停保";
		}
		return returnValue;
	}

	/**
	 * 核算记录类型
	 * @date 2015年5月11日
	 * @author sunzhiwei
	 * @param inValue 类型：1、薪资；2、社保；3、公积金；4、奖金
	 * @return
	 */
	public String accountingType(String inValue) {
		String returnValue=inValue;
		if ("1".equals(inValue)) {
			returnValue = "薪资核算";
		}
		else if ("2".equals(inValue)) {
			returnValue = "社保核算";
		}
		else if ("3".equals(inValue)) {
			returnValue = "公积金核算";
		}
		else if ("4".equals(inValue)) {
			returnValue = "奖金核算";
		}
		return returnValue;
	}
	
	/**
	 * 社保公积金变更状态
	 * @date 2015年5月11日
	 * @author sunzhiwei
	 * @param inValue 0已增员；1待增员；2已变更；3待变更；4已减员；5待减员
	 * @return
	 */
	public String sbGjjStatus(String inValue) {
		String returnValue=inValue;
		if ("0".equals(inValue)) {
			returnValue = "已增员";
		}
		else if ("1".equals(inValue)) {
			returnValue = "待增员";
		}
		else if ("2".equals(inValue)) {
			returnValue = "已变更";
		}
		else if ("3".equals(inValue)) {
			returnValue = "待变更";
		}
		else if ("4".equals(inValue)) {
			returnValue = "已减员";
		}
		else if ("5".equals(inValue)) {
			returnValue = "待减员";
		}
		return returnValue;
	}
	/**
	 * 
	 * @date 2015年5月14日
	 * @author YANGD
	 * @param str 要分隔的字符串
	 * @param regex 分隔的标识 如','
	 * @param index 要获取分隔的第几个字符
	 * @return
	 */
	public String splitStr(String str,String regex,int index){
		if(StringUtils.isEmpty(str))
			return str;
			String[] strArray= {};
		if(",".equals(regex))
			strArray= str.split(regex);
		else
			strArray= str.split("//"+regex);
		if(index >= strArray.length)
			return null;
		return strArray[index];
	}
	/**
	 * 百分比转化,精度最大取2位小数,添加百分号 例如0.231 转为23.1%
	 * @date 2015年12月08日
	 * @author zhouyw
	 * @param num
	 * @return
	 */
	public String formatPercent(BigDecimal num){
		NumberFormat percent = NumberFormat.getPercentInstance();
        percent.setMaximumFractionDigits(2);
		return percent.format(num);
	}
	
	public String formatExceptionMessage(String message){
		if(message.indexOf("Exception:")!=-1){
			message = message.substring(message.indexOf("Exception:")+"Exception:".length());
		}
		return message;
	}
	
	/**
	 * 判断是否存于数组中
	 * @date 2015年10月29日
	 * @author 曾天然
	 * @param string[], string
	 * @return true/false
	 */
	public String arrayIndexof(String[] array, String itm){
		String returnStr = "false";
		if(itm == null || array.length == 0){
			return returnStr;
		}
		try {
			for (String arr : array) {
				 if(arr == itm){
					 returnStr = "true";
				 }
			}
			return returnStr;		
		} catch (Exception e) {
			  e.printStackTrace();
			return returnStr;
		}
	}
	
	
	/**
	 * 工作日：使用7位定常的01编码代表周一至周日的上班情况；如1111100代表周一至周五上班周六周日不上班
	 * @author zhouyw
	 * @date   2015年12月22日
	 * @param workDate
	 * @return
	 */
	public String parseWorkDate(String workDate){
		try {
			StringBuffer sbf = new StringBuffer();
			char gowork_flag = '1';
			char charAt0 = workDate.charAt(0);
			if(gowork_flag == charAt0){
				sbf.append("周一").append("&nbsp;");
			}
			
			char charAt1 = workDate.charAt(1);
			if(gowork_flag == charAt1){
				sbf.append("周二").append("&nbsp;");
			}
			
			char charAt2 = workDate.charAt(2);
			if(gowork_flag == charAt2){
				sbf.append("周三").append("&nbsp;");
			}
			
			char charAt3 = workDate.charAt(3);
			if(gowork_flag == charAt3){
				sbf.append("周四").append("&nbsp;");
			}
			
			char charAt4 = workDate.charAt(4);
			if(gowork_flag == charAt4){
				sbf.append("周五").append("&nbsp;");
			}
			
			char charAt5 = workDate.charAt(5);
			if(gowork_flag == charAt5){
				sbf.append("周六").append("&nbsp;");
			}
			
			char charAt6 = workDate.charAt(6);
			if(gowork_flag == charAt6){
				sbf.append("周日").append("&nbsp;");
			}
			
			return sbf.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	/**
	 * 计算时间间隔的总和  12 不存在交叉  HH:mm
	 * @author zhouyw
	 * @date   2015年12月22日
	 * @param start1
	 * @param end1
	 * @param start2
	 * @param end2
	 * @return
	 */
	public String computerBeteewTime(String start1_str,String end1_str,String start2_str,String end2_str){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

			Date start1 = sdf.parse(start1_str);
			Date end1 = sdf.parse(end1_str);
			Date start2 = sdf.parse(start2_str);
			Date end2 = sdf.parse(end2_str);
		
			long time = end1.getTime() - start1.getTime() +  end2.getTime() - start2.getTime();
      		BigDecimal result = new BigDecimal(time/(1000*60*60.0)).setScale(1,RoundingMode.CEILING);
      		String resp = result.toString();
      		if(resp.indexOf(".0")!=-1){
      			resp = resp.substring(0, resp.length()-2);
      		};
			return resp ;
		} catch (ParseException e) {
			e.printStackTrace();
			return "0";
		}
	}
	
	/**
	 * 
	 * @author zhouyw
	 * @date   2015年12月23日
	 * @param bean
	 * @return
	 */
	public String parse2Json(Object bean){
		try {
			String json = JSON.toJSONString(bean);
//			return json.replaceAll("\"", "'");
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
}
