package ses.controller.sys.bms;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.DictionaryData;
import ses.model.bms.Qualification;
import ses.model.bms.QualificationLevel;
import ses.model.bms.User;
import ses.service.bms.QualificationLevelService;
import ses.service.bms.QualificationService;
import ses.util.DictionaryDataUtil;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import common.annotation.CurrentUser;
import common.bean.ResponseBean;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>资质管理
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
@Controller
@RequestMapping("/qualification")
public class QualificationController {
    
    /** service **/
    @Autowired
    private QualificationService quaService;
    
    @Autowired
    private QualificationLevelService qualificationLevelService;
    
    /**
     * 
     *〈简述〉初始化页面
     *〈详细描述〉
     * @author myc
     * @param model {@link Model}
     * @param request {@link HttpServletRequest}
     * @return 
     */
    @RequestMapping("/init")
    public String init(Model model, HttpServletRequest request,@CurrentUser User user){
    	//权限验证 登陆状态 角色只能是资源服务中心
    	if(null!=user && "4".equals(user.getTypeName())){
	        String type = request.getParameter("type");
	        model.addAttribute("type", type);
	        /**
	         * 查询所有的资质等级
	         */
	        List<DictionaryData> list = DictionaryDataUtil.find(31);
	        model.addAttribute("kind", list);
    	}
        return   "/ses/bms/qualification/list";
    }
    
    /**
     * 
     *〈简述〉查询
     *〈详细描述〉
     * @author myc
     * @param page 当前页
     * @param name 查询的名称
     * @param type 类型
     * @return
     */
    @ResponseBody
    @RequestMapping("/list")
    public ResponseBean list(Integer page, String name, String  type,String ids){
        
        ResponseBean res = new ResponseBean();
        if (StringUtils.isNotBlank(type)){
            Integer typeInteger = Integer.parseInt(type);
            List<Qualification> list = quaService.findList(page, null, name, typeInteger);
            PageInfo<Qualification> pageInfo = new PageInfo<Qualification> (list);
            res.setSuccess(true);
            res.setObj(pageInfo);
            res.setIds(ids);
        }
       return res;
    }
    
    /**
     * 
     *〈简述〉保存
     *〈详细描述〉
     * @author myc
     * @param type 类型
     * @param name 名称
     * @return  ResponseBean
     */
    @ResponseBody
    @RequestMapping(value = "/save", produces="application/json;chaset=UTF-8")
    public ResponseBean save(String type, String name, String operaType, String id){
        
        ResponseBean bean = new ResponseBean();
        
        if (!StringUtils.isNotBlank(name)){
            bean.setSuccess(false);
            bean.setObj("名称不能为空");
            return bean;
        }
        if (!StringUtils.isNotBlank(type)){
            bean.setSuccess(false);
            bean.setObj("类型值出现错误");
            return bean;
        }
        
        Qualification qualification = quaService.save(type, name, operaType, id);
        bean.setSuccess(true);
        bean.setObj(qualification);
        return bean;
    }
    
    /**
     * 
     *〈简述〉根据Id查询对象
     *〈详细描述〉
     * @author myc
     * @param id 主键
     * @return 
     */
    @ResponseBody
    @RequestMapping(value ="/getQualification", produces="application/json")
    public ResponseBean getQualification(String id){
        
        ResponseBean res = new ResponseBean();
        Qualification qualification = quaService.getQualification(id);
        if (qualification != null){
            res.setSuccess(true);
            res.setObj(qualification);
        }
        return res;
    }
    
    /**
     * 
     *〈简述〉根据id删除
     *〈详细描述〉
     * @author myc
     * @param id 主键id集合
     * @return 成功返回 ok
     */
    @ResponseBody
    @RequestMapping(value ="/del", produces="html/text")
    public String del(String id){
        
        return  quaService.del(id);
    }
    
    /**
     * 
     *〈简述〉
     *〈详细描述〉
     * @author myc
     * @param model {@link Model} 
     * @param type {@link type} 类型
     * @return 
     * @throws UnsupportedEncodingException 
     */
    @RequestMapping("/initLayer")
    public String initOpenLayer(Model model, String type, String ids,String names) throws UnsupportedEncodingException{
    	String profileNames = "";
    	if(ids!=null&&!"".equals(ids)){
    		String[] id=ids.split(",");
    		for(int i=0;i<id.length;i++){
    			Qualification  qua = quaService.getQualification(id[i]);
    			if(qua!=null){
    				profileNames+=qua.getName()+",";
    			}
    		}
    	}
    	if(profileNames.length()>0){
    		profileNames=profileNames.substring(0,profileNames.length()-1);
    	}
    	model.addAttribute("names", profileNames);
        model.addAttribute("type", type);
        model.addAttribute("ids", ids);
        
        return "/ses/bms/qualification/quaLayer";
    }
    
    @RequestMapping("/update")
    @ResponseBody
    public String update(String qid,String did){
    	qualificationLevelService.deleteByQuaId(qid);
    	QualificationLevel qua=new QualificationLevel();
    	String[] dids = did.split(",");
    	for(String str:dids){
    		String id=UUID.randomUUID().toString().replaceAll("-", "");
        	qua.setId(id);
        	qua.setQualificationId(qid);
        	qua.setGrade(str);
        	qualificationLevelService.add(qua);
    	}
    	
    	return "redirect:init.html";
    }
    /**
     * 回显对应的资质文件等级
     * @param 
     * @return
     */
    
    @RequestMapping("/getlevle")
    public String echo(String id,Model model){
    	String str="";
    	List<QualificationLevel> list = qualificationLevelService.queryByQId(id);
    	for(QualificationLevel q:list){
    		str+=q.getGrade()+",";
    	}
        List<DictionaryData> kind = DictionaryDataUtil.findDataByNotDefinedLevel(31);
        model.addAttribute("kind", kind);
        model.addAttribute("list", str);
        model.addAttribute("id", id);
    	return "/ses/bms/qualification/level";
    }
    
    @RequestMapping("/getLevelByQid")
    @ResponseBody
    public String getLevelByQid(String qid){
    	List<DictionaryData> list =  qualificationLevelService.getLevelByQid(qid);
		return JSON.toJSONString(list);
    }
    
}

