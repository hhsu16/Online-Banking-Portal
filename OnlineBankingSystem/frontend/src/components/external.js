import React, { Component } from "react";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";

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

export class external extends Component {
  constructor(props) {
    super(props);
    this.onChangeRoutingNumber = this.onChangeRoutingNumber.bind(this);
    this.onChangeAccountNumber = this.onChangeAccountNumber.bind(this);
    this.onChangeConfirmAccountNumber = this.onChangeConfirmAccountNumber.bind(
      this
    );
    this.onChangeBankName = this.onChangeBankName.bind(this);

    this.handleSubmitExternal = this.handleSubmitExternal.bind(this);

    this.state = {
      routingNumber: "",
      accountNumber: "",
      confirmAccountNumber: "",
      bankName: "",

      accountType: [
        {
          name: "Select…",
          value: null,
        },
        {
          name: "Checking",
          value: "Checking",
        },
        {
          name: "Saving",
          value: "Saving",
        },
      ],
      accountTypeValue: "?",

      successful: false,
      message: "",
    };
  }

  onChangeRoutingNumber(e) {
    this.setState({
      routingNumber: e.target.value,
    });
  }
  onChangeAccountNumber(e) {
    this.setState({
      accountNumber: e.target.value,
    });
  }
  onChangeConfirmAccountNumber(e) {
    this.setState({
      confirmAccountNumber: e.target.value,
    });
  }
  onChangeBankName(e) {
    this.setState({
      bankName: e.target.value,
    });
  }

  onChangeAccountType = (e) => {
    this.setState({
      accountTypeValue: e.target.value,
    });
  };

  // TO_DO: api call
  handleSubmitExternal(e) {
    e.preventDefault();

    this.setState({
      message: "",
      successful: false,
    });

    this.form.validateAll();

    if (this.checkBtn.context._errors.length === 0) {
      BankService.externalTransfer(
        this.state.accountNumber,
        this.state.routingNumber,
        this.state.bankName
      ).then(
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
            onSubmit={this.handleSubmitExternal}
            ref={(c) => {
              this.form = c;
            }}
          >
            {!this.state.successful && (
              <div>
                <div className="form-group">
                  <label htmlFor="firstname">Routing number</label>
                  <Input
                    type="text"
                    className="form-control"
                    name="routingNumber"
                    value={this.state.routingNumber}
                    onChange={this.onChangeRoutingNumber}
                    validations={[required]}
                  />
                </div>

                <div className="form-group">
                  <label htmlFor="lastname">Account number</label>
                  <Input
                    type="text"
                    className="form-control"
                    name="accountNumber"
                    value={this.state.accountNumber}
                    onChange={this.onChangeAccountNumber}
                    validations={[required]}
                  />
                </div>

                <div className="form-group">
                  <label htmlFor="email">Confirm account number</label>
                  <Input
                    type="text"
                    className="form-control"
                    name="confirmAccountNumber"
                    value={this.state.confirmAccountNumber}
                    onChange={this.onChangeConfirmAccountNumber}
                    validations={[required]}
                  />
                </div>

                <div className="form-group">
                  <label htmlFor="accountType">Account type</label>
                  <div>
                    <select
                      className="form-control"
                      onChange={this.onChangeAccountType}
                      value={this.state.accountTypeValue}
                    >
                      {this.state.accountType.map((item) => (
                        <option key={item.value} value={item.value}>
                          {item.name}
                        </option>
                      ))}
                    </select>
                    {/* <p>account type data: {this.state.accountTypeValue}</p> */}
                  </div>
                </div>

                <div className="form-group">
                  <label htmlFor="email">Bank name</label>
                  <Input
                    type="text"
                    className="form-control"
                    name="bankName"
                    value={this.state.bankName}
                    onChange={this.onChangeBankName}
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
