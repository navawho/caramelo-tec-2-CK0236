import User from './User';

export default interface Pet {
	id: number;
	user: User;
	name: string;
	port: 'Pequeno' | 'Médio' | 'Grande';
	type: 'Cachorro' | 'Gato';
	sex: 'Macho' | 'Fêmea';
	available: boolean;
	imageUrl: string;
	description: string;
	birthDate: string;
	createdAt?: string;
}
