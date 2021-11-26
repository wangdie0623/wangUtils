package cn.wang.custom.web.api.dao;

import org.apache.poi.ss.formula.functions.T;

import java.util.List;

public interface ICommonDao {
    void save(Object o);
    void update(Object o);
    void delete(Object o);
    List<T> selectAll(Class<T> tClass);
}
