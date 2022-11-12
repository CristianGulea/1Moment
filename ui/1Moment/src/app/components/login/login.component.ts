import { Component, OnInit } from '@angular/core';
import { LoginService } from './login-service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public username: string = "";
  public password: string = "";

  constructor(private loginService: LoginService) {}

  ngOnInit(): void {
  }

  hide: boolean = true;

  public login() {
    this.loginService.handleLoginByCredentials(this.username, this.password);
  }
}
