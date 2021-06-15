import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Year;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;


public class MyFrame extends JFrame{
	//connection properties
	private Connection conn = null;
	private PreparedStatement statement = null;
	ResultSet result = null;
	
	//main panels
	JPanel topPanel = new JPanel();
	JPanel bottomPanel = new JPanel();
	JPanel carBtnsPanel = new JPanel();
	JPanel saleBtnsPanel = new JPanel();
	
	//tabs for main panels
	JTabbedPane addTab = new JTabbedPane();
	JTabbedPane viewTab = new JTabbedPane();
	
	//inner panels
	JPanel Add_Car = new JPanel();
	JPanel Add_Sale = new JPanel();
	JPanel Add_Client = new JPanel();
	JPanel Add_Brand = new JPanel();
	JPanel Add_Country = new JPanel();
	
	JPanel View_Cars = new JPanel();
	JPanel View_Sales = new JPanel();
	JPanel View_Clients = new JPanel();
	JPanel View_Brands = new JPanel();
	JPanel View_Countries = new JPanel();

	//components for adding cars
	JLabel BrandL = new JLabel("Brand");
	JLabel ModelL = new JLabel("Model");
	JLabel YearL = new JLabel("Year");
	JLabel PriceL = new JLabel("Price");
	JLabel DescriptionL = new JLabel("Description");
	JLabel FillerL = new JLabel("  ");
	
	JComboBox BrandCombo = new JComboBox();
	JTextField ModelTF = new JTextField();
	JTextField YearTF = new JTextField();
	JTextField PriceTF = new JTextField();
	JTextField DescriptionTF = new JTextField();
	
	JButton AddCarBtn = new JButton("Add");
	JButton ClearCarBtn = new JButton("Clear");
	
	//components for adding sales
	JLabel SoldCarL = new JLabel("Car");
	JLabel DateL = new JLabel("Sale date");
	JLabel ClientL = new JLabel("Purchasing client");
	JLabel SoldForL = new JLabel("SoldFor");
	
	JComboBox SoldCarCombo = new JComboBox();
	JPanel dateP = new JPanel();
	JPanel clientP = new JPanel();
	JButton addClient = new JButton("Add new client");
	JComboBox ClientCombo = new JComboBox();
	JTextField SoldForTF = new JTextField();
	
	JButton AddSaleBtn = new JButton("Add");
	JButton ClearSaleBtn = new JButton("Clear");
	
	//components for adding clients
	JLabel ClientFnameL = new JLabel("First name");
	JLabel ClientLnameL = new JLabel("Last name");
	JTextField ClientFnameTF = new JTextField();
	JTextField ClientLnameTF = new JTextField();
	JButton AddClientBtn = new JButton("Add");
	JButton ClearClientBtn = new JButton("Clear");
	
	//components for adding brands
	JLabel b_nameL = new JLabel("Brand name");
	JLabel ctryL = new JLabel("Country of origin");
	JComboBox cntry_comboBox = new JComboBox();
	JTextField b_nameTF = new JTextField();
	JButton AddBrandBtn = new JButton("Add");
	JButton ClearBrandBtn = new JButton("Clear");
	
	//components for adding countries
	JLabel country_nameL = new JLabel("Country name");
	JTextField country_nameTF = new JTextField();
	JButton AddCountryBtn = new JButton("Add");
	JButton ClearCountryBtn = new JButton("Clear");
	
	//components for dateP
	String[] days = {"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31",};
	String[] months = {"01","02","03","04","05","06","07","08","09","10","11","12",};
	String[] years = ReturnYears();
	JLabel dayL = new JLabel("Day");
	JLabel monthL = new JLabel("Month");
	JLabel DateYearL = new JLabel("Year");
	JComboBox dayCombo = new JComboBox(days);
	JComboBox monthCombo = new JComboBox(months);
	JComboBox DateYearCombo = new JComboBox(years);
	
	//adding tables for viewing
	JTable cars_table = new JTable();
	JScrollPane cars_scroller = new JScrollPane(cars_table);
	JTable sales_table = new JTable();
	JScrollPane sales_scroller = new JScrollPane(sales_table);
	JTable clients_table = new JTable();
	JScrollPane clients_scroller = new JScrollPane(clients_table);
	JTable brands_table = new JTable();
	JScrollPane brands_scroller = new JScrollPane(brands_table);
	JTable countries_table = new JTable();
	JScrollPane countries_scroller = new JScrollPane(countries_table);

	EditCarFrame ecf;
	EditSaleFrame esf;
	EditClientFrame eclf;
	EditBrandFrame ebf;
	EditCountryFrame ecof;
	
	//adjustment panels
	JPanel car_labelsP = new JPanel();
	JPanel car_inputsP = new JPanel();
	JPanel sale_labelsP = new JPanel();
	JPanel sale_inputsP = new JPanel();
	JPanel client_labelsP = new JPanel();
	JPanel client_inputsP = new JPanel();
	JPanel brand_labelsP = new JPanel();
	JPanel brand_inputsP = new JPanel();
	JPanel country_labelsP = new JPanel();
	JPanel country_inputsP = new JPanel();
	
	//seacrh elements - cars
	JPanel searchP = new JPanel();
	JComboBox s_brandCombo = new JComboBox();
	JComboBox s_countryoriginCombo = new JComboBox();
	JTextField s_modelTF = new JTextField();
	JTextField s_yearTF = new JTextField();
	JTextField s_priceTF = new JTextField();
	JTextField s_commentTF = new JTextField();
	JButton cars_searchB = new JButton("Search");
	JButton cars_resetB = new JButton("Reset");
	JLabel s_brandL = new JLabel("Brand");
	JLabel s_countryoriginL = new JLabel("Origin");
	JLabel s_modelL = new JLabel("Model");
	JLabel s_yearL = new JLabel("Year");
	JLabel s_priceL = new JLabel("Price");
	JLabel s_commentL = new JLabel("Comment");
	JLabel FillerL2 = new JLabel("  ");
	
