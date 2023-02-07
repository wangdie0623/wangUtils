package cn.wang.custom.utils;


import cn.wang.custom.utils.excel.WExcelReadUtils;
import lombok.Data;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WTableExcelTargetClassUtils {
    private static final String COL_NAME_KEY = "pro";
    private static final String COL_TYPE_KEY = "type";
    private static final String COL_IS_NULL_KEY = "null";
    private static final String COL_DEFAULT_KEY = "default";
    private static final String COL_COMMENT_KEY = "comment";
    private static final String COL_LEN_KEY = "len";

    public static void main(String[] args) throws IOException {
        File file = new File("D:\\workspace\\wangUtils\\a.xls");
        byte[] data = FileUtils.readFileToByteArray(file);
        List<Map<Integer, String>> mapList = WExcelReadUtils.getExcelRowMapList(data, file.getName(), 1);
        Map<String, Integer> keyMap = getKeyMap();
        Map<String, String> valueDescMap = getValueDescMap();
        String sql = getCreateTableClass("UploadStockInfoDetailDTO", keyMap, valueDescMap, mapList, 1);
        System.out.println(sql);
    }

    /**
     * @param className    表名
     * @param keyMap       列位置描述
     * @param valueDescMap 值描述
     * @param rows         数据
     * @param firstIndex   有效数据开始下标
     * @return
     */
    private static String getCreateTableClass(String className, Map<String, Integer> keyMap,
                                              Map<String, String> valueDescMap,
                                              List<Map<Integer, String>> rows, int firstIndex) {
        if (rows == null || rows.isEmpty() || rows.size() <= firstIndex) {
            return null;
        }
        StringBuilder tableSql = new StringBuilder("package com.ouyeel.account.eom.gp.dto;\r\n" +
                "import com.fasterxml.jackson.annotation.JsonProperty;\r\n" +
                "import com.ouyeel.account.common.utils.BaseEntity;\r\n" +
                "import lombok.Data;\r\n" +
                "import org.hibernate.validator.constraints.NotEmpty;\r\n" +
                "import javax.validation.Valid;\r\n" +
                "import javax.validation.constraints.NotNull;\r\n" +
                "import javax.validation.constraints.Size;\r\n" +
                "import java.util.List;\r\n" +
                "@Data\r\n" +
                "public class " + className + " extends BaseEntity {\r\n");
        for (int i = firstIndex; i < rows.size(); i++) {
            Map<Integer, String> row = rows.get(i);
            String colName = MapUtils.getString(row, keyMap.get(COL_NAME_KEY), "");
            if (StringUtils.isBlank(colName)) {
                continue;
            }
            String typeVal = MapUtils.getString(row, keyMap.get(COL_TYPE_KEY), "");
            String isNullVal = MapUtils.getString(row, keyMap.get(COL_IS_NULL_KEY), "");
            String comment = MapUtils.getString(row, keyMap.get(COL_COMMENT_KEY), "");
            String lenVal = MapUtils.getString(row, keyMap.get(COL_LEN_KEY), "");
            String colType = MapUtils.getString(valueDescMap, COL_TYPE_KEY + "-" + typeVal.trim(), "");
            String proName = WStringUtils.toCamelCase(colName);
            String rowComment = "/**\r\n" +
                    "     * " + comment + "\r\n" +
                    "     */\r\n";
            String rowNull = "";
            if ("√".equals(isNullVal)) {
                rowNull = "@NotNull(message = \"" + colName + "不能为空\" )\r\n";
            }
            if ("√".equals(isNullVal) && "list".equals(typeVal)) {
                rowNull = "@Valid\r\n" +
                        "@NotEmpty(message = \"" + colName + "不能为空\" )\r\n";
            }
            String rowJPro = "@JsonProperty(\"" + colName + "\")\r\n";
            String rowSize = "";
            if (!"-".equals(lenVal)) {
                rowSize = "@Size(max = " + lenVal + ", message = \"" + colName + "最大长度不能大" + lenVal + "\")\r\n";
            }
            String rowP = "private " + colType + " " + proName + ";\r\n";
            String itemContent = rowComment + rowNull + rowJPro + rowSize + rowP;
            tableSql.append(itemContent);
        }
        tableSql.append("}\r\n");
        return tableSql.toString();
    }


    private static Map<String, Integer> getKeyMap() {
        Map<String, Integer> result = new HashMap<>();
        result.put(COL_NAME_KEY, 0);
        result.put(COL_TYPE_KEY, 1);
        result.put(COL_LEN_KEY, 2);
        result.put(COL_IS_NULL_KEY, 3);
        result.put(COL_COMMENT_KEY, 4);
        return result;
    }

    private static Map<String, String> getValueDescMap() {
        Map<String, String> result = new HashMap<>();
        result.put(COL_TYPE_KEY + "-int", " Integer");
        result.put(COL_TYPE_KEY + "-string", " String");
        result.put(COL_TYPE_KEY + "-decimal(18,6)", " BigDecimal");
        result.put(COL_TYPE_KEY + "-decimal(18,2)", " BigDecimal");
        result.put(COL_TYPE_KEY + "-date", " Date");
        result.put(COL_TYPE_KEY + "-list", " List");
        return result;
    }
}
