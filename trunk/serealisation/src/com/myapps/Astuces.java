package com.myapps;
public class Astuces {
	
	private String s[] = {
			"Appuyez sur Menu pour acc�der aux options",
			"Vous pouvez visualiser jusqu'� 6 cam�ras simultan�ment",
			"En touchant l'�cran vous pouvez stopper ou changer les cameras", 
			"De prochaines astuces bient�t disponibles",
			"Vos cam�ra se sauvegardent automatiquement",
			"Vous pouvre activer jusqu'a 9 d�t�ction de mouvement par camera",
			"Selectionner un cadre dans lequel surveiller les mouvements",
			"Regler les parametres avanc� de la camera en activant les controles avances dans le menu durant la visualisation de la video",
			"Diriger la camera avec vote doigt",
			"Une notification vous informe que vous avez bien recu votre snapshot",
			"Vous pouvez desactiver cette astuces dans les param�tres",
			"N'esitez pas � partager cette application"} ;
		
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