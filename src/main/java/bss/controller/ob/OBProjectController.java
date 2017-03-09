package bss.controller.ob;

import java.io.File;
import java.io.IOException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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

import ses.model.bms.User;
import ses.service.oms.OrgnizationServiceI;
import ses.util.DictionaryDataUtil;
import ses.util.PathUtil;
import ses.util.PropUtil;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
import common.constant.Constant;
import bss.model.ob.OBProject;
import bss.model.pms.PurchaseRequired;
import bss.service.ob.OBProjectServer;

import bss.util.ExcelUtil;
/**
 * 竞价信息管理控制
 * 
 * @author YangHongliang
 * 
 */
@Controller
@Scope("prototype")
@RequestMapping("/ob_project")
public class OBProjectController {
	@Autowired
	private OBProjectServer OBProjectServer;

	@Autowired
	private OrgnizationServiceI orgnizationService;

	/***
	 * 获取竞价信息跳转 list页
	 * 
	 * @author YangHongLiang
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Model model, HttpServletRequest request, Integer page) {
		OBProject op = new OBProject();
		op.setName("");
		op.setStartTime(new Date());
		List<OBProject> list = OBProjectServer.list(op);
		if (page == null) {
			page = 1;
		}
		PageHelper.startPage(page,
				Integer.parseInt(PropUtil.getProperty("pageSizeArticle")));
		model.addAttribute("listInfo", new PageInfo<OBProject>(list));

		return "bss/ob/biddingInformation/list";
	}

	/**
	 * 发布竞价信息跳转 add页
	 * 
	 * @author YangHongLiang
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("/add")
	public String addBidding(@CurrentUser User user, Model model,
			HttpServletRequest request) {

		model.addAttribute("userId", user.getId());
		model.addAttribute("sysKey", Constant.PROJECT_SYS_KEY);
		model.addAttribute("typeId", DictionaryDataUtil.getId("BID_FILE_AUDIT"));
		return "bss/ob/biddingInformation/publish";
	}

    /**
     * 获取可用的采购机构 信息 并返回页面
     * @author YangHongLiang
     * @throws IOException 
     */
    @RequestMapping("mechanism")
    public void getMechanism(@CurrentUser User user,Model model,HttpServletRequest request,HttpServletResponse response) throws IOException{
    	try {
    	    String json= orgnizationService.getMechanism();
    	    response.getWriter().print(json.toString());
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			response.getWriter().close();
		}
	}

	/** ------------竞价看板------------- **/

