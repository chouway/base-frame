package com.base.simple.test.regex.match;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import com.learn.regex.MatcherUtils;

public class MatcherTest {
	private Logger logger = LoggerFactory.getLogger(MatcherTest.class);
	
	
	
	/**
	 * 
	 * 全匹配  部份匹配
	 * @author zhouyw
	 * 2015年11月7日
	 */
	@Test
	public void test(){
		Pattern p = Pattern.compile("(?<target>\\d{7})");
		String strText = "1234567aabbcc3344";
		Matcher m = p.matcher(strText);
		

		while (m.find()) {
			int start = m.start();
			int end = m.end();
			logger.info("matched form :start={},end={}",start,end);
			logger.info("matcher form={}",strText.subSequence(start, end));
			String target = m.group("target");
			logger.info("m.target-->,target={}", target);
		}
		
		//全匹配
		boolean matches = m.matches();
		logger.info("是否匹配,matches={}", matches);
		
		//部份匹配
		boolean lookingAt = m.lookingAt();
		logger.info("是否匹配,lookingAt={}", lookingAt);
		
	}
	/**
	 * 
	 * 全匹配  simple
	 * @author zhouyw
	 * 2015年11月7日
	 */
	@Test
	public void test0_1(){
		//匹配 false;
//		CharSequence input = "1234567aabbcc3344";
		
		CharSequence input = "1234567";
		String regex = "(?<target>\\d{7})";
		//全匹配
		boolean matches = Pattern.matches(regex , input);
		logger.info("是否匹配,matches={}", matches);
		
	}
	
	
	
	/**
	 *替换  replaceAll  捕获组 组获取 
	 * @author zhouyw
	 * 2015年11月7日
	 */
	@Test
	public void test1(){
		//组命名（？<组名>）
		String regex = "(?<target>\\d{7})";
		String strText = "1234567aabbcc3344";

		logger.info("源串,strText={}", strText);
		//序列获取捕获组 ：  $序列
		String replaceAll = strText.replaceAll(regex, "xxx$0");
		logger.info("替换后,replaceAll={}", replaceAll);

		//组名获取捕获组 ： ${组名}
		String replaceAll_1 = strText.replaceAll(regex, "xxx${target}");
		logger.info("替换后,replaceAll_1={}", replaceAll_1);
		
	}
	/**
	 *  替换  replaceAll  当源串含有 转义字符时 ，调用quteReplacement进行预处理，即 在转义符前加  \\
	 * @author zhouyw
	 * 2015年11月7日
	 */
	@Test
	public void test1_1(){
		Pattern pattern = Pattern.compile("\\{C0\\}");

		Matcher matcher = pattern.matcher("Price: [{C0}].");
		
		String sourceStr = "€6.99";
		String replaceAll = matcher.replaceAll(sourceStr);
		logger.info("没有转义符号的源串,sourceStr={},replaceAll={}", sourceStr, replaceAll);
		String sourceStr_1 = "$6.99";

		//没有预处理
		try{
			String replaceAll_nopre = matcher.replaceAll(sourceStr_1);
			logger.info("有转义字符、没有预算理-->sourceStr_1={},replaceAll_nopre={}", sourceStr_1,replaceAll_nopre);
		}catch(Exception e){
		   logger.error("error:有转义字符、没有预算理-->[e]={}",e,e);
		}

		//预处理
		String preSouceStr_1 = Matcher.quoteReplacement(sourceStr_1);
		logger.info("预处理结果,preSouceStr_1={}", preSouceStr_1);
		String replaceAll_1 = matcher.replaceAll(preSouceStr_1);
		logger.info("有转义符号的源串,sourceStr_1={},preSouceStr_1={},replaceAll_1={}",sourceStr_1, preSouceStr_1,replaceAll_1);
		
//		正确输出:
//
//		Price: [€6.99].
//		Price: [$6.99].
	}

	@Test
	public void replaceAllqut(){
	    String match = "\\\\";
		String target = "[]\\\\d";
		String source = "\\";
		String afterRe = source.replaceAll(match, target);
		logger.info("-->source={},afterRe={}", source,afterRe);
	}

	@Test
	public void testMatch(){
	    String regex="s.*?a";
		String source = "s0attttass1aa";
//		MatcherUtils.matchLog(regex, source);
	}

	@Test
	public void testAssert(){
		//这个相当于 位置。。。 找到字符串中的某个位置后面的 是aa。
		String regex="(?=aa)";
		String source = "aas0attaattass1=aa";
//		MatcherUtils.matchLog(regex, source);

	}

	@Test
	public void testAssert1(){
		String regex="(?=aa)0";//这样永远找不出来。  因为某个位置后为aa的。  后面必然不会是0
		String source = "aas0attaattass1=aa0";
//		MatcherUtils.matchLog(regex, source);
	}

	@Test
	public void testAssert2(){
		//某个位置前面为aa
		String regex="(?<=aa)";
		String source = "aas0attaattass1=aa0";
//		MatcherUtils.matchLog(regex, source);
	}

	@Test
	public void testAssert3(){
		//某个位置后面不是aa
		String regex="(?!aa)";
		String source = "aa2a3aa";
//		MatcherUtils.matchLog(regex, source);
	}
	@Test
	public void testAssert4(){
		//某个位置前面不是aa
		String regex="(?<!aa)";
		String source = "aa2a3aa";
//		MatcherUtils.matchLog(regex, source);
	}

	@Test
	public void testAssert5(){
		String regex="<(\\w*+).*?>(.*?)</\\1>";
		String source = "<a>0<d>3</d></a><b>1</b><c>2</c>";

		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(source);

		Map<String,Object> map = new HashMap<String,Object>();
		int matchCount = matcher.groupCount();
		while (matcher.find()){
			map.put(matcher.group(1),matcher.group(2));
		}
		logger.info("-->map={}", map);
		logger.info("-->matchCount={},regex={},source={}",matchCount,regex,source);
	}

	/**
	 * 正则反向引用
	 */
	@Test
	public void testAssert6(){
//		String s="99-3933";
//		boolean b=Pattern.matches("([\\d])\\1[-]([3])\\1\\2{2}", s);
//		System.out.println(b);

		String s="99-3399";
		boolean b=Pattern.matches("([\\d]{2})-33\\1", s);
		System.out.println(b);

	}

	/**
	 *
	 *
	 * @param a
	 * @return
     */
	public static String testMethod(String a){
		return null;
	}
}
