package bss.service.ob.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.model.bms.User;
import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;

import bss.dao.ob.OBProjectMapper;
import bss.dao.ob.OBProjectResultMapper;
import bss.dao.ob.OBProjectRuleMapper;
import bss.dao.ob.OBProjectSupplierMapper;
import bss.dao.ob.OBResultSubtabulationMapper;
import bss.dao.ob.OBResultsInfoMapper;
import bss.model.ob.BidProductVo;
import bss.model.ob.ConfirmInfoVo;
import bss.model.ob.OBProduct;
import bss.model.ob.OBProject;
import bss.model.ob.OBProjectResult;
import bss.model.ob.OBProjectResultExample;
import bss.model.ob.OBProjectRule;
import bss.model.ob.OBResultSubtabulation;
import bss.model.ob.OBResultsInfo;
import bss.model.ob.OBRule;
import bss.model.ob.SupplierProductVo;
import bss.service.ob.OBProjectResultService;
import bss.util.BiddingStateUtil;
import common.annotation.CurrentUser;
import common.utils.DateUtils;
import bss.dao.ob.OBRuleMapper;
/**
 * 
 * @author Ma Mingwei
 * @param <OBRuleMapper>
 * @description 主要负责存储竞价结果信息
 * @method 没注释的是自动继承过来
 *
 */
@Service("oBProjectResultService")
public class OBProjectResultServiceImpl implements OBProjectResultService {

	@Autowired
	private OBProjectResultMapper oBProjectResultMapper;
	
	@Autowired
	private OBProjectSupplierMapper mapper;
	@Autowired
	private OBRuleMapper OBRuleMapper; 
	@Autowired
	private OBProjectMapper oBProjectMapper;
	@Autowired
	private OBResultsInfoMapper OBResultsInfoMapper;
	
	@Autowired
	private OBResultSubtabulationMapper OBResultSubtabulationMapper;
	
