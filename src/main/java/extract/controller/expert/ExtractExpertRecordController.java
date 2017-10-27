package extract.controller.expert;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.controller.sys.sms.BaseSupplierController;
import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.util.AuthorityUtil;
import ses.util.DictionaryDataUtil;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
import extract.model.expert.ExpertExtractProject;
import extract.service.expert.ExpertExtractProjectService;

@Controller
@RequestMapping("/extractExpertRecord")
public class ExtractExpertRecordController extends BaseSupplierController{

    /** 专家抽取项目信息 **/
    @Autowired
    private ExpertExtractProjectService expertExtractProjectService;

    /**
     * 
     * Description: 专家抽取记录查询
     * 
     * @author zhang shubin
     * @data 2017年9月13日
     * @param
     * @return
     * @throws IOException 
     */
    @RequestMapping("/getRecordList")
    public String getRecordList(@CurrentUser User user, Model model,@RequestParam(defaultValue = "1") Integer page, String startTime,String endTime, ExpertExtractProject expertExtractProject,HttpServletRequest request) throws IOException {
    	//获取当前登录用户数据查看权限
		Integer dataAccess = user.getDataAccess();
		if (dataAccess == null) {
			return AuthorityUtil.valiDataAccess(dataAccess, request, response);
		}
        Map<String, Object> map = new HashMap<>();
        if (dataAccess == 1) {
			//查看所有数据
		} else if (dataAccess == 2) {
			//查看本单位数据
			String orgId = ""; 
			if (user.getOrg() != null) {
				orgId = user.getOrg().getId();
			} else {
				orgId = user.getOrgId();
			}
			map.put("procurementDepId", orgId);
		} else if (dataAccess == 3) {
			//查看本人数据
			map.put("userId", user.getId());
		}
        map.put("page", page);
        map.put("startTime", null == startTime ? "" : startTime.trim());
        map.put("endTime", null == endTime ? "" : endTime.trim());
        List<ExpertExtractProject> list = expertExtractProjectService.findAll(map, expertExtractProject);
        PageInfo<ExpertExtractProject> info = new PageInfo<>(list);
        model.addAttribute("info", info);
        // 采购方式
        List<DictionaryData> purchaseWayList = DictionaryDataUtil.find(5);
        if (purchaseWayList != null && purchaseWayList.size() > 0) {
            for (DictionaryData dictionaryData : purchaseWayList) {
                if ("XJCG".equals(dictionaryData.getCode())) {
                    dictionaryData.setName("询价");
                }
            }
        }
        model.addAttribute("purchaseWayList", purchaseWayList);
        model.addAttribute("project", expertExtractProject);
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        return "ses/ems/exam/expert/extract/project_list";
    }

    /**
     * 下载记录表
     * 
     * @param id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/printRecord")
    public ResponseEntity<byte[]> printRecord(String id,HttpServletRequest request, HttpServletResponse response) {
        ResponseEntity<byte[]> printRecord = null;
        try {
            printRecord = expertExtractProjectService.printRecord(id, request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return printRecord;
    }

    /**
     * 
     * Description: 判断评审时间是否在半个小时后
     * 
     * @author zhang shubin
     * @data 2017年9月20日
     * @param 
     * @return
     */
    @RequestMapping("/selReviewTime")
    @ResponseBody
    public String selReviewTime(String id) {
        ExpertExtractProject expertExtractProject = expertExtractProjectService.selectByPrimaryKey(id);
        Date reviewTime = expertExtractProject.getReviewTime();
        //判断评审时间是否满足下载条件   评审时间开始半个小时之后
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        Calendar cal2=Calendar.getInstance(); 
        cal2.setTime(reviewTime);
        cal2.add(Calendar.MINUTE, 15);
        int v = calendar.compareTo(cal2);
        return JSON.toJSONString(v);
    }
}
