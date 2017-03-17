package bss.service.ob.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.util.PropertiesUtil;
import bss.dao.ob.OBProductInfoMapper;
import bss.dao.ob.OBProductMapper;
import bss.dao.ob.OBProjectMapper;
import bss.dao.ob.OBProjectResultMapper;
import bss.dao.ob.OBResultsInfoMapper;
import bss.dao.ob.OBRuleMapper;
import bss.dao.ob.OBSupplierMapper;
import bss.model.ob.OBProduct;
import bss.model.ob.OBProductInfo;
import bss.model.ob.OBProject;
import bss.model.ob.OBProjectResult;
import bss.model.ob.OBResultsInfo;
import bss.model.ob.OBRule;
import bss.service.ob.OBProjectServer;
import bss.util.BiddingStateUtil;
import bss.util.CheckUtil;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;

import common.utils.DateUtils;

/**
 * 竞价信息管理接口实现
 * 
 * @author YangHongLiang
 * 
 */
@Service("OBProject")
public class OBProjectServerImpl implements OBProjectServer {
	@Autowired
	private OBProjectMapper OBprojectMapper;
	/** 竞价产品 **/
	@Autowired
	private OBProductMapper OBProductMapper;

	@Autowired
	private OBSupplierMapper OBSupplierMapper;

	@Autowired
	private OBProductInfoMapper OBProductInfoMapper;

	@Autowired
	private OBProjectResultMapper OBProjectResultMapper;

	/** 竞价规则 **/
	@Autowired
	private OBRuleMapper OBRuleMapper;

	@Autowired
	private OBResultsInfoMapper OBResultsInfoMapper;

	@Override
	public List<OBProject> list(OBProject op) {
		// TODO Auto-generated method stub
		return OBprojectMapper.selectPageList(op);
	}

	/** ---------------竞价看板模块---------------- **/

	/**
	 * 竞价信息列表查询
	 * 
	 * @author Easong
	 */
	@Override
	public List<OBProject> selectAllOBproject(Map<String, Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage((Integer) (map.get("page")),
				Integer.parseInt(config.getString("pageSize")));
		List<OBProject> list = OBprojectMapper.selectAllOBproject(map);
		java.util.List<OBProject> afterList = BiddingStateUtil.judgeState(OBprojectMapper,list);
		if (list != null) {
			for (OBProject obp : list) {
				// 获取产品集合
				List<OBProductInfo> slist = obp.getObProductInfo();
				// 存储 产品id 集合
				List<String> pidList = new ArrayList<String>();
				if (slist != null) {
					for (OBProductInfo obinfo : slist) {
						pidList.add(obinfo.getProductId());
					}
					Map<String, Object> maps = new HashMap<String, Object>();
					maps.put("list", pidList);
					if (pidList != null && pidList.size() > 0) {
						// 获取合格供应商
						Integer qualifiedSupplier = OBSupplierMapper
								.countByProductId2(maps);
						obp.setQualifiedSupplier(qualifiedSupplier);
						if (obp.getStatus() == 3) {
							// 获取 成交供应商 数量
							Integer closingSupplier = OBProjectResultMapper
									.countByStatus(maps);
							obp.setClosingSupplier(closingSupplier);
						}
					}
				}
			}
		}
		return afterList;
	}

	/**
	 * 实现获取竞价产品相关信息
	 */
	@Override
	public String getProduct() {
		// TODO Auto-generated method stub
		List<OBProduct> list = OBProductMapper.selectList();
		return JSON.toJSONString(list);

	}

	@Override
	public List<OBProduct> productList() {
		// TODO Auto-generated method stub
		return OBProductMapper.selectList();
	}

