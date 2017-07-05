
package iss.service.ps.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.github.pagehelper.PageHelper;

import bss.model.ppms.AdvancedDetail;
import bss.model.ppms.AdvancedPackages;
import bss.model.ppms.Packages;
import bss.model.ppms.ProjectDetail;
import iss.dao.ps.ArticleMapper;
import iss.model.ps.Article;
import iss.model.ps.ArticleCategory;
import iss.service.ps.ArticleService;
import ses.dao.bms.CategoryMapper;
import ses.model.bms.Category;
import ses.model.bms.CategoryTree;
import ses.model.bms.DictionaryData;
import ses.model.sms.SupplierBlacklist;
import ses.util.DictionaryDataUtil;
import ses.util.PropUtil;
import ses.util.PropertiesUtil;


/**
 * @Title:ArticleServiceImpl
 * @Description: 信息发布接口实现类
 * @author Shen Zhenfei
 * @date 2016-9-7下午6:03:56
 */
@Service("articleService")
public class ArticleServiceImpl implements ArticleService {

  @Autowired
  private ArticleMapper articleMapper;

  @Autowired
  private CategoryMapper categoryMapper;
  
  /**
   * 新增信息
   */
  @Override
  public void addArticle(Article article) {
    articleMapper.insertSelective(article);
  }

  /**
   * 查询所有信息列表
   */
  @Override
  public List<Article> selectAllArticle(Article article,Integer pageNum) {
    PropertiesUtil config = new PropertiesUtil("config.properties");
    PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSizeArticle")));
    return articleMapper.selectAllArticle(article);
  }

  /**
   * 修改信息
   */
  @Override
  public void update(Article article) {
    articleMapper.update(article);
  }

  /**
   * 
   * @see iss.service.ps.ArticleService#insertArticle(iss.model.ps.Article)
   */
  @Override
  public void insertArticle(Article article) {
    articleMapper.insertArticle(article);
  }

  @Override
  public void delArticleById(String id) {
    articleMapper.deleteByPrimaryKey(id);
  }

  /**
   * 
   * @see iss.service.ps.ArticleService#updateArticle(iss.model.ps.Article)
   */
  @Override
  public void updateArticle(Article article) {
    articleMapper.updateArticle(article);
  }

  /**
   * 根据id查询信息
   */
  @Override
  public Article selectArticleById(String id) {
    return articleMapper.selectById(id);
  }
  
  @Override
  public Article selectReleaseById(String id) {
    return articleMapper.selectReleaseById(id);
  }
  

  @Override
  public void delete(String id) {
    articleMapper.Isdelete(id);
  }

  /**
   * 查询标题信息
   */
  @Override
  public List<Article> checkName(Article article) {
    List<Article> list = articleMapper.checkName(article);
    return list;
  }

  /**
   * 根据类型查询
   */
  @Override
  public List<Article> selectArticleByStatus(HashMap<String, Object> map) {
    List<Article> list = articleMapper.selectArticleByStatus(map);
    return list;
  }

  /**
   * 为solr查询所有信息
   */
  @Override
  public List<Article> selectAllArticleToSolr() {
    return articleMapper.selectAllArticleToSolr();
  }

  /**
   * 根据标题查询列表
   */
  @Override
  public List<Article> selectArticleByName(HashMap<String, Object> map) {
    List<Article> list = articleMapper.selectArticleByName(map);
    return list;
  }

  @Override
  public List<Article> selectArticleByProjectId(Article article) {
    List<Article> list = articleMapper.selectArticleByProjectId(article);
    return list;
  }

  @Override
  public void updateisPicShow(String isPicShow) {
    articleMapper.updateisPicShow(isPicShow);
  }

  @Override
  public List<Article> selectPic() {
    return articleMapper.selectPic();
  }

