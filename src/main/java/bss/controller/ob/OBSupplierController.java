package bss.controller.ob;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ses.model.bms.Category;
import ses.model.bms.CategoryTree;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.sms.Supplier;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;
import ses.service.sms.SupplierService;
import ses.util.PathUtil;
import bss.model.ob.OBProduct;
import bss.model.ob.OBSupplier;
import bss.service.ob.OBProductService;
import bss.service.ob.OBSupplierService;
import bss.util.ExcelUtil;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
import common.model.UploadFile;
import common.utils.JdcgResult;

/**
 * 
 * Description：
 * 
 * @author zhang shubin
 * @version
 * @since JDK1.7
 * @date 2017年3月7日 下午4:19:54
 * 
 */
@Controller
@Scope("prototype")
@RequestMapping("/obSupplier")
public class OBSupplierController  {

	@Autowired
	private OBSupplierService oBSupplierService;

	@Autowired
	private OBProductService oBProductService;
	
	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private CategoryService categoryService;
	
    @Autowired
    private DictionaryDataServiceI dictionaryDataServiceI;

	/**
	 * 
	 * Description: 查询供应商列表
	 * 
	 * @author zhang shubin
	 * @version 2017年3月7日
	 * @param @param example
	 * @param @param model
	 * @param @param page
	 * @param @return
	 * @return String
	 * @exception
	 */
	/*@RequestMapping("/list")
	public String list(OBProduct example, Model model, Integer page) {
		if (page == null) {
			page = 1;
		}
		List<OBProduct> list = oBProductService.selectByExample(example, page);
		PageInfo<OBProduct> info = new PageInfo<>(list);
		List<OBSupplier> numlist = oBSupplierService.selectSupplierNum();
		for (OBSupplier ob : numlist) {
			if (ob.getnCount() == null) {
				ob.setnCount(0);
			}
		}
		model.addAttribute("numlist", numlist);
		model.addAttribute("supplierinfo", info);
		model.addAttribute("supplierproductExample", example);
		return "bss/ob/addSupplier/addSupplierlist";
	}*/

	/**
	 * 
	 * Description: 跳转添加供应商页面
	 * 
	 * @author zhang shubin
	 * @version 2017年3月8日
	 * @param @param request
	 * @param @return
	 * @return String
	 * @exception
	 */
	@RequestMapping("/addSupplieri")
	public String addSupplieri(HttpServletRequest request,Model model) {
		String supid = UUID.randomUUID().toString().replaceAll("-", "");
		OBSupplier obSupplier = new OBSupplier();
		obSupplier.setId(supid);
		model.addAttribute("obSupplier",obSupplier);
		return "bss/ob/addSupplier/addSupplier";
	}

	/**
	 * 
	 * Description: 查看供应商列表
	 * 
	 * @author zhang shubin
	 * @version 2017年3月7日
	 * @param @param model
	 * @param @param request
	 * @param @return
	 * @return String
	 * @exception
	 */
	@RequestMapping("/supplier")
	public String supplier(@CurrentUser User user,Model model, HttpServletRequest request, Integer page) {
		if (page == null) {
			page = 1;
		}
		String orgTyp = null;
		if(user != null){
			if(user.getOrg().getTypeName().equals("1")){
				orgTyp = user.getOrg().getTypeName();
			}
		}
		int status = request.getParameter("status") == null ? 0 : Integer
				.parseInt(request.getParameter("status"));
		String supplierName = request.getParameter("supplierName") == null ? "" : request.getParameter("supplierName");
		String smallPointsId = request.getParameter("smallPointsId") == null ? "" : request.getParameter("smallPointsId");
		List<OBSupplier> list = oBSupplierService.selectByProductId(null, page,
				status,supplierName,null,smallPointsId);
		if(list != null){
			for (OBSupplier obSupplier : list) {
				String id = obSupplier.getSmallPointsId();
				if(id != null){
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("id", id);
					List<Category> clist = categoryService.findCategoryByParentNode(map);
					String str = "";
					for (Category category : clist) {
						if(!obSupplier.getSmallPoints().getName().equals(category.getName())){
							str += category.getName() +"/";
						}
						
					}
					str+=obSupplier.getSmallPoints().getName();
					obSupplier.setPointsName(str);
				}
			}
		}
		Category cl = categoryService.findById(smallPointsId);
		if(cl != null){
			model.addAttribute("catName", cl.getName());
		}
		PageInfo<OBSupplier> info = new PageInfo<>(list);
		model.addAttribute("info", info);
		model.addAttribute("status", status);
		model.addAttribute("orgTyp", orgTyp);
		model.addAttribute("supplierName", supplierName);
		model.addAttribute("smallPointsId", smallPointsId);
		return "bss/ob/addSupplier/supplierlist";
	}

