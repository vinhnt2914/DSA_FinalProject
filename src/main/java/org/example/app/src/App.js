import React, { useState } from "react";
import "./App.css";
import Map from "./components/Map";
import ServiceList from "./components/ServiceList";
import Setting from "./components/Setting";
import DebugMode from "./components/DebugMode";
import InfoDiag from "./components/InfoDiag";

// marker data structure example
// 	{ 
//      lat: 51.505, 
//      lng: -0.09, 
//      service: "Service 1",
//      distanceToMiddle: 0.1 
//  }

export default function App() {
    // Map parameters
    const DEFAULT_ZOOM = 0.4;
    const [center, setCenter] = useState([0, 0]);
    const [data, setData] = useState([]);
    // Other params

    // Main app function
    function moveToPosition(lat, lng) {
        setCenter({ lat: lat, lng: lng, zoom: DEFAULT_ZOOM });
        console.log("Move to: ", lat, lng);
    }
    // Initialize services list
    function fetchServices() {
        throw new Error("Not implemented");
    }

    function getMarkersList(markers) {
        setData(markers);
        // for now just console log the data
        console.log("Markers: ", markers);
    }



    return (
        <div className="h-dvh">
            {/* Selection */}
            <div className="flex w-86 z-[14] fixed m-8 gap-4 items-center">
                {/* <Input placeholder="Type something..." /> */}
                <Setting onFormSubmit={moveToPosition} />
                <ServiceList data={getMarkersList} />
            </div>
            

            <Map state={center} />
        </div>
    );
}
