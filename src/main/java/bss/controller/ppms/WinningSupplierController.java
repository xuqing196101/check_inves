/**
 * 
 */
package bss.controller.ppms;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.DictionaryData;
import ses.model.bms.StationMessage;
import ses.model.bms.User;
import ses.model.sms.Quote;
import ses.service.bms.StationMessageService;
import ses.service.bms.UserServiceI;
import ses.service.sms.SupplierQuoteService;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;

import com.alibaba.fastjson.JSON;











import common.annotation.CurrentUser;
import common.constant.Constant;
import common.model.UploadFile;
import common.service.UploadService;
import bss.controller.base.BaseController;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.SupplierCheckPass;
import bss.model.ppms.theSubject;
import bss.service.ppms.AduitQuotaService;
import bss.service.ppms.FlowMangeService;
import bss.service.ppms.PackageService;
import bss.service.ppms.ProjectDetailService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.SupplierCheckPassService;
import bss.service.ppms.theSubjectService;
import bss.service.ppms.impl.theSubjectServiceImpl;

/**
 * @Description: 中标供应商
 *
 * @author Wang Wenshuai
 * @version 2016年10月11日下午2:51:13
 * @since  JDK 1.7
 */
@Controller
@Scope("prototype")
@RequestMapping("/winningSupplier")
public class WinningSupplierController extends BaseController {
  /** OK */
  private final static String OK = "ok";
  /** SCCUESS */
  private static final String SUCCESS = "SCCUESS";
  /** ERROR */
  private static final String ERROR = "ERROR";
  /** ZERO */
  private static final Integer ZERO = 0;
  /** ONE */
  private static final Integer ONE = 1;
  /** TWO */
  private static final Integer TWO = 2;
  /**
   * 评审通过供应商
   */
  @Autowired
  SupplierCheckPassService checkPassService; 

  @Autowired  
  PackageService packageService;

  @Autowired
  SupplierQuoteService supplierQuoteService;

  @Autowired
  AduitQuotaService aduitQuotaService;

  @Autowired
  private StationMessageService stationMessageService;

  @Autowired
  private UserServiceI userServiceI;

  @Autowired
  private FlowMangeService flowMangeService;//环节

  @Autowired
  private ProjectDetailService detailService;
  
  @Autowired
  private ProjectService projectService;
	@Autowired
	private theSubjectService theSubjectService;

  /**
   * 文件上传
   */
  @Autowired
  UploadService  uploadService;
  /**
   *〈简述〉选择查看包
   *〈详细描述〉
   * @author Wang Wenshuai
   * @param model
   * @return
   */
  @RequestMapping("/selectSupplier")
  public String selectWinningSupplier(Model model, String projectId, String flowDefineId){
    List<Packages> packList = packageService.listSupplierCheckPass(projectId);
    model.addAttribute("packList", packList);
    model.addAttribute("projectId", projectId);
    model.addAttribute("flowDefineId", flowDefineId);
    Project project = projectService.selectById(projectId);
    DictionaryData findById = DictionaryDataUtil.findById(project.getPurchaseType());
    //获取已有中标供应商的包组
    String[] packcount = checkPassService.selectWonBid(projectId);
    if (packList.size() != packcount.length){
      model.addAttribute("error", ERROR);
    }
    model.addAttribute("kind", findById.getCode());
    String id = DictionaryDataUtil.getId("DYLY");
    if(project.getPurchaseType().equals(id)){
        List<Packages> pack = packageService.supplierCheckPa(projectId);
        model.addAttribute("packLi", pack);
        return "bss/ppms/winning_supplier/lists";
    }else{
        return "bss/ppms/winning_supplier/list";
    }
  }
  
