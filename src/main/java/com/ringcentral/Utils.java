package com.ringcentral;

import com.sun.deploy.util.StringUtils;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import static java.util.Optional.ofNullable;

/**
 * 工具类
 *
 * @author jianhua.luo
 * @date 2020/8/19
 */
public class Utils {

	/**
	 * 根据firstName->lastName（nullable）->ext(nullable)升序排序
	 *
	 * @author jianhua.luo
	 * @date 2020/8/19
	 */
	public static List<Extension> sortByName(List<Extension> extensions) {
		if(isEmpty(extensions)) {
			return Collections.emptyList();
		}
		return extensions.stream().sorted(
			Comparator.comparing(Extension::getFirstName)
				.thenComparing(Extension::getLastName, Comparator.nullsLast(Comparator.naturalOrder()))
				.thenComparing(Extension::getExt, Comparator.nullsLast(Comparator.naturalOrder()))
		).collect(Collectors.toList());
	}

	private static final Map<String, Integer> CHAR_RULES = new HashMap<String, Integer>(){{
		put("User", 1);
		put("Dept", 2);
		put("AO", 3);
		put("TMO", 4);
		put("Other", 5);
	}};

	/**
	 * 根据extType字段，按User > Dept > AO > TMO > Other规则排序
	 *
	 * @author jianhua.luo
	 * @date 2020/8/19
	 */
	public static List<Extension> sortByExtType(List<Extension> extensions) {
		if(isEmpty(extensions)) {
			return Collections.emptyList();
		}
		return extensions.stream()
				.filter(extension -> CHAR_RULES.get(extension.getExtType()) != null)
				.sorted(Comparator.comparingInt(extension -> CHAR_RULES.get(extension.getExtType())))
				.collect(Collectors.toList());
	}

	/**
	 * 按季度统计操作
	 *
	 * @author jianhua.luo
	 * @date 2020/8/20
	 */
	public static List<QuarterSalesItem> byQuarterGroupingOperate(List<SaleItem> saleItems, BinaryOperator<Double> binaryOperator) {
		return saleItems.stream()
				.filter(item -> item.getMonth() >= 1 || item.getMonth() <= 12)
				.collect(Collectors.groupingBy(item -> (item.getMonth() - 1) / 3 + 1,
						Collectors.reducing(0D, SaleItem::getSaleNumbers, binaryOperator)))
				.entrySet()
				.stream()
				.map(entry -> {
					QuarterSalesItem quarterSalesItem = new QuarterSalesItem();
					quarterSalesItem.setQuarter(entry.getKey());
					quarterSalesItem.setValue(entry.getValue());
					return quarterSalesItem;
				}).collect(Collectors.toList());
	}

	/**
	 * 按季度汇总所有销售项目
	 *
	 * @author jianhua.luo
	 * @date 2020/8/19
	 */
	public static List<QuarterSalesItem> sumByQuarter(List<SaleItem> saleItems) {
		if(isEmpty(saleItems)) {
			return Collections.emptyList();
		}
		return byQuarterGroupingOperate(saleItems, Double::sum);
	}
	
    /**
     * 求每季度最大销售量
     *
     * @author jianhua.luo
     * @date 2020/8/20
     */
	public static List<QuarterSalesItem> maxByQuarter(List<SaleItem> saleItems) {
		if(isEmpty(saleItems)) {
			return Collections.emptyList();
		}
		return byQuarterGroupingOperate(saleItems, BinaryOperator.maxBy(Comparator.naturalOrder()));
	}
    
	//Question5
	/**
	 * We have all Keys: 0-9;
	 * usedKeys is an array to store all used keys like :[2,3,4];
	 * We want to get all unused keys, in this example it would be: [0,1,5,6,7,8,9,]
	 */
	
	public static int[] getUnUsedKeys(int[] allKeys, int[] usedKeys) {
		return null;
	}

	public static boolean isEmpty(Collection collection) {
		return (collection == null || collection.isEmpty()) ? true : false;
	}
}
