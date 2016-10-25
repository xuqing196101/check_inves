package bss.echarts;

/**
* @Title:Feature 
* @Description: 
* @author Shen Zhenfei
* @date 2016-10-25上午10:03:38
 */
public class Feature {
    private MagicType magicType;
    private DataView dataView;
    private Restore restore;
    private Mark mark;
    private SaveAsImage saveAsImage;
    public MagicType getMagicType() {
        return magicType;
    }
    public void setMagicType(MagicType magicType) {
        this.magicType = magicType;
    }
    public DataView getDataView() {
        return dataView;
    }
    public void setDataView(DataView dataView) {
        this.dataView = dataView;
    }
    public Restore getRestore() {
        return restore;
    }
    public void setRestore(Restore restore) {
        this.restore = restore;
    }
    public Mark getMark() {
        return mark;
    }
    public void setMark(Mark mark) {
        this.mark = mark;
    }
    public SaveAsImage getSaveAsImage() {
        return saveAsImage;
    }
    public void setSaveAsImage(SaveAsImage saveAsImage) {
        this.saveAsImage = saveAsImage;
    }
}
