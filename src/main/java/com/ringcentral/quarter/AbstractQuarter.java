package com.ringcentral.quarter;

import com.ringcentral.QuarterSalesItem;
import com.ringcentral.SaleItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractQuarter implements Quarter {

    @Override
    public int getQuarter(int month) {
        return month / 3;
    }

    public List<QuarterSalesItem> calculate(List<SaleItem> saleItems) {
        saleItems = saleItems.stream().filter(saleItem -> saleItem.getMonth() >= 0 && saleItem.getMonth() < 12).collect(Collectors.toList());
        Map<Integer, QuarterSalesItem> quarterMap = new HashMap<>(8);
        for (SaleItem saleItem : saleItems) {
            int quarter = getQuarter(saleItem.getMonth());
            QuarterSalesItem quarterSalesItem = quarterMap.getOrDefault(quarter, new QuarterSalesItem(quarter));
            operate(saleItem.getSaleNumbers(), quarterSalesItem);
            quarterMap.put(quarterSalesItem.getQuarter(), quarterSalesItem);
        }
        return new ArrayList<>(quarterMap.values());
    }

    public abstract void operate(BigDecimal currentValue, QuarterSalesItem quarterSalesItem);

}
