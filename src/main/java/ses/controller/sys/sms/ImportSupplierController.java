package ses.controller.sys.sms;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.Todos;
import ses.model.bms.User;
import ses.model.sms.ImportSupplierWithBLOBs;
import ses.service.bms.TodosService;
import ses.service.sms.ImportSupplierService;
import ses.util.ValidateUtils;

import com.github.pagehelper.PageInfo;

/**
 * @Title: ImportSupplierController
 * @Description: 进口供应商注册审核控制层
 * @author: Song Biaowei
 * @date: 2016-9-7下午6:09:03
 */
@Controller
@Scope("prototype")
@RequestMapping("/importSupplier")
public class ImportSupplierController {
	@Autowired
	private ImportSupplierService importSupplierService;
	@Autowired
	private TodosService todosService;

	/**
	* @Title: beforeRegister
	* @author Song Biaowei
	* @date 2016-9-6 上午11:31:17  
	* @Description:点击进口供应商注册 
	* @param @return      
	* @return String
	 */
	@RequestMapping("list")
	public String registerStart(ImportSupplierWithBLOBs is,String supName,String supType,HttpServletRequest request,Integer page,Model model){
		if(supName!=null&&!supName.equals("")){
			is.setName(supName);
		}
		if(supType!=null&&!supType.equals("")){
			is.setSupplierType(supType);
		}
		List<ImportSupplierWithBLOBs> isList=importSupplierService.selectByFsInfo(is,page==null?1:page);
		request.setAttribute("isList", new PageInfo<>(isList));
		model.addAttribute("name", is.getName());
		model.addAttribute("supplierType", is.getSupplierType());
		return "ses/sms/import_supplier/list";
	}
	
	/**
	 * @Title: edit
	 * @author Song Biaowei
	 * @date 2016-9-27 下午4:35:59  
	 * @Description: 进入新增页面
	 * @param @param is
	 * @param @param model
	 * @param @param request
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("edit")
	public String edit(ImportSupplierWithBLOBs is,Model model,HttpServletRequest request){
		ImportSupplierWithBLOBs importSupplierWithBLOBs = importSupplierService.selectByPrimaryKey(is);
		model.addAttribute("is", importSupplierWithBLOBs);	
		return "ses/sms/import_supplier/edit";
	}
	
	@RequestMapping(value="auditShow")
	public String auditShow(ImportSupplierWithBLOBs is,Model model,HttpServletRequest request){
		ImportSupplierWithBLOBs importSupplierWithBLOBs = importSupplierService.selectByPrimaryKey(is);
		model.addAttribute("is", importSupplierWithBLOBs);
		return "ses/sms/import_supplier/audit";
	}
	
	@RequestMapping(value="audit")
	public String audit(ImportSupplierWithBLOBs is,Model model,HttpServletRequest request){
		ImportSupplierWithBLOBs importSupplierWithBLOBs = importSupplierService.selectByPrimaryKey(is);
		model.addAttribute("is", importSupplierWithBLOBs);
		importSupplierService.updateRegisterInfo(is);
		if(is.getStatus()!=0){
			todosService.updateIsFinish("importSupplier/auditShow.html?id="+is.getId());
		}
		return "redirect:list.html";
	}
	
	/**
	 * @Title: show
	 * @author Song Biaowei
	 * @date 2016-9-27 下午4:36:12  
	 * @Description: 查看页面
	 * @param @param is
	 * @param @param model
	 * @param @param request
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("show")
	public String show(ImportSupplierWithBLOBs is,Model model,HttpServletRequest request){
		ImportSupplierWithBLOBs importSupplierWithBLOBs = importSupplierService.selectByPrimaryKey(is);
		model.addAttribute("is", importSupplierWithBLOBs);	
		return "ses/sms/import_supplier/show";
	}
	
	/**
	 * @Title: delete_soft
	 * @author Song Biaowei
	 * @date 2016-9-27 下午4:36:23  
	 * @Description: 删除 
	 * @param @param ids
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("delete")
	public String delete_soft(String ids) {
		String[] id = ids.split(",");
		for (String str : id) {
			importSupplierService.delete(str);
		}
		return "redirect:list.html";
	}
	
	/**
	 * @Title: update
	 * @author Song Biaowei
	 * @date 2016-9-27 下午4:36:35  
	 * @Description: 更新到数据库
	 * @param @param is
	 * @param @param files
	 * @param @param model
	 * @param @param request
	 * @param @return
	 * @param @throws IOException      
	 * @return String
	 */
	@RequestMapping("update")
	public String update(@Valid ImportSupplierWithBLOBs is, BindingResult result, Model model,HttpServletRequest request) throws IOException{
		if(result.hasErrors()){
			List<FieldError> errors=result.getFieldErrors();
			for(FieldError fieldError:errors){
				model.addAttribute("ERR_"+fieldError.getField(), fieldError.getDefaultMessage());
			}
			model.addAttribute("is", is);
			if(!ValidateUtils.Zipcode(is.getPostCode()+"")){
				model.addAttribute("ERR_postCode", "请输入正确的邮编");
			}
			if(!ValidateUtils.Mobile(is.getTelephone()+"")){
				model.addAttribute("ERR_telephone", "请输入正确的手机号码");
			}
			return "ses/sms/import_supplier/edit";
		}
		
		is.setUpdatedAt(new Timestamp(new Date().getTime()));
		importSupplierService.updateRegisterInfo(is);
		return "redirect:list.html";
	}
	
