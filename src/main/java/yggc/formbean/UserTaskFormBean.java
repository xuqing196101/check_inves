package yggc.formbean;

/**
 * 
 * <p>Title: UserTaskFormBean</p>
 * <p>Description:用户任务管理formBean </p> 
 * @author Li Xiaoxiao
 * @date  2016年9月7日,下午5:31:18
 *
 */
public class UserTaskFormBean {

	private String id;
	
	private String start_date;
	
	private String end_date;
	
	private String subject;
	
	private String text;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
}
