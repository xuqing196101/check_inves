package extract.model.common;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class Supervise {
	
	 private String id;

	    private Date createdAt;
	   
	    private String recordId;
	    
	    private Date updatedAt;
	    
	    @NotEmpty
	    @Length(max=5)
	    private String name;
	    
	    @NotEmpty
	    @Length(max=50)
	    private String duty;
	    
	    @NotEmpty
	    @Length(max=50)
	    private String compary;
	    
	    @NotEmpty
	    @Length(max=50)
	    private String rank;
	   
	    private Short isDelete;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public Date getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(Date createdAt) {
			this.createdAt = createdAt;
		}

		public String getRecordId() {
			return recordId;
		}

		public void setRecordId(String recordId) {
			this.recordId = recordId;
		}

		public Date getUpdatedAt() {
			return updatedAt;
		}

		public void setUpdatedAt(Date updatedAt) {
			this.updatedAt = updatedAt;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDuty() {
			return duty;
		}

		public void setDuty(String duty) {
			this.duty = duty;
		}

		public String getCompary() {
			return compary;
		}

		public void setCompary(String compary) {
			this.compary = compary;
		}

		public String getRank() {
			return rank;
		}

		public void setRank(String rank) {
			this.rank = rank;
		}

		public Short getIsDelete() {
			return isDelete;
		}

		public void setIsDelete(Short isDelete) {
			this.isDelete = isDelete;
		}
}