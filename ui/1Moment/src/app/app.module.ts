

import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {LoginComponent} from './components/login/login.component';
import {RouterModule, Routes} from '@angular/router';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {GroupDiscussionPageComponent} from './groups/group-discussion-page/group-discussion-page.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatIconModule} from "@angular/material/icon";
import {MatBadgeModule} from "@angular/material/badge";
import {FlexModule} from "@angular/flex-layout";
import {MatCardModule} from "@angular/material/card";
import {MatTooltipModule} from "@angular/material/tooltip";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
import {MatListModule} from "@angular/material/list";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {HttpClientModule} from "@angular/common/http";
import {GroupsPageComponent} from './groups/groups-page/groups-page.component';
import {AuthGuard} from "./components/login/auth-guard";
import { SignupComponent } from './components/signup/signup.component';
import {FeedComponent} from "./components/feed/feed.component";


const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'register', component: SignupComponent},
  {path: 'feed', canActivate: [AuthGuard], component: FeedComponent},
  {path: '', redirectTo: '/groups', pathMatch: 'full'},
  {
    path: '', canActivate: [AuthGuard], children: [
      {path: 'group/:id', component: GroupDiscussionPageComponent},
      {path: 'groups', component: GroupsPageComponent}
    ]
  }
];

@NgModule({
  declarations: [
    FeedComponent,
    AppComponent,
    GroupDiscussionPageComponent,
    LoginComponent,
    GroupsPageComponent,
    SignupComponent
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(routes),
    BrowserAnimationsModule,
    MatToolbarModule,
    MatIconModule,
    MatBadgeModule,
    FlexModule,
    MatCardModule,
    MatTooltipModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatListModule,
    MatProgressSpinnerModule,
    HttpClientModule,
    BrowserModule,
    MatIconModule,
    MatFormFieldModule,
    MatInputModule,
    FormsModule,
    RouterModule.forRoot(routes),
    ReactiveFormsModule
  ],
  exports: [RouterModule],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}



