package ticketmachine;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@BeforeEach
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
	// On vérifie que le prix affiché correspond au paramètre passé lors de l'initialisation
	// S1 : le prix affiché correspond à l’initialisation.
	public void priceIsCorrectlyInitialized() {
		// Paramètres : valeur attendue, valeur effective, message si erreur
		assertEquals(PRICE, machine.getPrice(), "Initialisation incorrecte du prix");
	}

	@Test
	// S2 : la balance change quand on insère de l’argent
	public void insertMoneyChangesBalance() {
		machine.insertMoney(10);
		machine.insertMoney(20);
                // Les montants ont été correctement additionnés  
		assertEquals(10 + 20, machine.getBalance(), "La balance n'est pas correctement mise à jour");              
	}

	@Test
	// S3 : on n’imprime pas le ticket si le montant inséré est insuffisant
	public void amountNotSuffisant() {
		machine.insertMoney(10);
		assertFalse(machine.printTicket(), "On ne doit pas imprimer");
	}

	@Test
	// S4 :  on imprime le ticket si le montant inséré est suffisant
	public void amountSuffisant() {
		machine.insertMoney(60);
		assertTrue(machine.printTicket(), "On peux imprimer");
	}

	@Test
	// S5 : Quand on imprime un ticket la balance est décrémentée du prix du ticket
	public void decrementBalanceWithTicketAmount() {
		machine.insertMoney(60);
		machine.printTicket();
		assertEquals(60- machine.getPrice(), machine.getBalance());
	}

	@Test
	// S6 :  le montant collecté est mis à jour quand on imprime un ticket (pas avant)
	public void amountCollectedWhenPrintTicket() {
		machine.insertMoney(60);
		machine.printTicket();
		assertEquals(50, machine.getTotal());
	}

	@Test
	// S7 : refund()rendcorrectement la monnaie
	public void refundIsCorrect() {
		machine.insertMoney(60);
		machine.printTicket();
		assertEquals(60-PRICE, machine.refund());
	}

	@Test
	// S8 : refund()remet la balance à zéro
	public void refundResetBalanceToZero() {
		machine.insertMoney(60);
		machine.printTicket();
		machine.refund();
		assertEquals(0, machine.getBalance());
	}

	@Test
	// S9 : on ne peut pas insérer un montant négatif
	public void cantInsertNegAmount() {
		assertThrows(IllegalArgumentException.class, () -> { machine.insertMoney(-10); }, "Cet appel doit lever une exception");
	}


	@Test
	// S10 :  on ne peut pas créer de machine qui délivre des tickets dont le prix est négatif
	public void cantCreateMachineWithNegPrice() {
		assertThrows(IllegalArgumentException.class, () -> { new TicketMachine(-10);  }, "Cet appel doit lever une exception");
	}

}
