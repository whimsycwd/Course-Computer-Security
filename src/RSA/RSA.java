package RSA;


import java.io.ObjectInputStream.GetField;
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

/**
 * 参考了 “付立冬” 的一篇短文？
 * [用Java语言实现RSA公钥密码算法]
 * 
 * Step:
 * 1.产生大素数p,q
 * 2.phi = (p-1)*(q-1)
 * 3.PK = 65537 , 书里说二进制只有两个1，会加快运算？不过这个要用书里的测试素数方法吧？
 * 我这里用的BigInteger的产生素数的方法。应该无所谓吧。
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
	//产生随机数的类
	private int N;
	private final static Random rand = new Random();
	//密钥
	private BigInteger SK;
	//公钥
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
	 * 二进输出信息，位数对其
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
