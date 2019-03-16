
CREATE SCHEMA `testevr` ;

CREATE TABLE `testevr`.`curso` (
  `CODIGO` INT NOT NULL AUTO_INCREMENT,
  `DESCRICAO` VARCHAR(45) NOT NULL,
  `EMENTA` LONGTEXT NULL,
  PRIMARY KEY (`CODIGO`));


CREATE TABLE `testevr`.`aluno` (
  `codigo` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`codigo`));


CREATE TABLE `testevr`.`curso_aluno` (
  `codigo` INT NOT NULL AUTO_INCREMENT,
  `codigo_aluno` INT NULL,
  `codigo_curso` INT NULL,
  PRIMARY KEY (`codigo`),
  INDEX `FK_CURSO_idx` (`codigo_curso` ASC),
  INDEX `FK_ALUNO_idx` (`codigo_aluno` ASC),
  CONSTRAINT `FK_ALUNO`
    FOREIGN KEY (`codigo_aluno`)
    REFERENCES `testevr`.`aluno` (`codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `FK_CURSO`
    FOREIGN KEY (`codigo_curso`)
    REFERENCES `testevr`.`curso` (`CODIGO`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);