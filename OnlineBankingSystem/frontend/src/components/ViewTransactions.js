import React, { Component } from "react";
import { DataGrid } from "@material-ui/data-grid";
import UserService from "../services/user";
export class ViewTransactions extends Component {
  constructor(props) {
    super(props);

    this.state = {
      transcations: [],
      columns: [
        {
          field: "id",
          type: "number",
          headerName: "ID",
          width: 70,
        },
        {
          field: "accountNo",
          type: "number",
          headerName: "Account",
          width: 150,
        },
        {
          field: "transactionId",
          type: "number",
          headerName: "Transaction ID",
          width: 200,
        },
        {
          field: "transactionDate",
          type: "date",
          headerName: "Transaction Date",
          width: 200,
        },
        {
          field: "description",
          headerName: "Description",
          width: 200,
        },
        {
          field: "transactionType",
          headerName: "Transaction Type",
          width: 200,
        },
        {
          field: "transactionStatus",
          headerName: "Transaction Status",
          width: 200,
        },
        {
          field: "transactionAmount",
          type: "number",
          headerName: "Amount",
          width: 200,
        },
      ],
    };
  }

  componentDidMount() {
    UserService.viewTransaction()
      .then((res) => {
        let temp = res.data;
        let arr = [];
        temp.map((obj) => {
          arr.push({
            id: obj.tId,
            accountNo: obj.account.accountNo,
            transactionId: obj.transactionId,
            transactionDate: obj.transactionDate,
            description: obj.description,
            transactionType: obj.transactionType,
            transactionStatus: obj.transactionStatus,
            transactionAmount: obj.transactionAmount,
          });
        });

        this.setState({ transcations: arr }, () => {
          console.log(this.state.transcations, "transcations");
        });
      })
      .catch((e) => {
        console.log(e);
      });
  }

  render() {
    return (
      <div style={{ height: 400, width: "100%" }}>
        <DataGrid
          rows={this.state.transcations}
          columns={this.state.columns}
          pageSize={5}
          checkboxSelection={false}
        />
      </div>
    );
  }
}

export default ViewTransactions;
