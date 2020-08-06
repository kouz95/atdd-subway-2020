package wooteco.subway.maps.map.application;

import java.util.Optional;

import org.springframework.stereotype.Service;

import wooteco.subway.maps.map.domain.SubwayPath;
import wooteco.subway.members.member.domain.LoginMember;

@Service
public class FareDiscountService {
    public int calculateFare(SubwayPath subwayPath, Optional<LoginMember> loginMember) {
        return 0;
    }
}
