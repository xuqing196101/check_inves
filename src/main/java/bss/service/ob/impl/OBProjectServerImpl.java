package bss.service.ob.impl;

import java.math.BigDecimal;
import bss.dao.ob.OBRuleMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import bss.dao.ob.OBProductInfoMapper;
import bss.dao.ob.OBProjectResultMapper;
import bss.dao.ob.OBSupplierMapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import ses.util.PropertiesUtil;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;


import com.google.gson.Gson;

import common.utils.DateUtils;
import bss.dao.ob.OBProductMapper;
import bss.dao.ob.OBProjectMapper;
import bss.model.ob.OBProduct;
import bss.model.ob.OBProductInfo;
import bss.model.ob.OBProject;
import bss.model.ob.OBProjectResult;
import bss.model.ob.OBSupplier;
import bss.service.ob.OBProjectServer;
import bss.util.CheckUtil;
/**
 * 竞价信息管理接口实现
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
    private OBProjectResultMapper  OBProjectResultMapper;
    
    /** 竞价规则  **/
    @Autowired
    private OBRuleMapper OBRuleMapper;
	@Override
	public List<OBProject> list(OBProject op) {
		// TODO Auto-generated method stub
		return OBprojectMapper.selectPageList(op);
	}

	
	
	
	/**---------------竞价看板模块----------------**/
	
	@Override
	public List<OBProject> selectAllOBproject(Map<String, Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage((Integer) (map.get("page")),
				Integer.parseInt(config.getString("pageSize")));
		return OBprojectMapper.selectAllOBproject(map);
	}
	/**
	 * 实现获取竞价产品相关信息
	 */
	@Override
	public String getProduct() {
		// TODO Auto-generated method stub
		List<OBProduct> list= OBProductMapper.selectList();
		return JSON.toJSONString(list);

	}
	@Override
	public List<OBProduct> productList() {
		// TODO Auto-generated method stub
		return OBProductMapper.selectList();
	}
	/**
	 * 实现 保存 竞价信息
	 * @author YangHongLiang
	 */
	@Override
	public String saveProject(OBProject obProject,String userid,String fileid) {
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
	 * 封装  验证 竞价 json 
	 */
	private String toJsonProject(String name,String context){
		return "{\"attributeName\":\""+name+"\",\"show\":\""+context+"\"}";
	}
    

	/***
     * 实现 查询产品信息
     * @author YangHongLiang
     * @param string
     */
	@Override
	public List<OBProject> List(Map<String ,Object> map) {
		   PropertiesUtil config = new PropertiesUtil("config.properties");
	  		PageHelper.startPage((Integer) (map.get("page")),
	  		Integer.parseInt(config.getString("pageSize")));
	  		
	  		List<OBProject> list=OBprojectMapper.selectData(map);
	  		if(list!=null){
	  		for(OBProject obp:list){
	  		//获取产品集合
	        	List<OBProductInfo> slist= obp.getObProductInfo();
	        	//存储 产品id 集合
	        	List<String> pidList=new ArrayList<String>();
	        	if(slist!=null){
	        	for(OBProductInfo obinfo:slist){
	        		pidList.add(obinfo.getProductId());
	        	 }
	        	Map<String, Object> maps=new HashMap<String, Object>();
	        	maps.put("list", pidList);
	        	if(pidList!=null && pidList.size()>0){
	        	//获取合格供应商
	        		Integer qualifiedSupplier=OBSupplierMapper.countByProductId2(maps);
	        	obp.setQualifiedSupplier(qualifiedSupplier);
	        	if(obp.getStatus()==3){
	        		//获取 成交供应商 数量
	        		Integer closingSupplier=OBProjectResultMapper.countByStatus(maps);
	        	 obp.setClosingSupplier(closingSupplier);
	               }
	        	  }
	        	 }
	  			}
	  		}
		return list;
	}

	/**
	 *	根据竞价信息id查询
	 */
	@Override
	public OBProject selectByPrimaryKey(String id) {
		return OBprojectMapper.selectByPrimaryKey(id);
	}
}
