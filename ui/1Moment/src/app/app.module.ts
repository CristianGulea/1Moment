

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
import {CommentPageComponent} from "./components/comment-page/comment-page.component";
import {DateTimePickerModule} from "@syncfusion/ej2-angular-calendars";
import {
  NgxMatDatetimePickerModule,
  NgxMatNativeDateModule,
  NgxMatTimepickerModule
} from "@angular-material-components/datetime-picker";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatNativeDateModule} from "@angular/material/core";
import {MatSnackBar, MatSnackBarModule} from "@angular/material/snack-bar";
import {Dialog} from "./groups/group-discussion-page/dialog-page";
import {MatDialogModule} from "@angular/material/dialog";
import {MatGridListModule} from "@angular/material/grid-list";


const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'register', component: SignupComponent},
  {path: 'feed', canActivate: [AuthGuard], component: FeedComponent},
  {path: 'comments/:id',canActivate: [AuthGuard], component: CommentPageComponent},
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
    CommentPageComponent,
    Dialog,
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
    ReactiveFormsModule,
    DateTimePickerModule,
    NgxMatDatetimePickerModule,
    MatDatepickerModule,
    NgxMatTimepickerModule,
    MatNativeDateModule,
    NgxMatNativeDateModule,
    MatSnackBarModule,
    MatDialogModule,
    MatGridListModule
  ],
  exports: [RouterModule],
  providers: [MatDatepickerModule],
  bootstrap: [AppComponent]
})
export class AppModule {
}



