package bss.echarts;

import java.util.List;

/**
* @Title:Series 
* @Description: 
* @author Shen Zhenfei
* @date 2016-10-25上午10:04:52
 */
public class Series {
    private String name;

    private String type;
    
    private boolean showAllSymbol;
    
    private String symbolSize;
    
    private Integer yAxisIndex;
    
    private ItemStyle itemStyle;
    
    private MarkPoint markPoint;
    
    private MarkLine markLine;

    private List data;
    
    private Tooltip tooltip;
    
    private Integer barWidth;
    
    private String symbol;
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }

    public ItemStyle getItemStyle() {
        return itemStyle;
    }

    public void setItemStyle(ItemStyle itemStyle) {
        this.itemStyle = itemStyle;
    }

    public boolean isShowAllSymbol() {
        return showAllSymbol;
    }

    public void setShowAllSymbol(boolean showAllSymbol) {
        this.showAllSymbol = showAllSymbol;
    }

    //@JsonRawValue
    public String getSymbolSize() {
        return symbolSize;
    }

    public void setSymbolSize(String symbolSize) {
        this.symbolSize = symbolSize;
    }

    public Integer getyAxisIndex() {
        return yAxisIndex;
    }

    public void setyAxisIndex(Integer yAxisIndex) {
        this.yAxisIndex = yAxisIndex;
    }

	public MarkPoint getMarkPoint() {
		return markPoint;
	}

	public void setMarkPoint(MarkPoint markPoint) {
		this.markPoint = markPoint;
	}

	public MarkLine getMarkLine() {
		return markLine;
	}

	public void setMarkLine(MarkLine markLine) {
		this.markLine = markLine;
	}

	public Tooltip getTooltip() {
		return tooltip;
	}

	public void setTooltip(Tooltip tooltip) {
		this.tooltip = tooltip;
	}

	public Integer getBarWidth() {
		return barWidth;
	}

	public void setBarWidth(Integer barWidth) {
		this.barWidth = barWidth;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
    
}