	/**
	 * 实现 保存 竞价信息
	 * 
	 * @author YangHongLiang
	 */
	@Override
	public String saveProject(OBProject obProject, String userid, String fileid) {
		// TODO Auto-generated method stub
		String attribute = "";
		String show = "";
		if (StringUtils.isBlank(obProject.getName())) {
			attribute = "nameErr";
			show = "竞价标题不能为空";
			return toJsonProject(attribute, show);
		}
		if (obProject.getDeliveryDeadline() == null) {
			attribute = "deliveryDeadlineErr";
			show = "交货截至日期不能为空";
			return toJsonProject(attribute, show);
		}
		if (StringUtils.isBlank(obProject.getDeliveryAddress())) {
			attribute = "deliveryAddressErr";
			show = "交货地点不能为空";
			return toJsonProject(attribute, show);
		}
		if (obProject.getTradedSupplierCount() == null) {
			attribute = "tradedSupplierCountErr";
			show = "成交供应商数量不能为空";
			return toJsonProject(attribute, show);
		}
		Integer supplierCount = obProject.getTradedSupplierCount();
		if (supplierCount > 4 && supplierCount < 1) {
			attribute = "tradedSupplierCountErr";
			show = "成交供应商数量不能超出4少于1";
			return toJsonProject(attribute, show);
		}
		if (obProject.getTransportFees() == null) {
			attribute = "transportFeesErr";
			show = "运杂费不能为空";
			return toJsonProject(attribute, show);
		}

		if (StringUtils.isBlank(obProject.getDemandUnit())) {
			attribute = "demandUnitErr";
			show = "需求单位不能为空";
			return toJsonProject(attribute, show);
		}
		if (StringUtils.isBlank(obProject.getContactName())) {
			attribute = "contactNameErr";
			show = "联系人不能为空";
			return toJsonProject(attribute, show);
		}
		if (StringUtils.isBlank(obProject.getContactTel())) {
			attribute = "contactTelErr";
			show = "联系人电话不能为空";
			return toJsonProject(attribute, show);
		}
		if (!StringUtils.isNumeric(obProject.getContactTel())) {
			attribute = "contactTelErr";
			show = "联系人电话只能是数字";
			return toJsonProject(attribute, show);
		}
		if (StringUtils.isBlank(obProject.getOrgId())) {
			attribute = "orgIdErr";
			show = "采购机构不能为空";
			return toJsonProject(attribute, show);
		}
		if (StringUtils.isBlank(obProject.getOrgContactTel())) {
			attribute = "orgContactTelErr";
			show = "采购联系人电话不能为空";
			return toJsonProject(attribute, show);
		}
		if (StringUtils.isBlank(obProject.getOrgContactName())) {
			attribute = "orgContactNameErr";
			show = "采购联系人不能为空";
			return toJsonProject(attribute, show);
		}
		if (obProject.getStartTime() == null) {
			attribute = "startTimeErr";
			show = "竞价开始时间不能为空";
			return toJsonProject(attribute, show);
		}
		if (obProject.getEndTime() == null) {
			attribute = "endTimeErr";
			show = "竞价结束时间不能为空";
			return toJsonProject(attribute, show);
		}
		if (StringUtils.isBlank(obProject.getContent())) {
			attribute = "contentErr";
			show = "竞价内容不能为空";
			return toJsonProject(attribute, show);
		}

		if (obProject.getProductName() == null) {
			attribute = "buttonErr";
			show = "竞价产品名称不能为空";
			return toJsonProject(attribute, show);
		}
		if (CheckUtil.isList(obProject.getProductName())) {
			attribute = "buttonErr";
			show = "竞价产品名称不能为空";
			return toJsonProject(attribute, show);
		}

		if (obProject.getProductMoney() == null) {
			attribute = "buttonErr";
			show = "竞价产品限价不能为空";
			return toJsonProject(attribute, show);
		}
		if (CheckUtil.isList(obProject.getProductMoney())) {
			attribute = "buttonErr";
			show = "竞价产品限价不能为空";
			return toJsonProject(attribute, show);
		}
		if (obProject.getProductCount() == null) {
			attribute = "buttonErr";
			show = "竞价产品数量不能为空";
			return toJsonProject(attribute, show);
		}
		if (CheckUtil.isList(obProject.getProductCount())) {
			attribute = "buttonErr";
			show = "竞价产品数量不能为空";
			return toJsonProject(attribute, show);
		}
		if (obProject.getProductRemark() == null) {
			attribute = "buttonErr";
			show = "竞价产品备注不能为空";
			return toJsonProject(attribute, show);
		}
		if (CheckUtil.isList(obProject.getProductRemark())) {
			attribute = "buttonErr";
			show = "竞价产品备注不能为空";
			return toJsonProject(attribute, show);
		}
		// 生成ID
		String uuid = UUID.randomUUID().toString().toUpperCase()
				.replace("-", "");
		obProject.setId(uuid);
		obProject.setCreatedAt(new Date());
		obProject.setCreaterId(userid);
		obProject.setAttachmentId(fileid);
		OBProductInfo product = null;
		List<OBProductInfo> list = new ArrayList<OBProductInfo>();
		// 拆分数组
		List<String> productName = obProject.getProductName();
		for (int i = 0; i < productName.size(); i++) {
			String uid = UUID.randomUUID().toString().toUpperCase()
					.replace("-", "");
			product = new OBProductInfo();
			product.setId(uid);
			product.setProductId(productName.get(i));
			product.setLimitedPrice(new BigDecimal(Double.valueOf(obProject
					.getProductMoney().get(i))));
			product.setRemark(obProject.getProductRemark().get(i));
			product.setPurchaseCount(Integer.valueOf(obProject
					.getProductCount().get(i)));
			product.setProjectId(uuid);
			product.setCreatedAt(new Date());
			product.setCreaterId(userid);
			list.add(product);
		}
		int i = OBprojectMapper.insertSelective(obProject);
		if (i > 0) {
			for (OBProductInfo b : list) {
				OBProductInfoMapper.insertSelective(b);
			}
			OBRuleMapper.updateCount(obProject.getRuleId());
		}
		return toJsonProject("success", "执行成功");
	}

