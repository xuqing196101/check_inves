package ses.service.sms.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.dao.sms.AfterSaleSerMapper;
import bss.dao.cs.ContractRequiredMapper;
import bss.model.cs.ContractRequired;
import ses.model.sms.AfterSaleSer;
import ses.model.sms.SupplierAfterSaleDep;
import ses.model.sms.SupplierStars;
import ses.service.sms.AfterSaleSerService;
import ses.util.PropertiesUtil;
@Service(value="afterSaleSerService")
public class AfterSaleSerServiceImp implements AfterSaleSerService {
	/** 售后服务Mapper **/
    @Autowired
    private AfterSaleSerMapper afterSaleSerMapper;

	@Override
	public List<AfterSaleSer> queryBySupplierIdList(String supplierId,String goodsName,String code,String name, Map<String, Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage((Integer)(map.get("page")),Integer.parseInt(config.getString("pageSize")));
		return afterSaleSerMapper.queryBySupplierIdList(supplierId,goodsName,code,name);
	}

	@Override
	public List<AfterSaleSer> getAll(Map<String, Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage((Integer)(map.get("page")),Integer.parseInt(config.getString("pageSize")));
		return afterSaleSerMapper.queryByList();
	}

	@Override
	public void add(AfterSaleSer AfterSaleSer) {
		AfterSaleSer.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		afterSaleSerMapper.insertSelective(AfterSaleSer);
	}

	@Override
	public void update(AfterSaleSer AfterSaleSer) {
		AfterSaleSer.setUpdateAt(new Timestamp(System.currentTimeMillis()));
		afterSaleSerMapper.updateByPrimaryKeySelective(AfterSaleSer);
	}

	@Override
	public AfterSaleSer get(String id) {
		return afterSaleSerMapper.selectByPrimaryKey(id);
	}

	@Override
	public String queryPath(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String id) {
		afterSaleSerMapper.deleteByPrimaryKey(id);
		
	}

	@Override
	public void saveOrUpdateAfterSaleSer(AfterSaleSer AfterSaleSer) {
		 if (AfterSaleSer.getId() == null) {
	        	afterSaleSerMapper.insertSelective(AfterSaleSer);
	        } else {
	        	afterSaleSerMapper.updateByPrimaryKeySelective(AfterSaleSer);
	        }
		
	}
	/**
	 * 查询模板条数
	 */
	@Override
	public Integer queryByConut(String id) {
		return afterSaleSerMapper.queryByCount(id);
	}

	@Override
	public List<AfterSaleSer> findAfterSaleSer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateAfterSaleSer(AfterSaleSer AfterSaleSer) {
		// TODO Auto-generated method stub
		
	}

    @Override
    public List<AfterSaleSer> selectByAll(HashMap<String, Object> map) {
       
        return afterSaleSerMapper.selectByAll(map);
    }
    
}
