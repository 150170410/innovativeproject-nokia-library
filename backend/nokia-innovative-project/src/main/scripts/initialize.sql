insert into book_details (cover_picture_url, date_of_publication, description, isbn, title)  values
  ('https://about.canva.com/wp-content/uploads/sites/3/2015/01/art_bookcover.png'
  ,'2021-02-02'
  ,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque hendrerit elit ut venenatis feugiat. Suspendisse maximus dui ac risus ultricies, a accumsan risus semper. Etiam eu fringilla amet. ' ||
  ,'56756756784'
  ,'The World of Abstract Art');
insert into book_details (cover_picture_url, date_of_publication, description, isbn, title)  values
  ('https://www.creativindie.com/wp-content/uploads/2013/10/Enchantment-Book-Cover-Best-Seller1.jpg'
  ,'1999-01-02'
  ,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque hendrerit elit ut venenatis feugiat. Suspendisse maximus dui ac risus ultricies, a accumsan risus semper. Etiam eu fringilla amet. ' ||
  ,'12312312314'
  ,'Enchantment');
insert into book_details (cover_picture_url, date_of_publication, description, isbn, title)  values
  ('https://cdn-images-1.medium.com/max/1200/1*cNAPNAIDIHH0G-toMJhjxg.png'
  ,'1996-01-05'
  ,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque hendrerit elit ut venenatis feugiat. Suspendisse maximus dui ac risus ultricies, a accumsan risus semper. Etiam eu fringilla amet. ' ||
  ,'456347569012'
  ,'Learning The vi');
insert into book_details (cover_picture_url, date_of_publication, description, isbn, title)  values
  ('https://i.pinimg.com/236x/ee/15/e2/ee15e271f8e2467560ecf0d12ffb8ca5--o-reilly-programming.jpg'
  ,'1980-01-05'
  ,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque hendrerit elit ut venenatis feugiat. Suspendisse maximus dui ac risus ultricies, a accumsan risus semper. Etiam eu fringilla amet. ' ||
  ,'23423423411'
  ,'Banishing The Microsoft');
insert into book_details (cover_picture_url, date_of_publication, description, isbn, title)  values
  ('https://i.pinimg.com/474x/f2/39/34/f239346d5830f9382eee8400e990959c--software-development-computer-science.jpg'
  ,'1980-01-05'
  ,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque hendrerit elit ut venenatis feugiat. Suspendisse maximus dui ac risus ultricies, a accumsan risus semper. Etiam eu fringilla amet. ' ||
  ,'76576565444'
  ,'Useless Git Commit Messages');
insert into book_details (cover_picture_url, date_of_publication, description, isbn, title)  values
  ('https://i.pinimg.com/564x/70/bd/5f/70bd5f632826d5d0c6ee035e6ce97ddf.jpg'
  ,'2010-02-02'
  ,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque hendrerit elit ut venenatis feugiat. Suspendisse maximus dui ac risus ultricies, a accumsan risus semper. Etiam eu fringilla amet. ' ||
  ,'76576576543'
  ,'Solving Imaginary Scaling Issues');
insert into book_details (cover_picture_url, date_of_publication, description, isbn, title)  values
  ('https://d3ansictanv2wj.cloudfront.net/expect-2-d45f737c6b69f2c3ff53c765456ca541.jpg'
  ,'1980-01-05'
  ,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque hendrerit elit ut venenatis feugiat. Suspendisse maximus dui ac risus ultricies, a accumsan risus semper. Etiam eu fringilla amet. ' ||
  ,'76576576576'
  ,'Exploring Expect');
insert into book_details (cover_picture_url, date_of_publication, description, isbn, title)  values
  ('https://bit.ly/2DJuhzi'
  ,'1980-01-05'
  ,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque hendrerit elit ut venenatis feugiat. Suspendisse maximus dui ac risus ultricies, a accumsan risus semper. Etiam eu fringilla amet. ' ||
  ,'23523523525'
  ,'The Lives Inside Your Head');

insert into book_category (book_category_name) values ('Guide');
insert into book_category (book_category_name) values ('Programming');
insert into book_category (book_category_name) values ('Movel');

insert into book_details_categories values (1, 1);
insert into book_details_categories values (2, 3);
insert into book_details_categories values (3, 2);
insert into book_details_categories values (3, 1);
insert into book_details_categories values (4, 2);
insert into book_details_categories values (4, 1);
insert into book_details_categories values (5, 2);
insert into book_details_categories values (5, 1);
insert into book_details_categories values (8, 2);
insert into book_details_categories values (8, 1);

insert into author (author_description, author_name, author_surname) values ('Lorem ipsum dolor sit amet. ', 'Edgar', 'Jander');
insert into author (author_description, author_name, author_surname) values ('Lorem ipsum dolor sit amet. ', 'Lucas', 'Newell');
insert into author (author_description, author_name, author_surname) values ('Lorem ipsum dolor sit amet. ', 'Robbie', 'Torok');
insert into author (author_description, author_name, author_surname) values ('Lorem ipsum dolor sit amet. ', 'Raymond', 'Kertis');
insert into author (author_description, author_name, author_surname) values ('Lorem ipsum dolor sit amet. ', 'Emmet', 'Brenig');

insert into book_details_authors values (1,1);
insert into book_details_authors values (2,1);
insert into book_details_authors values (3,1);
insert into book_details_authors values (4,2);
insert into book_details_authors values (5,2);
insert into book_details_authors values (6,3);
insert into book_details_authors values (7,4);
insert into book_details_authors values (7,5);





