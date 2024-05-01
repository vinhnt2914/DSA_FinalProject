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
import React, { useState } from 'react'
// Icon
import { CiSettings } from 'react-icons/ci'

import '../App.css'

export default function Setting({ onFormSubmit, onBoundSubmit }) {
  const { isOpen, onOpen, onOpenChange } = useDisclosure()
  const [location, setLocation] = useState('') // Default location
  const [topLeft, setTopLeft] = useState('') // Default top-left
  const [widthAndHeight, setWidthAndHeight] = useState('') // Default width and height


  // Fn to change the top-left position
  function topLeftChange(value) {
    if (value.includes(',')) {
      console.log("Top-left: ", value)
      setTopLeft(value)
    }
  }

  // Function to set the width and height
  function widthAndHeightChange(value) {
    if (value.includes(',')) {
      console.log("Width and height: ", value)
      setWidthAndHeight(value)
    }
  }



  // Function to save the setting
  function onSaveSetting() {
    // Get the value
    let upperLeft = topLeft.split(',');
    let widthHeight = widthAndHeight.split(',');

    // calculate the center
    let lng = (parseFloat(upperLeft[0]) + (parseFloat(upperLeft[0]) + parseFloat(widthHeight[0]))) / 2;
    let lat = (parseFloat(upperLeft[1]) + (parseFloat(upperLeft[1]) - parseFloat(widthHeight[1]))) / 2;
    console.log("Center: ", lng, lat);

    // Call the parent function
    onFormSubmit(lng, lat)
    onBoundSubmit(topLeft, widthHeight[0], widthHeight[1])
    // Close the modal
    onOpenChange()
  }

  return (
    <>
      <Button onPress={onOpen} size="sm" isIconOnly={true}>
        <CiSettings className="size-10" />
      </Button>
      <Modal isOpen={isOpen} onOpenChange={onOpenChange}>
        <ModalContent>
          {(onClose) => (
            <>
              <ModalHeader className="flex flex-row gap-1 items-center">
                <CiSettings /> Settings
              </ModalHeader>
              <ModalBody>
                <h3 className=''>Note: All the data must include the colon for separating longtitude from latitude. Please type in x and y.</h3>
                <h2>Enter the top-left coordinate of the bound</h2>
                <Input
                  placeholder="Coordinate of top-left corner"
                  onValueChange={topLeftChange}
                  required={true}
                  defaultValue={topLeft}
                />
                <h2>Type in width and height</h2>
                <Input
                  placeholder="Width and height"
                  onValueChange={widthAndHeightChange}
                  defaultValue={widthAndHeight}
                />
              </ModalBody>
              <ModalFooter>
                <Button color="danger" variant="light" onPress={onClose}>
                  Close
                </Button>
                <Button color="primary" onPress={onSaveSetting}>
                  Accept
                </Button>
              </ModalFooter>
            </>
          )}
        </ModalContent>
      </Modal>
    </>
  )
}
