-- MySQL dump 10.13  Distrib 8.0.36, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: lottery
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
-- Table structure for table `activity`
--

DROP TABLE IF EXISTS `activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `activity` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `activity_id` bigint NOT NULL COMMENT '活动ID',
  `activity_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '活动名称',
  `activity_desc` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '活动描述',
  `begin_date_time` datetime NOT NULL COMMENT '开始时间',
  `end_date_time` datetime NOT NULL COMMENT '结束时间',
  `stock_count` int NOT NULL COMMENT '库存',
  `stock_surplus_count` int NOT NULL COMMENT '库存剩余',
  `take_count` int NOT NULL COMMENT '每人可参与次数',
  `strategy_id` bigint NOT NULL COMMENT '抽奖策略ID',
  `state` tinyint NOT NULL COMMENT '活动状态：1编辑、2提审、3撤审、4通过、5运行(审核通过后worker扫描状态)、6拒绝、7关闭、8开启',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `activity_activity_id_uindex` (`activity_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='活动配置';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `award`
--

DROP TABLE IF EXISTS `award`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `award` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `award_id` bigint NOT NULL COMMENT '奖品ID',
  `award_type` tinyint NOT NULL COMMENT '奖品类型（1:文字描述、2:兑换码、3:优惠券、4:实物奖品）',
  `award_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '奖品名称',
  `award_content` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '奖品内容「文字描述、Key、码」',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_award_id` (`award_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='奖品配置';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rule_tree`
--

DROP TABLE IF EXISTS `rule_tree`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rule_tree` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tree_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '规则树ID',
  `tree_desc` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '规则树描述',
  `tree_root_node_id` bigint NOT NULL COMMENT '规则树根ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rule_tree_node`
--

DROP TABLE IF EXISTS `rule_tree_node`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rule_tree_node` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tree_id` int NOT NULL COMMENT '规则树ID',
  `node_type` tinyint NOT NULL COMMENT '节点类型：1中间节点、2叶子结点',
  `node_value` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '节点值',
  `rule_key` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '规则Key',
  `rule_desc` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '规则描述',
  PRIMARY KEY (`id`),
  KEY `rule_tree_node_tree_id_index` (`tree_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `rule_tree_node_line`
--

DROP TABLE IF EXISTS `rule_tree_node_line`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rule_tree_node_line` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tree_id` bigint NOT NULL COMMENT '规则树ID',
  `node_id_from` bigint NOT NULL COMMENT '节点From',
  `node_id_to` bigint NOT NULL COMMENT '节点To',
  `rule_limit_type` int NOT NULL COMMENT '限定类型；1:=;2:>;3:<;4:>=;5<=;6:enum[枚举范围];7:果实',
  `rule_limit_value` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '限定值',
  PRIMARY KEY (`id`),
  KEY `rule_tree_node_line_tree_id_node_id_from_index` (`tree_id`,`node_id_from`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `strategy`
--

DROP TABLE IF EXISTS `strategy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `strategy` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `strategy_id` bigint NOT NULL COMMENT '策略ID',
  `strategy_desc` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '策略描述',
  `strategy_mode` tinyint NOT NULL COMMENT '策略方式（1:单项概率、2:总体概率）',
  `grant_type` tinyint NOT NULL COMMENT '发放奖品方式（1:即时、2:定时[含活动结束]、3:人工）',
  `grant_date` datetime DEFAULT NULL COMMENT '发放奖品时间',
  `ext_info` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '扩展信息',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `strategy_strategyId_uindex` (`strategy_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='策略配置';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `strategy_detail`
--

DROP TABLE IF EXISTS `strategy_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `strategy_detail` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `strategy_id` bigint NOT NULL COMMENT '策略ID',
  `award_id` bigint NOT NULL COMMENT '奖品ID',
  `award_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '奖品描述',
  `award_count` int NOT NULL COMMENT '奖品库存',
  `award_surplus_count` int NOT NULL DEFAULT '0' COMMENT '奖品剩余库存',
  `award_rate` decimal(10,9) NOT NULL COMMENT '中奖概率',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `strategy_detail_strategy_id_award_surplus_count_award_id_index` (`strategy_id`,`award_surplus_count`,`award_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='策略明细';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-01 14:04:22
