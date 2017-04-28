package bss.model.ob;

import java.io.Serializable;
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
public class OBResultInfoList implements Serializable {

	/** 
	* @Fields serialVersionUID : 
	*/ 
	private static final long serialVersionUID = 1L;
	List<OBResultsInfoExt> obResultsInfoExt;

	public List<OBResultsInfoExt> getObResultsInfoExt() {
		return obResultsInfoExt;
	}

	public void setObResultsInfoExt(List<OBResultsInfoExt> obResultsInfoExt) {
		this.obResultsInfoExt = obResultsInfoExt;
	}

}
