package ses.controller.sys.ppms;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.controller.sys.sms.BaseSupplierController;
import ses.formbean.CategotyBean;
import ses.model.bms.Category;
import ses.model.bms.CategoryAptitude;
import ses.model.bms.CategoryAssigned;
import ses.model.bms.CategoryParameter;
import ses.model.bms.CategoryTree;
import ses.model.bms.DictionaryData;
import ses.model.oms.Orgnization;
import ses.model.ppms.CategoryParam;
import ses.service.bms.CategoryAptitudeService;
import ses.service.bms.CategoryAssignedService;
import ses.service.bms.CategoryParameterService;
import ses.service.bms.CategoryService;
import ses.service.oms.OrgnizationServiceI;
import ses.service.ppms.CategoryParamService;
import ses.util.DictionaryDataUtil;
import ses.util.EncodingTool;

import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;

import common.constant.Constant;

@Controller
@Scope("prototype")
@RequestMapping("/categoryparam")
public class CategoryParamContrller extends BaseSupplierController {
    @Autowired
    private CategoryParamService categoryParamService;//品目参数
    @Autowired
    private CategoryService categoryService;//品目
    @Autowired
    private CategoryAptitudeService categoryAptitudeService;//品目资质

    @Autowired
    private OrgnizationServiceI orgnizationServiceI;
    
    /** 产品分配service  */
    @Autowired
    private CategoryAssignedService cateAssignService;

    @Autowired
    private CategoryParameterService categoryParameterService;

    private Map<String, Object> allListNews=new HashMap<String, Object>();

    public Map<String, Object> getAllListNews() {
        return allListNews;
    }
    public void setAllListNews(Map<String, Object> allListNews) {
        this.allListNews = allListNews;
    }
    /**
     * 
     * @Title: createtree
     * @author zhangxuefeng
     * @date 2016-7-18 下午4:27:01  
     * @Description:查询采购目的所有信息转换成json
     * @param @return  
     * @return String
     */
    @RequestMapping("/createtree")
    public String getAll(Category category,Orgnization orgnization){
        
        
        if(category.getId()==null){
            category.setId("0");
        }	Gson gson = new Gson();
        String list="";
        List<CategoryTree> jList=new ArrayList<CategoryTree>(); 
        List<Category> cateList=categoryService.findTreeByPid(category.getId());
        for(Category cate:cateList){
            List<Category> cList=categoryService.findTreeByPid(cate.getId());
            CategoryTree ct=new CategoryTree();
            if(!cList.isEmpty()){
                ct.setIsParent("true");
            }else{
                ct.setIsParent("false");
            }
            ct.setId(cate.getId());
            ct.setName(cate.getName());
            ct.setpId(cate.getParentId());
            ct.setParamStatus(orgnization.getStatus().toString());
            jList.add(ct);

            list=gson.toJson(jList);
        }
        return list;
    }
    /**
     * @Title: getAll
     * @author zhangxuefeng
     * @Description:进入参数页面
     * @param @return    
     * @return String
     */  
    @RequestMapping("/getAll")
    public String get(HttpServletRequest request){
        return "ses/ppms/categoryparam/add";
    }
    /**
     * 
     * @Title: save
     * @author zhangxuefeng
     * @date 2016-7-18 下午4:27:01  
     * @Description:添加参数
     * @param @return  
     * @return String
     */

