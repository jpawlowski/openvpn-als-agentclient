
				/*
 *  Adito
 *
 *  Copyright (C) 2003-2006 3SP LTD. All Rights Reserved
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation; either version 2 of
 *  the License, or (at your option) any later version.
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public
 *  License along with this program; if not, write to the Free Software
 *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */
			
package com.maverick.crypto.publickey;

import java.io.IOException;

import java.math.BigInteger;
import com.maverick.crypto.security.SecureRandom;

public final class Rsa {

  private static BigInteger ONE = BigInteger.valueOf(1);

  public static BigInteger doPrivateCrt(BigInteger input,
                                        BigInteger privateExponent,
                                        BigInteger primeP, BigInteger primeQ,
                                        BigInteger crtCoefficient) {
    return doPrivateCrt(input,
                        primeP, primeQ,
                        getPrimeExponent(privateExponent, primeP),
                        getPrimeExponent(privateExponent, primeQ),
                        crtCoefficient);
  }

  public static BigInteger doPrivateCrt(BigInteger input,
                                        BigInteger p, BigInteger q,
                                        BigInteger dP,
                                        BigInteger dQ,
                                        BigInteger qInv) {
    if (!qInv.equals(q.modInverse(p))) {
      BigInteger t = p;
      p = q;
      q = t;
      t = dP;
      dP = dQ;
      dQ = t;
    }

    BigInteger s_1 = input.modPow(dP, p);
    BigInteger s_2 = input.modPow(dQ, q);
    BigInteger h = qInv.multiply(s_1.subtract(s_2)).mod(p);
    return s_2.add(h.multiply(q));

  }

  public static BigInteger getPrimeExponent(BigInteger privateExponent,
                                            BigInteger prime) {
    BigInteger pe = prime.subtract(ONE);
    return privateExponent.mod(pe);
  }

  public static BigInteger padPKCS1(BigInteger input, int type,
                                    int padLen) throws
                                    IllegalStateException {
    BigInteger result;
    BigInteger rndInt;
    int inByteLen = (input.bitLength() + 7) / 8;

    if (inByteLen > padLen - 11) {
      throw new IllegalStateException("PKCS1 failed to pad input! "
                            + "input=" + String.valueOf(inByteLen)
                            + " padding=" + String.valueOf(padLen));
    }

    byte[] padBytes = new byte[ (padLen - inByteLen - 3) + 1];
    padBytes[0] = 0;

    for (int i = 1; i < (padLen - inByteLen - 3 + 1); i++) {
      if (type == 0x01) {
        padBytes[i] = (byte) 0xff;
      }
      else {
        byte[] b = new byte[1];
        do {
          SecureRandom.getInstance().nextBytes(b);
        }
        while (b[0] == 0);
        padBytes[i] = b[0];
      }
    }

    rndInt = new BigInteger(1, padBytes);
    rndInt = rndInt.shiftLeft( (inByteLen + 1) * 8);
    result = BigInteger.valueOf(type);
    result = result.shiftLeft( (padLen - 2) * 8);
    result = result.or(rndInt);
    result = result.or(input);

    return result;
  }

  public static BigInteger removePKCS1(BigInteger input, int type) throws
  										IllegalStateException {
    byte[] strip = input.toByteArray();
    byte[] val;
    int i;

    if (strip[0] != type) {
      throw new IllegalStateException("PKCS1 padding type " +
                            type + " is not valid");
    }

    for (i = 1; i < strip.length; i++) {
      if (strip[i] == 0) {
        break;
      }
      if (type == 0x01 && strip[i] != (byte) 0xff) {
        throw new IllegalStateException("Corrupt data found in expected PKSC1 padding");
      }
    }

    if (i == strip.length) {
      throw new IllegalStateException("Corrupt data found in expected PKSC1 padding");
    }

    val = new byte[strip.length - i];
    System.arraycopy(strip, i, val, 0, val.length);
    return new BigInteger(1, val);
  }

  public static RsaPrivateCrtKey generateKey(int bits, SecureRandom secRand) {
    return generateKey(bits, BigInteger.valueOf(0x10001L), secRand);
  }

  public static RsaPrivateCrtKey generateKey(int bits, BigInteger e,
                                             SecureRandom secRand) {
    BigInteger p = null;
    BigInteger q = null;
    BigInteger t = null;
    BigInteger phi = null;
    BigInteger d = null;
    BigInteger u = null;
    BigInteger n = null;
    boolean finished = false;
    BigInteger ONE = BigInteger.valueOf(1);

    int pbits = (bits + 1) / 2;
    int qbits = bits - pbits;

    while (!finished) {
      p = new BigInteger(pbits, 80, secRand);
      q = new BigInteger(qbits, 80, secRand);

      if (p.compareTo(q) == 0) {
        continue;
      }
      else if (p.compareTo(q) < 0) {
        t = q;
        q = p;
        p = t;
      }

      if (!p.isProbablePrime(25))
        continue;

      if(!q.isProbablePrime(25))
        continue;

      t = p.gcd(q);
      if (t.compareTo(ONE) != 0) {
        continue;
      }

      n = p.multiply(q);

      if (n.bitLength() != bits) {
        continue;
      }

      phi = p.subtract(ONE).multiply(q.subtract(ONE));
      d = e.modInverse(phi);
      u = q.modInverse(p);

      finished = true;
    }

    return new RsaPrivateCrtKey(n, e, d, p, q,
                                Rsa.getPrimeExponent(d, p),
                                Rsa.getPrimeExponent(d, q),
                                u);


  }

  public static BigInteger doPublic(BigInteger input, BigInteger modulus,
                                    BigInteger publicExponent) {
    return input.modPow(publicExponent, modulus);
  }

  public static BigInteger doPrivate(BigInteger input, BigInteger modulus,
                                     BigInteger privateExponent) {
    return doPublic(input, modulus, privateExponent);
  }

}
