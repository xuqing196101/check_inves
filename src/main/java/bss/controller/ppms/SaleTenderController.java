/**
 * 
 */
package bss.controller.ppms;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.sms.Supplier;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.sms.SupplierAuditService;
import ses.service.sms.SupplierExtRelateService;
import ses.service.sms.SupplierExtUserServicel;
import ses.service.sms.SupplierQuoteService;
import ses.service.sms.SupplierService;
import ses.util.DictionaryDataUtil;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.SaleTender;
import bss.service.ppms.FlowMangeService;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.SaleTenderService;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;

import common.constant.Constant;
import common.model.UploadFile;
import common.service.DownloadService;
import common.service.UploadService;

/**
 * @Description: 发售标书
 *	 
 * @author Wang Wenshuai
 * @version 2016年10月19日下午2:27:04
 * @since  JDK 1.7
 */
@Controller
@Scope("prototype")
@RequestMapping("/saleTender")
public class SaleTenderController {



  @Autowired
  private SupplierExtRelateService extRelateService; //关联表
  @Autowired
  private SaleTenderService saleTenderService; //关联表
  @Autowired
  private SupplierAuditService auditService;//查询所有供应商

  @Autowired
  private SupplierService supplierService;//查询全部的供应商

  @Autowired
  private SupplierExtUserServicel extUserServicel; //模板引入
  
  @Autowired
  private DictionaryDataServiceI dictionaryDataServiceI;//TypeId
  @Autowired
  private SupplierQuoteService supplierQuoteService;
  @Autowired
  private UploadService uploadService;
  @Autowired
  private DownloadService downloadService;
  
  @Autowired
  private FlowMangeService flowMangeService;

  /**
   * @Fields projectService : 引用项目业务实现接口
   */
  @Autowired
  private ProjectService projectService;

  /**
   * @Fields packageService : 引用分包业务逻辑接口
   */
  @Autowired
  private PackageService packageService;


  /**
   * @Description:展示发售标书列表
   *
   * @author Wang Wenshuai
   * @version 2016年10月19日 下午2:39:16  
   * @param @param prjectId      
   * @return void
   */
  @RequestMapping("/list")
  public String  list(Model model,String projectId, String page, SaleTender saleTender,String supplierName){
    saleTender.setProjectId(projectId);
    Supplier supplier=new Supplier();
    supplier.setSupplierName(supplierName);
    saleTender.setSuppliers(supplier);
    List<SaleTender> list = saleTenderService.list(saleTender,page==null?1:Integer.valueOf(page));
    saleTenderService.getPackageNames(list);
    model.addAttribute("list", new PageInfo<>(list));
    model.addAttribute("projectId", projectId);
    model.addAttribute("saleTender",saleTender);
    model.addAttribute("supplierName",supplierName);
    return "bss/ppms/sall_tender/list";
  }


