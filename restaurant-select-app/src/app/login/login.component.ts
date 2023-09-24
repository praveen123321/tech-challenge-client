import { Component } from '@angular/core';
import { LoginService } from '../services/login.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  model: any = { username: '', password: "" };
  responseMessage = "";
  constructor(private loginService: LoginService, private router: Router) { }

  onSubmit() {
    
    this.loginService.login(this.model).subscribe(
      (response) => {
        this.loginService.authenticate(response);
        console.log(response);
        this.router.navigateByUrl('/restaurant-picker');
      },
      (error) => {
        this.responseMessage = "You have entered an invalid username or password."
        console.error('Error:', error);
      }
    );

  }
}
