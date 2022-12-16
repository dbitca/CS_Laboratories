package com.example.testlab5;

import com.example.testlab5.Classical_Ciphers.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @GetMapping("/hellp")
    public String sayHello() {
        return "Hello! This is home page.";
    }

    @GetMapping("/caesar/{plaintext}/{key}")
    public String accesCaesarCipher(@PathVariable("plaintext") String plaintext, @PathVariable("key") String key, @RequestBody DecryptionResult decryptionResult) {
        SimpleCaesar simpleCaesar = new SimpleCaesar();
        return simpleCaesar.encrypt(plaintext, key);
    }

    @GetMapping("/permutation/{plaintext}/{key})")
    public String accesPermutationCaesar(@PathVariable("plaintext") String plaintext, @PathVariable("key") String key, @RequestBody DecryptionResult decryptionResult) {
        PermutationCaesar permutationCaesar = new PermutationCaesar();
        return permutationCaesar.encrypt(plaintext, key);
    }

    @GetMapping("/vigenere/{plaintext}/{key}")
    public String accesVigenere(@PathVariable("plaintext") String plaintext, @PathVariable("key") String key, @RequestBody DecryptionResult decryptionResult){
        Vigenere vigenere = new Vigenere();
        return vigenere.encrypt(plaintext, key);
    }

    @GetMapping("/playfair/{plaintext}/{key}")
    public String accessPlayfair (@PathVariable("plaintext") String plaintext, @PathVariable("key") String key, @RequestBody DecryptionResult decryptionResult){
        Playfair playfair = new Playfair();
        return playfair.encrypt(plaintext, key);
    }

}
