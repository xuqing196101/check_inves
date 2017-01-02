package bss.controller.pms;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ses.dao.bms.DictionaryDataMapper;
import ses.dao.oms.OrgnizationMapper;
import ses.model.bms.Category;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.service.bms.CategoryService;
import ses.service.bms.DictionaryDataServiceI;
import ses.util.DictionaryDataUtil;
import ses.util.PathUtil;
import bss.controller.base.BaseController;
import bss.formbean.PurchaseRequiredFormBean;
import bss.model.pms.PurchaseRequired;
import bss.service.pms.PurchaseRequiredService;
import bss.util.Excel;
import bss.util.ExcelUtil;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
import common.bean.ResponseBean;
import common.constant.Constant;
import common.constant.StaticVariables;
/**
 * 
 * @Title: PurcharseRequiredController
 * @Description:  采购需求计划类
 * @author Li Xiaoxiao
 * @date  2016年9月12日,下午1:54:34
 *
 */
@Controller
@Scope("prototype")
@RequestMapping("/purchaser")
public class PurchaseRequiredController extends BaseController{
	@Autowired
    private CategoryService categoryService;
	
	@Autowired
	private PurchaseRequiredService purchaseRequiredService;
	

	@Autowired
	private DictionaryDataServiceI dictionaryDataServiceI;
	
	@Autowired
	private OrgnizationMapper oargnizationMapper;
	
	@Autowired
	private DictionaryDataMapper dictionaryDataMapper;
	/**
	 * 
	* @Title: queryPlan
	* @Description: 条件查询分页
	* author: Li Xiaoxiao 
	* @param @param purchaseRequired
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/list")
	public String queryPlan(@CurrentUser User user,PurchaseRequired purchaseRequired,Integer page,Model model){
		purchaseRequired.setIsMaster(1);
		purchaseRequired.setStatus("1");
		if (page == null ){
		    page = StaticVariables.DEFAULT_PAGE;
		}
		purchaseRequired.setUserId(user.getId());
		List<PurchaseRequired> list = purchaseRequiredService.query(purchaseRequired,page);
		model.addAttribute("info", new PageInfo<PurchaseRequired>(list));
		model.addAttribute("inf", purchaseRequired);
		
		Map<String,Object> map=new HashMap<String,Object>();
		List<Orgnization> requires = oargnizationMapper.findOrgPartByParam(map);
		model.addAttribute("requires", requires);
		return "bss/pms/purchaserequird/list";
	}
	/**
	 * 
	* @Title: getById
	* @Description: 根据计划编号查询明细
	* author: Li Xiaoxiao 
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/queryByNo")
	public String getById(@CurrentUser User user,String planNo,Model model,String type){
		PurchaseRequired p=new PurchaseRequired();
		p.setUniqueId(planNo.trim());
		List<PurchaseRequired> list = purchaseRequiredService.queryUnique(p);
		model.addAttribute("kind", DictionaryDataUtil.find(5));//获取数据字典数据
		model.addAttribute("list", list);
		Map<String,Object> map=new HashMap<String,Object>();
		List<Orgnization> requires = oargnizationMapper.findOrgPartByParam(map);
		model.addAttribute("requires", requires);
		
		if(type.equals("1")){
			return "bss/pms/purchaserequird/view";
		}else{
			return "bss/pms/purchaserequird/edit";
		}
		
	}
	
	/**
	 * 
	* @Title: updateById
	* @Description: 根据id修改 
	* author: Li Xiaoxiao 
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/update")
	public String updateById(PurchaseRequiredFormBean list){
//		Map<String,Object> map=new HashMap<String,Object>();
		if(list!=null){
			if(list.getList()!=null&&list.getList().size()>0){
				for( PurchaseRequired p:list.getList()){
					if( p.getId()!=null){
						//历史数据
//						String id = UUID.randomUUID().toString().replaceAll("-", "");
//						map.put("oid", id);
//						Integer s=Integer.valueOf(purchaseRequiredService.queryByNo(p.getPlanNo()))+1;
//						map.put("historyStatus", s);
//						map.put("id", p.getId());
//						purchaseRequiredService.update(map);
						
						purchaseRequiredService.updateByPrimaryKeySelective(p);
						
						//保存新数据 
						// p.setHistoryStatus("0");
						//purchaseRequiredService.add(p);	
					}
				
					
				}
			}
		}
//		purchaseRequiredService.update(purchaseRequired);
		return "redirect:list.html";
	}
	/**
	 *   
	 * 
	* @Title: add
	* @Description: 添加跳转页面
	* author: Li Xiaoxiao 
	* @param @return     
	* @return String     
	* @throws  
	 */
	@RequestMapping("/add")
	public String add(@CurrentUser User user,Model model,String type) {
		model.addAttribute("user", user);
		model.addAttribute("list", DictionaryDataUtil.find(6));
		model.addAttribute("list2", DictionaryDataUtil.find(5));
		Map<String,Object> map=new HashMap<String,Object>();
		List<Orgnization> requires = oargnizationMapper.findOrgPartByParam(map);
		model.addAttribute("requires",requires);
	    model.addAttribute("orgName", user.getOrg().getName());
	    String typeId = DictionaryDataUtil.getId("PURCHASE_DETAIL");
	    
	    String fileId = UUID.randomUUID().toString().replaceAll("-", "");
	    model.addAttribute("fileId", fileId);
	    model.addAttribute("typeId", typeId);
	    model.addAttribute("planNo", randomPlano());
	    model.addAttribute("orgType", user.getOrg().getTypeName());
		return "bss/pms/purchaserequird/add";
	}
	
	
	@RequestMapping("/fileUpload")
	public String uploadPage(){
	    return "/bss/pms/purchaserequird/fileUpload";
	}
	
