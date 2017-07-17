package bss.controller.pms;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ses.dao.bms.DictionaryDataMapper;
import ses.dao.oms.OrgnizationMapper;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseDep;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.oms.OrgnizationServiceI;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.util.DictionaryDataUtil;
import bss.controller.base.BaseController;
import bss.model.pms.CollectPlan;
import bss.model.pms.PurchaseDetail;
import bss.model.pms.PurchaseManagement;
import bss.model.pms.PurchaseRequired;
import bss.service.pms.CollectPlanService;
import bss.service.pms.CollectPurchaseService;
import bss.service.pms.PurchaseDetailService;
import bss.service.pms.PurchaseManagementService;
import bss.service.pms.PurchaseRequiredService;
import bss.util.ExcelUtil;
import bss.util.NumberUtils;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import common.annotation.CurrentUser;

/**
 * @Title: CollectPlanController
 * @Description: 采购计划汇总管理 
 * @author Li Xiaoxiao
 * @date  2016年9月20日,下午3:21:24
 *
 */
@Controller
@RequestMapping("/collect")
public class CollectPlanController extends BaseController {

   @Autowired
   private CollectPlanService collectPlanService;

    @Autowired
    private PurchaseRequiredService purchaseRequiredService;
  
    @Autowired
    private CollectPurchaseService collectPurchaseService;
  
    @Autowired
    private DictionaryDataServiceI dictionaryDataServiceI;

    @Autowired
    private PurchaseOrgnizationServiceI purchaseOrgnizationServiceI;
  
    @Autowired
    private PurchaseDetailService purchaseDetailService;
  
	@Autowired
	private OrgnizationServiceI orgnizationServiceI;
	@Autowired
	private DictionaryDataMapper dictionaryDataMapper;
	
	@Autowired
	private PurchaseManagementService purchaseManagementService;
  
    /**
		* @Title: queryPlan
		* @Description: 条件查询分页需求计划
		* author: Li Xiaoxiao 
		* @param @param purchaseRequired
		* @param @return     
		* @return String     
		* @throws
		 */
	    @RequestMapping("/list")
	    public String queryPlan(@CurrentUser User user,PurchaseRequired purchaseRequired,Integer page,Model model,String status){
			    Map<String,Object> map=new HashMap<String,Object>();
			    if(purchaseRequired.getStatus()==null){
			    	//map.put("status", 4);
			    }
//			    else if(purchaseRequired.getStatus().equals("total")){
//			    	map.put("sign", "3");
//			    }else if(purchaseRequired.getStatus().equals("5")){
//			    	map.put("status", "5");
//			    }else if(purchaseRequired.getStatus().equals("3")){
//			    	map.put("status", "3");
//			    }
			    map.put("isMaster", "1");
			    map.put("planName", purchaseRequired.getPlanName());
			    model.addAttribute("status", status);
			    if(status==null){
			    	 model.addAttribute("status", "3");
			    	status="2";
			    }
			
			    if(status.equals("5")){
					status="4";
				}
			    if(status.equals("3")){
					status="2";
				}
			    
			    if(status.equals("total")){
					status="-1";
				}
			   
				List<PurchaseManagement> list2 = purchaseManagementService.queryByMid(user.getOrg().getId(), page==null?1:page,Integer.valueOf(status));
				/*PageInfo<PurchaseManagement> pm = new PageInfo<>(list2);*/
				List<String> listDep=new ArrayList<String>();
				if(list2!=null&&list2.size()>0){
					for(PurchaseManagement p:list2){
							listDep.add(p.getPurchaseId());
					}
				}else{
					listDep.add("sss");
				}
				
				map.put("list", listDep);
				map.put("page", page==null?1:page);
			    List<PurchaseRequired> list = purchaseRequiredService.queryListUniqueId(map);
			    PageInfo<PurchaseRequired> info = new PageInfo<>(list);
				/*info.setStartRow(pm.getStartRow());
				info.setEndRow(pm.getEndRow());
				info.setPages(pm.getPages());
				info.setTotal(pm.getTotal());
				info.setFirstPage(pm.getFirstPage());
				info.setPageNum(pm.getPageNum());
				info.setPageSize(pm.getPageSize());*/
			    model.addAttribute("info", info);
			    model.addAttribute("inf", purchaseRequired);
			    List<DictionaryData> dic = dictionaryDataServiceI.findByKind("6");
			  
			    model.addAttribute("dic", dic);
			 
				model.addAttribute("types", DictionaryDataUtil.find(6));
	        return "bss/pms/collect/collectlist";
	    }
    
