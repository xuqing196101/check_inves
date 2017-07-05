package common.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * Description: 访问量实体
 * 
 * @author Easong
 * @version 2017年6月13日
 * @since JDK1.7
 */
public class SystemPV implements Serializable{
	
	/**
	 * SystemPV.java
	 */
	private static final long serialVersionUID = 1L;

	/**id对应当前日期：例如20170613**/
    private Integer id;

    /**日访问量**/
    private BigDecimal dayNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getDayNum() {
        return dayNum;
    }

    public void setDayNum(BigDecimal dayNum) {
        this.dayNum = dayNum;
    }
}