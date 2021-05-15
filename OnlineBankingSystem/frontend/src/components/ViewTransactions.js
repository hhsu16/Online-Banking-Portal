import React, { Component } from "react";
import { DataGrid } from "@material-ui/data-grid";
import UserService from "../services/user";

const localAccounts = JSON.parse(localStorage.getItem("accounts"));

export class ViewTransactions extends Component {
  constructor(props) {
    super(props);

    this.state = {
      accounts: [{}],

      rows: [
        { id: 1, amount: 50, accountType: "Checking" },
        { id: 2, amount: 500, accountType: "Saving" },
        { id: 3, amount: 100, accountType: "Checking" },
      ],
      columns: [
        { field: "id", headerName: "Account Number", width: 200 },
        { field: "accountType", headerName: "Account Type", width: 200 },
        {
          field: "amount",
          type: "number",
          headerName: "Account Amount",
          width: 200,
        },
      ],
    };
  }

  componentDidMount() {
    UserService.viewTransaction()
      .then((res) => {
        let temp = res.data;

        temp = temp.map((obj) => {
          return {
            accountBalance: obj.accountBalance,
            accountNo: obj.accountNo,
            accountType: obj.accountType,
          };
        });

        this.setState({ accounts: temp });
        console.log(this.state.accounts);

        // var i;
        // for(i = 0; i < arrayObj.length; i++){
        //     arrayObj[i].stroke = arrayObj[i]['key1'];
        //     delete arrayObj[i].key1;
        // }
      })
      .catch((e) => {
        console.log(e);
      });

    let temp = localAccounts;
    temp.map((obj) => {
      this.state.accounts.push(obj.accountNo);
      // console.log(this.state.accounts);
    });
  }

  render() {
    return (
      <div style={{ height: 400, width: "100%" }}>
        <DataGrid
          rows={this.state.rows}
          columns={this.state.columns}
          pageSize={5}
          checkboxSelection={false}
        />
      </div>
    );
  }
}

export default ViewTransactions;
