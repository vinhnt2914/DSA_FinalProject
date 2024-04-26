import React, { useState } from "react";
import "./App.css";
import Map from "./components/Map";
import ServiceList from "./components/ServiceList";
import Setting from "./components/Setting";
import DebugMode from "./components/DebugMode";

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
    }



    return (
        <div className="h-dvh">
            {/* Selection */}
            <div className="flex w-86 z-[14] fixed m-8 gap-4 items-center">
                {/* <Input placeholder="Type something..." /> */}
                <Setting onFormSubmit={moveToPosition} />
                <ServiceList data={getMarkersList} />
            </div>
            {/* Locate the debug button at the bottom right */}
            <div className="absolute bottom-0 right-0 m-8 z-30">
                <DebugMode />
            </div>

            <Map state={center} />
        </div>
    );
}
