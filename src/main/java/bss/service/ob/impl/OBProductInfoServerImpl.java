package bss.service.ob.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.dao.ob.OBProductInfoMapper;
import bss.dao.ob.OBProductMapper;
import bss.model.ob.OBProduct;
import bss.model.ob.OBProductInfo;
import bss.service.ob.OBProductInfoServer;
@Service("OBProductInfo")
public class OBProductInfoServerImpl implements OBProductInfoServer {
	
   @Autowired
   private OBProductMapper OBProductMapper;
   
   @Autowired
   private OBProductInfoMapper obProductInfoMapper;
   /**
    * 实现保存竞价产品相关的信息 接口
    */
	@Override
	public void saveOBProductInfo(OBProduct product) {
		// TODO Auto-generated method stub
		OBProductMapper.insert(product);
	}
	@Override
	public int selectCount(String id) {
		return obProductInfoMapper.selectCount(id);
	}
	@Override
	public List<OBProductInfo> selectByProjectId(String projectId) {
		// TODO Auto-generated method stub
		return obProductInfoMapper.selectByProjectId(projectId);
	}
	@Override
	public List<OBProductInfo> getProductName(String projectId) {
		// TODO Auto-generated method stub
		return obProductInfoMapper.getProductName(projectId);
	}
}
