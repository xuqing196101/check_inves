package app.controller;

import iss.model.ps.Article;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import app.dao.app.AppArticleMapper;
import app.dao.app.AppSupplierBlackListMapper;
import app.dao.app.AppSupplierMapper;
import app.dao.app.IndexAppMapper;
import app.model.AppData;
import app.model.AppImg;
import app.model.AppSupplier;
import app.service.IndexAppService;

import com.alibaba.fastjson.JSON;

/**
 * 
 * Description： App接口Controller
 * 
 * @author zhang shubin
 * @version
 * @since JDK1.7
 * @date 2017年6月2日
 *
 */
@Controller
@RequestMapping("/api/v1")
public class IndexAppController {

    //首页通知
    @Autowired
    private IndexAppMapper indexAppMapper;
    
    //公告
    @Autowired
    private AppArticleMapper appArticleMapper;
    
    //供应商
    @Autowired
    private AppSupplierMapper appSupplierMapper;
    
    //供应商黑名单
    @Autowired
    private AppSupplierBlackListMapper appSupplierBlackListMapper;
    
    //App接口Service注入
    @Autowired
    private IndexAppService indexAppService;
    
    //工作动态typeId标识
    private static final String INDEX_DYNAMIC = "110"; 
    
    //重要通知typeId标识
    private static final String NOTICE = "109"; 
    
    //投诉处理公告typeId标识
    private static final String COMPLAINT_HANDLING = "112"; 
    
    //处罚公告typeId标识
    private static final String PUNISHMENT = "113"; 
    
    //需求公告typeId标识
    private static final String DEMAND = "103"; 
    
    //成交公告typeId标识
    private static final String CJ = "104"; 
    
    //废标公告typeId标识
    private static final String SCRAP = "105"; 
    
    //军队处罚公告typeId标识
    private static final String MILITARY_PUNISHMENT = "116";
    
    //军队处罚公告typeId标识
    private static final String PLACE_PUNISHMENT = "117";
    
    //专家处罚公告typeId标识
    private static final String EXPERT_PUNISHMENT = "115";
    
