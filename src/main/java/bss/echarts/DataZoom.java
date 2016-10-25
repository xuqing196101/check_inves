package bss.echarts;

/**
* @Title:DataZoom 
* @Description: 
* @author Shen Zhenfei
* @date 2016-10-25上午10:03:35
 */
public class DataZoom {
    private boolean show;

    private boolean realtime;

    private Integer y;

    private Integer height;

    private Integer start;

    private Integer end;

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public boolean isRealtime() {
        return realtime;
    }

    public void setRealtime(boolean realtime) {
        this.realtime = realtime;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }
}
