
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

public class EditCarFrame extends JFrame{
	
	JPanel frame = new JPanel();
	JPanel buttonsP = new JPanel();
	JPanel labels = new JPanel();
	JPanel inputs = new JPanel();
	
	JLabel brandL = new JLabel("Brand");
	JLabel modelL = new JLabel("Model");
	JLabel yearL = new JLabel("Year");
	JLabel priceL = new JLabel("Price");
	JLabel commentL = new JLabel("Comment");
	
	JComboBox<String> brandCombo = new JComboBox<>();
	JTextField modelTF = new JTextField();
	JTextField yearTF = new JTextField();
	JTextField priceTF = new JTextField();
	JTextField commentTF = new JTextField();
	JButton saveB = new JButton("Save");
	JButton delB = new JButton("Delete");
	JButton cancelB = new JButton("Cancel");
	
	JTable cars_table = new JTable();
	JTable sales_table = new JTable();
	
	int carID = -1;
	
	public EditCarFrame(int x,int y,JTable ct,JTable st,int car_id,String brand_name,String model_s,String year_s,String price_s,String desc_s) {
		carID=car_id;
		cars_table = ct;
		sales_table = st;
		this.setBounds(x,y,500,350);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.add(frame,BorderLayout.CENTER);
		this.add(buttonsP,BorderLayout.SOUTH);
		
		DBHelper.fillBrandCombo(brandCombo,false);
		brandCombo.setSelectedItem(brand_name);
		modelTF.setText(model_s);
		yearTF.setText(year_s);
		priceTF.setText(price_s);
		commentTF.setText(desc_s);
		
		frame.setLayout(new BorderLayout());
		frame.setPreferredSize(new Dimension(500, 200));
		
		labels.setLayout(new GridLayout(5,1,5,5));
		labels.setBorder(BorderFactory.createEmptyBorder(8, 30, 0, 10));
		inputs.setLayout(new GridLayout(5,1,5,10));
		inputs.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 30));
		inputs.setPreferredSize(new Dimension(480, 200));
		frame.add(labels,BorderLayout.WEST);
		frame.add(inputs,BorderLayout.CENTER);
		
		labels.add(brandL);
		inputs.add(brandCombo);
		labels.add(modelL);
		inputs.add(modelTF);
		labels.add(yearL);
		inputs.add(yearTF);
		labels.add(priceL);
		inputs.add(priceTF);
		labels.add(commentL);
		inputs.add(commentTF);
		
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
			if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this car?","Confirm to delete car.",
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
	
	void SaveChanges() {
		if((!modelTF.getText().isEmpty()&&!yearTF.getText().isEmpty()&&!priceTF.getText().isEmpty())&&(!modelTF.getText().isBlank()&&!yearTF.getText().isBlank()&&!priceTF.getText().isBlank())) {
			//check if user missclicked
			if (JOptionPane.showConfirmDialog(null, "Are you sure you want to override the data saved about this car?","Confirm to save changes.",
			        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				//orverride
				DBHelper.EditCar(carID, modelTF.getText(),brandCombo.getSelectedItem().toString(),Float.parseFloat(priceTF.getText()),yearTF.getText(),commentTF.getText());
				UpdateCarsTable();
				UpdateSalesTable();
				this.dispose();
			}
		}else {
			ErrorBox("All fields, except the comment field, must be filled!","Failed to save!");
		}
	}
	
	void Delete() {
		String s = "SELECT * FROM SALES WHERE CAR_ID = "+carID;
		if(DBHelper.ItemCount(s)>0) {
			ErrorBox("This car was sold and cannot be deleted until you delete all histories of the sale.","Failed to delete!");
		}else {
			DBHelper.DeleteComment(carID);
			DBHelper.DeleteCar(carID);
			UpdateCarsTable();
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
