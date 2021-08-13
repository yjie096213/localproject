package com.yj.mybatis_plus.model;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: Student
 * @ProjectName: mybatis_plus
 * @Description:
 * @Author: Administrator
 * @DATE: 2021/8/2 9:29
 **/

@Data
public class Student extends Model<Student>{

    private long id;

    private int age;

    private String name;

    private String no;

    @Override
    protected Serializable pkVal() {
        return id;
    }
}
