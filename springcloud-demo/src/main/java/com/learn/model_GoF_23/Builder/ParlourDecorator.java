package com.learn.model_GoF_23.Builder;

import com.learn.model_GoF_23.Factory.AbstractFactory.ReadXML;

import javax.swing.*;
import java.awt.*;

//用建造者（Builder）模式描述客厅装修。
public class ParlourDecorator {
    public static void main(String[] args) {
        try {
            Decorator d;
            d = (Decorator) ReadXML.getObject();
            ProjectManager m = new ProjectManager(d);
            Parlour p = m.decorate();
            p.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

//产品：客厅
class Parlour{
    private String wall;    //墙
    private String TV;      //电视
    private String sofa;    //沙发

    public void setWall(String wall) {
        this.wall = wall;
    }

    public void setTV(String TV) {
        this.TV = TV;
    }

    public void setSofa(String sofa) {
        this.sofa = sofa;
    }

    public void show(){
        JFrame jf = new JFrame("建造者模式测试");
        Container contentPane = jf.getContentPane();
        JPanel p = new JPanel();
        JScrollPane sp = new JScrollPane(p);
        String parlour = wall + TV + sofa;
        JLabel l = new JLabel(new ImageIcon("src/" + parlour + ".jpg"));
        p.setLayout(new GridLayout(1, 1));
        p.setBorder(BorderFactory.createTitledBorder("客厅"));
        p.add(l);
        contentPane.add(sp, BorderLayout.CENTER);
        jf.pack();
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

//抽象建造者：装修工人
abstract class Decorator{
    //创建产品对象
    protected Parlour product = new Parlour();

    public abstract void buildWall();

    public abstract void buildTV();

    public abstract void buildSofa();

    //返回产品对象
    public Parlour getResult(){
        return product;
    }
}

//具体建造者：具体装修工人1
class  ConcreteDecorator1 extends Decorator{

    @Override
    public void buildWall() {
        product.setWall("w1");
    }

    @Override
    public void buildTV() {
        product.setTV("TV1");
    }

    @Override
    public void buildSofa() {
        product.setSofa("sf1");
    }
}

//具体建造者：具体装修工人2
class ConcreteDecorator2 extends Decorator{

    @Override
    public void buildWall() {
        product.setWall("w2");
    }

    @Override
    public void buildTV() {
        product.setTV("TV2");
    }

    @Override
    public void buildSofa() {
        product.setSofa("sf2");
    }
}

//指挥者：项目经理
class ProjectManager{
    private Decorator builder;

    public ProjectManager(Decorator builder){
        this.builder=builder;
    }

    //产品构建与组装方法
    public Parlour decorate(){
        builder.buildSofa();
        builder.buildTV();
        builder.buildWall();
        return builder.getResult();
    }
}