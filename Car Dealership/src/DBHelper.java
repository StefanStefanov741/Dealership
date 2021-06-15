
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Properties;

import javax.swing.JComboBox;

public class DBHelper {
	public static Connection conn = null;
	public static CarsModel cars_model = null;
	public static SalesModel sales_model = null;
	public static ClientsModel clients_model = null;
	public static BrandsModel brands_model = null;
	public static CountriesModel countries_model = null;
	public static PreparedStatement state = null;
	public static ResultSet result = null;
	
	public static Connection getConnection() {
		
		try {
			
			Properties db_config = new Properties();
			InputStream is = new FileInputStream("driver/config.properties");
			db_config.load(is);
			Class.forName(db_config.getProperty("driver"));
			conn = DriverManager.getConnection(db_config.getProperty("url"), db_config.getProperty("username"), db_config.getProperty("password"));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return conn;
		
	}
	
	//method for filling brands in the combo for adding new cars
	static void fillBrandCombo(JComboBox<String>combo,boolean search) {
		conn = getConnection();
		String sql = "SELECT BRAND_NAME FROM CAR_BRANDS";
		try {
			state=conn.prepareStatement(sql);
			result = state.executeQuery();
			combo.removeAllItems();
			if(search) {
				combo.addItem("---");
			}
			while(result.next()) {
				String item = result.getObject(1).toString();
				combo.addItem(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//method for filling cars in the combo for adding new sales
	static void fillCarsCombo(JComboBox<String>combo) {
		conn = getConnection();
		String sql = "SELECT BRAND_ID,MODEL,YEAR FROM CARS";
		try {
			state=conn.prepareStatement(sql);
			result = state.executeQuery();
			combo.removeAllItems();
			while(result.next()) {
				ResultSet temp = result;
				String brand_name = GetBrandName(Integer.parseInt(result.getObject(1).toString()));
				result = temp;
				String item = brand_name+"_"+result.getObject(2).toString()+"_"+result.getObject(3).toString();
				combo.addItem(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//method for filling clients in the combo for adding new sales
	static void fillClientsCombo(JComboBox<String>combo,boolean search) {
		conn = getConnection();
		String sql = "select fname,lname from clients";
		try {
			state=conn.prepareStatement(sql);
			result = state.executeQuery();
			combo.removeAllItems();
			if(search==true) {
				combo.addItem("---");
			}
			while(result.next()) {
				String item = result.getObject(1).toString()+" "+result.getObject(2).toString();
				combo.addItem(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//method for filling brands in the combo for adding new cars
	static void fillCountryCombo(JComboBox<String>combo,boolean search) {
		conn = getConnection();
		String sql = "SELECT COUNTRY_NAME FROM COUNTRIES";
		try {
			state=conn.prepareStatement(sql);
			result = state.executeQuery();
			combo.removeAllItems();
			if(search) {
				combo.addItem("---");
			}
			while(result.next()) {
				String item = result.getObject(1).toString();
				combo.addItem(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static int GetBrandID(String brand) {
		int brand_id = -1;
		String sql = "select id from car_brands where brand_name = '"+brand+"'";
		try {
			if(conn.isClosed()) {
				conn = DBHelper.getConnection();
			}
			state = conn.prepareStatement(sql);
			result = state.executeQuery();
			result.next();
			brand_id = Integer.parseInt(result.getObject(1).toString());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally {
			try {
				state.close();
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return brand_id;
	}
	
	static String GetBrandName(int id) {
		String bName = "";
		String sql = "select brand_name from car_brands where id = "+id;
		try {
			if(conn.isClosed()) {
				conn = DBHelper.getConnection();
			}
			state = conn.prepareStatement(sql);
			result = state.executeQuery();
			result.next();
			bName = result.getObject(1).toString();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return bName;
	}
	
	static int GetCommentID(String desc) {
		conn = DBHelper.getConnection();
		String sql = "select id from car_comments where comment = '"+desc+"'";
		int found_id = 1;
		try {
			state = conn.prepareStatement(sql);
			result = state.executeQuery();
			result.next();
			found_id = Integer.parseInt(result.getObject(1).toString());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally {
			try {
				state.close();
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return found_id;
	}
	
	static int[] GetCommentIDbyPart(String part) {
		conn = DBHelper.getConnection();
		String sql = "select * from car_comments where comment LIKE '%"+part+"%'";
		int[] found_ids = new int[0];
		try {
			state = conn.prepareStatement(sql);
			result = state.executeQuery();
			int count = 0;
			while(result.next()) {
				count++;
				int id = Integer.parseInt(result.getObject(1).toString());
				int[]newFids = new int[count];
				for(int k = 0;k<found_ids.length;k++) {
					newFids[k]=found_ids[k];
				}
				newFids[count-1]=id;
				found_ids=newFids;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally {
			try {
				state.close();
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return found_ids;
	}
	
	static String GetComment(int id) {
		conn = DBHelper.getConnection();
		String sql = "select comment from car_comments where id = "+id;
		String found_comment="";
		try {
			state = conn.prepareStatement(sql);
			result = state.executeQuery();
			result.next();
			found_comment = result.getObject(1).toString();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally {
			try {
				state.close();
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return found_comment;
	}
	
	//add a new client
	static void addClient(String fname,String lname) {
		String sql = "insert into clients (fname,lname) values('"+fname+"','"+lname+"')";
		try {
			if(conn.isClosed()) {
				conn = getConnection();
			}
			state=conn.prepareStatement(sql);
			state.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				state.close();
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	//add a new client
	static void addBrand(String bname,String cname) {
		int c_id = GetCountryID(cname);
		String sql = "insert into car_brands (brand_name,country_id) values('"+bname+"','"+c_id+"')";
		try {
			if(conn.isClosed()) {
				conn = getConnection();
			}
			state=conn.prepareStatement(sql);
			state.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				state.close();
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	//add a new country
	static void addCountry(String name) {
		int c = GetCountryID(name);
		if(c<1) {
			String sql = "insert into countries (country_name) values('"+name+"')";
			try {
				if(conn.isClosed()) {
					conn = getConnection();
				}
				state=conn.prepareStatement(sql);
				state.execute();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					state.close();
					conn.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	
	static int GetCountryID(String name) {
		int id = -1;
		String sql = "select id from countries where country_name = '"+name+"'";
		try {
			if(conn.isClosed()) {
				conn = getConnection();
			}
			state = conn.prepareStatement(sql);
			result = state.executeQuery();
			result.next();
			id = Integer.parseInt(result.getObject(1).toString());
		} catch (SQLException e1) {
			
		}finally {
			try {
				state.close();
				conn.close();
			} catch (SQLException e1) {
				
			}
		}
		return id;
	}
	
	static int GetClientID(String fname,String lname) {
		int client_id = -1;
		conn = DBHelper.getConnection();
		String sql = "select id from clients where fname = '"+fname+"' and lname='"+lname+"'";
		try {
			state = conn.prepareStatement(sql);
			result = state.executeQuery();
			result.next();
			client_id = Integer.parseInt(result.getObject(1).toString());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally {
			try {
				state.close();
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return client_id;
	}
	
	static int GetCarID(int brand_id,String model,String year) {
		int car_id = -1;
		String sql = "select id from cars where brand_id ="+brand_id+" and model = '"+model+"' and year='"+year+"'";
		try {
			if(conn.isClosed()) {
				conn = DBHelper.getConnection();
			}
			state = conn.prepareStatement(sql);
			result = state.executeQuery();
			result.next();
			car_id = Integer.parseInt(result.getObject(1).toString());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally {
			try {
				state.close();
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return car_id;
	}
	
	static void DeleteCar(int id) {
		String sql = "DELETE FROM CARS WHERE ID = "+id;
		try {
			if(conn.isClosed()) {
				conn = DBHelper.getConnection();
			}
			state = conn.prepareStatement(sql);
			state.execute();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally {
			try {
				state.close();
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	static void DeleteClient(int id) {
		String sql = "DELETE FROM CLIENTS WHERE ID = "+id;
		try {
			if(conn.isClosed()) {
				conn = DBHelper.getConnection();
			}
			state = conn.prepareStatement(sql);
			state.execute();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally {
			try {
				state.close();
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	static void DeleteCountry(int id) {
		String sql = "DELETE FROM COUNTRIES WHERE ID = "+id;
		try {
			if(conn.isClosed()) {
				conn = DBHelper.getConnection();
			}
			state = conn.prepareStatement(sql);
			state.execute();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally {
			try {
				state.close();
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	static void DeleteBrand(int id) {
		String sql = "DELETE FROM CAR_BRANDS WHERE ID = "+id;
		try {
			if(conn.isClosed()) {
				conn = DBHelper.getConnection();
			}
			state = conn.prepareStatement(sql);
			state.execute();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally {
			try {
				state.close();
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	static void DeleteComment(int car_id) {
		int id = GetCommentId(car_id);
		String sql = "DELETE FROM CAR_COMMENTS WHERE ID = "+id;
		try {
			if(conn.isClosed()) {
				conn = DBHelper.getConnection();
			}
			state = conn.prepareStatement(sql);
			state.execute();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally {
			try {
				state.close();
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	static void DeleteSale(int id) {
		String sql = "DELETE FROM SALES WHERE ID = "+id;
		try {
			if(conn.isClosed()) {
				conn = DBHelper.getConnection();
			}
			state = conn.prepareStatement(sql);
			state.execute();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally {
			try {
				state.close();
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	static void addSale(int car_id,String sold_date,int client_id,float sold_for) {
		conn = getConnection();
		String sql = "insert into sales (car_id,sold_date,client_id,sold_for) values('"+car_id+"','"+sold_date+"','"+client_id+"','"+sold_for+"')";
		try {
			state=conn.prepareStatement(sql);
			state.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				state.close();
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public static CarsModel getAllcars() {
		try {
			if(conn.isClosed()) {
				conn = getConnection();
			}
			state = conn.prepareStatement("SELECT BRAND_ID,MODEL,YEAR,PRICE,COMMENT_ID FROM CARS");
			result = state.executeQuery();
			cars_model = new CarsModel(result);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return cars_model;
	}
	
	public static SalesModel getAllsales() {
		try {
			if(conn.isClosed()) {
				conn = getConnection();
			}
			state = conn.prepareStatement("SELECT CAR_ID,CLIENT_ID,SOLD_FOR,(CARS.PRICE-SOLD_FOR),SOLD_DATE FROM SALES INNER JOIN CARS ON SALES.CAR_ID=CARS.ID");
			result = state.executeQuery();
			sales_model = new SalesModel(result);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return sales_model;
	}
	
	public static ClientsModel getAllclients() {
		try {
			if(conn.isClosed()) {
				conn = getConnection();
			}
			state = conn.prepareStatement("SELECT FNAME, LNAME FROM CLIENTS");
			result = state.executeQuery();
			clients_model = new ClientsModel(result);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return clients_model;
	}
	
	public static BrandsModel getAllbrands() {
		try {
			if(conn.isClosed()) {
				conn = getConnection();
			}
			state = conn.prepareStatement("SELECT BRAND_NAME,COUNTRY_NAME FROM CAR_BRANDS INNER JOIN COUNTRIES ON COUNTRIES.ID=CAR_BRANDS.COUNTRY_ID");
			result = state.executeQuery();
			brands_model = new BrandsModel(result);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return brands_model;
	}
	
	public static CountriesModel getAllcountries() {
		try {
			if(conn.isClosed()) {
				conn = getConnection();
			}
			state = conn.prepareStatement("SELECT COUNTRY_NAME FROM COUNTRIES");
			result = state.executeQuery();
			countries_model = new CountriesModel(result);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return countries_model;
	}
	
	public static String GetCarDetails(int id) {
		String sql = "SELECT BRAND_ID,MODEL,YEAR FROM CARS WHERE id="+id;
		String item = "";
		try {
			if(conn.isClosed()) {
				conn = getConnection();
			}
			state=conn.prepareStatement(sql);
			result = state.executeQuery();
			result.next();
			ResultSet temp = result;
			String brand_name = GetBrandName(Integer.parseInt(result.getObject(1).toString()));
			result = temp;
			item = brand_name+"_"+result.getObject(2).toString()+"_"+result.getObject(3).toString();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return item;
	}
	
	public static String GetClientNames(int id) {
		String sql = "SELECT FNAME, LNAME FROM CLIENTS WHERE ID="+id;
		String item = "";
		try {
			if(conn.isClosed()) {
				conn = getConnection();
			}
			state=conn.prepareStatement(sql);
			result = state.executeQuery();
			result.next();
			item = result.getObject(1).toString()+" "+result.getObject(2).toString();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return item;
	}
	
	static int ItemCount(String sql) {
		int count = 0;
		try {
			if(conn.isClosed()) {
				conn = getConnection();
			}
			state=conn.prepareStatement(sql);
			result = state.executeQuery();
			while(result.next()) {
				count++;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	static int GetCommentId(int car_id) {
		int id = -1;
		String sql = "select comment_id from cars where id = "+car_id;
		try {
			if(conn.isClosed()) {
				conn = getConnection();
			}
			state=conn.prepareStatement(sql);
			result = state.executeQuery();
			result.next();
			id = Integer.parseInt(result.getObject(1).toString());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	static void UpdateCarComment(int car_id,String new_comment) {
		int id = GetCommentId(car_id);
		String sql = "update car_comments set comment = '"+new_comment+"' where id = "+id;
		try {
			if(conn.isClosed()) {
				conn = getConnection();
			}
			state=conn.prepareStatement(sql);
			state.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static void EditCar(int car_id,String model,String brand,float price,String year,String comment) {
		int brand_id = GetBrandID(brand);
		UpdateCarComment(car_id,comment);
		String sql = "update cars set model = '"+model+"', brand_id = '"+brand_id+"', year='"+year+"',price = '"+price+"' where id = "+car_id;
		try {
			if(conn.isClosed()) {
				conn = getConnection();
			}
			state=conn.prepareStatement(sql);
			state.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static void EditSale(int sale_id,String car_info,String client_info,float soldFor,String date) {
		String[]carInfo=car_info.split("_");
		String car_brand = carInfo[0];
		int car_brand_id = GetBrandID(car_brand);
		int car_id = GetCarID(car_brand_id,carInfo[1],carInfo[2]);
		String []clientNames = client_info.split(" ");
		int client_id = GetClientID(clientNames[0],clientNames[1]);
		String sql = "update sales set car_id = "+car_id+", client_id = "+client_id+", sold_for = '"+soldFor+"', sold_date = '"+date+"' where id = "+sale_id;
		try {
			if(conn.isClosed()) {
				conn = getConnection();
			}
			state=conn.prepareStatement(sql);
			state.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static void EditClient(int client_id,String fname,String lname) {
		String sql = "update clients set fname = '"+fname+"', lname = '"+lname+"' where id = "+client_id;
		try {
			if(conn.isClosed()) {
				conn = getConnection();
			}
			state=conn.prepareStatement(sql);
			state.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static void EditCountry(int country_id,String name) {
		String sql = "update countries set country_name = '"+name+"' where id = "+country_id;
		try {
			if(conn.isClosed()) {
				conn = getConnection();
			}
			state=conn.prepareStatement(sql);
			state.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static void EditBrand(int brand_id,String brand_name,int country_id) {
		String sql = "update car_brands set brand_name = '"+brand_name+"', country_id = "+country_id+" where id = "+brand_id;
		try {
			if(conn.isClosed()) {
				conn = getConnection();
			}
			state=conn.prepareStatement(sql);
			state.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	static int GetSaleId(int car_id,int client_id) {
		int s_id = -1;
		String sql = "select id from sales where car_id = "+car_id+" and client_id = "+client_id;
		try {
			if(conn.isClosed()) {
				conn = getConnection();
			}
			state=conn.prepareStatement(sql);
			result = state.executeQuery();
			result.next();
			s_id = Integer.parseInt(result.getObject(1).toString());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s_id;
	}
	
	//cars search methods
	public static CarsModel SearchCars(String sql) {
		if(sql.isBlank()||sql.isEmpty()) {
			sql = "SELECT BRAND_ID,MODEL,YEAR,PRICE,COMMENT_ID FROM CARS";
		}
		conn = getConnection();
		try {
			state = conn.prepareStatement(sql);
			result = state.executeQuery();
			cars_model = new CarsModel(result);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return cars_model;
	}
	
	//sales search methods
	public static SalesModel SearchSales(String sql) {
		if(sql.isBlank()||sql.isEmpty()) {
			sql = "SELECT CAR_ID,CLIENT_ID,SOLD_FOR,(CARS.PRICE-SOLD_FOR),SOLD_DATE FROM SALES INNER JOIN CARS ON SALES.CAR_ID=CARS.ID";
		}
		conn = getConnection();
		try {
			state = conn.prepareStatement(sql);
			result = state.executeQuery();
			sales_model = new SalesModel(result);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return sales_model;
	}
	
	//clients search method
	public static ClientsModel SearchClients(String sql) {
		if(sql.isBlank()||sql.isEmpty()) {
			sql = "SELECT FNAME, LNAME FROM CLIENTS";
		}
		conn = getConnection();
		try {
			state = conn.prepareStatement(sql);
			result = state.executeQuery();
			clients_model = new ClientsModel(result);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return clients_model;
	}
	
	//brands search method
	public static BrandsModel SearchBrands(String sql) {
		if(sql.isBlank()||sql.isEmpty()) {
			sql = "SELECT BRAND_NAME,COUNTRY_NAME FROM CAR_BRANDS INNER JOIN COUNTRIES ON COUNTRIES.ID=CAR_BRANDS.COUNTRY_ID";
		}
		conn = getConnection();
		try {
			state = conn.prepareStatement(sql);
			result = state.executeQuery();
			brands_model = new BrandsModel(result);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return brands_model;
	}
	
	//countries search method
	public static CountriesModel SearchCountries(String sql) {
		if(sql.isBlank()||sql.isEmpty()) {
			sql = "SELECT COUNTRY_NAME FROM COUNTRIES";
		}
		conn = getConnection();
		try {
			state = conn.prepareStatement(sql);
			result = state.executeQuery();
			countries_model = new CountriesModel(result);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return countries_model;
	}
	
	static String[] BrandOfOrigin(String country) {
		String[]brands=new String[0];
		
		try {
			if(conn.isClosed()) {
				conn=getConnection();
			}
			state = conn.prepareStatement("SELECT BRAND_NAME FROM CAR_BRANDS INNER JOIN COUNTRIES ON CAR_BRANDS.COUNTRY_ID = COUNTRIES.ID WHERE COUNTRY_NAME = '"+country+"' ");
			result = state.executeQuery();
			int count = 0;
			while(result.next()) {
				String b = result.getObject(1).toString();
				count++;
				String[]temp=new String[count];
				for(int i =0;i<brands.length;i++) {
					temp[i]=brands[i];
				}
				temp[count-1]=b;
				brands=temp;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return brands;
	}
	
	static int[] CarIds(String sql) {
		int[]ids=new int[0];
		
		try {
			if(conn.isClosed()) {
				conn=getConnection();
			}
			state = conn.prepareStatement(sql);
			result = state.executeQuery();
			int count = 0;
			while(result.next()) {
				int cid = Integer.parseInt(result.getObject(1).toString());
				count++;
				int[]temp=new int[count];
				for(int i =0;i<ids.length;i++) {
					temp[i]=ids[i];
				}
				temp[count-1]=cid;
				ids=temp;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ids;
	}
}//end class
