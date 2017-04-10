package bss.util;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import ses.model.bms.Category;
import ses.model.oms.Orgnization;
import ses.model.sms.Supplier;
import ses.service.bms.CategoryService;
import ses.service.oms.OrgnizationServiceI;
import ses.service.sms.SupplierService;
import bss.model.ob.OBProduct;
import bss.model.ob.OBSupplier;
import bss.model.pms.CollectPlan;
import bss.model.pms.PurchaseRequired;
import bss.service.ob.OBProductService;
import bss.service.ob.OBProjectServer;
import bss.service.ob.OBSupplierService;
import bss.service.pms.CollectPlanService;
import bss.service.pms.PurchaseRequiredService;
import bss.service.pms.impl.CollectPlanServiceImpl;

/**
 *
 * @Title: ExcelUtil
 * @Description: excel工具类读取
 * @author Li Xiaoxiao
 * @date  2016年9月12日,上午9:18:29
 *
 */
@Component
public class ExcelUtil {
 
  
  @Autowired
  private PurchaseRequiredService  purchaseRequiredService;
  /**定型产品**/
  @Autowired
  private  OBProjectServer OBProjectServer;
  
  @Autowired
  private OBProductService oBProductService;
  
  @Autowired
  private OrgnizationServiceI orgnizationService;
  
  @Autowired
  private CategoryService categoryService;
  
  @Autowired
  private SupplierService supplierService;
  
  @Autowired
  private OBSupplierService oBSupplierService;
  
  private static ExcelUtil excelUtil;
  
  
  public void setDdService(PurchaseRequiredService purchaseRequiredService){
      this.purchaseRequiredService = purchaseRequiredService;
  }
  public void setObProjectServer(OBProjectServer OBProjectServer){
	  this.OBProjectServer=OBProjectServer;
  }
  
