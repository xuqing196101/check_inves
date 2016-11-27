package ses.model.oms.util;


import java.io.Serializable;
import java.util.Date;
import java.util.List;
/**
 * 
* <p>Title:Ztree </p>
* <p>Description: </p>
* <p>Company: ses </p> 
* @author tkf
* @date 2016-9-7下午2:57:46
 */
public class Ztree implements Serializable {

	/**
	 * @Fields serialVersionUID : 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 节点的id
	 */
	private String id;
	
	/**
	 * 节点的名称
	 */
	private String name;
	
	/**
	 * 节点的父节点id
	 */
	private String pId;
	
	/**
	 * 是否是父类
	 */
	private String isParent;
	
	private String level;
	private String menuurl;
	private List<String> priList;
	
	/**
	 * @Fields checked : TODO(目的和意义)是否被选中
	 */
	private boolean checked = false;
	/**
	 * @Fields nocheck : TODO(目的和意义)是否可被选择
	 */
	private boolean nocheck;
	private boolean open;
	private Date createdAt;
	private String bidMethodId;
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	
	public String getMenuurl() {
		return menuurl;
	}
	public void setMenuurl(String menuurl) {
		this.menuurl = menuurl;
	}
	public List<String> getPriList() {
		return priList;
	}
	public void setPriList(List<String> priList) {
		this.priList = priList;
	}
	public String getIsParent() {
		return isParent;
	}
	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public Ztree(String id, String name, String pId, String isParent) {
		super();
		this.id = id;
		this.name = name;
		this.pId = pId;
		this.isParent = isParent;
	}
	public Ztree() {
		super();
	}
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getBidMethodId() {
		return bidMethodId;
	}
	public void setBidMethodId(String bidMethodId) {
		this.bidMethodId = bidMethodId;
	}
    public boolean isNocheck() {
        return nocheck;
    }
    public void setNocheck(boolean nocheck) {
        this.nocheck = nocheck;
    }
	
}
