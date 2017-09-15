package extract.model.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class Supervise {
		
	    private String id;

	    private Date createdAt;
	   
	    private String recordId;
	    
	    private Date updatedAt;
	    
	    private String personType;
	    
	    private String condditionId;
	    
	    @NotEmpty
	    @Length(min=1,max=5)
	    private String name;
	    
	    @NotEmpty
	    @Length(min=1,max=50)
	    private String duty;
	    
	    @NotEmpty
	    @Length(min=1,max=50)
	    private String compary;
	    
	    @NotEmpty
	    @Length(min=1,max=50)
	    private String rank;
	   
	    private Short isDelete;
	    
	    private List<Supervise> list = new ArrayList<>();

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

		public Short getIsDelete() {
			return isDelete;
		}

		public void setIsDelete(Short isDelete) {
			this.isDelete = isDelete;
		}


		public String getRank() {
			return rank;
		}

		public void setRank(String rank) {
			this.rank = rank;
		}

		public String getPersonType() {
			return personType;
		}

		public void setPersonType(String personType) {
			this.personType = personType;
		}

		public String getCondditionId() {
			return condditionId;
		}

		public void setCondditionId(String condditionId) {
			this.condditionId = condditionId;
		}

		public List<Supervise> getList() {
			return list;
		}

		public void setList(List<Supervise> list) {
			this.list = list;
		}
	 
}