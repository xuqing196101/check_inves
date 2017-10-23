package extract.service.supplier;

import java.util.Map;

import extract.model.supplier.SupplierConType;
import extract.model.supplier.SupplierExtractCondition;

public interface AutoExtractSupplierService {


	  /**
	   * 自动抽取
	   * <简述> 
	   *
	   * @author Jia Chengxiang
	   * @dateTime 2017-10-12上午11:21:02
	   * @param condition
	   * @param conType
	   * @param i
	   * @return
	   */
	  Map<String, Object> autoExtract(SupplierExtractCondition condition,
			  SupplierConType conType, String projectInfo);

	  /**
	   * 返回通知结果
	   * <简述> 
	   *
	   * @author Jia Chengxiang
	   * @dateTime 2017-10-20下午2:45:33
	   * @param json
	   */
	  void receiveVoiceResult(String json);
	
	  /**
	   * 点击自动抽取 将项目信息，抽取条件从内网导出
	   * <简述> 
	   *
	   * @author Jia Chengxiang
	   * @dateTime 2017-10-20下午6:09:19
	   * @return
	   */
	  Map<String, Object> exportExtractInfo(SupplierExtractCondition condition,
			  SupplierConType conType, String projectInfo);

}
