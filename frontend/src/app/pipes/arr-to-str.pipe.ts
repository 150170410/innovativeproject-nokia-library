import { Pipe, PipeTransform } from '@angular/core';
import { Author } from '../models/database/entites/Author';
import { BookCategory } from '../models/database/entites/BookCategory';

@Pipe({
	name: 'arrToStr'
})
export class ArrToStrPipe implements PipeTransform {

	transform(value: any, type?: any): any {
		if (value.length < 1) {
			return null;
		}

		switch (type) {
			case 'author': {
				return this.transformAuthors(value);
			}
			case 'category': {
				return this.transformCategories(value);
			}

		}
		return null;
	}

	transformAuthors(value: Author[]) {
		let out = '';
		for (let i = 0; i < value.length - 1; i++) {
			out += value[i].authorName + ' ' + value[i].authorSurname + ', ';
		}
		out += value[value.length - 1].authorName + ' ' + value[value.length - 1].authorSurname;
		return out;
	}

	transformCategories(value: BookCategory[]) {
		let out = '';
		for (let i = 0; i < value.length - 1; i++) {
			out += value[i].bookCategoryName + ', ';
		}
		out += value[value.length - 1].bookCategoryName;
		return out;
	}

}
