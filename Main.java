
public class Main {

	public static void main(String[] args) {
		// Alice chose to match
		Alice alice = new Alice(true);
		
		// UNCOMMENT TO SEE TRURTH TABLE (LABELS)
		// alice.printLabelTruthTable();

		// Bob chose to match
		Bob bob = new Bob(true);

		/* ############## */
		/* ### STEP 1 ### */
		/* ############## */

		/*
		 * In this step, Alice prepares the circuit and all other things she needs to
		 * send to Bob (for 1–2 oblivious transfer) (x0, x1, garbledCircuit, publicKey,
		 * out0, out1) and send it to Bob
		 */

		String[] garbledCircuit = alice.getGarbledCircuit(); // garbledCircuit
		String a = alice.getLabelByChoice(); // Alice input label
		String out0 = alice.getOut0();
		String out1 = alice.getOut1();
		String x0 = alice.getX0(); // First random message
		String x1 = alice.getX1(); // Second random message
		String publicKey = alice.keyToString("public"); // Public key for Bob

		// Alice send values to Bob (1–2 oblivious transfer)

		/* ############## */
		/* ### STEP 2 ### */
		/* ############## */

		/*
		 * In this step, Bob receives the data from Alice and proceeds to the execution
		 * of the protocol (1–2 oblivious transfer), he chose x0 or x1, generate random
		 * value r and send xi + r^e mod N to Alice
		 */

		bob.setInfoFromAlice(garbledCircuit, a, out0, out1, x0, x1, publicKey); // set values from Alice and compute v
		String v = bob.getV(); // get v and send it to Alice

		/* ############## */
		/* ### STEP 3 ### */
		/* ############## */

		/*
		 * In this step, Alice receives the data from Bob and compute (v - xi)^d mod N
		 * and send to Bob b0 - r0 and b1 - r1
		 */

		alice.setV(v);
		String encB0 = alice.getEncB0();
		String encB1 = alice.getEncB1();

		/* ############## */
		/* ### STEP 4 ### */
		/* ############## */

		/*
		 * In this step, Alice send data (r0, r1) to Bob and Bob evaluate the circle and Bob send result to Alice
		 */

		bob.receiveEncryptedLabels(encB0, encB1);
		String result = bob.getResult();
		
		// UNCOMMENT TO SEE RESULT
		// System.out.println(result);
		// System.out.println();
		
		/* ############## */
		/* ### STEP 5 ### */
		/* ############## */
		
		/*
		 * In this step, Alice receives label from Bob. She need to check label to find out match result
		 */
		
		System.out.println("Bob checked his circle and know that match result is: " + bob.getMatchResult());
		System.out.println("Alice checked response from Bob and know that match result is: " + alice.getMatchResult(result));
	}

}
