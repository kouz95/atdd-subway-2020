package wooteco.subway.maps.map.application;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import wooteco.subway.maps.line.domain.Line;
import wooteco.subway.maps.line.domain.LineStation;
import wooteco.subway.maps.map.domain.LineStationEdge;
import wooteco.subway.maps.map.domain.SubwayPath;
import wooteco.subway.members.member.domain.LoginMember;

class
MemberAgeFareDiscountServiceTest {
    private MemberAgeFareDiscountService memberAgeFareDiscountService;
    private LineStation fiveKm;
    private LineStation fourKm;
    private Line extraFareLine500;
    private Line extraFareLine900;

    private Optional<LoginMember> child;
    private Optional<LoginMember> youth;
    private Optional<LoginMember> adult;
    private Optional<LoginMember> notLogin;

    @BeforeEach
    void setUp() {
        memberAgeFareDiscountService = new MemberAgeFareDiscountService();

        fiveKm = new LineStation(1L, null, 5, 4);
        fourKm = new LineStation(2L, 1L, 4, 4);

        extraFareLine500 = new Line(1L, "test", "test", LocalTime.now(), LocalTime.now(), 10, 500);
        extraFareLine900 = new Line(2L, "test", "test", LocalTime.now(), LocalTime.now(), 10, 900);

        child = Optional.of(new LoginMember(1L, "test", "test", 6));
        youth = Optional.of(new LoginMember(1L, "test", "test", 13));
        adult = Optional.of(new LoginMember(1L, "test", "test", 19));
        notLogin = Optional.empty();
    }

    @DisplayName("총 운임이 2150원 일때, 로그인 한 유저가 어린이 일 경우 350원을 공제한 금액의 50%할인 하여 900원을 부과한다.")
    @Test
    void calculateTotalFareForChild() {
        SubwayPath subwayPath = new SubwayPath(Arrays.asList(new LineStationEdge(fiveKm, 2L), new LineStationEdge(fourKm, 2L)));
        assertThat(memberAgeFareDiscountService.calculateTotalFare(subwayPath, child, 900)).isEqualTo(900);
    }

    @DisplayName("총 운임이 2150원 일때, 로그인 한 유저가 청소년 일 경우 350원을 공제한 금액의 20%할인 하여 1440원을 부과한다.")
    @Test
    void calculateTotalFareForYouth() {
        SubwayPath subwayPath = new SubwayPath(Arrays.asList(new LineStationEdge(fiveKm, 2L), new LineStationEdge(fourKm, 2L)));
        assertThat(memberAgeFareDiscountService.calculateTotalFare(subwayPath, youth, 900)).isEqualTo(1440);
    }

    @DisplayName("총 운임이 2150원 일때, 로그인 한 유저가 어른일 경우 2150원을 부과한다.")
    @Test
    void calculateTotalFareForAdult() {
        SubwayPath subwayPath = new SubwayPath(Arrays.asList(new LineStationEdge(fiveKm, 2L), new LineStationEdge(fourKm, 2L)));
        assertThat(memberAgeFareDiscountService.calculateTotalFare(subwayPath, adult, 900)).isEqualTo(2150);
    }

    @DisplayName("총 운임이 2150원 일때, 유저의 나이를 알 수 없을 경우 2150원을 부과한다.")
    @Test
    void calculateTotalFareForNotLogin() {
        SubwayPath subwayPath = new SubwayPath(Arrays.asList(new LineStationEdge(fiveKm, 2L), new LineStationEdge(fourKm, 2L)));
        assertThat(memberAgeFareDiscountService.calculateTotalFare(subwayPath, adult, 900)).isEqualTo(2150);
    }
}