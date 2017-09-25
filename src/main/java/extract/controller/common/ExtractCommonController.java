package extract.controller.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import extract.service.common.ExtractService;

import ses.model.bms.Area;

@Controller
@RequestMapping("/extract")
public class ExtractCommonController {

	@Autowired
	private ExtractService extractService;
	  /**
     * @Description: 获取市
     *
     * @author Wang Wenshuai
     * @date 2016年9月18日 下午4:16:35
     * @param @return
     * @return String
     */
    @ResponseBody
    @RequestMapping("/city")
    public List<Area> city(Model model, String area){
        return  extractService.getTree();
    }
}
