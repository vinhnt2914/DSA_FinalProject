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

export default function PlaceTable({ placeProps, isHiding }) {
  // !! Fake data that will be replaced with the real data
  // const fakeData = [
  //   {
  //     id: 1,
  //     position: '51.505, -0.09',
  //     service: 'Service 1',
  //   },
  //   {
  //     id: 2,
  //     position: '51.505, -0.19',
  //     service: 'Service 2',
  //   },
  // ]

  // STATE
  const [isEmpty, setIsEmpty] = useState(true)
  const [placeData, setPlaceData] = useState({})
  const [editData, setEditData] = useState({})
  // Tracking which one is being edited
  const [workingPlace, setWorkingPlace] = useState({})
  const [editPlace, setEditPlace] = useState(false)

  useEffect(() => {
    if (placeProps !== undefined && isHiding == false) {
      setIsEmpty(false)
      let service =
        placeProps.services.length == 1
          ? placeProps.services
          : placeProps.services.slice(0, -1).join(',') +
            ',' +
            placeProps.services.slice(-1)
      console.log('Service: ', service)
      setPlaceData({
        x: placeProps.x,
        y: placeProps.y,
        service: service,
      })
    }
  }, [placeProps])

  /**
   * Function to set the place that is being edited
   * @param {((prevState: string) => string)|string} place: The place to be edited
   * @returns {void}
   */
  // function setPlaceWorking(place) {
  //   setWorkingPlace(place)
  // }

  function enableEditPlace(place) {
    // console.log("Current row: ", e)
    setEditData({
      x: placeData.x,
      y: placeData.y,
      service: place.service,
    })
    setEditPlace(true)
  }

  function disableEditPlace(place) {
    // console.log("Current row: ", e)
    console.log('[disableEditPlace] cancelling')
    setEditPlace(false)
  }

  // function submitPlace(e) {
  //   throw new Error('Not implemented')
  // }

  function deletePlace(e) {
    axios
      .delete(`http://localhost:8090/api/remove/${placeData.x}/${placeData.y}`)
      .then((response) => {
        console.info('Response: ', response)
        setIsEmpty(true)
      })
      .catch((error) => {
        console.error('Error: ', error)
      })
  }

  return (
    <>
      {isEmpty == false && (
        <Table aria-label="Data table">
          <TableHeader>
            <TableColumn>POSITION</TableColumn>
            <TableColumn>SERVICE</TableColumn>
            <TableColumn className="flex justify-center items-center">
              ACTION
            </TableColumn>
          </TableHeader>
          <TableBody emptyContent={'No rows to display.'}>
            <TableRow>
              <TableCell>{placeData.x + ',' + placeData.y}</TableCell>
              <TableCell>{placeData.service}</TableCell>
              <TableCell>
                <div className='flex items-center justify-center'>
                  <Button
                    cr="primary"
                    variant="light"
                    onClick={() => enableEditPlace(placeData)}
                  >
                    Edit
                  </Button>
                  <Button color="danger" variant="light" onClick={deletePlace}>
                    Delete
                  </Button>
                </div>
              </TableCell>
            </TableRow>
          </TableBody>
        </Table>
      )}
      {editPlace && (
        <EditExistingPlace
          dataToBeEdited={editData}
          cancelAction={disableEditPlace}
        />
      )}
    </>
  )
}
