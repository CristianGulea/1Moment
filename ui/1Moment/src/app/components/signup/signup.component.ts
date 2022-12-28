import {Component, OnInit} from '@angular/core';
import {LoginService} from "../login/login-service";
import {
  AbstractControl,
  FormControl,
  FormGroup,
  ValidationErrors,
  ValidatorFn,
  Validators
} from "@angular/forms";
import {User} from "../login/User";
import {Router} from "@angular/router";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  signUp: FormGroup;
  constructor(private loginService: LoginService, private router: Router) { }
  hide: boolean = true;
  isError:boolean= false;
  isLoading: boolean= false;
  ngOnInit(): void {
    this.signUp = new FormGroup(
      {
        username: new FormControl('', [Validators.required]),
        password: new FormControl('', [Validators.required]),
        confirmPassword: new FormControl('', [Validators.required]),
      },
      [CustomValidators.MatchValidator('password', 'confirmPassword')]
    );
  }
  get passwordMatchError() {
    return (
      this.signUp.getError('mismatch') &&
      this.signUp.get('confirmPassword')?.touched
    );
  }

  createAccount(){
    this.isLoading=true;
    const username= this.signUp.value.username;
    const password= this.signUp.value.password;
    const password2= this.signUp.value.confirmPassword;
    if(password===password2){
      this.loginService.signup(<User>{username:username, password:password}).subscribe(() => {
        this.router.navigate(['/login']);
        this.isLoading=false;
      },
        () => {
          this.signUp.reset();
          this.isError=true;
          this.isLoading= false;
        }
      )
    }
  }

}

export class CustomValidators {
  static MatchValidator(source: string, target: string): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const sourceCtrl = control.get(source);
      const targetCtrl = control.get(target);

      return sourceCtrl && targetCtrl && sourceCtrl.value !== targetCtrl.value
        ? { mismatch: true }
        : null;
    };
  }
}
