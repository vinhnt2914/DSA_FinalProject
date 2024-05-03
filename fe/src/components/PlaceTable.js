import React, { useState } from 'react'
import {
  Table,
  TableHeader,
  TableColumn,
  TableBody,
  TableRow,
  TableCell,
  Button,
} from '@nextui-org/react'

export default function PlaceTable({ placeProps }) {
  const fakeData = [
    {
      id: 1,
      position: '51.505, -0.09',
      service: 'Service 1',
    },
    {
      id: 2,
      position: '51.505, -0.09',
      service: 'Service 2',
    },
  ]
  const [placeData, setPlaceData] = useState([])
  return (
    <Table aria-label="Example dynamic collection table">
      <TableHeader>
        <TableColumn>POSITION</TableColumn>
        <TableColumn>SERVICE</TableColumn>
        <TableColumn className="flex justify-center items-center">
          ACTION
        </TableColumn>
      </TableHeader>
      <TableBody items={placeData} emptyContent={'No rows to display.'}>
        {(place) => (
          <TableRow key={place.id}>
            <TableCell>{place.position}</TableCell>
            <TableCell>{place.service}</TableCell>
            <TableCell>
              <Button color="primary" variant="light">
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
  )
}
