import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

public class EditSaleFrame extends JFrame{
	
	JPanel frame = new JPanel();
	JPanel buttonsP = new JPanel();
	JPanel dateP = new JPanel();
	JPanel labels = new JPanel();
	JPanel inputs = new JPanel();
	
	JLabel carL = new JLabel("Car");
	JLabel clientL = new JLabel("Client");
	JLabel soldForL = new JLabel("Sold for");
	JLabel saleDateL = new JLabel("Sale date");
	
	JComboBox<String>carCombo = new JComboBox<String>();
	JComboBox<String>clientCombo = new JComboBox<String>();
	JTextField soldForTF = new JTextField();
	
	JLabel saleDayL = new JLabel("Day");
	JComboBox saleDayC = new JComboBox();
	JLabel saleMonthL = new JLabel("Month");
	JComboBox saleMonthC = new JComboBox();
	JLabel saleYearL = new JLabel("Year");
	JComboBox saleYearC = new JComboBox();
	
	JButton saveB = new JButton("Save");
	JButton delB = new JButton("Delete");
	JButton cancelB = new JButton("Cancel");
	
	JTable cars_table = new JTable();
	JTable sales_table = new JTable();
	
	int saleID = -1;
	
	public EditSaleFrame(int x, int y,JTable ct,JTable st,int sale_id,String[]cars,String thisCar,String[]clients,String thisClient,String soldFor,String saleDate,String[]days,String[]months,String[] years) {
		saleID=sale_id;
		cars_table = ct;
		sales_table = st;
		this.setBounds(x,y,500,350);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.add(frame,BorderLayout.CENTER);
		this.add(buttonsP,BorderLayout.SOUTH);
		
		soldForTF.setText(soldFor);
		
		carCombo = new JComboBox(cars);
		clientCombo = new JComboBox(clients);
		SetCorrectCar(cars,thisCar);
		SetCorrectClient(clients,thisClient);
		
		frame.setLayout(new BorderLayout());
		frame.setPreferredSize(new Dimension(500, 200));
		
		labels.setLayout(new GridLayout(5,1,5,5));
		labels.setBorder(BorderFactory.createEmptyBorder(8, 30, 0, 10));
		inputs.setLayout(new GridLayout(5,1,5,10));
		inputs.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 30));
		inputs.setPreferredSize(new Dimension(480, 200));
		frame.add(labels,BorderLayout.WEST);
		frame.add(inputs,BorderLayout.CENTER);
		
		labels.add(carL);
		inputs.add(carCombo);
		labels.add(clientL);
		inputs.add(clientCombo);
		labels.add(soldForL);
		inputs.add(soldForTF);
		labels.add(saleDateL);
		
		saleDayC = new JComboBox(days);
		saleMonthC = new JComboBox(months);
		saleYearC = new JComboBox(years);

		String[]dateArray=saleDate.split("-");
		SetDateCombos(dateArray,days,months,years);
		
		dateP.setLayout(new GridLayout(1,6));
		dateP.add(saleDayL);
		saleDayL.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		dateP.add(saleDayC);
		dateP.add(saleMonthL);
		saleMonthL.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		dateP.add(saleMonthC);
		dateP.add(saleYearL);
		saleYearL.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		dateP.add(saleYearC);
		inputs.add(dateP);
		
		buttonsP.setLayout(new GridLayout(1,3));
		buttonsP.setPreferredSize(new Dimension(400, 100));
		buttonsP.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
		buttonsP.add(saveB);
		buttonsP.add(delB);
		buttonsP.add(cancelB);
		
		saveB.addActionListener(e -> {
			SaveChanges();
	    });
		
		delB.addActionListener(e -> {
			if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this sale?","Confirm to delete sale data.",
			        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				//orverride
				Delete();
				UpdateCarsTable();
			}
	    });
		
		cancelB.addActionListener(e -> {
	        this.dispose();
	    });
		
		this.setVisible(true);
	}
	
	void Delete() {
		DBHelper.DeleteSale(saleID);
		UpdateCarsTable();
		UpdateSalesTable();
        this.dispose();
	}
	
	void SaveChanges() {
		if(!soldForTF.getText().isBlank()&&!soldForTF.getText().isEmpty()&&!soldForTF.getText().equals("") && !soldForTF.getText().contains(" ")) {
			//check if user missclicked
			if (JOptionPane.showConfirmDialog(null, "Are you sure you want to override the data saved about this sale?","Confirm to save changes.",
			        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				//orverride
				String date = saleYearC.getSelectedItem().toString()+"-"+ saleMonthC.getSelectedItem().toString()+"-"+ saleDayC.getSelectedItem().toString();
				DBHelper.EditSale(saleID,carCombo.getSelectedItem().toString(),clientCombo.getSelectedItem().toString(),Float.parseFloat(soldForTF.getText()),date);
				UpdateCarsTable();
				UpdateSalesTable();
				this.dispose();
			}
		}else {
			if(soldForTF.getText().contains(" ")) {
				ErrorBox("Invalid value for sale price.","Failed to save!");
			}else {
				ErrorBox("All fields must be filled.","Failed to save!");
			}
		}
	}
	
	void SetDateCombos(String[]dateArray, String[]days, String[]months,String[]years) {
		String d = dateArray[2].toString();
		String m = dateArray[1];
		String yr = dateArray[0];
		
		int d_index = 0;
		int m_index = 0;
		int yr_index = 0;
		
		for(int i = 0;i<days.length;i++) {
			if(d.equalsIgnoreCase(days[i])) {
				d_index=i;
				break;
			}
		}
		for(int i = 0;i<months.length;i++) {
			if(m.equalsIgnoreCase(months[i])) {
				m_index=i;
				break;
			}
		}
		for(int i = 0;i<years.length;i++) {
			if(yr.equalsIgnoreCase(years[i])) {
				yr_index=i;
				break;
			}
		}
		
		saleDayC.setSelectedIndex(d_index);
		saleMonthC.setSelectedIndex(m_index);
		saleYearC.setSelectedIndex(yr_index);
	}
	
	void SetCorrectCar(String[] cars,String thisCar) {
		for(int i = 0;i<cars.length;i++) {
			if(thisCar.equalsIgnoreCase(cars[i])) {
				carCombo.setSelectedIndex(i);
				break;
			}
		}
	}
	
	void SetCorrectClient(String[] clients,String thisClient) {
		for(int i = 0;i<clients.length;i++) {
			if(thisClient.equalsIgnoreCase(clients[i])) {
				clientCombo.setSelectedIndex(i);
				break;
			}
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
		sales_table.getColumnModel().getColumn(4).setHeaderValue("Sold date");
	}
	
	public static void ErrorBox(String message, String title)
    {
        JOptionPane.showMessageDialog(null, message,title, JOptionPane.INFORMATION_MESSAGE);
    }
}
