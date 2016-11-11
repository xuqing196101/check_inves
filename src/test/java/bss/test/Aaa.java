package bss.test;

class Cbb{
	public Cbb(){
		System.out.println("helloA");
	}
	{
		System.out.println("i m A class");
	}
	static {
		System.out.println("static A");
	}
}
public class Aaa extends Cbb{

	public Aaa(){
		System.out.println("HelloB");
	}	
	{
		System.out.println("i m B class");
	}
	static {
		System.out.println("static B");
	}
	public static void main(String[] args) {
		//new Aaa();
		String s;
		//System.out.println(s);
		int aaa = Aaa(2);
		System.out.println(aaa);
	}
	public static int Aaa(int i){
		int result = 0;
		switch (i) {
		case 1:
			result=result+i;
		case 2:
			result=result+i*2;
		case 3:
			result=result+i*3;
		
	}
		return result;
	}
}
