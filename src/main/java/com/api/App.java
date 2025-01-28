/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.api;

import com.api.controller.PessoaController;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

/**
 *
 * @author Jackson Neves
 */
public class App {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        PessoaController pessoaController = new PessoaController();

        // Endpoint para listar todas as pessoas
        server.createContext("/pessoas", exchange -> {
            try {
                if (exchange.getRequestMethod().equalsIgnoreCase("GET")) {
                    String response = pessoaController.listarPessoas();
                    exchange.getResponseHeaders().set("Content-Type", "application/json");
                    exchange.sendResponseHeaders(200, response.getBytes().length);
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(response.getBytes());
                    }
                } else {
                    exchange.sendResponseHeaders(405, -1); // Método não permitido
                }
            } catch (Exception e) {
                handleError(exchange, e);
            }
        });

        // Endpoint para operações com uma única pessoa
        server.createContext("/pessoa", exchange -> {
            try {
                String query = exchange.getRequestURI().getQuery();
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                
                if (exchange.getRequestMethod().equalsIgnoreCase("GET") && query != null && query.startsWith("id=")) {
                    
                    try{
                        int id = Integer.parseInt(query.split("=")[1]);
                        String response = pessoaController.buscarPessoaPorId(id);                                                
                        exchange.sendResponseHeaders(200, response.getBytes().length);
                        try (OutputStream os = exchange.getResponseBody()) {
                            os.write(response.getBytes());
                        }
                    }catch(Exception e){
                        handleError(exchange, e);
                    }
                    
                } else if (exchange.getRequestMethod().equalsIgnoreCase("POST")) {
                    
                    try{
                        String requestBody = new String(exchange.getRequestBody().readAllBytes());
                        pessoaController.adicionarPessoa(requestBody);                        
                        String msg = "{\"message\":\"Pessoa criada com sucesso\"}";                        
                        exchange.sendResponseHeaders(201, msg.getBytes().length); // Criado com sucesso
                        try(OutputStream os = exchange.getResponseBody()) {
                                os.write(msg.getBytes());
                           }
                    }catch(Exception e){
                        handleError(exchange, e);
                    }
                    
                } else if (exchange.getRequestMethod().equalsIgnoreCase("PUT") && query != null && query.startsWith("id=")) {
                    try{
                        int id = Integer.parseInt(query.split("=")[1]);                        
                        String requestBody = new String(exchange.getRequestBody().readAllBytes());                        
                        pessoaController.atualizarPessoa(id, requestBody);
                        String msg = "{\"message\":\"Pessoa atualizada com sucesso\"}";
                        exchange.sendResponseHeaders(200, msg.getBytes().length); // Atualizado com sucesso                                                
                        try(OutputStream os = exchange.getResponseBody()) {
                                os.write(msg.getBytes());
                            }
                    }catch(Exception e){
                        handleError(exchange, e);
                    }
                    
                } else if (exchange.getRequestMethod().equalsIgnoreCase("DELETE") && query != null && query.startsWith("id=")) {
                    
                    try{                    
                        int id = Integer.parseInt(query.split("=")[1]);
                        pessoaController.deletarPessoa(id);                        
                        String msg = "{\"message\":\"Pessoa removido com sucesso\"}";
                        exchange.sendResponseHeaders(200, msg.getBytes().length); // Sem conteúdo (removido com sucesso)                        
                        try(OutputStream os = exchange.getResponseBody()){
                            os.write(msg.getBytes());
                        }
                    }catch(Exception e){
                        handleError(exchange, e);
                    }
                    
                } else {
                    String msg = "{\"message\":\"Dados de Pessoa ou URI deve esta preenchido de maneira errada\"}";                    
                    exchange.sendResponseHeaders(400, msg.getBytes().length); // Requisição inválida
                    try(OutputStream os = exchange.getResponseBody()){
                            os.write(msg.getBytes());
                        }
                }
            } catch (Exception e) {                
                handleError(exchange, e);
            }
        });

        server.setExecutor(null); // Executor padrão
        server.start();
        System.out.println("Servidor iniciado na porta 8080 - http://localhost:8080");
    }

    /**
     * Método para lidar com erros genéricos e retornar uma resposta apropriada.
     */
    private static void handleError(com.sun.net.httpserver.HttpExchange exchange, Exception e) {
        try {
            String errorResponse = "{\"error\":\"" + e.getMessage() + "\"}";
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(500, errorResponse.getBytes().length); // Erro interno do servidor
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(errorResponse.getBytes());
            }
            e.printStackTrace(); // Loga a pilha de erros no console
        } catch (IOException ioException) {
            System.out.println("Erro!!!!");
            ioException.printStackTrace();            
        }
    }
}
