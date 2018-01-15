/* Please do not delete the sql commands underneath this commentbar! These are essential for creating the database! */

USE master
IF EXISTS(select * from sys.databases where name='NetflixStatistix')
DROP DATABASE NetflixStatistix

CREATE DATABASE NetflixStatistix

USE NetflixStatistix

/* Create Aanbod Tabel */
CREATE TABLE Library (
	Id INT PRIMARY KEY,
	Type VARCHAR(10)
);

/* Creatie Film Tabel */

CREATE TABLE Movies (
	Id INT NOT NULL,
	Title VARCHAR(120) NOT NULL,
	Pg INT NOT NULL,
	Language VARCHAR(20) NOT NULL,
	Length TIME NOT NULL,
	Genre VARCHAR(20) NOT NULL

	CONSTRAINT PK_Movies PRIMARY KEY (Id),
	CONSTRAINT FK_Movies FOREIGN KEY (Id) REFERENCES Library(Id)
		ON DELETE CASCADE
		ON UPDATE CASCADE
);

/* Creatie Serie Tabel */

CREATE TABLE Shows (
	Show VARCHAR(120) PRIMARY KEY,
	Season VARCHAR(6) NOT NULL,
	Pg INT NOT NULL,
	Language VARCHAR(20) NOT NULL,
	Genre VARCHAR(20) NOT NULL,
	Recommended VARCHAR(120) NOT NULL
);

/* Creatie Aflevering Tabel */

CREATE TABLE Episodes (
	Id INT NOT NULL,
	Show VARCHAR(120) NOT NULL,	
	Season	VARCHAR(6) NOT NULL,
	Title VARCHAR(120) NOT NULL,	
	Length TIME NOT NULL

	CONSTRAINT PK_Episodes PRIMARY KEY (Id),
	CONSTRAINT FK_Episodes_Library FOREIGN KEY (Id) REFERENCES Library(Id),
	CONSTRAINT FK_Episodes_Shows FOREIGN KEY (Show) REFERENCES Shows(Show)
		ON DELETE CASCADE
		ON UPDATE CASCADE
);

/* Creatie Account Tabel */

CREATE TABLE Users (
	SubscriberId INT PRIMARY KEY,
	Name VARCHAR(40) NOT NULL,
	Street VARCHAR(50) NOT NULL,
	PostalCode VARCHAR(7) NOT NULL,
	HouseNumber VARCHAR(4) NOT NULL,
	City VARCHAR(40) NOT NULL
);

/* Creatie Profiel Tabel */

CREATE TABLE Profiles (
	SubscriberId INT NOT NULL,
	ProfileName VARCHAR(20) NOT NULL,
	DateOfBirth DATE NOT NULL,

	CONSTRAINT PK_Profiles PRIMARY KEY (SubscriberId, ProfileName),
	CONSTRAINT FK_Profiles FOREIGN KEY (SubscriberId) REFERENCES Users(SubscriberId)
		ON DELETE CASCADE
		ON UPDATE CASCADE
);

/* Creatie Bekeken Tabel */

CREATE TABLE Watched (
	SubscriberId INT NOT NULL,	
	ProfileName	VARCHAR(20) NOT NULL,
	Watched	INT NOT NULL,
	Percentage INT NOT NULL,

	CONSTRAINT PK_Watched
		PRIMARY KEY (SubscriberId, ProfileName, Watched),
	CONSTRAINT FK_Watched_Profiles 
		FOREIGN KEY (SubscriberId, ProfileName) REFERENCES Profiles(SubscriberId, ProfileName)
			ON DELETE CASCADE
			ON UPDATE CASCADE,
	CONSTRAINT FK_Watched_Library
		FOREIGN KEY (Watched) REFERENCES Library(Id)
			ON DELETE CASCADE
			ON UPDATE CASCADE
);

/* Vullen van Aanbod Tabel */

