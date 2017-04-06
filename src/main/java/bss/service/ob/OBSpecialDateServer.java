package bss.service.ob;

import java.util.Date;

/**
 * 竞价特殊日期
 * @author YangHongliang
 */
public interface OBSpecialDateServer {
	/**
	 * 导出特殊日期
	 * @param startTime
	 * @param endTime
	 * @param synchDate
	 * @return
	 */
    boolean exportSpecialDate(String startTime,String endTime,Date synchDate);
}