  @Override
  public StringBuilder getContent(List<Packages> listPackages) {
    StringBuilder sb = new StringBuilder("");
    sb.append("<table>");
    Integer index = 0;
    for (Packages pack : listPackages) {
   
      sb.append("<tr><td colspan=9>"+pack.getName()+"</td></tr>");
      sb.append("<tr><th>序号</th><th>货物名称</th><th>规格型号</th><th>技术要求</th><th>计量单位</th><th>数量</th><th>交货时间</th><th>交货地点</th><th>备注</th></tr>");
      for (ProjectDetail pd : pack.getProjectDetails()) {
        ++index;
        if (pd.getPrice() == null || "".equals(pd.getPrice())) {
          continue;
        } else {
          if (pd.getSerialNumber() == null) pd.setSerialNumber("");
          if (pd.getDepartment() == null) pd.setDepartment("");
          if (pd.getGoodsName() == null) pd.setGoodsName("");
          if (pd.getStand() == null) pd.setStand("");
          if (pd.getQualitStand() == null) pd.setQualitStand("");
          if (pd.getDeliverDate() == null) pd.setDeliverDate("");
          String price = "";
          if (pd.getPrice() != null) {
            price = pd.getPrice() + "";
          }
          String budget = "";
          if (pd.getBudget() != null) {
            budget = pd.getBudget() + "";
          }
          String purchaseCount = "";
          if (pd.getPurchaseCount() != null) {
            purchaseCount = pd.getPurchaseCount() + "";
          }
          if (pd.getItem() == null) pd.setItem("");
          if (pd.getPurchaseCount() == null) pd.setPurchaseCount(0.00);
          sb.append("<tr><td style=text-align: center;>"+index+"</td>");   
          sb.append("<td style=text-align: center;>"+pd.getGoodsName()+" </td>");
          sb.append("<td style=text-align: center;>"+pd.getStand()+"</td>");
          sb.append("<td style=text-align: center;>"+pd.getQualitStand()+"</td>");
          sb.append("<td style=text-align: center;>"+pd.getItem()+"</td>");
          sb.append("<td style=text-align: right;>"+purchaseCount+"</td>");
          sb.append("<td style=text-align: center;>"+pd.getDeliverDate()+"</td>");
          sb.append("<td></td><td></td></tr>");
        }
      }
    }
    sb.append("<tr><td>说明</td><td colspan=8>1. 投标人须对所投包内所有产品和数量进行投标报价，否则视为无效投标。<br/>2. 运杂费：</td></tr></table>");
    return sb;
  }

  @Override
  public void updateStatus(Article article) {
    articleMapper.updateStatus(article);
  }

  @Override
  public List<Article> selectArticleByParId(Map<String, Object> map) {
    return articleMapper.selectArticleByParId(map);
  }

  @Override
  public List<Article> selectArticleByParIdTwo(Map<String, Object> map) {
    PropertiesUtil config = new PropertiesUtil("config.properties");
    PageHelper.startPage((Integer)(map.get("page")),Integer.parseInt(config.getString("pageSize")));
    return articleMapper.selectArticleByParIdTwo(map);
  }

  @Override
  public List<Article> selectArticleByArticleType(Map<String, Object> map) {
    return articleMapper.selectArticleByArticleType(map);
  }

  @Override
  public List<Article> selectsumByParId(Map<String, Object> map) {
    PropertiesUtil config = new PropertiesUtil("config.properties");
    PageHelper.startPage((Integer)(map.get("page")),Integer.parseInt(config.getString("pageSize")));
    return articleMapper.selectsumByParId(map);
  }

  @Override
  public List<Article> selectsumBynews(Map<String, Object> map) {
    PropertiesUtil config = new PropertiesUtil("config.properties");
    PageHelper.startPage((Integer)(map.get("page")),Integer.parseInt(config.getString("pageSize")));
    return articleMapper.selectsumBynews(map);
  }

  @Override
  public List<Article> selectsumBydanNews(Map<String, Object> map) {
    PropertiesUtil config = new PropertiesUtil("config.properties");
    PageHelper.startPage((Integer)(map.get("page")),Integer.parseInt(config.getString("pageSize")));
    return articleMapper.selectsumBydanNews(map);
  }

  @Override
  public List<Article> selectJob(Map<String, Object> map) {
    return articleMapper.selectJob(map);
  }

  @Override
  public List<Article> selectPics() {
    return articleMapper.selectPics();
  }

  @Override
  public BigDecimal selectByTimer(Map<String, Object> map) {
    return articleMapper.selectByTimer(map);
  }

  @Override
  public BigDecimal selectByTimerByType(Map<String, Object> map) {
    return articleMapper.selectByTimerByType(map);
  }

  @Override
  public List<Article> selectAllByParId(Map<String, Object> map) {
    return articleMapper.selectAllByParId(map);
  }

  @Override
  public List<Article> selectAllByArticleType(Map<String, Object> map) {
    return articleMapper.selectAllByArticleType(map);
  }

