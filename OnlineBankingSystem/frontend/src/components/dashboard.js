import React, { Component } from "react";
import UserService from "../services/user";

export class dashboard extends Component {
  constructor(props) {
    super(props);
    this.state = {
      accounts: [{}],
    };
  }

  componentDidMount() {
    UserService.viewAccounts()
      .then((res) => {
        localStorage.setItem("accounts", JSON.stringify(res.data));

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
      })
      .catch((e) => {
        console.log(e);
      });
  }

  render() {
    return (
      <div>
        <div>
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
        </div>
      </div>
    );
  }
}

export default dashboard;
