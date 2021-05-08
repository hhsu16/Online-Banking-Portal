import React, { Component } from "react";
import AuthService from "../services/auth";

export default class Admin extends Component {
  constructor(props) {
    super(props);

    this.state = {
      currentUser: AuthService.getCurrentUser(),
    };
  }

  render() {
    const { currentUser } = this.state;

    return (
      <div className="container">
        <header className="jumbotron">
          <h3>
            <strong>Admin</strong>
          </h3>
        </header>
      </div>
    );
  }
}