    @RequestMapping("/save")
    public String save(CategoryParam categoryParam,CategoryAptitude categoryAptitude,Category category,
                       Model model,HttpServletRequest request,HttpServletResponse response){
        Boolean flag = true;
        String name = request.getParameter("names");
        String[] names = name.split(",");
        for (int i = 0; i < names.length; i++) {
            if (names[i]==null ||names[i].equals("")) {
                flag = false;
                allListNews.put("name", "参数名不能为空");
            }
        }
        for (int i = 0; i < names.length; i++) {
            for (int j = i+1; j < names.length; j++) {
                if (names[i].equals(names[j])) {
                    flag = false;
                    allListNews.put("name", "参数名不能重复");
                }
            }
        }
        List<CategoryParam> cateparam = categoryParamService.findListByCategoryId(request.getParameter("id"));
        String paramname = "";
        for (int i = 0; i < cateparam.size(); i++) {
            paramname+= cateparam.get(i).getName()+",";
        }
        String[] paramnames = paramname.split(",");
        for (int i = 0; i < names.length; i++) {
            for (int j = 0; j < paramnames.length; j++) {
                if (names[i].equals(paramnames[j])) {
                    flag = false;
                    allListNews.put("name", "参数名已存在");
                }
            }
        }

        String value = request.getParameter("values");
        String[] values = value.split(",");
        for (int i = 0; i < values.length; i++) {
            if (values[i]==null|| values[i].equals("")||values[i].equals("--请选择--")) {
                flag = false;
                allListNews.put("value", "请选择参数类型");
            }
        }
        Integer ispublish = Integer.parseInt(request.getParameter("ispublish"));
        if (ispublish==null||ispublish.equals("")) {
            flag = false;
            allListNews.put("ispublish","请选择是否公开");
        }
        String kind = request.getParameter("kinds");
        String[] kinds = kind.split(",");
        for (int i = 0; i < kinds.length; i++) {
            if (kinds[i]==null || kinds[i].equals("")) {
                flag = false;
                allListNews.put("kind", "类型不能为空");
            }
        }
        String acceptrange = request.getParameter("acceptRange");
        if (acceptrange==null || acceptrange.equals("")) {
            flag = false;
            allListNews.put("acceptrange", "验收规范不能为空");
        }
        String productname = request.getParameter("products");
        String[] productnames = productname.split(",");
        String saname = request.getParameter("sales");
        String[] sanames = saname.split(",");
        List<CategoryAptitude> cateAptitude = categoryAptitudeService.findListByCategoryId(request.getParameter("id"));
        String proname = "";
        String salename = "";
        for (int i = 0; i < cateAptitude.size(); i++) {
            proname+= cateAptitude.get(i).getProductName()+",";
            salename+= cateAptitude.get(i).getSaleName()+",";
        }	
        String[] pronames = proname.split(",");
        String[] salenames = salename.split(",");
        for (int i = 0; i <salenames.length; i++) {
            if (salenames[i]==null ||salenames[i].equals("")) {
                flag = false;
                allListNews.put("sale", "资质文件不能为空");
            }
        }
        for (int i = 0; i < pronames.length; i++) {
            for (int j = 0; j < productnames.length; j++) {
                if (pronames[i].equals(productnames[j])) {
                    flag = false;
                    allListNews.put("product","文件已存在");
                }
            }
        }

        for (int i = 0; i < productnames.length; i++) {
            if (productnames[i]==null || productnames[i].equals("")) {
                flag = false;
                allListNews.put("product", "资质文件不能为空");
            }
        }
        for (int i = 0; i < sanames.length; i++) {
            for (int j = 0; j < salenames.length; j++) {
                if (sanames[i].equals(salenames[j])) {
                    flag = false;
                    allListNews.put("sale", "资质文件已存在");
                }
            }
        }
        for (int i = 0; i < productnames.length; i++) {
            for (int j = i+1; j < productnames.length; j++) {
                if (productnames[i].equals(productnames[j])) {
                    allListNews.put("product", "资质文件不能重复");
                }
            }
        }
        for (int i = 0; i < sanames.length; i++) {
            for (int j = i+1; j < sanames.length; j++) {
                if (sanames[i].equals(sanames[j])) {
                    flag = false;
                    allListNews.put("sale", "资质文件不能重复");
                }
            }
        }

        if (flag== false) {
            super.writeJson(response, allListNews);
        }else{

            category.setAcceptRange(acceptrange);
            category.setIsPublish(ispublish);
            category.setKind(kind);
            categoryService.updateByPrimaryKeySelective(category);
            categoryParam.setCategory(category);
            for (int i = 0; i < names.length; i++) {
                categoryParam.setName(names[i]);
                categoryParam.setValueType(values[i]);
                categoryParam.setCreatedAt(new Date());
                categoryParamService.insertSelective(categoryParam);
            }
            for (int i = 0; i < productnames.length; i++) {
                categoryAptitude.setProductName(productnames[i]);
                categoryAptitude.setCreatedAt(new Date());
            }
            for (int i = 0; i < salenames.length; i++) {
                categoryAptitude.setSaleName(salenames[i]);
                categoryAptitude.setCreatedAt(new Date());
            }
            categoryAptitudeService.insertSelective(categoryAptitude);

        }
        return "ses/ppms/categoryparam/add";
    }
    /**
     * 
     * @Title: rename
     * @author Zhang XueFeng
     * @Description:修改品目名称
     * @param @return 
     * @return String
     */  
    @RequestMapping("/rename")
    public String updateName(HttpServletRequest request,Category category){
        categoryService.updateByPrimaryKeySelective(category);
        return "ses/ppms/categoryparam/add";
    }
    
