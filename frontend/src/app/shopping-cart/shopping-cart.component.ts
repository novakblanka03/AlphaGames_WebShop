import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent implements OnInit {
  cartGames: number[] = [];

  ngOnInit() {
    this.getCartGames();
  }

  getCartGames() {
    const cartGameIds: number[] = JSON.parse(localStorage.getItem('cartGameIds') || '[]');
    this.cartGames = cartGameIds;
    console.log('Cart Games:', this.cartGames);
  }
}
