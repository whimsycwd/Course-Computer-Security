package ECC;

public class Oval {
	static int p = 23;
	static int cnt = 0;
	static int a = 1;
	static int b = 1;

	public static void main(String[] args) {
		
//		order(5,4);
		order(17,3);
		
		
		for (int i = 0; i < p; ++i) {
			for (int j = 0; j < p; ++j) {
				if ((j * j % p) == (i * i * i + a*i  + b) % p) {
					System.out.println(i + " " + j + " " + order(i, j));
//					if (order(i,j) == 36) System.out.println("mark");
					++cnt;
//					return;
				}
			}
		}
//		System.out.println(cnt);*/
	}

	private static int order(int x1, int y1) {
		int x2 = x1, y2 = y1;
		int xx = 0, yy = 0;
		for (int i = 2; i < 30; ++i) {
			if (x2 == x1 && y2 != y1)
				return i;
			if (x1 == x2) {
				int k = MOD((3 * x1 * x1 + 1) * inverse(2 * y1));
				xx = MOD(k * k - 2 * x1);
				yy = MOD(-y1 + k * (x1 - xx));
			} else {
				int k = MOD((y1 - y2) * (inverse(x1 - x2)));
				xx = MOD(k * k - x1 - x2);
				yy = MOD(-y1 + k * (x1 - xx));
			}
			x2 = xx;
			y2 = yy;
//			System.out.println(xx+" : "+yy + (MOD(xx*xx*xx+a*xx+b) == MOD(yy*yy) ? "true" : "false"));
		}

		return 0;
	}

	private static int MOD(int i) {
		return (i % p + p) % p;

	}

	static int inverse(int x) {
		x = MOD(x);
		for (int i = 1; i < p; ++i) {
			if (x * i % p == 1) {
				return i;
			}
		}
		return 0;
	}
}
