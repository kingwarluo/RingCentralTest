package com.ringcentral;

import org.junit.Test;

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
            add(new Extension("wu", "bin", "java", "ad"));
            add(new Extension("wu", null, "php", "ad"));
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
            add(new SaleItem(1, 123.23));
            add(new SaleItem(2, 455.34));
            add(new SaleItem(3, 324.34));
            add(new SaleItem(4, 995.34));
            add(new SaleItem(5, 457.30));
            add(new SaleItem(6, 255.22));
            add(new SaleItem(7, 825.33));
            add(new SaleItem(8, 155.34));
            add(new SaleItem(9, 555.34));
            add(new SaleItem(10, 439.44));
            add(new SaleItem(11, 533.34));
            add(new SaleItem(12, 424.12));
        }};
        List<QuarterSalesItem> sumQuarterSalesItems = Utils.sumByQuarter(saleItems);
        sumQuarterSalesItems.forEach(System.out::println);
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
