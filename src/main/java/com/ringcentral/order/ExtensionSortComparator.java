package com.ringcentral.order;

import com.ringcentral.Extension;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 新的排序规则
 *
 * @author jianhua.luo
 * @date 2021/8/2
 */
public class ExtensionSortComparator {

    private static final Map<String, Integer> CHAR_RULES = new HashMap<String, Integer>() {{
        put("User", 1);
        put("Dept", 2);
        put("AO", 3);
        put("TMO", 4);
        put("Other", 5);
    }};

    public static List<Extension> sortByExtType(List<Extension> extensions) {
        return extensions.stream()
                .filter(extension -> CHAR_RULES.get(extension.getExtType()) != null)
                .sorted(Comparator.comparingInt(extension -> CHAR_RULES.get(extension.getExtType())))
                .collect(Collectors.toList());
    }

    /**
     * 空串放最后比较器
     */
    public final class EmptyStringLastComparator implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            if(o1 == null || o2 == null) {
                return 0;
            }
            if(o1.equals("") || o2.equals("")) {
                return -o1.compareTo(o2);
            } else {
                return o1.compareTo(o2);
            }
        }
    }


}
