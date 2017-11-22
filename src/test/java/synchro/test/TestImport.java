package synchro.test;

import bss.util.FileUtil;
import bss.util.PropUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import synchro.inner.read.supplier.InnerSupplierService;
import synchro.util.FileUtils;

import javax.annotation.Resource;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Description: 编写导入测试类
 *
 * @param
 * @author Easong
 * @version 2017/10/11
 * @since JDK1.7
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:spring-mvc.xml", "classpath:spring-mybatis.xml", "classpath:spring-redis.xml", "classpath:spring-solr.xml"})
@WebAppConfiguration
public class TestImport {

    @Resource
    private InnerSupplierService innerSupplierService;

    /**
     * Description: 测试新提交供应商导入导出
     *
     * @param
     * @author Easong
     * @version 2017/10/16
     * @since JDK1.7
     */
    @Test
    public void testSupplierNewSubmitImport() {
        // 获取本地文件
        File file = FileUtils.getImportFile();
        // 遍历文件
        if (file != null && file.exists()) {
            File[] files = file.listFiles();
            for (File f : files) {
                //　调用新提交供应商导入方法
                innerSupplierService.importSupplierInfo(f);
            }
        }
    }

    /**
     * Description: 测试注销供应商导入导出
     *
     * @param
     * @author Easong
     * @version 2017/10/16
     * @since JDK1.7
     */
    @Test
    public void testLogoutSupplierImport() {
        // 获取本地文件
        File file = FileUtils.getImportFile();
        // 遍历文件
        if (file != null && file.exists()) {
            File[] files = file.listFiles();
            for (File f : files) {
                //　调用新提交供应商导入方法
                innerSupplierService.importLogoutSupplier(f);
            }
        }
    }

    /**
     * Description: 测试图片导入
     *
     * @param
     * @author Easong
     * @version 2017/10/30
     * @since JDK1.7
     */
    @Test
    public void testImportPic() {
        Date date=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        cale.add(Calendar.DAY_OF_MONTH, -1);
        String src = sdf.format(cale.getTime());//昨天的文件夹名字
        // 数据同步导入目录: /web/sync/import
        String supplier = PropUtil.getProperty("file.sync.base") + PropUtil.getProperty("file.sync.import") + "/" + src;//供应商图片
        // 供应商图片上传目录：/web/attach/uploads/supplier
        String supplierPath = PropUtil.getProperty("file.base.path") + PropUtil.getProperty("file.supplier.system.path") + "/" + src;//供应商专路径
        // 完成后备份目录 /web/sync/finish/supplier_pic
        String bakPath = PropUtil.getProperty("file.sync.base") + PropUtil.getProperty("file.sync.finish") + "/" + "supplier_pic";
        FileUtil.copyFolder(supplier, supplierPath, bakPath);
    }

    /**
     * Description: 测试图片导出
     *
     * @param
     * @author Easong
     * @version 2017/10/30
     * @since JDK1.7
     */
    @Test
    public void testExportPic() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar cale = Calendar.getInstance();
        cale.setTime(date);
        cale.add(Calendar.DAY_OF_MONTH, -1);
        String src = sdf.format(cale.getTime());//昨天的文件夹名字
        // 供应商图片上传目录：/web/attach/uploads/supplier
        String supplierPath = PropUtil.getProperty("file.base.path") + PropUtil.getProperty("file.supplier.system.path") + "/" + src;//供应商所有图片
        // 数据同步导出目录: /web/sync/export
        String synchExport = PropUtil.getProperty("file.sync.base") + PropUtil.getProperty("file.sync.export") + "/" + src;
        FileUtil.copyFolder(supplierPath, synchExport, null);
    }

    /**
     *
     * Description: 测试供应商退回导入
     *
     * @author Easong
     * @version 2017/11/9
     * @param []
     * @since JDK1.7
     */
    @Test
    public void testSupplierBackImport() {
        //　调用新提交供应商退回修改导入方法
        innerSupplierService.importInner(null, null);
    }
}
