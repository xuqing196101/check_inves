package bss.model.ppms;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @Title: ScoreStandard
 * @Description: 评分标准
 * @author: Tian Kunfeng
 * @date: 2016-10-17下午7:45:13
 */
public class MarkTerm implements Serializable ,java.lang.Comparable{
	/**
	 * @Fields serialVersionUID : 
	 */
	private static final long serialVersionUID = 1L;
	private String id;//
	private String pid;//父id
	private String projectId;//项目id
	private String packageId;//分包id
	private String name;//名称
	private String pname;//父节点名称
	private String url;//
	private String checked;//1:价格分
	private Integer isDeleted;//
	private Date createdAt;//
	private Date updatedAt;//
	private String maxScore;//
	private String remainScore ;//
	private String typeName;
	
	private String isRoot ;
	private String bidMethodId;//
	private String bidMethodName;
	private String bidMethodTypeName;
	private String bidMethodMaxScore;
	private String bidMethodFloatingRatio;
	private String bidMethodRemark;
	private String method;//增删改标志位
	private String rowspan;
	private String smname;
	private Integer smtypename;
	private Double scscore;
	private String checkedPrice;
	//添加作为比较器 treeMap 
	private int judge;
	//scoremodel的id
	private String smId;
 
	private List<MarkTerm> listMarkTerm;//获取模型子集
  
	private ScoreModel scoreModels;//获取模型
	
	private Integer count;
	private Integer position;
	
	public Integer getPosition() {
        return position;
    }
    public void setPosition(Integer position) {
        this.position = position;
    }
    public String getSmId() {
        return smId;
    }
    public void setSmId(String smId) {
        this.smId = smId;
    }
    public int getJudge() {
        return judge;
    }
    public void setJudge(int judge) {
        this.judge = judge;
    }
    public String getSmname() {
        return smname;
    }
    public void setSmname(String smname) {
        this.smname = smname;
    }
    
    public Integer getSmtypename() {
        return smtypename;
    }
    public void setSmtypename(Integer smtypename) {
        this.smtypename = smtypename;
    }
    public Double getScscore() {
        return scscore;
    }
    public void setScscore(Double scscore) {
        this.scscore = scscore;
    }
    public String getRowspan() {
        return rowspan;
    }
    public void setRowspan(String rowspan) {
        this.rowspan = rowspan;
    }
    public MarkTerm() {
		super();
	}
	public MarkTerm(String id, String pid, String name, String pname) {
		super();
		this.id = id;
		this.pid = pid;
		this.name = name;
		this.pname = pname;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String isChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public Integer getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getIsRoot() {
		return isRoot;
	}
	public void setIsRoot(String isRoot) {
		this.isRoot = isRoot;
	}
	public String getBidMethodId() {
		return bidMethodId;
	}
	public void setBidMethodId(String bidMethodId) {
		this.bidMethodId = bidMethodId;
	}
	public String getBidMethodName() {
		return bidMethodName;
	}
	public void setBidMethodName(String bidMethodName) {
		this.bidMethodName = bidMethodName;
	}
	public String getBidMethodTypeName() {
		return bidMethodTypeName;
	}
	public void setBidMethodTypeName(String bidMethodTypeName) {
		this.bidMethodTypeName = bidMethodTypeName;
	}
	public String getBidMethodMaxScore() {
		return bidMethodMaxScore;
	}
	public void setBidMethodMaxScore(String bidMethodMaxScore) {
		this.bidMethodMaxScore = bidMethodMaxScore;
	}
	public String getBidMethodFloatingRatio() {
		return bidMethodFloatingRatio;
	}
	public void setBidMethodFloatingRatio(String bidMethodFloatingRatio) {
		this.bidMethodFloatingRatio = bidMethodFloatingRatio;
	}
	public String getBidMethodRemark() {
		return bidMethodRemark;
	}
	public void setBidMethodRemark(String bidMethodRemark) {
		this.bidMethodRemark = bidMethodRemark;
	}
	public String getMaxScore() {
		return maxScore;
	}
	public void setMaxScore(String maxScore) {
		this.maxScore = maxScore;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getPackageId() {
		return packageId;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getRemainScore() {
		return remainScore;
	}
	public void setRemainScore(String remainScore) {
		this.remainScore = remainScore;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	 @Override
     public int compareTo(Object o){      // 实现 Comparable 接口的抽象方法，定义排序规则
            MarkTerm mt = (MarkTerm)o;
            return this.getJudge() - mt.getJudge();      
     }
     @Override
     public boolean equals(Object o){     //equals
            boolean flag = false;
            if(o instanceof MarkTerm){
                   if(this.getJudge() == ((MarkTerm)o).getJudge())
                          flag = true;
            }
            return flag;          
     }
    public Integer getCount() {
        return count;
    }
    public void setCount(Integer count) {
        this.count = count;
    }
    /**
     * @return Returns the listMarkTerm.
     */
    public List<MarkTerm> getListMarkTerm() {
      return listMarkTerm;
    }
    /**
     * @param listMarkTerm The listMarkTerm to set.
     */
    public void setListMarkTerm(List<MarkTerm> listMarkTerm) {
      this.listMarkTerm = listMarkTerm;
    }
    /**
     * @return Returns the scoreModels.
     */
    public ScoreModel getScoreModels() {
      return scoreModels;
    }
    /**
     * @param scoreModels The scoreModels to set.
     */
    public void setScoreModels(ScoreModel scoreModels) {
      this.scoreModels = scoreModels;
    }
    public String getCheckedPrice() {
      return checkedPrice;
    }
    public void setCheckedPrice(String checkedPrice) {
      this.checkedPrice = checkedPrice;
    }
    
}
