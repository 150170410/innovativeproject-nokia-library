import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
	name: 'roles'
})
export class RolesPipe implements PipeTransform {

	transform(role: any, type: string): any {
		if (type === 'ROLE_ADMIN') {
			return 'Admin'
		} else if (type === 'ROLE_EMPLOYEE') {
			return 'Employee';
		}

		return null;
	}

}
