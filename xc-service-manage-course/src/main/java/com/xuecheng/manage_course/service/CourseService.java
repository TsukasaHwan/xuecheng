package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.dao.CourseBaseRepository;
import com.xuecheng.manage_course.dao.TeachPlanMapper;
import com.xuecheng.manage_course.dao.TeachPlanRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author 4you
 * @date 2020/06/07
 */
@Service
public class CourseService {
    @Autowired
    private TeachPlanMapper teachPlanMapper;
    @Autowired
    private TeachPlanRepository teachPlanRepository;
    @Autowired
    private CourseBaseRepository courseBaseRepository;

    public TeachplanNode findTeachPlanList(String courseId) {
        return teachPlanMapper.findTeachPlanList(courseId);
    }

    public ResponseResult addTeachPlan(Teachplan teachplan) {
        if (teachplan == null || StringUtils.isBlank(teachplan.getCourseid()) || StringUtils.isBlank(teachplan.getPname())) {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        String courseid = teachplan.getCourseid();
        String parentid = teachplan.getParentid();
        if (StringUtils.isBlank(parentid)) {
            parentid = getTeachPlanRoot(courseid);
        }
        Optional<Teachplan> optional = teachPlanRepository.findById(parentid);
        Teachplan parentNode = optional.get();
        String grade = parentNode.getGrade();
        Teachplan teachplanNew = new Teachplan();
        BeanUtils.copyProperties(teachplan, teachplanNew);
        teachplanNew.setCourseid(courseid);
        teachplanNew.setParentid(parentid);
        if ("1".equals(grade)) {
            teachplanNew.setGrade("2");
        } else {
            teachplanNew.setGrade("3");
        }
        teachPlanRepository.save(teachplan);
        return ResponseResult.SUCCESS();
    }

    @Transactional(rollbackFor = Exception.class)
    public String getTeachPlanRoot(String courseId) {
        List<Teachplan> teachplans = teachPlanRepository.findByCourseidAndParentid(courseId, "0");
        if (teachplans == null || teachplans.isEmpty()) {
            //添加根节点
            Optional<CourseBase> optionalCourseBase = courseBaseRepository.findById(courseId);
            if (!optionalCourseBase.isPresent()) {
                return null;
            }
            CourseBase courseBase = optionalCourseBase.get();
            Teachplan teachplan = new Teachplan();
            teachplan.setParentid("0");
            teachplan.setGrade("1");
            teachplan.setPname(courseBase.getName());
            teachplan.setCourseid(courseBase.getId());
            teachplan.setStatus("0");
            teachPlanRepository.save(teachplan);
            return teachplan.getId();
        }
        return teachplans.get(0).getId();
    }
}
