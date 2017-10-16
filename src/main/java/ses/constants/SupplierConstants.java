package ses.constants;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description: 供应商常量
 * @author hxg
 * @date 2017-8-31 上午11:25:51
 */
public class SupplierConstants {
	/**
	 * 供应商状态
	 * <pre>
	 * -1：暂存
	 *  0：待审核
	 *  2：退回修改 
	 *  9：退回再审核
	 * -2：预审核结束
	 *  3：审核不通过
	 * -3：公示中
	 *  1：入库（待复核）
	 * -4：预复核结束
	 *  5：复核合格（待考察）
	 *  6：复核不合格
	 * -5：预考察结束
	 *  7：考察合格
	 *  8：考察不合格
	 * </pre>
	 */
	public enum Status {
		/** 暂存 */
		TEMPORARY(-1),
		/** 待审核 */
		PENDING_AUDIT(0),
		/** 退回修改 */
		RETURN(2),
		/** 退回再审核 */
		RETURN_AUDIT(9),
		/** 预审核结束 */
		PRE_AUDIT_ENDED(-2),
		/** 审核不通过 */
		AUDIT_NOT_PASS(3),
		/** 公示中 */
		PUBLICITY(-3),
		/** 异议处理 */
		DISSENT(10),
		/** 入库（待复核） */
		PENDING_REVIEW(1),
		/** 预复核结束 */
		PRE_REVIEW_ENDED(-4),
		/** 复核合格（待考察） */
		REVIEW_PASSED(5),
		/** 复核不合格 */
		REVIEW_NOT_PASS(6),
		/** 预考察结束 */
		PRE_INVESTIGATE_ENDED(-5),
		/** 考察合格 */
		INVESTIGATE_PASSED(7),
		/** 考察不合格 */
		INVESTIGATE_NOT_PASS(8);
		
		private int value;
		
		private Status(int value) {
			this.value = value;
		}
		
		public int getValue(){
			return value;
		}
		
	}
	/*public enum Status {
		*//** 暂存 *//*
		TEMPORARY(-1),
		*//** 待审核 *//*
		PENDING_AUDIT(0),
		*//** 初审通过 *//*
		FIRST_PASSED(1),
		*//** 退回修改 *//*
		RETURN(2),
		*//** 初审未通过 *//*
		FIRST_NOT_PASS(3),
		*//** 待复核 *//*
		PENDING_REVIEW(4),
		*//** 复审通过 *//*
		REVIEW_PASSED(5),
		*//** 复审未通过 *//*
		REVIEW_NOT_PASS(6),
		*//** 待考察 *//*
		PENDING_INVESTIGATE(5),
		*//** 考察合格 *//*
		INVESTIGATE_PASSED(7),
		*//** 考察不合格 *//*
		INVESTIGATE_NOT_PASS(8),
		*//** 退回再审核 *//*
		RETURN_AUDIT(9),
		*//** 预审核通过 *//*
		PRE_AUDIT_PASSED(-2),
		*//** 公示中 *//*
		PUBLICITY(-3);
		
		private int value;

		private Status(int value) {
			this.value = value;
		}
		
		public int getValue(){
			return value;
		}
		
	}*/
	
	/** 供应商审核记录退回状态 */
	public enum AuditReturnStatus {
		/** 初始状态 */
		INIT_STATUS(0),
		/** 退回修改 */
		RETURN_TO_MODIFY(1),
		/** 审核不通过 （产品目录用）*/
		AUDIT_NOT_PASS(2),
		/** 已修改 */
		MODIFIED(3),
		/** 未修改 */
		NOT_MODIFY(4);
		
		private int value;

		private AuditReturnStatus(int value) {
			this.value = value;
		}
		
		public int getValue(){
			return value;
		}
		
	}
	
	/** 供应商审核暂存状态 */
	public enum AuditTemporaryStatus {
		/** 审核中 */
		IN_AUDIT(1),
		/** 复核中 */
		IN_REVIEW(2),
		/** 考察中 */
		IN_INVESTIGATE(3);
		
