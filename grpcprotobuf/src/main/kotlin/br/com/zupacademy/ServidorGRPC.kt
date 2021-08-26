package br.com.zupacademy

import com.google.protobuf.Timestamp
import io.grpc.ServerBuilder
import io.grpc.stub.StreamObserver
import java.time.LocalDateTime
import java.time.ZoneId

fun main() {

    val serverGrpc = ServerBuilder
        .forPort(50051)
        .addService(FuncionarioEndpoint())
        .build()

    serverGrpc.start()
    serverGrpc.awaitTermination() //sergura a thread criada

}

class FuncionarioEndpoint: FuncionarioServiceGrpc.FuncionarioServiceImplBase() {
    override fun cadastrar(request: FuncionarioRequest?, responseObserver: StreamObserver<FuncionarioResponse>?) {

        println(request!!)

        var nome: String? = request.name
        if (!request.hasField(FuncionarioRequest.getDescriptor().findFieldByName("name"))) {
            nome = "[???]"
        }
        val instant = LocalDateTime.now().atZone(ZoneId.of("UTC")).toInstant()
        val criadoEm = Timestamp.newBuilder()
            .setSeconds(instant.epochSecond)
            .setNanos(instant.nano)
            .build()
        val response = FuncionarioResponse.newBuilder()
            .setName(nome)
            .setCriadoEm(criadoEm)
            .build()

        responseObserver?.onNext(response)
        responseObserver?.onCompleted()
    }

}