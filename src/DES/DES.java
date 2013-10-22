package DES;

import java.lang.annotation.Target;

/**
 * 
 * DES 加密 计算机安全 课程 Project 1-1
 * 
 * dependency: DesStruct.java
 * 
 * @since 2013-10-21
 * @author whimsy
 * 
 */

public class DES {
	static int ROUND = 16;

	static int[] testSource1 = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
			1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
			1, 1, 1, 1, 1, 1, 1, 1, };
	static int[] testKey1 = { 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
			1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
			1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
			1, 1, 1, 0 };
	
	static String test1 = "01110100011010000110100101110011001000000110100101110011001000000110000100100000011101000110010101110011011101000000000000000000";
	static String key1 = "0110000101100010011000110110010001100101000000000000000000000000";

	static int[] testSource2 = { 1 };
	static int[] testKey2 = { 1 };

	public static void main(String[] args) {
		DES des = new DES();
//		int[] result = des.encode(testSource1, testKey1);
//		print(result, "encoded result:   ");
//
//		print(des.decode(result, testKey1), "decoded result:    ");
//
//		result = des.encode(testSource2, testKey2);
//		print(result, "encoded result:   ");
//
//		print(des.decode(result, testKey2), "decoded result:    ");
		
		String result = des.encode(test1, key1);
		System.out.println(result);
		System.out.println(des.decode(result, key1));
	
	}

	private static void print(int[] result, String msg) {
		
		for (int i = 0; i < result.length; ++i) {
//			if (i % 8 == 0)
//				System.out.print(" ");
			System.out.print(result[i]);
		}
		System.out.println("   "+msg);

	}

	public String encode(String source, String key) {

		int[] intSource = new int[source.length()];
		int[] intKey = new int[key.length()];
		for (int i = 0; i < source.length(); ++i) {
			intSource[i] = source.charAt(i) - '0';
		}
		for (int i = 0; i < key.length(); ++i) {
			intKey[i] = key.charAt(i) - '0';
		}

		int[] result = this.encode(intSource, intKey);
		String ret = "";
		for (int i = 0; i < result.length; ++i) {
			ret += result[i];
		}
		System.out.println(source);
		System.out.println(key);
		return ret;
	}

	/**
	 * a proxy
	 * 
	 * @param text
	 * @return
	 */
	public String decode(String source, String key) {
		int[] intSource = new int[source.length()];
		int[] intKey = new int[key.length()];
		for (int i = 0; i < source.length(); ++i) {
			intSource[i] = source.charAt(i) - '0';
		}
		for (int i = 0; i < key.length(); ++i) {
			intKey[i] = key.charAt(i) - '0';
		}

		int[] result = this.decode(intSource, intKey);
		String ret = "";
		for (int i = 0; i < result.length; ++i) {
			ret += result[i];
		}
		return ret;
	}

	/**
	 * 
	 * 解密,输入密文和密钥 输出明文
	 * 
	 * @param source
	 * @param key
	 * @return
	 */
	public int[] decode(int[] source, int[] key) {
		 // for CBC
		 int [] origin = new int[64];
		//
		key = validateKey(key);

		// 将原文扩充成64的倍数
		int[] nSource = new int[roundUp(source.length)];
		for (int i = 0; i < source.length; ++i) {
			nSource[i] = source[i];
		}
		source = nSource;

		for (int k = 0; k < source.length; k += 64) {
			int[] currentCode = new int[64];
			for (int j = k; j < k + 64; ++j) {
				currentCode[j - k] = source[j];
			}
			// for CBC

			 currentCode = xor(currentCode, origin);
			currentCode = IP(currentCode);

			int[] currentKey = new int[64];
			for (int j = 0; j < key.length; ++j) {
				currentKey[j] = key[j];
			}
			currentKey = IPC(key);

			int[] leftCode = new int[32];
			int[] rightCode = new int[32];

			for (int i = 0; i < 32; ++i) {
				leftCode[i] = currentCode[i];
				rightCode[i] = currentCode[i + 32];
			}

			for (int i = 0; i < ROUND; ++i) {

				int[] nLeftCode = new int[32];
				for (int j = 0; j < 32; ++j) {
					nLeftCode[j] = rightCode[j];
				}
				// left rotate sigma(d,15-i) this is the only different between
				// decode and encode!
				int[] K = PC(rotateLeft(currentKey, 15 - i));

				int[] t = xor(extend32to48(rightCode), K);

				int[] nRightCode = xor(leftCode, P(subBox(t)));

				rightCode = nRightCode;
				leftCode = nLeftCode;

				int[] temp = new int[64];
				for (int ii = 0; ii < 32; ++ii) {
					temp[ii] = leftCode[ii];
					temp[ii + 32] = rightCode[ii];
				}
//				 print(temp,"Round   " + i);

			}
			for (int i = 0; i < 32; ++i) {
				currentCode[i] = rightCode[i];
				currentCode[i + 32] = leftCode[i];
			}

			currentCode = IP_1(currentCode);
		

			for (int j = k; j < k + 64; ++j) {
				source[j] = currentCode[j - k];
			}

		}
		return source;

	}

	/**
	 * 读入明文,以及密钥 输出密文
	 * 
	 * @param source
	 * @param key
	 * @return
	 */
	public int[] encode(int[] source, int[] key) {
		// for CBC
		 int [] origin = new int[64];

		key = validateKey(key);

		// 将原文扩充成64的倍数
		int[] nSource = new int[roundUp(source.length)];
		for (int i = 0; i < source.length; ++i) {
			nSource[i] = source[i];
		}
		source = nSource;

		for (int k = 0; k < source.length; k += 64) {
			int[] currentCode = new int[64];
			for (int j = k; j < k + 64; ++j) {
				currentCode[j - k] = source[j];
			}
			// for CBC

			 currentCode = xor(currentCode, origin);

			currentCode = IP(currentCode);

			int[] currentKey = new int[64];
			for (int j = 0; j < key.length; ++j) {
				currentKey[j] = key[j];
			}
			currentKey = IPC(key);

			int[] leftCode = new int[32];
			int[] rightCode = new int[32];

			for (int i = 0; i < 32; ++i) {
				leftCode[i] = currentCode[i];
				rightCode[i] = currentCode[i + 32];
			}

			for (int i = 0; i < ROUND; ++i) {

				int[] nLeftCode = new int[32];
				for (int j = 0; j < 32; ++j) {
					nLeftCode[j] = rightCode[j];
				}
				// left rotate sigma(d,i)
				int[] K = PC(rotateLeft(currentKey, i));
//				print(K,"Key");

				int[] t = xor(extend32to48(rightCode), K);
//				print(extend32to48(rightCode),"extended");
//				print(t,"xor after");
				int[] nRightCode = xor(leftCode, P(subBox(t)));
//				print (subBox(t),"subBox");
				rightCode = nRightCode;
				leftCode = nLeftCode;

//				int[] temp = new int[64];
//				for (int ii = 0; ii < 32; ++ii) {
//					temp[ii] = leftCode[ii];
//					temp[ii + 32] = rightCode[ii];
//				}
//				 print(temp,"Round   " + i);

			}
			for (int i = 0; i < 32; ++i) {
				currentCode[i] = rightCode[i];
				currentCode[i + 32] = leftCode[i];
			}
			

			currentCode = IP_1(currentCode);

			for (int j = k; j < k + 64; ++j) {
				source[j] = currentCode[j - k];
			}

		}
		return source;

	}

	/**
	 * 
	 * 输入key,返回64位key.通过补全或者截断
	 * 
	 * @param key
	 * @return
	 */
	private int[] validateKey(int[] key) {
		// 将密钥 扩充成64位 ,或者 截掉末尾几位
		int[] nkeys = new int[64];
		for (int i = 0; i < Math.min(key.length, 64); ++i) {
			nkeys[i] = key[i];
		}
		key = nkeys;
		return key;
	}

	/**
	 * 沙盒步骤之后的P变换
	 * 
	 * @param subBox
	 * @return
	 */
	private int[] P(int[] a) {
		int[] ret = new int[32];
		for (int i = 0; i < 32; ++i) {
			ret[i] = a[DesStruct.P[i] - 1];
		}
		return ret;
	}

	/**
	 * 沙盒,读入48位,没6位转化为4位,输出32位
	 * 第0位和第5位作为行,1至4位作为列
	 * 
	 * @param t
	 * @return
	 */
	private int[] subBox(int[] t) {
		int[] ret = new int[32];
		int cnt = 0;
		for (int i = 0; i < 48; i += 6) {
			int row = 0;
			row = t[i]*2 + t[i+5];
			int col = 0;
			for (int k = 1; k < 5; ++k) {
				col = col * 2 + t[i + k];
			}
			int result = DesStruct.sBox[cnt][row][col];
	
	
			for (int k = 3; k >= 0; --k) {
				ret[cnt * 4 + k] = result % 2;
				result /= 2;
			}
			++cnt;
		}
		return ret;
	}

	/**
	 * 读入a和b数组,输出两者异或的结果
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	private int[] xor(int[] a, int[] b) {
		int[] ret = new int[a.length];
		for (int i = 0; i < a.length; ++i) {
			ret[i] = a[i] ^ b[i];
		}
		return ret;
	}

	/**
	 * 
	 * 把32位拓展成64位
	 * 
	 * @param rightCode
	 * @return
	 */
	private int[] extend32to48(int[] rightCode) {

		int[] ret = new int[48];

		for (int i = 0; i < 48; ++i) {
			ret[i] = rightCode[DesStruct.e[i] - 1];
		}

		return ret;
	}

	/**
	 * 
	 * 密钥需要在每轮轮函数的过程中左移动
	 * 
	 * @param currentKey
	 * @param i
	 * @return
	 */
	private int[] rotateLeft(int[] currentKey, int t) {

		int i = 0;
		for (int j = 0; j <= t; ++j) {
			i += DesStruct.d[j];
		}
//		 print(currentKey, "before  rotateLeft" + i);

		int[] ret = new int[56];
		for (int k = i; k < 28; ++k) {
			ret[k - i] = currentKey[k];
		}
		for (int k = 28 - i; k < 28; ++k) {
			ret[k] = currentKey[k - (28 - i)];
		}
		for (int k = 28 + i; k < 56; ++k) {
			ret[k - i] = currentKey[k];
		}
		for (int k = 56 - i; k < 56; ++k) {
			ret[k] = currentKey[k - (28 - i)];   // need k-(28-i) not k-(28-i)
		}

//		 print(ret,"after rotateRight");
		return ret;
	}

	/**
	 * 初始化置换
	 * 
	 * @param code
	 * @return
	 */
	private int[] IP(int[] code) {
		int[] ret = new int[64];
		for (int i = 0; i < DesStruct.IP.length; ++i) {
			ret[i] = code[DesStruct.IP[i] - 1];
		}
		return ret;
	}

	/**
	 * 输出前反置换
	 * 
	 * @param code
	 * @return
	 */
	private int[] IP_1(int[] code) {
		int[] ret = new int[64];
		for (int i = 0; i < DesStruct.IP_1.length; ++i) {
			ret[i] = code[DesStruct.IP_1[i] - 1];
		}
		return ret;
	}

	/**
	 * 输入密钥,生成子密钥 64位输入56位输出
	 * 
	 * @param key
	 * @return
	 */
	private int[] IPC(int[] key) {
		int[] ret = new int[56];
		for (int i = 0; i < DesStruct.IPC.length; ++i) {
			ret[i] = key[DesStruct.IPC[i] - 1];
		}
		return ret;
	}

	/**
	 * 输入56位左旋转之后的密钥,输出48位
	 * 
	 * @param key
	 * @return
	 */
	private int[] PC(int[] key) {
		int[] ret = new int[48];
		for (int i = 0; i < DesStruct.PC.length; ++i) {
			ret[i] = key[DesStruct.PC[i] - 1];
		}
		return ret;

	}

	/**
	 * 找到比x大的最小的64的倍数
	 * 
	 * @param x
	 * @return
	 */
	private int roundUp(int x) {
		x = (x - 1) / 64 * 64 + 64;
		return x;
	}

}
