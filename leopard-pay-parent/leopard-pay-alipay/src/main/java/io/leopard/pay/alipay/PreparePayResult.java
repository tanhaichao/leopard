package io.leopard.pay.alipay;

/**
 * 预支付结果
 * 
 * @author 谭海潮
 *
 */
public class PreparePayResult {

	/**
	 * 支付准备的结果类型
	 */
	private PreparePayResultType type;

	/**
	 * 支付准备的结果:
	 * <ul>
	 * <li>如果结果类型是<b>错误</b> {@link PreparePayResultType#ERROR}, 则这个结果就是<b>错误信息</b></li>
	 * <li>如果结果类型是<b>表单</b> {@link PreparePayResultType#FORM}, 则这个结果就是<b>表单的html 文本</b></li>
	 * <li>如果结果类型是<b>QRCode</b> {@link PreparePayResultType#QR_CODE}, 则这个结果就是<b>二维码的文本</b></li>
	 * <li>如果结果类型是<b>APP支付</b> {@link PreparePayResultType#APP_PAY_STRING}, 则这个结果就是<b>支付文本</b></li>
	 * <li>如果结果类型是<b>REDIRECT</b> {@link PreparePayResultType#REDIRECT}, 则这个结果就是<b>跳转路径(跳转是不带上下文路径的)</b></li>
	 * </ul>
	 */
	private String result;

	public PreparePayResultType getType() {
		return type;
	}

	public void setType(PreparePayResultType type) {
		this.type = type;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
