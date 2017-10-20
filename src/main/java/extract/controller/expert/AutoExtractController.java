package extract.controller.expert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import extract.service.expert.AutoExtractService;

@Controller
@Scope("prototype")
@RequestMapping("/expertExtracts")
public class AutoExtractController {

    /** 自动抽取 **/
    @Autowired
    private AutoExtractService autoExtractService;
    /**
     * 
     * Description: 专家结果上传
     * 
     * @author zhang shubin
     * @data 2017年10月17日
     * @param 
     * @return
     */
    @RequestMapping("/extractResult")
    @ResponseBody
    public String extractResult(String result) throws Exception{
        String v = autoExtractService.expertResultUpload(result.indexOf("＂") != -1 ? result.replace("＂", "\"") : result);
        return JSON.toJSONString(v);
    }
}
