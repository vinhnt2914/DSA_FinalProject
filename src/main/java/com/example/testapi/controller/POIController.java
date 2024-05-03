package com.example.testapi.controller;

import com.example.testapi.model.POI;

import com.example.testapi.model.POIWithDistance;
import com.example.testapi.model.POIWithDistanceJSON;
import com.example.testapi.utility.APIDataManager;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/poi")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class POIController {

    @GetMapping()
    public List<POIWithDistanceJSON> getManyPOIs(){
        APIDataManager apiDataManager = APIDataManager.getInstance();
        List<POIWithDistance> poiList = apiDataManager.poiWithDistanceList;
        return poiList.stream().map(POIWithDistance::mapToJSON).toList();
    }

//    @PostMapping()
//    public List<POI> addPOIBody(@RequestBody Map<String, String> body) {
//        int x = Integer.parseInt(body.get("x"));
//        int y = Integer.parseInt(body.get("y"));
//        String service = body.get("service");
//
//        POI poi = new POI(x, y, service);
//        poiList.add(poi);
//        System.out.println("ADDED POI: " + poi);
//        return poiList;
//    }

    private static List<POI> createTestPOI() {
        List<POI> poiList = new ArrayList<>();
        poiList.add(new POI(1,2,"Coffee"));
        poiList.add(new POI(2,2,"Coffee", "School"));
        poiList.add(new POI(2,5,"Bank"));
        return poiList;
    }

}
