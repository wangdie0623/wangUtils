package cn.wang.custom.utils.clazz;

import org.apache.commons.collections4.MapUtils;

import java.lang.reflect.Field;
import java.util.*;

public class ClassToSqlUtil {
    public static final Map<String, String> DEFAULT_JAVA_TO_SQL_MAP = getDefaultJavaToSqlMap();

    private static Map<String, String> getDefaultJavaToSqlMap() {
        Map<String, String> map = new HashMap<>();
        map.put("Integer", "int");
        map.put("String", "varchar(255)");
        map.put("Map", "json");
        map.put("List", "json");
        map.put("BigDecimal", "DECIMAL(11,2)");
        map.put("Date", "datetime");
        map.put("LocalDateTime", "datetime");
        return map;
    }

    public static void main(String[] args) throws Exception {
        /*Class<EntityModel> clazz = EntityModel.class;
        TableInfo table = clazz.getAnnotation(TableInfo.class);
        Field[] fatherFields = clazz.getSuperclass().getDeclaredFields();
        Field[] fields = clazz.getDeclaredFields();
        List<Field> list = Arrays.asList(fields);
        List<Field> fList = Arrays.asList(fatherFields);
        List<Field> allList=new LinkedList<>();
        allList.addAll(fList);
        allList.addAll(list);
        StringBuilder builder = new StringBuilder();
        builder.append("create table `" + table.value() + "`(\r\n");
        for (Field field :allList ) {
            ColumnInfo col = field.getAnnotation(ColumnInfo.class);
            if (!Objects.isNull(col)) {
                String colName = WStringUtils.camelCaseT_(field.getName());
                if (!col.name().isEmpty()) {
                    colName = col.name();
                }
                String colType = getSqlType(DEFAULT_JAVA_TO_SQL_MAP, field);
                if (!col.type().isEmpty()) {
                    colType = colType;
                }
                String sqlExt = getSqlExt(colType, col);
                builder.append("`"+colName+"` "+colType+" "+sqlExt);
                builder.append("\r\n");
            }
        }
        builder.append(" PRIMARY KEY (`id`)\r\n");
        builder.append(")ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin ROW_FORMAT=DYNAMIC COMMENT='" + table.desc() + "';");
        System.out.println(builder);*/
    }

    /**
     * 得到属性对应的sql类型
     *
     * @param javaToSqlTypeMap java转sql类型map
     * @param field            属性对象
     * @return sql类型
     */
    public static String getSqlType(Map<String, String> javaToSqlTypeMap, Field field) {
        String simpleName = field.getType().getSimpleName();
        if (field.getName().equals("id") && "String".equals(simpleName)) {
            return "char(32)";
        }
        return MapUtils.getString(javaToSqlTypeMap, simpleName, "");
    }

    /**
     * 得到扩展描述
     *
     * @param sqlType sql类型
     * @param col     注解
     * @return 扩展描述
     */
    public static String getSqlExt(String sqlType, ColumnInfo col) {
        String result = "";
        if (col.notNull()) {
            result += " not null ";
        }
        String defaultS = col.defaultVal().isEmpty() ? "" : " default " + col.defaultVal();
        if (sqlType.startsWith("varchar")) {
            defaultS = " default '' ";
        }
        if (sqlType.startsWith("json")) {
            defaultS = " default null ";
        }
        result += defaultS;
        result += " COMMENT '" + col.value() + "',";
        return result;
    }


}