  @Override
  public StringBuilder getContents(List<AdvancedPackages> listPackages) {
    StringBuilder sb = new StringBuilder("");
    sb.append("<table><tr><th>包名</th><th>货物名称</th><th>规格型号</th><th>技术要求</th><th>计量单位</th><th>数量</th><th>交货时间</th><th>交货地点</th><th>备注</th></tr>");
    for (AdvancedPackages pack : listPackages) {
      for (AdvancedDetail pd : pack.getAdvancedDetails()) {
        if (pd.getPrice() == null || "".equals(pd.getPrice())) {
          continue;
        } else {
          if (pd.getSerialNumber() == null) pd.setSerialNumber("");
          if (pd.getDepartment() == null) pd.setDepartment("");
          if (pd.getGoodsName() == null) pd.setGoodsName("");
          if (pd.getStand() == null) pd.setStand("");
          if (pd.getQualitStand() == null) pd.setQualitStand("");
          if (pd.getDeliverDate() == null) pd.setDeliverDate("");
          String price = "";
          if (pd.getPrice() != null) {
            price = pd.getPrice() + "";
          }
          String budget = "";
          if (pd.getBudget() != null) {
            price = pd.getPrice() + "";
          }
          String purchaseCount = "";
          if (pd.getPurchaseCount() != null) {
            purchaseCount = pd.getPurchaseCount() + "";
          }
          if (pd.getItem() == null) pd.setItem("");
          if (pd.getPurchaseCount() == null) {
            pd.setPurchaseCount(new BigDecimal(0));
          }
          sb.append("<tr><td>"+pack.getName()+"</td>");   
          sb.append("<td>"+pd.getGoodsName()+" </td>");
          sb.append("<td>"+pd.getStand()+"</td>");
          sb.append("<td>"+pd.getQualitStand()+"</td>");
          sb.append("<td>"+pd.getItem()+"</td>");
          sb.append("<td>"+purchaseCount+"</td>");
          sb.append("<td>"+pd.getDeliverDate()+"</td>");
          sb.append("<td></td><td></td></tr>");
        }
      }
    }
    sb.append("<tr><td>说明</td><td colspan=8>1. 投标人须对所投包内所有产品和数量进行投标报价，否则视为无效投标。<br/>2. 运杂费：</td></tr></table>");
    return sb;
  }

  @Override
  public BigDecimal selectDanByTimer(Map<String, Object> map) {
    return articleMapper.selectDanByTimer(map);
  }

  /**
   * 
   * @see iss.service.ps.ArticleService#selectListByTitle(java.lang.String)
   */
  @Override
  public List<Article> selectListByTitle(String title, Integer page) {
    PageHelper.startPage(page,Integer.parseInt(PropUtil.getProperty("pageSizeArticle")));
    return articleMapper.selectListByTitle(title);
  }

