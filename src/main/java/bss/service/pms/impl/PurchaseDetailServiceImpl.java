package bss.service.pms.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
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
import ses.model.bms.DictionaryData;
import ses.model.oms.Orgnization;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;
import bss.dao.pms.CollectPurchaseMapper;
import bss.dao.pms.PurchaseDetailMapper;
import bss.dao.pms.PurchaseRequiredMapper;
import bss.formbean.Line;
import bss.formbean.Maps;
import bss.model.pms.CollectPlan;
import bss.model.pms.PurchaseDetail;
import bss.model.pms.PurchaseRequired;
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
    @Autowired
    private PurchaseRequiredMapper purchaseRequiredMapper;

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
    public List<PurchaseDetail> getUnique(String unique,String org,String dep) {
        // TODO Auto-generated method stub
        return purchaseDetailMapper.getByUinuqeId(unique,org,dep);
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

	@Override
	public List<PurchaseDetail> getUniqueId(String uniqueId) {
		// TODO Auto-generated method stub
		return purchaseDetailMapper.getUniqueId(uniqueId);
	}

	@Override
	public List<String> queryDepartment(String uniqueId,Integer page) {
		 PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("pageSize")));
		List<String> departMents = purchaseDetailMapper.getDep(uniqueId);
		return departMents;
	}

	@Override
	public Map<String, Object> getbar(HashMap<String, Object> hashMap) {
		Map<String,Object> dataMap=new HashMap<String,Object>();
		List<Map<String, Object>> list = purchaseDetailMapper.getbar(hashMap);
		List<String> listData = new LinkedList<String>();
		List<String>  data=new LinkedList<String>();
		BigDecimal max=BigDecimal.ZERO;
		if(list!=null && list.size() >0){
			for (Map<String,Object> m : list) {
				listData.add(String.valueOf(m.get("DEPARTMENT")==null?"":m.get("DEPARTMENT"))) ;
				String str=String.valueOf(m.get("AMOUNT"));
				data.add(str);
				BigDecimal min = new BigDecimal(str);
				int n = max.compareTo(min);
				if(n<0){
					max=min;
				}
			}
		}
		dataMap.put("name", listData);
		dataMap.put("data", data);
		dataMap.put("max", max);
		return dataMap;
	}

	@Override
	public Map<String, Object> getpipe(HashMap<String, Object> hashMap) {
        Map<String,Object> data=new HashMap<String,Object>();
		List<Maps> maps=new LinkedList<Maps>();
		List<String> type=new LinkedList<String>();
		List<Map<String, Object>> list = purchaseDetailMapper.getpipe(hashMap);
		if(list!=null && list.size() >0){
			for (Map<String,Object> m : list) {
				if(m.get("PURCHASETYPE")==null){
					continue;
				}
				DictionaryData dic = DictionaryDataUtil.findById(String.valueOf(m.get("PURCHASETYPE")));
				
				if(dic!=null){
					type.add(dic.getName());
					
				}else{
					continue;
				}
				 Maps mp=new Maps();
 				 String string = String.valueOf(m.get("AMOUNT"));
 				BigDecimal decimal = new BigDecimal(string);
				 mp.setValue(decimal);
				if(dic!=null){
					 mp.setName(dic.getName());
				}
				 maps.add(mp);
			}
		}
		data.put("maps", maps);
		data.put("type", type);
		return data;
	}

	@Override
	public Map<String, Object> getline(HashMap<String, Object> hashMap) {
		List<Map<String,Object>> list =purchaseDetailMapper.getline(hashMap);
		Map<String,Object> data=new HashMap<String,Object>();
		List<String> month=new LinkedList<String>();
		List<String> val=new LinkedList<String>();
		List<Line> lineList=new LinkedList<Line>();
		if(list!=null && list.size() >0){
			for (Map<String,Object> m : list) {
				month.add(String.valueOf(m.get("MONTH")));
				String string = String.valueOf(m.get("AMOUNT"));
				val.add(string);
			/*	data.setValue(String.valueOf(m.get("AMOUNT")));
				listData.add(data);*/
			}
		}
		Line line=new Line();
		line.setData(val);
		line.setName("金额");
		line.setType("line");
		line.setStack("总量");
		lineList.add(line);
		data.put("month", month);
		data.put("line", lineList);
		return data;
	}

	@Override
	public List<PurchaseDetail> getdetailAllByUserId(
			HashMap<String, Object> hashMap) {
		PageHelper.startPage((Integer)hashMap.get("page"),Integer.parseInt(PropUtil.getProperty("page.size.thirty")));
		List<PurchaseDetail> list = purchaseDetailMapper.getdetailAllByUserId(hashMap);
		return list;
	}

    @Override
    public List<PurchaseDetail> getUniques(String unique, String org) {
        
        return purchaseDetailMapper.getByUinuqeIds(unique, org);
    }

    @Override
    public List<PurchaseDetail> getUniquesByTask(String projectId, String unique, String org) {
      return purchaseDetailMapper.getUniquesByTask(projectId, unique, org);
    }

    @Override
    public void updatePurchaseDetailByPlanId(CollectPlan collectPlan,
        String uniqueId) {
      //需求编报id集合
      String[] uId=uniqueId.split(",");
      //获取计划parentId为1的需求部门
      List<PurchaseDetail> departmentAndId = purchaseDetailMapper.getUniqueIdByParentId(collectPlan.getId());
      PurchaseRequired purchaseRequired=null;
      Map<String, String> oldDept=new HashMap<String, String>();
      for(int i=0;i<uId.length;i++){
        Boolean flg=true;
        //查询需求明细部门名称
        purchaseRequired=new PurchaseRequired();
        purchaseRequired.setUniqueId(uId[i]);
        purchaseRequired.setParentId("1");
        List<PurchaseRequired> purchaseRequiredList = purchaseRequiredMapper.queryByUinuqe(purchaseRequired);
        if(purchaseRequiredList.size()>0&&purchaseRequiredList!=null){
          purchaseRequired=purchaseRequiredList.get(0);
        }else{
          purchaseRequired=null;
        }
        if(purchaseRequired!=null){
          for (PurchaseDetail dId : departmentAndId) {
            //需求parentId=1的明细和计划明细parentId=1的部门匹配
            if(purchaseRequired.getDepartment().equals(dId.getDepartment())){//匹配上了部门
              HashMap<String, Object> map=new HashMap<String, Object>();
              map.put("parentId", dId.getId());
              List<PurchaseDetail> pds = purchaseDetailMapper.getByMap(map);
              if(pds.size()>0&&pds!=null){
                PurchaseDetail purchaseDetail = pds.get(pds.size()-1);
                if(purchaseDetail!=null){
                  oldDept.put(purchaseRequired.getDepartment(), purchaseDetail.getSeq());
                  String seq = oldDept.get(purchaseRequired.getDepartment());
                  if(seq!=null){
                    String seqNext =seqNext(seq);
                    System.out.println(seqNext);
                  }
                }
              }
              flg=false;
            }
          }
          if(flg){//没陪配上部门
          }
        }
      }
    }

    
    public String seqNext(String seq){
      seq=seq.substring(1, seq.length()-1);
      String seqNext="";
      String[] seqs={"一","二","三","四","五","六","七","八","九","十"};
      for (int i=0;i< seqs.length ;i++) {
        if(seqs[i].equals(seq)){
          seqNext=seqs[i+1];
          break;
        }
      }
      if(seqNext.length()>0){
        seqNext="（"+seqNext+"）";
      }
      return seqNext;
    }
}
