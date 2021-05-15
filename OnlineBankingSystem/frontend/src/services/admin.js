import axios from "axios";
const token = JSON.parse(localStorage.getItem("token"));

const API_URL = "http://bankingapi-lb-1422585049.us-west-2.elb.amazonaws.com/";

class UserService {
  getProspects() {
    return axios.get(API_URL + "prospects", {
      headers: {
        "Access-Control-Allow-Origin": "*",
        "Content-Type": "application/json",
        "Access-Control-Allow-Methods": "GET,PUT,POST,DELETE,PATCH,OPTIONS",
        Authorization: "Bearer " + token,
      },
    });
  }

  reject(id) {
    return axios.put(API_URL + `rejectProspect?prospectId=${id}`,{}, {
      headers: {
        "Access-Control-Allow-Origin": "*",
        "Content-Type": "application/json",
        "Access-Control-Allow-Methods": "GET,PUT,POST,DELETE,PATCH,OPTIONS",
        Authorization: "Bearer " + token,
      },
    });
  }

  approve(id) {
    return axios.post(API_URL + `addNewCustomer?prospectId=${id}`,{}, {
      headers: {
        "Access-Control-Allow-Origin": "*",
        "Content-Type": "application/json",
        "Access-Control-Allow-Methods": "GET,PUT,POST,DELETE,PATCH,OPTIONS",
        Authorization: "Bearer " + token,
      },
    }

    );
  }
}

export default new UserService();
