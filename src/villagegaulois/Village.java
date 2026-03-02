package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;
	
	private static class Marche {
		private Etal[] etals;

		private Marche(int nbMaxEtal) {
			this.etals = new Etal[nbMaxEtal];
			for (int i = 0; i < nbMaxEtal; i++) {
		        etals[i] = new Etal();
		    }
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur,String produit, int nbProduit) {
			  etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		private int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if (!etals[i].isEtalOccupe()) {
					return i;
				}
			}
			return -1;
		}
		
		private Etal[] trouverEtals(String produit) {
			Etal[] etalProduit = null;
			int nbetal=0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					nbetal++;
				}
			}
			if(nbetal!=0) {
			etalProduit=new Etal[nbetal];
			int a=0;
			for (int j = 0; j < etals.length; j++) {
				if (etals[j].contientProduit(produit)) {
					etalProduit[a]=etals[j];
					a++;
				}
			}
			}
			return etalProduit;
			}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			Etal vendeur=null;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe() && etals[i].getVendeur().equals(gaulois)) {
					vendeur=etals[i];
					}
			} 
			return vendeur;
		}
		
		private String afficherMarche() {
			StringBuilder chaine = new StringBuilder();
			int etalvide=0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					chaine.append(etals[i].afficherEtal());
				} else {
					etalvide++;
				}
			}
			if (etalvide!=0) {
				chaine.append("Il reste "+etalvide+" etals non utilises dans le marche.\n");
			}
			return chaine.toString();
		}
		
	}
	
	public Village(String nom, int nbVillageoisMaximum,int nbEtalsMax) {
		this.nom = nom;
		this.villageois = new Gaulois[nbVillageoisMaximum];
		this.marche= new Marche(nbEtalsMax);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit,int nbProduit) {
		StringBuilder chaine= new StringBuilder();
		chaine.append(vendeur.getNom()+" cherche un endroit pour vendre "+nbProduit+" des "+produit+".\n");
		int emplacement=this.marche.trouverEtalLibre()+1;
		chaine.append("Le vendeur "+vendeur.getNom()+" vend des "+produit+" a l'étal n°"+emplacement+".\n");
		this.marche.utiliserEtal(emplacement-1, vendeur, produit, nbProduit);
		return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine= new StringBuilder();
		Etal[] etalVendeur=marche.trouverEtals(produit);
		if (etalVendeur==null) {
			chaine.append("Il n'y a pas de vendeur qui propose des "+produit+" au marché.\n");
		}else if (etalVendeur.length==1) {
			chaine.append("Seul le vendeur "+etalVendeur[0].getVendeur().getNom()+" propose des "+produit+" au marché.\n");
		}else {
			chaine.append("Les vendeurs qui proposent des"+produit+" sont :\n");
			for (int i = 0; i < etalVendeur.length; i++) {
				chaine.append("- "+etalVendeur[i].getVendeur().getNom()+"\n");
			}
		}
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur){
		Etal rechVendeur=this.marche.trouverVendeur(vendeur);
		return rechVendeur;
	}
	
	public String partirVendeur(Gaulois vendeur){
		String affichage=this.rechercherEtal(vendeur).libererEtal();
		return affichage;
		
	}
	
	public String afficherMarche(){
		StringBuilder affichage= new StringBuilder();
		affichage.append("Le marché du village "+nom+" possède plusieurs étals : \n");
		affichage.append(this.marche.afficherMarche());
		return affichage.toString();
		
	}

	
	
}