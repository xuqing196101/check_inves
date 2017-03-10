package bss.service.ob.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import bss.dao.ob.OBProductInfoMapper;
import bss.dao.ob.OBProjectResultMapper;
import bss.dao.ob.OBSupplierMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import ses.util.PropertiesUtil;

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
		String json="";
		Gson gons=new Gson();
		if(list!=null){
			json=gons.toJson(list);
		}
		return json;

	}
	@Override
	public List<OBProduct> productList() {
		// TODO Auto-generated method stub
		return OBProductMapper.selectList();
	}
	@Override
	public String saveProject(OBProject obProject,String userid,String fileid) {
		// TODO Auto-generated method stub
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
			}
			return "200";
	}
    

	/***
     * 实现 查询产品信息
     * @author YangHongLiang
     * @param string
     */
	@Override
	public List<OBProductInfo> productInfoList(Map<String ,Object> map) {
		   PropertiesUtil config = new PropertiesUtil("config.properties");
	  		PageHelper.startPage((Integer) (map.get("page")),
	  		Integer.parseInt(config.getString("pageSize")));
		List<OBProductInfo> list=OBProductInfoMapper.selectByCreaterId(map);
		int supplieCount=0;
		if(list!=null){
		//便利 首先取出产品名称 产品id
          for(OBProductInfo info:list){
        	     //获取供应商
        	List<OBSupplier> slist= info.getObProduct().getObSupplierList();
        	// 存储合格供应商
        	List<OBSupplier> getSupplies=new ArrayList<OBSupplier>();
        	 // 获取竞价数据
        	OBProject obProject= info.getObProject();
        	if(obProject!=null){
        	OBProjectResult or=new OBProjectResult();
			or.setProductId(info.getProductId());
			or.setProjectId(obProject.getId());
			if(obProject.getStatus()==3){
			//获取成交供应商数量
		     supplieCount=OBProjectResultMapper.countByStatus(or);
			 }
        	}
			//赋值成交供应商数量
			info.setClosingSupplier(supplieCount);
			Date dt1=new Date();
			//便利供应商数据
			for(OBSupplier os:slist){
				//判断合格供应商
				if(DateUtils.compareDate(dt1, os.getCertValidPeriod())==1){
					getSupplies.add(os);
				}
			}
			//赋值合格供应商数量
			info.setQualifiedSupplier(getSupplies.size());
          }		
		}
		return list;
	}
}
