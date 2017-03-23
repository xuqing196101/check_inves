package iss.model.ps;

/**
 * 版权：(C) 版权所有 
 * <简述>信息公告和产品目录关联表
 * <详细描述>
 * @author   Ye MaoLin
 * @version  
 * @since
 * @see
 */
public class ArticleCategory {
  
    /**
     * @Fields articleId : 公告id
     */
    private String articleId;
    
    /**
     * @Fields categoryId : 品目id
     */
    private String categoryId;

    public String getArticleId() {
      return articleId;
    }

    public void setArticleId(String articleId) {
      this.articleId = articleId;
    }

    public String getCategoryId() {
      return categoryId;
    }

    public void setCategoryId(String categoryId) {
      this.categoryId = categoryId;
    }
    
}
