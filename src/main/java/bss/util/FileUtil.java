package bss.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

/**
 * @Title: FileUtil
 * @Description: 文件工具类
 * @author: Poppet_Brook
 * @date: 2016-6-14下午4:22:42
 */
public class FileUtil {

    private static Logger logger = Logger.getLogger(FileUtil.class);


    /**
     * @Title: getBys
     * @author: Poppet_Brook
     * @date: 2016-7-13 上午10:57:26
     * @Description: 获取文件字节数组
     * @param: @param fileName
     * @param: @return
     * @return: byte[]
     */
    public static byte[] getBys(String fileName, HttpServletRequest request) {
        String path = FileUtil.getPath(request);
        BufferedInputStream input = null;
        try {
            input = new BufferedInputStream(new FileInputStream(path + fileName));
            return IOUtils.toByteArray(input);
        } catch (Exception e) {
            logger.error(e);
            logger.error("<获取" + fileName + ">字节数组失败 !");
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    logger.error(e);
                }
            }

        }
    }

    /**
     * @Title: fileStash
     * @author: Poppet_Brook
     * @date: 2016-6-14 下午4:23:00
     * @Description: 根据传递的文件夹名称在暂存文件夹下新建指定文件夹
     * @param: @param srcFile
     * @param: @param fileName
     * @param: @return
     * @return: String
     */
    public static String fileStash(File srcFile, String folder, String fileName, HttpServletRequest request) {
        String path = FileUtil.getPath(request);
        path += folder + "/";
        fileName = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase() + "_" + fileName;
        File destFile = new File(path + fileName);
        try {
            FileUtils.copyFile(srcFile, destFile);
        } catch (IOException e) {
            logger.error(e);
            logger.error("<" + fileName + ">暂存失败 !");
        }
        return fileName;
    }

    /**
     * @Title: removeStash
     * @author: Poppet_Brook
     * @date: 2016-6-14 下午4:23:18
     * @Description: 删除暂存文件
     * @param: @param fileName
     * @return: void
     */
    public static void removeStash(String fileName, HttpServletRequest request) {
        final String path = FileUtil.getPath(request) + fileName;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // String path = FileUtil.getPath();
                File file = new File(path);
                if (file.isFile()) {
                    file.delete();
                }
            }
        }, 10000);
    }

    /**
     * @Title: removeStash
     * @author: Poppet_Brook
     * @date: 2016-6-14 下午4:23:18
     * @Description: 删除暂存文件
     * @param: @param fileName
     * @return: void
     */
    public static void removeStash(String fileName, String folder, HttpServletRequest request) {
        final String path = FileUtil.getPath(request) + folder + "/" + fileName;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // String path = FileUtil.getPath();
                File file = new File(path);
                if (file.isFile()) {
                    file.delete();
                }
            }
        }, 10000);
    }

    /**
     * @Title: getPath
     * @author: Poppet_Brook
     * @date: 2016-6-14 下午4:23:43
     * @Description: 获取暂存路径
     * @param: @return
     * @return: String
     */
    public static String getPath(HttpServletRequest request) {
        String path = request.getSession().getServletContext().getRealPath("/") + PropUtil.getProperty("file.stashPath") + "/";
        return path.replace("\\", "/");
    }

    /**
     * @Title: getRootPath
     * @author: Poppet_Brook
     * @date: 2016-6-20 上午11:16:32
     * @Description: 获取项目根路径
     * @param: @return
     * @return: String
     */
    public static String getRootPath(HttpServletRequest request) {
        return request.getSession().getServletContext().getRealPath("/");
    }

    /**
     * @param @param oldPath
     * @param @param newPath
     * @return void
     * @throws
     * @Title: copyFolder
     * @Description: 将指定文件下所有内容复制到指定的文件夹
     * author: Li Xiaoxiao
     */
    /*public static void copyFolder(String oldPath, String newPath) {
        FileInputStream input = null;
        FileOutputStream output = null;
        try {
            // 如果文件夹不存在，则建立新文件夹  
            (new File(newPath)).mkdirs();
            //读取整个文件夹的内容到file字符串数组，下面设置一个游标i，不停地向下移开始读这个数组  
            File filelist = new File(oldPath);
            String[] file = filelist.list();
            //要注意，这个temp仅仅是一个临时文件指针  
            //整个程序并没有创建临时文件  
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                //如果oldPath以路径分隔符/或者\结尾，那么则oldPath/文件名就可以了  
                //否则要自己oldPath后面补个路径分隔符再加文件名  
                //谁知道你传递过来的参数是f:/a还是f:/a/啊？  
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }
                //如果游标遇到文件
                if (temp.isFile()) {
                    input = new FileInputStream(temp);
                    output = new FileOutputStream(newPath + "/" + (temp.getName()).toString());
                    byte[] bufferarray = new byte[1024 * 64];
                    int prereadlength;
                    while ((prereadlength = input.read(bufferarray)) != -1) {
                        output.write(bufferarray, 0, prereadlength);
                    }
                    output.flush();
                }
                //如果游标遇到文件夹  
                if (temp.isDirectory()) {
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
        } catch (Exception e) {
            logger.error("复制整个文件夹内容操作出错");
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/

    /**
     * Description: 将指定文件下所有内容复制到指定的文件夹
     * 使用文件通道的方式复制文件
     * FileChannel复制文件的速度比BufferedInputStream/BufferedOutputStream
     * 复制文件的速度快了近三分之一
     *
     * @param [oldPath: 原路径   newPath：目标路径]
     * @author Easong
     * @version 2017/10/30
     * @since JDK1.7
     */
    public static void copyFolder(String oldPath, String newPath) {
        // 定义文件管道
        FileChannel cin = null;
        FileChannel cout = null;
        try {
            // 如果文件夹不存在，则建立新文件夹
            (new File(newPath)).mkdirs();
            //读取整个文件夹的内容到file字符串数组，下面设置一个游标i，不停地向下移开始读这个数组
            File filelist = new File(oldPath);
            String[] file = filelist.list();
            //要注意，这个temp仅仅是一个临时文件指针
            //整个程序并没有创建临时文件
            File temp = null;
            if (file != null) {
                for (int i = 0; i < file.length; i++) {
                    //如果oldPath以路径分隔符/或者\结尾，那么则oldPath/文件名就可以了
                    //否则要自己oldPath后面补个路径分隔符再加文件名
                    //谁知道你传递过来的参数是f:/a还是f:/a/啊？
                    if (oldPath.endsWith(File.separator)) {
                        temp = new File(oldPath + file[i]);
                    } else {
                        temp = new File(oldPath + File.separator + file[i]);
                    }
                    //如果游标遇到文件
                    if (temp.isFile()) {
                        cin = new FileInputStream(temp).getChannel();
                        cout = new FileOutputStream(newPath + "/" + (temp.getName()).toString()).getChannel();
                        cin.transferTo(0, cin.size(), cout);
                        cout.close();
                        cin.close();
                    }
                    //如果游标遇到文件夹
                    if (temp.isDirectory()) {
                        copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                    }
                }
                // 删除内网import下的供应商图片
                /*if ("0".equals(StaticVariables.ipAddressType)) {
                    FileUtils.deleteDirectory(filelist);
                }*/
            }
        } catch (Exception e) {
            logger.error("复制整个文件夹内容操作出错");
            e.printStackTrace();
        } finally {
            // 释放资源
            if (cout != null) {
                try {
                    cout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (cin != null) {
                try {
                    cin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}