package extract.service.supplier;

import java.io.File;
import java.util.Date;
import java.util.Map;

import extract.model.supplier.SupplierExtractCondition;

public interface AutoExtractSupplierService {


  /**
   * 返回通知结果
   * <简述> 
   *
   * @author Jia Chengxiang
   * @dateTime 2017-10-20下午2:45:33
   * @param json
 * @return 
   */
  String receiveVoiceResult(String json);

  /**
   * 点击自动抽取 将项目信息，抽取条件从内网导出
   * <简述> 
   *
   * @author Jia Chengxiang
   * @dateTime 2017-10-20下午6:09:19
   * @return
   */
  String exportExtractInfo(SupplierExtractCondition condition,
		 String projectInfo);

  
  /**
   * 
   * <简述> 导出供应商抽取结果
   *
   * @author Jia Chengxiang
   * @dateTime 2017-10-24上午10:40:42
   * @param start
   * @param end
   * @param synchDate
   */
  void exportSupplierExtractResult(String start, String end, Date synchDate);

  /**
   * 
   * <简述> 导入供应商抽取结果
   *
   * @author Jia Chengxiang
   * @dateTime 2017-10-24上午10:41:32
   * @param file
   */
  void importSupplierExtractResult(File file);

  /**
   * 
   * <简述> 导入供应商抽取条件等信息
   *
   * @author Jia Chengxiang
   * @dateTime 2017-10-24上午10:42:04
   * @param file
   */
  void importSupplierExtract(File file);

  Map<String, Object> autoExtract(SupplierExtractCondition condition,
		String projectInfo);


  /**
   * 
   * <简述> 内网导出项目信息
   *
   * @author Jia Chengxiang
   * @dateTime 2017-11-6下午12:13:32
   * @return
   */
  Map<String, Object> exportExtractProjectInfo(String start, String end,
		Date synchDate);

  void selectAutoExtractProject(Date start, Date end);



}
