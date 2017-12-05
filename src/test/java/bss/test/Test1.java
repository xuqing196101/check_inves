package bss.test;

public class Test1 {
	public static void main(String[] args) {
		String str = "ss111s";
		String regex = "111";
		System.out.println(str.replaceAll(regex, "<span>"+regex+"</span>"));
	}
}
