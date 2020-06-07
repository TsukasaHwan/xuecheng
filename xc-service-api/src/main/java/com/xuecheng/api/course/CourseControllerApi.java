package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author 4you
 * @date 2020/06/07
 */
@Api(value = "课程管理接口", description = "课程管理接口，提供数据模型管理、查询接口")
public interface CourseControllerApi {
    @ApiOperation("课程计划查询")
    TeachplanNode findTeachPlanList(String courseId);

    @ApiOperation("添加课程计划")
    ResponseResult addTeachPlan(Teachplan teachplan);
}
