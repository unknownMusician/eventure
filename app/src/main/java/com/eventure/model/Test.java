package com.eventure.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Test implements Serializable {
    private ArrayList<Integer> list;
    private String name;

    public ArrayList<Integer> getList() {
        return list;
    }

    public void setList(ArrayList<Integer> list) {
        this.list = list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Test(ArrayList list, String name){
        this.list= list;
        this.name = name;

    }
}
