/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.service;

import com.api.dao.PessoaDAO;
import com.api.model.Pessoa;
import java.util.List;

/**
 *
 * @author Jackson Neves
 */
public class PessoaService {
     private PessoaDAO pessoaDAO;

    public PessoaService() {
        this.pessoaDAO = new PessoaDAO();
    }

    public List<Pessoa> listarPessoas() {
        return pessoaDAO.listarPessoas();
    }

    public Pessoa buscarPessoaPorId(int id) {
        return pessoaDAO.buscarPessoaPorId(id);
    }

    public void adicionarPessoa(Pessoa pessoa) {
        pessoaDAO.adicionarPessoa(pessoa);
    }

    public void atualizarPessoa(Pessoa pessoa) {
        pessoaDAO.atualizarPessoa(pessoa);
    }

    public void deletarPessoa(int id) {
        pessoaDAO.deletarPessoa(id);
    }
}
