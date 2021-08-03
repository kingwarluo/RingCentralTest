package com.ringcentral.quarter;

import com.ringcentral.QuarterSalesItem;

import java.math.BigDecimal;

/**
 * 取最大值
 *
 * @author jianhua.luo
 * @date 2021/8/2
 */
public class MaxByQuarter extends AbstractQuarter {

    @Override
    public void operate(BigDecimal currentValue, QuarterSalesItem quarterSalesItem) {
        boolean replace = quarterSalesItem.getValue() == null || currentValue.compareTo(quarterSalesItem.getValue()) > 0;
        BigDecimal max = replace ? currentValue : quarterSalesItem.getValue();
        quarterSalesItem.setValue(max);
    }

}
