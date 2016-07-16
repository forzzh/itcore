/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50540
Source Host           : localhost:3306
Source Database       : core

Target Server Type    : MYSQL
Target Server Version : 50540
File Encoding         : 65001

Date: 2016-05-11 16:57:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for syslabel
-- ----------------------------
DROP TABLE IF EXISTS `syslabel`;
CREATE TABLE `syslabel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createDate` datetime DEFAULT NULL,
  `description` char(200) DEFAULT NULL,
  `foucsGroup` int(11) DEFAULT NULL,
  `foucsPerson` int(11) DEFAULT NULL,
  `name` char(200) DEFAULT NULL,
  `topics` int(11) DEFAULT NULL,
  `foucsBook` int(11) DEFAULT NULL,
  `user` int(11) DEFAULT NULL,
  `score` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_govio3uwj8y0bytssygtsr0d9` (`user`)
) ENGINE=MyISAM AUTO_INCREMENT=230 DEFAULT CHARSET=gbk;

-- ----------------------------
-- Records of syslabel
-- ----------------------------
INSERT INTO `syslabel` VALUES ('1', '2016-05-03 09:36:21', '1234', '11', '111', '11', '1', '59', null, '0');
INSERT INTO `syslabel` VALUES ('2', '2016-05-04 15:04:55', null, '1', '1', '123', '6', null, '1', '0');
INSERT INTO `syslabel` VALUES ('3', '2016-05-04 16:22:35', null, '1', '1', '1234', '2', null, '1', '0');
INSERT INTO `syslabel` VALUES ('4', '2016-05-04 16:22:35', null, '1', '1', '456', '0', null, '1', '0');
INSERT INTO `syslabel` VALUES ('5', '2016-05-04 16:36:49', null, '0', '1', '13456', '2', null, '1', '0');
INSERT INTO `syslabel` VALUES ('6', '2016-05-11 16:49:39', '12312312', '0', '0', '132131', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('7', '2016-05-11 16:49:42', '12312312', '0', '0', '111', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('8', '2016-05-11 16:49:45', '12312312', '0', '0', '222', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('9', '2016-05-11 16:49:49', '12312312', '0', '0', '32131', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('10', '2016-05-11 16:49:52', '12312312', '0', '0', '2321312', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('11', '2016-05-11 16:50:14', '12312312', '0', '0', '111222', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('12', '2016-05-11 16:50:16', '12312312', '0', '0', '111222aaa', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('13', '2016-05-11 16:50:19', '12312312', '0', '0', '111222aaabbb', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('14', '2016-05-11 16:50:21', '12312312', '0', '0', '111222aaabbbddd', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('15', '2016-05-11 16:50:26', '12312312', '0', '0', 'bbbb1111aaa', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('16', '2016-05-11 16:50:46', '12312312', '0', '0', '131231231', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('17', '2016-05-11 16:50:49', '12312312', '0', '0', 'bbbb111', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('18', '2016-05-11 16:50:52', '12312312', '0', '0', '12312321', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('19', '2016-05-11 16:50:55', '12312312', '0', '0', '12312321321321', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('20', '2016-05-11 16:51:08', '12312312', '0', '0', '31231aaa', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('21', '2016-05-11 16:53:30', '12312312', '0', '0', '31fbf391-d035-4680-ab05-e7cf9d9a1ab5', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('22', '2016-05-11 16:53:31', '12312312', '0', '0', 'fd521a62-7b25-4e76-b7fe-b528c2f6b24a', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('23', '2016-05-11 16:53:32', '12312312', '0', '0', '673c82f5-a40f-4b3e-abe3-2345c3bd2298', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('24', '2016-05-11 16:53:32', '12312312', '0', '0', '31f4424a-4b81-43ef-9481-5aec4000d928', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('25', '2016-05-11 16:53:32', '12312312', '0', '0', '55ca63cf-0894-4236-8fac-a879f71a2a86', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('26', '2016-05-11 16:53:33', '12312312', '0', '0', 'c4c03bae-2925-4b2f-bbe8-bd6cc7cf1310', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('27', '2016-05-11 16:53:33', '12312312', '0', '0', 'fee82a2f-47d5-4e4a-9b05-365f4598190d', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('28', '2016-05-11 16:53:33', '12312312', '0', '0', 'fcee71fe-e737-4639-bebe-f25ac048ae9c', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('29', '2016-05-11 16:53:33', '12312312', '0', '0', 'd5489f0d-18c7-49f5-a242-4dbd64d15eeb', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('30', '2016-05-11 16:53:34', '12312312', '0', '0', '36484cd8-79f2-44ed-b3cd-b7c9a4661420', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('31', '2016-05-11 16:53:34', '12312312', '0', '0', '3b0acc63-8c7d-4b63-a2d3-997b29bf1da6', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('32', '2016-05-11 16:53:34', '12312312', '0', '0', 'c4be8430-2c3e-43c2-867e-4b5ecf4f9b82', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('33', '2016-05-11 16:53:35', '12312312', '0', '0', '13aaf9f3-54a6-4f98-8e1f-ab9078c03227', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('34', '2016-05-11 16:53:35', '12312312', '0', '0', '4548e61c-1849-408e-adcb-b4c34684d053', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('35', '2016-05-11 16:53:35', '12312312', '0', '0', 'f2ffe771-d407-4ed8-9a1b-19eafd605e29', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('36', '2016-05-11 16:53:35', '12312312', '0', '0', '212540ba-3cc3-44d9-ae1d-bd9cacb24e53', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('37', '2016-05-11 16:53:36', '12312312', '0', '0', '195cb258-2112-4093-851f-d4baa887f9f3', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('38', '2016-05-11 16:53:36', '12312312', '0', '0', 'd1d5185c-a027-4c13-87d9-c49978f34b56', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('39', '2016-05-11 16:53:36', '12312312', '0', '0', '0c168989-825e-4416-875e-6ad2b2cadd84', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('40', '2016-05-11 16:53:36', '12312312', '0', '0', 'e084bdcd-9517-40e0-8483-464d601db1fa', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('41', '2016-05-11 16:53:37', '12312312', '0', '0', '50117abc-4758-4ace-94f2-04dd22556007', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('42', '2016-05-11 16:53:37', '12312312', '0', '0', 'ecfa030a-a102-48e6-9e40-40db4a296d09', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('43', '2016-05-11 16:53:37', '12312312', '0', '0', 'dedbaeac-6d92-49e0-b9ca-e98603aad581', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('44', '2016-05-11 16:53:37', '12312312', '0', '0', 'e1d314b1-a507-4995-8b90-dc064e5e9ec9', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('45', '2016-05-11 16:53:38', '12312312', '0', '0', '91e7caaa-4a92-44bd-994f-2369cf28502b', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('46', '2016-05-11 16:53:38', '12312312', '0', '0', '63a46ea1-11f0-48d8-81a2-50e5e73747cb', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('47', '2016-05-11 16:53:39', '12312312', '0', '0', 'a2d65056-9dfd-4300-96af-08b1b90d05f4', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('48', '2016-05-11 16:53:39', '12312312', '0', '0', '6afe194c-dc32-437f-bb3d-f3ed5160c4c9', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('49', '2016-05-11 16:53:39', '12312312', '0', '0', 'b7ca21e9-0af0-46f3-b423-6e7f4465b2c9', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('50', '2016-05-11 16:53:39', '12312312', '0', '0', '216c7964-ad4e-49ff-8b7c-24ac136a8b05', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('51', '2016-05-11 16:53:40', '12312312', '0', '0', '11b7262f-6ab2-46e7-b639-c96e6b2484f0', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('52', '2016-05-11 16:53:40', '12312312', '0', '0', '18c42718-3be0-4e20-bcf1-af607ec9a2df', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('53', '2016-05-11 16:53:40', '12312312', '0', '0', '0bff780b-be92-422b-855f-ed6b263fd86e', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('54', '2016-05-11 16:53:41', '12312312', '0', '0', '02064b89-2ad4-400f-aaa0-fd98312860cf', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('55', '2016-05-11 16:53:41', '12312312', '0', '0', '25de9655-cfb2-4ba8-a45f-9e50f71cb1e3', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('56', '2016-05-11 16:53:41', '12312312', '0', '0', 'd2628177-3b83-4f71-8f94-1870c4ae1618', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('57', '2016-05-11 16:53:42', '12312312', '0', '0', 'd3516970-7ca3-4197-acb3-8403a0dbc55f', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('58', '2016-05-11 16:53:42', '12312312', '0', '0', 'ca67ab3a-898d-4e1c-b570-ca6712eaabba', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('59', '2016-05-11 16:53:42', '12312312', '0', '0', '03113c2b-68a6-4e06-a030-31887c50a4e8', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('60', '2016-05-11 16:53:42', '12312312', '0', '0', '3d79bc97-a87a-47e7-b99f-201555d7c0e6', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('61', '2016-05-11 16:53:42', '12312312', '0', '0', 'd27c2cd9-1462-41f2-8cc5-b972dd90ddca', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('62', '2016-05-11 16:53:43', '12312312', '0', '0', '2462f21e-108d-4646-a47f-248780842d85', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('63', '2016-05-11 16:53:43', '12312312', '0', '0', '6f916d52-dcde-488a-a35f-85a42a816983', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('64', '2016-05-11 16:53:45', '12312312', '0', '0', 'd28938b6-7c52-487d-87f7-b2c3863aa05f', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('65', '2016-05-11 16:53:45', '12312312', '0', '0', 'f781ed95-996f-4563-ab22-37c052176c74', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('66', '2016-05-11 16:53:45', '12312312', '0', '0', '97a231c8-85a8-4eb3-bf59-590eb43babd3', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('67', '2016-05-11 16:53:46', '12312312', '0', '0', '25628dc2-096f-4666-94bc-fa1ffdcc7fbc', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('68', '2016-05-11 16:53:46', '12312312', '0', '0', 'a2786db6-ceec-4b8f-8095-c6f52b2a45e9', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('69', '2016-05-11 16:53:47', '12312312', '0', '0', '6f6eb0b3-2d02-4e23-828b-96b45ed3f0c9', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('70', '2016-05-11 16:53:47', '12312312', '0', '0', 'a576fa85-acec-4d3f-8eea-70a86b77ea04', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('71', '2016-05-11 16:53:47', '12312312', '0', '0', 'a5487df2-da19-4dae-aa51-c33e6c520a17', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('72', '2016-05-11 16:53:47', '12312312', '0', '0', 'f3d1168c-fd64-478c-a8f6-952747c085ef', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('73', '2016-05-11 16:53:48', '12312312', '0', '0', 'c763f430-68f5-4e8a-9261-79288983bc8e', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('74', '2016-05-11 16:53:48', '12312312', '0', '0', 'b72705bc-8620-4081-a0c9-ce4880c12ef2', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('75', '2016-05-11 16:53:48', '12312312', '0', '0', '182e31d5-97b9-46f0-83ec-36880f85ef50', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('76', '2016-05-11 16:53:48', '12312312', '0', '0', '5d8aa396-4b77-4324-958e-2803602cd42a', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('77', '2016-05-11 16:53:49', '12312312', '0', '0', 'cfa970b8-1b34-48f8-aa55-b79186bbec17', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('78', '2016-05-11 16:53:49', '12312312', '0', '0', '48a18186-72ed-4442-bdc0-6c1d12e76578', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('79', '2016-05-11 16:53:49', '12312312', '0', '0', '5d770556-d55a-4a3b-a1e9-67144fb752de', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('80', '2016-05-11 16:53:49', '12312312', '0', '0', 'd458c755-cc61-4b9b-bef0-e9c7c56ff7ff', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('81', '2016-05-11 16:53:50', '12312312', '0', '0', '53b20b84-7aff-47ef-9601-867ad969cef4', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('82', '2016-05-11 16:53:50', '12312312', '0', '0', '91c313cd-0c46-4acd-b5f0-b4bef83f1fbb', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('83', '2016-05-11 16:53:50', '12312312', '0', '0', 'da067ee4-a9c5-4cc9-9747-aa9b9a2895e2', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('84', '2016-05-11 16:53:51', '12312312', '0', '0', 'bca93e64-8fae-4728-a516-d53075758fc3', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('85', '2016-05-11 16:53:51', '12312312', '0', '0', '25d108f5-655b-4d15-a17f-bb02e524da0a', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('86', '2016-05-11 16:53:51', '12312312', '0', '0', '81a3caf3-f23e-4632-bbc5-343b41bf06ee', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('87', '2016-05-11 16:53:51', '12312312', '0', '0', 'b8f33c8e-9893-43c7-a9d3-1a3c28cc68d9', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('88', '2016-05-11 16:53:52', '12312312', '0', '0', 'eb8a7aef-31c9-4ad0-b5dc-f0f54273b55b', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('89', '2016-05-11 16:53:52', '12312312', '0', '0', '26d8d329-d285-4373-bf18-dc416cbb321c', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('90', '2016-05-11 16:53:52', '12312312', '0', '0', 'ffd3aab9-2998-49c6-8976-5e787c841f7d', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('91', '2016-05-11 16:53:53', '12312312', '0', '0', '43f5a4ed-48ff-44bd-90d8-9e45011892d9', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('92', '2016-05-11 16:53:53', '12312312', '0', '0', 'ceac3ca5-e3cf-4147-b505-38c68a95e04b', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('93', '2016-05-11 16:53:53', '12312312', '0', '0', '27162f2a-957d-4820-8725-9fc632ad8727', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('94', '2016-05-11 16:53:53', '12312312', '0', '0', 'fd3bea22-839a-48ec-b12f-d97912826024', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('95', '2016-05-11 16:53:54', '12312312', '0', '0', 'ef669ecd-02f6-4ea9-900e-5a86c6e7374c', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('96', '2016-05-11 16:53:54', '12312312', '0', '0', '77d3b872-12b2-475f-b964-dcf4d20c8a49', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('97', '2016-05-11 16:53:54', '12312312', '0', '0', '554fb69e-d422-44b3-8e6d-2fd32e2d9c04', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('98', '2016-05-11 16:53:54', '12312312', '0', '0', '6c9c7bcd-7e0a-408a-be2e-a71f761ec345', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('99', '2016-05-11 16:53:55', '12312312', '0', '0', 'dc77fac2-0fee-4f25-8727-78f2bcb624b0', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('100', '2016-05-11 16:53:55', '12312312', '0', '0', '1c6e5f37-de97-4b45-886e-62cbdd3f55ca', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('101', '2016-05-11 16:53:55', '12312312', '0', '0', 'd9bd42c9-f4f2-468d-bfc3-9e6c5ee04618', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('102', '2016-05-11 16:53:56', '12312312', '0', '0', '1261cd1c-ee0c-4469-bb96-4e463187a1d0', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('103', '2016-05-11 16:53:56', '12312312', '0', '0', 'ef52a627-a018-4809-8f56-297002b59cdc', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('104', '2016-05-11 16:53:56', '12312312', '0', '0', '70baa49c-4986-485a-a70f-e9f394fbada3', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('105', '2016-05-11 16:53:57', '12312312', '0', '0', '51b349a3-8ebf-4e19-b59b-2f06573ef306', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('106', '2016-05-11 16:53:57', '12312312', '0', '0', '24fe7749-0018-4d43-8a8e-02e18596d8c4', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('107', '2016-05-11 16:53:57', '12312312', '0', '0', '4ac62d43-7208-4b06-be2d-f6bed0baffc4', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('108', '2016-05-11 16:53:58', '12312312', '0', '0', '2be3e5d3-a497-44aa-bf46-292bf3c39398', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('109', '2016-05-11 16:53:58', '12312312', '0', '0', '22990581-6ddc-49a7-aae5-3ed267302cbd', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('110', '2016-05-11 16:53:58', '12312312', '0', '0', 'd8ec8f9d-6638-4980-8670-26d7dea7b596', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('111', '2016-05-11 16:53:59', '12312312', '0', '0', '6624a7cc-a7c3-486d-b350-05ea53f18255', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('112', '2016-05-11 16:53:59', '12312312', '0', '0', '97f3abc3-1383-4d3e-8b5e-d06fab7d0648', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('113', '2016-05-11 16:53:59', '12312312', '0', '0', '786f648e-fdbe-4238-88b5-b84f8eae16f0', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('114', '2016-05-11 16:54:00', '12312312', '0', '0', 'c61d3121-b214-4da5-b014-6724b08d3805', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('115', '2016-05-11 16:54:00', '12312312', '0', '0', 'e7e1f02c-040a-437a-a784-7841168f48ec', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('116', '2016-05-11 16:54:00', '12312312', '0', '0', '073527ed-ab8f-43b3-b1a6-8c00c46ef28c', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('117', '2016-05-11 16:54:00', '12312312', '0', '0', '8788025f-cad7-4222-9a19-fc5895b881a2', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('118', '2016-05-11 16:54:01', '12312312', '0', '0', '6f501d18-4956-4824-b11a-0a41c8ea732a', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('119', '2016-05-11 16:54:02', '12312312', '0', '0', 'ee12b6fd-b480-4bd0-97a3-c1f9ac99bbe2', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('120', '2016-05-11 16:54:02', '12312312', '0', '0', '3b5c1e86-5a44-4305-809b-fdedcdb457d5', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('121', '2016-05-11 16:54:02', '12312312', '0', '0', 'd1abfaac-384f-4183-bec9-e13b86c7a5a1', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('122', '2016-05-11 16:54:02', '12312312', '0', '0', '282d25a9-aedd-48c5-a22b-41689ddcdcab', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('123', '2016-05-11 16:54:03', '12312312', '0', '0', 'f1a9465d-bdf1-4172-afc7-2fb17008312c', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('124', '2016-05-11 16:54:03', '12312312', '0', '0', '051a74c6-0c53-41fd-b647-d5630f91f55e', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('125', '2016-05-11 16:54:03', '12312312', '0', '0', '5e586ad8-912a-4400-aaa5-8fd4c6ddbddb', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('126', '2016-05-11 16:54:04', '12312312', '0', '0', '29236f35-757b-47ea-b488-b04208141365', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('127', '2016-05-11 16:54:04', '12312312', '0', '0', 'd329db05-8628-4341-8af3-237d99c2d825', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('128', '2016-05-11 16:54:05', '12312312', '0', '0', '1eb87d60-38ef-4042-938c-3c068bd4bd87', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('129', '2016-05-11 16:54:05', '12312312', '0', '0', '8e0b6134-c5cb-4d0a-ac4a-ec3436f16910', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('130', '2016-05-11 16:54:05', '12312312', '0', '0', 'edde82d0-21e7-4ec3-a14c-abdcd3f308e5', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('131', '2016-05-11 16:54:05', '12312312', '0', '0', '30048037-745e-45e0-bc22-9a6dc1cde6d2', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('132', '2016-05-11 16:54:06', '12312312', '0', '0', '55f1b51c-9cd3-4be8-9f72-b216e1515d06', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('133', '2016-05-11 16:54:06', '12312312', '0', '0', '756cdfbe-22e7-4205-83d7-24da601aa08a', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('134', '2016-05-11 16:54:06', '12312312', '0', '0', '1e53e0fa-fb6d-424d-8dee-50181b07fdfc', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('135', '2016-05-11 16:54:07', '12312312', '0', '0', 'abf83ee2-c8e9-494e-8f45-03a97cd6c840', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('136', '2016-05-11 16:54:07', '12312312', '0', '0', '4773a992-ecea-4fa1-91f0-69120743fd6a', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('137', '2016-05-11 16:54:07', '12312312', '0', '0', '6ffaea70-e397-4a47-8a4c-396c85af10d8', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('138', '2016-05-11 16:54:08', '12312312', '0', '0', '71dad730-9dbd-450e-953f-68905fbb6144', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('139', '2016-05-11 16:54:08', '12312312', '0', '0', 'c33df5a0-7b24-4604-a659-dac89de2118e', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('140', '2016-05-11 16:54:08', '12312312', '0', '0', '21de0d1c-b372-4408-9c3b-ec52caf61586', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('141', '2016-05-11 16:54:09', '12312312', '0', '0', 'e670f17b-2381-4e02-a84f-00808f53cd24', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('142', '2016-05-11 16:54:09', '12312312', '0', '0', '2dfd2bab-ea21-4b56-81d9-1224a3575419', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('143', '2016-05-11 16:54:09', '12312312', '0', '0', '0c60ecef-3e3a-4501-902f-00cea5c04941', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('144', '2016-05-11 16:54:10', '12312312', '0', '0', 'e148dee3-d057-421b-a6d7-ab3bb01f8fea', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('145', '2016-05-11 16:54:10', '12312312', '0', '0', '48ad7522-fd49-4365-9e82-96538c4ecb6a', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('146', '2016-05-11 16:54:10', '12312312', '0', '0', 'eb2facdb-de30-421e-adc9-90cfd9bc6cee', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('147', '2016-05-11 16:54:11', '12312312', '0', '0', '8cd1b4cc-e393-4c66-b6d9-4f9b70e80325', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('148', '2016-05-11 16:54:11', '12312312', '0', '0', 'c280751e-ca52-4911-8f36-ead22336e290', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('149', '2016-05-11 16:54:11', '12312312', '0', '0', '43e2c6ba-167f-4f3d-9fd5-d417ee437feb', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('150', '2016-05-11 16:54:12', '12312312', '0', '0', 'b584fce4-34f9-48f0-885f-76193dd3135e', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('151', '2016-05-11 16:54:12', '12312312', '0', '0', 'a10d48ce-7e6a-45d6-9f42-32f21b90dedc', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('152', '2016-05-11 16:54:12', '12312312', '0', '0', '91bf365f-b677-4a83-be41-c0349443300d', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('153', '2016-05-11 16:54:28', '12312312', '0', '0', '73e2b62e-1295-4a21-b3f9-693f00e6c0c6', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('154', '2016-05-11 16:54:28', '12312312', '0', '0', '9f95b0bc-007c-45e6-b996-34f3c4c7b0f1', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('155', '2016-05-11 16:54:29', '12312312', '0', '0', '8622b5a6-ac2e-4d2c-adcc-4bacb730f8b3', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('156', '2016-05-11 16:54:29', '12312312', '0', '0', '2e0c3919-5c1b-4957-b21d-58b0d94e3b61', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('157', '2016-05-11 16:54:30', '12312312', '0', '0', 'a4728d4f-a543-4edf-8784-c3dea79df293', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('158', '2016-05-11 16:54:30', '12312312', '0', '0', 'e8bc1ae7-080b-4d90-bedd-38147954a464', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('159', '2016-05-11 16:54:30', '12312312', '0', '0', 'da05af2b-9db2-4c15-ba7e-f01aa451629a', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('160', '2016-05-11 16:54:31', '12312312', '0', '0', '2cd27e93-6e91-4180-87bd-39ddc75ae9a9', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('161', '2016-05-11 16:54:31', '12312312', '0', '0', 'f42a53ec-6bce-4a91-a151-72377e8c92be', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('162', '2016-05-11 16:54:31', '12312312', '0', '0', 'efc5c68a-cb7d-40ae-b4f2-dbdd6556fadb', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('163', '2016-05-11 16:54:32', '12312312', '0', '0', '867d3ca5-97f2-45cd-b04e-40124a0eb5a1', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('164', '2016-05-11 16:54:32', '12312312', '0', '0', 'a4ed3a62-7961-4821-94ec-77a359c501c6', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('165', '2016-05-11 16:54:32', '12312312', '0', '0', '247cd5b6-e5ef-4020-ab1c-208a7c7d085a', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('166', '2016-05-11 16:54:32', '12312312', '0', '0', 'addc44af-9bea-4542-9606-da68bae88374', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('167', '2016-05-11 16:54:33', '12312312', '0', '0', '48fc137e-9585-4655-b3e6-1c05d078e35e', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('168', '2016-05-11 16:54:33', '12312312', '0', '0', '78394ea6-ae46-4425-85aa-ff98f31ff4d3', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('169', '2016-05-11 16:54:33', '12312312', '0', '0', '0c20fd37-30c2-43d1-a4f3-4d25aaca146d', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('170', '2016-05-11 16:54:33', '12312312', '0', '0', '6ba212b7-440c-47a7-be20-68c662beb572', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('171', '2016-05-11 16:54:34', '12312312', '0', '0', 'cd920aa1-4d8d-4b43-9115-8dfb519cdbb8', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('172', '2016-05-11 16:54:34', '12312312', '0', '0', '0c26d6d3-793d-4856-a8e3-d81cc6bd4faa', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('173', '2016-05-11 16:54:35', '12312312', '0', '0', '17e437e8-522e-478c-8ebb-84afd3f108d0', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('174', '2016-05-11 16:54:35', '12312312', '0', '0', 'e7ca5df0-a254-48e6-8f29-5e27bce723c1', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('175', '2016-05-11 16:54:35', '12312312', '0', '0', '00baf403-64c8-4049-a9d5-83f62e19db1c', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('176', '2016-05-11 16:54:35', '12312312', '0', '0', '632fb2ec-c545-4b66-bf00-4bf93bbf2bc8', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('177', '2016-05-11 16:54:36', '12312312', '0', '0', '89d56e54-35da-4b96-b2fd-07886a678df4', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('178', '2016-05-11 16:54:36', '12312312', '0', '0', 'b25c23ae-1000-4432-9dad-7c969ebe783f', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('179', '2016-05-11 16:54:36', '12312312', '0', '0', 'eec98421-dc22-4495-9516-11bb3dd62c72', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('180', '2016-05-11 16:54:36', '12312312', '0', '0', '3fda009e-5905-4c1c-8bb9-6763dd3c05cd', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('181', '2016-05-11 16:54:37', '12312312', '0', '0', '3e141e4a-d1b0-42d2-bd5a-d482c1886536', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('182', '2016-05-11 16:54:37', '12312312', '0', '0', '65f4ff24-e84e-48f9-a6ca-ee3fa84485de', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('183', '2016-05-11 16:54:38', '12312312', '0', '0', 'eeadf16c-afe7-4685-b6b0-1ad4dcb46f08', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('184', '2016-05-11 16:54:38', '12312312', '0', '0', 'bef29eb2-60b6-4fe1-a226-fd5222e9def9', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('185', '2016-05-11 16:54:38', '12312312', '0', '0', 'ff761993-e335-4988-b929-fe256a0974ac', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('186', '2016-05-11 16:54:39', '12312312', '0', '0', '28333b4b-b926-41ce-8ea0-f2f00062f58a', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('187', '2016-05-11 16:54:39', '12312312', '0', '0', '25a7fbe5-7dad-45b6-a592-e9826d5a5283', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('188', '2016-05-11 16:54:39', '12312312', '0', '0', 'ad41fd34-c206-48a6-81ee-65aaae640cc9', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('189', '2016-05-11 16:54:40', '12312312', '0', '0', '6641d0b6-617f-4054-8bad-af1a290105dc', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('190', '2016-05-11 16:54:40', '12312312', '0', '0', 'c84e9c11-36e7-48af-a879-021c350879a2', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('191', '2016-05-11 16:54:40', '12312312', '0', '0', '897ec9a5-b9a9-457b-ae93-632277412522', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('192', '2016-05-11 16:54:41', '12312312', '0', '0', '3e48ef75-b975-46ad-9e48-e2dbaa410507', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('193', '2016-05-11 16:54:41', '12312312', '0', '0', '07ae90a0-1e7a-4891-b3b8-cde0e9b1b92c', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('194', '2016-05-11 16:54:41', '12312312', '0', '0', '8af12f67-c7e0-45c0-b962-d83c932cc838', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('195', '2016-05-11 16:54:42', '12312312', '0', '0', '80499a9b-5283-428c-a03e-2442b02f3973', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('196', '2016-05-11 16:54:42', '12312312', '0', '0', '978362ad-ea48-496c-a7ca-35816f3e8639', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('197', '2016-05-11 16:54:42', '12312312', '0', '0', '5237712f-bddf-4f48-8b85-9023c704c7fc', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('198', '2016-05-11 16:54:43', '12312312', '0', '0', 'f16824cf-df88-474d-9a4e-b2b98b25b6d5', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('199', '2016-05-11 16:54:43', '12312312', '0', '0', 'a27a953c-568a-4f63-a82c-d4a50e1db660', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('200', '2016-05-11 16:54:43', '12312312', '0', '0', 'e3e6c459-a4f1-4e51-ad70-2e5e72053dff', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('201', '2016-05-11 16:54:44', '12312312', '0', '0', 'bd7b168d-b1d3-41d8-a4b2-2352c72e88cb', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('202', '2016-05-11 16:54:44', '12312312', '0', '0', '7afc871c-bca2-425d-9711-c52ad145bf0e', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('203', '2016-05-11 16:54:44', '12312312', '0', '0', 'decd641a-3d21-4d7f-a8e2-babca416d8e5', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('204', '2016-05-11 16:54:45', '12312312', '0', '0', '003459eb-0a89-4aa4-9fb7-ed88fc2da0e3', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('205', '2016-05-11 16:54:45', '12312312', '0', '0', 'd5eb3f29-5029-4ea9-8349-b0a98fef44eb', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('206', '2016-05-11 16:54:45', '12312312', '0', '0', '933c0f91-d840-49be-9b1e-25baad027789', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('207', '2016-05-11 16:54:45', '12312312', '0', '0', '941e7687-64ff-40f2-8316-17d605211b73', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('208', '2016-05-11 16:54:46', '12312312', '0', '0', '72008d7d-e52f-46bd-b0da-dc94322ea23b', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('209', '2016-05-11 16:54:46', '12312312', '0', '0', 'c7fbd74b-e9b4-4e8f-bee1-1e5c5d7ddad0', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('210', '2016-05-11 16:54:47', '12312312', '0', '0', 'd9aa0198-23cd-41df-b66a-5b5dbc9203aa', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('211', '2016-05-11 16:54:47', '12312312', '0', '0', '447d47d7-ee75-4905-a09f-f82a45fb1f44', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('212', '2016-05-11 16:54:48', '12312312', '0', '0', '27fd4d76-2d1b-4557-8fa4-d08b738e782f', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('213', '2016-05-11 16:54:48', '12312312', '0', '0', '75ac7224-4205-4158-bf4c-a65378bf29c2', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('214', '2016-05-11 16:54:48', '12312312', '0', '0', '0586b907-287a-4643-a581-bd6ecfa89dc5', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('215', '2016-05-11 16:54:49', '12312312', '0', '0', 'cf991b73-ca3d-4f5f-8a50-62cfc613e8ad', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('216', '2016-05-11 16:54:49', '12312312', '0', '0', '8817971f-51d9-407c-9788-aef32ab82d67', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('217', '2016-05-11 16:54:49', '12312312', '0', '0', 'f29fa0f7-99f1-4630-bbc3-657b7eff6075', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('218', '2016-05-11 16:54:49', '12312312', '0', '0', '0b4b14c4-962f-4888-8d40-f149c0d959a3', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('219', '2016-05-11 16:54:50', '12312312', '0', '0', 'e7ffc4a6-4447-4762-8178-ab11247b3d46', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('220', '2016-05-11 16:54:50', '12312312', '0', '0', '5bcce830-eb75-4e00-b79a-1632dab83bc0', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('221', '2016-05-11 16:54:50', '12312312', '0', '0', 'c755507a-e08b-4796-beaf-66beb6543514', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('222', '2016-05-11 16:54:50', '12312312', '0', '0', 'd06c3344-269b-4b25-8a7e-26f7e4abc7c7', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('223', '2016-05-11 16:54:51', '12312312', '0', '0', '307c7d60-cd9d-4ad8-a2f9-bd8075d7c917', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('224', '2016-05-11 16:54:51', '12312312', '0', '0', '862c5d14-0f95-4e96-98a4-ca5a2d6a52ba', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('225', '2016-05-11 16:54:51', '12312312', '0', '0', 'fbb72c4b-2ff1-47cc-b44f-5efa424ad041', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('226', '2016-05-11 16:54:52', '12312312', '0', '0', 'cfe8db05-0834-48e5-a570-a25c547ee945', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('227', '2016-05-11 16:54:52', '12312312', '0', '0', '4877a668-d0d2-47ec-8736-fb7fd7bbfa22', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('228', '2016-05-11 16:54:52', '12312312', '0', '0', 'cec01656-5289-493f-886c-bb58d8c49c3a', '0', '0', '1', null);
INSERT INTO `syslabel` VALUES ('229', '2016-05-11 16:54:53', '12312312', '0', '0', '8ea1612c-3217-4901-a226-9363b926513f', '0', '0', '1', null);
