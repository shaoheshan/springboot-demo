package com.heshan.springboot.web.platform.common.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class MySQLConnections {
	public static void main(String args[]) {  
		tradingOrder();
		tradingOrderDetail();
		tradingDelivery();
		tradingDeliveryDetail();
	}
	
	//订单
	  public static void tradingOrder() {
		  Connection con = null;  
		  PreparedStatement pst = null;  
		  String url = "jdbc:mysql://localhost:3306/";  
		  String db = "hospital_db_v2?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";  
		  String driver = "com.mysql.cj.jdbc.Driver";  
		  String user = "root";  
		  String pass = "heshan123"; 
		  try {  
		  Class.forName(driver);  
		  con = DriverManager.getConnection(url + db, user, pass);  
		  con.setAutoCommit(false);
		  String sql = "INSERT INTO `hospital_db_v2`.`trading_order`(\r\n" + 
		  		"	`order_no`,\r\n" + 
		  		"	`ven_name`,\r\n" + 
		  		"	`ven_spell`,\r\n" + 
		  		"	`ven_code`,\r\n" + 
		  		"	`org_id`,\r\n" + 
		  		"	`hospital_name`,\r\n" + 
		  		"	`type`,\r\n" + 
		  		"	`order_status`,\r\n" + 
		  		"	`order_date`,\r\n" + 
		  		"	`department_name`,\r\n" + 
		  		"	`warehouse_code`,\r\n" + 
		  		"	`warehouse_name`,\r\n" + 
		  		"	`address`,\r\n" + 
		  		"	`total_money`,\r\n" + 
		  		"	`purchaser`,\r\n" + 
		  		"	`cancellation_reason`,\r\n" + 
		  		"	`notifier`,\r\n" + 
		  		"	`notice_time`,\r\n" + 
		  		"	`status`,\r\n" + 
		  		"	`create_time`,\r\n" + 
		  		"	`create_user_id`,\r\n" + 
		  		"	`create_user`,\r\n" + 
		  		"	`update_time`,\r\n" + 
		  		"	`update_user_id`,\r\n" + 
		  		"	`update_user`,\r\n" + 
		  		"	`remark`\r\n" + 
		  		")\r\n" + 
		  		"VALUES\r\n" + 
		  		"(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		  pst = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);  
		  pst.setString(2, "北京思派诺科技发展有限公司");  
		  pst.setString(3, "BJSPNKJFZYXGS");  
		  pst.setString(4, "1001");  
		  pst.setString(5, "1");  
		  pst.setString(6, "测试医院");  
		  pst.setString(7, "1");  
		  pst.setString(8, "1");  
		  pst.setString(9, "1499397135160");  
		  pst.setString(10, "信息科室");
		  pst.setString(11, "001");  
		  pst.setString(12, "库房2");  
		  pst.setString(13, "上地公园");  
		  pst.setString(14, "900.122100");  
		  pst.setString(15, "admin");
		  pst.setString(16, null);  
		  pst.setString(17, "admin");
		  pst.setString(18, "1502268081894");  
		  pst.setString(19, "1");  
		  pst.setString(20, "1502244786715");  
		  pst.setString(21, "1");  
		  pst.setString(22, "admin");  
		  pst.setString(23, "1504943903516");  
		  pst.setString(24, "1");  
		  pst.setString(25, "admin");  
		  pst.setString(26, "备注001");  
		  for (int i = 1; i < 30001; i++) {
			pst.setString(1,"D2017092600"+i);
			pst.addBatch();
			System.out.println(i);
		}
		  pst.executeBatch();  
		  con.commit();  // 提交  
		  pst.close();  
		  con.close();  
		  } catch (Exception e) {  
		  System.out.println("错误:"+e);  
		  }  
 } 
	  
	//订单明细
	  public static void tradingOrderDetail() {
		  Connection con = null;  
		  PreparedStatement pst = null;  
		  String url = "jdbc:mysql://localhost:3306/";  
		  String db = "hospital_db_v2?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";  
		  String driver = "com.mysql.cj.jdbc.Driver";  
		  String user = "root";  
		  String pass = "heshan123"; 
		  try {  
			  Class.forName(driver);  
			  con = DriverManager.getConnection(url + db, user, pass);  
			  con.setAutoCommit(false);
			  String sql = "INSERT INTO `hospital_db_v2`.`trading_order_detail` (`order_id`, `order_no`, `material_code`, `material_name`, `material_spec`, `material_unit`, `amount`, `received_amount`, `in_amount`, `price`, `total_money`, `req_dept_code`, `req_dept_name`, `estimation_date`, `remark`, `create_time`, `update_time`, `status`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			  pst = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);  
			  pst.setString(3, "1001");  
			  pst.setString(4, "1");  
			  pst.setString(5, "1盒");  
			  pst.setString(6, "1袋");  
			  pst.setString(7, "10.00");  
			  pst.setString(8, "0");  
			  pst.setString(9, "0");  
			  pst.setString(10, "0");
			  pst.setString(11, "0");  
			  pst.setString(12, null);  
			  pst.setString(13, null);  
			  pst.setString(14, "1499397135160");  
			  pst.setString(15, "lwjrewej备注");
			  pst.setString(16, "1502175711856");  
			  pst.setString(17, "1502175711856");  
			  pst.setString(18, "1");  
			  for (int i = 1; i < 30001; i++) {
				  	for (int j = 0; j < 5; j++) {
				  		pst.setString(1,i+"");
						pst.setString(2,"D2017092600"+i);
						pst.addBatch();
						System.out.println(i);
					}
				
			  }
			  pst.executeBatch();  
			  con.commit();  // 提交  
			  pst.close();  
			  con.close();  
			  } catch (Exception e) {  
			  System.out.println(e);  
			  }  
	  	}  
	  //送货单
	  public static void tradingDelivery() {
		  Connection con = null;  
		  PreparedStatement pst = null;  
		  String url = "jdbc:mysql://localhost:3306/";  
		  String db = "hospital_db_v2?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";  
		  String driver = "com.mysql.cj.jdbc.Driver";  
		  String user = "root";  
		  String pass = "heshan123"; 
		  try {  
			  Class.forName(driver);  
			  con = DriverManager.getConnection(url + db, user, pass);  
			  con.setAutoCommit(false);
			  String sql = "INSERT INTO `hospital_db_v2`.`trading_delivery` (\r\n" + 
			  		"	`org_id`,\r\n" + 
			  		"	`hospital_name`,\r\n" + 
			  		"	`ven_code`,\r\n" + 
			  		"	`ven_name`,\r\n" + 
			  		"	`ven_spell`,\r\n" + 
			  		"	`order_id`,\r\n" + 
			  		"	`order_no`,\r\n" + 
			  		"	`delivery_no`,\r\n" + 
			  		"	`total_money`,\r\n" + 
			  		"	`hospital_dept`,\r\n" + 
			  		"	`warehouse_code`,\r\n" + 
			  		"	`warehouse_name`,\r\n" + 
			  		"	`new_depot`,\r\n" + 
			  		"	`new_depot_code`,\r\n" + 
			  		"	`hospital_purchaser`,\r\n" + 
			  		"	`delivery_source`,\r\n" + 
			  		"	`order_time`,\r\n" + 
			  		"	`purchase_type`,\r\n" + 
			  		"	`arrival_address`,\r\n" + 
			  		"	`delivery_status`,\r\n" + 
			  		"	`remark`,\r\n" + 
			  		"	`delivery_create_time`,\r\n" + 
			  		"	`logistics_value`,\r\n" + 
			  		"	`logistics_company`,\r\n" + 
			  		"	`logistics_name`,\r\n" + 
			  		"	`logistics_no`,\r\n" + 
			  		"	`logistics_user`,\r\n" + 
			  		"	`logistics_link`,\r\n" + 
			  		"	`status`,\r\n" + 
			  		"	`create_time`,\r\n" + 
			  		"	`update_time`,\r\n" + 
			  		"	`delivery_time`,\r\n" + 
			  		"	`in_time`,\r\n" + 
			  		"	`stock_in_no`,\r\n" + 
			  		"	`make_date`,\r\n" + 
			  		"	`receipt_date`,\r\n" + 
			  		"	`receipt_operator_id`,\r\n" + 
			  		"	`receipt_operator_name`,\r\n" + 
			  		"	`receipt_status`,\r\n" + 
			  		"	`receipt_remark`\r\n" + 
			  		")\r\n" + 
			  		"VALUES\r\n" + 
			  		"	(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			  pst = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);  
			  pst.setString(1,"1");
			  pst.setString(2,"测试医院");
			  pst.setString(3, "1001");  
			  pst.setString(4, "1");  
			  pst.setString(5, "GYSMC");  
			  pst.setString(9, "2000.00");  
			  pst.setString(10, "信息科室");
			  pst.setString(11, null);  
			  pst.setString(12, null);  
			  pst.setString(13, null);  
			  pst.setString(14, null);  
			  pst.setString(15, "admin");
			  pst.setString(16, "0");  
			  pst.setString(17, "1502365918201");  
			  pst.setString(18, "1");
			  pst.setString(19, null);  
			  pst.setString(20, "2");  
			  pst.setString(21, null);  
			  pst.setString(22, "1502365918201");  
			  pst.setString(23, null);  
			  pst.setString(24, null);  
			  pst.setString(25, null);  
			  pst.setString(26, null);  
			  pst.setString(27, null);
			  pst.setString(28, null);  
			  pst.setString(29, "1");  
			  pst.setString(30, null); 
			  pst.setString(31, "1502365918201");  
			  pst.setString(32, "1504943867284");  
			  pst.setString(33, "1505724267008");  
			  pst.setString(34, null);  
			  pst.setString(35, null);  
			  pst.setString(36, null);  
			  pst.setString(37, null);  
			  pst.setString(38, null);  
			  pst.setString(39, null);  
			  pst.setString(40, null);  
			  int a= 1;
				  	for (int j = 1; j < 30001; j++) {
				  		pst.setString(6, j+"");  
						pst.setString(7, "D2017092600"+j);  
						for (int j2 = 1; j2 < 3; j2++) {
							pst.setString(8, "D2017092600-"+a);  
							pst.addBatch();
							a++;
						}
						System.out.println(j);
			}
			  pst.executeBatch();  
			  con.commit();  // 提交  
			  pst.close();  
			  con.close();  
			  } catch (Exception e) {  
			  System.out.println(e);  
			  }  
	  }
	  //送货单明细
	  public static void tradingDeliveryDetail() {
		  Connection con = null;  
		  PreparedStatement pst = null;  
		  String url = "jdbc:mysql://localhost:3306/";  
		  String db = "hospital_db_v2?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";  
		  String driver = "com.mysql.cj.jdbc.Driver";  
		  String user = "root";  
		  String pass = "heshan123"; 
		  try {  
			  Class.forName(driver);  
			  con = DriverManager.getConnection(url + db, user, pass);  
			  con.setAutoCommit(false);
			  String sql = "INSERT INTO `hospital_db_v2`.`trading_delivery_detail` (\r\n" + 
			  		"	`delivery_id`,\r\n" + 
			  		"	`delivery_no`,\r\n" + 
			  		"	`material_code`,\r\n" + 
			  		"	`material_name`,\r\n" + 
			  		"	`material_price`,\r\n" + 
			  		"	`delivery_amount`,\r\n" + 
			  		"	`actual_amount`,\r\n" + 
			  		"	`actual_total_money`,\r\n" + 
			  		"	`batch_number`,\r\n" + 
			  		"	`produced_date`,\r\n" + 
			  		"	`valid_date`,\r\n" + 
			  		"	`sterilization_date`,\r\n" + 
			  		"	`save_condition`,\r\n" + 
			  		"	`bar_code`,\r\n" + 
			  		"	`in_bar_code`,\r\n" + 
			  		"	`product_bar_code`,\r\n" + 
			  		"	`reg_num`,\r\n" + 
			  		"	`remark`,\r\n" + 
			  		"	`status`,\r\n" + 
			  		"	`create_time`,\r\n" + 
			  		"	`update_time`,\r\n" + 
			  		"	`req_dept_code`,\r\n" + 
			  		"	`req_dept_name`,\r\n" + 
			  		"	`in_detail_no`,\r\n" + 
			  		"	`in_amount`,\r\n" + 
			  		"	`in_money`,\r\n" + 
			  		"	`goods_received_amount`,\r\n" + 
			  		"	`supply_delivery_detail_id`\r\n" + 
			  		")\r\n" + 
			  		"VALUES\r\n" + 
			  		"	(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			  pst = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);  
			  pst.setString(3, "1001");  
			  pst.setString(4, "1");  
			  pst.setString(5, "200.000000");  
			  pst.setString(6, "10.00");  
			  pst.setString(7, "10.00");
			  pst.setString(8, "2000.00");  
			  pst.setString(9, "P1001");  
			  pst.setString(10, "1499397135160");  
			  pst.setString(11, "1499397135160");  
			  pst.setString(12, "1499397135160");
			  pst.setString(13, "储运条件");  
			  pst.setString(14, null);  
			  pst.setString(15, null);
			  pst.setString(16, null);  
			  pst.setString(17, "无注册证");  
			  pst.setString(18, null);  
			  pst.setString(19, null);  
			  pst.setString(20, "1502365970741");  
			  pst.setString(21, "1504943867284");  
			  pst.setString(22, null);  
			  pst.setString(23, null);  
			  pst.setString(24, null);
			  pst.setString(25, null);  
			  pst.setString(26, null);  
			  pst.setString(27, "10.00"); 
			  pst.setString(28, null);  
			  for (int j = 1; j < 60001; j++) {
				  for (int i = 0; i <10; i++) {
					  pst.setString(1, j+"");  
						pst.setString(2, "D2017092600"+j);  
						pst.addBatch();
						System.out.println(j);
				}
			}
			  pst.executeBatch();  
			  con.commit();  // 提交  
			  pst.close();  
			  con.close();  
			  } catch (Exception e) {  
			  System.out.println(e);  
			  }  
	  }
}
