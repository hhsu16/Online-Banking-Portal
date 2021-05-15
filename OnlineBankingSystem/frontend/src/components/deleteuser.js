import React, { Component } from "react";
import Form from "react-validation/build/form";
import Input from "react-validation/build/input";
import CheckButton from "react-validation/build/button";

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

export class Deleteuser extends Component {

    constructor(props) {
        super(props);
        this.onChangeMemo = this.onChangeMemo.bind(this);
        this.handleSubmitTransfer = this.handleSubmitTransfer.bind(this);
        this.state = {
            deleteme: "",
            deleteStatus: false
        }
    }

    onChangeMemo(e) {
        this.setState({
          deleteme: e.target.value,
        });
      }

      // TO_DO: api call
  handleSubmitTransfer(e) {
    e.preventDefault();

    if (this.state.deleteme == "deleteme") {
        UserService.deleteUser().then((response) => {
            if(response == "Accounts Closure Request accepted") {
                alert(response)
            }
            else {
                alert("Error Submitting data");
            }
        })
    }
    else {
        alert("Please type deleteme")
    }

    this.setState({
      deleteme: "",
      deleteStatus: true,
    });
  }

    render() {
        return(
        <div className="col-md-12">
        <div className="card-header">
        <Form
            onSubmit={this.handleSubmitTransfer}
            ref={(c) => {
              this.form = c;
            }}
          >
          <div>
                <div className="form-group">
                  <label htmlFor="firstname">Type Deleteme in box to delete user</label>
                  <Input
                    type="text"
                    className="form-control"
                    name="firstname"
                    value={this.state.deleteme}
                    onChange={this.onChangeMemo}
                  />
                </div>
            </div>
            <div className="form-group">
                  <button className="btn btn-primary ">Cancel</button>
                  <button className="btn btn-primary ">Next</button>
            </div>
            </Form>
            </div>
            </div>
        )
    }
};

export default Deleteuser;