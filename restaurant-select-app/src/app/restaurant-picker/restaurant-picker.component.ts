import { Component } from '@angular/core';
import { LoginService } from '../services/login.service';
import { Router } from '@angular/router';
import { RestaurantPickerService } from '../services/restaurant-picker.service';

@Component({
  selector: 'app-restaurant-picker',
  templateUrl: './restaurant-picker.component.html',
  styleUrls: ['./restaurant-picker.component.css']
})
export class RestaurantPickerComponent {
  restaurant: any = { name: '', user: { username: '' } };

  itemList: any[] = [];
  currentPage: number = 1;
  itemsPerPage: number = 5;
  totalItems: number = 1;
  itemsPerColumn: number = Math.ceil(this.itemList.length / 3);
  constructor(private loginService: LoginService, private router: Router, private restaurantPickerService: RestaurantPickerService) {

    this.getAllRestaurants();
  }

  get totalPages(): number {
    return Math.ceil(this.totalItems / this.itemsPerPage);
  }

  changePage(page: number): void {
    this.currentPage = page;
  }


  columnItems(numColumns: number): number[] {
    return Array(numColumns).fill(0).map((x, i) => i);
  }

  randomRestaurant: any = { name: '' };

  addRestaurant() {
    this.restaurant.user.username = this.getCurrentUser();
    this.restaurantPickerService.addRestaurant(this.restaurant, this.loginService.getToken()).subscribe(
      (response) => {
        console.log('restaurant added:', response);
        this.restaurant.name = "";
        this.getAllRestaurants();
      },
      (error) => {
        console.error('restaurant error:', error);
      }
    );
  }

  getAllRestaurants() {

    this.restaurantPickerService.getAllRestaurantsForTeam(this.getCurrentUser(), this.loginService.getToken()).subscribe(
      (response) => {
        console.log('restaurant list:', response);
        this.itemList = response;
        this.totalItems = this.itemList.length;

      },
      (error) => {
        console.error('restaurant list error:', error);
      }
    );
  }

  pickRandomRestaurant() {
    this.restaurantPickerService.pickRandomRestaurant(this.getCurrentUser(), this.loginService.getToken()).subscribe(
      (response) => {
        console.log('picked restaurant:', response);
        this.randomRestaurant = response;
      },
      (error) => {
        console.error('picked restaurant error:', error);
        this.randomRestaurant = "";
      }
    );
  }

  logout() {
    this.loginService.logout();
    this.router.navigateByUrl('/login');

  }


  getCurrentUser() {
    return this.loginService.getLoggedInUser();

  }

}
