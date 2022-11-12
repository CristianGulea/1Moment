import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class LoginService {

  constructor() { }

  public handleLoginByCredentials(username: string, password: string) {
  //request
  alert("username: " + username + "    password: " + password);
}

}

