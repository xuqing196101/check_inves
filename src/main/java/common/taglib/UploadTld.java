package common.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;

import common.constant.Constant;
import common.exception.TagTldException;
import ses.util.PropUtil;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述> 文件上传自定义标签
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
/**
 * 版权：(C) 版权所有 
 * <简述>
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
/**
 * 版权：(C) 版权所有 
 * <简述>
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
public class UploadTld extends TagSupport {

    /**
     * @Fields serialVersionUID : TODO(目的和意义)
     */
    private static final long serialVersionUID = 736631095730332534L;
    /** 异常提示信息 */
    private final static String  ERROR_MSG = "系统找不到对应的值";
    
    /** 基础单位 */
    private final static int UNIT = 1024;
    
    /** 上传按钮名称 */
    private String uloadButton = "开始上传";
    
    /** 选择按钮名称 */
    private String uClass = "wuexample";
    
    /** 总样式 */
    private String uListClass = "uploaderlist";
    
    /** 按钮样式 */
    private String btnClass = "btns";
    
    /** 上传按钮样式 */
    private String upBtnClass = "btn webuploaderbutton";
    
    /** 业务ID */
    private String businessId;
    
    /** 业务类型Id */
    private String typeId;
    
    /** 对应系统key */
    private String sysKey;
    
    /** 是否上传多个文件 */
    private boolean  multiple = false;
    
    /** 文件扩展名 **/
    private String exts;
    
    /** 扩展名 */
    private String extension = PropUtil.getProperty("file.upload.extension");
    
    /** 网页内容 */
    private String mimeTypes ="*";
    
    /** 上传文件是否重复 */
    private boolean duplicate = false;
    
    /** 是否自动上传 */
    private boolean auto = false;
    
    /**按钮名称**/
    private String buttonName;
    
    /** 唯一的标识 */
    private String id;
    /** 一组按钮的标识,必须为多个按钮的id组成,如:one,two */
    private String groups;
    
    /** 分块文件大小 */
    private long chunSize = Long.parseLong(PropUtil.getProperty("file.upload.chunk.fileSize")) * UNIT;
    
    /** 限制最大文件的大小 */
    private long limitFile = Long.parseLong(PropUtil.getProperty("file.upload.maxFileSize")) * UNIT * UNIT * UNIT; 
    
    /** 单个文件的大小 */
    private long singLimitFie = Long.parseLong(PropUtil.getProperty("file.upload.singleFileSize")) * UNIT * UNIT ;
   
    /** 单个文件大小 **/
    private Long singleFileSize;
    
    @Override
    public int doStartTag() throws JspException  {
        //系统key值判断
        if (StringUtils.isNotBlank(sysKey)){
            int key = Integer.parseInt(sysKey);
            if (!Constant.fileSystem.containsKey(key)){
                try {
                    throw new  TagTldException(ERROR_MSG);
                } catch (TagTldException e) {
                    e.printStackTrace();
                } 
            }
        }
        //扩展名
        if (StringUtils.isNotBlank(extension)) {
            String extensions = PropUtil.getProperty("file.upload.extension");
            String []  extenArray = extension.split(",");
            for (String exten : extenArray) {
                if (!extensions.contains(exten)){
                    try {
                        throw new TagTldException(ERROR_MSG);
                    } catch (TagTldException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doEndTag() throws JspException {
        JspWriter out = pageContext.getOut();
       try {
            out.println("<input id='id' type=\"hidden\"  value=" + id + " />");
            out.println("<input type=\"hidden\"  class=\"web_uploader_class\" />");
            out.println("<input id='groupId' type=\"hidden\"  value=" + groups + " />");
            out.println("<input id='"+id+"_businessId' type=\"hidden\"  value=" + businessId + " />");
            out.println("<input id='"+id+"_typeId'  type=\"hidden\"  value=" + typeId + " />");
            out.println("<input id='"+id+"_sysKeyId' type=\"hidden\"  value=" + sysKey + " />");
            out.println("<input id='chunSizeId' type=\"hidden\"  value=" + chunSize + " />");
            out.println("<input id='maxSizeId' type=\"hidden\"  value=" + limitFile + " />");
            out.println("<input id='singlSizeId' type=\"hidden\"  value=" + singLimitFie + " />");
            out.println("<input id='"+id+"_multipleId' type=\"hidden\"  value=" + multiple + " />");
            out.println("<input id='extensionId' type=\"hidden\"  value=" + extension + " />");
            out.println("<input id='"+id+"_extId' type=\"hidden\"  value=" + exts + " />");
            out.println("<input id='mimeTypesId' type=\"hidden\"  value=" + mimeTypes + " />");
            out.println("<input id='"+id+"_duplicateId' type=\"hidden\"  value=" + duplicate + " />");
            out.println("<input id='"+id+"_autoId' type=\"hidden\"  value=" + auto + " />");
            out.println("<input id='"+id+"_btnNameId' type=\"hidden\"  value=" + buttonName + " />");
            out.println("<input id='"+id+"_singFileSize' type=\"hidden\"  value=" + singleFileSize + " />");
            out.println("<div id=\"uploaderId\" class=\"" + uClass + "\">");
            out.println("<div class=\"" + btnClass + "\">");
            out.println("<div id=\""+id+"_picker\"></div>");
            if (!auto){
                out.println("<button id=\""+id+"_ctlBtn\" type=\"button\" style='marginleft:10px' class=\"" + upBtnClass + " \">" + uloadButton + "</button>");
            }
            out.println("</div>");
            out.println("<ul id=\""+id+"_thelist\" class=\"" + uListClass + "\"></ul>");
            out.println("</div> ");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }

    @Override
    public void release() {
        id = null;
        businessId = null;
        typeId = null;
        sysKey = null;
        super.release();
    }
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    
    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getSysKey() {
        return sysKey;
    }

    public void setSysKey(String sysKey) {
        this.sysKey = sysKey;
    }

    public String getUloadButton() {
        return uloadButton;
    }

    public void setUloadButton(String uloadButton) {
        this.uloadButton = uloadButton;
    }

    public String getuClass() {
        return uClass;
    }

    public void setuClass(String uClass) {
        this.uClass = uClass;
    }

    public String getuListClass() {
        return uListClass;
    }

    public void setuListClass(String uListClass) {
        this.uListClass = uListClass;
    }

    public String getBtnClass() {
        return btnClass;
    }

    public void setBtnClass(String btnClass) {
        this.btnClass = btnClass;
    }

    public String getUpBtnClass() {
        return upBtnClass;
    }

    public void setUpBtnClass(String upBtnClass) {
        this.upBtnClass = upBtnClass;
    }

    public boolean isMultiple() {
        return multiple;
    }

    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getMimeTypes() {
        return mimeTypes;
    }

    public void setMimeTypes(String mimeTypes) {
        this.mimeTypes = mimeTypes;
    }

    public boolean isDuplicate() {
        return duplicate;
    }

    public void setDuplicate(boolean duplicate) {
        this.duplicate = duplicate;
    }

    public boolean isAuto() {
        return auto;
    }

    public void setAuto(boolean auto) {
        this.auto = auto;
    }

    public String getExts() {
        return exts;
    }

    public void setExts(String exts) {
        this.exts = exts;
    }

    public String getButtonName() {
        return buttonName;
    }

    public void setButtonName(String buttonName) {
        this.buttonName = buttonName;
    }

    public Long getSingleFileSize() {
        return singleFileSize;
    }

    public void setSingleFileSize(Long singleFileSize) {
        this.singleFileSize = singleFileSize;
    }

    

    
    
    
}
