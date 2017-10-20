package synchro.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ses.dao.ems.ExpertCategoryMapper;
import ses.dao.ems.ExpertMapper;
import ses.dao.ems.ExpertTitleMapper;
import ses.model.ems.Expert;
import ses.model.ems.ExpertCategory;
import ses.model.ems.ExpertTitle;
import synchro.service.SynchMilitaryExpertService;
import synchro.service.SynchRecordService;
import synchro.util.Constant;
import synchro.util.FileUtils;

import com.alibaba.fastjson.JSON;

/**
 * 
 * Description: 专家同步
 * 
 * @version 2016-9-7
 * @since JDK1.7
 */
@Service("synchMilitaryExpertService")
public class SynchMilitaryExpertServiceImpl implements SynchMilitaryExpertService {

	@Autowired
	private ExpertMapper expertMapper;

    /** 记录service  **/
    @Autowired
    private SynchRecordService  synchRecordService;
    
    /** 执业资格 **/
    @Autowired
    private ExpertTitleMapper expertTitleMapper;
    
    /** 专家品目 **/
    @Autowired
    private ExpertCategoryMapper expertCategoryMapper;

	/**
	 * 军队专家导出
	 */
	@Override
	public void militaryExpertExport(String start, String end, Date synchDate) {
		int sum = 0;
		//专家基本信息  T_SES_EMS_EXPERT
		List<Expert> expertList = expertMapper.findMilitaryExpert(start, end);
		if (expertList != null && expertList.size() > 0) {
			sum = sum + expertList.size();
			// 专家基本信息
			FileUtils.writeFile(FileUtils.getExporttFile(FileUtils.MILITARY_EXPERT_PATH_FILENAME, 34), JSON.toJSONString(expertList));
			List<ExpertTitle> titleList = new ArrayList<ExpertTitle>();
			List<ExpertCategory> expertCategoryList = new ArrayList<ExpertCategory>();
			for (Expert expert : expertList) {
				//执业资格  T_SES_EMS_EXPERT_TITLE
				List<ExpertTitle> tLIst = expertTitleMapper.selectByExpertId(expert.getId());
				titleList.addAll(tLIst);
				//参评类别
				List<ExpertCategory> caList = expertCategoryMapper.findByExpertId(expert.getId());
				expertCategoryList.addAll(caList);
			}
			if(titleList != null && titleList.size() > 0){
				FileUtils.writeFile(FileUtils.getExporttFile(FileUtils.MILITARY_EXPERT_TITLE_PATH_FILENAME, 34), JSON.toJSONString(titleList));
			}
			if(expertCategoryList != null && expertCategoryList.size() > 0){
				FileUtils.writeFile(FileUtils.getExporttFile(FileUtils.MILITARY_EXPERT_CATEGORY_PATH_FILENAME, 34), JSON.toJSONString(expertCategoryList));
			}
		}
		synchRecordService.synchBidding(new Date(), sum + "",Constant.DATE_SYNCH_MILITARY_EXPERT,Constant.OPER_TYPE_EXPORT,Constant.MILITARY_EXPERT_COMMIT);
	}

	@Override
	public void militaryExpertImport(File file) {
		int num = 0;
        for (File file2 : file.listFiles()) {
            //专家基本信息
            if(file2.getName().contains(FileUtils.MILITARY_EXPERT_PATH_FILENAME)){
                List<Expert> expertList = FileUtils.getBeans(file2, Expert.class);
                if(expertList != null && expertList.size() > 0){
                    num = expertList.size();
                    for (Expert expert : expertList) {
						Expert expert2 = expertMapper.selectByPrimaryKey(expert.getId());
						if(null == expert2){
							expertMapper.insertSelective(expert);
						}else{
							expertMapper.updateByPrimaryKeySelective(expert);
						}
					}
                }
            }
            //专家执业资格
            if(file2.getName().contains(FileUtils.MILITARY_EXPERT_TITLE_PATH_FILENAME)){
            	List<ExpertTitle> titleList = FileUtils.getBeans(file2, ExpertTitle.class);
                if(titleList != null && titleList.size() > 0){
                	for (ExpertTitle expertTitle : titleList) {
                		ExpertTitle expertTitle2 = expertTitleMapper.selectByPrimaryKey(expertTitle.getId());
                		if(null == expertTitle2){
                			expertTitleMapper.insertSelective(expertTitle);
                		}else{
                			expertTitleMapper.updateByPrimaryKeySelective(expertTitle);
                		}
					}
                }
            }
            //专家品目信息
            if(file2.getName().contains(FileUtils.MILITARY_EXPERT_CATEGORY_PATH_FILENAME)){
            	List<ExpertCategory> expertCategoryList = FileUtils.getBeans(file2, ExpertCategory.class);
                if(expertCategoryList != null && expertCategoryList.size() > 0){
                	for (ExpertCategory expertCategory : expertCategoryList) {
						expertCategoryMapper.insertSelective(expertCategory);
					}
                }
            }
        }
        synchRecordService.synchBidding(new Date(), num+"", Constant.DATE_SYNCH_MILITARY_EXPERT, Constant.OPER_TYPE_IMPORT, Constant.MILITARY_EXPERT_COMMIT_IMPORT);
	}
}
