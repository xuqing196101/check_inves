package ses.model.sms;

import java.io.Serializable;

/**
 * @Title: SupplierStars
 * @Description: 供应商星级规则
 * @author: Wang Zhaohua
 * @date: 2016-10-4下午3:04:10
 */
public class SupplierStars implements Serializable {
	private static final long serialVersionUID = -8105815766954548777L;

	/**
	 * <pre>
	 * ID 主键
	 * 表字段 : T_SES_SMS_SUPPLIER_STARS.ID
	 * </pre>
	 */
	private String id;

	/**
	 * <pre>
	 * 一星级分数
	 * 表字段 : T_SES_SMS_SUPPLIER_STARS.ONE_STARS
	 * </pre>
	 */
	private Integer oneStars;

	/**
	 * <pre>
	 * 二星级分数
	 * 表字段 : T_SES_SMS_SUPPLIER_STARS.TWO_STARS
	 * </pre>
	 */
	private Integer twoStars;

	/**
	 * <pre>
	 * 三星级分数
	 * 表字段 : T_SES_SMS_SUPPLIER_STARS.THREE_STARS
	 * </pre>
	 */
	private Integer threeStars;

	/**
	 * <pre>
	 * 四星级分数
	 * 表字段 : T_SES_SMS_SUPPLIER_STARS.FOUR_STARS
	 * </pre>
	 */
	private Integer fourStars;

	/**
	 * <pre>
	 * 五星级分数
	 * 表字段 : T_SES_SMS_SUPPLIER_STARS.FIVE_STARS
	 * </pre>
	 */
	private Integer fiveStars;

	/**
	 * <pre>
	 * 0代表为启用 1代表启动
	 * 表字段 : T_SES_SMS_SUPPLIER_STARS.STATUS
	 * </pre>
	 */
	private Integer status = 0;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getOneStars() {
		return oneStars;
	}

	public void setOneStars(Integer oneStars) {
		this.oneStars = oneStars;
	}

	public Integer getTwoStars() {
		return twoStars;
	}

	public void setTwoStars(Integer twoStars) {
		this.twoStars = twoStars;
	}

	public Integer getThreeStars() {
		return threeStars;
	}

	public void setThreeStars(Integer threeStars) {
		this.threeStars = threeStars;
	}

	public Integer getFourStars() {
		return fourStars;
	}

	public void setFourStars(Integer fourStars) {
		this.fourStars = fourStars;
	}

	public Integer getFiveStars() {
		return fiveStars;
	}

	public void setFiveStars(Integer fiveStars) {
		this.fiveStars = fiveStars;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}