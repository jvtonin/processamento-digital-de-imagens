import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Morfologia {

    public void aplicaAfinamento(JLabel labelOriginal, JLabel labelTransformado, BufferedImage imagemOriginalImportada) {
        if (labelOriginal.getIcon() == null) {
            JOptionPane.showMessageDialog(null, "Por favor, insira uma imagem primeiro", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ImageIcon icon = (ImageIcon) labelOriginal.getIcon();
        Image img = icon.getImage();

        BufferedImage imagem = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_BYTE_BINARY);
        Graphics2D g2d = imagem.createGraphics();
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        int largura = imagem.getWidth();
        int altura = imagem.getHeight();
        int[][] matriz = new int[altura][largura];

        for (int y = 0; y < altura; y++) {
            for (int x = 0; x < largura; x++) {
                int pixel = imagem.getRGB(x, y) & 0xFFFFFF;
                matriz[y][x] = (pixel == 0xFFFFFF) ? 0 : 1;
            }
        }

        boolean alterado;
        do {
            alterado = false;
            List<Point> paraRemover = new ArrayList<>();

            for (int y = 1; y < altura - 1; y++) {
                for (int x = 1; x < largura - 1; x++) {
                    if (matriz[y][x] == 1) {
                        int[] viz = obterVizinhos(matriz, x, y);
                        int vizinhos = 0;
                        for (int v : viz) vizinhos += v;
                        int transicoes = contarTransicoes(viz);

                        if (
                            vizinhos >= 2 && vizinhos <= 6 &&
                            transicoes == 1 &&
                            viz[0] * viz[2] * viz[4] == 0 &&
                            viz[2] * viz[4] * viz[6] == 0
                        ) {
                            paraRemover.add(new Point(x, y));
                            alterado = true;
                        }
                    }
                }
            }
            for (Point p : paraRemover) matriz[p.y][p.x] = 0;
        } while (alterado);

        BufferedImage afinada = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < altura; y++) {
            for (int x = 0; x < largura; x++) {
                int cor = matriz[y][x] == 1 ? Color.BLACK.getRGB() : Color.WHITE.getRGB();
                afinada.setRGB(x, y, cor);
            }
        }

        labelTransformado.setIcon(new ImageIcon(afinada));

        int resposta = JOptionPane.showConfirmDialog(null, "Deseja fazer mais alterações na imagem?", "Finalizar?", JOptionPane.YES_NO_OPTION);
        if (resposta == JOptionPane.YES_OPTION) {
            labelOriginal.setIcon(new ImageIcon(afinada));
            labelTransformado.setIcon(null);
        } else {
            if (imagemOriginalImportada != null) {
                Image imgOriginal = imagemOriginalImportada.getScaledInstance(400, 300, Image.SCALE_SMOOTH);
                labelOriginal.setIcon(new ImageIcon(imgOriginal));
            }
        }
    }

    private int[] obterVizinhos(int[][] matriz, int x, int y) {
        return new int[]{
            matriz[y - 1][x],
            matriz[y - 1][x + 1],
            matriz[y][x + 1],
            matriz[y + 1][x + 1],
            matriz[y + 1][x],
            matriz[y + 1][x - 1],
            matriz[y][x - 1],
            matriz[y - 1][x - 1]
        };
    }

    private int contarTransicoes(int[] viz) {
        int cont = 0;
        for (int i = 0; i < viz.length; i++) {
            int atual = viz[i];
            int proximo = viz[(i + 1) % viz.length];
            if (atual == 0 && proximo == 1) cont++;
        }
        return cont;
    }

    public void aplicaDilatacao(JLabel labelOriginal, JLabel labelTransformado, BufferedImage imagemOriginalImportada) {
        if (labelOriginal.getIcon() == null) {
            JOptionPane.showMessageDialog(null, "Por favor, insira uma imagem primeiro", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ImageIcon icon = (ImageIcon) labelOriginal.getIcon();
        Image img = icon.getImage();

        BufferedImage imagem = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_BYTE_BINARY);
        Graphics2D g2d = imagem.createGraphics();
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        int largura = imagem.getWidth();
        int altura = imagem.getHeight();

        int[][] original = new int[altura][largura];
        int[][] resultado = new int[altura][largura];

        for (int y = 0; y < altura; y++) {
            for (int x = 0; x < largura; x++) {
                int pixel = imagem.getRGB(x, y) & 0xFFFFFF;
                original[y][x] = (pixel == 0xFFFFFF) ? 0 : 1;
            }
        }

        for (int y = 1; y < altura - 1; y++) {
            for (int x = 1; x < largura - 1; x++) {
                boolean deveAtivar = false;
                for (int j = -1; j <= 1; j++) {
                    for (int i = -1; i <= 1; i++) {
                        if (original[y + j][x + i] == 1) {
                            deveAtivar = true;
                            break;
                        }
                    }
                    if (deveAtivar) break;
                }
                resultado[y][x] = deveAtivar ? 1 : 0;
            }
        }

        BufferedImage dilatada = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < altura; y++) {
            for (int x = 0; x < largura; x++) {
                int cor = resultado[y][x] == 1 ? Color.BLACK.getRGB() : Color.WHITE.getRGB();
                dilatada.setRGB(x, y, cor);
            }
        }

        labelTransformado.setIcon(new ImageIcon(dilatada));
        int resposta = JOptionPane.showConfirmDialog(null, "Deseja fazer mais alterações na imagem?", "Finalizar?", JOptionPane.YES_NO_OPTION);
        if (resposta == JOptionPane.YES_OPTION) {
            labelOriginal.setIcon(new ImageIcon(dilatada));
            labelTransformado.setIcon(null);
        } else {
            if (imagemOriginalImportada != null) {
                Image imgOriginal = imagemOriginalImportada.getScaledInstance(400, 300, Image.SCALE_SMOOTH);
                labelOriginal.setIcon(new ImageIcon(imgOriginal));
            }
        }
    }

    public void aplicaErosao(JLabel labelOriginal, JLabel labelTransformado, BufferedImage imagemOriginalImportada) {
        if (labelOriginal.getIcon() == null) {
            JOptionPane.showMessageDialog(null, "Por favor, insira uma imagem primeiro", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        ImageIcon icon = (ImageIcon) labelOriginal.getIcon();
        Image img = icon.getImage();

        BufferedImage imagem = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_BYTE_BINARY);
        Graphics2D g2d = imagem.createGraphics();
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        int largura = imagem.getWidth();
        int altura = imagem.getHeight();

        int[][] original = new int[altura][largura];
        int[][] resultado = new int[altura][largura];

        for (int y = 0; y < altura; y++) {
            for (int x = 0; x < largura; x++) {
                int pixel = imagem.getRGB(x, y) & 0xFFFFFF;
                original[y][x] = (pixel == 0xFFFFFF) ? 0 : 1;
            }
        }

        for (int y = 1; y < altura - 1; y++) {
            for (int x = 1; x < largura - 1; x++) {
                boolean deveManter = true;
                for (int j = -1; j <= 1; j++) {
                    for (int i = -1; i <= 1; i++) {
                        if (original[y + j][x + i] == 0) {
                            deveManter = false;
                            break;
                        }
                    }
                    if (!deveManter) break;
                }
                resultado[y][x] = deveManter ? 1 : 0;
            }
        }

        BufferedImage erodida = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < altura; y++) {
            for (int x = 0; x < largura; x++) {
                int cor = resultado[y][x] == 1 ? Color.BLACK.getRGB() : Color.WHITE.getRGB();
                erodida.setRGB(x, y, cor);
            }
        }

        labelTransformado.setIcon(new ImageIcon(erodida));
        int resposta = JOptionPane.showConfirmDialog(null, "Deseja fazer mais alterações na imagem?", "Finalizar?", JOptionPane.YES_NO_OPTION);
        if (resposta == JOptionPane.YES_OPTION) {
            labelOriginal.setIcon(new ImageIcon(erodida));
            labelTransformado.setIcon(null);
        } else {
            if (imagemOriginalImportada != null) {
                Image imgOriginal = imagemOriginalImportada.getScaledInstance(400, 300, Image.SCALE_SMOOTH);
                labelOriginal.setIcon(new ImageIcon(imgOriginal));
            }
        }
    }

}