  @PostConstruct 
  public void init(){
	  excelUtil = this;
	  excelUtil.purchaseRequiredService = this.purchaseRequiredService;
	  excelUtil.OBProjectServer = this.OBProjectServer;
  }
  
  
  
 
  	/**
	 * @throws FileNotFoundException 
	 *
	* @Title: readExcel
	* @Description: 读取 excel表格内容
	* author: Li Xiaoxiao 
	* @param @param path
	* @param @return     
	* @return List<PurchaseRequired>     
	 */
	public static Map<String,Object> readExcel(MultipartFile file) throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		List<PurchaseRequired> list=new LinkedList<PurchaseRequired>();
		 //FileInputStream fis = new FileInputStream(path);
	        Workbook workbook = WorkbookFactory.create(file.getInputStream());
	       /* if (fis != null) {
	            fis.close();
	        }*/
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
	        Sheet sheet = workbook.getSheetAt(0);
	        String planName="";
	        String errMsg=null;
	        boolean bool=true;
	        for (Row row : sheet) {
	        	PurchaseRequired rq=new PurchaseRequired();
	        	if(row.getRowNum()==0){
	        		for (Cell cell : row) {
	        			if(cell.getColumnIndex()==0){
	        				planName=cell.getStringCellValue();
	        			}
	        		}
	        	}
	        
	        	if(row.getRowNum()>1){
	        		Cell cel = row.getCell(0);
	        		if(cel==null){
    					 errMsg=String.valueOf(row.getRowNum()+1)+"行A列错误，不能为空!";
    					 map.put("errMsg", errMsg);
    					 bool=false;
        				 break;
    				}
	        		
	        		
	        	 
	        		for (Cell cell : row) {
	        			 if(cell.getColumnIndex()==0){
			        			if(cell.getCellType()==1){
 			        				if(cell.getStringCellValue().contains("(")||cell.getStringCellValue().contains(")")){
 			        					 errMsg=String.valueOf(row.getRowNum()+1)+"行A列错误，不能包含英文括号!";
 			        					 map.put("errMsg", errMsg);
 			        					 bool=false;
 				        				 break;
 			        				}
 			        				if(cell.getStringCellValue().trim().length()<1){
 			        					errMsg=String.valueOf(row.getRowNum()+1)+"行A列错误，不能为空!";
			        					 map.put("errMsg", errMsg);
			        					 bool=false;
				        				 break;
 			        				}
 			        				rq.setSeq(cell.getRichStringCellValue().toString());
			        				 continue;
			        			} 
			        			if(cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
			        				rq.setSeq(String.valueOf((int)cell.getNumericCellValue()));
			        				 continue;
			        			}
			        			else {
			        					 errMsg=String.valueOf(row.getRowNum()+1)+"行A列错误，不允许为空！";
				        				 map.put("errMsg", errMsg);
				        				 break;
			        				}
	        			 }
	        			 if(cell.getColumnIndex()==1){
	        				 if(cell.getCellType()==1){
	        				/*     String dep = cell.getStringCellValue();
	        				     if(dep.trim().length()!=0){
	        				    	 boolean chinese = isContainChinese(rq.getSeq());
	        				    	 if(chinese==true){
	        				    		 Orgnization orgnization = excelUtil.purchaseRequiredService.queryByName(dep);
			        					 if(orgnization==null){
			        						 errMsg=String.valueOf(row.getRowNum()+1)+"行B列错误，需求部门不存在，请在系统中维护！";
					        				 map.put("errMsg", errMsg);
					        				  bool=false;
					        				  break; 
			        					 }
	        				    	 }
	        					 }*/
	        				
		        				
	        					 rq.setDepartment(cell.getStringCellValue());
		        				 continue;
		        			}else{
		        				if(cell.getCellType()!=3){
		        					 errMsg=String.valueOf(row.getRowNum()+1)+"行B列错误，请输入文本类型！";
			        				 map.put("errMsg", errMsg);
			        				 break;
		        				}
	        				 }
	        			 }
	        			 if(cell.getColumnIndex()==2){
	        				 if(cell.getCellType()==1){
	        					 rq.setGoodsName(cell.getStringCellValue());
			        			 continue;
	        				 }
	        				 if(cell.getCellType()!=3){
	        					 errMsg=String.valueOf(row.getRowNum()+1)+"行，C列错误";
		        				 map.put("errMsg", errMsg);
		        				 bool=false;
		        				 break;
		        			}
	        			 }
	        			 if(cell.getColumnIndex()==3){
	        				 if(cell.getCellType()==1){
	        					 rq.setStand(cell.getStringCellValue());
		        				 continue;
		        			}
	        				 if(cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
	        					 String stand = String.valueOf(cell.getNumericCellValue());
	 		        				 rq.setStand(stand.substring(0, stand.lastIndexOf("."))); 
	 		        				 continue;
	 	        				 
	        				 }
	        				 
	        				 
	        				 if(cell.getCellType()!=3){
		        				 errMsg=String.valueOf(row.getRowNum()+1)+"行，D列错误";
		        				 map.put("errMsg", errMsg);
		        				 break;
		        			}
	        			 }
	        			 if(cell.getColumnIndex()==4){
	        				 if(cell.getCellType()==1){
	        					 rq.setQualitStand(cell.getStringCellValue());
		        				 continue;
		        			} if(cell.getCellType()!=3){
		        				 errMsg=String.valueOf(row.getRowNum()+1)+"行，E列错误";
		        				 map.put("errMsg", errMsg);
		        				 bool=false;
		        				 break;
		        			}
	        			 }
	        			 if(cell.getColumnIndex()==5){
	        				 if(cell.getCellType()==1){
	        					 rq.setItem(cell.getStringCellValue());
		        				 continue;
		        				 
	        		
		        			}if(cell.getCellType()!=3){
		        				 errMsg=String.valueOf(row.getRowNum()+1)+"行，F列错误";
		        				 map.put("errMsg", errMsg);
		        				 bool=false;
		        				 break;
		        			}
	        			 }
	        			 if(cell.getColumnIndex()==6){
	        				 if(rq.getItem()==null){
	        					  if(cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
	 	        					 Double value = cell.getNumericCellValue();
	 	        					 if(value==0){
	 	        						 errMsg=String.valueOf(row.getRowNum()+1)+"行，F列错误,计量单位不能为空！";
	 			        				 map.put("errMsg", errMsg);
	 			        				 bool=false;
	 			        				 break;
	 	        					 }
	 	        				 }
	        				 }
	        				if(rq.getItem()!=null){
	        				  if(cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
	        					 Double value = cell.getNumericCellValue();
	        					 if(value==0){
	        						 errMsg=String.valueOf(row.getRowNum()+1)+"行，G列错误,采购数量不能为0！";
			        				 map.put("errMsg", errMsg);
			        				 bool=false;
			        				 break;
	        					 }
	 	        				 if(value!=null){ 
	 		        				 rq.setPurchaseCount(new BigDecimal(cell.getNumericCellValue())); 
	 		        				 continue;
	 	        				 }
	        				 }else if(cell.getCellType()!=3){
	        					 errMsg=String.valueOf(row.getRowNum()+1)+"行，G列错误";
		        				 map.put("errMsg", errMsg);
		        				 bool=false;
		        				 break;
	        				 	}
	        				 }
	        			 }
	        			 if(cell.getColumnIndex()==7){
	        				 boolean addMer = isAddMer(sheet,row.getRowNum(),cell.getColumnIndex());
	        				 if(rq.getItem()!=null){
	        					
	        					 if(cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC||cell.getCellType()==HSSFCell.CELL_TYPE_FORMULA){
			        				  rq.setPrice(new BigDecimal(cell.getNumericCellValue()));
		        					 continue;
		        				 }
	        					 
//	        					 else  if(addMer==true){
//	        						 errMsg=String.valueOf(row.getRowNum()+1)+"行，H列错误,不能合并单元格！";
//			        				 map.put("errMsg", errMsg); 
//			        				 bool=false;
//			        				 break;
//			        				 
//			        				 
//		        				 } 
	        					 if(cell.getCellType()!=3){
		        					 errMsg=String.valueOf(row.getRowNum()+1)+"行，H列错误";
			        				 map.put("errMsg", errMsg); 
			        				 bool=false;
			        				 continue;
		        				 }
	        				
	        				 }
	        			 }
	        			 if(cell.getColumnIndex()==8){
//	        				 if(rq.getItem()!=null){
//	        				 boolean addMer = isAddMer(sheet,row.getRowNum(),cell.getColumnIndex());
//	        				 if(addMer==true){
//    							 rq.setBudget(getMergedRegionValue(sheet,row.getRowNum(),cell.getColumnIndex()));
//	        					 
//	        					 errMsg=String.valueOf(row.getRowNum()+1)+"行，I列错误,不能合并单元格！";
//		        				 map.put("errMsg", errMsg);
//		        				 bool=false;
//		        				 break;
//	        				 }
	        				 
	        				 
	        					 if(cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC||cell.getCellType()==HSSFCell.CELL_TYPE_FORMULA){
		        					 rq.setBudget(new BigDecimal(cell.getNumericCellValue()));
		        					 continue;
		        				 }
	        					 if(cell.getCellType()!=3){
		        					 errMsg=String.valueOf(row.getRowNum()+1)+"行，I列错误";
			        				 map.put("errMsg", errMsg);
			        				 bool=false;
			        				 break;
		        				 }
//	        				 }
	        				
	        			 }
	        			 if(cell.getColumnIndex()==9){
	        				if(rq.getItem()!=null){
	        					 if(cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
		        					 boolean boo = HSSFDateUtil.isCellDateFormatted(cell);
//		        					 if(boo){
		        						 String date = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
		        						 rq.setDeliverDate(date);
		        						 continue;
//		        					 }
		        				 }
	        					 if(cell.getCellType()==HSSFCell.CELL_TYPE_STRING){
	        						 rq.setDeliverDate(cell.getStringCellValue());
		        					
		        				 }else if(cell.getCellType()!=3){
		        					 errMsg=String.valueOf(row.getRowNum()+1)+"行，J列错误";
			        				 map.put("errMsg", errMsg); 
			        				 bool=false;
			        				 break;
		        				 }
	        				}
	        			 }
	        	
	        			 if(cell.getColumnIndex()==10){
	        				if(cell.getCellType()==1){
	        					rq.setSupplier(cell.getStringCellValue());
	        				}else if(cell.getCellType()!=3){
	        					 errMsg=String.valueOf(row.getRowNum()+1)+"行，K错误";
		        				 map.put("errMsg", errMsg);
		        				 bool=false;
	        				}
	        				 
	        			 }
	        			 if(cell.getColumnIndex()==11){
	        				 if(cell.getCellType()==HSSFCell.CELL_TYPE_STRING){
	        					 String str = cell.getStringCellValue();
	        					 rq.setPurchaseType(str);
	        				 }else if(cell.getCellType()!=3){
	        					 errMsg=String.valueOf(row.getRowNum()+1)+"L行列错误，非文本格式!";
		        				 map.put("errMsg", errMsg); 
		        				 bool=false;
		        				 break;
	        				 }
	        				
	        			 }
	        			 
//	        			 if(cell.getColumnIndex()==12){
//	        				 if(cell.getCellType()==1){
//	        					 rq.setIsFreeTax(cell.getStringCellValue());
//	        					 continue;
//	        				 }else{
//	        					 errMsg=String.valueOf(row.getRowNum()+1)+"行，M列错误";
//		        				 map.put("errMsg", errMsg);  
//		        				 continue;
//		        				 bool=false;
//	        				 }
//	        			 }
//	        			 if(cell.getColumnIndex()==13){
//	        				if(cell.getCellType()!=1){
//	        					 errMsg=String.valueOf(row.getRowNum()+1)+"行，N列错误";
//		        				 map.put("errMsg", errMsg);
//		        				 continue;
//		        				 bool=false;
//	        				}else{
//	        					 rq.setGoodsUse(cell.getStringCellValue());
//	        					 continue;
//	        				}
//	        				
//	        			 }
	        			 if(cell.getColumnIndex()==12){
//	        				if(cell.getCellType()==1){
//	        					rq.setOrganization(cell.getStringCellValue());
//	        					 continue;
//	        				
//	        				}else if(cell.getCellType()!=3){ 
//	        					 errMsg=String.valueOf(row.getRowNum()+1)+"行M列错误";
//		        				 map.put("errMsg", errMsg);
//		        				 bool=false;
//		        				 break;
//	        				}
	        				 if(cell.getCellType()==1){
	        					 rq.setMemo(cell.getStringCellValue());
	        					 continue;
	        				}else if(cell.getCellType()!=3){
	        					 errMsg=String.valueOf(row.getRowNum()+1)+"行，N列错误";
		        				 map.put("errMsg", errMsg);
//		        				 continue;
		        				 bool=false;
		        				 break;
	        				}
	        			 }
	        			 if(cell.getColumnIndex()==13){
//	        				if(cell.getCellType()==1){
//	        					 rq.setMemo(cell.getStringCellValue());
//	        					 continue;
//	        				}else if(cell.getCellType()!=3){
//	        					 errMsg=String.valueOf(row.getRowNum()+1)+"行，N列错误";
//		        				 map.put("errMsg", errMsg);
////		        				 continue;
//		        				 bool=false;
//		        				 break;
//	        				}
	        			 }
	        			 rq.setPlanName(planName);
	        			 rq.setStatus("1");
	        			 rq.setHistoryStatus("0");
						}
	        		if(bool==false)break;
	        		list.add(rq);
					}
	        	}
	        	
		
	        map.put("list", list);
		