  /**
   * @Title: view
   * @author Shen Zhenfei 
   * @date 2016-12-12 下午4:40:49  
   * @Description: 根据包名，或者供应商
   * @param @param projectId
   * @param @param model
   * @param @return      
   * @return String
   * @throws UnsupportedEncodingException 
   */
  @RequestMapping("/view")
  public String view(String projectId, Model model,String supplierName,String armyBuinessTelephone,Integer statusBid, String flowDefineId,String ix) throws UnsupportedEncodingException{
    if(supplierName != null && !"".equals(supplierName)){
      supplierName = URLDecoder.decode(supplierName,"UTF-8"); 
    }
    if(armyBuinessTelephone != null && !"".equals(armyBuinessTelephone)){
      armyBuinessTelephone = URLDecoder.decode(armyBuinessTelephone,"UTF-8");  
    }

    //项目信息
    Project project=projectService.selectById(projectId);

    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("projectId",projectId);
    List<Packages> list = packageService.findPackageById(map);

    List<Packages> lists = new ArrayList<>();

    if(list != null && list.size()>0){
      //            for(Packages ps:list){
      //                saleTender.setPackages(ps.getId());
      //                saleTender.setSuppliers(supplier);
      //               //供应商
      //                List<SaleTender> saleTenderList = saleTenderService.getPackegeSupplier(saleTender);
      //                if(saleTenderList.size()>0){
      //                	ps.setSaleTenderList(saleTenderList);
      //                }
      //            }
      for(int i=0;i<list.size();i++){
        Packages packages = list.get(i);
        Supplier supplier=new Supplier();
        if(supplierName!=null){
          supplier.setSupplierName("%"+supplierName+"%");
        }
        if(armyBuinessTelephone!=null){
          supplier.setArmyBuinessTelephone(armyBuinessTelephone);
        }
        SaleTender saleTender = new SaleTender();
        if(statusBid!=null){
          saleTender.setStatusBid(statusBid.shortValue());
        }
        saleTender.setProject(new Project(projectId));
        saleTender.setPackages(packages.getId());
        saleTender.setSuppliers(supplier);
        List<SaleTender> saleTenderList = saleTenderService.getPackegeSupplier(saleTender);
        //	if(saleTenderList.size()>0){
        packages.setSaleTenderList(saleTenderList);
        DictionaryData dat = DictionaryDataUtil.findById(packages.getProjectStatus());
        if(dat!=null){
          packages.setProjectStatus(dat.getCode());
        }
        lists.add(packages);
        //    }
      }

    }
    DictionaryData findById = DictionaryDataUtil.findById(project.getPurchaseType());
    model.addAttribute("status", findById.getCode());
    model.addAttribute("kind", DictionaryDataUtil.find(5));
    model.addAttribute("packageList", lists);
    model.addAttribute("project", project);
    model.addAttribute("supplierName",supplierName);
    model.addAttribute("armyBuinessTelephone",armyBuinessTelephone);
    model.addAttribute("statusBid",statusBid);
    model.addAttribute("flowDefineId", flowDefineId);
    model.addAttribute("ix",ix);
    return "bss/ppms/sall_tender/view";
  }


  /**
   * @Title: register
   * @author Shen Zhenfei 
   * @date 2016-12-13 上午11:20:16  
   * @Description: 登记其他采购方式
   * @param @param id
   * @param @param packId
   * @param @param saleTender
   * @param @return      
   * @return String
   */
  @RequestMapping("/register")
  public String register(String id,String packId,String projectId,SaleTender saleTender,String ix){

    saleTender.setUpdatedAt(new Date());
    saleTender.setCreatedAt(new Date());
    saleTender.setStatusBid((short)2);
    saleTenderService.update(saleTender);

    return "redirect:view.html?projectId="+projectId+"&ix="+ix;
  }

  /**
   * @Description:展示供应商列表
   *
   * @author Wang Wenshuai
   * @version 2016年10月19日 下午2:41:09  
   * @param @return      
   * @return String
   */
  @RequestMapping("/showSupplier")
  public  String showSupplier(Model model, String projectId,String page,Supplier supplier,String ix){
    //查询list方法里面的供应商id 为了过滤供应商 已经有的就不显示了
    SaleTender saleTender=new SaleTender();
    saleTender.setProjectId(projectId);
    List<SaleTender> list = saleTenderService.list(saleTender,page==null||"".equals(page)?1:Integer.valueOf(page));
    List<String> stsupplierIds=new ArrayList<String>();
    if(list.size()>0){
      for(SaleTender st:list){
        stsupplierIds.add(st.getSuppliers().getId());
      }
      supplier.setStsupplierIds(stsupplierIds);
    }
    List<Supplier> allSupplier = auditService.selectSaleTenderSupplier(supplier, page == null || page.equals("") ? 1 : Integer.valueOf(page));
    //当前项目的所有包
    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("projectId", projectId);
    List<Packages> listPackage = supplierQuoteService.selectByPrimaryKey(map, null);
    model.addAttribute("listPackage", listPackage);
    model.addAttribute("list", new PageInfo<>(allSupplier));
    model.addAttribute("projectId", projectId);
    model.addAttribute("supplierName", supplier.getSupplierName());
    model.addAttribute("ix", ix);
    return "bss/ppms/sall_tender/supplier_list";
  }