  /**
   * 
   *〈简述〉
   *〈详细描述〉
   * @author FengTian
   * @param model
   * @param id
   * @return
   */
  @RequestMapping("/packageSuppliers")
  public String packageSuppliers(Model model, String id, String flowDefineId, String projectId, HttpServletRequest sq){
      SupplierCheckPass checkPass = new SupplierCheckPass();
      checkPass.setPackageId(id);
      //根据包ID获取ID
      List<SupplierCheckPass> listCheckPass = checkPassService.listCheckPass(checkPass);
      SupplierCheckPass pass = new SupplierCheckPass();
      pass.setId(listCheckPass.get(0).getId());
      pass.setIsWonBid((short)1);
      //查询已经中标的供应商
      List<SupplierCheckPass> listSupplierCheckPass = checkPassService.listCheckPass(checkPass);
      for (SupplierCheckPass supplierCheckPass : listSupplierCheckPass) {
        //查询报价历史记录
        if(supplierCheckPass != null && supplierCheckPass.getSupplier() != null ){
          Quote quote = new Quote();
          quote.setPackageId(id);
          quote.setSupplierId(supplierCheckPass.getSupplier().getId());
          List<Quote> quoteList = supplierQuoteService.selectQuoteHistoryList(quote);
          supplierCheckPass.getSupplier().setListQuote(quoteList);
        }
      }

      model.addAttribute("supplierCheckPass", listSupplierCheckPass);
      model.addAttribute("supplierCheckPassJosn",JSON.toJSONString(listSupplierCheckPass));
      model.addAttribute("flowDefineId", flowDefineId);
      model.addAttribute("projectId", projectId);
      model.addAttribute("packageId", listCheckPass.get(0).getId());
      model.addAttribute("pid", id);

      //获取已有中标供应商的包组
      String[] packcount = checkPassService.selectWonBid(projectId);
      List<Packages> packList = packageService.listSupplierCheckPass(projectId);
      if (packList.size() != packcount.length){
        model.addAttribute("error", ERROR);
      }
      //             //修改流程状态
      flowMangeService.flowExe(sq, flowDefineId, projectId, 2);
      
      Quote quote = new Quote();
      quote.setPackageId(id);
      List<Quote> quoteList = supplierQuoteService.selectQuoteHistoryList(quote);
      if (quoteList.size()>0) {
        if (quoteList.get(0).getQuotePrice() == null || quoteList.get(0).getQuotePrice().equals(new BigDecimal(0))){
          model.addAttribute("quote", 0);//提示唱总价
        }else if(quoteList.get(0).getQuotePrice() != null&&!quoteList.get(0).getQuotePrice().equals(new BigDecimal(0))){
          model.addAttribute("quote", 1);//提示唱明细
        }
      }
      
      HashMap<String,Object> map = new HashMap<>();
      map.put("packageId", id);
      List<ProjectDetail> detailList = detailService.selectById(map);
      model.addAttribute("detailList", detailList);
      return "bss/ppms/winning_supplier/supplier_list";
  }


  /**
   * 
   *〈简述〉获取包下所有供应商信息
   *〈详细描述〉
   * @author Wang Wenshuai update Ma Mingwei
   * @param model
   * @param packageId   供应商id组,是一个","分开的字符串
   * @param pid 是真正的packageId
   * @param projectId
   * @param ids 是T_BSS_PPMS_SUPPLIER_CHECK_PASS表的id组
   * @param flowDefineId
   * @param passquote此处传过来是否为唱明细还是唱总价，方法里面有判断，可以不用传，后期根据需要可以把方法里的quote判断删除了
   * @return 路径
   */
  @RequestMapping("/packageSupplier")
  public String selectpackage(String inputSubjectBtn, Model model,String passquote, String pid, String packageId,String ids, String priceRatios, String flowDefineId,String projectId,HttpServletRequest sq,Integer view){
	  //将传过来前面判断好的唱总价还是明细放到model中，在下个页面进行判断
	  if(passquote != null) {
		  model.addAttribute("passquote", passquote);
	  }
	  //调用service层方法把传过来的供应商id，确定为中标 @author Ma Mingwei
	  if(!"priceRatios".equals(priceRatios)) {
		  if(pid != null) {
			  checkPassService.changeSupplierWonTheBidding(ids,priceRatios);
		  }
	  }
	  
    if (view != null && view == 1) {
      SupplierCheckPass scp = new SupplierCheckPass();
      scp.setPackageId(packageId);
      scp.setIsWonBid((short)1);
      List<SupplierCheckPass> listCheck = checkPassService.listCheckPassBD(scp);
      String[] rat = ratio(listCheck.size());
      for (int i = 0,l = listCheck.size(); i < l; i++ ) {
        if (listCheck.get(i).getIsWonBid() == 1 && listCheck.get(i).getWonPrice() == null && listCheck.get(i).getPriceRatio() ==null ){
          Double  price = (Double.parseDouble(rat[i])/100)*Double.parseDouble(listCheck.get(0).getTotalPrice().toString());
          SupplierCheckPass supplierCheckPass = listCheck.get(i);
          supplierCheckPass.setWonPrice(new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_UP));
          supplierCheckPass.setPriceRatio(rat[i]);
          checkPassService.update(supplierCheckPass); 

        }

      }
    }
    SupplierCheckPass checkPass = new SupplierCheckPass();
    //checkPass.setPackageId(packageId);
    //把传过来的supplierid即packageId，切割处理放到一个字符串里适宜sql语句
    String pids[] = packageId.split(",");
    String str_id = "";
    for (String id : pids) {
		str_id += "'" + id + "',";
	}
    str_id = str_id.substring(0,str_id.lastIndexOf(","));
    str_id = "(" + str_id + ")";
    //checkPass.setId(str_id);
    checkPass.setSupplierId(str_id);
    //查询是否中标条件---已中标的
    checkPass.setIsWonBid((short)1);
    checkPass.setPackageId(pid);
    
