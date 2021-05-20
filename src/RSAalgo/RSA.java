package RSAalgo;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * This class represent the implementation of the RSA algorithm.
 * The RSA algorithm is used for encryption and decryption of the plain text messages.
 * @author Sergiu
 */
public class RSA
{
    private BigInteger n, d, e;
    private static RSA rsa;
    
    /**
     * Empty constructor
     */

    /**
     * Initialize an instance of the RSA class
     * This method is defined for implementation of the Singleton design pattern.
     * @return instance of the RSA class
     */
    public static RSA getInstance()
    {
        if (rsa == null)
        {
            rsa = new RSA();
            return rsa;
        }
        else
        {
            return rsa;
        }
    }
    
    /** 
     * Encrypt the given plaintext message.
     * @param message
     * @return Encrypted Message
     */
    public String encrypt(String name, String sizeInBytes, String message)
    {
        return (new BigInteger(message.getBytes())).modPow(e, n).toString();
    }

    /** 
     * Encode the given plaintext message.
     * @param message
     * @return Encoded Message
     */
    public BigInteger encode(String message)
    {
        return new BigInteger(message.getBytes());
    }
    
    /** 
     * Decrypt the given ciphertext message.
     * @param message
     * @param n
     * @param d
     * @return Decrypted Message
     */
    public String decrypt(String message, BigInteger n, BigInteger d)
    {
        return new String((new BigInteger(message)).modPow(d, n).toByteArray());
    }
    
    /**
     * Decode the given ciphertext message.
     * @param message
     * @return Decoded Message
     */
    public String decode(BigInteger message)
    {
        return new String(message.toByteArray());
    }
    
    /**
     * Generate a new public and private key set.
     * @param message
     */
    public final void generateKeys(String message)
    {
        SecureRandom random; 
        BigInteger p;
        BigInteger q;
        BigInteger m;
        BigInteger msg = new BigInteger(message.getBytes());
        
        random = new SecureRandom();
        p = new BigInteger(msg.bitLength(), 100, random);
        q = new BigInteger(msg.bitLength(), 100, random);
        n = p.multiply(q);
        
        m = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        e = new BigInteger("3");
        
        while (m.gcd(e).intValue() > 1 && e.compareTo(m) < 0 )
        {
            e = e.add(new BigInteger("2"));
        }
        
        d = e.modInverse(m);
  }

    /**
     * Return modulus from encrypting equation.
     * It is used to define the public key.
     * @return Modulus 
     */
    public BigInteger getN()
    {
        return n;
    }
    
    /**
     * Return the public exponent from encrypting equation.
     * @return Public Exponent
     */
    public BigInteger getE()
    {
        return e;
    }
    
    /**
     * Return the private key.
     * @return Private Key
     */
    public BigInteger getD()
    {
        return d;
    }
}