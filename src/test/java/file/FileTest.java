package file;/**
 * Created by Easong on 2017/10/17.
 */

import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * 文件测试
 *
 * @author Easong
 * @create 2017-10-17 17:21
 **/
public class FileTest {

    @Test
    public void testFilerenameToMethod() throws Exception{
        File srcFile= new File("D:\\import\\aaa.txt");
        FileReader w = new FileReader(srcFile);
        BufferedReader br = new BufferedReader(w);

        String s = br.readLine();

        boolean b = srcFile.renameTo(new File("D:\\finish\\aaa.txt"));
        System.err.println(b);
    }
}
