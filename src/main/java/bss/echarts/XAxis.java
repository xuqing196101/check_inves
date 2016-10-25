package bss.echarts;

import java.util.List;

/**
* @Title:XAxis 
* @Description: 
* @author Shen Zhenfei
* @date 2016-10-25上午10:05:26
 */
public class XAxis {
	private String type;
	private Integer power;
	private Object min;
	private Object max;
	private Integer splitNumber;
	private Integer precision;
	private boolean scale;
	private Object boundaryGap;
	private List data;
	private SplitLine splitLine;
	
	public Integer getPower() {
		return power;
	}

	public void setPower(Integer power) {
		this.power = power;
	}

	public Integer getPrecision() {
		return precision;
	}

	public void setPrecision(Integer precision) {
		this.precision = precision;
	}

	public boolean isScale() {
		return scale;
	}

	public void setScale(boolean scale) {
		this.scale = scale;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Object getBoundaryGap() {
		return boundaryGap;
	}

	public void setBoundaryGap(Object boundaryGap) {
		this.boundaryGap = boundaryGap;
	}

	public List getData() {
		return data;
	}

	public void setData(List data) {
		this.data = data;
	}

	public Integer getSplitNumber() {
		return splitNumber;
	}

	public void setSplitNumber(Integer splitNumber) {
		this.splitNumber = splitNumber;
	}

	public Object getMin() {
		return min;
	}

	public void setMin(Object min) {
		this.min = min;
	}

	public Object getMax() {
		return max;
	}

	public void setMax(Object max) {
		this.max = max;
	}

	public SplitLine getSplitLine() {
		return splitLine;
	}

	public void setSplitLine(SplitLine splitLine) {
		this.splitLine = splitLine;
	}
	
	
}