	/**
	 * 
	 * @Title: biddingInfoList
	 * @Description: 竞价信息列表显示
	 * @author Easong
	 * @param @param model
	 * @param @param request
	 * @param @param page
	 * @param @return
	 * @param @throws ParseException 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping("/biddingInfoList")
	public String biddingInfoList(Model model, HttpServletRequest request,
			Integer page) throws ParseException {
		if (page == null) {
			page = 1;
		}

		// 竞价标题
		String name = request.getParameter("name");
		// 竞价开始时间
		String startTimeStr = request.getParameter("startTime");
		// 竞价结束时间
		String endTimeStr = request.getParameter("endTime");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date startTime = null;
		if (StringUtils.isNotEmpty(startTimeStr)) {
			startTime = dateFormat.parse(startTimeStr);
		}
		Date endTime = null;
		if (StringUtils.isNotEmpty(endTimeStr)) {
			endTime = dateFormat.parse(endTimeStr);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("page", page);
		List<OBProject> list = OBProjectServer.selectAllOBproject(map);
		// 封装分页信息
		PageInfo<OBProject> info = new PageInfo<OBProject>(list);
		// 将查询信息封装到model域中
		model.addAttribute("info", info);
		model.addAttribute("name", name);
		model.addAttribute("startTime", startTimeStr);
		model.addAttribute("endTime", endTimeStr);
		return "bss/ob/biddingSpectacular/list";

	}

	/**
	 * 
	* @Title: findBiddingResult 
	* @Description: 竞价结果查询
	* @author Easong
	* @param @param model
	* @param @param request
	* @param @param page
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping("/findBiddingResult")
	public String findBiddingResult(Model model, HttpServletRequest request,
			Integer page) {
		// 获取竞价标题的id
		String id = request.getParameter("id");
		
		return "bss/ob/biddingSpectacular/result";
	}

    
    /**
     * 获取可用的产品相关信息 并返回页面
     * @author YangHongLiang
     * @throws IOException 
     */
    @RequestMapping("product")
    public void getProduct(@CurrentUser User user,Model model,HttpServletRequest request,HttpServletResponse response) throws IOException{
    	try {
    	    String json= OBProjectServer.getProduct();
    	    response.getWriter().print(json.toString());
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			response.getWriter().close();
	      }
    }
    /**
	 * 
	* @Title: downFile
	* @Description: 下载excel表格模板
	* author: YangHongLiang 
	* @param @param path
	* @param @return     
	* @return String     
	* @throws
	 */
	@RequestMapping("download")    
	public ResponseEntity<byte[]> download(HttpServletRequest request,String filename) throws IOException {
	    	String path = PathUtil.getWebRoot() + "excel/定型产品.xls";;  
	        File file=new File(path);
	        HttpHeaders headers = new HttpHeaders();    
	        String fileName=new String("定型产品模板.xls".getBytes("UTF-8"),"iso-8859-1");//为了解决中文名称乱码问题  
	        headers.setContentDispositionFormData("attachment", fileName);   
	        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);   
	        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),    
	                                          headers, HttpStatus.OK);    
	}
	/**
	* @Description: 竞价管理暂存
	* author: YangHongLiang
	* @param 接收页面返回数据
	* @return     
	* @return String     
    * @throws IOException 
	* @throws Exception
	*/
	@RequestMapping("addProject")
	public String addProject(@CurrentUser User user,OBProject obProject, HttpServletRequest request){
		/*System.out.println(name+" name");
		System.out.println(delivery_time+" delivery_time");
		System.out.println(address+"  address");
		System.out.println(supplier+"  supplier");
		System.out.println(fees+" fees");
		System.out.println(unit+"  unit");
		System.out.println(contact+"  contact");
		System.out.println(tel+" tel");
		System.out.println(principal+" principal");
		System.out.println(contact_tel+"  contact_tel");
		System.out.println(contact_name+" contact_name");
		System.out.println(start+"start" );
		System.out.println(end+"  end");
		System.out.println(context+" context");
		System.out.println(product_id+"  product_id");
		System.out.println(product_name+" product_name");
		System.out.println(product_money+" product_money");
		System.out.println(product_count+"  product_count");
		System.out.println(product_remark+"  product_remark");
		System.out.println(b_downBsId+"  b_downBsId");
		String name,Date delivery_time,String address,Integer supplier 
			,Integer fees,String unit,String contact,String tel,String principal,String contact_tel,String contact_name,
			Date start,Date end,String context,String product_id,String product_name,String product_money,String product_count,
			String product_remark,String b_downBsId
		*/
		return "";
		
	}
	/**
	* @Title: uploadFile
	* @Description: 导入excel表格数据
	* author: YangHongLiang
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
        	return "1";
        }  
        
		List<PurchaseRequired> list=new ArrayList<PurchaseRequired>();
		    Map<String,Object>  maps= (Map<String, Object>) ExcelUtil.readOBExcel(file);
		     list = (List<PurchaseRequired>) maps.get("list");
		     
		     String errMsg=(String) maps.get("errMsg");
		
		     if(errMsg!=null){
		          String jsonString = JSON.toJSONString(errMsg);
			   return jsonString;
			}
		
		String jsonString = JSON.toJSONString(list);
		return jsonString;
	}
}
