package synchro.test;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;

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
     * Description: 测试文件copy
     *
     * @param
     * @author Easong
     * @version 2017/10/30
     * @since JDK1.7
     */
    @Test
    public void testFileCopy() {
        InputStream is = null;
        FileOutputStream os = null;
        try {
            is = new FileInputStream(new File("C:\\web\\attach\\uploads\\5.jpg"));
            File file = new File("C:\\web\\attach\\supplier\\5.jpg");
            if (!file.exists()) {
                file.mkdirs();
            }
            os = new FileOutputStream(file);
            byte[] b = new byte[1024];
            int prereadlength;
            while ((prereadlength = is.read(b)) != -1) {
                os.write(b, 0, prereadlength);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void testFileChannel() {
        long a = copyFile(new File("C:\\web\\attach\\uploads\\5.jpg"), new File("C:\\web\\attach\\uploads\\supplier"), "a.jpg");
        System.err.println(a);
    }

    /**
     * Description: 复制文件(以超快的速度复制文件)
     *
     * @param
     * @author Easong
     * @version 2017/10/30
     * @since JDK1.7
     */
    public static long copyFile(File srcFile, File destDir, String newFileName) {
        long copySizes = 0;
        if (!srcFile.exists()) {
            System.out.println("源文件不存在");
            copySizes = -1;
        } else if (!destDir.exists()) {
            System.out.println("目标目录不存在");
            copySizes = -1;
        } else if (newFileName == null) {
            System.out.println("文件名为null");
            copySizes = -1;
        } else {
            try {
                FileChannel fcin = new FileInputStream(srcFile).getChannel();
                FileChannel fcout = new FileOutputStream(new File(destDir,
                        newFileName)).getChannel();
                long size = fcin.size();
                fcin.transferTo(0, fcin.size(), fcout);
                fcout.close();
                fcin.close();
                copySizes = size;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return copySizes;
    }

    @Test
    public void testRecursion() {
        File srcFile = new File("C:\\web\\pic\\1508219226400.jpg");
        String fileName;
        FileChannel fcin = null;
        FileChannel fcout = null;
        try {
            long start = System.currentTimeMillis();
            for (int i = 10000; i < 30000; i++) {
                String substring = srcFile.getName().substring(0, srcFile.getName().indexOf(".jpg"));
                fileName = substring + i + ".jpg";
                File descFile = new File("C:\\web\\pic");
                copyFileTest(srcFile, descFile, fileName);
               /* fcin = new FileInputStream(srcFile).getChannel();
                fcout = new FileOutputStream(new File(descFile,
                        fileName)).getChannel();
                fcout.close();
                fcin.close();*/
            }
            long end = System.currentTimeMillis();
            System.err.println(end - start);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fcout != null) {
                try {
                    fcout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fcin != null) {
                try {
                    fcin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Description:
     *
     * @param
     * @author Easong
     * @version 2017/10/31
     * @since JDK1.7
     */
    public static void copyFileTest(File srcFile, File destDir, String newFileName) {
        try {
            FileChannel fcin = new FileInputStream(srcFile).getChannel();
            FileChannel fcout = new FileOutputStream(new File(destDir,
                    newFileName)).getChannel();
            fcin.transferTo(0, fcin.size(), fcout);
            fcout.close();
            fcin.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * Description: 删除目录
     *
     * @author Easong
     * @version 2017/10/31
     * @param 
     * @since JDK1.7
     */
    @Test
    public void testDeleteDirFile(){
        try {
            FileUtils.deleteDirectory(new File("C:\\web\\pic"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
