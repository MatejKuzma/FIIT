import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class PlayerDetailWindow extends JFrame
{
	
	//kontajnery na zmenu
	List<PlayerAchiavement> achiavementChangeList = new ArrayList();
	List<PlayerStatistic> playerStatisticList = new ArrayList();
	
	JLabel lblUpdate;
	PlayerData playerData;
	DefaultTableModel scoreTableData;
	DefaultTableModel achiavementTableData;
	private JTable statisticTable;
	private JTable achiavementTable;
	private JFrame jFrame;
	private JTextField textFieldNickname;
	String[] countries;
	
	public PlayerDetailWindow(int id, String[] countries) 
	{
		this.countries = countries;
		
		// pully dat z databazy
		playerData = GuiDataProvider.instance.getPlayerDataByID(id); // pull z databazy o hracovych informaciach
		scoreTableData = GuiDataProvider.instance.getPlayerStatisticByID(id); // pull z databazy o hracovej statistike
		achiavementTableData = GuiDataProvider.instance.getPlayerAchiavementByID(id); // pull z databazy o hracovych achiavementoch
		
		jFrame = new JFrame();
		jFrame.setSize(400, 500);
		getContentPane().setLayout(null);
		
		statisticTable = new JTable(); 
		statisticTable.setModel(scoreTableData);
		// schovanie id 
		statisticTable.getColumnModel().getColumn(0).setMinWidth(0);
		statisticTable.getColumnModel().getColumn(0).setMaxWidth(0);
		jFrame.getContentPane().setLayout(null);
		JScrollPane scrollPaneStatistic = new JScrollPane(statisticTable);
		scrollPaneStatistic.setBounds(10, 131, 364, 130);
		jFrame.getContentPane().add(scrollPaneStatistic);
		
		achiavementTable = new JTable();
		achiavementTable.setModel(achiavementTableData);
		// schovanie id 
		achiavementTable.getColumnModel().getColumn(0).setMinWidth(0);
		achiavementTable.getColumnModel().getColumn(0).setMaxWidth(0);
		JScrollPane scrollPaneAchiavement = new JScrollPane(achiavementTable);
		scrollPaneAchiavement.setBounds(10, 286, 364, 130);
		//jFrame.getContentPane().setLayout(null);
		jFrame.getContentPane().add(scrollPaneAchiavement);
		
		JLabel lblPlayerID = new JLabel("Player ID: "+playerData.getId());
		lblPlayerID.setBounds(10, 11, 122, 14);
		jFrame.getContentPane().add(lblPlayerID);
		
		textFieldNickname = new JTextField();
		textFieldNickname.setBounds(90, 35, 150, 20);
		textFieldNickname.setText(playerData.getNickname());
		jFrame.getContentPane().add(textFieldNickname);
		textFieldNickname.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Nickname: ");
		lblNewLabel.setBounds(10, 38, 80, 14);
		jFrame.getContentPane().add(lblNewLabel);
		
		 String[] comboBoxStrings = {"M", "F"};
		final JComboBox comboBox = new JComboBox(comboBoxStrings);
		comboBox.setBounds(90, 63, 51, 20);
		if(playerData.getSex() == 'F') comboBox.setSelectedIndex(1);
			else comboBox.setSelectedIndex(0);
		jFrame.getContentPane().add(comboBox);
		
		JLabel lblNewLabel_1 = new JLabel("Sex: ");
		lblNewLabel_1.setBounds(10, 63, 46, 14);
		jFrame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblCountry = new JLabel("Country:");
		lblCountry.setBounds(10, 88, 80, 14);
		jFrame.getContentPane().add(lblCountry);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(10, 427, 89, 23);
		jFrame.getContentPane().add(btnCancel);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(285, 427, 89, 23);
		jFrame.getContentPane().add(btnUpdate);
		
		lblUpdate = new JLabel("Data succesfully updated!");
		lblUpdate.setForeground(Color.GREEN);
		lblUpdate.setBounds(109, 431, 166, 14);
		lblUpdate.setVisible(false);
		jFrame.getContentPane().add(lblUpdate);
		
		JLabel lblNewLabel_2 = new JLabel("Player achiavements :");
		lblNewLabel_2.setBounds(10, 271, 145, 14);
		jFrame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblPlayerScoreStatistics = new JLabel("Player score statistics for level:");
		lblPlayerScoreStatistics.setBounds(10, 113, 199, 14);
		jFrame.getContentPane().add(lblPlayerScoreStatistics);
		
		JLabel lblNewLabel_3 = new JLabel("Level column cannot be updated!");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.ITALIC, 9));
		lblNewLabel_3.setBounds(214, 113, 203, 14);
		jFrame.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Achiavement columnt cannot be updated!");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.ITALIC, 9));
		lblNewLabel_4.setBounds(0, 0, 384, 461);
		jFrame.getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Achiavement column cannot be updated!");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.ITALIC, 9));
		lblNewLabel_5.setBounds(165, 272, 209, 14);
		jFrame.getContentPane().add(lblNewLabel_5);
		
		final JComboBox comboBoxCountry = new JComboBox(countries);
		comboBoxCountry.setSelectedIndex(playerData.getCountryInt()-1);
		comboBoxCountry.setBounds(90, 85, 150, 20);
		jFrame.getContentPane().add(comboBoxCountry);
		
		jFrame.setVisible(true);
	
	// TLACIDLA
	btnUpdate.addActionListener(new ActionListener()
	{
	  public void actionPerformed(ActionEvent e)
	  {
		  // ziskavanie dat o hracovy
		  playerData.setNickname(textFieldNickname.getText());
		  playerData.setCountryInt(comboBoxCountry.getSelectedIndex());
		  playerData.setSex(comboBox.getSelectedItem().toString().charAt(0));
		  
		  // zmena dat v databaze
		  GuiDataProvider.instance.updatePlayer(achiavementChangeList, playerStatisticList, playerData);
		  achiavementChangeList.clear();
		  PageWindow.instance.refreshTable();
		  lblUpdate.setVisible(true);
	  }
	});
	
	// TLACIDLA
	btnCancel.addActionListener(new ActionListener()
	{
	  public void actionPerformed(ActionEvent e)
	  {
		  jFrame.setVisible(false);
		  jFrame = null;
		  playerData = null;
		  scoreTableData = null;
		  achiavementTableData = null;
	  }
	});
	
	// listener na zmenu tabuliek 
	achiavementTable.getModel().addTableModelListener(new TableModelListener() 
	{
	      public void tableChanged(TableModelEvent e) 
	      {  
	    	  // pridanie novej zmeny do listu
	    	  int id = (Integer) achiavementTable.getValueAt(achiavementTable.getSelectedRow(), 0);
	    	  String achiavement = (String) achiavementTable.getValueAt(achiavementTable.getSelectedRow(), 1);
	    	  String earnTime = (String) achiavementTable.getValueAt(achiavementTable.getSelectedRow(), 2);
	    	  achiavementChangeList.add(new PlayerAchiavement(id, achiavement, earnTime));
	    	  lblUpdate.setVisible(false);
	      }
	});	
	
	// listener na zmenu tabuliek 
	statisticTable.getModel().addTableModelListener(new TableModelListener() 
	{
	      public void tableChanged(TableModelEvent e) 
	      {  
	    	 int id = (Integer) statisticTable.getValueAt(statisticTable.getSelectedRow(), 0);
	    	 int score = Integer.parseInt( statisticTable.getValueAt(statisticTable.getSelectedRow(), 2).toString());
	    	 int playhours = Integer.parseInt(statisticTable.getValueAt(statisticTable.getSelectedRow(), 3).toString());
	    	 playerStatisticList.add(new PlayerStatistic(id,score,playhours));
	    	 lblUpdate.setVisible(false);
	      }
	    });	
	}
}
