package bss.model.ob;

import java.util.List;

/**
 * 接收供应商报价产品信息
 * 
 * @ClassName: OBResultInfoList
 * @Description:
 * @author Easong
 * @date 2017年3月10日 下午6:00:42
 * 
 */
public class OBResultInfoList {

	List<OBResultsInfoExt> obResultsInfoExt;

	public List<OBResultsInfoExt> getObResultsInfoExt() {
		return obResultsInfoExt;
	}

	public void setObResultsInfoExt(List<OBResultsInfoExt> obResultsInfoExt) {
		this.obResultsInfoExt = obResultsInfoExt;
	}

}