	/**
	 * 
	 * Description: 删除
	 * 
	 * @author zhang shubin
	 * @version 2017年3月7日
	 * @param @param request
	 * @return void
	 * @exception
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(HttpServletRequest request) {
		String ids = request.getParameter("ids");
		String productId = ids.trim();
		if (productId.length() != 0) {
			String[] uniqueIds = productId.split(",");
			for (String str : uniqueIds) {
				oBSupplierService.deleteByPrimaryKey(str);
			}

		}
	}

	/**
	 * 
	 * Description: 查询供应商信息
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月9日 
	 * @param  @return 
	 * @return String 
	 * @exception
	 */
	@RequestMapping("/findAllSupplier")
	@ResponseBody
	public List<Supplier> findAllSupplier(){
		List<Supplier> list = supplierService.findQualifiedSupplier();
		return list;
	}
	
	/**
	 * 
	 * Description: 根据供应商ID查询统一社会信用代码
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月9日 
	 * @param  @param request
	 * @param  @return 
	 * @return String 
	 * @exception
	 */
	@RequestMapping("/findUsccById")
	@ResponseBody
	public String findUsccById(HttpServletRequest request){
		String id = request.getParameter("option") == null ? "" :request.getParameter("option");
		Supplier supplier = supplierService.selectById(id);
		String creditCode = "";
		if(supplier != null){
			creditCode = supplier.getCreditCode();
		}
		return creditCode;
	}
	
