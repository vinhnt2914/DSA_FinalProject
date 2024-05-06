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
  Table,
  TableHeader,
  TableBody,
  TableColumn,
  TableRow,
  TableCell,
} from '@nextui-org/react'
import PlaceTable from './PlaceTable'

export default function PlaceManagement({ ClassProperties }) {
  // Predefined notifications
  const successNotification = 'The place has been added successfully'
  const errorNotification =
    "There's an error while adding the place. Please try again later.\nIf the problem persists, check the console for more information."
  const pendingNotification = 'Adding the place...'

  // Related state for application
  const { isOpen, onOpen, onOpenChange } = useDisclosure()
  const [isAdding, setIsAdding] = React.useState(false)
  const [isSearching, setIsSearching] = React.useState(false)
  const [status, setStatus] = React.useState('')

  function searchForPlace() {
    // Call the parent function
    console.log('Search for place')
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
                  <Input type="position" label="Position X of the place" />
                  <Input type="position" label="Position Y of the place" />
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
                <PlaceTable />
              </ModalBody>
              <ModalFooter>
                <Button color="danger" variant="light" onPress={onClose}>
                  Close
                </Button>
              </ModalFooter>
            </>
          )}
        </ModalContent>
      </Modal>
      {status === 'success' && (
        <div className="bg-green-400 p-2">{successNotification}</div>
      )}
      {status === 'error' && (
        <div className="bg-red-400 p-2">{errorNotification}</div>
      )}
    </>
  )
}