	/**
	 * @Title: register
	 * @author Song Biaowei
	 * @date 2016-9-9 下午5:15:47  
	 * @Description:注册第一步 
	 * @param @param user
	 * @param @return      
	 * @return String
	 */
	@RequestMapping("register")
	public String register(ImportSupplierWithBLOBs is,HttpServletRequest request,Model model){
		//保存基本信息返回 id作为外键保存到user用户表里面去
		return "ses/sms/import_supplier/register";
	}
	
	/**
	 * @Title: registerEnd
	 * @author Song Biaowei
	 * @date 2016-9-8 上午10:25:06  
	 * @Description: 注册 0代表登记成功，1代表审核通过，2代表审核退回
	 * @param @param is
	 * @param @return      
	 * @return String
	 * @throws IOException 
	 */
	@RequestMapping("registerEnd")
	public String registerEnd(@Valid ImportSupplierWithBLOBs is, BindingResult result,HttpServletRequest request,Model model) throws IOException{
		if(result.hasErrors()){
			List<FieldError> errors=result.getFieldErrors();
			for(FieldError fieldError:errors){
				model.addAttribute("ERR_"+fieldError.getField(), fieldError.getDefaultMessage());
			}
			model.addAttribute("is", is);
			if(!ValidateUtils.Zipcode(is.getPostCode()+"")){
				model.addAttribute("ERR_postCode", "请输入正确的邮编");
			}
			if(!ValidateUtils.Mobile(is.getTelephone()+"")){
				model.addAttribute("ERR_telephone", "请输入正确的手机号码");
			}
			return "ses/sms/import_supplier/register";
		}
		is.setStatus((short)0);
		is.setCreatedAt(new Timestamp(new Date().getTime()));
		User user1=(User) request.getSession().getAttribute("loginUser");
		is.setCreatorId(user1.getId());
		is.setOrgId(user1.getOrg().getId());
		importSupplierService.register(is);
		Todos todo=new Todos();
		//自己的id
		todo.setSenderId(user1.getId());
		//代办人id
		todo.setReceiverId(user1.getOrg().getId());
		//待办类型 供应商
		todo.setUndoType((short)1);
		//标题
		todo.setName("进口供应商审核");
		//逻辑删除 0未删除 1已删除
		todo.setIsDeleted((short)0);
		todo.setCreatedAt(new Date());
		todo.setUrl("importSupplier/auditShow.html?id="+is.getId());
		todosService.insert(todo);
		return "redirect:list.html";
	}
	


