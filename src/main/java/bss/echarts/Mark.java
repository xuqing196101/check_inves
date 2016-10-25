package bss.echarts;

/**
* @Title:Mark 
* @Description: 
* @author Shen Zhenfei
* @date 2016-10-25上午10:04:19
 */
public class Mark {
    private boolean show;
    private DataView DataView;
    private SaveAsImage saveAsImage;
    

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

	public DataView getDataView() {
		return DataView;
	}

	public void setDataView(DataView dataView) {
		DataView = dataView;
	}

	public SaveAsImage getSaveAsImage() {
		return saveAsImage;
	}

	public void setSaveAsImage(SaveAsImage saveAsImage) {
		this.saveAsImage = saveAsImage;
	}
    
    
}
