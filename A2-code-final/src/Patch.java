import java.util.List;

public interface Patch {

    Coordinate getCoordinate();

    List<Patch> getNeighbours();

}
