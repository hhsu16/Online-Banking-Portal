import React, { Component } from "react";
import { DataGrid } from "@material-ui/data-grid";
import AuthService from "../services/auth";

import axios from "axios";

const token = JSON.parse(localStorage.getItem("token"));

export class AdminDelete extends Component {
  constructor(props) {
    super(props);

    this.handleChangeID = this.handleChangeID.bind(this);

    this.addRefund = this.addRefund.bind(this);

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
        arr.push({
          id: obj.accountNo,
          accountStatus: obj.accountStatus,
          accountType: obj.accountType,
          accountBalance: obj.accountBalance,
          username: obj.user.firstName + " " + obj.user.lastName,
        });
        this.setState({ customers: arr }, () => {
          console.log(this.state.customers, "customers");
        });
      });
    });
  }

  deleteACustomer() {
    axios
      .put(
        `http://localhost:8080/deleteCustomer?userId=${this.state.ID}`,
        {},
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
          alert("Refund Success!");
          window.location.reload();
        } else {
          alert("Refund reject!");
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
              <th scope="col">Account Type</th>
              <th scope="col">Account Balance</th>
            </tr>
          </thead>
          {this.state.accounts.map((obj) => {
            return (
              <tbody>
                <tr>
                  <td>{obj.accountNo}</td>
                  <td>{obj.accountType}</td>
                  <td>{obj.accountBalance}</td>
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

        <button onClick={this.addRefund}>Process Refund</button>
      </div>
    );
  }
}

export default AdminDelete;
