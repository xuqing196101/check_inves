package ses.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ses.model.sms.SupplierFinance;

/**
 * 
 * Description:供应商工具
 * 
 * @author YangHongLiang
 * @version 2017-6-15
 * @since JDK1.7
 */
public class SupplierToolUtil {
	 //产品 生成
	public static final String TOOL_PRODUCT="PRODUCT";
	//销售
	public static  final String TOOL_SALES="SALES";
	//服务
	public static final String TOOL_SERVICE="SERVICE";
	//目录 物资 id
	public static final String GOODS_ID="FC9528B2E74F4CB2A9E74735A8D6E90A";
    //目录 物资 物资生产 ID
	public static final String PRODUCT_ID="4AB6BDE2FA9C4FB587C3A3AB4AD646F6";
    //目录 物资 物资销售 ID
	public static final String SALES_ID="EE317E287DFF4F4A845EC2A3234BDE53";
	//工程
	public static final String TOOL_PROJECT="PROJECT";
	/**
	 * 
	 * Description:根据参数近year 判断获取要素权重
	 * 近一年 50%  近二年30% 近3 年 20%
	 * @author YangHongLiang
	 * @version 2017-6-15
	 * @param year
	 * @return
	 */
	public static Integer elementWeight(int year){
		Integer elementDate=null;
		switch (year) {
			case 0 :
				elementDate=50;
				break;
			case 1:
				elementDate=30;
				break;
			case 2:
				elementDate=20;
				break;
		}
		return elementDate;
	}
	/**
	 * 
	 * Description:供应商分级要素要值
	 * 
	 * @author YangHongLiang
	 * @version 2017-6-15
	 * @param supplierType
	 * @param dataType: foundDate成立时间   totalNetAssets近三年加权平均净资产   taking近三年加权平均营业收入
	 * @return
	 * 	-- PRODUCT 产品
	 *- 销售 SALES
	 *-- 服务 SERVICE
	 */
	public static Integer elementScore(String supplierType,String dataType){
		Integer elementDate=null;
		//产品 生产
		if(TOOL_PRODUCT.equals(supplierType)){
			switch (dataType) {
				case "foundDate" ://成立时间
					elementDate=20;
					break;
				case "totalNetAssets"://近三年加权平均净资产
				case "taking"://近三年加权平均营业收入
					elementDate=40;
				break;
			}
		}else if(TOOL_SALES.equals(supplierType) && TOOL_SERVICE.equals(supplierType)){
			//销售 or 服务
			switch (dataType) {
				case "foundDate" ://成立时间
					elementDate=20;
					break;
				case "totalNetAssets"://近三年加权平均净资产
					elementDate=30;
					break;
				case "taking"://近三年加权平均营业收入
					elementDate=50;
				break;
			}
		}
		
		return elementDate;
	} 
	/**
	 * 
	 * Description:根据 percnet 判断 供应商等级
	 * 
	 * @author YangHongLiang
	 * @version 2017-6-15
	 * @param supplierType 
	 * @param percnet
	 * @return
	 */
	public static String elementPercnet(String supplierType ,String percnet){
		String elementData=null;
		//产品 生产
		if(TOOL_PRODUCT.equals(supplierType)){
			if(percnet.matches("^\\b|10")){
				// 1 -10
				elementData="一级";
			}else if(percnet.matches("^1[1-9]|20")){
				// 11 -- 20
				elementData="二级";
			}else if(percnet.matches("^2[1-9]|30")){
				// 21 -- 30
				elementData="三级";
			}else if(percnet.matches("^3[1-9]|40")){
				//31 -- 40
				elementData="四级";
			}else if(percnet.matches("^4[1-9]|5[0-5]")){
				//41 -- 55
				elementData="五级";
			}else if(percnet.matches("^5[6-9]|6\\d|70")){
				//56 --70
				elementData="六级";
			}else if(percnet.matches("^7[1-9]|8[0-5]")){
				//71 -- 85
				elementData="七级";
			}else if(percnet.matches("^8[6-9]|9\\d|100")){
				//86 --100
				elementData="八级";
			}
		}else if(TOOL_SALES.equals(supplierType) && TOOL_SERVICE.equals(supplierType)){
			if(percnet.matches("^[1-9]|1\\d|20")){
				// 1 -20
				elementData="一级";
			}else if(percnet.matches("^2[1-9]|3\\d|40")){
				// 21 -- 40
				elementData="二级";
			}else if(percnet.matches("^4[1-9]|5\\d|60")){
				// 41 -- 60
				elementData="三级";
			}else if(percnet.matches("^6[1-9]|7\\d|80")){
				//61 -- 80
				elementData="四级";
			}else if(percnet.matches("^8[1-9]|9\\d|100")){
				//81 -- 100
				elementData="五级";
			}
		}
		return elementData;
	}
	 /**
     * 
     * Description:封装 获取单个供应商 要素数值（ 近三年净资产平均）
     * 近三年净资产=近三年净资产平均/该供应商目录下最高近三年净资产平均*要素分值
     *  总分100 物资供应商 成立时间：20，近三年加权平均净资产：40 近三年加权平均营业收入：40
     *  物资销售和服务类型 成立时间：20，近三年加权平均净资产：30 近三年加权平均营业收入：50
     * @author YangHongLiang
     * @version 2017-6-15
     * @param finance 单个供应商的 近三年集合
     * @return 
     */
    public static BigDecimal elementNetAsset(List<SupplierFinance> finance){
    	//要素权重比例
    	BigDecimal elementWeight=null;
    	//近3年
    	BigDecimal divide=new BigDecimal(3);
    	SupplierFinance sf=null;
    	BigDecimal totalNetAssets=new BigDecimal(0);//净资产累加
    	for (int i=0;i<finance.size();i++) {
    		sf=finance.get(i);
    		//要素权重比例计算
    		elementWeight=new BigDecimal(elementWeight(i)).setScale(2,BigDecimal.ROUND_HALF_UP);
    		//净资产累加=净资产/要素权重
    		totalNetAssets=totalNetAssets.add(sf.getTotalNetAssets().multiply(elementWeight));
		}
    	//四舍五入 保留2位小数  三年净资产平均
    	totalNetAssets=totalNetAssets.divide(divide, 2, BigDecimal.ROUND_HALF_UP);
    	return totalNetAssets;
    }
    /**
     * 
     * Description:封装 获取单个供应商 要素数值（ 近三年加权平均营业收入）
     * 近三年加权平均营业收入=近三年加权平均营业收入/该供应商目录下最高近近三年加权平均营业收入*要素分值
     *  总分100 物资供应商 成立时间：20，近三年加权平均净资产：40 近三年加权平均营业收入：40
     *  物资销售和服务类型 成立时间：20，近三年加权平均净资产：30 近三年加权平均营业收入：50
     * @author YangHongLiang
     * @version 2017-6-15
     * @param finance 单个供应商的 近三年集合
     * @return 
     */
    public static BigDecimal elementTaking(List<SupplierFinance> finance){
    	//要素权重比例
    	BigDecimal elementWeight=null;
    	//近3年
    	BigDecimal divide=new BigDecimal(3);
    	SupplierFinance sf=null;
    	BigDecimal taking=new BigDecimal(0);//营业收入 累加
    	for (int i=0;i<finance.size();i++) {
    		sf=finance.get(i);
    		//要素权重比例计算
    		elementWeight=new BigDecimal(elementWeight(i)).setScale(2,BigDecimal.ROUND_HALF_UP);
    		//营业收入累加
    		taking=taking.add(sf.getTaking().multiply(elementWeight));
		}
    	//三年加权平均营业收入平均    四舍五入 保留2位小数
    	taking=taking.divide(divide, 2, BigDecimal.ROUND_HALF_UP);
    	return taking;
    }
	 /**
     * 
     * Description:封装 获取单个供应商 要素数值（成立时间）
     * 
     * @author YangHongLiang
     * @version 2017-6-15
     * @param supplier
     * @return
     */
    public static BigDecimal foundTimeFormat(Date foundTime){
    	BigDecimal bd=null;
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
    	bd=new BigDecimal(sdf.format(foundTime));
		return bd;
    }
}
