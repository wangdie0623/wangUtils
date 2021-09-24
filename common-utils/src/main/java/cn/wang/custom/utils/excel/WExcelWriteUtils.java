package cn.wang.custom.utils.excel;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * excel写相关工具类
 */
public class WExcelWriteUtils {
    /**
     * 写excel文件
     *
     * @param isOld     是否旧格式
     * @param content   内容
     * @param sheetName sheet名称
     * @return
     */
    public static byte[] writeBook(boolean isOld, List<Map<Integer, String>> content, String sheetName) throws IOException {
        Workbook book = WExcelUtils.getBook(isOld);
        Sheet sheet = null;
        if (StringUtils.isBlank(sheetName)) {
            sheet = book.createSheet();
        } else {
            sheet = book.createSheet(sheetName);
        }
        for (int i = 0; i < content.size(); i++) {
            Row row = sheet.createRow(i);
            Map<Integer, String> rowData = content.get(i);
            for (Map.Entry<Integer, String> entry : rowData.entrySet()) {
                Integer cellIndex = entry.getKey();
                String cellVal = entry.getValue();
                Cell cell = row.createCell(cellIndex);
                cell.setCellValue(cellVal);
            }
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        book.write(out);
        book.close();
        byte[] result = out.toByteArray();
        out.close();
        return result;
    }
}
