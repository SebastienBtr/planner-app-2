package tests;

import static org.junit.Assert.*;
import modele.Evenement;

import org.junit.Assert;
import org.junit.Test;

public class EvenementTest {

	@Test
	public void testAccesseurEvenement() {
		Evenement e = new Evenement(8,00,9,30,"O cet evenement est obigatoire","Evenement Obligatoire Test");
		Assert.assertEquals(e.getHeure_debut(),8);
		Assert.assertEquals(e.getMinute_debut(),0);
		Assert.assertEquals(e.getHeure_fin(),9);
		Assert.assertEquals(e.getMinute_fin(),30);
		Assert.assertEquals(e.getDescription(),"O cet evenement est obigatoire");
		Assert.assertEquals(e.getTitre(),"Evenement Obligatoire Test");
		Assert.assertEquals(e.getPriorite(), "O");
	}
	
	@Test
	public void testEligibiliteTempsEvenement(){
		Evenement e1 = new Evenement(8,00,9,30,"O cet evenement est obigatoire","Evenement Obligatoire Test");
		Evenement e2 = new Evenement(8,11,9,59,"O cet evenement est obigatoire","Evenement Obligatoire Test");
		Evenement e3 = new Evenement(19,30,21,00,"O cet evenement est obigatoire","Evenement Obligatoire Test");
		Assert.assertEquals(Evenement.estEligible(e1), true);
		Assert.assertEquals(Evenement.estEligible(e2), false);
		Assert.assertEquals(Evenement.estEligible(e3), false);
	}
	
	@Test
	public void testNombreQuartDHeureEvenement(){
		Evenement e1 = new Evenement(8,00,9,30,"O cet evenement est obigatoire","Evenement Obligatoire Test");
		Evenement e2 = new Evenement(8,00,20,00,"O cet evenement est obigatoire","Evenement Obligatoire Test");	
		Assert.assertEquals(e1.getNbCreneaux(), 6);
		Assert.assertEquals(e2.getNbCreneaux(), 48);
		
	}

}
