package org.clienteservidor.interfaceUI;

import org.clienteservidor.dao.IAlunoDAO;
import org.clienteservidor.dao.ICursoDAO;
import org.clienteservidor.model.Alunos;
import org.clienteservidor.model.Curso;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class InterfaceEx03 extends JFrame {
    private JPanel painelPrincipal;
    private JTable tableAlunos;
    private JTable tableCursos;
    private DefaultTableModel alunosModel;
    private DefaultTableModel cursosModel;
    private IAlunoDAO alunoDAO;
    private ICursoDAO cursoDAO;
    private JComboBox<String> comboCurso;

    public InterfaceEx03(IAlunoDAO alunoDAO, ICursoDAO cursoDAO) {
        this.alunoDAO = alunoDAO;
        this.cursoDAO = cursoDAO;

        setTitle("Gestão de Alunos e Cursos");
        setSize(1600, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        tableAlunos = new JTable();
        tableCursos = new JTable();

        setupTableAlunos();
        setupTableCursos();

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(tableAlunos), new JScrollPane(tableCursos));
        splitPane.setDividerLocation(300);
        add(splitPane, BorderLayout.CENTER);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(2, 1));
        formPanel.add(criarFormularioAlunos());
        formPanel.add(criarFormularioCursos());

        add(formPanel, BorderLayout.SOUTH);

        carregarDadosAlunos();
        carregarDadosCursos();
    }

    private void setupTableAlunos() {
        String[] colunasAlunos = {"Matrícula", "Nome", "Telefone", "Maioridade", "Curso", "Sexo"};
        alunosModel = new DefaultTableModel(colunasAlunos, 0);
        tableAlunos.setModel(alunosModel);
    }

    private void setupTableCursos() {
        String[] colunasCursos = {"Código", "Nome", "Sigla", "Área"};
        cursosModel = new DefaultTableModel(colunasCursos, 0);
        tableCursos.setModel(cursosModel);
    }

    private JPanel criarFormularioAlunos() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Margens internas
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Nome:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        JTextField campoNome = new JTextField(20);
        panel.add(campoNome, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Telefone:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        JTextField campoTelefone = new JTextField(15);
        panel.add(campoTelefone, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Curso:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        comboCurso = new JComboBox<>();
        comboCurso.addItem("Selecione um curso");
        List<Curso> cursos = cursoDAO.findAll();
        for (Curso curso : cursos) {
            comboCurso.addItem(curso.getSigla());
        }
        panel.add(comboCurso, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Sexo:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        JRadioButton masculino = new JRadioButton("Masculino");
        JRadioButton feminino = new JRadioButton("Feminino");

        ButtonGroup sexoGroup = new ButtonGroup();
        sexoGroup.add(masculino);
        sexoGroup.add(feminino);

        JPanel sexoPanel = new JPanel();
        sexoPanel.add(masculino);
        sexoPanel.add(feminino);
        panel.add(sexoPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        JCheckBox checkMaioridade = new JCheckBox("Maioridade");
        panel.add(checkMaioridade, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnAdicionarAluno = new JButton("Adicionar Aluno");
        panel.add(btnAdicionarAluno, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnAtualizarAluno = new JButton("Atualizar Aluno");
        panel.add(btnAtualizarAluno, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnExcluirAluno = new JButton("Excluir Aluno");
        panel.add(btnExcluirAluno, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnAdicionarAluno, gbc);

        gbc.gridx = 1;
        panel.add(btnAtualizarAluno, gbc);

        gbc.gridx = 2;
        panel.add(btnExcluirAluno, gbc);

        btnAtualizarAluno.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableAlunos.getSelectedRow();
                if (selectedRow != -1) {
                    Long matricula = (Long) alunosModel.getValueAt(selectedRow, 0);
                    String nome = (String) alunosModel.getValueAt(selectedRow, 1);
                    String telefone = (String) alunosModel.getValueAt(selectedRow, 2);

                    Object maioridadeObj = alunosModel.getValueAt(selectedRow, 3);
                    boolean maioridade = false;
                    if (maioridadeObj instanceof Boolean) {
                        maioridade = (Boolean) maioridadeObj;
                    } else if (maioridadeObj instanceof String) {
                        maioridade = Boolean.parseBoolean((String) maioridadeObj);
                    }

                    String curso = (String) alunosModel.getValueAt(selectedRow, 4);
                    String sexo = (String) alunosModel.getValueAt(selectedRow, 5);

                    Alunos alunoAtualizado = new Alunos(matricula, nome, telefone, maioridade, curso, sexo);
                    alunoDAO.update(alunoAtualizado);
                    carregarDadosAlunos();
                    limparCamposAlunos(campoNome, campoTelefone, comboCurso, sexoGroup, checkMaioridade);
                } else {
                    JOptionPane.showMessageDialog(panel, "Selecione uma linha para atualizar.");
                }
            }
        });

        btnExcluirAluno.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableAlunos.getSelectedRow();
                if (selectedRow != -1) {
                    Long matricula = (Long) alunosModel.getValueAt(selectedRow, 0);
                    Alunos alunoRemover = alunoDAO.findById(matricula).orElse(null);

                    if (alunoRemover != null) {
                        alunoDAO.delete(alunoRemover);
                        carregarDadosAlunos();
                        limparCamposAlunos(campoNome, campoTelefone, comboCurso, sexoGroup, checkMaioridade);
                    } else {
                        JOptionPane.showMessageDialog(panel, "Erro ao tentar excluir o aluno.");
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "Selecione uma linha para excluir.");
                }
            }
        });

        btnAdicionarAluno.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = campoNome.getText();
                String telefone = campoTelefone.getText();
                boolean maioridade = checkMaioridade.isSelected();
                String curso = (String) comboCurso.getSelectedItem();

                String sexo;
                if (masculino.isSelected()) {
                    sexo = "M";
                } else if (feminino.isSelected()) {
                    sexo = "F";
                } else {
                    sexo = "";
                }

                if (!curso.equals("Selecione um curso")) {
                    Alunos novoAluno = new Alunos();
                    novoAluno.setNome(nome);
                    novoAluno.setTelefone(telefone);
                    novoAluno.setMaioridade(maioridade);
                    novoAluno.setCurso(curso);
                    novoAluno.setSexo(sexo);

                    alunoDAO.create(novoAluno);
                    carregarDadosAlunos();
                    limparCamposAlunos(campoNome, campoTelefone, comboCurso, sexoGroup, checkMaioridade);
                } else {
                    JOptionPane.showMessageDialog(panel, "Por favor, selecione um curso.");
                }
            }
        });
        return panel;
    }

    private JPanel criarFormularioCursos() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Margens internas
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Nome do Curso:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        JTextField campoNomeCurso = new JTextField(20);
        panel.add(campoNomeCurso, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Sigla:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        JTextField campoSiglaCurso = new JTextField(10);
        panel.add(campoSiglaCurso, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Área:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        JComboBox<Curso.Area> comboArea = new JComboBox<>(Curso.Area.values());
        panel.add(comboArea, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnAdicionarCurso = new JButton("Adicionar Curso");
        panel.add(btnAdicionarCurso, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnAtualizarCurso = new JButton("Atualizar Curso");
        panel.add(btnAtualizarCurso, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnExcluirCurso = new JButton("Excluir Curso");
        panel.add(btnExcluirCurso, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnAdicionarCurso, gbc);

        gbc.gridx = 1;
        panel.add(btnAtualizarCurso, gbc);

        gbc.gridx = 2;
        panel.add(btnExcluirCurso, gbc);

        btnAtualizarCurso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableCursos.getSelectedRow();
                if (selectedRow != -1) {
                    Long codigo = (Long) cursosModel.getValueAt(selectedRow, 0);
                    String nome = (String) cursosModel.getValueAt(selectedRow, 1);
                    String sigla = (String) cursosModel.getValueAt(selectedRow, 2);
                    Curso.Area area = (Curso.Area) cursosModel.getValueAt(selectedRow, 3);

                    Curso cursoAtualizado = new Curso(codigo, nome, sigla, area);
                    cursoDAO.update(cursoAtualizado);
                    carregarDadosCursos();
                } else {
                    JOptionPane.showMessageDialog(panel, "Selecione uma linha para atualizar.");
                }
                atualizarComboCursos(comboCurso);
            }
        });

        btnExcluirCurso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tableCursos.getSelectedRow();
                if (selectedRow != -1) {
                    Long codigo = (Long) cursosModel.getValueAt(selectedRow, 0);
                    Curso cursoRemover = cursoDAO.findById(codigo).orElse(null);

                    if (cursoRemover != null) {
                        cursoDAO.delete(cursoRemover);
                        carregarDadosCursos();  // Atualiza a tabela
                    } else {
                        JOptionPane.showMessageDialog(panel, "Erro ao tentar excluir o curso.");
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "Selecione uma linha para excluir.");
                }
                atualizarComboCursos(comboCurso);
            }
        });

        btnAdicionarCurso.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = campoNomeCurso.getText();
                String sigla = campoSiglaCurso.getText();
                Curso.Area area = (Curso.Area) comboArea.getSelectedItem();

                Curso novoCurso = new Curso();
                novoCurso.setNome(nome);
                novoCurso.setSigla(sigla);
                novoCurso.setArea(area);

                cursoDAO.create(novoCurso);
                carregarDadosCursos();

                atualizarComboCursos(comboCurso);
            }
        });

        return panel;
    }

    private void carregarDadosAlunos() {
        alunosModel.setRowCount(0);
        List<Alunos> alunos = alunoDAO.findAll();
        for (Alunos aluno : alunos) {
            alunosModel.addRow(new Object[]{aluno.getMatricula(), aluno.getNome(), aluno.getTelefone(),
                    aluno.isMaioridade(), aluno.getCurso(), aluno.getSexo()});
        }
    }

    private void carregarDadosCursos() {
        cursosModel.setRowCount(0);
        List<Curso> cursos = cursoDAO.findAll();
        for (Curso curso : cursos) {
            cursosModel.addRow(new Object[]{curso.getCodigo(), curso.getNome(), curso.getSigla(), curso.getArea()});
        }
    }

    private void atualizarComboCursos(JComboBox<String> comboCurso) {
        comboCurso.removeAllItems();
        comboCurso.addItem("Selecione um curso");
        List<Curso> cursos = cursoDAO.findAll();
        for (Curso curso : cursos) {
            comboCurso.addItem(curso.getSigla());
        }
    }

    private void limparCamposAlunos(JTextField campoNome, JTextField campoTelefone, JComboBox<String> comboCurso, ButtonGroup sexoGroup, JCheckBox checkMaioridade) {
        campoNome.setText("");
        campoTelefone.setText("");
        comboCurso.setSelectedIndex(0);
        sexoGroup.clearSelection();
        checkMaioridade.setSelected(false);
    }


}