import { Component, OnInit } from '@angular/core';
import { LoginService } from './login-service';
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public username: string = "";
  public password: string = "";

  constructor(private loginService: LoginService, private routes: Router) {}

  ngOnInit(): void {
  }

  hide: boolean = true;
  hideAlert: boolean = false;

  public login() {
    this.loginService.handleLoginByCredentials(this.username, this.password).subscribe({next: message => {
      this.loginService.saveUserToLocalStorage(this.username, this.password, message);
        this.routes.navigate(["/groups"]);
      }, error: err => {
        console.log("ERR---"); console.log(err);
        this.hideAlert = true;
      }})
  }
}
