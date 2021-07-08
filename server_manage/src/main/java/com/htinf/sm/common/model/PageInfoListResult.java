package com.htinf.sm.common.model;

import java.util.List;

/**
 * @ClassName: PageInfoListResult
 * @ProjectName: server_manage
 * @Description:
 * @Author: Administrator
 * @DATE: 2021/7/5 16:06
 **/
public class PageInfoListResult<T> {

    private List<T> dataList;
    private Page page;

    public PageInfoListResult() {
    }

    public PageInfoListResult(Page page, List<T> dataList) {
        this.page = page;
        this.dataList = dataList;
    }

    public List<T> getDataList() {
        return this.dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public Page getPage() {
        return this.page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

}
