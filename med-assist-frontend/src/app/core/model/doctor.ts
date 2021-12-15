
export interface Doctor {
    id: string;
    firstName: string;
    lastName: string;
    username: string;
    email: string;
    authorities: string[];
    enabled: boolean;
    specialty: string;
    telephoneNumber: string;

    accessToken: string;
}
