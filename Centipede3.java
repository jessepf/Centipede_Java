/* Implements a game theory game, called Centipede
	Concept by Arun Koshy
	Code by Jesse P Francis
	Author notes: My first Java program (and first one with GUI, too!)
	
	Input: 1. Change parameters of the game
			1.1 Limit
			1.2 Initial Values
		2. Cooperate or Defect button for each player
	Output: 1. If a player defects, 2 of his points are transferred to other player and ends the game.
		2. If a player cooperates, that player gets 1 point.
		3. Each player gets a turn each till the upper limit, (default 100) is achieved.
*/

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Centipede3
  extends JFrame
  implements ActionListener
{
  /* Declaring UI Elements */
    JPanel pane = new JPanel();
	
  //Text area
	JTextArea txtArea = new JTextArea(20, 20);
	JTextField limitField = new JTextField(3);
  
  //Messages
	JLabel answer = new JLabel("");
  
  //Buttons
	JButton cooperate = new JButton("Cooperate");
	JButton defect = new JButton("Defect");
	JButton limit_edit = new JButton("Change Limit");
	JButton reset = new JButton("Reset Game");
	  
  //Other variables
	  int limit, p1 = 2, p2 = 2, turn_no = 0;
	  boolean turn = true, done = false; //turn true = P1, false = P2

  //Constructor: Where it all begins, and buttons are placed
	  Centipede3()
	  {
		super("Centipede Game");setBounds(100, 100, 800, 400);
		this.limitField.setText("100");
		//The dynamic messages and text handling
			this.pane.add(this.answer);
			this.answer.setText("\nPlayer 1's turn");
			JScrollPane localJScrollPane = new JScrollPane(this.txtArea);
			this.txtArea.setEditable(false);
		//Action listners
			this.cooperate.addActionListener(this);
			this.defect.addActionListener(this);
			this.limit_edit.addActionListener(this);
			this.reset.addActionListener(this);
			
		setDefaultCloseOperation(3);
		//I don't know what they are!
			Container localContainer = getContentPane();
			localContainer.add(this.pane);this.cooperate.setMnemonic('P');
		
		//Window designing
			this.pane.add(this.cooperate);
			this.cooperate.requestFocus();
			this.pane.add(this.defect);
			this.pane.add(this.limitField);
			this.pane.add(this.limit_edit);
			this.pane.add(localJScrollPane);
		//The reset and limit buttons
			this.pane.add(this.limitField);
			this.pane.add(this.limit_edit);
			this.pane.add(this.reset);
			this.reset.setEnabled(false);
		setVisible(true);
	  }
  //The function that responds to any action!
  public void actionPerformed(ActionEvent paramActionEvent)
  {
    Object localObject = paramActionEvent.getSource();
	//If user changes limit
    if (localObject == this.limit_edit)
    {
	  int temp_limit = Integer.parseInt(this.limitField.getText());
	  if(temp_limit > p1 && temp_limit > p2) 
	  {
		  this.limit = temp_limit;
		  JOptionPane.showMessageDialog(null, "Limit changed", "Settings Changed", -1);
		  setVisible(true);
		  this.limit_edit.setEnabled(false);
	  }
	  else 
	  {
		  JOptionPane.showMessageDialog(null, "Limit must be >" + p1, "Limit untouched changed", -1);
		  setVisible(true);
	  }

    }
	//If user resets
	else if(localObject == this.reset) 
	{
		p1 = 2; p2 = 2; turn_no = 0;
		turn = true; done = false;
		this.defect.setEnabled(true);
		this.cooperate.setEnabled(true);
		this.limit_edit.setEnabled(true);
		this.reset.setEnabled(false);
		this.txtArea.append("\n---------\nGame Reset.\n---------");
		JOptionPane.showMessageDialog(null, "Game Reset", "Values set to default", -1);
		setVisible(true);
		this.answer.setText("\n\nPlayer 1's turn");
	}
	//If user is playing - there are no other buttons to click
    else
    {
      //Game started: you can't change limits now, and reset is enabled
		  this.reset.setEnabled(true);
		  this.limit_edit.setEnabled(false);
	  //Part which does the math
		  this.turn_no += 1;
		  if (localObject == this.cooperate) //Coopertates
		  {
			this.txtArea.append("\nPlayer " + (this.turn == true ? "1 " : "2 ") + "Cooperates");
			if (this.turn) {
			  this.p1 += 1;
			} else {
			  this.p2 += 1;
			}
		  }
		  else if (localObject == this.defect) //Defects
		  {
			this.txtArea.append("\nPlayer " + (this.turn == true ? "1 " : "2 ") + "Defects");
			if (this.turn) //If it's player 1
			{
			  this.p1 += 2;this.p2 -= 2;
			}
			else //If it's player 2
			{
			  this.p2 += 2;this.p1 -= 2;
			}
			this.defect.setEnabled(false);
			this.done = true;
		  }
		  if ((this.p1 == this.limit) && (this.p2 == this.limit)) {
			this.done = true;
		  }
		  this.turn = (!this.turn);
		  this.txtArea.append("\nTurn " + this.turn_no + ":Results\nPlayer1=" + this.p1 + ";\nPlayer2=" + this.p2 + "\n");
		  if (this.done == true)
		  {
			this.defect.setEnabled(false);
			this.cooperate.setEnabled(false);
			JOptionPane.showMessageDialog(null, "You can copy the log from Text Area", "Game terminated!", -1);
			setVisible(true);
			this.txtArea.append("\nGame terminated");
		  }
		  else
		  {
			this.answer.setText("\n\nPlayer " + (this.turn == true ? "1" : "2") + "'s turn");
		  }
    }
  }
  
  public static void main(String[] paramArrayOfString)
  {
    new Centipede3();
  }
}
