package com.example.testapi.controller;

import com.example.testapi.model.POI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/poi")
@CrossOrigin("http://localhost:3000")
public class POIController {
    private static List<POI> poiList = createTestPOI();

    @GetMapping()
    public List<POI> getManyPOIs(){
        return poiList;
    }

//    @PostMapping()
//    public ResponseEntity<POI> addPOIParam(@RequestParam("x") String x,
//                                      @RequestParam("y") String y,
//                                      @RequestParam("service") String service) {
//        int parsedX = Integer.parseInt(x);
//        int parsedY = Integer.parseInt(y);
//        POI poi = new POI(parsedX, parsedY, service);
//        poiList.add(poi);
//        return new ResponseEntity<>(poi, HttpStatus.CREATED);
//    }

    @PostMapping()
    public void addPOIBody(@RequestParam("x") String x) {
//        poiList.add(poi);
//        System.out.println("ADDED POI: " + poi);
//        return new ResponseEntity<>(poi, HttpStatus.CREATED);
        System.out.println("Got: " + x);
    }

//    @PostMapping("/addpoi")
//    public List<POI> addPOI(
//            @RequestParam(name = "x") int x,
//            @RequestParam(name = "y") int y,
//            @RequestParam(name = "service") String service) {
//        System.out.println("Received: " + x);
//        System.out.println("Received: " + y);
//        System.out.println("Received: " + service);
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
