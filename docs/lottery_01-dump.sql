-- MySQL dump 10.13  Distrib 8.0.36, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: lottery_01
-- ------------------------------------------------------
-- Server version	8.0.36-0ubuntu0.20.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `user_strategy_export_001`
--

DROP TABLE IF EXISTS `user_strategy_export_001`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_strategy_export_001` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `u_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `order_id` bigint NOT NULL COMMENT '订单ID',
  `strategy_id` bigint NOT NULL COMMENT '策略ID',
  `strategy_mode` tinyint NOT NULL COMMENT '策略方式（1:单项概率、2:总体概率）',
  `grant_type` tinyint NOT NULL COMMENT '发放奖品方式（1:即时、2:定时[含活动结束]、3:人工）',
  `grant_date` datetime DEFAULT NULL COMMENT '发奖时间',
  `grant_state` tinyint NOT NULL COMMENT '发奖状态',
  `award_id` bigint NOT NULL COMMENT '发奖ID',
  `award_type` tinyint NOT NULL COMMENT '奖品类型（1:文字描述、2:兑换码、3:优惠券、4:实物奖品）',
  `award_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '奖品名称',
  `award_content` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '奖品内容「文字描述、Key、码」',
  `uuid` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '防重ID',
  `mq_state` tinyint NOT NULL COMMENT '消息发送状态（0未发送、1发送成功、2发送失败）',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_uuid` (`uuid`) USING BTREE,
  KEY `user_strategy_export_001_u_id_order_id_award_id_index` (`u_id`,`order_id`,`award_id`),
  KEY `user_strategy_export_001_mq_state_create_time_index` (`mq_state`,`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户策略计算结果表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_strategy_export_002`
--

DROP TABLE IF EXISTS `user_strategy_export_002`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_strategy_export_002` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `u_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户ID',
    `activity_id` bigint NOT NULL COMMENT '活动ID',
    `order_id` bigint NOT NULL COMMENT '订单ID',
    `strategy_id` bigint NOT NULL COMMENT '策略ID',
    `strategy_mode` tinyint NOT NULL COMMENT '策略方式（1:单项概率、2:总体概率）',
    `grant_type` tinyint NOT NULL COMMENT '发放奖品方式（1:即时、2:定时[含活动结束]、3:人工）',
    `grant_date` datetime DEFAULT NULL COMMENT '发奖时间',
    `grant_state` tinyint NOT NULL COMMENT '发奖状态',
    `award_id` bigint NOT NULL COMMENT '发奖ID',
    `award_type` tinyint NOT NULL COMMENT '奖品类型（1:文字描述、2:兑换码、3:优惠券、4:实物奖品）',
    `award_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '奖品名称',
    `award_content` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '奖品内容「文字描述、Key、码」',
    `uuid` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '防重ID',
    `mq_state` tinyint NOT NULL COMMENT '消息发送状态（0未发送、1发送成功、2发送失败）',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_uuid` (`uuid`) USING BTREE,
    KEY `user_strategy_export_001_u_id_order_id_award_id_index` (`u_id`,`order_id`,`award_id`),
    KEY `user_strategy_export_001_mq_state_create_time_index` (`mq_state`,`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户策略计算结果表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_strategy_export_003`
--

DROP TABLE IF EXISTS `user_strategy_export_003`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_strategy_export_003` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `u_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户ID',
    `activity_id` bigint NOT NULL COMMENT '活动ID',
    `order_id` bigint NOT NULL COMMENT '订单ID',
    `strategy_id` bigint NOT NULL COMMENT '策略ID',
    `strategy_mode` tinyint NOT NULL COMMENT '策略方式（1:单项概率、2:总体概率）',
    `grant_type` tinyint NOT NULL COMMENT '发放奖品方式（1:即时、2:定时[含活动结束]、3:人工）',
    `grant_date` datetime DEFAULT NULL COMMENT '发奖时间',
    `grant_state` tinyint NOT NULL COMMENT '发奖状态',
    `award_id` bigint NOT NULL COMMENT '发奖ID',
    `award_type` tinyint NOT NULL COMMENT '奖品类型（1:文字描述、2:兑换码、3:优惠券、4:实物奖品）',
    `award_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '奖品名称',
    `award_content` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '奖品内容「文字描述、Key、码」',
    `uuid` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '防重ID',
    `mq_state` tinyint NOT NULL COMMENT '消息发送状态（0未发送、1发送成功、2发送失败）',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_uuid` (`uuid`) USING BTREE,
    KEY `user_strategy_export_001_u_id_order_id_award_id_index` (`u_id`,`order_id`,`award_id`),
    KEY `user_strategy_export_001_mq_state_create_time_index` (`mq_state`,`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户策略计算结果表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_strategy_export_004`
--

DROP TABLE IF EXISTS `user_strategy_export_004`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_strategy_export_004` (
    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `u_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户ID',
    `activity_id` bigint NOT NULL COMMENT '活动ID',
    `order_id` bigint NOT NULL COMMENT '订单ID',
    `strategy_id` bigint NOT NULL COMMENT '策略ID',
    `strategy_mode` tinyint NOT NULL COMMENT '策略方式（1:单项概率、2:总体概率）',
    `grant_type` tinyint NOT NULL COMMENT '发放奖品方式（1:即时、2:定时[含活动结束]、3:人工）',
    `grant_date` datetime DEFAULT NULL COMMENT '发奖时间',
    `grant_state` tinyint NOT NULL COMMENT '发奖状态',
    `award_id` bigint NOT NULL COMMENT '发奖ID',
    `award_type` tinyint NOT NULL COMMENT '奖品类型（1:文字描述、2:兑换码、3:优惠券、4:实物奖品）',
    `award_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '奖品名称',
    `award_content` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '奖品内容「文字描述、Key、码」',
    `uuid` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '防重ID',
    `mq_state` tinyint NOT NULL COMMENT '消息发送状态（0未发送、1发送成功、2发送失败）',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_uuid` (`uuid`) USING BTREE,
    KEY `user_strategy_export_001_u_id_order_id_award_id_index` (`u_id`,`order_id`,`award_id`),
    KEY `user_strategy_export_001_mq_state_create_time_index` (`mq_state`,`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户策略计算结果表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_take_activity`
--

DROP TABLE IF EXISTS `user_take_activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_take_activity` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `u_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户ID',
  `take_id` bigint NOT NULL COMMENT '活动领取ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `activity_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '活动名称',
  `take_date` datetime NOT NULL COMMENT '活动领取时间',
  `take_count` int NOT NULL COMMENT '领取次数',
  `strategy_id` int NOT NULL COMMENT '抽奖策略ID',
  `state` tinyint NOT NULL COMMENT '活动单使用状态 0未使用、1已使用',
  `uuid` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '防重ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_uuid` (`uuid`) USING BTREE COMMENT '防重ID',
  KEY `user_take_activity_u_id_activity_id_state_take_id_index` (`u_id`,`activity_id`,`state`,`take_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户参与活动记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_take_activity_count`
--

DROP TABLE IF EXISTS `user_take_activity_count`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_take_activity_count` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `u_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `total_count` int NOT NULL COMMENT '可领取次数',
  `left_count` int NOT NULL COMMENT '已领取次数',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_uId_activityId` (`u_id`,`activity_id`,`left_count`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户活动参与次数表';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-01 14:04:31
