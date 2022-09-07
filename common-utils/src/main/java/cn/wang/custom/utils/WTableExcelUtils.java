package cn.wang.custom.utils;


import cn.wang.custom.utils.excel.WExcelReadUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WTableExcelUtils {
    private  static  final String COL_NAME_KEY="col";
    private  static  final String COL_TYPE_KEY="type";
    private  static  final String COL_IS_NULL_KEY="null";
    private  static  final String COL_DEFAULT_KEY="default";
    private  static  final String COL_COMMENT_KEY="comment";
    private  static  final String COL_LEN_KEY="len";
    public static void main(String[] args) throws IOException {
        File file = new File("D:\\workspace\\wangUtils\\a.xls");
        byte[] data = FileUtils.readFileToByteArray(file);
        List<Map<Integer, String>> mapList = WExcelReadUtils.getExcelRowMapList(data,file.getName(), "成品表");
        Map<String, Integer> keyMap = getKeyMap();
        Map<String, String> valueDescMap = getValueDescMap();
        String sql = getCreateTableSql("T_IMC_PROCESS_ORDER_M_C","成品表",keyMap, valueDescMap, mapList, 1);
        System.out.println(sql);
    }

    /**
     *
     * @param tableName 表名
     * @param keyMap 列位置描述
     * @param valueDescMap 值描述
     * @param rows 数据
     * @param firstIndex 有效数据开始下标
     * @return
     */
    private static String getCreateTableSql(String tableName,String tableDesc,Map<String,Integer> keyMap,
                                     Map<String,String> valueDescMap,
                                     List<Map<Integer, String>> rows,int firstIndex){
        if (rows==null||rows.isEmpty()||rows.size()<=firstIndex){
            return null;
        }
        StringBuilder tableSql=new StringBuilder("create table "+tableName+"(\r\n");
        StringBuilder commentSql=new StringBuilder("comment on table "+tableName+" is '"+tableDesc+"';\r\n");
        for (int i = firstIndex; i < rows.size(); i++) {
            Map<Integer, String> row = rows.get(i);
            String colName = MapUtils.getString(row,keyMap.get(COL_NAME_KEY),"");
            if (StringUtils.isBlank(colName)){
                continue;
            }
            String typeVal = MapUtils.getString(row,keyMap.get(COL_TYPE_KEY),"");
            String isNullVal = MapUtils.getString(row,keyMap.get(COL_IS_NULL_KEY),"");
            String comment = MapUtils.getString(row,keyMap.get(COL_COMMENT_KEY),"");
            String lenVal = MapUtils.getString(row,keyMap.get(COL_LEN_KEY),"");
            String colType= MapUtils.getString(valueDescMap,COL_TYPE_KEY+"-"+typeVal.trim(),"");
            String colLen= MapUtils.getString(valueDescMap,COL_LEN_KEY+"-"+lenVal.trim(),"");
            String colIsNull= MapUtils.getString(valueDescMap,COL_IS_NULL_KEY+"-"+isNullVal.trim(),"");
            if ("varchar2".equalsIgnoreCase(colType)&& StringUtils.isBlank(colLen)){
                colLen="(255) ";
            }
            String primaryKey="id".equalsIgnoreCase(colName)?" primary key":"";
            String tableRow=colName.trim()+colType+colLen+colIsNull+primaryKey+",\r\n";
            String commentRow="comment on column "+tableName+"."+colName+" is '"+comment+"';\r\n";
            tableSql.append(tableRow);
            commentSql.append(commentRow);
        }
        tableSql.deleteCharAt(tableSql.lastIndexOf(","));
        tableSql.append(");\r\n");
        tableSql.append(commentSql);
        return tableSql.toString();
    }


    private static Map<String,Integer> getKeyMap(){
        Map<String,Integer> result= new HashMap<>();
        result.put(COL_NAME_KEY,0);
        result.put(COL_COMMENT_KEY,1);
        result.put(COL_TYPE_KEY,2);
        result.put(COL_IS_NULL_KEY,3);
        result.put(COL_LEN_KEY,4);
        return result;
    }

    private static Map<String,String> getValueDescMap(){
        Map<String,String> result= new HashMap<>();
        result.put(COL_TYPE_KEY+"-字符型"," varchar2");
        result.put(COL_TYPE_KEY+"-长整型"," number");
        result.put(COL_TYPE_KEY+"-数值型"," number");
        result.put(COL_TYPE_KEY+"-浮点型"," number");
        result.put(COL_TYPE_KEY+"-日期型"," date ");
        result.put(COL_IS_NULL_KEY+"-"," not null ");
        for (int i = 1; i <= 1000; i++) {
            result.put(COL_LEN_KEY+"-"+i,"("+i+") ");
        }
        result.put(COL_LEN_KEY+"-长度18精度6","(18,6) ");
        result.put(COL_LEN_KEY+"-长度18精度8","(18,8) ");
        result.put(COL_LEN_KEY+"-长度10精度6","(10,6) ");
        result.put(COL_LEN_KEY+"-长度10精度6","(10,6) ");
        return result;
    }
}