  /**
   * @Title: showAllSupplier
   * @author Shen Zhenfei 
   * @date 2016-12-13 上午10:50:50  
   * @Description: 公开招标获取全部供应商
   * @param @param model
   * @param @param projectId
   * @param @param page
   * @param @param packId
   * @param @param supplier
   * @param @return      
   * @return String
   */
  @RequestMapping("/showAllSuppliers")
  public  String showAllSuppliers(Model model, String flowDefineId, String projectId,String page,String packId,Supplier supplier,String ix,String selectValue, HttpServletRequest request){
    SaleTender saleTender=new SaleTender();
    Project project = new Project();
    project.setId(projectId);
    saleTender.setProject(project);
    saleTender.setPackages(packId);
    List<SaleTender> list = saleTenderService.getPackegeSuppliers(saleTender);
    List<String> stsupplierIds=new ArrayList<String>();
    if (list.size()>0) {
      for (SaleTender st:list) {
        if (st != null && st.getSuppliers() != null && st.getSuppliers().getId() != null) {
          stsupplierIds.add(st.getSuppliers().getId());
        }
      }
      if (stsupplierIds != null && stsupplierIds.size() != 0) {
        supplier.setStsupplierIds(stsupplierIds);
      }
   }
  //该环节设置为执行中状态
  flowMangeService.flowExe(request, flowDefineId, projectId, 2);
  Project projects = projectService.selectById(projectId);
  DictionaryData findById = DictionaryDataUtil.findById(projects.getPurchaseType());
  model.addAttribute("kind", findById.getCode());
  List<Supplier> allSupplier = auditService.selectSaleTenderSupplier(supplier, page == null || page.equals("") ? 1 : Integer.valueOf(page));
  model.addAttribute("list",new PageInfo<>(allSupplier));
  model.addAttribute("packId", packId);
  model.addAttribute("projectId", projectId);
  model.addAttribute("supplierName", supplier.getSupplierName());
  model.addAttribute("ix", ix);
  model.addAttribute("selectValue", selectValue);
  return "bss/ppms/sall_tender/suppliers_list";
}

  
  /**
   * 
   *〈判断最少供应商〉
   *〈详细描述〉
   * @author FengTian
   * @param projectId
   * @return
   */
  @RequestMapping(value="/number",produces = "text/html;charset=UTF-8")
  @ResponseBody
  public String number(String projectId){
      HashMap<String, Object> map = new HashMap<String, Object>();
      map.put("projectId",projectId);
      List<Packages> list = packageService.findPackageById(map);
      if(list != null && list.size() > 0){
          for (int i = 0; i < list.size(); i++ ) {
              Project project = projectService.selectById(projectId);
              SaleTender saleTender = new SaleTender();
              saleTender.setProject(new Project(projectId));
              saleTender.setPackages(list.get(i).getId());
              //获取包下的供应商
              List<SaleTender> saleTenderList = saleTenderService.getPackegeSupplier(saleTender);
              //判断供应商人数是否和项目的最少供应商符合
              if(saleTenderList.size() < project.getSupplierNumber()){
                  Packages packages = new Packages();
                  packages.setName(list.get(i).getName());
                  packages.setProject(project);
                  String json = JSON.toJSONString(packages);
                  return json;
              }
          }
      }
      return "1";
  }
  
