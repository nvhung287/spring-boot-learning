package me.loda.springredis;
/*******************************************************
 * For Vietnamese readers:
 *    Các bạn thân mến, mình rất vui nếu project này giúp 
 * ích được cho các bạn trong việc học tập và công việc. Nếu 
 * bạn sử dụng lại toàn bộ hoặc một phần source code xin để 
 * lại dường dẫn tới github hoặc tên tác giá.
 *    Xin cảm ơn!
 *******************************************************/

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright 2019 {@author Loda} (https://loda.me).
 * This project is licensed under the MIT license.
 *
 * @since 5/5/2019
 * Github: https://github.com/loda-kun
 */
@Component
public class RedisExample implements CommandLineRunner {
    @Autowired
    private RedisTemplate template;

    @Override
    public void run(String... args) throws Exception {
        valueExample();
    }

    @Autowired
    private YAMLConfig myConfig;

    public void valueExample(){
        // Set giá trị của key "loda" là "hello redis"
//        template.opsForValue().set("loda","hello world");

        // In ra màn hình Giá trị của key "loda" trong Redis
//        System.out.println("Value of key loda: "+template.opsForValue().get("loda"));

        Student student = Student.builder().name("hungnv").old(27).build();
        template.opsForValue().set(student.getName(), student);
        System.out.println(myConfig);
    }

    public void listExample(){
        // Tạo ra một list gồm 2 phần tử
        List<String> list = new ArrayList<>();
        list.add("Hello");
        list.add("redis");

        // Set gia trị có key loda_list
        template.opsForList().rightPushAll("loda_list", list);
//        template.opsForList().rightPushAll("loda_list", "hello", "world");

        System.out.println("Size of key loda: "+template.opsForList().size("loda_list"));
    }
}
