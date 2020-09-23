package com.ringcentral.bigDecimal;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Collections;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collector;

/**
 * 自定义Collectors
 * 因java8没有关于Bigdecimal求和的一类方法，这里根据java8包含的int,double求和的方法，进行修改，变为自己合适的方法
 *
 * @author jianhua.luo
 * @date 2020/9/23
 */
public final class CustomCollectors {

    private static final Set<Collector.Characteristics> CH_NOID = Collections.emptySet();

    // 这个类不能实例化
    private CustomCollectors() {
    }

    @SuppressWarnings("unchecked")
    private static <I, R> Function<I, R> castingIdentity() {
        return i -> (R) i;
    }

    @SuppressWarnings("hiding")
    static class CollectorImpl<T, A, R> implements Collector<T, A, R> {
        private final Supplier<A> supplier;
        private final BiConsumer<A, T> accumulator;
        private final BinaryOperator<A> combiner;
        private final Function<A, R> finisher;
        private final Set<Characteristics> characteristics;

        CollectorImpl(Supplier<A> supplier, BiConsumer<A, T> accumulator, BinaryOperator<A> combiner, Function<A, R> finisher, Set<Characteristics> characteristics) {
            this.supplier = supplier;
            this.accumulator = accumulator;
            this.combiner = combiner;
            this.finisher = finisher;
            this.characteristics = characteristics;
        }

        CollectorImpl(Supplier<A> supplier, BiConsumer<A, T> accumulator, BinaryOperator<A> combiner, Set<Characteristics> characteristics) {
            this(supplier, accumulator, combiner, castingIdentity(), characteristics);
        }

        @Override
        public Supplier<A> supplier() {
            return supplier;
        }

        @Override
        public BiConsumer<A, T> accumulator() {
            return accumulator;
        }

        @Override
        public BinaryOperator<A> combiner() {
            return combiner;
        }

        @Override
        public Function<A, R> finisher() {
            return finisher;
        }

        @Override
        public Set<Characteristics> characteristics() {
            return characteristics;
        }
    }

    // 求和方法
    public static <T> Collector<T, ?, BigDecimal> summingBigDecimal(ToBigDecimalFunction<? super T> mapper) {
        return new CollectorImpl<>(
                () -> new BigDecimal[]{new BigDecimal(0)},
                (a, t) -> {
                    a[0] = a[0].add(mapper.applyAsBigDecimal(t), MathContext.DECIMAL32);
                },
                (a, b) -> {
                    a[0] = a[0].add(b[0], MathContext.DECIMAL32);
                    return a;
                },
                a -> a[0], CH_NOID
        );
    }

    //求最大,这里的最小MIN值，作为初始条件判断值，如果某些数据范围超过百亿以后，可以根据需求换成Long.MIN_VALUE或者Double.MIN_VALUE
    public static <T> Collector<T, ?, BigDecimal> maxBy(ToDoubleFunction<? super T> mapper) {
        return new CollectorImpl<>(
                () -> new Double[]{Double.MIN_VALUE},
                (a, t) -> {
                    a[0] = Double.max(a[0], mapper.applyAsDouble(t));
                },
                (a, b) -> {
                    a[0] = Double.max(a[0], b[0]);
                    return a;
                },
                a -> new BigDecimal(a[0].toString()), CH_NOID);
    }

    //求最小，这里的最大MAX值，作为初始条件判断值，如果某些数据范围超过百亿以后，可以根据需求换成Long.MAX_VALUE或者Double.MAX_VALUE
    public static <T> Collector<T, ?, BigDecimal> minBy(ToDoubleFunction<? super T> mapper) {
        return new CollectorImpl<>(
                () -> new Double[]{Double.MAX_VALUE},
                (a, t) -> {
                    a[0] = Double.max(a[0], mapper.applyAsDouble(t));
                },
                (a, b) -> {
                    a[0] = Double.max(a[0], b[0]);
                    return a;
                },
                a -> new BigDecimal(a[0].toString()), CH_NOID);
    }

    /**
     * 返回一个平均值
     *
     * @param newScale     保留小数位数
     * @param roundingMode 小数处理方式
     *                     #ROUND_UP 进1
     *                     #ROUND_DOWN 退1
     *                     #ROUND_CEILING  进1截取：正数则ROUND_UP，负数则ROUND_DOWN
     *                     #ROUND_FLOOR  退1截取：正数则ROUND_DOWN，负数则ROUND_UP
     *                     #ROUND_HALF_UP >=0.5进1
     *                     #ROUND_HALF_DOWN >0.5进1
     *                     #ROUND_HALF_EVEN
     *                     #ROUND_UNNECESSARY
     */
    //求平均，并且保留小数
    public static <T> Collector<T, ?, BigDecimal> averagingBigDecimal(ToBigDecimalFunction<? super T> mapper, int newScale, int roundingMode) {
        return new CollectorImpl<>(
                () -> new BigDecimal[]{new BigDecimal(0), new BigDecimal(0)},
                (a, t) -> {
                    a[0] = a[0].add(mapper.applyAsBigDecimal(t));
                    a[1] = a[1].add(BigDecimal.ONE);
                },
                (a, b) -> {
                    a[0] = a[0].add(b[0]);
                    return a;
                },
                a -> a[0].divide(a[1], MathContext.DECIMAL32).setScale(newScale, roundingMode), CH_NOID);
    }

}
