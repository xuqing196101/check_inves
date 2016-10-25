package bss.echarts;

import java.util.ArrayList;
import java.util.List;

/**
* @Title:Legend 
* @Description: 
* @author Shen Zhenfei
* @date 2016-10-25上午10:03:58
 */
public class Legend {
    private List data = new ArrayList();
    private String orient;
    private String x;
    private String y;
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

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }
}
