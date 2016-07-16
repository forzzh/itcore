package www.core.com;

public class Test {
	public int i=20;
	
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return this.i==((Test)obj).i;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(new Test().equals(new Test()));
	}

}
