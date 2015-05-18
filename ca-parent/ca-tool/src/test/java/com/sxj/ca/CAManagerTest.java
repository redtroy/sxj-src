package com.sxj.ca;

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.X509Certificate;

import org.bouncycastle.jce.PKCS10CertificationRequest;
import org.junit.Test;

import com.sxj.ca.exception.CertificateException;
import com.sxj.ca.exception.KeyPairException;
import com.sxj.ca.exception.StorageException;
import com.sxj.ca.store.PEMFileStore;
import com.sxj.ca.store.PfxStore;

public class CAManagerTest
{
    PEMFileStore<KeyPair> keystore = new PEMFileStore<KeyPair>("D:\\cakey.pem");
    
    PEMFileStore<X509Certificate> certstore = new PEMFileStore<X509Certificate>(
            "D:\\cacert.crt");
    
    PEMFileStore<KeyPair> interkeystore = new PEMFileStore<KeyPair>(
            "D:\\interkey.pem");
    
    PEMFileStore<X509Certificate> intercertstore = new PEMFileStore<X509Certificate>(
            "D:\\intercert.crt");
    
    PEMFileStore<KeyPair> serverkeystore = new PEMFileStore<KeyPair>(
            "D:\\serverkey.pem");
    
    PEMFileStore<PKCS10CertificationRequest> serverrequeststore = new PEMFileStore<PKCS10CertificationRequest>(
            "D:\\serverreq.pem");
    
    PEMFileStore<X509Certificate> servercertstore = new PEMFileStore<X509Certificate>(
            "D:\\servercert.crt");
    
    PEMFileStore<KeyPair> clientkeystore = new PEMFileStore<KeyPair>(
            "D:\\clientkey.pem");
    
    PEMFileStore<PKCS10CertificationRequest> clientrequeststore = new PEMFileStore<PKCS10CertificationRequest>(
            "D:\\clientreq.pem");
    
    PEMFileStore<X509Certificate> clientcertstore = new PEMFileStore<X509Certificate>(
            "D:\\clientcert.crt");
    
    PfxStore pfxstore = new PfxStore("D://client.pfx");
    
    CAManager ca = null;
    
    /**Step 1，创建CA
     * @throws KeyPairException
     * @throws CertificateException
     */
    public void initCA() throws KeyPairException, CertificateException
    {
        ca = new CAManager();
        X509Attrs principals = new X509Attrs();
        principals.setCommonName("绿色门窗平台");
        principals.setCountryCode("AU");
        ca.init(keystore, certstore, principals);
    }
    
    /**
     * Step 2，创建中间证书，后续的证书均有该证书创建
     * @throws KeyPairException
     * @throws StorageException
     * @throws CertificateException 
     */
    public void createIntermediateCert() throws KeyPairException,
            StorageException, CertificateException
    {
        KeyPair keypair = KeyPairManager.generateRSAKeyPair();
        interkeystore.save(keypair, null);
        X509Certificate cacert = certstore.read();
        KeyPair cakey = keystore.read();
        X509Attrs principals = new X509Attrs();
        principals.setCommonName("中继证书");
        principals.setCountryCode("CN");
        X509Certificate intercert = ca.generateCertificate(keypair,
                cakey,
                cacert,
                principals);
        intercertstore.save(intercert, null);
        
    }
    
    /**
     * Step 3，生成证书请求
     * @throws KeyPairException 
     * @throws CertificateException 
     * @throws StorageException 
     */
    public void createClientCSR() throws KeyPairException,
            CertificateException, StorageException
    {
        KeyPair keypair = KeyPairManager.generateRSAKeyPair();
        clientkeystore.save(keypair, null);
        X509Attrs principals = new X509Attrs();
        principals.setCommonName("测试门窗厂");
        principals.setCountryCode("AU");
        PKCS10CertificationRequest csr = CSRManager.generateCSR(keypair,
                principals);
        clientrequeststore.save(csr, null);
    }
    
    /**Step 4，利用中间证书签发客户证书
     * @throws StorageException
     * @throws CertificateException
     */
    public void createClientCert() throws StorageException,
            CertificateException
    {
        PKCS10CertificationRequest request = clientrequeststore.read();
        X509Certificate parentcert = intercertstore.read();
        KeyPair parentkey = interkeystore.read();
        X509Certificate certificate = ca.issueCertificate(request,
                365,
                parentcert,
                parentkey);
        clientcertstore.save(certificate, null);
    }
    
    /**Step 5，生成PKCS12
     * @throws StorageException
     * @throws KeyStoreException
     * @throws NoSuchProviderException
     * @throws NoSuchAlgorithmException
     * @throws java.security.cert.CertificateException
     * @throws IOException
     */
    public void createPfx() throws CertificateException, StorageException
    {
        
        X509Certificate cacert = certstore.read();
        X509Certificate intercert = intercertstore.read();
        X509Certificate clientcert = clientcertstore.read();
        X509Certificate[] chain = new X509Certificate[3];
        chain[0] = (clientcert);
        chain[1] = (intercert);
        chain[2] = (cacert);
        KeyPair clientkey = clientkeystore.read();
        KeyStore pkcs12 = ca.generatePKCS12(chain, clientkey);
        pfxstore.save(pkcs12, "123456");
    }
    
    /**
     * Step 6，生成证书请求
     * @throws KeyPairException 
     * @throws CertificateException 
     * @throws StorageException 
     */
    public void createServerCSR() throws KeyPairException,
            CertificateException, StorageException
    {
        KeyPair keypair = KeyPairManager.generateRSAKeyPair();
        serverkeystore.save(keypair, null);
        X509Attrs principals = new X509Attrs();
        principals.setCommonName("绿色门窗平台服务器");
        principals.setCountryCode("CN");
        PKCS10CertificationRequest csr = CSRManager.generateCSR(keypair,
                principals);
        serverrequeststore.save(csr, null);
    }
    
    /**Step 7，利用中间证书签发服务器证书
     * @throws StorageException
     * @throws CertificateException
     */
    public void createServerCert() throws StorageException,
            CertificateException
    {
        PKCS10CertificationRequest request = serverrequeststore.read();
        X509Certificate parentcert = intercertstore.read();
        KeyPair parentkey = interkeystore.read();
        X509Certificate certificate = ca.issueCertificate(request,
                365,
                parentcert,
                parentkey);
        servercertstore.save(certificate, null);
    }
    
    @Test
    public void testProcess() throws KeyPairException, CertificateException,
            StorageException, KeyStoreException, NoSuchProviderException,
            NoSuchAlgorithmException, java.security.cert.CertificateException,
            IOException
    {
        initCA();
        createIntermediateCert();
        createClientCSR();
        createClientCert();
        createPfx();
        createServerCSR();
        createServerCert();
    }
}
