package com.ito.assignment3.exceltodatabase;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mysql.jdbc.Driver;

public class JDBCImpl {
	public void storingDataExcelToDB(Properties properties) throws ConnectionFailedException, ExcelFileNotFoundException, SQLException, DataBaseRelatedException {

		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = getConnection(properties);
			Sheet firstSheet = getSheet(properties);

			String sql = "INSERT INTO business_partner (business_partner_id, business_partner_name, business_partner_code, contact_name, address1, address2, city, province, country, postal_code, parent_company, toll_free_number, phone, phone_extension, fax, website_url, payment_condition, gl_number, driver_min_age, api_enabled, status, one_way_fee_paid_by, country_code) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			statement = connection.prepareStatement(sql);

			int[] rows = jdbcImplementation(statement, firstSheet);
			System.out.println(rows.length);
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
				if (statement != null) {
					statement.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	// Getting Connection.
	public static Connection getConnection(Properties properties) throws ConnectionFailedException{

		try {
			String URL = properties.getProperty("URL");
			String username = properties.getProperty("username");
			String password = properties.getProperty("password");

			Driver driver = new Driver();
			DriverManager.registerDriver(driver);
			return DriverManager.getConnection(URL, username, password);
		} catch(Exception e) {
			throw new ConnectionFailedException();
		}
	}

	// Getting sheet of excel from properties file.
	public static Sheet getSheet(Properties properties) throws ExcelFileNotFoundException {

		try {
			FileInputStream file = new FileInputStream(properties.getProperty("bp_data"));
			Workbook workbook = new XSSFWorkbook(file);

			Sheet firstSheet = workbook.getSheetAt(0);
			return firstSheet;
		} catch(Exception e) {
			throw new ExcelFileNotFoundException();
		}
	}

	// Logic for taking data from excel sheet and adding to database.
	public static int[] jdbcImplementation(PreparedStatement statement, Sheet firstSheet) throws DataBaseRelatedException {

		try {
		for (int i = 1; i <= firstSheet.getLastRowNum(); i++) {

			Row nextRow = firstSheet.getRow(i);

			for (int j = 0; j < nextRow.getLastCellNum(); j++) {

				Cell nextCell = nextRow.getCell(j);

				if (nextCell == null) {
					nextCell = nextRow.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
				}
				int columnIndex = nextCell.getColumnIndex();
				switch (columnIndex) {
				case 0:
					int business_partner_id = (int) nextCell.getNumericCellValue();
					statement.setInt(1, business_partner_id);
					break;
				case 1:
					String business_partner_name = nextCell.getStringCellValue();
					statement.setString(2, business_partner_name);
					break;
				case 2:
					String business_partner_code = nextCell.getStringCellValue();
					statement.setString(3, business_partner_code);
					break;
				case 3:
					String contact_name = nextCell.getStringCellValue();
					statement.setString(4, contact_name);
					break;
				case 4:
					String address1 = nextCell.getStringCellValue();
					statement.setString(5, address1);
					break;
				case 5:
					String address2 = nextCell.getStringCellValue();
					statement.setString(6, address2);
					break;
				case 6:
					String city = nextCell.getStringCellValue();
					statement.setString(7, city);
					break;
				case 7:
					String province = nextCell.getStringCellValue();
					statement.setString(8, province);
					break;
				case 8:
					String country = nextCell.getStringCellValue();
					statement.setString(9, country);
					break;
				case 9:
					String postal_code = nextCell.getStringCellValue();
					statement.setString(10, postal_code);
					break;
				case 10:
					String parent_company = nextCell.getStringCellValue();
					statement.setString(11, parent_company);
					break;
				case 11:
					String toll_free_number = nextCell.getStringCellValue();
					statement.setString(12, toll_free_number);
					break;
				case 12:
					nextCell.setCellType(nextCell.CELL_TYPE_STRING);
					String phone = nextCell.getStringCellValue();
					statement.setString(13, phone);
					break;
				case 13:
					nextCell.setCellType(nextCell.CELL_TYPE_STRING);
					String phone_extension = nextCell.getStringCellValue();
					statement.setString(14, phone_extension);
					break;
				case 14:
					String fax = nextCell.getStringCellValue();
					statement.setString(15, fax);
					break;
				case 15:
					String website_url = nextCell.getStringCellValue();
					statement.setString(16, website_url);
					break;
				case 16:
					int payment_condition = (int) nextCell.getNumericCellValue();
					statement.setInt(17, payment_condition);
					break;
				case 17:
					String gl_number = nextCell.getStringCellValue();
					statement.setString(18, gl_number);
					break;
				case 18:
					int driver_min_age = (int) nextCell.getNumericCellValue();
					statement.setInt(19, driver_min_age);
					break;
				case 19:
					int api_enabled = (int) nextCell.getNumericCellValue();
					statement.setInt(20, api_enabled);
					break;
				case 20:
					int status = (int) nextCell.getNumericCellValue();
					statement.setInt(21, status);
					break;
				case 21:
					String one_way_fee_paid_by = nextCell.getStringCellValue();
					statement.setString(22, one_way_fee_paid_by);
					break;
				case 22:
					String country_code = nextCell.getStringCellValue();
					statement.setString(23, country_code);
					break;
				}
			}
			// adding to batch after completion of each row.
			statement.addBatch();
		}
		// execution of whole rows i.e batch
		return statement.executeBatch();
	} catch(Exception e) {
		throw new DataBaseRelatedException();
	}
	} 
}
