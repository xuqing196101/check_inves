package ses.main;

import java.io.File;

import org.mybatis.generator.api.ShellRunner;

/**
 * Description: mybatis自动生成实体类、xml、dao
 *
 * @author Ye MaoLin
 * @version 2016-9-18
 * @since JDK1.7
 */
public class MybatisConf {

	public static void main(String[] args) {
		
//		File file1 = new File("D:/WORKproject/jdcg/jdcg/src/main/java/yggc/main/generator.xml"); 
		File file1 = new File("E:\\zh\\jdcg\\src\\main\\java\\ses\\main\\generator.xml"); 
		String mysql_config = file1.getPath();
	//	String mysql_config = MybatisConf.class.getResource("/generator.xml").getPath();
        System.out.println("mysql_config: "+mysql_config);
		ShellRunner.main(new String[]{"-configfile", mysql_config, "-overwrite"});
	}
}