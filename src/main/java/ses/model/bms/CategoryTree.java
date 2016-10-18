package ses.model.bms;

public class CategoryTree {
	/*
	 * 节点的id
	 */
	private String id;
	
	/*
	 * 节点的名称
	 */
	private String name;
	
	private String kind;
	/*
	 * 节点的父节点id
	 */
	private String pId;
	
	/*
	 * 是否是父类
	 */
	private String isParent;
	
	private Integer isEnd;
	
	public Integer getIsEnd() {
		return isEnd;
	}
	public void setIsEnd(Integer isEnd) {
		this.isEnd = isEnd;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
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
	

	
	
	
	}
	

