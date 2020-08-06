package wooteco.subway.maps.map.domain;

@FunctionalInterface
public interface DiscountStrategyByAge {
    int calculate(int fare);
}
