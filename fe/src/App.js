import React, { useState } from 'react'
import './App.css'
import Map from './components/Map'
import ServiceList from './components/ServiceList'
import Setting from './components/Setting'

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
  const [PointsOfBound, setPointsOfBound] = useState([])
  // Other params

  // move the map to the given position (center)
  function moveToPosition(lng, lat) {
    setCenter({ lng: lng, lat: lat, zoom: DEFAULT_ZOOM })
    console.log('Move to: ', lng, lat)
  }
  
  // function fetchServices() {
  //   throw new Error('Not implemented')
  // }

  
  /* TODO: @Mai: implement the fetchServices function
   * which is getting the service type and send it
   * to server
   */
  function getMarkersList(markers) {
    setData(markers)
    // for now just console log the data
    console.log('Markers: ', markers)
  }

  function calculatePointsOfBound(boundLength, long, lati) {
    // let topLeft = topLeftArr.split(',').map((item) => parseFloat(item))

    // let widthHeightArr = [parseFloat(width), parseFloat(height)]
    // define the latitudes and longtitude of the bound
    console.log('This changed')
    let lng = parseFloat(long)
    let lat = parseFloat(lati)

    // calculate the bound based on the center and the bound length
    let topLeft = [lng - boundLength / 2, lat + boundLength / 2]
    let topRight = [lng + boundLength / 2, lat + boundLength / 2]
    let bottomLeft = [lng - boundLength / 2, lat - boundLength / 2]
    let bottomRight = [lng + boundLength / 2, lat - boundLength / 2]
    // let topRight = [lng, lat + widthHeightArr[1]]
    // let bottomLeft = [lng - widthHeightArr[0], lat]
    // let bottomRight = [lng - widthHeightArr[0], lat + widthHeightArr[1]]
    // return [topLeftArr, topRight, bottomLeft, bottomRight]
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
        <ServiceList data={getMarkersList}/>
      </div>
      <Map state={center} PointsOfBound={PointsOfBound} />
    </div>
  )
}
