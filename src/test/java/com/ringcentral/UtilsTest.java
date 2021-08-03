package com.ringcentral;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 工具类单元测试
 *
 * @author jianhua.luo
 * @date 2020/8/19
 */
public class UtilsTest {

    @Test
    public void sortByName() {
        List<Extension> extensionList = new ArrayList<Extension>() {{
            add(new Extension("luo", "kingwar", "java", "ad"));
            add(new Extension("chen", "yuandong", "java", "ad"));
            add(new Extension("chen", "kunlong", "php", "ad"));
            add(new Extension("huang", "ping", "andriod", "ad"));
            add(new Extension("wang", "zhaojian", "java", "ad"));
            add(new Extension("wang", "zhaojian", "add", "ad"));
            add(new Extension("wang", "zhaojian", "", "ad"));
            add(new Extension("wang", "zhaojian", null, "ad"));
//            add(new Extension("wu", "bin", "java", "ad"));
            add(new Extension("wu", null, "php", "ad"));
            add(new Extension("wu", null, null, "ad"));
            add(new Extension("wu", null, "", "ad"));
            add(new Extension("wu", null, "add", "ad"));
            add(new Extension("liao", null, "", "ad"));
            add(new Extension("liao", null, null, "ad"));
            add(new Extension("liao", "asdfa", null, "ad"));
            add(new Extension("liao", null, "sdfafa", "ad"));
        }};
        List<Extension> extensions = Utils.sortByName(extensionList);
        extensions.forEach(System.out::println);
    }

    @Test
    public void sortByExtType() {
        List<Extension> extensionList = new ArrayList<Extension>() {{
            add(new Extension("luo", "kingwar", "java", "ad"));
            add(new Extension("chen", "yuandong", "java", "Dept"));
            add(new Extension("chen", "kunlong", "php", "User"));
            add(new Extension("wu", "bin", "java", "TMO"));
            add(new Extension("huang", "ping", "andriod", "ad"));
            add(new Extension("wang", "zhaojian", "java", "AO"));
            add(new Extension("wu", null, "php", "Other"));
        }};
        List<Extension> extensions = Utils.sortByExtType(extensionList);
        extensions.forEach(System.out::println);
    }

    @Test
    public void byQuarterGroupingOperate() {
        List<SaleItem> saleItems = new ArrayList<SaleItem>() {{
            add(new SaleItem(0, new BigDecimal("123.23")));
            add(new SaleItem(1, new BigDecimal("123.23")));
            add(new SaleItem(2, new BigDecimal("455.34")));
            add(new SaleItem(3, new BigDecimal("324.34")));
            add(new SaleItem(4, new BigDecimal("995.34")));
            add(new SaleItem(5, new BigDecimal("457.30")));
            add(new SaleItem(6, new BigDecimal("255.22")));
            add(new SaleItem(7, new BigDecimal("825.33")));
            add(new SaleItem(8, new BigDecimal("155.34")));
            add(new SaleItem(9, new BigDecimal("555.34")));
            add(new SaleItem(10, new BigDecimal("439.44")));
            add(new SaleItem(11, new BigDecimal("533.34")));
            add(new SaleItem(12, new BigDecimal("424.12")));
        }};
        List<QuarterSalesItem> sumQuarterSalesItems = Utils.sumByQuarter(saleItems);
        sumQuarterSalesItems.forEach(System.out::println);
        System.out.println("=========================================");
        List<QuarterSalesItem> maxQuarterSalesItems = Utils.maxByQuarter(saleItems);
        maxQuarterSalesItems.forEach(System.out::println);
    }

    @Test
    public void getUnUsedKeys() {
        int[] allKeys = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] usedKeys = new int[]{2, 3, 4};
        int[] unUsedKeys = Utils.getUnUsedKeys(allKeys, usedKeys);
        IntStream.of(unUsedKeys).boxed().collect(Collectors.toList()).forEach(System.out::println);
    }

}
