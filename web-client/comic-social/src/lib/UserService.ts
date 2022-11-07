
export interface User {
    username: string;
    id: string;
}

export class UserService {

    async login(username: string): Promise<User> {
        const response = await fetch(`/api/user/?username=${username}`);
        if(!response.ok) {
            return Promise.reject('Login failed');
        }

        const user: User = await response.json();

        this.user = user;
        return user;
    }

    public get user(): User | null {
        const text = sessionStorage.getItem('user');
        return text ? JSON.parse(text) : null;
    }

    private set user(user: User | null) {
        user && sessionStorage.setItem('user', JSON.stringify(user));
    }

}

const service = new UserService();

export { service as userService };
