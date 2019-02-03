import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class IsbnValidationService {

  constructor() { }

  validateISBN(input: string): Boolean {
		let isbn = this.normalizeISBN(input);
		let result: Boolean;
		let sum = 0;
		if(isbn.length == 10){
			for(var i = 0; i < 9; i++)
			 sum+= isbn[i] * (i + 1);
			 result = sum % 11 == isbn[9];
		} else {
			for(var i = 0; i < 13; i++){
			 if(i % 2 == 1)
			     isbn[i]*=3;
			 sum+= isbn[i];
			}
			 result = sum % 10 == 0;
		}
		return result;
	}

	private normalizeISBN(isbn: string){
		let arr: Array<number> = [];
		let isbnArr = isbn.replace("-", "").replace(" ","").toLocaleLowerCase().split("");
		for(var i = 0; i < isbnArr.length; i++){
			if(isbnArr[i] == "x")
			  isbnArr[i] = "10";
			arr.push(+isbnArr[i]);
		}
		return arr;
	}
}
