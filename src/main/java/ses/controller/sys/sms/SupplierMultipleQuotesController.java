package ses.controller.sys.sms;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.sms.Quote;
import ses.model.sms.Supplier;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.sms.SupplierQuoteService;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.SaleTender;
import bss.service.ppms.ProjectDetailService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.SaleTenderService;

import com.github.pagehelper.PageInfo;
/**
 * @Title: SupplierMultipleQuotesController
 * @Description: 供应商报价控制层
 * @author: Song Biaowei
 * @date: 2016-10-28上午10:21:33
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/mulQuo")
public class SupplierMultipleQuotesController extends BaseSupplierController {
    
    @Autowired
    private SupplierQuoteService supplierQuoteService;
    
    @Autowired
    private ProjectDetailService detailService;
    
    @Autowired
    private SaleTenderService saleTenderService;
	
    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private DictionaryDataServiceI dictionaryDataService;
    
    /**
     * @Title: list
     * @author Song Biaowei
     * @date 2016-10-28 上午10:21:46  
     * @Description: 供应商报价的项目列表
     * @param @param req
     * @param @param response
     * @param @param saleTender
     * @param @param page
     * @param @param model
     * @param @return      
     * @return String
     */
	@RequestMapping(value="/list")
	public String list(HttpServletRequest req,Model model,String projectId){
	    SaleTender std = getProSupplier(req, projectId);
        model.addAttribute("std", std);
		Quote quote=new Quote();
		User user=(User)req.getSession().getAttribute("loginUser");
		quote.setProjectId(projectId);
	    quote.setSupplierId(user.getTypeId());
	    List<Date> listDate=supplierQuoteService.selectQuoteCount(quote);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("projectId", projectId);
		List<Packages> listPackage = supplierQuoteService.selectByPrimaryKey(map, null);
	    //开始循环包
	    List<List<ProjectDetail>> listPd=new ArrayList<List<ProjectDetail>>();
		    for(Packages pk:listPackage){
			    map.put("packageId", pk.getId());
		    	List<ProjectDetail> detailList = detailService.selectByCondition(map,null);
		    	listPd.add(detailList);
		    }
	    model.addAttribute("listPd",listPd );
		model.addAttribute("listPackage", listPackage);
	    model.addAttribute("projectId", projectId);
	    Project project=new Project();
		project.setId(projectId);
		model.addAttribute("project",project );
		model.addAttribute("listDate",listDate );
		return "ses/sms/multiple_quotes/quote_list";
	}
	
	/**
	 * @Title: save
	 * @author Song Biaowei
	 * @date 2016-10-28 上午10:22:14  
	 * @Description: 保存报价
	 * @param @param req
	 * @param @param quote
	 * @param @param model
	 * @param @return      
	 * @return String
	 * @throws ParseException 
	 */
	@RequestMapping(value="/save")
	public String save(HttpServletRequest req,Quote quote,Model model,String priceStr) throws ParseException {
		List<String> listBd=Arrays.asList(priceStr.split(","));
		User user=(User)req.getSession().getAttribute("loginUser");
		List<Quote> listQuote=new ArrayList<Quote>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("projectId", quote.getProjectId());
	    List<Packages> listPackage = supplierQuoteService.selectByPrimaryKey(map, null);
	    //循环次数
	    Integer count=0;
	    for(Packages pk:listPackage){
	    	map.put("packageId", pk.getId());
	    	List<ProjectDetail> detailList = detailService.selectByCondition(map,0);
	    	for(ProjectDetail pd:detailList){
	    		Quote qt=new Quote();
	    		count++;
	    		qt.setProjectId(quote.getProjectId());
	    		qt.setSupplierId(user.getTypeId());
	    		qt.setPackageId(pk.getId());
	    		qt.setProductId(pd.getId());
	    		qt.setQuotePrice(new BigDecimal(listBd.get(count*4-4)));
	    		qt.setTotal(new BigDecimal(listBd.get(count*4-3)));
	    		qt.setDeliveryTime(new Timestamp(new SimpleDateFormat("YYYY-MM-dd").parse(listBd.get(count*4-2)).getTime()));
	    		qt.setRemark(listBd.get(count*4-1).equals("null")?"":listBd.get(count*4-1));
	    		qt.setCreatedAt(new Timestamp(new Date().getTime()));
	    		listQuote.add(qt);
	    	}
	    }
	    try {
			supplierQuoteService.insert(listQuote);
			//修改状态
			SaleTender saleTender = new SaleTender();
			saleTender.setProjectId(quote.getProjectId());
			saleTender.setSupplierId(user.getTypeId());
			List<SaleTender> sts = saleTenderService.find(saleTender);
			SaleTender std = sts.get(0);
			std.setBidFinish((short)3);
			saleTenderService.update(std);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:supplierProject/bidIndex.html?projectId="+quote.getProjectId();
	}
	
	/**
	 * @Title: finish
	 * @author Song Biaowei
	 * @date 2016-11-4 下午4:38:47  
	 * @Description: TODO 
	 * @param @return      
	 * @return String
	 */
	@RequestMapping(value="/finish")
	public String finish(String projectId,Model model) {
		model.addAttribute("projectId", projectId);
		return "ses/sms/multiple_quotes/finish";
	}
	
	
	/**
	 * @Title: listProject
	 * @author Song Biaowei
	 * @date 2016-10-31 下午5:20:43  
	 * @Description: 正参与招标项目页面和参与项目结束页面 
	 * @param @param req
	 * @param @param response
	 * @param @param status2
	 * @param @param project
	 * @param @param page
	 * @param @param model
	 * @param @return      
	 * @return String
	 */
	@RequestMapping(value="/listProject")
	public String listProject(HttpServletRequest req,HttpServletResponse response,Project project,Integer page,Model model){
		    HashMap<String, Object> map = new HashMap<String, Object>();
		    User user=(User)req.getSession().getAttribute("loginUser");
		    if(user.getTypeId()==null){
		    	map.put("supplierId", "");
		    }else{
		    	map.put("supplierId", user.getTypeId());
		    }
		    map.put("name", project.getName());
		    map.put("projectNumber", project.getProjectNumber());
		    List<Project> pjList = supplierQuoteService.selectByCondition(map,page==null?0:page);
		    this.getPurchaserType(pjList);
		    model.addAttribute("info", new PageInfo<>(pjList));
		    model.addAttribute("project", project);
			return "ses/sms/multiple_quotes/list";
	}
    
    /**
     * @Title: quoteHistory
     * @author Song Biaowei
     * @date 2016-11-7 下午2:41:35  
     * @Description: 查询报价历史
     * @param @param req
     * @param @param timestamp
     * @param @param projectId
     * @param @param quote
     * @param @param model
     * @param @return      
     * @return String
     * @throws ParseException 
     */
    @RequestMapping(value="/quoteHistory")
    public String quoteHistory(HttpServletRequest req,String timestamp,String projectId,Quote quote,Model model) throws ParseException{
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("projectId", projectId);
        List<Packages> listPackage = supplierQuoteService.selectByPrimaryKey(map, null);
        List<List<Quote>> listQuote=new ArrayList<List<Quote>>();
        for(Packages pk:listPackage){
            quote.setCreatedAt(new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timestamp).getTime()));
            quote.setPackageId(pk.getId());
            List<Quote> quoteList = supplierQuoteService.selectQuoteHistoryList(quote);
            listQuote.add(quoteList);
        }
        model.addAttribute("listPackage",listPackage );
        model.addAttribute("listQuote",listQuote );
        return "ses/sms/multiple_quotes/quote_history_record";
    }
    
    /**
     * @Title: baojia
     * @author Song Biaowei
     * @date 2016-10-28 上午10:22:00  
     * @Description: 点击项目进行报价
     * @param @param req
     * @param @param id
     * @param @param packageName
     * @param @param packageId
     * @param @param model
     * @param @return
     * @param @throws UnsupportedEncodingException      
     * @return String
     */
    @RequestMapping(value="/baojia")
    public String baojia(HttpServletRequest req,String id,String packageName,HttpServletResponse response,String packageId,Integer page,Model model) throws UnsupportedEncodingException{
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("id",id );
            List<ProjectDetail> detailList = detailService.selectByCondition(map,page==null?0:page);
            List<ProjectDetail> list=new ArrayList<ProjectDetail>();
            if(detailList.size()>0){
                for(ProjectDetail pd:detailList){
                    if(pd.getPackages().getName().equals(URLDecoder.decode(packageName,"UTF-8"))){
                        list.add(pd);
                    }
                }
            }
            model.addAttribute("list", list);
            model.addAttribute("id", id);
            model.addAttribute("projectId", id);
            return "ses/sms/multiple_quotes/quote";
    }
    
     /**
     *〈简述〉获取供应商与项目的关联对象
     *〈详细描述〉
     * @author Ye MaoLin
     * @param req
     * @param projectId 项目id
     * @return 供应商与项目的关联对象
     */
    public SaleTender getProSupplier(HttpServletRequest req, String projectId){
        //供应商与项目的关联的关联作为投标文件的业务id
        SaleTender saleTender = new SaleTender();
        saleTender.setProjectId(projectId);
        Supplier supplier = (Supplier)req.getSession().getAttribute("loginSupplier");
        saleTender.setSupplierId(supplier.getId());
        List<SaleTender> sts = saleTenderService.find(saleTender);
        if (sts != null && sts.size() > 0) {
            SaleTender std = sts.get(0);
            return std;
        } else {
            return null;
        }
    }
    
    /**
     * @Title: getPurchaserType
     * @author Song Biaowei
     * @date 2016-11-20 下午3:02:21  
     * @Description: 给采购类型重新赋值
     * @param @param listPj      
     * @return void
     */
    public void getPurchaserType(List<Project> listPj){
    	if(listPj.size()>0){
    		for(Project project:listPj){
    			DictionaryData dd=new DictionaryData();
    			dd.setCode(project.getPurchaseType());
    			List<DictionaryData> listByPage = dictionaryDataService.listByPage(dd, 1);
    			if(listByPage.size()>0){
    				project.setPurchaseType(listByPage.get(0).getName());
    			}
    		}
    	}
    }
}