  /**
   * 
   *〈是否转竞争性谈判〉
   *〈详细描述〉
   * @author FengTian
   * @param projectId
   * @return
   */
  @RequestMapping("/transfer")
  public String transfer(String projectId){
      if(StringUtils.isNotBlank(projectId)){
          Project project = projectService.selectById(projectId);
          if(project != null){
              //修改项目状态
              project.setStatus(DictionaryDataUtil.getId("JZXTP"));
              projectService.update(project);
          }
      }
      return "redirect:project/excute.html?id="+projectId;
  }

@RequestMapping("/startTask")
@ResponseBody
public String startTask(String id){
  SaleTender saleTender=new SaleTender();
  saleTender.setPackages(id);
  List<SaleTender> list = saleTenderService.getPackegeSuppliers(saleTender);
  if(list != null && list.size() >0){
    return "1";
  }else{
    return "0";
  }

}

/**
 * 
 * @Description:修改状态
 *
 * @author Wang Wenshuai
 * @version 2016年10月19日 下午2:43:04  
 * @param @return      
 * @return String
 */
@RequestMapping("/uploadDeposit")
public String uploadDeposit(){
  return null;
}

/**
 * @Description:打开upload
 *
 * @author Wang Wenshuai
 * @version 2016年10月20日 下午1:39:13  
 * @param @return      
 * @return String
 */
@RequestMapping("/showUpload")
public String showUpload(String projectId,Model model,String id){


  //打印凭证
  DictionaryData dd = new  DictionaryData();
  dd.setCode("SALE_TENDER_DYPZ");
  List<DictionaryData> find = dictionaryDataServiceI.find(dd);
  if(find != null && find.size() !=0 ){
    model.addAttribute("saleTenderDypz", find.get(0).getId());
  }
  //发票上传
  dd = new  DictionaryData();
  dd.setCode("SALE_TENDER_FPSC");
  find = dictionaryDataServiceI.find(dd);
  if(find != null && find.size() !=0 ){
    model.addAttribute("saleTenderFpsc", find.get(0).getId());
  }
  //招标系统key
  Integer tenderKey = Constant.TENDER_SYS_KEY;
  model.addAttribute("projectId", projectId);
  model.addAttribute("saleId", id);
  model.addAttribute("tenderKey", tenderKey);


  return "bss/ppms/sall_tender/upload";
}

/**
 *〈简述〉打开上传标书费页面
 *〈详细描述〉
 * @author Song Biaowei
 * @param projectId 主键
 * @param model 模型
 * @param id 主键
 * @return String
 */
@RequestMapping(value = "uploadBsf")
public String uploadBsf(String projectId,Model model,String id){
  DictionaryData dd = new  DictionaryData();
  List<DictionaryData> find = dictionaryDataServiceI.find(dd);
  //发票上传
  dd = new  DictionaryData();
  dd.setCode("SALE_TENDER_BSF_FPSC");
  find = dictionaryDataServiceI.find(dd);
  if(find != null && find.size() !=0 ){
    model.addAttribute("saleTenderBsfFpsc", find.get(0).getId());
  }
  //招标系统key
  Integer tenderKey = Constant.TENDER_SYS_KEY;
  model.addAttribute("projectId", projectId);
  model.addAttribute("saleId", id);
  model.addAttribute("tenderKey", tenderKey);
  return "bss/ppms/sall_tender/upload_bsf";
}

/**
 *〈简述〉ajax修改状态
 *〈详细描述〉
 * @author Song Biaowei
 * @param projectId
 * @param saleId
 * @param statusBid
 * @return
 */
@ResponseBody
@RequestMapping("/uploadBsfAjax")
public String uploadBsfAjax(String projectId,String saleId,String statusBid){
  String upload = "success";
  saleTenderService.download(projectId,saleId);
  return JSON.toJSONString(upload);
}

/**
 * @Description:缴费
 *
 * @author Wang Wenshuai
 * @version 2016年10月20日 下午1:56:17  
 * @param @return      
 * @return String
 */
@ResponseBody
@RequestMapping("/upload")
public String paymentUpload(String projectId,String saleId,String statusBid){
  String upload = saleTenderService.upload(projectId,saleId,statusBid);
  return JSON.toJSONString(upload);
}
/**
 * @Description:保存供应商信息
 *
 * @author Wang Wenshuai
 * @version 2016年10月20日 下午2:39:12  
 * @param @return      
 * @return String
 */
@RequestMapping("/save")
public String save(String ids,String packages,String status,HttpServletRequest sq,String projectId,String ix){
  User attribute = (User) sq.getSession().getAttribute("loginUser");
  if (attribute != null){
    List<String> listIds = Arrays.asList(ids.split(","));
    for (String str:listIds){
      saleTenderService.insert(new SaleTender(projectId, (short)1, str, (short)2, attribute.getId(),packages));
    }
  }
  return "redirect:view.html?projectId="+projectId+"&ix="+ix;
}

/**
 * @Title: saveSupplier
 * @author Shen Zhenfei 
 * @date 2016-12-13 上午10:50:04  
 * @Description: 公开招标添加供应商
 * @param @param ids
 * @param @param packages
 * @param @param status
 * @param @param sq
 * @param @param projectId
 * @param @return      
 * @return String
 */
@RequestMapping("/saveSupplier")
public String saveSupplier(String ids,String packages,String status,HttpServletRequest sq,String projectId,String ix){
  User attribute = (User) sq.getSession().getAttribute("loginUser");
  if (attribute != null && ids != null && !"".equals(ids)){
    List<String> listIds=Arrays.asList(ids.split(","));
    for(String str:listIds){
      saleTenderService.insert(new SaleTender(projectId, (short)2, str, (short)2, attribute.getId(),packages));
    }
    Project project = projectService.selectById(projectId);
    projectService.updateStatus(project, "FSBSZ");
  }
  String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
  StringBuffer sb = new StringBuffer();  
  Random random = new Random();  
  for (int i = 0; i < 15; i++) {  
      sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));  
  }
  String randomCode = sb.toString();
  return "redirect:view.html?projectId="+projectId+"&ix="+ix+"&randomCode="+randomCode;
}

