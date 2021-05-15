import React from "react";
import AdminService from "../services/admin";
import { DataGrid } from "@material-ui/data-grid";

export default class admin extends React.Component {
  constructor(props) {
    super(props);
    this.onChangeSelectId = this.onChangeSelectId.bind(this);
    this.rejectProspect = this.rejectProspect.bind(this);
    this.approveProspect = this.approveProspect.bind(this);

    this.state = {
      prospects: [],
      selectId: "",
    };
  }

  onChangeSelectId(row) {
    this.setState(
      {
        selectId: row.data.id[0],
      },
      () => console.log(this.state.selectId)
    );
  }

  componentDidMount() {
    AdminService.getProspects().then((res) => {
      let temp = res.data;
      temp = temp.map((obj) => {
        return {
          id: [obj.prospectId],
          username: [obj.firstName] + " " + [obj.lastName],
          email: [obj.emailId],
          dateOfBirth: JSON.stringify(obj.dateOfBirth).substring(1, 11),
          contact: [obj.contact],
          address: [obj.address],
          type: [obj.accountTypeWanted],
        };
      });
      this.setState({ prospects: temp });
    });
  }

  rejectProspect() {
    AdminService.reject(this.state.selectId)
      .then((response) => {
        console.log(response.data);
        this.props.history.push("/");
      })
      .catch((e) => {
        console.log(e);
      });
  }

  approveProspect() {
    AdminService.approve(this.state.selectId)
      .then((response) => {
        console.log("RES:" + response.data);
        this.props.history.push("/");
      })
      .catch((e) => {
        console.log(e);
      });
  }

  render() {
    const columns = [
      { field: "id", headerName: "ID", width: 70 },
      { field: "username", headerName: "username", width: 120 },
      { field: "email", headerName: "email", width: 240 },
      { field: "dateOfBirth", headerName: "Birthday", width: 120 },
      { field: "contact", headerName: "Telephone", width: 120 },
      {
        field: "type",
        headerName: "Option",
        width: 120,
      },
      { field: "address", headerName: "address", width: 500 },
    ];

    return (
      <div>
        <div style={{ height: 400, width: "100%" }}>
          <DataGrid
            rows={this.state.prospects}
            columns={columns}
            pageSize={5}
            checkboxSelection={false}
            onRowSelected={(row) => {
              this.onChangeSelectId(row);
            }}
          />
        </div>
        <button class="btn btn-primary" onClick={this.approveProspect}>
          Add
        </button>
        <button className="btn btn-danger" onClick={this.rejectProspect}>
          Delete
        </button>
      </div>
    );
  }
}
