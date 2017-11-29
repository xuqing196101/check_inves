package ses.controller.sys.sms;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.User;
import ses.model.sms.OpenBidInfo;
import ses.model.sms.OpenBidInfoVO;
import ses.model.sms.Quote;
import ses.model.sms.Supplier;
import ses.service.sms.SupplierQuoteService;
import ses.service.sms.SupplierService;
import bss.model.ppms.Packages;
import bss.model.ppms.Project;
import bss.model.ppms.ProjectDetail;
import bss.model.ppms.SaleTender;
import bss.service.ppms.ProjectDetailService;
import bss.service.ppms.ProjectService;
import bss.service.ppms.SaleTenderService;
import com.github.pagehelper.PageInfo;
import common.annotation.CurrentUser;
import common.annotation.SystemControllerLog;
import common.annotation.SystemServiceLog;
import common.constant.StaticVariables;
import common.utils.JdcgResult;
/**
 * 版权：(C) 版权所有 
 * <简述>供应商报价控制层
 * <详细描述>
 * @author   Song Biaowei
 * @version  
 * @since
 * @see
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/mulQuo")
public class SupplierMultipleQuotesController extends BaseSupplierController {
    /**
     * 供应商报价服务层
     */
    @Autowired
    private SupplierQuoteService supplierQuoteService;
    /**
     * 项目明细服务层
     */
    @Autowired
    private ProjectDetailService detailService;
    /**
     * 发售标书服务层
     */
    @Autowired
    private SaleTenderService saleTenderService;
    /**
     * 供应商
     */
    @Autowired
    private SupplierService supplierService;
    /**
     * 项目服务
     */
    @Autowired
    private ProjectService projectService;
    /**
     * 项目明细 服务
     */
    @Autowired
    private ProjectDetailService projectDetailService;
    
    /**
     *〈简述〉供应商报价的项目
     *〈详细描述〉
     * @author Song Biaowei
     * @param req request
     * @param model 模型
     * @param projectId 项目id
     * @return String
     * @throws ParseException 
     */
    @RequestMapping(value = "/list")
    public String list(HttpServletRequest req, Model model, String projectId) throws ParseException{
        SaleTender std = getProSupplier(req, projectId);
        model.addAttribute("std", std);
        Quote quote = new Quote();
        User user = (User) req.getSession().getAttribute("loginUser");
        quote.setProjectId(projectId);
        quote.setSupplierId(user.getTypeId());
        List<Date> listDate = supplierQuoteService.selectQuoteCount(quote);
        HashMap<String, Object> map = new HashMap<>();
        map.put("projectId", projectId);
        List<Packages> listPackage = supplierQuoteService.selectByPrimaryKey(map, null);
        //获取发售标书表里面的包id
        List<Packages> listPackageEach = new ArrayList<>();
        SaleTender saleTender = new SaleTender();
        saleTender.setProjectId(projectId);
        saleTender.setSupplierId(user.getTypeId());
        List<SaleTender> sts = saleTenderService.find(saleTender);
        if (sts != null && sts.size() > 0) {
            String packageStr = sts.get(0).getPackages();
            for (Packages packages : listPackage) {
                if (packageStr.indexOf(packages.getId()) != -1) {
                    listPackageEach.add(packages);
                }
            }
            req.setAttribute("std", sts.get(0));
        }
        //开始循环包
        List<List<ProjectDetail>> listPd = new ArrayList<>();
        for (Packages pk:listPackageEach) {
            map.put("packageId", pk.getId());
            List<ProjectDetail> detailList = detailService.selectByCondition(map, null);
            listPd.add(detailList);
        }
        model.addAttribute("listPd", listPd);
        model.addAttribute("listPackage", listPackageEach);
        model.addAttribute("projectId", projectId);
        Project project = new Project();
        project.setId(projectId);
        model.addAttribute("project", project);
        model.addAttribute("listDate", listDate);
        return "ses/sms/multiple_quotes/quote_list";
    }
    
    /**
     *〈简述〉保存报价信息
     *〈详细描述〉
     * @author Song Biaowei
     * @param req request
     * @param quote 报价实体类
     * @param model 模型
     * @param priceStr 前台的所有报价数据组成的字符串
     * @return String
     * @throws ParseException 异常处理
     */
    @RequestMapping(value = "/save")
    public String save(HttpServletRequest req, Quote quote, Model model, String priceStr) throws ParseException {
        List<String> listBd = Arrays.asList(priceStr.split(","));
        User user = (User) req.getSession().getAttribute("loginUser");
        List<Quote> listQuote = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("projectId", quote.getProjectId());
        List<Packages> listPackage = supplierQuoteService.selectByPrimaryKey(map, null);
        //
        List<Packages> listPackageEach = new ArrayList<>();
        SaleTender st = new SaleTender();
        st.setProjectId(quote.getProjectId());
        st.setSupplierId(user.getTypeId());
        List<SaleTender> stList = saleTenderService.find(st);
        if (stList != null && stList.size() > 0) {
            String packageStr = stList.get(0).getPackages();
            for (Packages packages : listPackage) {
                if (packageStr.indexOf(packages.getId()) != -1) {
                    listPackageEach.add(packages);
                }
            }
            req.setAttribute("std", stList.get(0));
        }
        //循环次数
        Integer count = 0;
        Timestamp timestamp = new Timestamp(new Date().getTime());
        for (Packages pk:listPackageEach) {
            map.put("packageId", pk.getId());
            List<ProjectDetail> detailList = detailService.selectByCondition(map, 0);
            for (ProjectDetail pd:detailList) {
                Quote qt = new Quote();
                count++;
                qt.setProjectId(quote.getProjectId());
                qt.setSupplierId(user.getTypeId());
                qt.setPackageId(pk.getId());
                qt.setProductId(pd.getId());
                qt.setQuotePrice(new BigDecimal(listBd.get(count * 4 - 4)));
                qt.setTotal(new BigDecimal(listBd.get(count * 4 - 3)));
                qt.setDeliveryTime(listBd.get(count * 4 - 2));
                qt.setRemark(listBd.get(count * 4 - 1).equals("null") ? "" : listBd.get(count * 4 - 1));
                qt.setCreatedAt(timestamp);
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
            std.setBidFinish((short) 3);
            saleTenderService.update(std);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/supplierProject/bidIndex.html?projectId=" + quote.getProjectId();
    }
    
    /**
     *〈简述〉当前供应商可以报价的项目
     *〈详细描述〉
     * @author Song Biaowei
     * @param req request
     * @param response response
     * @param project 项目实体
     * @param page 当前页
     * @param model 模型
     * @return String
     */
    @RequestMapping(value = "/listProject")
    @SystemControllerLog(description=StaticVariables.SUPPLIER_ONLINE_BIDDING)
    public String listProject(@CurrentUser User user, Project project,Integer page, Model model){
    	if(user != null){
	        HashMap<String, Object> map = new HashMap<>();
	        if (user.getTypeId() == null) {
	            map.put("supplierId", "");
	        } else {
	            map.put("supplierId", user.getTypeId());
	        }
	        map.put("name", project.getName());
	        map.put("projectNumber", project.getProjectNumber());
	        //传入map封装数据 获取相关数据集合
	        List<Project> pjList = supplierQuoteService.selectByCondition(map, page == null ? 0 : page);
	        model.addAttribute("info", new PageInfo<>(pjList));
	        model.addAttribute("project", project);
    	}
        return "ses/sms/multiple_quotes/list";
    }
    
    /**
     *〈简述〉查询报价历史
     *〈详细描述〉
     * @author Song Biaowei
     * @param req request
     * @param timestamp 报价时间 体现报价次数
     * @param projectId 项目id
     * @param quote 报价实体类
     * @param model 模型
     * @return String
     * @throws ParseException 异常处理
     */
    @RequestMapping(value = "/quoteHistory")
    public String quoteHistory(HttpServletRequest req, String timestamp, String projectId, Quote quote, Model model) throws ParseException{
        User user = (User) req.getSession().getAttribute("loginUser");
        quote.setProjectId(projectId);
        quote.setSupplierId(user.getTypeId());
        List<Date> listDate = supplierQuoteService.selectQuoteCount(quote);
        timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(listDate.get(listDate.size()-1));
        model.addAttribute("listDate", listDate);
        HashMap<String, Object> map = new HashMap<>();
        map.put("projectId", projectId);
        List<Packages> listPackage = supplierQuoteService.selectByPrimaryKey(map, null);
        List<Packages> listPackageEach = new ArrayList<>();
        SaleTender st = new SaleTender();
        st.setProjectId(projectId);
        st.setSupplierId(user.getTypeId());
        List<SaleTender> stList = saleTenderService.find(st);
        if (stList != null && stList.size() > 0) {
            String packageStr = stList.get(0).getPackages();
            for (Packages packages : listPackage) {
                if (packageStr.indexOf(packages.getId()) != -1) {
                    listPackageEach.add(packages);
                }
                req.setAttribute("std", stList.get(0));
            }
        }
        List<List<Quote>> listQuote = new ArrayList<>();
        for (Packages pk:listPackageEach) {
            quote.setCreatedAt(new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timestamp).getTime()));
            quote.setPackageId(pk.getId());
            List<Quote> quoteList = supplierQuoteService.selectQuoteHistoryList(quote);
            listQuote.add(quoteList);
        }
        model.addAttribute("listPackage", listPackageEach);
        model.addAttribute("listQuote", listQuote);
        Project project = new Project();
        project.setId(projectId);
        model.addAttribute("project", project);
        return "ses/sms/multiple_quotes/quote_history_record";
    }
    
    /**
     *〈简述〉开标一览表
     *〈详细描述〉
     * @author Song Biaowei
     * @return String
     */
    @RequestMapping(value = "openBid")
    public String openBid(@CurrentUser User user, String projectId, Model model) {
        if(user !=null){
            //获取供应商 id
            if(user.getTypeId() !=null){
                 //获取投标人全程：供应商公司名称
               Supplier supplier= supplierService.selectById(user.getTypeId());
               // 项目名称:项目编号 包号 T_BSS_PPMS_PROJECTS
               Project project=projectService.selectById(projectId);
               //获取供应商可参与 有效包集合
               List<SaleTender> saleTenderList=saleTenderService.findPackages(user.getTypeId(), projectId);
               model.addAttribute("supplier", supplier);
               model.addAttribute("project", project);
               model.addAttribute("saleTenderList", saleTenderList);
            }
        }
        return "ses/sms/multiple_quotes/open_bid";
    }
    /**
     * 
     * Description:保存开标一览表
     * 
     * @author YangHongLiang
     * @version 2017-5-27
     * @param user
     * @param detailId
     * @return
     */
    @RequestMapping("/openBidSave")
    @ResponseBody
    public JdcgResult openBidSave(@CurrentUser User user,OpenBidInfoVO openBidInfoVO){
      System.out.println("ss openBidInfoVO");
      return null;
    }
    /**
     *〈简述〉价格构成表
     *〈详细描述〉
     * @author Song Biaowei
     * @return String
     */
    @RequestMapping(value = "priceBuild")
    public String priceBuild(@CurrentUser User user, String projectId, Model model) {
        SaleTender st = new SaleTender();
        st.setProjectId(projectId);
        st.setSupplierId(user.getTypeId());
        List<SaleTender> stList = saleTenderService.find(st);
        model.addAttribute("std", stList.get(0));
        Project project = new Project();
        project.setId(projectId);
        model.addAttribute("project", project);
        return "ses/sms/multiple_quotes/price_build";
    }
    
    /**
     *〈简述〉明细表
     *〈详细描述〉
     * @author Song Biaowei
     * @return String
     */
    @RequestMapping(value = "priceView")
    public String priceView(@CurrentUser User user, String projectId, Model model) {
        SaleTender st = new SaleTender();
        st.setProjectId(projectId);
        st.setSupplierId(user.getTypeId());
        List<SaleTender> stList = saleTenderService.find(st);
        model.addAttribute("std", stList.get(0));
        Project project = new Project();
        project.setId(projectId);
        model.addAttribute("project", project);
        return "ses/sms/multiple_quotes/price_view";
    }
    
    /**
     *〈简述〉获取供应商与项目的关联对象
     *〈详细描述〉
     * @author Song Biaowei
     * @param req request
     * @param projectId 项目id
     * @return SaleTender
     */
    public SaleTender getProSupplier(HttpServletRequest req, String projectId) {
        //供应商与项目的关联的关联作为投标文件的业务id
        SaleTender saleTender = new SaleTender();
        saleTender.setProjectId(projectId);
        Supplier supplier = (Supplier) req.getSession().getAttribute("loginSupplier");
        saleTender.setSupplierId(supplier.getId());
        List<SaleTender> sts = saleTenderService.find(saleTender);
        if (sts != null && sts.size() > 0) {
            SaleTender std = sts.get(0);
            return std;
        } else {
            return null;
        }
    }
}
