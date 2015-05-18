package com.sxj.ca;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.Date;

import org.bouncycastle.asn1.x509.CRLNumber;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.x509.X509V2CRLGenerator;
import org.bouncycastle.x509.extension.AuthorityKeyIdentifierStructure;

import com.sxj.ca.exception.CRLException;

/**
 * @author Administrator
 * 证书撤销列表
 *
 */
public class CRLManager
{
    
    public static X509CRL revoke(X509Certificate cacert, KeyPair cakeypair)
            throws CRLException
    {
        try
        {
            X509V2CRLGenerator crlGen = new X509V2CRLGenerator();
            Date now = new Date();
            crlGen.setIssuerDN(cacert.getSubjectX500Principal());
            crlGen.setThisUpdate(now);
            crlGen.setNextUpdate(new Date(now.getTime() + 100000));
            crlGen.setSignatureAlgorithm("SHA256WithRSAEncryption");
            //            crlGen.addCRLEntry(serialNumber,
            //                    now,
            //                    org.bouncycastle.asn1.x509.CRLReason.privilegeWithdrawn);
            crlGen.addExtension(X509Extensions.AuthorityKeyIdentifier,
                    false,
                    new AuthorityKeyIdentifierStructure(cacert));
            crlGen.addExtension(X509Extensions.CRLNumber, false, new CRLNumber(
                    BigInteger.valueOf(1)));
            return crlGen.generate(cakeypair.getPrivate(), "BC");
        }
        catch (Exception e)
        {
            throw new CRLException(e);
        }
    }
    
    public static X509CRL revoke(X509Certificate cacert, KeyPair cakeypair,
            BigInteger serialNumber) throws CRLException
    {
        try
        {
            
            X509V2CRLGenerator crlGen = new X509V2CRLGenerator();
            Date now = new Date();
            crlGen.setIssuerDN(cacert.getSubjectX500Principal());
            crlGen.setThisUpdate(now);
            crlGen.setNextUpdate(new Date(now.getTime() + 100000));
            crlGen.setSignatureAlgorithm("SHA256WithRSAEncryption");
            //            crlGen.addCRLEntry(serialNumber,
            //                    now,
            //                    org.bouncycastle.asn1.x509.CRLReason.privilegeWithdrawn);
            crlGen.addExtension(X509Extensions.AuthorityKeyIdentifier,
                    false,
                    new AuthorityKeyIdentifierStructure(cacert));
            crlGen.addExtension(X509Extensions.CRLNumber, false, new CRLNumber(
                    BigInteger.valueOf(1)));
            return crlGen.generate(cakeypair.getPrivate(), "BC");
        }
        catch (Exception e)
        {
            throw new CRLException(e);
        }
    }
    
}
