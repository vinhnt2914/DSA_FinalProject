import {
  Button,
  Popover,
  PopoverContent,
  PopoverTrigger,
} from '@nextui-org/react'
import React, { useEffect } from 'react'
import { MdOutlineBugReport } from 'react-icons/md'

/**
 * Renders the debug popover providing useful information about the
 * current positions and relevant parameters.
 * @param {Object} setOfData - The props object. Consists of latitude, longitude, number of markers, number of services, rectangle bound, and zoom level.
 * @returns {JSX.Element} The DebugMode component.
 */
export default function DebugMode({ setOfData }) {
  const content = (
    <PopoverContent>
      <div className="px-1 py-2">
        <div className="text-medium font-bold ">
          <MdOutlineBugReport /> Debug info
        </div>
        <div className="text-small">[CENTER] Latitude: {setOfData.lat}</div>
        <div className="text-small">[CENTER] Longtitude: {setOfData.lng}</div>
        {/* Count active markers */}
        <div className="text-small">
          [MARKERS] Active markers:{' '}
          {setOfData.numOfMarkers != null ? setOfData.numOfMarkers : 0}
        </div>
        {/* Count active services */}
        {/* <div className="text-small">[SERVICES] Number of services: {setOfData.activeNum != null ? setOfData.activeNum : 0}</div> */}
        {/* The rectangle bound details */}
        {/* <div className="text-small">[BOUND] Rectangle bound: {setOfData.rectBound}</div> */}
        {/* The current zoom level */}
        <div className="text-small">
          [ZOOM] Configured zoom: {setOfData.zoom}
        </div>
      </div>
    </PopoverContent>
  )
  // Placement of the popover
  const placements = ['top-end']

  return (
    <div className="flex flex-wrap md:inline-grid md:grid-cols-1 gap-4">
      {placements.map((placement) => (
        <Popover key={placement} placement={placement} color="secondary">
          <PopoverTrigger>
            <Button
              color="secondary"
              variant="shadow"
              isIconOnly={true}
              className="capitalize"
            >
              <MdOutlineBugReport />
            </Button>
          </PopoverTrigger>
          {content}
        </Popover>
      ))}
    </div>
  )
}
