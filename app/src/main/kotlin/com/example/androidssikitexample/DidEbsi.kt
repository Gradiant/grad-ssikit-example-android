package com.example.androidssikitexample

import id.walt.model.DidMethod
import id.walt.crypto.KeyAlgorithm
import id.walt.services.did.DidService.create
import id.walt.services.did.DidService.loadDidEbsi
import id.walt.services.essif.EssifClient.authApi
import id.walt.services.essif.EssifClient.onboard
import id.walt.services.essif.didebsi.DidEbsiService
import id.walt.services.key.KeyService

class DidEbsi {
    companion object {
        private val keyService = KeyService.getService()
        private val didEbsiService = DidEbsiService.getService()

        fun run() {
            println("SSI Kit example how to create a DID EBSI")

            ///////////////////////////////////////////////////////////////////////////
            // Create an asymmetric keypair
            ///////////////////////////////////////////////////////////////////////////
            val keyId = keyService.generate(KeyAlgorithm.EdDSA_Ed25519)
            println("EBSI key generated: $keyId")
            val ethKeyId = keyService.generate(KeyAlgorithm.ECDSA_Secp256k1)
            println("ETH key generated: $ethKeyId")

            ///////////////////////////////////////////////////////////////////////////
            // Create an EBSI compliant Decentralized Identifier
            ///////////////////////////////////////////////////////////////////////////
            val didEbsi =
                create(DidMethod.ebsi, keyId.id) // Note, that the DID is stored in /data/did/create
            println("EBSI DID created : $didEbsi")
            val didDoc = loadDidEbsi(didEbsi)
            print("\nDID EBSI Document loaded:\n$didDoc")

            ///////////////////////////////////////////////////////////////////////////
            // Registering the DID on the ledger
            // Pre requisite: put token from https://app.preprod.ebsi.eu/users-onboarding/ in the text field and click "Update Bearer Token"
            ///////////////////////////////////////////////////////////////////////////
            onboard(didEbsi, null)
            authApi(didEbsi)
            didEbsiService.registerDid(didEbsi, ethKeyId.id)
            print("\nDID EBSI Document successfully registered:\n$didEbsi")
            print("\nCheck EBSI API at https://api.preprod.ebsi.eu/docs/?urls.primaryName=DID%20Registry%20API:\n")
        }
    }
}