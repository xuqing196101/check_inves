package sums.service.oc;

import java.io.File;
import java.util.Date;
import java.util.List;

import sums.model.oc.Complaint;

public interface ComplaintService {

	/**
	 * 通过id查询投诉信息
	 */
	Complaint selectByPrimaryKey(String id);

	/**
	 * 
	 * Description: 根据投诉人id查询
	 * 
	 * @author zhang shubin
	 * @version 2017年3月17日
	 * @param @param userId
	 * @param @return
	 * @return List<Complaint>
	 * @exception
	 */
	List<Complaint> selectAllComplaint(Complaint record, Integer page);

	/**
	 * 
	 * Description: 删除 改变删除状态
	 * 
	 * @author zhang shubin
	 * @version 2017年3月18日
	 * @param @param id
	 * @return void
	 * @exception
	 */
	void updateIsDeleteByPrimaryKey(String id);

	/**
	 * 
	 * Description: 插入非空数据
	 * 
	 * @author zhang shubin
	 * @version 2017年3月18日
	 * @param @param record
	 * @param @return
	 * @return int
	 * @exception
	 */
	int insertSelective(Complaint record);

	/**
	 * 
	 * Description: 修改 非空数据
	 * 
	 * @author zhang shubin
	 * @version 2017年3月18日
	 * @param @param record
	 * @param @return
	 * @return int
	 * @exception
	 */
	int updateByPrimaryKeySelective(Complaint record);

	/**
	 * 
	 * Description: 验证文件上传
	 * 
	 * @author zhang shubin
	 * @version 2017年4月2日
	 * @param @param map
	 * @param @return
	 * @return Integer
	 * @exception
	 */
	Integer yzsc(String businessid, String typeId);
	
	/**
	 * 
	 * Description: 导出网上投诉信息
	 * 
	 * @author zhang shubin
	 * @data 2017年7月17日
	 * @param 
	 * @return
	 */
	boolean exportComplaintService(String start,String end,Date synchDate);
	
	/**
	 * 
	 * Description: 导入网上投诉信息
	 * 
	 * @author zhang shubin
	 * @data 2017年7月17日
	 * @param 
	 * @return
	 */
	boolean importProduct(File file);
}
