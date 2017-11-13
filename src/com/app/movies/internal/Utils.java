package com.app.movies.internal;

import java.util.ArrayList;


public class Utils {
	
	
	

	

	
	private static String getNameGenre(String ngenre)
	{
		String returnGenre ="";
		int numgenre = Integer.parseInt(ngenre);
		switch (numgenre){
		case 28: returnGenre="Action";
		break;
		case 12: returnGenre="Adventure";
		break;
		case 16: returnGenre="Animation";
		break;
		case 35: returnGenre="Comedy";
		break;
		case 80: returnGenre="Crime";
		break;
		case 99: returnGenre="Documentary";
		break;
		case 18: returnGenre="Drama";
		break;
		case 10751: returnGenre="Family";
		break;
		case 14: returnGenre="Fantasy";
		break;
		case 10769: returnGenre="Foreign";
		break;
		case 36: returnGenre="History";
		break;
		case 27: returnGenre="Horror";
		break;
		case 10402: returnGenre="Music";
		break;
		case 9648: returnGenre="Mystery";
		break;
		case 10749: returnGenre="Romance";
		break;
		case 878: returnGenre="Science Fiction";
		break;
		case 10770: returnGenre="TV Movie";
		break;
		case 53: returnGenre="Thriller";
		break;
		case 10752: returnGenre="War";
		break;
		case 37: returnGenre="Western";
		break;
		}
		return returnGenre;
	}
	
	public static String seqGenre(String genreCorch)
	{
		String seq = "";
		
		String auxgenre = genreCorch.substring(1, genreCorch.length()-1);
		
		if(isNumeric(auxgenre))
		{
			seq = getNameGenre(auxgenre);
		}else
		{
			String[] arrayDatos = auxgenre.split(",");
			
			for (int i = 0; i < arrayDatos.length; i++) {
				if(isNumeric( arrayDatos[i]) && i==0)
				{
					seq = getNameGenre(arrayDatos[i]);
				}else if(isNumeric(arrayDatos[i]) && i>0)
				{
					seq = seq + "," + getNameGenre(arrayDatos[i]);
				}
			}
		}
		
		return seq;
	}
	
	


	public static ArrayList<String> obtenerIdsListaEntrada(String listaEntrada)
	
	{
		ArrayList <String> lista_ids = new ArrayList<String>();
		String[] arrayDatos = listaEntrada.split("\"");
		for (int i = 0; i < arrayDatos.length; i++) {
			//Log.i("dato id: ",arrayDatos[i] );
			if(isNumeric(arrayDatos[i])) 
				{
					lista_ids.add(arrayDatos[i]);
				}
			
		}
		return lista_ids;
	}
	private static boolean isNumeric(String cadena){
		try {
			Integer.parseInt(cadena);
			return true;
		} catch (NumberFormatException nfe){
			return false;
		}
	}


}