    /**
     * 
     * @Title: delete
     * @author Zhang XueFeng/	
     * @Description:删除目录节点
     * @param @return 	
     * @return void
     */ 
    @RequestMapping("/del")
    public void delete(Category  category){
        categoryService.deleteByPrimaryKey(category.getId());
    }

    /**
     * 
     * @Title: findOne
     * @author Zhang XueFeng/	
     * @Description:根据品目id查询参数信息
     * @param @return 	
     * @return void
     */ 
    @RequestMapping("/findOne")
    public String findOne(HttpServletResponse response,String id,Model model){
        List<CategoryParam> cateparam = categoryParamService.findListByCategoryId(id);
        String name="";
        String value ="";
        for (int i = 0; i < cateparam.size(); i++) {
            name+= cateparam.get(i).getName()+",";
            value+= cateparam.get(i).getValueType()+",";
        }
        String productname = "";
        String salename = "";
        List<CategoryAptitude> cateaptitude = categoryAptitudeService.findListByCategoryId(id);
        for (int i = 0; i < cateaptitude.size(); i++) {
            productname+= cateaptitude.get(i).getProductName()+",";
            salename+= cateaptitude.get(i).getSaleName()+",";
        }
        Category category= categoryService.selectByPrimaryKey(id);
        model.addAttribute("name", name);
        model.addAttribute("value", value);
        model.addAttribute("productname", productname);
        model.addAttribute("salename", salename);
        model.addAttribute("category", category);
        return "ses/ppms/categoryparam/edit";
    }
    /**
     * 
     * @Title: edit
     * @author Zhang XueFeng/	
     * @Description：更新参数信息
     * @param @return 	
     * @return void
     */ 
    @RequestMapping("/edit")
    public String edit( CategoryAptitude categoryAptitude,CategoryParam categoryParam,Category category,
                        HttpServletRequest request,Model model,String id,HttpServletResponse response){
        Boolean flag = true;
        String name = request.getParameter("names");
        String[] names = name.split(",");
        for (int i = 0; i < names.length; i++) {
            if (names[i]==null ||names[i].equals("")) {
                flag = false;
                allListNews.put("name", "参数名不能为空");
            }
        }
        for (int i = 0; i < names.length; i++) {
            for (int j = i+1; j < names.length; j++) {
                if (names[i].equals(names[j])) {
                    flag = false;
                    allListNews.put("name", "参数名不能重复");
                }
            }
        }
        List<CategoryParam> cateparam = categoryParamService.findListByCategoryId(request.getParameter("id"));
        String paramname = "";
        for (int i = 0; i < cateparam.size(); i++) {
            paramname+= cateparam.get(i).getName()+",";
        }
        String[] paramnames = paramname.split(",");
        for (int i = 0; i < names.length; i++) {
            for (int j = 0; j < paramnames.length; j++) {
                if (names[i].equals(paramnames[j])) {
                    flag = false;
                    allListNews.put("name", "参数名已存在");
                }
            }
        }

        String value = request.getParameter("values");
        String[] values = value.split(",");
        for (int i = 0; i < values.length; i++) {
            if (values[i]==null|| values[i].equals("")||values[i].equals("--请选择--")) {
                flag = false;
                allListNews.put("value", "请选择参数类型");
            }
        }
        Integer ispublish = Integer.parseInt(request.getParameter("ispublish"));
        if (ispublish==null||ispublish.equals("")) {
            flag = false;
            allListNews.put("ispublish","请选择是否公开");
        }
        String kind = request.getParameter("kinds");
        String[] kinds = kind.split(",");
        for (int i = 0; i < kinds.length; i++) {
            if (kinds[i]==null || kinds[i].equals("")) {
                flag = false;
                allListNews.put("kind", "类型不能为空");
            }
        }
        String acceptrange = request.getParameter("acceptRange");
        if (acceptrange==null || acceptrange.equals("")) {
            flag = false;
            allListNews.put("acceptrange", "验收规范不能为空");
        }
        String productname = request.getParameter("products");
        String[] productnames = productname.split(",");
        String saname = request.getParameter("sales");
        String[] sanames = saname.split(",");
        List<CategoryAptitude> cateAptitude = categoryAptitudeService.findListByCategoryId(request.getParameter("id"));
        String proname = "";
        String salename = "";
        for (int i = 0; i < cateAptitude.size(); i++) {
            proname+= cateAptitude.get(i).getProductName()+",";
            salename+= cateAptitude.get(i).getSaleName()+",";
        }	
        String[] pronames = proname.split(",");
        String[] salenames = salename.split(",");
        for (int i = 0; i <salenames.length; i++) {
            if (salenames[i]==null ||salenames[i].equals("")) {
                flag = false;
                allListNews.put("sale", "资质文件不能为空");
            }
        }
        for (int i = 0; i < pronames.length; i++) {
            for (int j = 0; j < productnames.length; j++) {
                if (pronames[i].equals(productnames[j])) {
                    flag = false;
                    allListNews.put("product","文件已存在");
                }
            }
        }

        for (int i = 0; i < productnames.length; i++) {
            if (productnames[i]==null || productnames[i].equals("")) {
                flag = false;
                allListNews.put("product", "资质文件不能为空");
            }
        }
        for (int i = 0; i < sanames.length; i++) {
            for (int j = 0; j < salenames.length; j++) {
                if (sanames[i].equals(salenames[j])) {
                    flag = false;
                    allListNews.put("sale", "资质文件已存在");
                }
            }
        }
        for (int i = 0; i < productnames.length; i++) {
            for (int j = i+1; j < productnames.length; j++) {
                if (productnames[i].equals(productnames[j])) {
                    allListNews.put("product", "资质文件不能重复");
                }
            }
        }
        for (int i = 0; i < sanames.length; i++) {
            for (int j = i+1; j < sanames.length; j++) {
                if (sanames[i].equals(sanames[j])) {
                    flag = false;
                    allListNews.put("sale", "资质文件不能重复");
                }
            }
        }

        if (flag== false) {
            super.writeJson(response, allListNews);
        }else{

            category.setAcceptRange(acceptrange);
            category.setIsPublish(ispublish);
            category.setKind(kind);
            categoryService.updateByPrimaryKeySelective(category);
            categoryParam.setCategory(category);
            for (int i = 0; i < names.length; i++) {
                categoryParam.setName(names[i]);
                categoryParam.setValueType(values[i]);
                categoryParam.setCreatedAt(new Date());
                categoryParamService.updateByPrimaryKeySelective(categoryParam);
            }
            for (int i = 0; i < productnames.length; i++) {
                categoryAptitude.setProductName(productnames[i]);
                categoryAptitude.setCreatedAt(new Date());
            }
            for (int i = 0; i < salenames.length; i++) {
                categoryAptitude.setSaleName(salenames[i]);
                categoryAptitude.setCreatedAt(new Date());
            }
            categoryAptitudeService.updateByPrimaryKeySelective(categoryAptitude);

        }
        return "redirect:getAll.html";


    }
    /**
     * 
     * @Title: import
     * @author Zhang XueFeng/	
     * @Description：导入excel表
     * @param 	
     * @return void
     */ 
    @RequestMapping("/import")
    public String imports(String id) throws IOException{
        File is = new File("F:/参数模板.xlsx");
        Workbook wb = null;
        String name = "";
        String value = "";
        //判断Excel是2007以下还是2007以上版本
        try {
            wb = new XSSFWorkbook(is);
        }catch (Exception ex) { 
            wb = new HSSFWorkbook(new FileInputStream(is));
        }
        Sheet sheet = wb.getSheetAt(0);
        for (int i = 1; i < sheet.getPhysicalNumberOfRows();i++) {
            Row row = sheet.getRow(i);
            if (row==null) {
                continue;
            }
            name+= row.getCell(0).getStringCellValue()+",";
            value+= row.getCell(1).getStringCellValue()+",";
        }
        String[] names = name.split(",");
        String[] values = value.split(",");
        CategoryParam categoryParam =new CategoryParam();
        Category ca = new Category();
        ca.setId(id);
        categoryParam.setCategory(ca);
        for (int i = 0; i < names.length; i++) {
            categoryParam.setName(names[i]);
            categoryParam.setValueType(values[i]);
        }

        categoryParamService.insertSelective(categoryParam);
        return "redirect:getAll.html";
    }

