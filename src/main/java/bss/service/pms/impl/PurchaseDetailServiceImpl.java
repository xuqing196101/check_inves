package bss.service.pms.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import ses.dao.oms.OrgnizationMapper;
import ses.model.oms.Orgnization;
import ses.util.PropUtil;
import bss.dao.pms.CollectPurchaseMapper;
import bss.dao.pms.PurchaseDetailMapper;
import bss.model.pms.PurchaseDetail;
import bss.service.pms.PurchaseDetailService;

@Service("purchaseDetailService")
public class PurchaseDetailServiceImpl implements PurchaseDetailService {
    
    @Autowired
    private PurchaseDetailMapper purchaseDetailMapper;
    
    @Autowired
    private SqlSessionFactory sqlSessionFactory; 
    
    @Autowired
    private CollectPurchaseMapper collectPurchaseMapper;
    
    @Autowired
    private OrgnizationMapper orgnizationMapper;

    @Override
    public void add(PurchaseDetail purchaseDetail) {
        // TODO Auto-generated method stub
        purchaseDetailMapper.insert(purchaseDetail);
    }

    @Override
    public void update(Map<String, Object> map) {
        // TODO Auto-generated method stub
        purchaseDetailMapper.history(map);
    }

    @Override
    public void updateByPrimaryKeySelective(PurchaseDetail purchaseDetail) {
        // TODO Auto-generated method stub
        purchaseDetailMapper.updateByPrimaryKeySelective(purchaseDetail);
    }

    @Override
    public PurchaseDetail queryById(String id) {
        // TODO Auto-generated method stub
        return purchaseDetailMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<PurchaseDetail> query(PurchaseDetail purchaseDetail, Integer page) {
        PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("page.size.thirty")));
        List<PurchaseDetail> list = purchaseDetailMapper.query(purchaseDetail);
        return list;
    }

    @Override
    public String queryByNo(String no) {
        List<PurchaseDetail> list = purchaseDetailMapper.queryByNo(no);
        if(list.size()>0){
            return list.get(0).getHistoryStatus();
        }else{
            return null;
        }
    }

    @Override
    public void delete(String planNo) {
        // TODO Auto-generated method stub
        purchaseDetailMapper.delete(planNo);
    }

    @Override
    public void updateStatus(PurchaseDetail purchaseDetail) {
        // TODO Auto-generated method stub
        purchaseDetailMapper.updateStatus(purchaseDetail);
    }

    @Override
    public List<PurchaseDetail> getByMap(Map<String, Object> map) {
        // TODO Auto-generated method stub
        return purchaseDetailMapper.getByMap(map);
    }

    @Override
    public List<Map<String, Object>> statisticDepartment(Map<String, Object> map) {
        // TODO Auto-generated method stub
        return purchaseDetailMapper.statisticDepartment(map);
    }

    @Override
    public List<PurchaseDetail> selectByParentId(Map<String, Object> map) {
        // TODO Auto-generated method stub
        return purchaseDetailMapper.selectByParentId(map);
    }

    @Override
    public List<PurchaseDetail> selectByParent(Map<String, Object> map) {
        // TODO Auto-generated method stub
        return purchaseDetailMapper.selectByParent(map);
    }

    @Override
    public List<Map<String, Object>> statisticPurchaseMethod(Map<String, Object> map) {
        // TODO Auto-generated method stub
        return purchaseDetailMapper.statisticPurchaseMethod(map);
    }

    @Override
    public List<Map<String, Object>> statisticByMonth(Map<String, Object> map) {
        // TODO Auto-generated method stub
        return purchaseDetailMapper.statisticByMonth(map);
    }

    @Override
    public List<Map<String, Object>> statisticOrg(Map<String, Object> map) {
        // TODO Auto-generated method stub
        return purchaseDetailMapper.statisticOrg(map);
    }

    @Override
    public void batchAdd(List<PurchaseDetail> list)
        throws IOException {
        SqlSession batchSqlSession = null;
        try{
            batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
            int batchCount = 50;//每批commit的个数
            for(int index = 0; index < list.size();index++){
                PurchaseDetail stu = list.get(index);
                batchSqlSession.getMapper(PurchaseDetailMapper.class).insert(stu);
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
    public List<PurchaseDetail> getByProjectStatus(String id, int projectStatus) {
        // TODO Auto-generated method stub
        return purchaseDetailMapper.getByProjectStatus(id, projectStatus);
    }

    @Override
    public void updateProjectStatus(String planNo) {
        // TODO Auto-generated method stub
        purchaseDetailMapper.updateProjectStatus(planNo);
    }

    @Override
    public void updateIdById(Map<String, Object> map) {
        // TODO Auto-generated method stub
        purchaseDetailMapper.updateIdById(map);
    }

    @Override
    public List<PurchaseDetail> seqAgain(String id) {
        List<String> list = collectPurchaseMapper.getNo(id);
        Map<String,Object> map=new HashMap<String,Object>(); 
        Set<String> set=new HashSet<String>();
        List<String> uq=new ArrayList<String>();
        List<PurchaseDetail>  prs=new ArrayList<PurchaseDetail>();
        Map<String,Object> mapAll=new HashMap<String,Object>();
        for(String no:list){
            map.put("isMaster", 1);
            map.put("planNo", no);
            List<PurchaseDetail> pr = purchaseDetailMapper.getByMap(map);
            set.add(pr.get(0).getDepartment());
            mapAll.put("planNo", no);
            List<PurchaseDetail> prList = purchaseDetailMapper.getByMap(mapAll);
            prs.addAll(prList);
        }
        for(String str:set){
            uq.add(str);
        }
        for(PurchaseDetail p:prs){
            if(p.getIsMaster()==2){
                if(p.getDepartment().equals(uq.get(0))){
                    
                }   
            }
        }
        
//      先查出所有的部门；然后去重；
//      然后重新遍历需求部门，如果，有多个存在就给序号重新复制
        //如果不存在就给序号加一
        return null;
    }

    @Override
    public List<PurchaseDetail> queryList(PurchaseDetail purchaseDetail) {
        // TODO Auto-generated method stub
        return purchaseDetailMapper.query(purchaseDetail);
    }

    @Override
    public Orgnization queryByName(String name) {
        
        return orgnizationMapper.queryByName(name);
    }

    @Override
    public List<String> queryOrg(String uniqueId) {
        // TODO Auto-generated method stub
        return purchaseDetailMapper.queryOrg(uniqueId);
    }

    @Override
    public List<PurchaseDetail> queryUnique(PurchaseDetail purchaseDetail) {
        // TODO Auto-generated method stub
        return purchaseDetailMapper.queryByUinuqe(purchaseDetail);
    }

    @Override
    public List<PurchaseDetail> getUnique(String unique) {
        // TODO Auto-generated method stub
        return purchaseDetailMapper.getByUinuqeId(unique);
    }

    @Override
    public Integer getChilden(String children) {
        // TODO Auto-generated method stub
        return purchaseDetailMapper.queryChilden(children);
    }

	@Override
	public List<PurchaseDetail> groupDetail(String uniqueId) {
		// TODO Auto-generated method stub
		return purchaseDetailMapper.groupDetail(uniqueId);
	}

}
