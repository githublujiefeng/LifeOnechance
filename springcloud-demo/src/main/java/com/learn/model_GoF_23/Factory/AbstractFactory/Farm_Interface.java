package com.learn.model_GoF_23.Factory.AbstractFactory;

//抽象产品：农场类
public interface Farm_Interface {
    public Animal getAnimal();
    public Plant getPlant();

}
//抽象产品：动物类
interface Animal{
    public void  show();
}
//抽象产品：植物类
interface Plant{
    public void show();
}