    List<SupplierCheckPass> listSupplierCheckPass = checkPassService.listCheckPassBD(checkPass);
    for (SupplierCheckPass supplierCheckPass : listSupplierCheckPass) {
      //查询报价历史记录
      if(supplierCheckPass != null && supplierCheckPass.getSupplier() != null ){
    	  HashMap<String, Object> map=new HashMap<String, Object>();
    	  map.put("supplierId", supplierCheckPass.getSupplierId());
    	  map.put("packageId", supplierCheckPass.getPackageId());
    	  List<theSubject> theSubjects = theSubjectService.selectBysupplierIdAndPackagesId(map);
    	  if(theSubjects!=null&&theSubjects.size()>0){
    		  BigDecimal totalAmount = new BigDecimal(0+"");
    		  for(theSubject thesub:theSubjects){
    			  if(thesub.getDetailId()!=null){
    				  Double sum= thesub.getPurchaseCount()==null?0.0:Double.parseDouble(thesub.getPurchaseCount());
        			  BigDecimal price=thesub.getUnitPrice()==null?new BigDecimal(0):thesub.getUnitPrice();
        			  BigDecimal sumB = new BigDecimal(Double.toString(sum));  
        			  BigDecimal multiply = sumB.multiply(new BigDecimal(price+""));
        			  totalAmount=totalAmount.add(new BigDecimal(multiply+""));
    			  }
    			  
    		  }
    		  /*totalAmount=totalAmount.multiply(new BigDecimal(supplierCheckPass.getPriceRatio()+""));*/
    		  totalAmount=totalAmount.divide(new BigDecimal(10000+""));
    		  supplierCheckPass.setMoney(totalAmount);
    		  supplierCheckPass.setSubjects(theSubjects);
    	  }
    	  
        Quote quote = new Quote();
        quote.setPackageId(packageId);
        quote.setSupplierId(supplierCheckPass.getSupplier().getId());
        List<Quote> quoteList = supplierQuoteService.selectQuoteHistoryList(quote);
        supplierCheckPass.getSupplier().setListQuote(quoteList);
      }
    }

    model.addAttribute("supplierCheckPass", listSupplierCheckPass);
    model.addAttribute("supplierCheckPassJosn",JSON.toJSONString(listSupplierCheckPass));
    model.addAttribute("flowDefineId", flowDefineId);
    model.addAttribute("projectId", projectId);
    model.addAttribute("packageId", packageId);
    model.addAttribute("inputSubjectBtn", inputSubjectBtn);
    model.addAttribute("pid", pid);
    model.addAttribute("view", view);
   

    //获取已有中标供应商的包组
    String[] packcount = checkPassService.selectWonBid(projectId);
    List<Packages> packList = packageService.listSupplierCheckPass(projectId);
    if (packList.size() != packcount.length){
      model.addAttribute("error", ERROR);
    }
    //             //修改流程状态
    flowMangeService.flowExe(sq, flowDefineId, projectId, 2);
    
    //修改项目流程
/*    Project project = new Project();
    project.setId(projectId);
    project.setStatus(DictionaryDataUtil.getId("QRZBGYS"));
    projectService.update(project);*/
    //查询报价历史记录
    Quote quote = new Quote();
    quote.setPackageId(pid);
    List<Quote> quoteList = supplierQuoteService.selectQuoteHistoryList(quote);
    if (quoteList.size()>0) {
      if (quoteList.get(0).getQuotePrice() == null || quoteList.get(0).getQuotePrice().equals(new BigDecimal(0))){
        model.addAttribute("quote", 0);//提示唱总价
      }else if(quoteList.get(0).getQuotePrice() != null&&!quoteList.get(0).getQuotePrice().equals(new BigDecimal(0))){
        model.addAttribute("quote", 1);//提示唱明细
      }
    }
    //展示框设置
    if (view != null && view == 1) {
      model.addAttribute("quote", 1);
    }
    HashMap<String,Object> map = new HashMap<>();
    map.put("packageId", pid);
    