  @Override
  public Map<String, Object> topNews() {
    Map<String, Object> indexMapper = new HashMap<String, Object>();
    Map<String, Object> map = new HashMap<String, Object>();
    map.clear();
    String[] idArray = {"3","24"};
    map.put("idArray", idArray);
    List<Article> xinxicaiwuziList = articleMapper.selectAllByParId(map);
    //			xinxicaiwuziList.addAll(article3List);
    //			xinxicaiwuziList.addAll(article24List);
    indexMapper.put("xinxicaiwuziList", xinxicaiwuziList);
    //			List<Article> xinxicaigongchengList = new ArrayList<Article>();
    map.clear();
    String[] idArray1 = {"8","29"};
    map.put("idArray", idArray1);
    List<Article> xinxicaigongchengList = articleMapper.selectAllByParId(map);
    //			xinxicaigongchengList.addAll(article8List);
    //			xinxicaigongchengList.addAll(article29List);
    indexMapper.put("xinxicaigongchengList", xinxicaigongchengList);
    //			List<Article> xinxicaifuwuList = new ArrayList<Article>();
    map.clear();
    String[] idArray2 = {"13","34"};
    map.put("idArray", idArray2);
    List<Article> xinxicaifuwuList = articleMapper.selectAllByParId(map);
    //			xinxicaifuwuList.addAll(article13List);
    //			xinxicaifuwuList.addAll(article34List);
    indexMapper.put("xinxicaifuwuList", xinxicaifuwuList);
    //			List<Article> xinxicaijinkouList = new ArrayList<Article>();
    map.clear();
    String[] idArray3 = {"18","39"};
    map.put("idArray", idArray3);
    List<Article> xinxicaijinkouList = articleMapper.selectAllByParId(map);
    //			xinxicaijinkouList.addAll(article18List);
    //			xinxicaijinkouList.addAll(article39List);
    indexMapper.put("xinxicaijinkouList", xinxicaijinkouList);
    //			List<Article> xinxizhongwuziList = new ArrayList<Article>();
    map.clear();
    String[] idArray4 = {"46","67"};
    map.put("idArray", idArray4);
    List<Article> xinxizhongwuziList = articleMapper.selectAllByParId(map);
    //			xinxizhongwuziList.addAll(article46List);
    //			xinxizhongwuziList.addAll(article67List);
    indexMapper.put("xinxizhongwuziList", xinxizhongwuziList);
    //			List<Article> xinxizhonggongchengList = new ArrayList<Article>();
    map.clear();
    String[] idArray5 = {"51","72"};
    map.put("idArray", idArray5);
    List<Article> xinxizhonggongchengList = articleMapper.selectAllByParId(map);
    //			xinxizhonggongchengList.addAll(article51List);
    //			xinxizhonggongchengList.addAll(article72List);
    indexMapper.put("xinxizhonggongchengList", xinxizhonggongchengList);
    //			List<Article> xinxizhongfuwuList = new ArrayList<Article>();
    map.clear();
    String[] idArray6 = {"56","77"};
    map.put("idArray", idArray6);
    List<Article> xinxizhongfuwuList = articleMapper.selectAllByParId(map);
    //			xinxizhongfuwuList.addAll(article56List);
    //			xinxizhongfuwuList.addAll(article77List);
    indexMapper.put("xinxizhongfuwuList", xinxizhongfuwuList);
    //			List<Article> xinxizhongjinkouList = new ArrayList<Article>();
    map.clear();
    String[] idArray7 = {"61","82"};
    map.put("idArray", idArray7);
    List<Article> xinxizhongjinkouList = articleMapper.selectAllByParId(map);
    //			xinxizhongjinkouList.addAll(article61List);
    //			xinxizhongjinkouList.addAll(article82List);
    indexMapper.put("xinxizhongjinkouList", xinxizhongjinkouList);
    //			List<Article> xinxidanwuziList = new ArrayList<Article>();
    map.clear();
    String[] idArray8 = {"89","94"};
    map.put("idArray", idArray8);
    List<Article> xinxidanwuziList = articleMapper.selectAllByArticleType(map);
    //			xinxidanwuziList.addAll(article89List);
    //			xinxidanwuziList.addAll(article94List);
    indexMapper.put("xinxidanwuziList", xinxidanwuziList);
    //			List<Article> xinxidangongchengList = new ArrayList<Article>();
    map.clear();
    String[] idArray9 = {"90","95"};
    map.put("idArray", idArray9);
    List<Article> xinxidangongchengList = articleMapper.selectAllByArticleType(map);
    //			xinxidangongchengList.addAll(article90List);
    //			xinxidangongchengList.addAll(article95List);
    indexMapper.put("xinxidangongchengList", xinxidangongchengList);
    //			List<Article> xinxidanfuwuList = new ArrayList<Article>();
    map.clear();
    String[] idArray10 = {"91","96"};
    map.put("idArray", idArray10);
    List<Article> xinxidanfuwuList = articleMapper.selectAllByArticleType(map);
    //			xinxidanfuwuList.addAll(article91List);
    //			xinxidanfuwuList.addAll(article96List);
    indexMapper.put("xinxidanfuwuList", xinxidanfuwuList);
    //			List<Article> xinxidanjinkouList = new ArrayList<Article>();
    map.clear();
    String[] idArray11 = {"92","97"};
    map.put("idArray", idArray11);
    List<Article> xinxidanjinkouList = articleMapper.selectAllByArticleType(map);
    //			xinxidanjinkouList.addAll(article92List);
    //			xinxidanjinkouList.addAll(article97List);
    indexMapper.put("xinxidanjinkouList", xinxidanjinkouList);
    map.clear();
    map.put("typeId","103");
    List<Article> article103List = articleMapper.selectArticleByArticleType(map);
    indexMapper.put("select103List", article103List);
    map.clear();
    map.put("typeId","104");
    List<Article> article104List = articleMapper.selectArticleByArticleType(map);
    indexMapper.put("select104List", article104List);
    map.clear();
    map.put("typeId","105");
    List<Article> article105List = articleMapper.selectArticleByArticleType(map);
    indexMapper.put("select105List", article105List);
    map.clear();
    map.put("typeId","112");
    List<Article> article112List = articleMapper.selectArticleByArticleType(map);
    indexMapper.put("select112List", article112List);
    map.clear();
    map.put("typeId","107");
    List<Article> article107List = articleMapper.selectArticleByArticleType(map);
    indexMapper.put("select107List", article107List);
    map.clear();
    map.put("typeId","108");
    List<Article> article108List = articleMapper.selectArticleByArticleType(map);
    indexMapper.put("select108List", article108List);
    map.clear();
    map.put("typeId","109");
    List<Article> article109List = articleMapper.selectArticleByArticleType(map);
    indexMapper.put("select109List", article109List);
    map.clear();
    map.put("typeId","115");
    List<Article> article115List = articleMapper.selectArticleByArticleType(map);
    indexMapper.put("article115List", article115List);
    map.clear();
    map.put("typeId","116");
    List<Article> article116List = articleMapper.selectArticleByArticleType(map);
    indexMapper.put("article116List", article116List);
    map.clear();
    map.put("typeId","117");
    List<Article> article117List = articleMapper.selectArticleByArticleType(map);
    indexMapper.put("article117List", article117List);
    return indexMapper;
  }

