package iss.service.ps.impl;

import iss.dao.ps.ArticleMapper;
import iss.model.ps.Article;
import iss.service.ps.ArticleService;

import java.util.HashMap;
import java.util.List;

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
	public List<Article> selectArticleByStatus(Article article,Integer pageNum) {
		PropertiesUtil config = new PropertiesUtil("config.properties");
		PageHelper.startPage(pageNum,Integer.parseInt(config.getString("pageSize")));
		List<Article> list = articleMapper.selectArticleByStatus(article);
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
        sb.append("<p style=text-align: center;><strong>测试项目不要删</strong></p><p><br/>");
        for (Packages pack : listPackages) {
            sb.append("<strong>包名</strong><strong>：</strong>"+pack.getName()+"</p>");
            sb.append("<table><tbody><tr class=firstRow>");
            sb.append("<td width=59 valign=middle align=center><strong>序号<br/></strong></td>");   
            sb.append("<td style=word-break: break-all; width=59 valign=middle align=center><p><strong>需求</strong></p><p><strong>部门</strong></p></td>");
            sb.append("<td style=word-break: break-all; width=59 valign=middle align=center><p><strong>物资</strong></p><p><strong>名称</strong></p></td>");
            sb.append("<td style=word-break: break-all; width=59 valign=middle align=center><p><strong>规格</strong></p><p><strong>型号</strong></p></td>");
            sb.append("<td width=59 valign=middle align=center><strong>质量技术标准<br/></strong></td>");
            sb.append("<td style=word-break: break-all; width=59 valign=middle align=center><p><strong>计量</strong></p><p><strong>单位</strong></p></td>");
            sb.append("<td style=word-break: break-all; width=59 valign=middle align=center><p><strong>采购</strong></p><p><strong>数量</strong></p></td>");
            sb.append("<td style=word-break: break-all; width=59 valign=middle align=center><p><strong>单价</strong></p><p><strong>(元)<br/></strong></p></td>");
            sb.append("<td style=word-break: break-all; width=59 valign=middle align=center><p><strong>预算金额</strong></p><p><strong>(万元)</strong></p></td>");
            sb.append("<td colspan=1 rowspan=1 style=word-break: break-all; width=59 valign=middle align=center><p><strong>交货</strong></p><p><strong>期限</strong></p></td>");
            sb.append("<td colspan=1 rowspan=1 style=word-break: break-all; width=59 valign=middle align=center><strong>采购方式建议<br/></strong></td>");
            sb.append("<td colspan=1 rowspan=1 width=59 valign=middle align=center><strong>供应商名称<br/></strong></td>");
            sb.append("<td width=59 valign=middle align=center><strong>备注<br/></strong></td></tr>");
            for (ProjectDetail pd : pack.getProjectDetails()) {
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
                sb.append("<tr><td width=59 valign=top><br/>"+pd.getSerialNumber()+"</td>");
                sb.append("<td width=59 valign=top><br/>"+pd.getDepartment()+"</td>");
                sb.append("<td width=59 valign=top><br/>"+pd.getGoodsName()+"</td>");
                sb.append("<td width=59 valign=top><br/>"+pd.getStand()+"</td>");
                sb.append("<td width=59 valign=top><br/>"+pd.getQualitStand()+"</td>");
                sb.append("<td width=59 valign=top><br/>"+pd.getItem()+"</td>");
                sb.append("<td width=59 valign=top><br/>"+purchaseCount+"</td>");
                sb.append("<td width=59 valign=top><br/>"+price+"</td>");
                sb.append("<td width=59 valign=top><br/>"+budget+"</td>");
                sb.append("<td width=59 valign=top><br/>"+pd.getDeliverDate()+"</td>");
                for (DictionaryData dd : DictionaryDataUtil.find(5)) {
                    if (dd.getId() == pack.getPurchaseType()) {
                        sb.append("<td colspan=1 rowspan=1 width=59 valign=top><br/>"+dd.getName()+"</td>");
                    }
                }
                sb.append("<td colspan=1 rowspan=1 width=59 valign=top><br/></td>");
                sb.append("<td colspan=1 rowspan=1 width=59 valign=top><br/></td>");
                sb.append("<td width=59 valign=top><br/></td>");
                sb.append("</tr>");
            }
            sb.append("</tbody></table><p><br/><br/></p>");
        }
        return sb;
    }
	
}
