package app.controller;

import iss.dao.ps.ArticleMapper;
import iss.dao.ps.IndexNewsMapper;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import app.model.AppData;
import app.model.AppImg;

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
@RequestMapping("/indexApp")
public class IndexApp {

    @Autowired
    private IndexNewsMapper indexNewsMapper;
    
    @Autowired
    private ArticleMapper articleMapper;
    
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
        List<Article> dynamicList= indexNewsMapper.selectAppNewsByArticleTypeId(INDEX_DYNAMIC);
        if(dynamicList != null && !dynamicList.isEmpty()){
            dynamicList.get(0).setCreate_at(dataToString(dynamicList.get(0).getPublishedAt()));
            indexMsgList.add(dynamicList.get(0));
        }
        //通知
        List<Article> noticeList= indexNewsMapper.selectAppNewsByArticleTypeId(NOTICE);
        if(noticeList != null && !noticeList.isEmpty()){
            noticeList.get(0).setCreate_at(dataToString(noticeList.get(0).getPublishedAt()));
            indexMsgList.add(noticeList.get(0));
        }
        //法规
        Map<String, Object> map = new HashMap<>();
        String[] idArray = new String[2];
        idArray[0] = "107";
        idArray[1] = "108";
        map.put("idArray",idArray);
        List<Article> regulationsList= articleMapper.selectsumApp(map);
        if(regulationsList != null && !regulationsList.isEmpty()){
            regulationsList.get(0).setCreate_at(dataToString(regulationsList.get(0).getPublishedAt()));
            indexMsgList.add(regulationsList.get(0));
        }
        //投诉
        List<Article> complaintHandlingList= indexNewsMapper.selectAppNewsByArticleTypeId(COMPLAINT_HANDLING);
        if(complaintHandlingList != null && !complaintHandlingList.isEmpty()){
            complaintHandlingList.get(0).setCreate_at(dataToString(complaintHandlingList.get(0).getPublishedAt()));
            indexMsgList.add(complaintHandlingList.get(0));
        }
        //处罚
        List<Article> punishmentList= indexNewsMapper.selectAppChuFaNewsByTypeId(PUNISHMENT);
        if(punishmentList != null && !punishmentList.isEmpty()){
            punishmentList.get(0).setCreate_at(dataToString(punishmentList.get(0).getPublishedAt()));
            indexMsgList.add(punishmentList.get(0));
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
                List<Article> demandList= indexNewsMapper.selectAppNewsByArticleTypeId(DEMAND);
                if(demandList != null && !demandList.isEmpty()){
                    demandList.get(0).setCreate_at(dataToString(demandList.get(0).getPublishedAt()));
                    demandList.get(0).setType_id(15);
                    indexMsgList.add(demandList.get(0));
                };
                //成交
                List<Article> cjList= indexNewsMapper.selectAppNewsByArticleTypeId(CJ);
                if(cjList != null && !cjList.isEmpty()){
                    cjList.get(0).setCreate_at(dataToString(cjList.get(0).getPublishedAt()));
                    cjList.get(0).setType_id(16);
                    indexMsgList.add(cjList.get(0));
                };
                //废标
                List<Article> scrapList= indexNewsMapper.selectAppNewsByArticleTypeId(SCRAP);
                if(scrapList != null && !scrapList.isEmpty()){
                    scrapList.get(0).setCreate_at(dataToString(scrapList.get(0).getPublishedAt()));
                    scrapList.get(0).setType_id(17);
                    indexMsgList.add(scrapList.get(0));
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
        Article article= (i == 0 ? articleMapper.selectOneAppNoticeByParId(map) : articleMapper.selectsumApp(map).get(0));
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
