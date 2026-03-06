package histoire;

import villagegaulois.Etal;

public class ScenarioCasDegrade {

	public static void main(String[] args) {
		 Etal etal = new Etal();

	        try {
	            etal.acheterProduit(-5, null);
	        } catch (IllegalArgumentException e) {
	            System.out.println("Erreur quantité : " + e.getMessage());
	        } catch (IllegalStateException e) {
	            System.out.println("Erreur étal : " + e.getMessage());
	        }

	        System.out.println("Fin du test");

	}

}