  @Override
  public List<Article> selectAllByTab(Map<String, Object> map) {
    return articleMapper.selectAllByTab(map);
  }

  @Override
  public List<Article> selectAllByTabs(Map<String, Object> map) {
    PropertiesUtil config = new PropertiesUtil("config.properties");
    PageHelper.startPage((Integer)(map.get("page")),Integer.parseInt(config.getString("pageSize")));
    return articleMapper.selectAllByTabs(map);
  }

  @Override
  public List<Article> selectAllByDanTab(Map<String, Object> map) {
    return articleMapper.selectAllByDanTab(map);
  }

  @Override
  public List<Article> selectAllByDanTabs(Map<String, Object> map) {
    PropertiesUtil config = new PropertiesUtil("config.properties");
    PageHelper.startPage((Integer)(map.get("page")),Integer.parseInt(config.getString("pageSize")));
    return articleMapper.selectAllByDanTabs(map);
  }

  /**
   * 
   * @see iss.service.ps.ArticleService#getListBypublishedTime(java.lang.String, java.lang.String)
   */
  @Override
  public List<Article> getListBypublishedTime(String startTime, String endTime) {
    List<Article> list =  articleMapper.getListByPublishedTime(startTime,  endTime);
    if (list != null && list.size() > 0){
      return list;
    }
    return new ArrayList<>();
  }

  /**
   * 
   * @see iss.service.ps.ArticleService#getCount(java.lang.String)
   */
  @Override
  public Integer getArticleCount(String id) {
    if (StringUtils.isNotBlank(id)){
      Integer count = articleMapper.getArticleCount(id);
      return count;
    }
    return 0;
  }

  @Override
  public List<Article> selectByJurisDiction(Map<String, Object> map) {
    PropertiesUtil config = new PropertiesUtil("config.properties");
    PageHelper.startPage((Integer)(map.get("page")),Integer.parseInt(config.getString("pageSize")));
    return articleMapper.selectByJurisDiction(map);
  }

  @Override
  public List<Article> selectAllByTabss(Map<String, Object> map) {
    PropertiesUtil config = new PropertiesUtil("config.properties");
    PageHelper.startPage((Integer)(map.get("page")),Integer.parseInt(config.getString("pageSize")));
    return articleMapper.selectAllByTabss(map);
  }

  @Override
  public BigDecimal selectAllByTimer(Map<String, Object> map) {
    return articleMapper.selectAllByTimer(map);
  }

  /**
   * 
   * @see iss.service.ps.ArticleService#getCancelNews(java.lang.String, java.lang.String)
   */
  @Override
  public List<Article> getCancelNews(String startTime, String endTime) {
    return articleMapper.getCancelArticle(startTime, endTime);
  }

  @Override
  public List<Article> selectAllByArticleTypeId(Map<String, Object> map) {
    return articleMapper.selectAllByArticleTypeId(map);
  }

  @Override
  public BigDecimal selectByTypeIdTimer(HashMap<String, String> timerMap) {
    return articleMapper.selectByTypeIdTimer(timerMap);
  }

  @Override
  public List<Category> getCategoryIsPublish(String parentId) {
      return categoryMapper.findPublishTree(parentId, null);
  }

  @Override
  public boolean isCheckCategory(String articleId, String categoryId) {
      ArticleCategory articleCategory = new ArticleCategory();
      articleCategory.setArticleId(articleId);
      articleCategory.setCategoryId(categoryId);
      List<ArticleCategory> articleCategories = articleMapper.findArtCategory(articleCategory);
      if (articleCategories != null && articleCategories.size() > 0) {
          return true;
      } else {
          return false;
      }
  }

  @Override
  public void saveArtCategory(String articleId, String categoryIds) {
      //先查删除原有关联
      ArticleCategory articleCategory0 = new ArticleCategory();
      articleCategory0.setArticleId(articleId);
      articleMapper.deleteArtCategory(articleCategory0);
      if (articleId != null && !"".equals(articleId) && categoryIds != null && !"".equals(categoryIds)) {
          //保存关联
          String[] categoryIdArry = categoryIds.split(",");
          for (String categoryId : categoryIdArry) {
              ArticleCategory articleCategory = new ArticleCategory();
              articleCategory.setArticleId(articleId);
              articleCategory.setCategoryId(categoryId.trim());
              articleMapper.saveArtCategory(articleCategory);
          }
      }
    
  }

