package com.base.simple.test.bean.ftl;
import java.io.Serializable;
/**
 * User
 * @author zhouyw
 * @date 2016.12.05
 */
public class User implements Serializable {

    private int id;
    private String name;
    private int age;
    private Group group;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}