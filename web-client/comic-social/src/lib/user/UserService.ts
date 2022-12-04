import type {Writable} from 'svelte/store';
import {writable} from 'svelte/store';

export interface User {
    username: string;
    id: string;
}

export function emptyUser(): User {
    return {
        username: '',
        id: '',
    }
}

export class UserService {

    #user: Writable<User> = writable(this.loadUser());

    async login(username: string): Promise<User> {
        const response = await fetch(`/user-api/user/?username=${username}`);
        if(!response.ok) {
            return Promise.reject(response);
        }

        return await response.json();
    }

    subscribe(fn: (g: User) => void): () => void {
        return this.#user.subscribe(fn);
    }

    set user(user: User) {
        sessionStorage.setItem('user', JSON.stringify(user));
        this.#user.set(user);
    }

    private loadUser(): User {
        const text = sessionStorage.getItem('user');
        return text ? JSON.parse(text) : emptyUser();
    }
}

const service = new UserService();

export { service as userService };
