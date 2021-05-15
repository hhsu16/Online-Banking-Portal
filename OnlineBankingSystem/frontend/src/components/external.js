import React, { Component } from "react";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";
import DatePicker from "react-date-picker";

import BankService from "../services/bank";
import UserService from "../services/user";
import BillerService from "../services/biller";

const required = (value) => {
  if (!value) {
    return (
      <div className="alert alert-danger" role="alert">
        This field is required!
      </div>
    );
  }
};

export class external extends Component {
  constructor(props) {
    super(props);
    this.onChangeAmount = this.onChangeAmount.bind(this);
    this.onChangeTransferDate = this.onChangeTransferDate.bind(this);
    this.onChangeMemo = this.onChangeMemo.bind(this);

    this.onChangeTransferFrom = this.onChangeTransferFrom.bind(this);
    this.onChangeTransferTo = this.onChangeTransferTo.bind(this);
    this.onChangeRepeatOptions = this.onChangeRepeatOptions.bind(this);

    this.handleSubmitTransfer = this.handleSubmitTransfer.bind(this);

    this.state = {
      accounts: [],
      billers: [],
      dummy: "",
      transferDate: new Date(),
      memo: "",
      transferFromValue: "?",
      transferToValue: "?",
      repeatOptions: [
        {
          name: "Select…",
          value: null,
        },
        {
          name: "oneTime",
          value: "oneTime",
        },
        {
          name: "weekly",
          value: "weekly",
        },
        {
          name: "monthly",
          value: "monthly",
        },
      ],
      repeatOptionsValue: "?",

      successful: false,
      message: "",
    };
  }

  componentDidMount() {
    UserService.viewAccounts().then((res) => {
      localStorage.setItem("accounts", JSON.stringify(res.data));
      let temp = res.data;
      temp = temp.map((obj ) => {
        console.log(obj.accountNo)
        this.state.accounts.push(obj.accountNo)
        this.setState({ state: this.state });
      })
    });

    BillerService.viewBillers().then((res) => {
      let billers = res.data;
      billers.map((biller) => {
        console.log(biller.billerName)
        this.state.billers.push(biller)
        this.setState({ state: this.state });
      });
    });
  }

  onChangeAmount(e) {
    this.setState({
      amount: e.target.value,
    });
  }
  onChangeTransferDate(e) {
    this.setState({
      transferDate: new Date(),
    });
  }
  onChangeMemo(e) {
    this.setState({
      memo: e.target.value,
    });
  }
  onChangeTransferFrom(e) {
    this.setState({
      transferFromValue: e.target.value,
    });
  }

  onChangeTransferTo = (e) => {
    this.setState({
      transferToValue: e.target.value,
    });
  };

  onChangeRepeatOptions = (e) => {
    this.setState({
      repeatOptionsValue: e.target.value,
    });
  };

  // TO_DO: api call
  handleSubmitTransfer(e) {
    e.preventDefault();

    this.setState({
      message: "",
      successful: false,
    });

    this.form.validateAll();

    if (this.checkBtn.context._errors.length === 0) {
      BankService.submitBill(this.state.transferFromValue, this.state.transferToValue, this.state.repeatOptionsValue, this.state.amount).then(
        (response) => {
          if(response == "Bill Payment successful") {
            alert("Bill Payment Sucessful")
          }
          this.setState({
            message: response.data,
            successful: true,
          });
        },
        (error) => {
          const resMessage =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();

          this.setState({
            successful: false,
            message: resMessage,
          });
        }
      );
    }
  }

  render() {
    return (
      <div className="col-md-12">
        <div className="card-header">
          <Form
            onSubmit={this.handleSubmitTransfer}
            ref={(c) => {
              this.form = c;
            }}
          >
            {!this.state.successful && (
              <div>
                <div className="form-group">
                  <label htmlFor="transferFrom">Select Account</label>
                  <div>
                    <select
                      className="form-control"
                      onChange={this.onChangeTransferFrom}
                      value={this.state.transferFromValue}
                    >
                       <option key="" value="">Select</option>
                      {this.state.accounts.map((item) => (
                        <option key={item} value={item}>
                          {item}
                        </option>
                      ))}
                    </select>
                    {/* <p> data: {this.state.transferFromValue}</p> */}
                  </div>
                </div>

                <div className="form-group">
                  <label htmlFor="transferTo">Select Biller</label>
                  <div>
                    <select
                      className="form-control"
                      onChange={this.onChangeTransferTo}
                      value={this.state.transferToValue}
                    >
                      <option key="" value="">Select</option>
                      {this.state.billers.map((item) => (
                        <option key={item.billerId} value={item.billerId}>
                          {item.billerName}
                        </option>
                      ))}
                    </select>
                    {/* <p> data: {this.state.transferFromValue}</p> */}
                  </div>
                </div>

                <div className="form-group">
                  <label htmlFor="repeatTransferType">
                    Transfer type
                  </label>
                  <div>
                    <select
                      className="form-control"
                      onChange={this.onChangeRepeatOptions}
                      value={this.state.repeatOptionsValue}
                    >
                      {this.state.repeatOptions.map((item) => (
                        <option key={item.value} value={item.value}>
                          {item.name}
                        </option>
                      ))}
                    </select>
                    {/* <p> data: {this.state.transferFromValue}</p> */}
                  </div>
                </div>

                <div className="form-group">
                  <label htmlFor="tel">Amount</label>
                  <Input
                    type="tel"
                    className="form-control"
                    name="tel"
                    onChange={this.onChangeAmount}
                    value={this.state.amount}
                    validations={[required]}
                  />
                </div>

                <div className="form-group">
                  <label htmlFor="firstname">Comments</label>
                  <Input
                    type="text"
                    className="form-control"
                    name="firstname"
                    value={this.state.memo}
                    onChange={this.onChangeMemo}
                    validations={[required]}
                  />
                </div>

                <div className="form-group">
                  <button className="btn btn-primary ">Cancel</button>
                  <button className="btn btn-primary ">Next</button>
                </div>
              </div>
            )}

            {this.state.message && (
              <div className="form-group">
                <div
                  className={
                    this.state.successful
                      ? "alert alert-success"
                      : "alert alert-danger"
                  }
                  role="alert"
                >
                  {this.state.message}
                </div>
              </div>
            )}
            <CheckButton
              style={{ display: "none" }}
              ref={(c) => {
                this.checkBtn = c;
              }}
            />
          </Form>
        </div>
      </div>
    );
  }
}

export default external;