	/**
	 * 
	 * Description: 添加供应商
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月9日 
	 * @param  @param model
	 * @param  @param request
	 * @param  @param obSupplier
	 * @param  @return 
	 * @return String 
	 * @exception
	 */
	@RequestMapping("/add")
	public String add(Model model,HttpServletRequest request,OBSupplier obSupplier){
		boolean flag = true;
		if(obSupplier.getSupplierId() == null || obSupplier.getSupplierId() == ""){
			flag = false;
			model.addAttribute("errorName","供应商名称不能为空");
		}
		if(obSupplier.getCertValidPeriod() == null){
			flag = false;
			model.addAttribute("errorCertValidPeriod","证书有效期不能为空");
		}
		if(obSupplier.getQualityInspectionDep() == null || obSupplier.getQualityInspectionDep() == ""){
			flag = false;
			model.addAttribute("errorQualityInspectionDep","质检机构不能为空");
		}
		if(obSupplier.getContactName() == null || obSupplier.getContactName() == ""){
			flag = false;
			model.addAttribute("errorContactName","联系人姓名不能为空");
		}
		if(obSupplier.getContactTel() == null || obSupplier.getContactTel() == ""){
			flag = false;
			model.addAttribute("errorContactTel","联系人电话不能为空");
		}else {
			if(isMobileNO(obSupplier.getContactTel()) == false){
				flag = false;
				model.addAttribute("errorContactTel","请输入正确手机号码");
			}
		}
		if(obSupplier.getCertCode() == null || obSupplier.getCertCode() == ""){
			flag = false;
			model.addAttribute("errorCertCode","资质证书编号不能为空");
		}else{
			if(oBSupplierService.yzzsCode(obSupplier.getCertCode(),null) > 0){
				flag = false;
				model.addAttribute("errorCertCode","资质证书编号不能重复");
			}
		}
		if(obSupplier.getUscc() == null || obSupplier.getUscc() == ""){
			flag = false;
			model.addAttribute("errorUscc","统一社会信用代码不能为空");
		}
		if(oBSupplierService.yzShangchuan(obSupplier.getId()) < 1){
			flag = false;
			model.addAttribute("errorShangchuan","请上传资质证书图片");
		}
		if(obSupplier.getSmallPointsId() == null || obSupplier.getSmallPointsId() == ""){
			flag = false;
			model.addAttribute("errorsmallPoints","产品目录不能为空");
		}else{
			if(oBSupplierService.yzSupplierName(obSupplier.getSupplierId(), obSupplier.getSmallPointsId(),null) > 0){
				flag = false;
				model.addAttribute("errorsmallPoints","已经添加过该目录了");
			}
		}
		if(flag == true){
			HttpSession session = request.getSession();
			obSupplier.setIsDeleted(0);
			User user = (User) session.getAttribute("loginUser");
			String userId = "";
			if(user != null){
				userId = user.getId();
			}
			obSupplier.setCreaterId(userId);
			obSupplier.setCreatedAt(new Date());
			oBSupplierService.insertSelective(obSupplier);
			return "redirect:/obSupplier/supplier.do";
		}else{
			if(obSupplier.getSmallPointsId() != null){
				Category category = categoryService.findById(obSupplier.getSmallPointsId());
				if(category != null){
					model.addAttribute("catName", category.getName());
				}
			}
			model.addAttribute("obSupplier", obSupplier);
			return "bss/ob/addSupplier/addSupplier";
		}
		
	}
	
	
	@RequestMapping("/toedit")
	public String toedit(Model model,HttpServletRequest request){
		String id = request.getParameter("suppid") == null ? "" : request.getParameter("suppid");
		OBSupplier obSupplier = oBSupplierService.selectByPrimaryKey(id);
		model.addAttribute("obSupplier", obSupplier);
		if(obSupplier != null){
			Category category = categoryService.findById(obSupplier.getSmallPointsId());
			model.addAttribute("catName", category.getName());
		}
		return "bss/ob/addSupplier/editSupplier";
	}
	
	/**
	 * 
	 * Description: 修改
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月9日 
	 * @param  @param model
	 * @param  @param request
	 * @param  @param obSupplier
	 * @param  @return 
	 * @return String 
	 * @exception
	 */
	@RequestMapping("/edit")
	public String edit(Model model,HttpServletRequest request,OBSupplier obSupplier){
		boolean flag = true;
		if(obSupplier.getSupplierId() == null || obSupplier.getSupplierId() == ""){
			flag = false;
			model.addAttribute("errorName","供应商名称不能为空");
		}else{
			if(oBSupplierService.yzSupplierName(obSupplier.getSupplierId(), obSupplier.getProductId(),obSupplier.getId()) > 0){
				flag = false;
				model.addAttribute("errorName","已经添加过该供应商了");
			}
		}
		if(obSupplier.getCertValidPeriod() == null){
			flag = false;
			model.addAttribute("errorCertValidPeriod","证书有效期不能为空");
		}
		if(obSupplier.getQualityInspectionDep() == null || obSupplier.getQualityInspectionDep() == ""){
			flag = false;
			model.addAttribute("errorQualityInspectionDep","质检机构不能为空");
		}
		if(obSupplier.getContactName() == null || obSupplier.getContactName() == ""){
			flag = false;
			model.addAttribute("errorContactName","联系人姓名不能为空");
		}
		if(obSupplier.getContactTel() == null || obSupplier.getContactTel() == ""){
			flag = false;
			model.addAttribute("errorContactTel","联系人电话不能为空");
		}
		if(obSupplier.getContactTel() == null || obSupplier.getContactTel() == ""){
			flag = false;
			model.addAttribute("errorContactTel","联系人电话不能为空");
		}else {
			if(obSupplier.getContactTel().matches("[0-9]+") == false){
				flag = false;
				model.addAttribute("errorContactTel","请输入正确电话号码");
			}
		}
		if(obSupplier.getCertCode() == null || obSupplier.getCertCode() == ""){
			flag = false;
			model.addAttribute("errorCertCode","资质证书编号不能为空");
		}else{
			if(oBSupplierService.yzzsCode(obSupplier.getCertCode(),obSupplier.getId()) > 0){
				flag = false;
				model.addAttribute("errorCertCode","资质证书编号不能重复");
			}
		}
		if(obSupplier.getUscc() == null || obSupplier.getUscc() == ""){
			flag = false;
			model.addAttribute("errorUscc","统一社会信用代码不能为空");
		}
		if(oBSupplierService.yzShangchuan(obSupplier.getId()) < 1){
			flag = false;
			model.addAttribute("errorShangchuan","请上传资质证书图片");
		}
		if(obSupplier.getSmallPointsId() == null || obSupplier.getSmallPointsId() == ""){
			flag = false;
			model.addAttribute("errorsmallPoints","产品目录不能为空");
		}else{
			if(oBSupplierService.yzSupplierName(obSupplier.getSupplierId(), obSupplier.getSmallPointsId(),obSupplier.getId()) > 0){
				flag = false;
				model.addAttribute("errorsmallPoints","已经添加过该目录了");
			}
		}
		if(flag == true){
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute("loginUser");
			String userId = "";
			if(user != null){
				userId = user.getId();
			}
			obSupplier.setCreaterId(userId);
			obSupplier.setUpdatedAt(new Date());
			oBSupplierService.updateByPrimaryKeySelective(obSupplier);
			return "redirect:/obSupplier/supplier.do";
		}else{
			Category category = categoryService.findById(obSupplier.getSmallPointsId());
			model.addAttribute("catName", category.getName());
			model.addAttribute("obSupplier", obSupplier);
			return "bss/ob/addSupplier/editSupplier";
		}
		
	}
	
