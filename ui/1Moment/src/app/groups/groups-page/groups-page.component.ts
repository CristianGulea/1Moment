import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {GroupService} from "../group.service";
import {Group} from "./Group";
import {Router} from "@angular/router";
import {User} from "../../components/login/User";
import {LoginService} from "../../components/login/login-service";
import {FormControl} from "@angular/forms";
import {MatDialog} from "@angular/material/dialog";
import {debounceTime, distinctUntilChanged, Subject} from "rxjs";

@Component({
  selector: 'app-groups-page',
  templateUrl: './groups-page.component.html',
  styleUrls: ['./groups-page.component.css']
})
export class GroupsPageComponent implements OnInit {

  public nameControl = new FormControl();
  groups : Group[] = [];
  groupsCopy: Group[] = [];
  isFetching: boolean = false;
  error: boolean = false;
  user:User={};
  searchTerm = new Subject<string>();
  searchString: string;

  constructor(public matDialog: MatDialog, private route: ActivatedRoute, private groupService: GroupService, private router: Router, private loginService: LoginService) {
    this.searchTerm.pipe(
      debounceTime(300),
      distinctUntilChanged())
      .subscribe(value => {
        this.onSearchGroup(value);
      });
  }

  ngOnInit(): void {
    this.isFetching=true;
    this.loginService.user.subscribe(value => this.user=value);
    this.groupService.getGroups().subscribe(value => {this.groups= value; this.groupsCopy=value; this.isFetching=false}, () =>{ this.error= true; this.isFetching=false});
    if(this.error){
      this.router.navigate(['/error']);
    }
  }

  onLogout(){
    this.loginService.logout();
  }

  onGroupShow(group: Group) {
    this.router.navigate(['group/', group.id])
  }

  onSearchGroup(value:string){
    this.groups= this.groupsCopy;
    this.groups= this.groups.filter(value1 => value1.name.toLowerCase().includes(value.toLowerCase()));
  }


  navigateToFeed() {
    this.router.navigate(["/feed"]);
  }

  // @ts-ignore
  openDialog(content): void {
    this.matDialog.open(content, {
      data: { },
      height: '300',
      width: '600px',
    });
  }

  onCancelClick() {
    this.matDialog.closeAll();
  }

  dateUpdated() {

  }

  onSaveClick() {
    // @ts-ignore
    let group: Group = {name: this.nameControl.value, subscribed: true};
    this.groupService.createAGroup(group).subscribe(value => {
      console.log(value);
      this.groups = [...this.groups, value];
    });
    this.nameControl.reset();
    this.matDialog.closeAll();
  }
}