	/***
	 * 封装 验证 竞价 json
	 */
	private String toJsonProject(String name, String context) {
		return "{\"attributeName\":\"" + name + "\",\"show\":\"" + context
				+ "\"}";
	}

	/***
	 * 实现 查询产品信息
	 * 
	 * @author YangHongLiang
	 * @param string
	 */
	@Override
	public List<OBProject> List(Map<String, Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage((Integer) (map.get("page")),
				Integer.parseInt(config.getString("pageSize")));

		List<OBProject> list = OBprojectMapper.selectData(map);
		if (list != null) {
			for (OBProject obp : list) {
				// 获取产品集合
				List<OBProductInfo> slist = obp.getObProductInfo();
				// 存储 产品id 集合
				List<String> pidList = new ArrayList<String>();
				if (slist != null) {
					for (OBProductInfo obinfo : slist) {
						pidList.add(obinfo.getProductId());
					}
					Map<String, Object> maps = new HashMap<String, Object>();
					maps.put("list", pidList);
					if (pidList != null && pidList.size() > 0) {
						// 获取合格供应商
						Integer qualifiedSupplier = OBSupplierMapper
								.countByProductId2(maps);
						obp.setQualifiedSupplier(qualifiedSupplier);
						if (obp.getStatus() == 3) {
							// 获取 成交供应商 数量
							Integer closingSupplier = OBProjectResultMapper
									.countByStatus(maps);
							obp.setClosingSupplier(closingSupplier);
						}
					}
				}
			}
		}
		return list;
	}

	/**
	 * 根据竞价信息id查询
	 */
	@Override
	public OBProject selectByPrimaryKey(String id) {
		return OBprojectMapper.selectByPrimaryKey(id);
	}

	/***
	 * 实现根据规则 更新状态
	 * 
	 * @author YangHongLiang
	 * 
	 */

