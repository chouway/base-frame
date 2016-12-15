package com.base.simple.test.regex.pattern;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * http://blog.csdn.net/luoweifu/article/details/42759439
 * 
 * 探讨Java正则表达中Greedy、Reluctant、Possessive三种策略的区别。
 * 
 * Greedy(def 贪婪的)、Reluctant(?勉强的)和Possessive(+独占的)
 * 
 * 正则：模式串，用于匹配出一定规则的字符串
 * 
 * 关键理解: 匹配次数、读取顺序、回溯、
 * 贪婪： * 读取的 量取最大，非成功时依次减少，直到成功匹配或者读取的量减到最少 才结束；
 * 勉强： *？ 一次读取的量取最小，一直依次增加，直到读取到最后；   
 * 独占： *+ 一次读取的量取最大，并且不回退，直到读取到最后;
 * @author zhouyw
 * @date  2015年11月6日
 */
public class NumberAdjTest {
	
	private Logger LOGGER = LoggerFactory.getLogger(NumberAdjTest.class);
	/**
	 * 贪婪数量词 def
	 * 模式串：.*foo
	        查找串：xfooxxxxxxfoo
		 结果：matched form 0 to 13
	 * 
	 *  原理:
	 * 		Greedy数量词被称为“贪婪的”是因为匹配器被强制要求第一次尝试匹配时读入整个输入串，如果第一次尝试匹配失败，则从后往前逐个字符地回退并尝试再次匹配，直到匹配成功或没有字符可回退。
	 * 
	 * @author zhouyw
	 * 2015年11月6日
	 */
	@Test
	public void testGreedy() {
		Pattern p = Pattern.compile(".*foo");
		String strText = "xfooxxxxxxfoo";
		Matcher m = p.matcher(strText);
		while (m.find()) {
			int start = m.start();
			int end = m.end();
			LOGGER.info("matched form :start={},end={}",start,end);
			LOGGER.info("matcher form={}",strText.subSequence(start, end));
		}
	}
	/**
	 *勉强数量词
	 *模式串：.*？foo
	      查找串：xfooxxxxxxfoo
	          结果：matched form 0 to 4
      	    matched form 4 to 13
	 *
	 *  原理:
	 *		Reluctant采用与Greedy相反的方法，它从输入串的首(字符)位置开始，在一次尝试匹配查找中只勉强地读一个字符，直到尝试完整个字符串。
	 *
	 * @author zhouyw
	 * 2015年11月6日
	 */
	@Test
	public void testReluctant() {
		Pattern p = Pattern.compile(".*?foo");
		String strText = "xfooxxxxxxfoo";
		Matcher m = p.matcher(strText);
		while (m.find()) {
			int start = m.start();
			int end = m.end();
			LOGGER.info("matched form :start={},end={}",start,end);
			LOGGER.info("matcher form={}",strText.subSequence(start, end));
		}
	}
	
	/**
	 * 独占数据词
	 * 模式串：.*+foo
	        查找串：xfooxxxxxxfoo
		 结果：
	 		  未匹配到
	 * 
	 *  原理:
	 *  	Possessive数量词总是读入整个输入串，尝试一次(仅且一次)匹配成功，不像Greedy，Possessive ，Possessive数量词总是读入整个输入串从不回退，即便这样做也可能使整体匹配成功。
	 *  
	 * @author zhouyw
	 * 2015年11月6日
	 */
	@Test
	public void testPossessive() {
		
		//  .*+ 一次读到满 不回退了。。
		Pattern p = Pattern.compile(".*+foo");
		// .* 有回退
//		Pattern p = Pattern.compile(".*foo");
		String strText = "xfooaaxxxxxxfoo";
		Matcher m = p.matcher(strText);
		while (m.find()) {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
			int start = m.start();
			int end = m.end();
			LOGGER.info("matched form :start={},end={}",start,end);
			LOGGER.info("matcher form={}",strText.subSequence(start, end));
		}
	}
	
	/**
	 *		(ab)*+a 匹配 ababacd 时 可以匹配到 ababa ， 因为两个ab之后没有ab了
	 */
	
	@Test
	public void testPossessive2() {
		Pattern p = Pattern.compile("(ab)*+a");
		String strText = "aabacda";
		Matcher m = p.matcher(strText);
		while (m.find()) {
			int start = m.start();
			int end = m.end();
			LOGGER.info("matched form :start={},end={}",start,end);
			LOGGER.info("matcher form={}",strText.subSequence(start, end));
		}
	}

	
	
	/**
	 * Greedy 数量词
		X?
		X，一次或一次也没有
		X*
		X，零次或多次
		X+
		X，一次或多次
		X{n}
		X，恰好 n 次
		X{n,}
		X，至少 n 次
		X{n,m}
		X，至少 n 次，但是不超过 m 次
		 
		 
		Reluctant 数量词
		X??
		X，一次或一次也没有
		X*?
		X，零次或多次
		X+?
		X，一次或多次
		X{n}?
		X，恰好 n 次
		X{n,}?
		X，至少 n 次
		X{n,m}?
		X，至少 n 次，但是不超过 m 次
		 
		 
		Possessive 数量词
		X?+
		X，一次或一次也没有
		X*+
		X，零次或多次
		X++
		X，一次或多次
		X{n}+
		X，恰好 n 次
		X{n,}+
		X，至少 n 次
		X{n,m}+
		X，至少 n 次，但是不超过 m 次
	 */
}
