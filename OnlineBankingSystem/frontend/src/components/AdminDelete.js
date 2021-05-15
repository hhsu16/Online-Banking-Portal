import React, { Component } from "react";
import { DataGrid } from "@material-ui/data-grid";
import AuthService from "../services/auth";

import axios from "axios";

const token = JSON.parse(localStorage.getItem("token"));

export class AdminDelete extends Component {
  constructor(props) {
    super(props);

    this.handleChangeID = this.handleChangeID.bind(this);
    this.deleteACustomer = this.deleteACustomer.bind(this);

    this.state = {
      ID: "",
      customers: [],
    };
  }

  handleChangeID(e) {
    this.setState({ ID: e.target.value }, () => {
      console.log(this.state.ID);
    });
  }

  componentDidMount() {
    AuthService.viewCloseCustomers().then((res) => {
      let temp = res.data;
      let arr = [];
      temp.map((obj) => {
        console.log(obj);
        if(obj == null) {
          return
        }
        arr.push({
          id: obj.userId,
          accountStatus: obj.userStatus,
          username: obj.firstName + " " + obj.lastName,
        });
        this.setState({ customers: arr }, () => {
          console.log(this.state.customers, "customers");
        });
      });
    });
  }

  deleteACustomer() {
    axios.delete(
        `http://bankingapi-lb-1422585049.us-west-2.elb.amazonaws.com/deleteCustomer?userId=${this.state.ID}`,
        {
          headers: {
            "Access-Control-Allow-Origin": "*",
            "Content-Type": "application/json",
            "Access-Control-Allow-Methods": "GET,PUT,POST,DELETE,PATCH,OPTIONS",
            Authorization: "Bearer " + token,
          },
        }
      )
      .then((res) => {
        if (res.status === 202) {
          alert("Delete Success!");
          window.location.reload();
        } else {
          alert("Delete reject!");
          window.location.reload();
        }
      })
      .catch((e) => {
        console.log(e);
      });
  }

  render() {
    return (
      <div style={{ height: 400, width: "100%" }}>
        <label>Delete Customer using input ID: </label>
        <table class="table">
          <thead>
            <tr>
              <th scope="col">Account Number</th>
              <th scope="col">Account Status</th>
              <th scope="col">Username</th>
            </tr>
          </thead>
          {this.state.customers.map((obj) => {
            return (
              <tbody>
                <tr>
                  <td>{obj.id}</td>
                  <td>{obj.accountStatus}</td>
                  <td>{obj.username}</td>
                </tr>
              </tbody>
            );
          })}
        </table>
        <div>
          <label>ID</label>
          <input
            type="number"
            onChange={(e) => {
              this.handleChangeID(e);
            }}
          ></input>
        </div>

        <button onClick={this.deleteACustomer}>Delete User</button>
      </div>
    );
  }
}

export default AdminDelete;
