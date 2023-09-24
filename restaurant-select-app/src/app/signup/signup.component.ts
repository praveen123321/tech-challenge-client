import { Component } from '@angular/core';
import { LoginService } from '../services/login.service';


@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  model: any = {username:'',password:"", confirmPassword:""}; 

  constructor(private loginService: LoginService) {}
  passwordMatch = false;
  responseMessage="";

  onSubmit() {

    this.loginService.signup(this.model).subscribe(
      (response) => {
        this.responseMessage = "User credentials created Successfully.";
        console.log('User saved successfully:', response);
      },
      (error) => {
        this.responseMessage = "An Error occured while creating the User credentials.";
        console.error('Error saving user:', error);
      }
    );
  }


  checkPasswordMatch() {
    console.log('this.passwordMatch'  + this.passwordMatch);
    this.passwordMatch = (this.model.password === this.model.confirmPassword);
     console.log(this.passwordMatch);
  }
}
