import React, { useState } from "react";
import { Modal, ModalContent, ModalHeader, ModalBody, ModalFooter, Button, useDisclosure, Input } from "@nextui-org/react";
// Icon
import { CiSettings } from "react-icons/ci";

import "../App.css";

export default function Setting({ onFormSubmit }) {
    const { isOpen, onOpen, onOpenChange } = useDisclosure();
    const [location, setLocation] = useState("");   // Default location

    function inputChange(value) {
        setLocation(value);
    }

    // Function to save the setting
    function onSaveSetting() {
        // Get the value of the input
        const value = location;
        // Split the value into two parts
        // return if there is no comma
        if (!value.includes(",")) {
            console.log("[Setting] Invalid value");
            return;
        }
        const [lat, lng] = value.split(",");
        // Call the parent function
        onFormSubmit(lat, lng);
        // Close the modal
        onOpenChange();
    }

    return (
        <>
            <Button onPress={onOpen} size="sm" isIconOnly={true}><CiSettings className="size-10" /></Button>
            <Modal isOpen={isOpen} onOpenChange={onOpenChange} >
                <ModalContent>
                    {(onClose) => (
                        <>
                            <ModalHeader className="flex flex-row gap-1 items-center"><CiSettings /> Settings</ModalHeader>
                            <ModalBody>
                                <h2>Enter the longtitude and altitude for center</h2>
                                <Input placeholder="Type in longtitude and altitude (separated by colon)" onValueChange={inputChange} />
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
    );
}
