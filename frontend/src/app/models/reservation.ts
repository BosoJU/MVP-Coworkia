export interface Reservation {
    id: number; 
    zone: {
        id: number;
        code: string;
        nom: string;
        capacite: number;
    };
    user: {
        id: number;
        email: string;
        nom: string;
        prenom: string;
    };
    dateDebut: string;
    dateFin: string;
    status: string;
}