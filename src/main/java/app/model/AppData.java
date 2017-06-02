package app.model;


import iss.model.ps.Article;

import java.util.List;

/**
 *
 * Description：封装app接口返回的数据
 *
 * @author zhang shubin
 * @version
 * @since JDK1.7
 * @date 2017年6月1日 下午2:10:39
 *
 */
public class AppData {

	// 首页轮播图片url集合
	private List<String> imgUrl;

	// 首页公告信息
	private List<Article> indexMsgList;

	public List<String> getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(List<String> imgUrl) {
		this.imgUrl = imgUrl;
	}

	public List<Article> getIndexMsgList() {
		return indexMsgList;
	}

	public void setIndexMsgList(List<Article> indexMsgList) {
		this.indexMsgList = indexMsgList;
	}

}
