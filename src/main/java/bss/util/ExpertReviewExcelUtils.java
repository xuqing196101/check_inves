package bss.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ses.model.ems.ExpertBatchDetails;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: Excel表格操作工具
 *
 * @author Easong
 * @author zhangxq
 * @version 2017/11/9
 * @since JDK1.7
 */
public class ExpertReviewExcelUtils {
    // 定义日志记录
    private Logger log = LoggerFactory.getLogger(this.getClass());

    public static final String OFFICE_EXCEL_2003_POSTFIX = ".xls";
    public static final String OFFICE_EXCEL_2007_POSTFIX = ".xlsx";
    public static final String EMPTY = "";
    public static final String POINT = ".";
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    HttpServletResponse response;
    // 文件名
    private String fileName;
    //文件保存路径
    private String fileDir;
    //sheet名
    private String sheetName;
    //表头字体
    private String titleFontType = "Arial Unicode MS";
    //表头背景色
    private String titleBackColor = "C1FBEE";
    //表头字号
    private short titleFontSize = 12;
    //添加自动筛选的列 如 A:M
    private String address = "";
    //正文字体
    private String contentFontType = "Arial Unicode MS";
    //正文字号
    private short contentFontSize = 12;
    //Float类型数据小数位
    private String floatDecimal = ".00";
    //Double类型数据小数位
    private String doubleDecimal = ".00";
    //设置列的公式
    private String colFormula[] = null;
    // 设置冻结操作
    /*
        1:colSplit
        拆分单元格的列（Excel中row的标号）
        2:rowSplit
        拆分单元格的行（Excel中row的标号）
        leftmostColumn
        3:右边区域可见的左边列数（Excel中column的标号）
        topRow
        4:下面区域可见的首行（Excel中column的标号）
    */
    private Integer[] freezePane;
    // 是否设置冻结模式
    private boolean isFreezePane = false;
    // 是否设置序号列
    private boolean isOrder = false;

    DecimalFormat floatDecimalFormat = new DecimalFormat(floatDecimal);
    DecimalFormat doubleDecimalFormat = new DecimalFormat(doubleDecimal);
    private SXSSFWorkbook workbook = null;

    // 内存中缓存记录行数
    private int size;

    public ExpertReviewExcelUtils(String fileDir, String sheetName, Integer size) {
        this.fileDir = fileDir;
        this.sheetName = sheetName;
        this.size = size;
        workbook = new SXSSFWorkbook(size);
    }

    public ExpertReviewExcelUtils(HttpServletResponse response, String fileName, String sheetName, Integer size) {
        this.response = response;
        this.sheetName = sheetName;
        this.fileName = fileName;
        this.size = size;
        workbook = new SXSSFWorkbook(size);
    }

    public Integer[] getFreezePane() {
        return freezePane;
    }

    public void setFreezePane(Integer[] freezePane) {
        this.freezePane = freezePane;
    }

    public boolean isFreezePane() {
        return isFreezePane;
    }

    public void setFreezePane(boolean freezePane) {
        isFreezePane = freezePane;
    }

    public boolean isOrder() {
        return isOrder;
    }

    public void setOrder(boolean order) {
        isOrder = order;
    }

    /**
     * 设置表头字体.
     *
     * @param titleFontType
     */
    public void setTitleFontType(String titleFontType) {
        this.titleFontType = titleFontType;
    }

    /**
     * 设置表头背景色.
     *
     * @param titleBackColor 十六进制
     */
    public void setTitleBackColor(String titleBackColor) {
        this.titleBackColor = titleBackColor;
    }

    /**
     * 设置表头字体大小.
     *
     * @param titleFontSize
     */
    public void setTitleFontSize(short titleFontSize) {
        this.titleFontSize = titleFontSize;
    }

    /**
     * 设置表头自动筛选栏位,如A:AC.
     *
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 设置正文字体.
     *
     * @param contentFontType
     */
    public void setContentFontType(String contentFontType) {
        this.contentFontType = contentFontType;
    }

    /**
     * 设置正文字号.
     *
     * @param contentFontSize
     */
    public void setContentFontSize(short contentFontSize) {
        this.contentFontSize = contentFontSize;
    }

