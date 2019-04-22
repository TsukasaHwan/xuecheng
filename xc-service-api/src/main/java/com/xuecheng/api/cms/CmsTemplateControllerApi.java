package com.xuecheng.api.cms;

import com.xuecheng.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "cms模板接口", description = "cms模板接口，提供模板的查询功能")
public interface CmsTemplateControllerApi {
    @ApiOperation("查询所有模板")
    QueryResponseResult findList();
}
