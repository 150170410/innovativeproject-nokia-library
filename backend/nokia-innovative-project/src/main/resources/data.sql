INSERT INTO book_status (status_name) VALUES ('AVAILABLE');
INSERT INTO book_status (status_name) VALUES ('AWAITING');
INSERT INTO book_status (status_name) VALUES ('BORROWED');
INSERT INTO book_status (status_name) VALUES ('RESERVED');
INSERT INTO book_status (status_name) VALUES ('UNAVAILABLE');

INSERT INTO book_category (book_category_name, is_removable) VALUES ('Guide', false);
INSERT INTO book_category (book_category_name) VALUES ('Programming');
INSERT INTO book_category (book_category_name, is_removable) VALUES ('Novel', false);
INSERT INTO book_category (book_category_name, is_removable) VALUES ('Cooking', false);
INSERT INTO book_category (book_category_name, is_removable) VALUES ('Autobiography', false);
INSERT INTO book_category (book_category_name) VALUES ('Politics');
INSERT INTO book_category (book_category_name, is_removable) VALUES ('Sci-fi', false);
INSERT INTO book_category (book_category_name) VALUES ('Religion');
INSERT INTO book_category (book_category_name) VALUES ('Education');
INSERT INTO book_category (book_category_name) VALUES ('Astronomy');
INSERT INTO book_category (book_category_name) VALUES ('Science');
INSERT INTO book_category (book_category_name) VALUES ('Java');

INSERT INTO author (author_full_name) VALUES ('Edgar Jander');
INSERT INTO author (author_full_name) VALUES ('Lucas Newell');
INSERT INTO author (author_full_name) VALUES ('Robbie Torok');
INSERT INTO author (author_full_name) VALUES ('Raymond Kertis');
INSERT INTO author (author_full_name) VALUES ('Emmet Brenig');
INSERT INTO author (author_full_name) VALUES ('Andrzej Sapkowski');
INSERT INTO author (author_full_name, is_removable) VALUES ('Sejal Badani', false);
INSERT INTO author (author_full_name, is_removable) VALUES ('Rhys Bowen', false);
INSERT INTO author (author_full_name) VALUES ('Melinda Leigh');
INSERT INTO author (author_full_name, is_removable) VALUES ('Jeff Kinney', false);
INSERT INTO author (author_full_name, is_removable) VALUES ('Michelle Obama', false);
INSERT INTO author (author_full_name, is_removable) VALUES ('Samin Nosrat', false);
INSERT INTO author (author_full_name, is_removable) VALUES ('Stephen Hawking', false);
INSERT INTO author (author_full_name, is_removable) VALUES ('George Orwell', false);


INSERT INTO book_details (isbn, title, description, cover_picture_url, publication_date, is_removable) VALUES (
  '9781542048279',
  'The Storytellers Secret: A Novel',
  'Nothing prepares Jaya, a New York journalist, for the heartbreak of her third miscarriage and the slow unraveling of her marriage in its wake. Desperate to assuage her deep anguish, she decides to go to India to uncover answers to her familys past.Intoxicated by the sights, smells, and sounds she experiences, Jaya becomes an eager student of the culture. But it is Ravi—her grandmothers former servant and trusted confidant—who reveals the resilience, struggles, secret love, and tragic fall of Jayas pioneering grandmother during the British occupation. Through her courageous grandmothers arrestingly romantic and heart-wrenching story, Jaya discovers the legacy bequeathed to her and a strength that, until now, she never knew was possible.',
  'https://images-na.ssl-images-amazon.com/images/I/51GC3g1SSXL._SX331_BO1,204,203,200_.jpg',
  '2018-09-01',
  false);
