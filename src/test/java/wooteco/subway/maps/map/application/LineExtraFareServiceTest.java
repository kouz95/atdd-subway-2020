package wooteco.subway.maps.map.application;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import wooteco.subway.maps.line.domain.Line;
import wooteco.subway.maps.line.domain.LineStation;
import wooteco.subway.maps.map.domain.LineStationEdge;
import wooteco.subway.maps.map.domain.SubwayPath;

class LineExtraFareServiceTest {
    private LineExtraFareService lineExtraFareService;

    private LineStation fiveKm;
    private LineStation fourKm;
    private LineStation sixKm;
    private LineStation fiftyKm;
    private List<Line> lines;
    private Line extraFareLine500;
    private Line extraFareLine900;

    @BeforeEach
    void setUp() {
        lineExtraFareService = new LineExtraFareService();

        fiveKm = new LineStation(1L, null, 5, 4);
        fourKm = new LineStation(2L, 1L, 4, 4);
        sixKm = new LineStation(3L, 2L, 6, 4);
        fiftyKm = new LineStation(4L, 3L, 50, 4);

        extraFareLine500 = new Line(1L, "test", "test", LocalTime.now(), LocalTime.now(), 10, 500);
        extraFareLine900 = new Line(2L, "test", "test", LocalTime.now(), LocalTime.now(), 10, 900);
        lines = Arrays.asList(extraFareLine500, extraFareLine900);
    }

    @DisplayName("900원 추가 요금이 있는 노선 9km 이용 시 노선 추가 요금은 900원이다.")
    @Test
    void calculateExtraFareForOneExtraLine() {
        SubwayPath subwayPath = new SubwayPath(
            Arrays.asList(new LineStationEdge(fiveKm, 2L), new LineStationEdge(fourKm, 2L)));

        assertThat(lineExtraFareService.calculateExtraFare(subwayPath, lines)).isEqualTo(900);
    }

    @DisplayName("500원과 900원의 추가 요금이 있는 노선 9km 이용 시 노선 추가 요금은 900원이다.")
    @Test
    void calculateExtraFareForTwoExtraLine() {
        SubwayPath subwayPath = new SubwayPath(
            Arrays.asList(new LineStationEdge(fiveKm, 1L), new LineStationEdge(fourKm, 2L)));

        assertThat(lineExtraFareService.calculateExtraFare(subwayPath, lines)).isEqualTo(900);
    }
}