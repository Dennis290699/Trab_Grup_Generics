package Principal;

import java.io.IOException;

import Util.Servidor_Principal;

public class Inicio {

	public static void main(String[] args) {

		try {
			Servidor_Principal.Operaciones();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}// FINAL CLASS
