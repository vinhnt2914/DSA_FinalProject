import React, { useState, useMemo } from 'react';
import { Rectangle, useMap } from 'react-leaflet';

const innerBounds = [
  [1200, 1200],
  [2400, 2400],
]
const outerBounds = [
  [50.505, -29.09],
  [52.505, 29.09],
]

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
    </>
  )
}