package synchro.task;

import java.io.File;
import java.util.List;

import com.alibaba.fastjson.JSON;

import ses.model.sms.Supplier;
import synchro.util.FileUtils;

public class TestJson {

    public static void main(String[] args) {
        
        File file = new File("D:/web/sync/import/1482838241658_c_supplier.dat");
        String content  = FileUtils.readFile(file);
        List<Supplier> list = JSON.parseArray(content,Supplier.class);
        System.out.println(list.size());
        System.out.println(list.get(0).getLoginName());
    }
}
