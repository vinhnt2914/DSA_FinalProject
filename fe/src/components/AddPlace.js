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

export default function AddPlace({ ClassProperties }) {
  const { isOpen, onOpen, onOpenChange } = useDisclosure()
  const [data, setData] = React.useState({
    x: '',
    y: '',
    service: '',
  })
  const [isLoaded, setIsLoaded] = React.useState(false)

  /** TODO: @Mai: implement this function
   * using axios to send the data to the backend
   * also return the status of the request to set
   * the status of the button
   */
  function addNewPlace() {
    // Call the parent function
    setIsLoaded(true)
    console.error('[addNewPlace] this function is not defined')
  }

  function trackX(data) {
    setData({ ...data, x: data })
  }

  function trackY(data) {
    setData({ ...data, y: data })
  }

  function trackService(data) {
    setData({ ...data, service: data })
  }

  return (
    <>
      <Button onPress={onOpen} className={ClassProperties}>
        Add a place
      </Button>
      <Modal isOpen={isOpen} onOpenChange={onOpenChange}>
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
                  <Input
                    type="position"
                    label="Type the name of the services"
                    onValueChange={trackService}
                  />
                </div>
                <Button
                  color="primary"
                  variant="ghost"
                  onPress={addNewPlace}
                  isLoading={isLoaded}
                >
                  Add place
                </Button>
                {/* TODO: A display for status */}
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
