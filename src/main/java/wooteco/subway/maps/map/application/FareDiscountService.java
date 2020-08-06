package wooteco.subway.maps.map.application;

import org.springframework.stereotype.Service;

import wooteco.subway.maps.map.domain.SubwayPath;
import wooteco.subway.members.member.domain.LoginMember;

@Service
public class FareDiscountService {
    public int calculateFare(SubwayPath subwayPath, LoginMember loginMember) {
        return 0;
    }
}
