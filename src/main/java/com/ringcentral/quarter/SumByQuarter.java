package com.ringcentral.quarter;

import com.ringcentral.QuarterSalesItem;

import java.math.BigDecimal;

/**
 * 求和
 *
 * @author jianhua.luo
 * @date 2021/8/2
 */
public class SumByQuarter extends AbstractQuarter {

    @Override
    public void operate(BigDecimal currentValue, QuarterSalesItem quarterSalesItem) {
        BigDecimal sum = quarterSalesItem.getValue() == null ? currentValue : quarterSalesItem.getValue().add(currentValue);
        quarterSalesItem.setValue(sum);
    }

}