    //查询到包下面的明细条数
    /*List<ProjectDetail> detailList = detailService.selectById(map);
    model.addAttribute("detailList", detailList);*/
   List<theSubject> detailList = theSubjectService.selectByPackagesId(pid);
    model.addAttribute("detailList", detailList);
    return "bss/ppms/winning_supplier/supplier_list";
  }
  @RequestMapping("/changeRatioNull")
  public String changeRatioNull(String ids,Model model,String packageId, String flowDefineId,String projectId,Integer view){
	  
	    String[] id=ids.split(",");
	    SupplierCheckPass checkPass=null;
	    for(int i=0;i<id.length;i++){
	    	SupplierCheckPass pass = checkPassService.findByPrimaryKey(id[i]);
	    	if(pass!=null){
	    		checkPass=new SupplierCheckPass();
	    		checkPass.setId(id[i]);
	    		checkPass.setIsWonBid((short)0);
	    		checkPass.setPriceRatio("");
	    		packageId=pass.getPackageId();
	    		checkPassService.update(checkPass);
	    	}
	    }
	    model.addAttribute("flowDefineId", flowDefineId);
	    model.addAttribute("projectId", projectId);
	    model.addAttribute("packageId", packageId);
	    model.addAttribute("view", view);
	  return "redirect:confirmSupplier.html";
	  
  }
  /**
   * 
   *〈简述〉判断占比数量是否为小数
   *〈详细描述〉
   * @author Ma Mingwei
   * @param ids checkpassId的一个字符串组
   * @param priceRatios 传过来的占比的一个字符串组
   * @return 路径---确认供应商页面
   */
  @ResponseBody
  @RequestMapping("/changeRatioByCheckpassId")
  public String changeRatioByCheckpassId(String ids, String priceRatios){
	  //String idsStr = ids.toString();
	  String[] id=ids.split(",");
	  String[] pRatios=priceRatios.split(",");
	  Boolean bool=true;
	  for(int i=0;i<id.length;i++){
		  SupplierCheckPass supplierCheckPass = checkPassService.findByPrimaryKey(id[i]);
		  List<ProjectDetail> projectDetails = detailService.selectByPackageId(supplierCheckPass.getPackageId());
		  if(projectDetails!=null&&projectDetails.size()>0){
			  for(ProjectDetail  pd:projectDetails){
				  BigDecimal flg=new BigDecimal(pd.getPurchaseCount()+"");
				  BigDecimal pRa=new BigDecimal(pRatios[i]);
				  BigDecimal multiply = flg.multiply(pRa);
				  BigDecimal divide = multiply.divide(new BigDecimal("100"));
				  if((divide+"").indexOf(".")>0){
					  bool=false;
				  }
			  }
		  }
		  
	  }
	 /* checkPassService.changeSupplierWonTheBidding(ids,priceRatios);*/
	  if(bool==false){
		  return "no";
	  }else{
		  return "ok";
	  }
	  
	 
  }
  @ResponseBody
  @RequestMapping("/changeRatio")
  public String changeRatio(String ids, String priceRatios){
	  checkPassService.changeSupplierWonTheBidding(ids,priceRatios);
	  return "ok";
  }
  /**
   * 
   *〈简述〉获取包下所有供应商信息
   *〈详细描述〉
   * @author Ma Mingwei
   * @param model
   * @param projectId 项目id
   * @param packageId 包id
   * @param flowDefineId
   * @return 路径---确认供应商页面
   */
  @RequestMapping("/confirmSupplier")
  public String confirmSupplier(Model model, String packageId, String flowDefineId,String projectId,HttpServletRequest sq,Integer view){
    if (view != null && view == 1) {
      SupplierCheckPass scp = new SupplierCheckPass();
      scp.setPackageId(packageId);
      //scp.setIsWonBid((short)1);//
      List<SupplierCheckPass> listCheck = checkPassService.listCheckPass(scp);
      String[] rat = ratio(listCheck.size());
      for (int i = 0,l = listCheck.size(); i < l; i++ ) {
        if (listCheck.get(i).getIsWonBid() == 1 && listCheck.get(i).getWonPrice() == null && listCheck.get(i).getPriceRatio() ==null ){
          Double  price = (Double.parseDouble(rat[i])/100)*Double.parseDouble(listCheck.get(0).getTotalPrice().toString());
          SupplierCheckPass supplierCheckPass = listCheck.get(i);
          supplierCheckPass.setWonPrice(new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_UP));
          supplierCheckPass.setPriceRatio(rat[i]);
          checkPassService.update(supplierCheckPass); 

        }

      }
    }
    SupplierCheckPass checkPass = new SupplierCheckPass();
    checkPass.setPackageId(packageId);
    List<SupplierCheckPass> listSupplierCheckPass = checkPassService.listCheckPass(checkPass);
    for (SupplierCheckPass supplierCheckPass : listSupplierCheckPass) {
      //查询报价历史记录
      if(supplierCheckPass != null && supplierCheckPass.getSupplier() != null ){
        Quote quote = new Quote();
        quote.setPackageId(packageId);
        quote.setSupplierId(supplierCheckPass.getSupplier().getId());
        List<Quote> quoteList = supplierQuoteService.selectQuoteHistoryList(quote);
        supplierCheckPass.getSupplier().setListQuote(quoteList);
      }
    }

    model.addAttribute("supplierCheckPass", listSupplierCheckPass);
    model.addAttribute("supplierCheckPassJosn",JSON.toJSONString(listSupplierCheckPass));
    model.addAttribute("flowDefineId", flowDefineId);
    model.addAttribute("projectId", projectId);
    model.addAttribute("packageId", packageId);
    model.addAttribute("view", view);

    //获取已有中标供应商的包组
    String[] packcount = checkPassService.selectWonBid(projectId);
    List<Packages> packList = packageService.listSupplierCheckPass(projectId);
    if (packList.size() != packcount.length){
      model.addAttribute("error", ERROR);
    }
    //             //修改流程状态
    flowMangeService.flowExe(sq, flowDefineId, projectId, 2);
    
    //修改项目流程
