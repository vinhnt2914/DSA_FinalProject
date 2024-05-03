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
  const [center, setCenter] = useState('') // Default center
  const [boundLength, setBoundLength] = useState('') // Default width and height


  // Fn to change the top-left position
  function centerChange(value) {
    if (value.includes(',')) {
      console.info("[trackingInput] Center: ", value)
      setCenter(value)
    }
  }

  // Function to set the width and height
  function boundLengthChange(value) {
      setBoundLength(value)
    }



  // Function to save the setting
  function onSaveSetting() {
    // Get the value
    // let upperLeft = topLeft.split(',');
    // let widthHeight = widthAndHeight.split(',');

    // Old code - refer to the logic below this chunk
    // calculate the center
    // let lng = (parseFloat(upperLeft[0]) + (parseFloat(upperLeft[0]) + parseFloat(widthHeight[0]))) / 2;
    // let lat = (parseFloat(upperLeft[1]) + (parseFloat(upperLeft[1]) - parseFloat(widthHeight[1]))) / 2;
    // console.log("Center: ", lng, lat);

    // New code
    let centered = center.split(',');

    let lng = parseFloat(centered[0]);
    let lat = parseFloat(centered[1]);
    console.log("[onsavesetting] Center: ", lng, lat);

    // Call the parent function
    onFormSubmit(lng, lat)
    onBoundSubmit(boundLength, lng, lat)
    // onBoundSubmit(topLeft, widthHeight[0], widthHeight[1])
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
                <CiSettings />
                Settings
              </ModalHeader>
              <ModalBody>
                {/* <h3 className=''>Note: All the data must include the colon for separating longtitude from latitude. Please type in x and y.</h3> */}
                {/* <h2>Enter the top-left coordinate of the bound (x and y respectively, separated by colon).</h2> */}
                <h2>Enter the center coordinate of the bounding (x and y respectively, separated by colon).</h2>
                <Input
                  placeholder="Coordinate of the center"
                  onValueChange={centerChange}
                  required={true}
                  defaultValue={center}
                />
                <h2>Type the length of the bounding (enter number only).</h2>
                <Input
                  placeholder="Length of the bounding box"
                  onValueChange={boundLengthChange}
                  defaultValue={boundLength}
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
