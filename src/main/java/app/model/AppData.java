package app.model;


import iss.model.ps.Article;

import java.util.List;

import ses.model.ems.ExpertPublicity;
import ses.model.sms.SupplierPublicity;

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
    private List<Img> imgUrl;

    // 首页公告信息
    private List<Article> indexMsgList;
    
    //供应商名录
    private List<AppSupplier> supplierList;
    
    //黑名单
    private List<AppBlackList> blackList;
    
    //公告内容
    private String content;
    
    //公告title
    private String title;
    
    //App版本号
    private String version;
    
    //apk下载链接
    private String downloadUrl;
    
    //供应商入库公示
    private List<SupplierPublicity> supplierPublicityList;
    
    //专家入库公示
    private List<ExpertPublicity> expertPublicityList;
    
    public List<SupplierPublicity> getSupplierPublicityList() {
		return supplierPublicityList;
	}

	public void setSupplierPublicityList(
			List<SupplierPublicity> supplierPublicityList) {
		this.supplierPublicityList = supplierPublicityList;
	}

	public List<ExpertPublicity> getExpertPublicityList() {
		return expertPublicityList;
	}

	public void setExpertPublicityList(List<ExpertPublicity> expertPublicityList) {
		this.expertPublicityList = expertPublicityList;
	}

	public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //服务热线
    private List<AppHotLine> hotLineList;
    
    public List<AppHotLine> getHotLineList() {
        return hotLineList;
    }

    public void setHotLineList(List<AppHotLine> hotLineList) {
        this.hotLineList = hotLineList;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<AppBlackList> getBlackList() {
        return blackList;
    }

    public void setBlackList(List<AppBlackList> blackList) {
        this.blackList = blackList;
    }

    public List<Article> getIndexMsgList() {
        return indexMsgList;
    }

    public void setIndexMsgList(List<Article> indexMsgList) {
        this.indexMsgList = indexMsgList;
    }

    public List<AppSupplier> getSupplierList() {
        return supplierList;
    }

    public void setSupplierList(List<AppSupplier> supplierList) {
        this.supplierList = supplierList;
    }

    public List<Img> getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(List<Img> imgUrl) {
        this.imgUrl = imgUrl;
    }
}
