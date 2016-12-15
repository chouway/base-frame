package com.base.simple.test.regex.pattern;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通俗来讲：
 * (?<=yy)xx  匹配xx，并且此时xx的左边等于yy;    “断言左等”
 * (?<!yy)xx  匹配xx,并且此时xx的左边不等于yy;    "断言左不等"
 * xx(?=yy)   匹配xx，并且此时xx的右边等于yy;    “断言右等”
 * xx(?!yy)	  匹配xx，并且此时xx的右边不等于yy;    “断言右不等”
 *
 * .*  默认贪婪模式   .*? 勉强模式 .*+ 独占模式
 *
 * (?:xx) 非捕获组
 * (?<groupName>xx)  捕获组 groupName jdk 1.7+
 * ()捕获组    组序列 0代表匹配项整体，之后按匹配的序列排序。
 *
 *
 * Matcher.quoteReplacement   转义处理
 *
 * ([\d]{2})-33\1    反向引用组
 *
 * Pattern.matches   整体匹配
 * Pattern.lookingAt 部份匹配
 * Pattern.find      部分匹配
 * matches:整个匹配，只有整个字符序列完全匹配成功，才返回True，否则返回False。但如果前部分匹配成功，将移动下次匹配的位置。
   lookingAt:部分匹配，总是从第一个字符进行匹配,匹配成功了不再继续匹配，匹配失败了,也不继续匹配。
   find:部分匹配，从当前位置开始匹配，找到一个匹配的子串，将移动下次匹配的位置。


 */
public class ZeroWidthRegexTest {
	private Logger LOGGER = LoggerFactory.getLogger(ZeroWidthRegexTest.class);
	
	/**
	 * 零宽度正预测先行断言 
	 * 
	 * 注意：先行断言的执行步骤是这样的先从要匹配的字符串中的最右端找到第一个 ing (也就是先行断言中的表达式)然后 再匹配其前面的表达式，

		若无法匹配则继续查找第二个 ing 再匹配第二个 ing 前面的字符串，若能匹配则匹配，符合正则的贪婪性。
	 * 
	 * @author zhouyw
	 * 2015年11月7日
	 */
	@Test
	public void test0(){
		//匹配单元 从左到右
		// 第一次读[a-z]+  贪婪正则   判断 是否结尾是ing是话取 前头[a-z]+
		// 然后继续直到结尾
		Pattern p = Pattern.compile("[a-z]+(?=ing)");
		//结果 cook    sing
		String strText = "cooking singing";

		// 结果 ： cookingsing
//		String strText = "cookingsinging";
		Matcher m = p.matcher(strText);
		while (m.find()) {
			int start = m.start();
			int end = m.end();
			LOGGER.info("matched form :start={},end={}",start,end);
			LOGGER.info("matcher form={}",strText.subSequence(start, end));
		}
	}//如果 patter (?=ing)[a-z]+  则判断
	/**
	 * 零宽度正回顾后发断言 

	 * 注意：后发断言跟先行断言恰恰相反 它的执行步骤是这样的：先从要匹配的字符串中的最左端找到第一个abc(也就是先行断言中的表达式)然后

		        再匹配其后面的表达式，若无法匹配则继续查找第二个 abc 再匹配第二个 abc 后面的字符串，若能匹配则匹配。
	 * 
	 * @author zhouyw
	 * 2015年11月7日
	 */
	@Test
	public void test1(){
		Pattern p = Pattern.compile("(?<=ing)[a-z]+");
		String strText = "cooking singing";
//		String strText = "cookingsinging";
		Matcher m = p.matcher(strText);
		while (m.find()) {
			int start = m.start();
			int end = m.end();
			LOGGER.info("matched form :start={},end={}",start,end);
			LOGGER.info("matcher form={}",strText.subSequence(start, end));
		}
	}
	/**
	 * 零宽度负预测先行断言      (?!exp) 匹配后面跟的不是exp的位置

	       例：\d{3}(?!\d)匹配三位数字，而且这三位数字的后面不能是数字
	 * 
	 * @author zhouyw
	 * 2015年11月7日
	 */
	@Test
	public void test2(){
		Pattern p = Pattern.compile("\\d{3}(?!\\d)");
		String strText = "12324aa222";
		Matcher m = p.matcher(strText);
		while (m.find()) {
			int start = m.start();
			int end = m.end();
			LOGGER.info("matched form :start={},end={}",start,end);
			LOGGER.info("matcher form={}",strText.subSequence(start, end));
		}
	}
	/**
	 * 零宽度负回顾后发断言      (?<!exp) 匹配前面不是exp的位置

	例：(?<![a-z])\d{7}匹配前面不是小写字母的七位数字。
	 * 
	 * @author zhouyw
	 * 2015年11月7日
	 */
	@Test
	public void test3(){
		Pattern p = Pattern.compile("(?<![a-z])\\d{7}");
		String strText = "a131313132A11111111111111B2222222";
		Matcher m = p.matcher(strText);
		while (m.find()) {
			int start = m.start();
			int end = m.end();
			LOGGER.info("matched form :start={},end={}",start,end);
			LOGGER.info("matcher form={}",strText.subSequence(start, end));
		}

	}