INSERT INTO Library
VALUES
	(1010, 'Film'),
	(8001, 'Film'),
	(8002, 'Film'),
	(8004, 'Film'),
	(8008, 'Film'),
	(8010, 'Film'),
	(8011, 'Film'),
	(8012, 'Film'),
	(8014, 'Film'),
	(8016, 'Film'),
	(8017, 'Film'),
	(1001, 'Show'),
	(1002, 'Show'),
	(1003, 'Show'),
	(1004, 'Show'),
	(1005, 'Show'),
	(1006, 'Show'),
	(1007, 'Show'),
	(1008, 'Show'),
	(1009, 'Show'),
	(2000, 'Show'),
	(2001, 'Show'),
	(2002, 'Show'),
	(2003, 'Show'),
	(2004, 'Show'),
	(2005, 'Show'),
	(2006, 'Show'),
	(2007, 'Show'),
	(2008, 'Show'),
	(2009, 'Show'),
	(2010, 'Show'),
	(2011, 'Show'),
	(2012, 'Show'),
	(2013, 'Show'),
	(2014, 'Show'),
	(2015, 'Show'),
	(2016, 'Show'),
	(2017, 'Show'),
	(2018, 'Show'),
	(2019, 'Show'),
	(3001, 'Show'),
	(3002, 'Show'),
	(3003, 'Show'),
	(3004, 'Show'),
	(3005, 'Show'),
	(3006, 'Show'),
	(3007, 'Show'),
	(3008, 'Show'),
	(3009, 'Show'),
	(3010, 'Show'),
	(3101, 'Show'),
	(3102, 'Show'),
	(3103, 'Show'),
	(3105, 'Show'),
	(3104, 'Show'),
	(3106, 'Show'),
	(3107, 'Show'),
	(3108, 'Show'),
	(3109, 'Show'),
	(3110, 'Show');

/* Vullen van Film Tabel */

INSERT INTO Movies 
VALUES 
	(1010, 'The Abominable Bride', 12, 'English', '01:29', 'Detective'),
	(8001, 'The Life of Brian', 12, 'English', '01:34', 'Comedy'),
	(8002, 'Pulp Fiction', 16, 'English-USA', '02:34', 'Crime'),
	(8004, 'Pruimebloesem', 18, 'Dutch', '01:20', 'Erotic'),
	(8008, 'Reservoir Dogs', 16, 'English-USA', '01:39', 'Crime'),
	(8010, 'The Good, the Bad and the Ugly', 12, 'English-USA', '02:41', 'Western'),
	(8011, 'Andy Warhol''s Dracula', 16, 'English-USA', '01:43', 'Comedy'),
	(8012, 'Ober', 6, 'Dutch', '01:37', 'Comedy'),
	(8014, 'Der Untergang', 6, 'German', '02:58', 'War'),
	(8016, 'De helaasheid der dingen', 12, 'Dutch-Flemish', '01:48', 'Comedy'),
	(8017, 'A Clockwork Orange', 16, 'English', '02:16', 'SF');

/* Vullen van Serie Tabel */

INSERT INTO Shows
VALUES
	('Sherlock', 'S01E01', 12, 'English', 'Detective', 'Fargo'),
	('Breaking Bad', 'S01E01', 16, 'English-USA', 'Action', 'Fargo'),
	('Fargo', 'S01E01', 16 ,'Engels-Amerikaans', 'Action', 'Breaking Bad');

/* Vullen van Aflevering Tabel */

