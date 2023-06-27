import { Component, OnInit } from '@angular/core';
import { Game } from '../models/game.model';
import { Router } from '@angular/router';
import { GameService } from '../game/game.service';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-store',
  templateUrl: './store.component.html',
  styleUrls: ['./store.component.css']
})
export class StoreComponent implements OnInit {
  games: Game[] = [];

  constructor(
      private router: Router,
      private gameService: GameService,
    private changeDetector: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.getGames();
  }

  getGames() {
    this.gameService.getGames().subscribe({
      next: (games: Game[]) => {
        this.games = games;
        console.log('Received games:', games);
      },
      error: (error) => {
        console.error(error);
      }
    });
  }

  viewGameDetails(gameId: number) {
    this.router.navigate(['/game', gameId]);
  }

  addToCart(game: Game) {
    // Add the game ID to the cart
    const cartGameIds: number[] = JSON.parse(localStorage.getItem('cartGameIds') || '[]');
    cartGameIds.push(game.id);
    localStorage.setItem('cartGameIds', JSON.stringify(cartGameIds));

    // Force Angular to check whether the game is in the cart
    this.changeDetector.detectChanges();

    // Redirect to the shopping cart component
    this.router.navigate(['/shopping-cart']);
  }

  isInCart(gameId: number): boolean {
    const cartGameIds: number[] = JSON.parse(localStorage.getItem('cartGameIds') || '[]');
    return cartGameIds.includes(gameId);
  }
}
