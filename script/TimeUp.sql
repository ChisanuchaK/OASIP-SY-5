-- MySQL Script generated by MySQL Workbench
-- Mon May  9 22:13:31 2022
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema TimeUp
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema TimeUp
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `TimeUp` DEFAULT CHARACTER SET utf8 ;
USE `TimeUp` ;

-- -----------------------------------------------------
-- Table `TimeUp`.`eventCategory`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `TimeUp`.`eventCategory` (
  `eventCategoryId` INT NOT NULL,
  `eventCategoryName` VARCHAR(100) NOT NULL,
  `eventDuration` INT NOT NULL,
  `eventColor` VARCHAR(45) NOT NULL,
  `eventCategoryDescription` VARCHAR(500) NULL,
  UNIQUE INDEX `eventCategoeyName_UNIQUE` (`eventCategoryName` ASC) VISIBLE,
  PRIMARY KEY (`eventCategoryId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `TimeUp`.`booking`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `TimeUp`.`booking` (
  `idbooking` INT NOT NULL AUTO_INCREMENT,
  `bookingName` VARCHAR(100) NOT NULL,
  `bookingEmail` VARCHAR(100) NOT NULL,
  `eventStartTime` TIMESTAMP NOT NULL,
  `eventDuration` INT NOT NULL,
  `eventNotes` VARCHAR(500) NULL,
  `eventCategory` INT NOT NULL,
  `user_iduser` INT ,
  PRIMARY KEY (`idbooking`),
  INDEX `fk_booking_eventCategory_idx` (`eventCategory` ASC) VISIBLE,
  CONSTRAINT `fk_booking_eventCategory`
    FOREIGN KEY (`eventCategory`)
    REFERENCES `TimeUp`.`eventCategory` (`eventCategoryId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
CONSTRAINT `fk_booking_user`
    FOREIGN KEY (`user_iduser`) REFERENCES `TimeUp`.`user` (`iduser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
    )  
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `TimeUp`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `TimeUp`.`user` (
`iduser` INT NOT NULL AUTO_INCREMENT,
`name` VARCHAR(100) NOT NULL,
`email` VARCHAR(50) NOT NULL,
`password` VARCHAR(100) NOT NULL,
`role enum` enum ('admin', 'lecturer', 'student'),
`createdOn` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
`updatedOn` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE  CURRENT_TIMESTAMP,
primary key (`iduser`),
unique index `name` (`name` asc) visible,
unique index `email` (`email`) visible

)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `TimeUp`.`event_category_owner`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `TimeUp`.`eventCategoryOwner` (
  `eventCategory_eventCategoryId` INT NOT NULL,
  `user_iduser` INT NOT NULL,
  PRIMARY KEY (`eventCategory_eventCategoryId`, `user_iduser`),
  INDEX `fk_user_id` (`user_iduser` ASC) VISIBLE,
  INDEX `fk_eventCategory_id` (`eventCategory_eventCategoryId` ASC) VISIBLE,
  CONSTRAINT `fk_eventCategory_id`
    FOREIGN KEY (`eventCategory_eventCategoryId`) REFERENCES `TimeUp`.`eventCategory` (`eventCategoryId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_id`
    FOREIGN KEY (`user_iduser`) REFERENCES `TimeUp`.`user` (`iduser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE user 'admin'@'%' IDENTIFIED  by 'admin123';
CREATE user 'dev'@'%' IDENTIFIED  by 'dev123';
GRANT ALL PRIVILEGES ON *.* TO 'admin'@'%';
GRANT insert , update , delete , select on TimeUp.* to 'dev'@'%';
FLUSH PRIVILEGES ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
