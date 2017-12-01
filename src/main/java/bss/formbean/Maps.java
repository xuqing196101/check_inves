package bss.formbean;

import java.io.Serializable;
import java.math.BigDecimal;

public class Maps implements Serializable{

	private String name;
	
	private BigDecimal value;

	private String id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