	/**
	 * @Title: checkLoginName
	 * @author Song Biaowei
	 * @date 2016-9-9 上午9:04:40  
	 * @Description: 验证用户名
	 * @param @param is
	 * @param @return
	 * @param @throws Exception      
	 * @return boolean
	 */
	@RequestMapping("checkLoginName")
	@ResponseBody
	public boolean checkLoginName(ImportSupplierWithBLOBs is) throws Exception {
		boolean flag=false;
		if(is.getId()!=null){
			List<ImportSupplierWithBLOBs> isList=importSupplierService.selectByFsInfo(is,1);
			//isList==null 说明没有重复
			if(isList.size()==0){
				flag=true;
			//如果isList!=null 并且查出来的id和条件id一样说明没有修改
			}else if(isList!=null&&isList.size()==1&&is.getId().equals(isList.get(0).getId())){
				flag=true;
			}else{
				for(ImportSupplierWithBLOBs iswbs :isList){
					//这种情况说明搜索出来了不止一条，名字相同id相同则是本身
					if(iswbs.getName().equals(is.getName())&&is.getId().equals(isList.get(0).getId())){
						flag=true;
						continue;
					}else if(iswbs.getName().equals(is.getName())&&!is.getId().equals(isList.get(0).getId())){
						flag=false;
						break;
					}else{
						flag=true;
					}
				}
			}
				return flag;
		}else{
			List<ImportSupplierWithBLOBs> isList=importSupplierService.selectByFsInfo(is,1);
			for(ImportSupplierWithBLOBs iswbs :isList){
				if(!iswbs.getName().equals(is.getName())){
					flag=true;
					continue;
				}else{
					flag=false;
					break;
				}
			}
			if(isList.size()==0){
				flag=true;
			}
		}
		return flag;	
	}
	/**
	 * @Title: checkSupName
	 * @author Song Biaowei
	 * @date 2016-9-27 下午5:48:09  
	 * @Description: TODO 
	 * @param @param is
	 * @param @return
	 * @param @throws Exception      
	 * @return boolean
	 */
	@RequestMapping("checkSupName")
	@ResponseBody
	public boolean checkSupName(ImportSupplierWithBLOBs is) throws Exception {
		boolean flag=false;
		if(is.getId()!=null){
			List<ImportSupplierWithBLOBs> isList=importSupplierService.selectByFsInfo(is,1);
			//isList==null 说明没有重复
			if(isList.size()==0){
				flag=true;
			//如果isList!=null 并且查出来的id和条件id一样说明没有修改
			}else if(isList!=null&&isList.size()==1&&is.getId().equals(isList.get(0).getId())){
				flag=true;
			}else{
				for(ImportSupplierWithBLOBs iswbs :isList){
					//这种情况说明搜索出来了不止一条，名字相同id相同则是本身
					if(iswbs.getName().equals(is.getName())&&is.getId().equals(isList.get(0).getId())){
						flag=true;
						continue;
					}else if(iswbs.getName().equals(is.getName())&&!is.getId().equals(isList.get(0).getId())){
						flag=false;
						break;
					}else{
						flag=true;
					}
				}
			}
				return flag;
		}else{
			List<ImportSupplierWithBLOBs> isList=importSupplierService.selectByFsInfo(is,1);
			for(ImportSupplierWithBLOBs iswbs :isList){
				if(!iswbs.getName().equals(is.getName())){
					flag=true;
					continue;
				}else{
					flag=false;
					break;
				}
			}
			if(isList.size()==0){
				flag=true;
			}
		}
		return flag;
	}
	/*@RequestMapping("dayin")
	public ResponseEntity<byte[]> guidang(ImportSupplierAud cmt,HttpServletRequest request) throws Exception{
		Map<String, Object> dataMap = new HashMap<String, Object>();
		//文件名称
		String fileName =new String(("进口供应商注册信息表.docx").getBytes("UTF-8"), "UTF-8");
		String name = WordUtil.createWord(dataMap, "importSupplier.ftl",fileName, request);
		String filePath = request.getSession().getServletContext().getRealPath("/WEB-INF/upload_file/");
		File file=new File(filePath+"/"+name);  
        HttpHeaders headers = new HttpHeaders(); 
        String downFileName=new String("进口供应商注册信息表.doc".getBytes("UTF-8"),"iso-8859-1");//为了解决中文名称乱码问题  
        headers.setContentDispositionFormData("attachment", downFileName);   
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);   
        ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED); 
        file.delete();
        return entity;
	} */
}
