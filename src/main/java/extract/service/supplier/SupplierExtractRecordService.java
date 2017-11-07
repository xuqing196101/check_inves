/**
 * 
 */
package extract.service.supplier;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;

import ses.model.bms.User;
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
   * 
   *〈简述〉修改
   *〈详细描述〉
   * @author Jia Chengxiang
   */
  void update(SupplierExtractProjectInfo extracts);

  /**
   * 查询是否存在项目记录
   * @param id
   * @return
   * @author Jia Chengxiang
   */
  SupplierExtractProjectInfo selectByPrimaryKey(String id);
  
  /**
   * 
   * <简述>  抽取记录列表 查询结束状态
   *
   * @author Jia Chengxiang
   * @param i
   * @param user
   * @param project
   * @return
   */
  List<SupplierExtractProjectInfo> getList(int i, User user, SupplierExtractProjectInfo project);

  /**
   * 
   * <简述>保存项目信息 
   *
   * @author Jia Chengxiang
   * @param projectInfo
   * @param user
 * @return 
   */
  int saveOrUpdateProjectInfo(SupplierExtractProjectInfo projectInfo);
  
  /**
   * @Description:插入记录
   *
   * @author Jia Chengxiang
   * @version 2016年9月27日 下午4:32:28  
   * @param @param record      
   * @return void
   */
  void insertProjectInfo(SupplierExtractProjectInfo record);

  /**
   * 
   * <简述>  下载记录表
   *
   * @author Jia Chengxiang
   * @dateTime 2017-9-20下午1:20:41
   * @param id
   * @param request
   * @param response
 * @param projectInto 
   * @return
   * @throws Exception
   */
  ResponseEntity<byte[]> printRecord(String id, HttpServletRequest request, HttpServletResponse response, String projectInto) throws Exception;

  /**
   * 
   * <简述>校验唯一项目编号 
   *
   * @author Jia Chengxiang
   * @dateTime 2017-9-20下午1:20:57
   * @param projectCode
   * @return
   */
  List<SupplierExtractProjectInfo> checkSoleProjectCdoe(String projectCode);

  /**
   * 查询自动抽取待通知项目集合
   * <简述> 
   *
   * @author Jia Chengxiang
   * @dateTime 2017-10-19下午7:12:51
   * @return
   */
  List<SupplierExtractProjectInfo> selectAutoExtractProject();

  /**
   * 
   * <简述>导出抽取记录，用于更新内网抽取状态 
   *
   * @author Jia Chengxiang
 * @param end 
 * @param start 
   * @dateTime 2017-11-6下午12:45:21
   * @return
   */
  List<SupplierExtractProjectInfo> selectRecordForExport(String start, String end);

}
