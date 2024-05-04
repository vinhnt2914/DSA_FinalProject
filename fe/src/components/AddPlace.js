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

export default function AddPlace({ ClassProperties }) {
    const { isOpen, onOpen, onOpenChange } = useDisclosure()
    const [isAdding, setIsAdding] = React.useState(false)

    function addNewPlace() {
        // Call the parent function
        console.log('[addNewPlace] this needs to be implemented')
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
                                    <Input type="position" label="Position X of the place" />
                                    <Input type="position" label="Position Y of the place" />
                                </div>
                                <div>
                                    <Input type="position" label="Type the name of the services" />
                                </div>
                                <Button
                                    color="primary"
                                    variant="ghost"
                                    onPress={addNewPlace}
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
