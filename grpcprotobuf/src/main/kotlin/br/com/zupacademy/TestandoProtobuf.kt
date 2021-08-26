package br.com.zupacademy

import java.io.FileInputStream
import java.io.FileOutputStream

fun main() {

    //craindo o Objeto Request
    val request1 = FuncionarioRequest.newBuilder()
        .setName("Clayton Pereira")
        .setCpf("111.111.111-11")
        .setSalario(2000.20)
        .setCargo(Cargo.QA)
        .addEnderecos(
            FuncionarioRequest.Endereco.newBuilder()
                .setLogradouro("Praça Tabajaras")
                .setCep("00000-000")
                .setComplemento("Casa")
                .build()
        )
        .build()

    println(request1)
    // gera o aquivo binário no disco (SERIALIZA)
    // este é o modelo de arquivo que trafegará na rede
    request1.writeTo(FileOutputStream("funcionario-request.bin"))


    //alterando o Objeto Request
    val request2 = FuncionarioRequest.newBuilder()
        .mergeFrom(FileInputStream("funcionario-request.bin")) // lê o arquivo (DESERIALIZA)

    request2.setCargo(Cargo.GERENTE).build() // altera o cargo

    println(request2)

}