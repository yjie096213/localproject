package com.htinf.syn.syn_old_data.controller;

import com.htinf.syn.syn_old_data.service.SynVisitRecord;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: SynOldVisitRecordController
 * @ProjectName: syn_old_data
 * @Description: 同步老会见记录controller
 * @Author: Administrator
 * @DATE: 2021/7/29 16:19
 **/

@RestController
@RequestMapping("syn")
public class SynOldVisitRecordController {

    @Resource(name = "jdbcTemplate1")
    public JdbcTemplate jdbcTemplate1;

    @Resource(name = "jdbcTemplate2")
    public JdbcTemplate jdbcTemplate2;

    @RequestMapping(value = "visitRecord", method = RequestMethod.POST)
    public String synVisitRecord(@RequestParam String selectOldVisitRecord, String insertVisitRecord) {
        Map<String, String> map = new HashMap<>();
        map.put("selectOldVisitRecord",selectOldVisitRecord);
        map.put("insertVisitRecord",insertVisitRecord);
        SynVisitRecord synVisitRecord = new SynVisitRecord();
        String result = synVisitRecord.run(map, jdbcTemplate1, jdbcTemplate2);

        return result;
    }

}