    /**
     * 
     * Description: App首页
     * 
     * @author zhang shubin
     * @data 2017年6月2日
     * @param 
     * @return 
     * @exception
     */
    @RequestMapping(value="/indexNews",produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String indexNews(){
        List<String> imgList = new ArrayList<>();
        imgList.add("/zhbj/public/portal/images/1.png");
        imgList.add("/zhbj/public/portal/images/2.png");
        imgList.add("/zhbj/public/portal/images/3.png");
        List<Article> indexMsgList = new ArrayList<>();
        //动态
        Article dynamic= indexAppMapper.selectAppNewsByArticleTypeId(INDEX_DYNAMIC);
        if(dynamic != null){
            dynamic.setCreate_at(dataToString(dynamic.getPublishedAt()));
            indexMsgList.add(dynamic);
        }
        //通知
        Article notice = indexAppMapper.selectAppNewsByArticleTypeId(NOTICE);
        if(notice != null){
            notice.setCreate_at(dataToString(notice.getPublishedAt()));
            indexMsgList.add(notice);
        }
        //法规
        Map<String, Object> map = new HashMap<>();
        String[] idArray = new String[2];
        idArray[0] = "107";
        idArray[1] = "108";
        map.put("idArray",idArray);
        Article regulations= appArticleMapper.selectsumApp(map);
        if(regulations != null){
            regulations.setCreate_at(dataToString(regulations.getPublishedAt()));
            indexMsgList.add(regulations);
        }
        //投诉
        Article complaintHandling= indexAppMapper.selectAppNewsByArticleTypeId(COMPLAINT_HANDLING);
        if(complaintHandling != null){
            complaintHandling.setCreate_at(dataToString(complaintHandling.getPublishedAt()));
            indexMsgList.add(complaintHandling);
        }
        //处罚
        Article punishment = indexAppMapper.selectAppChuFaNewsByTypeId(PUNISHMENT);
        if(punishment != null){
            punishment.setCreate_at(dataToString(punishment.getPublishedAt()));
            indexMsgList.add(punishment);
        }
        AppImg appImg = new AppImg();
        if(imgList.size() > 0 && indexMsgList.size() > 0){
            appImg.setStatus(true);
            AppData appData = new AppData();
            appData.setImgUrl(imgList);
            appData.setIndexMsgList(indexMsgList);
            appImg.setData(appData);
        }else{
            appImg.setStatus(false);
            appImg.setMsg("数据获取失败");
        }
        return JSON.toJSONString(appImg);
    }
    
    /**
     * 
     * Description: App公告
     * 
     * @author zhang shubin
     * @data 2017年6月2日
     * @param 
     * @return 
     * @exception
     */
    @RequestMapping(value="/announcement",produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String announcement(Integer id){
        AppData appData = new AppData();
        AppImg appImg = new AppImg();
        List<Article> indexMsgList = new ArrayList<>();
        switch(id){
            case 1 ://采购公告
                //物资
                indexMsgList.add(getAppNotice(new String[]{"3","24"},11,0));
                //工程
                indexMsgList.add(getAppNotice(new String[]{"8","29"},12,0));
                //服务
                indexMsgList.add(getAppNotice(new String[]{"13","34"},13,0));
                //进口
                indexMsgList.add(getAppNotice(new String[]{"18","39"},14,0));
                break;
            case 2 ://中标公示
                indexMsgList.add(getAppNotice(new String[]{"46","67"},11,0));
                indexMsgList.add(getAppNotice(new String[]{"51","72"},12,0));
                indexMsgList.add(getAppNotice(new String[]{"56","77"},13,0));
                indexMsgList.add(getAppNotice(new String[]{"61","82"},14,0));
                break;
            case 3 ://单一来源
                indexMsgList.add(getAppNotice(new String[]{"89","94"},11,1));
                indexMsgList.add(getAppNotice(new String[]{"90","95"},12,1));
                indexMsgList.add(getAppNotice(new String[]{"91","96"},13,1));
                indexMsgList.add(getAppNotice(new String[]{"92","97"},14,1));
                break;
            case 4 ://竞价公告
                //需求
                Article demand= indexAppMapper.selectAppNewsByArticleTypeId(DEMAND);
                if(demand != null){
                    demand.setCreate_at(dataToString(demand.getPublishedAt()));
                    demand.setType_id(15);
                    indexMsgList.add(demand);
                };
                //成交
                Article cj= indexAppMapper.selectAppNewsByArticleTypeId(CJ);
                if(cj != null){
                    cj.setCreate_at(dataToString(cj.getPublishedAt()));
                    cj.setType_id(16);
                    indexMsgList.add(cj);
                };
                //废标
                Article scrap= indexAppMapper.selectAppNewsByArticleTypeId(SCRAP);
                if(scrap != null ){
                    scrap.setCreate_at(dataToString(scrap.getPublishedAt()));
                    scrap.setType_id(17);
                    indexMsgList.add(scrap);
                };
                break;
        }
        if(indexMsgList != null && !indexMsgList.isEmpty()){
            appData.setIndexMsgList(indexMsgList);
            appImg.setStatus(true);
            appData.setIndexMsgList(indexMsgList);
            appImg.setData(appData);
        }else{
            appImg.setStatus(false);
            appImg.setMsg("数据获取失败");
        }
        return JSON.toJSONString(appImg);
    }
    
    /**
     * 
     * Description: App供应商
     * 
     * @author zhang shubin
     * @data 2017年6月5日
     * @param 
     * @return
     */
    @RequestMapping(value="/appSupplier",produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String appSupplier(Integer id,@RequestParam(defaultValue="1")Integer page){
        AppData appData = new AppData();
        AppImg appImg = new AppImg();
        switch(id){
            case 1 ://供应商名录  //1465798
                Map<String, Object> map = new HashMap<>();
                String[] statusArray = new String[] {"1","4","6","5","7","9","8"};
                map.put("statusArray",statusArray);
                map.put("page", page);
                List<AppSupplier> supplierList = indexAppService.selectAppSupplierList(map);
                if(supplierList != null && !supplierList.isEmpty()){
                    appData.setSupplierList(supplierList);
                    appImg.setData(appData);
                    appImg.setStatus(true);
                }else {
                    appImg.setStatus(false);
                    appImg.setMsg("暂无数据");
                }
                break;
            case 2 ://诚信记录
                appImg.setStatus(false);
                appImg.setMsg("暂无数据");
                break;
            case 3 ://军队处罚公告
                List<Article> militaryPunishmentList = indexAppService.selectAppArticleListByTypeId(MILITARY_PUNISHMENT,page);
                if(militaryPunishmentList != null && !militaryPunishmentList.isEmpty()){
                    for (Article article : militaryPunishmentList) {
                        article.setCreate_at(dataToString(article.getPublishedAt()));
                    }
                    appData.setIndexMsgList(militaryPunishmentList);
                    appImg.setData(appData);
                    appImg.setStatus(true);
                }else {
                    appImg.setStatus(false);
                    appImg.setMsg("暂无数据");
                }
                break;
            case 4 ://地方处罚公告
                List<Article> placePunishmentList = indexAppService.selectAppArticleListByTypeId(PLACE_PUNISHMENT,page);
                if(placePunishmentList != null && !placePunishmentList.isEmpty()){
                    for (Article article : placePunishmentList) {
                        article.setCreate_at(dataToString(article.getPublishedAt()));
                    }
                    appData.setIndexMsgList(placePunishmentList);
                    appImg.setData(appData);
                    appImg.setStatus(true);
                }else {
                    appImg.setStatus(false);
                    appImg.setMsg("暂无数据");
                }
                break;
            case 5 ://供应商黑名单
                appData.setBlackList(indexAppService.findAppSupplierBlacklist(page));
                if(appData.getBlackList() != null && !appData.getBlackList().isEmpty()){
                    appImg.setData(appData);
                    appImg.setStatus(true);
                }else {
                    appImg.setStatus(false);
                    appImg.setMsg("暂无数据");
                }
                break;
        }
        return JSON.toJSONString(appImg);
    }
    
    /**
     * 
     * Description: 专家
     * 
     * @author zhang shubin
     * @data 2017年6月6日
     * @param 
     * @return
     */
    @RequestMapping(value="/appExpert",produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String appExpert(Integer id,@RequestParam(defaultValue="1")Integer page){
        AppData appData = new AppData();
        AppImg appImg = new AppImg();
        switch(id){
            case 1 ://专家名录  //4 6 8 复审通过  7 复查通过
                Map<String, Object> map = new HashMap<>();
                String[] statusArray = new String[] {"4","6","8","7"};
                map.put("statusArray",statusArray);
                map.put("page", page);
                List<AppSupplier> expertList = indexAppService.selectAppExpertList(map);
                if(expertList != null && !expertList.isEmpty()){
                    appData.setSupplierList(expertList);
                    appImg.setData(appData);
                    appImg.setStatus(true);
                }else {
                    appImg.setStatus(false);
                    appImg.setMsg("暂无数据");
                }
                break;
            case 2 ://诚信记录
                appImg.setStatus(false);
                appImg.setMsg("暂无数据");
                break;
            case 3 ://处罚公告
                List<Article> expertPunishmentList = indexAppService.selectAppArticleListByTypeId(EXPERT_PUNISHMENT,page);
                if(expertPunishmentList != null && !expertPunishmentList.isEmpty()){
                    for (Article article : expertPunishmentList) {
                        article.setCreate_at(dataToString(article.getPublishedAt()));
                    }
                    appData.setIndexMsgList(expertPunishmentList);
                    appImg.setData(appData);
                    appImg.setStatus(true);
                }else {
                    appImg.setStatus(false);
                    appImg.setMsg("暂无数据");
                }
                break;
            case 4 ://专家黑名单
                appData.setBlackList(indexAppService.findAppExpertBlacklist(page));
                if(appData.getBlackList() != null && !appData.getBlackList().isEmpty()){
                    appImg.setData(appData);
                    appImg.setStatus(true);
                }else {
                    appImg.setStatus(false);
                    appImg.setMsg("暂无数据");
                }
                break;
        }
        return JSON.toJSONString(appImg);
    }
    
    /**
     * 
     * Description: 首页公告列表查询
     * 
     * @author zhang shubin
     * @data 2017年6月6日
     * @param 
     * @return
     */
    @RequestMapping(value="/indexList",produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String indexList(String id,@RequestParam(defaultValue="1")Integer page){
        AppData appData = new AppData();
        AppImg appImg = new AppImg();
        Map<String, Object> map = new HashMap<>();
        if(id != null){
            Integer typeId = Integer.parseInt(id);
            switch (typeId) {
                case 110 ://工作动态
                case 109://重要通知
                case 112://投诉处理
                    List<Article> list = indexAppService.selectAppArticleListByTypeId(id,page);
                    if(list != null && !list.isEmpty()){
                        for (Article article : list) {
                            article.setCreate_at(dataToString(article.getPublishedAt()));
                        }
                        appData.setIndexMsgList(list);
                        appImg.setData(appData);
                        appImg.setStatus(true);
                    }else {
                        appImg.setStatus(false);
                        appImg.setMsg("暂无数据");
                    }
                    break;
                case 108://法规
                case 107:
                    String[] idArray = new String[2];
                    idArray[0] = "107";
                    idArray[1] = "108";
                    map.put("idArray",idArray);
                    map.put("page", page);
                    List<Article> regulationsList = indexAppService.selectAppRegulations(map);
                    if(regulationsList != null && !regulationsList.isEmpty()){
                        for (Article article : regulationsList) {
                            article.setCreate_at(dataToString(article.getPublishedAt()));
                        }
                        appData.setIndexMsgList(regulationsList);
                        appImg.setData(appData);
                        appImg.setStatus(true);
                    }else {
                        appImg.setStatus(false);
                        appImg.setMsg("暂无数据");
                    }
                    break;
                case 113://处罚
                    map.put("page", page);
                    map.put("articleTypeId", PUNISHMENT);
                    List<Article> punishmentList = indexAppService.selectAppPunishment(map);
                    if(punishmentList != null && !punishmentList.isEmpty()){
                        for (Article article : punishmentList) {
                            article.setCreate_at(dataToString(article.getPublishedAt()));
                        }
                        appData.setIndexMsgList(punishmentList);
                        appImg.setData(appData);
                        appImg.setStatus(true);
                    }else {
                        appImg.setStatus(false);
                        appImg.setMsg("暂无数据");
                    }
                    break;
                default :
                    break;
            }
        }else{
            appImg.setStatus(false);
            appImg.setMsg("请传正确参数");
        }
        return JSON.toJSONString(appImg);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * 
     * Description: 搜索
     * 
     * @author zhang shubin
     * @data 2017年6月6日
     * @param 
     * @return
     */
    @RequestMapping(value="/search",produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String search(String title,@RequestParam(defaultValue="1")Integer page){
    	AppData appData = new AppData();
        AppImg appImg = new AppImg();
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("title", title);
        List<Article> list = indexAppService.search(map);
        if(list != null && !list.isEmpty()){
            for (Article article : list) {
                article.setCreate_at(dataToString(article.getPublishedAt()));
            }
            appData.setIndexMsgList(list);
            appImg.setData(appData);
            appImg.setStatus(true);
        }else {
            appImg.setStatus(false);
            appImg.setMsg("暂无数据");
        }
        return JSON.toJSONString(appImg);
    }
    
    
    
    /**
     * 
     * Description: app 采购公告  中标公告查询
     * 
     * @author zhang shubin
     * @data 2017年6月2日
     * @param 
     * @return 
     * @exception
     */
    public Article getAppNotice(String ids[],Integer type_id,Integer i){
        Map<String, Object> map = new HashMap<>();
        map.put("idArray", ids);
        Article article= (i == 0 ? appArticleMapper.selectOneAppNoticeByParId(map) : appArticleMapper.selectsumApp(map));
        article.setCreate_at(dataToString(article.getPublishedAt()));
        article.setType_id(type_id);
        return article;
    }
    
    /**
     * 
     * Description: Date转换String
     * 
     * @author zhang shubin
     * @data 2017年6月2日
     * @param 
     * @return 
     * @exception
     */
    public String dataToString(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        String time = simpleDateFormat.format(date);
        return time;
    }
}
