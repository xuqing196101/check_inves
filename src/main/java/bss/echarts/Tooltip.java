package bss.echarts;

import com.fasterxml.jackson.annotation.JsonRawValue;

/**
* @Title:Tooltip 
* @Description: 
* @author Shen Zhenfei
* @date 2016-10-25上午10:05:21
 */
public class Tooltip {
    private String trigger;
    private boolean show;
    private Integer showDelay;
    private String formatter;
    private AxisPointer axisPointer;
    //此属性返回时值不会加双引号
    //@JsonRawValue
    public String getFormatter() {
        return formatter;
    }

    public void setFormatter(String formatter) {
        this.formatter = formatter;
    }

    public Integer getShowDelay() {
        return showDelay;
    }

    public void setShowDelay(Integer showDelay) {
        this.showDelay = showDelay;
    }

    public String getTrigger() {
        return trigger;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    public AxisPointer getAxisPointer() {
        return axisPointer;
    }

    public void setAxisPointer(AxisPointer axisPointer) {
        this.axisPointer = axisPointer;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }
}
