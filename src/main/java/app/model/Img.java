package app.model;

/**
 * 
 * Description: App首页轮播图
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
public class Img {

    //图片id
    private String id;
    
    //图片路径
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Img(String id, String url) {
        super();
        this.id = id;
        this.url = url;
    }

    public Img() {
        super();
    }
}
