package bss.test;

import bss.model.pms.PurchaseRequired;
import net.sf.json.JSONObject;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {
	public static void main(String[] args) {
		
//		List<String> list=new LinkedList<String>();
//		list.add("1");
//		list.add("2");
//		for (int i=0;i<list.size();i++ ) {
//			System.out.println(list.get(i));
//		}
		
//		String s="123.xlsx";
//		boolean a = !s.toLowerCase().endsWith(".xls");
//		System.out.println(a);
//		boolean b = !s.toLowerCase().endsWith(".xlsx");
//		System.out.println(b);
//		if(a ||b){
//			System.out.println("c");
//		}
		List<PurchaseRequired> list=new ArrayList<PurchaseRequired>();
		PurchaseRequired p=new PurchaseRequired();
		p.setSeq("一");
		PurchaseRequired p1=new PurchaseRequired();
		p1.setSeq("（一）");
		PurchaseRequired p2=new PurchaseRequired();
		p2.setSeq("1");
		PurchaseRequired p3=new PurchaseRequired();
		p3.setSeq("（1）");
		
		PurchaseRequired p4=new PurchaseRequired();
		p4.setSeq("a");
		PurchaseRequired p5=new PurchaseRequired();
		p5.setSeq("（a）");
		PurchaseRequired p6=new PurchaseRequired();
		p6.setSeq("二");
		PurchaseRequired p7=new PurchaseRequired();
		p7.setSeq("（一）");
		PurchaseRequired p8=new PurchaseRequired();
		p8.setSeq("1");
		PurchaseRequired p9=new PurchaseRequired();
		p9.setSeq("（1）");
		
		PurchaseRequired p10=new PurchaseRequired();
		p10.setSeq("a");
		PurchaseRequired p11=new PurchaseRequired();
		p11.setSeq("（a）");
		PurchaseRequired p12=new PurchaseRequired();
		p12.setSeq("（b）");
		
		PurchaseRequired p13=new PurchaseRequired();
		p13.setSeq("（c）");
		
		PurchaseRequired p14=new PurchaseRequired();
		p14.setSeq("（d）");
		list.add(p);
		list.add(p14);
		list.add(p13);
		list.add(p12);
		list.add(p11);
		list.add(p10);
		list.add(p9);
		list.add(p8);
		list.add(p7);
		list.add(p6);
		list.add(p5);
		list.add(p4);
		list.add(p3);
		list.add(p2);
		list.add(p1);
		
		
		
		
		
		
		
		
		
	}

		@Test
		public void ss(){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
			Date today=new Date();
			String str = sdf.format(today);
			System.out.println(str);
			Date date2= addDate(new Date(),3,45);
			
			String str2 = sdf.format(date2);
			System.out.println( str2);
		     if(today.getTime()>date2.getTime()){
		    	 System.out.println(123);
		     }   
		        
		}
		
		public Date addDate(Date baseDate, int type, int num) {
			Date lastDate = null;
			Calendar cale = Calendar.getInstance();
			cale.setTime(baseDate);
			if (type == 1)
				cale.add(Calendar.YEAR, num);
			else if (type == 2)
				cale.add(Calendar.MONTH, num);
			else if (type == 3)
				cale.add(Calendar.DAY_OF_MONTH, num);
			else if(type == 4)
				cale.add(Calendar.HOUR, num);
			lastDate = cale.getTime();
			return lastDate;
		} 

		@Test
		public void test6(){
			String str="123123.0";
			String string = str.substring(0, str.lastIndexOf("."));
			System.out.print(string);
			
		}
		
		@Test
		public void testHistory(){
			TestUser tu=new TestUser();
			tu.setName("李晓");
			tu.setAge(18);
			tu.setSex("男");
		
			JSONObject jsonString =JSONObject.fromObject(tu);
			System.out.print(jsonString);
			TestUser object = (TestUser) JSONObject.toBean(jsonString, TestUser.class);
//			TestUser object = (TestUser) JSONObject
			System.out.print(object.getAge());
		}
		
		
		@Test
		public void testSolr() throws SolrServerException, IOException{
			ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-solr.xml");
			SolrServer solr =  (SolrServer) context.getBean("solrServer");
			// 创建查询条件
			SolrQuery query = new SolrQuery();
			solr.deleteByQuery("*:*");
			solr.commit();
		}
		@Test
		public void testJedis() throws SolrServerException, IOException{
			Jedis jedis = new Jedis("192.168.17.128", 6379);
			String string = jedis.get("a");
			System.out.println(string);
			jedis.close();
		}

		@Test
		public void tetFile()  {

			String srcFile = "C:\\web\\src\\QQ20170706-102843.png";
			String destFile = "C:\\web\\desc\\a.png";

			FileInputStream is = null;
			FileOutputStream os = null;
			try {
				is = new FileInputStream(new File(srcFile));
				os = new FileOutputStream(new File(destFile));
				byte[] b = new byte[1024];
				int len;
				while ((len = is.read(b)) != -1){
					os.write(b, 0, len);
				}
			}catch (IOException e){
				e.printStackTrace();
			}finally {
				if(os != null)
					try {
						os.close();
					} catch (IOException e) {
						e.printStackTrace();
					}

				if(is != null)
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
			}

		}

		@Test
	public void test001(){
			System.err.println(validateIdCard("411481199810143396"));
		}

	/****
	 * 验证 身份证 * 身份证15位编码规则：dddddd yymmdd xx p dddddd：6位地区编码 yymmdd:
	 * 出生年(两位年)月日，如：910215 xx: 顺序编码，系统产生，无法确定 p: 性别，奇数为男，偶数为女
	 *
	 * 身份证18位编码规则：dddddd yyyymmdd xxx y dddddd：6位地区编码 yyyymmdd:
	 * 出生年(四位年)月日，如：19910215 xxx：顺序编码，系统产生，无法确定，奇数为男，偶数为女 y: 校验码，该位数值可通过前17位计算获得
	 *
	 * 前17位号码加权因子为 Wi = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ]
	 * 验证位 Y = [ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 ]
	 * 如果验证码恰好是10，为了保证身份证是十八位，那么第十八位将用X来代替 校验位计算公式：Y_P = mod( ∑(Ai×Wi),11 )
	 * i为身份证号码1...17 位; Y_P为校验码Y所在校验码数组位置
	 */
	public static String validateIdCard(String card){
		String returnStr="";
		//15位和18位身份证号码的正则表达式
		String regIdCard="^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$";
		// 编译正则表达式
		Pattern pattern = Pattern.compile(regIdCard);
		Matcher matcher = pattern.matcher(card);
		// 字符串是否与正则表达式相匹配
		boolean rs = matcher.matches();
		//如果通过该验证，说明身份证格式正确，但准确性还需计算
		if(rs){
			if(card.length()==18){
				Integer idCardWi[]={ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 }; //将前17位加权因子保存在数组里
				Integer idCardY[]={  1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 }; //这是除以11后，可能产生的11位余数、验证码，也保存成数组
				int idCardWiSum=0; //用来保存前17位各自乖以加权因子后的总和
				for(int i=0;i<17;i++){
					idCardWiSum+=Integer.valueOf(card.substring(i,i+1))*idCardWi[i];
				}

				int idCardMod=idCardWiSum%11;//计算出校验码所在数组的位置
				String idCardLast=card.substring(17);//得到最后一位身份证号码

				//如果等于2，则说明校验码是10，身份证号码最后一位应该是X
				if(idCardMod==2){
					if("X".equals(idCardLast) || "x".equals(idCardLast)){
						returnStr= "success";
					}else{
						returnStr= "身份证号码错误！";
					}
				}else{
					//用计算出的验证码与最后一位身份证号码匹配，如果一致，说明通过，否则是无效的身份证号码
					if(idCardLast.equals(idCardY[idCardMod]+"")){
						returnStr= "success";
					}else{
						returnStr= "身份证号码错误！";
					}
				}
			}else{
				//15位
				returnStr= "success";
			}
		}else{
			returnStr= "身份证格式不正确!";
		}
		return returnStr;
	}
}
