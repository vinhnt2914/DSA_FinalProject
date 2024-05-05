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
  const { isOpen, onOpen, onOpenChange } = useDisclosure()
  const [isAdding, setIsAdding] = React.useState(false)

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
                >
                  Search
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
    </>
  )
}
