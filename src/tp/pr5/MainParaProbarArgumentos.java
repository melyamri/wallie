package tp.pr5;


public class MainParaProbarArgumentos {

	/**
	 * @param args
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {

		String[] swing = {"-i","swing","-m","madrid.txt"} ;
		String[] console = {"-m","madrid.txt","--interface","console"} ;
		String[] both = {"-i","both","-m","madrid.txt"} ;
		Main.main(both);
		
	}

}