INSERT INTO book_details (isbn, title, description, cover_picture_url, publication_date, is_removable) VALUES (
  '9781503941359',
  'In Farleigh Field: A Novel of World War II',
  'World War II comes to Farleigh Place, the ancestral home of Lord Westerham and his five daughters, when a soldier with a failed parachute falls to his death on the estate. After his uniform and possessions raise suspicions, MI5 operative and family friend Ben Cresswell is covertly tasked with determining if the man is a German spy. The assignment also offers Ben the chance to be near Lord Westerhams middle daughter, Pamela, whom he furtively loves. But Pamela has her own secret: she has taken a job at Bletchley Park, the British code-breaking facility.As Ben follows a trail of spies and traitors, which may include another member of Pamelas family, he discovers that some within the realm have an appalling, history-altering agenda. Can he, with Pamelas help, stop them before England falls?Inspired by the events and people of World War II, writer Rhys Bowen crafts a sweeping and riveting saga of class, family, love, and betrayal.',
  'https://images-na.ssl-images-amazon.com/images/I/51Xg26g1znL._SX331_BO1,204,203,200_.jpg',
  '2017-03-01',
  false);
INSERT INTO book_details (isbn, title, description, cover_picture_url, publication_date, is_removable) VALUES (
  '9781419727436',
  'Diary of a Wimpy Kid #13: Meltdown',
  'When snow shuts down Greg Heffleys middle school, his neighborhood transforms into a wintry battlefield. Rival groups fight over territory, build massive snow forts, and stage epic snowball fights. And in the crosshairs are Greg and his trusty best friend, Rowley Jefferson.Its a fight for survival as Greg and Rowley navigate alliances, betrayals, and warring gangs in a neighborhood meltdown. When the snow clears, will Greg and Rowley emerge as heroes? Or will they even survive to see another day?',
  'https://images-na.ssl-images-amazon.com/images/I/512EFepj8GL._SX340_BO1,204,203,200_.jpg',
  '2018-10-30',
  false);
INSERT INTO book_details (isbn, title, description, cover_picture_url, publication_date, is_removable) VALUES (
  '9781524763138',
  'Becoming',
  'In a life filled with meaning and accomplishment, Michelle Obama has emerged as one of the most iconic and compelling women of our era. As First Lady of the United States of America—the first African American to serve in that role—she helped create the most welcoming and inclusive White House in history, while also establishing herself as a powerful advocate for women and girls in the U.S. and around the world, dramatically changing the ways that families pursue healthier and more active lives, and standing with her husband as he led America through some of its most harrowing moments. Along the way, she showed us a few dance moves, crushed Carpool Karaoke, and raised two down-to-earth daughters under an unforgiving media glare.In her memoir, a work of deep reflection and mesmerizing storytelling, Michelle Obama invites readers into her world, chronicling the experiences that have shaped her—from her childhood on the South Side of Chicago to her years as an executive balancing the demands of motherhood and work, to her time spent at the worlds most famous address. With unerring honesty and lively wit, she describes her triumphs and her disappointments, both public and private, telling her full story as she has lived it—in her own words and on her own terms. Warm, wise, and revelatory, Becoming is the deeply personal reckoning of a woman of soul and substance who has steadily defied expectations—and whose story inspires us to do the same.',
  'https://images-na.ssl-images-amazon.com/images/I/414JfiBCutL._SX327_BO1,204,203,200_.jpg',
  '2018-11-13',
  false);
INSERT INTO book_details (isbn, title, description, cover_picture_url, publication_date, is_removable) VALUES (
  '9781476753836',
  'Salt, Fat, Acid, Heat: Mastering the Elements of Good Cooking',
  'A visionary new master class in cooking that distills decades of professional experience into just four simple elements, from the woman declared “Americas next great cooking teacher” by Alice Waters.In the tradition of The Joy of Cooking and How to Cook Everything comes Salt, Fat, Acid, Heat, an ambitious new approach to cooking by a major new culinary voice. Chef and writer Samin Nosrat has taught everyone from professional chefs to middle school kids to author Michael Pollan to cook using her revolutionary, yet simple, philosophy. Master the use of just four elements—Salt, which enhances flavor; Fat, which delivers flavor and generates texture; Acid, which balances flavor; and Heat, which ultimately determines the texture of food—and anything you cook will be delicious. By explaining the hows and whys of good cooking, Salt, Fat, Acid, Heat will teach and inspire a new generation of cooks how to confidently make better decisions in the kitchen and cook delicious meals with any ingredients, anywhere, at any time.Echoing Samins own journey from culinary novice to award-winning chef, Salt, Fat Acid, Heat immediately bridges the gap between home and professional kitchens. With charming narrative, illustrated walkthroughs, and a lighthearted approach to kitchen science, Samin demystifies the four elements of good cooking for everyone. Refer to the canon of 100 essential recipes—and dozens of variations—to put the lessons into practice and make bright, balanced vinaigrettes, perfectly caramelized roast vegetables, tender braised meats, and light, flaky pastry doughs.Featuring 150 illustrations and infographics that reveal an atlas to the world of flavor by renowned illustrator Wendy MacNaughton, Salt, Fat, Acid, Heat will be your compass in the kitchen. Destined to be a classic, it just might be the last cookbook youll ever need.',
  'https://images-na.ssl-images-amazon.com/images/I/81ABBZDod%2BL._AC_SR201,266_.jpg',
  '2018-04-25',
  false);

