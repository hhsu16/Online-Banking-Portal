import React, { Component } from "react";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";
import DatePicker from "react-date-picker";

import BankService from "../services/bank";

const required = (value) => {
  if (!value) {
    return (
      <div className="alert alert-danger" role="alert">
        This field is required!
      </div>
    );
  }
};

export class transfer extends Component {
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
      amount: "",
      transferDate: new Date(),
      memo: "",

      transferFromOptions: [
        {
          name: "Select…",
          value: null,
        },
        {
          name: "Saving",
          value: "Saving",
        },
        {
          name: "Checking",
          value: "Checking",
        },
      ],
      transferFromValue: "?",
      transferToOptions: [
        {
          name: "Select…",
          value: null,
        },
        {
          name: "Saving",
          value: "Saving",
        },
        {
          name: "Checking",
          value: "Checking",
        },
      ],
      transferToValue: "?",
      repeatOptions: [
        {
          name: "Select…",
          value: null,
        },
        {
          name: "one time",
          value: "one time",
        },
        {
          name: "weekly",
          value: "weekly",
        },
        {
          name: "monthly",
          value: "monthly",
        },
        {
          name: "yearly",
          value: "yearly",
        },
      ],
      repeatOptionsValue: "?",

      successful: false,
      message: "",
    };
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
      BankService.externalTransfer(this.state.amount, this.state.memo).then(
        (response) => {
          this.setState({
            message: response.data.message,
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
                  <label htmlFor="transferFrom">Transfer from</label>
                  <div>
                    <select
                      className="form-control"
                      onChange={this.onChangeTransferFrom}
                      value={this.state.transferFromValue}
                    >
                      {this.state.transferFromOptions.map((item) => (
                        <option key={item.value} value={item.value}>
                          {item.name}
                        </option>
                      ))}
                    </select>
                    {/* <p> data: {this.state.transferFromValue}</p> */}
                  </div>
                </div>

                <div className="form-group">
                  <label htmlFor="transferTo">Transfer to</label>
                  <div>
                    <select
                      className="form-control"
                      onChange={this.onChangeTransferTo}
                      value={this.state.transferToValue}
                    >
                      {this.state.transferToOptions.map((item) => (
                        <option key={item.value} value={item.value}>
                          {item.name}
                        </option>
                      ))}
                    </select>
                    {/* <p> data: {this.state.transferFromValue}</p> */}
                  </div>
                </div>

                <div className="form-group">
                  <label htmlFor="transferDate">Transfer date</label>
                  <DatePicker
                    onChange={this.onChangeTransferDate}
                    value={this.state.transferDate}
                    validations={[required]}
                  />
                  {/* <p>{JSON.stringify(this.state.dateOfBirth)}</p> */}
                </div>

                <div className="form-group">
                  <label htmlFor="repeatTransferType">
                    Repeating transfer type
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
                  <label htmlFor="firstname">Memo</label>
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

export default transfer;