  @Override
  public void getArticleCategory(String articleId, Model model) {
      ArticleCategory articleCategory = new ArticleCategory();
      articleCategory.setArticleId(articleId);
      List<ArticleCategory> articleCategories = articleMapper.findArtCategory(articleCategory);
      String categoryIds = "";
      String categoryNames = "";
      if (articleCategories != null && articleCategories.size() > 0) {
          for (int i = 0; i < articleCategories.size(); i++) {
              ArticleCategory category = articleCategories.get(i);
              Category category2 = categoryMapper.findById(category.getCategoryId());
              if (category != null) {
                  if (category2 != null) {
                    if (i+1 == articleCategories.size()) {
                      categoryIds += category.getCategoryId();
                      categoryNames += category2.getName();
                    } else {
                      categoryIds += category.getCategoryId() + ",";
                      categoryNames += category2.getName() + ",";
                    }
                  }else {
                    //根节点
                    DictionaryData dictionaryData = DictionaryDataUtil.findById(category.getCategoryId());
                    if (dictionaryData != null && category != null) {
                      if (i+1 == articleCategories.size()) {
                        categoryIds += category.getCategoryId();
                        categoryNames += dictionaryData.getName();
                      } else {
                        categoryIds += category.getCategoryId() + ",";
                        categoryNames += dictionaryData.getName() + ",";
                      }
                    } 
                  }
              }
          }
      }
      model.addAttribute("categoryIds", categoryIds);
      model.addAttribute("categoryNames", categoryNames);
  }

  @Override
  public void backArtCategory(String categoryIds, Model model) {
      String categoryNames = "";
      if (categoryIds != null && !"".equals(categoryIds)) {
          String[] categoryIdArry = categoryIds.split(",");
          for (int i = 0; i < categoryIdArry.length; i++) {
              Category category2 = categoryMapper.findById(categoryIdArry[i]);
              if (category2 != null) {
                  if (i+1 == categoryIdArry.length) {
                    categoryNames += category2.getName();
                  } else {
                    categoryNames += category2.getName() + ",";
                  }
              }else {
                  //根节点
                  DictionaryData dictionaryData = DictionaryDataUtil.findById(categoryIdArry[i]);
                  if (dictionaryData != null) {
                        if (i+1 == categoryIdArry.length) {
                          categoryNames += dictionaryData.getName();
                        } else {
                          categoryNames += dictionaryData.getName() + ",";
                        }
                  } 
              }
          }
      }
      model.addAttribute("categoryIds", categoryIds);
      model.addAttribute("categoryNames", categoryNames);
      model.addAttribute("backCategoryIds", categoryIds);
  }

  @Override
  public List<Category> getAllParent(String categoryId) {
      List < Category > categoryList = new ArrayList < Category > ();
      while(true) {
          Category category = null;
          category = categoryMapper.findById(categoryId);
          if(category == null) {
              DictionaryData root = DictionaryDataUtil.findById(categoryId);
              Category rootNode = new Category();
              rootNode.setId(root.getId());
              rootNode.setName(root.getName());
              categoryList.add(rootNode);
              break;
          } else {
              categoryList.add(category);
              categoryId = category.getParentId();
          }
      }
      return categoryList;
  }

  @Override
  public JSONObject saveArtCategory2(String categoryIds, String categoryNames, String articleId, List<Category> list) {
    JSONObject jsonObj = new JSONObject();  
    for (Category category : list) {
          String cId = category.getId();
          if (categoryIds != null && !"".equals(categoryIds)) {
              if (categoryIds.indexOf(cId) != -1) {
              } else {
                  categoryIds += ","+ cId;
                  categoryNames += ","+ category.getName();
              }
          } else {
              categoryIds = cId;
              categoryNames = category.getName();
          }
          
      }
      jsonObj.put("categoryIds", categoryIds);
      jsonObj.put("categoryNames", categoryNames);
      return jsonObj;
  }

