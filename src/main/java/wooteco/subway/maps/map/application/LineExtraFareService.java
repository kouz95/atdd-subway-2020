package wooteco.subway.maps.map.application;

import static java.util.stream.Collectors.*;

import java.util.List;

import org.springframework.stereotype.Service;

import wooteco.subway.maps.line.domain.Line;
import wooteco.subway.maps.map.domain.SubwayPath;

@Service
public class LineExtraFareService {
    private static final int NO_EXTRA_FARE = 0;

    public int calculateExtraFare(SubwayPath subwayPath, List<Line> lines) {
        List<Long> lineIds = subwayPath.extractLineId();
        List<Line> includedLines = lines.stream()
            .filter(line -> lineIds.contains(line.getId()))
            .collect(toList());

        return includedLines.stream()
            .mapToInt(Line::getExtraFare)
            .max()
            .orElse(NO_EXTRA_FARE);
    }
}