	//seacrh elements - sales
	JPanel searchP2 = new JPanel();
	JComboBox s_clientCombo = new JComboBox();
	JComboBox s_brandCombo2 = new JComboBox();
	JTextField s_model2TF = new JTextField();
	JTextField s_year2TF = new JTextField();
	JTextField s_soldpriceTF = new JTextField();
	JTextField s_saledateTF = new JTextField();
	JButton sales_searchB = new JButton("Search");
	JButton sales_resetB = new JButton("Reset");
	JLabel s_clientL = new JLabel("Client");
	JLabel s_brand2L = new JLabel("Brand");
	JLabel s_model2L = new JLabel("Model");
	JLabel s_year2L = new JLabel("Car Year");
	JLabel s_soldpriceL = new JLabel("Sold for");
	JLabel s_saledateL = new JLabel("<html>"+ "Date of sale (yyyy-mm-dd)" +"</html>");
	JLabel FillerL3 = new JLabel("  ");
	JLabel FillerL4 = new JLabel("  ");
	
	//search elements - clients
	JPanel searchP3 = new JPanel();
	JButton clients_searchB = new JButton("Search");
	JButton clients_resetB = new JButton("Reset");
	JLabel s_clientFnameL = new JLabel("First name");
	JLabel s_clientLnameL = new JLabel("Last name");
	JTextField s_clientFnameTF = new JTextField();
	JTextField s_clientLnameTF = new JTextField();
	JLabel FillerL5 = new JLabel("         ");
	JLabel FillerL6 = new JLabel("         ");
	
	//search elements - brands
	JPanel searchP4 = new JPanel();
	JLabel s_BrandNameL = new JLabel("Brand name");
	JLabel s_CountryL = new JLabel("Country of origin");
	JTextField s_BrandNameTF = new JTextField();
	JComboBox s_CountryCB = new JComboBox();
	JButton brands_searchB = new JButton("Search");
	JButton brands_resetB = new JButton("Reset");
	JLabel FillerL7 = new JLabel("         ");
	JLabel FillerL8 = new JLabel("         ");
	
	//search elements - countries
	JPanel searchP5 = new JPanel();
	JLabel s_CountryNameL = new JLabel("Country name");
	JTextField s_CountryNameTF = new JTextField();
	JButton countries_searchB = new JButton("Search");
	JButton countries_resetB = new JButton("Reset");
	JLabel FillerL9 = new JLabel("         ");
	JLabel FillerL10 = new JLabel("         ");
	
