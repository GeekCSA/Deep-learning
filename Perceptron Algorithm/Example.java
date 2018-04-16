import java.util.Arrays;


public class Example {

	private Letter letter = Letter.BET;
	private int[] bits;

	public Example(String s) throws Exception {	

		String[] temp = s.split(",");
		bits = new int[temp.length - 1];

		for (int i = 0; i < bits.length; i++) {
			bits[i] = Integer.parseInt(temp[i + 1]);
		}

		switch(Integer.parseInt(temp[0])) {
		case 2:
			letter=Letter.BET;
			break;
		case 3:
			letter=Letter.GIMEL;
			break;
		case 6:
			letter=Letter.VAV;
			break;
		case 7:
			letter=Letter.ZAIN;
			break;
		case 10:
			letter=Letter.YUD;
			break;
		case 20:
			letter=Letter.KAF;
			break;
		case 50:
			letter=Letter.NUN;
			break;
		default:
			throw new Exception("No correct letter");

		}
	}

	public Letter getLetter() {
		return letter;
	}

	public int[] getBits() {
		return bits;
	}

	public void setBits(int[] bits) {
		this.bits = bits;
	}

	@Override
	public String toString() {
		String s = "";
		for (int i = 0; i < bits.length; i++) {
			s += bits[i] + ",";
		}
		return s;
	}

}
