import React, { useState } from 'react'
import { Autocomplete, AutocompleteItem, Button } from '@nextui-org/react'
import { CiSearch } from 'react-icons/ci'
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
    /** TODO: @Mai: implement this function
     * using axios to send the data to the backend
     * also return the status of the request to set
     * the status of the button
     * 
     * PS: console.log the data for now
     */
    const sampleData = [
      [0, 0],
      [1, 1],
      [2, 2],
    ]
    getData(sampleData)
  }

  return (
    <div className="flex w-full flex-wrap md:flex-nowrap gap-4 items-center">
      <Autocomplete label="Select or type service name" className="max-w-xs">
        {serviceData.map((service) => (
          <AutocompleteItem key={service.value} value={service.value}>
            {service.label}
          </AutocompleteItem>
        ))}
      </Autocomplete>
      <Button isIconOnly={true} onClick={fetchData}>
        <CiSearch />
      </Button>
    </div>
    // old code
    // <div className="flex gap-2">
    //   <Select
    //     isMulti
    //     components={animatedComponents}
    //     name="colors"
    //     placeholder="Search for services..."
    //     options={serviceData}
    //     className="basic-multi-select"
    //     classNamePrefix="select"
    //     onChange={handleChange}
    //   />
    //   <Button isIconOnly={true} onClick={fetchData}><CiSearch /></Button>
    // </div>
  )
}
