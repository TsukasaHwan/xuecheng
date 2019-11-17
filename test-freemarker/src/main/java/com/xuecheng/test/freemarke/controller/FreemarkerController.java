package com.xuecheng.test.freemarke.controller;

import com.xuecheng.test.freemarke.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RequestMapping("/freemarker")
@Controller
public class FreemarkerController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/test1")
    public String test1(Map<String, Object> map) {
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
        return "test1";
    }

    @RequestMapping("/banner")
    public String index_banner(Map<String, Object> map) {
        ResponseEntity<Map> forEntity = restTemplate.getForEntity("http://localhost:31001/cms/config/getmodel/5a791725dd573c3574ee333f", Map.class);
        Map body = forEntity.getBody();
        map.putAll(body);
        return "index_banner";
    }
}
