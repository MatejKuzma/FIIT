import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Font;


public class InsertNewPlayer extends JFrame
{
	private JTextField textFieldNickname;
	private JFrame jFrame;
	private PlayerData playerData;
	
	public InsertNewPlayer(String[] countryStrings, String[] sexStrings) 
	{
		jFrame = this;
		jFrame.setSize(300,250);
		getContentPane().setLayout(null);
		
		JLabel lblCountry = new JLabel("Country:");
		lblCountry.setBounds(10, 30, 46, 14);
		getContentPane().add(lblCountry);
		
		JLabel lblSex = new JLabel("Sex: ");
		lblSex.setBounds(10, 55, 46, 14);
		getContentPane().add(lblSex);
		
		JLabel lblNickname = new JLabel("Nickname:");
		lblNickname.setBounds(10, 80, 66, 14);
		getContentPane().add(lblNickname);
		
		JButton btnInsert = new JButton("Insert");
		btnInsert.setBounds(182, 164, 89, 23);
		getContentPane().add(btnInsert);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(10, 164, 89, 23);
		getContentPane().add(btnCancel);
		
		final JLabel lblPlayerSuccesfullyAdded = new JLabel("Player succesfully added!");
		lblPlayerSuccesfullyAdded.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPlayerSuccesfullyAdded.setForeground(Color.GREEN);
		lblPlayerSuccesfullyAdded.setVisible(false);
		lblPlayerSuccesfullyAdded.setBounds(71, 105, 252, 14);
		getContentPane().add(lblPlayerSuccesfullyAdded);
		
		final JComboBox comboBoxCountry = new JComboBox(countryStrings);
		comboBoxCountry.setBounds(71, 27, 200, 20);
		getContentPane().add(comboBoxCountry);
		
		final JComboBox comboBoxSex = new JComboBox(sexStrings);
		comboBoxSex.setBounds(71, 52, 51, 20);
		getContentPane().add(comboBoxSex);
		
		textFieldNickname = new JTextField();
		textFieldNickname.setBounds(71, 77, 200, 20);
		getContentPane().add(textFieldNickname);
		textFieldNickname.setColumns(10);
		
		final JLabel lblPlayerCannotBe = new JLabel("Player cannot be inserted, missing nickname!");
		lblPlayerCannotBe.setForeground(Color.RED);
		lblPlayerCannotBe.setBounds(10, 130, 278, 14);
		lblPlayerCannotBe.setVisible(false);
		getContentPane().add(lblPlayerCannotBe);
		
		jFrame.setVisible(true);
		
		// tlacidlo na vlozenie noveho prvku
		btnInsert.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			  if(textFieldNickname.getText().equals(""))
			  {
				  lblPlayerCannotBe.setVisible(true);
				  lblPlayerSuccesfullyAdded.setVisible(false);
			  }
			  else
			  {
				  lblPlayerCannotBe.setVisible(false);
				  
				  playerData = new PlayerData();
				  playerData.setCountryInt(comboBoxCountry.getSelectedIndex()+1);
				  playerData.setSex(((String)comboBoxSex.getSelectedItem()).charAt(0));
				  playerData.setNickname(textFieldNickname.getText());
				  
				  if(GuiDataProvider.instance.insertPlayerToDB(playerData)) 
					  lblPlayerSuccesfullyAdded.setVisible(true);
				  PageWindow.instance.refreshTable();
			  }
		  }
		});
		
		// tlacidlo na vypnutie okna
		btnCancel.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			 jFrame.setVisible(false);
			 jFrame = null;
		  }
		});
	}
}
