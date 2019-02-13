CS 541 - Game Development for Mobile Platforms {Spring 2019}

Project 2 - Android Application  : 2048 Game


Application Description -	This is the classic 2048 number puzzle game!	You have to join the numbers and get 2048 on the tile.

•	Animation demo video (YouTube) - https://youtu.be/8sV7poPltMY

•	How to play - 
		Swipe up / down / left / right to move the tile. 
		When the two tiles having same number touch each other, they merge into one increasing the value of the tile twice, and the game score increases as per the merged tiles.
		On your every swipe, you will get new 2 on any of the empty tile, and it will be displayed in WHITE background to stand apart as a new number.
		You need to achieve 2 --> 4 --> 8 --> 16 --> 32 --> 64 --> 128 --> 256 --> 512 --> 1024 --> 2048 and you win when you reach 2048.
		You will lose, if all tiles filled and there is no empty tile for a new number.
		The game resets once you lose or win.

		NOTE - You can undo your last action, and all tiles will move back to their last position, and score will be reduced to the score you had before your last swipe.


•	Implementation - 
		Used single array of 4 size instead of 4x4 matrix and accessed one row/column at a time.
		On each swipe, the code loops through all the tiles one row/column at a time.
		When you swipe, it is the combination of two actions - sum of similar tiles, move tiles in swiped direction to skip empty tiles.
		To perform slide animation, I kept track of which tiles of row/column are merged, and which tile are sliding to cover up blank tiles and performed sliding animation of tiles accordingly.
		The new value i.e. 2 is provided on each swipe, on random blank tile. 

		To fill the colors, used array of 12 colors.
		Once the tile gets its value, the code determines log (base 2) of the value to get the index for color array and sets the background.

		Score is calculated based on new addition value in each swipe action.

		To provide undo action, on each Swipe, the state of the game is stored in array of size 16.
		Once undo pressed, the state is restored using this array.
		You can perform undo only last action, multiple undo actions aren’t allowed.

		To end the game, checked if any tile has reached 2048 or all the tiles are full and no new tile can be generated.