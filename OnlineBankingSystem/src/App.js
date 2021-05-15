import React, { Component } from "react";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import Home from "./Components/Home";
import "bootstrap/dist/css/bootstrap.min.css";

// redux
// import { Provider } from "react-redux";
// import store from "./redux/store";

class App extends Component {
  state = {};
  render() {
    return (
      <React.Fragment>
        <Router>
          <Switch>
            <Route exact path="/" component={Home} />
            {/* <Route path="/LogIn" component={UserLogin} /> */}
          </Switch>
        </Router>
      </React.Fragment>
    );
  }
}
export default App;
