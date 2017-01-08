package bss.controller.pms;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ses.dao.oms.OrgnizationMapper;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.model.oms.PurchaseDep;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.oms.PurchaseOrgnizationServiceI;
import ses.util.DictionaryDataUtil;
import bss.controller.base.BaseController;
import bss.model.pms.CollectPlan;
import bss.model.pms.CollectPurchase;
import bss.model.pms.PurchaseDetail;
import bss.model.pms.PurchaseRequired;
import bss.service.pms.CollectPlanService;
import bss.service.pms.CollectPurchaseService;
import bss.service.pms.PurchaseDetailService;
import bss.service.pms.PurchaseRequiredService;
import bss.service.pms.impl.CollectPlanServiceImpl;

import com.github.pagehelper.PageInfo;

import bss.util.ExcelUtil;
import common.annotation.CurrentUser;
import common.bean.ResponseBean;
import common.constant.Constant;

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
  private OrgnizationMapper oargnizationMapper;
  
  
  @Autowired
  private PurchaseOrgnizationServiceI purchaseOrgnizationServiceI;
  
  @Autowired
  private PurchaseDetailService purchaseDetailService;
  
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
    public String queryPlan(PurchaseRequired purchaseRequired,Integer page,Model model){
    purchaseRequired.setIsMaster(1);
    if(purchaseRequired.getStatus()==null){
    	purchaseRequired.setStatus("3");
    }else if(purchaseRequired.getStatus().equals("total")){
    	purchaseRequired.setSign("3");
    	purchaseRequired.setStatus(null);
    }
    
    
    List<PurchaseRequired> list = purchaseRequiredService.query(purchaseRequired,page==null?1:page);
    PageInfo<PurchaseRequired> info = new PageInfo<>(list);
    model.addAttribute("info", info);
    model.addAttribute("inf", purchaseRequired);
    List<DictionaryData> dic = dictionaryDataServiceI.findByKind("6");
    model.addAttribute("dic", dic);

//    Map<String,Object> map = new HashMap<String,Object>();
//    List<Orgnization> requires = oargnizationMapper.findOrgPartByParam(map);
//    model.addAttribute("requires", requires);
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
    public String queryCollect(CollectPlan collectPlan,Integer page,Model model,String type) {
    //下达状态
    collectPlan.setStatus(1);
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
    public String queryCollect(@CurrentUser User user,CollectPlan collectPlan,String uniqueId,String goodsType,HttpServletRequest request) {
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
					
					
				}
			}
					BigDecimal budget=BigDecimal.ZERO;
					for(PurchaseRequired pr:list){
						if(pr.getSeq().equals("一")){
							budget=budget.add(pr.getBudget());
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
					
//					String[] uid = uniqueId.split(",");
//					CollectPurchase c=new CollectPurchase();
//					for(String u:uid){
//						c.setCollectPlanId(id);
//						c.setPlanNo(u);
//						collectPurchaseService.add(c);
//					}
					
					
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
		public String update(CollectPlan collectPlan){
			CollectPlan plan = collectPlanService.queryById(collectPlan.getId());
			String [] planNo = collectPlan.getPlanNo().split(",");
			List<PurchaseRequired> list=new LinkedList<PurchaseRequired>();
			CollectPurchase c=new CollectPurchase();
			PurchaseRequired p=new PurchaseRequired();
			for(String no:planNo){
				c.setCollectPlanId(collectPlan.getId());
				c.setPlanNo(no);
				
				//保存至中间表
				collectPurchaseService.add(c);
				
				p.setUniqueId(no);
				p.setIsMaster(1);
				List<PurchaseRequired> one = purchaseRequiredService.queryUnique(p);
				p.setStatus("5");//修改
				p.setIsMaster(null);
				purchaseRequiredService.updateStatus(p);
				list.addAll(one);
			}
			BigDecimal budget=BigDecimal.ZERO;
			for(PurchaseRequired pr:list){
				if(pr.getSeq().equals("一")){
					budget=budget.add(pr.getBudget());
				}
			}
			
			BigDecimal decimal = plan.getBudget();
			BigDecimal budget2=	decimal.add(budget);
			
			plan.setBudget(budget2);
			collectPlanService.update(plan);
			
 
			
			
			return "";
		}
		
		
		/**
     * 
    * @Title: upload
    * @Description: 采购计划导入
    * @author: LChenHao 
    * @return     
    * @return String     
    * @throws
     */
  @RequestMapping(value = "/upload" )
  public String uploadFile(@CurrentUser User user,MultipartFile file,Model model)throws IOException{
    ResponseBean bean = new ResponseBean();
    if ( file == null) {
      bean.setSuccess(false);
         //bean.setObj("文件不能为空");
         //return bean;
    }
    String fileName = file.getOriginalFilename();  
    if ( !fileName.endsWith(".xls") && !fileName.endsWith(".xlsx")) {
      bean.setSuccess(false);
      bean.setObj("文件格式不支持");
      //return bean;
    }  
    List<PurchaseRequired> list = new ArrayList<PurchaseRequired>();
    try {
      Workbook workbook = WorkbookFactory.create(file.getInputStream());
        
      //表头导入
      Sheet sheet = workbook.getSheetAt(0);
      Row firstRow = sheet.getRow(0);
      CollectPlan collect = new CollectPlan();
      Cell planName = firstRow.getCell(0);
      collect.setFileName(planName.toString());
        
      String id = UUID.randomUUID().toString().replaceAll("-", "");
      collect.setId(id);
      collect.setFileName(planName.toString());
      collectPlanService.add(collect);
      int endNum = 0;//最底层记录数
      int meanNum = 0;//中间数
      List<String> parentId = new ArrayList<String>();
      //表内容
      for (int i = 3 ; i < sheet.getLastRowNum();i++) {
        Row row = sheet.getRow(i);
        String pId = UUID.randomUUID().toString().replaceAll("-", "");
        parentId.add(pId);
        PurchaseRequired pr = new PurchaseRequired();
        pr.setId(pId);
        Cell xh = row.getCell(0);//序号
        if ( xh.toString() != null ) {
          pr.setSeq(xh.toString());
        }
        Cell xqbm = row.getCell(1);//需求部门
        if ( xqbm.toString() != null ) {
          pr.setDepartment(xqbm.toString());
        }
        Cell wzmc = row.getCell(2);//物资类别及名称
        if ( wzmc.toString() != null ) {
          pr.setGoodsName(wzmc.toString());
        }
        Cell ggxh = row.getCell(3);//规格型号
        if ( ggxh.toString() != null ) {
          pr.setPurchaseType(ggxh.toString());
        }
        Cell zlbz = row.getCell(4);//质量技术标准
         
        Cell jldw = row.getCell(5);//计量单位
          
        Cell cgsl = row.getCell(6);//采购数量
          
        Cell dj = row.getCell(7);//单价
          
        Cell ysje = row.getCell(8);//预算金额（万）
          
          
        Cell jhqx = row.getCell(9);//交货期限
        if ( jhqx.toString() != null ) {
          pr.setDeliverDate(jhqx.toString());
        }
          
        Cell gysm = row.getCell(10);//供应商名称
        /*if ( gysm.toString() != null ) {
          pr.setDeliverDate(gysm.toString());
        }*/
          
        Cell cgfs = row.getCell(11);//采购方式
          
        Cell cgjg = row.getCell(12);//采购机构
        if ( cgjg.toString() != null ) {
          pr.setOrganization(cgjg.toString());
        }
          
        Cell bz = row.getCell(13);//备注
        /*if(bz.toString()!=null){
          pr.setGoodsName(bz.toString());
        }*/
         
        if ( i == 3 ) {
          pr.setParentId("1");
        } else {
          BigDecimal count = new BigDecimal(cgsl.toString());
          if ( count != null ) {
            if ( meanNum == 0 ) {
              endNum = i;
            }
            meanNum++;
            pr.setParentId(parentId.get(endNum - 3 ) );
          } else {
            pr.setParentId(parentId.get(i - 3 ) );
          }
        }
        purchaseRequiredService.add(pr);
      }
    } catch ( Exception e ) {
      bean.setSuccess(false);
      bean.setObj(e.getMessage());
    }

    return "bss/pms/collect/collectlist";
  }

}


