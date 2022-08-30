package Util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import Diseño.Colors;
import Diseño.Menus;

public class Servidor_Principal {

	static Scanner obj;

//	LEER EL ARCHIVO CSV
	public static ArrayList<Producto> LeerArrayList() throws IOException {

		BufferedReader br = null;
		ArrayList<Producto> Datos = new ArrayList<Producto>();
		try {
			br = new BufferedReader(new FileReader("Data/Productos.csv"));
			String line = br.readLine();
			while (null != line) {
				String[] fields = line.split(";");
				String Supermercado = fields[0].toString();
				double Precio = Double.parseDouble(fields[3]);
				double PrecioR = Double.parseDouble(fields[4]);
				Producto P = new Producto(Supermercado, fields[1], fields[2], Precio, PrecioR, fields[5]);
				Datos.add(P);
				line = br.readLine();
			}
		} catch (Exception e) {
			System.out.println(" Error: " + e.getMessage());
		} finally {
			if (null != br) {
				br.close();
			}
		}
		return Datos;
	}

//	PARAMETROS DE CALIFICACION
	public static void Operaciones() throws IOException {
		
		obj = new Scanner(System.in);
		DecimalFormat df = new DecimalFormat("#.00");
		int opcion = 0;

		ArrayList<Producto> MyArreglo = LeerArrayList();
		
		Comparator<Producto> Categoria = Comparator.comparing(Producto::getCategoria);
		Comparator<Producto> Nombre = Comparator.comparing(Producto::getNombre);
		Comparator<Producto> Super = Comparator.comparing(Producto::getSupermercados);
		Comparator<Producto> Precio = Comparator.comparingDouble(Producto::getPrecio);
		Comparator<Producto> PrecioR = Comparator.comparingDouble(Producto::getPrecioR);

		System.out.println(" ******* GRUPO 3 ******* ");
		System.out.println();
		System.out.println("Cargando Productos.......");
		System.out.println("Productos Cargados con Exito: " + MyArreglo.size());

		Menus.choose();

		while (opcion != 11) {
			
			opcion = obj.nextInt();
			
//			CANTIDAD DE REGISTROS
//			---------------------------------------------------------------------------------------
			double TotalProductos = MyArreglo.stream().map(a -> a.getPrecio()).count();
			
//			SUMA PRECIO
//			---------------------------------------------------------------------------------------
			double precioTotal = MyArreglo.stream().mapToDouble(a -> a.getPrecio()).sum();

//			BUSQUEDA
//			---------------------------------------------------------------------------------------
			Optional<Producto> IntProducto = MyArreglo.stream().filter(x -> x.getPrecio() == 6).findAny();

			Optional<Producto> Producto1 = MyArreglo.stream().filter(a -> a.getUnidadR().equals("cl"))
					.sorted(Precio.reversed()).findAny();

			Optional<Producto> Producto2 = MyArreglo.stream().filter(a -> a.getPrecioR() < 4).sorted(PrecioR)
					.findFirst();

			Optional<Producto> StringProducto = MyArreglo.stream()
					.filter(x -> x.getSupermercados().equals("carrefour-es")).sorted(Categoria.reversed()).findFirst();

//			OPERACIONES
//			---------------------------------------------------------------------------------------
			switch (opcion) {
			
			case 1:
				System.out.println("\n" + "-------INSERTAR PRODUCTO--------");
				System.out.println("Ingrese nombre del supermercado: ");
				String S = obj.next();
				System.out.println("Ingrese la caracteristica: ");
				String C = obj.next();
				System.out.println("Ingrese el nombre: ");
				String N = obj.next();
				System.out.println("Ingrese el precio: ");
				double P = obj.nextDouble();
				System.out.println("Ingrese el precio de referencia: ");
				double PR = obj.nextDouble();
				System.out.println("Ingrese la unidad de referencia: ");
				String U = obj.next();
				Producto Prod = new Producto(S, C, N, P, PR, U);
				MyArreglo.add(Prod);
				System.out.println("Productos: " + MyArreglo.size());
				
				Menus.repeat();
				break;

			case 2:
				System.out.println("\n" + "-------OPERACIONES FINALES--------");
//				CANTIDAD DE REGISTROS
//				-------------------------------------------------------------------------------
				System.out.println("Total de productos: " + df.format(TotalProductos));

//				SUMA PRECIO
//				---------------------------------------------------------------------------------------
				System.out.println("Suma precio: " + df.format(precioTotal));
				
//				PROMEDIO
//				---------------------------------------------------------------------------------------
				double promedio = MyArreglo.stream().collect(Collectors.averagingDouble(a -> a.getPrecio()));
				System.out.println("Promedio: " + df.format(promedio));

//				PRECIO MINIMO
//				---------------------------------------------------------------------------------------
				System.out.println("Minimo");
				Optional<Producto> minimo = MyArreglo.stream().min(Precio);
				if (minimo.isPresent()) {
					Producto Min = minimo.get();
					System.out.println("El producto con menor valor es: " + Min.getNombre() + " con un valor de: " + Min.getPrecio());

//				PRECIO MAXIMO
//				----------------------------------------------------------------------------------------
				System.out.println("Maximo");
				Optional<Producto> maximo = MyArreglo.stream().max(Precio);
				if (maximo.isPresent()) {
					Producto max = maximo.get();
					System.out.println("El producto con mayor valor es: " + max.getNombre() + " con un valor de: " + max.getPrecio());
					} else
						System.out.println("no existe ");
				}

				System.out.println("---------------------------------------------------------------------------------");
				DoubleSummaryStatistics datosTotal = MyArreglo.stream().collect(Collectors.summarizingDouble(Producto::getPrecio));
				System.out.println("Datos: " + datosTotal);

				Menus.repeat();
				break;

			case 3:
				System.out.println("\n" + "-------OPERACION DE BUSQUEDA--------");
				if (IntProducto.isPresent()) {
					Producto PI = IntProducto.get();
					System.out.println("El producto con ese precio de referencia es: \n[Nombre -> " + PI.getNombre()
							+ "] \n[Unidad -> " + PI.getUnidadR() + "] \n[Supermercado -> " + PI.getSupermercados()
							+ "]");
				} else {
					System.out.println("No existen productos con ese precio de referencia.");
				}

				if (Producto1.isPresent()) {
					Producto p = Producto1.get();
					System.out.println("\n El producto con la condicion  indicada es: \n[Nombre -> " + p.getNombre()
							+ "] \n[Categoria -> " + p.getCategoria() + "] \n[Precio -> " + p.getPrecio() + "]");
				} else {
					System.out.println("No existen prooductos con esa condicion. ");
				}

				if (Producto1.isPresent()) {
					Producto p = Producto2.get();
					System.out.println("\nEl producto con la condicion  indicada es: \n[Nombre -> " + p.getNombre()
							+ "] \n[Categoria -> " + p.getCategoria() + "] \n[Unidad -> " + p.getUnidadR() + "]");
				} else {
					System.out.println("No existen prooductos con esa condicion. ");
				}

				if (StringProducto.isPresent()) {
					Producto PS = StringProducto.get();
					System.out.println("\nEl producto con el supermercado de referencia es: \n[Nombre -> "
							+ PS.getNombre() + "] \n[Precio -> " + PS.getPrecio() + "]");
				} else {
					System.out.println("No existen productos con el supermercado de referencia.");
				}

				Menus.repeat();
				break;

			case 4:
				System.out.println("\n" + "-------OPERACION DE ORDENAMIENTO--------");
				System.out.println("\nOrdenamiento Nombre y Precio");
				MyArreglo.stream().sorted(Super.thenComparing(Precio.reversed()))
						.map(x -> x.getSupermercados() + "  " + x.getNombre() + " - $" + x.getPrecio()).limit(500)
						.forEach(System.out::println);

				System.out.println("\nOrdenamiento categoria y supermercados");
				MyArreglo.stream().sorted(Super.thenComparing(Categoria))
						.map(x -> x.getCategoria() + " - " + x.getSupermercados()).limit(500)
						.forEach(System.out::println);

				System.out.println("\nOrdenamiento por Nombres de supermercados");
				Collection<String> nom = MyArreglo.stream().map(x -> x.getSupermercados())
						.collect(Collectors.toCollection(HashSet::new));

				nom.stream().sorted(new Comparator<String>() {
					@Override
					public int compare(String a, String b) {
						return a.toString().compareTo(b.toString());
					}
				}).forEach(System.out::println);

				System.out.println("\nProducto con el precio Mayor ");
				Optional<Producto> costo = MyArreglo.stream().max(Precio);

				if (costo.isPresent()) {
					Producto p0 = costo.get();
					System.out.println("Supermercado: " + p0.getSupermercados() + " Nombre producto: " + p0.getNombre()
							+ " Precio: " + p0.getPrecio());
				}
				System.out.println("\nProducto con el precio Menor ");
				Optional<Producto> costo1 = MyArreglo.stream().min(Precio);

				if (costo1.isPresent()) {
					Producto p0 = costo1.get();
					System.out.println("Supermercado: " + p0.getSupermercados() + " Nombre producto: " + p0.getNombre()
							+ " Precio: " + p0.getPrecio());
				}

				Menus.repeat();
				break;

			case 5:
				System.out.println("\n" + "-------OPERACION DE NTERMEDIAS--------");
				System.out.println("Los primeros 20 productos ordenados: ");
				MyArreglo.stream().sorted(Nombre)
						.map(x -> "Nombre: " + x.getNombre() + " -------> Precio " + x.getPrecio()).limit(20)
						.forEach(System.out::println);

				System.out.println("\n  Los productos ECONOMICOS!! $.$: ");
				MyArreglo.stream().filter(a -> a.getPrecio() <= 1).sorted(Nombre)
						.map(x -> "Nombre: " + x.getNombre() + "  -------> Precio  " + x.getPrecio()).limit(20)
						.forEach(System.out::println);

				System.out.println("\n Cuantos productos hay en el Super  'Mercadona-es' con valor mayor a 30$ ");
				MyArreglo.stream().filter(n -> n.Supermercados.compareTo("mercadona-es") == 0 && n.getPrecio() > 30)
						.forEach(System.out::println);

				System.out.println("\nEliminar los 2489 primeros productos");
				MyArreglo.stream().sorted(Nombre).skip(2489)
						.map(x -> "Nombre: " + x.getNombre() + "  -------> Precio  " + x.getPrecio())
						.forEach(System.out::println);

				System.out.println("\nBebidas en 'Mercadona-es' ");
				MyArreglo.stream().filter(n -> n.Categoria.compareTo("bebidas") == 0 && n.Precio > 5)
						.map(x -> "Nombre: " + x.getCategoria() + "  -------> Precio  " + x.getPrecio())
						.forEach(System.out::println);

				Menus.repeat();
				break;

			case 6:
				System.out.println("\n" + "-------OPERACION DE REDUCCION--------");
				Optional<Double> sum = MyArreglo.stream().map(Producto::getPrecio).reduce(Double::sum);
				System.out.println("La suma total de los Precios usando el metodo Reduce es: " + df.format(sum.get()));

				Optional<Double> sum1 = MyArreglo.stream().map(Producto::getPrecioR).reduce(Double::sum);

				System.out.println("La suma total de los Precios de Referencia usando el metodo Reduce es: "
						+ df.format(sum1.get()));

				Optional<Double> max = MyArreglo.stream().map(Producto::getPrecio).reduce(Double::max);
				System.out.println("El maximo de los Precios usando el metodo Reduce es: " + df.format(max.get()));

				Optional<Double> min = MyArreglo.stream().map(Producto::getPrecio).reduce(Double::min);
				System.out.println("El minimo de los Precios usando el metodo Reduce es: " + df.format(min.get()));

				Optional<Double> max1 = MyArreglo.stream().map(Producto::getPrecioR).reduce(Double::max);
				System.out.println(
						"El maximo de los Precios de Referencia usando el metodo Reduce es: " + df.format(max1.get()));

				Optional<Double> min1 = MyArreglo.stream().map(Producto::getPrecioR).reduce(Double::min);
				System.out.println(
						"El minimo de los Precios de Referencia usando el metodo Reduce es: " + df.format(min1.get()));

				Menus.repeat();
				break;

			case 7:
				System.out.println("\n" + "-------OPERACION DE AGRUPAMIENTO--------");
				
				System.out.println("Agrupar  supermercado ");
				Map<String, Long> agrupar = MyArreglo.stream()
						.collect(Collectors.groupingBy(Producto::getSupermercados, Collectors.counting()));
				System.out.println(agrupar);
				System.out.println();
				
				System.out.println("Agrupar  precio de referencia de cada supermercado ");
				Map<String, Double> agrupar2 = MyArreglo.stream()
						.collect(Collectors.groupingBy(Producto::getSupermercados, Collectors.summingDouble(Producto::getPrecioR)));
				System.out.println(agrupar2);
				System.out.println();
				
				System.out.println("Agrupar Unidad de Referencia ");
				Map<String, Long> agrupar3 = MyArreglo.stream()
						.collect(Collectors.groupingBy(Producto::getUnidadR, Collectors.counting()));
				System.out.println(agrupar3);
				System.out.println();
				
				System.out.println("Agrupar Precio Referencia ");
				Map<Double, Long> agrupar4 = MyArreglo.stream()
						.collect(Collectors.groupingBy(Producto::getPrecioR, Collectors.counting()));
				System.out.println(agrupar4);
				System.out.println();
				
				System.out.println("Agrupar por Precio ");
				Map<Double, Long> agrupar5 = MyArreglo.stream()
						.collect(Collectors.groupingBy(Producto::getPrecio, Collectors.counting()));
				System.out.println(agrupar5);

				Menus.repeat();
				break;

			case 8:
				System.out.println("\n" + "-------OPERACION DE PARTICIONAMIENTO--------");
				Map<Boolean, Optional<Producto>> PartProducto = MyArreglo.stream()
						.collect(Collectors.partitioningBy(x -> x.Precio > 10, Collectors.maxBy(Precio)));
				
				Map<Boolean, Optional<Producto>> PartProducto1 = MyArreglo.stream()
						.collect(Collectors.partitioningBy(x -> x.PrecioR > 10, Collectors.maxBy(PrecioR)));
				
				Map<Boolean, List<Producto>> PartProducto2 = MyArreglo.stream().collect(
						Collectors.partitioningBy(x -> x.Categoria.compareTo("bodega licores") == 0 && x.Precio < 50));
				
				Map<Boolean, List<Producto>> PartProducto3 = MyArreglo.stream().collect(Collectors
						.partitioningBy(x -> x.Nombre.compareTo("Ginebra 15 botanicals Blumara") == 0 && x.Precio > 5));
				
				Map<Boolean, List<Producto>> PartProducto4 = MyArreglo.stream().collect(Collectors
						.partitioningBy(x -> x.Supermercados.compareTo("mercadona-es") == 0 && x.Precio > 600));

				System.out.println(
						"------------------------------------------------------------------------------------------------------------------");
				if (PartProducto.get(true).isPresent()) {
					Producto Mayorprecio = PartProducto.get(true).get();
					System.out.println("Los productos que si cumplen: " + Mayorprecio);
				}

				if (PartProducto.get(false).isPresent()) {
					Producto Menorprecio = PartProducto.get(false).get();
					System.out.println("Los productos que no cumplen: " + Menorprecio);
				}
				System.out.println(
						"------------------------------------------------------------------------------------------------------------------");
				if (PartProducto1.get(true).isPresent()) {
					Producto MayorPrecioR = PartProducto1.get(true).get();
					System.out.println("Los productos que si cumplen: " + MayorPrecioR);
				}

				if (PartProducto1.get(false).isPresent()) {
					Producto MenorPrecioR = PartProducto1.get(false).get();
					System.out.println("Los productos que no cumplen:" + MenorPrecioR);
				}
				System.out.println(
						"----------------------------------------------------------------------------------------------------------------------------------------");
				System.out.println("Los productos que si cumplen: ");
				PartProducto2.get(true).forEach(System.out::println);
				System.out.println(
						"------------------------------------------------------------------------------------------------------------------------------");
				System.out.println("Los productos que si cumplen: ");
				PartProducto3.get(true).forEach(System.out::println);
				System.out.println(
						"----------------------------------------------------------------------------------------------------------------------------");
				System.out.println("Los productos que si cumplen: ");
				PartProducto4.get(true).forEach(System.out::println);

				Menus.repeat();
				break;

			case 9:
				System.out.println("\n" + "-------PRODUCTOS--------");
				MyArreglo.stream().sorted((a, b) -> a.getNombre().compareTo(b.getNombre()))
						.map(x -> "Supermercado: " + x.getSupermercados() + " Categoria: " + x.getCategoria()
								+ " Nombre: " + x.getNombre() + " Precio: " + x.getPrecio() + " Precio Referencia: "
								+ x.getPrecioR() + " Unidad: { " + x.getUnidadR() + " }")
						.forEach(System.out::println);

				Menus.repeat();
				break;

			case 10:
				System.out.println("\n" + "-------OPERACION DE REDUCCION COLLECTORS--------");
				System.out.println("MAXIMO PRECIO");
				Optional<Producto> maximo = MyArreglo.stream().collect(Collectors.maxBy(Precio));
				System.out.println(maximo);

				System.out.println("MAXIMO DE PRECIO REFERENCIA");
				Optional<Producto> maximoR = MyArreglo.stream().collect(Collectors.maxBy(PrecioR));
				System.out.println(maximoR);

				System.out.println("MINIMO DE PRECIO");
				Optional<Producto> minCollectors = MyArreglo.stream().collect(Collectors.minBy(Precio));
				System.out.println(minCollectors);

				System.out.println("MINIMO DE PRECIO REFERENCIA");
				Optional<Producto> minRCollectors = MyArreglo.stream().collect(Collectors.minBy(PrecioR));
				System.out.println(minRCollectors);

				System.out.println("SUMA DE PRECIOS");
				double sumatotal = MyArreglo.stream().collect(Collectors.summingDouble(Producto::getPrecio));
				System.out.println(sumatotal);

				System.out.println("SUMA DE PRECIOS REFERENCIA");
				double sumatotalR = MyArreglo.stream().collect(Collectors.summingDouble(Producto::getPrecioR));
				System.out.println(sumatotalR);

				Menus.repeat();
				break;

			case 11:
				System.out.println("TENGA UN BUEN DIA :)");
				break;
			}
		}
	}
	
}// FINAL CLASS
