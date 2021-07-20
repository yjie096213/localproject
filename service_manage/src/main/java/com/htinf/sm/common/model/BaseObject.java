package com.htinf.sm.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @ClassName: BaseObject
 * @ProjectName: server_manage
 * @Description: 基础对象类
 * @Author: Administrator
 * @DATE: 2021/7/5 17:01
 **/
public abstract class BaseObject implements Serializable {

    private static final long serialVersionUID = -7518991020435178796L;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @NotNull(message = "分页参数不能为空")
    private Page page;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
