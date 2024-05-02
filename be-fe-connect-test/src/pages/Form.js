import axios from "axios";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { addPOI } from "../services/poiService";

export default function Form() {

    let navigate = useNavigate();

    const[x,setX] = useState("");
    const[y,setY] = useState("");
    const[service,setService] = useState([]);

    // const dummyData = {
    //     x: "1",
    //     y: "2",
    //     service: "Hello"
    // }

    const handleSubmit = async (e) => {
        // e.preventDefault();
        const poi = {x,y,service};
        console.log(poi);

        // const response = await axios.post("http://localhost:8080/api/poi", dummyData).then(() => {
        //     console.log(response)
        //     navigate("/")
        // }).catch(err => console.log(err))


        // addPOI(poi).then((repsonse) => {
        //     console.log("Reponse data: " + repsonse.data);
        //     navigate("/")
        // })

        const reponse = addPOI(poi);
        console.log("Reponse: ");
        console.log(reponse);
    }

    const handleX = e => {
        setX(e.target.value)
    }

    const handleY = e => {
        setY(e.target.value)
    }

    const handleService = e => {
        setService(e.target.value)
    }

    return(
        <div className="container">
            <div className="py-4">
                <form >
                    <div className="mb-3">
                        <label className="form-label">Enter x coordinate</label>
                        <input 
                            type="text" 
                            className="form-control" 
                            name="x" 
                            value={x} 
                            onChange={handleX}/>
                    </div>
                    <div className="mb-3">
                        <label className="form-label">Enter y coordinate</label>
                        <input 
                            type="text" 
                            className="form-control" 
                            name="y" 
                            value={y} 
                            onChange={handleY}/>
                    </div>
                    <div className="mb-3">
                        <label className="form-label">Enter service</label>
                        <input 
                            type="text" 
                            className="form-control" 
                            name="service" 
                            value={service} 
                            onChange={handleService}/>
                    </div>
                    <button type="submit" className="btn btn-primary" onClick={handleSubmit}>Submit</button>
                </form>
            </div>
        </div>  
    );
}