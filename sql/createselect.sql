CREATE TABLE `address` (
  `addressId` int unsigned NOT NULL AUTO_INCREMENT,
  `address1` varchar(45) NOT NULL,
  `address2` varchar(45) DEFAULT NULL,
  `cityId` int unsigned NOT NULL,
  `stateId` int unsigned NOT NULL,
  `countryId` int unsigned NOT NULL,
  `postalCode` varchar(10) DEFAULT NULL,
  `createdAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isActive` tinyint NOT NULL DEFAULT '1',
  `createdBy` int unsigned DEFAULT NULL,
  `updatedBy` int unsigned DEFAULT NULL,
  PRIMARY KEY (`addressId`),
  UNIQUE KEY `addressId_UNIQUE` (`addressId`),
  KEY `fk_createdByaddress_idx` (`createdBy`),
  KEY `fk_updatedByaddress_idx` (`updatedBy`),
  KEY `fk_cityId_address_idx` (`cityId`),
  KEY `fk_countryId_address_idx` (`countryId`),
  KEY `fk_stateId_address_idx` (`stateId`),
  CONSTRAINT `fk_cityId_address` FOREIGN KEY (`cityId`) REFERENCES `enucity` (`cityId`),
  CONSTRAINT `fk_countryId_address` FOREIGN KEY (`countryId`) REFERENCES `enucountry` (`countryId`),
  CONSTRAINT `fk_createdByaddress` FOREIGN KEY (`createdBy`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `fk_stateId_address` FOREIGN KEY (`stateId`) REFERENCES `enustate` (`stateId`),
  CONSTRAINT `fk_updatedByaddress` FOREIGN KEY (`updatedBy`) REFERENCES `userdetails` (`userDetailsId`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `attendee` (
  `attendeeId` int unsigned NOT NULL AUTO_INCREMENT,
  `attendeeName` varchar(45) NOT NULL,
  `mobileNumber` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `eventId` int unsigned NOT NULL,
  `createdAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isActive` tinyint NOT NULL DEFAULT '1',
  `createdBy` int unsigned DEFAULT NULL,
  `updatedBy` int unsigned DEFAULT NULL,
  PRIMARY KEY (`attendeeId`),
  UNIQUE KEY `attendeeId_UNIQUE` (`attendeeId`),
  KEY `fk_eventAttendee_idx` (`eventId`),
  KEY `fk_createdByattendee_idx` (`createdBy`),
  KEY `fk_updatedBy_idx` (`updatedBy`),
  CONSTRAINT `fk_createdByattendee` FOREIGN KEY (`createdBy`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `fk_eventAttendee` FOREIGN KEY (`eventId`) REFERENCES `event` (`eventId`),
  CONSTRAINT `fk_updatedBy` FOREIGN KEY (`updatedBy`) REFERENCES `userdetails` (`userDetailsId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `employee` (
  `employeeId` int unsigned NOT NULL AUTO_INCREMENT,
  `userDetailsId` int unsigned NOT NULL,
  `employeeRoleId` int unsigned NOT NULL,
  `gender` varchar(10) NOT NULL,
  `DOB` date NOT NULL,
  `hiringDate` date NOT NULL,
  `leavingDate` date DEFAULT NULL,
  `marriageStatus` varchar(10) NOT NULL,
  `hiredBy` varchar(25) NOT NULL,
  `salary` double NOT NULL,
  `createdAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isActive` tinyint NOT NULL DEFAULT '1',
  `createdBy` int unsigned DEFAULT NULL,
  `updatedBy` int unsigned DEFAULT NULL,
  PRIMARY KEY (`employeeId`),
  UNIQUE KEY `employeeId_UNIQUE` (`employeeId`),
  KEY `fk_userDetailsId_Employee_idx` (`userDetailsId`),
  KEY `fk_employeeRoleId_idx` (`employeeRoleId`),
  KEY `fk_updatedBy_Employee_idx` (`updatedBy`),
  KEY `fk_createdBy_Employee_idx` (`createdBy`),
  CONSTRAINT `fk_createdBy_Employee` FOREIGN KEY (`createdBy`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `fk_employeeRoleId` FOREIGN KEY (`employeeRoleId`) REFERENCES `enuemployeerole` (`employeeRoleId`),
  CONSTRAINT `fk_updatedBy_Employee` FOREIGN KEY (`updatedBy`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `fk_userDetailsId_Employee` FOREIGN KEY (`userDetailsId`) REFERENCES `userdetails` (`userDetailsId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `enquiry` (
  `enquiryId` int unsigned NOT NULL AUTO_INCREMENT,
  `pesronName` varchar(45) NOT NULL,
  `mobileNumber` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `eventTypeId` int unsigned NOT NULL,
  `eventSubTypeId` int unsigned NOT NULL,
  `startDate` date NOT NULL,
  `startTime` time NOT NULL,
  `endDate` date NOT NULL,
  `endTime` time NOT NULL,
  `description` varchar(255) NOT NULL,
  `isPublic` tinyint NOT NULL DEFAULT '1',
  `isFree` tinyint NOT NULL DEFAULT '1',
  `estimatedGuest` int NOT NULL,
  `budget` double NOT NULL,
  `enquiryStatusId` int unsigned NOT NULL,
  `response` varchar(255) DEFAULT NULL,
  `responseDate` date DEFAULT NULL,
  `responseTime` time DEFAULT NULL,
  `createdAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isActive` tinyint NOT NULL DEFAULT '1',
  `createdBy` int unsigned DEFAULT NULL,
  `updatedBy` int unsigned DEFAULT NULL,
  PRIMARY KEY (`enquiryId`),
  UNIQUE KEY `enquiryId_UNIQUE` (`enquiryId`),
  KEY `fk_enquiryeventtype_idx` (`eventTypeId`),
  KEY `fk_enquirysubeventtype_idx` (`eventSubTypeId`),
  KEY `fk_enquirystatus_idx` (`enquiryStatusId`),
  KEY `fk_createdByenquiry_idx` (`createdBy`),
  KEY `fk_updatedByenquiry_idx` (`updatedBy`),
  CONSTRAINT `fk_createdByenquiry` FOREIGN KEY (`createdBy`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `fk_enquiryeventsubtype` FOREIGN KEY (`eventSubTypeId`) REFERENCES `enueventsubtype` (`eventSubTypeId`),
  CONSTRAINT `fk_enquiryeventtype` FOREIGN KEY (`eventTypeId`) REFERENCES `enueventtype` (`eventTypeId`),
  CONSTRAINT `fk_enquirystatus` FOREIGN KEY (`enquiryStatusId`) REFERENCES `enuenquirystatus` (`statusId`),
  CONSTRAINT `fk_updatedByenquiry` FOREIGN KEY (`updatedBy`) REFERENCES `userdetails` (`userDetailsId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `enucity` (
  `cityId` int unsigned NOT NULL AUTO_INCREMENT,
  `city` varchar(255) NOT NULL,
  `stateId` int unsigned NOT NULL,
  `countryId` int unsigned NOT NULL,
  PRIMARY KEY (`cityId`),
  UNIQUE KEY `cityId_UNIQUE` (`cityId`),
  KEY `stateId_idx` (`stateId`),
  KEY `city_countryId_idx` (`countryId`),
  CONSTRAINT `city_countryId` FOREIGN KEY (`countryId`) REFERENCES `enucountry` (`countryId`),
  CONSTRAINT `stateId` FOREIGN KEY (`stateId`) REFERENCES `enustate` (`stateId`)
) ENGINE=InnoDB AUTO_INCREMENT=149215 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `enucountry` (
  `countryId` int unsigned NOT NULL AUTO_INCREMENT,
  `country` varchar(45) NOT NULL,
  PRIMARY KEY (`countryId`),
  UNIQUE KEY `countryId_UNIQUE` (`countryId`)
) ENGINE=InnoDB AUTO_INCREMENT=251 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `enuemployeerole` (
  `employeeRoleId` int unsigned NOT NULL AUTO_INCREMENT,
  `role` varchar(45) NOT NULL DEFAULT 'Employee',
  `description` varchar(255) DEFAULT NULL,
  `isActive` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`employeeRoleId`),
  UNIQUE KEY `employeeRoleId_UNIQUE` (`employeeRoleId`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `enuemployeeworkingstatus` (
  `statusId` int unsigned NOT NULL AUTO_INCREMENT,
  `status` varchar(45) NOT NULL,
  PRIMARY KEY (`statusId`),
  UNIQUE KEY `statusId_UNIQUE` (`statusId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `enuenquirystatus` (
  `statusId` int unsigned NOT NULL AUTO_INCREMENT,
  `status` varchar(45) NOT NULL,
  PRIMARY KEY (`statusId`),
  UNIQUE KEY `statusId_UNIQUE` (`statusId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `enueventsubtype` (
  `eventSubTypeId` int unsigned NOT NULL AUTO_INCREMENT,
  `eventSubType` varchar(45) NOT NULL,
  `description` varchar(45) DEFAULT NULL,
  `isActive` tinyint NOT NULL DEFAULT '1',
  `eventTypeId` int unsigned NOT NULL,
  PRIMARY KEY (`eventSubTypeId`),
  UNIQUE KEY `eventSubTypeId_UNIQUE` (`eventSubTypeId`),
  KEY `fk_eventType_subType_idx` (`eventTypeId`),
  CONSTRAINT `fk_eventType_subType` FOREIGN KEY (`eventTypeId`) REFERENCES `enueventtype` (`eventTypeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `enueventtype` (
  `eventTypeId` int unsigned NOT NULL AUTO_INCREMENT,
  `event` varchar(45) NOT NULL,
  `description` varchar(45) DEFAULT NULL,
  `isActive` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`eventTypeId`),
  UNIQUE KEY `eventTypeId_UNIQUE` (`eventTypeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `enupaymentstakeholder` (
  `paymentStakeholderId` int unsigned NOT NULL AUTO_INCREMENT,
  `paymentStakeholder` varchar(45) NOT NULL,
  PRIMARY KEY (`paymentStakeholderId`),
  UNIQUE KEY `paymentstakeholderId_UNIQUE` (`paymentStakeholderId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `enupaymenttype` (
  `paymentTypeId` int unsigned NOT NULL AUTO_INCREMENT,
  `paymentType` varchar(45) NOT NULL,
  PRIMARY KEY (`paymentTypeId`),
  UNIQUE KEY `paymentTypeId_UNIQUE` (`paymentTypeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `enuserviceproviderworkingstatus` (
  `statusId` int unsigned NOT NULL AUTO_INCREMENT,
  `status` varchar(45) NOT NULL,
  PRIMARY KEY (`statusId`),
  UNIQUE KEY `statusId_UNIQUE` (`statusId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `enuservicetype` (
  `serviceTypeId` int unsigned NOT NULL AUTO_INCREMENT,
  `service` varchar(45) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `isActive` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`serviceTypeId`),
  UNIQUE KEY `serviceTypeId_UNIQUE` (`serviceTypeId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `enustate` (
  `stateId` int unsigned NOT NULL AUTO_INCREMENT,
  `state` varchar(255) NOT NULL,
  `countryId` int unsigned NOT NULL,
  PRIMARY KEY (`stateId`),
  UNIQUE KEY `stateId_UNIQUE` (`stateId`),
  KEY `countryId_idx` (`countryId`),
  CONSTRAINT `fk_countryId` FOREIGN KEY (`countryId`) REFERENCES `enucountry` (`countryId`)
) ENGINE=InnoDB AUTO_INCREMENT=5066 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `enuvenuefacility` (
  `venueFacilityId` int unsigned NOT NULL AUTO_INCREMENT,
  `facility` varchar(45) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `isActive` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`venueFacilityId`),
  UNIQUE KEY `venueFacilityId_UNIQUE` (`venueFacilityId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `event` (
  `eventId` int unsigned NOT NULL AUTO_INCREMENT,
  `eventTitle` varchar(45) NOT NULL,
  `objective` varchar(255) DEFAULT NULL,
  `eventTypeId` int unsigned NOT NULL,
  `eventSubTypeId` int unsigned NOT NULL,
  `userDetailsId` int unsigned NOT NULL,
  `packageId` int unsigned NOT NULL,
  `eventOrganiserId` int unsigned DEFAULT NULL,
  `isPublic` tinyint NOT NULL DEFAULT '1',
  `isFree` tinyint NOT NULL DEFAULT '1',
  `startDate` date NOT NULL,
  `startTime` time NOT NULL,
  `endDate` date NOT NULL,
  `endTime` time NOT NULL,
  `estimatedGuest` int NOT NULL,
  `registrationFee` double NOT NULL DEFAULT '0',
  `registrationsAvailable` int NOT NULL DEFAULT '0',
  `createdAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isActive` tinyint NOT NULL DEFAULT '1',
  `createdBy` int unsigned DEFAULT NULL,
  `updatedBy` int unsigned DEFAULT NULL,
  PRIMARY KEY (`eventId`),
  UNIQUE KEY `eventBookingId_UNIQUE` (`eventId`),
  KEY `fk_eventPackage_idx` (`packageId`),
  KEY `fk_eventOrganiser_idx` (`eventOrganiserId`),
  KEY `fk_customer_idx` (`userDetailsId`),
  KEY `fk_createdByevent_idx` (`createdBy`),
  KEY `fk_updatedByevent_idx` (`updatedBy`),
  KEY `fk_eventTypeEvent_idx` (`eventTypeId`),
  KEY `fk_eventSubTypeEvent_idx` (`eventSubTypeId`),
  CONSTRAINT `fk_createdByevent` FOREIGN KEY (`createdBy`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `fk_customer` FOREIGN KEY (`userDetailsId`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `fk_eventOrganiser` FOREIGN KEY (`eventOrganiserId`) REFERENCES `employee` (`employeeId`),
  CONSTRAINT `fk_eventPackage` FOREIGN KEY (`packageId`) REFERENCES `packagedetails` (`packageDetailsId`),
  CONSTRAINT `fk_eventSubTypeEvent` FOREIGN KEY (`eventSubTypeId`) REFERENCES `enueventsubtype` (`eventSubTypeId`),
  CONSTRAINT `fk_eventTypeEvent` FOREIGN KEY (`eventTypeId`) REFERENCES `enueventtype` (`eventTypeId`),
  CONSTRAINT `fk_updatedByevent` FOREIGN KEY (`updatedBy`) REFERENCES `userdetails` (`userDetailsId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `eventbanner` (
  `bannerId` int unsigned NOT NULL AUTO_INCREMENT,
  `banner` blob NOT NULL,
  `eventId` int unsigned NOT NULL,
  `createdAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isActive` tinyint NOT NULL DEFAULT '1',
  `createdBy` int unsigned DEFAULT NULL,
  `updatedBy` int unsigned DEFAULT NULL,
  PRIMARY KEY (`bannerId`),
  UNIQUE KEY `bannerId_UNIQUE` (`bannerId`),
  KEY `fk_eventbanner_idx` (`eventId`),
  KEY `fk_createdByeventbanner_idx` (`createdBy`),
  KEY `fk_updatedByeventbanner_idx` (`updatedBy`),
  CONSTRAINT `fk_createdByeventbanner` FOREIGN KEY (`createdBy`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `fk_eventbanner` FOREIGN KEY (`eventId`) REFERENCES `event` (`eventId`),
  CONSTRAINT `fk_updatedByeventbanner` FOREIGN KEY (`updatedBy`) REFERENCES `userdetails` (`userDetailsId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `eventemployeemapping` (
  `eventEmployeemappingId` int unsigned NOT NULL AUTO_INCREMENT,
  `eventId` int unsigned NOT NULL,
  `employeeId` int unsigned NOT NULL,
  `workDescription` varchar(255) DEFAULT NULL,
  `statusId` int unsigned NOT NULL,
  `createdAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isActive` tinyint NOT NULL DEFAULT '1',
  `createdBy` int unsigned DEFAULT NULL,
  `updatedBy` int unsigned DEFAULT NULL,
  PRIMARY KEY (`eventEmployeemappingId`),
  UNIQUE KEY `eventEmployeemappingId_UNIQUE` (`eventEmployeemappingId`),
  KEY `fk_eventemployee_mapping_idx` (`employeeId`),
  KEY `fk_eventemployeestatus_mapping_idx` (`statusId`),
  KEY `fk_eventId_mapping_idx` (`eventId`),
  KEY `fk_createdByeventemployeemapping_idx` (`createdBy`),
  KEY `fk_updatedByeventemployeemapping_idx` (`updatedBy`),
  CONSTRAINT `fk_createdByeventemployeemapping` FOREIGN KEY (`createdBy`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `fk_eventemployee_mapping` FOREIGN KEY (`employeeId`) REFERENCES `employee` (`employeeId`),
  CONSTRAINT `fk_eventemployeestatus_mapping` FOREIGN KEY (`statusId`) REFERENCES `enuemployeeworkingstatus` (`statusId`),
  CONSTRAINT `fk_eventId_mapping` FOREIGN KEY (`eventId`) REFERENCES `event` (`eventId`),
  CONSTRAINT `fk_updatedByeventemployeemapping` FOREIGN KEY (`updatedBy`) REFERENCES `userdetails` (`userDetailsId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `feedback` (
  `feedbackId` int unsigned NOT NULL AUTO_INCREMENT,
  `eventId` int unsigned DEFAULT NULL,
  `eventRating` int DEFAULT NULL,
  `eventDescription` varchar(255) DEFAULT NULL,
  `venueId` int unsigned DEFAULT NULL,
  `venueRating` int DEFAULT NULL,
  `venueDescription` varchar(255) DEFAULT NULL,
  `employeeId` int unsigned DEFAULT NULL,
  `employeeRating` int DEFAULT NULL,
  `employeeDescription` varchar(255) DEFAULT NULL,
  `serviceProviderId` int unsigned DEFAULT NULL,
  `serviceProviderRating` int DEFAULT NULL,
  `serviceProviderDescription` varchar(255) DEFAULT NULL,
  `createdAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isActive` tinyint NOT NULL DEFAULT '1',
  `createdBy` int unsigned DEFAULT NULL,
  `updatedBy` int unsigned DEFAULT NULL,
  PRIMARY KEY (`feedbackId`),
  UNIQUE KEY `feedbackId_UNIQUE` (`feedbackId`),
  KEY `fk_venueFeedback_idx` (`venueId`),
  KEY `fk_employeeFeedback_idx` (`employeeId`),
  KEY `fk_serviceProviderFeedback_idx` (`serviceProviderId`),
  KEY `fk_eventFeedback_idx` (`eventId`),
  KEY `fk_createdByfeedback_idx` (`createdBy`),
  KEY `fk_updatedByfeedback_idx` (`updatedBy`),
  CONSTRAINT `fk_createdByfeedback` FOREIGN KEY (`createdBy`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `fk_employeeFeedback` FOREIGN KEY (`employeeId`) REFERENCES `employee` (`employeeId`),
  CONSTRAINT `fk_eventFeedback` FOREIGN KEY (`eventId`) REFERENCES `event` (`eventId`),
  CONSTRAINT `fk_serviceProviderFeedback` FOREIGN KEY (`serviceProviderId`) REFERENCES `serviceprovider` (`serviceProviderId`),
  CONSTRAINT `fk_updatedByfeedback` FOREIGN KEY (`updatedBy`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `fk_venueFeedback` FOREIGN KEY (`venueId`) REFERENCES `venue` (`venueId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `packagedetails` (
  `packageDetailsId` int unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(45) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `guestAmount` int NOT NULL,
  `totalCost` double DEFAULT NULL,
  `isStatic` tinyint NOT NULL DEFAULT '1',
  `eventTypeId` int unsigned NOT NULL,
  `eventSubTypeId` int unsigned NOT NULL,
  `venueId` int unsigned NOT NULL,
  `createdAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isActive` tinyint NOT NULL DEFAULT '1',
  `createdBy` int unsigned DEFAULT NULL,
  `updatedBy` int unsigned DEFAULT NULL,
  PRIMARY KEY (`packageDetailsId`),
  UNIQUE KEY `packageId_UNIQUE` (`packageDetailsId`),
  KEY `fk_eventSubTypeId_package_idx` (`eventSubTypeId`),
  KEY `fk_venueId_package_idx` (`venueId`),
  KEY `fk_createdBy_package_idx` (`createdBy`),
  KEY `fk_updatedBy_package_idx` (`updatedBy`),
  KEY `fk_eventTypeId_package_idx` (`eventTypeId`),
  CONSTRAINT `fk_createdBy_package` FOREIGN KEY (`createdBy`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `fk_eventSubTypeId_package` FOREIGN KEY (`eventSubTypeId`) REFERENCES `enueventsubtype` (`eventSubTypeId`),
  CONSTRAINT `fk_eventTypeId_package` FOREIGN KEY (`eventTypeId`) REFERENCES `enueventtype` (`eventTypeId`),
  CONSTRAINT `fk_updatedBy_package` FOREIGN KEY (`updatedBy`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `fk_venueId_package` FOREIGN KEY (`venueId`) REFERENCES `venue` (`venueId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `packageserviceprovidermapping` (
  `packageServiceprovidermappingId` int unsigned NOT NULL AUTO_INCREMENT,
  `packageId` int unsigned NOT NULL,
  `serviceProviderId` int unsigned NOT NULL,
  `statusId` int unsigned NOT NULL,
  `createdAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isActive` tinyint NOT NULL DEFAULT '1',
  `createdBy` int unsigned DEFAULT NULL,
  `updatedBy` int unsigned DEFAULT NULL,
  PRIMARY KEY (`packageServiceprovidermappingId`),
  UNIQUE KEY `packageServiceprovidermappingId_UNIQUE` (`packageServiceprovidermappingId`),
  KEY `fk_packageId_mapping_idx` (`packageId`),
  KEY `fk_serviceProvider_mapping_idx` (`serviceProviderId`),
  KEY `fk_serviceProviderStatus_mapping_idx` (`statusId`),
  KEY `fk_createdBypackageserviceprovidermapping_idx` (`createdBy`),
  KEY `fk_updatedBypackageserviceprovidermapping_idx` (`updatedBy`),
  CONSTRAINT `fk_createdBypackageserviceprovidermapping` FOREIGN KEY (`createdBy`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `fk_packageId_mapping` FOREIGN KEY (`packageId`) REFERENCES `packagedetails` (`packageDetailsId`),
  CONSTRAINT `fk_serviceProvider_mapping` FOREIGN KEY (`serviceProviderId`) REFERENCES `serviceprovider` (`serviceProviderId`),
  CONSTRAINT `fk_serviceProviderStatus_mapping` FOREIGN KEY (`statusId`) REFERENCES `enuserviceproviderworkingstatus` (`statusId`),
  CONSTRAINT `fk_updatedBypackageserviceprovidermapping` FOREIGN KEY (`updatedBy`) REFERENCES `userdetails` (`userDetailsId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `packagevenuefacilitymapping` (
  `packageVenueFacilitymappingId` int unsigned NOT NULL AUTO_INCREMENT,
  `packageId` int unsigned NOT NULL,
  `venueId` int unsigned NOT NULL,
  `facilityId` int unsigned NOT NULL,
  `createdAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isActive` tinyint NOT NULL DEFAULT '1',
  `createdBy` int unsigned DEFAULT NULL,
  `updatedBy` int unsigned DEFAULT NULL,
  PRIMARY KEY (`packageVenueFacilitymappingId`),
  UNIQUE KEY `packagevenuefacilitymappingId_UNIQUE` (`packageVenueFacilitymappingId`),
  KEY `fk_packageId_pvfmapping_idx` (`packageId`),
  KEY `fk_facilityId_pvfmapping_idx` (`facilityId`),
  KEY `fk_venueId_pvfmapping_idx` (`venueId`),
  KEY `fk_createdBypackagevenuefacilitymapping_idx` (`createdBy`),
  KEY `fk_updatedBypackagevenuefacilitymapping_idx` (`updatedBy`),
  CONSTRAINT `fk_createdBypackagevenuefacilitymapping` FOREIGN KEY (`createdBy`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `fk_facilityId_pvfmapping` FOREIGN KEY (`facilityId`) REFERENCES `enuvenuefacility` (`venueFacilityId`),
  CONSTRAINT `fk_packageId_pvfmapping` FOREIGN KEY (`packageId`) REFERENCES `packagedetails` (`packageDetailsId`),
  CONSTRAINT `fk_updatedBypackagevenuefacilitymapping` FOREIGN KEY (`updatedBy`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `fk_venueId_pvfmapping` FOREIGN KEY (`venueId`) REFERENCES `packagedetails` (`venueId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `payment` (
  `paymentId` int unsigned NOT NULL AUTO_INCREMENT,
  `transactionId` int unsigned NOT NULL,
  `paymentTypeId` int unsigned NOT NULL,
  `paymentStakeholderId` int unsigned NOT NULL,
  `remarks` varchar(45) DEFAULT NULL,
  `isReceived` tinyint NOT NULL DEFAULT '1',
  `userDetailsId` int unsigned DEFAULT NULL,
  `venueId` int unsigned DEFAULT NULL,
  `attendeeId` int unsigned DEFAULT NULL,
  `employeeId` int unsigned DEFAULT NULL,
  `serviceProviderId` int unsigned DEFAULT NULL,
  `amount` int NOT NULL,
  `createdAt` datetime NOT NULL,
  PRIMARY KEY (`paymentId`),
  UNIQUE KEY `paymentId_UNIQUE` (`paymentId`),
  UNIQUE KEY `transactionId_UNIQUE` (`transactionId`),
  KEY `fk_paymentType_idx` (`paymentTypeId`),
  KEY `fk_paymentStakeholderType_idx` (`paymentStakeholderId`),
  KEY `fk_venuePayment_idx` (`venueId`),
  KEY `fk_employeePayment_idx` (`employeeId`),
  KEY `fk_customerPayment_idx` (`userDetailsId`),
  KEY `fk_serviceProviderPayment_idx` (`serviceProviderId`),
  CONSTRAINT `fk_customerPayment` FOREIGN KEY (`userDetailsId`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `fk_employeePayment` FOREIGN KEY (`employeeId`) REFERENCES `employee` (`employeeId`),
  CONSTRAINT `fk_paymentStakeholderType` FOREIGN KEY (`paymentStakeholderId`) REFERENCES `enupaymentstakeholder` (`paymentStakeholderId`),
  CONSTRAINT `fk_paymentType` FOREIGN KEY (`paymentTypeId`) REFERENCES `enupaymenttype` (`paymentTypeId`),
  CONSTRAINT `fk_serviceProviderPayment` FOREIGN KEY (`serviceProviderId`) REFERENCES `serviceprovider` (`serviceProviderId`),
  CONSTRAINT `fk_venuePayment` FOREIGN KEY (`venueId`) REFERENCES `venue` (`venueId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `serviceprovider` (
  `serviceProviderId` int unsigned NOT NULL AUTO_INCREMENT,
  `userDetailsId` int unsigned NOT NULL,
  `serviceTypeId` int unsigned NOT NULL,
  `cost` int unsigned NOT NULL,
  `createdAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isActive` tinyint NOT NULL DEFAULT '1',
  `createdBy` int unsigned DEFAULT NULL,
  `updatedBy` int unsigned DEFAULT NULL,
  PRIMARY KEY (`serviceProviderId`),
  UNIQUE KEY `serviceProviderId_UNIQUE` (`serviceProviderId`),
  KEY `fk_userDetails_serviceprovider_idx` (`userDetailsId`),
  KEY `fk_serviceType_serviceProvider_idx` (`serviceTypeId`),
  KEY `fk_createdByserviceprovider_idx` (`createdBy`),
  KEY `fk_updatedByserviceprovider_idx` (`updatedBy`),
  CONSTRAINT `fk_createdByserviceprovider` FOREIGN KEY (`createdBy`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `fk_serviceType_serviceProvider` FOREIGN KEY (`serviceTypeId`) REFERENCES `enuservicetype` (`serviceTypeId`),
  CONSTRAINT `fk_updatedByserviceprovider` FOREIGN KEY (`updatedBy`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `fk_userDetails_serviceprovider` FOREIGN KEY (`userDetailsId`) REFERENCES `userdetails` (`userDetailsId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `userdetails` (
  `userDetailsId` int unsigned NOT NULL AUTO_INCREMENT,
  `firstName` varchar(45) DEFAULT NULL,
  `lastName` varchar(45) DEFAULT NULL,
  `serviceProviderName` varchar(45) DEFAULT NULL,
  `addressId` int unsigned NOT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `mobileNumber` varchar(45) NOT NULL,
  `isAdmin` tinyint NOT NULL DEFAULT '0',
  `isCustomer` tinyint NOT NULL DEFAULT '1',
  `isEmployee` tinyint NOT NULL DEFAULT '0',
  `isServiceProvider` tinyint NOT NULL DEFAULT '0',
  `isAuth` tinyint NOT NULL DEFAULT '0',
  `createdAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isActive` tinyint NOT NULL DEFAULT '1',
  `createdBy` int unsigned DEFAULT NULL,
  `updatedBy` int unsigned DEFAULT NULL,
  PRIMARY KEY (`userDetailsId`),
  UNIQUE KEY `userId_UNIQUE` (`userDetailsId`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `mobileNumber_UNIQUE` (`mobileNumber`),
  KEY `updatedBy_idx` (`createdBy`,`updatedBy`),
  KEY `updatedBy_idx1` (`updatedBy`),
  KEY `userAddress_idx` (`addressId`),
  CONSTRAINT `createdBy` FOREIGN KEY (`createdBy`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `updatedBy` FOREIGN KEY (`updatedBy`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `userAddress` FOREIGN KEY (`addressId`) REFERENCES `address` (`addressId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `venue` (
  `venueId` int unsigned NOT NULL AUTO_INCREMENT,
  `venueName` varchar(45) NOT NULL,
  `description` varchar(45) DEFAULT NULL,
  `addressId` int unsigned NOT NULL,
  `latitude` decimal(10,8) NOT NULL,
  `longitude` decimal(11,8) NOT NULL,
  `contactNumber` varchar(15) NOT NULL,
  `email` varchar(45) NOT NULL,
  `guestCapacity` int NOT NULL,
  `cost` double NOT NULL,
  `createdAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isActive` tinyint NOT NULL DEFAULT '1',
  `createdBy` int unsigned DEFAULT NULL,
  `updatedBy` int unsigned DEFAULT NULL,
  PRIMARY KEY (`venueId`),
  UNIQUE KEY `venueId_UNIQUE` (`venueId`),
  KEY `fk_address_venue_idx` (`addressId`),
  KEY `fk_createdBy_venue_idx` (`createdBy`),
  KEY `fk_updatedBy_venue_idx` (`updatedBy`),
  CONSTRAINT `fk_address_venue` FOREIGN KEY (`addressId`) REFERENCES `address` (`addressId`),
  CONSTRAINT `fk_createdBy_venue` FOREIGN KEY (`createdBy`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `fk_updatedBy_venue` FOREIGN KEY (`updatedBy`) REFERENCES `userdetails` (`userDetailsId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `venueeventtypemapping` (
  `venueEventTypemappingId` int unsigned NOT NULL AUTO_INCREMENT,
  `venueId` int unsigned NOT NULL,
  `eventTypeId` int unsigned NOT NULL,
  `eventSubTypeId` int unsigned NOT NULL,
  `isActive` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`venueEventTypemappingId`),
  UNIQUE KEY `venueEventTypemappingId_UNIQUE` (`venueEventTypemappingId`),
  KEY `fk_venue_eventType1_idx` (`eventSubTypeId`),
  KEY `fk_venue_eventType` (`venueId`),
  KEY `fk_venue_eventType2_idx` (`eventTypeId`),
  CONSTRAINT `fk_venue_eventSubType` FOREIGN KEY (`eventSubTypeId`) REFERENCES `enueventsubtype` (`eventSubTypeId`),
  CONSTRAINT `fk_venue_eventType` FOREIGN KEY (`venueId`) REFERENCES `venue` (`venueId`),
  CONSTRAINT `fk_venue_eventType2` FOREIGN KEY (`eventTypeId`) REFERENCES `enueventtype` (`eventTypeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `enuvenuefacility` (
  `venueFacilityId` int unsigned NOT NULL AUTO_INCREMENT,
  `facility` varchar(45) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `isActive` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`venueFacilityId`),
  UNIQUE KEY `venueFacilityId_UNIQUE` (`venueFacilityId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `venuefacilitymapping` (
  `venueFacilitymappingId` int unsigned NOT NULL AUTO_INCREMENT,
  `venueId` int unsigned NOT NULL,
  `facilityId` int unsigned NOT NULL,
  `cost` double NOT NULL,
  `isActive` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`venueFacilitymappingId`),
  UNIQUE KEY `venueFacilitymappingId_UNIQUE` (`venueFacilitymappingId`),
  KEY `fk_venue_facility1_idx` (`facilityId`),
  KEY `fk_venue_facility_idx` (`venueId`),
  CONSTRAINT `fk_venue_facility` FOREIGN KEY (`venueId`) REFERENCES `venue` (`venueId`),
  CONSTRAINT `fk_venue_facility1` FOREIGN KEY (`facilityId`) REFERENCES `enuvenuefacility` (`venueFacilityId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `venueimagemapping` (
  `venueImagemappingId` int unsigned NOT NULL AUTO_INCREMENT,
  `venueId` int unsigned NOT NULL,
  `image` blob NOT NULL,
  PRIMARY KEY (`venueImagemappingId`),
  UNIQUE KEY `venueImagemappingId_UNIQUE` (`venueImagemappingId`),
  KEY `fk_venueId_image_idx` (`venueId`),
  CONSTRAINT `fk_venueId_image` FOREIGN KEY (`venueId`) REFERENCES `venue` (`venueId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;





SELECT `address`.`addressId`,
    `address`.`address1`,
    `address`.`address2`,
    `address`.`cityId`,
    `address`.`stateId`,
    `address`.`countryId`,
    `address`.`postalCode`,
    `address`.`createdAt`,
    `address`.`updatedAt`,
    `address`.`isActive`,
    `address`.`createdBy`,
    `address`.`updatedBy`
FROM `unico`.`address`;

SELECT `attendee`.`attendeeId`,
    `attendee`.`attendeeName`,
    `attendee`.`mobileNumber`,
    `attendee`.`email`,
    `attendee`.`eventId`,
    `attendee`.`createdAt`,
    `attendee`.`updatedAt`,
    `attendee`.`isActive`,
    `attendee`.`createdBy`,
    `attendee`.`updatedBy`
FROM `unico`.`attendee`;

SELECT `employee`.`employeeId`,
    `employee`.`userDetailsId`,
    `employee`.`employeeRoleId`,
    `employee`.`gender`,
    `employee`.`DOB`,
    `employee`.`hiringDate`,
    `employee`.`leavingDate`,
    `employee`.`marriageStatus`,
    `employee`.`hiredBy`,
    `employee`.`salary`,
    `employee`.`createdAt`,
    `employee`.`updatedAt`,
    `employee`.`isActive`,
    `employee`.`createdBy`,
    `employee`.`updatedBy`
FROM `unico`.`employee`;

SELECT `enquiry`.`enquiryId`,
    `enquiry`.`pesronName`,
    `enquiry`.`mobileNumber`,
    `enquiry`.`email`,
    `enquiry`.`eventTypeId`,
    `enquiry`.`eventSubTypeId`,
    `enquiry`.`startDate`,
    `enquiry`.`startTime`,
    `enquiry`.`endDate`,
    `enquiry`.`endTime`,
    `enquiry`.`description`,
    `enquiry`.`isPublic`,
    `enquiry`.`isFree`,
    `enquiry`.`estimatedGuest`,
    `enquiry`.`budget`,
    `enquiry`.`enquiryStatusId`,
    `enquiry`.`response`,
    `enquiry`.`responseDate`,
    `enquiry`.`responseTime`,
    `enquiry`.`createdAt`,
    `enquiry`.`updatedAt`,
    `enquiry`.`isActive`,
    `enquiry`.`createdBy`,
    `enquiry`.`updatedBy`
FROM `unico`.`enquiry`;

SELECT `enucity`.`cityId`,
    `enucity`.`city`,
    `enucity`.`stateId`,
    `enucity`.`countryId`
FROM `unico`.`enucity`;

SELECT `enucountry`.`countryId`,
    `enucountry`.`country`
FROM `unico`.`enucountry`;

SELECT `enuemployeerole`.`employeeRoleId`,
    `enuemployeerole`.`role`,
    `enuemployeerole`.`description`,
    `enuemployeerole`.`isActive`
FROM `unico`.`enuemployeerole`;

SELECT `enuemployeeworkingstatus`.`statusId`,
    `enuemployeeworkingstatus`.`status`
FROM `unico`.`enuemployeeworkingstatus`;

SELECT `enuenquirystatus`.`statusId`,
    `enuenquirystatus`.`status`
FROM `unico`.`enuenquirystatus`;

SELECT `enueventsubtype`.`eventSubTypeId`,
    `enueventsubtype`.`eventSubType`,
    `enueventsubtype`.`description`,
    `enueventsubtype`.`isActive`,
    `enueventsubtype`.`eventTypeId`
FROM `unico`.`enueventsubtype`;

SELECT `enueventtype`.`eventTypeId`,
    `enueventtype`.`event`,
    `enueventtype`.`description`,
    `enueventtype`.`isActive`
FROM `unico`.`enueventtype`;

SELECT `enupaymentstakeholder`.`paymentStakeholderId`,
    `enupaymentstakeholder`.`paymentStakeholder`
FROM `unico`.`enupaymentstakeholder`;

SELECT `enupaymenttype`.`paymentTypeId`,
    `enupaymenttype`.`paymentType`
FROM `unico`.`enupaymenttype`;

SELECT `enuserviceproviderworkingstatus`.`statusId`,
    `enuserviceproviderworkingstatus`.`status`
FROM `unico`.`enuserviceproviderworkingstatus`;

SELECT `enuservicetype`.`serviceTypeId`,
    `enuservicetype`.`service`,
    `enuservicetype`.`description`,
    `enuservicetype`.`isActive`
FROM `unico`.`enuservicetype`;

SELECT `enustate`.`stateId`,
    `enustate`.`state`,
    `enustate`.`countryId`
FROM `unico`.`enustate`;

SELECT `enuvenuefacility`.`venueFacilityId`,
    `enuvenuefacility`.`facility`,
    `enuvenuefacility`.`description`,
    `enuvenuefacility`.`isActive`
FROM `unico`.`enuvenuefacility`;

SELECT `event`.`eventId`,
    `event`.`eventTitle`,
    `event`.`objective`,
    `event`.`eventTypeId`,
    `event`.`eventSubTypeId`,
    `event`.`userDetailsId`,
    `event`.`packageId`,
    `event`.`eventOrganiserId`,
    `event`.`isPublic`,
    `event`.`isFree`,
    `event`.`startDate`,
    `event`.`startTime`,
    `event`.`endDate`,
    `event`.`endTime`,
    `event`.`estimatedGuest`,
    `event`.`registrationFee`,
    `event`.`registrationsAvailable`,
    `event`.`createdAt`,
    `event`.`updatedAt`,
    `event`.`isActive`,
    `event`.`createdBy`,
    `event`.`updatedBy`
FROM `unico`.`event`;

SELECT `eventbanner`.`bannerId`,
    `eventbanner`.`banner`,
    `eventbanner`.`eventId`,
    `eventbanner`.`createdAt`,
    `eventbanner`.`updatedAt`,
    `eventbanner`.`isActive`,
    `eventbanner`.`createdBy`,
    `eventbanner`.`updatedBy`
FROM `unico`.`eventbanner`;

SELECT `eventemployeemapping`.`eventEmployeemappingId`,
    `eventemployeemapping`.`eventId`,
    `eventemployeemapping`.`employeeId`,
    `eventemployeemapping`.`workDescription`,
    `eventemployeemapping`.`statusId`,
    `eventemployeemapping`.`createdAt`,
    `eventemployeemapping`.`updatedAt`,
    `eventemployeemapping`.`isActive`,
    `eventemployeemapping`.`createdBy`,
    `eventemployeemapping`.`updatedBy`
FROM `unico`.`eventemployeemapping`;

SELECT `feedback`.`feedbackId`,
    `feedback`.`eventId`,
    `feedback`.`eventRating`,
    `feedback`.`eventDescription`,
    `feedback`.`venueId`,
    `feedback`.`venueRating`,
    `feedback`.`venueDescription`,
    `feedback`.`employeeId`,
    `feedback`.`employeeRating`,
    `feedback`.`employeeDescription`,
    `feedback`.`serviceProviderId`,
    `feedback`.`serviceProviderRating`,
    `feedback`.`serviceProviderDescription`,
    `feedback`.`createdAt`,
    `feedback`.`updatedAt`,
    `feedback`.`isActive`,
    `feedback`.`createdBy`,
    `feedback`.`updatedBy`
FROM `unico`.`feedback`;

SELECT `packagedetails`.`packageDetailsId`,
    `packagedetails`.`title`,
    `packagedetails`.`description`,
    `packagedetails`.`guestAmount`,
    `packagedetails`.`totalCost`,
    `packagedetails`.`isStatic`,
    `packagedetails`.`eventTypeId`,
    `packagedetails`.`eventSubTypeId`,
    `packagedetails`.`venueId`,
    `packagedetails`.`createdAt`,
    `packagedetails`.`updatedAt`,
    `packagedetails`.`isActive`,
    `packagedetails`.`createdBy`,
    `packagedetails`.`updatedBy`
FROM `unico`.`packagedetails`;

SELECT `packageserviceprovidermapping`.`packageServiceprovidermappingId`,
    `packageserviceprovidermapping`.`packageId`,
    `packageserviceprovidermapping`.`serviceProviderId`,
    `packageserviceprovidermapping`.`statusId`,
    `packageserviceprovidermapping`.`createdAt`,
    `packageserviceprovidermapping`.`updatedAt`,
    `packageserviceprovidermapping`.`isActive`,
    `packageserviceprovidermapping`.`createdBy`,
    `packageserviceprovidermapping`.`updatedBy`
FROM `unico`.`packageserviceprovidermapping`;

SELECT `packagevenuefacilitymapping`.`packageVenueFacilitymappingId`,
    `packagevenuefacilitymapping`.`packageId`,
    `packagevenuefacilitymapping`.`venueId`,
    `packagevenuefacilitymapping`.`facilityId`,
    `packagevenuefacilitymapping`.`createdAt`,
    `packagevenuefacilitymapping`.`updatedAt`,
    `packagevenuefacilitymapping`.`isActive`,
    `packagevenuefacilitymapping`.`createdBy`,
    `packagevenuefacilitymapping`.`updatedBy`
FROM `unico`.`packagevenuefacilitymapping`;

SELECT `payment`.`paymentId`,
    `payment`.`transactionId`,
    `payment`.`paymentTypeId`,
    `payment`.`paymentStakeholderId`,
    `payment`.`remarks`,
    `payment`.`isReceived`,
    `payment`.`userDetailsId`,
    `payment`.`venueId`,
    `payment`.`attendeeId`,
    `payment`.`employeeId`,
    `payment`.`serviceProviderId`,
    `payment`.`amount`,
    `payment`.`createdAt`
FROM `unico`.`payment`;

SELECT `serviceprovider`.`serviceProviderId`,
    `serviceprovider`.`userDetailsId`,
    `serviceprovider`.`serviceTypeId`,
    `serviceprovider`.`cost`,
    `serviceprovider`.`createdAt`,
    `serviceprovider`.`updatedAt`,
    `serviceprovider`.`isActive`,
    `serviceprovider`.`createdBy`,
    `serviceprovider`.`updatedBy`
FROM `unico`.`serviceprovider`;

SELECT `userdetails`.`userDetailsId`,
    `userdetails`.`firstName`,
    `userdetails`.`lastName`,
    `userdetails`.`serviceProviderName`,
    `userdetails`.`addressId`,
    `userdetails`.`email`,
    `userdetails`.`password`,
    `userdetails`.`mobileNumber`,
    `userdetails`.`isAdmin`,
    `userdetails`.`isCustomer`,
    `userdetails`.`isEmployee`,
    `userdetails`.`isServiceProvider`,
    `userdetails`.`isAuth`,
    `userdetails`.`createdAt`,
    `userdetails`.`updatedAt`,
    `userdetails`.`isActive`,
    `userdetails`.`createdBy`,
    `userdetails`.`updatedBy`
FROM `unico`.`userdetails`;

SELECT `venue`.`venueId`,
    `venue`.`venueName`,
    `venue`.`description`,
    `venue`.`addressId`,
    `venue`.`latitude`,
    `venue`.`longitude`,
    `venue`.`contactNumber`,
    `venue`.`email`,
    `venue`.`guestCapacity`,
    `venue`.`cost`,
    `venue`.`createdAt`,
    `venue`.`updatedAt`,
    `venue`.`isActive`,
    `venue`.`createdBy`,
    `venue`.`updatedBy`
FROM `unico`.`venue`;

SELECT `venueeventtypemapping`.`venueEventTypemappingId`,
    `venueeventtypemapping`.`venueId`,
    `venueeventtypemapping`.`eventTypeId`,
    `venueeventtypemapping`.`eventSubTypeId`,
    `venueeventtypemapping`.`isActive`
FROM `unico`.`venueeventtypemapping`;

SELECT `enuvenuefacility`.`venueFacilityId`,
    `enuvenuefacility`.`facility`,
    `enuvenuefacility`.`description`,
    `enuvenuefacility`.`isActive`
FROM `unico`.`enuvenuefacility`;

SELECT `venuefacilitymapping`.`venueFacilitymappingId`,
    `venuefacilitymapping`.`venueId`,
    `venuefacilitymapping`.`facilityId`,
    `venuefacilitymapping`.`cost`,
    `venuefacilitymapping`.`isActive`
FROM `unico`.`venuefacilitymapping`;

SELECT `venueimagemapping`.`venueImagemappingId`,
    `venueimagemapping`.`venueId`,
    `venueimagemapping`.`image`
FROM `unico`.`venueimagemapping`;