/**
 * @Description:下载
 *
 * @author Wang Wenshuai
 * @version 2016年10月21日 上午9:59:02  
 * @param @return      
 * @return String
 */
@RequestMapping("/download")
public String download(HttpServletRequest request, HttpServletResponse response, String projectId,String id){
  String typeId = DictionaryDataUtil.getId("PROJECT_BID");
  List<UploadFile> files = uploadService.getFilesOther(projectId, typeId, Constant.TENDER_SYS_KEY+"");
  if (files != null && files.size() > 0) {
    downloadService.downloadOther(request, response, files.get(0).getId(), Constant.TENDER_SYS_KEY+"");
  }
  //saleTenderService.download(projectId,id);
  return "redirect:list.html?projectId="+projectId;
}

/**
 *〈简述〉 根据供应商下载标书
 *〈详细描述〉
 * @author Ye MaoLin
 * @param request
 * @param id 项目id
 * @param model
 * @param response
 * @return
 * @throws IOException 
 */
@RequestMapping("/downloads")
public void downloads(HttpServletRequest request, HttpServletResponse response, String projectId, String id){
  String typeId = DictionaryDataUtil.getId("PROJECT_BID");
  List<UploadFile> files = uploadService.getFilesOther(projectId, typeId, Constant.TENDER_SYS_KEY+"");

  if (files != null && files.size() > 0) {
    downloadService.downloadOther(request, response, files.get(0).getId(), Constant.TENDER_SYS_KEY+"");
    /*
     * 按包下载
     * SaleTender saleTender = new SaleTender();
            saleTender.setId(id);
            List<SaleTender> sts = saleTenderService.findByCon(saleTender);
            if (sts != null && sts.size() > 0) {
              Supplier supplier = sts.get(0).getSuppliers();
              if (supplier != null) {
                HashMap<String, String> returnMap = saleTenderService.downloadBidFile(projectId, request, supplier.getId());
                String filePath = returnMap.get("filePath");
                String fileName = returnMap.get("fileName");
                downloadService.downLoadFile(request, response, filePath);
              }
            }*/
  }
  //saleTenderService.download(projectId,id);
  //return "redirect:view.html?projectId="+projectId;
}
/**
*〈简述〉 根据供应商参与包 处理相关文件
*〈详细描述〉
* @author YangHongLiang
* @param request
* @param id 项目id
* @param model
* @param response
* @return
 * @throws Exception 
* @throws IOException 
*/
@RequestMapping("/processingDocuments")
@ResponseBody
public String processingDocuments(Model model,HttpServletRequest request, HttpServletResponse response,String projectId,String suppliersID) throws Exception{
	//判断是否上传招标文件
    String typeId = DictionaryDataUtil.getId("PROJECT_BID");
    Project project = projectService.selectById(projectId);
	List<UploadFile> files = uploadService.getFilesOther(projectId, typeId, Constant.TENDER_SYS_KEY+"");
	if (files != null && files.size() > 0 ){
	//调用生成word模板传人 标识0 表示 只是生成 拆包部分模板
    String filePath = extUserServicel.downLoadBiddingDoc(request,projectId,1,suppliersID);
    System.out.println(filePath+"  filePath");
    if (StringUtils.isNotBlank(filePath)){
       model.addAttribute("filePath", filePath);
    	}
    System.out.println(files.get(0).getId()+"  files.get(0).getId()");
      model.addAttribute("fileId", files.get(0).getId());
      model.addAttribute("project", project);
      return  "bss/ppms/sall_tender/before_download";
	}else{
		//model.addAttribute("filePath", "0");
	      return "error不能下载";

	}
}

