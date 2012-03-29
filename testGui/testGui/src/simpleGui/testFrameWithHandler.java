package simpleGui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class testFrameWithHandler {
	private String sdelt=" : ";
	//top
	private String[] sCoffeeSize = {"small  $0.99","medium $ 1.39","large  $1.99"};
	private double[] dpriceTag = {0.99,1.39,1.99};
	private double totalPrice = 0.0;
	private JComboBox cbCoffeeSize;
	//center
	private String[] radioLabels={"Regular","Decaf"};
	private JRadioButton radioRegular = new JRadioButton(radioLabels[0]);
	private JRadioButton radioDecaf= new JRadioButton(radioLabels[1]);
	ButtonGroup btngrp = new ButtonGroup();
	private String[] cbxLabels= {"Cream","Sugar"};
	private JCheckBox cbxCream = new JCheckBox(cbxLabels[0]);
	private JCheckBox cbxSugar = new JCheckBox(cbxLabels[1]);
	//south
	private JButton buttonPay,buttonOrder;
	private JTextArea outputArea = new JTextArea("",7,1);
	public  testFrameWithHandler(){
		//prepare frame
		JFrame mainFrame = new JFrame("Java cafe");
		
		Container mainFrameContainer = mainFrame.getContentPane();
		mainFrameContainer.setLayout(new BorderLayout(5,5));
		
		//layout	north panel	
		JPanel northPanel = new JPanel();
		JLabel lbCoffeeSize = new JLabel("Coffee size:");
		
		cbCoffeeSize = new JComboBox(sCoffeeSize);
		cbCoffeeSize.setSelectedIndex(0);
		cbCoffeeSize.setEditable(false);
		//“small  $0.99”, “medium $ 1.39” and “large  $1.99.” 
		//cbCoffeeSize.setSize(240, 1);
		northPanel.setLayout(new BorderLayout(20,0));
		northPanel.add(lbCoffeeSize,BorderLayout.WEST);
		northPanel.add(new JScrollPane(cbCoffeeSize));//,BorderLayout.EAST);
		mainFrameContainer.add(northPanel,BorderLayout.NORTH);
		
		//layout center components
		btngrp.add(radioRegular);
		btngrp.add(radioDecaf);
		JPanel centerPanel = new JPanel();
		JPanel ctNorthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,60,3));
		JPanel ctSouthPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,60,3));
		centerPanel.setLayout(new BorderLayout());
		ctNorthPanel.add(radioRegular);
		ctNorthPanel.add(radioDecaf);
		ctSouthPanel.add(cbxCream);
		ctSouthPanel.add(cbxSugar);
		centerPanel.add(ctNorthPanel,BorderLayout.NORTH);
		centerPanel.add(ctSouthPanel,BorderLayout.CENTER);
		mainFrameContainer.add(centerPanel,BorderLayout.CENTER);
		
		//adding components
		buttonPay = new JButton("Pay");
		buttonPay.setSize(175, 20);
		buttonOrder = new JButton("Order");
		buttonOrder.setSize(175, 20);
		outputArea.setEditable(false);//readonly
		
		//layout    south panel
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());
		JPanel innerPanel = new JPanel();
		innerPanel.setLayout(new FlowLayout(FlowLayout.LEFT,60,3));
		innerPanel.add(buttonPay,BorderLayout.WEST);
		innerPanel.add(buttonOrder,BorderLayout.EAST);
		southPanel.add(innerPanel,BorderLayout.NORTH);
		southPanel.add(new JScrollPane(outputArea),BorderLayout.SOUTH);
		mainFrameContainer.add(southPanel,BorderLayout.SOUTH);
		
		//adding even handler to components.
		buttonEvenHandler btEvenHandler= new buttonEvenHandler();
		buttonPay.addActionListener(btEvenHandler);
		buttonOrder.addActionListener(btEvenHandler);
		
		radioEvenHandler radioHdlr = new radioEvenHandler();	
		radioRegular.addItemListener(radioHdlr);
		radioDecaf.addItemListener(radioHdlr);
		checkEvenHandler checkHdler = new checkEvenHandler();
		cbxCream.addItemListener(checkHdler);
		cbxSugar.addItemListener(checkHdler);
		cbCoffeeSize.addItemListener(new cxEventHandler());
		
		mainFrame.setSize(350,300);
		mainFrame.setVisible(true);
		
		mainFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		//button even handler class
		
	}
	//even handler for buttons
	class buttonEvenHandler implements ActionListener{
		public void actionPerformed(ActionEvent aet){
			if(aet.getSource()==buttonPay){
				//totalPrice
				//String stemp = "Payed.\n";
				//outputArea.append(stemp);
				outputArea.append("************************************************\n");
				totalPrice = 0.00;
			}
			if(aet.getSource()==buttonOrder){
				String s=(orderState[1]!=-1?(radioLabels[orderState[1]]+" "):"")
						+(orderState[2]==1?(cbxLabels[0]+" "):"")
						+(orderState[3]==1?(cbxLabels[1]+" "):"")
						+":"
						+sCoffeeSize[orderState[0]]
						+"\n";
				outputArea.append(s);
				totalPrice+=dpriceTag[orderState[0]];
				s = s.valueOf(totalPrice);
				String st = "                                         total = $";
				outputArea.append(st+s+"\n");
				//outputArea.append(totalPrice);
				
			}
		}
	}
	private int[] orderState={0,-1,0,0};//orderState[0]: combox
										//orderState[1]: radio
										//orderState[2]: checkbox cbxCream
										//orderState[3]: checkbox cbxSugar
	
	//even handler for radio button
	class radioEvenHandler implements ItemListener{
		public void itemStateChanged(ItemEvent itmAct){
			if(itmAct.getStateChange()==ItemEvent.SELECTED){
				if(itmAct.getSource()==radioRegular) orderState[1]=0;
				else orderState[1]=1;
			}
			
		}
	}
	//even handle for checkbox button
	class checkEvenHandler implements ItemListener{
		public void itemStateChanged(ItemEvent itmAct){
			if(itmAct.getStateChange()==ItemEvent.SELECTED){
				if(itmAct.getSource()==cbxCream) orderState[2]=1;
				if(itmAct.getSource()==cbxSugar) orderState[3]=1;
			}
			if(itmAct.getStateChange()==ItemEvent.DESELECTED){
				if(itmAct.getSource()==cbxCream) orderState[2]=0;
				if(itmAct.getSource()==cbxSugar) orderState[3]=0;
			}
		}
	}
	//even handle for combo box
	class cxEventHandler implements ItemListener{
		public void itemStateChanged(ItemEvent ite){
			if(ite.getStateChange()==ItemEvent.SELECTED){
				orderState[0] = cbCoffeeSize.getSelectedIndex();
			}
		}
	}
	public static void main(String[] arg) {

		testFrameWithHandler ts= 
				new testFrameWithHandler();
	   }

}
