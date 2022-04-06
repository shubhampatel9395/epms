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

SET SQL_SAFE_UPDATES=0;
SET FOREIGN_KEY_CHECKS=0;
delete from unico.address;
ALTER TABLE `unico`.`address` AUTO_INCREMENT=1;

SET SQL_SAFE_UPDATES=0;
SET FOREIGN_KEY_CHECKS=0;
delete from attendee;
ALTER TABLE `unico`.`attendee` AUTO_INCREMENT=1;

SET SQL_SAFE_UPDATES=0;
SET FOREIGN_KEY_CHECKS=0;
delete from employee;
ALTER TABLE `unico`.`employee` AUTO_INCREMENT=1;

SET SQL_SAFE_UPDATES=0;
SET FOREIGN_KEY_CHECKS=0;
delete from enquiry;
ALTER TABLE `unico`.`enquiry` AUTO_INCREMENT=1;

SET SQL_SAFE_UPDATES=0;
SET FOREIGN_KEY_CHECKS=0;
delete from `event`;
ALTER TABLE `unico`.`event` AUTO_INCREMENT=1;

SET SQL_SAFE_UPDATES=0;
SET FOREIGN_KEY_CHECKS=0;
delete from eventbanner;
ALTER TABLE `unico`.`eventbanner` AUTO_INCREMENT=1;

SET SQL_SAFE_UPDATES=0;
SET FOREIGN_KEY_CHECKS=0;
delete from eventemployeemapping;
ALTER TABLE `unico`.`eventemployeemapping` AUTO_INCREMENT=1;

SET SQL_SAFE_UPDATES=0;
SET FOREIGN_KEY_CHECKS=0;
delete from feedback;
ALTER TABLE `unico`.`feedback` AUTO_INCREMENT=1;

SET SQL_SAFE_UPDATES=0;
SET FOREIGN_KEY_CHECKS=0;
delete from packagedetails;
ALTER TABLE `unico`.`packagedetails` AUTO_INCREMENT=1;

SET SQL_SAFE_UPDATES=0;
SET FOREIGN_KEY_CHECKS=0;
delete from packageserviceprovidermapping;
ALTER TABLE `unico`.`packageserviceprovidermapping` AUTO_INCREMENT=1;

SET SQL_SAFE_UPDATES=0;
SET FOREIGN_KEY_CHECKS=0;
delete from packagevenuefacilitymapping;
ALTER TABLE `unico`.`packagevenuefacilitymapping` AUTO_INCREMENT=1;

SET SQL_SAFE_UPDATES=0;
SET FOREIGN_KEY_CHECKS=0;
delete from payment;
ALTER TABLE `unico`.`payment` AUTO_INCREMENT=1;

SET SQL_SAFE_UPDATES=0;
SET FOREIGN_KEY_CHECKS=0;
delete from serviceprovider;
ALTER TABLE `unico`.`serviceprovider` AUTO_INCREMENT=1;

SET SQL_SAFE_UPDATES=0;
SET FOREIGN_KEY_CHECKS=0;
delete from userdetails;
ALTER TABLE `unico`.`userdetails` AUTO_INCREMENT=1;

SET SQL_SAFE_UPDATES=0;
SET FOREIGN_KEY_CHECKS=0;
delete from venue;
ALTER TABLE `unico`.`venue` AUTO_INCREMENT=1;

SET SQL_SAFE_UPDATES=0;
SET FOREIGN_KEY_CHECKS=0;
delete from venueeventtypemapping;
ALTER TABLE `unico`.`venueeventtypemapping` AUTO_INCREMENT=1;

SET SQL_SAFE_UPDATES=0;
SET FOREIGN_KEY_CHECKS=0;
delete from venuefacilitymapping;
ALTER TABLE `unico`.`venuefacilitymapping` AUTO_INCREMENT=1;

SET SQL_SAFE_UPDATES=0;
SET FOREIGN_KEY_CHECKS=0;
delete from venueimagemapping;
ALTER TABLE `unico`.`venueimagemapping` AUTO_INCREMENT=1;


SET SQL_SAFE_UPDATES=0;
SET FOREIGN_KEY_CHECKS=0;
UPDATE packageserviceprovidermapping
SET serviceTypeId = (SELECT serviceTypeId FROM serviceprovider s WHERE packageserviceprovidermapping.serviceProviderId = s.serviceProviderId);

