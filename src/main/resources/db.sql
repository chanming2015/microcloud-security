CREATE TABLE `system_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_at` datetime NOT NULL,
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  `version` int(11) NOT NULL DEFAULT '1',
  `loginname` varchar(64) NOT NULL,
  `password` varchar(512) NOT NULL,
  `secretkey` char(64) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_s99xncrj6ogdbk401vhydfi25` (`loginname`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

CREATE TABLE `system_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_at` datetime NOT NULL,
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  `version` int(11) NOT NULL DEFAULT '1',
  `name` varchar(64) NOT NULL,
  `description` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

CREATE TABLE `system_function` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_at` datetime NOT NULL,
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  `version` int(11) NOT NULL DEFAULT '1',
  `name` varchar(64) NOT NULL,
  `action_url` varchar(512) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

CREATE TABLE `system_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_at` datetime NOT NULL,
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  `version` int(11) NOT NULL DEFAULT '1',
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

CREATE TABLE `system_role_function` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_at` datetime NOT NULL,
  `deleted` bit(1) NOT NULL DEFAULT b'0',
  `version` int(11) NOT NULL DEFAULT '1',
  `role_id` bigint(20) NOT NULL,
  `function_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;