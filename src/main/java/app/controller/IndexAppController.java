package app.controller;

import gui.ava.html.image.generator.HtmlImageGenerator;
import iss.model.ps.Article;
import iss.model.ps.ArticleType;
import iss.service.ps.ArticleService;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ses.model.bms.DictionaryData;
import ses.service.bms.DictionaryDataServiceI;
import ses.util.PropUtil;
import app.dao.app.AppArticleMapper;
import app.dao.app.AppSupplierBlackListMapper;
import app.dao.app.AppSupplierMapper;
import app.dao.app.IndexAppMapper;
import app.model.AppData;
import app.model.AppHotLine;
import app.model.AppImg;
import app.model.AppSupplier;
import app.model.Img;
import app.service.IndexAppService;

import com.alibaba.fastjson.JSON;

import common.constant.Constant;
import common.model.UploadFile;
import common.service.UploadService;
import common.utils.CommonStringUtil;
import common.utils.UploadUtil;

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
    
    //数据字典
    @Autowired
    private DictionaryDataServiceI dictionaryDataServiceI;
    
    //公告Service
    @Autowired
    private ArticleService articleService;
    
    //文件上传
    @Autowired
    private UploadService uploadService;

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

    //供应商军队处罚公告typeId标识
    private static final String MILITARY_PUNISHMENT = "116";

    //供应商地方处罚公告typeId标识
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
    public String indexNews(HttpServletRequest request){
        List<Img> imgList = new ArrayList<>();
        StringBuffer url = request.getRequestURL();  
        String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append(request.getServletContext().getContextPath()).append("/").toString(); 
        List<Article> picList = articleService.selectPics();
        List<Article> indexPics = null;
        if(picList.size()>0){
            indexPics = new ArrayList<Article>();
            for(Article article : picList){
                DictionaryData dd=new DictionaryData();
                dd.setCode("POST_ATTACHMENT");
                List<DictionaryData> lists = dictionaryDataServiceI.find(dd);
                String sysKey = Constant.TENDER_SYS_KEY.toString();
                String attachTypeId=null;
                if(lists.size()>0){
                    attachTypeId = lists.get(0).getId();
                }
                List<UploadFile> uploadList = uploadService.getFilesOther(article.getId(), attachTypeId, sysKey);
                if(uploadList.size()>0){
                    article.setUploadId(uploadList.get(0).getId());
                    indexPics.add(article);
                }
            }
        }
        if(indexPics != null && !indexPics.isEmpty()){
            for (Article article : indexPics) {
                imgList.add(new Img(article.getId(),tempContextUrl+"file/viewFile.html?id="+article.getUploadId()+"&key=2"));
            }
        }else{
            imgList.add(new Img(null,tempContextUrl+"public/portal/images/AppImg1.png"));
            imgList.add(new Img(null,tempContextUrl+"public/portal/images/AppImg2.png"));
            imgList.add(new Img(null,tempContextUrl+"public/portal/images/AppImg3.png"));
        }
        List<Article> indexMsgList = new ArrayList<>();
        //动态
        Article dynamic= indexAppMapper.selectAppNewsByArticleTypeId(INDEX_DYNAMIC);
        if(dynamic != null){
            dynamic.setCreate_at(dataToString(dynamic.getPublishedAt()));
            indexMsgList.add(dynamic);
        }else{
            Article nullArticle = new Article();
            nullArticle.setName("");
            nullArticle.setCreate_at("");
            ArticleType at = new ArticleType();
            at.setId(INDEX_DYNAMIC);
            nullArticle.setArticleType(at);
            indexMsgList.add(nullArticle);
        }
        //通知
        Article notice = indexAppMapper.selectAppNewsByArticleTypeId(NOTICE);
        if(notice != null){
            notice.setCreate_at(dataToString(notice.getPublishedAt()));
            indexMsgList.add(notice);
        }else{
            Article nullArticle = new Article();
            nullArticle.setName("");
            nullArticle.setCreate_at("");
            ArticleType at = new ArticleType();
            at.setId(NOTICE);
            nullArticle.setArticleType(at);
            indexMsgList.add(nullArticle);
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
        }else{
            Article nullArticle = new Article();
            nullArticle.setName("");
            nullArticle.setCreate_at("");
            ArticleType at = new ArticleType();
            at.setId("107");
            nullArticle.setArticleType(at);
            indexMsgList.add(nullArticle);
        }
        //投诉
        Article complaintHandling= indexAppMapper.selectAppNewsByArticleTypeId(COMPLAINT_HANDLING);
        if(complaintHandling != null){
            complaintHandling.setCreate_at(dataToString(complaintHandling.getPublishedAt()));
            indexMsgList.add(complaintHandling);
        }else{
            Article nullArticle = new Article();
            nullArticle.setName("");
            nullArticle.setCreate_at("");
            ArticleType at = new ArticleType();
            at.setId(COMPLAINT_HANDLING);
            nullArticle.setArticleType(at);
            indexMsgList.add(nullArticle);
        }
        //处罚
        Article punishment = indexAppMapper.selectAppChuFaNewsByTypeId(PUNISHMENT);
        if(punishment != null){
            punishment.setCreate_at(dataToString(punishment.getPublishedAt()));
            indexMsgList.add(punishment);
        }else{
            Article nullArticle = new Article();
            nullArticle.setName("");
            nullArticle.setCreate_at("");
            ArticleType at = new ArticleType();
            at.setId(PUNISHMENT);
            nullArticle.setArticleType(at);
            indexMsgList.add(nullArticle);
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
                }else{
                    Article nullArticle = new Article();
                    nullArticle.setName("");
                    nullArticle.setCreate_at("");
                    nullArticle.setType_id(15);
                    indexMsgList.add(nullArticle);
                }
                //成交
                Article cj= indexAppMapper.selectAppNewsByArticleTypeId(CJ);
                if(cj != null){
                    cj.setCreate_at(dataToString(cj.getPublishedAt()));
                    cj.setType_id(16);
                    indexMsgList.add(cj);
                }else{
                    Article nullArticle = new Article();
                    nullArticle.setName("");
                    nullArticle.setCreate_at("");
                    nullArticle.setType_id(16);
                    indexMsgList.add(nullArticle);
                }
                //废标
                Article scrap= indexAppMapper.selectAppNewsByArticleTypeId(SCRAP);
                if(scrap != null ){
                    scrap.setCreate_at(dataToString(scrap.getPublishedAt()));
                    scrap.setType_id(17);
                    indexMsgList.add(scrap);
                }else{
                    Article nullArticle = new Article();
                    nullArticle.setName("");
                    nullArticle.setCreate_at("");
                    nullArticle.setType_id(17);
                    indexMsgList.add(nullArticle);
                }
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
            case 3 ://处罚公告
                Map<String, Object> map1 = new HashMap<>();
                String[] idArray = new String[2];
                idArray[0] = MILITARY_PUNISHMENT;
                idArray[1] = PLACE_PUNISHMENT;
                map1.put("idArray",idArray);
                map1.put("page", page);
                List<Article> militaryPunishmentList = indexAppService.selectAppRegulations(map1);
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
                    if(typeId == 110){
                        appData.setTitle("工作动态");
                    }else if(typeId == 109){
                        appData.setTitle("重要通知");
                    }else if(typeId == 112){
                        appData.setTitle("投诉处理公告");
                    }else{
                        appData.setTitle("通知");
                    }
                    List<Article> list = indexAppService.selectAppArticleListByTypeId(id,page);
                    if(list != null && !list.isEmpty()){
                        for (Article article : list) {
                            article.setCreate_at(dataToString(article.getPublishedAt()));
                        }
                        appData.setIndexMsgList(list);
                        appImg.setData(appData);
                        appImg.setStatus(true);
                    }else {
                        appImg.setData(appData);
                        appImg.setStatus(false);
                        appImg.setMsg("暂无数据");
                    }
                    break;
                case 108://法规
                case 107:
                    appData.setTitle("采购法规");
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
                        appImg.setData(appData);
                        appImg.setStatus(false);
                        appImg.setMsg("暂无数据");
                    }
                    break;
                case 113://处罚
                    appData.setTitle("处罚公告");
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
                        appImg.setData(appData);
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
     * Description: 查看公告详细列表
     * 
     * @author zhang shubin
     * @data 2017年6月6日
     * @param 
     * @return
     */
    @RequestMapping(value="/announcementList",produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String announcementList(Integer id,String type_id,@RequestParam(defaultValue="1")Integer page){
        AppImg appImg = new AppImg();
        Integer typeId = Integer.parseInt(type_id);
        String title = "";
        switch(id){
            case 1 ://采购公告
                title = "采购公告";
                switch (typeId) {
                    case 11 ://物资
                        appImg = getAppImg(indexAppService.selectAppNoticeList(new String[]{"3","24"},page),title);
                        break;
                    case 12 ://工程
                        appImg = getAppImg(indexAppService.selectAppNoticeList(new String[]{"8","29"},page),title);
                        break;
                    case 13 ://服务
                        appImg = getAppImg(indexAppService.selectAppNoticeList(new String[]{"13","34"},page),title);
                        break;
                    case 14 ://进口
                        appImg = getAppImg(indexAppService.selectAppNoticeList(new String[]{"18","39"},page),title);
                        break;
                }
                break;
            case 2 ://中标公示
                title =  "中标公示";
                switch (typeId) {
                    case 11 ://物资
                        appImg = getAppImg(indexAppService.selectAppNoticeList(new String[]{"46","67"},page),title);
                        break;
                    case 12 ://工程
                        appImg = getAppImg(indexAppService.selectAppNoticeList(new String[]{"51","72"},page),title);
                        break;
                    case 13 ://服务
                        appImg = getAppImg(indexAppService.selectAppNoticeList(new String[]{"56","77"},page),title);
                        break;
                    case 14 ://进口
                        appImg = getAppImg(indexAppService.selectAppNoticeList(new String[]{"61","82"},page),title);
                        break;
                }
                break;
            case 3 ://单一来源
                Map<String, Object> map = new HashMap<>();
                map.put("page", page);
                title = "单一来源公示";
                switch (typeId) {
                    case 11 ://物资
                        map.put("idArray", new String[]{"89","94"});
                        appImg = getAppImg(indexAppService.selectAppRegulations(map),title);
                        break;
                    case 12 ://工程
                        map.put("idArray", new String[]{"90","95"});
                        appImg = getAppImg(indexAppService.selectAppRegulations(map),title);
                        break;
                    case 13 ://服务
                        map.put("idArray", new String[]{"91","96"});
                        appImg = getAppImg(indexAppService.selectAppRegulations(map),title);
                        break;
                    case 14 ://进口
                        map.put("idArray", new String[]{"92","97"});
                        appImg = getAppImg(indexAppService.selectAppRegulations(map),title);
                        break;
                }
                break;
            case 4 ://竞价公告
                title = "竞价公告";
                switch (typeId) {
                    case 15 ://需求
                        appImg = getAppImg(indexAppService.selectAppArticleListByTypeId(DEMAND, page),title);
                        break;
                    case 16 ://成交
                        appImg = getAppImg(indexAppService.selectAppArticleListByTypeId(CJ, page),title);
                        break;
                    case 17 ://废标
                        appImg = getAppImg(indexAppService.selectAppArticleListByTypeId(SCRAP, page),title);
                        break;
                }
                break;
        }
        return JSON.toJSONString(appImg);
    }

    /**
     * 
     * Description: 查询公告内容
     * 
     * @author zhang shubin
     * @data 2017年6月6日
     * @param 
     * @return
     */
    @RequestMapping(value="/appDatailsById",produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String appDatailsById(String id,HttpServletRequest request){
        Article article = indexAppService.selectContentById(id);
        StringBuffer url = request.getRequestURL();  
        String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append(request.getServletContext().getContextPath()).append("/").toString(); 
        getContentImg(article, request);
        String content = null;
        if(article != null){
            content = "<div style='width: 100%;overflow: hidden;'><h3 style = 'text-align: center;font-size: 60px;'>"
                + "<div style='color: #323232 !important;'>"+article.getName()+"</div></h3>"
                + "<div style='overflow: hidden;border-bottom: 1px dashed #ddd;height: 100px;line-height: 100px;'>"
                + "<div style='text-align: right; margin-left: 15px;font-size: 40px;'><span>"
                + "<i style='margin-right: 5px;'>"
                + "<img src='"+tempContextUrl+"public/portal/images/block.png'/></i>"+dataToString(article.getPublishedAt())+"</span></div></div>"
                + "<div style='width: 100%; clear: both;margin-top: 20px;line-height: 30px;'>"
                + "<img src='"+tempContextUrl+"api/v1/AppdownloadDetailsImage.html?id="+article.getId()+"' width='100%'/></div></div>";
        }
        AppData appData = new AppData();
        AppImg appImg = new AppImg();
        if(content != null && !content.equals("")){
            appImg.setStatus(true);
            appData.setContent(content);
            appImg.setData(appData);
        }else{
            appImg.setStatus(false);
            appImg.setMsg("暂无内容");
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
    public String search(String key,@RequestParam(defaultValue="1")Integer page){
        AppData appData = new AppData();
        AppImg appImg = new AppImg();
        appData.setTitle("搜索结果");
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("key", key);
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
     * Description: 获取服务信息
     * 
     * @author zhang shubin
     * @data 2017年6月7日
     * @param 
     * @return
     */
    @RequestMapping(value="/appService",produces = "text/json;charset=UTF-8")
    @ResponseBody
    public String appService(Integer id,@RequestParam(defaultValue="1")Integer page){
        AppData appData = new AppData();
        AppImg appImg = new AppImg();
        switch (id) {
            case 1 ://意见反馈
                appImg.setStatus(false);
                appImg.setMsg("暂无数据");
                break;
            case 2 ://售后服务
                List<AppHotLine> list = indexAppService.selectHotLineList(page);
                if(list != null && !list.isEmpty()){
                    appData.setHotLineList(list);
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
     * Description: 封装公告返回的json数据
     * 
     * @author zhang shubin
     * @data 2017年6月6日
     * @param 
     * @return
     */
    public AppImg getAppImg(List<Article> list,String title){
        AppData appData = new AppData();
        AppImg appImg = new AppImg();
        appData.setTitle(title);
        if(list != null && !list.isEmpty()){
            for (Article article : list) {
                article.setCreate_at(dataToString(article.getPublishedAt()));
            }
            appData.setIndexMsgList(list);
            appImg.setData(appData);
            appImg.setStatus(true);
        }else {
            appImg.setData(appData);
            appImg.setStatus(false);
            appImg.setMsg("暂无数据");
        }
        return appImg;
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
        if(article != null){
            article.setCreate_at(dataToString(article.getPublishedAt()));
            article.setType_id(type_id);
            return article;
        }else{
            Article nullArticle = new Article();
            nullArticle.setName("");
            nullArticle.setCreate_at("");
            nullArticle.setType_id(type_id);
            return nullArticle;
        }
        
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

    /**
     * 
     * Description: 生成公告内容图片
     * 
     * @author zhang shubin
     * @data 2017年6月7日
     * @param 
     * @return
     */
    public void getContentImg(Article articleDetail,HttpServletRequest request){
        String filePath = PropUtil.getProperty("file.noticePic.base")+ File.separator + "Appzanpic";
        String glisteningPath = PropUtil.getProperty("file.noticePic.base")+ File.separator + "Appglistening"; 
        File glisteningFile = new File(glisteningPath+"/"+articleDetail.getId()+".jpg");
        UploadUtil.createDir(filePath);
        UploadUtil.createDir(glisteningPath);
        String proWaterPath = request.getSession().getServletContext().getRealPath("/")+"/proWatermark/shuiyin.png";
        File stagingFile = new File(filePath);
        File glisFile = new File(glisteningPath);
        //判读图片是否存在
        if(glisteningFile.exists()){
        } else {
            if(!stagingFile.exists()){
                stagingFile.mkdir();
            }
            if(!glisFile.exists()){
                glisFile.mkdir();
            }
            HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
            StringBuffer divStyle = new StringBuffer();
            divStyle.append("<div class='article_content' style='font-size: 20px; line-height: 35px; padding: 35px;width: 400px;'>");
            String content = articleDetail.getContent();
            if (StringUtils.isNotBlank(content)){
                content = content.replaceAll(CommonStringUtil.getAppendString("&nbsp;", 30), "");
                content = content.replaceAll(":=\"\"", "=\"\"");
            }
            divStyle.append(content);
            divStyle.append("</div>");
            String htmlstr = divStyle.toString();
            imageGenerator.loadHtml(htmlstr);
            imageGenerator.getBufferedImage();
            imageGenerator.saveAsImage(filePath+"/"+articleDetail.getId()+".png");
            String zancunPicPath = filePath+"/"+articleDetail.getId()+".png";
            String srcImgPath = zancunPicPath;  
            String iconPath = proWaterPath;
            String targerPath2 = glisteningPath+"/"+articleDetail.getId()+".jpg";
            //给图片添加水印，水印旋转-45
            markByText(iconPath, srcImgPath,targerPath2,0);
        }
    }

    /**
     * 
     * Description: 给图片添加水印
     * 
     * @author zhang shubin
     * @data 2017年6月7日
     * @param 
     * @return
     */
    public static void markByText(String logoText,String srcImgPath,String targetPath,Integer degree){
        //主图片路径
        InputStream is = null;
        FileOutputStream os = null;
        try {
            Image srcImg = ImageIO.read(new File(srcImgPath));
            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);
            //得到画笔对象
            Graphics2D g = buffImg.createGraphics();
            //设置对线段的锯齿状边缘处理
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg.getHeight(null), Image.SCALE_SMOOTH),0,0,null);
            if(null!=degree){
                //设置水印旋转
                g.rotate(Math.toRadians(degree),(double) buffImg.getWidth()/2,(double) buffImg.getHeight()/2);
            }
            ImageIcon imgIcon = new ImageIcon(logoText);
            Image img = imgIcon.getImage();
            float alpha = 0.5f;
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));
            g.drawImage(img,200,10,null);
            g.dispose();
            os = new FileOutputStream(targetPath);
            //生成图片
            ImageIO.write(buffImg, "jpg", os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(null!=is)
                    is.close();
                if(null!=os)
                    os.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /**
     * 
     * Description: 读取生成的公告图片
     * 
     * @author zhang shubin
     * @data 2017年6月9日
     * @param 
     * @return
     */
    @RequestMapping("/AppdownloadDetailsImage")
    public void downloadDetailsImage(HttpServletRequest request,HttpServletResponse response){
        String id = request.getParameter("id");
        String filePath = PropUtil.getProperty("file.noticePic.base") + "/Appglistening";
        String targerPath2 = filePath+"/"+id+".jpg";
        InputStream fis = null;
        try {
            File file = new File(targerPath2);
            response.setContentType("image/*");
            fis = new BufferedInputStream(new FileInputStream(file));  
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            byte[] b = new byte[fis.available()];
            fis.read(b);
            toClient.write(b);
            toClient.flush();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(fis!=null)
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }
    
}
