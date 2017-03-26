package bss.service.ob.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.model.bms.User;
import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;

import bss.dao.ob.OBProjectResultMapper;
import bss.dao.ob.OBProjectSupplierMapper;
import bss.model.ob.BidProductVo;
import bss.model.ob.ConfirmInfoVo;
import bss.model.ob.OBProduct;
import bss.model.ob.OBProject;
import bss.model.ob.OBProjectResult;
import bss.model.ob.OBProjectResultExample;
import bss.model.ob.SupplierProductVo;
import bss.service.ob.OBProjectResultService;
import bss.util.BiddingStateUtil;
import common.annotation.CurrentUser;
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
	
	@Autowired
	OBProjectSupplierMapper mapper;
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
	public ConfirmInfoVo selectInfoByPSId(OBProjectResult obProjectResult,String confirmStatus) {
		// TODO Auto-generated method stub
		ConfirmInfoVo confirmInfoVo = oBProjectResultMapper.selectInfoByPSId(obProjectResult);
		
		if(confirmInfoVo != null) {
			//根据confirmStatus当前的状态进行查询显示的是第一轮还是第二轮
			List<BidProductVo> productList = null;
			if("-1".equals(confirmStatus)) {
				productList = oBProjectResultMapper.selectProductBySupplierId(obProjectResult);
			} else if("1".equals(confirmStatus)) {
				Integer proportion2 = oBProjectResultMapper.selectProportionByProject(obProjectResult);
				proportion2 = 100 - proportion2;
				confirmInfoVo.setBidRatio(proportion2.toString());
				productList = oBProjectResultMapper.selectResultProductBySupplierId(obProjectResult);
			}
			//取到的只是一个竞价的开始时间，下面依次根据取到规则的时间段设置确认各个段的时间值
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(confirmInfoVo.getConfirmOvertime());
			gc.add(Calendar.MINUTE, confirmInfoVo.getQuoteTime().intValue());
			confirmInfoVo.setConfirmStarttime(gc.getTime());
			gc.add(Calendar.MINUTE, confirmInfoVo.getConfirmTime().intValue());
			confirmInfoVo.setConfirmOvertime(gc.getTime());
			gc.add(Calendar.MINUTE, confirmInfoVo.getConfirmTimeSecond().intValue());
			confirmInfoVo.setSecondOvertime(gc.getTime());
			
			confirmInfoVo.setBidProductList(productList);
		}
		return confirmInfoVo;
	}

	@Override
	public List<BidProductVo> selectProductBySupplierId(OBProjectResult obProjectResult) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
     * <p>Description 把此供应商的状态都改为0，表示放弃</p>
     * @author Ma Mingwei
     * @param obProjectResult封装的条件对象
     * @return 竞价管理-结果查询 
     */
	@Override
	public int updateBySupplierId(OBProjectResult record, String confirmStatus) {
		// TODO Auto-generated method stub
		if("1".equals(confirmStatus)) {
			OBProject obProject = new OBProject();
			obProject.setId(record.getProjectId());
			User user = new User();
			user.setTypeId(record.getSupplierId());
			String remark = "3";
			BiddingStateUtil.updateRemark(mapper, obProject, user, remark);
		} else if("-1".equals(confirmStatus)) {
			OBProject obProject = new OBProject();
			obProject.setId(record.getProjectId());
			User user = new User();
			user.setTypeId(record.getSupplierId());
			String remark = "3";
			BiddingStateUtil.updateRemark(mapper, obProject, user, remark);
		}
		return oBProjectResultMapper.updateBySupplierId(record);
	}

	/**
     * <p>Description 根据供应商Id、产品Id和竞价标题Id修改此条信息	SPPId supplierId、productId和projectId</p>
     * @author Ma Mingwei
     * @param obProjectResult封装的条件对象
     * @param confirmNum 当前处于第几轮的标识
     * @return 竞价管理-结果查询   修改了几条记录数
     */
	public int updateInfoBySPPIdList(User user,
			List<OBProjectResult> projectResultList,String confirmNum) {
		// TODO Auto-generated method stub
		int flag = 0;
		Date currentDate = new Date();
		
		for(int i = 0; i < projectResultList.size();i++) {
			projectResultList.get(i).setUpdatedAt(currentDate);
			//在第二轮接受时进行判断，
			
			if(i == 0) {
				oBProjectResultMapper.updateInfoBySPPId(projectResultList.get(i));
				flag++;
			} else if(i > 0) {
				oBProjectResultMapper.insert(projectResultList.get(i));
				flag++;
			}
			
			//上面修改过之后向数据库查找，查找是否所有的供应商已经此轮已经全接受并且占比总和100%
			if("1".equals(confirmNum)) {
				Integer getSumProportion = oBProjectResultMapper.selectProportionByProject(projectResultList.get(i));
				if(100 == getSumProportion) {
					String remark = "3";
					OBProject obProject = new OBProject();
					obProject.setId(projectResultList.get(i).getProjectId());
					//设置列表页面显示的操作状态判断值
					BiddingStateUtil.updateRemark(mapper, obProject, user, remark);
				}
			}
			if("2".equals(confirmNum)) {
				OBProject obProject = new OBProject();
				obProject.setId(projectResultList.get(i).getProjectId());
				String remark = "3";
				//设置列表页面显示的操作状态判断值
				BiddingStateUtil.updateRemark(mapper, obProject, user, remark);
			}
		}
		return flag;
	}

	/**
     * 根据标题id获取封装的供应商信息
     * @author Ma Mingwei
     */
	@Override
	public List<SupplierProductVo> selectInfoByPID(String projectID, String supplierID) {
		// TODO Auto-generated method stub
		OBProjectResult oBProjectResult = new OBProjectResult();
		oBProjectResult.setProjectId(projectID);
		oBProjectResult.setProjectId(supplierID);
		List<SupplierProductVo> spVo = oBProjectResultMapper.selectInfoByPID(projectID); 
		for (SupplierProductVo supplierProductVo : spVo) {
			List<BidProductVo> bidProductList = oBProjectResultMapper.selectProductBySupplierId(oBProjectResult);
		}
		return spVo;
	}

}
