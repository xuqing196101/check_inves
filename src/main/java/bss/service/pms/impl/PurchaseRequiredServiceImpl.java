package bss.service.pms.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.util.PropertiesUtil;
import bss.dao.pms.PurchaseRequiredMapper;
import bss.model.pms.PurchaseRequired;
import bss.service.pms.PurchaseRequiredService;

import com.github.pagehelper.PageHelper;
/**
 * 
 * @Title: PurcharseRequiredServiceImpl
 * @Description: 采购需求计划业务实现类 
 * @author Li Xiaoxiao
 * @date  2016年9月12日,下午2:03:45
 *
 */
@Service
public class PurchaseRequiredServiceImpl implements PurchaseRequiredService{

	@Autowired
	private PurchaseRequiredMapper purchaseRequiredMapper;

	
	@Autowired
	private SqlSessionFactory sqlSessionFactory; 
	
	
	@Override
	public void add(PurchaseRequired purcharseRequired) {
		purchaseRequiredMapper.insertSelective(purcharseRequired);
		
	}

	@Override
	public void update(Map<String,Object> map) {
		purchaseRequiredMapper.history(map);
		
	}

	@Override
	public List<PurchaseRequired> query(PurchaseRequired purchaseRequired,Integer page) {
		List<PurchaseRequired> list = purchaseRequiredMapper.query(purchaseRequired);
		//PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(page,Integer.parseInt("30"));
		return list;
	}

	@Override
	public PurchaseRequired queryById(String id) {
		// TODO Auto-generated method stub
		return purchaseRequiredMapper.selectByPrimaryKey(id);
	}

	@Override
	public String queryByNo(String no) {
		List<PurchaseRequired> list = purchaseRequiredMapper.queryByNo(no);
		if(list.size()>0){
			return list.get(0).getHistoryStatus();
		}else{
			return null;
		}
		
	}

	@Override
	public void delete(String planNo) {
		purchaseRequiredMapper.delete(planNo);
		
	}

	@Override
	public void updateStatus(PurchaseRequired purchaseRequired) {
		purchaseRequiredMapper.updateStatus(purchaseRequired);
		
	}

	@Override
	public List<PurchaseRequired> getByMap(Map<String, Object> map) {
		List<PurchaseRequired> list = purchaseRequiredMapper.getByMap(map);	
		return list;
	}

	@Override
	public List<Map<String, Object>> statisticDepartment(Map<String, Object> map) {
		 
		return purchaseRequiredMapper.statisticDepartment(map);
	}

	@Override
	public List<PurchaseRequired> selectByParentId(Map<String, Object> map) {
		return purchaseRequiredMapper.selectByParentId(map);
	}

	@Override
	public List<PurchaseRequired> selectByParent(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return purchaseRequiredMapper.selectByParent(map);
	}
	public List<Map<String, Object>> statisticPurchaseMethod( Map<String, Object> map) {
		return purchaseRequiredMapper.statisticPurchaseMethod(map);
	}

	@Override
	public List<Map<String, Object>> statisticByMonth(Map<String, Object> map) {
		 
		return purchaseRequiredMapper.statisticByMonth(map);
	}

	@Override
	public List<Map<String, Object>> statisticOrg(Map<String, Object> map) {
		// 
		return purchaseRequiredMapper.statisticOrg(map);
	}

    @Override
    public void updateByPrimaryKeySelective(PurchaseRequired purchaseRequired) {
        purchaseRequiredMapper.updateByPrimaryKeySelective(purchaseRequired);
    }

	@Override
	public void batchAdd(List<PurchaseRequired> data) throws IOException {

	        SqlSession batchSqlSession = null;
	        try{
	            batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
	            int batchCount = 50;//每批commit的个数
	            for(int index = 0; index < data.size();index++){
	            	PurchaseRequired stu = data.get(index);
	                batchSqlSession.getMapper(PurchaseRequiredMapper.class).insert(stu);
	                if(index !=0 && index%batchCount == 0){
	                    batchSqlSession.commit();
	                }
	            }
	            batchSqlSession.commit();
	        }catch (Exception e){
	            e.printStackTrace();
	        }finally {
	            if(batchSqlSession != null){
	                batchSqlSession.close();
	            }
	        }
	        
	}

	
	@Override
	public List<PurchaseRequired> getByProjectStatus(String id,
			int projectStatus) {
		return purchaseRequiredMapper.getByProjectStatus(id, projectStatus);
	}

	
	@Override
	public void updateProjectStatus(String planNo) {
		purchaseRequiredMapper.updateProjectStatus(planNo);
	}
	
	
	@Override
	public void updateIdById(Map<String, Object> map) {
		purchaseRequiredMapper.updateIdById(map);
	}
	

}
