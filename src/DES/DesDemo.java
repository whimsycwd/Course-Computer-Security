package DES;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class DesDemo extends JFrame{
	private JButton jbtDecode = new JButton("Decode");
	private JButton jbtEncode = new JButton("Encode");
	
	private JButton jbtLoadSource = new JButton("Load M");
	private JButton jbtLoadTarget = new JButton("Load C");
	
	
	private JPanel buttonPanel = new JPanel();
	private DES des = new DES();
	
	
	private JTextArea textSource = new JTextArea("这里是明文区域,请输入01串");
	private JTextArea textTarget = new JTextArea("这里是密文区域");
	private JTextArea textKey = new JTextArea("这里是密钥区域");
	
	private JPanel textPanel = new JPanel();
	
	public static void main(String[] args) throws FileNotFoundException {

		
		DesDemo  frame = new DesDemo();
		frame.setTitle("Whimsy's DES Demo");
		frame.setSize(500,400);
		frame.setLocationRelativeTo(null);;
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setVisible(true);;
	}
	public DesDemo() {
		this.setLayout(new BorderLayout(10, 10));
		buttonPanel.setLayout(new GridLayout(4,1,5,5));
		buttonPanel.add(jbtDecode);
		buttonPanel.add(jbtEncode);
		buttonPanel.add(jbtLoadSource);
		buttonPanel.add(jbtLoadTarget);
		
		textPanel.setLayout(new GridLayout(1,2,5,5));
		textPanel.add(new JScrollPane(textSource));
		textPanel.add(new JScrollPane(textTarget));
		textSource.setLineWrap(true);
		textTarget.setLineWrap(true);
		
		this.setLayout(new BorderLayout());
		this.add(buttonPanel,BorderLayout.EAST);
		this.add(textPanel,BorderLayout.CENTER);
		
		this.add(textKey,BorderLayout.SOUTH);
		
		jbtDecode.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String target = textTarget.getText();
				String key = textKey.getText();
				
				textSource.setText(des.decode(target,key));
			}
		});
		
		jbtEncode.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String source = textSource.getText();
				String key = textKey.getText();
				textTarget.setText(des.encode(source, key));
			}
		});
		
		jbtLoadSource.addActionListener(new ActionListener() {
			
			private Scanner in;

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.showOpenDialog(DesDemo.this);
				
				try {

					System.out.println("sucess");
					System.out.println(fc.getSelectedFile());
					in = new Scanner(fc.getSelectedFile());
					String text = "";
					while (in.hasNext()){
						text += in.next();
					}
					textSource.setText(text);

					System.out.println(text);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		jbtLoadTarget.addActionListener(new ActionListener() {
			private Scanner in;
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.showOpenDialog(DesDemo.this);
				
				try {

					System.out.println("sucess");
					System.out.println(fc.getSelectedFile());
					in = new Scanner(fc.getSelectedFile());
					String text = "";
					while (in.hasNext()){
						text += in.next();
					}
					textTarget.setText(text);

					System.out.println(text);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
	}
	
}
