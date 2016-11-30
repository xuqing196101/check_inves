package ses.controller.sys.bms;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 版权：(C) 版权所有 
 * <简述>页面样式控制层
 * <详细描述>
 * @author   Song Biaowei
 * @version  
 * @since
 * @see
 */
@Controller
@RequestMapping(value = "pageStyle")
public class PageStyleController {
    
    /**
     *〈简述〉页面样式列表
     *〈详细描述〉
     * @author Song Biaowei
     * @return String
     */
    @RequestMapping(value = "list")
    public String list() {
        return "ses/bms/page_style/list";
    }
    
    /**
     *〈简述〉上下结构表单页面
     *〈详细描述〉
     * @author Song Biaowei
     * @return String
     */
    @RequestMapping(value = "details")
    public String details() {
        return "ses/bms/page_style/details";
    }
    
    /**
     *〈简述〉列表页面
     *〈详细描述〉
     * @author Song Biaowei
     * @return String
     */
    @RequestMapping(value = "order")
    public String order() {
        return "ses/bms/page_style/order";
    }
    
    /**
     *〈简述〉详情页面
     *〈详细描述〉
     * @author Song Biaowei
     * @return String
     */
    @RequestMapping(value = "show_order")
    public String showOrder() {
        return "ses/bms/page_style/show_order";
    }
    
    /**
     *〈简述〉左右结构表格页面
     *〈详细描述〉
     * @author Song Biaowei
     * @return String
     */
    @RequestMapping(value = "table_special")
    public String tableSpecial() {
        return "ses/bms/page_style/table_special";
    }
    
    /**
     *〈简述〉左右布局页面
     *〈详细描述〉
     * @author Song Biaowei
     * @return String
     */
    @RequestMapping(value = "manage")
    public String manage() {
        return "ses/bms/page_style/manage";
    }
    
    /**
     *〈简述〉实施页面
     *〈详细描述〉
     * @author Song Biaowei
     * @return String
     */
    @RequestMapping(value = "evaluation")
    public String evaluation() {
        return "ses/bms/page_style/evaluation";
    }
    
    /**
     *〈简述〉后台主页
     *〈详细描述〉
     * @author Song Biaowei
     * @return String
     */
    @RequestMapping(value = "backbottom")
    public String backbottom() {
        return "ses/bms/page_style/backbottom";
    }
    
    /**
     *〈简述〉投标左侧页面
     *〈详细描述〉
     * @author Song Biaowei
     * @return String
     */
    @RequestMapping(value = "left")
    public String left() {
        return "ses/bms/page_style/left";
    }

    /**
     *〈简述〉切换标签页面
     *〈详细描述〉
     * @author Song Biaowei
     * @return String
     */
    @RequestMapping(value = "tab")
    public String tab() {
        return "ses/bms/page_style/tab";
    }

    /**
     *〈简述〉弹出框页面
     *〈详细描述〉
     * @author Song Biaowei
     * @return String
     */
    @RequestMapping(value = "openLayer")
    public String openLayer() {
        return "ses/bms/page_style/openLayer";
    }
    
    /**
     *〈简述〉后台头部
     *〈详细描述〉
     * @author Song Biaowei
     * @return String
     */
    @RequestMapping(value = "backhead")
    public String backhead() {
        return "ses/bms/page_style/backhead";
    }
}
