package nextstep.subway.line.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.stream.Stream;
import nextstep.subway.station.domain.Station;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

class SectionsTest {

    private Station 강남역;
    private Station 양재시민의숲역;
    private Station 판교역;
    private Station 양재역;
    private Line 신분당선;

    @BeforeEach
    void setUp() {
        강남역 = new Station("강남역");
        양재시민의숲역 = new Station("양재시민의숲역");
        판교역 = new Station("판교역");
        양재역 = new Station("양재역");
        신분당선 = new Line("신분당선", "red", 강남역, 양재시민의숲역, 10);
    }

    @Test
    @DisplayName("노선에 구간을 등록 후, 등록된 지하철역을 상하행 순서로 출력한다.")
    void getStations() {
        신분당선.add(new Section(신분당선, 양재시민의숲역, 판교역, new Distance(5)));

        assertThat(신분당선.getStations()).containsExactly(강남역, 양재시민의숲역, 판교역);
    }

    @TestFactory
    @DisplayName("노선에 구간을 추가할 때 실패하는 케이스를 확인한다.")
    Stream<DynamicTest> addLineStation_fail() {
        return Stream.of(
            DynamicTest.dynamicTest("노선에 이미 구간이 등록되어있을 경우", () -> {
                assertThatIllegalArgumentException()
                    .isThrownBy(() -> 신분당선.add(new Section(신분당선, 강남역, 양재시민의숲역, new Distance(5))));
            }),
            DynamicTest.dynamicTest("노선에 두 종점역이 모두 등록되어있지 않을 경우", () -> {
                assertThatIllegalArgumentException()
                    .isThrownBy(() -> 신분당선.add(new Section(신분당선, 양재역, 판교역, new Distance(5))));
            }),
            DynamicTest.dynamicTest("기존 구간보다 거리가 긴 노선이 추가될 경우 ", () -> {
                assertThatIllegalArgumentException()
                    .isThrownBy(() -> 신분당선.add(new Section(신분당선, 강남역, 판교역, new Distance(15))));
            })
        );
    }

    @Test
    @DisplayName("노선에서 지하철역을 삭제할 때, 남은 구간이 1개라면 에러를 던진다.")
    void removeLineStation_fail() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> 신분당선.removeLineStation(강남역));
    }

    @Test
    @DisplayName("노선에서 지하철역을 삭제하면, 노선 정보에 지하철역에서 삭제된다.")
    void removeLineStation() {
        신분당선.add(new Section(신분당선, 양재시민의숲역, 판교역, new Distance(5)));

        신분당선.removeLineStation(양재시민의숲역);
        assertThat(신분당선.getStations()).containsExactly(강남역, 판교역);
    }

}
