
export interface User {
    id: string;
    firstName: string;
    lastName: string;
    username: string;
    email: string;
    authorities: string[];
    enabled: boolean;

    accessToken: string;
}
