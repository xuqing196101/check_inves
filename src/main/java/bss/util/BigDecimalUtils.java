package bss.util;

import java.math.BigDecimal;
import java.util.List;

import bss.model.ob.OBProjectResult;
import bss.model.ob.OBResultSubtabulation;

/**
 * 
* @ClassName: BigDecimalUtils 
* @Description: 竞价金额换算工具类
* @author Easong
* @date 2017年4月7日 下午4:21:50 
*
 */
public class BigDecimalUtils {

	/**
	 * 
	* @Title: getSignalDecimalScale4 
	* @Description: 计算单个商品总价保留万元方法
	* @author Easong
	* @param @param bigDecimal
	* @param @param million
	* @param @return    设定文件 
	* @return BigDecimal    返回类型 
	* @throws
	 */
	public static BigDecimal getSignalDecimalScale4(BigDecimal bigDecimal,BigDecimal million){
		BigDecimal moneyBigDecimal = bigDecimal.divide(million,4,BigDecimal.ROUND_HALF_UP);
		return moneyBigDecimal;
	}
	
	/**
	 * 
	* @Title: getBigDecimalTOScale4 
	* @Description: 计算全部商品总价保留万元方法
	* @author Easong
	* @param @param totalCountPriceBigDecimal
	* @param @param million
	* @param @return    设定文件 
	* @return BigDecimal    返回类型 
	* @throws
	 */
	public static BigDecimal getBigDecimalTOScale4(BigDecimal totalCountPriceBigDecimal,BigDecimal million){
		// 保留四位小数
		BigDecimal bigDecimal = totalCountPriceBigDecimal.divide(million, 4, BigDecimal.ROUND_HALF_UP);
		return bigDecimal;
	}
	
	/**
	 * 
	* @Title: getTotalFigure 
	* @Description: 计算成交总价
	* @author Easong
	* @param @param findConfirmResult
	* @param @return    设定文件 
	* @return Double    返回类型 
	* @throws
	 */
	public static Double getTotalFigure(OBProjectResult findConfirmResult){
		//NumberFormat currency = NumberFormat.getNumberInstance();
		// 定义第一轮成交总价
		Double confirmFirstTotalFigure = 0.00;
		if(findConfirmResult != null){
			List<OBResultSubtabulation> obResultSubtabulationList = findConfirmResult.getObResultSubtabulation();
			if(obResultSubtabulationList != null && obResultSubtabulationList.size() > 0){
				for (OBResultSubtabulation obResultSubtabulation : obResultSubtabulationList) {
					// 获取单件商品的成交总价
					BigDecimal dealMoney = obResultSubtabulation.getTotalMoney();
					// 计算成交总价
					/**计算成交总价 = 单件商品的成交总价 相加**/
					/** 累加得到总计 **/
					confirmFirstTotalFigure = dealMoney.add(
							new BigDecimal(Double
									.toString(confirmFirstTotalFigure)))
							.doubleValue();
					
				}
			}
		}
		//String confirmFirstTotalFigureStr = currency.format(confirmFirstTotalFigure);
		return confirmFirstTotalFigure;
	}
}
