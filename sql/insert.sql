SET GLOBAL max_allowed_packet=1073741824;
SHOW VARIABLES LIKE 'max_allowed_packet';

SET SQL_SAFE_UPDATES=0;
SET FOREIGN_KEY_CHECKS=0;
delete from `unico`.`enucity`;
ALTER TABLE `unico`.`enucity` AUTO_INCREMENT=1;
INSERT INTO unico.enucity(cityId,city,stateId,countryId) select id,name,state_id,country_id from sakila.cities;

SET SQL_SAFE_UPDATES=0;
SET FOREIGN_KEY_CHECKS=0;
delete from `unico`.`enustate`;
ALTER TABLE `unico`.`enustate` AUTO_INCREMENT=1;
INSERT INTO unico.enustate(stateId,state,countryId) select id,name,country_id from sakila.states;

SET SQL_SAFE_UPDATES=0;
SET FOREIGN_KEY_CHECKS=0;
delete from `unico`.`enucountry`;
ALTER TABLE `unico`.`enucountry` AUTO_INCREMENT=1;
INSERT INTO unico.enucountry(country) select name from sakila.countries;

select * from `unico`.`enucountry`;
select * from `unico`.`enustate`;
select * from `unico`.`enucity`;

SET SQL_SAFE_UPDATES=0;
SET FOREIGN_KEY_CHECKS=0;
delete from `unico`.`enuemployeerole`;
ALTER TABLE `unico`.`enuemployeerole` AUTO_INCREMENT=1;
INSERT INTO unico.enuemployeerole(role) values ('Administrative Assistant');
INSERT INTO unico.enuemployeerole(role) values ('Event Coordinator');
INSERT INTO unico.enuemployeerole(role) values ('Event Planner');
INSERT INTO unico.enuemployeerole(role) values ('Event Manager');
INSERT INTO unico.enuemployeerole(role) values ('Logistics Manager');
INSERT INTO unico.enuemployeerole(role) values ('Marketing Lead');
INSERT INTO unico.enuemployeerole(role) values ('On-Site Lead');
INSERT INTO unico.enuemployeerole(role) values ('Designer / Experiential Designer');
INSERT INTO unico.enuemployeerole(role) values ('Employee');

SET SQL_SAFE_UPDATES=0;
SET FOREIGN_KEY_CHECKS=0;
delete from `unico`.`enuemployeeworkingstatus`;
ALTER TABLE `unico`.`enuemployeeworkingstatus` AUTO_INCREMENT=1;
INSERT INTO unico.enuemployeeworkingstatus(status) values('Pending');
INSERT INTO unico.enuemployeeworkingstatus(status) values('In Progress');
INSERT INTO unico.enuemployeeworkingstatus(status) values('Completed');
INSERT INTO unico.enuemployeeworkingstatus(status) values('Assigned');
select * from unico.enuemployeeworkingstatus;

SET SQL_SAFE_UPDATES=0;
SET FOREIGN_KEY_CHECKS=0;
delete from `unico`.`enuenquirystatus`;
ALTER TABLE `unico`.`enuenquirystatus` AUTO_INCREMENT=1;
INSERT INTO unico.enuenquirystatus(status) values('Submitted');
INSERT INTO unico.enuenquirystatus(status) values('In Review');
INSERT INTO unico.enuenquirystatus(status) values('Responded');
SELECT * FROM `unico`.`enuenquirystatus`;

SET SQL_SAFE_UPDATES=0;
SET FOREIGN_KEY_CHECKS=0;
delete from `unico`.`enuserviceproviderworkingstatus`;
ALTER TABLE `unico`.`enuserviceproviderworkingstatus` AUTO_INCREMENT=1;
INSERT INTO unico.enuserviceproviderworkingstatus(status) select status from unico.enuemployeeworkingstatus;
select * from unico.enuserviceproviderworkingstatus;

