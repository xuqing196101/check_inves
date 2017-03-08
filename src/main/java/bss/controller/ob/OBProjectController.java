package bss.controller.ob;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ses.model.bms.User;
import ses.model.oms.Orgnization;
import ses.service.oms.OrgnizationServiceI;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
import common.constant.Constant;

import bss.model.ob.OBProject;
import bss.model.ppms.Project;
import bss.service.ob.OBProjectServer;
/**
 * 竞价信息管理控制
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
	 * @author YangHongLiang
	 * @param model
	 * @param request
	 * @return
	 */
    @RequestMapping("/list")
	public String list(Model model, HttpServletRequest request,Integer page){
    	OBProject op =new OBProject();
    	op.setName("");
    	op.setStartTime(new Date());
    	List<OBProject> list=OBProjectServer.list(op);
    	if(page==null){
    		page=1;
    	}
    	 PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("pageSizeArticle")));
    	model.addAttribute("listInfo", new PageInfo<OBProject>(list));
    	
    	return "bss/ob/biddingInformation/list";
    }
    /**
      * 发布竞价信息跳转 add页
	 * @author YangHongLiang
	 * @param model
	 * @param request
	 * @return
	 */
    @RequestMapping("/add")
    public String addBidding(@CurrentUser User user,Model model, HttpServletRequest request){
    	
    	model.addAttribute("userId",user.getId());
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
    public void getMechanism(Model model,HttpServletRequest request,HttpServletResponse response) throws IOException{
    	try {
    	    String json= orgnizationService.getMechanism();
    	    response.getWriter().print(json.toString());
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			response.getWriter().close();
	      }
    }
}
