package org.clienteservidor.dao;

import org.clienteservidor.config.ConnectionFactory;
import org.clienteservidor.model.Curso;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CursoDAO implements ICursoDAO {

    @Override
    public Curso create(Curso curso) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String query = "INSERT into curso " +
                    "(nome, sigla, area) " +
                    "values (?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, curso.getNome());
            preparedStatement.setString(2, curso.getSigla());
            preparedStatement.setString(3, curso.getArea().toString());

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                curso.setCodigo(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return curso;
    }

    @Override
    public void update(Curso curso) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String query = "UPDATE curso SET nome = ?, sigla = ?, area = ? WHERE codigo = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, curso.getNome());
            preparedStatement.setString(2, curso.getSigla());
            preparedStatement.setString(3, curso.getArea().toString());
            preparedStatement.setLong(4, curso.getCodigo());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Curso curso) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String query = "DELETE FROM curso WHERE codigo = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, curso.getCodigo());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Curso> findAll() {
        List<Curso> cursos = new ArrayList<>();
        String query = "SELECT * FROM curso";

        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Curso curso = new Curso();
                curso.setCodigo(resultSet.getLong("codigo"));
                curso.setNome(resultSet.getString("nome"));
                curso.setSigla(resultSet.getString("sigla"));
                curso.setArea(Curso.Area.valueOf(resultSet.getString("area")));
                cursos.add(curso);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cursos;
    }

    @Override
    public Optional<Curso> findById(Long id) {
        Curso curso = null;
        String query = "SELECT * FROM curso WHERE codigo = ?";

        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                curso = new Curso();
                curso.setCodigo(resultSet.getLong("codigo"));
                curso.setNome(resultSet.getString("nome"));
                curso.setSigla(resultSet.getString("sigla"));
                curso.setArea(Curso.Area.valueOf(resultSet.getString("area")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(curso);
    }

    @Override
    public List<Curso> findByArea(Curso.Area area) {
        List<Curso> cursos = new ArrayList<>();
        String query = "SELECT * FROM curso WHERE area = ?";

        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, area.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Curso curso = new Curso();
                curso.setCodigo(resultSet.getLong("codigo"));
                curso.setNome(resultSet.getString("nome"));
                curso.setSigla(resultSet.getString("sigla"));
                curso.setArea(Curso.Area.valueOf(resultSet.getString("area")));
                cursos.add(curso);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cursos;
    }

    @Override
    public Optional<Curso> findBySigla(String sigla) {
        Curso curso = null;
        String query = "SELECT * FROM curso WHERE sigla = ?";

        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, sigla);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                curso = new Curso();
                curso.setCodigo(resultSet.getLong("codigo"));
                curso.setNome(resultSet.getString("nome"));
                curso.setSigla(resultSet.getString("sigla"));
                curso.setArea(Curso.Area.valueOf(resultSet.getString("area")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(curso);
    }
}
