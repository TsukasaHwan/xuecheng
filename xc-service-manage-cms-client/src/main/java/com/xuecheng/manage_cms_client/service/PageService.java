package com.xuecheng.manage_cms_client.service;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.manage_cms_client.dao.CmsPageRepository;
import com.xuecheng.manage_cms_client.dao.CmsSiteRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

/**
 * @author 4you
 * @date 2020/03/25
 */
@Slf4j
@Service
public class PageService {
    @Autowired
    private CmsPageRepository cmsPageRepository;
    @Autowired
    private CmsSiteRepository cmsSiteRepository;
    @Autowired
    private GridFsTemplate gridFsTemplate;
    @Autowired
    private GridFSBucket gridFSBucket;

    /**
     * 保存页面Html到服务器物理路径
     *
     * @param pageId
     */
    public void savePageToServerPath(String pageId) {
        CmsPage cmsPage = findCmsPageById(pageId);
        if (cmsPage == null) {
            log.error("cmsPage is null,pageId:{}", pageId);
            return;
        }
        String htmlFileId = cmsPage.getHtmlFileId();
        InputStream is = this.getFileById(htmlFileId);
        if (is == null) {
            log.error("getFileById InputStream is null,htmlFileId:{}", htmlFileId);
            return;
        }
        CmsSite cmsSite = this.findCmsSiteById(cmsPage.getSiteId());
        if (cmsSite == null) {
            log.error("cmsSite is null,siteId:{}", cmsPage.getSiteId());
            return;
        }
        String sitePhysicalPath = cmsSite.getSitePhysicalPath();
        String pagePath = sitePhysicalPath + cmsPage.getPagePhysicalPath() + cmsPage.getPageName();
        try (FileOutputStream fos = new FileOutputStream(new File(pagePath))) {
            IOUtils.copy(is, fos);
        } catch (Exception e) {
            log.error("make file error:{}", e.toString(), e);
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                log.error("InputStream close error:{}", e.toString(), e);
            }
        }
    }

    public InputStream getFileById(String htmlFileId) {
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(htmlFileId)));
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
        try {
            return gridFsResource.getInputStream();
        } catch (IOException e) {
            log.error("getFileById error:{}", e.toString(), e);
        }
        return null;
    }

    public CmsPage findCmsPageById(String pageId) {
        Optional<CmsPage> byId = cmsPageRepository.findById(pageId);
        return byId.orElse(null);
    }

    public CmsSite findCmsSiteById(String siteId) {
        Optional<CmsSite> byId = cmsSiteRepository.findById(siteId);
        return byId.orElse(null);
    }
}
