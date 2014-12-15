
public class MyPair implements Pair {
	private int x;
	private int y;
	
	public MyPair() {
		x = 0;
		y = 0;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public void setX(int newVal) {
		x = newVal;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public void setY(int newVal) {
		y = newVal;
	}
}
