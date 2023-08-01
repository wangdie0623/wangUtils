package cn.wang.custom.user.module.beans;

import java.util.Collections;
import java.util.List;

/**
 * @author 王叠  2019-07-08 10:51
 */
public class VuePageResult<T> {
    private Integer pageNum;
    private Integer pageSize;
    private List<T> list;
    private Long total;
    private Long pages;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getPages() {
        return pages;
    }

    public void setPages(Long pages) {
        this.pages = pages;
    }

    public static VuePageResult emptyPage(Integer pageNum,Integer pageSize){
        VuePageResult page = new VuePageResult();
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        page.setPages(0L);
        page.setList(Collections.emptyList());
        page.setTotal(0L);
        return  page;
    }
}
