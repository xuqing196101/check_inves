package common.bean;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.fileupload.FileItem;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述> 文件bean对象
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public class MultipartFileBean {

    /** 该请求是否是multipart */
    private boolean isMultipart;
    /** 任务ID */
    private String id;
    /** 总分片数量 */
    private int chunks;
    /** 当前为第几块分片 */
    private int chunk;
    /** 当前分片大小 */
    private long size = 0L;
    /** 文件名 */
    private String fileName;
    /** 分片对象 */
    private FileItem fileItem;
    /** 请求中附带的自定义参数 */
    private ConcurrentHashMap<String, String> param = new ConcurrentHashMap<>();
    
    public boolean isMultipart() {
        return isMultipart;
    }
    public void setMultipart(boolean isMultipart) {
        this.isMultipart = isMultipart;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getChunks() {
        return chunks;
    }
    public void setChunks(int chunks) {
        this.chunks = chunks;
    }
    public int getChunk() {
        return chunk;
    }
    public void setChunk(int chunk) {
        this.chunk = chunk;
    }
    public long getSize() {
        return size;
    }
    public void setSize(long size) {
        this.size = size;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public FileItem getFileItem() {
        return fileItem;
    }
    public void setFileItem(FileItem fileItem) {
        this.fileItem = fileItem;
    }
    public ConcurrentHashMap<String, String> getParam() {
        return param;
    }
    public void setParam(ConcurrentHashMap<String, String> param) {
        this.param = param;
    }
    
    
    

}
