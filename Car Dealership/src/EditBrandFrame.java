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

public class EditBrandFrame extends JFrame{
	JPanel frame = new JPanel();
	JPanel buttonsP = new JPanel();
	JPanel labels = new JPanel();
	JPanel inputs = new JPanel();
	
	JLabel brandL = new JLabel("Brand name");
	JLabel countryL = new JLabel("Country of origin");
	
	JTextField brandTF = new JTextField();
	JComboBox cntryCombo = new JComboBox();
	
	JButton saveB = new JButton("Save");
	JButton delB = new JButton("Delete");
	JButton cancelB = new JButton("Cancel");
	
	JTable brands_table = new JTable();
	JTable sales_table = new JTable();
	JTable cars_table = new JTable();
	JComboBox<String> ccBrandCombo = new JComboBox<String>();
	JComboBox<String> vcBrandCombo = new JComboBox<String>();
	JComboBox<String> vsBrandCombo = new JComboBox<String>();
	JComboBox<String> csCarCombo = new JComboBox<String>();
	
	int brand_id = -1;
	
	public EditBrandFrame(int x, int y, JTable bt,JTable st,JTable ct,int b_id,JComboBox<String> vsCC,JComboBox<String> csCC,JComboBox<String> vcCC,JComboBox<String> ccCC,String brand_name,String country_name) {
		brand_id = b_id;
		cars_table=ct;
		sales_table=st;
		brands_table=bt;
		vcBrandCombo=vcCC;
		ccBrandCombo=ccCC;
		vsBrandCombo=vsCC;
		csCarCombo=csCC;
		
		this.setBounds(x,y,500,300);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.add(frame,BorderLayout.CENTER);
		this.add(buttonsP,BorderLayout.SOUTH);
		
		brandTF.setText(brand_name);
		DBHelper.fillCountryCombo(cntryCombo, false);

		for(int i = 0;i<cntryCombo.getItemCount();i++) {
			if(country_name.equalsIgnoreCase(cntryCombo.getItemAt(i).toString())) {
				cntryCombo.setSelectedIndex(i);
				break;
			}
		}
		
		labels.setLayout(new GridLayout(2,1,5,5));
		labels.setBorder(BorderFactory.createEmptyBorder(8, 30, 0, 10));
		inputs.setLayout(new GridLayout(2,1,5,10));
		inputs.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 30));
		inputs.setPreferredSize(new Dimension(480, 200));
		
		frame.setLayout(new BorderLayout());
		frame.add(labels,BorderLayout.WEST);
		frame.add(inputs,BorderLayout.CENTER);
		
		buttonsP.setLayout(new GridLayout(1,3));
		buttonsP.setPreferredSize(new Dimension(400, 100));
		buttonsP.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
		
		labels.add(brandL);
		labels.add(countryL);
		inputs.add(brandTF);
		inputs.add(cntryCombo);
		
		buttonsP.add(saveB);
		buttonsP.add(delB);
		buttonsP.add(cancelB);
		
		saveB.addActionListener(e -> {
			SaveChanges();
	    });
		
		delB.addActionListener(e -> {
			if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this brand?","Confirm to delete brand.",
			        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				//orverride
				Delete();
				UpdateCarsTable();
				UpdateSalesTable();
				UpdateBrandsTable();
				DBHelper.fillBrandCombo(ccBrandCombo,false);
				DBHelper.fillCarsCombo(csCarCombo);
				DBHelper.fillBrandCombo(vcBrandCombo,true);
				DBHelper.fillBrandCombo(vsBrandCombo,true);
			}
	    });
		
		cancelB.addActionListener(e -> {
	        this.dispose();
	    });

		this.setVisible(true);
	}
	
	void SaveChanges() {
		if(!brandTF.getText().isEmpty()&&!brandTF.getText().isBlank()) {
			//check if user missclicked
			if (JOptionPane.showConfirmDialog(null, "Are you sure you want to override the data saved about this brand?","Confirm to save changes.",
			        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				//orverride
				int country_id = DBHelper.GetCountryID(cntryCombo.getSelectedItem().toString());
				DBHelper.EditBrand(brand_id,brandTF.getText(),country_id);
				UpdateCarsTable();
				UpdateSalesTable();
				UpdateBrandsTable();
				DBHelper.fillBrandCombo(ccBrandCombo,false);
				DBHelper.fillCarsCombo(csCarCombo);
				DBHelper.fillBrandCombo(vcBrandCombo,true);
				DBHelper.fillBrandCombo(vsBrandCombo,true);
				this.dispose();
			}
		}else {
			ErrorBox("All fields must be filled!","Failed to save!");
		}
	}
	
	void Delete() {
		String s = "SELECT * FROM CARS WHERE BRAND_ID = "+brand_id;
		if(DBHelper.ItemCount(s)>0) {
			ErrorBox("There is a car from this brand already added to your database. If you want to delete the brand you must delete the car first!","Failed to delete!");
		}else {
			DBHelper.DeleteBrand(brand_id);
	        this.dispose();
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
	void UpdateBrandsTable() {
		brands_table.setModel(DBHelper.getAllbrands());
		brands_table.getColumnModel().getColumn(0).setHeaderValue("Brand name");
		brands_table.getColumnModel().getColumn(1).setHeaderValue("Country origin");
	}
	void UpdateSalesTable() {
		sales_table.setModel(DBHelper.getAllsales());
		sales_table.getColumnModel().getColumn(0).setHeaderValue("Car");
		sales_table.getColumnModel().getColumn(1).setHeaderValue("Client");
		sales_table.getColumnModel().getColumn(2).setHeaderValue("Sold for");
		sales_table.getColumnModel().getColumn(3).setHeaderValue("Discount from original price");
		sales_table.getColumnModel().getColumn(4).setHeaderValue("Sale date");
	}
	
	public static void ErrorBox(String message, String title)
    {
        JOptionPane.showMessageDialog(null, message,title, JOptionPane.INFORMATION_MESSAGE);
    }
}
