package ses.model.ems;

/**
 * <p>Title:ExpertBlackListVO</p>
 * <p>Description:专家黑名单VO</p>
 * @author HuangXigang
 * @date 2017-5-24 上午10z:37:03
 */
public class ExpertBlackListVO extends ExpertBlackList {
	private String expertTypeName;// 专家类型名称

	public String getExpertTypeName() {
		return expertTypeName;
	}

	public void setExpertTypeName(String expertTypeName) {
		this.expertTypeName = expertTypeName;
	}
	
}
