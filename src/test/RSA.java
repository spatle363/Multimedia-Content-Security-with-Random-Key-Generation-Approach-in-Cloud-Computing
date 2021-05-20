package test;

import java.math.BigInteger;
import java.util.Random;
import java.io.*;

public class RSA
{
  // The bit length of our large primes.
  public static final int SIZE = 512;

  
  // This method prompts the user for a message,
  // encrypts it using N and e and returns the ciphertext.
  public static BigInteger encodeMessage(BigInteger N, BigInteger e) throws IOException
  {
    // Get a message to encrypt. It should be
    // smaller than N and coprime to it.
    String input;
    BigInteger message;
    
    do
    {
      System.out.println("Enter a message "
                         +"(less than N and coprime to it):");
      input = (new BufferedReader(
            new InputStreamReader(System.in))).readLine();
      message = new BigInteger(input);
    }
    while ((message.compareTo(N) != -1)
          || (message.gcd(N).compareTo(BigInteger.valueOf(1)) != 0));
    System.out.println();

    // Encrypt the message
    BigInteger ciphertext = message.modPow(e, N);
    System.out.println("Encoded :" + ciphertext.toString());
    System.out.println("Bet you cannot crack this "
                       +"using N and e alone");
    System.out.println();
    
    // Return the ciphertext
    return ciphertext;
  }
  
  
  // Generate keys, get encrypted message and decrypt it.
  public static void main(String[] argv) throws IOException
  {
    BigInteger p, q, N, P, e, ciphertext, d, plaintext;
    String input;

    // Generate two distinct large prime numbers p, q
    p = new BigInteger(SIZE, 10, new Random());
    do
    {
      q = new BigInteger(SIZE, 10, new Random());
    }
    while (q.compareTo(p) == 0);

    // Calculate their product
    N = p.multiply(q);

    // And calculate P = (p-1)(q-1)
    P = p.subtract(BigInteger.valueOf(1));
    P = P.multiply(q.subtract(BigInteger.valueOf(1)));

    // Next choose e, coprime to and less than P
    do
    {
      e = new BigInteger(2*SIZE, new Random());
    }
    while ((e.compareTo(P) != -1)
          || (e.gcd(P).compareTo(BigInteger.valueOf(1)) != 0));

    // Publish N and e
    System.out.println("N is " + N.toString());
    System.out.println("e is " + e.toString());
    System.out.println();

    // Get a message from the user, encrypted using N and e.
    ciphertext = encodeMessage(N, e);

    // Compute d, the inverse of e mod P
    d = e.modInverse(P);
    System.out.println("d is " + d.toString());

    // Decrypt the message
    plaintext = ciphertext.modPow(d, N);
    System.out.println("Decoded :" + plaintext.toString());
  }
}
