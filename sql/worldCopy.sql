SET SQL_SAFE_UPDATES=0;
delete from `unico`.`enucity`;

SET SQL_SAFE_UPDATES=0;
delete from `unico`.`enustate`;

SET SQL_SAFE_UPDATES=0;
delete from `unico`.`enucountry`;

ALTER TABLE `unico`.`enucountry` AUTO_INCREMENT=1;
ALTER TABLE `unico`.`enustate` AUTO_INCREMENT=1;
ALTER TABLE `unico`.`enucity` AUTO_INCREMENT=1;

INSERT INTO unico.enucountry(country) select name from sakila.countries;
SET FOREIGN_KEY_CHECKS=0;
INSERT INTO unico.enustate(stateId,state,countryId) select id,name,country_id from sakila.states;
SET FOREIGN_KEY_CHECKS=0;
INSERT INTO unico.enucity(cityId,city,stateId,countryId) select id,name,state_id,country_id from sakila.cities;

select * from `unico`.`enucountry`;
select * from `unico`.`enustate`;
select * from `unico`.`enucity`;

INSERT INTO unico.enuemployeerole(role) values ('Administrative Assistant');
INSERT INTO unico.enuemployeerole(role) values ('Event Coordinator');
INSERT INTO unico.enuemployeerole(role) values ('Event Planner');
INSERT INTO unico.enuemployeerole(role) values ('Event Manager');
INSERT INTO unico.enuemployeerole(role) values ('Logistics Manager');
INSERT INTO unico.enuemployeerole(role) values ('Marketing Lead');
INSERT INTO unico.enuemployeerole(role) values ('On-Site Lead');
INSERT INTO unico.enuemployeerole(role) values ('Designer / Experiential Designer');
INSERT INTO unico.enuemployeerole(role) values ('Employee');

INSERT INTO unico.enuemployeeworkingstatus(status) values('Pending');
INSERT INTO unico.enuemployeeworkingstatus(status) values('In Progress');
INSERT INTO unico.enuemployeeworkingstatus(status) values('Completed');
select * from unico.enuemployeeworkingstatus;

INSERT INTO unico.enuenquirystatus(status) values('Submitted');
INSERT INTO unico.enuenquirystatus(status) values('In Review');
INSERT INTO unico.enuenquirystatus(status) values('Responded');
SELECT * FROM `unico`.`enuenquirystatus`;

INSERT INTO unico.enuserviceproviderworkingstatus(status) select status from unico.enuemployeeworkingstatus;
select * from unico.enuserviceproviderworkingstatus;

INSERT INTO unico.enuservicetype(service) values('Venue');
INSERT INTO unico.enuservicetype(service) values('Cateror');
INSERT INTO unico.enuservicetype(service) values('Decorator');
INSERT INTO unico.enuservicetype(service) values('Photographer');
INSERT INTO unico.enuservicetype(service) values('Videographer');
select * from unico.enuservicetype;