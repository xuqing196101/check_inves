package sums.service.oc;

import java.util.List;

import sums.model.oc.Complaint;

public interface ComplaintService {

	/**
	 * 查询所有投诉信息
	 * @param 
	 * @return
	 */
	List<Complaint> selectAllComplaint(Integer page); 
	/**
	 * 通过id查询投诉信息
	 */
	Complaint selectByPrimaryKey(String id);
	 
}
