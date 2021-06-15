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

public class EditClientFrame extends JFrame{
	JPanel frame = new JPanel();
	JPanel buttonsP = new JPanel();
	JPanel labels = new JPanel();
	JPanel inputs = new JPanel();
	
	JLabel fnameL = new JLabel("First name");
	JLabel lnameL = new JLabel("Last name");
	
	JTextField fnameTF = new JTextField();
	JTextField lnameTF = new JTextField();
	
	JButton saveB = new JButton("Save");
	JButton delB = new JButton("Delete");
	JButton cancelB = new JButton("Cancel");
	
	JTable clients_table = new JTable();
	JTable sales_table = new JTable();
	JComboBox<String> vsClientCombo = new JComboBox<>();
	JComboBox<String> csClientCombo = new JComboBox<>();
	
	int client_id = -1;
	
	public EditClientFrame(int x, int y, JTable ct,JTable st,int cl_id,JComboBox<String> vsCC,JComboBox<String> csCC,String fn,String ln) {
		client_id = cl_id;
		clients_table=ct;
		sales_table=st;
		vsClientCombo=vsCC;
		csClientCombo=csCC;
		
		this.setBounds(x,y,500,280);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.add(frame,BorderLayout.CENTER);
		this.add(buttonsP,BorderLayout.SOUTH);
		
		fnameTF.setText(fn);
		lnameTF.setText(ln);
		
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
		
		labels.add(fnameL);
		labels.add(lnameL);
		inputs.add(fnameTF);
		inputs.add(lnameTF);
		
		buttonsP.add(saveB);
		buttonsP.add(delB);
		buttonsP.add(cancelB);
		
		saveB.addActionListener(e -> {
			SaveChanges();
	    });
		
		delB.addActionListener(e -> {
			if (JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this client?","Confirm to delete client.",
			        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				//orverride
				Delete();
				UpdateClientsTable();
			}
	    });
		
		cancelB.addActionListener(e -> {
	        this.dispose();
	    });

		this.setVisible(true);
	}
	
	void SaveChanges() {
		if((!fnameTF.getText().isEmpty()&&!lnameTF.getText().isEmpty())&&(!fnameTF.getText().isBlank()&&!lnameTF.getText().isBlank())) {
			//check if user missclicked
			if (JOptionPane.showConfirmDialog(null, "Are you sure you want to override the data saved about this client?","Confirm to save changes.",
			        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				//orverride
				DBHelper.EditClient(client_id,fnameTF.getText(),lnameTF.getText());
				UpdateSalesTable();
				UpdateClientsTable();
				this.dispose();
			}
		}else {
			ErrorBox("All fields must be filled!","Failed to save!");
		}
	}
	
	void Delete() {
		String s = "SELECT * FROM SALES WHERE CLIENT_ID = "+client_id;
		if(DBHelper.ItemCount(s)>0) {
			ErrorBox("This client has made a purchase and cannot be deleted until you delete all histories of the sale.","Failed to delete!");
		}else {
			DBHelper.DeleteClient(client_id);
			UpdateClientsTable();
	        this.dispose();
		}
	}
	
	void UpdateClientsTable() {
		clients_table.setModel(DBHelper.getAllclients());
		clients_table.getColumnModel().getColumn(0).setHeaderValue("First name");
		clients_table.getColumnModel().getColumn(1).setHeaderValue("Last name");
		DBHelper.fillClientsCombo(vsClientCombo, true);
		DBHelper.fillClientsCombo(csClientCombo, true);
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
