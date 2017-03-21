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

import ses.model.bms.User;
import ses.util.PropertiesUtil;
import bss.dao.ob.OBProductInfoMapper;
import bss.dao.ob.OBProductMapper;
import bss.dao.ob.OBProjectMapper;
import bss.dao.ob.OBProjectResultMapper;
import bss.dao.ob.OBProjectSupplierMapper;
import bss.dao.ob.OBResultsInfoMapper;
import bss.dao.ob.OBRuleMapper;
import bss.dao.ob.OBSupplierMapper;
import bss.model.ob.OBProduct;
import bss.model.ob.OBProductInfo;
import bss.model.ob.OBProject;
import bss.model.ob.OBProjectResult;
import bss.model.ob.OBProjectSupplier;
import bss.model.ob.OBResultsInfo;
import bss.model.ob.OBRule;
import bss.model.ob.OBSpecialDate;
import bss.model.ob.OBSupplier;
import bss.service.ob.OBProjectServer;
import bss.util.BiddingStateUtil;
import bss.util.CheckUtil;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;

import common.constant.Constant;
import common.utils.DateUtils;
import bss.dao.ob.OBSpecialDateMapper;

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

	/***
	 * 竞价信息和供应商关系表
	 */
	@Autowired
	private OBProjectSupplierMapper OBProjectSupplierMapper;
	/** 竞价规则 **/
	@Autowired
	private OBRuleMapper OBRuleMapper;
	/** 特殊日期 **/
	@Autowired
	private OBSpecialDateMapper OBSpecialDateMapper;
	@Autowired
	private OBResultsInfoMapper OBResultsInfoMapper;
	// 定义 竞价控制类型
	private int type = 2;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Autowired
	private OBProjectSupplierMapper obProjectSupplierMapper;

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
	 * @throws ParseException
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
		if (obProject.getName().length() > 101) {
			attribute = "nameErr";
			show = "竞价标题长度过长";
			return toJsonProject(attribute, show);
		}
		if (obProject.getDeliveryDeadline() == null) {
			attribute = "deliveryDeadlineErr";
			show = "交货日期不能为空";
			return toJsonProject(attribute, show);
		}
		if (StringUtils.isBlank(obProject.getDeliveryAddress())) {
			attribute = "deliveryAddressErr";
			show = "交货地点不能为空";
			return toJsonProject(attribute, show);
		}
		if (obProject.getDeliveryAddress().length() > 151) {
			attribute = "deliveryAddressErr";
			show = "交货地点长度过长";
			return toJsonProject(attribute, show);
		}
		if (obProject.getTradedSupplierCount() == null) {
			attribute = "tradedSupplierCountErr";
			show = "成交供应商数量不能为空";
			return toJsonProject(attribute, show);
		}

		Integer supplierCount = obProject.getTradedSupplierCount();
		if (supplierCount >= 6 && supplierCount < 1) {
			attribute = "tradedSupplierCountErr";
			show = "成交供应商数量不能超出6少于1";
			return toJsonProject(attribute, show);
		}
		if (obProject.getTransportFees() == null) {
			attribute = "transportFeesErr";
			show = "运杂费不能为空";
			return toJsonProject(attribute, show);
		}
		if (obProject.getTransportFees().toString().length() > 11) {
			attribute = "transportFeesErr";
			show = "运杂费长度过长";
			return toJsonProject(attribute, show);
		}
		if (StringUtils.isBlank(obProject.getDemandUnit())) {
			attribute = "demandUnitErr";
			show = "需求单位不能为空";
			return toJsonProject(attribute, show);
		}
		if (obProject.getDemandUnit().length() > 50) {
			attribute = "demandUnitErr";
			show = "需求单位长度过长";
			return toJsonProject(attribute, show);
		}
		if (StringUtils.isBlank(obProject.getContactName())) {
			attribute = "contactNameErr";
			show = "联系人不能为空";
			return toJsonProject(attribute, show);
		}
		if (obProject.getContactName().length() > 20) {
			attribute = "contactNameErr";
			show = "联系人长度过长";
			return toJsonProject(attribute, show);
		}
		if (StringUtils.isBlank(obProject.getContactTel())) {
			attribute = "contactTelErr";
			show = "联系人电话不能为空";
			return toJsonProject(attribute, show);
		}
		if (obProject.getContactTel().length() > 20) {
			attribute = "contactTelErr";
			show = "联系人电话长度过长";
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

		if (StringUtils.isBlank(obProject.getContent())) {
			attribute = "contentErr";
			show = "竞价内容不能为空";
			return toJsonProject(attribute, show);
		}
		if (obProject.getContent().length() > 1000) {
			attribute = "contentErr";
			show = "竞价内容长度过长";
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
		if (obProject.getStatus() == null) {
			obProject.setStatus(0);
		}

		// 默认规则
		OBRule obr = OBRuleMapper.selectByStatus();
		if (obr == null) {
			show = "发布竞价请先设置默认规则";
			return toJsonProject("attribute", show);
		}
		if (StringUtils.isBlank(obProject.getSupplieId())) {
			show = "竞价产品有误";
			return toJsonProject("attribute", show);
		}
		/*
		 * if(obProject.getSupplieId().size()<obr.getLeastSupplierNum()){ show =
		 * "提供当前产品的供应商不能少于"+obr.getLeastSupplierNum()+"家"; return
		 * toJsonProject("attribute", show); }
		 */

		// 获取间隔日
		int intervalWorkday = obr.getIntervalWorkday();
		// 获取竞价开始 时间忽略年月日
		Date definiteTime = obr.getDefiniteTime();
		// 获取报价时间
		int quoteTime = obr.getQuoteTime();
		// 确认时间
		int confirmTime = obr.getConfirmTime();
		// 第二轮 确认时间
		int confirmTimeSecond = obr.getConfirmTimeSecond();
		// 获取当前日期
		Date date = new Date();
		// 1.当前时间加上间隔日
		date = DateUtils.addDayDate(date, intervalWorkday);
		// date加上竞价开始时间 计算竞价开始时间 未加特殊节假日
		Date startdate = DateUtils.changeDate(date, definiteTime);

		// 封装map
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startDate", DateUtils.combinationDate(startdate));
		// 根据规则 和特殊日期 节假日 组合竞价开始时间
		List<OBSpecialDate> specialDateList = OBSpecialDateMapper
				.selectBySpecialDate(map);
		// 2.date时间加上 节假日
		if (specialDateList != null && specialDateList.size() > 0) {
			// 调用排除 特殊假期
			startdate = recursion(specialDateList,
					DateUtils.combinationDate(startdate), type,
					specialDateList.size());
			// 判断是否是特殊日期上班 如果不是排除 周末假日
			if (getType() != 3) {
				startdate = DateUtils.ignoreWeekend(startdate);
			}
		}
		// 获取竞价结束时间 竞价开始时间+ 报价
		Date endDate = DateUtils.getAddDate(startdate, quoteTime);

		// 生成ID
		String uuid = UUID.randomUUID().toString().toUpperCase()
				.replace("-", "");
		obProject.setCreatedAt(new Date());
		obProject.setCreaterId(userid);
		obProject.setAttachmentId(fileid);
		obProject.setStartTime(startdate);
		obProject.setEndTime(endDate);
		List<OBProductInfo> list = new ArrayList<OBProductInfo>();
		// 如果有 id 更新数据
		if (StringUtils.isNotBlank(obProject.getId())) {
			OBprojectMapper.updateByPrimaryKeySelective(obProject);
			// 组合 集合
			list = splitList(list, obProject, userid);
			for (OBProductInfo b : list) {
				OBProductInfoMapper.deleteByPrimaryKey(b.getId());
				OBProductInfoMapper.updateByPrimaryKeySelective(b);
			}
			OBProjectSupplierMapper.deleteByProjectId(obProject.getId());
			// 保存关系数据
			addSupplier(obProject, uuid, 1);
			if (obProject.getStatus() == 1) {
				OBRuleMapper.updateCount(obProject.getRuleId());
			}
		} else {
			obProject.setId(uuid);
			// 组合 集合
			list = splitList(list, obProject, userid);
			int i = OBprojectMapper.insertSelective(obProject);
			if (i > 0) {
				for (OBProductInfo b : list) {
					OBProductInfoMapper.insertSelective(b);
				}
				// 保存关系数据
				addSupplier(obProject, uuid, 0);
				if (obProject.getStatus() == 1) {
					OBRuleMapper.updateCount(obProject.getRuleId());
				}
			}
		}
		return toJsonProject("success", "执行成功");
	}

	/***
	 * 封装插入 竞价信息 供应商关系表
	 * 
	 * @param i类型区分
	 *            是否插入更新 时间
	 */
	private void addSupplier(OBProject obProject, String uuid, int i) {
		if (obProject.getSupplieId() != null) {
			OBProjectSupplier supplier = null;
			String[] list = obProject.getSupplieId().split(",");
			for (String supp : list) {
				supplier = new OBProjectSupplier();
				supplier.setId(UUID.randomUUID().toString().toUpperCase()
						.replace("-", ""));
				supplier.setCreatedAt(new Date());
				supplier.setProjectId(uuid);
				if (i == 1) {
					supplier.setUpdatedAt(new Date());
				}
				supplier.setSupplierId(supp);
				OBProjectSupplierMapper.insertSelective(supplier);
				System.out.println(supplier.toString());
			}
		}
	}

	/**
	 * 拆分 集合
	 * */
	private List<OBProductInfo> splitList(List<OBProductInfo> list,
			OBProject obProject, String userid) {
		OBProductInfo product = null;
		// 拆分数组
		List<String> productName = obProject.getProductName();
		for (int i = 0; i < productName.size(); i++) {
			String uid = UUID.randomUUID().toString().toUpperCase()
					.replace("-", "");
			product = new OBProductInfo();
			product.setId(uid);
			product.setProductId(productName.get(i));
			if (obProject.getProductMoney() == null) {
				product.setLimitedPrice(new BigDecimal(0));
			} else {
				product.setLimitedPrice(new BigDecimal(Double.valueOf(obProject
						.getProductMoney().get(i))));
			}
			if (obProject.getProductRemark() == null) {

			} else {
				product.setRemark(obProject.getProductRemark().get(i));
			}
			if (obProject.getProductCount() == null) {
				product.setPurchaseCount(0);
			} else {
				product.setPurchaseCount(Integer.valueOf(obProject
						.getProductCount().get(i)));
			}
			product.setProjectId(obProject.getId());
			product.setCreatedAt(new Date());
			product.setCreaterId(userid);
			list.add(product);
		}
		return list;

	}

	/**
	 * 计算忽略 特殊
	 * 
	 * @param specialDateList
	 *            特殊日期集合
	 * @param date
	 *            竞价时间
	 * @param type
	 *            控制分类
	 * @param size
	 *            集合的数量
	 * @author YangHongLiang
	 * @return date
	 * @throws
	 */
	private Date recursion(List<OBSpecialDate> specialDateList, Date start,
			int type, int size) {
		for (OBSpecialDate sd : specialDateList) {
			if (type == 2) {
				if (sd.getSpecialDate().getTime() == start.getTime()) {
					if (sd.getDateType().equals("0")) {// 放假
						type = 2;
						start = DateUtils.addDayDate(start, 1);
						break;
					} else if (sd.getDateType().equals("1")) {// 上班
						type = 3;
						start = sd.getSpecialDate();
						break;
					}
				}
			}
		}
		if (size > 0 && type == 2) {
			size = size - 1;
			recursion(specialDateList, start, type, size);
		}
		setType(type);
		return start;
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
				List<OBProductInfo> slist = OBProductInfoMapper
						.selectByProjectId(obp.getId());
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
				// 确认时间
				int confirmTime = obRule.getConfirmTime();
				// 第二轮 确认时间
				int confirmTimeSecond = obRule.getConfirmTimeSecond();

				/** 竞价状态 0：暂存 1已发布 2竞价中 3：竞价结束 4.流拍 5待确认 **/
				switch (op.getStatus()) {
				// 已发布
				case 1:
					// 判断 竞价结束时间 是否已到
					int compare = DateUtils.compareDate(new Date(),
							op.getStartTime());
					// 比较 竞价信息 如果等于1 那么是竞价 开始的时间
					if (compare > -1) {
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
					if (compareDate > -1) {
						// 说明 已发布 的竞价信息 已经超过 报价 时间
						List<OBResultsInfo> obresultsList = OBResultsInfoMapper
								.selectByProjectId(op.getId());
						// 判断 是否有竞价供应商
						if (obresultsList != null && obresultsList.size() > 0) {
							for (int i = 0; i < obresultsList.size(); i++) {
								Integer rk[] = null;
								int proportion = 0;
								if (op.getTradedSupplierCount() == 1) {
									rk = Constant.OB_PROJECT_ONE;
								} else if (op.getTradedSupplierCount() == 2) {
									rk = Constant.OB_PROJECT_TWO;
								} else if (op.getTradedSupplierCount() == 3) {
									rk = Constant.OB_PROJECT_THREE;
								} else if (op.getTradedSupplierCount() == 4) {
									rk = Constant.OB_PROJECT_FOUR;
								} else if (op.getTradedSupplierCount() == 5) {
									rk = Constant.OB_PROJECT_FIVE;
								} else if (op.getTradedSupplierCount() == 6) {
									rk = Constant.OB_PROJECT_SIX;
								}
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
								if (rk.toString().length() > i) {
									proportion = rk[i];
								} else {
									proportion = 0;
								}
								rsult.setProportion(String.valueOf(proportion));
								// 将 参与报价的供应商 插入到结果数据中
								OBProjectResultMapper.insertSelective(rsult);
							}
							// 根据状态竞价中 修改竞价结束时间
							OBProject upstatus = new OBProject();
							upstatus.setStatus(5);
							upstatus.setUpdatedAt(new Date());
							upstatus.setId(op.getId());
							upstatus.setEndTime(DateUtils.getAddDate(
									op.getEndTime(), confirmTime));
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
						// 根据竞价id 获取 是否 在规定的时间内 参与报价
					int compareDate1 = DateUtils.compareDate(new Date(),
							op.getEndTime());
					// 比较 竞价信息 如果等于1 那么是竞价确认结束的时间
					if (compareDate1 > -1) {
						// 说明 已发布 的竞价信息 已经超过 确认 时间 获取全部参与报价的供应商 数据
						List<OBProjectResult> prlist = OBProjectResultMapper
								.selectNotSuppler(op.getId());
						// 临时存储交易比例
						int temp = 0;
						if (prlist != null) {
							for (int i = 0; i < prlist.size(); i++) {
								String proportionString = prlist.get(i)
										.getProportion();
								if (proportionString == null
										|| proportionString == "") {
									proportionString = "0";
								}
								// 累加交易比例
								temp = temp + Integer.valueOf(proportionString);
								if (prlist.get(i).getStatus() == -1) {
									OBProjectResult rsult = new OBProjectResult();
									rsult.setStatus(0);
									rsult.setSupplierId(prlist.get(i)
											.getSupplierId());
									rsult.setProjectId(prlist.get(i)
											.getProjectId());
									rsult.setId(prlist.get(i).getId());
									// 更新 超过时间没有 确认报价的供应商
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
						// 数据不为空 说明 是第二轮
						if (round != null && round.size() > 0) {
							// 竞价结束
							upstatus.setStatus(3);
						} else {
							if (temp < 100) {
								// 竞价二轮 待确认
								upstatus.setStatus(5);
								// 竞价结束时间 加上 二轮结束时间
								upstatus.setEndTime(DateUtils.getAddDate(
										op.getEndTime(), confirmTimeSecond));
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
	public String updateProject(OBProject obProject, String userid,
			String fileid) {
		// TODO Auto-generated method stub
		return toJsonProject("success", "执行成功");
	}

	/**
	 * 实现 获取可编辑的竞价信息
	 * 
	 * @author Yanghongliang
	 * @return OBProject
	 */
	@Override
	public OBProject editOBProject(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return OBprojectMapper.selectTemporary(map);
	}

	/**
	 * 
	 * 实现获取 并集 供应商
	 * 
	 * @author YangHongLiang
	 * @exception
	 */
	@Override
	public java.util.List<OBSupplier> selecUniontSupplier(
			java.util.List<String> productID) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", productID);
		map.put("count", productID.size());
		return OBSupplierMapper.selecUniontSupplier(map);
	}

	/**
	 * 
	 * @Title: selectSupplierOBproject
	 * @Description: 供应商查看符合自己的竞价项目
	 * @author Easong
	 * @param @param map
	 * @param @return 设定文件
	 */
	@Override
	public List<OBProjectSupplier> selectSupplierOBproject(Map<String, Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage((Integer) (map.get("page")),
				Integer.parseInt(config.getString("pageSize")));
		// 获取此用户所对应的供应商
		User user = (User) map.get("user");
		if (user != null) {
			map.put("supplier_id", user.getTypeId());
		}
		// 查询符合自己需求的竞价项目
		List<OBProjectSupplier> obProjectList = obProjectSupplierMapper
				.selectSupplierOBprojectList(map);
		// 遍历得到竞价项目信息
		if (obProjectList != null && obProjectList.size() > 0) {
			for (OBProjectSupplier obProjectSupplier : obProjectList) {
				List<OBProject> obProject = obProjectSupplier
						.getObProjectList();
				if (obProject != null && obProject.size() > 0) {
					// 调用封装报价截止时间方法
					obProject.get(0).setQuoteEndTime(BiddingStateUtil.getQuoteEndTime(obProject.get(0)));
				}
			}
		}
		return obProjectList;
	}
}
