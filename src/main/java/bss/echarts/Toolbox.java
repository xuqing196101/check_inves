package bss.echarts;

/**
* @Title:Toolbox 
* @Description: 
* @author Shen Zhenfei
* @date 2016-10-25上午10:05:15
 */
public class Toolbox {
    private boolean show;
    private Feature feature;
    private String orient;
    private String x;
    private String y;
    public boolean isShow() {
        return show;
    }
    public void setShow(boolean show) {
        this.show = show;
    }
    public Feature getFeature() {
        return feature;
    }
    public void setFeature(Feature feature) {
        this.feature = feature;
    }
    public String getOrient() {
        return orient;
    }
    public void setOrient(String orient) {
        this.orient = orient;
    }
    public String getX() {
        return x;
    }
    public void setX(String x) {
        this.x = x;
    }
    public String getY() {
        return y;
    }
    public void setY(String y) {
        this.y = y;
    }
}
