package com.ringcentral.bigDecimal;

import java.math.BigDecimal;

/**
 * 对BigDecimal方法处理
 *
 * @author jianhua.luo
 * @date 2020/9/23
 */
@FunctionalInterface
public interface ToBigDecimalFunction<T> {

    BigDecimal applyAsBigDecimal(T value);
}
