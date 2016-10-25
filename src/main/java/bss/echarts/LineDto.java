package bss.echarts;

/**
* @Title:LineDto 
* @Description: 
* @author Shen Zhenfei
* @date 2016-10-25上午10:04:08
 */
public class LineDto {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer value;

    private String label;
    
    private String participants;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

	public String getParticipants() {
		return participants;
	}

	public void setParticipants(String participants) {
		this.participants = participants;
	}
}
