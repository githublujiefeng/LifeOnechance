package com.learn.restful.bean;

import com.sun.xml.internal.ws.developer.Serialization;
import lombok.Data;

import java.io.Serializable;

@Data
@Serialization
public class User {
    private Long id;
    private String name;
    private Integer age;
}
