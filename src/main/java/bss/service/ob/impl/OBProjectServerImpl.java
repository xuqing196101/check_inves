package bss.service.ob.impl;

import java.util.List;
import java.util.Map;
import bss.dao.ob.OBProductInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;


import com.google.gson.Gson;

import bss.dao.ob.OBProductMapper;

import bss.dao.ob.OBProjectMapper;
import bss.model.ob.OBProduct;
import bss.model.ob.OBProductInfo;
import bss.model.ob.OBProject;
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
    private OBProductInfoMapper OBProductInfoMapper;
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
	public void saveProject(OBProject project, List<OBProductInfo> pinfo) {
		// TODO Auto-generated method stub
		int i=OBprojectMapper.insert(project);
		if(i>0){
		for(OBProductInfo b:pinfo){
			OBProductInfoMapper.insert(b);
		 }
		}
	}
}
