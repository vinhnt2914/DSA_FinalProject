import React, {useState} from "react";
import {Button, Input} from "@nextui-org/react";

export default function EditExistingPlace({dataToBeEdited, returnData, cancelAction}) {
    const [bufferedData, setBufferedData] = useState(dataToBeEdited);
    const [inputData, setInputData] = useState({
        x: "",
        y: "",
        service: ""
    });

    // Setter for input fields
    function setX(value) {
        console.log("Setting X: ", value)
        setInputData({...inputData, x: value});
    }

    function setY(value) {
        setInputData({...inputData, y: value});
    }

    function setService(value) {
        setInputData({...inputData, service: value});
    }

    // Function to update the data
    function composeNewData(place) {
        const dataToUpdated = {
            x: place.x,
            y: place.y,
            service: place.service
        }
        setBufferedData(place);
        returnData(dataToUpdated);
    }

    return (
        <>
        <div className="flex w-full flex-wrap md:flex-nowrap gap-4">
            <Input type="place" label="new X" defaultValue={dataToBeEdited.x} onValueChange={setX}/>
            <Input type="place" label="new Y" defaultValue={dataToBeEdited.y} onValueChange={setY}/>
            <Input type="place" label="New service" defaultValue={dataToBeEdited.service} onValueChange={setService}/>
        </div>
        <Button type={"submit"} color="primary" variant="ghost" onPress={() => composeNewData(inputData)}>
            Submit
        </Button>
        <Button onClick={cancelAction}>
            Cancel
        </Button>
        </>
    );
}
