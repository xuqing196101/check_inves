package bss.service.pms.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import common.utils.JdcgResult;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.oms.OrgnizationMapper;
import ses.dao.sms.SupplierMapper;
import ses.model.oms.Orgnization;
import ses.model.sms.Supplier;
import ses.util.PropUtil;
import bss.dao.pms.CollectPurchaseMapper;
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
@Service("purchaseRequiredService")
public class PurchaseRequiredServiceImpl implements PurchaseRequiredService{

	@Autowired
	private PurchaseRequiredMapper purchaseRequiredMapper;

	
	@Autowired
	private SqlSessionFactory sqlSessionFactory; 
	
	@Autowired
	private CollectPurchaseMapper collectPurchaseMapper;
	
	@Autowired
	private OrgnizationMapper orgnizationMapper;
	
	@Autowired
	private SupplierMapper supplierMapper;
	
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
	    PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("page.size.thirty")));
		List<PurchaseRequired> list = purchaseRequiredMapper.query(purchaseRequired);
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

	@Override
	public List<PurchaseRequired> seqAgain(String id) {
		List<String> list = collectPurchaseMapper.getNo(id);
		Map<String,Object> map=new HashMap<String,Object>(); 
		Set<String> set=new HashSet<String>();
//		List<String> depList=new ArrayList<String>();
		List<String> uq=new ArrayList<String>();
		List<PurchaseRequired>  prs=new ArrayList<PurchaseRequired >();
		Map<String,Object> mapAll=new HashMap<String,Object>();
		for(String no:list){
			map.put("isMaster", 1);
			map.put("planNo", no);
			List<PurchaseRequired> pr = purchaseRequiredMapper.getByMap(map);
			set.add(pr.get(0).getDepartment());
//			depList.add(pr.get(0).getDepartment());
		
			mapAll.put("planNo", no);
			List<PurchaseRequired> prList = purchaseRequiredMapper.getByMap(mapAll);
			prs.addAll(prList);
		}
		for(String str:set){
			uq.add(str);
		}
		for(PurchaseRequired p:prs){
			if(p.getIsMaster()==2){
				if(p.getDepartment().equals(uq.get(0))){
					
				}	
			}
			
			
		}
		
//		先查出所有的部门；然后去重；
//		然后重新遍历需求部门，如果，有多个存在就给序号重新复制
		//如果不存在就给序号加一
		
		
		return null;
	}

	@Override
	public List<PurchaseRequired> queryList(PurchaseRequired purchaseRequired) {
		List<PurchaseRequired> list = purchaseRequiredMapper.query(purchaseRequired);
		return list;
	}

	@Override
	public  Orgnization queryByName(String name) {
     Orgnization orgnization = orgnizationMapper.queryByName(name);
	 
		return orgnization;
	}

	@Override
	public List<Map<String, Object>> queryOrg(List<String> list) {
		// TODO Auto-generated method stub
		return purchaseRequiredMapper.queryOrg(list);
	}

	@Override
	public List<PurchaseRequired> queryUnique(PurchaseRequired purchaseRequired) {
		// TODO Auto-generated method stub
		return purchaseRequiredMapper.queryByUinuqe(purchaseRequired);
	}

	@Override
	public List<PurchaseRequired> getUnique(String unique) {
		// TODO Auto-generated method stub
		return purchaseRequiredMapper.getByUinuqeId(unique);
	}

	@Override
	public Integer getChilden(String children) {
		// TODO Auto-generated method stub
		return purchaseRequiredMapper.queryChilden(children);
	}

	@Override
	public List<PurchaseRequired> queryByAuthority(Map<String, Object> map,Integer page) {
	 PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("page.size.thirty")));
	 List<PurchaseRequired> list = purchaseRequiredMapper.getByDep(map);
		return list;
	}

	@Override
	public Orgnization queryByDepId(String id) {
		return orgnizationMapper.queryById(id);
	}

	@Override
	public void updateByUniqueId(String uniqueId) {
		 purchaseRequiredMapper.uddateByUniqueId(uniqueId);
		
	}

	@Override
	public List<Supplier> queryAllSupplier() {
		// TODO Auto-generated method stub
		return supplierMapper.queryAll();
	}
	
	
	 
	public Orgnization queryPur(String id) {
		return orgnizationMapper.queryPur(id);
	}

	@Override
	public List<String> getUniqueId(List<String> list) {
		// TODO Auto-generated method stub
		return purchaseRequiredMapper.getUniqueId(list);
	}

	
	public List<PurchaseRequired> queryListUniqueId(Map<String, Object> map) {
		PageHelper.startPage((Integer)map.get("page"),Integer.parseInt(PropUtil.getProperty("page.size.thirty")));
		 List<PurchaseRequired> list = purchaseRequiredMapper.getByDep(map);
			return list;
		}

	@Override
	public String getAttachementId(String id) {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public List<PurchaseRequired> selectByAll(HashMap<String, Object> map) {
        
        return purchaseRequiredMapper.selectByAll(map);
    }

	/**
	 *
	 * Description: 采购文号唯一校验
	 *
	 * @author Easong
	 * @version 2017/7/14
	 * @param
	 * @since JDK1.7
	 */
	@Override
	public JdcgResult selectUniqueReferenceNO(String referenceNO, String uniqueId) {
	    Map<String, Object> map = new HashMap<String, Object>();
	    map.put("planNo", uniqueId);
	    map.put("parentId", "1");
	    List<PurchaseRequired> byMap = purchaseRequiredMapper.getByMap(map);
	    if(byMap != null && !byMap.isEmpty()){
	        if(!byMap.get(0).getReferenceNo().equals(referenceNO)){
	            Integer integer = purchaseRequiredMapper.selectUniqueReferenceNO(referenceNO);
	            return JdcgResult.ok(integer);
	        }
	    }
	    return null;
	}

	/**
	 *
	 * Description: 根据ID查询
	 *
	 * @author Easong
	 * @version 2017/8/17
	 * @param 
	 * @since JDK1.7
	 */
	@Override
	public PurchaseRequired selectById(String id) {
		return purchaseRequiredMapper.selectById(id);
	}

    @Override
    public List<PurchaseRequired> connectByList(String id) {
        
        return purchaseRequiredMapper.connectByList(id);
    }
    public void deletedList(String unqueId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("planNo", unqueId);
        List<PurchaseRequired> byMap = purchaseRequiredMapper.getByMap(map);
        if(byMap != null && !byMap.isEmpty()){
            for (PurchaseRequired purchaseRequired : byMap) {
                purchaseRequiredMapper.deleteByPrimaryKey(purchaseRequired.getId());
            }
    
    }
}

	@Override
	public List<PurchaseRequired> selectByCreatedAt(String projectId) {
		
		return purchaseRequiredMapper.selectByCreatedAt(projectId);
	}
}
