package wooteco.subway.maps.map.documentation;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.web.context.WebApplicationContext;

import wooteco.security.core.TokenResponse;
import wooteco.subway.common.documentation.Documentation;
import wooteco.subway.maps.map.application.MapService;
import wooteco.subway.maps.map.dto.PathResponse;
import wooteco.subway.maps.map.ui.MapController;
import wooteco.subway.maps.station.dto.StationResponse;

@WebMvcTest(controllers = MapController.class)
public class PathDocumentation extends Documentation {
    @Autowired
    private MapController mapController;
    @MockBean
    private MapService mapService;

    protected TokenResponse tokenResponse;

    @BeforeEach
    public void setUp(WebApplicationContext context, RestDocumentationContextProvider restDocumentation) {
        super.setUp(context, restDocumentation);
        tokenResponse = new TokenResponse("token");
    }

    @Test
    void findPath() {
        List<StationResponse> stations = Arrays.asList(
            new StationResponse(1L, "교대역", LocalDateTime.now(), LocalDateTime.now()),
            new StationResponse(2L, "강남역", LocalDateTime.now(), LocalDateTime.now()),
            new StationResponse(3L, "양재역", LocalDateTime.now(), LocalDateTime.now())
        );
        PathResponse pathResponse = new PathResponse(stations, 3, 4, 1250);
        when(mapService.findPath(any(), any(), any(), any())).thenReturn(pathResponse);

        given().log().all().
            header("Authorization", "Bearer " + tokenResponse.getAccessToken()).
            accept(MediaType.APPLICATION_JSON_VALUE).
            param("source", 1L).
            param("target", 3L).
            param("type", "DISTANCE").
            when().
            get("/paths").
            then().
            log().all().
            apply(document("paths/find-path",
                getDocumentRequest(),
                getDocumentResponse(),
                requestHeaders(
                    headerWithName("Authorization").description("Optional - Bearer auth credentials")),
                responseFields(
                    fieldWithPath("stations[]").type(JsonFieldType.ARRAY).description("지하철역 목록"),
                    fieldWithPath("stations[].id").type(JsonFieldType.NUMBER).description("지하철역 아이디"),
                    fieldWithPath("stations[].name").type(JsonFieldType.STRING).description("지하철역 이름"),
                    fieldWithPath("duration").type(JsonFieldType.NUMBER).description("소요 시간"),
                    fieldWithPath("distance").type(JsonFieldType.NUMBER).description("이동 거리"),
                    fieldWithPath("fare").type(JsonFieldType.NUMBER).description("요금")))).
            extract();
    }
}


/*
    @Test
    void findFavorites() {
        List<FavoriteResponse> favoriteResponses = Lists.newArrayList(
                new FavoriteResponse(1L, new StationResponse(1L, "광교중앙역", LocalDateTime.now(), LocalDateTime.now()), new StationResponse(2L, "잠실역", LocalDateTime.now(), LocalDateTime.now())),
                new FavoriteResponse(2L, new StationResponse(3L, "강남역", LocalDateTime.now(), LocalDateTime.now()), new StationResponse(4L, "역삼역", LocalDateTime.now(), LocalDateTime.now()))
        );
        when(favoriteService.findFavorites(any())).thenReturn(favoriteResponses);

        given().log().all().
                header("Authorization", "Bearer " + tokenResponse.getAccessToken()).
                accept(MediaType.APPLICATION_JSON_VALUE).
                when().
                get("/favorites").
                then().
                log().all().
                apply(document("favorites/find-list",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer auth credentials")),
                        responseFields(
                                fieldWithPath("[]").type(JsonFieldType.ARRAY).description("즐겨찾기 목록"),
                                fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("즐겨찾기 아이디"),
                                fieldWithPath("[].source").type(JsonFieldType.OBJECT).description("출발역"),
                                fieldWithPath("[].source.id").type(JsonFieldType.NUMBER).description("지하철역 아이디"),
                                fieldWithPath("[].source.name").type(JsonFieldType.STRING).description("지하철역 이름"),
                                fieldWithPath("[].target").type(JsonFieldType.OBJECT).description("도착역"),
                                fieldWithPath("[].target.id").type(JsonFieldType.NUMBER).description("지하철역 아이디"),
                                fieldWithPath("[].target.name").type(JsonFieldType.STRING).description("지하철역 이름")))).
                extract();
    }

    @Test
    void deleteFavorite() {
        given().
                header("Authorization", "Bearer " + tokenResponse.getAccessToken()).
                when().
                delete("/favorites/{favoriteId}", 1L).
                then().
                log().all().
                apply(document("favorites/delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestHeaders(
                                headerWithName("Authorization").description("Bearer auth credentials")),
                        pathParameters(
                                parameterWithName("favoriteId").description("삭제할 즐겨찾기 아이디")))).
                extract();
    }
}
 */