	/**
	 * 
	 * Description: 手机号验证
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月10日 
	 * @param  @param mobiles
	 * @param  @return 
	 * @return boolean 
	 * @exception
	 */
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("[0-9]{1,}");
		return p.matcher(mobiles).matches();
	}
	
	/**
	 * 
	 * Description: 模板下载
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月16日 
	 * @param  @param request
	 * @param  @param filename
	 * @param  @return
	 * @param  @throws IOException 
	 * @return ResponseEntity<byte[]> 
	 * @exception
	 */
	@RequestMapping("/download")
	public ResponseEntity<byte[]> download(HttpServletRequest request,
			String filename) throws IOException {
		String path = PathUtil.getWebRoot() + "excel/添加供应商模板.xlsx";
		File file = new File(path);
		HttpHeaders headers = new HttpHeaders();
		String fileName = new String("添加供应商模板.xlsx".getBytes("UTF-8"),
				"iso-8859-1");// 为了解决中文名称乱码问题
		headers.setContentDispositionFormData("attachment", fileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
				headers, HttpStatus.OK);
	}
	
	/**
	 * 
	 * Description: 导入excel
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月16日 
	 * @param  @param user
	 * @param  @param planDepName
	 * @param  @param file
	 * @param  @param type
	 * @param  @param planName
	 * @param  @param planNo
	 * @param  @param model
	 * @param  @return
	 * @param  @throws Exception 
	 * @return String 
	 * @exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/upload", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String uploadFile(@CurrentUser User user,MultipartFile file,HttpServletRequest request) throws Exception {
		String fileName = file.getOriginalFilename();
		if (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx")) {
			return "1";
		}
		List<OBSupplier> list = new ArrayList<OBSupplier>();
		Map<String, Object> maps = (Map<String, Object>) ExcelUtil
				.readOBSupplierExcel(file);
		list = (List<OBSupplier>) maps.get("list");
		
		String errMsg = (String) maps.get("errMsg");

		if (errMsg != null) {
			String jsonString = JSON.toJSONString(errMsg);
			return jsonString;
		}else{
			if(list != null){
				for (OBSupplier obSupplier : list) {
					String supid = UUID.randomUUID().toString().replaceAll("-", "");
					obSupplier.setId(supid);
					obSupplier.setIsDeleted(0);
					String userId = "";
					if(user != null){
						userId = user.getId();
					}
					obSupplier.setCreaterId(userId);
					obSupplier.setCreatedAt(new Date());
					oBSupplierService.insertSelective(obSupplier);
				}
			}
		}
		String jsonString = JSON.toJSONString(list);
		return jsonString;
	}

	
	/**
	 * 
	 * Description: 根据业务id查询主键id
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月21日 
	 * @param  @param request
	 * @param  @return 
	 * @return String 
	 * @exception
	 */
	@RequestMapping("/findBybusinessId")
	@ResponseBody
	public String findBybusinessId(HttpServletRequest request){
		String id = request.getParameter("id") == null ? "" : request.getParameter("id");
		Integer key = request.getParameter("key") == null ? 0 : Integer.parseInt(request.getParameter("key"));
		if(id != "" && key != 0){
			List<UploadFile> list = oBSupplierService.findBybusinessId(id, key);
			if(list != null){
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < list.size(); i++) {
					sb. append(list.get(i).getId());
					sb. append(",");
				}
				String s = sb.toString();
				return s;
			}
		}
		return "";
	}
	
    /**
     * 
     * Description: 根据id查询目录树
     * 
     * @author  zhang shubin
     * @version  2017年3月17日 
     * @param  @param category
     * @param  @return 
     * @return String 
     * @exception
     */
    @ResponseBody
    @RequestMapping(value="/createtreeByproduct", produces = "application/json;charset=utf-8")
    public String createtreeById(HttpServletResponse response, Category category,String name){
    	List<CategoryTree> jList=new ArrayList<CategoryTree>();
    	if((name!=null&&!"".equals(name))){
			try {
				if(name!=null&&!"".equals(name)){
					name=java.net.URLDecoder.decode(name, "UTF-8");
				}	
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
    	}
		DictionaryData data=new DictionaryData();
        data.setKind(6);
        List<DictionaryData> listByPage = dictionaryDataServiceI.listByPage(data, 1);
        for (DictionaryData dictionaryData : listByPage) {
        	if(dictionaryData.getName().equals("物资")){
            CategoryTree ct=new CategoryTree();
            ct.setId(dictionaryData.getId());
            ct.setName(dictionaryData.getName());
            ct.setIsParent("true");
            ct.setClassify(dictionaryData.getCode());
            jList.add(ct);
        	}
        }
	       
    	List<OBProduct> list = oBProductService.selectAllAmallPointsId(name == null ? null : name.trim());
    	Set<String> set=new HashSet<String>();
    	if(list!=null&&list.size()>0){
    		for(int i=0;i<list.size();i++){
    			if(list.get(i)!=null){
    				if(list.get(i).getSmallPointsId() != null&& !"".equals(list.get(i).getSmallPointsId())){
        				set.add(list.get(i).getSmallPointsId());
        			}
    			}
    			
    		}
    	}
		
		Iterator<String> it=set.iterator();
		Set<Category> categories=new HashSet<Category>();
		while(it.hasNext()){
			HashMap<String,Object> map=new HashMap<String, Object>();
			map.put("id", it.next());
			List<Category> catego = categoryService.findCategoryByParentNode(map);
			for(int j=0;j<catego.size();j++){
				categories.add(catego.get(j));
			}
		}
		
        for(Category cate:categories){
            List<Category> cList=categoryService.findTreeByPidPublish(cate.getId());
            CategoryTree ct=new CategoryTree();
            if(!cList.isEmpty()){
                ct.setIsParent("true");
            }else{
                ct.setIsParent("false");
            }
            
            ct.setId(cate.getId());
            ct.setName(cate.getName());
            ct.setpId(cate.getParentId());
            ct.setKind(cate.getKind());
            ct.setStatus(cate.getStatus());
            jList.add(ct);
        }

		JSONArray res=JSONArray.fromObject(jList);
        return res.toString();
	}
}
