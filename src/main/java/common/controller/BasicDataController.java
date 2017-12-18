package common.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.Area;
import ses.model.bms.ContinentNationRel;
import ses.model.bms.DictionaryData;
import ses.service.bms.AreaServiceI;
import ses.service.bms.ContinentNationRelService;
import ses.util.DictionaryDataUtil;

/**
 * @Description: 基础数据控制类
 * @author yggc
 * @date 2017-11-23 上午10:03:54
 */
@Controller
@RequestMapping("/basicData")
public class BasicDataController {
	
	@Autowired
	private AreaServiceI areaServiceI;
	@Autowired
	private ContinentNationRelService continentNationRelService;
	
    /**
     * ajax获取字典数据
     * @param kind
     * @return
     */
    @RequestMapping(value = "ajaxDicData", method = RequestMethod.POST)
    @ResponseBody
    public List<DictionaryData> ajaxDicData(Integer kind){
    	if(kind != null){
    		return DictionaryDataUtil.find(kind);
    	}
    	return null;
    }
    
    /**
     * 根据pid查询地区（如果pid为空，则查询一级地区）
     * @param pid
     * @return
     */
    @RequestMapping(value = "ajaxAreaData", method = RequestMethod.POST)
    @ResponseBody
    public List<Area> ajaxAreaData(String pid){
    	if(StringUtils.isBlank(pid)){
    		pid = "0";
    	}
    	return areaServiceI.findAreaByParentId(pid);
    }
    
    /**
     * 根据洲id获取对应国家
     * @param continentId
     * @return
     */
    @RequestMapping(value = "ajaxCrnData", method = RequestMethod.POST)
    @ResponseBody
    public List<ContinentNationRel> ajaxContinentNationRelData(String continentId){
    	if(StringUtils.isBlank(continentId)){
    		return null;
    	}
    	return continentNationRelService.findByContinentId(continentId);
    }
}
