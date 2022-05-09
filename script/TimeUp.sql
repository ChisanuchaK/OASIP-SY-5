-- MySQL Script generated by MySQL Workbench
-- Mon May  9 21:50:41 2022
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
  `eventId` INT NOT NULL,
  `eventCategoryName` VARCHAR(100) NOT NULL,
  `eventDuration` INT NOT NULL,
  `eventColor` VARCHAR(45) NOT NULL,
  `eventCategoryDescription` VARCHAR(500) NULL,
  UNIQUE INDEX `eventCategoeyName_UNIQUE` (`eventCategoryName` ASC) VISIBLE,
  PRIMARY KEY (`eventId`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `TimeUp`.`booking`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `TimeUp`.`booking` (
  `idbooking` INT NOT NULL AUTO_INCREMENT,
  `bookingName` VARCHAR(100) NOT NULL,
  `bookingEmail` VARCHAR(100) NOT NULL,
  `eventCategory` VARCHAR(100) NOT NULL,
  `eventStartTime` TIMESTAMP NOT NULL,
  `eventDuration` INT NOT NULL,
  `eventNotes` VARCHAR(500) NULL,
  PRIMARY KEY (`idbooking`),
  INDEX `fk_booking_eventCategory_idx` (`eventCategory` ASC) VISIBLE,
  CONSTRAINT `fk_booking_eventCategory`
    FOREIGN KEY (`eventCategory`)
    REFERENCES `TimeUp`.`eventCategory` (`eventCategoryName`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
