package com.ringcentral.bigDecimal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TestBigDecimal {

    public static void main(String[] args) {
        //测试数据主备
        List<Student> list = new ArrayList<Student>();
        list.add(new Student(1, "张三", new BigDecimal(-1)));
        list.add(new Student(1, "张三", new BigDecimal(180.521)));
        list.add(new Student(1, "张三", new BigDecimal(170.6)));
        list.add(new Student(2, "李四", new BigDecimal(160)));

        Map<Integer, BigDecimal> map = list.stream().collect(
                Collectors.groupingBy(Student::getId, CustomCollectors.summingBigDecimal(Student::getHeight)));
        System.out.println("求和的数据" + map.toString());
        System.out.println("=======================================");
        Map<Integer, BigDecimal> map1 = list.stream().collect(
                Collectors.groupingBy(Student::getId, CustomCollectors.averagingBigDecimal(Student::getHeight, 2, 2)));
        System.out.println("求平均值" + map1.toString());
        System.out.println("----------------------------------------");
        Map<Integer, BigDecimal> map2 = list.stream().collect(
                Collectors.groupingBy(Student::getId, CustomCollectors.maxBy(student -> student.getHeight().doubleValue())));
//        System.out.println(new BigDecimal(170.11).add(new BigDecimal(1), MathContext.DECIMAL32));
        System.out.println("求最大的值为：" + map2.toString());
        System.out.println("----------------------------------------");
        Map<Integer, BigDecimal> map3 = list.stream().collect(
                Collectors.groupingBy(Student::getId, CustomCollectors.minBy(Student::getHeight)));
        System.out.println("分组后的最小值为：" + map3.toString());

        Double a = 455.34;
        System.out.println(new BigDecimal(a));
    }


    static class Student {

        private Integer id;

        private String name;

        private BigDecimal height;

        public Student() {
        }

        /**
         * @param id
         * @param name
         * @param height
         */
        public Student(Integer id, String name, BigDecimal height) {
            super();
            this.id = id;
            this.name = name;
            this.height = height;
        }

        /**
         * @return the id
         */
        public Integer getId() {
            return id;
        }

        /**
         * @param id the id to set
         */
        public void setId(Integer id) {
            this.id = id;
        }

        /**
         * @return the name
         */
        public String getName() {
            return name;
        }

        /**
         * @param name the name to set
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return the height
         */
        public BigDecimal getHeight() {
            return height;
        }

        /**
         * @param height the height to set
         */
        public void setHeight(BigDecimal height) {
            this.height = height;
        }

        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "Student [id=" + id + ", name=" + name + ", height=" + height + "]";
        }
    }
}
