package common.exception;


/**
 * 
 * 版权：(C) 版权所有 
 * <简述> 自定义标签异常
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public class TagTldException extends Exception {

    /**
     * @Fields serialVersionUID : TODO(目的和意义)
     */
    private static final long serialVersionUID = 6693093429203768718L;
    
    /**
     * @param msg 提示异常
     */
    public TagTldException (String msg){
        super(msg);
    }

}
