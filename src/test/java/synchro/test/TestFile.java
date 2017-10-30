package synchro.test;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Description: 编写文件测试类
 *
 * @param
 * @author Easong
 * @version 2017/10/30
 * @since JDK1.7
 */
public class TestFile {


    /**
     *
     * Description: 测试文件copy
     *
     * @author Easong
     * @version 2017/10/30
     * @param
     * @since JDK1.7
     */
    @Test
    public void testFileCopy(){
        InputStream is = null;
        FileOutputStream os = null;
        try {
            is = new FileInputStream(new File("C:\\web\\attach\\uploads\\5.jpg"));
            File file = new File("C:\\web\\attach\\supplier\\5.jpg");
            if(!file.exists()){
                file.mkdirs();
            }
            os = new FileOutputStream(file);
            byte[] b = new byte[1024];
            int prereadlength;
            while ((prereadlength = is.read(b)) != -1){
                os.write(b, 0, prereadlength);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if(os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
