package wooteco.subway.maps.map.application;

import java.util.Optional;

import org.springframework.stereotype.Service;

import wooteco.subway.maps.map.domain.AgeType;
import wooteco.subway.maps.map.domain.DiscountStrategyByAge;
import wooteco.subway.maps.map.domain.SubwayPath;
import wooteco.subway.members.member.domain.LoginMember;

@Service
public class MemberAgeFareDiscountService {
    public int calculateTotalFare(SubwayPath subwayPath, Optional<LoginMember> optionalLoginMember, int extraFare) {
        int totalFare = subwayPath.calculateFare() + extraFare;
        if (optionalLoginMember.isPresent()) {
            DiscountStrategyByAge discountStrategyByAge = AgeType.of(optionalLoginMember.get().getAge())
                .getDiscountStrategyByAge();
            return discountStrategyByAge.calculate(totalFare);
        }
        return totalFare;
    }
}