		private int value;
		
		private AuditTemporaryStatus(int value) {
			this.value = value;
		}
		
		public int getValue(){
			return value;
		}
		
	}
	
	/** 供应商状态集合 */
	public final static Map<Integer, String> STATUSMAP = new LinkedHashMap<Integer, String>();
	static{
		STATUSMAP.put(Status.TEMPORARY.getValue(), "暂存");
		STATUSMAP.put(Status.PENDING_AUDIT.getValue(), "待审核");
		STATUSMAP.put(Status.RETURN.getValue(), "退回修改");
		STATUSMAP.put(Status.RETURN_AUDIT.getValue(), "退回再审核");
		STATUSMAP.put(Status.PRE_AUDIT_ENDED.getValue(), "预审核结束");
		STATUSMAP.put(Status.AUDIT_NOT_PASS.getValue(), "审核不通过");
		STATUSMAP.put(Status.PUBLICITY.getValue(), "公示中");
		STATUSMAP.put(Status.DISSENT.getValue(), "异议处理");
		STATUSMAP.put(Status.PENDING_REVIEW.getValue(), "入库（待复核）");
		STATUSMAP.put(Status.PRE_REVIEW_ENDED.getValue(), "预复核结束");
		STATUSMAP.put(Status.REVIEW_PASSED.getValue(), "复核合格（待考察）");
		STATUSMAP.put(Status.REVIEW_NOT_PASS.getValue(), "复核不合格");
		STATUSMAP.put(Status.PRE_INVESTIGATE_ENDED.getValue(), "预考察结束");
		STATUSMAP.put(Status.INVESTIGATE_PASSED.getValue(), "考察合格");
		STATUSMAP.put(Status.INVESTIGATE_NOT_PASS.getValue(), "考察不合格");
	}
	
	/** 入库供应商状态集合 */
	public final static Map<Integer, String> STATUSMAP_RUKU = new LinkedHashMap<Integer, String>();
	static{
		STATUSMAP_RUKU.put(Status.PENDING_REVIEW.getValue(), "入库（待复核）");
		STATUSMAP_RUKU.put(Status.PRE_REVIEW_ENDED.getValue(), "预复核结束");
		STATUSMAP_RUKU.put(Status.REVIEW_PASSED.getValue(), "复核合格（待考察）");
		STATUSMAP_RUKU.put(Status.REVIEW_NOT_PASS.getValue(), "复核不合格");
		STATUSMAP_RUKU.put(Status.PRE_INVESTIGATE_ENDED.getValue(), "预考察结束");
		STATUSMAP_RUKU.put(Status.INVESTIGATE_PASSED.getValue(), "考察合格");
		STATUSMAP_RUKU.put(Status.INVESTIGATE_NOT_PASS.getValue(), "考察不合格");
	}
	
	/** 审核供应商状态集合 */
	public final static Map<Integer, String> STATUSMAP_SHENHE = new LinkedHashMap<Integer, String>();
	static{
		STATUSMAP_SHENHE.put(Status.PENDING_AUDIT.getValue(), "待审核");
		STATUSMAP_SHENHE.put(Status.RETURN.getValue(), "退回修改");
		STATUSMAP_SHENHE.put(Status.RETURN_AUDIT.getValue(), "退回再审核");
		STATUSMAP_SHENHE.put(Status.PRE_AUDIT_ENDED.getValue(), "预审核结束");
		STATUSMAP_SHENHE.put(Status.PUBLICITY.getValue(), "公示中");
		STATUSMAP_SHENHE.put(Status.PENDING_REVIEW.getValue(), "入库（待复核）");
		STATUSMAP_SHENHE.put(Status.AUDIT_NOT_PASS.getValue(), "审核不通过");
	}
	
