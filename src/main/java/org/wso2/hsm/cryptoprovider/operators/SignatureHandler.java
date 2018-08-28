package org.wso2.hsm.cryptoprovider.operators;

import iaik.pkcs.pkcs11.Mechanism;
import iaik.pkcs.pkcs11.Session;
import iaik.pkcs.pkcs11.TokenException;
import iaik.pkcs.pkcs11.objects.PrivateKey;
import iaik.pkcs.pkcs11.objects.PublicKey;


public class SignatureHandler {

    /**
     * Constructor for signature handler.
     */
    public SignatureHandler() {
    }

    /**
     * Method to full sign a given data.
     *
     * @param session       : Session used to perform signing.
     * @param dataToSign    : Data to be signed.
     * @param signMechanism : Signing mechanism
     * @param signKey       : Key used for signing.
     * @return signature as a byte array.
     * @throws TokenException
     */
    public byte[] fullSign(Session session, byte[] dataToSign, long signMechanism, PrivateKey signKey) {
        byte[] signature = null;
        Mechanism signingMechanism = Mechanism.get(signMechanism);
        if (signingMechanism.isFullSignVerifyMechanism()) {
            try {
                session.signInit(signingMechanism, signKey);
                signature = session.sign(dataToSign);
            } catch (TokenException e) {
                System.out.println("Full sign generation error : " + e.getMessage());
            }
        }
        return signature;
    }

    /**
     * Method to full verify a given data.
     *
     * @param session         : Session used to perform verifying.
     * @param dataToVerify    : Data to be verified.
     * @param signature       : Signature of the data.
     * @param verifyMechanism : verifying mechanism.
     * @param verificationKey : Key used for verification.
     * @return True if verified.
     */
    public boolean fullVerify(Session session, byte[] dataToVerify, byte[] signature,
                              long verifyMechanism, PublicKey verificationKey) {
        boolean verified = false;
        Mechanism verifyingMechanism = Mechanism.get(verifyMechanism);
        if (verifyingMechanism.isFullSignVerifyMechanism()) {
            try {
                session.verifyInit(verifyingMechanism, verificationKey);
                session.verify(dataToVerify, signature);
                verified = true;
            } catch (TokenException e) {
                if (!e.getMessage().equals("")) {
                    System.out.println("Sign verification error : " + e.getMessage());
                }
            }
        }
        return verified;
    }
}