SET SQL_SAFE_UPDATES=0;
SET FOREIGN_KEY_CHECKS=0;
delete from `unico`.`enuservicetype`;
ALTER TABLE `unico`.`enuservicetype` AUTO_INCREMENT=1;
INSERT INTO unico.enuservicetype(service,isActive) values('Venue',0);
INSERT INTO unico.enuservicetype(service,basicCost) values('Cateror',5000);
INSERT INTO unico.enuservicetype(service,basicCost) values('Decorator',6000);
INSERT INTO unico.enuservicetype(service,basicCost) values('Photographer',3500);
INSERT INTO unico.enuservicetype(service,basicCost) values('Videographer',4500);
select * from unico.enuservicetype;

SET SQL_SAFE_UPDATES=0;
update unico.enuservicetype set isActive=false where service='Venue';

SET SQL_SAFE_UPDATES=0;
SET FOREIGN_KEY_CHECKS=0;
delete from `unico`.`enueventtype`;
ALTER TABLE `unico`.`enueventtype` AUTO_INCREMENT=1;
INSERT INTO unico.enueventtype(eventType) values("Birthday Party");
INSERT INTO unico.enueventtype(eventType) values("Wedding");
INSERT INTO unico.enueventtype(eventType) values("Wedding Reception");
INSERT INTO unico.enueventtype(eventType) values("Festival Gathering");
INSERT INTO unico.enueventtype(eventType) values("Business Meeting");
INSERT INTO unico.enueventtype(eventType) values("Conference");
INSERT INTO unico.enueventtype(eventType) values("Sports Event");
INSERT INTO unico.enueventtype(eventType) values("Workshop");
INSERT INTO unico.enueventtype(eventType) values("Concert");
INSERT INTO unico.enueventtype(eventType) values("Seminar");
INSERT INTO unico.enueventtype(eventType) values("Comedy Show");
INSERT INTO unico.enueventtype(eventType) values("Charity Event");
INSERT INTO unico.enueventtype(eventType) values("Trade Show / Expo");
SELECT * from unico.enueventtype;

INSERT INTO unico.enueventsubtype(eventSubType,eventTypeId) values("A1",1);
INSERT INTO unico.enueventsubtype(eventSubType,eventTypeId) values("A2",1);
INSERT INTO unico.enueventsubtype(eventSubType,eventTypeId) values("B1",2);
INSERT INTO unico.enueventsubtype(eventSubType,eventTypeId) values("B2",2);
SELECT * from unico.enueventsubtype;

SET SQL_SAFE_UPDATES=0;
SET FOREIGN_KEY_CHECKS=0;
delete from `unico`.`enuvenuetype`;
ALTER TABLE `unico`.`enuvenuetype` AUTO_INCREMENT=1;
insert into unico.enuvenuetype(venueType) values("Club");
insert into unico.enuvenuetype(venueType) values("Restraurant");
insert into unico.enuvenuetype(venueType) values("Hotel");
insert into unico.enuvenuetype(venueType) values("Conference Centre");
insert into unico.enuvenuetype(venueType) values("Business Centre");
insert into unico.enuvenuetype(venueType) values("Community Centre");
insert into unico.enuvenuetype(venueType) values("Sports Club");
insert into unico.enuvenuetype(venueType) values("Art Gallery");
insert into unico.enuvenuetype(venueType) values("Academic Venue");
insert into unico.enuvenuetype(venueType) values("Stadiums and Arenas");

SET SQL_SAFE_UPDATES=0;
SET FOREIGN_KEY_CHECKS=0;
delete from `unico`.`enuvenuefacility`;
ALTER TABLE `unico`.`enuvenuefacility` AUTO_INCREMENT=1;
insert into unico.enuvenuefacility(facility,cost) values("Parking area", 100);
insert into unico.enuvenuefacility(facility,cost) values("Wi-fi", 500);
insert into unico.enuvenuefacility(facility,cost) values("Rest room", 1000);
insert into unico.enuvenuefacility(facility,cost) values("Child daycare", 1000);
select * from `unico`.`enuvenuefacility`;