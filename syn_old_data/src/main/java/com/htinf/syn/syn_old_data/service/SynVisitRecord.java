package com.htinf.syn.syn_old_data.service;

import com.htinf.syn.syn_old_data.model.HistoryRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: SynVisitRecord
 * @ProjectName: syn_old_data
 * @Description: 同步老会见信息
 * @Author: Administrator
 * @DATE: 2021/7/29 15:00
 **/

@Component
public class SynVisitRecord implements ApplicationRunner {

    private final static Logger logger = LoggerFactory.getLogger(SynVisitRecord.class);

    @Resource(name = "jdbcTemplate1")
    public JdbcTemplate jdbcTemplate1;

    @Resource(name = "jdbcTemplate2")
    public JdbcTemplate jdbcTemplate2;

    @Value("${selectOldVisitRecord}")
    private String selectOldVisitRecord;

    @Value("${insertVisitRecord}")
    private String insertVisitRecord;

    @Override
    public void run(ApplicationArguments args) {

        logger.info("开始查询老系统会见数据...");
        logger.info("查询老系统会见数据sql:{}", selectOldVisitRecord);
        List<HistoryRecord> list1 = this.jdbcTemplate1.query(selectOldVisitRecord, new BeanPropertyRowMapper<>(HistoryRecord.class));
        logger.info("样本数据:{}", list1.get(0).toString());
        logger.info("共查询老系统会见数据{}条", list1.size());
        logger.info("结束查询老系统会见数据...");

        logger.info("开始插入新系统会见数据...");
        logger.info("插入新系统会见数据sql:{}", insertVisitRecord);

        int i = 0;
        for(HistoryRecord h : list1) {

            try {
                Object[] object = new Object[] {
                        h.getPrisonerNo(),
                        h.getPrisonerName(),
                        h.getPrisonerArea(),
                        h.getPrisonerLevel(),
                        h.getVisitType(),
                        h.getWindowNo(),
                        h.getIsApply(),
                        h.getApproveOperator(),
                        h.getApproveTime(),
                        h.getRegister(),
                        h.getRegistTime(),
                        h.getStartTime(),
                        h.getEndTime(),
                        h.getVisitState(),
                        h.getDuration(),
                        h.getVisitorNames(),
                        h.getVisitorIdNos(),
                        h.getListener(),
                        h.getListenTime(),
                        h.getListenEvaluation(),
                        h.getListenContext(),
                        h.getRecordPath(),
                        h.getRecordName(),
                        h.getRecordWebPath(),
                        h.getOriginalId()
                };

                this.jdbcTemplate2.update(insertVisitRecord, object);
                if(i > 0 && i % 500 == 0) {
                    logger.info("处理进度{}/{}", i, list1.size());
                }
            } catch (Exception e){
                logger.info("处理异常：{}", e.toString());
            } finally {
                i ++;
            }
        }
        logger.info("处理进度:{}/{}", i, list1.size());

        logger.info("结束插入新系统会见数据...");
    }

    public String run(Map<String, String> map, JdbcTemplate jdbcTemplate1, JdbcTemplate jdbcTemplate2) {

        int suc = 0;
        int fail = 0;

        try {
            logger.info("开始查询老系统会见数据...");
            logger.info("查询老系统会见数据sql:{}", map.get("selectOldVisitRecord"));
            List<HistoryRecord> list1 = jdbcTemplate1.query(map.get("selectOldVisitRecord"), new BeanPropertyRowMapper<>(HistoryRecord.class));
            logger.info("样本数据:{}", list1.get(0).toString());
            logger.info("共查询老系统会见数据{}条", list1.size());
            logger.info("结束查询老系统会见数据...");

            logger.info("开始插入新系统会见数据...");
            logger.info("查询老系统会见数据sql:{}", map.get("insertVisitRecord"));

            int i = 0;
            for(HistoryRecord h : list1) {

                try {
                    Object[] object = new Object[] {
                            h.getPrisonerNo(),
                            h.getPrisonerName(),
                            h.getPrisonerArea(),
                            h.getPrisonerLevel(),
                            h.getVisitType(),
                            h.getWindowNo(),
                            h.getIsApply(),
                            h.getApproveOperator(),
                            h.getApproveTime(),
                            h.getRegister(),
                            h.getRegistTime(),
                            h.getStartTime(),
                            h.getEndTime(),
                            h.getVisitState(),
                            h.getDuration(),
                            h.getVisitorNames(),
                            h.getVisitorIdNos(),
                            h.getListener(),
                            h.getListenTime(),
                            h.getListenEvaluation(),
                            h.getListenContext(),
                            h.getRecordPath(),
                            h.getRecordName(),
                            h.getRecordWebPath(),
                            h.getOriginalId()
                    };

                    jdbcTemplate2.update(map.get("insertVisitRecord"), object);
                    suc ++;
                    if(i > 0 && i % 500 == 0) {
                        logger.info("处理进度{}/{}", i, list1.size());
                    }
                } catch (Exception e){
                    logger.info("处理异常：{}", e.toString());
                    fail ++;
                } finally {
                    i ++;
                }
            }

            logger.info("处理进度:{}/{}", i, list1.size());

            logger.info("结束插入新系统会见数据...");

        }catch (Exception e1) {
            e1.printStackTrace();
            return "同步失败！";
        }

        return "同步完成，成功" + suc + "条，失败" + fail + "条！";
    }

}
