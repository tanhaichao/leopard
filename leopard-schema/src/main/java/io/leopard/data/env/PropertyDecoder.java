package io.leopard.data.env;

/**
 * 配置解密
 * 
 * @author 谭海潮
 *
 */
public interface PropertyDecoder {

	/**
	 * 一、在app.properties中，如果一行的内容没有#、=即表示为加密内容<br/>
	 * 二、Leopard会调用PropertyDecoder.decode(encode)方法进行解密<br/>
	 * 三、Leopard默认解密的publickey是"12345678901234567890123456789012"。注意密文是由AESUtil.encrypt("str", publickey)方法进行加密。<br/>
	 * 四、自定义PropertyDecoder实现<br/>
	 * 1、创建文件META-INF/services/io.leopard.data.env.PropertyDecoder<br/>
	 * 2、内容写入自定义实现类名.<br/>
	 * 五、默认加密内容(整个文件)<br/>
	 * String decode = PropertyDecoderImpl.encode("app2.properties");<br/>
	 * System.out.println(decode);<br/>
	 */

	// app.properties示例
	// abc=bcd
	// 618C019BBF7953917B29649708303E3A
	// #comment

	/**
	 * 解密
	 * 
	 * @param encode
	 * @return
	 */
	String decode(String encode);
}
