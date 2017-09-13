package extract.controller.expert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import extract.model.expert.ExpertExtractResult;
import extract.service.expert.ExpertExtractResultService;

/**
 * 
 * Description: 专家抽取结果
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
@Controller
@Scope("prototype")
@RequestMapping("/extractExpertResult")
public class ExtractExpertResultController {

    /** 专家抽取结果 **/
    @Autowired
    private ExpertExtractResultService expertExtractResultService;
    
    /**
     * 
     * Description: 保存抽取结果信息
     * 
     * @author zhang shubin
     * @data 2017年9月12日
     * @param 
     * @return
     */
    @RequestMapping("/saveResult")
    @ResponseBody
    public void saveResult(ExpertExtractResult expertExtractResult){
        expertExtractResultService.save(expertExtractResult);
    }
}
