import React, { Component } from "react";
import "./Home.css";
import axios from "axios";
//using Typography
import Typography from "@material-ui/core/Typography";
import {
  Nav,
  Button,
  Card,
  Navbar,
  Form,
  FormControl,
  Row,
  Col,
  ButtonGroup,
  Table,
} from "react-bootstrap";
import ExitToAppIcon from "@material-ui/icons/ExitToApp";
//fb link
import FacebookIcon from "@material-ui/icons/Facebook";
//twiter link
import TwitterIcon from "@material-ui/icons/Twitter";
//gamil
import EmailIcon from "@material-ui/icons/Email";
class Home extends Component {
  constructor(props) {
    super(props);
    this.state = {
      data: [],
      tempData: [],
      api: "",
      searchData: null,
    };
    this.onSearch = this.onSearch.bind(this);
    this.onChange = this.onChange.bind(this);
    this.onClicked = this.onClicked.bind(this);
  }
  onSearch(event) {
    if (event.target.value == "") {
      this.setState((this.state.data = []));
    } else {
      this.setState({
        searchData: event.target.value,
      });
    }
  }
  async onClicked() {
    let getData = await axios.get("https://jsonplaceholder.typicode.com/users");
    this.setState({
      data: getData.data,
      tempData: getData.data,
    });
    let searchProducts = [];
    this.state.data.map((data2) => {
      if (data2.id == this.state.searchData) {
        searchProducts.push(data2);
      }
    });
    this.setState({
      data: searchProducts,
    });
  }
  async onChange(e) {
    if (e.target.id == "1") {
      this.setState({
        api: "https://jsonplaceholder.typicode.com/posts",
      });
    } else if (e.target.id == "2") {
      this.setState({
        api: "Second",
      });
    } else {
      this.setState({
        api: "Thired",
      });
    }
  }
  render() {
    let { data, api } = this.state;

    return (
      <div className="MainContanier">
        <Nav className="justify-content-end" activeKey="/home">
          <Nav.Item>
            <Nav.Link eventKey="disabled" disabled>
              <Button variant="success">
                <ExitToAppIcon style={{ marginRight: "10px" }} />
                Log In
              </Button>
            </Nav.Link>
          </Nav.Item>
        </Nav>
        <Navbar bg="light" expand="lg">
          <Navbar.Brand href="#home">Application Logo</Navbar.Brand>
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
          <Navbar.Collapse id="basic-navbar-nav">
            <Nav className="mr-auto">
              <Form inline>
                <FormControl
                  type="text"
                  placeholder="Search"
                  className="mr-sm-2"
                  variant="success"
                  style={{ width: "500px" }}
                  onChange={this.onSearch}
                  id="search"
                />
                <Button variant="outline-success" onClick={this.onClicked}>
                  Search
                </Button>
              </Form>
            </Nav>
          </Navbar.Collapse>
        </Navbar>
        <div
          style={{ backgroundColor: "#F8F9FA", width: "100vw", height: "70vh" }}
        >
          <Row style={{ height: "62vh" }}>
            <Col>
              <Card
                border="success"
                style={{ width: "100%", height: "100%", marginLeft: "10px" }}
              >
                <Card.Header>Search Result</Card.Header>
                <Card.Body>
                  <Card.Text>
                    <div className="Scrolling">
                      {data.map((data2) => (
                        <Card id={data.id} style={{ margin: "10px" }}>
                          <Card.Body>
                            <Card.Title>{data2.name}</Card.Title>
                            <Button variant="success">
                              <EmailIcon />
                              {data2.email}
                            </Button>
                            <Card.Text>
                              Address :{data2.address.street}
                              {data2.address.suite} {data2.address.suite}
                            </Card.Text>
                          </Card.Body>
                          <Card.Footer>
                            <small className="text-muted">
                              Last updated 3 mins ago
                            </small>
                          </Card.Footer>
                        </Card>
                      ))}
                    </div>
                  </Card.Text>
                </Card.Body>
              </Card>
            </Col>
            <Col xs={3}>
              <Card border="success" style={{ width: "98%", height: "100%" }}>
                <Card.Body>
                  <Card.Title>Get All API's </Card.Title>
                  <Card.Text>
                    <Row>
                      <Col xs={3}>
                        <Button
                          variant="success"
                          id="1"
                          style={{ marginTop: "20px" }}
                          onClick={this.onChange}
                        >
                          Get API_1
                        </Button>
                        <Button
                          variant="success"
                          id="2"
                          style={{ marginTop: "20px" }}
                          onClick={this.onChange}
                        >
                          Get API_2
                        </Button>
                        <Button
                          variant="success"
                          id="3"
                          style={{ marginTop: "20px" }}
                          onClick={this.onChange}
                        >
                          Get API_3
                        </Button>
                      </Col>
                      <Col sx="auto">
                        <div className="Scrolling2">
                          <Typography style={{ wordWrap: "break-word" }}>
                            {api}
                          </Typography>
                        </div>
                      </Col>
                    </Row>
                  </Card.Text>
                </Card.Body>
              </Card>
            </Col>
          </Row>
        </div>
        <Card className="text-center">
          <Card.Body>
            <Card.Title>Special title treatment</Card.Title>
            <Card.Text>
              Web developers design and build websites. They are typically
              responsible for the appearance, of the site and technical aspects,
              such as site speed and how much traffic the site can handle. Web
              developers may also create site content that requires technical
              features.
            </Card.Text>
            <ButtonGroup size="sm">
              <Button variant="success">
                <FacebookIcon />
              </Button>
              <Button variant="success">
                <TwitterIcon />
              </Button>
              <Button variant="success">
                <EmailIcon />
              </Button>
            </ButtonGroup>
          </Card.Body>
        </Card>
      </div>
    );
  }
}

export default Home;
