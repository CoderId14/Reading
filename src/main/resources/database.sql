create database reading;
use reading;

CREATE TABLE `attachment` (
  `id` varchar(255) NOT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `data` longblob,
  `file_name` varchar(255) DEFAULT NULL,
  `file_type` varchar(255) DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

CREATE TABLE `chapter` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  `content` text,
  `description` text,
  `child_id` bigint DEFAULT NULL,
  `new_id` bigint DEFAULT NULL,
  `parent_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcp5o271dwb1vj0sgyy3yyukos` (`child_id`),
  KEY `FKgn6htf57c6rfrhtjfxdyvkkyq` (`new_id`),
  KEY `FKtc9ifnut90j2vy7fvww5ogv8j` (`parent_id`),
  CONSTRAINT `FKcp5o271dwb1vj0sgyy3yyukos` FOREIGN KEY (`child_id`) REFERENCES `chapter` (`id`),
  CONSTRAINT `FKgn6htf57c6rfrhtjfxdyvkkyq` FOREIGN KEY (`new_id`) REFERENCES `news` (`id`),
  CONSTRAINT `FKtc9ifnut90j2vy7fvww5ogv8j` FOREIGN KEY (`parent_id`) REFERENCES `chapter` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `chapter_comment_chapter` (
  `chapter_entity_id` bigint NOT NULL,
  `comment_chapter_id` bigint NOT NULL,
  UNIQUE KEY `UK_ln07ln9uqr19r7jk24w54gw7i` (`comment_chapter_id`),
  KEY `FKtfkdnvqyemqavgd1m3shcfi68` (`chapter_entity_id`),
  CONSTRAINT `FKmpw59munovb9qnby3kjnm0xeu` FOREIGN KEY (`comment_chapter_id`) REFERENCES `comments_chapter` (`id`),
  CONSTRAINT `FKtfkdnvqyemqavgd1m3shcfi68` FOREIGN KEY (`chapter_entity_id`) REFERENCES `chapter` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `comments_chapter` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  `body` varchar(255) DEFAULT NULL,
  `chapter_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKaq4ynxs4ytcc2x6qtoaxtg9es` (`chapter_id`),
  KEY `FK8jmkssuq4iv9s9ng8ryy6unct` (`user_id`),
  CONSTRAINT `FK8jmkssuq4iv9s9ng8ryy6unct` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKaq4ynxs4ytcc2x6qtoaxtg9es` FOREIGN KEY (`chapter_id`) REFERENCES `chapter` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `comments_news` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  `body` varchar(255) DEFAULT NULL,
  `news_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKo2o19ehuvwek55wsvk78bgi41` (`news_id`),
  KEY `FKp2w2fu7m9kv8p6dd9mb8ufdj7` (`user_id`),
  CONSTRAINT `FKo2o19ehuvwek55wsvk78bgi41` FOREIGN KEY (`news_id`) REFERENCES `news` (`id`),
  CONSTRAINT `FKp2w2fu7m9kv8p6dd9mb8ufdj7` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `news` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  `content` text,
  `short_description` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `thumbnail` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK89pmf5khugpi1nbdsc6mft0bd` (`thumbnail`),
  KEY `FK4538gbwfa03nwr9edl3fdloo9` (`user_id`),
  CONSTRAINT `FK4538gbwfa03nwr9edl3fdloo9` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK89pmf5khugpi1nbdsc6mft0bd` FOREIGN KEY (`thumbnail`) REFERENCES `attachment` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

CREATE TABLE `news_category` (
  `new_id` bigint NOT NULL,
  `category_id` bigint NOT NULL,
  PRIMARY KEY (`new_id`,`category_id`),
  KEY `FKe8kg0ga2881udnvtit2x1diqw` (`category_id`),
  CONSTRAINT `FKe8kg0ga2881udnvtit2x1diqw` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`),
  CONSTRAINT `FKlgvpcgiydlt0eehn6x52upwi` FOREIGN KEY (`new_id`) REFERENCES `news` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

CREATE TABLE `tag_new` (
  `tag_id` bigint NOT NULL,
  `new_id` bigint NOT NULL,
  PRIMARY KEY (`tag_id`,`new_id`),
  KEY `FKm4n5c06wvb460vwn29on4gkg9` (`new_id`),
  CONSTRAINT `FKay7ph678l27dv37khcdcl1mi9` FOREIGN KEY (`tag_id`) REFERENCES `news` (`id`),
  CONSTRAINT `FKm4n5c06wvb460vwn29on4gkg9` FOREIGN KEY (`new_id`) REFERENCES `tbl_tags` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `tbl_tags` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKpy6jc2i1knanplf27kyt8oytx` (`title`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `full_name` varchar(50) DEFAULT 'Nguyen Van A',
  `password` varchar(255) DEFAULT NULL,
  `status` tinyint(1) DEFAULT '1',
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKsb8bbouer5wak8vyiiy4pf2bx` (`username`),
  UNIQUE KEY `UKob8kqyqqgmefl0aco34akdtpe` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;

CREATE TABLE `user_role` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
