package bss.service.ob.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.ob.OBProjectResultMapper;
import bss.model.ob.OBProjectResult;
import bss.model.ob.OBProjectResultExample;
import bss.service.ob.OBProjectResultService;
/**
 * 
 * @author Ma Mingwei
 * @description 主要负责存储竞价结果信息
 * @method 没注释的是自动继承过来
 *
 */
@Service("oBProjectResultService")
public class OBProjectResultServiceImpl implements OBProjectResultService {

	@Autowired
	private OBProjectResultMapper oBProjectResultService;
	
	@Override
	public int countByExample(OBProjectResultExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByExample(OBProjectResultExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(OBProjectResult record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertSelective(OBProjectResult record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<OBProjectResult> selectByExample(OBProjectResultExample example) {
		// TODO Auto-generated method stub
		return oBProjectResultService.selectByExample(example);
	}

	@Override
	public OBProjectResult selectByPrimaryKey(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByExampleSelective(OBProjectResult record, OBProjectResultExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByExample(OBProjectResult record, OBProjectResultExample example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKeySelective(OBProjectResult record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateByPrimaryKey(OBProjectResult record) {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
     * @description 根据供应商Id查询结果
     * @param id  供应商id
     * @return 结果列表
     */
	@Override
	public List<OBProjectResult> selectBySupplierId(String supplierId) {
		// TODO Auto-generated method stub
		return oBProjectResultService.selectBySupplierId(supplierId);
	}

}
