CREATE SCHEMA IF NOT EXISTS shoppinglist_java3;
USE shoppinglist_java3;

CREATE TABLE IF NOT EXISTS shoppinglist_java3.users (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(45) NOT NULL,
  last_name VARCHAR(45) NOT NULL,
  active tinyint(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (id));

CREATE TABLE IF NOT EXISTS shoppinglist_java3.shopping_list(
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  user_id INT UNSIGNED NOT NULL,
  name VARCHAR(45) NOT NULL,
  color VARCHAR(45) NOT NULL,
  created_utc TIMESTAMP NOT NULL,
  modified_utc TIMESTAMP NOT NULL,
  PRIMARY KEY (id));
  
CREATE TABLE IF NOT EXISTS shoppinglist_java3.shopping_list_item (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  shopping_list_id INT UNSIGNED NOT NULL,
  contents STRING NOT NULL,
  priority INT UNSIGNED NOT NULL,
  is_checked tinyint(1) NOT NULL DEFAULT 1,
  created_utc TIMESTAMP NOT NULL,
  modified_utc TIMESTAMP NOT NULL,
  PRIMARY KEY (id));
  
CREATE TABLE IF NOT EXISTS shoppinglist_java3.notes (
  id INT UNSIGNED NOT NULL AUTO_INCREMENT,
  shopping_list_item_id UNSIGNED NOT NULL,
  body String VARCHAR(250) NOT NULL,
  created_utc TIMESTAMP NOT NULL,
  modified_utc TIMESTAMP NOT NULL,
  PRIMARY KEY (id));
  