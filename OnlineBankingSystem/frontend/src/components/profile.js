import React, { Component } from "react";
// import { Redirect, Route } from "react-router-dom";
import AuthService from "../services/auth";

export default class Profile extends Component {
  constructor(props) {
    super(props);

    this.state = {
      currentUser: AuthService.getCurrentUser(),
    };
  }

  render() {
    const { currentUser } = this.state;

    if (!currentUser) {
      this.props.history.push("/");
      window.location.reload();
    }

    return (
      <div className="container">
        <header className="jumbotron">
          <h3>
            <strong>{currentUser.firstname}</strong> Profile
          </h3>
        </header>
        <p>
          <strong>Password: </strong> {currentUser.password}
        </p>
        <p>
          <strong>Id: </strong> {currentUser.userId}
        </p>
        <p>
          <strong>Email: </strong> {currentUser.emailId}
        </p>
        <strong>Role: </strong>
        {currentUser.role}
      </div>
    );
  }
}
