DROP SCHEMA IF EXISTS `weatherapp`;

CREATE SCHEMA `weatherapp`;

USE `weatherapp`;

SET FOREIGN_KEY_CHECKS = 0;


/* APP_USER table contains all user data */
CREATE TABLE `app_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sso_id` varchar(30) NOT NULL,
  `password` varchar(100) NOT NULL,
  `first_name` varchar(30) NOT NULL,
  `last_name` varchar(30) NOT NULL,
  `email` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `sso_id` (`sso_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8

/* USER_PROFILE table contains all possible roles */ 
CREATE TABLE `user_profile` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `type` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8

/* JOIN TABLE for MANY-TO-MANY relationship*/  
CREATE TABLE `app_user_user_profile` (
  `user_id` bigint(20) NOT NULL,
  `user_profile_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`user_profile_id`),
  KEY `FK_USER_PROFILE` (`user_profile_id`),
  CONSTRAINT `FK_APP_USER` FOREIGN KEY (`user_id`) REFERENCES `app_user` (`id`),
  CONSTRAINT `FK_USER_PROFILE` FOREIGN KEY (`user_profile_id`) REFERENCES `user_profile` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

/* Create PERSISTENT_LOGIN Table used to store rememberme related stuff */
CREATE TABLE `persistent_logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

/* Create WEATHER_LOCATION Table for saving locations */
CREATE TABLE `weather_location` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `location_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8

/* Create WEATHER_REPORT Table used to store weather forecast data */
CREATE TABLE `weather_report` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sorgulayan_kullanici_id` bigint(20) NOT NULL,
  `sorgulama_zamani` datetime DEFAULT CURRENT_TIMESTAMP,
  `sorgulama_yapilan_lokasyon_id` bigint(20) NOT NULL,
  `sorgulama_yapan_kullanici_ip_adresi` varchar(30) DEFAULT NULL,
  `sorgulama_sonucu` varchar(60) DEFAULT NULL,
  `sorgulama_sonuc_getirme_suresi` bigint(20) NOT NULL,
  `sorgulama_durumu` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8

/* Populate USER_PROFILE Table (Don't forget to change Schema names) */
INSERT INTO `heroku_939ad1bd67157ce`.`user_profile` (`id`, `type`) VALUES ('2', 'ADMIN');
INSERT INTO `heroku_939ad1bd67157ce`.`user_profile` (`id`, `type`) VALUES ('3', 'DBA');
INSERT INTO `heroku_939ad1bd67157ce`.`user_profile` (`id`, `type`) VALUES ('1', 'USER');

/* Populate one Admin User which will further create other users for the application using GUI (Don't forget to change Schema names) */
INSERT INTO `app_user` (`id`,`sso_id`,`password`,`first_name`,`last_name`,`email`) VALUES (21,'root','$2a$10$0ptqgRE/fG8z9SGTyoZinOv7lklcNlWRE4rV76.62XhmYjezW9PMm','root','root','root@root.com');

/* Populate JOIN Table (Don't forget to change Schema names)*/
INSERT INTO `heroku_939ad1bd67157ce`.`app_user_user_profile` (`user_id`, `user_profile_id`) VALUES ('1', '2');