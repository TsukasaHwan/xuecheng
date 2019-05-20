package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageRepositoryTest {
    @Autowired
    private CmsPageRepository cmsPageRepository;

    @Test
    public void testFindAll() {
        List<CmsPage> all = cmsPageRepository.findAll();
        System.out.println(all);
    }

    @Test
    public void testPageable() {
        int pageSize = 10;
        int pageIndex = 1;
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<CmsPage> cmsPages = cmsPageRepository.findAll(pageable);
        List<CmsPage> content = cmsPages.getContent();
        for (CmsPage cmsPage : content) {
            System.out.println(cmsPage);
        }
    }

    @Test
    public void testMatch() {
        int page = 1;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        CmsPage cmsPage = new CmsPage();
        cmsPage.setSiteId("5a751fab6abb5044e0d19ea1");
        cmsPage.setPageAliase("轮播");
        //定期匹配器
        ExampleMatcher matching = ExampleMatcher.matching();
        //定义模糊匹配
        matching = matching.withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<CmsPage> example = Example.of(cmsPage, matching);
        Page<CmsPage> cmsPages = cmsPageRepository.findAll(example, pageable);
        List<CmsPage> content = cmsPages.getContent();
        System.out.println(content);
    }
}
