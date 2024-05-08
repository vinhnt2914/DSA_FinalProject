import {
  Button,
  Input,
  Modal,
  ModalBody,
  ModalContent,
  ModalFooter,
  ModalHeader,
  useDisclosure,
} from '@nextui-org/react'
import React from 'react'
import axios from 'axios'
import Select from 'react-select'
import { serviceData } from '../data/data'

export default function AddPlace({ ClassProperties }) {
  const { isOpen, onOpen, onOpenChange } = useDisclosure()
  const [data, setData] = React.useState({
    x: '',
    y: '',
    service: [],
  })
  const [isLoading, setIsLoading] = React.useState(false)
  const [status, setStatus] = React.useState('')

  // Status: success, error

  const successNotification = 'The place has been added successfully'
  const errorNotification =
    "There's an error while adding the place. Please try again later.\nIf the problem persists, check the console for more information."
  const pendingNotification = 'Adding the place...'

  function addNewPlace() {
    setIsLoading(true)
    setStatus('pending')
    // convert array in service to string
    let serviceString = data.service.join(',')
    console.log('Service string: ', serviceString)
    let finalData = { x: data.x, y: data.y, service: serviceString }
    axios.post('http://localhost:8090/api/add', finalData).then(
      (response) => {
        console.log('Response: ', response)
        setStatus('success')
        setIsLoading(false)
      },
      (error) => {
        console.error('Error: ', error)
        setStatus('error')
        setIsLoading(false)
      }
    )
  }

  function trackX(data) {
    setData((prevData) => ({ ...prevData, x: data }))
  }

  function trackY(data) {
    setData((prevData) => ({ ...prevData, y: data }))
  }

  function trackService(data) {
    setData((prevData) => ({ ...prevData, service: data }))
  }

  return (
    <>
      <Button onPress={onOpen} className={ClassProperties}>
        Add a place
      </Button>
      <Modal isOpen={isOpen} onOpenChange={onOpenChange} onClose={() => {
        setStatus('')
      }}>
        <ModalContent>
          {(onClose) => (
            <>
              <ModalHeader className="flex flex-col gap-1">
                Add a place
              </ModalHeader>
              <ModalBody>
                <div className="flex w-full flex-wrap md:flex-nowrap gap-4">
                  <Input
                    type="position"
                    label="Position X of the place"
                    onValueChange={trackX}
                  />
                  <Input
                    type="position"
                    label="Position Y of the place"
                    onValueChange={trackY}
                  />
                </div>
                <div>
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
                      trackService(selectedValues)
                    }}
                  />
                </div>
                <Button
                  color="primary"
                  variant="ghost"
                  onPress={addNewPlace}
                  isLoading={isLoading}
                >
                  {isLoading ? 'Adding...' : 'Add a place'}
                </Button>
                {/* TODO: A display for status */}
              </ModalBody>
              <ModalFooter>
                <Button color="danger" variant="light" onPress={onClose}>
                  Close
                </Button>
              </ModalFooter>
              {status === 'success' && (
                <div className="bg-green-400 p-2">{successNotification}</div>
              )}
              {status === 'error' && (
                <div className="bg-red-400 p-2">{errorNotification}</div>
              )}
              {/* {isLoading && (
                <div className="bg-yellow-400 p-2">
                  <></>{pendingNotification}
                </div>
              )} */}
            </>
          )}
        </ModalContent>
      </Modal>
      {/* <NotificationDiag notifState={'success'} content={'Hello, world!'} /> */}
    </>
  )
}
