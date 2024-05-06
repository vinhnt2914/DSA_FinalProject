import React, { useEffect, useState } from 'react'
import {
  Table,
  TableHeader,
  TableColumn,
  TableBody,
  TableRow,
  TableCell,
  Button,
} from '@nextui-org/react'
import EditExistingPlace from './EditExistingPlace'
import axios from 'axios'

export default function PlaceTable({ placeProps }) {
  // !! Fake data that will be replaced with the real data
  const fakeData = [
    {
      id: 1,
      position: '51.505, -0.09',
      service: 'Service 1',
    },
    {
      id: 2,
      position: '51.505, -0.19',
      service: 'Service 2',
    },
  ]
  const [placeData, setPlaceData] = useState([])
  const [editData, setEditData] = useState({})
  // Tracking which one is being edited
  const [workingPlace, setWorkingPlace] = useState({})
  const [editPlace, setEditPlace] = useState(false)

  // Run on load
  // useEffect(() => {
  //   setPlaceData(placeProps)
  // }, [placeProps])

  /**
   * Function to set the place that is being edited
   * @param {((prevState: string) => string)|string} place: The place to be edited
   * @returns {void}
   */
  function setPlaceWorking(place) {
    setWorkingPlace(place)
  }

  function enableEditPlace(place) {
    // console.log("Current row: ", e)
    setEditData({
      x: place.position.split(',')[0],
      y: place.position.split(',')[1],
      service: place.service,
    })
    setEditPlace(true)
  }

  function disableEditPlace(place) {
    // console.log("Current row: ", e)
    console.log('[disableEditPlace] cancelling')
    setEditPlace(false)
  }

  /** TODO: @Mai: implement this function [PlaceTable]
   * using axios to send the data to the backend
   * also return the status of the request to set
   * the status of the button
   */
  function submitPlace(e) {
    throw new Error('Not implemented')
  }
  return (
    <>
      <Table aria-label="Data table">
        <TableHeader>
          <TableColumn>POSITION</TableColumn>
          <TableColumn>SERVICE</TableColumn>
          <TableColumn className="flex justify-center items-center">
            ACTION
          </TableColumn>
        </TableHeader>
        <TableBody items={placeData} emptyContent={'No rows to display.'}>
          {(place) => (
            <TableRow key={place.position}>
              <TableCell>{place.position}</TableCell>
              <TableCell>{place.service}</TableCell>
              <TableCell>
                <Button
                  color="primary"
                  variant="light"
                  onClick={() => enableEditPlace(place)}
                >
                  Edit
                </Button>
                <Button color="danger" variant="light">
                  Delete
                </Button>
              </TableCell>
            </TableRow>
          )}
        </TableBody>
      </Table>
      {editPlace && (
        <EditExistingPlace
          dataToBeEdited={editData}
          cancelAction={disableEditPlace}
        />
      )}
    </>
  )
}
