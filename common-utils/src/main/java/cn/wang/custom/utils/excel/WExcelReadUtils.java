package cn.wang.custom.utils.excel;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.*;

/**
 * excel文件读相关工具类
 */
@Slf4j
public class WExcelReadUtils {

    /**
     * 获取指定工作区格式化数据
     *
     * @param fileDate   数据字节数组
     * @param fileName   数据文件名
     * @param sheetIndex 工作区下标 从0开始
     * @return
     */
    public static List<Map<Integer, String>> getExcelRowMapList(byte[] fileDate, String fileName, int sheetIndex) {
        try (Workbook book = WExcelUtils.getBook(fileDate, WExcelUtils.isOld(fileName))) {
            return getExcelRowMapList(book, sheetIndex);
        } catch (Exception e) {
            log.error("解析excel文件异常：" + fileName + "|" + sheetIndex, e);
            throw new RuntimeException("解析excel文件异常：" + fileName + "|" + sheetIndex);
        }
    }

    /**
     * 获取指定工作区格式化数据
     *
     * @param fileDate  数据字节数组
     * @param fileName  数据文件名
     * @param sheetName 工作区名
     * @return
     */
    public static List<Map<Integer, String>> getExcelRowMapList(byte[] fileDate, String fileName, String sheetName) {
        try (Workbook book = WExcelUtils.getBook(fileDate, WExcelUtils.isOld(fileName))) {
            return getExcelRowMapList(book, sheetName);
        } catch (Exception e) {
            log.error("解析excel文件异常：" + fileName + "|" + sheetName, e);
            throw new RuntimeException("解析excel文件异常：" + fileName + "|" + sheetName);
        }
    }

    private static List<Map<Integer, String>> getExcelRowMapList(Workbook book, String sheetName) {
        if (book == null) {
            return Collections.emptyList();
        }
        Sheet sheet = book.getSheet(sheetName);
        return getExcelRowMapList(book, sheet);
    }

    private static List<Map<Integer, String>> getExcelRowMapList(Workbook book, int sheetIndex) {
        if (book == null) {
            return Collections.emptyList();
        }
        Sheet sheet = book.getSheetAt(sheetIndex);
        return getExcelRowMapList(book, sheet);
    }

    /**
     * 获取指定工作区数据
     *
     * @param book  工作对象
     * @param sheet 工作区下标 从0开始
     * @return 工作区数据
     */
    private static List<Map<Integer, String>> getExcelRowMapList(Workbook book, Sheet sheet) {
        if (sheet == null) {
            return Collections.emptyList();
        }
        Iterator<Row> rowIterator = sheet.rowIterator();
        List<Map<Integer, String>> result = new ArrayList<>();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            int rowNum = row.getRowNum();
            //当前行真实有值的列数
            int realCellValCount = row.getPhysicalNumberOfCells();
            if (realCellValCount == 0) {
                break;
            }
            Map<Integer, String> rowMap = new HashMap<>();
            rowMap.put(-1, Integer.toString(rowNum));
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                int cellNum = cell.getColumnIndex();
                String cellValue = WExcelUtils.getCellValue(cell);
                rowMap.put(cellNum, cellValue);
            }
            result.add(rowMap);
        }
        return result;
    }


}
