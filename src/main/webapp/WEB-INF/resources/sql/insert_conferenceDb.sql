use conferencedb;

insert positions (position) 
value('Admin'),
     ('Moderator'),
     ('Speaker'),
     ('User');
     
	insert language (language) 
value('UA'),
     ('EN'),
     ('RU');

-- You can execute this script or register this user in application
-- (emails and passwords are real except 'admin' and 'moder');
insert users (name, surname, email, password, position, language)
value('Юій','Юдильцев', 'userudilcev@gmail.com','user1111',4,1),
     ('Ulia','Ulieva', 'userulieva@gmail.com','user2222',4,2),
     ('Святослав','Семин', 'speakersemin@gmail.com','speaker1111',3,1),
     ('Савелий','Синий', 'sinijsavelij@gmail.com','speaker2222',3,3),
     ('admin','admin', 'admin@ukr.net','admin',1,3),
     ('moder','moder', 'moder@ukr.net','moder',2,1);
    
     