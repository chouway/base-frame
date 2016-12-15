package com.base.simple.test.regex.pattern;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * http://mcj8089.iteye.com/blog/1183075
 * 
 * JAVA正则表达式-捕获组与非捕获组
 * 
 * 正则表达式中每个"()"内的部分算作一个捕获组，每个捕获组都有一个编号，从1,2...，编号0代表整个匹配到的内容。
        至于非捕获组，只需要将捕获组中"()"变为"(?:)"即可
 * 
 * 组命名 jdk1.7 支持
 * 
 * @author zhouyw
 * @date  2015年11月6日
 */
public class GroupRegexTest {
	
	private Logger LOGGER = LoggerFactory.getLogger(GroupRegexTest.class);
	
	@Test
	public void testCatchTotal(){
		 String text = "<textarea rows=\"20\" cols=\"70\">nexus maven repository index properties updating index central</textarea>";  
	        String reg = "<textarea.*?>.*?</textarea>";
	        Pattern p = Pattern.compile(reg);  
	        Matcher m = p.matcher(text);  
	        while (m.find()) {  

	           LOGGER.info("m.group={}",m.group());  
	         
	        }  
	}

	@Test
	public void testGroupCatch(){
		String text = "<textarea rows=\"20\" cols=\"70\">nexus maven repository index properties updating index central</textarea>";
		  //下面的正则表达式中共有四个捕获组：(<textarea.*?>)、(.*?)、(</textarea>)和整个匹配到的内容 
		String reg = "(<textarea.*?>)(.*?)(</textarea>)";
		Pattern p = Pattern.compile(reg);  
		Matcher m = p.matcher(text);  
		LOGGER.info("m.groupCount={}-->",m.groupCount());
		while (m.find()) {  
			LOGGER.info("m.group={}",m.group());  
			LOGGER.info("m.group0 eqaul group={}",m.group(0).equals(m.group()));  
			LOGGER.info("m.group1={}",m.group(1));  
			LOGGER.info("m.group2={}",m.group(2));  
			LOGGER.info("m.group3={}",m.group(3));  
//			LOGGER.info("m.group4={}",m.group(4)); java.lang.IndexOutOfBoundsException: No group 4
		}  
	}
	
	@Test
	public void testGroupNotCatch(){
		String text = "<textarea rows=\"20\" cols=\"70\">nexus maven repository index properties updating index central</textarea>";  
        // 下面的正则表达式中共有二个捕获组：(.*?)和整个匹配到的内容，两个非捕获组:(?:</textarea>)和(?:<textarea.*?>)   
        String reg = "(?:<textarea.*?>)(.*?)(?:</textarea>)";
        Pattern p = Pattern.compile(reg);  
        Matcher m = p.matcher(text);
		LOGGER.info("m.groupCount={}-->",m.groupCount());
        while (m.find()) {  
        	LOGGER.info("m.group0={}",m.group(0));  
			LOGGER.info("m.group1={}",m.group(1));  
        }  
	}
	/**
	 * 组命名简便的写法 获取data   jdk1.7 以上才支持
	 * @author zhouyw
	 * 2015年11月7日
	 */
	@Test
	public void testGroupCatch_simple(){
		String text = "<textarea rows=\"20\" cols=\"70\">nexus maven repository index properties updating index central</textarea>";  
		// 下面的正则表达式中共有二个捕获组：(.*?)和整个匹配到的内容，两个非捕获组:(?:</textarea>)和(?:<textarea.*?>)   
		String reg = "(<textarea.*?>)(?<data>.*?)(</textarea>)";
		
		// 比较  	String reg = "(<textarea.*?>)(.*?)(</textarea>)";  
		Pattern p = Pattern.compile(reg);  
		Matcher m = p.matcher(text);  
		while (m.find()) {  
			LOGGER.info("m.group(data)={}",m.group("data"));  
		}  
	}


	@Test
	public void testGroupCatch_simple2(){
		String text = "bb";  
		// 下面的正则表达式中共有二个捕获组：(.*?)和整个匹配到的内容，两个非捕获组:(?:</textarea>)和(?:<textarea.*?>)   
		String reg = "(?<data>bb)";
		
		// 比较  	String reg = "(<textarea.*?>)(.*?)(</textarea>)";  
		Pattern p = Pattern.compile(reg);  
		Matcher m = p.matcher(text);  
		while (m.find()) {  
			LOGGER.info("m.group(data)={}",m.group("data"));  
		}  
	}
	
	/**
	 * 正则表达式之非捕获组
		有什么用
		
		非捕获类，在正则很简单时，没什么用，只有在正则中大量使用()时才有用
		
		详细说明-举例说明
		
		匹配2013-05-07，你可以用\d{4}-\d{2}-\d{2}，你也可以加个括号(\d{4})-(\d{2})-(\d{2})，意思是完全一样的，但加了括号，就意味括号里面的东西，你捕获到了，你可以再使用，这就是捕获组的概念
		
		上面这种情况，你是没必要加括号，但是，有时候你必不得已要加括号，比如说匹配1-100的数字，你会用^([1-9]?[0-9]|100)$，但是这个时候，会默认把括号里的东西捕获过来以供你下次使用，其实，你只是用着正则匹配而已，因此就造成了内存浪费，当正则复杂时，效率更加低下，因此才有了非捕获组，上面的就可以改写成^(?:[1-9]?[0-9]|100)$
		
		小结
		
		非捕获组跟匹配没什么关系，只是为了提高效率
	 */
	
}
