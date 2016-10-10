package bss.formbean;

import java.io.Serializable;

/**
 * 
 * @Title: Chart
 * @Description: 设置图形属性
 * @author Li Xiaoxiao
 * @date  2016年9月27日,上午9:41:22
 *
 */
public class Chart implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/** 图表的主标题 */
	private String caption;
	
	/** 图表的副标题 */
	private String subCaption;
	
	private String palette;
	
	/** 是否格式化数字,默认为1(True),自动的给你的数字加上K（千）或M（百万）；若取0,则不加K或M */
	private String formatnumberscale;
	
	/** 画布边框透明度，默认为1显示 */
	private String showborder;
	
	/** 横向坐标轴(x轴)名称 */
	private String xaxisName;
	
	/** 纵向坐标轴(y轴)名称 */
	private String yaxisName;
	
	/** 增加数字后缀 */
	private String numberSuffix;
	
	/** 图表背景色，可以使用逗号分割多个颜色值 6位16进制颜色值 */
	private String bgcolor;
	
	/** 图表字体颜色，6位16进制颜色值 */
	private String basefontcolor;
	
	/** 画布边框颜色，6位16进制颜色值 */
	private String canvasbordercolor;
	
	/** 水平分区线颜色，6位16进制颜色值 */
	private String divlinecolor;
	
	/** 横向网格带交替的颜色，6位16进制颜色值 */
	private String alternatehgridcolor;
	
	/** 是否在横向网格带交替的颜色，默认为0(False) */
	private String showalternatehgridcolor;
	
	/** 图表字体大小 */
	private String baseFontSize;
	
	/** 图表字体样式，如“宋体”、“黑体” */
	private String baseFont;
	
	/** 线条颜色 */
	private String linecolor;
	
	private String palettecolors;
	
	private String theme;
	
	private String decimalPrecision;
	
	private String plottooltext;
	
	/** 左边距 */
	private String chartLeftMargin = "50";
	
	/** 右边距 */
	private String chartRightMargin = "50";
	
	/** 上边距 */
	private String chartTopMargin = "50";
	
	/** 下边距 */
	private String chartBottomMargin = "50";

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getSubCaption() {
		return subCaption;
	}

	public void setSubCaption(String subCaption) {
		this.subCaption = subCaption;
	}

	public String getFormatnumberscale() {
		return formatnumberscale;
	}

	public void setFormatnumberscale(String formatnumberscale) {
		this.formatnumberscale = formatnumberscale;
	}

	public String getShowborder() {
		return showborder;
	}

	public void setShowborder(String showborder) {
		this.showborder = showborder;
	}

	public String getXaxisName() {
		return xaxisName;
	}

	public void setXaxisName(String xaxisName) {
		this.xaxisName = xaxisName;
	}

	public String getYaxisName() {
		return yaxisName;
	}

	public void setYaxisName(String yaxisName) {
		this.yaxisName = yaxisName;
	}

	public String getNumberSuffix() {
		return numberSuffix;
	}

	public void setNumberSuffix(String numberSuffix) {
		this.numberSuffix = numberSuffix;
	}

	public String getBgcolor() {
		return bgcolor;
	}

	public void setBgcolor(String bgcolor) {
		this.bgcolor = bgcolor;
	}

	public String getBasefontcolor() {
		return basefontcolor;
	}

	public void setBasefontcolor(String basefontcolor) {
		this.basefontcolor = basefontcolor;
	}

	public String getCanvasbordercolor() {
		return canvasbordercolor;
	}

	public void setCanvasbordercolor(String canvasbordercolor) {
		this.canvasbordercolor = canvasbordercolor;
	}

	public String getDivlinecolor() {
		return divlinecolor;
	}

	public void setDivlinecolor(String divlinecolor) {
		this.divlinecolor = divlinecolor;
	}

	public String getAlternatehgridcolor() {
		return alternatehgridcolor;
	}

	public void setAlternatehgridcolor(String alternatehgridcolor) {
		this.alternatehgridcolor = alternatehgridcolor;
	}

	public String getShowalternatehgridcolor() {
		return showalternatehgridcolor;
	}

	public void setShowalternatehgridcolor(String showalternatehgridcolor) {
		this.showalternatehgridcolor = showalternatehgridcolor;
	}

	public String getBaseFontSize() {
		return baseFontSize;
	}

	public void setBaseFontSize(String baseFontSize) {
		this.baseFontSize = baseFontSize;
	}

	public String getBaseFont() {
		return baseFont;
	}

	public void setBaseFont(String baseFont) {
		this.baseFont = baseFont;
	}

	public String getLinecolor() {
		return linecolor;
	}

	public void setLinecolor(String linecolor) {
		this.linecolor = linecolor;
	}

	public String getPalettecolors() {
		return palettecolors;
	}

	public void setPalettecolors(String palettecolors) {
		this.palettecolors = palettecolors;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getDecimalPrecision() {
		return decimalPrecision;
	}

	public void setDecimalPrecision(String decimalPrecision) {
		this.decimalPrecision = decimalPrecision;
	}

	public String getPlottooltext() {
		return plottooltext;
	}

	public void setPlottooltext(String plottooltext) {
		this.plottooltext = plottooltext;
	}

	public String getChartLeftMargin() {
		return chartLeftMargin;
	}

	public void setChartLeftMargin(String chartLeftMargin) {
		this.chartLeftMargin = chartLeftMargin;
	}

	public String getChartRightMargin() {
		return chartRightMargin;
	}

	public void setChartRightMargin(String chartRightMargin) {
		this.chartRightMargin = chartRightMargin;
	}

	public String getChartTopMargin() {
		return chartTopMargin;
	}

	public void setChartTopMargin(String chartTopMargin) {
		this.chartTopMargin = chartTopMargin;
	}

	public String getChartBottomMargin() {
		return chartBottomMargin;
	}

	public void setChartBottomMargin(String chartBottomMargin) {
		this.chartBottomMargin = chartBottomMargin;
	}

	public String getPalette() {
		return palette;
	}

	public void setPalette(String palette) {
		this.palette = palette;
	}
	
}
