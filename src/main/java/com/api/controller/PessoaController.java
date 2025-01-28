/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.controller;

import com.api.model.Pessoa;
import com.api.service.PessoaService;
import com.google.gson.Gson;
import java.util.List;

/**
 *
 * @author Jackson Neves
 */
public class PessoaController {
     private PessoaService pessoaService;
    private Gson gson;

    public PessoaController() {
        this.pessoaService = new PessoaService();
        this.gson = new Gson();
    }

    public String listarPessoas() {
        List<Pessoa> pessoas = pessoaService.listarPessoas();
        return gson.toJson(pessoas);
    }

    public String buscarPessoaPorId(int id) {
        Pessoa pessoa = pessoaService.buscarPessoaPorId(id);
        return gson.toJson(pessoa);
    }

    public void adicionarPessoa(String json) {
        Pessoa pessoa = gson.fromJson(json, Pessoa.class);
        pessoaService.adicionarPessoa(pessoa);
    }

    public void atualizarPessoa(int id, String json) {
        Pessoa pessoa = gson.fromJson(json, Pessoa.class);
        pessoa.setIdPessoa(id);
        pessoaService.atualizarPessoa(pessoa);
    }

    public void deletarPessoa(int id) {
        pessoaService.deletarPessoa(id);
    }
}
