package sums.service.oc.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.util.PropertiesUtil;
import sums.dao.oc.ComplaintMapper;
import sums.model.oc.Complaint;
import sums.service.oc.ComplaintService;

@Service
public class ComplaintServiceImpl implements ComplaintService {

	@Autowired
	private ComplaintMapper complaintMapper;

	@Override
	public List<Complaint> selectAllComplaint(Integer page, String Id) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,
				Integer.parseInt(config.getString("pageSize")));
		List<Complaint> list = complaintMapper.selectAllComplaint(Id);
		return list;
	}

	public Complaint selectByPrimaryKey(String id) {

		return complaintMapper.selectByPrimaryKey(id);

	}

	@Override
	public int updateByPrimaryKey(Complaint complaint) {
		return complaintMapper.updateByPrimaryKeySelective(complaint);
	}

	@Override
	public List<Complaint> selectComplaintByUserId(Complaint record,
			Integer page) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,
				Integer.parseInt(config.getString("pageSize")));
		return complaintMapper.selectComplaintByUserId(record);
	}

	@Override
	public void updateIsDeleteByPrimaryKey(String id) {
		complaintMapper.updateIsDeleteByPrimaryKey(id);
	}

	@Override
	public int insertSelective(Complaint record) {
		return complaintMapper.insertSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(Complaint record) {
		return complaintMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public Integer yzsc(String businessid, String typeId) {
		return complaintMapper.yzsc(businessid, typeId);

	}

}
