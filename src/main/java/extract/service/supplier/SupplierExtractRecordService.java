/**
 * 
 */
package extract.service.supplier;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;

import bss.model.ppms.Project;

import ses.model.bms.User;
import ses.model.sms.Supplier;
import extract.model.supplier.SupplierExtractProjectInfo;

/**
 * @Description: 供应商抽取记录
 *	 
 * @author Wang Wenshuai
 * @date 2016年9月18日下午2:02:59
 * @since  JDK 1.7
 */
public interface SupplierExtractRecordService {

  /**
   * @Description:插入记录
   *
   * @author Wang Wenshuai
   * @version 2016年9月27日 下午4:32:28  
   * @param @param record      
   * @return void
   */
  void insert(SupplierExtractProjectInfo record);

  /**
   * @Description:获取集合
   *
   * @author Wang Wenshuai
   * @version 2016年9月27日 下午4:33:36  
   * @param @return      
   * @return List<ExpExtractRecord>
   */
  List<SupplierExtractProjectInfo> listExtractRecord(SupplierExtractProjectInfo expExtractRecord,Integer pageNum);

  /**
   * 
   *〈简述〉修改
   *〈详细描述〉
   * @author Wang Wenshuai
   */
  void update(SupplierExtractProjectInfo extracts);


  SupplierExtractProjectInfo selectByPrimaryKey(String id);
  
  
  

  List<SupplierExtractProjectInfo> getList(int i, User user, SupplierExtractProjectInfo project);

  
  void saveOrUpdateProjectInfo(SupplierExtractProjectInfo projectInfo, User user);
  
  /**
   * @Description:插入记录
   *
   * @author Wang Wenshuai
   * @version 2016年9月27日 下午4:32:28  
   * @param @param record      
   * @return void
   */
  void insertProjectInfo(SupplierExtractProjectInfo record);

  ResponseEntity<byte[]> printRecord(String id, HttpServletRequest request, HttpServletResponse response) throws Exception;

  
  List<SupplierExtractProjectInfo> checkSoleProjectCdoe(String projectCode);
  
}