INSERT INTO Episodes
VALUES
	(1001, 'Sherlock', 'S01E01', 'A Study in Pink', '01:28'),
	(1002, 'Sherlock', 'S01E02', 'The Blind Banker', '01:28'),
	(1003, 'Sherlock', 'S01E03', 'The Great Game', '01:28'),
	(1004, 'Sherlock', 'S02E01', 'A Scandal in Belgravia', '01:28'),
	(1005, 'Sherlock', 'S02E02', 'The Hounds of Baskerville', '01:28'),
	(1006, 'Sherlock', 'S02E03', 'The Reichenbach Fall', '01:28'),
	(1007, 'Sherlock', 'S03E01', 'The Empty Hearse', '01:28'),
	(1008, 'Sherlock', 'S03E02', 'The Sign of Three', '01:28'),
	(1009, 'Sherlock', 'S03E03', 'His Last Vow', '01:28'),
	(2000, 'Breaking Bad', 'S01E01', 'Pilot', '00:58'),
	(2001, 'Breaking Bad', 'S01E02', 'Cat’s in the Bag…', '00:48'),
	(2002, 'Breaking Bad', 'S01E03', '…And the Bag’s in the River', '00:48'),
	(2003, 'Breaking Bad', 'S01E04', 'Cancer Man', '00:48'),
	(2004, 'Breaking Bad', 'S01E05', 'Gray Matter', '00:48'),
	(2005, 'Breaking Bad', 'S01E06', 'Crazy Handful of Nothin’', '00:48'),
	(2006, 'Breaking Bad', 'S01E07', 'A No-Rough-Stuff-Type Deal', '00:48'),
	(2007, 'Breaking Bad', 'S02E01', 'Seven Thirty-Seven', '00:48'),
	(2008, 'Breaking Bad', 'S02E02', 'Grilled', '00:48'),
	(2009, 'Breaking Bad', 'S02E03', 'Bit by a Dead Bee', '00:48'),
	(2010, 'Breaking Bad', 'S02E04', 'Down', '00:48'),
	(2011, 'Breaking Bad', 'S02E05', 'Breakage', '00:48'),
	(2012, 'Breaking Bad', 'S02E06', 'Peekaboo', '00:48'),
	(2013, 'Breaking Bad', 'S02E07', 'Negro Y Azul', '00:48'),
	(2014, 'Breaking Bad', 'S02E08', 'Better Call Saul', '00:48'),
	(2015, 'Breaking Bad', 'S02E09', '4 Days Out', '00:48'),
	(2016, 'Breaking Bad', 'S02E10', 'Over', '00:48'),
	(2017, 'Breaking Bad', 'S02E11', 'Mandala', '00:48'),
	(2018, 'Breaking Bad', 'S02E12', 'Phoenix', '00:48'),
	(2019, 'Breaking Bad', 'S02E13', 'ABQ', '00:48'),
	(3001, 'Fargo', 'S01E01', 'The Crocodile''s Dilemma', '01:08'),
	(3002, 'Fargo', 'S01E02', 'The Rooster Prince', '01:08'),
	(3003, 'Fargo', 'S01E03', 'A Muddy Road', '01:08'),
	(3004, 'Fargo', 'S01E04', 'Eating the Blame', '01:08'),
	(3005, 'Fargo', 'S01E05', 'The Six Ungraspables', '01:08'),
	(3006, 'Fargo', 'S01E06', 'Buridan''s Ass', '01:08'),
	(3007, 'Fargo', 'S01E07', 'Who Shaves the Barber?', '01:08'),
	(3008, 'Fargo', 'S01E08', 'The Heap', '01:08'),
	(3009, 'Fargo', 'S01E09', 'A Fox, a Rabbit, and a Cabbage', '01:08'),
	(3010, 'Fargo', 'S01E10', 'Morton''s Fork', '01:08'),
	(3101, 'Fargo', 'S02E01', 'Waiting for Dutch', '01:08'),
	(3102, 'Fargo', 'S02E02', 'Before the Law', '01:08'),
	(3103, 'Fargo', 'S02E03', 'The Myth of Sisyphus', '01:08'),
	(3104, 'Fargo', 'S02E04', 'The Gift of the Magi', '01:08'),
	(3105, 'Fargo', 'S02E05', 'Fear and Trembling', '01:08'),
	(3106, 'Fargo', 'S02E06', 'Rhinoceros', '01:08'),
	(3107, 'Fargo', 'S02E07', 'Did you do this? No, you did it!', '01:08'),
	(3108, 'Fargo', 'S02E08', 'Loplop', '01:08'),
	(3109, 'Fargo', 'S02E09', 'The Castle', '01:08'),
	(3110, 'Fargo', 'S02E10', 'Palindrome', '01:08');

/* Vullen Tabel Account */

INSERT INTO Users
VALUES
	(1215426, 'Fam. van Raalte', 'Schopenhauerdijkje', '3991 ML', '5', 'Houten'),
	(5602533, 'J. van Betlehem', 'Nietzschestraat',	'8542 BE', '99', 'Breda'),
	(5285824, 'F. de Kat', 'Kantlaan', '8542 CD', '11', 'Breda');

/* Vullen Profiel Tabel */

INSERT INTO Profiles
VALUES
	(1215426, 'Frank', '1968-01-25'),
	(1215426, 'Madelief', '2001-08-19'),
	(5602533, 'Petrus', '1999-06-26'),
	(5602533, 'Paulus', '1999-06-26'),
	(5285824, 'Fritz', '1968-08-19'),
	(5285824, 'Diana', '1988-12-25');

/* Vullen Bekeken Tabel */

INSERT INTO Watched
VALUES
	(1215426, 'Frank', 1001, 100),
	(1215426, 'Frank', 1002, 100),
	(1215426, 'Frank', 1003, 78),
	(1215426, 'Madelief', 1001, 100),
	(1215426, 'Madelief', 1002,	60),
	(1215426, 'Madelief', 3001,	91),
	(1215426, 'Madelief', 2001,	100),
	(1215426, 'Madelief', 2002,	100),
	(1215426, 'Madelief', 2003, 100),
	(1215426, 'Madelief', 2004,	22),
	(5602533, 'Petrus', 3001, 100),
	(5602533, 'Petrus',	3002, 100),
	(5602533, 'Petrus',	3010, 60),
	(5602533, 'Petrus',	8001, 100),
	(5602533, 'Petrus', 8002, 99),
	(5602533, 'Paulus',	3001, 100),
	(5602533, 'Paulus', 3002, 74),
	(5602533, 'Paulus', 3010, 60),
	(5602533, 'Paulus',	8001, 100),
	(5602533, 'Paulus',	2019, 10),
	(5285824, 'Fritz',	1001, 100),
	(5285824, 'Fritz',	1002, 100),
	(5285824, 'Fritz',	1010, 5),
	(5285824, 'Diana',	8002, 100),
	(5285824, 'Diana',	1001, 45);