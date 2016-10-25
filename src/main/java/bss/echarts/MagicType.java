package bss.echarts;

import java.util.List;

/**
* @Title:MagicType 
* @Description: 
* @author Shen Zhenfei
* @date 2016-10-25上午10:04:15
 */
public class MagicType {
    private boolean show;
    private List type;
    private Restore restore;
    
    
    public boolean isShow() {
        return show;
    }
    public void setShow(boolean show) {
        this.show = show;
    }
    public List getType() {
        return type;
    }
    public void setType(List type) {
        this.type = type;
    }
	public Restore getRestore() {
		return restore;
	}
	public void setRestore(Restore restore) {
		this.restore = restore;
	}
    
    
}
