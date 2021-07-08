package com.htinf.sm.common.util;

import com.htinf.sm.common.model.Page;
import com.htinf.sm.common.model.PageInfoListResult;

import java.util.List;

/**
 * @ClassName: CommonUtil
 * @ProjectName: server_manage
 * @Description: 工具类
 * @Author: Administrator
 * @DATE: 2021/7/5 16:13
 **/
public class CommonUtil {

    /**
     * @ClassName:   CommonUtil
     * @ProjectName: com.htinf.sm.util.CommonUtil
     * @Description: TODO 封装分页数据
     * @Author:      Administrator
     * @DATE:        2021/7/5 16:35
     **/
    public static<T> PageInfoListResult<T> pageListResult(Page page, List<T> dataList){

        PageInfoListResult<T> pageInfoListResult = new PageInfoListResult();
        pageInfoListResult.setDataList(dataList);
        pageInfoListResult.setPage(page);
        return pageInfoListResult;
    }

}
