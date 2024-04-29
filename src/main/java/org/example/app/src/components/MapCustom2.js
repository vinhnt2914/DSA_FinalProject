import React, { useState, useMemo } from 'react';
import { Rectangle, Polygon, useMap } from 'react-leaflet';

const innerBounds = [
  [1200, 1200],
  [2400, 2400],
]
const outerBounds = [
  [50.505, -29.09],
  [52.505, 29.09],
]

const polygon = [
  [51.515, 120.09],
  [151.52, 12.1],
  [51.52, 0.12],
]
const purpleOptions = { color: 'purple' }

const redColor = { color: 'red' }
const whiteColor = { color: 'white' }

export default function MapCustom2() {
  const [bounds, setBounds] = useState(outerBounds)
  const map = useMap()

  const innerHandlers = useMemo(
    () => ({
      click() {
        setBounds(innerBounds)
        map.fitBounds(innerBounds)
      },
    }),
    [map],
  )

  return (
    <>
      <Rectangle
        bounds={innerBounds}
        eventHandlers={innerHandlers}
        pathOptions={redColor}
      />
      {/* <Polygon pathOptions={purpleOptions} positions={polygon} /> */}
    </>
  )
}