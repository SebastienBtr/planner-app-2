package tests;

import static org.junit.Assert.*;

import java.time.LocalDate;

import modele.Evenement;
import modele.Personne;

import org.junit.Assert;
import org.junit.Test;

public class PersonneTest {

	@Test
	public void testAccesseurPersonne() {
		Personne pTest = new Personne("test@msn.com","Kevuakue","Didou");
		Assert.assertEquals(pTest.getNom(), "Kevuakue");
		Assert.assertEquals(pTest.getPrenom(), "Didou");
		Assert.assertEquals(pTest.getId(), "Kevuakue Didou");
	}
	
	@Test
	public void testDispoPersonne(){
		Personne pTest = new Personne("test@msn.com","Kevuakue","Didou");
		Assert.assertEquals(pTest.estLibre(LocalDate.now(), 16, 00),0);
		pTest.ajouterEvenement(LocalDate.now(), new Evenement(16,00,17,45,"O Cet evenement est obligatoire","Evenement Obligatoire Test"));
		Assert.assertEquals(pTest.estLibre(LocalDate.now(), 16, 00), 3);
		Assert.assertEquals(pTest.estLibre(LocalDate.now(), 18, 00), 0);
		pTest.ajouterEvenement(LocalDate.now(), new Evenement(18,00,19,00,"A Cet evenement est annulable","Evenement Annulable Test"));
		Assert.assertEquals(pTest.estLibre(LocalDate.now(), 18, 30), 1);
		Assert.assertEquals(pTest.estLibre(LocalDate.now(), 19, 00), 0);
		pTest.ajouterEvenement(LocalDate.now(), new Evenement(19,00,19,30,"I Cet evenement est informatif","Evenement Informatif Test"));
		Assert.assertEquals(pTest.estLibre(LocalDate.now(), 19, 15), 2);
		Assert.assertEquals(pTest.estLibre(LocalDate.now(), 19, 45), 0);
		
	}

}
