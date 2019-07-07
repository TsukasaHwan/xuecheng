package com.xuecheng.manage_cms;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GridFsTest {
    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFSBucket gridFSBucket;

    @Test
    public void testGridFs() {
        File file = new File("D:\\index_banner.ftl");
        try {
            InputStream is = new FileInputStream(file);
            ObjectId objectId = gridFsTemplate.store(is, "index_banner.ftl");
            System.out.println(objectId);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetGridFsFile() throws IOException {
        //根据ID查询文件
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is("5d04bd782f5e5d3028442402")));
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
        String content = IOUtils.toString(gridFsResource.getInputStream(), "UTF-8");
        System.out.println(content);
    }
}
