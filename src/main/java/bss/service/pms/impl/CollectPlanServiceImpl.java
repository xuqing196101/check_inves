package bss.service.pms.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		List<PurchaseRequired>  all=new LinkedList<PurchaseRequired>();
		if(uniqueId!=null&&uniqueId.size()>0){
			for(int i=0;i<uniqueId.size();i++){
				if(i==0){
					  List<PurchaseRequired>  list= purchaseRequiredService.getUnique(uniqueId.get(i));
					  all.addAll(list);
				}
				if(i<uniqueId.size()-1){
					String no1 = uniqueId.get(i);
					String no2 = uniqueId.get(i+1);
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
	
	
}
