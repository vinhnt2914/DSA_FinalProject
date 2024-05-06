import axios from "axios"

const REST_API_BASE_URL = "http://localhost:8080/api/poi"

export const getAllPOIs = () => axios.get(REST_API_BASE_URL);

export const addPOI = async (poi) => {
    try {
        const response = await axios.post(REST_API_BASE_URL, poi);
        return response.data;
      } catch (error) {
        throw error;
      }
}