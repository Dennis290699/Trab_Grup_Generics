package Diseño;

public class Menus {

	public static void choose() {
		System.out.println("\n" + "Elija una opcion: " 
								+ "\n[1] Insertar Productos" 
								+ "\n[2] Operaciones estadisticas"
								+ "\n[3] Operaciones de busqueda" 
								+ "\n[4] Operaciones de ordenamiento"
								+ "\n[5] Operaciones intermedias" 
								+ "\n[6] Operaciones de reduccion"
								+ "\n[7] Operaciones de agrupamiento" 
								+ "\n[8] Operaciones de particionamiento" 
								+ "\n[9] Productos"
								+ "\n[10] Reduccion de collectors" 
								+ "\n[11] Salir");
	}
	
	public static void esperar(){
        System.out.println("...Presione la tecla Enter para continuar...\n");
        try{System.in.read();}
                catch(Exception e){};
    }

     public static void repeat(){
    	Menus.esperar();
    	Menus.choose();
    }
	
}// FINAL CLASS
