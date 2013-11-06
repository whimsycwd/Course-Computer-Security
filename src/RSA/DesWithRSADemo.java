package RSA;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import DES.DES;

/**
 * 
 * Project1-2�����ʵ��128bit��RSA(������������Կ�Բ���),ʵ�ִ���DES�����㷨��Ҫ�õ���Կ������DES�㷨һ���γɴ���DES�����㷨��Ȼ����DES�����㷨���ܵ�������ϵ��
 * ����Project1-1 �õ���64λ����Կ= =��
 * ���������64λ�ģ���Ϊ֮ǰDES���õ�64λ�����Ҫ���128λ��ɳ��ҲҪ�䣬�Ķ�̫���ˣ�
 * lengthOfKey�������������
 * 
 * @author whimsycwd
 *
 */
public class DesWithRSADemo extends JFrame{
	
	private static int lengthOfKey = 64;
	
	private JButton jbtDecode = new JButton("Decode");
	private JButton jbtEncode = new JButton("Encode");
	
	private JButton jbtLoadSource = new JButton("Load M");
	private JButton jbtLoadTarget = new JButton("Load C");
	
	private JButton jbtNewKey = new JButton("NewKey");
	
	private JButton jbtPublic = new JButton("PublicInfo");
	
	private JPanel buttonPanel = new JPanel();
	private DES des = new DES();
	
	
	private JTextArea textSource = new JTextArea("��������������,������01��");
	private JTextArea textTarget = new JTextArea("��������������");

	private JPanel textPanel = new JPanel();
	/**
	 * RSA generate key part.
	 */
	private RSA key = new RSA(lengthOfKey);
	private BigInteger originalKey = new BigInteger(lengthOfKey/2,200,new Random());
	
	
	public static void main(String[] args) throws FileNotFoundException {

		
		DesWithRSADemo  frame = new DesWithRSADemo();
		frame.setTitle("Whimsy's DES Demo");
		frame.setSize(500,400);
		frame.setLocationRelativeTo(null);;
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setVisible(true);;
	}
	public DesWithRSADemo() {
		this.setLayout(new BorderLayout(10, 10));
		buttonPanel.setLayout(new GridLayout(0,1,5,5));
		buttonPanel.add(jbtDecode);
		buttonPanel.add(jbtEncode);
		buttonPanel.add(jbtLoadSource);
		buttonPanel.add(jbtLoadTarget);
		buttonPanel.add(jbtNewKey);
		buttonPanel.add(jbtPublic);
		
		textPanel.setLayout(new GridLayout(1,2,5,5));
		textPanel.add(new JScrollPane(textSource));
		textPanel.add(new JScrollPane(textTarget));
		textSource.setLineWrap(true);
		textTarget.setLineWrap(true);
		
		this.setLayout(new BorderLayout());
		this.add(buttonPanel,BorderLayout.EAST);
		this.add(textPanel,BorderLayout.CENTER);
		
		
		jbtPublic.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					PrintWriter out = new PrintWriter(new File("PublicInfo.txt"));
					out.println("PK:");
					out.println(key.getPK());
					out.println("npq : (i.e. p*q)");
					out.println(key.getNpq());
					
					out.println("DES key encrypted by RSA above:");
					out.println(key.outputBinary(key.encrypt(originalKey)));
					out.close();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		jbtNewKey.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				key = new RSA(lengthOfKey);
				originalKey = new BigInteger(lengthOfKey, new Random());
			}
		});
		
		jbtDecode.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String target = textTarget.getText();
				
				String key1 = key.outputBinary(originalKey);	
				textSource.setText(des.decode(target,key1));
			}
		});
		
		jbtEncode.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String source = textSource.getText();
				String key1 = key.outputBinary(originalKey);
				textTarget.setText(des.encode(source, key1));
			}
		});
		
		jbtLoadSource.addActionListener(new ActionListener() {
			
			private Scanner in;

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				fc.showOpenDialog(DesWithRSADemo.this);
				
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
				fc.showOpenDialog(DesWithRSADemo.this);
				
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
