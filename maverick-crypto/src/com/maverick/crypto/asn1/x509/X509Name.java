
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
			
package com.maverick.crypto.asn1.x509;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import com.maverick.crypto.asn1.*;

public class X509Name
    implements DEREncodable
{
    /**
     * country code - StringType(SIZE(2))
     */
    public static final DERObjectIdentifier C = new DERObjectIdentifier("2.5.4.6");

    /**
     * organization - StringType(SIZE(1..64))
     */
    public static final DERObjectIdentifier O = new DERObjectIdentifier("2.5.4.10");

    /**
     * organizational unit name - StringType(SIZE(1..64))
     */
    public static final DERObjectIdentifier OU = new DERObjectIdentifier("2.5.4.11");

    /**
     * Title
     */
    public static final DERObjectIdentifier T = new DERObjectIdentifier("2.5.4.12");

    /**
     * common name - StringType(SIZE(1..64))
     */
    public static final DERObjectIdentifier CN = new DERObjectIdentifier("2.5.4.3");

    /**
     * device serial number name - StringType(SIZE(1..64))
     */
    public static final DERObjectIdentifier SN = new DERObjectIdentifier("2.5.4.5");

    /**
     * locality name - StringType(SIZE(1..64))
     */
    public static final DERObjectIdentifier L = new DERObjectIdentifier("2.5.4.7");

    /**
     * state, or province name - StringType(SIZE(1..64))
     */
    public static final DERObjectIdentifier ST = new DERObjectIdentifier("2.5.4.8");

    /**
     * Naming attributes of type X520name
     */
    public static final DERObjectIdentifier SURNAME = new DERObjectIdentifier("2.5.4.4");
    public static final DERObjectIdentifier GIVENNAME = new DERObjectIdentifier("2.5.4.42");
    public static final DERObjectIdentifier INITIALS = new DERObjectIdentifier("2.5.4.43");
    public static final DERObjectIdentifier GENERATION = new DERObjectIdentifier("2.5.4.44");
	public static final DERObjectIdentifier UNIQUE_IDENTIFIER = new DERObjectIdentifier("2.5.4.45");

    /**
     * Email address (RSA PKCS#9 extension) - IA5String.
     * <p>Note: if you're trying to be ultra orthodox, don't use this! It shouldn't be in here.
     */
    public static final DERObjectIdentifier EmailAddress = new DERObjectIdentifier("1.2.840.113549.1.9.1");

	/**
	 * email address in Verisign certificates
	 */
	public static final DERObjectIdentifier E = EmailAddress;

    /*
     * others...
     */
    public static final DERObjectIdentifier DC = new DERObjectIdentifier("0.9.2342.19200300.100.1.25");

    /**
     * LDAP User id.
     */
    public static final DERObjectIdentifier UID = new DERObjectIdentifier("0.9.2342.19200300.100.1.1");

    /**
     * look up table translating OID values into their common symbols - this static is scheduled for deletion
     */
    public static Hashtable OIDLookUp = new Hashtable();

    /**
     * determines whether or not strings should be processed and printed
     * from back to front.
     */
    public static boolean DefaultReverse = false;

    /**
     * default look up table translating OID values into their common symbols following
     * the convention in RFC 2253 with a few extras
     */
    public static Hashtable DefaultSymbols = OIDLookUp;

    /**
     * look up table translating OID values into their common symbols following the convention in RFC 2253
     * with a few extras
     */
    public static Hashtable RFC2253Symbols = new Hashtable();

    /**
     * look up table translating string values into their OIDS -
     * this static is scheduled for deletion
     */
    public static Hashtable SymbolLookUp = new Hashtable();

    /**
     * look up table translating common symbols into their OIDS.
     */
    public static Hashtable DefaultLookUp = SymbolLookUp;

    static
    {
        DefaultSymbols.put(C, "C");
        DefaultSymbols.put(O, "O");
        DefaultSymbols.put(T, "T");
        DefaultSymbols.put(OU, "OU");
        DefaultSymbols.put(CN, "CN");
        DefaultSymbols.put(L, "L");
        DefaultSymbols.put(ST, "ST");
        DefaultSymbols.put(SN, "SN");
        DefaultSymbols.put(EmailAddress, "E");
        DefaultSymbols.put(DC, "DC");
        DefaultSymbols.put(UID, "UID");
        DefaultSymbols.put(SURNAME, "SURNAME");
        DefaultSymbols.put(GIVENNAME, "GIVENNAME");
        DefaultSymbols.put(INITIALS, "INITIALS");
        DefaultSymbols.put(GENERATION, "GENERATION");

        RFC2253Symbols.put(C, "C");
        RFC2253Symbols.put(O, "O");
        RFC2253Symbols.put(T, "T");
        RFC2253Symbols.put(OU, "OU");
        RFC2253Symbols.put(CN, "CN");
        RFC2253Symbols.put(L, "L");
        RFC2253Symbols.put(ST, "ST");
        RFC2253Symbols.put(SN, "SN");
        RFC2253Symbols.put(EmailAddress, "EMAILADDRESS");
        RFC2253Symbols.put(DC, "DC");
        RFC2253Symbols.put(UID, "UID");
        RFC2253Symbols.put(SURNAME, "SURNAME");
        RFC2253Symbols.put(GIVENNAME, "GIVENNAME");
        RFC2253Symbols.put(INITIALS, "INITIALS");
        RFC2253Symbols.put(GENERATION, "GENERATION");

        DefaultLookUp.put("c", C);
        DefaultLookUp.put("o", O);
        DefaultLookUp.put("t", T);
        DefaultLookUp.put("ou", OU);
        DefaultLookUp.put("cn", CN);
        DefaultLookUp.put("l", L);
        DefaultLookUp.put("st", ST);
        DefaultLookUp.put("sn", SN);
        DefaultLookUp.put("emailaddress", E);
        DefaultLookUp.put("dc", DC);
        DefaultLookUp.put("e", E);
        DefaultLookUp.put("uid", UID);
        DefaultLookUp.put("surname", SURNAME);
        DefaultLookUp.put("givenname", GIVENNAME);
        DefaultLookUp.put("initials", INITIALS);
        DefaultLookUp.put("generation", GENERATION);
    }

    private Vector                  ordering = new Vector();
    private Vector                  values = new Vector();
	private Vector                  added = new Vector();

    private ASN1Sequence            seq;

    public static X509Name getInstance(
        ASN1TaggedObject obj,
        boolean          explicit)
    {
        return getInstance(ASN1Sequence.getInstance(obj, explicit));
    }

    public static X509Name getInstance(
        Object  obj)
    {
        if (obj == null || obj instanceof X509Name)
        {
            return (X509Name)obj;
        }
        else if (obj instanceof ASN1Sequence)
        {
            return new X509Name((ASN1Sequence)obj);
        }

        throw new IllegalArgumentException("unknown object in factory");
    }

    /**
     * Constructor from ASN1Sequence
     *
     * the principal will be a list of constructed sets, each containing an (OID, String) pair.
     */
    public X509Name(
        ASN1Sequence  seq)
    {
        this.seq = seq;

        Enumeration e = seq.getObjects();

        while (e.hasMoreElements())
        {
            ASN1Set         set = (ASN1Set)e.nextElement();

			for (int i = 0; i < set.size(); i++)
			{
				   ASN1Sequence s = (ASN1Sequence)set.getObjectAt(i);

				   ordering.addElement(s.getObjectAt(0));
				   values.addElement(((DERString) s.getObjectAt(1)).getString());
				   added.addElement((i != 0) ? new Boolean(true) : new Boolean(false));
			}
        }
    }

    /**
     * constructor from a table of attributes.
     * <p>
     * it's is assumed the table contains OID/String pairs, and the contents
     * of the table are copied into an internal table as part of the
     * construction process.
     * <p>
     * <b>Note:</b> if the name you are trying to generate should be
     * following a specific ordering, you should use the constructor
     * with the ordering specified below.
     */
    public X509Name(
        Hashtable  attributes)
    {
        this(null, attributes);
    }

    /**
     * Constructor from a table of attributes with ordering.
     * <p>
     * it's is assumed the table contains OID/String pairs, and the contents
     * of the table are copied into an internal table as part of the
     * construction process. The ordering vector should contain the OIDs
     * in the order they are meant to be encoded or printed in toString.
     */
    public X509Name(
        Vector      ordering,
        Hashtable   attributes)
    {
        if (ordering != null)
        {
            for (int i = 0; i != ordering.size(); i++)
            {
                this.ordering.addElement(ordering.elementAt(i));
                this.added.addElement(new Boolean(false));
            }
        }
        else
        {
            Enumeration     e = attributes.keys();

            while (e.hasMoreElements())
            {
                this.ordering.addElement(e.nextElement());
				this.added.addElement(new Boolean(false));
            }
        }

        for (int i = 0; i != this.ordering.size(); i++)
        {
            DERObjectIdentifier     oid = (DERObjectIdentifier)this.ordering.elementAt(i);

            if (attributes.get(oid) == null)
            {
                throw new IllegalArgumentException("No attribute for object id - " + oid.getId() + " - passed to distinguished name");
            }

            this.values.addElement(attributes.get(oid)); // copy the hash table
        }
    }

    /**
     * Takes two vectors one of the oids and the other of the values.
     */
    public X509Name(
        Vector  oids,
        Vector  values)
    {
        if (oids.size() != values.size())
        {
            throw new IllegalArgumentException("oids vector must be same length as values.");
        }

        for (int i = 0; i < oids.size(); i++)
        {
            this.ordering.addElement(oids.elementAt(i));
            this.values.addElement(values.elementAt(i));
			this.added.addElement(new Boolean(false));
        }
    }

    /**
     * Takes an X509 dir name as a string of the format "C=AU, ST=Victoria", or
     * some such, converting it into an ordered set of name attributes.
     */
    public X509Name(
        String  dirName)
    {
        this(DefaultReverse, DefaultLookUp, dirName);
    }

    /**
     * Takes an X509 dir name as a string of the format "C=AU, ST=Victoria", or
     * some such, converting it into an ordered set of name attributes. If reverse
     * is true, create the encoded version of the sequence starting from the
     * last element in the string.
     */
    public X509Name(
        boolean reverse,
        String  dirName)
    {
        this(reverse, DefaultLookUp, dirName);
    }

    /**
     * Takes an X509 dir name as a string of the format "C=AU, ST=Victoria", or
     * some such, converting it into an ordered set of name attributes. lookUp
     * should provide a table of lookups, indexed by lowercase only strings and
     * yielding a DERObjectIdentifier, other than that OID. and numeric oids
     * will be processed automatically.
     * <br>
     * If reverse is true, create the encoded version of the sequence
     * starting from the last element in the string.
     * @param reverse true if we should start scanning from the end (RFC 2553).
     * @param lookUp table of names and their oids.
     * @param dirName the X.500 string to be parsed.
     */
    public X509Name(
        boolean     reverse,
        Hashtable   lookUp,
        String      dirName)
    {
        X509NameTokenizer   nTok = new X509NameTokenizer(dirName);

        while (nTok.hasMoreTokens())
        {
            String  token = nTok.nextToken();
            int     index = token.indexOf('=');

            if (index == -1)
            {
                throw new IllegalArgumentException("badly formated directory string");
            }

            String              name = token.substring(0, index);
            String              value = token.substring(index + 1);
            DERObjectIdentifier oid = null;

            if (name.toUpperCase().startsWith("OID."))
            {
                oid = new DERObjectIdentifier(name.substring(4));
            }
            else if (name.charAt(0) >= '0' && name.charAt(0) <= '9')
            {
                oid = new DERObjectIdentifier(name);
            }
            else
            {
                oid = (DERObjectIdentifier)lookUp.get(name.toLowerCase());
                if (oid == null)
                {
                    throw new IllegalArgumentException("Unknown object id - " + name + " - passed to distinguished name");
                }
            }

            this.ordering.addElement(oid);
            this.values.addElement(value);
            this.added.addElement(new Boolean(false));
        }

        if (reverse)
        {
            Vector  o = new Vector();
            Vector  v = new Vector();

            for (int i = this.ordering.size() - 1; i >= 0; i--)
            {
                o.addElement(this.ordering.elementAt(i));
                v.addElement(this.values.elementAt(i));
				this.added.addElement(new Boolean(false));
            }

            this.ordering = o;
            this.values = v;
        }
    }

    /**
     * return a vector of the oids in the name, in the order they were found.
     */
    public Vector getOIDs()
    {
        Vector  v = new Vector();

        for (int i = 0; i != ordering.size(); i++)
        {
            v.addElement(ordering.elementAt(i));
        }

        return v;
    }

    /**
     * return a vector of the values found in the name, in the order they
     * were found.
     */
    public Vector getValues()
    {
        Vector  v = new Vector();

        for (int i = 0; i != values.size(); i++)
        {
            v.addElement(values.elementAt(i));
        }

        return v;
    }

    /**
     * return false if we have characters out of the range of a printable
     * string, true otherwise.
     */
    private boolean canBePrintable(
        String  str)
    {
        for (int i = str.length() - 1; i >= 0; i--)
        {
            if (str.charAt(i) > 0x007f)
            {
                return false;
            }
        }

        return true;
    }

    public DERObject getDERObject()
    {
        if (seq == null)
        {
            ASN1EncodableVector  vec = new ASN1EncodableVector();

            for (int i = 0; i != ordering.size(); i++)
            {
                ASN1EncodableVector     v = new ASN1EncodableVector();
                DERObjectIdentifier     oid = (DERObjectIdentifier)ordering.elementAt(i);

                v.add(oid);

                String  str = (String)values.elementAt(i);

				if (str.charAt(0) == '#')
				{
					str = str.toLowerCase();
					byte[]	data = new byte[str.length() / 2];
					for (int index = 0; index != data.length; index++)
					{
						char	left = str.charAt((index * 2) + 1);
						char right = str.charAt((index * 2) + 2);

						if (left < 'a')
						{
							data[index] = (byte)((left - '0') << 4);
						}
						else
						{
							data[index] = (byte)((left - 'a' + 10) << 4);
						}
						if (right < 'a')
						{
							data[index] |= (byte)(right - '0');
						}
						else
						{
							data[index] |= (byte)(right - 'a' + 10);
						}
					}

					ASN1InputStream aIn = new ASN1InputStream(
														new ByteArrayInputStream(data));

					try
                    {
                        v.add(aIn.readObject());
                    }
                    catch (IOException e)
                    {
                        throw new RuntimeException("bad object in '#' string");
                    }
				}
				else
				{
	                if (oid.equals(EmailAddress))
	                {
	                    v.add(new DERIA5String(str));
	                }
	                else
	                {
	                    if (canBePrintable(str))
	                    {
	                        v.add(new DERPrintableString(str));
	                    }
	                    else
	                    {
	                        v.add(new DERUTF8String(str));
	                    }
	                }
				}

                vec.add(new DERSet(new DERSequence(v)));
            }

            seq = new DERSequence(vec);
        }

        return seq;
    }

    /**
     * @param inOrder if true the order of both X509 names must be the same,
     * as well as the values associated with each element.
     */
    public boolean equals(Object _obj, boolean inOrder)
    {
        if (_obj == this)
        {
            return true;
        }

        if (!inOrder)
        {
            return this.equals(_obj);
        }

        if (_obj == null || !(_obj instanceof X509Name))
        {
            return false;
        }

        X509Name _oxn          = (X509Name)_obj;
        int      _orderingSize = ordering.size();

        if (_orderingSize != _oxn.ordering.size())
        {
			return false;
		}

		for(int i = 0; i < _orderingSize; i++)
		{
			String  _oid   = ((DERObjectIdentifier)ordering.elementAt(i)).getId();
			String  _val   = (String)values.elementAt(i);

            String _oOID = ((DERObjectIdentifier)_oxn.ordering.elementAt(i)).getId();
            String _oVal = (String)_oxn.values.elementAt(i);

            if (_oid.equals(_oOID))
            {
                _val = _val.trim().toLowerCase();
                _oVal = _oVal.trim().toLowerCase();
                if (_val.equals(_oVal))
                {
                    continue;
                }
                else
                {
                    StringBuffer    v1 = new StringBuffer();
                    StringBuffer    v2 = new StringBuffer();

                    if (_val.length() != 0)
                    {
                        char    c1 = _val.charAt(0);

                        v1.append(c1);

                        for (int k = 1; k < _val.length(); k++)
                        {
                            char    c2 = _val.charAt(k);
                            if (!(c1 == ' ' && c2 == ' '))
                            {
                                v1.append(c2);
                            }
                            c1 = c2;
                        }
                    }

                    if (_oVal.length() != 0)
                    {
                        char    c1 = _oVal.charAt(0);

                        v2.append(c1);

                        for (int k = 1; k < _oVal.length(); k++)
                        {
                            char    c2 = _oVal.charAt(k);
                            if (!(c1 == ' ' && c2 == ' '))
                            {
                                v2.append(c2);
                            }
                            c1 = c2;
                        }
                    }

                    if (!v1.toString().equals(v2.toString()))
                    {
                        return false;
                    }
                }
            }
        }

		return true;
	}

    /**
     * test for equality - note: case is ignored.
     */
    public boolean equals(Object _obj)
    {
        if (_obj == this)
        {
            return true;
        }

        if (_obj == null || !(_obj instanceof X509Name))
        {
            return false;
        }

        X509Name _oxn          = (X509Name)_obj;

        if (this.getDERObject().equals(_oxn.getDERObject()))
        {
        	return true;
        }

        int      _orderingSize = ordering.size();

        if (_orderingSize != _oxn.ordering.size())
        {
			return false;
		}

		boolean[] _indexes = new boolean[_orderingSize];

		for(int i = 0; i < _orderingSize; i++)
		{
			boolean _found = false;
			String  _oid   = ((DERObjectIdentifier)ordering.elementAt(i)).getId();
			String  _val   = (String)values.elementAt(i);

			for(int j = 0; j < _orderingSize; j++)
			{
				if(_indexes[j] == true)
				{
					continue;
				}

				String _oOID = ((DERObjectIdentifier)_oxn.ordering.elementAt(j)).getId();
				String _oVal = (String)_oxn.values.elementAt(j);

				if (_oid.equals(_oOID))
                {
                    _val = _val.trim().toLowerCase();
                    _oVal = _oVal.trim().toLowerCase();
                    if (_val.equals(_oVal))
                    {
                        _indexes[j] = true;
                        _found      = true;
                        break;
                    }
                    else
                    {
                        StringBuffer    v1 = new StringBuffer();
                        StringBuffer    v2 = new StringBuffer();

                        if (_val.length() != 0)
                        {
                            char    c1 = _val.charAt(0);

                            v1.append(c1);

                            for (int k = 1; k < _val.length(); k++)
                            {
                                char    c2 = _val.charAt(k);
                                if (!(c1 == ' ' && c2 == ' '))
                                {
                                    v1.append(c2);
                                }
                                c1 = c2;
                            }
                        }

                        if (_oVal.length() != 0)
                        {
                            char    c1 = _oVal.charAt(0);

                            v2.append(c1);

                            for (int k = 1; k < _oVal.length(); k++)
                            {
                                char    c2 = _oVal.charAt(k);
                                if (!(c1 == ' ' && c2 == ' '))
                                {
                                    v2.append(c2);
                                }
                                c1 = c2;
                            }
                        }

                        if (v1.toString().equals(v2.toString()))
                        {
                            _indexes[j] = true;
                            _found      = true;
                            break;
                        }
                    }
                }
			}

			if(!_found)
			{
				return false;
			}
		}

		return true;
	}

    public int hashCode()
    {
        ASN1Sequence  seq = (ASN1Sequence)this.getDERObject();
        Enumeration   e = seq.getObjects();
        int           hashCode = 0;

        while (e.hasMoreElements())
        {
            hashCode ^= e.nextElement().hashCode();
        }

        return hashCode;
    }

    private void appendValue(
        StringBuffer        buf,
        Hashtable           oidSymbols,
        DERObjectIdentifier oid,
        String              value)
    {
        String  sym = (String)oidSymbols.get(oid);

        if (sym != null)
        {
            buf.append(sym);
        }
        else
        {
            buf.append(oid.getId());
        }

        buf.append("=");

        int     index = buf.length();

        buf.append(value);

        int     end = buf.length();

        while (index != end)
        {
            if ((buf.charAt(index) == ',')
               || (buf.charAt(index) == '"')
               || (buf.charAt(index) == '\\')
               || (buf.charAt(index) == '+')
               || (buf.charAt(index) == '<')
               || (buf.charAt(index) == '>')
               || (buf.charAt(index) == ';'))
            {
                buf.insert(index, "\\");
                index++;
                end++;
            }

            index++;
        }
    }

    /**
     * convert the structure to a string - if reverse is true the
     * oids and values are listed out starting with the last element
     * in the sequence (ala RFC 2253), otherwise the string will begin
     * with the first element of the structure. If no string definition
     * for the oid is found in oidSymbols the string value of the oid is
     * added. Two standard symbol tables are provided DefaultSymbols, and
     * RFC2253Symbols as part of this class.
     *
     * @param reverse if true start at the end of the sequence and work back.
     * @param oidSymbols look up table strings for oids.
     */
    public String toString(
        boolean     reverse,
        Hashtable   oidSymbols)
    {
        StringBuffer            buf = new StringBuffer();
        boolean                 first = true;

        if (reverse)
        {
            for (int i = ordering.size() - 1; i >= 0; i--)
            {
                if (first)
                {
                    first = false;
                }
                else
                {
					if (((Boolean)added.elementAt(i + 1)).booleanValue())
					{
						buf.append(" + ");
					}
					else
					{
						buf.append(",");
					}
                }

                appendValue(buf, oidSymbols,
                            (DERObjectIdentifier)ordering.elementAt(i),
                            (String)values.elementAt(i));
            }
        }
        else
        {
            for (int i = 0; i < ordering.size(); i++)
            {
                if (first)
                {
                    first = false;
                }
                else
                {
                	if (((Boolean)added.elementAt(i)).booleanValue())
                	{
                		buf.append(" + ");
                	}
                	else
                	{
						buf.append(",");
                	}
                }

                appendValue(buf, oidSymbols,
                            (DERObjectIdentifier)ordering.elementAt(i),
                            (String)values.elementAt(i));
            }
        }

        return buf.toString();
    }

    public String toString()
    {
        return toString(DefaultReverse, DefaultSymbols);
    }
}
