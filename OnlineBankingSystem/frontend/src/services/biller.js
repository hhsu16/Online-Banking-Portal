import axios from "axios";
const token = JSON.parse(localStorage.getItem("token"));
const user = JSON.parse(localStorage.getItem("user"));
const API_URL = "http://bankingapi-lb-1422585049.us-west-2.elb.amazonaws.com/";

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
    return axios.get(API_URL + `viewPayees?userId=${user.userId}`, {
        headers: {
          "Access-Control-Allow-Origin": "*",
          "Content-Type": "application/json",
          "Access-Control-Allow-Methods": "GET,PUT,POST,DELETE,PATCH,OPTIONS",
          Authorization: "Bearer " + token,
        },
      });
  }

  addPayee(account,name,email,contact) {
    return axios.post(API_URL + `registerPayee?userId=${user.userId}`, {
      "payeeAccount" : parseInt(account),
	    "payeeName" : name,
	    "payeeEmailId" : email,
	    "payeeContactNo" : contact
    },{
        headers: {
          "Access-Control-Allow-Origin": "*",
          "Content-Type": "application/json",
          "Access-Control-Allow-Methods": "GET,PUT,POST,DELETE,PATCH,OPTIONS",
          Authorization: "Bearer " + token,
        },
    }).then((response) => {
      return response;
    });
  }
}

export default new BillerService();