INSERT INTO book_details (isbn, title, description, cover_picture_url, publication_date, is_removable) VALUES (
  '9780553380163',
  'A Brief History of Time',
  'A landmark volume in science writing by one of the great minds of our time, Stephen Hawkings book explores such profound questions as: How did the universe begin—and what made its start possible? Does time always flow forward? Is the universe unending—or are there boundaries? Are there other dimensions in space? What will happen when it all ends?
Told in language we all can understand, A Brief History of Time plunges into the exotic realms of black holes and quarks, of antimatter and “arrows of time,” of the big bang and a bigger God—where the possibilities are wondrous and unexpected. With exciting images and profound imagination, Stephen Hawking brings us closer to the ultimate secrets at the very heart of creation.',
  'https://images-na.ssl-images-amazon.com/images/I/51%2BGySc8ExL._SX333_BO1,204,203,200_.jpg',
  '1998-09-01',
  false);
INSERT INTO book_details (isbn, title, description, cover_picture_url, publication_date, is_removable) VALUES (
  '9780451524935',
  '1984 (Signet Classics)',
  '“The Party told you to reject the evidence of your eyes and ears. It was their final, most essential command.”Winston Smith toes the Party line, rewriting history to satisfy the demands of the Ministry of Truth. With each lie he writes, Winston grows to hate the Party that seeks power for its own sake and persecutes those who dare to commit thoughtcrimes. But as he starts to think for himself, Winston cant escape the fact that Big Brother is always watching...A startling and haunting vision of the world, 1984 is so powerful that it is completely convincing from start to finish. No one can deny the influence of this novel, its hold on the imaginations of multiple generations of readers, or the resiliency of its admonitions—a legacy that seems only to grow with the passage of time.',
  'https://images-na.ssl-images-amazon.com/images/I/31lWUHDG7uL._SX282_BO1,204,203,200_.jpg',
  '1950-07-01',
  false);


INSERT INTO book_details_authors VALUES (1, 7);
INSERT INTO book_details_authors VALUES (2, 8);
INSERT INTO book_details_authors VALUES (3, 10);
INSERT INTO book_details_authors VALUES (4, 11);
INSERT INTO book_details_authors VALUES (5, 12);
INSERT INTO book_details_authors VALUES (6, 13);
INSERT INTO book_details_authors VALUES (7, 14);

INSERT INTO book_details_categories VALUES (1, 3);
INSERT INTO book_details_categories VALUES (2, 3);
INSERT INTO book_details_categories VALUES (3, 1);
INSERT INTO book_details_categories VALUES (4, 5);
INSERT INTO book_details_categories VALUES (5, 4);
INSERT INTO book_details_categories VALUES (6, 5);
INSERT INTO book_details_categories VALUES (6, 7);
INSERT INTO book_details_categories VALUES (7, 7);

INSERT INTO address (building, city) VALUES ('West Link', 'Wrocław');

INSERT INTO "user" (email, first_name, last_name, password, address_id)
VALUES ('user@user.com', 'User', 'User', '$2a$10$q42WZU7b.2emX8QlMwWV/.WWY.hSAB5by6mI1Tkq58XMi7ij0KbBy', 1);

INSERT INTO "user" (email, first_name, last_name, password, address_id)
VALUES ('wojtek@wojtek.com', 'Wojtek', 'Wojtek', '$2a$10$q42WZU7b.2emX8QlMwWV/.WWY.hSAB5by6mI1Tkq58XMi7ij0KbBy', 1);

