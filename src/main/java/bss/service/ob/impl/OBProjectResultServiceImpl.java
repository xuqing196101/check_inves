package bss.service.ob.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;

import bss.dao.ob.OBProjectResultMapper;
import bss.model.ob.BidProductVo;
import bss.model.ob.ConfirmInfoVo;
import bss.model.ob.OBProduct;
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
	private OBProjectResultMapper oBProjectResultMapper;
	
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
		return oBProjectResultMapper.selectByExample(example);
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
		return oBProjectResultMapper.selectBySupplierId(supplierId);
	}

	@Override
	public List<OBProjectResult> selectByProjectId(String supplierId,Integer page) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt(config.getString("pageSize")));
		List<OBProjectResult> list = oBProjectResultMapper.selectByProjectId(supplierId);
		return list;
	}

	/**
     * @author MaMingwei
     * @param oBProjectResult
     * @return 查找到的状态
     * @description 查找符合当前竞标的供应商在 竞价结果表 中的status
     */
	@Override
	public String selectSupplierStatus(OBProjectResult oBProjectResult) {
		
		return oBProjectResultMapper.selectSupplierStatus(oBProjectResult);
	}

	/**
     * <p>Description 根据竞价Id和供应商Id查询竞价结果  PSId  project supplier id</p>
     * @author Ma Mingwei
     * @param obProjectResult
     * @return 竞价管理-结果查询 页面信息封装对象
     */
	@Override
	public ConfirmInfoVo selectInfoByPSId(OBProjectResult obProjectResult) {
		// TODO Auto-generated method stub
		ConfirmInfoVo confirmInfoVo = oBProjectResultMapper.selectInfoByPSId(obProjectResult);
		List<BidProductVo> productList = oBProjectResultMapper.selectProductBySupplierId(obProjectResult);
		if(confirmInfoVo != null) {
			confirmInfoVo.setBidProductList(productList);
		}
		return oBProjectResultMapper.selectInfoByPSId(obProjectResult);
	}

	@Override
	public List<BidProductVo> selectProductBySupplierId(OBProjectResult obProjectResult) {
		// TODO Auto-generated method stub
		return null;
	}

}