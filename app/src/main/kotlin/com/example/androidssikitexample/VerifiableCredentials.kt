package com.example.androidssikitexample

import id.walt.auditor.Auditor
import id.walt.auditor.SignaturePolicy
import id.walt.custodian.Custodian
import id.walt.model.DidMethod
import id.walt.services.did.DidService
import id.walt.signatory.ProofConfig
import id.walt.signatory.ProofType
import id.walt.signatory.Signatory

class VerifiableCredentials {
    companion object {

        fun run() {
            val issuerDid = DidService.create(DidMethod.ebsi)
            val holderDid = DidService.create(DidMethod.key)

            // Issue VC in JSON-LD and JWT format (for show-casing both formats)
            val vcJson = Signatory.getService().issue("VerifiableId", ProofConfig(issuerDid = issuerDid, subjectDid = holderDid, proofType = ProofType.LD_PROOF))
            val vcJwt = Signatory.getService().issue("VerifiableId", ProofConfig(issuerDid = issuerDid, subjectDid = holderDid, proofType = ProofType.JWT))

            // Present VC in JSON-LD and JWT format (for show-casing both formats)
            val vpJson = Custodian.getService().createPresentation(listOf(vcJson), holderDid)
            val vpJwt = Custodian.getService().createPresentation(listOf(vcJwt), holderDid)

            // Verify VPs, using Signature, JsonSchema and a custom policy
            val thread = Thread {
                try {
                    val resJson = Auditor.getService().verify(vpJson, listOf(SignaturePolicy()))
                    val resJwt = Auditor.getService().verify(vpJwt, listOf(SignaturePolicy()))

                    println("JSON verification result: ${resJson.valid}")
                    println("JWT verification result: ${resJwt.valid}")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            thread.start()
        }
    }
}