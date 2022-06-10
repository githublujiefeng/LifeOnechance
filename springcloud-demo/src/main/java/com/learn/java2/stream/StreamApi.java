package com.learn.java2.stream;

import com.learn.java2.stream.PoJo.Apple;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class StreamApi {
    public static void main(String[] args) {
        List<Apple> apples = Arrays.asList(
                new Apple("yello",150),
                new Apple("green",50),
                new Apple("red",100),
                new Apple("yello",100),
                new Apple("green",150),
                new Apple("red",50),
                new Apple("yello",110),
                new Apple("yello",90),
                new Apple("yello",700),
                new Apple("green",50)
                );
        //过滤器filter()
        apples.stream().filter(a -> a.getColor().equals("green")).forEach(System.out::println);
        apples.stream().filter(a->a.getWeight()>100).forEach(System.out::println);
        apples.stream().filter(a->a.getWeight()>100).filter(a->a.getColor().equals("green")).forEach(System.out::println);
        //去重distinct()
        apples.stream().distinct().forEach(System.out::println);
        //排序sorted()
        apples.stream().sorted(Comparator.comparing(Apple::getColor)).forEach(System.out::println);
        apples.stream().sorted(Comparator.comparing(Apple::getWeight)).forEach(System.out::println);
        apples.stream().sorted(Comparator.comparing(Apple::getColor)).sorted(Comparator.comparing(Apple::getWeight)).forEach(System.out::println);
        apples.stream().sorted(Comparator.comparing(Apple::getWeight).thenComparing(Apple::getColor).reversed()).forEach(System.out::println);
        //截断流limit（）
        apples.stream().sorted(Comparator.comparing(Apple::getWeight)).limit(5).forEach(System.out::println);
        //跳过元素skip（）
        apples.stream().sorted(Comparator.comparing(Apple::getWeight)).limit(5).skip(3).forEach(System.out::println);
        //函数传递map()
        apples.stream().map(Apple::getColor).distinct().forEach(System.out::println);
        apples.stream().map(Apple::getColor).distinct().map(String::toUpperCase).forEach(System.out::println);

        /**
         * stream流的终止操作
         */
        //判断是否存在anyMatch()
        Boolean p = apples.stream().anyMatch(a->a.getColor().equals("green"));

        //判断所有选中元素是否相同allMatch()
        Boolean o = apples.stream().allMatch(a->a.getColor().equals("green"));

        //当前元素是否不在集合中 noneMatch()
        Boolean g = apples.stream().noneMatch(a->a.getColor().equals("green"));

        //找到第一个元素与找到一个元素findFirst()与findAny()
        Optional<Apple> a = apples.stream().findFirst();
        Optional<Apple> b =apples.stream().findAny();

        //统计流中元素的个数count()
        long count = apples.stream().count();

        //最大值与最小值max()与min()
        Optional<Float> optionalFloat = apples.stream().map(Apple::getWeight).max(Float::compareTo);

        //整合reduce()
        Optional<Float> floatOptional = apples.stream().map(Apple::getWeight).reduce(Float::sum);
    }

}
