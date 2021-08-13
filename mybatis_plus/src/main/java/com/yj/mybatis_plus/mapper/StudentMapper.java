package com.yj.mybatis_plus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yj.mybatis_plus.model.Student;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: StudentMapper
 * @ProjectName: mybatis_plus
 * @Description:
 * @Author: Administrator
 * @DATE: 2021/8/2 9:42
 **/

@Repository
public interface StudentMapper extends BaseMapper<Student> {

    List<Student> selectAll(Student student);

}
