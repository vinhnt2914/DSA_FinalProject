import React, { useState, useMemo, useEffect } from 'react';
import { Rectangle, Polygon, useMap } from 'react-leaflet';

const innerBounds = [
  [1200, 1200],
  [2400, 2400],
]
const outerBounds = [
  [50.505, -29.09],
  [52.505, 29.09],
]


const purpleOptions = { color: 'purple' }

const redColor = { color: 'red' }
const whiteColor = { color: 'white' }

export default function MapCustom2({ boundForPolygon }) {

  const defaultPolygon = [
    [500, 500],
    [200, 500],
    [200, 200],
    [500, 200]
  ];
  const [bounds, setBounds] = useState(defaultPolygon);
  const map = useMap()

  useEffect(() => {
    if (boundForPolygon.length === 0) {
      return;
    }
    console.log("Polygon bound: ", boundForPolygon);
    setBounds(boundForPolygon);
    // log
    // map.fitBounds(boundForPolygon)
    console.log("Polygon bound: ", boundForPolygon);
  }, [boundForPolygon]);

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
      {/* <Rectangle
        bounds={innerBounds}
        eventHandlers={innerHandlers}
        pathOptions={redColor}
      /> */}
      <Polygon pathOptions={purpleOptions} positions={bounds} />
    </>
  )
}