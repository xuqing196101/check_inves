package ses.model.bms;

import java.util.List;

/**
 * @Description: 洲-国家关系扩展类
 * @author hxg
 * @date 2017-12-18 下午3:31:21
 */
public class ContinentNationRelExt {
	
	private ContinentNationRel cnr;// 洲-国家关系(one-to-one)
	private List<ContinentNationRel> cnrList;// 洲-国家关系(one-to-many)
	
	public ContinentNationRel getCnr() {
		return cnr;
	}
	public void setCnr(ContinentNationRel cnr) {
		this.cnr = cnr;
	}
	public List<ContinentNationRel> getCnrList() {
		return cnrList;
	}
	public void setCnrList(List<ContinentNationRel> cnrList) {
		this.cnrList = cnrList;
	}
	
}