  @Override
  public JSONObject cancelArtCategory(String categoryIds, String categoryNames, String articleId, String categoryId) {
      JSONObject jsonObj = new JSONObject();  
      String[] categoryIdArry = categoryIds.split(",");
      //去除取消选中的节点
      ArrayList<String> listIds=new ArrayList<String>();
      ArrayList<String> listNames=new ArrayList<String>();
      for (String strId : categoryIdArry) {
          if (!strId.trim().equals(categoryId)) {
              listIds.add(strId.trim());
              Category category = categoryMapper.findById(strId);
              if (category !=null) {
                  listNames.add(category.getName());
              } else {
                  DictionaryData dd = DictionaryDataUtil.findById(strId);
                  if (dd != null) {
                      listNames.add(dd.getName());
                  }
              }
          }
      }
      categoryIds = listIds.toString().substring(1, listIds.toString().length()-1);
      categoryNames = listNames.toString().substring(1, listNames.toString().length()-1);
      while(true) {
          Category category = null;
          category = categoryMapper.findById(categoryId);
          if(category == null) {
              //查询子节点是否被选中
              List<Category> categories = categoryMapper.findByParentId(categoryId);
              int isExist = 0;
              for (Category category2 : categories) {
                  if (categoryIds.indexOf(category2.getId()) != -1) {
                      isExist += 1;
                  }
              }
              if (isExist == 0) {
                  String[] categoryIdArry2 = categoryIds.split(",");
                  //去除取消选中的节点
                  ArrayList<String> listIds2= new ArrayList<String>();
                  ArrayList<String> listNames2=new ArrayList<String>();
                  for (String strId : categoryIdArry2) {
                      if (!strId.trim().equals(categoryId)) {
                          listIds2.add(strId.trim());
                          Category category2 = categoryMapper.findById(strId.trim());
                          if (category2 !=null) {
                              listNames2.add(category2.getName());
                          } else {
                            DictionaryData dd = DictionaryDataUtil.findById(strId.trim());
                            if (dd != null) {
                                listNames2.add(dd.getName());
                            }
                          }
                      }
                  }
                  categoryIds = listIds2.toString().substring(1, listIds2.toString().length()-1);
                  categoryNames = listNames2.toString().substring(1, listNames2.toString().length()-1);
              }
              break;
          } else {
              //查询子节点是否被选中
              List<Category> categories = categoryMapper.findByParentId(categoryId);
              int isExist = 0;
              for (Category category2 : categories) {
                  if (categoryIds.indexOf(category2.getId()) != -1) {
                      //有子节点被选中
                      isExist += 1;
                  }
              }
              if (isExist == 0) {
                  String[] categoryIdArry2 = categoryIds.split(",");
                  //去除取消选中的节点
                  ArrayList<String> listIds2= new ArrayList<String>();
                  ArrayList<String> listNames2=new ArrayList<String>();
                  for (String strId : categoryIdArry2) {
                      //剔除该父节点
                      if (!strId.trim().equals(categoryId)) {
                          listIds2.add(strId.trim());
                          Category category2 = categoryMapper.findById(strId.trim());
                          if (category2 !=null) {
                              listNames2.add(category2.getName());
                          } else {
                            DictionaryData dd = DictionaryDataUtil.findById(strId.trim());
                            if (dd != null) {
                                listNames2.add(dd.getName());
                            }
                          }
                      }
                  }
                  categoryIds = listIds2.toString().substring(1, listIds2.toString().length()-1);
                  categoryNames = listNames2.toString().substring(1, listNames2.toString().length()-1);
              }
              categoryId = category.getParentId();
          }
      }
      jsonObj.put("categoryIds", categoryIds);
      jsonObj.put("categoryNames", categoryNames);
      return jsonObj;
  }
  	
  		
  /**
	 * 
	* @Title: selectAllArticleByCondition 
	* @Description: 条件查询所有文章
	* @author Easong
	* @param @param map
	* @param @return    设定文件 
	* @return List<Article>    返回类型 
	* @throws
	 */
	@Override
	public List<Article> selectAllArticleByCondition(Map<String, Object> map) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage((Integer) (map.get("page")),
				Integer.parseInt(config.getString("pageSize")));
		return articleMapper.selectAllArticleByCondition(map);
	}

  @Override
  public void searchCategory(List<CategoryTree> jList, String name, String rootCode) {
      if (rootCode != null && !"".equals(rootCode)) {
          DictionaryData dictionaryData = DictionaryDataUtil.get(rootCode);
          if (dictionaryData != null) {
              //一级
              CategoryTree ct=new CategoryTree();
              ct.setId(dictionaryData.getId());
              ct.setName(dictionaryData.getName());
              ct.setIsParent("true");
              ct.setClassify(dictionaryData.getCode());
              jList.add(ct);
              //二级
              HashMap<String, Object> map = new HashMap<String, Object>();
              map.put("name", name);
              map.put("isPublish", 0);
              map.put("parentId", dictionaryData.getId());
              List<Category> Categorys = categoryMapper.listByKeywordIsPublish(map);
              if (Categorys != null && Categorys.size() > 0) {
                  for (Category category : Categorys) {
                      CategoryTree ct2=new CategoryTree();
                      ct2.setId(category.getId());
                      ct2.setName(category.getName());
                      ct2.setIsParent("true");
                      ct2.setParentId(dictionaryData.getId());
                      jList.add(ct2);
                      //三级
                      HashMap<String, Object> map2 = new HashMap<String, Object>();
                      map2.put("name", name);
                      map2.put("isPublish", 0);
                      map2.put("parentId", category.getId());
                      List<Category> Categorys2 = categoryMapper.listByKeywordIsPublish(map2);
                      for (Category category2 : Categorys2) {
                          CategoryTree ct3=new CategoryTree();
                          ct3.setId(category2.getId());
                          ct3.setName(category2.getName());
                          ct3.setIsParent("false");
                          jList.add(ct3);
                      }
                  }
              } else {
                  List<Category> twoCategories = categoryMapper.findByParentId(dictionaryData.getId());
                  for (Category category : twoCategories) {
                      //三级
                      HashMap<String, Object> map2 = new HashMap<String, Object>();
                      map2.put("name", name);
                      map2.put("isPublish", 0);
                      map2.put("parentId", category.getId());
                      List<Category> Categorys2 = categoryMapper.listByKeywordIsPublish(map2);
                      if (Categorys2 != null && Categorys2.size() > 0) {
                            CategoryTree pct3=new CategoryTree();
                            pct3.setId(category.getId());
                            pct3.setName(category.getName());
                            pct3.setIsParent("true");
                            pct3.setParentId(dictionaryData.getId());
                            jList.add(pct3);
                            for (Category category2 : Categorys2) {
                                CategoryTree ct3=new CategoryTree();
                                ct3.setId(category2.getId());
                                ct3.setName(category2.getName());
                                ct3.setIsParent("false");
                                ct3.setParentId(category2.getParentId());
                                jList.add(ct3);
                            }
                      }
                  }
              }
          }
      }else {
        List<DictionaryData> dictionaryDatas = DictionaryDataUtil.find(6);
        if (dictionaryDatas != null && dictionaryDatas.size() > 0) {
            for (DictionaryData dictionaryData : dictionaryDatas) {
              //一级
              CategoryTree ct=new CategoryTree();
              ct.setId(dictionaryData.getId());
              ct.setName(dictionaryData.getName());
              ct.setIsParent("true");
              ct.setClassify(dictionaryData.getCode());
              jList.add(ct);
              //二级
              HashMap<String, Object> map = new HashMap<String, Object>();
              map.put("name", name);
              map.put("isPublish", 0);
              map.put("parentId", dictionaryData.getId());
              List<Category> Categorys = categoryMapper.listByKeywordIsPublish(map);
              if (Categorys != null && Categorys.size() > 0) {
                for (Category category : Categorys) {
                  CategoryTree ct2=new CategoryTree();
                  ct2.setId(category.getId());
                  ct2.setName(category.getName());
                  ct2.setIsParent("true");
                  ct2.setParentId(dictionaryData.getId());
                  jList.add(ct2);
                  //三级
                  HashMap<String, Object> map2 = new HashMap<String, Object>();
                  map2.put("name", name);
                  map2.put("isPublish", 0);
                  map2.put("parentId", category.getId());
                  List<Category> Categorys2 = categoryMapper.listByKeywordIsPublish(map2);
                  for (Category category2 : Categorys2) {
                    CategoryTree ct3=new CategoryTree();
                    ct3.setId(category2.getId());
                    ct3.setName(category2.getName());
                    ct3.setIsParent("false");
                    jList.add(ct3);
                  }
                }
              } else {
                List<Category> twoCategories = categoryMapper.findByParentId(dictionaryData.getId());
                for (Category category : twoCategories) {
                  //三级
                  HashMap<String, Object> map2 = new HashMap<String, Object>();
                  map2.put("name", name);
                  map2.put("isPublish", 0);
                  map2.put("parentId", category.getId());
                  List<Category> Categorys2 = categoryMapper.listByKeywordIsPublish(map2);
                  if (Categorys2 != null && Categorys2.size() > 0) {
                    CategoryTree pct3=new CategoryTree();
                    pct3.setId(category.getId());
                    pct3.setName(category.getName());
                    pct3.setIsParent("true");
                    pct3.setParentId(dictionaryData.getId());
                    jList.add(pct3);
                    for (Category category2 : Categorys2) {
                      CategoryTree ct3=new CategoryTree();
                      ct3.setId(category2.getId());
                      ct3.setName(category2.getName());
                      ct3.setIsParent("false");
                      ct3.setParentId(category2.getParentId());
                      jList.add(ct3);
                    }
                  }
                }
              }
            }
        }
      }
  }

  //通过产品目录查询文章
  @Override
  public List<Article> findArtByCategory(Map<String, Object> map) {
	  // TODO Auto-generated method stub
	  return articleMapper.findArtByCategory(map);
  }

}
