import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AppConfig } from '../config/config';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  private baseUrl = AppConfig.baseUrl + '/api/login';
  public authenticated = false;
  constructor(private http: HttpClient) { }


  signup(user: any): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post(`${this.baseUrl}/signupb`, user, { headers });
  }


  login(user: any): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post(`${this.baseUrl}/user`, user, { headers });
  }


  isAuthenticated() {
    const isLoggedIn = sessionStorage.getItem('isLoggedIn') === 'true';
    return isLoggedIn;
  }

  logout() {
    sessionStorage.clear();
  }

  authenticate(response: any) {
    sessionStorage.setItem('isLoggedIn', 'true');
    sessionStorage.setItem('token', response.token);
    sessionStorage.setItem('username', response.username);
  }

  getLoggedInUser() {
    return sessionStorage.getItem('username');
  }

  getToken() {
    return sessionStorage.getItem('token');
  }
}