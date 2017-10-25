package synchro.task.outer.importTask;

import bss.service.ob.OBProductService;
import bss.service.ob.OBProjectServer;
import bss.service.ob.OBSupplierService;
import common.constant.StaticVariables;
import iss.service.ps.DataDownloadService;
import iss.service.ps.TemplateDownloadService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ses.service.bms.CategoryParameterService;
import ses.service.bms.CategoryService;
import ses.service.bms.QualificationService;
import ses.service.sms.SMSProductLibService;
import ses.util.DictionaryDataUtil;
import synchro.inner.read.InnerFilesRepeater;
import synchro.inner.read.expert.InnerExpertService;
import synchro.inner.read.supplier.InnerSupplierService;
import synchro.outer.back.service.supplier.OuterSupplierService;
import synchro.outer.read.att.OuterAttachService;
import synchro.service.SynchRecordService;
import synchro.util.Constant;
import synchro.util.DateUtils;
import synchro.util.FileUtils;
import synchro.util.MultiTaskUril;
import synchro.util.OperAttachment;

import java.io.File;

/**
 * 定时 外网 导入 数据
 *
 * @author YHL
 */
@Component("outerMultiImportTask")
public class MultiImportTask {
    @Autowired
    /** 附件导入 **/
    private OuterAttachService attachService;
    /**
     * 同步 竞价定型产品
     **/
    @Autowired
    private OBProductService OBProductService;
    /**
     * 竞价供应商
     **/
    @Autowired
    private OBSupplierService OBSupplierService;
    /**
     * 竞价信息
     **/
    @Autowired
    private OBProjectServer OBProjectServer;
    /**
     * 同步供应商数据service
     **/
    @Autowired
    private InnerSupplierService innerSupplierService;
    /**
     * 产品库
     **/
    @Autowired
    private SMSProductLibService smsProductLibService;
    /**
     * 产品目录
     **/
    @Autowired
    private CategoryService categoryService;
    /**
     * 产品目录参数
     **/
    @Autowired
    private CategoryParameterService categoryParameterService;
    /**
     * 门户模板管理
     **/
    @Autowired
    private TemplateDownloadService templateDownloadService;
    @Autowired
    private InnerExpertService innerExpertService;
    /**
     * 资料数据
     **/
    @Autowired
    private DataDownloadService dataDownloadService;
    /**
     * 产品资质
     **/
    @Autowired
    private QualificationService qualificationService;
    /**
     * 文件导入
     */
    @Autowired
    private InnerFilesRepeater fileRepeater;

    // 导入导出记录Service
    @Autowired
    private SynchRecordService recordService;

    // 供应商导出Service
    @Autowired
    private OuterSupplierService outerSupplierService;

