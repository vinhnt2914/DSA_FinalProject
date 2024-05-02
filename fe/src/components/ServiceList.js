import React, { useState } from 'react'
// import { Autocomplete, AutocompleteItem, Button } from "@nextui-org/react";
// import { Combobox } from '@headlessui/react'
import Select from 'react-select'
import makeAnimated from 'react-select/animated'

// DATA
import { serviceData } from '../data/data'
// import { divIcon } from "leaflet";

// Animation
const animatedComponents = makeAnimated()

export default function ServiceList({ data: getData }) {
  // Initialize the state
  const [services, setServices] = useState('')

  function handleChange(selection) {
    // setServices(selectedOption)
    console.log(selection)
  }

  /**
   * Fetches data.
   * @throws {Error} - The function is not implemented.
   */
  function fetchData() {
    // Temporary implementation using
    // mock data
    // TODO: Fetch using backend API
    const sampleData = [
      [0, 0],
      [1, 1],
      [2, 2],
    ]
    getData(sampleData)
  }

  return (
    // <div className="flex w-full flex-wrap md:flex-nowrap gap-4 items-center">
    //     <Autocomplete
    //         label="Select or type service name"
    //         className="max-w-xs"
    //     >
    //         {serviceData.map((service) => (
    //             <AutocompleteItem key={service.value} value={service.value}>
    //                 {service.label}
    //             </AutocompleteItem>
    //         ))}
    //     </Autocomplete>
    //     <Button isIconOnly={true} onClick={fetchData}><CiSearch /></Button>
    // </div>
    <div className="w-80 ">
      <Select
        //   defaultValue={}
        isMulti
        components={animatedComponents}
        name="colors"
        placeholder="Search for services..."
        options={serviceData}
        className="basic-multi-select"
        classNamePrefix="select"
        onChange={handleChange}
      />
    </div>
  )
}
