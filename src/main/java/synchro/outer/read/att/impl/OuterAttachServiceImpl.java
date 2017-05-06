package synchro.outer.read.att.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common.constant.Constant;
import common.model.UploadFile;
import common.service.UploadService;
import ses.model.ems.Expert;
import ses.model.sms.Supplier;
import synchro.outer.read.att.OuterAttachService;
import synchro.service.SynchRecordService;
import synchro.util.FileUtils;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述>导入附件实现类
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
@Service
public class OuterAttachServiceImpl implements OuterAttachService {
    
    /** 注入service */
    @Autowired
    private UploadService uploadService;
    
    /** 记录service  **/
    @Autowired
    private SynchRecordService  recordService;
    
    /**
     * 
     * @see synchro.outer.read.att.OuterAttachService#importAttach()
     */
    @Override
    public void importAttach(final File file) {
        List<UploadFile> list = FileUtils.getBeans(file, UploadFile.class); 
        if (list != null && list.size() > 0){
            for (UploadFile uploadFile : list){
                Integer count = uploadService.findCountById(uploadFile.getId(),Constant.TENDER_SYS_KEY);
                if (count > 0){
                    uploadService.updateFile(uploadFile, Constant.TENDER_SYS_KEY);
                } else {
                    uploadService.insertFile(uploadFile,Constant.TENDER_SYS_KEY);
                }
            }
            recordService.importAttach(new Integer(list.size()).toString());
        }
    }

    
    @Override
    public void importSupplierAttach(final File file) {
        List<Supplier> supplierList = getSupplier(file);
        try{
            if(null != supplierList && !supplierList.isEmpty()){
                for(Supplier supplier:supplierList){
                    List<UploadFile> list = supplier.getAttchList();
                    if (null != list && !list.isEmpty()){
                        for (UploadFile uploadFile : list){
                            Integer count = uploadService.findCountById(uploadFile.getId(),Constant.SUPPLIER_SYS_KEY);
                            if (count > 0){
                                uploadService.updateFile(uploadFile, Constant.SUPPLIER_SYS_KEY);
                            } else {
                                uploadService.insertFile(uploadFile,Constant.SUPPLIER_SYS_KEY);
                            }
                        }
                        recordService.importAttach(new Integer(list.size()).toString());
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //附件上传成功后移动文件
        FileUtils.moveFile(file);
    }


	@Override
	public void importExpertAttach(File file) {
        List<Expert> expertList = getExpert(file);
        try{
            if(null != expertList && !expertList.isEmpty()){
                for(Expert expert:expertList){
                    List<UploadFile> list = expert.getAttchList();
                    if(null != list && !list.isEmpty()){
                        for (UploadFile uploadFile : list){
                            Integer count = uploadService.findCountById(uploadFile.getId(),Constant.EXPERT_SYS_KEY);
                            if (count > 0){
                                uploadService.updateFile(uploadFile, Constant.EXPERT_SYS_KEY);
                            } else {
                                uploadService.insertFile(uploadFile,Constant.EXPERT_SYS_KEY);
                            }
                        }
                        recordService.importAttach(new Integer(list.size()).toString());
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //附件上传成功后移动文件
        FileUtils.moveFile(file);
//		 List<UploadFile> list = FileUtils.getBeans(file, UploadFile.class);
//	        if (list != null && list.size() > 0){
//	            for (UploadFile uploadFile : list){
//	                Integer count = uploadService.findCountById(uploadFile.getId(),Constant.EXPERT_SYS_KEY);
//	                if (count > 0){
//	                    uploadService.updateFile(uploadFile, Constant.EXPERT_SYS_KEY);
//	                } else {
//	                    uploadService.insertFile(uploadFile,Constant.EXPERT_SYS_KEY);
//	                }
//	            }
//	            recordService.importAttach(new Integer(list.size()).toString());
//	        }
	}

    /**
     *〈简述〉获取专家list
     *〈详细描述〉
     * @author WangHuijie
     * @param file 文件
     * @return
     */
    private List<Expert> getExpert(final File file) {
        List<Expert> expertList = FileUtils.getExpert(file, Expert.class);
        return expertList;
    }
    /**
     *
     *〈简述〉获取供应商list
     *〈详细描述〉
     * @author myc
     * @param file 文件
     * @return
     */
    private List<Supplier> getSupplier(final File file){
        List<Supplier> supplierList = FileUtils.getSupplier(file, Supplier.class);
        return supplierList;
    }
}
