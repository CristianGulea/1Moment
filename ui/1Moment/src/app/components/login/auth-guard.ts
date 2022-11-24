import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import {LoginService} from "./login-service";
import {map, Observable, take} from "rxjs";


@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
  constructor(private loginService: LoginService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    router: RouterStateSnapshot
  ):
    | boolean
    | UrlTree
    | Promise<boolean | UrlTree>
    | Observable<boolean | UrlTree> {
    return  this.loginService.user.pipe(
      take(1),
      map(user => {
        console.log(user);
        const isAuth = !!user.accessToken;
        console.log(isAuth);
        if (isAuth) {
          return true;
        }
        return this.router.createUrlTree(['/login']);
      }));
  }
}
