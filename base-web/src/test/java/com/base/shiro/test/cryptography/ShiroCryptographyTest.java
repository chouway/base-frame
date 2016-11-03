package com.base.shiro.test.cryptography;

import com.base.common.CommonTest;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.*;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.junit.Assert;
import org.junit.Test;

import java.security.Key;

/**
 * ShiroCryptographyTest
 * @author zhouyw
 * @date 2016.11.03
 */
public class ShiroCryptographyTest extends CommonTest{

    @Test
    public void testBase64(){
        String str = "hello";
        String base64Encoded = Base64.encodeToString(str.getBytes());
        logger.info("-->str={},base64Encoded={}", str,base64Encoded);//-->str=hello,base64Encoded=aGVsbG8=
        String str2 = Base64.decodeToString(base64Encoded);
        Assert.assertEquals(str, str2);
    }

    @Test
    public void testHex(){// 16 进制字符串编码/解码
        String str = "hello";
        String hexEncoded = Hex.encodeToString(str.getBytes());
        logger.info("-->str={},hexEncoded={}", str,hexEncoded);//-->str=hello,hexEncoded=68656c6c6f
        String str2 = new String(Hex.decode(hexEncoded.getBytes()));
        Assert.assertEquals(str, str2);
    }

    @Test
    public void testMd5(){
        String str = "hello";
        String salt = "123";
        String md5 = new Md5Hash(str, salt).toString();//还可以转换为 toBase64()/toHex()
        logger.info("-->str={},salt={},md5={}", str,salt,md5);//-->str=hello,salt=123,md5=86fcb4c0551ea48ede7df5ed9626eee7
    }

    @Test
    public void testSha256(){
        String str = "hello";
        String salt = "123";
        String sha256 = new Sha256Hash(str, salt).toString();
        logger.info("-->str={},salt={},sha256={}", str,salt,sha256);//-->str=hello,salt=123,sha256=7dfe54ea69b2d07a597952e49374a1aebf3c10689444a83f0a084761c8a1c626
    }

    @Test
    public void testSha1(){
        String str = "hello";
        String salt = "123";
        //内部使用 MessageDigest
        String sha1 = new SimpleHash("SHA-1", str, salt).toString();
        logger.info("-->str={},salt={},sha1={}", str,salt,sha1);//-->str=hello,salt=123,sha1=f0a139408f7b134c66342e3d1cf4870a293c11c3
    }
    
    @Test
    public void testHashService(){
        String str = "hello";
        String salt = "123";
        DefaultHashService hashService = new DefaultHashService(); //默认算法 SHA-512
        hashService.setHashAlgorithmName("SHA-512");
        hashService.setPrivateSalt(new SimpleByteSource("123")); //私盐，默认无
        hashService.setGeneratePublicSalt(true);//是否生成公盐，默认 false
        hashService.setRandomNumberGenerator(new SecureRandomNumberGenerator());//用于生成公盐。默认就这个
        hashService.setHashIterations(1); //生成 Hash 值的迭代次数
        HashRequest request = new HashRequest.Builder()
                .setAlgorithmName("MD5").setSource(ByteSource.Util.bytes(str))
                .setSalt(ByteSource.Util.bytes(salt)).setIterations(2).build();
        String hex = hashService.computeHash(request).toHex();
        logger.info("-->str={},salt={},hex={}", str,salt,hex);//-->str=hello,salt=123,hex=fd2b413d4f8c465db16d51ce3e8dc18e
        /*
        1、首先创建一个 DefaultHashService，默认使用 SHA-512 算法；
        2、可以通过 hashAlgorithmName 属性修改算法；
        3、可以通过 privateSalt 设置一个私盐，其在散列时自动与用户传入的公盐混合产生一个新盐；
        4、可以通过 generatePublicSalt 属性在用户没有传入公盐的情况下是否生成公盐；
        5、可以设置 randomNumberGenerator 用于生成公盐；
        6、可以设置 hashIterations 属性来修改默认加密迭代次数；
        7、需要构建一个 HashRequest，传入算法、数据、公盐、迭代次数。
         */
    }
    
    @Test
    public void testGenerateRandomNum(){
        SecureRandomNumberGenerator randomNumberGenerator =
                new SecureRandomNumberGenerator();
        randomNumberGenerator.setSeed("123".getBytes());
        String hex = randomNumberGenerator.nextBytes().toHex();
        logger.info("-->hex={}", hex);//23ae809ddacaf96af0fd78ed04b6a265
    }
    @Test
    public void testAES(){
        AesCipherService aesCipherService = new AesCipherService();
        aesCipherService.setKeySize(128); //设置 key 长度
        //生成 key
        Key key = aesCipherService.generateNewKey();
        String text = "hello";
        //加密
        String encrptText =
                aesCipherService.encrypt(text.getBytes(), key.getEncoded()).toHex();
        //解密
        String text2 =
                new String(aesCipherService.decrypt(Hex.decode(encrptText), key.getEncoded()).getBytes());
        Assert.assertEquals(text, text2);
    }
}
