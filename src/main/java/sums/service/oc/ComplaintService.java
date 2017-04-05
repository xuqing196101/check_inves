package sums.service.oc;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import sums.model.oc.Complaint;

public interface ComplaintService {

	/**
	 * 查询所有投诉信息
	 * 
	 * @param
	 * @return
	 */
	List<Complaint> selectAllComplaint(Integer page, String Id);

	/**
	 * 通过id查询投诉信息
	 */
	Complaint selectByPrimaryKey(String id);

	int updateByPrimaryKey(Complaint complaint);

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
	List<Complaint> selectComplaintByUserId(Complaint record, Integer page);

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
}
