import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;


public class PlayerFilterWindow extends JFrame
{
	private JTextField textFieldName;
	private JTextField textRevenueValue;
	private JComboBox comboBoxSex;
	private JComboBox comboBoxLogic;
	JButton btnFilter;
	JButton btnCancel;
	JButton ceaseButton;
	JTable filterTable;
	JFrame frame;
	
	public PlayerFilterWindow(JButton ceaseBut, JTable table, final PlayerFilterRequest pfr, String[] sexStrings) 
	{
		frame = this;
		filterTable = table;
		this.setSize(330,170);
		getContentPane().setLayout(null);
		ceaseButton = ceaseBut;
		
		JLabel lblBySex = new JLabel("by sex:");
		lblBySex.setBounds(10, 11, 46, 14);
		getContentPane().add(lblBySex);
		
		comboBoxSex = new JComboBox(sexStrings);
		comboBoxSex.setSelectedIndex(2);
		comboBoxSex.setBounds(66, 8, 60, 20);
		getContentPane().add(comboBoxSex);
		
		JLabel lblByName = new JLabel("by name:");
		lblByName.setBounds(10, 36, 67, 14);
		getContentPane().add(lblByName);
		
		textFieldName = new JTextField();
		textFieldName.setBounds(66, 33, 200, 20);
		getContentPane().add(textFieldName);
		textFieldName.setColumns(10);
		
		JLabel lblByRevenue = new JLabel("by revenue:");
		lblByRevenue.setBounds(10, 60, 46, 14);
		getContentPane().add(lblByRevenue);
		
		String[] logicStrings = {"=","<=", "=>"};
		comboBoxLogic = new JComboBox(logicStrings);
		comboBoxLogic.setSelectedIndex(2);
		comboBoxLogic.setBounds(76, 57, 50, 20);
		getContentPane().add(comboBoxLogic);
		
		textRevenueValue = new JTextField();
		textRevenueValue.setBounds(136, 57, 86, 20);
		textRevenueValue.setText("0");
		getContentPane().add(textRevenueValue);
		textRevenueValue.setColumns(10);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(10, 103, 89, 23);
		getContentPane().add(btnCancel);
		
		btnFilter = new JButton("Filter data");
		btnFilter.setBounds(191, 103, 113, 23);
		getContentPane().add(btnFilter);
		
		frame.setVisible(true);
		
		btnFilter.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			// naplnanie input informacii ...
			pfr.setSexRequest(comboBoxSex.getSelectedIndex());
			pfr.setName(textFieldName.getText());
			pfr.setLogicRequest(comboBoxLogic.getSelectedIndex());
			pfr.setSumValue((float) Double.parseDouble(textRevenueValue.getText()));
			
			DefaultTableModel dtm = GuiDataProvider.instance.filterPlayer(pfr , 100, 1);
			// nastav nove data pre tabulku
			filterTable.setModel(dtm);
			ceaseButton.setVisible(true);
		  }
		});
		
		btnCancel.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			frame.setVisible(false);
			frame = null;
		  }
		});
	}	
}
