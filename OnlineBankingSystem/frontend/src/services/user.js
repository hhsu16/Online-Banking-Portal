import axios from "axios";
const token = JSON.parse(localStorage.getItem("token"));
const user = JSON.parse(localStorage.getItem("user"));
const API_URL = "http://bankingapi-lb-1422585049.us-west-2.elb.amazonaws.com/";

class UserService {
  viewAccounts() {
    return axios.get(API_URL + `viewAccounts?userId=${user.userId}`, {
      headers: {
        "Access-Control-Allow-Origin": "*",
        "Content-Type": "application/json",
        "Access-Control-Allow-Methods": "GET,PUT,POST,DELETE,PATCH,OPTIONS",
        Authorization: "Bearer " + token,
      },
    });
  }

  viewTransaction() {
    console.log(user.userId);

    return axios.get("http://localhost:8080/" + `userTransactions?userId=${user.userId}`, {
      headers: {
        "Access-Control-Allow-Origin": "*",
        "Content-Type": "application/json",
        "Access-Control-Allow-Methods": "GET,PUT,POST,DELETE,PATCH,OPTIONS",
        Authorization: "Bearer " + token,
      },
    });
  }

  deleteUser() {
    console.log(user.userId);
    return axios.post(API_URL + `deleteAccounts?userId=${user.userId}`, {}, {
      headers: {
        "Access-Control-Allow-Origin": "*",
        "Content-Type": "application/json",
        "Access-Control-Allow-Methods": "GET,PUT,POST,DELETE,PATCH,OPTIONS",
        Authorization: "Bearer " + token,
      },
    }).then((response) => {
      return response.data
    });
  }
}

export default new UserService();
