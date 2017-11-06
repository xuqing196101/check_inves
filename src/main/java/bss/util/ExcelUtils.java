package bss.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @param
 * @author zhangxq
 * @author Easong
 * @since JDK1.7
 */
public class ExcelUtils {
    // 定义日志记录
    private Logger log = LoggerFactory.getLogger(this.getClass());

    public static final String OFFICE_EXCEL_2003_POSTFIX = "xls";
    public static final String OFFICE_EXCEL_2010_POSTFIX = "xlsx";
    public static final String EMPTY = "";
    public static final String POINT = ".";
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

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
    DecimalFormat floatDecimalFormat = new DecimalFormat(floatDecimal);
    DecimalFormat doubleDecimalFormat = new DecimalFormat(doubleDecimal);
    private HSSFWorkbook workbook = null;

    public ExcelUtils(String fileDir, String sheetName) {
        this.fileDir = fileDir;
        this.sheetName = sheetName;
        workbook = new HSSFWorkbook();
    }

    public ExcelUtils(HttpServletResponse response, String fileName, String sheetName) {
        this.response = response;
        this.sheetName = sheetName;
        this.fileName = fileName;
        workbook = new HSSFWorkbook();
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
    public void wirteExcel(String titleColumn[], String titleName[], int titleSize[], List<?> dataList) {
        //添加Worksheet（不添加sheet时生成的xls文件打开时会报错)
        Sheet sheet = workbook.createSheet(this.sheetName);
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
                fileName = fileName + ".xls";
                response.setContentType("application/x-msdownload");
                response.setHeader("Content-Disposition", "attachment; filename="
                        + URLEncoder.encode(fileName, "UTF-8"));
            }
            //写入excel的表头
            Row titleNameRow = workbook.getSheet(sheetName).createRow(0);
            //--设置样式开始
            HSSFCellStyle titleStyle = workbook.createCellStyle();
            titleStyle = (HSSFCellStyle) setFontAndBorder(titleStyle, titleFontType, (short) titleFontSize);
            titleStyle = (HSSFCellStyle) setColor(titleStyle, titleBackColor, (short) 10);
            //--设置样式结束
            for (int i = 0; i < titleName.length; i++) {
                if(titleSize != null){
                    sheet.setColumnWidth(i, titleSize[i] * 256); //设置宽度
                }
                Cell cell = titleNameRow.createCell(i);
                cell.setCellStyle(titleStyle);
                cell.setCellValue(titleName[i].toString());
            }
            //为表头添加自动筛选
            if (!"".equals(address)) {
                CellRangeAddress c = (CellRangeAddress) CellRangeAddress.valueOf(address);
                sheet.setAutoFilter(c);
            }
            //通过反射获取数据并写入到excel中
            // 定义序号
            int orderNum = 0;
            if (dataList != null && dataList.size() > 0) {
                //设置样式
                titleStyle = (HSSFCellStyle) setFontAndBorder(titleStyle, contentFontType, (short) contentFontSize);
                if (titleColumn.length > 0) {
                    for (int rowIndex = 1; rowIndex <= dataList.size(); rowIndex++) {
                        orderNum += 1;
                        Object obj = dataList.get(rowIndex - 1); //获得该对象
                        Class clsss = obj.getClass(); //获得该对对象的class实例
                        Row dataRow = workbook.getSheet(sheetName).createRow(rowIndex);
                        for (int columnIndex = 0; columnIndex < titleColumn.length; columnIndex++) {
                            Cell cell = dataRow.createCell(columnIndex);
                            if(columnIndex == 0){
                                // 设置第一列为序号并且居中
                                titleStyle = (HSSFCellStyle)setAlignment(titleStyle);
                                cell.setCellValue(orderNum);
                                cell.setCellStyle(titleStyle);
                                continue;
                            }
                            String title = titleColumn[columnIndex].toString().trim();
                            if (!"".equals(title)) { //字段不为空
                                //使首字母大写
                                String UTitle = Character.toUpperCase(title.charAt(0)) + title.substring(1, title.length()); // 使其首字母大写;
                                String methodName = "get" + UTitle;
                                // 设置要执行的方法
                                Method method = clsss.getDeclaredMethod(methodName);
                                //获取返回类型
                                String returnType = method.getReturnType().getName();
                                String data = method.invoke(obj) == null ? "" : method.invoke(obj).toString();
                                if (data != null && !"".equals(data)) {
                                    if ("int".equals(returnType) || "java.lang.Integer".equals(returnType)) {
                                        cell.setCellValue(Integer.parseInt(data));
                                    } else if ("long".equals(returnType) || "java.lang.Long".equals(returnType)) {
                                        cell.setCellValue(Long.parseLong(data));
                                    } else if ("float".equals(returnType) || "java.lang.Float".equals(returnType)) {
                                        cell.setCellValue(floatDecimalFormat.format(Float.parseFloat(data)));
                                    } else if ("double".equals(returnType) || "java.lang.Double".equals(returnType)) {
                                        cell.setCellValue(doubleDecimalFormat.format(Double.parseDouble(data)));
                                    } else {
                                        cell.setCellValue(data);
                                    }
                                }
                            } else { //字段为空 检查该列是否是公式
                                if (colFormula != null) {
                                    String sixBuf = colFormula[columnIndex].replace("@", (rowIndex + 1) + "");
                                    cell = dataRow.createCell(columnIndex);
                                    cell.setCellFormula(sixBuf.toString());
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
            HSSFPalette palette = workbook.getCustomPalette();
            palette.setColorAtIndex((short) index, (byte) r, (byte) g, (byte) b);
            style.setFillPattern(CellStyle.SOLID_FOREGROUND);
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
        HSSFFont font = workbook.createFont();
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
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
        return style;
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
}