/*    Project project = new Project();
    project.setId(projectId);
    project.setStatus(DictionaryDataUtil.getId("QRZBGYS"));
    projectService.update(project);*/
    //查询报价历史记录
    Quote quote = new Quote();
    quote.setPackageId(packageId);
    List<Quote> quoteList = supplierQuoteService.selectQuoteHistoryList(quote);
    if (quoteList.size()>0) {
      if (quoteList.get(0).getQuotePrice() == null || quoteList.get(0).getQuotePrice().equals(new BigDecimal(0))){
        model.addAttribute("quote", 0);//提示唱总价
      }else if(quoteList.get(0).getQuotePrice() != null&&!quoteList.get(0).getQuotePrice().equals(new BigDecimal(0))){
        model.addAttribute("quote", 1);//提示唱明细
      }
    }
    //展示框设置
    if (view != null && view == 1) {
      model.addAttribute("quote", 1);
    }
    HashMap<String,Object> map = new HashMap<>();
    map.put("packageId", packageId);
    
    List<ProjectDetail> detailList = detailService.selectById(map);
    model.addAttribute("detailList", detailList);

    return "bss/ppms/winning_supplier/supplier_check";
  }

  private String[] ratio(Integer key) {
    String[] str = null;
    switch (key) {
      case 1:
        str = new String[]{"100"};
        break;
      case 2:
        str = new String[]{"70","30"};
        break;
      case 3:
        str = new String[]{"50","30","20"};
        break;
      case 4:
        str = new String[]{"40","30","20","10"};
        break;
      default:
        break;
    }
    return str;

  }

  /**
   * 
   *〈简述〉比较是否是按照排名来的
   *〈详细描述〉
   * @author Wang Wenshuai
   * @return
   */
  @ResponseBody
  @RequestMapping("/comparison")
  public String comparison(@CurrentUser User user,String[] checkPassId, String jsonCheckPass,BigDecimal[] wonPrice){
    int type = 0; 
    List<SupplierCheckPass> supplierCheckPass = JSON.parseArray(jsonCheckPass, SupplierCheckPass.class);
    for (int i = 0; i < checkPassId.length; i++ ) {
      if (supplierCheckPass.get(i).getIsDeleted() == 0) {
        if (!checkPassId[i].equals(supplierCheckPass.get(i).getId()) ) {
          type = 1;
          break;
        }  
      }

    }
    //按照排名不需要上传变更依据
    if (type != 1){
      checkPassService.updateBid(checkPassId,wonPrice,user.getId());
      return JSON.toJSONString(SUCCESS);
    } else {
      return JSON.toJSONString(ERROR);
    }


  } 
  
  /**
   * 
   *〈单一来源确定中标〉
   *〈详细描述〉
   * @author FengTian
   * @param user
   * @param id
   * @return
   */
  @ResponseBody
  @RequestMapping("/comparisons")
  public String comparisons(@CurrentUser User user,String id){
      String[] ids= id.split(",");
      for (int i = 0; i < ids.length; i++ ) {
          SupplierCheckPass checkPass = new SupplierCheckPass();
          checkPass.setPackageId(ids[i]);
          List<SupplierCheckPass> listCheckPass = checkPassService.listCheckPass(checkPass);
          for (SupplierCheckPass supplierCheckPass : listCheckPass) {
              supplierCheckPass.setIsWonBid((short)1);
              checkPassService.update(supplierCheckPass);
          }
    }
      return JSON.toJSONString(SUCCESS);
  } 

  /**
   * 
   *〈简述〉上传
   *〈详细描述〉
   * @author Wang Wenshuai
   * @param model
   * @param packageId
   * @param flowDefineId
   * @return
   */
  @RequestMapping("/upload")
  public String upload(Model model,String projectId, String packageId, String flowDefineId, String checkPassId,String wonPrice){
    //凭证上传
    String id = DictionaryDataUtil.getId("CHECK_PASS_BGYJ");
    model.addAttribute("checkPassBgyj", id);

    //招标系统key
    Integer tenderKey = Constant.TENDER_SYS_KEY;
    model.addAttribute("packageId", packageId);
    model.addAttribute("tenderKey", tenderKey);
    model.addAttribute("projectId", projectId);
    model.addAttribute("flowDefineId", flowDefineId);
    model.addAttribute("checkPassId", checkPassId);
    model.addAttribute("wonPrice", wonPrice);
    return "bss/ppms/winning_supplier/upload";
  }

  /**
   * 
   *〈简述〉供应商上传凭证
   *〈详细描述〉
   * @author Wang Wenshuai
   * @param model
   * @param packageId
   * @param flowDefineId
   * @return
   */
  @RequestMapping("/supplierUpload")
  public String supplierUpload(Model model,String projectId, String packageId, String flowDefineId, String checkPassId,String wonPrice){
    //凭证上传
    String id = DictionaryDataUtil.getId("CHECK_PASS_SUPPLIER_BGYJ");
    model.addAttribute("checkPassSupplierBgyj", id);

    //招标系统key
    Integer tenderKey = Constant.TENDER_SYS_KEY;
    model.addAttribute("packageId", packageId);
    model.addAttribute("tenderKey", tenderKey);
    model.addAttribute("projectId", projectId);
    model.addAttribute("flowDefineId", flowDefineId);
    model.addAttribute("checkPassId", checkPassId);
    model.addAttribute("wonPrice", wonPrice);
    return "bss/ppms/winning_supplier/supplierUpload";
  }

  /**
   * 
   *〈简述〉删除文件
   *〈详细描述〉
   * @author Wang Wenshuai
   * @return
   */
  @ResponseBody
  @RequestMapping("/deleFile")
  public String delFile(String packageId,String checkpassId){
    //凭证上传
    String id = DictionaryDataUtil.getId("CHECK_PASS_BGYJ");
    //招标系统key
    Integer tenderKey = Constant.TENDER_SYS_KEY;
    List<UploadFile> filesOther = uploadService.getFilesOther(packageId, id, tenderKey.toString());
    if (filesOther != null && filesOther.size() != 0){
      uploadService.updateFileOther(filesOther.get(0).getId(), tenderKey.toString());
    } else {
      //凭证上传
      String ids = DictionaryDataUtil.getId("CHECK_PASS_SUPPLIER_BGYJ");
      //招标系统key
      Integer tenderKeys = Constant.TENDER_SYS_KEY;
      List<UploadFile> filesOthers = uploadService.getFilesOther(checkpassId, ids, tenderKeys.toString());
      if (filesOthers != null && filesOthers.size() != 0) {
        uploadService.updateFileOther(filesOthers.get(0).getId(), tenderKey.toString());
      }
    }
    return SUCCESS;
  }

  /**
   * 
   *〈简述〉执行完成
   *〈详细描述〉
   * @author Wang Wenshuai
   * @param projectId 项目id 
   * @param flowDefineId 流程id 
   * @return
   */
  @ResponseBody
  @RequestMapping("/executeFinish")
  public String executeFinish(String  projectId, String flowDefineId,HttpServletRequest sq){
    HashMap<String, Object> map = new HashMap<String, Object>();
    //获取已有中标供应商的包组
    String[] packList = checkPassService.selectWonBid(projectId);
    //查看项目下有多少包
    map.put("projectId", projectId);
    List<Packages> findPackageById = packageService.findPackageById(map);
    //对比
    if (findPackageById != null && findPackageById.size() != ZERO){
      if (findPackageById.size() != packList.length){
        return JSON.toJSONString(ERROR);
      } else {
        flowMangeService.flowExe(sq, flowDefineId, projectId, 1);
      }
    }
    return JSON.toJSONString(SUCCESS);
  }
  
  /**
   * 
   *〈简述〉完成
   *〈详细描述〉
   * @author Wang Wenshuai
   * @param projectId
   * @param flowDefineId
   * @param sq
   * @return
   */
  @ResponseBody
  @RequestMapping("/finish")
  public String finish(String  projectId, String flowDefineId,HttpServletRequest sq){
    HashMap<String, Object> map = new HashMap<String, Object>();
    //获取已有中标供应商的包组
    String[] packList = checkPassService.selectWonBid(projectId);
    //查看项目下有多少包
    map.put("projectId", projectId);
    List<Packages> findPackageById = packageService.findPackageById(map);
    //对比
    if (findPackageById != null && findPackageById.size() != ZERO){
      if (findPackageById.size() != packList.length){
        return JSON.toJSONString(ERROR);
      } else {
        flowMangeService.flowExe(sq, flowDefineId, projectId, 1);
      }
    }
    return JSON.toJSONString(SUCCESS);
  }

  /**
   * @Description:打开中标模板
   *
   * @author Wang Wenshuai
   * @version 2016年10月11日 下午4:46:42  
   * @param projectId    项目id  
   * @return String
   */
  @RequestMapping("/template")
  public String template(Model model, String projectId){
    SupplierCheckPass checkPass = new SupplierCheckPass();
    checkPass.setProjectId(projectId);
    checkPass.setIsSendNotice((short)0);
    checkPass.setIsWonBid((short)1);
    List<SupplierCheckPass> listSupplierCheckPass = checkPassService.listSupplierCheckPass(checkPass);
    model.addAttribute("listSupplierCheckPass", listSupplierCheckPass);
    model.addAttribute("projectId", projectId);
    //获取已有中标供应商的包组
    String[] packcount = checkPassService.selectWonBid(projectId);
    List<Packages> packList = packageService.listSupplierCheckPass(projectId);
    if (packList.size() != packcount.length){
      model.addAttribute("error", ERROR);
    }
    return "bss/ppms/winning_supplier/template";
  }

  /**
   * @Description:打开未中标模板
   *
   * @author Wang Wenshuai
   * @version 2016年10月11日 下午4:46:42  
   * @param projectId 项目id      
   * @return String
   */
  @RequestMapping("/notTemplate")
  public String notTemplate(Model model, String projectId){
    SupplierCheckPass checkPass = new SupplierCheckPass();
    checkPass.setProjectId(projectId);
    checkPass.setIsSendNotice((short)0);
    checkPass.setIsWonBid((short)0);
    List<SupplierCheckPass> listSupplierCheckPass = checkPassService.listSupplierCheckPass(checkPass);
    model.addAttribute("listSupplierCheckPass", listSupplierCheckPass);
    model.addAttribute("projectId", projectId);
    return "bss/ppms/winning_supplier/not_template";
  }

  /**
   * 
   *〈简述〉根据包id获取供应商
   *〈详细描述〉
   * @author Wang Wenshuai
   * @param model ui
   * @param packageId 项目id
   * @param isWonBid 是否中标 0未中标 1中标 
   * @return 地址
   */
  @ResponseBody
  @RequestMapping("/getSupplierJosn")
  public String getSupplier(Model model, String packageId, String isWonBid){
    SupplierCheckPass checkPass = new SupplierCheckPass();
    //是否发送  0未发送 1发送   
    checkPass.setIsSendNotice((short) 0);
    //包id 
    checkPass.setPackageId(packageId);
    //是否中标 0未中标 1中标 
    checkPass.setIsWonBid(new Short(isWonBid));
    List<SupplierCheckPass> supplierCheckPasses = checkPassService.listSupplierCheckPass(checkPass);
    return JSON.toJSONString(supplierCheckPasses);
  }

  /**
   * 
   *〈简述〉修改中标供应商状态
   *〈详细描述〉对比评审通过后的供应商是否和选中供应商一致。
   * @author Wang Wenshuai
   * @param id id集合
   * @return json
   */
  @ResponseBody
  @RequestMapping("/updateBid")
  public String updateBid(String id){
    //        checkPassService.updateBid(id);
    return JSON.toJSONString("sccuess");
  }

  /**
   * 发布
   *〈简述〉
   *〈详细描述〉
   * @author Wang Wenshuai
   * @return
   * @throws Exception 
   */
  @RequestMapping("/publish")
  public String  publish(String supplierId,Integer isWon,String projectId,String content,HttpServletRequest sq) throws Exception{
    if (supplierId != null &&  !"".equals(supplierId)){
      String[] supplierIds = supplierId.split("\\^");
      SupplierCheckPass checkPass = new SupplierCheckPass();
      checkPass.setIsSendNotice((short) 1);

      checkPass.setSupplierId(supplierIds[0]);
      checkPassService.update(checkPass);

      //获取供应商登录id
      ses.model.bms.User findByTypeId = userServiceI.findByTypeId(supplierIds[0]);

      ses.model.bms.User  login = (ses.model.bms.User ) sq.getSession().getAttribute("loginUser");
      if (login != null){
        StationMessage stationMessage = new StationMessage();
        if(findByTypeId != null){
          stationMessage.setReceiverId(findByTypeId.getId());
        }

        String pro = PropUtil.getProperty("bidNotice");
        stationMessage.setName(pro);
        stationMessage.setUrl("downloadabiddocument");
        stationMessage.setSenderId(login.getId());
        stationMessageService.insertStationMessage(stationMessage); 
        //招标系统key
        Integer tenderKey = Constant.TENDER_SYS_KEY;
        String uploadFileByContext = uploadService.uploadFileByContext(stationMessage.getId(), tenderKey.toString(), content); 
        System.out.println(uploadFileByContext);

        //                }
      }
    }
    if (isWon == 1){
      return "redirect:template.html?projectId=" + projectId;
    }else{
      return "redirect:notTemplate.html?projectId=" + projectId;
    }

  }

  /**
   * 
   *〈简述〉获取文件是已上传
   *〈详细描述〉
   * @author Wang Wenshuai
   * @param packageId 业务id
   * @param typeId typeid
   * @return 路径
   */
  @ResponseBody
  @RequestMapping("/getFilesOther")
  public String getFilesOther(@CurrentUser User user,String packageId, String typeId, String[] checkPassId,BigDecimal[] wonPrice){
    //招标系统key
    Integer tenderKey = Constant.TENDER_SYS_KEY;
    List<UploadFile> filesOther = uploadService.getFilesOther(packageId, typeId, tenderKey.toString());
    if (filesOther != null && filesOther.size() != ZERO){
      checkPassService.updateBid(checkPassId,wonPrice,user.getId());
      return JSON.toJSONString(SUCCESS);
    } else {
      List<UploadFile> filesOthers = uploadService.getFilesOther(checkPassId[0], typeId, tenderKey.toString());
      if (filesOthers != null && filesOthers.size() != ZERO){
        return JSON.toJSONString(SUCCESS);
      }
    }
    return JSON.toJSONString(ERROR);
  }

  /**
   * 
   *〈简述〉 修改方法
   *〈详细描述〉
   * @author Wang Wenshuai
   * @return
   */
  @RequestMapping("/update")
  public String update(SupplierCheckPass checkPass){
    checkPassService.update(checkPass);
    return null;
  }

  /**
   * 
   *〈简述〉报价明细计算总金额
   *〈详细描述〉
   * @author Wang Wenshuai
   * @param priceRatio
   * @param SupplierId
   * @param detail
   * @return
   */
  @ResponseBody
  @RequestMapping("/amountRransaction")
  public String  amountRransaction(String[] priceRatio,String[] SupplierId,String[] detail,String packageId){
    return checkPassService.amountRransaction(priceRatio, SupplierId, detail, packageId);

  }

  /**
   * 
   *〈简述〉移除供应商
   *〈详细描述〉
   * @author Wang Wenshuai
   * @param passId checkId
   * @return
   */
  @ResponseBody
  @RequestMapping("/delSupplier")
  public String delSupplier(String passId){
    SupplierCheckPass checkPass = new SupplierCheckPass();
    checkPass.setId(passId);
    checkPass.setIsDeleted(1);
    return  JSON.toJSONString(checkPassService.update(checkPass));
  }
  
  /**
   * 
   *〈简述〉录入标的
   *〈详细描述〉
   * @author Wang Wenshuai
   * 后续修改@author Ma Mingwei
   * @param passId checkId
   * @param supplierId 供应商id
   * @param packageId 封装了 供应商id组
   * @param pid 包id
   * @return
   */
  @RequestMapping("/inputList")
  public String inputList(Model model,String quote, String supplierId, String packageId,String projectId,String pid,String passId){
	model.addAttribute("supplierId", supplierId);
    model.addAttribute("packageId", packageId);
    model.addAttribute("projectId", projectId);
    model.addAttribute("quote", quote);
    model.addAttribute("pid", pid);
    //获取明细
    HashMap<String,Object> map = new HashMap<>();
    map.put("packageId", pid);
    List<ProjectDetail> detailList = detailService.selectById(map);
    SupplierCheckPass findByPrimaryKey = checkPassService.findByPrimaryKey(passId);
    model.addAttribute("detailList", detailList);
    model.addAttribute("pass", findByPrimaryKey);
    return "bss/ppms/winning_supplier/add_list";
  }
  
}
