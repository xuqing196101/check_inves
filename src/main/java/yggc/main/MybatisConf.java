package yggc.main;

import java.io.File;

import org.mybatis.generator.api.ShellRunner;

/**
* <p>Title:MybatisConf </p>
* <p>Description: mybatis自动生成实体类、xml、dao</p>
* <p>Company: yggc </p> 
* @author yyyml
* @date 2016-7-15下午1:34:06
*/
public class MybatisConf {

	public static void main(String[] args) {
		//File file1 = new File("D:/WORKproject/jdcg/jdcg/src/main/java/yggc/main/generator.xml"); 
		File file1 = new File("E:/github_zh/jdcg/src/main/java/yggc/main/generator.xml"); 
		String mysql_config = file1.getPath();
	//	String mysql_config = MybatisConf.class.getResource("/generator.xml").getPath();
        System.out.println("mysql_config: "+mysql_config);
		ShellRunner.main(new String[]{"-configfile", mysql_config, "-overwrite"});
	}
}
