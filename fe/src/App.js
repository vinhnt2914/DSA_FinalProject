import React, { useState } from 'react'
import './App.css'
import Map from './components/Map'
import ServiceList from './components/ServiceList'
import Setting from './components/Setting'
import axios from 'axios'

// marker data structure example
// 	{
//      lat: 51.505,
//      lng: -0.09,
//      service: "Service 1",
//      distanceToMiddle: 0.1
//  }

export default function App() {
  // Map parameters
  const DEFAULT_ZOOM = 0.4
  const [center, setCenter] = useState([0, 0])
  const [data, setData] = useState([])
  const [searchStatus, setSearchStatus] = useState(false)
  const [PointsOfBound, setPointsOfBound] = useState([])
  const [boundingSize, setBoundingSize] = useState(undefined)
  // Other params

  // move the map to the given position (center)
  function moveToPosition(lng, lat) {
    setCenter({ lng: lng, lat: lat, zoom: DEFAULT_ZOOM })
    console.log('Move to: ', lng, lat)
  }

  function fetchServices(service) {
    const dataToSend = {
      x: `${center.lng}`,
      y: `${center.lat}`,
      service: service,
      boundingSize: boundingSize,
    }
    console.log('[debug_fetchService] data sent: ', dataToSend)
    axios
      .post('http://localhost:8090/api', dataToSend)
      .then((response) => {
        console.log('[fetchService] response: ', response)
        processData(response.data)
      })
      .catch((error) => {
        console.log(error)
      })
  }

  function processData(data) {
    let markers = []
    // Loop through the data and create markers
    for (let i = 0; i < data.length; i++) {
      let services =
        data[i].services.length == 1
          ? data[i].services
          : data[i].services.slice(0, -1).join(',') +
            ',' +
            data[i].services.slice(-1)
      markers.push({
        x: data[i].x,
        y: data[i].y,
        service: services,
        distanceToMiddle: data[i].distance,
      })
    }
    console.log('[debug_processData] Markers length: ', markers.length)
    setData(markers)
  }

  function calculatePointsOfBound(boundLength, long, lati) {
    // define the latitudes and longtitude of the bound
    console.log('This changed')
    // This is for getting services list
    setBoundingSize(boundLength)
    let lng = parseFloat(long)
    let lat = parseFloat(lati)

    // calculate the bound based on the center and the bound length
    let topLeft = [lng - boundLength / 2, lat + boundLength / 2]
    let topRight = [lng + boundLength / 2, lat + boundLength / 2]
    let bottomLeft = [lng - boundLength / 2, lat - boundLength / 2]
    let bottomRight = [lng + boundLength / 2, lat - boundLength / 2]
    setSearchStatus(true)
    setPointsOfBound([topLeft, bottomLeft, bottomRight, topRight])
  }

  return (
    <div className="h-dvh">
      {/* Selection */}
      <div className="flex w-full z-[14] fixed m-8 gap-4 items-center">
        {/* <Input placeholder="Type something..." /> */}
        <Setting
          onFormSubmit={moveToPosition}
          onBoundSubmit={calculatePointsOfBound}
        />
        <ServiceList getData={fetchServices} isEnable={searchStatus} />
      </div>
      <Map
        state={center}
        PointsOfBound={PointsOfBound}
        isSearched={searchStatus}
        markers={data}
      />
    </div>
  )
}
