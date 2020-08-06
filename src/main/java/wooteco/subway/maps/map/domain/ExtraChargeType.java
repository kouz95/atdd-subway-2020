package wooteco.subway.maps.map.domain;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

public enum ExtraChargeType {
    BASIC(distance -> distance <= 10, distance -> 0),
    EXTRA(distance -> distance > 10 && distance <= 50, distance -> (int) ((Math.ceil((distance - 10) / 5) + 1) * 100)),
    ADDITIONAL_EXTRA(distance -> distance > 50, distance -> (int) ((Math.ceil((distance - 50) / 8) + 9) * 100));

    private final Predicate<Integer> judge;
    private final Function<Integer, Integer> calculateExtra;

    ExtraChargeType(Predicate<Integer> judge,
        Function<Integer, Integer> calculateExtra) {
        this.judge = judge;
        this.calculateExtra = calculateExtra;
    }

    public static ExtraChargeType of(int distance) {
        return Arrays.stream(values())
            .filter(value -> value.judge.test(distance))
            .findFirst()
            .orElseThrow(() -> new AssertionError("허용 되지 않는 distance 입력 distance = " + distance));
    }

    public int calculateExtraCharge(int distance) {
        return calculateExtra.apply(distance);
    }
}
