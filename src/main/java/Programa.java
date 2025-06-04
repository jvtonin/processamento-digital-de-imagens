import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Programa extends JFrame {

    private JLabel labelOriginal;
    private JLabel labelTransformado;
    private BufferedImage imagemOriginalImportada;
    JPanel painel = new JPanel();
    JPanel painelDeImagens = new JPanel(new GridLayout(1, 2));

    public Programa() {
        setTitle("Programa de Processamento de Imagens");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    
        JLabel label = new JLabel("Iago Mauri e João Tonin");
    
        painelDeImagens = new JPanel(new GridBagLayout());
    
        labelOriginal = new JLabel();
        labelOriginal.setPreferredSize(new Dimension(400, 300));
        labelOriginal.setHorizontalAlignment(JLabel.CENTER);
    
        labelTransformado = new JLabel();
        labelTransformado.setPreferredSize(new Dimension(400, 300));
        labelTransformado.setHorizontalAlignment(JLabel.CENTER);
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.gridy = 0;
    
        gbc.gridx = 0;
        painelDeImagens.add(labelOriginal, gbc);
    
        gbc.gridx = 1;
        painelDeImagens.add(labelTransformado, gbc);
    
        JMenuBar barraDoMenu = new JMenuBar();
    
        JMenu arquivos = new JMenu("Arquivos");
        JMenuItem abrirArquivos = new JMenuItem("Abrir");
        abrirArquivos.addActionListener(e -> selectFile());
    
        JMenuItem salvarArquivos = new JMenuItem("Salvar");
        salvarArquivos.addActionListener(e -> salvarProjeto());
    
        JMenuItem sobreArquivos = new JMenuItem("Sobre");
        sobreArquivos.addActionListener(e -> sobreArq());
    
        JMenuItem sairArquivos = new JMenuItem("Sair");
        sairArquivos.addActionListener(e -> sairSistema());
    
        arquivos.add(abrirArquivos);
        arquivos.add(salvarArquivos);
        arquivos.add(sobreArquivos);
        arquivos.add(sairArquivos);
        barraDoMenu.add(arquivos);
    
        Transformacoes transformacao = new Transformacoes();
    
        JMenu transformacoes = new JMenu("Transformações Geométricas");
    
        JMenuItem transladarTrans = new JMenuItem("Transladar");
        transladarTrans.addActionListener(e -> transformacao.transladarImagem(labelOriginal, labelTransformado, imagemOriginalImportada));
    
        JMenuItem rotacionarTrans = new JMenuItem("Rotacionar");
        rotacionarTrans.addActionListener(e -> transformacao.rotacionaImagem(labelOriginal, labelTransformado, imagemOriginalImportada));
    
        JMenuItem espelharTrans = new JMenuItem("Espelhar");
        espelharTrans.addActionListener(e -> transformacao.espelharImagem(labelOriginal, labelTransformado, imagemOriginalImportada));
    
        JMenuItem aumentarTrans = new JMenuItem("Aumentar/Diminuir");
        aumentarTrans.addActionListener(e -> transformacao.escalarImagem(labelOriginal, labelTransformado, imagemOriginalImportada));
    
        transformacoes.add(transladarTrans);
        transformacoes.add(rotacionarTrans);
        transformacoes.add(espelharTrans);
        transformacoes.add(aumentarTrans);
        barraDoMenu.add(transformacoes);
    
        Filtros filtro = new Filtros();
    
        JMenu filtros = new JMenu("Filtros");
    
        JMenuItem filtroGrayscale = new JMenuItem("Grayscale");
        filtroGrayscale.addActionListener(e -> filtro.aplicaGrayscale(labelOriginal, labelTransformado, imagemOriginalImportada));
    
        JMenuItem filtroPassaBaixa = new JMenuItem("Passa Baixa");
        filtroPassaBaixa.addActionListener(e -> filtro.passaBaixa(labelOriginal, labelTransformado, imagemOriginalImportada));
    
        JMenuItem filtroPassaAlta = new JMenuItem("Passa Alta");
        filtroPassaAlta.addActionListener(e -> filtro.passaAlta(labelOriginal, labelTransformado, imagemOriginalImportada));
    
        JMenuItem filtroThreshold = new JMenuItem("Threshold");
        filtroThreshold.addActionListener(e -> filtro.aplicaThreshold(labelOriginal, labelTransformado, imagemOriginalImportada));
    
        filtros.add(filtroGrayscale);
        filtros.add(filtroPassaBaixa);
        filtros.add(filtroPassaAlta);
        filtros.add(filtroThreshold);
        barraDoMenu.add(filtros);
    
        setJMenuBar(barraDoMenu);
        painel.add(label);
        add(painel, BorderLayout.NORTH);
        add(painelDeImagens, BorderLayout.CENTER);
    }
    
    private ImageIcon selectFile() {
        ImageIcon conteudo = null;
        File caminho = null;
        String caminhoDoArquivo = null;
    
        try {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Imagens (*.png, *.jpg, *.jpeg)", "png", "jpg", "jpeg"));
    
            int ok = jFileChooser.showOpenDialog(null);
    
            if (ok == JFileChooser.APPROVE_OPTION) {
                caminho = jFileChooser.getSelectedFile();
                caminhoDoArquivo = caminho.getAbsolutePath();
    
                String extensao = caminhoDoArquivo.substring(caminhoDoArquivo.lastIndexOf('.') + 1).toLowerCase();
                if (!extensao.equals("png") && !extensao.equals("jpg") && !extensao.equals("jpeg")) {
                    JOptionPane.showMessageDialog(null, "Formato de arquivo não suportado.\nUse PNG, JPG ou JPEG.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return null;
                }
    
                conteudo = new ImageIcon(caminhoDoArquivo);
                Image img = conteudo.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
                labelOriginal.setIcon(new ImageIcon(img));
                imagemOriginalImportada = ImageIO.read(caminho);
            } 
            else {
                jFileChooser.cancelSelection();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao carregar o arquivo.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    
        return conteudo;
    }
    

    private void salvarProjeto() {

        BufferedImage imagem = new BufferedImage(painelDeImagens.getWidth(), painelDeImagens.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = imagem.createGraphics();
        painelDeImagens.paint(g2d);
        g2d.dispose();

        JFileChooser jFile = new JFileChooser();

        jFile.setDialogTitle("Escolha o lugar para salvar o arquivo");
        jFile.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imagens PNG", "png"));

        int ok = jFile.showSaveDialog(this);

        if (ok == JFileChooser.APPROVE_OPTION) {
            File destinoSalvar = jFile.getSelectedFile();
            if (!destinoSalvar.getName().toLowerCase().endsWith(".png")) {
                destinoSalvar = new File(destinoSalvar.getAbsolutePath() + ".png");
            }
            try {
                ImageIO.write(imagem, "png", destinoSalvar);
                JOptionPane.showMessageDialog(this, "Imagem salva em: " + destinoSalvar.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao salvar a imagem!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void sobreArq() {
        JOptionPane.showMessageDialog(null, "Versão 1.4.1 PDI System", "Sobre", JOptionPane.INFORMATION_MESSAGE);
    }

    private void sairSistema() {
        int resposta = JOptionPane.showConfirmDialog(this, "Deseja sair do programa?","Sair", JOptionPane.YES_NO_OPTION);

        if (resposta == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
    
}