    /**
     * 设置float类型数据小数位 默认.00
     *
     * @param doubleDecimal 如 ".00"
     */
    public void setDoubleDecimal(String doubleDecimal) {
        this.doubleDecimal = doubleDecimal;
    }

    /**
     * 设置doubel类型数据小数位 默认.00
     *
     * @param floatDecimalFormat 如 ".00
     */
    public void setFloatDecimalFormat(DecimalFormat floatDecimalFormat) {
        this.floatDecimalFormat = floatDecimalFormat;
    }

    /**
     * 设置列的公式
     *
     * @param colFormula 存储i-1列的公式 涉及到的行号使用@替换 如A@+B@
     */
    public void setColFormula(String[] colFormula) {
        this.colFormula = colFormula;
    }

    /**
     * 获得path的后缀名
     *
     * @param path
     * @return
     */
    public static String getPostfix(String path) {
        if (path == null || EMPTY.equals(path.trim())) {
            return EMPTY;
        }
        if (path.contains(POINT)) {
            return path.substring(path.lastIndexOf(POINT) + 1, path.length());
        }
        return EMPTY;
    }

    /**
     * 单元格格式
     *
     * @param hssfCell
     * @return
     */
    @SuppressWarnings({"static-access", "deprecation"})
    public static String getHValue(HSSFCell hssfCell) {
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
            String cellValue = "";
            if (HSSFDateUtil.isCellDateFormatted(hssfCell)) {
                Date date = HSSFDateUtil.getJavaDate(hssfCell.getNumericCellValue());
                cellValue = sdf.format(date);
            } else {
                DecimalFormat df = new DecimalFormat("#.##");
                cellValue = df.format(hssfCell.getNumericCellValue());
                String strArr = cellValue.substring(cellValue.lastIndexOf(POINT) + 1, cellValue.length());
                if (strArr.equals("00")) {
                    cellValue = cellValue.substring(0, cellValue.lastIndexOf(POINT));
                }
            }
            return cellValue;
        } else {
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }

    /**
     * 单元格格式
     *
     * @param xssfCell
     * @return
     */
    public static String getXValue(XSSFCell xssfCell) {
        if (xssfCell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(xssfCell.getBooleanCellValue());
        } else if (xssfCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            String cellValue = "";
            if (DateUtil.isCellDateFormatted(xssfCell)) {
                Date date = DateUtil.getJavaDate(xssfCell.getNumericCellValue());
                cellValue = sdf.format(date);
            } else {
                DecimalFormat df = new DecimalFormat("#.##");
                cellValue = df.format(xssfCell.getNumericCellValue());
                String strArr = cellValue.substring(cellValue.lastIndexOf(POINT) + 1, cellValue.length());
                if (strArr.equals("00")) {
                    cellValue = cellValue.substring(0, cellValue.lastIndexOf(POINT));
                }
            }
            return cellValue;
        } else {
            return String.valueOf(xssfCell.getStringCellValue());
        }
    }