SET SQL_SAFE_UPDATES=0;
SET FOREIGN_KEY_CHECKS=0;
UPDATE packagedetails
SET venueTypeId = (SELECT venueTypeId FROM venue v WHERE packagedetails.venueId = v.venueId);

SET SQL_SAFE_UPDATES=0;
SET FOREIGN_KEY_CHECKS=0;
delete from packagedetails;
ALTER TABLE `unico`.`packagedetails` AUTO_INCREMENT=1;
LOCK TABLES `packagedetails` WRITE;
/*!40000 ALTER TABLE `packagedetails` DISABLE KEYS */;
INSERT INTO `packagedetails` VALUES 
(1,'Package1','',500,10500,1,1,NULL,1,NULL,'2022-02-24 18:39:06','2022-02-24 18:39:06',1,NULL,NULL),
(2,'Package2','',200,60050,1,1,NULL,2,NULL,'2022-02-24 18:40:16','2022-02-24 18:40:16',1,NULL,NULL),
(3,'Package3','',100,2600,1,2,NULL,3,NULL,'2022-02-24 18:40:36','2022-02-24 18:40:36',1,NULL,NULL),
(4,'Package4','',250,9520,1,2,NULL,3,NULL,'2022-02-24 18:41:09','2022-02-24 18:41:09',1,NULL,NULL),
(5,'Package5','',120,6000,1,3,NULL,1,NULL,'2022-02-24 18:42:04','2022-02-24 18:42:04',1,NULL,NULL),
(6,'Package6','',100,12500,1,3,NULL,3,NULL,'2022-02-24 18:42:26','2022-02-24 18:42:26',1,NULL,NULL),
(7,'Package7','',100,8000,1,4,NULL,1,NULL,'2022-02-24 18:42:51','2022-02-24 18:42:51',1,NULL,NULL),
(8,'Package8','',150,11250,1,4,NULL,1,NULL,'2022-02-24 18:43:21','2022-02-24 18:43:21',1,NULL,NULL),
(9,'Package9','',500,61300,1,5,NULL,4,NULL,'2022-02-24 18:45:09','2022-02-24 18:45:09',1,NULL,NULL),
(10,'Package10','',200,5150,1,5,NULL,6,NULL,'2022-02-24 18:45:32','2022-02-24 18:45:32',1,NULL,NULL),
(11,'Package11','',250,2100,1,6,NULL,5,NULL,'2022-02-24 18:46:24','2022-02-24 18:46:24',1,NULL,NULL),
(12,'Package12','',200,10050,1,8,NULL,4,NULL,'2022-02-24 18:47:00','2022-02-24 18:47:28',1,NULL,NULL),
(13,'Package13','',500,15050,1,9,NULL,6,NULL,'2022-02-24 18:47:57','2022-02-24 18:47:57',1,NULL,NULL),
(14,'Package14','',150,2750,1,10,NULL,3,NULL,'2022-02-24 18:48:30','2022-02-24 18:48:30',1,NULL,NULL),
(15,'Package15','',500,11200,1,13,NULL,5,NULL,'2022-02-24 18:49:12','2022-02-24 18:49:12',1,NULL,NULL),
(16,'Package16','',500,15080,1,11,NULL,8,NULL,'2022-02-24 19:03:13','2022-02-24 19:03:13',1,NULL,NULL),
(17,'Package17','',20000,55310,1,7,NULL,7,NULL,'2022-02-24 19:03:51','2022-02-24 19:03:51',1,NULL,NULL),
(18,NULL,NULL,NULL,55100,0,6,NULL,5,NULL,'2022-02-24 19:13:37','2022-02-24 19:13:37',1,NULL,NULL),
('19', 'Package18', '', '400', '8000', '1', '8', NULL, '3', '3', '2022-02-24 22:07:37', '2022-03-11 12:54:29', '1', NULL, NULL),
('20', NULL, NULL, NULL, '2500', '0', '4', NULL, '3', '3', '2022-02-24 22:10:38', '2022-03-11 12:54:29', '1', NULL, NULL),
('21', 'Dummy1', '', '500', '52500', '1', '3', NULL, '3', '3', '2022-03-10 16:16:24', '2022-03-11 12:54:29', '1', NULL, NULL),
('22', 'Dummy1', '', '500', '52500', '1', '3', NULL, '3', '3', '2022-03-10 16:18:14', '2022-03-11 12:54:29', '1', NULL, NULL);
/*!40000 ALTER TABLE `packagedetails` ENABLE KEYS */;
UNLOCK TABLES;

