package com.example.testapi.controller;

import com.example.testapi.dto.POIJson;
import com.example.testapi.model.Array.MyArray;
import com.example.testapi.model.KDTree.POINode;
import com.example.testapi.model.POI;
import com.example.testapi.model.POIWithDistance;
import com.example.testapi.dto.POIWithDistanceJson;
import com.example.testapi.utility.APIDataManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class POIController {

    private final APIDataManager apiDataManager;

    public POIController() {
        this.apiDataManager = APIDataManager.getInstance();
    }

    @GetMapping("/get/{x}/{y}")
    public ResponseEntity<POIJson> getPOI(@PathVariable int x, @PathVariable int y) {
        POI poi = apiDataManager.kdTree.search(x, y);
        if (poi == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(poi.mapToPOIJson());
    }

    @PostMapping()
    public ResponseEntity<List<POIWithDistanceJson>> searchPOIs(@RequestBody Map<String, String> body) {
        int x = Integer.parseInt(body.get("x"));
        int y = Integer.parseInt(body.get("y"));
        String service = body.get("service");
        int boundingSize = Integer.parseInt(body.get("boundingSize"));
        MyArray<POIWithDistance> res = apiDataManager.kdTree.KNNSearch(x, y, service, boundingSize);
        List<POIWithDistance> poiList = res.toArrayList();
        return new ResponseEntity<>(poiList.stream().map(POIWithDistance::mapToJSON).toList(), HttpStatus.CREATED);
    }

    @PutMapping("/update/{x}/{y}")
    public ResponseEntity<POIJson> updatePOI(@PathVariable int x, @PathVariable int y, @RequestBody Map<String, String[]> body) {
        POI kdTreePOI = apiDataManager.kdTree.search(x,y);
        if (kdTreePOI == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        MyArray<String> newServices = new MyArray<>(body.get("services"));
        kdTreePOI.setServices(newServices);
        return new ResponseEntity<>(kdTreePOI.mapToPOIJson(), HttpStatus.CREATED);
    }

    @DeleteMapping("/remove/{x}/{y}")
    public ResponseEntity<POIJson> removePOI(@PathVariable int x, @PathVariable int y) {
        // Find the poi from hashmap
        POINode poi = apiDataManager.kdTree.search(x, y);
        if (poi == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        // Remove the poi from hashmap
        apiDataManager.kdTree.remove(poi);
        // Remove the poi from kd tree (await)

        return new ResponseEntity<>(poi.mapToPOIJson(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<POIJson> addPOI(@RequestBody Map<String, String> body) {
        // Get the data from request body
        int x = Integer.parseInt(body.get("x"));
        int y = Integer.parseInt(body.get("y"));
        String[] services = body.get("service").split(",");
        MyArray<String> newServices = new MyArray<>(services);
        // Create poi based on data
        POI poi = new POI(x, y, newServices);
        // Add poi to both hashmap and kd-tree
        apiDataManager.kdTree.insert(poi.mapToPOINode());
        return new ResponseEntity<>(poi.mapToPOIJson(), HttpStatus.CREATED);
    }
}
