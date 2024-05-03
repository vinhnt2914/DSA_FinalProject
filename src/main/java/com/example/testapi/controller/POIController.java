package com.example.testapi.controller;

import com.example.testapi.dto.POIJson;
import com.example.testapi.model.Array.MyArray;
import com.example.testapi.model.POI;
import com.example.testapi.model.POIWithDistance;
import com.example.testapi.dto.POIWithDistanceJson;
import com.example.testapi.utility.APIDataManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/poi")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class POIController {

    @GetMapping("/get/{x}/{y}")
    public ResponseEntity<POIJson> getPOI(@PathVariable int x,
                          @PathVariable int y){
        POI poi = APIDataManager.getInstance().poiHashMap.find(x,y);

        if (poi == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(poi.mapToPOIJson());
    }

    @GetMapping()
    public List<POIWithDistanceJson> getManyPOIs(){
        APIDataManager apiDataManager = APIDataManager.getInstance();
        List<POIWithDistance> poiList = apiDataManager.poiWithDistanceList;
        return poiList.stream().map(POIWithDistance::mapToJSON).toList();
    }

    @PostMapping()
    public ResponseEntity<List<POIWithDistanceJson>> searchPOIs(@RequestBody Map<String, String> body) {
        // Get the params from request body
        int x = Integer.parseInt(body.get("x"));
        int y = Integer.parseInt(body.get("y"));
        String service = body.get("service");

        // Get the POIWithDistance from kdTree KNN search
        List<POIWithDistance> poiList = APIDataManager.getInstance().kdTree.kNearestNeighborsWithMap(x,y,service).toArrayList();
        // Convert to JSON format and return them
        return new ResponseEntity<>(poiList.stream().map(POIWithDistance::mapToJSON).toList(), HttpStatus.CREATED);
    }

    @PutMapping("/update/{x}/{y}")
    public ResponseEntity<POIJson> updatePOI(@PathVariable int x,
                             @PathVariable int y,
                             @RequestBody Map<String, String[]> body) {
        POI poi = APIDataManager.getInstance().poiHashMap.find(x,y);

        if (poi == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        MyArray<String> newServices = new MyArray<>(body.get("services"));
        poi.setServices(newServices);
        return new ResponseEntity<>(poi.mapToPOIJson(), HttpStatus.CREATED);
    }

    @DeleteMapping("/remove/{x}/{y}")
    public ResponseEntity<POIJson> removePOI(@PathVariable int x,
                                             @PathVariable int y) {
        APIDataManager apiDataManager = APIDataManager.getInstance();
        POI poi = apiDataManager.poiHashMap.find(x, y);
        if (poi == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        apiDataManager.poiHashMap.remove(poi);
        return new ResponseEntity<>(poi.mapToPOIJson(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<POIJson> addPOI(@RequestBody Map<String, String> body) {
        int x = Integer.parseInt(body.get("x"));
        int y = Integer.parseInt(body.get("y"));
        String[] services = body.get("service").split(",");
        MyArray<String> newServices = new MyArray<>(services);
        POI poi = new POI(x, y, newServices);
        APIDataManager.getInstance().poiHashMap.put(poi);
        return new ResponseEntity<>(poi.mapToPOIJson(), HttpStatus.CREATED);
    }

}
