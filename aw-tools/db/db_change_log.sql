# a-polyakov  20130201 удаление схемы awrank
DROP SCHEMA `awrank`;
# a-polyakov  20130201  созданире схемы awrank
CREATE SCHEMA `awrank`;
# a-polyakov  20130201 попытка упорядочить столбцы (слишком трудоемко, займусь когда будет по свободнее, темболее структура еще может поменятся)
ALTER TABLE `awrank`.`dicrionary` CHANGE COLUMN `language_code` `language_code` VARCHAR(255) NOT NULL
AFTER `object_type`, CHANGE COLUMN `created_at` `created_at` DATETIME NOT NULL
AFTER `text`, CHANGE COLUMN `updated_at` `updated_at` DATETIME NOT NULL
AFTER `created_at`, CHANGE COLUMN `ended_at` `ended_at` DATETIME NULL
AFTER `updated_at`;