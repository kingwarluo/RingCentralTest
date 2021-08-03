package com.ringcentral;

import com.ringcentral.order.ExtensionSortComparator;
import com.ringcentral.quarter.MaxByQuarter;
import com.ringcentral.quarter.SumByQuarter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
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
        return new SumByQuarter().calculate(saleItems);
    }

    /**
     * 问题4：求每季度最大销售量
     *
     * @author jianhua.luo
     * @date 2020/8/20
     */
    public static List<QuarterSalesItem> maxByQuarter(List<SaleItem> saleItems) {
        return new MaxByQuarter().calculate(saleItems);
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
