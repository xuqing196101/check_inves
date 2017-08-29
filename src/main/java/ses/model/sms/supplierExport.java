package ses.model.sms;

import java.io.Serializable;

public class supplierExport implements Serializable {

	private String id;//机构id
	private String name;//机构名称
	private String shortName;//机构简称
	private String depId;//关联表id
	private Integer reg;//注册人数
	private Integer statusOne;//供应商:待审核人数/专家:待审核人数
	private Integer statusTwo;//供应商:已审核人数/专家:初审合格
	private Integer statusThree;//供应商:退回修改/专家:退回修改
	private Integer statusFour;//供应商:审核不通过/专家:初审不合格
	private Integer statusFive;//供应商:公示中/专家:公示中
	private Integer statusSix;//供应商:复核不通过/专家:复审不合格
	private Integer statusSeven;//供应商:复核通过/专家:审核通过
	private Integer statusEight;//供应商:考察不合格/专家:复察不合格
	private Integer statusNine;//供应商:考察合格/专家:复察合格
	private Integer formalMr;//供应商:已入库物资生产/专家:已入库物资技术
	private Integer formalMs;//供应商:已入库物资销售/专家:已入库物资服务经济
	private Integer formalPr;//供应商:已入库工程/专家:已入库工程技术
	private Integer formalSe;//供应商:已入库服务/专家:已入库服务技术
	private Integer formalPs;//专家:已入库工程经济
	private Integer checkMr;//供应商:复核物资生产/专家:初审物资技术
	private Integer checkMs;//供应商:复核物资销售/专家:初审物资服务经济
	private Integer checkPr;//供应商:复核工程/专家:初审工程技术
	private Integer checkSe;//供应商:复核服务/专家:初审服务技术
	private Integer checkPs;//专家:初审工程经济
	private Integer inspectMr;//供应商:考察合格生产/专家:复查物资技术
  private Integer inspectMs;//供应商:考察合格销售/专家:复查物资服务经济
  private Integer inspectPr;//供应商:考察合格工程/专家:复查工程技术
  private Integer inspectSe;//供应商:考察合格服务/专家:复查服务技术
  private Integer inspectPs;//专家:复查工程经济
	private Integer expertArmy;//军队专家
	private Integer expertsLocal;//地方专家
	private Integer sums;//总数
	private Integer formalSum;
	private Integer checkSum;
	private Integer inspectsum;
	private Integer total;//合计
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
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getDepId() {
		return depId;
	}
	public void setDepId(String depId) {
		this.depId = depId;
	}
	public Integer getReg() {
		return reg;
	}
	public void setReg(Integer reg) {
		this.reg = reg;
	}
	public Integer getStatusOne() {
		return statusOne;
	}
	public void setStatusOne(Integer statusOne) {
		this.statusOne = statusOne;
	}
	public Integer getStatusTwo() {
		return statusTwo;
	}
	public void setStatusTwo(Integer statusTwo) {
		this.statusTwo = statusTwo;
	}
	public Integer getStatusThree() {
		return statusThree;
	}
	public void setStatusThree(Integer statusThree) {
		this.statusThree = statusThree;
	}
	public Integer getStatusFour() {
		return statusFour;
	}
	public void setStatusFour(Integer statusFour) {
		this.statusFour = statusFour;
	}
	public Integer getExpertArmy() {
		return expertArmy;
	}
	public void setExpertArmy(Integer expertArmy) {
		this.expertArmy = expertArmy;
	}
	public Integer getExpertsLocal() {
		return expertsLocal;
	}
	public void setExpertsLocal(Integer expertsLocal) {
		this.expertsLocal = expertsLocal;
	}
  public Integer getSums() {
    return sums;
  }
  public void setSums(Integer sums) {
    this.sums = sums;
  }
  public Integer getTotal() {
    return total;
  }
  public void setTotal(Integer total) {
    this.total = total;
  }
  public Integer getStatusFive() {
    return statusFive;
  }
  public void setStatusFive(Integer statusFive) {
    this.statusFive = statusFive;
  }
  public Integer getStatusSix() {
    return statusSix;
  }
  public void setStatusSix(Integer statusSix) {
    this.statusSix = statusSix;
  }
  public Integer getStatusSeven() {
    return statusSeven;
  }
  public void setStatusSeven(Integer statusSeven) {
    this.statusSeven = statusSeven;
  }
  public Integer getStatusEight() {
    return statusEight;
  }
  public void setStatusEight(Integer statusEight) {
    this.statusEight = statusEight;
  }
  public Integer getStatusNine() {
    return statusNine;
  }
  public void setStatusNine(Integer statusNine) {
    this.statusNine = statusNine;
  }
  public Integer getFormalMr() {
    return formalMr;
  }
  public void setFormalMr(Integer formalMr) {
    this.formalMr = formalMr;
  }
  public Integer getFormalMs() {
    return formalMs;
  }
  public void setFormalMs(Integer formalMs) {
    this.formalMs = formalMs;
  }
  public Integer getFormalPr() {
    return formalPr;
  }
  public void setFormalPr(Integer formalPr) {
    this.formalPr = formalPr;
  }
  public Integer getFormalSe() {
    return formalSe;
  }
  public void setFormalSe(Integer formalSe) {
    this.formalSe = formalSe;
  }
  public Integer getCheckMr() {
    return checkMr;
  }
  public void setCheckMr(Integer checkMr) {
    this.checkMr = checkMr;
  }
  public Integer getCheckMs() {
    return checkMs;
  }
  public void setCheckMs(Integer checkMs) {
    this.checkMs = checkMs;
  }
  public Integer getCheckPr() {
    return checkPr;
  }
  public void setCheckPr(Integer checkPr) {
    this.checkPr = checkPr;
  }
  public Integer getCheckSe() {
    return checkSe;
  }
  public void setCheckSe(Integer checkSe) {
    this.checkSe = checkSe;
  }
  public Integer getInspectMr() {
    return inspectMr;
  }
  public void setInspectMr(Integer inspectMr) {
    this.inspectMr = inspectMr;
  }
  public Integer getInspectMs() {
    return inspectMs;
  }
  public void setInspectMs(Integer inspectMs) {
    this.inspectMs = inspectMs;
  }
  public Integer getInspectPr() {
    return inspectPr;
  }
  public void setInspectPr(Integer inspectPr) {
    this.inspectPr = inspectPr;
  }
  public Integer getInspectSe() {
    return inspectSe;
  }
  public void setInspectSe(Integer inspectSe) {
    this.inspectSe = inspectSe;
  }
  public Integer getFormalPs() {
    return formalPs;
  }
  public void setFormalPs(Integer formalPs) {
    this.formalPs = formalPs;
  }
  public Integer getCheckPs() {
    return checkPs;
  }
  public void setCheckPs(Integer checkPs) {
    this.checkPs = checkPs;
  }
  public Integer getInspectPs() {
    return inspectPs;
  }
  public void setInspectPs(Integer inspectPs) {
    this.inspectPs = inspectPs;
  }
  public Integer getFormalSum() {
    return formalSum;
  }
  public void setFormalSum(Integer formalSum) {
    this.formalSum = formalSum;
  }
  public Integer getCheckSum() {
    return checkSum;
  }
  public void setCheckSum(Integer checkSum) {
    this.checkSum = checkSum;
  }
  public Integer getInspectsum() {
    return inspectsum;
  }
  public void setInspectsum(Integer inspectsum) {
    this.inspectsum = inspectsum;
  }
	
	
	
}
