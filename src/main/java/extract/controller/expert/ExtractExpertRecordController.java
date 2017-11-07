package extract.controller.expert;

import java.util.ArrayList;
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

import ses.model.bms.DictionaryData;
import ses.model.bms.User;
import ses.util.DictionaryDataUtil;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;

import common.annotation.CurrentUser;
import extract.model.expert.ExpertExtractProject;
import extract.service.expert.ExpertExtractProjectService;

@Controller
@RequestMapping("/extractExpertRecord")
public class ExtractExpertRecordController {

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
     */
    @RequestMapping("/getRecordList")
    public String getRecordList(@CurrentUser User user, Model model,@RequestParam(defaultValue = "1") Integer page, String startTime,String endTime, ExpertExtractProject expertExtractProject) {
        // 声明标识是否是资源服务中心
        String authType = null;
        if (null != user && ("4".equals(user.getTypeName()) || "1".equals(user.getTypeName()))) {
            authType = user.getTypeName();
            Map<String, Object> map = new HashMap<>();
            if (authType.equals("1")) {
                map.put("procurementDepId", user.getOrg().getId());
            }
            map.put("page", page);
            map.put("startTime", null == startTime ? "" : startTime.trim());
            map.put("endTime", null == endTime ? "" : endTime.trim());
            List<ExpertExtractProject> list = expertExtractProjectService.findAll(map, expertExtractProject);
            PageInfo<ExpertExtractProject> info = new PageInfo<>(list);
            model.addAttribute("info", info);
            // 采购方式
            List<DictionaryData> purchaseWayList = new ArrayList<DictionaryData>();
            //公开招标
            purchaseWayList.add(DictionaryDataUtil.get("GKZB"));
            //邀请招标
            purchaseWayList.add(DictionaryDataUtil.get("YQZB"));
            //竞争性谈判
            purchaseWayList.add(DictionaryDataUtil.get("JZXTP"));
            //询价
            DictionaryData dictionaryData = DictionaryDataUtil.get("XJCG");
            dictionaryData.setName("询价");
            purchaseWayList.add(dictionaryData);
            //单一来源
            purchaseWayList.add(DictionaryDataUtil.get("DYLY"));
            model.addAttribute("purchaseWayList",purchaseWayList);
            model.addAttribute("project", expertExtractProject);
            model.addAttribute("startTime", startTime);
            model.addAttribute("endTime", endTime);
            model.addAttribute("authType", authType);
            return "ses/ems/exam/expert/extract/project_list";
        }
        return "redirect:/qualifyError.jsp";
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
