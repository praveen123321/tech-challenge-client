import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AppConfig } from '../config/config';

@Injectable({
    providedIn: 'root',
})
export class RestaurantPickerService {
    private baseUrl = AppConfig.baseUrl +'/api/restaurant';
    public authenticated = false;
    constructor(private http: HttpClient) { }


    addRestaurant(restaurant: any, bearerToken: any): Observable<any> {
        console.log('save user service');
        const headers = new HttpHeaders({
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${bearerToken}`
        });
        return this.http.post(`${this.baseUrl}/submit-choices`, restaurant, { headers });
    }

    getAllRestaurantsForTeam(username: any, bearerToken: any): Observable<any> {
        console.log('save user service');
        const headers = new HttpHeaders({
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${bearerToken}`
        });
        return this.http.get(`${this.baseUrl}/restaurantList/${username}`, { headers });
    }

    pickRandomRestaurant(username: any, bearerToken: any): Observable<any> {
        console.log('save user service');
        const headers = new HttpHeaders({
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${bearerToken}`,
        });
        return this.http.get(`${this.baseUrl}/get-random-choice/${username}`, { headers });
    }

}