	@Test
	public void testFeFn(){
	    String source = "abc1abc5efg8hig9";
		String regex = "(?<!(abc|efg))\\d"; // 9   匹配数字 并且左边不是abc和efg
//		String regex = "(?<![abc])\\d";// 8   9    匹配数字  并且左边是 不是a 或且 不是b 或者不是c；
//		String regex = "(?<!abc)\\d";// 8   9    匹配数字  并且左边 不是 abc；
//		String regex = "(?<=(abc|efg))\\d";// 1 5 8  匹配数字 并且左边是abc或者是efg
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(source);
		while (m.find()) {
			int start = m.start();
			int end = m.end();
			LOGGER.info("matcher :start={},end={},form={}",start,end,m.group());
		}

	}
}
/**
 零宽断言用于查找在某些内容(但并不包括这些内容)之前或之后的东西，也就是说它们像 \b ^ $ \< \> 这样的锚定作用，用于指定一个位置，这个位置应该满足一定的条件(即断言)，因此它们也被称为零宽断言。

断言用来声明一个应该为真的事实。正则表达式中只有当断言为真时才会继续进行匹配。

1.零宽度正预测先行断言      (?=exp) 匹配exp前面的位置

例：[a-z]*(?=ing) 可以匹配 cooking 和 singing 中的 cook 与 sing 。

注意：先行断言的执行步骤是这样的先从要匹配的字符串中的最右端找到第一个 ing (也就是先行断言中的表达式)然后 再匹配其前面的表达式，

若无法匹配则继续查找第二个 ing 再匹配第二个 ing 前面的字符串，若能匹配则匹配，符合正则的贪婪性。

2.零宽度正回顾后发断言      (?<=exp) 匹配exp后面的位置

例：(?<=abc).* 可以匹配 abcdefgabc 中的 defgabc 而不是 abcdefg

注意：后发断言跟先行断言恰恰相反 它的执行步骤是这样的：先从要匹配的字符串中的最左端找到第一个abc(也就是先行断言中的表达式)然后

再匹配其后面的表达式，若无法匹配则继续查找第二个 abc 再匹配第二个 abc 后面的字符串，若能匹配则匹配。



3.零宽度负预测先行断言      (?!exp) 匹配后面跟的不是exp的位置

例：\d{3}(?!\d)匹配三位数字，而且这三位数字的后面不能是数字

4.零宽度负回顾后发断言      (?<!exp) 匹配前面不是exp的位置

例：(?<![a-z])\d{7}匹配前面不是小写字母的七位数字。

实例：

例1：\b\w+(?=ing\b)，匹配以ing结尾的单词的前面部分(除了ing以外的部分)，如查找I'm singing while you're dancing.时，它会匹配sing和danc。

例2：(?<=\bre)\w+\b  匹配以re开头的单词的后半部分(除了re以外的部分)，例如在查找reading a book时，它匹配ading。

例3：(?<=\s)\d+(?=\s) 匹配以空白符间隔的数字(再次强调，不包括这些空白符)。

例4：((?<=\d)\d{3})+\b，用它对1234567890进行查找时结果是234567890。

例5：\b\w*q(?!u)\w*\b 匹配包含后面不是字母u的字母q的单词

例6：\d{3}(?!\d)      匹配三位数字，而且这三位数字的后面不能是数字

例7：\b((?!abc)\w)+\b 匹配不包含连续字符串abc的单词。

例8：(?<=<(\w+)>).*(?=<\/\1>)匹配不包含属性的简单HTML标签内里的内容。
 */