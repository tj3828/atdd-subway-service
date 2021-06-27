package nextstep.subway.path.domain;

import java.util.List;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.WeightedMultigraph;

import nextstep.subway.exception.path.PathException;
import nextstep.subway.line.domain.Section;
import nextstep.subway.station.domain.Station;

public class LinePathSearch {

    private WeightedMultigraph<Station, SectionEdge> weightedMultigraph;
    private DijkstraShortestPath<Station, SectionEdge> dijkstra;

    private LinePathSearch(List<Section> sections) {
        this.weightedMultigraph = settingGraph(sections);
        this.dijkstra = new DijkstraShortestPath<>(this.weightedMultigraph);
    }

    private WeightedMultigraph<Station, SectionEdge> settingGraph(List<Section> sections) {
        WeightedMultigraph<Station, SectionEdge> graph = new WeightedMultigraph<>(SectionEdge.class);
        addAllToGraph(sections, graph);

        return graph;
    }

    private void addAllToGraph(List<Section> sections, WeightedMultigraph<Station, SectionEdge> graph) {
        sections.stream()
            .forEach(section -> {
                SectionEdge edge = SectionEdge.of(section);
                graph.addVertex(section.getUpStation());
                graph.addVertex(section.getDownStation());
                graph.addEdge(section.getUpStation(), section.getDownStation(), edge);
                graph.setEdgeWeight(edge, section.getDistance());
            });
    }

    public static LinePathSearch of(List<Section> sections) {
        return new LinePathSearch(sections);
    }

    public Path searchPath(Station source, Station target) {
        validataionStation(source, target);
        GraphPath<Station, SectionEdge> path = dijkstra.getPath(source, target);
        return StationsDijkstraPath.of(path);
    }

    private void validataionStation(Station source, Station target) {
        if (source.equals(target)) {
            throw new PathException(PathException.SAME_STATION);
        }

        if (!weightedMultigraph.containsVertex(source) || !weightedMultigraph.containsVertex(target)) {
            throw new PathException(PathException.NO_REGISTRATION);
        }
    }

}