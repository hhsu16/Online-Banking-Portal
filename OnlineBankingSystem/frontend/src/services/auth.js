import axios from "axios";

const API_URL = "http://localhost:8080/";

class AuthService {
  login(email, password) {
    return axios
      .get(API_URL + "api/v1/login?email=" + email + "&password=" + password, {
        email,
        password,
        headers: { "Access-Control-Allow-Origin": "*" },
      })
      .then((response) => {
        if (response.data) {
          localStorage.setItem("user", JSON.stringify(response.data));
        }
        // console.log(response.data);
        return response.data;
      });
  }

  logout() {
    localStorage.removeItem("user");
  }

  register(username, email, password) {
    return axios.post(API_URL + "signup", {
      username,
      email,
      password,
    });
  }

  getCurrentUser() {
    return JSON.parse(localStorage.getItem("user"));
  }
}

export default new AuthService();