	public MyFrame() {
		//configure the frame
		this.setSize(1000,800);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		topPanel.setPreferredSize(new Dimension(1000, 300));
		bottomPanel.setPreferredSize(new Dimension(1000, 400));
		carBtnsPanel.setLayout(new GridLayout(1,2,50,0));
		saleBtnsPanel.setLayout(new GridLayout(1,2,50,0));
		//adding components to panels
		//configure tab
		Add_Car.setLayout(new BorderLayout());
		Add_Sale.setLayout(new BorderLayout());
		Add_Client.setLayout(new BorderLayout());
		Add_Brand.setLayout(new BorderLayout());
		Add_Country.setLayout(new BorderLayout());
		//TOP PANEL
		topPanel.setLayout(new GridLayout(1,1));
		//car panel components
		car_labelsP.setLayout(new GridLayout(7,1,5,10));
		car_labelsP.setPreferredSize(new Dimension(180, 300));
		car_labelsP.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
		car_inputsP.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));
		car_inputsP.setLayout(new GridLayout(7,1,5,10));
		car_inputsP.setPreferredSize(new Dimension(500, 300));
		Add_Car.add(car_labelsP,BorderLayout.WEST);
		Add_Car.add(car_inputsP,BorderLayout.CENTER);
		car_labelsP.add(BrandL);
		car_inputsP.add(BrandCombo);
		DBHelper.fillBrandCombo(BrandCombo,false);
		car_labelsP.add(ModelL);
		car_inputsP.add(ModelTF);
		car_labelsP.add(YearL);
		car_inputsP.add(YearTF);
		car_labelsP.add(PriceL);
		car_inputsP.add(PriceTF);
		car_labelsP.add(DescriptionL);
		car_inputsP.add(DescriptionTF);
		car_labelsP.add(FillerL);
		carBtnsPanel.add(AddCarBtn);
		AddCarBtn.addActionListener(new AddCar());
		carBtnsPanel.add(ClearCarBtn);
		car_inputsP.add(carBtnsPanel);
		ClearCarBtn.addActionListener(new ClearCarForm());
		//sale panel components
		//configure date panel
		dateP.setLayout(new GridLayout(1,6));
		dateP.setBorder(BorderFactory.createEmptyBorder(0, -10, 0, 50));
		dayL.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		dateP.add(dayL);
		dateP.add(dayCombo);
		monthL.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		dateP.add(monthL);
		dateP.add(monthCombo);
		DateYearL.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		dateP.add(DateYearL);
		dateP.add(DateYearCombo);
		LocalDateTime now = LocalDateTime.now();
		int today = now.getDayOfMonth();
		int month = now.getMonthValue();
		dayCombo.setSelectedIndex(today-1);
		monthCombo.setSelectedIndex(month-1);
		//configure client panel for sales
		clientP.setLayout(new GridLayout(1,2,30,0));
		addClient.addActionListener(new OpenNewClientWindow());
		clientP.add(ClientCombo);
		clientP.add(addClient);
		DBHelper.fillClientsCombo(ClientCombo,false);
		//add components
		sale_labelsP.setLayout(new GridLayout(7,1,5,10));
		sale_labelsP.setPreferredSize(new Dimension(180, 300));
		sale_labelsP.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
		sale_inputsP.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));
		sale_inputsP.setLayout(new GridLayout(7,1,5,10));
		sale_inputsP.setPreferredSize(new Dimension(500, 300));
		Add_Sale.add(sale_labelsP,BorderLayout.WEST);
		Add_Sale.add(sale_inputsP,BorderLayout.CENTER);
		sale_labelsP.add(SoldCarL);
		sale_inputsP.add(SoldCarCombo);
		DBHelper.fillCarsCombo(SoldCarCombo);
		sale_labelsP.add(DateL);
		sale_inputsP.add(dateP);
		sale_labelsP.add(ClientL);
		sale_inputsP.add(clientP);
		sale_labelsP.add(SoldForL);
		sale_inputsP.add(SoldForTF);
		saleBtnsPanel.add(AddSaleBtn);
		saleBtnsPanel.add(ClearSaleBtn);
		sale_inputsP.add(saleBtnsPanel);
		ClearSaleBtn.addActionListener(new ClearSalesForm());
		AddSaleBtn.addActionListener(new AddNewSale());
		//client panel components (stand-alone)
		client_labelsP.setLayout(new GridLayout(3,1,5,10));
		client_labelsP.setPreferredSize(new Dimension(180, 300));
		client_labelsP.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 0));
		client_inputsP.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 30));
		client_inputsP.setLayout(new GridLayout(3,1,5,10));
		client_inputsP.setPreferredSize(new Dimension(500, 300));
		Add_Client.add(client_labelsP,BorderLayout.WEST);
		Add_Client.add(client_inputsP,BorderLayout.CENTER);
		client_labelsP.add(ClientFnameL);
		client_labelsP.add(ClientLnameL);
		client_labelsP.add(AddClientBtn);
		client_inputsP.add(ClientFnameTF);
		client_inputsP.add(ClientLnameTF);
		client_inputsP.add(ClearClientBtn);
		AddClientBtn.addActionListener(new AddClient());
		ClearClientBtn.addActionListener(new ClearClient());
		//brands panel components
		brand_labelsP.setLayout(new GridLayout(3,1,5,10));
		brand_labelsP.setPreferredSize(new Dimension(180, 300));
		brand_labelsP.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 0));
		brand_inputsP.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 30));
		brand_inputsP.setLayout(new GridLayout(3,1,5,10));
		brand_inputsP.setPreferredSize(new Dimension(500, 300));
		Add_Brand.add(brand_labelsP,BorderLayout.WEST);
		Add_Brand.add(brand_inputsP,BorderLayout.CENTER);
		brand_labelsP.add(b_nameL);
		brand_labelsP.add(ctryL);
		brand_labelsP.add(AddBrandBtn);
		brand_inputsP.add(b_nameTF);
		brand_inputsP.add(cntry_comboBox);
		brand_inputsP.add(ClearBrandBtn);
		AddBrandBtn.addActionListener(new AddBrand());
		ClearBrandBtn.addActionListener(new ClearBrand());
		//countries panel components
		country_labelsP.setLayout(new GridLayout(2,1,5,10));
		country_labelsP.setPreferredSize(new Dimension(180, 300));
		country_labelsP.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 0));
		country_inputsP.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 30));
		country_inputsP.setLayout(new GridLayout(2,1,5,10));
		country_inputsP.setPreferredSize(new Dimension(500, 300));
		Add_Country.add(country_labelsP,BorderLayout.WEST);
		Add_Country.add(country_inputsP,BorderLayout.CENTER);
		country_labelsP.add(country_nameL);
		country_labelsP.add(AddCountryBtn);
		country_inputsP.add(country_nameTF);
		country_inputsP.add(ClearCountryBtn);
		AddCountryBtn.addActionListener(new AddCountry());
		ClearCountryBtn.addActionListener(new ClearCountry());
		//adding panels to tab
		addTab.add(Add_Car,"Add new car");
		addTab.add(Add_Sale,"Add new sale");
		addTab.add(Add_Client,"Add new client");
		addTab.add(Add_Brand,"Add new brand");
		addTab.add(Add_Country,"Add new country");
		//add tab to panel
		topPanel.add(addTab);
		//BOTTOM PANEL
		bottomPanel.setLayout(new GridLayout(1,1));
		//configure tab
		View_Cars.setLayout(new BorderLayout());
		View_Sales.setLayout(new BorderLayout());
		View_Clients.setLayout(new BorderLayout());
		View_Brands.setLayout(new BorderLayout());
		View_Countries.setLayout(new BorderLayout());
		viewTab.add(View_Cars,"View cars");
		viewTab.add(View_Sales,"View sales");
		viewTab.add(View_Clients,"View clients");
		viewTab.add(View_Brands,"View brands");
		viewTab.add(View_Countries,"View countries");
		//adding tables
		View_Cars.add(searchP,BorderLayout.NORTH);
		View_Cars.add(cars_scroller,BorderLayout.CENTER);
		View_Sales.add(searchP2,BorderLayout.NORTH);
		View_Sales.add(sales_scroller,BorderLayout.CENTER);
		View_Clients.add(searchP3,BorderLayout.NORTH);
		View_Clients.add(clients_scroller,BorderLayout.CENTER);
		View_Brands.add(searchP4,BorderLayout.NORTH);
		View_Brands.add(brands_scroller,BorderLayout.CENTER);
		View_Countries.add(searchP5,BorderLayout.NORTH);
		View_Countries.add(countries_scroller,BorderLayout.CENTER);
		cars_table.addMouseListener(new CarsTableListener());
		sales_table.addMouseListener(new SalesTableListener());
		clients_table.addMouseListener(new ClientsTableListener());
		brands_table.addMouseListener(new BrandsTableListener());
		countries_table.addMouseListener(new CountriesTableListener());
		//add mouse listeners for countries brands and clients tables
		UpdateCarsTable();
		UpdateSalesTable();
		UpdateClientsTable();
		UpdateBrandsTable();
		UpdateCountriesTable();
		//add search
		//cars search
		DBHelper.fillBrandCombo(s_brandCombo,true);
		DBHelper.fillCountryCombo(s_countryoriginCombo,true);
		searchP.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		searchP.setLayout(new GridLayout(2,8,5,10));
		searchP.add(s_brandL);
		searchP.add(s_countryoriginL);
		searchP.add(s_modelL);
		searchP.add(s_yearL);
		searchP.add(s_priceL);
		searchP.add(s_commentL);
		searchP.add(FillerL);
		searchP.add(FillerL2);
		searchP.add(s_brandCombo);
		searchP.add(s_countryoriginCombo);
		searchP.add(s_modelTF);
		searchP.add(s_yearTF);
		searchP.add(s_priceTF);
		searchP.add(s_commentTF);
		searchP.add(cars_searchB);
		searchP.add(cars_resetB);
		cars_searchB.addActionListener(new SearchCars());
		cars_resetB.addActionListener(new ResetCarsS());
		//sales search
		DBHelper.fillBrandCombo(s_brandCombo2,true);
		DBHelper.fillClientsCombo(s_clientCombo,true);
		searchP2.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		searchP2.setLayout(new GridLayout(2,8,5,10));
		searchP2.add(s_clientL);
		searchP2.add(s_brand2L);
		searchP2.add(s_model2L);
		searchP2.add(s_year2L);
		searchP2.add(s_soldpriceL);
		searchP2.add(s_saledateL);
		searchP2.add(FillerL3);
		searchP2.add(FillerL4);
		searchP2.add(s_clientCombo);
		searchP2.add(s_brandCombo2);
		searchP2.add(s_model2TF);
		searchP2.add(s_year2TF);
		searchP2.add(s_soldpriceTF);
		searchP2.add(s_saledateTF);
		searchP2.add(sales_searchB);
		searchP2.add(sales_resetB);
		sales_searchB.addActionListener(new SearchSales());
		sales_resetB.addActionListener(new ResetSalesS());
		//clients search
		searchP3.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		searchP3.setLayout(new GridLayout(2,4,5,10));
		searchP3.add(s_clientFnameL);
		searchP3.add(s_clientLnameL);
		searchP3.add(FillerL5);
		searchP3.add(FillerL6);
		searchP3.add(s_clientFnameTF);
		searchP3.add(s_clientLnameTF);
		searchP3.add(clients_searchB);
		searchP3.add(clients_resetB);
		clients_searchB.addActionListener(new SearchClients());
		clients_resetB.addActionListener(new ResetClientsS());
		//brands search
		DBHelper.fillCountryCombo(s_CountryCB,true);
		searchP4.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		searchP4.setLayout(new GridLayout(2,4,5,10));
		searchP4.add(s_BrandNameL);
		searchP4.add(s_CountryL);
		searchP4.add(FillerL7);
		searchP4.add(FillerL8);
		searchP4.add(s_BrandNameTF);
		searchP4.add(s_CountryCB);
		searchP4.add(brands_searchB);
		searchP4.add(brands_resetB);
		brands_searchB.addActionListener(new SearchBrands());
		brands_resetB.addActionListener(new ResetBrandsS());
		//countries search
		searchP5.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		searchP5.setLayout(new GridLayout(2,4,5,10));
		searchP5.add(s_CountryNameL);
		searchP5.add(FillerL9);
		searchP5.add(FillerL10);
		searchP5.add(s_CountryNameTF);
		searchP5.add(countries_searchB);
		searchP5.add(countries_resetB);
		countries_searchB.addActionListener(new SearchCountries());
		countries_resetB.addActionListener(new ResetCountriesS());
		
		//add tab to panel
		bottomPanel.add(viewTab);
		
		//adding the panels
		this.add(topPanel,BorderLayout.NORTH);
		this.add(bottomPanel,BorderLayout.CENTER);
		//display components
		this.setVisible(true);
	}
	
	class CarsTableListener implements MouseListener{

		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			int row = cars_table.getSelectedRow();
			if (e.getClickCount() == 2) {
				String brand_s = cars_table.getValueAt(row, 0).toString();
				int b_id = DBHelper.GetBrandID(brand_s);
				String model_s = cars_table.getValueAt(row, 1).toString();
				String year_s = cars_table.getValueAt(row, 2).toString();
				int c_id = DBHelper.GetCarID(b_id,model_s,year_s);
				if(ecf!=null) {
					ecf.dispose();
				}
				ecf = new EditCarFrame(cars_table.getLocationOnScreen().x+50,cars_table.getLocationOnScreen().y-150,cars_table,sales_table,c_id,brand_s,model_s,year_s,cars_table.getValueAt(row, 3).toString(),cars_table.getValueAt(row, 4).toString());
	        }
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class SalesTableListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			int row = sales_table.getSelectedRow();

			if(e.getClickCount() == 2) {
				String car_info = sales_table.getValueAt(row, 0).toString();
				String client_info = sales_table.getValueAt(row, 1).toString();
				String[] carItems = car_info.split("_");
				String[] clientItems = client_info.split(" ");
				int brand_id = DBHelper.GetBrandID(carItems[0]);
				int car_id = DBHelper.GetCarID(brand_id,carItems[1],carItems[2]);
				int client_id = DBHelper.GetClientID(clientItems[0], clientItems[1]);
				int s_id = DBHelper.GetSaleId(car_id,client_id);
				if(s_id>0) {
					String [] carComboArray = new String[SoldCarCombo.getItemCount()];
					for(int i =0;i<SoldCarCombo.getItemCount();i++) {
						carComboArray[i]=SoldCarCombo.getItemAt(i).toString();
					}
					String [] clientComboArray = new String[ClientCombo.getItemCount()];
					for(int i =0;i<ClientCombo.getItemCount();i++) {
						clientComboArray[i]=ClientCombo.getItemAt(i).toString();
					}
					if(esf!=null) {
						esf.dispose();
					}
					esf = new EditSaleFrame(sales_table.getLocationOnScreen().x+50,sales_table.getLocationOnScreen().y-150,cars_table,sales_table,s_id,carComboArray,car_info,clientComboArray,client_info,sales_table.getValueAt(row, 2).toString(),sales_table.getValueAt(row, 4).toString(),days,months,years);
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class ClientsTableListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			int row = clients_table.getSelectedRow();

			if(e.getClickCount() == 2) {
				String fname = clients_table.getValueAt(row, 0).toString();
				String lname = clients_table.getValueAt(row, 1).toString();
				int cl_id = DBHelper.GetClientID(fname,lname);
				if(cl_id>0) {
					if(eclf!=null) {
						eclf.dispose();
					}
					eclf = new EditClientFrame(clients_table.getLocationOnScreen().x+50,clients_table.getLocationOnScreen().y-150,clients_table,sales_table,cl_id,s_clientCombo,ClientCombo,fname,lname);
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class BrandsTableListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			int row = brands_table.getSelectedRow();

			if(e.getClickCount() == 2) {
				String brand = brands_table.getValueAt(row, 0).toString();
				String cntry = brands_table.getValueAt(row, 1).toString();
				int b_id = DBHelper.GetBrandID(brand);
				if(b_id>0) {
					if(ebf!=null) {
						ebf.dispose();
					}
					ebf = new EditBrandFrame(brands_table.getLocationOnScreen().x+50,brands_table.getLocationOnScreen().y-100,brands_table,sales_table,cars_table,b_id,s_brandCombo2,SoldCarCombo,s_brandCombo,BrandCombo,brand,cntry);
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class CountriesTableListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			int row = countries_table.getSelectedRow();

			if(e.getClickCount() == 2) {
				String cntry = countries_table.getValueAt(row, 0).toString();
				int c_id = DBHelper.GetCountryID(cntry);
				if(c_id>0) {
					if(ecof!=null) {
						ecof.dispose();
					}
					ecof = new EditCountryFrame(countries_table.getLocationOnScreen().x+50,countries_table.getLocationOnScreen().y-150,c_id,countries_table,brands_table,s_countryoriginCombo,cntry_comboBox,s_CountryCB,cntry);
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	class ClearCarForm implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			ClearCarFormVoid();
		}
	}
	
	void ClearCarFormVoid() {
		BrandCombo.setSelectedIndex(0);
		ModelTF.setText("");
		YearTF.setText("");
		DescriptionTF.setText("");
		PriceTF.setText("");
	}
	
	void ClearSalesFormVoid() {
		LocalDateTime now = LocalDateTime.now();
		int today = now.getDayOfMonth();
		int month = now.getMonthValue();
		today = today-1;
		month = month-1;
		dayCombo.setSelectedIndex(today);
		monthCombo.setSelectedIndex(month);
		SoldCarCombo.setSelectedIndex(0);
		ClientCombo.setSelectedIndex(0);
		dayCombo.setSelectedIndex(today);
		monthCombo.setSelectedIndex(month);
		DateYearCombo.setSelectedIndex(0);
		SoldForTF.setText("");
	}
	
	class ClearSalesForm implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			ClearSalesFormVoid();
		}
	}
	
	class AddNewSale implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if(Float.parseFloat(SoldForTF.getText())>0) {
				//get car id
				String[] carItems = SoldCarCombo.getSelectedItem().toString().split("_");
				String cBrand=carItems[0];
				String cModel=carItems[1];
				String cYear=carItems[2];
				int brandID=DBHelper.GetBrandID(cBrand);
				int carID = DBHelper.GetCarID(brandID,cModel,cYear);
				//get client id
				String[] clientItems = ClientCombo.getSelectedItem().toString().split(" ");
				String fname = clientItems[0];
				String lname = clientItems[1];
				int client_id = DBHelper.GetClientID(fname, lname);
				//get date
				String y = DateYearCombo.getSelectedItem().toString();
				String yLastDigits = y.substring(1);
				String date = DateYearCombo.getSelectedItem().toString()+"-"+monthCombo.getSelectedItem().toString()+"-"+dayCombo.getSelectedItem().toString();
				//get sold_for
				float sold_for = Float.parseFloat(SoldForTF.getText());
				DBHelper.addSale(carID,date,client_id,sold_for);
				ClearSalesFormVoid();
				UpdateSalesTable();
			}
		}
	}
	
	Boolean checkYear(String y) {
		if(y.length()>4) {
			return false;
		}
		Boolean b = true;
		char[]allowedSymbols = {'0','1','2','3','4','5','6','7','8','9'};
		for(int i = 0 ;i<y.length();i++) {
			Boolean ok = false;
			for(int k = 0 ;k<allowedSymbols.length;k++) {
				if(y.charAt(i)==allowedSymbols[k]) {
					ok=true;
					break;
				}
			}
			if(!ok) {
				b=false;
				break;
			}
		}
		return b;
	}
	
	class AddCar implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//what to do when the action is called
			String brand = BrandCombo.getSelectedItem().toString();
			int brand_id = DBHelper.GetBrandID(brand);
			String model = ModelTF.getText();
			String year = YearTF.getText();
			float price = Float.parseFloat(PriceTF.getText());
			String description = DescriptionTF.getText();
			Boolean proceed = false;
			if(checkYear(year)) {
				AddCarComment(description);
				proceed = true;
			}
			if(proceed) {
				int comment_id = DBHelper.GetCommentID(description);
				conn = DBHelper.getConnection();
				try {
					statement = conn.prepareStatement("insert into CARS values(null,?,?,?,?,?);");
					statement.setString(1,model);
					statement.setInt(2,brand_id);
					statement.setFloat(3,price);
					statement.setInt(4,comment_id);
					statement.setString(5,year);
					
					statement.execute();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}finally {
					try {
						statement.close();
						conn.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			UpdateCarsTable();
			ClearCarFormVoid();
			DBHelper.fillCarsCombo(SoldCarCombo);
		}
	}
	
	void AddCarComment(String desc) {
		conn = DBHelper.getConnection();
		try {
			statement = conn.prepareStatement("insert into CAR_COMMENTS values(null,?);");
			statement.setString(1,desc);
			
			statement.execute();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally {
			try {
				statement.close();
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	String[] ReturnYears() {
		Year y = Year.now();
		int count = 0;
		for(int i = 2000; i <=Integer.parseInt(y.toString());i++) {
			count++;
		}
		String[] yrs=new String[count];
		int yToAdd = 2021;
		for(int i = 0; i <count;i++) {
			yrs[i]=""+yToAdd;
			yToAdd--;
		}
		return yrs;
	}
	
	class OpenNewClientWindow implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			ClientPopupFrame cpf = new ClientPopupFrame(addClient.getLocationOnScreen().x,addClient.getLocationOnScreen().y,ClientCombo,s_clientCombo,clients_table);
		}
		
	}
	
	void UpdateCarsTable() {
		cars_table.setModel(DBHelper.getAllcars());
		cars_table.getColumnModel().getColumn(0).setHeaderValue("Brand");
		cars_table.getColumnModel().getColumn(1).setHeaderValue("Model");
		cars_table.getColumnModel().getColumn(2).setHeaderValue("Year");
		cars_table.getColumnModel().getColumn(3).setHeaderValue("Price");
		cars_table.getColumnModel().getColumn(4).setHeaderValue("Comment");
	}
	void UpdateSalesTable() {
		sales_table.setModel(DBHelper.getAllsales());
		sales_table.getColumnModel().getColumn(0).setHeaderValue("Car");
		sales_table.getColumnModel().getColumn(1).setHeaderValue("Client");
		sales_table.getColumnModel().getColumn(2).setHeaderValue("Sold for");
		sales_table.getColumnModel().getColumn(3).setHeaderValue("Discount from original price");
		sales_table.getColumnModel().getColumn(4).setHeaderValue("Sale date");
	}
	
	void UpdateClientsTable() {
		clients_table.setModel(DBHelper.getAllclients());
		clients_table.getColumnModel().getColumn(0).setHeaderValue("First name");
		clients_table.getColumnModel().getColumn(1).setHeaderValue("Last name");
		DBHelper.fillClientsCombo(s_clientCombo, true);
	}
	
	void UpdateBrandsTable() {
		brands_table.setModel(DBHelper.getAllbrands());
		brands_table.getColumnModel().getColumn(0).setHeaderValue("Brand name");
		brands_table.getColumnModel().getColumn(1).setHeaderValue("Country of origin");
		DBHelper.fillBrandCombo(BrandCombo, false);
		DBHelper.fillBrandCombo(s_brandCombo, true);
		DBHelper.fillBrandCombo(s_brandCombo2, true);
	}
	
	void UpdateCountriesTable() {
		countries_table.setModel(DBHelper.getAllcountries());
		countries_table.getColumnModel().getColumn(0).setHeaderValue("Country name");
		DBHelper.fillCountryCombo(s_countryoriginCombo,true);
		DBHelper.fillCountryCombo(cntry_comboBox,false);
	}
	
	void ResetCarSearch() {
		s_brandCombo.setSelectedIndex(0);
		s_countryoriginCombo.setSelectedIndex(0);
		s_modelTF.setText("");
		s_yearTF.setText("");
		s_priceTF.setText("");
		s_commentTF.setText("");
		UpdateCarsTable();
	}
	void ResetSaleSearch() {
		s_brandCombo2.setSelectedIndex(0);
		s_clientCombo.setSelectedIndex(0);
		s_model2TF.setText("");
		s_year2TF.setText("");
		s_soldpriceTF.setText("");
		s_saledateTF.setText("");
		UpdateSalesTable();
	}
	class ResetCarsS implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			ResetCarSearch();
		}
		
	}
	class ResetSalesS implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			ResetSaleSearch();
		}
		
	}
	
	class SearchCars implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			//perform search
			String sql = "";
			String part1 = "SELECT BRAND_ID,MODEL,YEAR,PRICE,COMMENT_ID ";
			String part2 = "FROM CARS WHERE ";
			String brnd = s_brandCombo.getSelectedItem().toString();
			String cntry = s_countryoriginCombo.getSelectedItem().toString();
			String mdl = s_modelTF.getText();
			String yr = s_yearTF.getText();
			String prc = s_priceTF.getText();
			String cmnt = s_commentTF.getText();
			String[]allowedBrands = null;
			if(cntry!="---") {
				allowedBrands=DBHelper.BrandOfOrigin(cntry);
			}
			if(brnd!="---") {
				boolean allowed = false;
				int b_id = 0;
				if(allowedBrands!=null) {
					for(int j = 0;j<allowedBrands.length;j++) {
						if(brnd.equalsIgnoreCase(allowedBrands[j])) {
							allowed=true;
						}
					}
				}else {
					allowed=true;
				}
				if(allowed) {
					b_id = DBHelper.GetBrandID(brnd);
					part2+="BRAND_ID = "+b_id;
				}else {
					part2+="BRAND_ID = -1";
				}
			}else {
				if(allowedBrands!=null) {
					part2+="(";
					int[]allowedBids = new int[allowedBrands.length];
					for(int f = 0;f<allowedBids.length;f++) {
						if(f>0) {
							part2+=" OR ";
						}
						allowedBids[f]=DBHelper.GetBrandID(allowedBrands[f]);
						part2+="BRAND_ID = "+allowedBids[f];
					}
					if(allowedBrands.length==0) {
						part2+="BRAND_ID = -1";
					}
					part2+=")";
				}
			}
			if(!mdl.isBlank()&&!mdl.isEmpty()) {
				if(part2=="FROM CARS WHERE "){
					part2+="MODEL ='"+mdl+"'";
				}else {
					part2+=" AND MODEL ='"+mdl+"'";
				}
			}
			if(!yr.isBlank()&&!yr.isEmpty()) {
				if(part2=="FROM CARS WHERE "){
					part2+="YEAR ='"+yr+"'";
				}else {
					part2+=" AND YEAR ='"+yr+"'";
				}
			}
			if(!prc.isBlank()&&!prc.isEmpty()) {
				if(part2=="FROM CARS WHERE "){
					part2+="PRICE ='"+prc+"'";
				}else {
					part2+=" AND PRICE ='"+prc+"'";
				}
			}
			if(!cmnt.isBlank()&&!cmnt.isEmpty()) {
				int[] cid;
				cid = DBHelper.GetCommentIDbyPart(cmnt);
				if(cid.length==0) {
					if(part2=="FROM CARS WHERE "){
						part2+="COMMENT_ID = -1";
					}
				}
				if(cid.length>0) {
					if(part2=="FROM CARS WHERE "){
						part2+="(COMMENT_ID ='"+cid[0]+"')";
					}else {
						part2+=" AND (COMMENT_ID ='"+cid[0]+"')";
					}
				}
				if(cid.length>1) {
					for(int i =1;i<cid.length;i++) {
						part2 = part2.substring(0, part2.length() - 1);
						part2+=" OR COMMENT_ID ='"+cid[i]+"')";
					}
				}
			}
			sql = part1+part2;
			if(sql.equalsIgnoreCase("SELECT BRAND_ID,MODEL,YEAR,PRICE,COMMENT_ID FROM CARS WHERE ")) {
				sql="";
			}
			//update table
			cars_table.setModel(DBHelper.SearchCars(sql));
			cars_table.getColumnModel().getColumn(0).setHeaderValue("Brand");
			cars_table.getColumnModel().getColumn(1).setHeaderValue("Model");
			cars_table.getColumnModel().getColumn(2).setHeaderValue("Year");
			cars_table.getColumnModel().getColumn(3).setHeaderValue("Price");
			cars_table.getColumnModel().getColumn(4).setHeaderValue("Comment");
		}
		
	}
	
	class SearchSales implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String sql = "";
			String part1 = "SELECT CAR_ID,CLIENT_ID,SOLD_FOR,(CARS.PRICE-SOLD_FOR),SOLD_DATE ";
			String part2 = "FROM SALES INNER JOIN CARS ON SALES.CAR_ID=CARS.ID WHERE ";
			
			String brnd = s_brandCombo2.getSelectedItem().toString();
			String clnt = s_clientCombo.getSelectedItem().toString();
			String mdl = s_model2TF.getText();
			String yr = s_year2TF.getText();
			String sldprc = s_soldpriceTF.getText();
			String sldt = s_saledateTF.getText();
			
			String carSql = "SELECT ID FROM CARS WHERE ";
			if(brnd!="---") {
				int b_id = DBHelper.GetBrandID(brnd);
				carSql+="BRAND_ID = "+b_id;
			}
			if(!mdl.isBlank()&&!mdl.isEmpty()) {
				if(carSql == "SELECT ID FROM CARS WHERE ") {
					carSql+="MODEL = '"+mdl+"'";
				}else {
					carSql+=" AND MODEL = '"+mdl+"'";
				}
			}
			if(!yr.isBlank()&&!yr.isEmpty()) {
				if(carSql == "SELECT ID FROM CARS WHERE ") {
					carSql+="YEAR = '"+yr+"'";
				}else {
					carSql+=" AND YEAR = '"+yr+"'";
				}
			}
			if(carSql == "SELECT ID FROM CARS WHERE ") {
				carSql = "SELECT ID FROM CARS";
			}
			int[] carIDs=DBHelper.CarIds(carSql);
			if(carIDs.length>0) {
				part2+="(";
				for(int h=0;h<carIDs.length;h++) {
					if(h==0) {
						part2+="CAR_ID = "+carIDs[h];
					}else {
						part2+=" OR CAR_ID = "+carIDs[h];
					}
				}
				part2+=")";
			}else {
				part2+="CAR_ID = -1";
			}
			if(clnt!="---") {
				String[] cArr = clnt.split(" ");
				int clnt_id = DBHelper.GetClientID(cArr[0], cArr[1]);
				if(part2 == "FROM SALES INNER JOIN CARS ON SALES.CAR_ID=CARS.ID WHERE ") {
					part2+="CLIENT_ID = '"+clnt_id+"'";
				}else {
					part2+=" AND CLIENT_ID = '"+clnt_id+"'";
				}
			}
			if(!sldprc.isBlank()&&!sldprc.isEmpty()) {
				if(part2 == "FROM SALES INNER JOIN CARS ON SALES.CAR_ID=CARS.ID WHERE ") {
					part2+="SOLD_FOR = '"+sldprc+"'";
				}else {
					part2+=" AND SOLD_FOR = '"+sldprc+"'";
				}
			}
			if(!sldt.isBlank()&&!sldt.isEmpty()) {
				boolean validD = false;
				try {
		            DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
		            df.setLenient(false);
		            df.parse(sldt);
		            validD=true;
					
		        } catch (java.text.ParseException e1) {
					//not a valid date
		        	validD = false;
				}
				//add to sql statement
				if(validD) {
					if(part2 == "FROM SALES INNER JOIN CARS ON SALES.CAR_ID=CARS.ID WHERE ") {
						part2+="SOLD_DATE = '"+sldt+"'";
					}else {
						part2+=" AND SOLD_DATE = '"+sldt+"'";
					}
				}else {
		        	if(part2 == "FROM SALES INNER JOIN CARS ON SALES.CAR_ID=CARS.ID WHERE ") {
						part2+="SOLD_DATE = '1000-01-01'";
					}else {
						part2+=" AND SOLD_DATE = '1000-01-1'";
					}
				}
			}
			
			sql = part1+part2;
			if(sql.equalsIgnoreCase("SELECT CAR_ID,CLIENT_ID,SOLD_FOR,(CARS.PRICE-SOLD_FOR),SOLD_DATE FROM SALES INNER JOIN CARS ON SALES.CAR_ID=CARS.ID WHERE ")) {
				sql="";
			}
			//update table
			sales_table.setModel(DBHelper.SearchSales(sql));
			sales_table.getColumnModel().getColumn(0).setHeaderValue("Car");
			sales_table.getColumnModel().getColumn(1).setHeaderValue("Client");
			sales_table.getColumnModel().getColumn(2).setHeaderValue("Sold for");
			sales_table.getColumnModel().getColumn(3).setHeaderValue("Discount from original price");
			sales_table.getColumnModel().getColumn(4).setHeaderValue("Sale date");
		}
		
	}
	
	void ClearClientForm() {
		ClientFnameTF.setText("");
		ClientLnameTF.setText("");
	}
	
	class AddClient implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(!ClientFnameTF.getText().isEmpty() && !ClientFnameTF.getText().isBlank()&&!ClientLnameTF.getText().isEmpty() && !ClientLnameTF.getText().isBlank()) {
				DBHelper.addClient(ClientFnameTF.getText(), ClientLnameTF.getText());
				DBHelper.fillClientsCombo(ClientCombo,false);
				ClearClientForm();
				UpdateClientsTable();
			}
		}
		
	}
	
	class ClearClient implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			ClearClientForm();
		}
		
	}
	
	void ClearBrandForm() {
		cntry_comboBox.setSelectedIndex(0);
		b_nameTF.setText("");
	}
	
	class AddBrand implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(!b_nameTF.getText().isEmpty() && !b_nameTF.getText().isBlank()) {
				DBHelper.addBrand(b_nameTF.getText(),cntry_comboBox.getSelectedItem().toString());
				DBHelper.fillBrandCombo(BrandCombo,false);
				DBHelper.fillBrandCombo(s_brandCombo,true);
				DBHelper.fillBrandCombo(s_brandCombo2,true);
				ClearBrandForm();
				UpdateBrandsTable();
			}
		}
		
	}
	
	class ClearBrand implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			ClearBrandForm();
		}
		
	}
	
	void ClearCountryForm() {
		country_nameTF.setText("");
	}
	
	class AddCountry implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(!country_nameTF.getText().isEmpty() && !country_nameTF.getText().isBlank()) {
				DBHelper.addCountry(country_nameTF.getText());
				DBHelper.fillCountryCombo(s_countryoriginCombo,true);
				DBHelper.fillCountryCombo(cntry_comboBox,false);
				DBHelper.fillCountryCombo(s_CountryCB,true);
				ClearCountryForm();
				UpdateCountriesTable();
			}
		}
		
	}
	
	class ClearCountry implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			ClearCountryForm();
		}
		
	}
	
	class SearchClients implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String sql = "";
			String part = "SELECT FNAME, LNAME FROM CLIENTS WHERE ";
			if(!(s_clientFnameTF.getText().isEmpty())&&!(s_clientFnameTF.getText().isBlank())) {
				part+="FNAME = '"+s_clientFnameTF.getText()+"' ";
			}
			if(part.equalsIgnoreCase("SELECT FNAME, LNAME FROM CLIENTS WHERE ")) {
				if(!(s_clientLnameTF.getText().isEmpty())&&!(s_clientLnameTF.getText().isBlank())) {
					part+="LNAME = '"+s_clientLnameTF.getText()+"'";
				}
			}else {
				if(!(s_clientLnameTF.getText().isEmpty())&&!(s_clientLnameTF.getText().isBlank())) {
					part+="AND LNAME = '"+s_clientLnameTF.getText()+"'";
				}
			}
			sql = part;
			if(sql.equalsIgnoreCase("SELECT FNAME, LNAME FROM CLIENTS WHERE ")) {
				sql = "SELECT FNAME, LNAME FROM CLIENTS";
			}
			//update table
			clients_table.setModel(DBHelper.SearchClients(sql));
			clients_table.getColumnModel().getColumn(0).setHeaderValue("First name");
			clients_table.getColumnModel().getColumn(1).setHeaderValue("Last name");
		}
	}
	
	class ResetClientsS implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			UpdateClientsTable();
			s_clientFnameTF.setText("");
			s_clientLnameTF.setText("");
		}
	}
	
	class SearchBrands implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String bname = s_BrandNameTF.getText();
			String cname = s_CountryCB.getSelectedItem().toString();
			int cid = -1;
			if(!cname.equalsIgnoreCase("---")) {
				cid = DBHelper.GetCountryID(cname);
			}
			String sql = "";
			String part = "SELECT BRAND_NAME,COUNTRY_NAME FROM CAR_BRANDS INNER JOIN COUNTRIES ON COUNTRIES.ID=CAR_BRANDS.COUNTRY_ID WHERE ";
			
			if(!bname.isBlank()&&!bname.isEmpty()) {
				part+="BRAND_NAME = '"+bname+"' ";
			}
			if(part.equalsIgnoreCase("SELECT BRAND_NAME,COUNTRY_NAME FROM CAR_BRANDS INNER JOIN COUNTRIES ON COUNTRIES.ID=CAR_BRANDS.COUNTRY_ID WHERE ")) {
				if(cid>-1) {
					part+="COUNTRY_ID = "+cid;
				}
			}else {
				if(cid>-1) {
					part+="AND COUNTRY_ID = "+cid;
				}
			}
			
			sql = part;
			if(sql.equalsIgnoreCase("SELECT BRAND_NAME,COUNTRY_NAME FROM CAR_BRANDS INNER JOIN COUNTRIES ON COUNTRIES.ID=CAR_BRANDS.COUNTRY_ID WHERE ")) {
				sql = "SELECT BRAND_NAME,COUNTRY_NAME FROM CAR_BRANDS INNER JOIN COUNTRIES ON COUNTRIES.ID=CAR_BRANDS.COUNTRY_ID";
			}
			//update table
			brands_table.setModel(DBHelper.SearchBrands(sql));
			brands_table.getColumnModel().getColumn(0).setHeaderValue("Brand name");
			brands_table.getColumnModel().getColumn(1).setHeaderValue("Country of origin");
		}
	}
	
	class ResetBrandsS implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			UpdateBrandsTable();
			s_BrandNameTF.setText("");
			s_CountryCB.setSelectedIndex(0);
		}
	}
	
	class SearchCountries implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String sql = "";
			String part = "SELECT COUNTRY_NAME FROM COUNTRIES WHERE ";
			if(!(s_CountryNameTF.getText().isEmpty())&&!(s_CountryNameTF.getText().isBlank())) {
				part+="COUNTRY_NAME = '"+s_CountryNameTF.getText()+"'";
			}
			sql = part;
			if(sql.equalsIgnoreCase("SELECT COUNTRY_NAME FROM COUNTRIES WHERE ")) {
				sql = "SELECT COUNTRY_NAME FROM COUNTRIES";
			}
			//update table
			countries_table.setModel(DBHelper.SearchCountries(sql));
			countries_table.getColumnModel().getColumn(0).setHeaderValue("Country name");
		}
	}
	
	class ResetCountriesS implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			s_CountryNameTF.setText("");
			UpdateCountriesTable();
		}
	}
	
}//END OF MY FRAME CLASS
