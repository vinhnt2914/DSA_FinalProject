import React, {useEffect, useState} from "react";
import axios from "axios";
import { Link } from "react-router-dom";
import Form from "./Form";
import { getAllPOIs } from "../services/poiService";

export default function Home() {
    const [POIs, setPOIs] = useState([]);

    useEffect(() => {
        getAllPOIs().then(response => {
            setPOIs(response.data);
        }).catch(error => console.log(error))
    }, [])

    // const loadPOIs = async () => {
    //     const result = await axios.get("http://localhost:8080/pois")
    //     console.log(result.data);
    //     setPOIs(result.data);
    // }

    return (
        <div className="container">
            <div className="py-4">
                <table className="table border shadow">
                <thead>
                    <tr>
                    <th scope="col">x</th>
                    <th scope="col">y</th>
                    <th scope="col">services</th>
                    </tr>
                </thead>
                <tbody>
                    {POIs.map((poi) => (
                    <tr>
                        <td>{poi.x}</td>
                        <td>{poi.y}</td>
                        <td>{poi.services}</td>
                    </tr>
                    ))}
                </tbody>
                </table>
            </div>

            <Link class="btn btn-primary" type="button" to="/form">Button</Link>
        </div>
    );
}