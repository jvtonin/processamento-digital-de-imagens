import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.lang.Math;

    
     public class Transformacoes {

        public void transladarImagem(JLabel labelOriginal, JLabel labelTransformado, BufferedImage imagemOriginalImportada) {
            if (labelOriginal.getIcon() != null) {
                ImageIcon originalIcon = (ImageIcon) labelOriginal.getIcon();
                Image img = originalIcon.getImage();
        
                BufferedImage bufferedImage = new BufferedImage(
                    img.getWidth(null),
                    img.getHeight(null),
                    BufferedImage.TYPE_INT_ARGB
                );
                Graphics2D g2d = bufferedImage.createGraphics();
                g2d.drawImage(img, 0, 0, null);
                g2d.dispose();

                String inputX = JOptionPane.showInputDialog(null, "Digite o valor de translação em X:", "Translação", JOptionPane.QUESTION_MESSAGE);
                String inputY = JOptionPane.showInputDialog(null, "Digite o valor de translação em Y:", "Translação", JOptionPane.QUESTION_MESSAGE);

                if (inputX == null || inputY == null) {
                    return; 
                }

                int tx = 0;
                int ty = 0;
                try {
                    tx = Integer.parseInt(inputX);
                    ty = Integer.parseInt(inputY);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Por favor, insira apenas números inteiros.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                

                int largura = bufferedImage.getWidth();
                int altura = bufferedImage.getHeight();
        
                BufferedImage novaImagem = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_ARGB);
        
                for (int y = 0; y < altura; y++) {
                    for (int x = 0; x < largura; x++) {
                        int novoX = x + tx;
                        int novoY = y + ty;
                        if (novoX >= 0 && novoX < largura && novoY >= 0 && novoY < altura) {
                            int cor = bufferedImage.getRGB(x, y);
                            novaImagem.setRGB(novoX, novoY, cor);
                        }
                    }
                }
        
                Image imgEscalada = novaImagem.getScaledInstance(400, 300, Image.SCALE_SMOOTH);
                labelTransformado.setIcon(new ImageIcon(imgEscalada));

                int resposta = JOptionPane.showConfirmDialog(null, "Deseja fazer mais alterações na imagem?", "Finalizar?", JOptionPane.YES_NO_OPTION);
                if (resposta == JOptionPane.YES_OPTION) {
                    labelOriginal.setIcon(new ImageIcon(imgEscalada));
                    labelTransformado.setIcon(null);
                } else {
                    if (imagemOriginalImportada != null) {
                        Image imgOriginal = imagemOriginalImportada.getScaledInstance(400, 300, Image.SCALE_SMOOTH);
                        labelOriginal.setIcon(new ImageIcon(imgOriginal));
                    }
                }
            } else
            JOptionPane.showMessageDialog(null, "Por favor, insira uma imagem primeiro", "Erro", JOptionPane.ERROR_MESSAGE);
        }


        public void rotacionaImagem(JLabel labelOriginal, JLabel labelTransformado, BufferedImage imagemOriginalImportada) {
            if (labelOriginal.getIcon() != null) {
                ImageIcon originalIcon = (ImageIcon) labelOriginal.getIcon();
                Image img = originalIcon.getImage();
        
                BufferedImage bufferedImage = new BufferedImage(
                    img.getWidth(null),
                    img.getHeight(null),
                    BufferedImage.TYPE_INT_ARGB
                );
                Graphics2D g2d = bufferedImage.createGraphics();
                g2d.drawImage(img, 0, 0, null);
                g2d.dispose();
        
                int largura = bufferedImage.getWidth();
                int altura = bufferedImage.getHeight();
        
                String inputAngulo = JOptionPane.showInputDialog(null, "Digite o valor de rotação (90, 180 ou 270)", "Rotação", JOptionPane.QUESTION_MESSAGE);
        
                if (inputAngulo == null) return;
        
                int tAngulo;
                try {
                    tAngulo = Integer.parseInt(inputAngulo);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Por favor, insira apenas números inteiros.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                BufferedImage novaImagem;
        
                switch (tAngulo) {
                    case 90:
                        novaImagem = new BufferedImage(altura, largura, BufferedImage.TYPE_INT_ARGB);
                        for (int y = 0; y < altura; y++) {
                            for (int x = 0; x < largura; x++) {
                                int cor = bufferedImage.getRGB(x, y);
                                novaImagem.setRGB(altura - 1 - y, x, cor);
                            }
                        }
                        break;
        
                    case 180:
                        novaImagem = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_ARGB);
                        for (int y = 0; y < altura; y++) {
                            for (int x = 0; x < largura; x++) {
                                int cor = bufferedImage.getRGB(x, y);
                                novaImagem.setRGB(largura - 1 - x, altura - 1 - y, cor);
                            }
                        }
                        break;
        
                    case 270:
                        novaImagem = new BufferedImage(altura, largura, BufferedImage.TYPE_INT_ARGB);
                        for (int y = 0; y < altura; y++) {
                            for (int x = 0; x < largura; x++) {
                                int cor = bufferedImage.getRGB(x, y);
                                novaImagem.setRGB(y, largura - 1 - x, cor);
                            }
                        }
                        break;
        
                    default:
                        JOptionPane.showMessageDialog(null, "Ângulo inválido! Digite 90, 180 ou 270.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                }
        
                Image imgEscalada = novaImagem.getScaledInstance(400, 300, Image.SCALE_SMOOTH);
                labelTransformado.setIcon(new ImageIcon(imgEscalada));

                int resposta = JOptionPane.showConfirmDialog(null, "Deseja fazer mais alterações na imagem?", "Finalizar?", JOptionPane.YES_NO_OPTION);
                if (resposta == JOptionPane.YES_OPTION) {
                    labelOriginal.setIcon(new ImageIcon(imgEscalada));
                    labelTransformado.setIcon(null);
                } else {
                    if (imagemOriginalImportada != null) {
                        Image imgOriginal = imagemOriginalImportada.getScaledInstance(400, 300, Image.SCALE_SMOOTH);
                        labelOriginal.setIcon(new ImageIcon(imgOriginal));
                    }
                }
            } else
            JOptionPane.showMessageDialog(null, "Por favor, insira uma imagem primeiro", "Erro", JOptionPane.ERROR_MESSAGE);
        }


        public void espelharImagem(JLabel labelOriginal, JLabel labelTransformado, BufferedImage imagemOriginalImportada) {
            if (labelOriginal.getIcon() != null) {
                ImageIcon originalIcon = (ImageIcon) labelOriginal.getIcon();
                Image img = originalIcon.getImage();
        
                BufferedImage bufferedImage = new BufferedImage(
                    img.getWidth(null),
                    img.getHeight(null),
                    BufferedImage.TYPE_INT_ARGB
                );
                Graphics2D g2d = bufferedImage.createGraphics();
                g2d.drawImage(img, 0, 0, null);
                g2d.dispose();
        
                int largura = bufferedImage.getWidth();
                int altura = bufferedImage.getHeight();
        
                Object[] opcoes = {"Horizontal", "Vertical"};
                int escolha = JOptionPane.showOptionDialog(null, 
                    "Escolha o tipo de espelhamento", 
                    "Espelhamento", 
                    JOptionPane.DEFAULT_OPTION, 
                    JOptionPane.INFORMATION_MESSAGE, 
                    null, opcoes, opcoes[0]);
        
                if (escolha == JOptionPane.CLOSED_OPTION) {
                    return;
                }
        
                BufferedImage novaImagem = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_ARGB);
        
                for (int y = 0; y < altura; y++) {
                    for (int x = 0; x < largura; x++) {
                        int cor = bufferedImage.getRGB(x, y);
        
                        if (escolha == 0) {
                            novaImagem.setRGB(largura - 1 - x, y, cor);
                        } else { 
                            novaImagem.setRGB(x, altura - 1 - y, cor);
                        }
                    }
                }
        
                Image imgEscalada = novaImagem.getScaledInstance(400, 300, Image.SCALE_SMOOTH);
                labelTransformado.setIcon(new ImageIcon(imgEscalada));

                int resposta = JOptionPane.showConfirmDialog(null, "Deseja fazer mais alterações na imagem?", "Finalizar?", JOptionPane.YES_NO_OPTION);
                if (resposta == JOptionPane.YES_OPTION) {
                    labelOriginal.setIcon(new ImageIcon(imgEscalada));
                    labelTransformado.setIcon(null);
                } else {
                    if (imagemOriginalImportada != null) {
                        Image imgOriginal = imagemOriginalImportada.getScaledInstance(400, 300, Image.SCALE_SMOOTH);
                        labelOriginal.setIcon(new ImageIcon(imgOriginal));
                    }
                }
            }
            else
            JOptionPane.showMessageDialog(null, "Por favor, insira uma imagem primeiro", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        public void escalarImagem(JLabel labelOriginal, JLabel labelTransformado, BufferedImage imagemOriginalImportada) {
            if (labelOriginal.getIcon() != null) {
                ImageIcon originalIcon = (ImageIcon) labelOriginal.getIcon();
                Image img = originalIcon.getImage();
        
                BufferedImage bufferedImage = new BufferedImage(
                    img.getWidth(null),
                    img.getHeight(null),
                    BufferedImage.TYPE_INT_ARGB
                );
                Graphics2D g2d = bufferedImage.createGraphics();
                g2d.drawImage(img, 0, 0, null);
                g2d.dispose();
        
                int largura = bufferedImage.getWidth();
                int altura = bufferedImage.getHeight();
        
                String input = JOptionPane.showInputDialog(null, "Digite o fator de escala (ex: 2 para aumentar, 0.5 para diminuir):", "Escala", JOptionPane.QUESTION_MESSAGE);
                if (input == null) 
                    return;

                double escala = 1.0;

                try {
                    escala = Double.parseDouble(input);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Digite um número válido (ex: 1.5, 0.75, etc).", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                if (escala <= 0) {
                    JOptionPane.showMessageDialog(null, "O fator de escala deve ser maior que zero.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                int novaLargura = (int)(largura * escala);
                int novaAltura = (int)(altura * escala);
        
                BufferedImage novaImagem = new BufferedImage(novaLargura, novaAltura, BufferedImage.TYPE_INT_ARGB);
        
                for (int y = 0; y < novaAltura; y++) {
                    for (int x = 0; x < novaLargura; x++) {
                        int origX = (int)(x / escala);
                        int origY = (int)(y / escala);
        
                        origX = Math.min(origX, largura - 1);
                        origY = Math.min(origY, altura - 1);
        
                        int cor = bufferedImage.getRGB(origX, origY);
                        novaImagem.setRGB(x, y, cor);
                    }
                }
        
                Image imgEscalada = novaImagem.getScaledInstance(novaLargura, novaAltura, Image.SCALE_SMOOTH);
                labelTransformado.setIcon(new ImageIcon(imgEscalada));

                int resposta = JOptionPane.showConfirmDialog(null, "Deseja fazer mais alterações na imagem", "Finalizar?", JOptionPane.YES_NO_OPTION);
                if (resposta == JOptionPane.YES_OPTION) {
                    labelOriginal.setIcon(new ImageIcon(imgEscalada));
                    labelTransformado.setIcon(null);
                } else {
                    if (imagemOriginalImportada != null) {
                        Image imgOriginal = imagemOriginalImportada.getScaledInstance(400, 300, Image.SCALE_SMOOTH);
                        labelOriginal.setIcon(new ImageIcon(imgOriginal));
                    }
                }
            }
            else
            JOptionPane.showMessageDialog(null, "Por favor, insira uma imagem primeiro", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        
        
        
    }        

