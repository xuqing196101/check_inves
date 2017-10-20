package extract.service.expert;

/**
 * 
 * Description: 专家自动抽取
 * 
 * @author zhang shubin
 * @version 2016-9-7
 * @since JDK1.7
 */
public interface AutoExtractService {
    
    /**
     * 
     * Description: 专家语音抽取
     * 
     * @author zhang shubin
     * @data 2017年10月19日
     * @param 
     * @return
     */
    public String expertAutoExtract(String projectId);

    /**
     * 
     * Description: 专家抽取结果上传
     * 
     * @author zhang shubin
     * @data 2017年10月17日
     * @param 
     * @return
     */
    public String expertResultUpload(String result) throws Exception;
    
}