INSERT INTO "user" (email, first_name, last_name, password, address_id)
VALUES ('jacek@jacek.com', 'Jacek', 'Jacek', '$2a$10$q42WZU7b.2emX8QlMwWV/.WWY.hSAB5by6mI1Tkq58XMi7ij0KbBy', 1);

INSERT INTO "user" (email, first_name, last_name, password, address_id)
VALUES ('alex@alex.com', 'Alex', 'Alex', '$2a$10$q42WZU7b.2emX8QlMwWV/.WWY.hSAB5by6mI1Tkq58XMi7ij0KbBy', 1);

INSERT INTO "user" (email, first_name, last_name, password, address_id)
VALUES ('mikolaj@mikolaj.com', 'Mikolaj', 'Mikolaj', '$2a$10$q42WZU7b.2emX8QlMwWV/.WWY.hSAB5by6mI1Tkq58XMi7ij0KbBy', 1);

INSERT INTO "user" (email, first_name, last_name, password, address_id)
VALUES ('admin@admin.com', 'Admin', 'Admin', '$2a$10$Ro6ctBdfMovhKqJdYFgnrO58tTf3uQ7hrY9tm93Uclb0fiP3fQMKS', 1);
INSERT INTO role (role) VALUES ('ROLE_EMPLOYEE');
INSERT INTO role (role) VALUES ('ROLE_ADMIN');
INSERT INTO user_roles (user_id, roles_id) VALUES (1, 1);
INSERT INTO user_roles (user_id, roles_id) VALUES (2, 1);
INSERT INTO user_roles (user_id, roles_id) VALUES (3, 1);
INSERT INTO user_roles (user_id, roles_id) VALUES (4, 1);
INSERT INTO user_roles (user_id, roles_id) VALUES (5, 1);
INSERT INTO user_roles (user_id, roles_id) VALUES (6, 1);
INSERT INTO user_roles (user_id, roles_id) VALUES (6, 2);

INSERT INTO book (signature, book_details_id, book_status_id, comments, actual_owner_id) VALUES ('booka', 1, 1, 'dirty', 6);
INSERT INTO book (signature, book_details_id, book_status_id, comments, actual_owner_id) VALUES ('bookaAA', 1, 1, 'putti', 6);
INSERT INTO book (signature, book_details_id, book_status_id, comments, actual_owner_id) VALUES ('bookB', 2, 1, 'missing pages', 6);
INSERT INTO book (signature, book_details_id, book_status_id, comments, actual_owner_id) VALUES ('YHNANK', 3, 1, 'bad', 6);
INSERT INTO book (signature, book_details_id, book_status_id, comments, actual_owner_id) VALUES ('ALMNUM', 4, 1, 'cool', 6);
INSERT INTO book (signature, book_details_id, book_status_id, comments, actual_owner_id) VALUES ('SIG123', 5, 1, 'stupid', 6);
INSERT INTO book (signature, book_details_id, book_status_id, comments, actual_owner_id) VALUES ('OUF', 6, 1, 'chairman', 6);
INSERT INTO book (signature, book_details_id, book_status_id, comments, actual_owner_id) VALUES ('ui ui', 7, 1, 'gut', 6);
INSERT INTO book (signature, book_details_id, book_status_id, comments, actual_owner_id) VALUES ('miu miau', 7, 1, 'missing cover', 6);
INSERT INTO book (signature, book_details_id, book_status_id, comments, actual_owner_id) VALUES ('nubu', 7, 1, 'juj', 6);
INSERT INTO book (signature, book_details_id, book_status_id, comments, actual_owner_id) VALUES ('pook', 7, 1, 'is fine', 6);

INSERT INTO book_to_order (isbn, title, requested_user)  VALUES ('0439708184', 'Harry Potter and the Sorcerer''s Stone', 1);
INSERT INTO book_to_order (isbn, title, requested_user)  VALUES ('9780679745587', 'In Cold Blood', 2);
INSERT INTO book_to_order (isbn, title, requested_user)  VALUES('9780679732761', 'Invisible Man', 3);
INSERT INTO book_to_order (isbn, title, requested_user)  VALUES ('0061958271', 'Little House on the Prairie', 4);