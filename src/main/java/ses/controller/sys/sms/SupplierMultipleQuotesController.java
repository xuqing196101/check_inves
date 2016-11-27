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
import bss.service.ppms.SaleTenderService;

import com.github.pagehelper.PageInfo;
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
     * 数据字典服务层
     */
    @Autowired
    private DictionaryDataServiceI dictionaryDataService;
    
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
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("projectId", projectId);
        List<Packages> listPackage = supplierQuoteService.selectByPrimaryKey(map, null);
        //获取发售标书表里面的包id
        List<Packages> listPackageEach = new ArrayList<Packages>();
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
        List<List<ProjectDetail>> listPd = new ArrayList<List<ProjectDetail>>();
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
        List<Quote> listQuote = new ArrayList<Quote>();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("projectId", quote.getProjectId());
        List<Packages> listPackage = supplierQuoteService.selectByPrimaryKey(map, null);
        //
        List<Packages> listPackageEach = new ArrayList<Packages>();
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
                qt.setDeliveryTime(new Timestamp(new SimpleDateFormat("YYYY-MM-dd").parse(listBd.get(count * 4 - 2)).getTime()));
                qt.setRemark(listBd.get(count * 4 - 1).equals("null") ? "" : listBd.get(count * 4 - 1));
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
    public String listProject(HttpServletRequest req, HttpServletResponse response, Project project, Integer page, Model model){
        HashMap<String, Object> map = new HashMap<String, Object>();
        User user = (User) req.getSession().getAttribute("loginUser");
        if (user.getTypeId() == null) {
            map.put("supplierId", "");
        } else {
            map.put("supplierId", user.getTypeId());
        }
        map.put("name", project.getName());
        map.put("projectNumber", project.getProjectNumber());
        List<Project> pjList = supplierQuoteService.selectByCondition(map, page == null ? 0 : page);
        this.getPurchaserType(pjList);
        model.addAttribute("info", new PageInfo<>(pjList));
        model.addAttribute("project", project);
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
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("projectId", projectId);
        List<Packages> listPackage = supplierQuoteService.selectByPrimaryKey(map, null);
        User user = (User) req.getSession().getAttribute("loginUser");
        List<Packages> listPackageEach = new ArrayList<Packages>();
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
        List<List<Quote>> listQuote = new ArrayList<List<Quote>>();
        for (Packages pk:listPackageEach) {
            quote.setCreatedAt(new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timestamp).getTime()));
            quote.setPackageId(pk.getId());
            List<Quote> quoteList = supplierQuoteService.selectQuoteHistoryList(quote);
            listQuote.add(quoteList);
        }
        model.addAttribute("listPackage", listPackageEach);
        model.addAttribute("listQuote", listQuote);
        return "ses/sms/multiple_quotes/quote_history_record";
    }
    
    /**
     *〈简述〉跳转到报价页面
     *〈详细描述〉
     * @author Song Biaowei
     * @param id 项目ID
     * @param packageName 包名称 
     * @param packageId 包id
     * @param page 当前页
     * @param model 模型
     * @return String
     * @throws UnsupportedEncodingException 异常处理
     */
    @RequestMapping(value = "/baojia")
    public String baojia(String id, String packageName, String packageId, Integer page, Model model) throws UnsupportedEncodingException{
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        List<ProjectDetail> detailList = detailService.selectByCondition(map, page == null ? 0 : page);
        List<ProjectDetail> list = new ArrayList<ProjectDetail>();
        if (detailList.size() > 0) {
            for (ProjectDetail pd:detailList) {
                if (pd.getPackages().getName().equals(URLDecoder.decode(packageName, "UTF-8"))) {
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
    
    /**
     *〈简述〉给采购类型重新赋值
     *〈详细描述〉
     * @author Song Biaowei
     * @param listPj 项目集合
     */
    public void getPurchaserType(List<Project> listPj){
        if (listPj.size() > 0) {
            for (Project project : listPj) {
                DictionaryData dd = new DictionaryData();
                dd.setId(project.getPurchaseType());
                List<DictionaryData> listByPage = dictionaryDataService.listByPage(dd, 1);
                if (listByPage.size() > 0) {
                    project.setPurchaseType(listByPage.get(0).getName());
                }
            }
        }
    }
}
