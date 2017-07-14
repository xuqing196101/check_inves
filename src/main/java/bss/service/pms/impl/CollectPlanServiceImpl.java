package bss.service.pms.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.oms.OrgnizationMapper;
import ses.model.oms.Orgnization;
import ses.util.PropUtil;
import ses.util.PropertiesUtil;

import com.github.pagehelper.PageHelper;

import bss.dao.pms.CollectPlanMapper;
import bss.model.pms.CollectPlan;
import bss.model.pms.PurchaseRequired;
import bss.service.pms.CollectPlanService;
import bss.service.pms.CollectPurchaseService;
import bss.service.pms.PurchaseRequiredService;
import bss.util.NumberUtils;
/**
 * 
 * @Title: CollectPlanServiceImpl
 * @Description: 采购计划汇总业务实现类
 * @author Li Xiaoxiao
 * @date  2016年9月20日,下午3:18:32
 *
 */
@Service
public class CollectPlanServiceImpl implements CollectPlanService{

	@Autowired
	private CollectPlanMapper collectPlanMapper;
	
	
	@Autowired
	private CollectPurchaseService collectPurchaseService; 
	
	@Autowired
	private PurchaseRequiredService purchaseRequiredService;
	
	@Autowired
	private OrgnizationMapper orgnizationMapper; 
	
	
	@Override
	public void add(CollectPlan collectPlan) {
		collectPlanMapper.insertSelective(collectPlan);
		
	}
	@Override
	public List<CollectPlan> queryCollect(CollectPlan collectPlan, Integer page) {
	    PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("page.size.thirty")));
		List<CollectPlan> list = collectPlanMapper.query(collectPlan);
		return list;
	}
	@Override
	public CollectPlan queryById(String id) {
	 
		return collectPlanMapper.selectByPrimaryKey(id);
	}
	@Override
	public void update(CollectPlan collectPlan) {
		// TODO Auto-generated method stub
		collectPlanMapper.updateByPrimaryKeySelective(collectPlan);
	}
	@Override
	public Integer getMax() {
		 
		return collectPlanMapper.getMax();
	}


	@Override
	public List<CollectPlan> getDepartmentList(Integer pageNum) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
		return collectPlanMapper.getDepartmentList();
	}
	@Override
	public List<PurchaseRequired> getAll(String id,HttpServletRequest request) {
		List<PurchaseRequired>  all=new LinkedList<PurchaseRequired>();
		//处理相同部门的
		List<String> no = collectPurchaseService.getNo(id);
		for(int i=0;i<no.size();i++){
			if(i==0){
				  List<PurchaseRequired>  list= purchaseRequiredService.getUnique(no.get(i));
				  all.addAll(list);
			}
			if(i<no.size()-1){
				String no1 = no.get(i);
				String no2 = no.get(i+1);
				   List<PurchaseRequired>  list= purchaseRequiredService.getUnique(no1);
				  List<PurchaseRequired>  list2 = purchaseRequiredService.getUnique(no2);
				 if(list.get(0).getDepartment().equals(list2.get(0).getDepartment())){
					 List<PurchaseRequired> list3 = getChildren(list,list2,request);
					 all.addAll(list3);
				}else{
					String string = NumberUtils.translate(i+2);
					list2.get(0).setSeq(string);
					all.addAll(list2);
				}
			}
		}	
		
		for(int i=0;i<all.size();i++){
			if(i>0){
				if(all.get(i).getSeq().equals("一")){
					all.remove(all.get(i));
				}
			}
		}
		return all;
	}

	
	
	
	@Override
	public List<PurchaseRequired> getAll(List<String> uniqueId, HttpServletRequest request) {
//		List<PurchaseRequired>  list1= purchaseRequiredService.getUnique(u);
		uniqueId= dep(uniqueId);
		List<PurchaseRequired>  all=new LinkedList<PurchaseRequired>();
//		Set<String> diff=new HashSet<String>();
		Integer count=1;
		List<String> diff=new ArrayList<String>();
		if(uniqueId!=null&&uniqueId.size()>0){
//			List<PurchaseRequired>  list= purchaseRequiredService.getUnique(uniqueId.get(0));
//			  all.addAll(list);
		for(int i = 0; i < uniqueId.size(); i++){
			if(i==0){
				  List<PurchaseRequired>  list= purchaseRequiredService.getUnique(uniqueId.get(0));
				  all.addAll(list);
			}
//			else
				if(i>=1){
				String no1 = uniqueId.get(i-1);
				String no2 = uniqueId.get(i);
				List<PurchaseRequired>  list1= purchaseRequiredService.getUnique(no1);
				List<PurchaseRequired>  list2= purchaseRequiredService.getUnique(no2);
				
				 if(list1.get(0).getDepartment().equals(list2.get(0).getDepartment())){
					 List<PurchaseRequired> list3 = getChildren(list1,list2,request);
//					 
					 all.addAll(list3);
				}else{
					count++;
					String string = NumberUtils.translate(count);
					list2.get(0).setSeq(string);
					all.addAll(list2);
					request.getSession().removeAttribute("NoCount");
				}
				 
//				if(list1.get(0).getDepartment().equals(list2.get(0).getDepartment())){
//					diff.add(uniqueId.get(i));
//					diff.add(uniqueId.get(k));
//					diff.add(list1.get(k).getId());
//					list1.remove(i);
//					i = i-1; 
//			       break;
				}
			}
		}
			
		
		
//			for(String u:uniqueId){
//				for(String un:uniqueId){
//					List<PurchaseRequired>  list1= purchaseRequiredService.getUnique(u);
//					List<PurchaseRequired>  list2= purchaseRequiredService.getUnique(un);
//					if(list1.get(0).getDepartment().equals(list2.get(0).getDepartment())){
//						 diff.add(un);
//					}
//				}
//			}
				
//			for(int i=0;i<uniqueId.size();i++){
//				if(i==0){
//					  List<PurchaseRequired>  list= purchaseRequiredService.getUnique(uniqueId.get(i));
//					  all.addAll(list);
//				}
//				if(i<uniqueId.size()-1){
//					String no1 = uniqueId.get(i);
//					String no2 = uniqueId.get(i+1);
//					   List<PurchaseRequired>  list= purchaseRequiredService.getUnique(no1);
//					  List<PurchaseRequired>  list2 = purchaseRequiredService.getUnique(no2);
//					 if(list.get(0).getDepartment().equals(list2.get(0).getDepartment())){
//						 List<PurchaseRequired> list3 = getChildren(list,list2,request);
//						 all.addAll(list3);
//					}else{
//						String string = NumberUtils.translate(i+2);
//						list2.get(0).setSeq(string);
//						all.addAll(list2);
//					}
//				}
//			}
//		}
//		Integer count=0;
//		if(diff!=null&&diff.size()>0){
//			for(int i=0;i<diff.size();i++){
//				if(i==0){
//					  List<PurchaseRequired>  list= purchaseRequiredService.getUnique(diff.get(i));
//					  all.addAll(list);
//				}
//				else 
//				if(i<diff.size()-1){
//					String no1 = diff.get(i);
//					String no2 = diff.get(i+1);
//					   List<PurchaseRequired>  list= purchaseRequiredService.getUnique(no1);
//					  List<PurchaseRequired>  list2 = purchaseRequiredService.getUnique(no2);
//					 if(list.get(0).getUserId().equals(list2.get(0).getUserId())){
//						 List<PurchaseRequired> list3 = getChildren(list,list2,request);
//						 count++;
//						 all.addAll(list3);
//					}else{
//						String string = NumberUtils.translate(i+2);
//						list2.get(0).setSeq(string);
//						all.addAll(list2);
//					}
//				}
//			}
		
//		}
//		
//		uniqueId.removeAll(diff);
//		 
//		if(uniqueId!=null&&uniqueId.size()>0){
//			for(String str:uniqueId){
//				count++;
//				List<PurchaseRequired>  list= purchaseRequiredService.getUnique(str);
//				list.get(0).setSeq( NumberUtils.translate(count));
//				all.addAll(list);
//			}
//		}
		
		
		for(int i=0;i<all.size();i++){
			if(i>0){
				if("一".equals(all.get(i).getSeq())){
					all.remove(all.get(i));
				}
			}
		}
		return all;
	}
	
	
	
	
	/**
	 * 
	* @Title: remove
	* @Description: 移除顶级节点
	* author: Li Xiaoxiao 
	* @param @param list
	* @param @return     
	* @return List<PurchaseRequired>     
	* @throws
	 */

	
	public List<PurchaseRequired> getChildren(List<PurchaseRequired> list1,List<PurchaseRequired> list2,HttpServletRequest request){
//		Integer integer1 = purchaseRequiredService.getChilden(list1.get(0).getId());
		Integer count=0;
//		int count2=0;
		String pid="";
		if(list1!=null&&list1.size()>0){
				pid=list1.get(0).getId();
			for(PurchaseRequired p:list1){
				if(p.getParentId().equals(pid)){
					count++;
				}
			}
		}
		Integer num = (Integer) request.getSession().getAttribute("NoCount");
		if(num==null){
			if(list2!=null&&list2.size()>0){
				String id=list2.get(0).getId();
				for(PurchaseRequired p:list2){
					if(p.getParentId().equals(id)){
						count++;
						String string = NumberUtils.translate(count);
						p.setSeq("（"+string+"）");
						p.setParentId(pid);
					}
				}
			}
		}else{
			if(list2!=null&&list2.size()>0){
				String id=list2.get(0).getId();
				for(PurchaseRequired p:list2){
					if(p.getParentId().equals(id)){
						num++;
						String string = NumberUtils.translate(num);
						p.setSeq("（"+string+"）");
						p.setParentId(pid);
					}
				}
			}
		}
		
		request.getSession().setAttribute("NoCount", count);
//		list2.remove(list2.get(0));
//		List<PurchaseRequired> list = remove(list2);
//		list1.addAll(list2);
//		Map<String,Object> map=new HashMap<String,Object>();
//		map.put("list1", list1);
		
		
		return list2;
	}

	
	