    /**
     * 
     * @Title: exports
     * @author Zhang XueFeng	
     * @Description：导出excel表
     * @param 	id session
     * @return void
     * @throws IOException 
     */ 
    @RequestMapping("/exports")
    public void export(String id,HttpSession session,HttpServletRequest request,HttpServletResponse response) throws IOException{
        String filename ="品目参数表";

        response.setContentType("application/vnd.ms-excel; charset=utf-8");
        response.setHeader("Content-Disposition","attachment;filename="+filename);
        response.setCharacterEncoding("utf-8");
        OutputStream os=response.getOutputStream();
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("品目参数表");
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("参数名");
        HSSFCell cell1 = row.createCell(1);
        cell1.setCellValue("参数类型");
        List<CategoryParam> cateparam = categoryParamService.findListByCategoryId(id);
        String name = "";
        String value = "";
        for (int i = 0; i < cateparam.size(); i++) {
            name+= cateparam.get(i).getName()+",";
            value+=cateparam.get(i).getValueType()+",";
        }
        String[] names = name.split(",");
        String[] values = value.split(",");
        for (int i = 0; i < names.length; i++) {
            row = sheet.createRow(i+1);
            row.createCell(0).setCellValue(names[i]);
            row.createCell(1).setCellValue(values[i]);
        }   
        try {



            os.close();

        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**********************************参数审核*************************************************************************/
    /**
     * 
     * @Title: change
     * @author Zhang XueFeng/	
     * @Description:进入审核列表页面
     * @param @return 	
     * @return void
     */ 
    @RequestMapping("/change")
    public String change(HttpServletResponse response,String id,Category category){
        return "ses/ppms/categoryparam/audit";
    }

    /**
     * 
     * @Title: searchCategory
     * @author Zhang XueFeng/	
     * @Description:根据品目状态查询列表
     * @param 
     * @return String
     */ 
    @RequestMapping("/search_category")
    public String searchCategory(HttpServletResponse response,HttpServletRequest request,Model model,Integer page){
        String status = request.getParameter("paramstatus");
        Integer paramstatus = Integer.parseInt(status);
        if(page==null){
            page=1;
        }
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("paramStatus", paramstatus);
        map.put("page", page);
        List<Category> cate = categoryService.listByParamstatus(map);
        model.addAttribute("list",new PageInfo<Category>(cate));
        model.addAttribute("cate",cate);
        return "ses/ppms/categoryparam/audit";
    }
    /**
     * 
     * @Title: queryCategory
     * @author Zhang XueFeng/	
     * @Description:创建审核页面
     * @param 
     * @return String
     */
    @RequestMapping("/query_category")
    public String queryCategory(Model model,String id){
        List<CategoryParam> cateparam = categoryParamService.findListByCategoryId(id);
        String name="";
        String value ="";
        for (int i = 0; i < cateparam.size(); i++) {
            name+= cateparam.get(i).getName()+",";
            value+= cateparam.get(i).getValueType()+",";
        }
        String productname = "";
        String salename = "";
        List<CategoryAptitude> cateaptitude = categoryAptitudeService.findListByCategoryId(id);
        for (int i = 0; i < cateaptitude.size(); i++) {
            productname+= cateaptitude.get(i).getProductName()+",";
            salename+= cateaptitude.get(i).getSaleName()+",";
        }
        Category category= categoryService.selectByPrimaryKey(id);
        model.addAttribute("name", name);
        model.addAttribute("value", value);
        model.addAttribute("productname", productname);
        model.addAttribute("salename", salename);
        model.addAttribute("category", category);
        return "ses/ppms/categoryparam/auditinfo";
    }

    /**
     * @Title: queryCategory
     * @author Zhang XueFeng/	
     * @Description:审核页面添加公示范围，改变参数状态
     * @param id
     * @return void
     */
    @RequestMapping("/audit_param")
    public String auditParam(HttpServletRequest request,Category category){
        String ranges =request.getParameter("ranges");
        if (ranges==null) {
        }
        category.setParamPublishRange(ranges);
        category.setParamStatus(Integer.parseInt(request.getParameter("storage")));
        categoryService.updateByPrimaryKeySelective(category);
        return "redirect:change.html";
    }
    /**
     * @Title: publish
     * @author Zhang XueFeng/	
     * @Description:进入待发布列表页面
     * @param id
     * @return string
     */
    @RequestMapping("/publish")
    public String publish(HttpServletRequest request,Model model,Integer page){
        if(page==null){
            page=1;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", page);
        List<Category> cate = categoryService.findByStatus(map);
        model.addAttribute("list",new PageInfo<Category>(cate));
        model.addAttribute("page",page);
        model.addAttribute("cate",cate);
        return "ses/ppms/categoryparam/unrelease";

    }
    /**
     * @Title: findCategory
     * @author Zhang XueFeng/	
     * @Description:根据名称进行模糊查询
     * @param id
     * @return string
     */
    @RequestMapping("/findCategory")
    public String findCategory(HttpServletRequest request,Model model,Integer page){
        if (page==null) {
            page = 1;
        }
        String name = request.getParameter("name");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("page", page);
        List<Category>  cate = categoryService.listByCateogryName(map);
        model.addAttribute("list",new PageInfo<Category>(cate));
        model.addAttribute("page",page);
        model.addAttribute("cate",cate);
        return "ses/ppms/categoryparam/unrelease";
    }
    /**
     * @Title: queryCategory
     * @author Zhang XueFeng/	
     * @Description:对参数进行发布 改变状态
     * @param id
     * @return string
     */
    @RequestMapping("/publish_category")
    public String publishCategory(HttpServletRequest request,Model model,String id){
        String[] ids = id.split(",");
        for (String str : ids) {
            Category category = categoryService.selectByPrimaryKey(str);
            category.setParamStatus(4);
            categoryService.updateByPrimaryKeySelective(category);
        }
        return "redirect:publish.html";

    } 

    /**
     * @Title: publish_param
     * @author Zhang XueFeng/	
     * @Description:发布
     * @param id
     * @return string
     */
    @RequestMapping("/publish_param")
    public String publishParam(HttpServletRequest request,Model model,String id){
        Category category = categoryService.selectByPrimaryKey(id);
        category.setParamStatus(4);
        categoryService.updateByPrimaryKeySelective(category);
        return "ses/ppms/categoryparam/unrelease";

    }
    /***********************************************参数分配*******************************************************************/ 

    /**
     * @Title: query_orgnization
     * @author Zhang XueFeng/	
     * @Description:按照事业部门  负责人查询
     * @param id
     * @return string
     */
    @RequestMapping("/query_orgnization")
    public String queryOrgnization(HttpServletRequest request,Model model,Orgnization orgnization){
        String name =request.getParameter("name");
        String princinpal= request.getParameter("princinpal");
        Integer page = null;
        if (request.getParameter("page")!=""&&request.getParameter("page")!=null) {
            page = Integer.parseInt(request.getParameter("page"));
        }else{
            page=1;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name",name);
        map.put("princinpal", princinpal);
        map.put("page", page);
        List<Orgnization>  cate = orgnizationServiceI.getNeedOrg(map);
        
        List<Orgnization> orgList = new ArrayList<Orgnization>();
        for (Orgnization org : cate){
            
             List<CategoryAssigned> caList  = cateAssignService.findCaListByOrgId(org.getId());
             String cateNames = "";
             for (CategoryAssigned ca : caList) {
                  if (ca != null && StringUtils.isNotBlank(ca.getCateName())){
                      cateNames += ca.getCateName() + ",";
                  }
             }
             if (StringUtils.isNotBlank(cateNames)){
                 org.setCateNames(cateNames.substring(0,cateNames.length()-1));
             } 
             
             orgList.add(org);
             
        }
        model.addAttribute("cate",orgList);
        model.addAttribute("name", name);
        model.addAttribute("princinpal", princinpal);
        model.addAttribute("list",new PageInfo<Orgnization>(cate));
        return "ses/ppms/categoryparam/allocate";
    }
    /**
     * @Title: edit_allocate
     * @author Zhang XueFeng/	
     * @Description:对部门和品目进行分配
     * @param 
     * @return string
     */
    @Deprecated
    @RequestMapping("/edit_allocate")
    public String allocate(HttpServletRequest request,HttpServletResponse response,
                           String ids,String id,String status){//ids  是树的节点   id是部门id
        String[] cateid = ids.split(","); 
        Orgnization org = orgnizationServiceI.findByCategoryId(id);
        org.setStatus(Integer.valueOf(status));
        for (int i = 0; i < cateid.length; i++) {
            Category category=categoryService.selectByPrimaryKey(cateid[i]);
            category.setOrgnization(org);
        }
        orgnizationServiceI.updateByCategoryId(org);
        return "redirect:query_orgnization.html";

    }
    
    /**
     * 
     *〈简述〉
     * 获取授权后的品目信息
     *〈详细描述〉
     * @author myc
     * @param request {@link HttpServletRequest}
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/assignedRes",produces= "application/json;charset=UTF-8")
    public List<CategotyBean> categoryResult(HttpServletRequest request){
       String orgIds = request.getParameter("orgIds");
       List<CategotyBean> list = cateAssignService.getCateAssignedRes(orgIds);
       return list;
    }
    
    /**
     * 
     *〈简述〉 
     *  分配任务
     *〈详细描述〉
     * @author myc
     * @param request {@link HttpServletRequest}
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/assigned",produces="text/html;charset=UTF-8")
    public String  assigned(HttpServletRequest request){
        
        String orgIds = request.getParameter("orgId");
        String cateIds = request.getParameter("cateId");
       String cateNames="";
        try {
        	cateNames=java.net.URLDecoder.decode(request.getParameter("cateName"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
      
        return cateAssignService.assigned(orgIds, cateIds, cateNames);
        
    }
    
    /**
     * 
     *〈简述〉
     * 取消分配
     *〈详细描述〉
     * @author myc
     * @param request {@link HttpServletRequest}
     * @return 
     */
    @ResponseBody
    @RequestMapping(value="/unassigned",produces="text/html;charset=UTF-8")
    public String unassigned(HttpServletRequest request){
        String orgIds = request.getParameter("orgId");
        String cateIds = request.getParameter("cateId");
        return cateAssignService.unassigned(orgIds, cateIds);
    }
    
    /**
     * 
     *〈简述〉
     *  获取已分配的品目Id
     *〈详细描述〉
     * @author myc
     * @param orgId 组织机构
     * @return 已分配的组织机构id
     */
    @ResponseBody
    @RequestMapping(value = "/allocaItemIds",produces="application/json;charset=UTF-8")
    public List<String> getAssignedItemIds(String orgId){
        
        return cateAssignService.getAllocationItemIds(orgId);
    }
    
    
    /**
     * @Title: abrogate_allocate
     * @author Zhang XueFeng
     * @Description:取消已分配的部门
     * @param 
     * @return string
     */
    @RequestMapping("/abrogate_allocate")
    public String abrogateAllocate(HttpServletRequest request,String id,String status){
        String[] ids=id.split(",");
        for (int i = 0; i < ids.length; i++) {
            Orgnization org = orgnizationServiceI.findByCategoryId(ids[i]);
            org.setStatus(Integer.valueOf(status));
            orgnizationServiceI.updateByCategoryId(org);
        }
        for (int i = 0; i < ids.length; i++) {
            List<Category> cate = categoryService.findByOrgId(ids[i]);
            for (Category category : cate) {
                category.setOrgnization(null);
                categoryService.updateByPrimaryKeySelective(category);
            }
        }
        return "redirect:query_orgnization.html";
    }
    /**************************************************按产品查询和按目录查询************************************************************************/

    /**
     * @Title: getCategoryparam
     * @author Zhang XueFeng/	
     * @Description:进入查询页面
     * @param id
     * @return string
     */
    @RequestMapping("/get_categoryparam")
    public String getCategoryparam(){
        return "ses/ppms/categoryparam/search";
    }
    /**
     * @Title: search_categoryname
     * @author Zhang XueFeng/	
     * @Description:根据产品名称进行模糊查
     * @param id
     * @return string
     */
    @RequestMapping("/search_categoryname")
    public String searchName(HttpServletRequest request,Category category,Integer page,Model model){
        String name = request.getParameter("name");
        if (page==null) {
            page=1;
        }
        Map<String, Object> map= new HashMap<String, Object>();
        map.put("name", name);
        map.put("page", page);
        List<Category>  list = categoryService.listByKeyword(map);
        String ids=null;

        for (Category cate : list) {
            if (cate.getOrgnization()!=null) {

                ids+=cate.getOrgnization().getId()+",";
            }
        }
        List<Object> catelist = new ArrayList<Object>();
        if(ids!=null){
            String[]  id =ids.split(",");
            for (int i = 0; i < id.length; i++) {
                Orgnization  org = orgnizationServiceI.findByCategoryId(id[i]);
                category.setOrgnization(org);
                catelist.add(category);
            }
        }
        model.addAttribute("list",new PageInfo<Category>(list));
        model.addAttribute("cate",catelist);
        return "ses/ppms/categoryparam/search";
    }
    /**
     * @Title: checkCategoryparam
     * @author Zhang XueFeng/	
     * @Description:进入按目录查询页面
     * @param id
     * @return string
     */ 
    @RequestMapping("/check_categoryparam")
    public String checkCategoryparam(){
        return "ses/ppms/categoryparam/searchinfo";
    }  

    /**
     * @Title: search_info
     * @author: Wang Zhaohua
     * @date: 2016-10-10 下午8:23:29
     * @Description: 根据品目 ID 查找技术参数
     * @param: @param model
     * @param: @param categoryId
     * @param: @return
     * @return: String
     */

    @RequestMapping("/search_info")
    public void searchInfo(String id,HttpServletResponse response){
        List<CategoryAptitude> cateaptitude = categoryAptitudeService.findListByCategoryId(id);
        String productname = "";
        String salename = "";
        for (int i = 0; i < cateaptitude.size(); i++) {
            productname+= cateaptitude.get(i).getProductName()+",";
            salename+= cateaptitude.get(i).getSaleName()+",";
        }
        List<CategoryParam> categoryParam = categoryParamService.findListByCategoryId(id);
        String name = "";
        String value = "";
        for (int i = 0; i < categoryParam.size(); i++) {
            name+= categoryParam.get(i).getName()+",";
            value+= categoryParam.get(i).getValueType()+",";
        }
        Category cate = categoryService.selectByPrimaryKey(id);	
        allListNews.put("productname", productname);
        allListNews.put("salename", salename);
        allListNews.put("name", name);
        allListNews.put("value", value);
        allListNews.put("cate", cate);
        super.writeJson(response, allListNews);
    } 

    /***********************************************************************************************************************************/
    @RequestMapping(value = "category_param")
    public String listByCategoryIdAndProductsId(Model model, String categoryId, String supplierId) {
//        List<CategoryParam> list = categoryParamService.findParamByCategoryIdAndProductsId(categoryId, productsId);
//        model.addAttribute("list", list);
//        model.addAttribute("categoryId", categoryId);
//        model.addAttribute("productsId", productsId);
    	
    	List<CategoryParameter> list = categoryParameterService.getParametersByItemId(categoryId);
    	for(CategoryParameter param:list){
    		DictionaryData dic = DictionaryDataUtil.findById(param.getParamTypeId());
    		param.setParamTypeId(dic.getCode());
    	}
    	model.addAttribute("list", list);
    	model.addAttribute("categoryId", categoryId);
    	model.addAttribute("supplierId", supplierId);
    	model.addAttribute("sysKey", Constant.SUPPLIER_SYS_KEY);
    	List<DictionaryData> data = DictionaryDataUtil.find(14);
 
    	for(DictionaryData dic:data){
    		if(dic.getCode().equals("ATTACHMENT")){
    			model.addAttribute("attachmentId",dic.getId());
    		}
    	}
    	String attid = UUID.randomUUID().toString().replaceAll("-", "");
    	model.addAttribute("attid",attid);
		
        return "ses/sms/supplier_register/add_param";
    }

    /*********************************************打开品目树**************************************************************************************/

    /**
     * 
     *〈简述〉品目管理
     *〈详细描述〉
     * @author Wang Wenshuai
     * @return
     */
    @RequestMapping("/crudCategory")
    public String crudCategory(HttpServletRequest request,Model model,Orgnization orgnization){
        String name =request.getParameter("name");
        String princinpal= request.getParameter("princinpal");
        Integer page = null;
        if (request.getParameter("page")!=""&&request.getParameter("page")!=null) {
            page = Integer.parseInt(request.getParameter("page"));
        }else{
            page=1;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name",name);
        map.put("princinpal", princinpal);
        map.put("page", page);
        List<Orgnization>  cate = orgnizationServiceI.findByName(map);
        model.addAttribute("cate",cate);
        model.addAttribute("list",new PageInfo<Orgnization>(cate));
        return "ses/ppms/categoryparam/create";

    }


    /**
     * 
     *〈简述〉品目参数管理
     *〈详细描述〉
     * @author Wang Wenshuai
     * @return
     */
    @RequestMapping("/parameter")
    public String parameter(){
        return "ses/ppms/categoryparam/parameter";
    }
    


}
