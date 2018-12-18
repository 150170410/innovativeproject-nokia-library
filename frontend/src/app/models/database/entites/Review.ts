import { User } from './User';

export class Review {
  id: number;
  comment: string;
  creationDate: Date;
  lastEditionDate: Date;
  user: User;
}
