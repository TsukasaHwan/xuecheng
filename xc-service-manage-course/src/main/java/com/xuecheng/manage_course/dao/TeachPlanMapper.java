package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author 4you
 * @date 2020/06/07
 */
@Mapper
@Repository
public interface TeachPlanMapper {
    TeachplanNode findTeachPlanList(String courseId);
}