/*
SET SQL_SAFE_UPDATES=0;
SET FOREIGN_KEY_CHECKS=0;
update event e, packagedetails p set e.eventOrganizerId=1,e.eventStatusId=2,p.venueId=5 where e.packageId=p.packageDetailsId AND e.eventId=4;
*/


SET AUTOCOMMIT=0;
SET SQL_SAFE_UPDATES=0;
SET FOREIGN_KEY_CHECKS=0;
update event e, packageserviceprovidermapping p, eventemployeemapping m set e.isActive=false,p.isActive=false,m.isActive=false where e.packageId=p.packageId AND e.eventId=m.eventId AND e.eventId=4;
rollback;

SET AUTOCOMMIT=1;
show VARIABLES LIKE 'AUTOCOMMIT';

SELECT statusId from enueventstatus where status="Completed";

--  Total Packages
SELECT count(1) from packagedetails p
join packageserviceprovidermapping m on p.packageDetailsId=m.packageId
where p.isStatic=1 and p.isActive=true and m.serviceProviderId=1;

--  Total Completed Events Part
SELECT count(1) from event e
join packagedetails p on e.packageId=p.packageDetailsId
join packageserviceprovidermapping m on p.packageDetailsId=m.packageId
where e.eventStatusId=(SELECT statusId from enueventstatus where status='Completed') and e.isActive=true and m.serviceProviderId=9;

--  Total Ongoing Events Part
SELECT count(1) from event e
join packagedetails p on e.packageId=p.packageDetailsId
join packageserviceprovidermapping m on p.packageDetailsId=m.packageId
where e.eventStatusId=(SELECT statusId from enueventstatus where status='Verified') and e.isActive=true and m.serviceProviderId=1;

(SELECT statusId from enueventstatus where status='Completed');
--  Total Review Sum
SELECT sum(serviceProviderRating) / 5 from feedback f
join serviceprovider s on f.serviceProviderId=s.serviceProviderId
where f.isActive=true and s.serviceProviderId=4;