    /**
     * Description: write excel operate
     *
     * @param titleColumn 对应bean的属性名
     *                    example：titleColumn[] = {"name", "age", "sex", "sal", ""};
     * @param titleName   excel要导出的表名
     *                    example：titleName[] = {"姓名", "性别", "身份证号", "月薪", "年薪"};
     * @param titleSize   列宽
     *                    example：titleSize[] = {13, 13, 13, 13, 13};
     * @param dataList    数据
     *                    example： List<Object> dataList = new ArrayList();
     * @author Easong
     * @version 2017/11/6
     * @since JDK1.7
     */
    public void wirteExcel(String titleColumn[], String titleName[], int titleSize[],Map<String,List<ExpertBatchDetails>> map) {
        //添加Worksheet（不添加sheet时生成的xls文件打开时会报错)
        Sheet sheet = workbook.createSheet(this.sheetName);
        String tableName=fileName;
        // 设置Excel密码保护
        // sheet.protectSheet("xxx");
        /*冻结行操作
            colSplit
            拆分单元格的列（Excel中row的标号）
            rowSplit
            拆分单元格的行（Excel中row的标号）
            leftmostColumn
            右边区域可见的左边列数（Excel中column的标号）
            topRow
            下面区域可见的首行（Excel中column的标号）
         */
        if (isFreezePane && freezePane != null) {
            sheet.createFreezePane(freezePane[0], freezePane[1], freezePane[2], freezePane[3]);
        }
        //新建文件
        OutputStream out = null;
        try {
            // 将Excel下载到指定的目录下
            if (fileDir != null) {
                //有文件路径
                File file = new File(fileDir);
                if (!file.exists()) {
                    file.getParentFile().mkdir();
                    file.createNewFile();
                }
                out = new FileOutputStream(file);
            } else {
                //否则，直接写到输出流中
                out = response.getOutputStream();
                fileName = fileName + OFFICE_EXCEL_2007_POSTFIX;
                response.setContentType("application/x-msdownload");
                response.setHeader("Content-Disposition", "attachment; filename="
                        + URLEncoder.encode(fileName, "UTF-8"));
            }
            Row firstNameRow = workbook.getSheet(sheetName).createRow(0);
            CellStyle firstStyle=workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setFontHeightInPoints((short)20);
            font.setFontName("宋体");
            font.setBold(true);
            firstStyle.setFont(font);
            firstStyle.setBorderBottom(CellStyle.BORDER_THIN); //下边框
            firstStyle.setBorderLeft(CellStyle.BORDER_THIN);//左边框
            firstStyle.setBorderTop(CellStyle.BORDER_THIN);//上边框
            firstStyle.setBorderRight(CellStyle.BORDER_THIN);//右边框
            firstStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中 
            Cell firstCell = firstNameRow.createCell(0);
            firstCell.setCellValue(tableName);
            firstCell.setCellStyle(firstStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,titleName.length-1));
            //写入excel的表头
            Row titleNameRow = workbook.getSheet(sheetName).createRow(1);
            //--设置样式开始
            CellStyle titleStyle = workbook.createCellStyle();
            // 单元格样式
            CellStyle cellStyle = null;
            titleStyle = setFontAndBorder(titleStyle, titleFontType, titleFontSize);
            //titleStyle = setColor(titleStyle, titleBackColor, (short) 10);
            //--设置样式结束
            for (int i = 0; i < titleName.length; i++) {
                if (titleSize != null) {
                    sheet.setColumnWidth(i, titleSize[i] * 256); //设置宽度
                }
                Cell cell = titleNameRow.createCell(i);
                cell.setCellStyle(titleStyle);
                cell.setCellValue(titleName[i].toString());
            }
            //为表头添加自动筛选
            if (!"".equals(address)) {
                CellRangeAddress c = CellRangeAddress.valueOf(address);
                sheet.setAutoFilter(c);
            }
            //通过反射获取数据并写入到excel中
            // 定义序号
            int orderNum = 0;
            if (map != null && map.size() > 0) {
                //每当行数达到设置的值就刷新数据到硬盘,以清理内存
                //设置样式
                titleStyle = setFontAndBorder(titleStyle, contentFontType, contentFontSize);
                if (titleColumn.length > 0) {
                    cellStyle = workbook.createCellStyle();
                    int rowIndexs=2;
                    int index=1;
                    CellStyle style=setAlignment(titleStyle);
                    for (String key : map.keySet()) {
                    	Row dataRow = workbook.getSheet(sheetName).createRow(rowIndexs);
                    	Cell cell = dataRow.createCell(0);
                    	cell.setCellStyle(style);
                    	if(index==1){
                    		cell.setCellValue("一");
                    		index++;
                    	}else if(index==2){
                    		cell.setCellValue("二");
                    		index++;
                    	}else if(index==3){
                    		cell.setCellValue("三");
                    		index++;
                    	}else if(index==4){
                    		cell.setCellValue("四");
                    		index++;
                    	}
                    	Cell cell2 = dataRow.createCell(1);
                    	cell2.setCellStyle(style);
                    	cell2.setCellValue(key+"("+map.get(key).size()+")");
                    	sheet.addMergedRegion(new CellRangeAddress(rowIndexs, rowIndexs, 1,4));
                    	List<ExpertBatchDetails> list = map.get(key);
                    	for (ExpertBatchDetails expertBatchDetails : list) {
							rowIndexs++;
							Row row = workbook.getSheet(sheetName).createRow(rowIndexs);
							int cellIndex=0;
							for (String s : titleColumn) {
								Cell c = row.createCell(cellIndex);
								Class clsss1 = expertBatchDetails.getClass();
								 //使首字母大写
                                String UTitle = Character.toUpperCase(s.charAt(0)) + s.substring(1, s.length()); // 使其首字母大写;
                                String methodName = "get" + UTitle;
                                // 设置要执行的方法
                                Method method = clsss1.getDeclaredMethod(methodName);
                                //获取返回类型
                                String returnType = method.getReturnType().getName();
                                Object data = method.invoke(expertBatchDetails) == null ? "" : method.invoke(expertBatchDetails);
                                // 写入单元格数据
                                packageData(cellStyle, data, c, returnType, s);
                                cellIndex++;
                             // 每当行数达到设置的值就刷新数据到硬盘,以清理内存
                                if (rowIndexs % size == 0) {
                                    ((SXSSFSheet) sheet).flushRows();
                                }
							}
						}
                    	
                    }
                }
            }
            workbook.write(out);
            out.flush();
        } catch (Exception e) {
            log.error("生成excel错误：", e);
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Description:
     *
     * @param [cellStyle, data, cell, returnType, title]
     *                    cellStyle: 单元格样式对象
     *                    data: 单元格数据
     *                    cell: 单元格对象
     *                    returnType: 字段返回值类型
     *                    title: 字段值
     * @author Easong
     * @version 2017/11/9
     * @since JDK1.7
     */
    public void packageData(CellStyle cellStyle, Object data, Cell cell, String returnType, String title) {
        // 设置自动换行
        // cellStyle.setWrapText(true);
        // cell.setCellStyle(cellStyle);
        if (data != null && !"".equals(data)) {
            if ("java.util.Date".equals(returnType)) {
                cell.setCellValue((Date) data);
                cell.setCellStyle(setFormatDate(cellStyle));
            } else if ("int".equals(returnType) || "java.lang.Integer".equals(returnType)) {
                cell.setCellValue(Integer.parseInt(data.toString()));
            } else if ("long".equals(returnType) || "java.lang.Long".equals(returnType)) {
                cell.setCellValue(Long.parseLong(data.toString()));
            } else if ("float".equals(returnType) || "java.lang.Float".equals(returnType)) {
                cell.setCellValue(floatDecimalFormat.format(Float.parseFloat(data.toString())));
            } else if ("double".equals(returnType) || "java.lang.Double".equals(returnType)) {
                cell.setCellValue(doubleDecimalFormat.format(Double.parseDouble(data.toString())));
            } else {
                cell.setCellValue(data.toString());
            }
        } else if ("instorageAt".equals(title)) {
            // 防止为空时前一个单元格内容占用后一个单元格
            cell.setCellValue("");
        }
    }

    /**
     * Description: 将16进制的颜色代码写入样式中来设置颜色
     *
     * @param style 保证style统一
     * @param color 颜色：66FFDD
     * @param index 索引 8-64 使用时不可重复
     * @author Easong
     * @version 2017/11/6
     * @since JDK1.7
     */
    public CellStyle setColor(CellStyle style, String color, short index) {
        if (color != "" && color != null) {
            //转为RGB码
            int r = Integer.parseInt((color.substring(0, 2)), 16); //转为16进制
            int g = Integer.parseInt((color.substring(2, 4)), 16);
            int b = Integer.parseInt((color.substring(4, 6)), 16);
            //自定义cell颜色
            /*HSSFPalette palette = workbook.getCustomPalette();
            palette.setColorAtIndex((short) index, (byte) r, (byte) g, (byte) b);
            style.setFillPattern(CellStyle.SOLID_FOREGROUND);*/
            style.setFillForegroundColor(index);
        }
        return style;
    }

    /**
     * Description: 设置字体并加外边框
     *
     * @param style 样式
     * @param style 字体名
     * @param style 大小
     * @author Easong
     * @version 2017/11/6
     * @since JDK1.7
     */
    public CellStyle setFontAndBorder(CellStyle style, String fontName, short size) {
        Font font = workbook.createFont();
        font.setFontHeightInPoints(size);
        font.setFontName(fontName);
        font.setBold(true);
        style.setFont(font);
        style.setBorderBottom(CellStyle.BORDER_THIN); //下边框
        style.setBorderLeft(CellStyle.BORDER_THIN);//左边框
        style.setBorderTop(CellStyle.BORDER_THIN);//上边框
        style.setBorderRight(CellStyle.BORDER_THIN);//右边框
        return style;
    }

    /**
     * Description: 设置字体并加外边框
     *
     * @param style 样式
     * @author Easong
     * @version 2017/11/6
     * @since JDK1.7
     */
    public CellStyle setAlignment(CellStyle style) {
        style.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 居中
        return style;
    }

    /**
     * Description: 日期转换
     *
     * @param style 样式
     * @author Easong
     * @version 2017/11/6
     * @since JDK1.7
     */
    public CellStyle setFormatDate(CellStyle cellStyle) {
        DataFormat format = workbook.createDataFormat();
        cellStyle.setDataFormat(format.getFormat("yyyy/MM/dd HH:mm:ss"));
        return cellStyle;
    }

    /**
     * Description: 删除文件
     *
     * @param []
     * @author Easong
     * @version 2017/11/6
     * @since JDK1.7
     */
    public boolean deleteExcel() {
        boolean flag = false;
        File file = new File(this.fileDir);
        // 判断目录或文件是否存在
        if (!file.exists()) { // 不存在返回 false
            return flag;
        } else {
            // 判断是否为文件
            if (file.isFile()) { // 为文件时调用删除文件方法
                file.delete();
                flag = true;
            }
        }
        return flag;
    }

    /**
     * Description: 删除指定文件
     *
     * @param [path]
     * @author Easong
     * @version 2017/11/6
     * @since JDK1.7
     */
    public boolean deleteExcel(String path) {
        boolean flag = false;
        File file = new File(path);
        // 判断目录或文件是否存在
        if (!file.exists()) { // 不存在返回 false
            return flag;
        } else {
            // 判断是否为文件
            if (file.isFile()) { // 为文件时调用删除文件方法
                file.delete();
                flag = true;
            }
        }
        return flag;
    }
    /**  
     * 合并单元格  
     * @param sheet 要合并单元格的excel 的sheet
     * @param cellLine  要合并的列  
     * @param startRow  要合并列的开始行  
     * @param endRow    要合并列的结束行  
     */  
    private static void addMergedRegion(HSSFSheet sheet, int cellLine, int startRow, int endRow,HSSFWorkbook workBook){   
           
     HSSFCellStyle style = workBook.createCellStyle(); // 样式对象    
     
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直    
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平    
        //获取第一行的数据,以便后面进行比较    
        String s_will = sheet.getRow(startRow).getCell(cellLine).getStringCellValue();   
     
        int count = 0;
        boolean flag = false;
        for (int i = 1; i <= endRow; i++) {   
         String s_current = sheet.getRow(i).getCell(0).getStringCellValue(); 
         if(s_will.equals(s_current))
         {
          s_will = s_current;
          if(flag)
          {
           sheet.addMergedRegion(new CellRangeAddress(startRow-count,startRow,cellLine,cellLine));
           HSSFRow row = sheet.getRow(startRow-count);
           String cellValueTemp = sheet.getRow(startRow-count).getCell(0).getStringCellValue(); 
           HSSFCell cell = row.createCell(0);
           cell.setCellValue(cellValueTemp); // 跨单元格显示的数据    
                  cell.setCellStyle(style); // 样式    
           count = 0;
           flag = false;
          }
          startRow=i;
          count++;          
         }else{
          flag = true;
          s_will = s_current;
         }
  //由于上面循环中合并的单元放在有下一次相同单元格的时候做的，所以最后如果几行有相同单元格则要运行下面的合并单元格。
         if(i==endRow&&count>0)
         {
          sheet.addMergedRegion(new CellRangeAddress(endRow-count,endRow,cellLine,cellLine));   
          String cellValueTemp = sheet.getRow(startRow-count).getCell(0).getStringCellValue(); 
          HSSFRow row = sheet.getRow(startRow-count);
       HSSFCell cell = row.createCell(0);
       cell.setCellValue(cellValueTemp); // 跨单元格显示的数据    
                cell.setCellStyle(style); // 样式    
         }
        }
    }
}
