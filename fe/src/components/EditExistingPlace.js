import React, { useState } from 'react'
import { Button, Input, Chip } from '@nextui-org/react'
import Select from 'react-select'
import { serviceData } from '../data/data'
import axios from 'axios'

export default function EditExistingPlace({
  dataToBeEdited,
  returnData,
  cancelAction,
}) {
  const [bufferedData, setBufferedData] = useState(dataToBeEdited)
  const [inputData, setInputData] = useState({
    x: '',
    y: '',
    service: '',
  })

  // Setter for input fields
  function setX(value) {
    console.log('Setting X: ', value)
    setInputData({ ...inputData, x: value })
  }

  function setY(value) {
    setInputData({ ...inputData, y: value })
  }

  function setService(value) {
    setInputData({ ...inputData, service: value })
  }

  // Function to update the data
  function composeNewData(place) {
    const dataToUpdated = {
      services: place.service,
    }
    // setBufferedData(place)
    // returnData(dataToUpdated)
    console.table(dataToUpdated)
    axios
      .put(
        `http://localhost:8090/api/update/${dataToBeEdited.x}/${dataToBeEdited.y}`,
        dataToUpdated
      )
      .then((response) => {
        console.info('Response: ', response)
        // returnData(dataToUpdated)
      })
      .catch((error) => {
        console.error('Error: ', error)
      })
  }

  return (
    <>
      <div className="flex w-full flex-wrap md:flex-nowrap gap-4">
        <Input
          type="place"
          label="X"
          defaultValue={dataToBeEdited.x}
          onValueChange={setX}
          isDisabled={true}
        />
        <Input
          type="place"
          label="Y"
          defaultValue={dataToBeEdited.y}
          onValueChange={setY}
          isDisabled={true}
        />
        {/* <Input
          type="place"
          label="service"
          defaultValue={dataToBeEdited.service}
          onValueChange={setService}
        />
        <Chip color="warning" variant="dot">
          Hello
        </Chip> */}
      </div>
      <Select
        // defaultValue={}
        placeholder="Select services..."
        isMulti
        name="colors"
        options={serviceData}
        className="basic-multi-select"
        classNamePrefix="select"
        onChange={(value) => {
          console.log('Selected: ', value)
          // get only the values
          let selectedValues = value.map((item) => item.value)
          console.log('Selected values: ', selectedValues)
          setService(selectedValues)
        }}
      />
      <Button
        type={'submit'}
        color="primary"
        variant="ghost"
        onPress={() => composeNewData(inputData)}
      >
        Submit
      </Button>
      <Button onClick={cancelAction}>Cancel</Button>
    </>
  )
}
