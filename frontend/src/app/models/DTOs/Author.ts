export class AuthorDTO {
  authorName: string;
  authorSurname: string;
  authorDescription: string;

  constructor(authorName: string, authorSurname: string,  authorDescription: string) {
    this.authorName = this.authorName;
    this.authorSurname = authorSurname;
    this.authorDescription = authorDescription;
  }
}
