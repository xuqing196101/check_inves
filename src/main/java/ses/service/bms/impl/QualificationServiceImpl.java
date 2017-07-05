package ses.service.bms.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;

import common.constant.StaticVariables;
import ses.dao.bms.QualificationMapper;
import ses.model.bms.CategoryQua;
import ses.model.bms.Qualification;
import ses.service.bms.QualificationService;
import ses.util.PropUtil;
import synchro.service.SynchRecordService;
import synchro.util.Constant;
import synchro.util.FileUtils;

/**
 * 
 * 版权：(C) 版权所有 
 * <简述> Qualification Service  
 * <详细描述>
 * @author   myc
 * @version  
 * @since
 * @see
 */
@Service
public class QualificationServiceImpl implements QualificationService {
    
    /** 资质Mapper **/
    @Autowired
    private QualificationMapper mapper;
    /**
     * 同步service
     */
    @Autowired
    private SynchRecordService recordService;
    
    /**
     * 
     * @see ses.service.bms.QualificationService#findList(java.lang.Integer, ses.model.bms.Qualification)
     * @param  pageNum 当前页
     * @param pageSize 每页显示条数(默认为配置文件数据,支持自定义条数)
     * @param  name 查询条件
     * @param  type 类型
     * @return
     */
    @Override
    public List<Qualification> findList(Integer pageNum, Integer pageSize, String name, Integer type) {
        
        if (pageNum == null){
            pageNum = 1;
        }
        if(pageSize==null){
            pageSize = Integer.parseInt(PropUtil.getProperty("pageSize"));
        }
        PageHelper.startPage(pageNum, pageSize);
        return mapper.findList(name, type);
    }
    
    /**
     * 
     * @see ses.service.bms.QualificationService#save(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public Qualification save(String type, String name, String operaType, String id) {
        
        Qualification qualification = new Qualification();
        if (operaType.equals(StaticVariables.OPER_ADD_TYPE)){
            qualification.setCreatedAt(new Date());
            qualification.setIsDeleted(StaticVariables.ISNOT_DELETED);
            qualification.setType(Integer.parseInt(type));
            qualification.setName(name);
            mapper.save(qualification);
        }
        
        if (operaType.equals(StaticVariables.OPER_EDIT_TYPE)){
            qualification.setUpdatedAt(new Date());
            qualification.setName(name);
            qualification.setId(id);
            mapper.update(qualification);
        }
        
        return  qualification;
    }
    
    /**
     * 
     * @see ses.service.bms.QualificationService#update(ses.model.bms.Qualification)
     */
    @Override
    public void update(Qualification qualification) {
        mapper.update(qualification);
    }
    
    /**
     * 
     * @see ses.service.bms.QualificationService#getQualification(java.lang.String)
     */
    @Override
    public Qualification getQualification(String id) {
       
        return mapper.getQualification(id);
    }
    
    /**
     * 
     * @see ses.service.bms.QualificationService#del(java.lang.String)
     */
    @Override
    public String del(String ids) {
        if (!StringUtils.isNotBlank(ids)){
            return StaticVariables.FAILED;
        }
        if (ids.contains(StaticVariables.COMMA_SPLLIT)){
            String [] idArray = ids.split(StaticVariables.COMMA_SPLLIT);
            for (String id : idArray){
                boolean flag = update(id);  
                if (!flag){
                    return StaticVariables.FAILED;
                }
            }
        } else {
            boolean flag = update(ids);
            if (!flag){
                return StaticVariables.FAILED;
            }
        }
        return StaticVariables.SUCCESS;
    }
    
    /**
     * 
     *〈简述〉根据id业务删除
     *〈详细描述〉
     * @author myc
     * @param id 主键Id
     * @return true 成功, false 失败
     */
    private boolean  update(String id){
        
        boolean flag = true;
        Qualification qualification = new Qualification();
        qualification.setUpdatedAt(new Date());
        qualification.setIsDeleted(StaticVariables.IS_DELETED);
        qualification.setId(id);
        try {
            mapper.update(qualification);
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }
	/**
	 * 实现导出目录资质关联表录 根据时间范围
	 */
	@Override
	public boolean exportQualification(String start, String end, Date synchDate) {
		//根据时间获取创建 数据范围
		List<Qualification> createList=mapper.selectByCreatedAt(start, end);
		List<Qualification> updateList=mapper.selectByUpdatedAt(start, end);
		List<Qualification> list=new ArrayList<>();
		if(createList!=null  && createList.size()>0){
			list.addAll(createList);
		}
		if(updateList!=null  && updateList.size()>0){
			list.addAll(updateList);
		  }
		if(list!=null  && !list.isEmpty()){
			FileUtils.writeFile(FileUtils.getExporttFile(FileUtils.C_SYNCH_QUALIFICATION, 22),JSON.toJSONString(list));
		}
	    if(list!=null){
	    	recordService.synchBidding(synchDate, String.valueOf(list.size()), synchro.util.Constant.DATA_SYNCH_QUALIFICATION, synchro.util.Constant.OPER_TYPE_EXPORT, synchro.util.Constant.IMPORT_COMMIT_SYNCH_QUALIFICATION);
	    }
		return false;
	}
	/**
	 * 导入目录资质关联表录数据 
	 */
	private boolean importDate(File file) {
		List<Qualification> list = FileUtils.getBeans(file, Qualification.class); 
		 if(list!=null  && !list.isEmpty()){
			 for(Qualification category:list){
			 Integer isExist=mapper.countByPrimaryKey(category.getId());
			  if(isExist!=null && isExist >0){
				  mapper.update(category);
			  }else{
				  mapper.insertSelective(category);
			  }
			 }
			 recordService.synchBidding(new Date(), list.size()+"", synchro.util.Constant.DATA_SYNCH_QUALIFICATION, synchro.util.Constant.OPER_TYPE_IMPORT, synchro.util.Constant.EXPORT_COMMIT_SYNCH_QUALIFICATION);
		 }
		return false;
	}
	/**
	 * 导入目录资质关联表录数据 
	 */
	@Override
	public boolean importQualification(String synchType,File file) {
		if(synchType.contains(Constant.DATA_SYNCH_QUALIFICATION)){
			if (Constant.FILE_SYNCH_QUALIFICATION_PATH.equals(file.getName())) {
				for (File file2 : file.listFiles()) {
					if (file2.getName().contains(FileUtils.C_SYNCH_QUALIFICATION)) {
						importDate(file2);
					}
				}
			}
		}
		return false;
	}
}
