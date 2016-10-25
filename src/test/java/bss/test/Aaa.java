package bss.test;

import java.util.HashMap;
import java.util.Map;

public class Aaa {

	public static void main(String[] args) {
		Bbb bbb = new Bbb();
		Bbb bb2= new Bbb();
		Map<String,Object> map = new HashMap<>();
		//map.put("aaa", "1");
		map.put("aaa",bbb );
		Object object = map.get("aaa");
		
		if(object.equals("1")){
			System.out.println(111);
		}else if(object instanceof Bbb){
			System.out.println(222);
		}
	}
	private String aaa;
	private String bbb;
	private String ccc;
	public String getAaa() {
		return aaa;
	}
	public void setAaa(String aaa) {
		this.aaa = aaa;
	}
	public String getBbb() {
		return bbb;
	}
	public void setBbb(String bbb) {
		this.bbb = bbb;
	}
	public String getCcc() {
		return ccc;
	}
	public void setCcc(String ccc) {
		this.ccc = ccc;
	}
	
	
}
