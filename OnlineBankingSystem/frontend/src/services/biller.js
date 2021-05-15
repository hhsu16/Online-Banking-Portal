import axios from "axios";
const token = JSON.parse(localStorage.getItem("token"));
const user = JSON.parse(localStorage.getItem("user"));
const API_URL = "http://localhost:8080/";

class BillerService {
  viewBillers() {
    return axios.get(API_URL + `viewBillers`, {
      headers: {
        "Access-Control-Allow-Origin": "*",
        "Content-Type": "application/json",
        "Access-Control-Allow-Methods": "GET,PUT,POST,DELETE,PATCH,OPTIONS",
        Authorization: "Bearer " + token,
      },
    });
  }

  viewPayees() {
    return axios.get(API_URL + `viewPayees?userId=3`, {
        headers: {
          "Access-Control-Allow-Origin": "*",
          "Content-Type": "application/json",
          "Access-Control-Allow-Methods": "GET,PUT,POST,DELETE,PATCH,OPTIONS",
          Authorization: "Bearer " + token,
        },
      });
  }
}

export default new BillerService();