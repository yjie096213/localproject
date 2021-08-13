package com.yj.mybatis_plus;

import com.dangdang.ddframe.rdb.sharding.id.generator.self.CommonSelfIdGenerator;
import com.yj.mybatis_plus.mapper.StudentMapper;
import com.yj.mybatis_plus.model.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.IdGenerator;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: Test1
 * @ProjectName: mybatis_plus
 * @Description:
 * @Author: Administrator
 * @DATE: 2021/8/2 9:45
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test1 {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private CommonSelfIdGenerator idGenerator;

    @Test
    public void test() {

        Student student = new Student();
//        student.setId(13);
//        student.setName("的热");
        List<Student> list1 = studentMapper.selectAll(student);


        for(Student s : list1) {
            System.out.println(s.toString());
        }


//        Student student = new Student();
//        student.setId(Long.parseLong(idGenerator.generateId().toString()));
//        student.setName("伊一");
//        student.setAge(11);
//        student.setNo("3314");
//        student.insert();
//
//        student.setId(Long.parseLong(idGenerator.generateId().toString()));
//        student.setName("刘二");
//        student.setAge(12);
//        student.setNo("3313");
//        student.insert();
//
//        student.setId(Long.parseLong(idGenerator.generateId().toString()));
//        student.setName("张三");
//        student.setAge(13);
//        student.setNo("3312");
//        student.insert();
//
//        student.setId(Long.parseLong(idGenerator.generateId().toString()));
//        student.setName("李四");
//        student.setAge(14);
//        student.setNo("3313");
//        student.insert();
//
//        student.setId(Long.parseLong(idGenerator.generateId().toString()));
//        student.setName("王五");
//        student.setAge(15);
//        student.setNo("3312");
//        student.insert();
    }
}
