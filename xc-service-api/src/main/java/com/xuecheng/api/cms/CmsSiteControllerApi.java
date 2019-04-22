package com.xuecheng.api.cms;

import com.xuecheng.framework.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "cms站点接口", description = "cms站点接口，提供站点的查询功能")
public interface CmsSiteControllerApi {
    @ApiOperation("查询所有站点")
    QueryResponseResult findList();
}