	@Autowired
	private OBProjectRuleMapper OBProjectRuleMapper;
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
		return oBProjectResultMapper.selectByPrimaryKey(id);
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
     * <p>Description 根据竞价Id和供应商Id查询竞价结果  PSId  project supplier id</p>
     * @author Ma Mingwei
     * @param obProjectResult
     * @return 竞价管理-结果查询 页面信息封装对象
     */
	@Override
	public ConfirmInfoVo selectInfoByPSId(OBProjectResult obProjectResult,String confirmStatus) {
		// TODO Auto-generated method stub
		ConfirmInfoVo confirmInfoVo = oBProjectResultMapper.selectInfoByPSId(obProjectResult);
		
		/*if(confirmInfoVo != null) {
			//根据confirmStatus当前的状态进行查询显示的是第一轮还是第二轮
			List<BidProductVo> productList = null;
			if("-1".equals(confirmStatus)) {
				productList = oBProjectResultMapper.selectProductBySupplierId(obProjectResult);
			} else if("1".equals(confirmStatus)) {
				OBProjectResult item=oBProjectResultMapper.selectProportionByProject(obProjectResult);
				 if(item.getProportion()==null){
					 item.setProportion("0");
				 }
				Integer proportion2 =Integer.valueOf(item.getProportion());
				proportion2 = 100 - (proportion2);
				confirmInfoVo.setBidRatio(proportion2);
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
			
			confirmInfoVo.setBidProductList(productList);*/
		//}
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
     * @param -1 第一轮 1 第二轮
     * @return 竞价管理-结果查询 
     */
	@Override
	public boolean updateBySupplierId(String projectId,String supplierId, String confirmStatus,String projectResultId) {
		// TODO Auto-generated method stub
		boolean boo=false;
		//第一轮 放弃
		if("1".equals(confirmStatus)) {
			OBProjectResult obpro=new OBProjectResult();
			obpro.setProjectId(projectId);
			obpro.setSupplierId(supplierId);
			obpro.setStatus(0);
			obpro.setId(projectResultId);
			oBProjectResultMapper.updateByPrimaryKeySelective(obpro);
			
			OBProject obProject = new OBProject();
			obProject.setId(projectId);
			User user = new User();
			user.setTypeId(supplierId);
			String remark = "3";
			BiddingStateUtil.updateRemark(mapper, obProject, user, remark);
			boo=true;
			
		} else if("2".equals(confirmStatus)) {
			
			OBProjectRule obRule = OBProjectRuleMapper.selectByPrimaryKey(projectId);
			 if(obRule!=null){
			//第二轮 确认时间
			 int confirmTimeSecond=obRule.getConfirmTimeSecond();
			String uuid = UUID.randomUUID().toString().toUpperCase().replace("-", "");
			OBProjectResult result= oBProjectResultMapper.selectByPrimaryKey(projectResultId);
			result.setId(uuid);
			result.setStatus(0);
			result.setCreatedAt(new Date());
			oBProjectResultMapper.insertSelective(result);
			// 第二轮 放弃
			OBProject obProject = new OBProject();
			obProject.setEndTime(DateUtils.getAddDate(new Date(), confirmTimeSecond));
			obProject.setId(projectId);
			oBProjectMapper.updateByPrimaryKeySelective(obProject);
			User user = new User();
			user.setTypeId(supplierId);
			String remark = "32";
			BiddingStateUtil.updateRemark(mapper, obProject, user, remark);
			boo=true;
			 }
		}
		return boo;
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
			//先查找数据库表project_result，此供应商标题的数据信息的条数----其实没必要这么做，确定完，不可以再进来了，为了避免不必要的异常数据重复加上这个判断
			int countByPSID = oBProjectResultMapper.countByPSID(projectResultList.get(i));
			//在第二轮接受时进行判断，
			//是否接受的状态： -1默认		0表示不接受	1表示第一轮接受		2表示第二轮接受		第二轮放弃状态仍为1
			/**
			if(projectResultList.get(i).getStatus()==2 ){
				oBProjectResultMapper.insert(projectResultList.get(i));
				flag++;
			}else{
				oBProjectResultMapper.updateInfoBySPPId(projectResultList.get(i));
				flag++;
			}**/
			
			if("1".equals(confirmNum)) {
				if(countByPSID > 1) {
					oBProjectResultMapper.updateInfoBySPPId(projectResultList.get(i));
				} else {
					if(i == 0) {
						oBProjectResultMapper.updateInfoBySPPId(projectResultList.get(i));
						flag++;
					} else if(i > 0) {
						if(projectResultList.get(i).getStatus()==2 ){
							String uuid = UUID.randomUUID().toString().toUpperCase()
									.replace("-", "");
							projectResultList.get(i).setId(uuid);
							oBProjectResultMapper.insert(projectResultList.get(i));
						}
						
//						oBProjectResultMapper.insert(projectResultList.get(i));
						flag++;
					}
				}
				
				/*if(countByPSID > 1) {
					oBProjectResultMapper.updateInfoBySPPId(projectResultList.get(i));
				} else {
					if(i == 0) {
						oBProjectResultMapper.updateInfoBySPPId(projectResultList.get(i));
						flag++;
					} else if(i > 0) {
						String uuid = UUID.randomUUID().toString().toUpperCase()
								.replace("-", "");
						projectResultList.get(i).setId(uuid);
						oBProjectResultMapper.insert(projectResultList.get(i));
						flag++;
					}
				}*/
			} else {//这个else主要针对2状态
				oBProjectResultMapper.updateInfoBySPPId(projectResultList.get(i));
				flag++;
			}
			
			//上面修改过之后向数据库查找，查找是否所有的供应商已经此轮已经全接受并且占比总和100%
			if("1".equals(confirmNum)) {
				OBProjectResult item=oBProjectResultMapper.selectProportionByProject(projectResultList.get(i));
				 if(item.getProportion()==null){
					 item.setProportion("0");
				 }
				Integer getSumProportion = Integer.valueOf(item.getProportion());
				if(getSumProportion==null){
					getSumProportion=0;
				}
				String remark=null;
				//全部接受
				if(100 == getSumProportion) {
					 remark = "4";
				}
				//比例接受 
				if(100 > getSumProportion && getSumProportion>0){
					 remark = "5";
				}
				OBProject obProject = new OBProject();
				obProject.setId(projectResultList.get(i).getProjectId());
				//设置列表页面显示的操作状态判断值
				BiddingStateUtil.updateRemark(mapper, obProject, user, remark);
			}
			if("2".equals(confirmNum)) {
				OBProjectResult item=oBProjectResultMapper.selectProportionByProject(projectResultList.get(i));
				 if(item.getProportion()==null){
					 item.setProportion("0");
				 }
				Integer getSumProportion = Integer.valueOf(item.getProportion());
				String remark=null;
				//全部接受
				if(100 == getSumProportion){
					 remark = "42";
				}
				//比例接受
				if(100 > getSumProportion && getSumProportion>0){
					 remark = "52";
				}
				OBProject obProject = new OBProject();
				obProject.setId(projectResultList.get(i).getProjectId());
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

	/**
	 * 
	* @Title: findSupplierUnBidding 
	* @Description: 查询未中标的供应商
	* @author Easong
	* @param @param map
	* @param @return    设定文件 
	* @throws
	 */
	@Override
	public List<OBProjectResult> findSupplierUnBidding(Map<String, Object> map) {
		return oBProjectResultMapper.findSupplierUnBidding(map);
		
	}
    /***
     * 实现 供应商 竞价结果 页面数据
     */
	@Override
	public ConfirmInfoVo selectSupplierDate(String supplierId,
			String projectId,String status) {
		// TODO Auto-generated method stub
		//获取竞价结果 基础信息
		ConfirmInfoVo info= oBProjectResultMapper.getBasic(projectId, supplierId);
		if(info!=null){
			if(status.equals("2")){
			String	propo= oBProjectResultMapper.getProportionSum(projectId);
				if(propo==null){
					propo="0";
				}else{
					info.setSecondRatio(100-Integer.parseInt(propo));
				}
			}
			
		//产品 报价  信息
		List<OBResultsInfo> or=OBResultsInfoMapper.getProductInfo(projectId, supplierId);
		//第二轮 剩余 数量
		if(status.equals("2")){
		List<OBResultSubtabulation> list=OBResultSubtabulationMapper.getNotDealNumber(projectId);	
			if(or!=null&&or.size()>0){
				for(OBResultsInfo ifo: or){
					for(OBResultSubtabulation in:list){
						if(ifo.getProductId().equals(in.getProductId())){
							ifo.setSurplusNumber(in.getResultNumber().intValue());
							}
						}
					}
				}
		}
		// 获取 竞价金额 成交金额
		List<OBResultsInfo> obinfoList=OBResultsInfoMapper.getDealMoney(projectId);
		 //封装 竞价的成交 金额
		if(or!=null&&or.size()>0){
		for(OBResultsInfo ifo: or){
			for(OBResultsInfo in:obinfoList){
				if(ifo.getProductId().equals(in.getProductId())){
					ifo.setDealMoney(in.getMyOfferMoney());
				}
		     }
		    }
		  }
		OBProjectRule rule= OBProjectRuleMapper.selectByPrimaryKey(projectId);
		//第一轮确定时间
		int time= rule.getConfirmTime();
		//第二轮确定时间
		int secoud=rule.getConfirmTimeSecond();
		//报价信息
		int quote=rule.getQuoteTime();
		
		info.setOBResultsInfo(or);
		//取到的只是一个竞价的开始时间，下面依次根据取到规则的时间段设置确认各个段的时间值
		Date date=DateUtils.getAddDate(info.getConfirmStarttime(),quote);
		 info.setConfirmOvertime(DateUtils.getAddDate(date,time));
		 date= DateUtils.getAddDate(info.getConfirmOvertime(),secoud);
		 info.setSecondOvertime(date);
		}
		return info;
	}
    /**
     * 实现  接受 更新供应商结果 数据
     */
	@Override
	public String updateResult(User user,List<OBResultSubtabulation> projectResultList,String acceptNum) {
		// TODO Auto-generated method stub
		String reslt="";
		 if(projectResultList!=null){
			 String uuid = UUID.randomUUID().toString().toUpperCase()
					 .replace("-", "");
			 if(acceptNum.equals("1")&& projectResultList.get(0).getStatus()==1){
				//封装 更新 结果表
				 OBProjectResult obresulit=new OBProjectResult();
				 obresulit.setUpdatedAt(new Date());
				 obresulit.setId(projectResultList.get(0).getProjectResultId());
				 obresulit.setProportion(projectResultList.get(0).getProportion()+"");
				 obresulit.setStatus(projectResultList.get(0).getStatus());
				 oBProjectResultMapper.updateByPrimaryKeySelective(obresulit);
				 //更新状态 第一轮 接受
				 OBProject obProject = new OBProject();
				 obProject.setId(projectResultList.get(0).getProjectId());
				 User users = new User();
				 users.setTypeId(projectResultList.get(0).getSupplierId());
				 String remark = "4";
				 BiddingStateUtil.updateRemark(mapper, obProject, users, remark);
				 
			 }else{
				 
				 OBProjectRule obRule = OBProjectRuleMapper.selectByPrimaryKey(projectResultList.get(0).getProjectId());
				 if(obRule!=null){
				//第二轮 确认时间
				 int confirmTimeSecond=obRule.getConfirmTimeSecond();
				//封装 插入第二轮 结果表
				 OBProjectResult obresulit=new OBProjectResult();
				 obresulit.setId(uuid);
				 obresulit.setProjectId(projectResultList.get(0).getProjectId());
				 obresulit.setSupplierId(projectResultList.get(0).getSupplierId());
				 obresulit.setCreatedAt(new Date());
				 obresulit.setStatus(1);
				 obresulit.setRanking(projectResultList.get(0).getRanking());
				 obresulit.setProportion(projectResultList.get(0).getProportion()+"");
				 oBProjectResultMapper.insertSelective(obresulit);
				 
				 //更新状态 第二轮 接受
				 OBProject obProject = new OBProject();
				 obProject.setEndTime(DateUtils.getAddDate(new Date(), confirmTimeSecond));
				 obProject.setUpdatedAt(new Date());
				 obProject.setId(projectResultList.get(0).getProjectId());
				 oBProjectMapper.updateByPrimaryKeySelective(obProject);
				 User users = new User();
				 users.setTypeId(projectResultList.get(0).getSupplierId());
				 String remark = "42";
				 BiddingStateUtil.updateRemark(mapper, obProject, users, remark);
				 }
				 
				 
			 }
			 Date date=new Date();
			 for(OBResultSubtabulation or:projectResultList){
					 //第一轮
				 String uui = UUID.randomUUID().toString().toUpperCase()
						 .replace("-", "");
					 if(acceptNum.equals("1")){
						 if(or.getStatus()==1){
					 or.setCreatedAt(date);
					 or.setId(uui);
					 or.setTotalMoney(or.getDealMoney().multiply(or.getResultNumber()));
					OBResultSubtabulationMapper.insertSelective(or);
						 }
					 }else{
						 if(or.getStatus()==2){
						//第二轮
						 or.setId(uui);
						 or.setCreatedAt(date);
						 or.setProjectResultId(uuid);
						 or.setTotalMoney(or.getDealMoney().multiply(or.getResultNumber()));
						 OBResultSubtabulationMapper.insertSelective(or);
						 }
					 }
					 //插入附表 产品结果
			 }
			 reslt="成功";
		 }else{
			 reslt="参数错误！";
		 }
		return reslt;
	}

	@Override
	public String selectSupplierStatus(OBProjectResult oBProjectResult) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OBProjectResult> selByProjectId(String projectId) {
		return oBProjectResultMapper.selByProjectId(projectId);
	}
	/**
	 * 
	* @Title: findConfirmResult 
	* @Description: 查询 确定第一，第二轮确认结果
	* @author Easong
	* @param @param map
	* @param @return    设定文件 
	* @throws
	 */
	@Override
	public OBProjectResult findConfirmResult(Map<String, Object> map) {
		// 调用查询确认结果方法
		List<OBProjectResult> list = oBProjectResultMapper.findConfirmResult(map);
		OBProjectResult obProjectResult = null;
		if(list != null && list.size() > 0){
			obProjectResult = list.get(0);
			
		}
		return obProjectResult;
	}

	@Override
	public List<OBProjectResult> selResultByProjectId(String projectId) {
		return oBProjectResultMapper.selResultByProjectId(projectId);
	}

	@Override
	public List<OBProjectResult> selProportion(String projectId,
			String supplierId) {
		return oBProjectResultMapper.selProportion(projectId, supplierId);
	}

}