	    @RequestMapping("/view")
	    public String getById(@CurrentUser User user,String planNo,Model model,String type){
	        PurchaseRequired p=new PurchaseRequired();
	        p.setUniqueId(planNo.trim());
	        List<PurchaseRequired> list = purchaseRequiredService.queryUnique(p);
	        model.addAttribute("kind", DictionaryDataUtil.find(5));//获取数据字典数据
	        model.addAttribute("list", list);
	        
	    	HashMap<String,Object> maps=new HashMap<String,Object>();
			maps.put("typeName", 1);
		     List<PurchaseDep> orga = purchaseOrgnizationServiceI.findPurchaseDepList(maps);
			
			
		      model.addAttribute("orga", orga);	
		      
		      
	//        回头加上
	//        Map<String,Object> map=new HashMap<String,Object>();
	//        List<Orgnization> requires = oargnizationMapper.findOrgPartByParam(map);
	//        model.addAttribute("requires", requires);
	        
	        return "bss/pms/collect/collect_view";
	        
	    }
	    
    
		/**
		 * 
		* @Title: queryPlan
		* @Description: 条件查询分页汇总计划
		* author: Li Xiaoxiao 
		* @param @param purchaseRequired
		* @param @return     
		* @return String     
		* @throws
		 */
	@RequestMapping("/collectlist")
    public String queryCollect(@CurrentUser User user,CollectPlan collectPlan,Integer page,Model model,String type) {
    //下达状态
	    collectPlan.setSign("3");
		collectPlan.setUserId(user.getId());
	    List<CollectPlan> list = collectPlanService.queryCollect(collectPlan, page == null ? 1 : page);
	    PageInfo<CollectPlan> info = new PageInfo<>(list);
	 
	    model.addAttribute("info", info);
	    model.addAttribute("inf", collectPlan);
	    model.addAttribute("type", type);
	    List<DictionaryData> list2 = DictionaryDataUtil.find(6);
	    model.addAttribute("mType", list2);
	
	    return "bss/pms/collect/contentlist";
	 }
    /**
		  * 
		 * @Title: queryCollect
		 * @Description: 汇总,以及修改采购计划 
		 * author: Li Xiaoxiao 
		 * @param @param collectPlan
		 * @param @return     
		 * @return String     
		 * @throws
		  */
    @RequestMapping("/add")
    public String updateCollectPlan(@CurrentUser User user,CollectPlan collectPlan,String uniqueId,String goodsType,HttpServletRequest request) {
    PurchaseRequired p = new PurchaseRequired();
    List<PurchaseRequired> list = new LinkedList<PurchaseRequired>();
    List<String> ulist=new ArrayList<String>();
    if (uniqueId != null ) {
      String[] uid = uniqueId.split(",");
      for (String u:uid) {
        p.setUniqueId(u);
        p.setIsMaster(1);
        ulist.add(u);
        //修改状态
        List<PurchaseRequired> one = purchaseRequiredService.queryUnique(p);
					p.setStatus("5");//修改
					p.setIsMaster(null);
					purchaseRequiredService.updateStatus(p);
					list.addAll(one);
					//修改中间表状态
					purchaseManagementService.updateStatus(u,4);	
				}
			}
					BigDecimal budget=BigDecimal.ZERO;
					for(PurchaseRequired pr:list){
						if("一".equals(pr.getSeq())){
							budget=budget.add(pr.getBudget()==null?new BigDecimal(0):pr.getBudget());
						}
//						if(pr.getPurchaseCount()!=null){
//							budget=budget.add(pr.getBudget());
//						}
						
					}
					String id = UUID.randomUUID().toString().replaceAll("-", "");
					collectPlan.setId(id);
					collectPlan.setStatus(1);
					collectPlan.setBudget(budget);
					collectPlan.setCreatedAt(new Date());
					Integer max = collectPlanService.getMax();
					if(max!=null){
						max+=1;
						collectPlan.setPosition(max);
					}else{
						collectPlan.setPosition(1);
						
					}
					
					//保存至中间表开始
//					String[] uid = uniqueId.split(",");
//					CollectPurchase c=new CollectPurchase();
//					for(String u:uid){
//						c.setCollectPlanId(id);
//						c.setPlanNo(u);
//						collectPurchaseService.add(c);
//					}
					//保存至中间表结束
					
					collectPlan.setUserId(user.getId());
					collectPlan.setGoodsType(goodsType);
					List<PurchaseRequired> list2 = collectPlanService.getAll(ulist, request);
					if(list2!=null&&list2.size()>0){
						Integer count=1;
						for(PurchaseRequired pr:list2){
							PurchaseDetail pd=new PurchaseDetail();
							BeanUtils.copyProperties(pr, pd,new String[] {"serialVersionUID"});
							pd.setUniqueId(collectPlan.getId());
							pd.setIsMaster(count);
							count++;
							purchaseDetailService.add(pd);
						}
					}
					collectPlan.setDepartment("");
					collectPlanService.add(collectPlan);
					request.getSession().removeAttribute("NoCount");
			return "redirect:list.html";
		}
		/**
		 * 
		* @Title: update
		* @Description: 汇入采购计划
		* author: Li Xiaoxiao 
		* @param @param collectPlan
		* @param @return     
		* @return String     
		* @throws
		 */
		@RequestMapping("/update")
		@ResponseBody
		public String update(CollectPlan collectPlan,String uniqueId,HttpServletRequest request){
			
//			List<PurchaseDetail> detail = purchaseDetailService.groupDetail(collectPlan.getId());
			//采购计划明细
			List<PurchaseDetail> pd = purchaseDetailService.getUniqueId(collectPlan.getId());
			List<String> allList=new ArrayList<String>();//新的以以及老的
			
			List<String> ids=new ArrayList<String>();
			for(PurchaseDetail p:pd){
				ids.add(p.getId());
//				PurchaseRequired pr = purchaseRequiredService.queryById(p.getId());
//				
//				if(pr!=null){
//					allList.add(pr.getUniqueId());
//				}
			}
			List<String> uIds = purchaseRequiredService.getUniqueId(ids);
			allList.addAll(uIds);
			CollectPlan plan = collectPlanService.queryById(collectPlan.getId());
			String [] uniqueIds = uniqueId.split(",");//获取要汇入采购计划 的id
			List<PurchaseRequired> list=new LinkedList<PurchaseRequired>();
			PurchaseRequired p=new PurchaseRequired();
//			String pid="";
//			Integer seq=1;
//			List<String> addList=new ArrayList<String>();
			for(String c:uniqueIds){
//				c.setCollectPlanId(collectPlan.getId());
//				c.setPlanNo(no);
//				
//				保存至中间表
//				collectPurchaseService.add(c);
				
				p.setUniqueId(c);
				List<PurchaseRequired> one = purchaseRequiredService.queryUnique(p);
//				append(one,detail,collectPlan.getId());
//				for(PurchaseRequired pr:one){
//					pid=one.get(0).getId();
//					if(pid.equals(pr.getParentId())){
//						
//					}
//				}
				allList.add(c);
				list.addAll(one);
				
//				addList.add(c);
				p.setStatus("5");//修改
				p.setIsMaster(null);
				purchaseRequiredService.updateStatus(p);
				
				//修改中间表状态
				purchaseManagementService.updateStatus(c,4);	
			
			}
			
			List<PurchaseRequired> list2 = collectPlanService.getAll(allList, request);
			
			
			
			BigDecimal budget=BigDecimal.ZERO;//计算金额
			for(PurchaseRequired pr:list){
				if(pr.getSeq().equals("一")){
					budget=budget.add(pr.getBudget());
				}
			}
//			List<PurchaseDetail> list2 = purchaseDetailService.getUnique(plan.getId());
			
			if(list2!=null&&list2.size()>0){
				Integer count=1;
				for(PurchaseRequired pr:list2){
					PurchaseDetail pds=new PurchaseDetail();
					BeanUtils.copyProperties(pr, pds,new String[] {"serialVersionUID"});
					pds.setUniqueId(collectPlan.getId());
					pds.setIsMaster(count);
					count++;
					if(!pr.getSeq().equals("一")){
				    	PurchaseDetail detail = purchaseDetailService.queryById(pds.getId());
						if(detail!=null){
							purchaseDetailService.updateByPrimaryKeySelective(pds);
						}else {
							purchaseDetailService.add(pds);
						}
					}
					
					
				}
			}
			
			
			BigDecimal decimal = plan.getBudget();
			BigDecimal budget2=	decimal.add(budget);
			
			plan.setBudget(budget2);
			collectPlanService.update(plan);
			
 
			
			
			return "";
		}
		
		
		