    /**
     * 实现 定时导入 数据方法
     */
    public void importTask() {
        // 外网
        if ("1".equals(StaticVariables.ipAddressType)) {
            fileRepeater.initFiles();

            /**
             * 供应商注销导出
             */
            // 导出
            String startTime = MultiTaskUril.getSynchDate(Constant.SYNCH_LOGOUT_SUPPLIER, recordService);
            if (StringUtils.isNotBlank(startTime)) {
                startTime = DateUtils.getCalcelDate(startTime);
                String endTime = DateUtils.getCurrentTime();
                outerSupplierService.selectLogoutSupplierOfExport(startTime, endTime);
            }

            /** 内网导入 **/
            File file = FileUtils.getImportFile();
            if (file != null && file.exists()) {
                File[] files = file.listFiles();
                for (File f : files) {
                    if (f.isDirectory()) {
                        // 竞价定型产品导入
                        String result = DictionaryDataUtil.getId(Constant.DATE_SYNCH_BIDDING_PRODUCT);
                        /*	if (StringUtils.isNotBlank(result)) {
							*//** 竞价定型产品导入 只能是外网导入 **//*
							if (f.getName().equals(Constant.PRODUCT_FILE_EXPERT)) {
								if (f.isDirectory()) {
									for (File file2 : f.listFiles()) {
										// 判断文件名是否是 竞价产品 创建 数据名称
										// 判断是否有竞价产品 更新数据名称
										if (file2.isDirectory()) {
											if (file2.getName()
													.contains(FileUtils.C_OB_PRODUCT_FILENAME)
													|| file2.getName().contains(FileUtils.M_OB_PRODUCT_FILENAME)) {
												OBProductService.importProduct(file2);
											}
										}
									}
									if (f.getName().equals(
											Constant.PRODUCT_FILE_EXPERT)) {
										OperAttachment.moveFolder(f);
									}
								}
							}
						}*/
                        // 竞价供应商导入
						/*result = DictionaryDataUtil
								.getId(Constant.DATE_SYNCH_BIDDING_SUPPLIER);
						if (StringUtils.isNotBlank(result)) {
							*//** 竞价供应商导入 只能是外网导入内网 数据 **//*
							if (f.getName().equals(
									Constant.SUPPLIER_FILE_EXPERT)) {
								if (f.isDirectory()) {
									for (File file3 : f.listFiles()) {
										if (file3.isDirectory()) {
											if (file3
													.getName()
													.contains(
															FileUtils.C_OB_SUPPLIER_FILENAME)
													|| file3.getName()
															.contains(
																	FileUtils.M_OB_SUPPLIER_FILENAME)) {
												OBSupplierService
														.importSupplier(file3);
											}
										}
									}

									if (f.getName().equals(
											Constant.SUPPLIER_FILE_EXPERT)) {
										OperAttachment.moveFolder(f);
									}
								}
							}
						}*/
                        // 竞价信息导入
						/*result = DictionaryDataUtil
								.getId(Constant.DATA_TYPE_BIDDING_CODE);
						if (StringUtils.isNotBlank(result)) {
							*//** 竞价信息导入 只能是外网导入内网 **//*
							if (f.getName().equals(Constant.PROJECT_EXPERT)) {
								if (f.isDirectory()) {
									for (File file2 : f.listFiles()) {
										if (file2.isDirectory()) {
											// 判断文件名是否是 竞价信息 数据名称
											if (file2
													.getName()
													.contains(
															FileUtils.C_OB_PROJECT_STATUS_FILENAME)) {
												OBProjectServer
														.importProject(file2);
											}
											// 判断文件是否是竞价信息 附件文件
											if (file2
													.getName()
													.contains(
															FileUtils.C_OB_PROJECT_FILE_FILENAME)) {
												OBProjectServer
														.importFile(
																file2,
																common.constant.Constant.OB_PROJECT_SYS_KEY);
											}
										}
									}
								}
							}
							if (f.getName()
									.equals(Constant.PROJECT_FILE_EXPERT)) {
								if (f.isDirectory()) {
									for (File file2 : f.listFiles()) {
										if (file2.isDirectory()) {
											OperAttachment
													.moveToPathFolder(
															file2,
															FileUtils.BASE_ATTCH_PATH
																	+ FileUtils.OB_PROJECT_PATH);
										}
									}
								}
							}
						}*/
                        /** 产品库 **/
						/*result = DictionaryDataUtil
								.getId(Constant.SYNCH_PRODUCT_LIBRARY);
						if (StringUtils.isNotBlank(result)) {
							*//** 产品库管理导入 **//*
							if (f.getName().equals(
									Constant.OUTER_PRODUCT_LIBRARY_EXPERT)) {
								if (f.isDirectory()) {
									for (File file2 : f.listFiles()) {
										if (file2.isDirectory()) {
											// 判断文件名是否是 数据名称
											if (file2
													.getName()
													.contains(
															FileUtils.C_SYNCH_OUTER_PRODUCT_LIBRARY)) {
												smsProductLibService
														.importAddProjectData(file2);
											}
											// 判断文件是否是 附件文件
											if (file2
													.getName()
													.contains(
															FileUtils.C_SYNCH_OUTER_FILE_PRODUCT_LIBRARY)) {
												OBProjectServer
														.importFile(
																file2,
																common.constant.Constant.SUPPLIER_SYS_KEY);
											}
										}
									}
								}
							}
							// 图片移动
							if (f.getName().equals(
									Constant.OUTER_FILE_PRODUCT_LIBRARY_EXPERT)) {
								if (f.isDirectory()) {
									for (File file2 : f.listFiles()) {
										if (file2.isDirectory()) {
											OperAttachment
													.moveToPathFolder(
															file2,
															FileUtils.BASE_ATTCH_PATH
																	+ FileUtils.SUPPLIER_ATTFILE_PATH);
										}
									}
								}
							}
						}*/
                        // 产品目录管理
                        result = DictionaryDataUtil.getId(Constant.SYNCH_CATEGORY);
                        if (StringUtils.isNotBlank(result)) {
                            /** 产品目录管理 只能是外网 导入 **/
                            if (f.getName().equals(Constant.T_SES_BMS_CATEGORY_PATH)) {
                                if (f.isDirectory()) {
                                    // 便利文件夹 目录
                                    for (File file2 : f.listFiles()) {
                                        if (file2.isDirectory()) {
                                            // 判断文件名是否是 导出 创建 数据名称
                                            if (file2.getName().contains(FileUtils.C_CATEGORY_FILENAME)) {
                                                categoryService.importCategory(file2);
                                            }
                                            // 判断文件是否是 产品的 附件文件
                                            if (file2.getName().contains(FileUtils.C_FILE_CATEGORY_FILENAME)) {
                                                OBProjectServer.importFile(file2, common.constant.Constant.TENDER_SYS_KEY);
                                            }
                                        }
                                    }
                                }
                            }
                            if (f.getName().equals(Constant.FILE_T_SES_BMS_CATEGORY_PATH)) {
                                if (f.isDirectory()) {
                                    for (File file2 : f.listFiles()) {
                                        if (file2.isDirectory()) {
                                            OperAttachment.moveToPathFolder(file2,
                                                    FileUtils.BASE_ATTCH_PATH + FileUtils.TENDER_ATTFILE_PATH);
                                        }
                                    }
                                }
                            }
                        }
                        // 产品目录参数管理
                        result = DictionaryDataUtil.getId(Constant.SYNCH_CATE_PARAMTER);
                        if (StringUtils.isNotBlank(result)) {
                            /** 产品目录参数管理 只能是外网导入 **/
                            if (f.getName().equals(Constant.T_SES_BMS_CATEGORY_PARAMTER_PATH)) {
                                if (f.isDirectory()) {
                                    // 遍历文件夹中的所有文件
                                    for (File file2 : f.listFiles()) {
                                        if (file2.isDirectory()) {
                                            // 判断文件名是否是导出创建数据名称
                                            if (file2.getName().contains(FileUtils.C_CATEGORY_PARAMTER_FILENAME)) {
                                                categoryParameterService.importCategoryParmter(file2);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        /**目录资质关联表*/
                        categoryService.importCategoryQua(Constant.DATA_SYNCH_CATEGORY_QUA, f);
                        /** 产品资质表*/
                        qualificationService.importQualification(Constant.DATA_SYNCH_QUALIFICATION, f);
						/*// 资料 管理 导入
						result = DictionaryDataUtil.getId(Constant.SYNCH_DATA);
						if (StringUtils.isNotBlank(result)) {
							// 判断 是否有资料管理的数据 文档
							if (f.getName().equals(
									Constant.ISS_PS_DATA_DOWNLOAD_PATH)) {
								if (f.isDirectory()) {
									for (File file2 : f.listFiles()) {
										if (file2.isDirectory()) {
											// 判断 是否有 资料管理 数据
											if (file2
													.getName()
													.contains(
															FileUtils.C_DATA_DOWNLOAD_FILENAME)) {
												dataDownloadService
														.importDate(file2);
											}
											// 判断 是否有 附件 数据
											if (file2
													.getName()
													.contains(
															FileUtils.C_FILE_DATA_DOWNLOAD_FILENAME)) {
												OBProjectServer
														.importFile(
																file2,
																common.constant.Constant.TENDER_SYS_KEY);
											}
										}
									}
								}
							}
							if (f.getName().equals(
									Constant.FILE_ISS_PS_DATA_DOWNLOAD_PATH)) {
								if (f.isDirectory()) {
									for (File file2 : f.listFiles()) {
										if (file2.isDirectory()) {
											OperAttachment
													.moveToPathFolder(
															file2,
															FileUtils.BASE_ATTCH_PATH
																	+ FileUtils.TENDER_ATTFILE_PATH);
										}
									}
								}
							}
						}*/
                        // 门户模板 管理 导入
						/*result = DictionaryDataUtil
								.getId(Constant.SYNCH_TEMPLATE_DOWNLOAD);
						if (StringUtils.isNotBlank(result)) {
							*//** 门户模板管理 只能是外网导入 **//*
							if (f.getName().equals(
									Constant.T_ISS_PS_TEMPLATE_DOWNLOAD_PATH)) {
								if (f.isDirectory()) {
									// 遍历文件夹中的所有文件
									for (File file2 : f.listFiles()) {
										if (file2.isDirectory()) {
											// 判断文件名是否是导出创建数据名称
											if (file2
													.getName()
													.contains(
															FileUtils.C_TEMPLATE_DOWNLOAD_FILENAME)) {
												templateDownloadService
														.importTemplateDownload(file2);
											}
											if (file2
													.getName()
													.contains(
															FileUtils.C_FILE_TEMPLATE_DOWNLOAD_FILENAME)) {
												OBProjectServer
														.importFile(
																file2,
																common.constant.Constant.TENDER_SYS_KEY);
											}
										}
									}
								}
							}
							// 附件目录复制
							if (f.getName()
									.equals(Constant.T_ISS_PS_TEMPLATE_DOWNLOAD_ATTFILE_PATH)) {
								if (f.isDirectory()) {
									for (File file2 : f.listFiles()) {
										if (file2.isDirectory()) {
											OperAttachment
													.moveToPathFolder(
															file2,
															FileUtils.BASE_ATTCH_PATH
																	+ FileUtils.TENDER_ATTFILE_PATH);
										}
									}
								}
							}
						}*/

                        /**
                         * 供应商公示自动导入
                         */
                        result = DictionaryDataUtil.getId(Constant.SYNCH_PUBLICITY_SUPPLIER);
                        if (StringUtils.isNotEmpty(result)) {
                            if (f.getName().equals(Constant.T_SES_SMS_SUPPLIER_PUBLICITY_PATH)) {
                                if (f.isDirectory()) {
                                    // 遍历文件夹中的所有文件
                                    for (File file2 : f.listFiles()) {
                                        if (file2.getName().contains(FileUtils.C_SYNCH_PUBLICITY_SUPPLIER_FILENAME)) {
                                            innerSupplierService.importInner(file2, "publicity");
                                        }
                                    }
                                }
                            }
                        }
						/**
						 * 专家公示自动导入
						 */
						result = DictionaryDataUtil.getId(Constant.SYNCH_PUBLICITY_EXPERT);
						if(StringUtils.isNotEmpty(result)){
							if (f.getName().equals(Constant.T_SES_SMS_EXPERT_PUBLICITY_PATH)) {
								if (f.isDirectory()) {
									// 遍历文件夹中的所有文件
									for (File file2 : f.listFiles()) {
										if (file2.getName().contains(FileUtils.C_SYNCH_PUBLICITY_EXPERT_FILENAME)) {
											innerExpertService.importExpOfPublicity(file2);
										}
									}
								}
							}
						}

                        /**
                         * 供应商注销自动导入
                         */
                        result = DictionaryDataUtil.getId(Constant.SYNCH_LOGOUT_SUPPLIER);
                        if (StringUtils.isNotEmpty(result)) {
                            if (FileUtils.getSynchAttachFile(31).equals("/" + f.getName())) {
                                // 遍历文件夹中的所有文件
                                for (File file2 : f.listFiles()) {
                                    if (file2.getName().contains(FileUtils.C_SYNCH_LOGOUT_SUPPLIER_FILENAME)) {
                                        innerSupplierService.importLogoutSupplier(file2);
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
    }
}
