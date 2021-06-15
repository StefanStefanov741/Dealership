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

public class EditCountryFrame extends JFrame{
	JPanel frame = new JPanel();
	JPanel buttonsP = new JPanel();
	JPanel labels = new JPanel();
	JPanel inputs = new JPanel();
	
	JLabel country_nameL = new JLabel("Country name");
	
	JTextField country_nameTF = new JTextField();
	
	JButton saveB = new JButton("Save");
	JButton delB = new JButton("Delete");
	JButton cancelB = new JButton("Cancel");
	
	JTable brands_table = new JTable();
	JTable countries_table = new JTable();
	
	JComboBox<String> vcCountryCombo = new JComboBox<String>();
	JComboBox<String> cbCountryCombo = new JComboBox<String>();
	JComboBox<String> scCountryCombo = new JComboBox<String>();
	
	int country_id = -1;
	
	public EditCountryFrame(int x, int y,int c_id,JTable ct,JTable bt,JComboBox vcCC,JComboBox cbCC,JComboBox s_CountryCB,String c_name) {
		country_id = c_id;
		countries_table=ct;
		brands_table=bt;
		vcCountryCombo=vcCC;
		cbCountryCombo=cbCC;
		scCountryCombo=s_CountryCB;
		
		this.setBounds(x,y,300,200);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.add(frame,BorderLayout.CENTER);
		this.add(buttonsP,BorderLayout.SOUTH);
		
		country_nameTF.setText(c_name);
		
		labels.setLayout(new GridLayout(1,1,5,5));
		labels.setBorder(BorderFactory.createEmptyBorder(8, 30, 0, 10));
		inputs.setLayout(new GridLayout(1,1,5,10));
		inputs.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 30));
		inputs.setPreferredSize(new Dimension(480, 200));
		
		frame.setLayout(new BorderLayout());
		frame.add(labels,BorderLayout.WEST);
		frame.add(inputs,BorderLayout.CENTER);
		
		buttonsP.setLayout(new GridLayout(1,3));
		buttonsP.setPreferredSize(new Dimension(400, 100));
		buttonsP.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
		
		labels.add(country_nameL);
		inputs.add(country_nameTF);
		
		buttonsP.add(saveB);
		buttonsP.add(delB);
		buttonsP.add(cancelB);
		
		saveB.addActionListener(e -> {
			SaveChanges();
	    });
		
		delB.addActionListener(e -> {
			if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this country?","Confirm to delete country.",
			        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				//orverride
				Delete();
				UpdateCountriesTable();
				UpdateBrandsTable();
			}
	    });
		
		cancelB.addActionListener(e -> {
	        this.dispose();
	    });

		this.setVisible(true);
	}
	
	void SaveChanges() {
		if(!country_nameTF.getText().isEmpty()&&!country_nameTF.getText().isBlank()) {
			//check if user missclicked
			if (JOptionPane.showConfirmDialog(null, "Are you sure you want to override the data saved about this country?","Confirm to save changes.",
			        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				//orverride
				DBHelper.EditCountry(country_id,country_nameTF.getText());
				UpdateCountriesTable();
				UpdateBrandsTable();
				this.dispose();
			}
		}else {
			ErrorBox("All fields must be filled!","Failed to save!");
		}
	}
	
	void Delete() {
		String s = "SELECT * FROM CAR_BRANDS WHERE COUNTRY_ID = "+country_id;
		if(DBHelper.ItemCount(s)>0) {
			ErrorBox("There is a brand from this country in your database. If you want to delete the country you must delete the brand first!","Failed to delete!");
		}else {
			DBHelper.DeleteCountry(country_id);
			UpdateCountriesTable();
	        this.dispose();
		}
	}
	
	void UpdateCountriesTable() {
		countries_table.setModel(DBHelper.getAllcountries());
		countries_table.getColumnModel().getColumn(0).setHeaderValue("Country name");
		DBHelper.fillCountryCombo(cbCountryCombo,false);
		DBHelper.fillCountryCombo(vcCountryCombo,true);
		DBHelper.fillCountryCombo(scCountryCombo,true);
	}
	void UpdateBrandsTable() {
		brands_table.setModel(DBHelper.getAllbrands());
		brands_table.getColumnModel().getColumn(0).setHeaderValue("Brand name");
		brands_table.getColumnModel().getColumn(1).setHeaderValue("Country origin");
	}

	public static void ErrorBox(String message, String title)
    {
        JOptionPane.showMessageDialog(null, message,title, JOptionPane.INFORMATION_MESSAGE);
    }
}
