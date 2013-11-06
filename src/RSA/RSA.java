package RSA;


import java.io.ObjectInputStream.GetField;
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

/**
 * �ο��� ���������� ��һƪ���ģ�
 * [��Java����ʵ��RSA��Կ�����㷨]
 * 
 * Step:
 * 1.����������p,q
 * 2.phi = (p-1)*(q-1)
 * 3.PK = 65537 , ����˵������ֻ������1����ӿ����㣿�������Ҫ������Ĳ������������ɣ�
 * �������õ�BigInteger�Ĳ��������ķ�����Ӧ������ν�ɡ�
 * 4.SK = inverse(PK,phi) 
 * 
 * 
 * 
 * @since 2013-11-06
 * 
 * @author  whimsycwd
 *
 */

public class RSA {
	//�������������
	private int N;
	private final static Random rand = new Random();
	//��Կ
	private BigInteger SK;
	//��Կ
	private BigInteger PK;
	private BigInteger npq;

	public RSA(int N) {
		this.N = N;
		BigInteger p = new BigInteger(N / 2, 200, rand);
		BigInteger q = new BigInteger(N / 2, 200, rand);
		BigInteger phi = p.subtract(BigInteger.ONE).multiply(
				q.subtract(BigInteger.ONE));
		
		npq = p.multiply(q);
		PK = new BigInteger("65537");
		SK = PK.modInverse(phi);

	}
	BigInteger encrypt(BigInteger message){
		return message.modPow(PK,npq);
	}
	BigInteger decrypt(BigInteger encrypted){
		return encrypted.modPow(SK, npq);
	}
	public int getN(){
		return N;
	}
	public BigInteger getPK(){
		return PK;
	}
	public BigInteger getNpq(){
		return npq;
	}
	public static void main(String[] args) {

		int n;
		Scanner in = new Scanner(System.in);
		
		n = in.nextInt();
		RSA key = new RSA(n);
		BigInteger message = new BigInteger(n,rand);
		BigInteger encrypt = key.encrypt(message);
		BigInteger decrypt = key.decrypt(encrypt);
		System.out.println("Message:  "+key.outputBinary(message));
		System.out.println("encrypt:  "+key.outputBinary(encrypt));
		System.out.println("decrypt:  "+key.outputBinary(decrypt));
	}
	/**
	 * ���������Ϣ��λ������
	 * 
	 * @param message
	 * @return
	 */
	public String outputBinary(BigInteger message) {
		String ret = "";
		
		while (message.compareTo(BigInteger.ZERO)>0){
			ret = message.mod(new BigInteger("2")) + ret;
			message = message.divide(new BigInteger("2"));
//			System.out.println(message);
		}
		while (ret.length()< N){
			ret = "0"+ret;
		}
		return ret;
	}
	

}
