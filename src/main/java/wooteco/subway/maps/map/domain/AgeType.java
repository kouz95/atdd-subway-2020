package wooteco.subway.maps.map.domain;

import java.util.Arrays;
import java.util.function.Predicate;

public enum AgeType {
    CHILD(age -> age >= 6 && age < 13, fare -> (fare - 350) / 2),
    YOUTH(age -> age >= 13 && age < 19, fare -> (fare - 350) * 4 / 5),
    NONE(age -> true, fare -> fare);

    private final Predicate<Integer> judge;
    private final DiscountStrategyByAge discountStrategyByAge;

    AgeType(Predicate<Integer> judge,
        DiscountStrategyByAge discountStrategyByAge) {
        this.judge = judge;
        this.discountStrategyByAge = discountStrategyByAge;
    }

    public static AgeType of(int age) {
        return Arrays.stream(values())
            .filter(value -> value.judge.test(age))
            .findFirst()
            .orElseThrow(AssertionError::new);
    }

    public DiscountStrategyByAge getDiscountStrategyByAge() {
        return discountStrategyByAge;
    }
}