	@Override
	public void changeStatus() {
		// TODO Auto-generated method stub
		// 1.获取 不是暂存 和结束 的竞价数据
		List<OBProject> getOBProject = OBprojectMapper.selectByStatus();
		if (getOBProject != null) {
			// 获取当前 默认规则
			OBRule obRule = OBRuleMapper.selectByStatus();
			for (OBProject op : getOBProject) {
				/** 竞价状态 0：暂存 1已发布 2竞价中 3：竞价结束 4.流拍 5待确认 **/
				switch (op.getStatus()) {
				case 1:// 已发布
					int compare = DateUtils.compareDate(new Date(),
							op.getStartTime());
					// 比较 竞价信息 如果等于1 那么是竞价 开始的时间
					if (compare == 1) {
						// 根据状态竞价中
						OBProject upstatus = new OBProject();
						upstatus.setStatus(2);
						upstatus.setId(op.getId());
						upstatus.setUpdatedAt(new Date());
						OBprojectMapper.updateByPrimaryKeySelective(upstatus);
					}
					break;
				case 2:// 竞价中
						// 根据竞价id 获取 是否 在规定的时间内 参与报价
					int compareDate = DateUtils.compareDate(new Date(),
							op.getEndTime());
					// 比较 竞价信息 如果等于1 那么是竞价 报价结束的时间
					if (compareDate == 1) {
						// 说明 已发布 的竞价信息 已经超过 报价 时间
						List<OBResultsInfo> obresultsList = OBResultsInfoMapper
								.selectByProjectId(op.getId());
						// 判读 是否有竞价供应商
						if (obresultsList != null && obresultsList.size() > 0) {
							for (int i = 0; i < obresultsList.size(); i++) {
								// 生成ID
								String uuid = UUID.randomUUID().toString()
										.toUpperCase().replace("-", "");
								OBProjectResult rsult = new OBProjectResult();
								rsult.setRanking(i + 1);
								rsult.setSupplierId(obresultsList.get(i)
										.getSupplierId());
								rsult.setProjectId(obresultsList.get(i)
										.getProjectId());
								rsult.setUpdatedAt(new Date());
								rsult.setId(uuid);
								rsult.setCreatedAt(new Date());
								OBProjectResultMapper.insertSelective(rsult);
							}
							// 根据状态竞价中 修改竞价结束时间
							OBProject upstatus = new OBProject();
							upstatus.setStatus(5);
							upstatus.setUpdatedAt(new Date());
							upstatus.setId(op.getId());
							OBprojectMapper
									.updateByPrimaryKeySelective(upstatus);
						} else {
							// 到规定时间 如果没有竞价供应商 修改竞状态 流拍
							OBProject upstatus = new OBProject();
							upstatus.setStatus(4);
							upstatus.setId(op.getId());
							upstatus.setUpdatedAt(new Date());
							OBprojectMapper
									.updateByPrimaryKeySelective(upstatus);
						}
					}
					break;
				case 5:// 待确认
						// 结束时间 加上 确定时间
					Date date = DateUtils.getAddDate(op.getEndTime(),
							obRule.getConfirmTime());
					// 在加上 二轮确认时间
					date = DateUtils.getAddDate(date, obRule.getConfirmTime());
					// 根据竞价id 获取 是否 在规定的时间内 参与报价
					int compareDate1 = DateUtils.compareDate(new Date(), date);
					// 比较 竞价信息 如果等于1 那么是竞价确认结束的时间
					if (compareDate1 == 1) {
						// 说明 已发布 的竞价信息 已经超过 确认 时间
						List<OBProjectResult> prlist = OBProjectResultMapper
								.selectNotSuppler(op.getId());
						// 临时存储交易比例
						int temp = 0;
						if (prlist != null) {
							for (int i = 0; i < prlist.size(); i++) {
								temp = temp
										+ Integer.valueOf(prlist.get(i)
												.getProportion());
								if (prlist.get(i).getStatus() != 0
										&& prlist.get(i).getStatus() != 1) {
									OBProjectResult rsult = new OBProjectResult();
									rsult.setStatus(0);
									rsult.setSupplierId(prlist.get(i)
											.getSupplierId());
									rsult.setProjectId(prlist.get(i)
											.getProjectId());
									rsult.setId(prlist.get(i).getId());
									OBProjectResultMapper
											.updateByPrimaryKeySelective(rsult);
								}
							}
						}
						// 根据状态竞价中 修改竞价结束时间
						OBProject upstatus = new OBProject();
						// 获取是否有二轮确认
						List<OBProjectResult> round = OBProjectResultMapper
								.selectSecondRound(op.getId());
						if(round!=null && round.size()>0){
							if(temp<100){
								//流拍
						    	 upstatus.setStatus(4);
							}else{
							 //竞价结束
					    	 upstatus.setStatus(3);
							}
						}else{
					     if(temp<100){
					    	 //竞价二轮 待确认
								upstatus.setStatus(2);
								// 获取 第一轮的供应商 数据
								if (prlist != null) {
									for (int i = 0; i < prlist.size(); i++) {
										// 生成ID
										String uuid = UUID.randomUUID()
												.toString().toUpperCase()
												.replace("-", "");
										OBProjectResult or = prlist.get(i);
										// 封装 对象
										OBProjectResult rsult = new OBProjectResult();
										rsult.setStatus(null);
										rsult.setSupplierId(or.getSupplierId());
										rsult.setProjectId(or.getProjectId());
										rsult.setId(uuid);
										rsult.setRanking(or.getRanking());
										rsult.setCreatedAt(new Date());
										rsult.setUpdatedAt(new Date());
										// 插入第二轮 供应商 数据
										OBProjectResultMapper
												.insertSelective(rsult);
									}
								}
							} else if (temp == 0) {
								// 流拍
								upstatus.setStatus(4);
							} else {
								// 竞价结束
								upstatus.setStatus(3);
							}
						}
						upstatus.setId(op.getId());
						upstatus.setUpdatedAt(new Date());
						OBprojectMapper.updateByPrimaryKeySelective(upstatus);
					}
					break;
				}
			}
		}
	}

