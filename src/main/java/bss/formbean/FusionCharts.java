package bss.formbean;

import java.io.Serializable;
import java.util.List;

/**
 * @Title: FusionCharts
 * @Description: fusioncharts 类,
 * @Company: yggc 
 * @author: Poppet_Brook
 * @date: 2016-7-15上午9:59:31
 */
public class FusionCharts implements Serializable {
	private static final long serialVersionUID = -1875334261038528917L;
	
	private Chart chart;
	private List<Data> data;

	public Chart getChart() {
		return chart;
	}

	public void setChart(Chart chart) {
		this.chart = chart;
	}

	public List<Data> getData() {
		return data;
	}

	public void setData(List<Data> data) {
		this.data = data;
	}
}
