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
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `enquiry` (
  `enquiryId` int unsigned NOT NULL AUTO_INCREMENT,
  `personName` varchar(45) NOT NULL,
  `mobileNumber` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `eventTypeId` int unsigned NOT NULL,
  `eventSubTypeId` int unsigned DEFAULT NULL,
  `startDate` date NOT NULL,
  `startTime` time NOT NULL,
  `endDate` date NOT NULL,
  `endTime` time NOT NULL,
  `description` varchar(255) NOT NULL,
  `isPublic` tinyint NOT NULL DEFAULT '1',
  `isFree` tinyint NOT NULL DEFAULT '1',
  `estimatedGuest` int NOT NULL,
  `minBudget` double NOT NULL,
  `maxBudget` double NOT NULL,
  `enquiryStatusId` int unsigned NOT NULL DEFAULT '1',
  `response` varchar(10000) DEFAULT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `enuenquirystatus` (
  `statusId` int unsigned NOT NULL AUTO_INCREMENT,
  `status` varchar(45) NOT NULL,
  PRIMARY KEY (`statusId`),
  UNIQUE KEY `statusId_UNIQUE` (`statusId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `enueventstatus` (
  `statusId` int unsigned NOT NULL AUTO_INCREMENT,
  `status` varchar(45) NOT NULL,
  PRIMARY KEY (`statusId`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `enueventtype` (
  `eventTypeId` int unsigned NOT NULL AUTO_INCREMENT,
  `eventType` varchar(45) NOT NULL,
  `description` varchar(45) DEFAULT NULL,
  `isActive` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`eventTypeId`),
  UNIQUE KEY `eventTypeId_UNIQUE` (`eventTypeId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `enuservicetype` (
  `serviceTypeId` int unsigned NOT NULL AUTO_INCREMENT,
  `service` varchar(45) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `basicCost` double NOT NULL DEFAULT '0',
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
  `cost` double NOT NULL,
  `isActive` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`venueFacilityId`),
  UNIQUE KEY `venueFacilityId_UNIQUE` (`venueFacilityId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `enuvenuetype` (
  `enuVenueTypeId` int unsigned NOT NULL AUTO_INCREMENT,
  `venueType` varchar(45) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `isActive` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`enuVenueTypeId`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `event` (
  `eventId` int unsigned NOT NULL AUTO_INCREMENT,
  `eventTitle` varchar(45) NOT NULL,
  `objective` varchar(255) DEFAULT NULL,
  `eventTypeId` int unsigned NOT NULL,
  `eventSubTypeId` int unsigned DEFAULT NULL,
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
  `eventStatusId` int unsigned NOT NULL,
  `bill` blob,
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
  KEY `fk_eventstatusId_idx` (`eventStatusId`),
  CONSTRAINT `fk_createdByevent` FOREIGN KEY (`createdBy`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `fk_customer` FOREIGN KEY (`userDetailsId`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `fk_eventOrganiser` FOREIGN KEY (`eventOrganiserId`) REFERENCES `employee` (`employeeId`),
  CONSTRAINT `fk_eventPackage` FOREIGN KEY (`packageId`) REFERENCES `packagedetails` (`packageDetailsId`),
  CONSTRAINT `fk_eventstatusId` FOREIGN KEY (`eventStatusId`) REFERENCES `enueventstatus` (`statusId`),
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
  `eventSubTypeId` int unsigned DEFAULT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `packageserviceprovidermapping` (
  `packageServiceprovidermappingId` int unsigned NOT NULL AUTO_INCREMENT,
  `packageId` int unsigned NOT NULL,
  `serviceProviderId` int unsigned NOT NULL,
  `statusId` int unsigned NOT NULL DEFAULT '4',
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
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
  `amount` double NOT NULL,
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
  `cost` double unsigned NOT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `userdetails` (
  `userDetailsId` int unsigned NOT NULL AUTO_INCREMENT,
  `firstName` varchar(45) DEFAULT NULL,
  `lastName` varchar(45) DEFAULT NULL,
  `serviceProviderName` varchar(45) DEFAULT NULL,
  `addressId` int unsigned NOT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(255) NOT NULL,
  `mobileNumber` varchar(45) NOT NULL,
  `roleName` varchar(125) NOT NULL DEFAULT 'ROLE_CUSTOMER',
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
  `resetPasswordToken` varchar(255) DEFAULT NULL,
  `resetPasswordTokenTime` datetime DEFAULT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `venue` (
  `venueId` int unsigned NOT NULL AUTO_INCREMENT,
  `venueName` varchar(45) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `venueTypeId` int unsigned NOT NULL,
  `addressId` int unsigned NOT NULL,
  `latitude` decimal(10,5) NOT NULL,
  `longitude` decimal(10,5) NOT NULL,
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
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `contactNumber_UNIQUE` (`contactNumber`),
  KEY `fk_address_venue_idx` (`addressId`),
  KEY `fk_createdBy_venue_idx` (`createdBy`),
  KEY `fk_updatedBy_venue_idx` (`updatedBy`),
  KEY `fk_venueTypeVenue_idx` (`venueTypeId`),
  CONSTRAINT `fk_address_venue` FOREIGN KEY (`addressId`) REFERENCES `address` (`addressId`),
  CONSTRAINT `fk_createdBy_venue` FOREIGN KEY (`createdBy`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `fk_updatedBy_venue` FOREIGN KEY (`updatedBy`) REFERENCES `userdetails` (`userDetailsId`),
  CONSTRAINT `fk_venueTypeVenue` FOREIGN KEY (`venueTypeId`) REFERENCES `enuvenuetype` (`enuVenueTypeId`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `venueeventtypemapping` (
  `venueEventTypemappingId` int unsigned NOT NULL AUTO_INCREMENT,
  `venueId` int unsigned NOT NULL,
  `eventTypeId` int unsigned NOT NULL,
  `eventSubTypeId` int unsigned DEFAULT NULL,
  `isActive` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`venueEventTypemappingId`),
  UNIQUE KEY `venueEventTypemappingId_UNIQUE` (`venueEventTypemappingId`),
  KEY `fk_venue_eventType1_idx` (`eventSubTypeId`),
  KEY `fk_venue_eventType` (`venueId`),
  KEY `fk_venue_eventType2_idx` (`eventTypeId`),
  CONSTRAINT `fk_venue_eventSubType` FOREIGN KEY (`eventSubTypeId`) REFERENCES `enueventsubtype` (`eventSubTypeId`),
  CONSTRAINT `fk_venue_eventType` FOREIGN KEY (`venueId`) REFERENCES `venue` (`venueId`),
  CONSTRAINT `fk_venue_eventType2` FOREIGN KEY (`eventTypeId`) REFERENCES `enueventtype` (`eventTypeId`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `venuefacilitymapping` (
  `venueFacilitymappingId` int unsigned NOT NULL AUTO_INCREMENT,
  `venueId` int unsigned NOT NULL,
  `facilityId` int unsigned NOT NULL,
  `isActive` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`venueFacilitymappingId`),
  UNIQUE KEY `venueFacilitymappingId_UNIQUE` (`venueFacilitymappingId`),
  KEY `fk_venue_facility1_idx` (`facilityId`),
  KEY `fk_venue_facility_idx` (`venueId`),
  CONSTRAINT `fk_venue_facility` FOREIGN KEY (`venueId`) REFERENCES `venue` (`venueId`),
  CONSTRAINT `fk_venue_facility1` FOREIGN KEY (`facilityId`) REFERENCES `enuvenuefacility` (`venueFacilityId`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `venueimagemapping` (
  `venueImagemappingId` int unsigned NOT NULL AUTO_INCREMENT,
  `venueId` int unsigned NOT NULL,
  `image` longblob NOT NULL,
  PRIMARY KEY (`venueImagemappingId`),
  UNIQUE KEY `venueImagemappingId_UNIQUE` (`venueImagemappingId`),
  KEY `fk_venueId_image_idx` (`venueId`),
  CONSTRAINT `fk_venueId_image` FOREIGN KEY (`venueId`) REFERENCES `venue` (`venueId`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;