package ses.service.oms.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import common.constant.StaticVariables;
import ses.dao.oms.OrgnizationMapper;
import ses.model.oms.Orgnization;
import ses.service.oms.OrgnizationServiceI;
import ses.service.oms.PurChaseDepOrgService;
import ses.util.PropUtil;
import ses.util.PropertiesUtil;

@Service("orgnizationService")
public class OrgnizationServiceImpl implements OrgnizationServiceI{
	
    @Autowired
	private OrgnizationMapper orgniztionMapper;
    
    /**关联service */
    @Autowired
    private PurChaseDepOrgService purChaseDepOrgService;

	@Override
	public List<Orgnization> findOrgnizationList(HashMap<String, Object> map) {
		return orgniztionMapper.findOrgnizationList(map);
	}

	@Override
	public int saveOrgnization(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return orgniztionMapper.saveOrgnization(map);
	}

	@Override
	public int updateOrgnization(HashMap<String, Object> map) {
		return orgniztionMapper.updateOrgnization(map);
	}
	/**
	 * 多对多关联查询
	 */
	@Override
	public List<Orgnization> findPurchaseOrgList(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return orgniztionMapper.findPurchaseOrgList(map);
	}

	@Override
	public int delOrgnizationByid(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return orgniztionMapper.delOrgnizationByid(map);
	}

	@Override
	public int updateOrgnizationById(Orgnization orgnization) {
		// TODO Auto-generated method stub
		return orgniztionMapper.updateOrgnizationById(orgnization);
	}

	

	@Override
	public Orgnization findByCategoryId(String id) {
		
		return orgniztionMapper.findByCategoryId(id);
	}

	@Override
	public int updateByCategoryId(Orgnization orgnization) {
		// TODO Auto-generated method stub
		return orgniztionMapper.updateByCategoryId(orgnization);
	}

	@Override
	public List<Orgnization> selectByPrimaryKey(Map<String, Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage((Integer)(map.get("page")),Integer.parseInt(config.getString("pageSize")));
		return orgniztionMapper.selectByPrimaryKey(map);
	}

	@Override
	public List<Orgnization> findByName(Map<String, Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage((Integer)(map.get("page")),Integer.parseInt(config.getString("pageSize")));
		return orgniztionMapper.findByName(map);
	}
	
	/**
	 * 
	 * @see ses.service.oms.OrgnizationServiceI#getOrgByPrimaryKey(java.lang.String)
	 */
	@Override
    public Orgnization getOrgByPrimaryKey(String id) {
        if (StringUtils.isNotBlank(id)){
            return orgniztionMapper.findOrgByPrimaryKey(id);
        }
        return null;
    }

    /**
	 * 
	 * @see ses.service.oms.OrgnizationServiceI#getNeedOrg(java.util.Map)
	 */
    @Override
    public List<Orgnization> getNeedOrg(Map<String, Object> map) {
        
        PageHelper.startPage((Integer)(map.get("page")),Integer.parseInt(PropUtil.getProperty("pageSize")));
        return orgniztionMapper.findOrgPartByParam(map);
    }
    
    /**
     * 
     * @see ses.service.oms.OrgnizationServiceI#delOrg(java.lang.String)
     */
    @Override
    public String delOrg(String id) {
       
        if (StringUtils.isNotBlank(id)){
           try {
               orgniztionMapper.delOrgById(id);
               HashMap<String,Object> orgMap = new HashMap<String, Object>();
               orgMap.put("org_id", id);
               purChaseDepOrgService.delByOrgId(orgMap);
              return StaticVariables.SUCCESS;
           } catch (Exception e) {
            e.printStackTrace();
             return StaticVariables.FAILED;
           }
           
       }
        return StaticVariables.FAILED;
    }
	
	
	
	
	

}
