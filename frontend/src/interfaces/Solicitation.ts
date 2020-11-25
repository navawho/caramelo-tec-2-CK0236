import Pet from './Pet';
import User from './User';

export default interface Adoption {
  id: number;
  pet: Pet;
  user: User;
  accepted?: boolean;
}