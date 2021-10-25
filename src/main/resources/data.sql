CREATE TABLE `csv_records`.`record`
(
    `id`      VARCHAR(45) NOT NULL,
    `name`    VARCHAR(45) NULL,
    `docDate` VARCHAR(45) NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE
);