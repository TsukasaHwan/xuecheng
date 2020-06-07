package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.Teachplan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author 4you
 * @date 2020/06/07
 */
public interface TeachPlanRepository extends JpaRepository<Teachplan, String> {
    List<Teachplan> findByCourseidAndParentid(String courseid, String parentid);
}
