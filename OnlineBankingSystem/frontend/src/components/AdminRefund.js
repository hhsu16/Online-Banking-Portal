import React, { Component } from "react";
import { DataGrid } from "@material-ui/data-grid";
import AuthService from "../services/auth";

import axios from "axios";

const token = JSON.parse(localStorage.getItem("token"));

export class AdminRefund extends Component {
  constructor(props) {
    super(props);

    this.handleChangeID = this.handleChangeID.bind(this);
    this.handleChangeAmount = this.handleChangeAmount.bind(this);

    this.addRefund = this.addRefund.bind(this);

    this.state = {
      ID: "",
      amount: "",
      customers: [],
      columns: [
        {
          field: "id",
          type: "number",
          headerName: "ID",
          width: 70,
        },
        {
          field: "accountStatus",
          headerName: "Account Status",
          width: 150,
        },
        {
          field: "accountType",
          headerName: "Account Type",
          width: 200,
        },
        {
          field: "accountBalance",
          type: "number",
          headerName: "Balance",
          width: 200,
        },
        {
          field: "username",
          headerName: "Customer Name",
          width: 220,
        },
      ],
    };
  }

  handleChangeID(e) {
    this.setState({ ID: e.target.value }, () => {
      console.log(this.state.ID);
    });
  }
  handleChangeAmount(e) {
    this.setState({ amount: e.target.value }, () => {
      console.log(this.state.amount);
    });
  }

  componentDidMount() {
    AuthService.viewCustomerAccounts().then((res) => {
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

  addRefund() {
    axios
      .put(
        `http://bankingapi-lb-1422585049.us-west-2.elb.amazonaws.com/addRefund?accountNo=${this.state.ID}&amount=${this.state.amount}`,
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
        {/* <DataGrid
          rows={this.state.customers}
          columns={this.state.columns}
          pageSize={8}
          checkboxSelection={false}
          disableSelectionOnClick={true}
          disableColumnSelector={true}
          disableColumnMenu={true}
        /> */}
      <table class="table">
                <thead>
                  <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Account type</th>
                    <th scope="col">Balance</th>
                    <th scope="col">Customer Name</th>
                  </tr>
                </thead>
      {this.state.customers.map((obj) => {
            return (
              
                <tbody>
                  <tr>
                    <td>{obj.id}</td>
                    <td>{obj.accountType}</td>
                    <td>{obj.accountBalance}</td>
                    <td>{obj.username}</td>
                  </tr>
                </tbody>
            );
          })}
          </table>
        <label>Refund Fees: </label>

        <div>
          <label>ID</label>
          <input
            type="number"
            onChange={(e) => {
              this.handleChangeID(e);
            }}
          ></input>
        </div>
        <div>
          <label>Amount</label>
          <input
            type="number"
            onChange={(e) => {
              this.handleChangeAmount(e);
            }}
          ></input>
        </div>

        <button onClick={this.addRefund}>Process Refund</button>
      </div>
    );
  }
}

export default AdminRefund;
