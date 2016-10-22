package bss.test;

public class Bbb extends Aaa {

	private String ddd;

	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	@Override
	public String toString() {
		return "Bbb [ddd=" + ddd + ", getDdd()=" + getDdd() + ", getAaa()=" + getAaa() + ", getBbb()=" + getBbb()
				+ ", getCcc()=" + getCcc() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
}
