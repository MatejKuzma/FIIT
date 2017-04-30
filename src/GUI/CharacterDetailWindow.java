import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;


public class CharacterDetailWindow extends JFrame
{
	private JTextField textFieldCreateTime;
	private JTextField textFieldCharacterName;
	private JLabel lblSuccesfullUpdate;
	private JTable stockTable;
	private JFrame jFrame;
	DefaultTableModel stockTableData;
	CharacterData characterData;

	public CharacterDetailWindow(int id) 
	{
		//datove modely na pully z databaz
		
		// pully z databaz 
		characterData = GuiDataProvider.instance.getCharacterDataByID(id);
		stockTableData = GuiDataProvider.instance.getStockDataByID(id);
		
		jFrame = new JFrame();
		jFrame.setSize(400, 400);
		getContentPane().setLayout(null);
		jFrame.getContentPane().setLayout(null);
		
		JLabel lblPlayerID = new JLabel("Belongs to player: "+characterData.getPlayerID());
		lblPlayerID.setBounds(10, 40, 199, 14);
		jFrame.getContentPane().add(lblPlayerID);
		
		JLabel lblCharacterID = new JLabel("Character ID: "+ characterData.getId());
		lblCharacterID.setBounds(10, 15, 199, 14);
		jFrame.getContentPane().add(lblCharacterID);
		
		JLabel lblTime = new JLabel("Create time: ");
		lblTime.setBounds(10, 65, 90, 14);
		jFrame.getContentPane().add(lblTime);
		
		textFieldCreateTime = new JTextField();
		textFieldCreateTime.setBounds(110, 62, 199, 20);
		textFieldCreateTime.setColumns(10);
		textFieldCreateTime.setText(characterData.getCreateTime());
		jFrame.getContentPane().add(textFieldCreateTime);
		
		JLabel lblType = new JLabel("Type:");
		lblType.setBounds(10, 112, 46, 14);
		jFrame.getContentPane().add(lblType);
		
		 String[] comboBoxStrings = {"Warrior", "Mage", "Priest", "Archer", "Knight"};
		final JComboBox comboBox = new JComboBox(comboBoxStrings);
		comboBox.setBounds(110, 109, 99, 20);
		comboBox.setSelectedIndex(characterData.getType()-1);
		jFrame.getContentPane().add(comboBox);
		
		JLabel lblCharacterName = new JLabel("Character name:");
		lblCharacterName.setBounds(10, 87, 90, 14);
		jFrame.getContentPane().add(lblCharacterName);
		
		textFieldCharacterName = new JTextField();
		textFieldCharacterName.setBounds(110, 84, 199, 20);
		textFieldCharacterName.setColumns(10);
		textFieldCharacterName.setText(characterData.getCharacterName());
		jFrame.getContentPane().add(textFieldCharacterName);
		
		stockTable = new JTable(); 
		stockTable.setModel(stockTableData);
		// schovanie id 
		stockTable.getColumnModel().getColumn(0).setMinWidth(0);
		stockTable.getColumnModel().getColumn(0).setMaxWidth(0);
		JScrollPane scrollPane = new JScrollPane(stockTable);
		scrollPane.setBounds(10, 152, 364, 164);
		jFrame.getContentPane().add(scrollPane);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(0, 327, 89, 23);
		jFrame.getContentPane().add(btnCancel);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(285, 327, 89, 23);
		jFrame.getContentPane().add(btnUpdate);
		
		JLabel lblCharacterInventory = new JLabel("Character inventory:");
		lblCharacterInventory.setBounds(10, -37, 384, 361);
		jFrame.getContentPane().add(lblCharacterInventory);
		
		lblSuccesfullUpdate = new JLabel("Update succesful!");
		lblSuccesfullUpdate.setForeground(Color.GREEN);
		lblSuccesfullUpdate.setBounds(110, 331, 146, 14);
		lblSuccesfullUpdate.setVisible(false);
		jFrame.getContentPane().add(lblSuccesfullUpdate);
		
		jFrame.setVisible(true);
		
		// TLACIDLA
		// tlacidlo na ukoncenie 
		btnCancel.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			  jFrame.setVisible(false);
			  jFrame = null;
			  stockTableData = null;
		  }
		});
		
		btnUpdate.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			  characterData.setCharacterName(textFieldCharacterName.getText());
			  characterData.setCreateTime(textFieldCreateTime.getText());
			  characterData.setType(comboBox.getSelectedIndex()+1);
			  GuiDataProvider.instance.updateCharacter(characterData);
			  lblSuccesfullUpdate.setVisible(true);
			  PageWindow.instance.refreshTable();
		  }
		});
	}
}