	/**
	* @Title: uploadFile
	* @Description: 导入excel表格数据
	* author: Li Xiaoxiao 
	* @param @return     
	* @return String     
	 * @throws IOException 
	 * @throws Exception
	 */
 

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/upload", produces="text/html;charset=UTF-8" )
	@ResponseBody
	public String uploadFile(@CurrentUser User user,String planDepName,MultipartFile file,String type,String planName,String planNo,Model model) throws Exception{
        String fileName = file.getOriginalFilename();  
        if(!fileName.endsWith(".xls")&&!fileName.endsWith(".xlsx")){
        	return "文件格式不支持";
        }  
        
		List<PurchaseRequired> list=new ArrayList<PurchaseRequired>();
//		ExcelUtil util=new ExcelUtil();
		    Map<String,Object>  maps= (Map<String, Object>) ExcelUtil.readExcel(file);
		     list = (List<PurchaseRequired>) maps.get("list");
		     String errMsg=(String) maps.get("errMsg");
		
		     if(errMsg!=null){
		          String jsonString = JSON.toJSONString(errMsg);
					return jsonString;
				
					
			}
		     
		String id = UUID.randomUUID().toString().replaceAll("-", "");
		String pid = UUID.randomUUID().toString().replaceAll("-", "");
		String cid = UUID.randomUUID().toString().replaceAll("-", "");
		String ccid = UUID.randomUUID().toString().replaceAll("-", "");
		String cccid = UUID.randomUUID().toString().replaceAll("-", "");
		String ccccid = UUID.randomUUID().toString().replaceAll("-", "");
		String plano = randomPlano();
		
		int len=list.size()-1;
		StringBuffer sbUp=new StringBuffer("");
		StringBuffer sbShow=new StringBuffer("");
		int count=1;
//		BigDecimal budget=BigDecimal.ZERO;
		for(int i=0;i<list.size();i++){
//			String id = UUID.randomUUID().toString().replaceAll("-", "");
	 
				PurchaseRequired p = list.get(i);
				p.setPlanNo(plano);
				if(p.getPlanName()!=null){
					 
				}else{
					p.setPlanName(planName);	
				}
				p.setPlanType(type);
				p.setHistoryStatus("0");
				p.setIsDelete(0);
				p.setIsMaster(count);
				p.setCreatedAt(new Date());
				p.setUserId(user.getId());
				/*if(p.getPurchaseType()!=null){
					DictionaryData data = dictionaryDataMapper.queryByName(p.getPurchaseType());
					p.setPurchaseType(data.getId());
				}*/
				
				//p.setOrganization(user.getOrg().getName());
				p.setDetailStatus(1);
				
//				if(p.getBudget()!=null){
//					budget=budget.add(p.getBudget());
//				}
			//顶级节点	
			 if(p.getSeq().matches("[\u4E00-\u9FA5]")&&!p.getSeq().contains("（")){
				 	 p.setSeq("一");
				 	 count=1;
				 	 p.setIsMaster(count);
					
					 p.setParentId("1");//注释
					 plano = randomPlano();
					 p.setPlanNo(plano);
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
			
			sbUp.append("pUp"+i+",");
			sbShow.append("pShow"+i+",");
			if(len==i){
				sbUp.append("pUp"+i);
				sbShow.append("pShow"+i);
			}
		}
		String jsonString = JSON.toJSONString(list);
		// purchaseRequiredService.batchAdd(list);
		
		return jsonString;
	}
	/**
	 * @throws IOException 
	 * 
	* @Title: addReq
	* @Description: 添加数据
	* author: Li Xiaoxiao 
	* @param @param purchaseRequired
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/adddetail")
	public String addReq(PurchaseRequiredFormBean list,String planType,String planNo,String planName,String recorderMobile,HttpServletRequest request,String referenceNo) throws IOException{
		User user = (User) request.getSession().getAttribute("loginUser");
		List<PurchaseRequired> plist = list.getList();
		// List<String> parentId = new ArrayList<>();
		
		String id = UUID.randomUUID().toString().replaceAll("-", "");
        String pid = UUID.randomUUID().toString().replaceAll("-", "");
        String cid = UUID.randomUUID().toString().replaceAll("-", "");
        String ccid = UUID.randomUUID().toString().replaceAll("-", "");
        String cccid = UUID.randomUUID().toString().replaceAll("-", "");
        String ccccid = UUID.randomUUID().toString().replaceAll("-", "");
        
        String unqueId = UUID.randomUUID().toString().replaceAll("-", "");
        
        
		int count=1;
		if(list!=null){
			if(plist!=null&&plist.size()>0){
				for(int i=0;i<plist.size();i++){
				    
				    PurchaseRequired p = plist.get(i);
	                p.setPlanNo(planNo);
	              /*  if(p.getPlanName()!=null){
	                     
	                }else{*/
	                    p.setPlanName(planName);    
	               // }
	                p.setPlanType(planType);
	                p.setHistoryStatus("0");
	                p.setIsDelete(0);
	                p.setIsMaster(count);
	                p.setCreatedAt(new Date());
	                p.setUserId(user.getId());
	                p.setRecorderMobile(recorderMobile);
                    p.setProjectStatus(0);
                    p.setAdvancedStatus(0);
                    p.setIsDelete(0);
                    p.setStatus("1");
                    if(p.getPurchaseType()!=null){
                        DictionaryData data = dictionaryDataMapper.queryByName(p.getPurchaseType());
                        p.setPurchaseType(data.getId());
                    }
                    
                    
                    if(p.getSeq().matches("[\u4E00-\u9FA5]")&&!p.getSeq().contains("（")){
                        p.setSeq("一");
                        count=1;
                        p.setIsMaster(count);
                        p.setParentId("1");//注释
                        id = UUID.randomUUID().toString().replaceAll("-", "");//重新给顶级id赋值
                        p.setId(id);//注释
                        
                        unqueId= UUID.randomUUID().toString().replaceAll("-", "");
                        p.setUniqueId(unqueId);
                        count++;
                        continue;
                    }
                //判断是否是二级节点(一)
                   if(isContainChinese(p.getSeq())){
                        p.setParentId(id);
                        pid = UUID.randomUUID().toString().replaceAll("-", "");//重新给顶级pid赋值
                        p.setId(pid);
                        p.setUniqueId(unqueId);
                        count++;
                        continue;  
                   }
                   
                    
                   //判断是否是三级节点1,2,3
                   else  if(isInteger(p.getSeq())){
               
                     p.setParentId(pid);
                     cid = UUID.randomUUID().toString().replaceAll("-", "");//重新给顶级cid赋值
                     p.setId(cid);
                     p.setUniqueId(unqueId);
                     count++;
                     continue; 
                   }
                   
                   //判断是否四级节点(1),(2)
                   else if(isContainIntger(p.getSeq())){
                   
                       p.setParentId(cid);
                       ccid = UUID.randomUUID().toString().replaceAll("-", "");//重新给顶级cid赋值
                       p.setId(ccid);
                       p.setUniqueId(unqueId);
                        count++;
                       continue;
                   }
                   //五级节点
                   else if(isEng(p.getSeq())){
                       p.setId(cccid);
                   
                       cccid = UUID.randomUUID().toString().replaceAll("-", "");//重新给顶级cid赋值
                       p.setParentId(ccid);
                       p.setUniqueId(unqueId);
                        count++;
                       continue;
                   }else{
                       p.setId(ccccid);
                       ccccid = UUID.randomUUID().toString().replaceAll("-", "");//重新给顶级cid赋值
                       p.setParentId(cccid);
                       p.setUniqueId(unqueId);
                       count++;
                   }
					
				}
			}
	}

		purchaseRequiredService.batchAdd(plist);
		return "redirect:list.html";
	}
	
	
	/**
	 * 
	* @Title: excel
	* @Description: 根据计划编号导出excel表格 
	* author: Li Xiaoxiao 
	* @param @param planNo
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/exports")
	@ResponseBody
	public String excel(HttpServletResponse resp,String planNo){
		
		String headers[]={"序号","需求部门","物资类别及品种名称","规格型号","质量技术标准（技术参数）", "计量单位","采购数量","单位（元）","预算金额（万元）","交货期限","采购方式建议","供应商名称","是否申请办理免税","物资用途（进口）","使用单位（进口）","备注"};
		String attrs[]={"seq","department","goodsName","stand","qualitStand","item","purchaseCount","price","budget","deliverDate","purchaseType","supplier","isFreeTax","goodsUse","useUnit","memo"};
	
		String filedisplay = "明细.xls";
		
		try {
			resp.addHeader("Content-Disposition", "attachment;filename="  + new String(filedisplay.getBytes("gb2312"), "iso8859-1"));
		} catch (UnsupportedEncodingException e1) {
		 
		}
		PurchaseRequired p=new PurchaseRequired();
		p.setUniqueId(planNo.trim());
		List<PurchaseRequired> list = purchaseRequiredService.queryUnique(p);
		
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		Excel<PurchaseRequired> sheet = new Excel<PurchaseRequired>();
		ServletOutputStream fileOut=null;
		try{
			filedisplay = URLEncoder.encode(filedisplay, "UTF-8");
			sheet.creatAuditSheet(workbook, "ErrInfoChannel sheet", list, headers, attrs);
			fileOut=resp.getOutputStream();
	        workbook.write(fileOut);
		}catch(Exception e){	
		}
        try {
			fileOut.close();
		} catch (IOException e) {e.printStackTrace();
		}
        
		
		return "下载成功";
	}
	/**
	 * 
	* @Title: delete
	* @Description: 逻辑删除数据
	* author: Li Xiaoxiao 
	* @param @param planNo
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(HttpServletRequest request){
		String planNo = request.getParameter("planNo");
		String[] no = planNo.split(",");
		for(int i=0;i<no.length;i++){
			purchaseRequiredService.delete(no[i]);
		}
	}
	
	/**
	 * 
	* @Title: downFile
	* @Description: 下载excel表格模板
	* author: Li Xiaoxiao 
	* @param @param path
	* @param @return     
	* @return String     
	* @throws
	 */
	  @RequestMapping("download")    
	    public ResponseEntity<byte[]> download(HttpServletRequest request,String filename) throws IOException {
//	    	filename = new String(filename.getBytes("iso8859-1"),"UTF-8");
	    	String path = PathUtil.getWebRoot() + "excel/需求计划模板.xlsx";;  
	        File file=new File(path);
	        
	        HttpHeaders headers = new HttpHeaders();    
	        String fileName=new String("模板.xls".getBytes("UTF-8"),"iso-8859-1");//为了解决中文名称乱码问题  
	        headers.setContentDispositionFormData("attachment", fileName);   
	        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);   
	        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),    
	                                          headers, HttpStatus.CREATED);    
	    }
	    /**
	     * 
	    * @Title: submit
	    * @Description: 提交
	    * author: Li Xiaoxiao 
	    * @param @return     
	    * @return String     
	    * @throws
	     */
	    @RequestMapping("/submit")
	    public String submit(String planNo){
	    	PurchaseRequired p=new PurchaseRequired();
	    	p.setUniqueId(planNo);
	    	p.setStatus("2");
	    	p.setDetailStatus(0);
	    	purchaseRequiredService.updateStatus(p);
//	    	purchaseRequiredService.update(planNo, "2");
	    	return "redirect:list.html";
	    }
	
	    @RequestMapping("/ztree")
	    public String ztree(String type,String planNo,String planName,Model model){
	    	model.addAttribute("type", type);
	    	model.addAttribute("planNo", planNo);
	    	model.addAttribute("planName", planName);
	    	return "bss/pms/purchaserequird/ztreeadd";
	    }
	    /**
	     * 
	    * @Title: id
	    * @Description: 生成id
	    * author: Li Xiaoxiao 
	    * @param @return     
	    * @return String     
	    * @throws
	     */
	    @RequestMapping("/getId")
	    @ResponseBody
	    public String id(){
	    	String id = UUID.randomUUID().toString().replaceAll("-", "");
	    	
	    	return id;
	    }
	    
	    /**
	     * 
	    * @Title: listName
	    * @author ZhaoBo
	    * @date 2016-12-14 下午1:55:01  
	    * @Description: 检索物种名称 
	    * @param @param request
	    * @param @return      
	    * @return String
	     */
	    @RequestMapping("/listName")
	    @ResponseBody
	    public List<Category> listName(HttpServletRequest request){
	    	List<Category> list = new ArrayList<>();
	    	String name = request.getParameter("name");
	    	if(name.trim().equals("")||name.trim()==null){
	    		list = null;
	    	}else{
	    		Map<String,Object> map = new HashMap<String,Object>();
		    	map.put("name", name);
		    	list = categoryService.listByKeyname(map);
	    	}
	    	return list;
	    }
	    
	    /**
	     * 
	    * @Title: viewIds
	    * @author ZhaoBo
	    * @date 2016-12-19 下午5:04:54  
	    * @Description: 关联计算 
	    * @param @param response
	    * @param @param id
	    * @param @throws IOException      
	    * @return void
	     */
	    @RequestMapping("/viewIds")
	    public void viewIds(HttpServletResponse response,String id) throws IOException {
	          Map<String, Object> map = new HashMap<String, Object>();
	          map.put("id", id);
	          List<PurchaseRequired> list = purchaseRequiredService.selectByParent(map);
	          String json = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss");
	          response.setContentType("text/html;charset=utf-8");
	          response.getWriter().write(json);
	          response.getWriter().flush();
	          response.getWriter().close();
	    }
	    
	    
		@RequestMapping(value="/queryNo")
		@ResponseBody
		public String line(String no){
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("planNo", no);
			List<PurchaseRequired> list = purchaseRequiredService.getByMap(map);
			if(list.size()>0){
				return "1";
			}
			return "0";
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
		
		public String randomPlano(){
			String str[]= {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
			Date date=new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			String year = sdf.format(date).split("-")[0];
			
			String res = "XQ-"+year+"-";
			for(int i=0;i<5;i++){
				  int id = (int) Math.ceil(Math.random()*35);
			         res += str[id];
			}
	
			return res;
		}
}
