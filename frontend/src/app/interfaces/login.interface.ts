export interface User{
    username: string,
    password: string
}
export class User {
    constructor(
        username: string, password: string
    ) {
        this.username = username,
        this.password = password
    }
}