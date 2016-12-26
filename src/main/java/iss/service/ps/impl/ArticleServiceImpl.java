package iss.service.ps.impl;

import iss.dao.ps.ArticleMapper;
import iss.model.ps.Article;
import iss.service.ps.ArticleService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bss.model.ppms.Packages;
import bss.model.ppms.ProjectDetail;

import com.github.pagehelper.PageHelper;

import ses.model.bms.DictionaryData;
import ses.util.DictionaryDataUtil;
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
		PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
		return articleMapper.selectAllArticle(article);
	}
	
	/**
	 * 修改信息
	 */
	@Override
	public void update(Article article) {
		articleMapper.update(article);
	}

	@Override
	public void delArticleById(String id) {
		articleMapper.deleteByPrimaryKey(id);
	}
	
	/**
	 * 根据id查询信息
	 */
	@Override
	public Article selectArticleById(String id) {
		return articleMapper.selectById(id);
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
        sb.append("<table><tr><th>包名</th><th>货物名称</th><th>规格型号</th><th>技术要求</th><th>计量单位</th><th>数量</th><th>交货时间</th><th>交货地点</th><th>备注</th></tr>");
        for (Packages pack : listPackages) {
            for (ProjectDetail pd : pack.getProjectDetails()) {
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
}
