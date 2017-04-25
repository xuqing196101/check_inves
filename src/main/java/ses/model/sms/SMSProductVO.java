package ses.model.sms;

import java.util.List;

/**
 * 
* @ClassName: SMSProductVO 
* @Description: 封装参数VO
* @author Easong
* @date 2017年4月19日 下午7:15:39 
*
 */
public class SMSProductVO {

	// 产品的基本信息
	private SMSProductBasic productBasic;
	
	// 产品的描述信息
	private SMSProductInfo smsProductInfo;
	
	// 产品的参数信息--多个
	private List<SMSProductArguments> productArguments;

	public SMSProductBasic getProductBasic() {
		return productBasic;
	}

	public void setProductBasic(SMSProductBasic productBasic) {
		this.productBasic = productBasic;
	}

	public SMSProductInfo getSmsProductInfo() {
		return smsProductInfo;
	}

	public void setSmsProductInfo(SMSProductInfo smsProductInfo) {
		this.smsProductInfo = smsProductInfo;
	}

	public List<SMSProductArguments> getProductArguments() {
		return productArguments;
	}

	public void setProductArguments(List<SMSProductArguments> productArguments) {
		this.productArguments = productArguments;
	}
	
}
