package DSA.Assignment1.controller;

import DSA.Assignment1.dto.POIDto;
import DSA.Assignment1.service.POIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pois")
public class POIController {

    private final POIService poiService;

    @Autowired
    public POIController(POIService poiService) {
        this.poiService = poiService;
    }

    @PostMapping
    public ResponseEntity<String> addPOI(@RequestBody POIDto poi) {
        poiService.addPOI(poi);
        return ResponseEntity.ok("POI added successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePOI(@PathVariable int id, @RequestBody POIDto poi) {
        poiService.updatePOI(id, poi);
        return ResponseEntity.ok("POI updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePOI(@PathVariable int id) {
        poiService.deletePOI(id);
        return ResponseEntity.ok("POI deleted successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<POIDto> getPOI(@PathVariable int id) {
        POIDto poi = poiService.getPOI(id);
        return ResponseEntity.ok(poi);
    }

    @GetMapping("/search")
    public ResponseEntity<List<POIDto>> searchPOIs(
            @RequestParam int x,
            @RequestParam int y,
            @RequestParam int width,
            @RequestParam int height,
            @RequestParam String service) {

        List<POIDto> pois = poiService.searchPOIs(x, y, width, height, service);
        return ResponseEntity.ok(pois);
    }
}