@RequestMapping("/downList")
public String downList(Model model, String projectId, String flowDefineId){
  Project project = projectService.selectById(projectId);
  SaleTender saleTender = new SaleTender();
  saleTender.setProject(project);
  //去重后的供应商
  List<SaleTender> saleTenders = saleTenderService.findByCon(saleTender);
  for (int i = 0; i < saleTenders.size()-1; i++) {
    SaleTender st = saleTenders.get(i);
    for (int j = saleTenders.size()-1; j > i; j--) {
      SaleTender st2 = saleTenders.get(j);
      if (st.getSuppliers().getId().equals(st2.getSuppliers().getId())) {
        saleTenders.remove(st2);
      }
    }
  }

  for (SaleTender saleTender2 : saleTenders) {
    String packageName = "";
    SaleTender st = new SaleTender();
    st.setSuppliers(saleTender2.getSuppliers());
    st.setProject(project);
    //该供应商参与的包
    List<SaleTender> ls = saleTenderService.findByCon(st);
    if (ls != null && ls.size() > 0) {
      for (int i = 0; i < ls.size(); i++) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("id", ls.get(i).getPackages());
        List<Packages> list = packageService.findPackageById(map);
        if (list != null && list.size() > 0) {
          String pName = list.get(0).getName();
          if (i == 0) {
            packageName += pName;
          } else {
            packageName += ","+pName;
          }
        }
      }
    }
    saleTender2.setPackageNames(packageName);
  }
  model.addAttribute("sds", saleTenders);
  return "bss/ppms/sall_tender/download_list";
}

@RequestMapping("/manage")
public String manage(Model model, String projectId, String flowDefineId){
  Project project = projectService.selectById(projectId);
  DictionaryData findById = DictionaryDataUtil.findById(project.getPurchaseType());
  model.addAttribute("kind", findById.getCode());
  model.addAttribute("projectId", projectId);
  model.addAttribute("flowDefineId", flowDefineId);
  return "bss/ppms/sall_tender/manage";
}

/**
 * 
 *〈简述〉移除供应商
 *〈详细描述〉
 * @author Wang Wenshuai
 * @param supplierId
 * @param packagesId
 * @return
 */
@ResponseBody
@RequestMapping("/deleteSale")
public String delSaleDelete(String supplierId,String packagesId){
  String status = saleTenderService.delSale(supplierId, packagesId);
  return status;

}
}
