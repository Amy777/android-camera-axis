package com.myapps;
public class Astuces {
	
	private String s[] = {
			"Appuyez sur Menu pour acc�der aux options",
			"Vous pouvez visualiser jusqu'� 4 cam�ras",
			"En touchant l'�cran vous pouvez echanger les cameras", 
			"De prochaines astuces bient�t disponibles",
			"Sauvegarder vos cam�ras dans un fichier. Ca �vitera de les rentrer � chaque fois"} ;
		
	private int max = 5 ;
	Astuces(){
	}
	
	public String getLabel(int i) {
	      return s[i] ;
	   }
	
	public int getMax(){
		return max;
	}
	
}