package dss.model.rids;

import java.io.Serializable;

/**
 * Description:
 * 
 * @author Easong
 * @version 2017年6月12日
 * @since JDK1.7
 */
public class ArticleAnalyzeVo implements Serializable{

	/**
	 * ArticleAnalyzeVo.java
	 */
	private static final long serialVersionUID = 1L;
	/**发布年限**/
	private String publishYear;
	
	/**栏目类型  ：进口、物资、工程、服务**/
	private String threeArticleTypeId;
	
	/**公告所属产品类别ID**/
	private String categoryId;
	
	/**采购方式**/
	private String fourArticleTypeId;

	public String getPublishYear() {
		return publishYear;
	}

	public void setPublishYear(String publishYear) {
		this.publishYear = publishYear;
	}

	public String getThreeArticleTypeId() {
		return threeArticleTypeId;
	}

	public void setThreeArticleTypeId(String threeArticleTypeId) {
		this.threeArticleTypeId = threeArticleTypeId;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getFourArticleTypeId() {
		return fourArticleTypeId;
	}

	public void setFourArticleTypeId(String fourArticleTypeId) {
		this.fourArticleTypeId = fourArticleTypeId;
	}
	
}
