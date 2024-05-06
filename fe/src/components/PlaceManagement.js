import React from 'react'
import {
  Modal,
  ModalContent,
  ModalHeader,
  ModalBody,
  ModalFooter,
  Input,
  Button,
  useDisclosure,
} from '@nextui-org/react'
import PlaceTable from './PlaceTable'
import axios from 'axios'

export default function PlaceManagement({ ClassProperties }) {
  function trackX(data) {
    setData((prevData) => ({ ...prevData, x: data }))
  }
  function trackY(data) {
    setData((prevData) => ({ ...prevData, y: data }))
  }

  // Predefined notifications
  const successNotification = 'The place has been found successfully'
  const errorNotification =
    "The place you entered doesn't exist in the database. Please try again."

  // Related state for application
  const { isOpen, onOpen, onOpenChange } = useDisclosure()
  const [isAdding, setIsAdding] = React.useState(false)
  const [isSearching, setIsSearching] = React.useState(false)
  const [status, setStatus] = React.useState('')
  const [data, setData] = React.useState({})
  const [result, setResult] = React.useState()

  function searchForPlace() {
    // Call the parent function
    setIsSearching(true)
    axios
      .get(`http://localhost:8090/api/get/${data.x}/${data.y}`)
      .then((response) => {
        console.log('Response: ', response)
        setResult(response.data)
        setIsSearching(false)
        setStatus('success')
      })
      .catch((error) => {
        console.error('Error: ', error)
        setIsSearching(false)
        setStatus('error')
      })
  }

  return (
    <>
      <Button onPress={onOpen} className={ClassProperties}>
        Edit/Manage a place
      </Button>
      <Modal isOpen={isOpen} onOpenChange={onOpenChange}>
        <ModalContent>
          {(onClose) => (
            <>
              <ModalHeader className="flex flex-col gap-1">
                Edit/Manage a place
              </ModalHeader>
              <ModalBody>
                <p className="mt-[-24px] opacity-50">
                  Search and edit a specific place.
                </p>
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
                <Button
                  color="primary"
                  variant="ghost"
                  onPress={searchForPlace}
                  isLoading={isSearching}
                >
                  {isSearching ? 'Searching...' : 'Search'}
                </Button>
                <h1 className="text-lg font-bold">Search result</h1>
                <PlaceTable placeProps={result} />
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
            </>
          )}
        </ModalContent>
      </Modal>
    </>
  )
}
