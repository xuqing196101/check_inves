package ses.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 供应商常量
 * @author hxg
 * @date 2017-8-31 上午11:25:51
 */
public class SupplierConstants {
	/** 供应商状态 */
	public enum Status {
		/** 暂存 */
		TEMPORARY(-1),
		/** 待审核 */
		PENDING_AUDIT(0),
		/** 初审通过 */
		FIRST_PASSED(1),
		/** 退回修改 */
		RETURN(2),
		/** 初审未通过 */
		FIRST_NOT_PASS(3),
		/** 待复核 */
		PENDING_REVIEW(4),
		/** 复审通过 */
		REVIEW_PASSED(5),
		/** 复审未通过 */
		REVIEW_NOT_PASS(6),
		/** 待考察 */
		PENDING_INVESTIGATE(5),
		/** 考察合格 */
		INVESTIGATE_PASSED(7),
		/** 考察不合格 */
		INVESTIGATE_NOT_PASS(8),
		/** 退回再审核 */
		RETURN_AUDIT(9),
		/** 预审核通过 */
		PRE_AUDIT_PASS(-2),
		/** 公示中 */
		PUBLICITY(-3);
		
		private int value;

		private Status(int value) {
			this.value = value;
		}
		
		public int getValue(){
			return value;
		}
		
	}
	
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
	
	/** 供应商状态集合 */
	public final static Map<Integer, String> STATUSMAP = new HashMap<Integer, String>();
	static{
		STATUSMAP.put(Status.TEMPORARY.getValue(), "暂存");
		STATUSMAP.put(Status.PENDING_AUDIT.getValue(), "待审核");
		STATUSMAP.put(Status.FIRST_PASSED.getValue(), "初审通过");
		STATUSMAP.put(Status.RETURN.getValue(), "退回修改");
		STATUSMAP.put(Status.FIRST_NOT_PASS.getValue(), "初审未通过");
		STATUSMAP.put(Status.PENDING_REVIEW.getValue(), "待复核");
		STATUSMAP.put(Status.REVIEW_PASSED.getValue(), "复审通过");
		STATUSMAP.put(Status.REVIEW_NOT_PASS.getValue(), "复审未通过");
		STATUSMAP.put(Status.PENDING_INVESTIGATE.getValue(), "待考察");
		STATUSMAP.put(Status.INVESTIGATE_PASSED.getValue(), "考察合格");
		STATUSMAP.put(Status.INVESTIGATE_NOT_PASS.getValue(), "考察不合格");
		STATUSMAP.put(Status.RETURN_AUDIT.getValue(), "退回再审核");
		STATUSMAP.put(Status.PRE_AUDIT_PASS.getValue(), "预审核通过");
		STATUSMAP.put(Status.PUBLICITY.getValue(), "公示中");
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
