import axios from "axios";
const token = JSON.parse(localStorage.getItem("token"));
const API_URL = "http://localhost:8080/";

class AuthService {
  login(email, password) {
    return axios
      .post(API_URL + "login", {
        emailId: email,
        password: password,
        headers: {
          "Access-Control-Allow-Origin": "*",
          "Content-Type": "application/json",
        },
      })
      .then((response) => {
        if (response.data) {
          localStorage.setItem("user", JSON.stringify(response.data.user));
          localStorage.setItem("token", JSON.stringify(response.data.token));
        }
        // console.log(response.data);
        return response.data;
      });
  }

  logout() {
    localStorage.removeItem("user");
    localStorage.removeItem("token");
  }

  register(
    firstName,
    lastName,
    password,
    emailId,
    dateOfBirth,
    contact,
    address,
    accountTypeWanted
  ) {
    return axios.post(API_URL + "signup", {
      firstName: firstName,
      lastName: lastName,
      password: password,
      emailId: emailId,
      dateOfBirth: dateOfBirth,
      contact: contact,
      address: address,
      accountTypeWanted: accountTypeWanted,
      headers: { "Access-Control-Allow-Origin": "*" },
    });
  }

  getCurrentUser() {
    return JSON.parse(localStorage.getItem("user"));
  }

  viewCustomerAccounts() {
    return axios.get(API_URL + "viewCustomerAccounts", {
      headers: {
        "Access-Control-Allow-Origin": "*",
        "Content-Type": "application/json",
        "Access-Control-Allow-Methods": "GET,PUT,POST,DELETE,PATCH,OPTIONS",
        Authorization: "Bearer " + token,
      },
    });
  }
}

export default new AuthService();
