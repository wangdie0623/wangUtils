package cn.wang.custom.utils;


import cn.wang.custom.utils.builder.WMapBuilder;
import cn.wang.custom.utils.excel.WExcelReadUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WTableExcelTargetSqlUtils {
    private  static  final String COL_NAME_KEY="col";
    private  static  final String COL_TYPE_KEY="type";
    private  static  final String COL_IS_NULL_KEY="null";
    private  static  final String COL_DEFAULT_KEY="default";
    private  static  final String COL_COMMENT_KEY="comment";
    private  static  final String COL_COMMENT_KEY2="comment2";
    private  static  final String COL_LEN_KEY="len";

    /**
     * 默认使用值集转换
     */
    private static final Map<String,String> DEFAULT_VALUE_DESC_MAP= WMapBuilder.builder()
            .put(COL_TYPE_KEY+"-ID"," NUMBER(19)")
            .put(COL_TYPE_KEY+"-单据号"," VARCHAR2(32)")
            .put(COL_TYPE_KEY+"-单据号-长"," VARCHAR2(64)")
            .put(COL_TYPE_KEY+"-主数据代码-短"," VARCHAR2(16)")
            .put(COL_TYPE_KEY+"-主数据代码-中"," VARCHAR2(32)")
            .put(COL_TYPE_KEY+"-主数据代码-长"," VARCHAR2(64)")
            .put(COL_TYPE_KEY+"-值集代码"," NUMBER(4)")
            .put(COL_TYPE_KEY+"-标识号-短"," VARCHAR2(8)")
            .put(COL_TYPE_KEY+"-标识号-中"," VARCHAR2(16)")
            .put(COL_TYPE_KEY+"-标识号-长"," VARCHAR2(64)")
            .put(COL_TYPE_KEY+"-标识号-超长"," VARCHAR2(128)")
            .put(COL_TYPE_KEY+"-文本描述-短"," VARCHAR2(32)")
            .put(COL_TYPE_KEY+"-文本描述-中"," VARCHAR2(128)")
            .put(COL_TYPE_KEY+"-文本描述-长"," VARCHAR2(512)")
            .put(COL_TYPE_KEY+"-文本描述-超长"," VARCHAR2(2048)")
            .put(COL_TYPE_KEY+"-大文本描述"," CLOB")
            .put(COL_TYPE_KEY+"-数量"," NUMBER(16,6)")
            .put(COL_TYPE_KEY+"-尺寸"," NUMBER(8,2)")
            .put(COL_TYPE_KEY+"-件数"," NUMBER(9)")
            .put(COL_TYPE_KEY+"-单价"," NUMBER(16,4)")
            .put(COL_TYPE_KEY+"-金额"," NUMBER(18,2)")
            .put(COL_TYPE_KEY+"-整数"," NUMBER(9)")
            .put(COL_TYPE_KEY+"-大整数"," NUMBER(18)")
            .put(COL_TYPE_KEY+"-税率"," NUMBER(9,2)")
            .put(COL_TYPE_KEY+"-汇率"," NUMBER(9,4)")
            .put(COL_TYPE_KEY+"-小数"," NUMBER(6,4)")
            .put(COL_TYPE_KEY+"-小数-中"," NUMBER(9,4)")
            .put(COL_TYPE_KEY+"-系数"," NUMBER(9,6)")
            .put(COL_TYPE_KEY+"-谓词"," NUMBER(1)")
            .put(COL_TYPE_KEY+"-状态"," NUMBER(2)")
            .put(COL_TYPE_KEY+"-日期"," DATE")
            .put(COL_TYPE_KEY+"-时间"," DATE")
            .put(COL_TYPE_KEY+"-时间戳"," TIMESTAMP(6)")
            .put(COL_TYPE_KEY+"-比例"," NUMBER(9,6)")
            .put(COL_TYPE_KEY+"-费率"," NUMBER(9,6)")
            .put(COL_TYPE_KEY+"-JSON字段"," JSON")
            .put(COL_IS_NULL_KEY+"-√"," not null")
            .create();
    /**
     *默认使用类型转换Map
     */
    private static final Map<String,Integer> DEFAULT_KEY_MAP=WMapBuilder.builder()
            .put(COL_NAME_KEY,0)
            .put(COL_COMMENT_KEY,1)
            .put(COL_TYPE_KEY,2)
            .put(COL_IS_NULL_KEY,3)
            .put(COL_DEFAULT_KEY,4)
            .put(COL_COMMENT_KEY2,5)
            .create();

    public static void main(String[] args) throws IOException {
        File file = new File("D:\\workspace\\wangUtils\\a.xls");
        byte[] data = FileUtils.readFileToByteArray(file);
        List<Map<Integer, String>> mapList = WExcelReadUtils.getExcelRowMapList(data,file.getName(), 0);
        Map<String, Integer> keyMap = DEFAULT_KEY_MAP;
        Map<String, String> valueDescMap = DEFAULT_VALUE_DESC_MAP;
        String sql = getCreateTableSql("cw.T_ASC_ACCRUAL","财务计提表",keyMap, valueDescMap, mapList, 1);
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
            String defaultVal = MapUtils.getString(row,keyMap.get(COL_DEFAULT_KEY),"");
            String comment = MapUtils.getString(row,keyMap.get(COL_COMMENT_KEY),"");
            String comment2 = MapUtils.getString(row,keyMap.get(COL_COMMENT_KEY2),"");
            comment+=" "+comment2;
            String lenVal = MapUtils.getString(row,keyMap.get(COL_LEN_KEY),"");
            String colType= MapUtils.getString(valueDescMap,COL_TYPE_KEY+"-"+typeVal.trim(),"");
            String colLen= MapUtils.getString(valueDescMap,COL_LEN_KEY+"-"+lenVal.trim(),"");
            String colIsNull= MapUtils.getString(valueDescMap,COL_IS_NULL_KEY+"-"+isNullVal.trim(),"");
            if ("varchar2".equalsIgnoreCase(colType)&& StringUtils.isBlank(colLen)){
                colLen="(255) ";
            }
            String primaryKey="id".equalsIgnoreCase(colName)?" primary key":"";
            String colDefault="";
            if (StringUtils.isNotBlank(defaultVal)){
                colDefault+=" default ";
                colDefault+=colType.contains("VARCHAR2")?"'"+defaultVal+"'":defaultVal;
            }
            String tableRow=colName.trim()+colType+colLen+colDefault+colIsNull+primaryKey+",\r\n";
            String commentRow="comment on column "+tableName+"."+colName+" is '"+comment.trim()+"';\r\n";
            tableSql.append(tableRow);
            commentSql.append(commentRow);
        }
        tableSql.deleteCharAt(tableSql.lastIndexOf(","));
        tableSql.append(");\r\n");
        tableSql.append(commentSql);
        return tableSql.toString();
    }




}
