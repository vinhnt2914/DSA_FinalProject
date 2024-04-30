package DSA.Assignment1.service;

import DSA.Assignment1.dto.POIDto;
import DSA.Assignment1.repository.POIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class POIService {

    private final POIRepository poiRepository;

    @Autowired
    public POIService(POIRepository poiRepository) {
        this.poiRepository = poiRepository;
    }

    public void addPOI(POIDto poi) {
        poiRepository.add(poi);
    }

    public void updatePOI(int id, POIDto poi) {
        poiRepository.update(id, poi);
    }

    public void deletePOI(int id) {
        poiRepository.delete(id);
    }

    public POIDto getPOI(int id) {
        return poiRepository.findById(id);
    }

    public List<POIDto> searchPOIs(int x, int y, int width, int height, String service) {
        return poiRepository.search(x, y, width, height, service);
    }
}
