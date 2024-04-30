import { CiSearch } from "react-icons/ci";
import React, { useState } from "react";
import { Autocomplete, AutocompleteItem, Button } from "@nextui-org/react";
import { serviceData } from "../data/data";

export default function ServiceList({ data: getData }) {
    // Initialize the state
    const [services, setServices] = useState("");

    /**
    * Fetches data.
    * @throws {Error} When the function is not implemented.
    */
    function fetchData() {
        
        // Temporary implementation using 
        // mock data
        // TODO: Fetch using backend API
        const sampleData = [[0, 0], [1, 1], [2, 2]];
        getData(sampleData);
    }

    return (
        <div className="flex w-full flex-wrap md:flex-nowrap gap-4 items-center">
            <Autocomplete
                label="Select or type service name"
                className="max-w-xs"
            >
                {serviceData.map((service) => (
                    <AutocompleteItem key={service.value} value={service.value}>
                        {service.label}
                    </AutocompleteItem>
                ))}
            </Autocomplete>
            <Button isIconOnly={true} onClick={fetchData}><CiSearch /></Button>
        </div>
    );
}