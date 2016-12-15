package com.base.simple.freemarker.bean;
import java.io.Serializable;
import java.util.List;

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
    private List<String> list;

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

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