package cn.wang.custom.utils.excel;

import cn.wang.custom.utils.WDateUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

public class WExcelUtils {
    /**
     * 日期格式指定
     */
    public static final String EXCEL_ROW_MAP_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * 判断excel版本新旧
     *
     * @param file 文件对象
     * @return true-旧 false-新
     */
    public static boolean isOld(File file) {
        String name = file.getName();
        return isOld(name);
    }

    /**
     * 判断excel版本新旧
     *
     * @param fileName 文件名
     * @return true-旧 false-新
     */
    public static boolean isOld(String fileName) {
        if (fileName.toLowerCase().endsWith(".xls")) {
            return true;
        } else if (fileName.toLowerCase().endsWith(".xlsx")) {
            return false;
        } else {
            throw new RuntimeException(fileName + "格式不正确");
        }
    }


    /**
     * 得到excel工作对象
     *
     * @param fileDate 文件字节数组
     * @param isOld    是否xls结尾文件
     * @return excel工作对象
     * @throws IOException
     */
    public static Workbook getBook(byte[] fileDate, boolean isOld) throws IOException {
        if (fileDate == null || fileDate.length == 0) {
            throw new RuntimeException("excel文件不能为空");
        }
        ByteArrayInputStream input = new ByteArrayInputStream(fileDate);
        if (isOld) {
            return new HSSFWorkbook(input);
        } else {
            return new XSSFWorkbook(input);
        }
    }

    /**
     * 得到列值
     *
     * @param cell 待取值列对象
     * @return 列值
     */
    public static String getCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        CellType cellType = cell.getCellType();
        String value;
        switch (cellType) {
            case NUMERIC: // 数字
                //如果为时间格式的内容
                if (DateUtil.isCellDateFormatted(cell)) {
                    //注：format格式 yyyy-MM-dd hh:mm:ss 中小时为12小时制，若要24小时制，则把小h变为H即可，yyyy-MM-dd HH:mm:ss
                    value = WDateUtils.getStr(cell.getDateCellValue(),EXCEL_ROW_MAP_DATE_FORMAT);
                    break;
                } else {
                    value = new DecimalFormat("#.#######").format(cell.getNumericCellValue());
                }
                break;
            case STRING: // 字符串
                value = cell.getStringCellValue();
                break;
            case BOOLEAN: // Boolean
                value = cell.getBooleanCellValue() + "";
                break;
            case FORMULA: // 公式
                value = cell.getCellFormula() + "";
                break;
            case _NONE: // 空值
            case BLANK: // 空值
                value = "";
                break;
            case ERROR: // 故障
                value = "非法字符";
                break;
            default:
                value = "未知类型";
                break;
        }
        return value;
    }

    /**
     * 得到空工作对象
     * @param isOld
     * @return
     */
    public static Workbook getBook(boolean isOld){
        if (isOld){
            return new HSSFWorkbook();
        }
        return new XSSFWorkbook();
    }
}