	/** 复核供应商状态集合 */
	public final static Map<Integer, String> STATUSMAP_FUHE = new LinkedHashMap<Integer, String>();
	static{
		STATUSMAP_FUHE.put(Status.PENDING_REVIEW.getValue(), "入库（待复核）");
		STATUSMAP_FUHE.put(Status.PRE_REVIEW_ENDED.getValue(), "预复核结束");
		STATUSMAP_FUHE.put(Status.REVIEW_PASSED.getValue(), "复核合格（待考察）");
		STATUSMAP_FUHE.put(Status.REVIEW_NOT_PASS.getValue(), "复核不合格");
	}
	
	/** 考察供应商状态集合 */
	public final static Map<Integer, String> STATUSMAP_KAOCHA = new LinkedHashMap<Integer, String>();
	static{
		STATUSMAP_KAOCHA.put(Status.REVIEW_PASSED.getValue(), "复核合格（待考察）");
		STATUSMAP_KAOCHA.put(Status.PRE_INVESTIGATE_ENDED.getValue(), "预考察结束");
		STATUSMAP_KAOCHA.put(Status.INVESTIGATE_PASSED.getValue(), "考察合格");
		STATUSMAP_KAOCHA.put(Status.INVESTIGATE_NOT_PASS.getValue(), "考察不合格");
	}
	
	/** 供应商审核暂存状态集合 */
	public final static Map<Integer, String> STATUSMAP_AUDITTEMPORARY = new LinkedHashMap<Integer, String>();
	static{
		STATUSMAP_AUDITTEMPORARY.put(AuditTemporaryStatus.IN_AUDIT.getValue(), "审核中 ");
		STATUSMAP_AUDITTEMPORARY.put(AuditTemporaryStatus.IN_REVIEW.getValue(), "复核中 ");
		STATUSMAP_AUDITTEMPORARY.put(AuditTemporaryStatus.IN_INVESTIGATE.getValue(), "考察中 ");
	}
	
	/** 注册拥有的状态 */
	public final static Integer[] STATUS_TO_REGISTER = new Integer[]{
		Status.TEMPORARY.getValue(),// 暂存
		Status.RETURN.getValue()// 退回修改
	};
	
	/** 审核拥有的状态 */
	public final static Integer[] STATUS_TO_AUDIT = new Integer[]{
		Status.PENDING_AUDIT.getValue(),// 待审核
		Status.RETURN_AUDIT.getValue(),//退回再审核
		Status.PRE_AUDIT_ENDED.getValue(),// 预审核结束
		Status.PENDING_REVIEW.getValue()// 待复核
	};
	
	/**
	 * 是否注册拥有的状态
	 * @param status
	 * @return
	 */
	public static final boolean isStatusToRegister(final Integer status){
		//return ArrayUtils.contains(SupplierConstants.STATUS_TO_REGISTER, status);
		if(null == status){
			return false;
		}
		return status == Status.TEMPORARY.getValue()
			|| status == Status.RETURN.getValue();
	}
	
	/**
	 * 是否审核拥有的状态
	 * @param status
	 * @return
	 */
	public static final boolean isStatusToAudit(final Integer status){
		//return ArrayUtils.contains(SupplierConstants.STATUS_TO_AUDIT, status);
		if(null == status){
			return false;
		}
		return status == Status.PENDING_AUDIT.getValue()
			|| status == Status.RETURN_AUDIT.getValue()
			|| status == Status.PRE_AUDIT_ENDED.getValue()
			|| status == Status.PENDING_REVIEW.getValue();
	}
	
	/** 供应商审核记录退回状态 */
	//public final static Integer[] AUDIT_RETURN_STATUS = new Integer[]{0, 1, 2, 4};
	public final static Integer[] AUDIT_RETURN_STATUS = new Integer[]{
		AuditReturnStatus.INIT_STATUS.getValue(), 
		AuditReturnStatus.RETURN_TO_MODIFY.getValue(), 
		AuditReturnStatus.AUDIT_NOT_PASS.getValue(), 
		AuditReturnStatus.NOT_MODIFY.getValue()
	};
	
	public static void main(String[] args) {
		for(Status status : Status.values()){
			System.out.println(status.getValue());
		}
	}
}
