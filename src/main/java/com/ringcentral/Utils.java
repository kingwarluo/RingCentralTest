package com.ringcentral;

import com.ringcentral.order.ExtensionSortComparator;
import com.ringcentral.quarter.CollectorsUtil;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 工具类
 *
 * @author jianhua.luo
 * @date 2020/8/19
 */
public class Utils {

    private static ExtensionSortComparator extensionSortComparator = new ExtensionSortComparator();

    /**
     * 问题1：根据firstName->lastName（nullable）->ext(nullable)升序排序
     *
     * @author jianhua.luo
     * @date 2020/8/19
     */
    public static List<Extension> sortByName(List<Extension> extensions) {
        return extensions.stream().sorted(
                Comparator.comparing(Extension::getFirstName)
                    .thenComparing(Extension::getLastName, Comparator.nullsLast(extensionSortComparator.new EmptyStringLastComparator()))
                    .thenComparing(Extension::getExt, Comparator.nullsLast(extensionSortComparator.new EmptyStringLastComparator())))
            .collect(Collectors.toList());
    }

    /**
     * 问题2：根据extType字段，按User > Dept > AO > TMO > Other规则排序
     *
     * @author jianhua.luo
     * @date 2020/8/19
     */
    public static List<Extension> sortByExtType(List<Extension> extensions) {
        return ExtensionSortComparator.sortByExtType(extensions);
    }

    /**
     * 问题3：按季度汇总所有销售项目
     *
     * @author jianhua.luo
     * @date 2020/8/19
     */
    public static List<QuarterSalesItem> sumByQuarter(List<SaleItem> saleItems) {
        Map<Integer, BigDecimal> collect = saleItems.stream().collect(Collectors.groupingBy(item -> item.getMonth() / 3, CollectorsUtil.summingBigDecimal(SaleItem::getSaleNumbers)));
        return collect.entrySet().stream().map(s -> {
            QuarterSalesItem q = new QuarterSalesItem(s.getKey());
            q.setValue(s.getValue());
            return q;
        }).collect(Collectors.toList());
    }

    public static List<QuarterSalesItem> getQuarterItem(List<SaleItem> saleItems, int op) {
        // 1. 将数据按季度分组
        // 2. 将季度数据递归操作
        // 3. 整出一个集合
        Map<Integer, BigDecimal> collect = saleItems.stream().collect(Collectors.groupingBy(item -> item.getMonth() / 3, CollectorsUtil.summingBigDecimal(SaleItem::getSaleNumbers)));
        return collect.entrySet().stream().map(s -> {
            QuarterSalesItem q = new QuarterSalesItem(s.getKey());
            q.setValue(s.getValue());
            return q;
        }).collect(Collectors.toList());
    }

    /**
     * 问题4：求每季度最大销售量
     *
     * @author jianhua.luo
     * @date 2020/8/20
     */
    public static List<QuarterSalesItem> maxByQuarter(List<SaleItem> saleItems) {
        Map<Integer, BigDecimal> collect = saleItems.stream().collect(Collectors.groupingBy(item -> item.getMonth() / 3, CollectorsUtil.maxBigDecimal(SaleItem::getSaleNumbers)));
        return collect.entrySet().stream().map(s -> {
            QuarterSalesItem q = new QuarterSalesItem(s.getKey());
            q.setValue(s.getValue());
            return q;
        }).collect(Collectors.toList());
    }

    //Question5

    /**
     * We have all Keys: 0-9;
     * usedKeys is an array to store all used keys like :[2,3,4];
     * We want to get all unused keys, in this example it would be: [0,1,5,6,7,8,9,]
     */
    public static int[] getUnUsedKeys(int[] allKeys, int[] usedKeys) {
        Set<Integer> usedKeyList = IntStream.of(usedKeys).boxed().collect(Collectors.toSet());
        Integer[] unUsedKeys = IntStream.of(allKeys).boxed().filter(num -> !usedKeyList.contains(num)).toArray(Integer[]::new);
        return Arrays.stream(unUsedKeys).mapToInt(Integer::valueOf).toArray();
    }
}
