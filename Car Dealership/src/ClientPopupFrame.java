import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

public class ClientPopupFrame extends JFrame{
	
	JLabel fnameL = new JLabel("First name: ");
	JLabel lnameL = new JLabel("Last name: ");
	JTextField fnameTF = new JTextField();
	JTextField lnameTF = new JTextField();
	JButton add = new JButton("Add");
	JButton cancel = new JButton("Cancel");
	JTable clients_table = new JTable();
	
	public ClientPopupFrame(int x,int y,JComboBox<String> combo,JComboBox<String> searchCombo,JTable tbl) {
		clients_table = tbl;
		this.setBounds(x,y,250,150);
		this.setLayout(new GridLayout(3,2));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.add(fnameL);
		this.add(fnameTF);
		this.add(lnameL);
		this.add(lnameTF);
		this.add(add);
		this.add(cancel);
		cancel.addActionListener(e -> {
	         this.dispose();
	    });
		add.addActionListener(e -> {
			//add client
			DBHelper.addClient(fnameTF.getText(), lnameTF.getText());
			DBHelper.fillClientsCombo(combo,false);
			DBHelper.fillClientsCombo(searchCombo,true);
			UpdateClientsTable();
	        this.dispose();
	    });
		this.setVisible(true);
	}
	
	void UpdateClientsTable() {
		clients_table.setModel(DBHelper.getAllclients());
		clients_table.getColumnModel().getColumn(0).setHeaderValue("First name");
		clients_table.getColumnModel().getColumn(1).setHeaderValue("Last name");
	}
}
