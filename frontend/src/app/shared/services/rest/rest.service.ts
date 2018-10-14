import { Injectable } from '@angular/core';
import { API_URL } from '../../../config';
import axios from 'axios';
import { config } from 'rxjs/index';

@Injectable()
export class RestService {

	constructor() {
	}

	post<T>(resourceName: string, params?) {
		const url = `${API_URL}/${resourceName}`;
		return axios.post(url, params)
		.then(response => response)
		.catch(error => error);
	}

	remove<T>(resourceName: string, id: any) {
		const url = `${API_URL}/${resourceName}/${id}`;
		return axios.delete(url)
		.then(response => response)
		.catch(error => error);
	}

	update<T>(resourceName: string, id: any, params?) {
		const url = `${API_URL}/${resourceName}/${id}`;
		return axios.put(url, params)
		.then(response => response)
		.catch(error => error);
	}

	getOne<T>(id: any, resourceName: string) {
		const url = `${API_URL}/${resourceName}/${id}`;
		return axios.get(url)
		.then(function (response) {
			return response;
		})
		.catch(function (error) {
			return error;
		});
	}

	getAll<T>(resourceName: String) {

		const url = `${API_URL}/${resourceName}`;
		return axios.get(url)
		.then(function (response) {
			return response;
		}).catch((error) => {
			if (error.message === 'Network Error') {
				return error;
			}
			return error.response;
		});
	}
}