--  Total Completed Events Part Details
SELECT e.eventId,e.eventTitle,e.objective,(SELECT bannerId from eventbanner where e.eventId=eventId and isActive=true) as bannerId,et.eventType,e.startDate,e.startTime,e.endDate,e.endTime,e.isPublic,e.updatedAt,
concat(u.firstName,' ',u.lastName) as customerName,u.email,u.mobileNumber,
(SELECT concat(firstName,' ',lastName) from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerName,
(SELECT email from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerEmail,
(SELECT mobileNumber from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerMobileNumber,
v.venueName,v.addressId,v.email as venueEmail,v.contactNumber 
from event e
join packagedetails p on e.packageId=p.packageDetailsId
join packageserviceprovidermapping m on p.packageDetailsId=m.packageId
join venue v on p.venueId = v.venueId
join enueventtype et on et.eventTypeId = e.eventTypeId
join userdetails u on u.userDetailsId= e.userDetailsId
join employee emp on emp.employeeId=e.eventOrganizerId
where e.eventStatusId=(SELECT statusId from enueventstatus where status='Completed') and e.isActive=true and m.serviceProviderId=9;

--  Total Ongoing Events Part Details
SELECT e.eventId,e.eventTitle,e.objective,(SELECT bannerId from eventbanner where e.eventId=eventId and isActive=true) as bannerId,et.eventType,e.startDate,e.startTime,e.endDate,e.endTime,e.isPublic,e.updatedAt,
concat(u.firstName,' ',u.lastName) as customerName,u.email,u.mobileNumber,
(SELECT concat(firstName,' ',lastName) from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerName,
(SELECT email from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerEmail,
(SELECT mobileNumber from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerMobileNumber,
v.venueName,v.addressId,v.email as venueEmail,v.contactNumber
from event e
join packagedetails p on e.packageId=p.packageDetailsId
join packageserviceprovidermapping m on p.packageDetailsId=m.packageId
join venue v on p.venueId = v.venueId
join enueventtype et on et.eventTypeId = e.eventTypeId
join userdetails u on u.userDetailsId= e.userDetailsId
join employee emp on emp.employeeId=e.eventOrganizerId
where e.eventStatusId=(SELECT statusId from enueventstatus where status='Verified') and e.isActive=true and m.serviceProviderId=8;

--  Total Packages Details
SELECT p.packageDetailsId,p.title,p.description,et.eventType,p.guestAmount,p.totalCost,p.createdAt,
v.venueName,v.addressId,v.email as venueEmail,v.contactNumber
from packagedetails p
join packageserviceprovidermapping m on p.packageDetailsId=m.packageId
join enueventtype et on et.eventTypeId = p.eventTypeId
join venue v on p.venueId = v.venueId
join serviceprovider sp on m.serviceProviderId=sp.serviceProviderId
join enuservicetype est on sp.serviceTypeId=est.serviceTypeId
where p.isStatic=1 and p.isActive=true and m.serviceProviderId=8;

SELECT p.packageDetailsId,
u.serviceProviderName,u.email,est.service
from packagedetails p
join packageserviceprovidermapping m on p.packageDetailsId=m.packageId
join serviceprovider sp on m.serviceProviderId=sp.serviceProviderId
join userdetails u on sp.userDetailsId=u.userDetailsId
join enuservicetype est on sp.serviceTypeId=est.serviceTypeId
where p.isStatic=1 and p.isActive=true 
and p.packageDetailsId IN (SELECT p.packageDetailsId
from packagedetails p
join packageserviceprovidermapping m on p.packageDetailsId=m.packageId
where p.isStatic=1 and p.isActive=true and m.serviceProviderId=8);

-- Particular Event Details
SELECT e.eventId,e.eventTitle,e.objective,(SELECT bannerId from eventbanner where e.eventId=eventId and isActive=true) as bannerId,et.eventType,e.startDate,e.startTime,e.endDate,e.endTime,e.isPublic,e.updatedAt,
concat(u.firstName,' ',u.lastName) as customerName,u.email,u.mobileNumber,
(SELECT concat(firstName,' ',lastName) from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerName,
(SELECT email from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerEmail,
(SELECT mobileNumber from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerMobileNumber,
v.venueName,v.addressId,v.email as venueEmail,v.contactNumber 
from event e
join packagedetails p on e.packageId=p.packageDetailsId
join packageserviceprovidermapping m on p.packageDetailsId=m.packageId
join venue v on p.venueId = v.venueId
join enueventtype et on et.eventTypeId = e.eventTypeId
join userdetails u on u.userDetailsId= e.userDetailsId
join employee emp on emp.employeeId=e.eventOrganizerId
where e.eventId=4 and e.isActive=true and m.serviceProviderId=9;

-- Feedback ServiceProvider
SELECT e.eventId,e.eventTitle,e.objective,(SELECT bannerId from eventbanner where e.eventId=eventId and isActive=true) as bannerId,et.eventType,e.startDate,e.startTime,e.endDate,e.endTime,e.isPublic,e.updatedAt,
concat(u.firstName,' ',u.lastName) as customerName,u.email,u.mobileNumber,
(SELECT concat(firstName,' ',lastName) from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerName,
(SELECT email from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerEmail,
(SELECT mobileNumber from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerMobileNumber,
v.venueName,v.addressId,v.email as venueEmail,v.contactNumber,f.feedbackId,f.eventId, f.eventRating, f.eventDescription, f.venueId,f.venueRating, f.venueDescription, f.employeeId, f.employeeRating, f.employeeDescription, f.serviceProviderId, f.serviceProviderRating, f.serviceProviderDescription, f.packageId, f.packageRating, f.packageDescription, f.createdAt, f.updatedAt, f.isActive, f.createdBy, f.updatedBy 
FROM feedback f
join event e on e.eventId=f.eventId
join serviceprovider sp on sp.serviceProviderId=f.serviceProviderId
join packagedetails p on e.packageId=p.packageDetailsId
join venue v on p.venueId = v.venueId
join enueventtype et on et.eventTypeId = e.eventTypeId
join userdetails u on u.userDetailsId= e.userDetailsId
join employee emp on emp.employeeId=e.eventOrganizerId
where sp.serviceProviderId=1 and e.eventId IN (SELECT e.eventId from event e
join packagedetails p on e.packageId=p.packageDetailsId
join packageserviceprovidermapping m on p.packageDetailsId=m.packageId
where e.eventStatusId=(SELECT statusId from enueventstatus where status='Completed') and e.isActive=true and m.serviceProviderId=1);

-- Event Organizer Completed Event Count
SELECT count(1) FROM event e
where e.eventStatusId=(SELECT statusId from enueventstatus where status='Completed') and e.isActive=true and e.eventOrganizerId=3;

-- Event Organizer Upcoming Event Count
SELECT count(1) FROM event e
where e.eventStatusId=(SELECT statusId from enueventstatus where status='Verified') and e.isActive=true and e.eventOrganizerId=3;

-- Employee Upcoming Event Count
SELECT count(1) from event e
join eventemployeemapping em on em.eventId=e.eventId
where em.employeeId=6 and em.isActive=true and e.eventStatusId=(SELECT statusId from enueventstatus where status='Verified');

-- Employee Completed Event Count
SELECT count(1) from event e
join eventemployeemapping em on em.eventId=e.eventId
where em.employeeId=6 and em.isActive=true and e.eventStatusId=(SELECT statusId from enueventstatus where status='Completed');

-- Employee Upcoming Event Details
SELECT e.eventId,e.eventTitle,e.objective,(SELECT bannerId from eventbanner where e.eventId=eventId and isActive=true) as bannerId,et.eventType,e.startDate,e.startTime,e.endDate,e.endTime,e.isPublic,e.isFree,e.updatedAt,
concat(u.firstName,' ',u.lastName) as customerName,u.email,u.mobileNumber,
(SELECT concat(firstName,' ',lastName) from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerName,
(SELECT email from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerEmail,
(SELECT mobileNumber from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerMobileNumber,
v.venueName,v.addressId,v.email as venueEmail,v.contactNumber,em.workDescription,emws.status
from event e
join packagedetails p on e.packageId=p.packageDetailsId
join eventemployeemapping em on em.eventId=e.eventId
join enuemployeeworkingstatus emws on emws.statusId=em.statusId
join venue v on p.venueId = v.venueId
join enueventtype et on et.eventTypeId = e.eventTypeId
join userdetails u on u.userDetailsId= e.userDetailsId
join employee emp on emp.employeeId=e.eventOrganizerId
where e.eventStatusId=(SELECT statusId from enueventstatus where status='Verified') and e.isActive=true and em.isActive=true and em.employeeId=5 ORDER BY e.startDate;

-- Employee Completed Event Details
SELECT e.eventId,e.eventTitle,e.objective,(SELECT bannerId from eventbanner where e.eventId=eventId and isActive=true) as bannerId,et.eventType,e.startDate,e.startTime,e.endDate,e.endTime,e.isPublic,e.isFree,e.updatedAt,
concat(u.firstName,' ',u.lastName) as customerName,u.email,u.mobileNumber,
(SELECT concat(firstName,' ',lastName) from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerName,
(SELECT email from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerEmail,
(SELECT mobileNumber from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerMobileNumber,
v.venueName,v.addressId,v.email as venueEmail,v.contactNumber,em.workDescription,emws.status
from event e
join packagedetails p on e.packageId=p.packageDetailsId
join eventemployeemapping em on em.eventId=e.eventId
join enuemployeeworkingstatus emws on emws.statusId=em.statusId
join venue v on p.venueId = v.venueId
join enueventtype et on et.eventTypeId = e.eventTypeId
join userdetails u on u.userDetailsId= e.userDetailsId
join employee emp on emp.employeeId=e.eventOrganizerId
where e.eventStatusId=(SELECT statusId from enueventstatus where status='Completed') and e.isActive=true and em.employeeId=5 and em.isActive=true ORDER BY e.endDate DESC;

-- Event Organizer Upcoming Event Details
SELECT e.eventId,e.eventTitle,e.objective,(SELECT bannerId from eventbanner where e.eventId=eventId and isActive=true) as bannerId,et.eventType,e.startDate,e.startTime,e.endDate,e.endTime,e.isPublic,e.isFree,e.updatedAt,
concat(u.firstName,' ',u.lastName) as customerName,u.email,u.mobileNumber,
(SELECT concat(firstName,' ',lastName) from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerName,
(SELECT email from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerEmail,
(SELECT mobileNumber from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerMobileNumber,
v.venueName,v.addressId,v.email as venueEmail,v.contactNumber
from event e
join packagedetails p on e.packageId=p.packageDetailsId
join venue v on p.venueId = v.venueId
join enueventtype et on et.eventTypeId = e.eventTypeId
join userdetails u on u.userDetailsId= e.userDetailsId
join employee emp on emp.employeeId=e.eventOrganizerId
where e.eventStatusId=(SELECT statusId from enueventstatus where status='Verified') and e.isActive=true and e.eventOrganizerId=1 ORDER BY e.startDate;

-- Event Organizer Completed Event Details
SELECT e.eventId,e.eventTitle,e.objective,(SELECT bannerId from eventbanner where e.eventId=eventId and isActive=true) as bannerId,et.eventType,e.startDate,e.startTime,e.endDate,e.endTime,e.isPublic,e.updatedAt,
concat(u.firstName,' ',u.lastName) as customerName,u.email,u.mobileNumber,
(SELECT concat(firstName,' ',lastName) from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerName,
(SELECT email from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerEmail,
(SELECT mobileNumber from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerMobileNumber,
v.venueName,v.addressId,v.email as venueEmail,v.contactNumber,e.isFree,e.packageId
from event e
join packagedetails p on e.packageId=p.packageDetailsId
join venue v on p.venueId = v.venueId
join enueventtype et on et.eventTypeId = e.eventTypeId
join userdetails u on u.userDetailsId= e.userDetailsId
join employee emp on emp.employeeId=e.eventOrganizerId
where e.eventStatusId=(SELECT statusId from enueventstatus where status='Completed') and e.isActive=true and e.eventOrganizerId=3 ORDER BY e.endDate DESC;

-- Services Details Per Package for event organizer
SELECT p.packageDetailsId,
u.serviceProviderName,u.email,est.service
from packagedetails p
join packageserviceprovidermapping m on p.packageDetailsId=m.packageId
join serviceprovider sp on m.serviceProviderId=sp.serviceProviderId
join userdetails u on sp.userDetailsId=u.userDetailsId
join enuservicetype est on sp.serviceTypeId=est.serviceTypeId
where p.packageDetailsId IN (SELECT e.packageId
from event e
join packagedetails p on e.packageId=p.packageDetailsId
where e.eventOrganizerId=3);

-- Employee Any Event Details
SELECT e.eventId,e.eventTitle,e.objective,(SELECT bannerId from eventbanner where e.eventId=eventId and isActive=true) as bannerId,et.eventType,e.startDate,e.startTime,e.endDate,e.endTime,e.isPublic,e.isFree,e.updatedAt,
concat(u.firstName,' ',u.lastName) as customerName,u.email,u.mobileNumber,
(SELECT concat(firstName,' ',lastName) from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerName,
(SELECT email from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerEmail,
(SELECT mobileNumber from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerMobileNumber,
v.venueName,v.addressId,v.email as venueEmail,v.contactNumber,em.workDescription,emws.status
from event e
join packagedetails p on e.packageId=p.packageDetailsId
join eventemployeemapping em on em.eventId=e.eventId
join enuemployeeworkingstatus emws on emws.statusId=em.statusId
join venue v on p.venueId = v.venueId
join enueventtype et on et.eventTypeId = e.eventTypeId
join userdetails u on u.userDetailsId= e.userDetailsId
join employee emp on emp.employeeId=e.eventOrganizerId
where e.isActive=true and em.employeeId=8 and e.eventId=11 and em.isActive=true;

-- Event Organizer Any Event Details
SELECT e.eventId,e.eventTitle,e.objective,(SELECT bannerId from eventbanner where e.eventId=eventId and isActive=true) as bannerId,et.eventType,e.startDate,e.startTime,e.endDate,e.endTime,e.isPublic,e.updatedAt,
concat(u.firstName,' ',u.lastName) as customerName,u.email,u.mobileNumber,
(SELECT concat(firstName,' ',lastName) from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerName,
(SELECT email from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerEmail,
(SELECT mobileNumber from userDetails where userDetailsId=emp.userDetailsId) as eventOrganizerMobileNumber,
v.venueName,v.addressId,v.email as venueEmail,v.contactNumber,e.isFree,e.packageId
from event e
join packagedetails p on e.packageId=p.packageDetailsId
join venue v on p.venueId = v.venueId
join enueventtype et on et.eventTypeId = e.eventTypeId
join userdetails u on u.userDetailsId= e.userDetailsId
join employee emp on emp.employeeId=e.eventOrganizerId
where e.isActive=true and e.eventOrganizerId=3 and e.eventId=9;

-- All Assigned Employees Details
SELECT em.eventEmployeemappingId,concat(u.firstName,' ',u.lastName) as employeeName,er.role as employeeRole,u.email,u.mobileNumber,em.workDescription,emws.status as workingStatus from event e
join eventemployeemapping em on em.eventId=e.eventId
join enuemployeeworkingstatus emws on emws.statusId=em.statusId
join employee emp on emp.employeeId=em.employeeId
join enuemployeerole er on emp.employeeRoleId=er.employeeRoleId
join userdetails u on emp.userDetailsId=u.userDetailsId
where e.isActive=true and e.eventId=5 and em.isActive=true;

select DATE(NOW());
select TIME(NOW());

-- Service Provider Next Week's Events
SELECT e.eventId,e.eventTitle,e.startDate,e.startTime,concat(u.firstName,' ',u.lastName) as customerName,u.email,u.mobileNumber
from event e
join packagedetails p on e.packageId=p.packageDetailsId
join packageserviceprovidermapping m on p.packageDetailsId=m.packageId
join userdetails u on u.userDetailsId= e.userDetailsId
where e.isActive=true and e.eventStatusId=(SELECT statusId from enueventstatus where status='Verified') 
and m.serviceProviderId=1
and e.startDate BETWEEN DATE(NOW()) AND DATE(NOW()) + INTERVAL 6 DAY and e.startTime >= TIME(NOW()) ORDER BY e.startDate;

-- Admin Next Week's Events
SELECT e.eventId,e.eventTitle,e.startDate,e.startTime,concat(u.firstName,' ',u.lastName) as customerName,u.email,u.mobileNumber
from event e
join userdetails u on u.userDetailsId= e.userDetailsId
where e.isActive=true and e.eventStatusId=(SELECT statusId from enueventstatus where status='Verified')
and e.startDate BETWEEN DATE(NOW()) AND DATE(NOW()) + INTERVAL 6 DAY and IF(e.startDate <=> e.endDate <=> DATE(NOW()), e.startTime >= TIME(NOW()), true)  ORDER BY e.startDate;

-- Employee Next Week's Events
SELECT e.eventId,e.eventTitle,e.startDate,e.startTime,concat(u.firstName,' ',u.lastName) as customerName,u.email,u.mobileNumber
from event e
join packagedetails p on e.packageId=p.packageDetailsId
join eventemployeemapping em on em.eventId=e.eventId
join enuemployeeworkingstatus emws on emws.statusId=em.statusId
join userdetails u on u.userDetailsId= e.userDetailsId
join employee emp on emp.employeeId=e.eventOrganizerId
where e.isActive=true and e.eventStatusId=(SELECT statusId from enueventstatus where status='Verified') and em.isActive=true 
and em.employeeId=15 
and e.startDate BETWEEN DATE(NOW()) AND DATE(NOW()) + INTERVAL 6 DAY and IF(e.startDate <=> e.endDate <=> DATE(NOW()), e.startTime >= TIME(NOW()), true) ORDER BY e.startDate;

-- EO Next Week's Events
SELECT e.eventId,e.eventTitle,e.startDate,e.startTime,concat(u.firstName,' ',u.lastName) as customerName,u.email,u.mobileNumber
from event e
join userdetails u on u.userDetailsId= e.userDetailsId
join employee emp on emp.employeeId=e.eventOrganizerId
where e.eventStatusId=(SELECT statusId from enueventstatus where status='Verified') and e.isActive=true 
and e.eventOrganizerId=3
and e.startDate BETWEEN DATE(NOW()) AND DATE(NOW()) + INTERVAL 6 DAY and IF(e.startDate <=> e.endDate <=> DATE(NOW()), e.startTime >= TIME(NOW()), true) 
ORDER BY e.startDate;

-- Payment Details
SELECT p.paymentId,p.orderId,concat(u.firstName,' ',u.lastName) as customerName,u.email,u.mobileNumber,p.eventId,
p.method,p.amount,p.description,p.status,p.refundStatus,p.createdAt
from payment p
INNER JOIN userdetails u ON p.userDetailsId=u.userDetailsId
ORDER BY p.createdAt;

-- Particular Payment Details
SELECT p.paymentId,p.orderId,concat(u.firstName,' ',u.lastName) as customerName,u.email,u.mobileNumber,p.eventId,
p.method,p.amount,p.description,p.status,p.refundStatus,p.createdAt
from payment p
INNER JOIN userdetails u ON p.userDetailsId=u.userDetailsId
WHERE p.paymentId='pay_JG4UDc9uX0h4EH'
ORDER BY p.createdAt;