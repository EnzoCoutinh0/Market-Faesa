package com.example.marketfaesamain;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BancoDados {

    // Mantém o arquivo de serviços existente
    private static final String ARQUIVO_SERVICOS = "servicos.dat";
    // Novo arquivo para os usuários
    private static final String ARQUIVO_USUARIOS = "usuarios.dat";

    public static void salvar(List<Servico> servicos) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO_SERVICOS))) {
            oos.writeObject(new ArrayList<>(servicos));
        } catch (IOException e) {
            System.err.println("Erro ao salvar serviços: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Servico> carregar() {
        File arquivo = new File(ARQUIVO_SERVICOS);
        if (!arquivo.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Servico>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar serviços: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void salvarUsuarios(List<Usuario> usuarios) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO_USUARIOS))) {
            oos.writeObject(new ArrayList<>(usuarios));
        } catch (IOException e) {
            System.err.println("Erro ao salvar usuários: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Usuario> carregarUsuarios() {
        File arquivo = new File(ARQUIVO_USUARIOS);
        if (!arquivo.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Usuario>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar usuários: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}