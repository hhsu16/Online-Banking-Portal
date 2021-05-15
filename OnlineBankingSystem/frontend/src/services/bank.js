import axios from "axios";
import { format } from "date-fns";
const token = JSON.parse(localStorage.getItem("token"));
const user = JSON.parse(localStorage.getItem("user"));
const API_URL = "http://bankingapi-lb-1422585049.us-west-2.elb.amazonaws.com/";

class BankService {
  submitBill(from, to, type, amount) {
    if(from == "" || to == "" | type == "" | amount == "") {
        alert ("Please input data and try again");
        return 
    }
    
    if (type == "oneTime") {
        return axios.get(API_URL + `billPayment?accountNo=` + parseInt(from) + `&billerId=` + parseInt(to) + `&amount=` + parseInt(amount), {
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
    else {
        var date = new Date();
        if (type == "weekly") {
            date.setDate(date.getDate()+7);
        }
        if (type == "monthly") {
            date.setMonth(date.getMonth()+1);
        }

        var dateString = format(date, "yyyy-MM-dd")
        console.log("Date:" + dateString);

        return axios.post(API_URL + `recurringPayment`, {
            "accountNo":parseInt(from),
            "billerId":parseInt(to),
            "paymentAmount":parseInt(amount),
            "paymentDate":dateString
        },{
            headers: {
              "Access-Control-Allow-Origin": "*",
              "Content-Type": "application/json",
              "Access-Control-Allow-Methods": "GET,PUT,POST,DELETE,PATCH,OPTIONS",
              Authorization: "Bearer " + token,
            },
          });
        }
    }

    submitPayment(from, to, type, amount) {
        if(from == "" || to == "" | type == "" | amount == "") {
            alert ("Please input data and try again");
            return 
        }
        
        if (type == "oneTime") {
            return axios.get(API_URL + `fundTransfer?accountNo=` + parseInt(from) + `&payeeId=` + parseInt(to) + `&amount=` + parseInt(amount), {
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
        else {
            var date = new Date();
            if (type == "weekly") {
                date.setDate(date.getDate()+7);
            }
            if (type == "monthly") {
                date.setMonth(date.getMonth()+1);
            }
    
            var dateString = format(date, "yyyy-MM-dd")
            console.log("Date:" + dateString);
    
            return axios.post(API_URL + `recurringTransfer`, {
                "accountNo":parseInt(from),
                "payeeId":parseInt(to),
                "transferAmount":parseInt(amount),
                "transferDate":dateString
            },{
                headers: {
                  "Access-Control-Allow-Origin": "*",
                  "Content-Type": "application/json",
                  "Access-Control-Allow-Methods": "GET,PUT,POST,DELETE,PATCH,OPTIONS",
                  Authorization: "Bearer " + token,
                },
              });
            }
        }
}

export default new BankService();