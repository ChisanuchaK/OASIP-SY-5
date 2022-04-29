-- MySQL Script generated by MySQL Workbench
-- Fri Apr 29 14:58:08 2022
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
DROP TABLE IF EXISTS `TimeUp`.`eventCategory` ;

CREATE TABLE IF NOT EXISTS `TimeUp`.`eventCategory` (
  `eventCategoryName` VARCHAR(100) NOT NULL,
  `eventDuration` INT NOT NULL,
  `eventCategoryDescription` VARCHAR(500) NULL,
  UNIQUE INDEX `eventCategoeyName_UNIQUE` (`eventCategoryName` ASC) VISIBLE,
  PRIMARY KEY (`eventCategoryName`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `TimeUp`.`booking`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `TimeUp`.`booking` ;

CREATE TABLE IF NOT EXISTS `TimeUp`.`booking` (
  `idbooking` INT NOT NULL AUTO_INCREMENT,
  `bookingName` VARCHAR(100) NOT NULL,
  `bookingEmail` VARCHAR(100) NOT NULL,
  `eventCategoryName` VARCHAR(100) NOT NULL,
  `eventStartTime` DATETIME NOT NULL,
  `eventDuration` INT NOT NULL,
  `eventNotes` VARCHAR(500) NULL,
  INDEX `fk_booking_eventCategory_idx` (`eventCategoryName` ASC) VISIBLE,
  PRIMARY KEY (`idbooking`),
  CONSTRAINT `fk_booking_eventCategory`
    FOREIGN KEY (`eventCategoryName`)
    REFERENCES `TimeUp`.`eventCategory` (`eventCategoryName`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;