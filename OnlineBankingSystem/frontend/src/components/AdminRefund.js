import React, { Component } from "react";
import { DataGrid } from "@material-ui/data-grid";
import AuthService from "../services/auth";

export class AdminRefund extends Component {
  constructor(props) {
    super(props);

    this.state = {
      selectedUser: "",
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

  render() {
    return (
      <div style={{ height: 400, width: "100%" }}>
        <DataGrid
          rows={this.state.customers}
          columns={this.state.columns}
          pageSize={5}
          checkboxSelection={false}
          onRowSelected={(u) => {
            this.setState({ selectedUser: u });
          }}
        />
      </div>
    );
  }
}

export default AdminRefund;
