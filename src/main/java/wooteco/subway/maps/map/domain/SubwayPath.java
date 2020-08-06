package wooteco.subway.maps.map.domain;

import static java.util.stream.Collectors.*;

import java.util.List;

import com.google.common.collect.Lists;

public class SubwayPath {
    private static final int BASIC_FARE = 1250;

    private List<LineStationEdge> lineStationEdges;

    public SubwayPath(List<LineStationEdge> lineStationEdges) {
        this.lineStationEdges = lineStationEdges;
    }

    public List<LineStationEdge> getLineStationEdges() {
        return lineStationEdges;
    }

    public List<Long> extractLineId() {
        List<Long> lineIds = lineStationEdges.stream()
            .map(LineStationEdge::getLineId)
            .collect(toList());

        return lineIds;
    }

    public List<Long> extractStationId() {
        List<Long> stationIds = Lists.newArrayList(lineStationEdges.get(0).getLineStation().getPreStationId());
        stationIds.addAll(lineStationEdges.stream()
                .map(it -> it.getLineStation().getStationId())
                .collect(toList()));

        return stationIds;
    }

    public int calculateDuration() {
        return lineStationEdges.stream().mapToInt(it -> it.getLineStation().getDuration()).sum();
    }

    public int calculateDistance() {
        return lineStationEdges.stream().mapToInt(it -> it.getLineStation().getDistance()).sum();
    }

    public int calculateFare() {
        int distance = calculateDistance();
        int extraCharge = ExtraChargeType.of(distance).calculateExtraCharge(distance);
        return BASIC_FARE + extraCharge;
    }
}