		return map;
		
	}
	
	
  	/**
	 * @throws FileNotFoundException 
	 *
	* @Title: readExcel
	* @Description: 读取 excel表格内容
	* author: Li Xiaoxiao 
	* @param @param path
	* @param @return     
	* @return List<PurchaseRequired>     
	 */
	public static Map<String,Object> cgjhExcel(MultipartFile file) throws Exception{
		
		Map<String,Object> map=new HashMap<String,Object>();
		List<PurchaseRequired> list=new LinkedList<PurchaseRequired>();
		 //FileInputStream fis = new FileInputStream(path);
	        Workbook workbook = WorkbookFactory.create(file.getInputStream());
	       /* if (fis != null) {
	            fis.close();
	        }*/
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
	        Sheet sheet = workbook.getSheetAt(0);
	        String planName="";
	        String errMsg=null;
	        boolean bool=true;
	        for (Row row : sheet) {
	        	PurchaseRequired rq=new PurchaseRequired();
	        	if(row.getRowNum()==0){
	        		for (Cell cell : row) {
	        			if(cell.getColumnIndex()==0){
	        				planName=cell.getStringCellValue();
	        			}
	        		}
	        	}
	        
	        	if(row.getRowNum()>1){
	        		Cell cel = row.getCell(0);
	        		if(cel==null){
    					 errMsg=String.valueOf(row.getRowNum()+1)+"行A列错误，不能为空!";
    					 map.put("errMsg", errMsg);
    					 bool=false;
        				 break;
    				}
	        		
	        		
	        	 
	        		for (Cell cell : row) {
	        		
	        			 if(cell.getColumnIndex()==0){
			        			if(cell.getCellType()==1){
 			        				if(cell.getStringCellValue().contains("(")||cell.getStringCellValue().contains(")")){
 			        					 errMsg=String.valueOf(row.getRowNum()+1)+"行A列错误，不能包含英文括号!";
 			        					 map.put("errMsg", errMsg);
 			        					 bool=false;
 				        				 break;
 			        				}
 			        				if(cell.getStringCellValue().trim().length()<1){
 			        					errMsg=String.valueOf(row.getRowNum()+1)+"行A列错误，不能为空!";
			        					 map.put("errMsg", errMsg);
			        					 bool=false;
				        				 break;
 			        				}
 			        				rq.setSeq(cell.getRichStringCellValue().toString());
			        				 continue;
			        			} 
			        			if(cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
			        				rq.setSeq(String.valueOf((int)cell.getNumericCellValue()));
			        				 continue;
			        			}
			        			else {
			        					 errMsg=String.valueOf(row.getRowNum()+1)+"行A列错误，不允许为空！";
				        				 map.put("errMsg", errMsg);
				        				 break;
			        				}
	        			 }
	        			 if(cell.getColumnIndex()==1){
	        				 if(cell.getCellType()==1){
	        				/*     String dep = cell.getStringCellValue();
	        				     if(dep.trim().length()!=0){
	        				    	 boolean chinese = isContainChinese(rq.getSeq());
	        				    	 if(chinese==true){
	        				    		 Orgnization orgnization = excelUtil.purchaseRequiredService.queryByName(dep);
			        					 if(orgnization==null){
			        						 errMsg=String.valueOf(row.getRowNum()+1)+"行B列错误，需求部门不存在，请在系统中维护！";
					        				 map.put("errMsg", errMsg);
					        				  bool=false;
					        				  break; 
			        					 }
	        				    	 }
	        					 }*/
	        				
		        				
	        					 rq.setDepartment(cell.getStringCellValue());
		        				 continue;
		        			}else{
		        				if(cell.getCellType()!=3){
		        					 errMsg=String.valueOf(row.getRowNum()+1)+"行B列错误，请输入文本类型！";
			        				 map.put("errMsg", errMsg);
			        				 break;
		        				}
	        				 }
	        			 }
	        			 if(cell.getColumnIndex()==2){
	        				 if(cell.getCellType()==1){
	        					 rq.setGoodsName(cell.getStringCellValue());
			        			 continue;
	        				 }
	        				 if(cell.getCellType()!=3){
	        					 errMsg=String.valueOf(row.getRowNum()+1)+"行，C列错误";
		        				 map.put("errMsg", errMsg);
		        				 bool=false;
		        				 break;
		        			}
	        			 }
	        			 if(cell.getColumnIndex()==3){
	        				 if(cell.getCellType()==1){
	        					 rq.setStand(cell.getStringCellValue());
		        				 continue;
		        			}if(cell.getCellType()!=3){
		        				 errMsg=String.valueOf(row.getRowNum()+1)+"行，D列错误";
		        				 map.put("errMsg", errMsg);
		        				 break;
		        			}
	        			 }
	        			 if(cell.getColumnIndex()==4){
	        				 if(cell.getCellType()==1){
	        					 rq.setQualitStand(cell.getStringCellValue());
		        				 continue;
		        			} if(cell.getCellType()!=3){
		        				 errMsg=String.valueOf(row.getRowNum()+1)+"行，E列错误";
		        				 map.put("errMsg", errMsg);
		        				 bool=false;
		        				 break;
		        			}
	        			 }
	        			 if(cell.getColumnIndex()==5){
	        				 if(cell.getCellType()==1){
	        					 rq.setItem(cell.getStringCellValue());
		        				 continue;
		        				 
	        		
		        			}if(cell.getCellType()!=3){
		        				 errMsg=String.valueOf(row.getRowNum()+1)+"行，F列错误";
		        				 map.put("errMsg", errMsg);
		        				 bool=false;
		        				 break;
		        			}
	        			 }
	        			 if(cell.getColumnIndex()==6){
	        				 if(rq.getItem()==null){
	        					  if(cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
	 	        					 Double value = cell.getNumericCellValue();
	 	        					 if(value==0){
	 	        						 errMsg=String.valueOf(row.getRowNum()+1)+"行，F列错误,计量单位不能为空！";
	 			        				 map.put("errMsg", errMsg);
	 			        				 bool=false;
	 			        				 break;
	 	        					 }
	 	        				 }
	        				 }
	        				if(rq.getItem()!=null){
	        				  if(cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
	        					 Double value = cell.getNumericCellValue();
	        					 if(value==0){
	        						 errMsg=String.valueOf(row.getRowNum()+1)+"行，G列错误,采购数量不能为0！";
			        				 map.put("errMsg", errMsg);
			        				 bool=false;
			        				 break;
	        					 }
	 	        				 if(value!=null){ 
	 		        				 rq.setPurchaseCount(new BigDecimal(cell.getNumericCellValue())); 
	 		        				 continue;
	 	        				 }
	        				 }else if(cell.getCellType()!=3){
	        					 errMsg=String.valueOf(row.getRowNum()+1)+"行，G列错误";
		        				 map.put("errMsg", errMsg);
		        				 bool=false;
		        				 break;
	        				 	}
	        				 }
	        			 }
	        			 if(cell.getColumnIndex()==7){
	        				 boolean addMer = isAddMer(sheet,row.getRowNum(),cell.getColumnIndex());
	        				 if(rq.getItem()!=null){
	        					
	        					 if(cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC||cell.getCellType()==HSSFCell.CELL_TYPE_FORMULA){
			        				  rq.setPrice(new BigDecimal(cell.getNumericCellValue()));
		        					 continue;
		        				 }
	        					 
//	        					 else  if(addMer==true){
//	        						 errMsg=String.valueOf(row.getRowNum()+1)+"行，H列错误,不能合并单元格！";
//			        				 map.put("errMsg", errMsg); 
//			        				 bool=false;
//			        				 break;
//			        				 
//			        				 
//		        				 } 
	        					 if(cell.getCellType()!=3){
		        					 errMsg=String.valueOf(row.getRowNum()+1)+"行，H列错误";
			        				 map.put("errMsg", errMsg); 
			        				 bool=false;
			        				 continue;
		        				 }
	        				
	        				 }
	        			 }
	        			 if(cell.getColumnIndex()==8){
//	        				 if(rq.getItem()!=null){
//	        				 boolean addMer = isAddMer(sheet,row.getRowNum(),cell.getColumnIndex());
//	        				 if(addMer==true){
//    							 rq.setBudget(getMergedRegionValue(sheet,row.getRowNum(),cell.getColumnIndex()));
//	        					 
//	        					 errMsg=String.valueOf(row.getRowNum()+1)+"行，I列错误,不能合并单元格！";
//		        				 map.put("errMsg", errMsg);
//		        				 bool=false;
//		        				 break;
//	        				 }
	        				 
	        				 
	        					 if(cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC||cell.getCellType()==HSSFCell.CELL_TYPE_FORMULA){
		        					 rq.setBudget(new BigDecimal(cell.getNumericCellValue()));
		        					 continue;
		        				 }
	        					 if(cell.getCellType()!=3){
		        					 errMsg=String.valueOf(row.getRowNum()+1)+"行，I列错误";
			        				 map.put("errMsg", errMsg);
			        				 bool=false;
			        				 break;
		        				 }
//	        				 }
	        				
	        			 }
	        			 if(cell.getColumnIndex()==9){
	        				if(rq.getItem()!=null){
	        					 if(cell.getCellType()==HSSFCell.CELL_TYPE_NUMERIC){
		        					 boolean boo = HSSFDateUtil.isCellDateFormatted(cell);
//		        					 if(boo){
		        						 String date = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
		        						 rq.setDeliverDate(date);
		        						 continue;
//		        					 }
		        				 }
	        					 if(cell.getCellType()==HSSFCell.CELL_TYPE_STRING){
	        						 rq.setDeliverDate(cell.getStringCellValue());
		        					
		        				 }else if(cell.getCellType()!=3){
		        					 errMsg=String.valueOf(row.getRowNum()+1)+"行，J列错误";
			        				 map.put("errMsg", errMsg); 
			        				 bool=false;
			        				 break;
		        				 }
	        				}
	        			 }
	        	
	        			 if(cell.getColumnIndex()==10){
	        				if(cell.getCellType()==1){
	        					rq.setSupplier(cell.getStringCellValue());
	        				}else if(cell.getCellType()!=3){
	        					 errMsg=String.valueOf(row.getRowNum()+1)+"行，K错误";
		        				 map.put("errMsg", errMsg);
		        				 bool=false;
	        				}
	        				 
	        			 }
	        			 if(cell.getColumnIndex()==11){
	        				 if(cell.getCellType()==HSSFCell.CELL_TYPE_STRING){
	        					 String str = cell.getStringCellValue();
	        					 rq.setPurchaseType(str);
	        				 }else if(cell.getCellType()!=3){
	        					 errMsg=String.valueOf(row.getRowNum()+1)+"L行列错误，非文本格式!";
		        				 map.put("errMsg", errMsg); 
		        				 bool=false;
		        				 break;
	        				 }
	        				
	        			 }
	        			 if(cell.getColumnIndex()==12){
	        				if(cell.getCellType()==1){
	        					 rq.setOrganization(cell.getStringCellValue());
	        					 Orgnization orgnization = excelUtil.purchaseRequiredService.queryByName(cell.getStringCellValue());
	        					 if(orgnization==null){
	        						 errMsg=String.valueOf(row.getRowNum()+1)+"行B列错误，采购机构不存在，请在系统中维护！";
			        				 map.put("errMsg", errMsg);
			        				  bool=false;
			        				  break; 
	        					 }
	        					 continue;
	        				
	        				}else if(cell.getCellType()!=3){ 
	        					 errMsg=String.valueOf(row.getRowNum()+1)+"行M列错误";
		        				 map.put("errMsg", errMsg);
		        				 bool=false;
		        				 break;
	        				}
	        			 }
	        			 if(cell.getColumnIndex()==13){
	        				if(cell.getCellType()==1){
	        					 rq.setMemo(cell.getStringCellValue());
	        					 continue;
	        				}else if(cell.getCellType()!=3){
	        					 errMsg=String.valueOf(row.getRowNum()+1)+"行，N列错误";
		        				 map.put("errMsg", errMsg);
		        				 bool=false;
		        				 break;
	        				}
	        			 }
	        			 rq.setPlanName(planName);
	        			 rq.setStatus("1");
	        			 rq.setHistoryStatus("0");
						}
	        		if(bool==false)break;
	        		list.add(rq);
					}
	        	}
	        	
		
	        map.put("list", list);
		
		return map;
		
	}
	
	
	
	/**
	 * @throws FileNotFoundException 
	 *
	* @Title: readExcel
	* @Description: 读取 excel竞价定型产品表格内容
	* author: YangHongLiang
	* @param @param path
	* @param @return     
	* @return List<PurchaseRequired>     
	 */
	public static Map<String,Object> readOBExcel(MultipartFile file) throws Exception{
		List<OBProduct> list=new LinkedList<OBProduct>();
		Map<String,Object> map=new HashMap<String,Object>();
	        Workbook workbook = WorkbookFactory.create(file.getInputStream());
	        Sheet sheet = workbook.getSheetAt(0);
	        String planName="";
	        String errMsg=null;
	        boolean bool=true;
	        for(Row row:sheet){
	        	OBProduct obp=new OBProduct();
	        	for(Cell cell : row){
	        		if(cell.getColumnIndex()==0){
        				planName=cell.getStringCellValue();
        			}
	        	}
	        	if(row.getRowNum()>1){
	        		Cell cel = row.getCell(0);
	        		if(cel==null){
   					 errMsg=String.valueOf(row.getRowNum()+1)+"行A列错误，不能为空!";
   					 map.put("errMsg", errMsg);
   					 bool=false;
       				 break;
   				}
	        		for (Cell cell : row) {
	        			 if(cell.getColumnIndex()==0){
			        			if(cell.getCellType()==1){
			        				if(cell.getStringCellValue().trim().length()<1){
			        					errMsg=String.valueOf(row.getRowNum()+1)+"行A列错误，不能为空!";
			        					 map.put("errMsg", errMsg);
			        					 bool=false;
				        				 break;
			        				}
			        				List<OBProduct> lists=excelUtil.OBProjectServer.productList();
			        				if(lists==null){
			        					errMsg=String.valueOf(row.getRowNum()+1)+"行A列错,请维护定型产品!";
			        					 map.put("errMsg", errMsg);
			        					 bool=false;
				        				 break;
			        				}else{
			        					String product=cell.getRichStringCellValue().toString();
			        					boolean boo=false;
			        					int listsize=0;
			        					String id="";
			        					for(OBProduct ob:lists){
			        						if(ob.getName().equals(product)){
			        							boo=true;
			        							id=ob.getId();
			        							break;
			        						}
			        					}
			        					if(boo==true){
			        						obp.setId(id);//产品id
			        						obp.setCode(product);//产品名称
			        						/*obp.setName(listsize+"");//供应商数量*/					        				 continue;
			        					}else{
			        						errMsg=String.valueOf(row.getRowNum()+1)+"行A列错,定型产品不存在!";
				        					 map.put("errMsg", errMsg);
				        					 bool=false;
					        				 break;
			        					}
			        					
			        				}
			        			} 
	        			 }
	        			 if(cell.getColumnIndex()==1){
	        				 if(cell.getCellType()==0){
	        					 obp.setStandardModel(String.valueOf(cell.getNumericCellValue()));//金额
		        				 continue;
		        			}else{
		        				if(cell.getCellType()!=3){
		        					 errMsg=String.valueOf(row.getRowNum()+1)+"行B列错误,请输入数字类型！";
			        				 map.put("errMsg", errMsg );
			        				 bool=false;
			        				 break;
		        				}
	        				 }
	        			 }
	        			 if(cell.getColumnIndex()==2){
	        				 if(cell.getCellType()==0){
	        					 obp.setIsDeleted((int)cell.getNumericCellValue());//数量
			        			 continue;
	        				 }if(cell.getCellType()!=3){
	        					 errMsg=String.valueOf(row.getRowNum()+1)+"行，C列错误,请输入数字类型";
		        				 map.put("errMsg", errMsg);
		        				 bool=false;
		        				 break;
		        			}
	        			 }
	        			 if(cell.getColumnIndex()==3){
	        				 if(cell.getCellType()==1){
	        					 obp.setRemark(cell.getStringCellValue());//备注
		        				 continue;
		        			}if(cell.getCellType()==3){
		        				 errMsg=String.valueOf(row.getRowNum()+1)+"行，D列错误不能为空";
		        				 map.put("errMsg", errMsg);
		        				 bool=false;
		        				 break;
		        			}
	        			 }        		
	        			}
	        		if(bool==false)break;
	        		list.add(obp);
	        		}
	        	}
	         map.put("list", list);
		return map;
	}
	
	/**
	 * 
	 * Description: 定型产品文件上传
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月16日 
	 * @param  @param file
	 * @param  @return
	 * @param  @throws Exception 
	 * @return Map<String,Object> 
	 * @exception
	 */
	public static Map<String,Object> readOBProductExcel(MultipartFile file) throws Exception{
		List<OBProduct> list=new LinkedList<OBProduct>();
		Map<String,Object> map=new HashMap<String,Object>();
		Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        String errMsg=null;
        String planName="";
        boolean bool=true;
        for(Row row:sheet){
        	String orgId = "";
        	OBProduct obp=new OBProduct();
        	for(Cell cell : row){
        		if(cell.getColumnIndex()==0){
    				planName=cell.getStringCellValue();
    			}
        	}
        	if(row.getRowNum()>1){
        		Cell cel = row.getCell(0);
        		if(cel==null){
					 errMsg=String.valueOf(row.getRowNum()+1)+"行A列错误，不能为空!";
					 map.put("errMsg", errMsg);
					 bool=false;
   				 break;
				}
        		for (Cell cell : row) {
        			//判断第一列
        			if(cell.getColumnIndex()==0){
        				if(cell.getCellType()==1){
        					//判断是否为空
        					if(cell.getStringCellValue().trim().length()<1){
	        					errMsg=String.valueOf(row.getRowNum()+1)+"行A列错误，不能为空!";
	        					 map.put("errMsg", errMsg);
	        					 bool=false;
		        				 break;
	        				}
        					//验证产品代码唯一
        					String code = cell.getRichStringCellValue().toString();
        					if(excelUtil.oBProductService.yzProductCode(code, null) > 0){
        						errMsg=String.valueOf(row.getRowNum()+1)+"行A列错误，产品代码不能重复!";
        						map.put("errMsg", errMsg);
	        					 bool=false;
		        				 break;
        					}else{
        						obp.setCode(code);
        					}
        				}
        			}
        			//第二列
    				if(cell.getColumnIndex()==1){
    					if(cell.getCellType()==1){
        					//判断是否为空
        					if(cell.getStringCellValue().trim().length()<1){
	        					errMsg=String.valueOf(row.getRowNum()+1)+"行B列错误，不能为空!";
	        					 map.put("errMsg", errMsg);
	        					 bool=false;
		        				 break;
	        				}
        					//验证产品代码唯一
        					String name = cell.getRichStringCellValue().toString();
        					if(excelUtil.oBProductService.yzProductName(name, null) > 0){
        						errMsg=String.valueOf(row.getRowNum()+1)+"行B列错误，产品名称不能重复!";
        						map.put("errMsg", errMsg);
	        					 bool=false;
		        				 break;
        					}else{
        						obp.setName(name);
        					}
        				}
        			 }
        			//第三列
    				if(cell.getColumnIndex()==2){
    					if(cell.getCellType()==1){
        					//判断是否为空
        					if(cell.getStringCellValue().trim().length()<1){
	        					errMsg=String.valueOf(row.getRowNum()+1)+"行C列错误，不能为空!";
	        					 map.put("errMsg", errMsg);
	        					 bool=false;
		        				 break;
	        				}
        					//验证产采购机构是否存在
        					String org = cell.getRichStringCellValue().toString();
        					if(excelUtil.oBProductService.yzorg(org) < 1){
        						errMsg=String.valueOf(row.getRowNum()+1)+"行C列错误，采购机构不存在!";
        						map.put("errMsg", errMsg);
	        					 bool=false;
		        				 break;
        					}else{
        						Orgnization orgnization = excelUtil.orgnizationService.selectByShortName(org);
        						orgId = orgnization.getId();
        					}
        				}
        			 }
        			//第四列
    				if(cell.getColumnIndex()==3){
    					if(cell.getCellType()==1){
        					//判断是否为空
        					if(cell.getStringCellValue().trim().length()<1){
	        					errMsg=String.valueOf(row.getRowNum()+1)+"行D列错误，目录末节点不能为空!";
	        					 map.put("errMsg", errMsg);
	        					 bool=false;
		        				 break;
	        				}
        					//验证产品目录是否存在
        					String categoryCode = cell.getRichStringCellValue().toString();
        					if(null == excelUtil.categoryService.selectByCode(categoryCode) || excelUtil.categoryService.selectByCode(categoryCode).size() == 0){
        						errMsg=String.valueOf(row.getRowNum()+1)+"行D列错误，产品目录不存在!";
        						map.put("errMsg", errMsg);
	        					 bool=false;
		        				 break;
        					}
        					List<Category> selectByCode = excelUtil.categoryService.selectByCode(categoryCode);
        					if(selectByCode.get(0).getIsPublish() != 0){
        						errMsg=String.valueOf(row.getRowNum()+1)+"行D列错误，只能添加已开放的目录!";
        						map.put("errMsg", errMsg);
	        					 bool=false;
		        				 break;
        					}
        					List<Category> list2 = excelUtil.categoryService.selectByCode(categoryCode);
        					HashMap<String, Object> map1 = new HashMap<String, Object>();
        					map1.put("id", list2.get(0).getId());
        					if(excelUtil.categoryService.findCategoryByChildren(map1).size() != 0){
        						errMsg=String.valueOf(row.getRowNum()+1)+"行D列错误，请选择目录末节点添加!";
   	        					 map.put("errMsg", errMsg);
   	        					 bool=false;
   	        					 break;
        					}
    						if(list2 != null){
    							String org = excelUtil.oBProductService.selOrgByCategory(list2.get(0).getId(),null);
    							if(org != null){
    								if(! org.equals(orgId)){
    									errMsg=String.valueOf(row.getRowNum()+1)+"行C列错误，该目录已有采购机构!";
    	        						map.put("errMsg", errMsg);
    		        					 bool=false;
    			        				 break;
    								}
    							}
    							obp.setProcurementId(orgId);
    							obp.setSmallPointsId(list2.get(0).getId());
    						}
    					}
    				}
    				/*//第五列
    				if(cell.getColumnIndex()==4){
    					if(cell.getCellType()==1){
        					//判断是否为空
        					if(cell.getStringCellValue().trim().length() >= 1){
	        					//验证产品目录是否存在
	        					String categoryCode = cell.getRichStringCellValue().toString();
	        					if(excelUtil.categoryService.findByCode(categoryCode) < 1){
	        						errMsg=String.valueOf(row.getRowNum()+1)+"行E列错误，产品目录不存在!";
	        						map.put("errMsg", errMsg);
		        					 bool=false;
			        				 break;
	        					}else{
	        						Category category = new Category();
	        						category.setCode(categoryCode);
	        						List<Category> categorylist = excelUtil.categoryService.readExcel(category);
	        						if(categorylist != null){
	        							obp.setCategoryMiddleId(categorylist.get(0).getId());
	        						}
	        					}
        					}
        				}
        			 }
    				//第六列
    				if(cell.getColumnIndex()==5){
    					if(cell.getCellType()==1){
        					//判断是否为空
        					if(cell.getStringCellValue().trim().length() >= 1){
	        					//验证产品目录是否存在
	        					String categoryCode = cell.getRichStringCellValue().toString();
	        					if(excelUtil.categoryService.findByCode(categoryCode) < 1){
	        						errMsg=String.valueOf(row.getRowNum()+1)+"行F列错误，产品目录不存在!";
	        						map.put("errMsg", errMsg);
		        					 bool=false;
			        				 break;
	        					}else{
	        						Category category = new Category();
	        						category.setCode(categoryCode);
	        						List<Category> categorylist = excelUtil.categoryService.readExcel(category);
	        						if(categorylist != null){
	        							obp.setCategoryId(categorylist.get(0).getId());
	        						}
	        					}
        					}
        				}
        			 }
    				//第七列
    				if(cell.getColumnIndex()==6){
    					if(cell.getCellType()==1){
        					//判断是否为空
        					if(cell.getStringCellValue().trim().length() >= 1){
	        					//验证产品目录是否存在
	        					String categoryCode = cell.getRichStringCellValue().toString();
	        					if(excelUtil.categoryService.findByCode(categoryCode) < 1){
	        						errMsg=String.valueOf(row.getRowNum()+1)+"行G列错误，产品目录不存在!";
	        						map.put("errMsg", errMsg);
		        					 bool=false;
			        				 break;
	        					}else{
	        						Category category = new Category();
	        						category.setCode(categoryCode);
	        						List<Category> categorylist = excelUtil.categoryService.readExcel(category);
	        						if(categorylist != null){
	        							obp.setProductCategoryId(categorylist.get(0).getId());
	        						}
	        					}
        					}
        				}
        			 }*/
        			
        			//第五列
    				if(cell.getColumnIndex()==4){
    					String standardModel = cell.getRichStringCellValue().toString();
    					obp.setStandardModel(standardModel);
    				}
    				//第六列
    				if(cell.getColumnIndex()==5){
    					String qualityTechnicalStandard = cell.getRichStringCellValue().toString();
    					obp.setQualityTechnicalStandard(qualityTechnicalStandard);;
    				}
        		}
        		if(bool==false)break;
        		list.add(obp);
        	}
        }
        map.put("list", list);
		return map;
	}
	
	/**
	 * 
	 * Description: 供应商列表上传
	 * 
	 * @author  zhang shubin
	 * @version  2017年3月16日 
	 * @param  @param file
	 * @param  @return
	 * @param  @throws Exception 
	 * @return Map<String,Object> 
	 * @exception
	 */
	public static Map<String,Object> readOBSupplierExcel(MultipartFile file) throws Exception{
		List<OBSupplier> list=new LinkedList<OBSupplier>();
		Map<String,Object> map=new HashMap<String,Object>();
		Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        String errMsg=null;
        String planName="";
        String suId = "";
        boolean bool=true;
        for(Row row:sheet){
        	String uscc = "";
        	OBSupplier obp=new OBSupplier();
        	for(Cell cell : row){
        		if(cell.getColumnIndex()==0){
    				planName=cell.getStringCellValue();
    			}
        	}
        	if(row.getRowNum()>1){
        		Cell cel = row.getCell(0);
        		if(cel==null){
					 errMsg=String.valueOf(row.getRowNum()+1)+"行A列错误，不能为空!";
					 map.put("errMsg", errMsg);
					 bool=false;
   				 break;
				}
        		for (Cell cell : row) {
        			//判断第一列
        			if(cell.getColumnIndex()==0){
        				if(cell.getCellType()==1){
        					//判断是否为空
        					if(cell.getStringCellValue().trim().length()<1){
	        					errMsg=String.valueOf(row.getRowNum()+1)+"行A列错误，供应商名称不能为空!";
	        					 map.put("errMsg", errMsg);
	        					 bool=false;
		        				 break;
	        				}
        					String supplierName = cell.getRichStringCellValue().toString();
        					List<Supplier> listsupplier = excelUtil.supplierService.selByName(supplierName);
        					//验证供应商是否存在
        					if(listsupplier == null || listsupplier.size() < 1){
        						errMsg=String.valueOf(row.getRowNum()+1)+"行A列错误，供应商不存在!";
        						map.put("errMsg", errMsg);
	        					 bool=false;
		        				 break;
        					}
        					Supplier supplier = listsupplier.get(0);
        					uscc = supplier.getCreditCode();
        					suId = supplier.getId();
        					obp.setSupplierId(supplier.getId());
        				}
        			}
        			//第二列
    				if(cell.getColumnIndex()==1){
    					if(cell.getCellType()==1){
        					//判断是否为空
        					if(cell.getStringCellValue().trim().length()<1){
	        					errMsg=String.valueOf(row.getRowNum()+1)+"行B列错误，不能为空!";
	        					 map.put("errMsg", errMsg);
	        					 bool=false;
		        				 break;
	        				}
        				}else if(HSSFDateUtil.isCellDateFormatted(cell)){
        					Date time = cell.getDateCellValue();
        					obp.setCertValidPeriod(time);
        				}else{
        					errMsg=String.valueOf(row.getRowNum()+1)+"行B列错误，日期格式错误!";
       					 map.put("errMsg", errMsg);
       					 bool=false;
	        				 break;
        				}
        			 }
        			//第三列
    				if(cell.getColumnIndex()==2){
    					if(cell.getCellType()==1){
        					//判断是否为空
        					if(cell.getStringCellValue().trim().length()<1){
	        					errMsg=String.valueOf(row.getRowNum()+1)+"行C列错误，不能为空!";
	        					 map.put("errMsg", errMsg);
	        					 bool=false;
		        				 break;
	        				}
        					String str = cell.getRichStringCellValue().toString();
        					obp.setQualityInspectionDep(str);
        				}
        			 }
        			//第四列
    				if(cell.getColumnIndex()==3){
    					if(cell.getCellType()==1){
        					//判断是否为空
        					if(cell.getStringCellValue().trim().length()<1){
	        					errMsg=String.valueOf(row.getRowNum()+1)+"行D列错误，不能为空!";
	        					 map.put("errMsg", errMsg);
	        					 bool=false;
		        				 break;
	        				}
        					String str = cell.getRichStringCellValue().toString();
        					obp.setContactName(str);
        				}
        			 }
    				//第五列
    				if(cell.getColumnIndex()==4){
    					if(cell.getCellType()==0){
    						int i = (int) cell.getNumericCellValue();
       					 obp.setContactTel(Integer.toString(i));//数量
		        			 continue;
       				 }if(cell.getCellType()!=3){
       					 errMsg=String.valueOf(row.getRowNum()+1)+"行，E列错误,请输入正确的电话号";
	        				 map.put("errMsg", errMsg);
	        				 bool=false;
	        				 break;
	        			}
        			 }
    				//第六列
    				if(cell.getColumnIndex()==5){
    					if(cell.getCellType()==1){
        					//判断是否为空
        					if(cell.getStringCellValue().trim().length()<1){
	        					errMsg=String.valueOf(row.getRowNum()+1)+"行F列错误，不能为空!";
	        					 map.put("errMsg", errMsg);
	        					 bool=false;
		        				 break;
	        				}else{
	        					Integer ii = excelUtil.oBSupplierService.yzzsCode(cell.getRichStringCellValue().toString(), null);
	        					if(ii > 0){
	        						errMsg=String.valueOf(row.getRowNum()+1)+"行F列错误，资质证书编号不能重复!";
		        					 map.put("errMsg", errMsg);
		        					 bool=false;
			        				 break;
	        					}
	        				}
        					String str = cell.getRichStringCellValue().toString();
        					obp.setCertCode(str);
        				}
        			 }
    				//第七列
    				if(cell.getColumnIndex()==6){
    					if(cell.getCellType()==1){
        					//判断是否为空
        					if(cell.getStringCellValue().trim().length()<1){
	        					errMsg=String.valueOf(row.getRowNum()+1)+"行G列错误，不能为空!";
	        					 map.put("errMsg", errMsg);
	        					 bool=false;
		        				 break;
	        				}
        					String str = cell.getRichStringCellValue().toString();
        					if(!str.trim().equals(uscc)){
        						errMsg=String.valueOf(row.getRowNum()+1)+"行G列错误，统一社会信用代码不正确!";
	        					 map.put("errMsg", errMsg);
	        					 bool=false;
		        				 break;
        					}
        					obp.setUscc(str);
        					}
        				}
    				//第八列
    				if(cell.getColumnIndex()==7){
    					if(cell.getCellType()==1){
        					//判断是否为空
        					if(cell.getStringCellValue().trim().length()<1){
	        					errMsg=String.valueOf(row.getRowNum()+1)+"行H列错误，不能为空!";
	        					 map.put("errMsg", errMsg);
	        					 bool=false;
		        				 break;
	        				}
        					String str = cell.getRichStringCellValue().toString();
        					List<Category> list2 = excelUtil.categoryService.selectByCode(str);
        					if(null == list2 || list2.size() == 0){
        						errMsg=String.valueOf(row.getRowNum()+1)+"行H列错误，目录不存在!";
	        					 map.put("errMsg", errMsg);
	        					 bool=false;
		        				 break;
        					}else{
        						HashMap<String, Object> map1 = new HashMap<String, Object>();
        						map1.put("id", list2.get(0).getId());
        						if(excelUtil.categoryService.findCategoryByChildren(map1).size() != 0){
        							errMsg=String.valueOf(row.getRowNum()+1)+"行H列错误，请选择目录末节点添加!";
   	        					 	map.put("errMsg", errMsg);
   	        					 	bool=false;
   	        					 	break;
        						}
        						if(excelUtil.oBSupplierService.yzSupplierName(suId, list2.get(0).getId(), null) > 0){
        							errMsg=String.valueOf(row.getRowNum()+1)+"行H列错误，不能重复添加!";
   	        					 map.put("errMsg", errMsg);
   	        					 bool=false;
   		        				 break;
        						}
        					}
        					obp.setSmallPointsId(list2.get(0).getId());
        					}
        				}
        		}
        		if(bool==false)break;
        		list.add(obp);
        	}
        }
        map.put("list", list);
		return map;
	
	}
	
	
	
	
	public static void readPlanExcel(MultipartFile file) throws Exception{
    List<PurchaseRequired> list=new LinkedList<PurchaseRequired>();
    
          Workbook workbook = WorkbookFactory.create(file.getInputStream());
         
          Sheet sheet = workbook.getSheetAt(0);
          Row firstRow = sheet.getRow(0);
          CollectPlan collect = new CollectPlan();
          Cell planName = firstRow.getCell(0);
          collect.setFileName(planName.toString());
          CollectPlanService collPlan = new CollectPlanServiceImpl();
          String id = UUID.randomUUID().toString().replaceAll("-", "");
          collect.setId(id);
          collPlan.add(collect);
          

  }
	
	
	
	
	/**
	 * 
	* @Title: isAddMer
	* @Description: 判断是否合并单元格
	* author: Li Xiaoxiao 
	* @param @param sheet
	* @param @param r
	* @param @param c
	* @param @return     
	* @return boolean     
	* @throws
	 */
	public static boolean isAddMer(Sheet sheet,int r,int c){
		boolean bool=true;

	      int sheetMergeCount = sheet.getNumMergedRegions();  
	      for (int i = 0; i < sheetMergeCount; i++) {  
	        CellRangeAddress range = sheet.getMergedRegion(i);  
	        int firstColumn = range.getFirstColumn();  
	        int lastColumn = range.getLastColumn();  
	        int firstRow = range.getFirstRow();  
	        int lastRow = range.getLastRow();  
	        if(r == firstRow && r == lastRow){  
	            if(c >= firstColumn && c <= lastColumn){  
	                return true;  
	            }  
	        }  
	      }  
		return bool;
	}
	/**
	 * 
	* @Title: getMergedRegionValue
	* @Description: 获取合并单元格的第一个值
	* author: Li Xiaoxiao 
	* @param @param sheet
	* @param @param row
	* @param @param column
	* @param @return     
	* @return String     
	* @throws
	 */
    public static BigDecimal getMergedRegionValue(Sheet sheet ,int row , int column){    
        
        int sheetMergeCount = sheet.getNumMergedRegions();    
            
        for(int i = 0 ; i < sheetMergeCount ; i++){    
            CellRangeAddress ca = sheet.getMergedRegion(i);    
            int firstColumn = ca.getFirstColumn();    
            int lastColumn = ca.getLastColumn();    
            int firstRow = ca.getFirstRow();    
            int lastRow = ca.getLastRow();    
                
            if(row >= firstRow && row <= lastRow){    
                    
                if(column >= firstColumn && column <= lastColumn){    
                    Row fRow = sheet.getRow(firstRow);    
                    Cell fCell = fRow.getCell(firstColumn);    
                    return getCellValue(fCell) ;    
                }    
            }    
        }    
            
        return null ;    
    }    
    
    public static BigDecimal getCellValue(Cell cell){    
        
          if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){    
                
            return new BigDecimal(cell.getNumericCellValue());    
                
        }    
        return null;    
    }    
 

    
    public static boolean isContainChinese(String str){
		boolean bool=true;
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(str);
	    if(m.find()==true&&!str.contains("（")){
	        	bool=true;
	     }else{
	        	bool=false;
	    }
		return bool;
	}
    
    
    
    
 
}
