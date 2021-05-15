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

  export class Addpayee extends Component {

    constructor(props) {
        super(props);
        this.onChangeAccount = this.onChangeAccount.bind(this);
        this.onChangeName = this.onChangeName.bind(this);
        this.onChangeEmail = this.onChangeEmail.bind(this);
        this.onChangeContact = this.onChangeContact.bind(this);
        this.handleSubmitTransfer = this.handleSubmitTransfer.bind(this);
        this.state = {
            payeeaccount: "",
            payeename: "",
            payeeemaiid: "",
            payeecontact:"",
            allvalues: false
        }
    }

onChangeAccount(e) {
this.setState({
    payeeaccount: e.target.value,
});
}

onChangeName(e) {
this.setState({
    payeename: e.target.value,
});
}

onChangeEmail(e) {
this.setState({
    payeeemaiid: e.target.value,
});
}

onChangeContact(e) {
this.setState({
    payeecontact: e.target.value,
});
}

      // TO_DO: api call
handleSubmitTransfer(e) {
e.preventDefault();

if(this.state.payeeaccount == "" || this.state.payeename == "" || this.state.payeeemaiid == "" || this.state.payeecontact == "") {
    alert("Please fill all the fields")
}
else {
    this.setState({
        allvalues : true
    })
}

BillerService.addPayee(this.state.payeeaccount, this.state.payeename, this.state.payeeemaiid, this.state.payeecontact).then((response) => {
    if(response.status == 201) {
        alert("Successfully added payee");
    }
    console.log(response.status)
});

this.setState({
    payeeaccount: "",
    payeename: "",
    payeeemaiid: "",
    payeecontact: "",
    allvalues: false
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
              <label htmlFor="firstname">Payee Account</label>
              <Input
                type="text"
                className="form-control"
                name="payeeaccount"
                value={this.state.payeeaccount}
                onChange={this.onChangeAccount}
              />
            </div>
            <div className="form-group">
              <label htmlFor="firstname">Payee Name</label>
              <Input
                type="text"
                className="form-control"
                name="payeename"
                value={this.state.payeename}
                onChange={this.onChangeName}
              />
            </div>
            <div className="form-group">
              <label htmlFor="firstname">Payee Email</label>
              <Input
                type="text"
                className="form-control"
                name="payeeemaiid"
                value={this.state.payeeemaiid}
                onChange={this.onChangeEmail}
              />
            </div>
            <div className="form-group">
              <label htmlFor="firstname">Payee contact</label>
              <Input
                type="text"
                className="form-control"
                name="payeecontact"
                value={this.state.payeecontact}
                onChange={this.onChangeContact}
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

export default Addpayee;
