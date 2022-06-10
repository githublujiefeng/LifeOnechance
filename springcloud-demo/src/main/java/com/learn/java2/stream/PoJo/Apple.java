package com.learn.java2.stream.PoJo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
public class Apple {
    private String color;
    private float weight;

    public String toString(){
        return "Apple ["+"color='"+color +'\''+ ", weight="
                +weight+']';
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
//        if(o instanceof Apple){
//            return false;
//        }
        if(o==null||getClass()!=o.getClass()) return false;
        Apple apple =(Apple) o;
        return Float.compare(apple.weight,weight)==0&&
                Objects.equals(color,apple.color);
    }

    public  int hashCode(){
        return Objects.hash(color,weight);
    }
}
