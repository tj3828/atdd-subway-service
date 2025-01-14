package nextstep.subway.line.domain;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import nextstep.subway.station.domain.Station;

@Entity
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "line_id")
    private Line line;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "up_station_id", nullable = false)
    private Station upStation;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "down_station_id", nullable = false)
    private Station downStation;

    @Embedded
    private Distance distance;

    public Section() {
    }

    public Section(Line line, Station upStation, Station downStation, int distance) {
        this(line, upStation, downStation, new Distance(distance));
    }

    public Section(Line line, Station upStation, Station downStation, Distance distance) {
        this.line = line;
        this.upStation = upStation;
        this.downStation = downStation;
        this.distance = distance;
    }

    public void updateUpStation(Station station, Distance newDistance) {
        if (containDownStation(station)) {
            throw new IllegalArgumentException("이미 하행역으로 설정되어 있습니다.");
        }
        this.upStation = station;
        this.distance = this.distance.subtract(newDistance);
    }

    public void updateDownStation(Station station, Distance newDistance) {
        if (containUpStation(station)) {
            throw new IllegalArgumentException("이미 하행역으로 설정되어 있습니다.");
        }
        this.downStation = station;
        this.distance = this.distance.subtract(newDistance);
    }

    public void changeLine(Line line) {
        this.line = line;
    }

    public boolean containUpStation(Station station) {
        return this.upStation == station;
    }

    public boolean containDownStation(Station station) {
        return this.downStation == station;
    }

    public Long getId() {
        return id;
    }

    public Line getLine() {
        return line;
    }

    public Station getUpStation() {
        return upStation;
    }

    public Station getDownStation() {
        return downStation;
    }

    public Distance getDistance() {
        return distance;
    }

}