	/**
	 * 更新竞价信息
	 * 
	 * @author YangHongLiang
	 */
	@Override
	public String updateProject(OBProject obProject, String userid, String fileid) {
		// TODO Auto-generated method stub
				 String attribute="";
				 String show="";
				if (StringUtils.isBlank(obProject.getName())) {
					attribute = "nameErr";
					show = "竞价标题不能为空";
					return toJsonProject(attribute, show);
				}
				if (obProject.getDeliveryDeadline()==null) {
					attribute = "deliveryDeadlineErr";
					show = "交货截至日期不能为空";
					return toJsonProject(attribute, show);
				}
				if (StringUtils.isBlank(obProject.getDeliveryAddress())) {
					attribute = "deliveryAddressErr";
					show = "交货地点不能为空";
					return toJsonProject(attribute, show);
				}
				if (obProject.getTradedSupplierCount()==null) {
					attribute = "tradedSupplierCountErr";
					show = "成交供应商数量不能为空";
					return toJsonProject(attribute, show);
				}
				Integer supplierCount = obProject.getTradedSupplierCount();
				if (supplierCount > 4 && supplierCount < 1) {
					attribute = "tradedSupplierCountErr";
					show = "成交供应商数量不能超出4少于1";
					return toJsonProject(attribute, show);
				}
				if (obProject.getTransportFees()==null) {
					attribute = "transportFeesErr";
					show = "运杂费不能为空";
					return toJsonProject(attribute, show);
				}

				if (StringUtils.isBlank(obProject.getDemandUnit())) {
					attribute = "demandUnitErr";
					show = "需求单位不能为空";
					return toJsonProject(attribute, show);
				}
				if (StringUtils.isBlank(obProject.getContactName())) {
					attribute = "contactNameErr";
					show = "联系人不能为空";
					return toJsonProject(attribute, show);
				}
				if (StringUtils.isBlank(obProject.getContactTel())) {
					attribute = "contactTelErr";
					show = "联系人电话不能为空";
					return toJsonProject(attribute, show);
				}
				if (!StringUtils.isNumeric(obProject.getContactTel())) {
					attribute = "contactTelErr";
					show = "联系人电话只能是数字";
					return toJsonProject(attribute, show);
				}
				if (StringUtils.isBlank(obProject.getOrgId())) {
					attribute = "orgIdErr";
					show = "采购机构不能为空";
					return toJsonProject(attribute, show);
				}
				if (StringUtils.isBlank(obProject.getOrgContactTel())) {
					attribute = "orgContactTelErr";
					show = "采购联系人电话不能为空";
					return toJsonProject(attribute, show);
				}
				if (StringUtils.isBlank(obProject.getOrgContactName())) {
					attribute = "orgContactNameErr";
					show = "采购联系人不能为空";
					return toJsonProject(attribute, show);
				}
				if (obProject.getStartTime()==null) {
					attribute = "startTimeErr";
					show = "竞价开始时间不能为空";
					return toJsonProject(attribute, show);
				}
				if (obProject.getEndTime()==null) {
					attribute = "endTimeErr";
					show = "竞价结束时间不能为空";
					return toJsonProject(attribute, show);
				}
				if (StringUtils.isBlank(obProject.getContent())) {
					attribute = "contentErr";
					show = "竞价内容不能为空";
					return toJsonProject(attribute, show);
				}

				if (obProject.getProductName()==null) {
					attribute = "buttonErr";
					show = "竞价产品名称不能为空";
					return toJsonProject(attribute, show);
				}
				if (CheckUtil.isList(obProject.getProductName())) {
					attribute = "buttonErr";
					show = "竞价产品名称不能为空";
					return toJsonProject(attribute, show);
				}

				if (obProject.getProductMoney()==null) {
					attribute = "buttonErr";
					show = "竞价产品限价不能为空";
					return toJsonProject(attribute, show);
				}
				if (CheckUtil.isList(obProject.getProductMoney())) {
					attribute = "buttonErr";
					show = "竞价产品限价不能为空";
					return toJsonProject(attribute, show);
				}
				if (obProject.getProductCount()==null) {
					attribute = "buttonErr";
					show = "竞价产品数量不能为空";
					return toJsonProject(attribute, show);
				}
				if (CheckUtil.isList(obProject.getProductCount())) {
					attribute = "buttonErr";
					show = "竞价产品数量不能为空";
					return toJsonProject(attribute, show);
				}
				if (obProject.getProductRemark()==null) {
					attribute = "buttonErr";
					show = "竞价产品备注不能为空";
					return toJsonProject(attribute, show);
				}
				if (CheckUtil.isList(obProject.getProductRemark())) {
					attribute = "buttonErr";
					show = "竞价产品备注不能为空";
					return toJsonProject(attribute, show);
				}
				//生成ID
			    String uuid = UUID.randomUUID().toString().toUpperCase().replace("-", "");
				obProject.setId(uuid);
				obProject.setCreatedAt(new Date());
			    obProject.setCreaterId(userid);
				obProject.setAttachmentId(fileid);
				OBProductInfo product=null;
				List<OBProductInfo> list=new ArrayList<OBProductInfo>();
				//拆分数组
					List<String> productName = obProject.getProductName();
					for (int i = 0; i < productName.size(); i++) {
						String uid=UUID.randomUUID().toString().toUpperCase().replace("-", "");
						product = new OBProductInfo();
						product.setId(uid);
						product.setProductId(productName.get(i));
						product.setLimitedPrice(new BigDecimal(Double.valueOf(obProject
								.getProductMoney().get(i))));
						product.setRemark(obProject.getProductRemark().get(i));
						product.setPurchaseCount(Integer.valueOf(obProject
								.getProductCount().get(i)));
						product.setProjectId(uuid);
						product.setCreatedAt(new Date());
						product.setCreaterId(userid);
						list.add(product);
					}
					 OBprojectMapper.updateByPrimaryKeySelective(obProject);
					/*if (obProject.getStatus()--- > 0) {
						for (OBProductInfo b : list) {
							OBProductInfoMapper.insertSelective(b);
						}
						OBRuleMapper.updateCount(obProject.getRuleId());
					}*/
					return toJsonProject("success", "执行成功");
	}

	 /**
     * 实现 获取可编辑的竞价信息
     * @author Yanghongliang
     * @return OBProject
     */
	@Override
	public OBProject editOBProject(Map<String,Object> map) {
		// TODO Auto-generated method stub
		return OBprojectMapper.selectTemporary(map);
	}
}
