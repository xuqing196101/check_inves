package ses.util;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ses.model.bms.DictionaryData;
import ses.service.bms.DictionaryDataServiceI;

/**
 * 版权：(C) 版权所有 
 * <简述>获取数据字典数据
 * <详细描述>
 * @author   Ye MaoLin
 * @version  
 * @since
 * @see
 */
@Component
public class DictionaryDataUtil {
    
    public static DictionaryData dictionaryData = new DictionaryData();
    
    @Autowired
    private DictionaryDataServiceI dictionaryDataService;
    
    private static DictionaryDataUtil dictionaryDataUtil;
    
    public void setDdService(DictionaryDataServiceI dictionaryDataService){
        this.dictionaryDataService = dictionaryDataService;
    }
    
    @PostConstruct 
    public void init(){
        dictionaryDataUtil = this;
        dictionaryDataUtil.dictionaryDataService = this.dictionaryDataService;
    }
    
    /**
     *〈简述〉根据code获取数据字典id
     *〈详细描述〉
     * @author Ye MaoLin
     * @param code 数据字典编码
     * @return id
     * @throws Exception
     */
    public static String getId(String code) {
        String id = "";
        dictionaryData.setCode(code);
        List<DictionaryData> dds= dictionaryDataUtil.dictionaryDataService.find(dictionaryData);
        if (dds != null && dds.size() > 0) {
            id = dds.get(0).getId();
        } 
        return id;
    }
    
    /**
     *〈简述〉根据code获取数据字典对象
     *〈详细描述〉
     * @author Ye MaoLin
     * @param code 编码
     * @return
     */
    public static DictionaryData get(String code){
        DictionaryData dd = null;
        dictionaryData.setCode(code);
        List<DictionaryData> dds= dictionaryDataUtil.dictionaryDataService.find(dictionaryData);
        if (dds != null && dds.size() > 0) {
            dd = dds.get(0);
        } 
        return dd;
    }
    
    /**
     *〈简述〉根据类型查询数据字典集合
     *〈详细描述〉
     * @author Ye MaoLin
     * @param kind
     * @return
     */
    public static List<DictionaryData> find(Integer kind){
        dictionaryData.setKind(kind);
        List<DictionaryData> dds= dictionaryDataUtil.dictionaryDataService.find(dictionaryData);
        return dds;
    }
}
