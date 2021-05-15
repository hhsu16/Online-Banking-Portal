import React, { Component } from "react";
import { Switch, Route, Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";

import AuthService from "./services/auth";

import Login from "./components/login";
import Register from "./components/register";
import Dashboard from "./components/dashboard";
import FilterView from "./components/filterView";
import Transfer from "./components/transfer";
import External from "./components/external";
import Home from "./components/home";
import ViewTransactions from "./components/ViewTransactions";

import Profile from "./components/profile";
import Admin from "./components/admin";
// import BoardUser from "./components/board-user";
// import BoardAdmin from "./components/board-admin";

class App extends Component {
  constructor(props) {
    super(props);
    this.logOut = this.logOut.bind(this);

    this.state = {
      showAdminBoard: false,
      showCustomerBoard: false,
      currentUser: undefined,
    };
  }

  componentDidMount() {
    const user = AuthService.getCurrentUser();

    if (user) {
      this.setState({
        currentUser: user,
        showAdminBoard: user.role.includes("ADMIN"),
        showCustomerBoard: user.role.includes("CUSTOMER"),
      });
    }
  }

  logOut() {
    AuthService.logout();
  }

  render() {
    const { currentUser, showAdminBoard, showCustomerBoard } = this.state;

    return (
      <div>
        <nav className="navbar navbar-expand navbar-dark bg-dark">
          <Link to={"/"} className="navbar-brand">
            HTVS
          </Link>
          <div className="navbar-nav mr-auto">
            {showAdminBoard && (
              <li className="nav-item">
                <Link to={"/admin"} className="nav-link">
                  Admin Board
                </Link>
              </li>
            )}

            {showCustomerBoard && (
              <ul class="nav">
                <li className="nav-item">
                  <Link to={"/dashboard"} className="nav-link">
                    Dashboard
                  </Link>
                </li>
                <li>
                  <Link to={"/viewTransactions"} className="nav-link">
                    View Transactions
                  </Link>
                </li>
                <li>
                  <Link to={"/transfer"} className="nav-link">
                    Fund Transfer
                  </Link>
                </li>
                <li>
                  <Link to={"/external"} className="nav-link">
                    Bill Payment
                  </Link>
                </li>
              </ul>
            )}
          </div>

          {currentUser ? (
            <div className="navbar-nav ml-auto">
              <li className="nav-item">
                <Link to={"/profile"} className="nav-link">
                  {currentUser.firstName}
                </Link>
              </li>
              <li className="nav-item">
                <a href="/login" className="nav-link" onClick={this.logOut}>
                  LogOut
                </a>
              </li>
            </div>
          ) : (
            <div className="navbar-nav ml-auto">
              <li className="nav-item">
                <Link to={"/login"} className="nav-link">
                  Login
                </Link>
              </li>

              <li className="nav-item">
                <Link to={"/register"} className="nav-link">
                  Sign Up
                </Link>
              </li>
            </div>
          )}
        </nav>

        <div className="container mt-3">
          <Switch>
            <Route exact path={["/", "/home"]} component={Home} />
            <Route exact path="/login" component={Login} />
            <Route exact path="/register" component={Register} />

            <Route exact path="/profile" component={Profile} />

            <Route exact path="/dashboard" component={Dashboard} />
            <Route exact path="/transfer" component={Transfer} />
            <Route exact path="/external" component={External} />
            <Route exact path="/filterView" component={FilterView} />
            <Route
              exact
              path="/viewTransactions"
              component={ViewTransactions}
            />

            <Route exact path="/admin" component={Admin} />

            {/* <Route path="/user" component={BoardUser} /> */}
            {/* <Route path="/mod" component={BoardModerator} /> */}
          </Switch>
        </div>
      </div>
    );
  }
}

export default App;
