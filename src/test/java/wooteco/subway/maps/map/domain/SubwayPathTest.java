package wooteco.subway.maps.map.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import wooteco.subway.maps.line.domain.LineStation;

class SubwayPathTest {
    private LineStation fiveKm;
    private LineStation fourKm;
    private LineStation sixKm;
    private LineStation fiftyKm;

    @BeforeEach
    void setUp() {
        fiveKm = new LineStation(1L, null, 5, 4);
        fourKm = new LineStation(2L, 1L, 4, 4);
        sixKm = new LineStation(3L, 2L, 6, 4);
        fiftyKm = new LineStation(4L, 3L, 50, 4);
    }

    @DisplayName("10km 이내 일 경우 기본 운임 1,250원을 부과한다.")
    @Test
    void calculateFareForUnder10km() {
        SubwayPath subwayPath = new SubwayPath(
            Arrays.asList(new LineStationEdge(fiveKm, 1L), new LineStationEdge(fourKm, 1L)));
        assertThat(subwayPath.calculateFare()).isEqualTo(1250);
    }

    @DisplayName("10km 초과, 50km 이내 일 경우 5km 마다 100원의 추가 요금을 부과한다.")
    @Test
    void calculateFareForOver10kmUnder50km() {
        SubwayPath subwayPath = new SubwayPath(
            Arrays.asList(new LineStationEdge(fiveKm, 1L), new LineStationEdge(sixKm, 1L)));
        assertThat(subwayPath.calculateFare()).isEqualTo(1350);
    }

    @DisplayName("50km 초과일 경우 8km 마다 100원의 추가 요금을 부과한다.")
    @Test
    void calculateFareForOver50km() {
        SubwayPath subwayPath = new SubwayPath(
            Arrays.asList(new LineStationEdge(fiftyKm, 1L), new LineStationEdge(sixKm, 1L)));
        assertThat(subwayPath.calculateFare()).isEqualTo(1250 + 800 + 100);
    }
}
