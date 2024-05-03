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
    public POIJson getPOI(@PathVariable int x,
                          @PathVariable int y){
        POI poi = APIDataManager.getInstance().poiHashMap.find(x,y);
        return poi.mapToPOIJson();
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
        APIDataManager apiDataManager = APIDataManager.getInstance();
        // Get the POIWithDistance from kdTree KNN search
        List<POIWithDistance> poiList = APIDataManager.getInstance().kdTree.kNearestNeighborsWithMap(x,y,service).toArrayList();
        // Convert to JSON format and return them
        return new ResponseEntity<>(poiList.stream().map(POIWithDistance::mapToJSON).toList(), HttpStatus.CREATED);
    }

    @PutMapping("/update/{x}/{y}")
    public ResponseEntity<POIJson> updatePOI(@PathVariable int x,
                                             @PathVariable int y,
                                             @RequestBody Map<String, String> body) {
        POI poi = APIDataManager.getInstance().poiHashMap.find(x,y);

        System.out.println("THIS IS SERVICE: " + body.get("service"));

        String[] services = body.get("service").split(",");
        System.out.println("Services: " + Arrays.toString(services));
        MyArray<String> newServices = new MyArray<>(services);
        poi.setServices(newServices);
        return new ResponseEntity<>(poi.mapToPOIJson(), HttpStatus.CREATED);
    }




}
