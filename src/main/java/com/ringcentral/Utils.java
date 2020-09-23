package com.ringcentral;

import com.ringcentral.bigDecimal.CustomCollectors;
import com.ringcentral.util.MonthUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 工具类
 *
 * @author jianhua.luo
 * @date 2020/8/19
 */
public class Utils {

    /**
     * 问题1：根据firstName->lastName（nullable）->ext(nullable)升序排序
     *
     * @author jianhua.luo
     * @date 2020/8/19
     */
    public static List<Extension> sortByName(List<Extension> extensions) {
        if (isEmpty(extensions)) {
            return Collections.emptyList();
        }
        return extensions.stream().sorted(
                Comparator.comparing(Extension::getFirstName)
                        .thenComparing(Extension::getLastName, Comparator.nullsLast(Comparator.naturalOrder()))
                        .thenComparing(Extension::getExt, Comparator.nullsLast(Comparator.naturalOrder()))
        ).collect(Collectors.toList());
    }

    private static final Map<String, Integer> CHAR_RULES = new HashMap<String, Integer>() {{
        put("User", 1);
        put("Dept", 2);
        put("AO", 3);
        put("TMO", 4);
        put("Other", 5);
    }};

    /**
     * 问题2：根据extType字段，按User > Dept > AO > TMO > Other规则排序
     *
     * @author jianhua.luo
     * @date 2020/8/19
     */
    public static List<Extension> sortByExtType(List<Extension> extensions) {
        if (isEmpty(extensions)) {
            return Collections.emptyList();
        }
        return extensions.stream()
                .filter(extension -> CHAR_RULES.get(extension.getExtType()) != null)
                .sorted(Comparator.comparingInt(extension -> CHAR_RULES.get(extension.getExtType())))
                .collect(Collectors.toList());
    }

    /**
     * 问题3：按季度汇总所有销售项目
     *
     * @author jianhua.luo
     * @date 2020/8/19
     */
    public static List<QuarterSalesItem> sumByQuarter(List<SaleItem> saleItems) {
        if (isEmpty(saleItems)) {
            return Collections.emptyList();
        }
        return saleItems.stream()
                .filter(item -> MonthUtil.isValidMonth(item.getMonth()))
                .collect(Collectors.groupingBy(item -> MonthUtil.getQuarter(item.getMonth()),
                        CustomCollectors.summingBigDecimal(SaleItem::getSaleNumbers)))
                .entrySet().stream().map(entry -> {
                    QuarterSalesItem quarterSalesItem = new QuarterSalesItem();
                    quarterSalesItem.setQuarter(entry.getKey());
                    quarterSalesItem.setValue(entry.getValue());
                    return quarterSalesItem;
                }).collect(Collectors.toList());
    }

    /**
     * 问题4：求每季度最大销售量
     *
     * @author jianhua.luo
     * @date 2020/8/20
     */
    public static List<QuarterSalesItem> maxByQuarter(List<SaleItem> saleItems) {
        if (isEmpty(saleItems)) {
            return Collections.emptyList();
        }
        return saleItems.stream()
                .filter(item -> MonthUtil.isValidMonth(item.getMonth()))
                .collect(Collectors.groupingBy(item -> MonthUtil.getQuarter(item.getMonth()),
                        CustomCollectors.maxBy(saleItem -> saleItem.getSaleNumbers().doubleValue())))
                .entrySet().stream().map(entry -> {
                    QuarterSalesItem quarterSalesItem = new QuarterSalesItem();
                    quarterSalesItem.setQuarter(entry.getKey());
                    quarterSalesItem.setValue(entry.getValue());
                    return quarterSalesItem;
                }).collect(Collectors.toList());
    }

    //Question5

    /**
     * We have all Keys: 0-9;
     * usedKeys is an array to store all used keys like :[2,3,4];
     * We want to get all unused keys, in this example it would be: [0,1,5,6,7,8,9,]
     */
    public static int[] getUnUsedKeys(int[] allKeys, int[] usedKeys) {
        List<Integer> usedKeyList = IntStream.of(usedKeys).boxed().collect(Collectors.toList());
        Integer[] unUsedKeys = IntStream.of(allKeys).boxed().filter(num -> !usedKeyList.contains(num)).toArray(Integer[]::new);
        return Arrays.stream(unUsedKeys).mapToInt(Integer::valueOf).toArray();
    }

    public static boolean isEmpty(Collection collection) {
        return (collection == null || collection.isEmpty()) ? true : false;
    }
}
