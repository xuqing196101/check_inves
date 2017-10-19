package extract.model.supplier;


public class Qua {

	private String id;
	private String name;
	private String quatype;
	@Override
	public String toString() {
		return "Qua [id=" + id + ", name=" + name + ", quatype=" + quatype
				+ "]";
	}
	
	@Override  
    public boolean equals(Object obj) {  
	    Qua q=(Qua)obj;   
	    return id.equals(q.id);   
    }   
    @Override
    public int hashCode() {  
	    String in =  id;  
	    return in.hashCode();  
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQuatype() {
		return quatype;
	}

	public void setQuatype(String quatype) {
		this.quatype = quatype;
	}
	
	
}