		public  void append(List<PurchaseRequired> one,List<PurchaseDetail> list,String uniqueId){
			List<PurchaseDetail> updateList=new ArrayList<PurchaseDetail>();
			String pid=one.get(0).getId();
			String dep=one.get(0).getDepartment();
			Integer count=0;
			Integer oldSize=list.size();
			for(PurchaseDetail pd:list){
				if(dep.equals(pd.getDepartment())){
					count=purchaseDetailService.getChilden(pd.getId());
					for(PurchaseRequired pr:one){
						if(pid.equals(pr.getParentId())){
							count++;
							one.get(0).setSeq(NumberUtils.translate(count));
							pr.setParentId(pd.getId());
						}
					
					}
				}else{
					oldSize++;
					one.get(0).setSeq(NumberUtils.translate(oldSize));
				}
			}
			
		}
		
				/**
				 * @throws Exception 
		     * 
		    * @Title: upload
		    * @Description: 采购计划导入
		    * @author: LChenHao 
		    * @return     
		    * @return String     
		    * @throws
		     */
		  @SuppressWarnings("unchecked")
		  @RequestMapping(value = "/upload" ,produces="text/html;charset=UTF-8")
		  @ResponseBody
		  public String uploadFile(@CurrentUser User user,MultipartFile file,Model model,String planType)throws Exception{
	        String fileName = file.getOriginalFilename();  
	        if(!fileName.endsWith(".xls")&&!fileName.endsWith(".xlsx")){  
	        	return "1";
	        }  
	        
			List<PurchaseRequired> list=new ArrayList<PurchaseRequired>();
//			ExcelUtil util=new ExcelUtil();
			    Map<String,Object>  maps= (Map<String, Object>) ExcelUtil.cgjhExcel(file);
			     list = (List<PurchaseRequired>) maps.get("list");
			     
			     String errMsg=(String) maps.get("errMsg");
			      if(list==null||list.size()<=0){
			        return "1";
			      }
			     if(errMsg!=null){
			          String jsonString = JSON.toJSONString(errMsg);
						return jsonString;
				}
			   /*  if(!user.getOrg().getName().equals(list.get(0).getDepartment())){
			    	 return "2"; 
			     }*/
			     
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			String pid = UUID.randomUUID().toString().replaceAll("-", "");
			String cid = UUID.randomUUID().toString().replaceAll("-", "");
			String ccid = UUID.randomUUID().toString().replaceAll("-", "");
			String cccid = UUID.randomUUID().toString().replaceAll("-", "");
			String ccccid = UUID.randomUUID().toString().replaceAll("-", "");
			String unqueId = UUID.randomUUID().toString().replaceAll("-", "");
//			int len=list.size()-1;
//			StringBuffer sbUp=new StringBuffer("");
//			StringBuffer sbShow=new StringBuffer("");
			int count=1;
//			BigDecimal budget=BigDecimal.ZERO;
			String org="2";
			CollectPlan collectPlan=new CollectPlan();
			collectPlan.setId(unqueId);
			collectPlan.setStatus(1);
			collectPlan.setFileName(list.get(0).getPlanName());
			collectPlan.setBudget(list.get(0).getBudget());
			collectPlan.setCreatedAt(new Date());
			Integer max = collectPlanService.getMax();
			if(max!=null){
				max+=1;
				collectPlan.setPosition(max);
			}else{
				collectPlan.setPosition(1);
				
			}
			collectPlan.setUserId(user.getId());
			collectPlan.setGoodsType(planType);
			collectPlanService.add(collectPlan);
			
			
			for(int i=0;i<list.size();i++){
//				String id = UUID.randomUUID().toString().replaceAll("-", "");
		 
					PurchaseRequired p = list.get(i);
					 
					p.setPlanType(planType);
					p.setHistoryStatus("0");
					p.setIsDelete(0);
					p.setIsMaster(count);
					p.setCreatedAt(new Date());
					p.setUserId(user.getId());
				    p.setProjectStatus(0);
                    p.setAdvancedStatus(0);
                    p.setStatus("5");
                    p.setUniqueId(unqueId);
					if(p.getOrganization()!=null){
					 Orgnization dep = purchaseRequiredService.queryByName(p.getOrganization());
						if(dep!=null){
							p.setOrganization(dep.getId());
						}else{
							break;
						}
					}
					/*if(p.getPurchaseType()!=null){
						DictionaryData data = dictionaryDataMapper.queryByName(p.getPurchaseType());
						p.setPurchaseType(data.getId());
					}*/
					
					//p.setOrganization(user.getOrg().getName());
					
					p.setDetailStatus(1);
					 if(p.getPurchaseType()!=null&&p.getPurchaseType().trim().length()!=0){
	                        DictionaryData data = dictionaryDataMapper.queryByName(p.getPurchaseType());
	                        p.setPurchaseType(data.getId());
	                    }
					 
//					if(p.getBudget()!=null){
//						budget=budget.add(p.getBudget());
//					}
				//顶级节点	
				 if(p.getSeq().matches("[\u4E00-\u9FA5]")&&!p.getSeq().contains("（")){
					 	 p.setSeq("一");
					 	 count=1;
					 	 p.setIsMaster(count);
						
						 p.setParentId("1");//注释
//						 plano = randomPlano();
						 id = UUID.randomUUID().toString().replaceAll("-", "");//重新给顶级id赋值
						 p.setId(id);//注释
						 count++;
						 continue;
					 }
				 //判断是否是二级节点(一)
				 	if(isContainChinese(p.getSeq())){
				 		
						 p.setParentId(id);
						 pid = UUID.randomUUID().toString().replaceAll("-", "");//重新给顶级pid赋值
						 p.setId(pid);
						 count++;
						 continue;	
					}
				 	
				 	 
				 	//判断是否是三级节点1,2,3
				 	else  if(isInteger(p.getSeq())){
				
					  p.setParentId(pid);
					  cid = UUID.randomUUID().toString().replaceAll("-", "");//重新给顶级cid赋值
					  p.setId(cid);
					  count++;
					  continue;	
					}
				 	
				 	//判断是否四级节点(1),(2)
				 	else if(isContainIntger(p.getSeq())){
					
						p.setParentId(cid);
						ccid = UUID.randomUUID().toString().replaceAll("-", "");//重新给顶级cid赋值
						p.setId(ccid);
						 count++;
						continue;
					}
				 	//五级节点
				 	else if(isEng(p.getSeq())){
						p.setId(cccid);
					
						cccid = UUID.randomUUID().toString().replaceAll("-", "");//重新给顶级cid赋值
						p.setParentId(ccid);
						 count++;
						continue;
					}else{
						p.setId(ccccid);
						ccccid = UUID.randomUUID().toString().replaceAll("-", "");//重新给顶级cid赋值
						p.setParentId(cccid);
						count++;
					}
					
			 
			//	count++;
				
//				sbUp.append("pUp"+i+",");
//				sbShow.append("pShow"+i+",");
//				if(len==i){
//					sbUp.append("pUp"+i);
//					sbShow.append("pShow"+i);
//				}
			}
			String jsonString = JSON.toJSONString(org);
			for(PurchaseRequired pr:list){
				PurchaseDetail pd=new PurchaseDetail();
				BeanUtils.copyProperties(pr, pd,new String[] {"serialVersionUID"});
				purchaseDetailService.add(pd);
			}
		    return jsonString;
		 }

	  
		/**
		 * 
		* @Title: isContainChinese
		* @Description: 判断是否是含有中文 
		* author: Li Xiaoxiao 
		* @param @param str
		* @param @return     
		* @return boolean     
		* @throws
		 */
		public boolean isContainChinese(String str){
			boolean bool=true;
			Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
			Matcher m = p.matcher(str);
		    if(m.find()==true&&str.contains("（")){
		        	bool=true;
		     }else{
		        	bool=false;
		    }
			return bool;
		}
		/**
		 * 
		* @Title: isInteger
		* @Description: 判断是否是数字
		* author: Li Xiaoxiao 
		* @param @param str
		* @param @return     
		* @return boolean     
		* @throws
		 */
		public boolean isInteger(String str){
			boolean bool=true;
			String regex="^\\d+$";
			if(str.matches(regex)) {
				bool=true; 
			}else{
				bool=false; 
			}
			return bool;
		}
		/**
		 * 
		* @Title: isContain
		* @Description: 是否包含数字
		* author: Li Xiaoxiao 
		* @param @param str
		* @param @return     
		* @return boolean     
		* @throws
		 */
		public boolean isContainIntger(String str){
			boolean bool=true;
		    Pattern p = Pattern.compile(".*\\d+.*");
		    Matcher m = p.matcher(str);
		    if (m.matches()) {
		    	bool = true;
		    }
			return bool;
		}
		
		/**
		 * 
		* @Title: isEng
		* @Description:是否是英文
		* author: Li Xiaoxiao 
		* @param @param str
		* @param @return     
		* @return boolean     
		* @throws
		 */
		public boolean isEng(String str){
			boolean bool=true;
			 String eng="abcdefghijklmnopqrstuvwxyz";
			 if(eng.contains(str)){
					bool=true; 
			 }else{
					bool=false; 
				}
				return bool;
		}
		
		
		
  @InitBinder  
  public void initBinder(WebDataBinder binder) {  
      // 设置List的最大长度  
      binder.setAutoGrowCollectionLimit(30000);  
      binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
  } 
}


