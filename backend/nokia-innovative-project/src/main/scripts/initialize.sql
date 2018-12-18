INSERT INTO book_status (id, status_name) VALUES (1, 'AVAILABLE');
INSERT INTO book_status (id, status_name) VALUES (2, 'BORROWED');
INSERT INTO book_status (id, status_name) VALUES (3, 'RESERVED');
INSERT INTO book_status (id, status_name) VALUES (4, 'UNKNOWN');


INSERT INTO book_details (cover_picture_url, publication_date, description, isbn, title) VALUES
  ('https://about.canva.com/wp-content/uploads/sites/3/2015/01/art_bookcover.png'
    , '2018-02-02'
    , 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque hendrerit elit ut venenatis feugiat. Suspendisse maximus dui ac risus ultricies, a accumsan risus semper. Etiam eu fringilla amet. '
    , '56756756784'
    , 'The World of Abstract Art');
INSERT INTO book_details (cover_picture_url, publication_date, description, isbn, title) VALUES
  ('https://www.creativindie.com/wp-content/uploads/2013/10/Enchantment-Book-Cover-Best-Seller1.jpg'
    , '1999-01-02'
    , 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque hendrerit elit ut venenatis feugiat. Suspendisse maximus dui ac risus ultricies, a accumsan risus semper. Etiam eu fringilla amet. '
    , '12312312314'
    , 'Enchantment');
INSERT INTO book_details (cover_picture_url, publication_date, description, isbn, title) VALUES
  ('https://cdn-images-1.medium.com/max/1200/1*cNAPNAIDIHH0G-toMJhjxg.png'
    , '1996-01-05'
    , 'There''s nothing that hard-core Unix and Linux users are more fanatical about than their text editor. Editors are the subject of adoration and worship, or of scorn and ridicule, depending upon whether the topic of discussion is your editor or someone else''s.
vi has been the standard editor for close to 30 years. Popular on Unix and Linux, it has a growing following on Windows systems, too. Most experienced system administrators cite vi as their tool of choice. And since 1986, this book has been the guide for vi. '
    , '456347569012'
    , 'Learning The vi');
INSERT INTO book_details (cover_picture_url, publication_date, description, isbn, title) VALUES
  ('https://i.pinimg.com/236x/ee/15/e2/ee15e271f8e2467560ecf0d12ffb8ca5--o-reilly-programming.jpg'
    , '1980-01-05'
    , 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque hendrerit elit ut venenatis feugiat. Suspendisse maximus dui ac risus ultricies, a accumsan risus semper. Etiam eu fringilla amet. '
    , '23423423411'
    , 'Banishing The Microsoft');
INSERT INTO book_details (cover_picture_url, publication_date, description, isbn, title) VALUES
  ('https://i.pinimg.com/474x/f2/39/34/f239346d5830f9382eee8400e990959c--software-development-computer-science.jpg'
    , '1980-01-05'
    , 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque hendrerit elit ut venenatis feugiat. Suspendisse maximus dui ac risus ultricies, a accumsan risus semper. Etiam eu fringilla amet. '
    , '76576565444'
    , 'Useless Git Commit Messages');
INSERT INTO book_details (cover_picture_url, publication_date, description, isbn, title) VALUES
  ('https://i.pinimg.com/564x/70/bd/5f/70bd5f632826d5d0c6ee035e6ce97ddf.jpg'
    , '2010-02-02'
    , 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque hendrerit elit ut venenatis feugiat. Suspendisse maximus dui ac risus ultricies, a accumsan risus semper. Etiam eu fringilla amet. '
    , '76576576543'
    , 'Solving Imaginary Scaling Issues');
INSERT INTO book_details (cover_picture_url, publication_date, description, isbn, title) VALUES
  ('https://d3ansictanv2wj.cloudfront.net/expect-2-d45f737c6b69f2c3ff53c765456ca541.jpg'
    , '1980-01-05'
    , 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque hendrerit elit ut venenatis feugiat. Suspendisse maximus dui ac risus ultricies, a accumsan risus semper. Etiam eu fringilla amet. '
    , '76576576576'
    , 'Exploring Expect');
INSERT INTO book_details (cover_picture_url, publication_date, description, isbn, title) VALUES
  ('https://bit.ly/2DJuhzi'
    , '1980-01-05'
    , 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque hendrerit elit ut venenatis feugiat. Suspendisse maximus dui ac risus ultricies, a accumsan risus semper. Etiam eu fringilla amet. '
    , '23523523525'
    , 'The Lives Inside Your Head');

INSERT INTO book_category (book_category_name) VALUES ('Guide');
INSERT INTO book_category (book_category_name) VALUES ('Programming');
INSERT INTO book_category (book_category_name) VALUES ('Novel');
INSERT INTO book_category (book_category_name) VALUES ('Cooking');
INSERT INTO book_category (book_category_name) VALUES ('Autobiography');
INSERT INTO book_category (book_category_name) VALUES ('Politics');
INSERT INTO book_category (book_category_name) VALUES ('Sci-fi');
INSERT INTO book_category (book_category_name) VALUES ('Religion');
INSERT INTO book_category (book_category_name) VALUES ('Education');

INSERT INTO book_details_categories VALUES (1, 1);
INSERT INTO book_details_categories VALUES (2, 3);
INSERT INTO book_details_categories VALUES (3, 2);
INSERT INTO book_details_categories VALUES (3, 1);
INSERT INTO book_details_categories VALUES (4, 2);
INSERT INTO book_details_categories VALUES (4, 1);
INSERT INTO book_details_categories VALUES (5, 2);
INSERT INTO book_details_categories VALUES (5, 1);
INSERT INTO book_details_categories VALUES (8, 2);
INSERT INTO book_details_categories VALUES (8, 1);

INSERT INTO author (author_full_name) VALUES ('Edgar Jander');
INSERT INTO author (author_full_name) VALUES ('Lucas Newell');
INSERT INTO author (author_full_name) VALUES ('Robbie Torok');
INSERT INTO author (author_full_name) VALUES ('Raymond Kertis');
INSERT INTO author (author_full_name) VALUES ('Emmet Brenig');
INSERT INTO author (author_full_name) VALUES ('Andrzej Sapkowski');

INSERT INTO book_details_authors VALUES (1, 1);
INSERT INTO book_details_authors VALUES (2, 1);
INSERT INTO book_details_authors VALUES (3, 1);
INSERT INTO book_details_authors VALUES (4, 2);
INSERT INTO book_details_authors VALUES (5, 2);
INSERT INTO book_details_authors VALUES (6, 3);
INSERT INTO book_details_authors VALUES (7, 4);
INSERT INTO book_details_authors VALUES (7, 5);


INSERT INTO book (signature, book_details_id, book_status_id, comments) VALUES ('booka', 1, 1, 'dirty');
INSERT INTO book (signature, book_details_id, book_status_id, comments) VALUES ('bookaAA', 1, 2, 'putti');
INSERT INTO book (signature, book_details_id, book_status_id, comments) VALUES ('bookB', 2, 2, 'missing pages');
INSERT INTO book (signature, book_details_id, book_status_id, comments) VALUES ('YHNANK', 3, 3, 'bad');
INSERT INTO book (signature, book_details_id, book_status_id, comments) VALUES ('ALMNUM', 4, 3, 'cool');
INSERT INTO book (signature, book_details_id, book_status_id, comments) VALUES ('SIG123', 5, 1, 'stupid');
INSERT INTO book (signature, book_details_id, book_status_id, comments) VALUES ('OUF', 6, 1, 'lame');



