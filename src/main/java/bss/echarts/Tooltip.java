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
    private boolean showContent;
    private String triggerOn;
    private boolean alwaysShowContent;
    private TextStyle textStyle;
    private String backgroundColor;
    private String borderColor;
    private String extraCssText;
    
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

	public boolean isShowContent() {
		return showContent;
	}

	public void setShowContent(boolean showContent) {
		this.showContent = showContent;
	}

	public String getTriggerOn() {
		return triggerOn;
	}

	public void setTriggerOn(String triggerOn) {
		this.triggerOn = triggerOn;
	}

	public boolean isAlwaysShowContent() {
		return alwaysShowContent;
	}

	public void setAlwaysShowContent(boolean alwaysShowContent) {
		this.alwaysShowContent = alwaysShowContent;
	}

	
	public TextStyle getTextStyle() {
		return textStyle;
	}

	public void setTextStyle(TextStyle textStyle) {
		this.textStyle = textStyle;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public String getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}

	public String getExtraCssText() {
		return extraCssText;
	}

	public void setExtraCssText(String extraCssText) {
		this.extraCssText = extraCssText;
	}
    
}
