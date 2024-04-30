package DSA.Assignment1.repository;

import DSA.Assignment1.dto.POIDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class POIRepository {

    public void add(POIDto poi) {
        // Implementation goes here
    }

    public void update(int id, POIDto poi) {
        // Implementation goes here
    }

    public void delete(int id) {
        // Implementation goes here
    }

    public POIDto findById(int id) {
        // Implementation goes here
        return null;
    }

    public List<POIDto> search(int x, int y, int width, int height, String service) {
        // Implementation goes here
        return null;
    }
}
