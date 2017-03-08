package bss.service.ob.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import bss.dao.ob.OBProductMapper;
import bss.dao.ob.OBProjectMapper;
import bss.model.ob.OBProduct;
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
    
	@Override
	public List<OBProject> list(OBProject op) {
		// TODO Auto-generated method stub
		return OBprojectMapper.selectPageList(op);
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
}
