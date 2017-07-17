package app.service.impl;

import gui.ava.html.image.generator.HtmlImageGenerator;
import iss.model.ps.Article;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.swing.ImageIcon;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.util.PropUtil;
import common.utils.CommonStringUtil;
import common.utils.UploadUtil;
import app.dao.app.AppArticleMapper;
import app.dao.app.AppSupplierBlackListMapper;
import app.dao.app.AppSupplierMapper;
import app.dao.app.IndexAppMapper;
import app.model.AppBlackList;
import app.model.AppHotLine;
import app.model.AppSupplier;
import app.service.IndexAppService;

@Service("indexAppService")
public class IndexAppServiceImpl implements IndexAppService{
    
    //分页条数
    private static final Integer PAGESIZE = 10;
    
    //首页通知
    @Autowired
    private IndexAppMapper indexAppMapper;
    
    //供应商
    @Autowired
    private AppSupplierMapper appSupplierMapper;
    
    //供应商黑名单
    @Autowired
    private AppSupplierBlackListMapper appSupplierBlackListMapper;
    
    //公告Mappper
    @Autowired
    private AppArticleMapper appArticleMapper;
    
    /**
     * 分页查询公告列表
     */
    @Override
    public List<Article> selectAppArticleListByTypeId(String articleTypeId,Integer page) {
        Map<String, Object> map = new HashMap<>();
        map.put("articleTypeId", articleTypeId);
        map.put("start", (page-1)*PAGESIZE+1);
        map.put("end", page*PAGESIZE);
        return indexAppMapper.selectAppArticleListByTypeId(map);
    }

    /**
     * 分页查询供应商名录
     */
    @Override
    public List<AppSupplier> selectAppSupplierList(Map<String, Object> map) {
        Integer page = (Integer) map.get("page");
        map.put("start", (page-1)*PAGESIZE+1);
        map.put("end", page*PAGESIZE);
        return appSupplierMapper.selectAppSupplierList(map);
    }

    /**
     * 分页查询供应商黑名单
     */
    @Override
    public List<AppBlackList> findAppSupplierBlacklist(Integer page) {
        Map<String, Object> map = new HashMap<>();
        map.put("start", (page-1)*PAGESIZE+1);
        map.put("end", page*PAGESIZE);
        return appSupplierBlackListMapper.findAppSupplierBlacklist(map);
    }

    /**
     * 分页查询专家名录
     */
    @Override
    public List<AppSupplier> selectAppExpertList(Map<String, Object> map) {
        Integer page = (Integer) map.get("page");
        map.put("start", (page-1)*PAGESIZE+1);
        map.put("end", page*PAGESIZE);
        return appSupplierMapper.selectAppExpertList(map);
    }

    /**
     * 专家黑名单
     */
    @Override
    public List<AppBlackList> findAppExpertBlacklist(Integer page) {
        Map<String, Object> map = new HashMap<>();
        map.put("start", (page-1)*PAGESIZE+1);
        map.put("end", page*PAGESIZE);
        return appSupplierBlackListMapper.findAppExpertBlacklist(map);
    }

    /**
     * 法规
     */
    @Override
    public List<Article> selectAppRegulations(Map<String, Object> map) {
        Integer page = (Integer) map.get("page");
        map.put("start", (page-1)*PAGESIZE+1);
        map.put("end", page*PAGESIZE);
        return indexAppMapper.selectAppRegulations(map);
    }

    /**
     * 处罚公告
     */
    @Override
    public List<Article> selectAppPunishment(Map<String, Object> map) {
        Integer page = (Integer) map.get("page");
        map.put("start", (page-1)*PAGESIZE+1);
        map.put("end", page*PAGESIZE);
        return indexAppMapper.selectAppPunishment(map);
    }

    /**
     * 搜索
     */
    @Override
    public List<Article> search(Map<String, Object> map) {
        Integer page = (Integer) map.get("page");
        map.put("start", (page-1)*PAGESIZE+1);
        map.put("end", page*PAGESIZE);
        return indexAppMapper.search(map);
    }

    /**
     * 公告列表 采购公告  中标公示
     */
    @Override
    public List<Article> selectAppNoticeList(String ids[],Integer page) {
        Map<String, Object> map = new HashMap<>();
        map.put("idArray", ids);
        map.put("start", (page-1)*PAGESIZE+1);
        map.put("end", page*PAGESIZE);
        return appArticleMapper.selectAppNoticeList(map);
    }

    /**
     * 查询公告内容
     */
    @Override
    public Article selectContentById(String id) {
        return indexAppMapper.selectContentById(id);
    }

    /**
     * 查询服务热线
     */
    @Override
    public List<AppHotLine> selectHotLineList(Integer page) {
        Map<String, Object> map = new HashMap<>();
        map.put("start", (page-1)*PAGESIZE+1);
        map.put("end", page*PAGESIZE);
        return appArticleMapper.selectHotLineList(map);
    }

    /**
     * 生成公告图片
     */
	@Override
	public void getContentImg(Article articleDetail, HttpServletRequest request) {
        String filePath = PropUtil.getProperty("file.noticePic.base")+ File.separator + "Appzanpic";
        String glisteningPath = PropUtil.getProperty("file.noticePic.base")+ File.separator + "Appglistening"; 
        File glisteningFile = new File(glisteningPath+"/"+articleDetail.getId()+".jpg");
        UploadUtil.createDir(filePath);
        UploadUtil.createDir(glisteningPath);
        String PATH_CLASS_ROOT = this.getClass().getClassLoader().getResource("").getPath();    
    	/*项目根路径*/  
    	String ROOT_Path = PATH_CLASS_ROOT.substring(0,PATH_CLASS_ROOT.length() - "WEB-INF\\classes\\".length());
        String proWaterPath = ROOT_Path+"/proWatermark/shuiyin.png";
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
}
