import React, { Component } from "react";

export class ViewTransactions extends Component {
  constructor(props) {
    super(props);

    this.state = {
      accounts: [],
    };
  }

  componentDidMount() {
    let temp = JSON.parse(localStorage.getItem("accounts"));
    // console.log(temp);
    let arr = [];
    temp.map((obj) => {
      // console.log(obj.accountNo);
      // arr.push(obj.accountNo);
      this.state.accounts.push(obj.accountNo);
      console.log(this.state.accounts);
    });
  }

  render() {
    return (
      <div>
        <div class="btn-group">
          <button
            type="button"
            class="btn btn-secondary dropdown-toggle"
            data-bs-toggle="dropdown"
            aria-expanded="false"
          >
            Right-aligned menu example
          </button>
          <ul class="dropdown-menu dropdown-menu-end">
            <li>
              <button class="dropdown-item" type="button">
                Action
              </button>
            </li>
            <li>
              <button class="dropdown-item" type="button">
                Another action
              </button>
            </li>
            <li>
              <button class="dropdown-item" type="button">
                Something else here
              </button>
            </li>
          </ul>
        </div>
      </div>
    );
  }
}

export default ViewTransactions;
