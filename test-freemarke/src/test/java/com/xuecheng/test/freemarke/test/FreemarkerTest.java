package com.xuecheng.test.freemarke.test;

import com.xuecheng.test.freemarke.model.Student;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FreemarkerTest {

    @Test
    public void testGenerateHtml() {
        Configuration configuration = new Configuration(Configuration.getVersion());
        try {
            configuration.setDirectoryForTemplateLoading(new File(FreemarkerTest.class.getResource("/").getPath() + "/templates/"));
            Template template = configuration.getTemplate("test1.ftl");
            Map dataModel = getDataModel();
            String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, dataModel);
            System.out.println(content);
            InputStream inputStream = IOUtils.toInputStream(content, "utf-8");
            FileOutputStream fos = new FileOutputStream(new File("D:/test1.html"));
            IOUtils.copy(inputStream, fos);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    //基于模板文件内容生成字符串
    @Test
    public void testGenerateHtmlByString() {
        Configuration configuration = new Configuration(Configuration.getVersion());
        String templateString = "" +
                "<html>\n" +
                "   <head></head>\n" +
                "   <body>\n" +
                "   名称：${name}\n" +
                "   </body>\n" +
                "<html>";
        StringTemplateLoader stl = new StringTemplateLoader();
        stl.putTemplate("template", templateString);
        configuration.setTemplateLoader(stl);
        try {
            Template template = configuration.getTemplate("template", "utf-8");
            Map<String, Object> map = new HashMap<>();
            map.put("name", "张三");
            String s = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
            System.out.println(s);
            InputStream is = IOUtils.toInputStream(s, "utf-8");
            FileOutputStream fos = new FileOutputStream(new File("D:/template.html"));
            IOUtils.copy(is, fos);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    public Map getDataModel() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "张三");
        Student student1 = new Student();
        student1.setName("李四");
        student1.setAge(20);
        student1.setBirthday(new Date());
        student1.setMoney(5850.00f);
        Student student2 = new Student();
        student2.setName("王五");
        student2.setAge(22);
//        student1.setBirthday(new Date());
        student2.setMoney(3000.00f);
        student2.setBestFriend(student1);
        List<Student> friends = new ArrayList<>();
        friends.add(student1);
        friends.add(student2);
        student1.setFriends(friends);
        student2.setFriends(friends);
        List<Student> students = new ArrayList<>();
        students.add(student1);
//        students.add(student2);
        Map<String, Object> studentMap = new HashMap<>();
        studentMap.put("student1", student1);
        studentMap.put("student2", student2);
        map.put("students", students);
        map.put("studentMap", studentMap);
        map.put("point", 123456);
        return map;
    }
}