//	public List<PurchaseRequired> remove(List<PurchaseRequired> list){
//		for(int i=0;i<list.size();i++){
//			list.remove(list.get(0));
//		}
//		return list;
//	}
	/**
	 * 
	* @Title: dep
	* @Description:根据需求部门顺序重新汇总
	* author: Li Xiaoxiao 
	* @param @param uniqueId
	* @param @param request
	* @param @return     
	* @return List<String>     
	* @throws
	 */
	public List<String>  dep(List<String> uniqueId) {
		//获取顶级ID
		PurchaseRequired p = new PurchaseRequired();
		List<Orgnization> allList=new ArrayList<Orgnization>();
		List<Orgnization> detailList=new ArrayList<Orgnization>();
		for(String u:uniqueId){
			   p.setUniqueId(u);
//		       p.setIsMaster(1);
			   List<PurchaseRequired> one = purchaseRequiredService.queryUnique(p);
			   String department = one.get(0).getDepartment();
			   Orgnization orgnization = purchaseRequiredService.queryByName(department);
			   List<Orgnization> list = orgnizationMapper.getParent(orgnization.getId());
			   for(Orgnization org:list){
				   Orgnization depart = orgnizationMapper.findOrgByPrimaryKey(org.getParentId());
				   if(depart==null){
					   allList.add(org);
				   }
			   }
			   
		}
//		List<Orgnization> same=new ArrayList<Orgnization>();
//		for(int i = 0; i < allList.size(); i++){
//			for(int k = 0;k < (i<=1?i:(i-1)) ; k++){
//				 String id2 = allList.get(i).getId();
//				 String id = allList.get(k).getId();
//				if(!id.equals(id2)){
//					same.add(allList.get(i));
//					same.add(allList.get(k));
//					diff.add(list1.get(k).getId());
//					list1.remove(i);
//					i = i-1; 
//			       break;
//				}
//			}
//		}
		//对顶级id去重
		for (int i = 0; i < allList.size(); i++)  //外循环是循环的次数
        {
            for (int j = allList.size() - 1 ; j > i; j--)  //内循环是 外循环一次比较的次数
            {

                if (allList.get(i).getId().equals(allList.get(j).getId()))
                {
                	allList.remove(j);
                }

            }
        }
		
		
//		allList.removeAll(same);
		//对顶级id排序
	      Collections.sort(allList, new Comparator<Orgnization>(){  
	    	  
	            /*  
	             * int compare(Student o1, Student o2) 返回一个基本类型的整型，  
	             * 返回负数表示：o1 小于o2，  
	             * 返回0 表示：o1和o2相等，  
	             * 返回正数表示：o1大于o2。  
	             */  
	            public int compare(Orgnization o1, Orgnization o2) {  
	              
	               
	                if(Integer.valueOf(o1.getPosition())>Integer.valueOf(o2.getPosition())){  
	                    return 1;  
	                }  
	                if(o1.getPosition() == o2.getPosition()){  
	                    return 0;  
	                }  
	                return -1;  
	            }  
	        });  
	      
	      //循环遍历
		for(Orgnization org:allList){
			Orgnization orgnization = orgnizationMapper.findOrgByPrimaryKey(org.getId());
			detailList.add(orgnization);
			List<Orgnization> orderOrg = orderOrg(org.getId());
			detailList.addAll(orderOrg);
		}
//		Set<Orgnization> set=new HashSet<Orgnization>();
		
		List<String> list=new ArrayList<String>();
		for(Orgnization org:detailList){
	   	  for(String u:uniqueId){
			   p.setUniqueId(u);
		       p.setIsMaster(1);
			   List<PurchaseRequired> one = purchaseRequiredService.queryUnique(p);
			   String department = one.get(0).getDepartment();
			  
				   if(org.getShortName().equals(department)){
					   list.add(u);
				   }
			   }
		}
		return list;
	}
	
	public List<Orgnization> orderOrg(String id){
		List<Orgnization> childList=new ArrayList<Orgnization>();
		List<Orgnization> list = orgnizationMapper.getListByPidAndType(id, "0");
		for(Orgnization org:list){
			Orgnization orgnization = orgnizationMapper.findOrgByPrimaryKey(org.getId());
			if(orgnization!=null){
				childList.add(orgnization);
			}
			childList.addAll(orderOrg(org.getId()));
		}
		return childList;
	}
	@Override
	public List<CollectPlan> getSummary(CollectPlan collectPlan,Integer page) {
		PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("page.size.thirty")));
		List<CollectPlan> list = collectPlanMapper.getSummary(collectPlan);
		return list;
	}
    @Override
    public List<CollectPlan> querySupervision(CollectPlan collectPlan, Integer page) {
        PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("page.size.thirty")));
        List<CollectPlan> list = collectPlanMapper.querySupervision(collectPlan);
        return list;
    }
    @Override
    public List<CollectPlan> selectManagePlan(HashMap<String, Object> map) {
        
        return collectPlanMapper.selectManagePlan(map);
    }
    @Override
    public List<CollectPlan> selectDatePlan(HashMap<String, Object> map) {

        return collectPlanMapper.selectDatePlan(map);
    }
    @Override
    public List<CollectPlan> selectOrgPlan(HashMap<String, Object> map) {
        
        return collectPlanMapper.selectOrgPlan(map);
    }
	
}
